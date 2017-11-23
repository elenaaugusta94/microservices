package com.elena.application.MsCustomer.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.elena.application.MsCustomer.entities")
@ComponentScan({"com.elena.application.MsCustomer.controller"})
//@ComponentScan("com.elena.application.MsCustomer.intercomm")
@EnableJpaRepositories("com.elena.application.MsCustomer.DAO")
@EnableEurekaClient
@EnableDiscoveryClient
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableFeignClients
@SpringBootApplication(scanBasePackages={"com.elena.application.MsCustomer"})
public class CustomerApp {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerApp.class,args);

	}


}
