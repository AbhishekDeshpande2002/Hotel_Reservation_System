package api;

import java.util.Collection;
import java.util.Scanner;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

public class AdminMainMenu {

    public static void displayAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            printAdminMenu();
            try {
                int selection = Integer.parseInt(scanner.nextLine().trim());
                switch (selection) {
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservations();
                        break;
                    case 4:
                        addARoom(scanner);
                        break;
                    case 5:
                        MainMenu.displayMainMenu();
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid number (1-5).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void printAdminMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please enter your Choice: ");
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = AdminResourse.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = AdminResourse.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }


    private static void seeAllReservations() {
        AdminResourse.displayAllReservations();
    }

    private static void addARoom(Scanner scanner) {
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        System.out.print("Enter room price: ");
        double roomPrice;
        try {
            roomPrice = Double.parseDouble(scanner.nextLine());
            if (roomPrice < 0) {
                System.out.println("Invalid! Room price cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid! Room price must be a valid number.");
            return;
        }

        System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
        int roomTypeInput;
        try {
            roomTypeInput = Integer.parseInt(scanner.nextLine());
            if (roomTypeInput != 1 && roomTypeInput != 2) {
                System.out.println("Invalid! Enter valid room type (1 for SINGLE, 2 for DOUBLE).");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Room type must be a number (1 for SINGLE, 2 for DOUBLE).");
            return;
        }

        RoomType roomType = (roomTypeInput == 1) ? RoomType.SINGLE : RoomType.DOUBLE;
        Room room = new Room(roomNumber, roomPrice, roomType);
        AdminResourse.addRoom(room);
        System.out.println("Room added successfully!");
    }
}
