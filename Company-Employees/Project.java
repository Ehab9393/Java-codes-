/**********************************************************************
 * Description:
 * This file defines the Project class for the Departments, Employees,
 * and Projects Management System (Assignment 2).
 *
 * The Project class represents a single project with the following
 * attributes:
 * - Project Number (ID)
 * - Title
 * - Sponsor
 * - Associated Department Number
 * - Budget
 *
 * Core Features:
 * - Stores project details in private fields
 * - Provides getter methods for all fields
 * - Implements the DataIO interface for:
 *     - Reading project data from a Scanner
 *     - Writing project data to a Formatter
 * - Overrides toString() for CSV-like representation
 *
 * Key Concepts Demonstrated:
 * - Object-oriented programming (encapsulation)
 * - Interface implementation (DataIO)
 * - File input/output integration via Scanner and Formatter
 *
 * This class serves as the data model for project records and is used
 * throughout the JavaFX GUI application to manage project information.
 **********************************************************************/

import java.util.*;               // Imports utility classes including Scanner
import java.util.Formatter;       // Imports Formatter class for formatted output

// Project class implementing the DataIO interface
public class Project implements DataIO {
    // Private fields to store project details
    private int number;        // Project number
    private String title;      // Project title
    private String sponsor;    // Sponsor name
    private int dNumber;       // Associated department number
    private double budget;     // Project budget

    // Constructor to initialise all fields
    public Project(int number, String title, String sponsor, int dNumber, double budget) {
        this.number = number;
        this.title = title;
        this.sponsor = sponsor;
        this.dNumber = dNumber;
        this.budget = budget;
    }

    // Getter for project number
    public int getNumber() {
        return number;
    }

    // Getter for project title
    public String getTitle() {
        return title;
    }

    // Getter for sponsor
    public String getSponsor() {
        return sponsor;
    }

    // Getter for department number
    public int getDNumber() {
        return dNumber;
    }

    // Getter for project budget
    public double getBudget() {
        return budget;
    }

    // Read project data from Scanner
    @Override
    public void dataInput(Scanner sc) {
        number = sc.nextInt();
        title = sc.next();
        sponsor = sc.next();
        dNumber = sc.nextInt();
        budget = sc.nextDouble();
    }

    // Output project data using Formatter
    @Override
    public void dataOutput(Formatter fout) {
        fout.format("%d, %s, %s, %d, %.1f\n", number, title, sponsor, dNumber, budget);
    }

    // Display project info as a string (optional but useful for debugging)
    @Override
    public String toString() {
        return number + ", " + title + ", " + sponsor + ", " + dNumber + ", " + budget;
    }
}
