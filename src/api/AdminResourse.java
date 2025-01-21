package api;

import java.util.Collection;
import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResourse {
	private static CustomerService customerService = CustomerService.getInstance();
	private static ReservationService reservationService = ReservationService.getInstance();
	
	public static void setServices(CustomerService customerService,ReservationService reservationService) {
		AdminResourse.customerService=customerService;
		AdminResourse.reservationService=reservationService;
	}
	
	public static Customer getCustomer(String Email) {
		return customerService.getCustomer(Email);
	}
	
	public static void addRoom(IRoom rooms) {
		reservationService.addRoom(rooms);
	}
	
	public static Collection<IRoom> getAllRooms(){
		return reservationService.getAllRooms();
	}
	
	public static Collection<Customer> getAllCustomers(){
		return customerService.getAllCustomers();
	}
	
	public static void displayAllReservations() {
		reservationService.printAllReservations();
	}
}
