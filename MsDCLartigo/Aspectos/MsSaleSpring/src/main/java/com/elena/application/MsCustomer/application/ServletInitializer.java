package com.elena.application.MsCustomer.application;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer{
	@Override
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SaleApp.class);
	}
}
