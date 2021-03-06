package com.example.saleApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.elena.application.MsSaleSpring.controller.SaleController;

@ComponentScan({"com.elena.application.MsSaleSpring.controller"})

@EnableEurekaClient
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients(basePackages = {"com.elena.application.MsSaleSpring.intercomm", "com.elena.application.MsSaleSpring.controller"})
@SpringBootApplication(scanBasePackages={"com.elena.application.MsSaleSpring.intercomm","com.elena.application.MsSaleSpring.controller"})
public class TesteSaleApplication {

	public static void main(String[] args) {
		teste();
		SaleController s = new SaleController();
		s.getAuthentication("elena", "123");
		SpringApplication.run(TesteSaleApplication.class, args);
		
	}
	public  static void teste() {
		System.err.println("OLA");
	}
	
	 @LoadBalanced
	  @Bean
	  public RestTemplate restTemplate(RestTemplateBuilder builder) {
	     return builder.build();
	  }
}
