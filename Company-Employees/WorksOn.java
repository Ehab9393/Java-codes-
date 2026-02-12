/**********************************************************************
 * Description:
 * This file defines the WorksOn class for the Departments, Employees,
 * and Projects Management System (Assignment 2).
 *
 * The WorksOn class represents a record of an employee working on a
 * specific project, storing the following details:
 * - Employee Number (ID)
 * - Project Number (ID)
 * - Number of Hours Worked
 *
 * Core Features:
 * - Stores works-on details in private fields
 * - Provides getter methods for all fields
 * - Implements the DataIO interface for:
 *     - Reading works-on data from a Scanner
 *     - Writing works-on data to a Formatter
 * - Overrides toString() for CSV-like representation, suitable for
 *   displaying in JavaFX ListView
 *
 * Key Concepts Demonstrated:
 * - Object-oriented programming (encapsulation)
 * - Interface implementation (DataIO)
 * - File input/output integration via Scanner and Formatter
 *
 * This class serves as the data model for employee-project assignments
 * and is used throughout the JavaFX GUI application to manage works-on
 * information.
 **********************************************************************/


import java.util.*;               // Imports utility classes including Scanner
import java.util.Formatter;       // Imports Formatter class for formatted output

// WorksOn class implementing the DataIO interface
public class WorksOn implements DataIO {
    // Private fields to store employee number, project number, and hours worked
    private int eNumber;  // Employee number
    private int pNumber;  // Project number
    private int hours;    // Hours worked on the project

    // Constructor to initialize all fields
    public WorksOn(int eNumber, int pNumber, int hours) {
        this.eNumber = eNumber;
        this.pNumber = pNumber;
        this.hours = hours;
    }

    // Getter method for employee number
    public int getENumber() {
        return eNumber;
    }

    // Getter method for project number
    public int getPNumber() {
        return pNumber;
    }

    // Getter method for hours worked
    public int getHours() {
        return hours;
    }

    @Override
    public void dataInput(Scanner sc) {
        eNumber = sc.nextInt();  // Read employee number
        pNumber = sc.nextInt();  // Read project number
        hours = sc.nextInt();    // Read number of hours worked
    }

    // Method to output data using Formatter
    @Override
    public void dataOutput(Formatter fout) {
        fout.format("%d, %d, %d\n", eNumber, pNumber, hours); // Write to file or output stream
    }

    // Returns a string representation of the object, useful for displaying in ListView
    @Override
    public String toString() {
        return eNumber + ", " + pNumber + ", " + hours;
    }
}
