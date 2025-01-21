package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {

	public static ReservationService instance;

	private Map<String, IRoom>rooms;

	private List<Reservation>reservations;

	private ReservationService() {
		rooms = new HashMap<>();
		reservations = new ArrayList<>();
	}

	public static ReservationService getInstance() {
		if(instance == null) {
			instance = new ReservationService();
		}
		return instance;

	}

	public void addRoom(IRoom room) {
		rooms.put(room.getRoomNumber(), room);
	}

	public IRoom getARoom(String roomNumber) {
		return rooms.get(roomNumber);
	}

	public Reservation reserveARoom(Customer customer,IRoom room,Date checkInDate,Date checkOutDate) {
		if(!isRoomReserved(room, checkInDate, checkOutDate)) {
		Reservation reservation =new Reservation(customer,room,checkInDate,checkOutDate);
		reservations.add(reservation);
		return reservation;
		}else {
			return null;
		}
	}

	public Collection<IRoom> findRooms(Date checkInDate,Date checkOutDate){
		Collection<IRoom> availableRooms=new ArrayList<>();
		for(IRoom room:rooms.values()) {
			if(!isRoomReserved(room,checkInDate,checkOutDate)) {
				availableRooms.add(room);
			}
		}
		return availableRooms;
	}

	public Collection<Reservation> getCustomersReservation(Customer customer){
		List<Reservation> customerReservations = new ArrayList<>();
		for(Reservation reservation:reservations) {
			if(reservation.getCustomer().equals(customer)) {
				customerReservations.add(reservation);
			}
		}
		return customerReservations;
	}

	public void printAllReservations() {
		for(Reservation reservation:reservations) {
			System.out.println(reservation);
		}
	}

	private boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
		for(Reservation reservation:reservations) {
			if(reservation.getRoom().equals(room) && !((reservation.getCheckInDate().after(checkOutDate)) || (reservation.getCheckOutDate().before(checkInDate)))) {
				return true;
			}
		}
		return false;
	}
	public Collection<IRoom> getAllRooms(){
		return new ArrayList<>(rooms.values());
	}

	public Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
		int retryCount = 0; 
		while (retryCount < 2) {
			Collection<IRoom> availableRooms = findRooms(checkInDate, checkOutDate);
			if (!availableRooms.isEmpty()) {
				return availableRooms;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(checkInDate);
			calendar.add(Calendar.DATE, 7);
			checkInDate = calendar.getTime();

			calendar.setTime(checkOutDate);
			calendar.add(Calendar.DATE, 7);
			checkOutDate = calendar.getTime();

			retryCount++;
		}
		return Collections.emptyList();
	}
}
