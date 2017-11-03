package com.configServer;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication {
	static Logger logger = Logger.getLogger(ConfigServerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
		logger.info("Microserviço iniciado com sucesso!!");
	}
}
