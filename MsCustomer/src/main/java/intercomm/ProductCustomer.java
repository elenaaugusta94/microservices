package intercomm;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("MsProduct")
@Component
public interface ProductCustomer {

	@RequestMapping( method = RequestMethod.GET,value ="/product/getProduct/{id}")
	String getProductID(@PathVariable("id") String id);

}
