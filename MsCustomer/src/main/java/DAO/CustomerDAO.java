package DAO;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import entities.Customer;


@Transactional
public interface CustomerDAO extends CrudRepository<Customer,String>{

	
	  public Customer save(Customer c);
	  public Customer findByCpf(String cpf);
	  public Customer findByName(String name);
	  public void delete(Customer c);
	  public List<Customer> findAll();


	
}
