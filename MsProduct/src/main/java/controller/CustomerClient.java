package controller;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name="MsCustomer")
public interface CustomerClient {
	
	//@RequestMapping(value = "/customers/getCustomers", method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.GET, value = "/customer/getCustomer/{id}")
	@ResponseBody	
	public String getCustomer(@RequestParam(value = "id") String id);
	//public List<String> getCustomer(@RequestParam(value = "customers") List<String> customers);

}
