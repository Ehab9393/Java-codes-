/**********************************************************************
 * Description:
 * This file contains the main driver class of the Purchase Management
 * System. It is responsible for managing customers, sellers, and
 * purchase records within the application.
 *
 * Main Responsibilities:
 * - Load customer, seller, and purchase data from text files
 * - Store records in ArrayLists during program execution
 * - Display a menu-driven user interface
 * - Search for customers, sellers, and purchases
 * - Add new records dynamically
 * - Save updated data back to text files
 *
 * Key Concepts Used:
 * - Object-Oriented Programming (inheritance & polymorphism)
 * - Interface implementation (MyFileIO)
 * - File handling using Scanner, Formatter, and java.nio.file
 * - Exception handling (InputMismatchException, IOException)
 * - Collections framework (ArrayList)
 *
 * This class acts as the central controller of the system,
 * coordinating all interactions between data models and the user.
 **********************************************************************/

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Manages customers, sellers, and purchases: loads data from text files,
 * displays menu options, and handles all user interactions.
 */
public class PurchaseManagement {

    // Lists to hold data in memory
    private List<Customer> customers = new ArrayList<>();
    private List<Seller> sellers = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();

    // Default constructor (no setup required)
    public PurchaseManagement() {

    }

    // Entry point: create instance, load data, start menu
    public static void main(String[] args) {
        PurchaseManagement pm = new PurchaseManagement();
        pm.initData();      // load existing records
        pm.runMenu();       // start user interface
    }

    // Load all data types from their respective files
    private void initData() {
        loadCustomers();
        loadSeller();
        loadPurchases();
    }

    /**
     * Show menu repeatedly until user exits. Handles choices and catches
     * invalid input.
     */
    private void runMenu() {
        Scanner in = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Display all customers.");
            System.out.println("2. Display all sellers.");
            System.out.println("3. Display all purchases.");
            System.out.println("4. Find a customer based on their number.");
            System.out.println("5. Find a seller based on their email.");
            System.out.println("6. Find a purchase based on its number.");
            System.out.println("7. Add a new customer.");
            System.out.println("8. Add a new seller.");
            System.out.println("9. Add a new purchase.");
            System.out.println("10. Save all data into files.");
            System.out.println("0. Exit.");
            System.out.print("Input a choice (0-10): ");

            try {
                choice = in.nextInt();
                in.nextLine();          // consume newline
                switch (choice) {
                    case 1:
                        displayAllCustomers();
                        break;
                    case 2:
                        displayAllSellers();
                        break;
                    case 3:
                        displayAllPurchases();
                        break;
                    case 4:
                        findCustomerByNumber(in);
                        break;
                    case 5:
                        findSellerByEmail(in);
                        break;
                    case 6:
                        findPurchaseByNumber(in);
                        break;
                    case 7:
                        addNewCustomer(in);
                        break;
                    case 8:
                        addNewSeller(in);
                        break;
                    case 9:
                        addNewPurchase(in);
                        break;
                    case 10:
                        saveAll();
                        break;
                    case 0:
                        System.out.println("Exiting.....");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input.");
                in.nextLine();      // clear bad input
            }

        } while (choice != 0);
        in.close();

    }

    //Read customers.txt and populate the customers list.
    public void loadCustomers() {
        Path path = Paths.get("customers.txt");
        if (!Files.exists(path)) {
            return;
        }
        try (Scanner fin = new Scanner(path)) {
            fin.useDelimiter(", |\\r?\\n|\\t");
            while (fin.hasNext()) {
                String type = fin.next();    // "F" or "V"
                int num = fin.nextInt();
                String name = fin.next();
                String addr = fin.next();
                String phone = fin.next();
                if ("F".equals(type)) {
                    int pts = fin.nextInt();
                    customers.add(new Frequent(num, name, addr, phone, pts));
                } else {
                    String disc = fin.next();
                    customers.add(new VIP(num, name, addr, phone, disc));
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error loading customers: " + e.getMessage());
        }
    }

    //Read sellers.txt and populate the sellers list.
    public void loadSeller() {
        Path path = Paths.get("sellers.txt");
        if (!Files.exists(path)) {
            return;
        }
        try (Scanner fin = new Scanner(path)) {
            fin.useDelimiter(", |\\r?\\n|\\t");
            while (fin.hasNext()) {
                int num = fin.nextInt();
                String name = fin.next();
                String addr = fin.next();
                String email = fin.next();
                sellers.add(new Seller(num, name, addr, email));
            }
        } catch (IOException e) {
            System.err.println("I/O error loading sellers: " + e.getMessage());
        }
    }

    //Read purchases.txt and populate the purchases list.
    public void loadPurchases() {
        Path path = Paths.get("purchases.txt");
        if (!Files.exists(path)) {
            return;
        }
        try (Scanner fin = new Scanner(path)) {
            fin.useDelimiter(", |\\r?\\n|\\t");
            while (fin.hasNext()) {
                int pno = fin.nextInt();
                int cno = fin.nextInt();
                int sno = fin.nextInt();
                String dt = fin.next();
                purchases.add(new Purchase(pno, cno, sno, dt));
            }
        } catch (IOException e) {
            System.err.println("I/O error loading purchases: " + e.getMessage());
        }
    }

    // Display each customer on its own line
    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    // Display each seller on its own line
    public void displayAllSellers() {
        for (Seller s : sellers) {
            System.out.println(s);
        }
    }

    // Display each purchase on its own line
    public void displayAllPurchases() {
        for (Purchase p : purchases) {
            System.out.println(p);
        }
    }

    // Prompt for customer number and search in list
    private void findCustomerByNumber(Scanner in) {
        System.out.print("Enter customer number: ");
        int num = in.nextInt();
        in.nextLine();
        boolean found = false;
        for (Customer c : customers) {
            if (c.getNumber() == num) {
                System.out.println(c);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Customer not found.");
        }
    }

    // Prompt for seller email and search in list
    private void findSellerByEmail(Scanner in) {
        System.out.print("Enter seller email: ");
        String mail = in.next();

        boolean found = false;
        for (Seller s : sellers) {
            if (s.getEmail().equalsIgnoreCase(mail)) {
                System.out.println(s);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Seller not found.");
        }
    }

    // Prompt for purchase number and search in list
    private void findPurchaseByNumber(Scanner in) {
        System.out.print("Enter purchase number: ");
        int num = in.nextInt();

        boolean found = false;
        for (Purchase p : purchases) {
            if (p.getPNumber() == num) {
                System.out.println(p);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Purchase not found.");
        }
    }

    // Gather details and add a new customer to the list
    private void addNewCustomer(Scanner in) {
        System.out.print("Frequent (F) or VIP (V)? ");
        String type = in.nextLine();

        System.out.print("Enter customer number: ");
        int num = in.nextInt();
        in.nextLine();

        System.out.print("Enter name: ");
        String name = in.nextLine();

        System.out.print("Enter address: ");
        String addr = in.nextLine();

        System.out.print("Enter phone: ");
        String phone = in.nextLine();

        Customer c;
        if (type.equalsIgnoreCase("F")) {
            System.out.print("Enter points: ");
            int pts = in.nextInt();
            in.nextLine();
            c = new Frequent(num, name, addr, phone, pts);
        } else {
            System.out.print("Enter discount: ");
            String disc = in.nextLine();
            c = new VIP(num, name, addr, phone, disc);
        }
        customers.add(c);
        System.out.println("Added customer: " + c);
    }

    // Gather details and add a new seller to the list
    private void addNewSeller(Scanner in) {
        System.out.print("Enter seller number: ");
        int num = in.nextInt();
        in.nextLine();

        System.out.print("Enter name: ");
        String name = in.nextLine();

        System.out.print("Enter address: ");
        String addr = in.nextLine();

        System.out.print("Enter email: ");
        String email = in.nextLine();

        Seller s = new Seller(num, name, addr, email);
        sellers.add(s);
        System.out.println("Added seller:   " + s);
    }

    // Gather details and add a new purchase to the list
    private void addNewPurchase(Scanner in) {
        System.out.print("Enter purchase number: ");
        int pno = in.nextInt();
        in.nextLine();

        System.out.print("Enter customer number: ");
        int cno = in.nextInt();
        in.nextLine();

        System.out.print("Enter seller number: ");
        int sno = in.nextInt();
        in.nextLine();

        System.out.print("Enter date (e.g. 01/03/2022): ");
        String date = in.nextLine();

        Purchase p = new Purchase(pno, cno, sno, date);
        purchases.add(p);
        System.out.println("Added purchase: " + p);
    }

    //Save all data lists back to text files.
    public void saveAll() {
        try (Formatter fout = new Formatter("customers.txt")) {      // turn3search0
            for (Customer c : customers) {
                fout.format("%s%n", c);
            }
        } catch (IOException e) {
            System.err.println("Error writing customers.txt");
        }

        try (Formatter fout = new Formatter("sellers.txt")) {
            for (Seller s : sellers) {
                fout.format("%s%n", s);
            }
        } catch (IOException e) {
            System.err.println("Error writing sellers.txt");
        }

        try (Formatter fout = new Formatter("purchases.txt")) {
            for (Purchase p : purchases) {
                fout.format("%s%n", p);
            }
        } catch (IOException e) {
            System.err.println("Error writing purchases.txt");
        }

        System.out.println("Data saved.");
    }

}
