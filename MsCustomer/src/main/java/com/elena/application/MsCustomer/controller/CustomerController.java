package com.elena.application.MsCustomer.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elena.application.MsCustomer.entities.Customer;
import com.elena.application.MsCustomer.intercomm.CustomerProductInterface;

//@ComponentScan("com.elena.application.MsCustomer.intercomm")
@RestController
//@RequestMapping("/customer")
public  class CustomerController {

	@Autowired	
	private CustomerService customer ;
	
	@Autowired 		 
	CustomerProductInterface product;
	
	private CustomerController(){}
	
	protected Logger logger = Logger.getLogger(CustomerController.class.getName());
	

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		return "index";
	}
		
	@RequestMapping("*")
	public String fallbackMethod() {
		return "fallback method";
	}

	@RequestMapping(value = "/customer/add/{name}/{cpf}/{email}", method = RequestMethod.GET)
	@ResponseBody
	public String addCustomer(@PathVariable String name, @PathVariable String cpf, @PathVariable String email) {
		
		
		try {
			Customer c = new Customer(name, cpf, email);
			System.out.println("Cliente:  "+ c.getName());
			customer.saveCustomer(c);
			return "Success! ";
			
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		

	}

	//@RequestMapping("/customer/getCustomers")
	//@ResponseBody
	//public List<String> getAllCustomers() {
		//return customer.findAllCustomer();	
	//}

	@RequestMapping("/customer/getCustomerCpf/{cpf}")
	@ResponseBody
	public Customer getClientePorCpf(@PathVariable String cpf) {
		Customer c = customer.findCustomerByCpf(cpf);
		return c;
	}
	
	@RequestMapping("/customer/products/{id}")
	@ResponseBody	
	//@Bean
	 private String productID(@PathVariable("id") String id){
		try {
			return product.getProductID(id);//id);
			
			
		} catch (Exception ex) {
			return "Error at finded product: " + ex.toString();
		}
		 
	 }
	 @RequestMapping("/customer/delete/{cpf}")
	 @ResponseBody
	 public String delete(@PathVariable String cpf) {
	 
		 return null; //customer.deleteCustomer(cpf);
	 }
			

}	