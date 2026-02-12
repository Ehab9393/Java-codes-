/**********************************************************************  
 * Description:  
 * This file defines the Department class for the Departments, Employees, 
 * and Projects Management System (Assignment 2).  
 *  
 * The Department class represents a single department with attributes:  
 * - Department Number (ID)  
 * - Name  
 * - Manager ID  
 * - Budget  
 * - Start Date  
 *  
 * Core Features:  
 * - Stores department details in private fields  
 * - Provides getter methods for all fields  
 * - Implements the DataIO interface for:  
 *     - Reading data from a Scanner  
 *     - Writing data to a Formatter  
 * - Overrides toString() for CSV-like representation  
 *  
 * Key Concepts Demonstrated:  
 * - Object-oriented programming (encapsulation, class design)  
 * - Interface implementation (DataIO)  
 * - File input/output integration via Scanner and Formatter  
 *  
 * This class serves as the data model for department records and is 
 * used throughout the JavaFX GUI application to manage department data.  
 **********************************************************************/

 import java.util.Formatter;
import java.util.Scanner;// Imports Formatter class for formatted output

// Department class implementing the DataIO interface
public class Department implements DataIO {
    // Private fields to store department details
    private int number;
    private String name;
    private int manager;
    private double budget;
    private String startDate;

    // Constructor to initialize all fields
    public Department(int number, String name, int manager, double budget, String startDate) {
        this.number = number;
        this.name = name;
        this.manager = manager;
        this.budget = budget;
        this.startDate = startDate;
    }

    // Getter method for department number
    public int getNumber() {
        return number;
    }

    // Getter method for department name
    public String getName() {
        return name;
    }

    // Getter method for manager ID
    public int getManager() {
        return manager;
    }

    // Getter method for department budget
    public double getBudget() {
        return budget;
    }

    // Getter method for department start date
    public String getStartDate() {
        return startDate;
    }

    // Method to input department data using Scanner
    @Override
    public void dataInput(Scanner sc) {
        number = sc.nextInt();      // Read department number
        name = sc.next();           // Read department name
        manager = sc.nextInt();     // Read manager ID
        budget = sc.nextDouble();   // Read budget
        startDate = sc.next();      // Read start date
    }

    // Method to output department data using Formatter
    @Override
    public void dataOutput(Formatter fout) {
        fout.format("%d, %s, %d, %.1f, %s\n", number, name, manager, budget, startDate);
        // Format and print department data
    }
    @Override
    public String toString() {
        return number + ", " + name + ", " + manager + ", " + budget + ", " + startDate;
    }
}
