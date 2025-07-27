package org.example;

import org.example.entities.Ticket;
import org.example.entities.User;
import org.example.services.UserBookingService;
import org.example.utils.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.exit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("Running Ticket Booking System:");
        UserBookingService userBookingService;
        int option = 0;
        Scanner scanner = new Scanner(System.in);

        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Something went wrong");
            return;
        }

        while (option < 7) {
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Incorrect input, please try again.");
                break;
            }

            switch (option){
                case 1:
                    System.out.println("Enter username to signup: ");
                    String username = scanner.next();
                    System.out.println("Enter password to signup: ");
                    String password = scanner.next();
                    User userToSignUp = new User(username, password, UserServiceUtil.hashPassword(password), new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignUp);
                    break;
                case 2:
                    System.out.println("Enter username to login: ");
                    String usernameToLogin = scanner.next();
                    System.out.println("Enter password to login: ");
                    String passwordLogin = scanner.next();
                    User userToLogin = new User(usernameToLogin, passwordLogin, UserServiceUtil.hashPassword(passwordLogin), new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                    } catch (IOException e) {
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings....");
                    userBookingService.fetchBookings();
                    break;
                case 4:
                    System.out.println("Search Train");
                    break;
            }

        }

    }
}