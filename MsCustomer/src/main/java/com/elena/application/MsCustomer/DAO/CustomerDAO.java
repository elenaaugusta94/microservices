package com.elena.application.MsCustomer.DAO;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elena.application.MsCustomer.entities.Customer;


@Transactional
@Repository
public interface CustomerDAO extends CrudRepository<Customer,String>{
	  
	  public Customer save(Customer c);
	  public Customer findByCpf(String cpf);
	  public Customer findByName(String name);
	  public void delete(Customer c);
	  public List<Customer> findAll();


	
}
