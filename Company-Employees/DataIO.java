/**********************************************************************
 * Description:
 * This file defines the DataIO interface for Assignment 2.
 *
 * The interface specifies standard methods for handling
 * file input and output operations within the system.
 *
 * Implementing classes are required to:
 * - Read object data using a Scanner (dataInput)
 * - Write object data using a Formatter (dataOutput)
 * - Provide a string representation of the object (toString)
 *
 * This interface promotes consistency, modular design,
 * and polymorphism by ensuring all data-related classes
 * follow the same input/output structure.
 **********************************************************************/

import java.util.Scanner;   // Imports the Scanner class for input
import java.util.Formatter; // Imports the Formatter class for formatted output

// Interface definition named DataIO
public interface DataIO {

    // Abstract method for inputting data using a Scanner
    void dataInput(Scanner sc);

    // Abstract method for outputting data using a Formatter
    void dataOutput(Formatter fout);

    // Method to return a string representation
    @Override
    String toString();
}