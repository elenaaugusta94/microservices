package com.elena.application.MsCustomer.intercomm;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="MsProduct")
@Service
public interface CustomerProductInterface {

	@RequestMapping( method = RequestMethod.GET,value ="/product/getProduct/{id}")
	String getProductID(@PathVariable("id") String id);

}
