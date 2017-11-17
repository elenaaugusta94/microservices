package intercomm;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@FeignClient("MsCustomer")
public interface ProductCustomer {

	@RequestMapping(method = RequestMethod.GET, value = "/product/customer/{id}")
	String getProductID(@PathVariable("id") String id);

}
