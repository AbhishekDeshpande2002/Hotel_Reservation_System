package api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;


import model.IRoom;


public class MainMenu {
	private static final Scanner sc = new Scanner(System.in);

	public static void displayMainMenu() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				System.out.println("Welcome to the Hotel Reservation Application");
				System.out.println("------------------------------------------------");
				System.out.println("1. Find and reserve a room");
				System.out.println("2. See my reservations");
				System.out.println("3. Create an Account");
				System.out.println("4. Admin");
				System.out.println("5. Exit");
				System.out.println("------------------------------------------------");
				System.out.print("Please enter your Choice: ");

				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					findAndReserveRoom();
					break;
				case 2:
					seeMyReservations();
					break;
				case 3:
					createAnAccount();
					break;
				case 4:
					AdminMainMenu.displayAdminMenu();
					break;
				case 5:
					System.out.println("exiting application\nVisit Again\nThank You");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid input, Please try again.");
				}
			} catch (Exception e) {
				System.out.println("Error: Invalid input.");
			}
		}
	}


	public static void findAndReserveRoom() {

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateFormat.setLenient(false);
			Date today = dateFormat.parse(dateFormat.format(new Date()));
			Date checkInDate=null;
			Date checkOutDate=null;
			while(checkInDate==null) {
				System.out.print("Enter check-in date (yyyy-MM-dd): ");
				Date receivedInDate = convertStringToDate(sc.next());
				if(receivedInDate!=null && !receivedInDate.before(today)){
					checkInDate=receivedInDate;
				}else {
					System.out.println("Check-in date must be today or later. Please try again.");
				}
			}

			while(checkOutDate==null) {
				System.out.print("Enter check-out date (yyyy-MM-dd): ");
				Date receivedOutDate = convertStringToDate(sc.next());
				if(receivedOutDate!=null && receivedOutDate.after(checkInDate)) {
					checkOutDate=receivedOutDate;
				}else {
					System.out.println("Check-out date must be after the check-in date. Please try again.");
				}
			}

			Collection<IRoom> availableRooms = HotelResourse.findARoom(checkInDate, checkOutDate);

			if (availableRooms.isEmpty()) {
				System.out.println("No available rooms for the given dates.");
				System.out.println("Searching for recommended rooms...");
				recommendAndBookRoom(checkInDate, checkOutDate);
				
			}else {
				System.out.println("Available rooms:");
				for (IRoom room : availableRooms) {
					System.out.println(room);
				}

				System.out.print("Enter room number to reserve: ");
				String roomNumber = sc.next();

				System.out.print("Enter your email: ");
				String customerEmail = sc.next();

				IRoom room = HotelResourse.getRoom(roomNumber);
				if (room == null) {
					System.out.println("Invalid room number. Please try again.");
					return;
				}
				HotelResourse.bookARoom(customerEmail, room,checkInDate,checkOutDate);
				System.out.println("Reservation successful!");
			}
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void seeMyReservations() {
		System.out.print("Enter your email: ");
		String email = sc.next();
		try {
			HotelResourse.getCustomerReservations(email).forEach(System.out::println);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}


	private static void createAnAccount() {
		try {
			System.out.print("Enter your email (format: name@domain.com): ");
			String email = sc.next();

			System.out.print("Enter your first name: ");
			String firstName = sc.next();

			System.out.print("Enter your last name: ");
			String lastName = sc.next();

			HotelResourse.createACustomer(email, firstName, lastName);
			System.out.println("Account created successfully!");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Please try again with a valid email address.");
		}
	}

	public static Date convertStringToDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			System.out.println("please enter the date in the format 'yyyy-MM-dd'");
		}
		return null;
	}

	public static void recommendAndBookRoom(Date checkInDate,Date checkOutDate) {
		Calendar calendar = Calendar.getInstance();
		int daysToAdd = 7;
		calendar.setTime(checkInDate);
		calendar.add(Calendar.DATE, daysToAdd);
		Date newCheckInDate = calendar.getTime();

		calendar.setTime(checkOutDate);
		calendar.add(Calendar.DATE, daysToAdd);
		Date newCheckOutDate = calendar.getTime();

		
		Collection<IRoom> recommendedRooms = HotelResourse.findARoom(newCheckInDate, newCheckOutDate);

		if (!recommendedRooms.isEmpty()) {
			
			System.out.println("Recommended rooms available for new dates:");
			System.out.println("Check-In: " + formatDate(newCheckInDate) + ", Check-Out: " + formatDate(newCheckOutDate));
			displayAvailableRooms(recommendedRooms);

			System.out.print("Would you like to book one of the recommended rooms? (y/n): ");
			char userResponse = sc.next().trim().toLowerCase().charAt(0); 

			if (userResponse == 'y') {
				System.out.println("Please select a room from the recommended list by entering the room number:");
				String roomNumber = sc.next();

				IRoom selectedRoom = HotelResourse.getRoom(roomNumber);

				if (selectedRoom != null && recommendedRooms.contains(selectedRoom)) {
					System.out.println("Enter your email to complete the booking:");
					String customerEmail = sc.next();

					HotelResourse.bookARoom(customerEmail, selectedRoom, newCheckInDate, newCheckOutDate);
					System.out.println("Room successfully booked for alternative dates!");
				} else {
					System.out.println("Invalid room selection. Please try again.");
				}
			} else if (userResponse == 'n') {
				System.out.println("Exiting operation. No rooms booked.");
			} 
			else {
				System.out.println("Invalid choice. Please enter 'y' for YES or 'n' for NO.");
			}
		} 
		else {
			System.out.println("No rooms available for the alternative dates.");
		}
	}

	private static String formatDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date);
	}

	private static void displayAvailableRooms(Collection<IRoom> availableRooms) {
		if (availableRooms.isEmpty()) {
			System.out.println("No rooms available.");
		} else {
			System.out.println("Available rooms:");
			for (IRoom room : availableRooms) {
				System.out.println(room);
			}
		}
	}
}
