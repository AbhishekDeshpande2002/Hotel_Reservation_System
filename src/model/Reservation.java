package model;

import java.util.Date;

public class Reservation {
	private final Customer customer;
	private final IRoom room;
	private final Date checkInDate;
	private final Date checkOutDate;
	
	public Reservation(Customer customer,IRoom room,Date checkInDate,Date checkOutDate){
		this.customer=customer;
		this.room=room;
		this.checkInDate=checkInDate;
		this.checkOutDate=checkOutDate;
	}
	
	
	public Customer getCustomer() {
		return customer;
	}


	public IRoom getRoom() {
		return room;
	}


	public Date getCheckInDate() {
		return checkInDate;
	}


	public Date getCheckOutDate() {
		return checkOutDate;
	}


	@Override
	public String toString() {
		return "Customer:"+customer+"\nRoom:"+room+"Check-In Date:"+checkInDate+"Check-Out Date"+checkOutDate;
	}
}
