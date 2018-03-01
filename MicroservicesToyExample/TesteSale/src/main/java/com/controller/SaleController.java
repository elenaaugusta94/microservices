package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.intercomm.AuthenticateInterface;
import com.intercomm.CustomerInterface;
import com.intercomm.ProductInterface;

@Controller
public class SaleController {

	@Autowired
	private CustomerInterface customer;

	@Autowired
	private ProductInterface product;
	
	@Autowired
	private AuthenticateInterface auth;
	
	@Autowired
	private RestTemplate restTemplate;


	@RequestMapping("/login")
	String login() throws Exception {
		return "index";
	}

	@RequestMapping("/service")
	String service() throws Exception {
		return "sale";
	}

	/**
	 * 
	 * Retorna os produtos atraves do product feign como uma string JSON
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public @ResponseBody String getProducts() {
		String produtos = product.getAllProduct();
		return produtos;
	}

	
	@RequestMapping(value = { "/sale" }, method = RequestMethod.GET)
	ModelAndView index2( ) {
		ModelAndView modelAndView = new ModelAndView();
		
		return modelAndView;
	}

	@RequestMapping("/")
	String index() throws Exception {
		return "sale";
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
	
	@RequestMapping(value= "/venda/autenticacao/{user}/{password}", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthentication(@PathVariable String user, @PathVariable String password) {
		System.out.println("user: " + user + "password:  " + password);
		String response = auth.verifyAuthentication(user, password);
		//		String response = restTemplate.getForObject(
//				"http://MsAutenticacao/autenticate/" + user +  password, null, String.class);
//		System.out.println("RESPONSE/ " + response);
		return response;
	}
	
//	@RequestMapping("/venda/autenticacao/{user}/{senha}")
//	@ResponseBody
//	public String getAuthentication(@PathVariable("user") String user, @PathVariable("senha") String senha) {
//		String response = restTemplate.postForObject(
//				"http://MsAuthentication/api/authenticate?username=" + user + "&password=" + senha, null, String.class);
//		return response;
//
//	}
	@RequestMapping("/atualizaProducts/{qnt}/{id}")
	@ResponseBody
	public String productUpdate(@PathVariable("qnt") int qnt, @PathVariable("id") String id){
		String response = restTemplate.postForObject(
				"http://MsProduct/updateProduct/" + qnt + "/" + id, null, String.class);
		return response;
	}
}
