package msCustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("entities")
@ComponentScan("controller")
@ComponentScan("intercomm")
@EnableJpaRepositories("DAO")
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages={"controller","DAO","entities","intercomm"})
public class CustomerApp {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerApp.class,args);

	}


}
