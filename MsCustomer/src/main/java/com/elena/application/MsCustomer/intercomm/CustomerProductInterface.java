package com.elena.application.MsCustomer.intercomm;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value="MsProduct", url="localhost:9092")
public interface CustomerProductInterface {

	@RequestMapping( method = RequestMethod.GET,value ="/product/getProduct/{id}")
	@ResponseBody	
	String getProductID(@PathVariable("id") String id);

}
