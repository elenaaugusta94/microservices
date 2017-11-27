package com.elena.application.MsSaleSpring.controller;

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

	CustomerService cs = new CustomerService();

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

	@RequestMapping("/sale")
	public String greeting(@RequestParam(value = "contents", required = true) String name, Model model) {
		Map<String, Object> atributos = new HashMap<>();
		String productsJson = "[]";
		try {

			atributos.put("productsJson", productsJson);
		} catch (Exception ex) {
			model.addAttribute("server_not_available",
					"Product server not available (" + ex.getClass().getName() + ": " + ex.getMessage() + ")");
			return "sale";
		}

		atributos.put("client_search", true);
		model.addAllAttributes(atributos);

		return "sale";
	}

	@RequestMapping(value = { "{id}" }, method = RequestMethod.GET)
	ModelAndView index(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id", id);
		modelAndView.setViewName("sale");
		// return
		// SaleController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return modelAndView;// "sale" ;//"sale";
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

	@RequestMapping("/venda/getCustomers")
	@ResponseBody
	public String GetServiceInstancesRt() {

		String response = restTemplate.getForObject("http://MsCustomer/customer/getCustomers", String.class);
		return response;
	}

	@RequestMapping("/venda/autenticacao/{user}/{senha}")
	@ResponseBody
	public String getAuthentication(@PathVariable("user") String user, @PathVariable("senha") String senha) {
		String response = restTemplate.postForObject(
				"http://MsAuthentication/api/authenticate?username=" + user + "&password=" + senha, null, String.class);
		return response;

	}

}