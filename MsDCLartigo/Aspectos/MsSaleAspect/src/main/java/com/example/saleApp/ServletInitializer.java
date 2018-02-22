package main.java.com.example.saleApp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.example.saleApp.TesteSaleApplication;



public class ServletInitializer extends SpringBootServletInitializer{
	@Override
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TesteSaleApplication.class);
	}
}
