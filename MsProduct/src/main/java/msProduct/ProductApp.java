package msProduct;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(scanBasePackages={"controller","DAO","entities"})
@EntityScan("entities")
@ComponentScan("controller")
@EnableJpaRepositories("DAO")
@EnableEurekaClient
public class ProductApp {

	public static void main(String[] args) {
		
		SpringApplication.run(ProductApp.class, args);

	}

}
