package com.elena.application.MsSaleSpring.intercomm;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="MsProduct", configuration = FeignConfiguration.class)
public interface ProductInterface {

	@RequestMapping( method = RequestMethod.GET, value="/product/getProduct/{id}")
	public String getProduct(@PathVariable("id") String id);
}
