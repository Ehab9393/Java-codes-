/**********************************************************************
 * Description:
 * This file defines the MyFileIO interface used for handling
 * file input and output operations within the system.
 *
 * The interface declares methods that require implementing classes to:
 * - Read object data using a Scanner
 * - Write object data using a Formatter
 * - Provide a string representation of the object
 *
 * This interface enables consistent file I/O behavior across
 * different classes (such as Customer, Frequent, and VIP),
 * supporting polymorphism and structured data processing.
 **********************************************************************/

import java.util.*;

// Define an interface for file input/output operations
public interface MyFileIO {

    // Read data from a Scanner
    public void inputData(Scanner input);

    // Write data using a Formatter
    public void outputData(Formatter output);

    // Provide a string representation of the object
    public String toString();
}
