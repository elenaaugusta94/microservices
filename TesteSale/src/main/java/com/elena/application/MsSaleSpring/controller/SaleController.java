package com.elena.application.MsSaleSpring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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
	
	private HttpServletRequest p1;
	private HttpServletResponse p2;
	
	CustomerService cs;


	@RequestMapping("/")
	String index() throws Exception {
		return "sale";
	}

	@RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
	ModelAndView index(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", id);
        modelAndView.setViewName("sale");
        //return SaleController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return modelAndView;//"sale" ;//"sale";
	}
	
	
	@RequestMapping("*")
	String home() {
		return "falback";
	}

	@RequestMapping("/venda/getCustomerCpf/{cpf}")
	@ResponseBody
	public String getClient(@PathVariable("cpf") String cpf) {
		if (!cpf.isEmpty()) {
			String clientJson = customer.getCustomerCpf(cpf);
			if (clientJson.isEmpty()) {
				return null;
			} else {
				return clientJson;
			}
		} else {
			return null;
		}

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
