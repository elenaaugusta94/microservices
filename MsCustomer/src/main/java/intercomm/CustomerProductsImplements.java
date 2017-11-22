package intercomm;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerProductsImplements {
	
	
	ProductCustomer pc;
	
	public String getProductsInCustomer(@PathVariable("id") String id){
		return pc.getProductID(id);
		
	}

}
