package springdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import springdemo.entity.Customer;
import springdemo.service.CustomerService;
import springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject the customer Service
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	// or can use @GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required=false) String sort)
	{
		// get customers from the Customer Service
		List<Customer> theCustomers = null;
		
		// check for sort field
		if(sort != null)
		{
			int theSortField = Integer.parseInt(sort);
			
			theCustomers = customerService.getCustomers(theSortField);
		}
		else
		{
			// no sort field provided ... default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@RequestMapping(value="/showFormForAdd", method=RequestMethod.GET)
	public String showFormForAdd(Model model)
	{
		// create MODEL ATTRIBUTE to BIND FORM DATA
		Customer theCustomer = new Customer();
		
		model.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@RequestMapping(value="/saveCustomer", method=RequestMethod.POST)
	// value="/saveCustomer" is mapping to action="saveCustomer" in customer-form.jsp
	// @ModelAttribute("customer") is mapping to modelAttribute="customer" in customer-form.jsp
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer theCustomer, BindingResult theBindingResult)
	{
		// check if there is any validating error
		if(theBindingResult.hasErrors())
		{
			return "customer-form";
		}
		else
		{
			// save the customer using service
			customerService.saveCustomer(theCustomer);
			
			return "redirect:/customer/list";
		}
	}
	
	@RequestMapping(value="/showFormForUpdate", method=RequestMethod.GET)
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model model)
	{
		// get the customer from service
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", theCustomer);
		
		// send over to the form		
		return "customer-form";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteCustomer(@RequestParam("customerId") int theId)
	{
		// delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model model)
	{
		 // search customers from the service
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
                
        // add the customers to the model
        model.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
}
