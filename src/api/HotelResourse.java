package api;


import java.util.Collection;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResourse {

	private static CustomerService customerService = CustomerService.getInstance();
	private static ReservationService reservationService= ReservationService.getInstance();

	public static void setServices(CustomerService customerService,ReservationService reservationService) {
		HotelResourse.customerService=customerService;
		HotelResourse.reservationService=reservationService;
	}

	public static Customer getCustomer(String email) {
		return customerService.getCustomer(email);
	}

	public static void createACustomer(String email,String firstName,String lastName) {
		customerService.addCustomer(email, firstName, lastName);
	}

	public static IRoom getRoom(String roomNumber) {
		return reservationService.getARoom(roomNumber);
	}
	public static Reservation bookARoom(String customerEmail,IRoom room, Date checkInDate,Date checkOutDate) {
		Customer customer = customerService.getCustomer(customerEmail);
		if (customer == null) {
			System.out.println("Customer not found\n please create your account");
			MainMenu.displayMainMenu();
		}
		if(reservationService.reserveARoom(customer, room, checkInDate, checkOutDate)==null) {
			System.out.println("Room is already booked for these dates.");
			MainMenu.displayMainMenu();
		}
		return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
	}

	public static Collection<Reservation> getCustomerReservations(String email){
		Customer customer = customerService.getCustomer(email);
		if (customer == null) {
			System.out.println("Customer not found\n please create your account");
			MainMenu.displayMainMenu();
		}
		return reservationService.getCustomersReservation(customer);
	}

	public static Collection<IRoom> findARoom(Date checkIn ,Date checkOut){
		return reservationService.findRooms(checkIn, checkOut);
	}

	public static Collection<IRoom> findRecommendedRooms(Date checkIn, Date checkOut) {
		return reservationService.findRecommendedRooms(checkIn, checkOut);
	}

}
