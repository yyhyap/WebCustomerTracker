package springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springdemo.dao.CustomerDAO;
import springdemo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	// need to inject CustomerDAO
	@Autowired
	private CustomerDAO customerDAO;
	
	
	@Override
	@Transactional
	// @Transactional means no need to write beginTransaction() and getTransaction().commit()
	public List<Customer> getCustomers(int theSortField) 
	{
		return customerDAO.getCustomers(theSortField);
	}


	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) 
	{
		customerDAO.saveCustomer(theCustomer);
	}


	@Override
	@Transactional
	public Customer getCustomer(int theId) 
	{
		return customerDAO.getCustomer(theId);
	}


	@Override
	@Transactional
	public void deleteCustomer(int theId) 
	{
		customerDAO.deleteCustomer(theId);		
	}


	@Override
	@Transactional
	public List<Customer> searchCustomers(String theSearchName) 
	{
		return customerDAO.searchCustomers(theSearchName);
	}

}
