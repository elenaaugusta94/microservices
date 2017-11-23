package com.elena.application.MsCustomer.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elena.application.MsCustomer.entities.Customer;
import com.elena.application.MsCustomer.intercomm.CustomerProductService;

@RestController
public  class CustomerController {

	@Autowired	
	private CustomerService customer ;
	
	@Autowired
	private CustomerProductService product;
	
	private CustomerController(){}
	
	protected Logger logger = Logger.getLogger(CustomerController.class.getName());
	
		
	@RequestMapping("*")
	public String fallbackMethod() {
		return "fallback method";
	}

	@RequestMapping(value = "add/{name}/{cpf}/{email}", method = RequestMethod.GET)
	@ResponseBody
	public String addCustomer(@PathVariable String name, @PathVariable String cpf, @PathVariable String email) {
		
		
		try {
			Customer c = new Customer(name, cpf, email);
			System.out.println("Cliente:  "+ c.getName());
			customer.saveCustomer(c);
			
			
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "Success! ";

	}

	@RequestMapping("/getCustomers")
	@ResponseBody
	public List<String> getAllCustomers() {
		return customer.findAllCustomer();	
	}

	@RequestMapping("getCustomerCpf/{cpf}")
	@ResponseBody
	public Customer getClientePorCpf(@PathVariable String cpf) {
		Customer c = customer.findCustomerByCpf(cpf);
		return c;
	}
	
	@RequestMapping("/customer/products/{id}")
	@ResponseBody
	
	 private String productID(@PathVariable("id") String id){
		try {
			return product.getProductsInCustomer(id);
			
			
		} catch (Exception ex) {
			return "Error at finded product: " + ex.toString();
		}
		 
	 }
	 @RequestMapping("delete/{cpf}")
	 @ResponseBody
	 public String delete(@PathVariable String cpf) {
	 
		 return customer.deleteCustomer(cpf);
	 }
			

}
