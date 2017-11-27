package com.elena.application.MsSaleSpring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.elena.application.MsSaleSpring.exception.ServiceException;
import com.elena.application.MsSaleSpring.intercomm.CustomerInterface;

public class CustomerService {

	@Autowired
	CustomerInterface customer;
	
	
	public String getCustomerByCpf(String cpf){
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
}
