/**********************************************************************
 * Description:
 * This file defines the Seller class, which represents a seller
 * entity in the Purchase Management System.
 *
 * Each Seller object stores:
 * - Seller number (snumber)
 * - Seller name
 * - Seller address
 * - Seller email
 *
 * The class implements the MyFileIO interface, allowing seller
 * information to be read from and written to text files using
 * Scanner and Formatter.
 *
 * This class is used by the PurchaseManagement system to:
 * - Store seller records in memory
 * - Search sellers by email
 * - Display seller information
 * - Save seller data back to file
 *
 * It follows object-oriented principles and integrates with
 * other system components such as Purchase and Customer.
 **********************************************************************/

import java.util.*;

// Represents a seller with ID, name, address, and email
public class Seller implements MyFileIO {

    private int snumber;        // seller ID
    private String sname;       // seller name
    private String saddress;    // seller address
    private String email;       // seller email

    // Default constructor: initialize fields to default values
    public Seller() {
        this.snumber = 0;
        this.sname = "";
        this.saddress = "";
        this.email = "";
    }

    // Constructor with provided values
    public Seller(int snumber, String sname, String saddress, String email) {
        this.snumber = snumber;
        this.sname = sname;
        this.saddress = saddress;
        this.email = email;
    }

    // Get the seller's email
    public String getEmail() {
        return email;
    }

    // Read seller data from a Scanner
    public void inputData(Scanner input) {
        this.snumber = input.nextInt();
        this.sname = input.next();
        this.saddress = input.next();
        this.email = input.next();
    }

    // Write seller data using a Formatter
    public void outputData(Formatter output) {
        output.format("%d, %s, %s, %s\n", snumber, sname, saddress, email);
    }

    // Return a string description of the seller
    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s", snumber, sname, saddress, email);
    }
}
