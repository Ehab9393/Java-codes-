/**********************************************************************
 * Description:
 * This file defines a customer class hierarchy for a customer 
 * management system.
 *
 * It includes:
 * - Customer: Base class representing a regular customer with ID,
 *   name, address, and phone number. Implements MyFileIO for
 *   reading and writing customer data.
 * - Frequent: Subclass of Customer that adds loyalty points
 *   functionality for frequent customers.
 * - VIP: Subclass of Customer that adds a discount code
 *   for VIP customers.
 *
 * The classes demonstrate:
 * - Inheritance
 * - Method overriding
 * - Polymorphism
 * - File input/output using Scanner and Formatter
 *
 * Designed to support customer data storage, retrieval,
 * and formatted output in a structured system.
 **********************************************************************/

import java.util.*;

// Represents a regular customer with ID, name, address, and phone
class Customer implements MyFileIO {

    private int cnumber;        // customer ID
    private String cname;       // customer name
    private String caddress;    // customer address
    private String phone;       // customer phone number

    // Default constructor: initialize fields to defaults
    public Customer() {
        this.cnumber = 0;
        this.cname = "";
        this.caddress = "";
        this.phone = "";
    }

    // Constructor with provided values
    public Customer(int cnumber, String cname, String caddress, String phone) {
        this.cnumber = cnumber;
        this.cname = cname;
        this.caddress = caddress;
        this.phone = phone;
    }

    // Get the customer ID
    public int getNumber() {
        return cnumber;
    }

    // Get the customer's name
    public String getName() {
        return cname;
    }

    // Get the customer's address
    public String getAddress() {
        return caddress;
    }

    // Get the customer's phone
    public String getPhone() {
        return phone;
    }

    // Read fields from a Scanner (implements MyFileIO)
    @Override
    public void inputData(Scanner input) {
        this.cnumber = input.nextInt();
        this.cname = input.next();
        this.caddress = input.next();
        this.phone = input.next();
    }

    // Write fields to a Formatter (implements MyFileIO)
    @Override
    public void outputData(Formatter output) {
        output.format("%d, %s, %s, %s", cnumber, cname, caddress, phone);
    }

    // Return a String representation of the customer (overrides Object.toString)
    @Override
    public String toString() {
        return String.format("Customer: %d, %s, %s, %s", cnumber, cname, caddress, phone);
    }
}

// Represents a frequent customer with loyalty points
class Frequent extends Customer {

    private int points;         // loyalty points

    // Default: no points
    public Frequent() {
        super();
        this.points = 0;
    }

    // Constructor with points
    public Frequent(int cnumber, String cname, String caddress, String phone, int points) {
        super(cnumber, cname, caddress, phone);
        this.points = points;
    }

    // Read frequent customer data including points (implements MyFileIO)
    @Override
    public void inputData(Scanner input) {
        super.inputData(input);
        this.points = input.nextInt();
    }

    // Write frequent customer data with prefix 'F' and points (implements MyFileIO)
    @Override
    public void outputData(Formatter output) {
        output.format("F, ");
        super.outputData(output);
        output.format(", %d\n", points);
    }

    // Return a string representation including 'F' prefix and points (overrides toString)
    @Override
    public String toString() {
        return String.format("F, %d, %s, %s, %s, %d",
                getNumber(), getName(), getAddress(), getPhone(), points);
    }
}

// Represents a VIP customer with a discount code
class VIP extends Customer {

    private String discount;     // discount code

    // Default: no discount
    public VIP() {
        super();
        discount = "";
    }

    // Constructor with provided discount code
    public VIP(int cnumber, String cname, String caddress, String phone, String discount) {
        super(cnumber, cname, caddress, phone);
        this.discount = discount;
    }

    // Read VIP customer data including discount (implements MyFileIO)
    @Override
    public void inputData(Scanner input) {
        super.inputData(input);
        this.discount = input.next();
    }

    // Write VIP customer data with prefix 'V' and discount (implements MyFileIO)
    @Override
    public void outputData(Formatter output) {
        output.format("V, ");
        super.outputData(output);
        output.format(", %s\n", discount);
    }

    // Return a string representation including 'V' prefix and discount (overrides toString)
    @Override
    public String toString() {
        return String.format("V, %d, %s, %s, %s, %s",
                getNumber(), getName(), getAddress(), getPhone(), discount);
    }
}
