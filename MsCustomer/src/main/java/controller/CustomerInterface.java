package controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public interface CustomerInterface {
	@RequestMapping("customer/getCustomers")
	@ResponseBody
	public List<String> getAllClientes();
	
//	@RequestMapping(value = "add/{name}/{cpf}/{email}", method = RequestMethod.GET)
//	@ResponseBody
//	public String addCustomer(String nome, String cpf, String email);	
	
	@RequestMapping("customer/getCustomer/{cpf}")
	@ResponseBody
	public String getClienteByCpf(String cpf);
	
	@RequestMapping("/index")
	@ResponseBody
	public String index();
}
