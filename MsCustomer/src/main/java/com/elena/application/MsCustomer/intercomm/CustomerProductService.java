package com.elena.application.MsCustomer.intercomm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CustomerProductService {
	
	@Autowired
	private CustomerProductInterface pc;
	
	@Autowired
	public String getProductsInCustomer(String id) {
		try {
			return pc.getProductID(id);
			

		} catch (ArrayIndexOutOfBoundsException e) {

			throw new UnsupportedOperationException("Not supported yet.");

		}

	}


}
