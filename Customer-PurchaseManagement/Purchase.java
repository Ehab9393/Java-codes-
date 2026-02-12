/**********************************************************************
 * Description:
 * This file defines the Purchase class, which represents a purchase
 * transaction in the system.
 *
 * Each Purchase object stores:
 * - Purchase number (pnumber)
 * - Customer number (cnumber)
 * - Seller number (snumber)
 * - Purchase date
 *
 * The class implements the MyFileIO interface, allowing purchase
 * records to be read from a file using Scanner and written to a file
 * using Formatter.
 *
 * This class supports structured transaction tracking and integrates
 * with the customer and seller management components of the system.
 **********************************************************************/

import java.util.*;

// Represents a purchase record with purchase, customer, seller numbers and a date
class Purchase implements MyFileIO {

    // Purchase ID number
    private int pnumber;
    // Customer ID number
    private int cnumber;
    // Seller ID number
    private int snumber;
    // purchase date
    private String date;

    // Default constructor
    public Purchase() {
        this.pnumber = 0;
        this.cnumber = 0;
        this.snumber = 0;
        this.date = "";
    }

    // Parameterized constructor
    public Purchase(int pnumber, int cnumber, int snumber, String date) {
        this.pnumber = pnumber;
        this.cnumber = cnumber;
        this.snumber = snumber;
        this.date = date;
    }

    // Getter for purchase number
    public int getPNumber() {
        return pnumber;
    }

    // Read fields from a Scanner
    @Override
    public void inputData(Scanner input) {
        this.pnumber = input.nextInt();
        this.cnumber = input.nextInt();
        this.snumber = input.nextInt();
        this.date = input.next();
    }

    // Write fields to a Formatter
    @Override
    public void outputData(Formatter output) {
        output.format("%d, %d, %d, %s\n", pnumber, cnumber, snumber, date);
    }

    // Return a String representation of the purchase
    @Override
    public String toString() {
        return String.format("%d, %d, %d, %s", pnumber, cnumber, snumber, date);
    }
}
