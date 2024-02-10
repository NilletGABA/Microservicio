package com.paymentchain.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
        
@SpringBootApplication
@EnableEurekaClient
//@EnableSwagger2
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
//        @Bean
//        public Docket ProductApi(){
//            return new Docket(DocumentationType.SWAGGER_2).select()
//                    .apis(RequestHandlerSelectors.basePackage("com.paymentchain")).build();
//        }

}
