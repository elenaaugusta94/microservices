package com.elena.application.MsSaleSpring.controller;

import java.io.Console;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elena.application.MsSaleSpring.intercomm.CustomerInterface;
import com.elena.application.MsSaleSpring.intercomm.ProductInterface;

@Controller
public class CustomerService {
	


	@Autowired
	CustomerInterface customer;
	
	@Autowired
	ProductInterface product;
	
	
	
//	public String executa(HttpServletRequest req) throws Exception {
//		
//		String clientJson;
//	
//		System.out.println("Entrou aqui no CustomerService??");
//		try {
//			String cpf = req.getParameter("cpf");
//			
//			clientJson = customer.getCustomerCpf(cpf);
//		} catch (Exception ex) {
//			req.setAttribute("server_not_available",
//					"Client server not available (" + ex.getClass().getName() + ": " + ex.getMessage() + ")");
//			return "sale";
//		}
//		String productsJson = "[]";
//		try {
//			productsJson = product.getAllProduct();
//		} catch (Exception ex) {
//			req.setAttribute("server_not_available",
//					"Product server not available (" + ex.getClass().getName() + ": " + ex.getMessage() + ")");
//			return "sale";
//		}
//		req.setAttribute("jsonProducts", productsJson);
//		req.setAttribute("client", clientJson);
//		req.setAttribute("client_search", true);
//		return "sale";
//	}
//


	
}
