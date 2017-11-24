package com.elena.application.MsCustomer.intercomm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("product")
public class CustomerProductService {
	
	@Autowired
	@Qualifier(value="product")
	private  CustomerProductInterface product;
	
	public CustomerProductService(){}
	
	@Bean
	public String getProductsInCustomer(String id) {
		try {
			return product.getProductID(id);
			

		} catch (ArrayIndexOutOfBoundsException e) {

			throw new UnsupportedOperationException("Not supported yet.");

		}

	}


	public CustomerProductInterface getProduct() {
		return product;
	}


	public void setProduct(CustomerProductInterface product) {
		this.product = product;
	}


}
