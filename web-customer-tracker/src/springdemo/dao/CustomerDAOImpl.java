package springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springdemo.entity.Customer;
import springdemo.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject hibernate session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	// @Transactional means no need to write beginTransaction() and getTransaction().commit()
	// already moved to Service layer
	public List<Customer> getCustomers(int theSortField) 
	{
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// determine sort field
		String theFieldName = null;
		
		switch (theSortField) {
			case SortUtils.FIRST_NAME: 
				theFieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				theFieldName = "email";
				break;
			default:
				// if nothing matches the default to sort by lastName
				theFieldName = "lastName";
		}
		
		// create a query
		
		Query<Customer> theQuery = currentSession.createQuery("from Customer ORDER BY " + theFieldName, Customer.class);
		
		/*
		// ORDER BY cannot be parameterized
		theQuery.setParameter("fieldName", theFieldName);
		*/
		
		/*
		String queryString = "from Customer order by " + theFieldName;
		
		Query<Customer> theQuery = currentSession.createQuery(queryString, Customer.class);
		*/
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) 
	{
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer
		// saveOrUpdate means INSERT new customer if primary key (id) not exist
		// or UPDATE for existing customer with primary key (id)
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) 
	{
		
		// get current hiberante session
		Session currentSession = sessionFactory.getCurrentSession();
		
		
		// read from database using that primary key (customer Id)
		Customer customer = currentSession.get(Customer.class, theId);
				
		/*
		Query<Customer> theQuery = currentSession.createQuery("SELECT c FROM Customer c WHERE c.id = :theId", Customer.class);
		
		theQuery.setParameter("theId", theId);
		
		Customer customer = theQuery.getSingleResult();
		*/
		
		
		return customer;
	}

	@Override
	public void deleteCustomer(int theId) 
	{
		// get current hiberante session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query theQuery = currentSession.createQuery("DELETE from Customer WHERE id = :customerId");
		
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) 
	{
		// get current hiberante session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery;
        
	    //
	    // only search by name if theSearchName is not empty
	    //
	    if (theSearchName != null && theSearchName.trim().length() > 0) 
	    {
	        // search for firstName or lastName ... case insensitive
	        theQuery = currentSession.createQuery("from Customer WHERE LOWER(firstName) LIKE :theName OR LOWER(lastName) LIKE :theName ", Customer.class);
	        theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");	
	    }
	    else 
	    {
	        // theSearchName is empty ... so just get all customers
	        theQuery = currentSession.createQuery("from Customer ORDER BY lastName", Customer.class);            
	    }
	    
	    // execute query and get result list
	    List<Customer> customers = theQuery.getResultList();
	            
	    // return the results        
	    return customers;
		
	}

}
