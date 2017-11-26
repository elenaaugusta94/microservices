package com.elena.application.MsSaleSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.elena.application.MsSaleSpring.intercomm.CustomerInterface;
import com.elena.application.MsSaleSpring.intercomm.ProductInterface;

@Controller
public class SaleController {

	@Autowired
	CustomerInterface customer;

	@Autowired
	ProductInterface product;

	@Autowired
	NewsLetterService newsLetter;
	
	@Autowired
	private RestTemplate restTemplate;


	@RequestMapping("/")
	String index() {
		return "sale";
	}

	@RequestMapping("*")
	String home() {
		return "falback";
	}

	@RequestMapping("/venda/getCustomerCpf/{cpf}")
	@ResponseBody
	public String getClient(@PathVariable("cpf") String cpf) {
		return customer.getCustomerCpf(cpf);
	}

	@RequestMapping("/venda/getProduct/{id}")
	@ResponseBody
	public String getProduct(@PathVariable("id") String id) {
		return product.getProduct(id);
	}

	@RequestMapping("/venda/getCustomers")
	@ResponseBody
	public String GetServiceInstancesRt() {

		String response = restTemplate.getForObject("http://MsCustomer/customer/getCustomers", String.class);
		return response;
	}

	
	

}
