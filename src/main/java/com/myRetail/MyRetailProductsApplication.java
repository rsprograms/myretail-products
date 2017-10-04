package com.myRetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.myRetail.entity.ProductEntity;
import com.myRetail.model.ProductInfo;
import com.myRetail.service.ProductService;
import com.myRetail.utility.ProductUtility;

@SpringBootApplication
@RestController
public class MyRetailProductsApplication {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private Gson gson;

	@Value("${productinfo.url}")
	private String productInfoURL;

	private final String jsonMimeType = "application/json; charset=utf-8";
	private final String textMimeType = "text/plain; charset=us-ascii";
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(MyRetailProductsApplication.class, args);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getProduct(@PathVariable("id") Integer productId, RestTemplate restTemplate) {

		ProductEntity productEntity = productService.findByProductId(productId);
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(productInfoUsername, productInfoPassword));
		ProductInfo productInfo = gson.fromJson(
				restTemplate.getForEntity(productInfoURL + productId, String.class).getBody(), ProductInfo.class);

		return getResponseEntity(gson.toJson(ProductUtility.convertEntityToProduct(productEntity, productInfo.getName())), jsonMimeType, HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateProduct(@PathVariable("id") Integer productId, @RequestBody(required = true) ProductEntity product) {
		
		boolean productExists = productService.productExists(productId);
		
		if (productExists) {
			product.setProductId(productId);
			productService.saveProduct(product);
			return getResponseEntity("Success", textMimeType, HttpStatus.OK);
		} else {
			return getResponseEntity("Product does not exists", textMimeType, HttpStatus.UNPROCESSABLE_ENTITY);
		}		
	}
		
	private ResponseEntity<String> getResponseEntity(String responseStr, String responseType, HttpStatus httpStatus){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", responseType);
		return new ResponseEntity<String>(responseStr, responseHeaders, httpStatus);		
	}
	
	@Value("${productinfo.user.name}")
	private String productInfoUsername;

	@Value("${productinfo.user.password}")
	private String productInfoPassword;
}
