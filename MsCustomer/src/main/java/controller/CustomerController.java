package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

import DAO.CustomerDAO;
import entities.Customer;
import intercomm.CustomerProductsImplements;
import intercomm.ProductCustomer;

@RestController
@Component
public  class CustomerController {

	@Autowired	
	private CustomerImplementsDAO customer;
	@Autowired
	private CustomerProductsImplements product ;
	
	private CustomerController(){
	
		
	}
	
	private List<Customer> customers ;
	
	protected Logger logger = Logger.getLogger(CustomerController.class.getName());

	
	
	
	//public CustomerController(){
//		customers=new ArrayList<Customer>();
//		customers.add(new Customer(1,"elena","1234567","elena@elena"));
//		customers.add(new Customer(1,"augusto","123","elena@elena"));
//		customers.add(new Customer(1,"juh","123456789","elena@elena"));
//		
//	}
	
	public String index() {

		return "gerenciador";
	}
	
	@RequestMapping("*")
	public String fallbackMethod() {
		return "fallback method";
	}

	@RequestMapping(value = "add/{name}/{cpf}/{email}", method = RequestMethod.GET)
	@ResponseBody
	public String addCustomer(@PathVariable String name, @PathVariable String cpf, @PathVariable String email) {
		String idCustomer = "";
		
		try {
			Customer c = new Customer(name, cpf, email);
			customer.saveCustomer(c);
			idCustomer = String.valueOf(c.getId());
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
	
//	@RequestMapping("getCustomerCpf/{cpf}")
//	@ResponseBody
//	public Customer getCustomersByCpf(@PathVariable String cpf ){
//		
//		for(Customer c: customers){
//			if(c.getCpf().equals(cpf))
//				return c;
//			else
//				logger.info("Não existe cliente com esse Cpf!");
//				
//		}
//		return null;		
//	}
	
	
	@RequestMapping("/customer/products/{id}")
	@ResponseBody
	 private String productID(@PathVariable("id") String id){
		product= new CustomerProductsImplements();
		String result = product.getProductsInCustomer(id);
		logger.info(String.format("Resultado", result));

		return result;
		 
	 }
	 @RequestMapping("delete/{cpf}")
	 @ResponseBody
	 public String delete(@PathVariable String cpf) {
	 
		 return customer.deleteCustomer(cpf);
	 }
			

}
