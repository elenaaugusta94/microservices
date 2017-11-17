package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

import DAO.CustomerDAO;
import entities.Customer;
import intercomm.ProductCustomer;

@RestController
public  class CustomerController {

	@Autowired	
	private CustomerDAO customerDAO;
	protected Logger logger = Logger.getLogger(CustomerController.class.getName());
	private ProductCustomer pc;
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
			customerDAO.save(c);
			idCustomer = String.valueOf(c.getId());
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "Sucess! ";

	}

	@RequestMapping("/getCustomers")
	@ResponseBody
	public List<String> getAllClientes() {
		List<Customer> customers = new ArrayList();
		List<String> c2 = new ArrayList();
	//	customerDAO.findAll().forEach(customers::add);
		return customerDAO.findAll().stream().map(a -> a.getName()).collect(Collectors.toList());
		
		
	}

	@RequestMapping("getCustomerCpf/{cpf}")
	@ResponseBody
	public Customer getClientePorCpf(@PathVariable String cpf) {
		Customer c = customerDAO.findByCpf(cpf);
		return c;

	}
	
	
	public String getClienteByCpf(@PathVariable String cpf) {
		Customer c = customerDAO.findByCpf(cpf);
		return c.toString();

	}
	@RequestMapping("/products/{id}")
	 private String productID(String id){
		logger.info(String.format("Customer.findById(%s)", id));
		System.out.println("veio algo?"+pc.getProductID(id));
		return pc.getProductID(id);
		 
	 }
	 @RequestMapping("delete/{cpf}")
	 @ResponseBody
	 public String delete(@PathVariable String cpf) {
	 Customer c = customerDAO.findByCpf(cpf);
	 try{
	 customerDAO.delete(c);
	 }catch (Exception ex) {
	 return "Error deleting the user:" + ex.toString();
	 }
	 return "Customer deleted!";
	 }

			

}
