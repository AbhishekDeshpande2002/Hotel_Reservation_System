package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Customer;

public class CustomerService {
	
	private static CustomerService instance;
	
	//map to store customers using email as key
	private Map<String, Customer> customers;
	
	private CustomerService() {
		customers = new HashMap<>();
	}
	
	public static CustomerService getInstance() {
		if(instance == null) {
			instance =new CustomerService();
		}
		return instance;
	}
	public void addCustomer(String email,String firstName,String lastName) {
		customers.put(email, new Customer(email,firstName,lastName));
	}
	public Customer getCustomer(String customerEmail) {
		return customers.get(customerEmail);
	}
	
	public Collection<Customer> getAllCustomers(){
		return new ArrayList<>(customers.values());
	}
}
