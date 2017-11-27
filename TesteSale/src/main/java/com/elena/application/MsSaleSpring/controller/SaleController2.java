package com.elena.application.MsSaleSpring.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.elena.application.MsSaleSpring.exception.ServiceException;
import com.elena.application.MsSaleSpring.intercomm.ProductInterface;

public class SaleController2 {

	
	
	 public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 Services s = new Services();
		 String ids[] = req.getParameterValues("product_id");
	        String qtds[] = req.getParameterValues("product_qtd");
	        if(ids != null && qtds != null && ids.length > 0 && qtds.length > 0 && ids.length == qtds.length){
	            try{
	                boolean success = s.serviceUpdateStock(ids, qtds);
	                req.setAttribute("sale_submitted", true);
	                req.setAttribute("success", success);
	            } catch (ServiceException | IOException ex) {
	                req.setAttribute("server_not_available", "Product server not available (" + ex.getClass().getName() + ": " + ex.getMessage() + ")");
	            }
	        }else{
	            req.setAttribute("sale_submitted", true);
	            req.setAttribute("success", false);
	        }
	        return "sale.jsp";
	    }
}
