package controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Customers implements CustomerClient{
	static Logger logger = Logger.getLogger(Customers.class);
	@Override
	public List<String> getCustomer(List<String> customers) {
		logger.info("Lista dos Clientes!!");
		return customers;
	}

}
