/**********************************************************************
 * Description:
 * This file defines the abstract Employee class and its two concrete
 * subclasses, Admin and Developer, for the Departments, Employees, 
 * and Projects Management System (Assignment 2).
 *
 * The Employee class represents a single employee with the following 
 * attributes:
 * - Employee Number (ID)
 * - Name
 * - Date of Birth
 * - Address
 * - Gender
 * - Salary
 * - Supervisor ID
 * - Department Number
 *
 * Admin and Developer classes extend Employee and add role-specific
 * attributes:
 * - Admin: skills
 * - Developer: programming languages
 *
 * Core Features:
 * - Stores employee details in private fields
 * - Provides getter methods for all fields
 * - Implements the DataIO interface for:
 *     - Reading data from a Scanner
 *     - Writing data to a Formatter
 * - Overrides toString() for CSV-like representation
 *
 * Key Concepts Demonstrated:
 * - Object-oriented programming (inheritance, encapsulation, polymorphism)
 * - Interface implementation (DataIO)
 * - File input/output integration via Scanner and Formatter
 *
 * This class hierarchy serves as the data model for employee records 
 * and is used throughout the JavaFX GUI application to manage employee
 * data.
 **********************************************************************/

/*-----------------------------------
Student name: Ihab AL-NASHEEI
Student number: 8867197
Subject code: CSIT213
-----------------------------------*/

import java.util.Formatter; // Used for formatted output
import java.util.Scanner;   // Used for reading input

// Abstract base class Employee that implements the DataIO interface
public abstract class Employee implements DataIO {
    // Private fields to store employee details
    private int number;
    private String name;
    private String dob;
    private String address;
    private String gender;
    private double salary;
    private int supervisor;
    private int dNumber; // Department number

    // Constructor to initialise employee data
    public Employee(int number, String name, String dob, String address, String gender,
                    double salary, int supervisor, int dNumber) {
        this.number = number;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.salary = salary;
        this.supervisor = supervisor;
        this.dNumber = dNumber;
    }

    // Getters for accessing private fields
    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getDob() { return dob; }
    public String getAddress() { return address; }
    public String getGender() { return gender; }
    public double getSalary() { return salary; }
    public int getSupervisor() { return supervisor; }
    public int getDNumber() { return dNumber; }

    // Returns a string representation of the employee
    @Override
    public String toString() {
        return number + ", " + name + ", " + dob + ", " + address + ", " + gender + ", " +
                salary + ", " + supervisor + ", " + dNumber;
    }

    // Protected setters to allow subclasses to update fields when reading from Scanner
    protected void setNumber(int number) { this.number = number; }
    protected void setName(String name) { this.name = name; }
    protected void setDob(String dob) { this.dob = dob; }
    protected void setAddress(String address) { this.address = address; }
    protected void setGender(String gender) { this.gender = gender; }
    protected void setSalary(double salary) { this.salary = salary; }
    protected void setSupervisor(int supervisor) { this.supervisor = supervisor; }
    protected void setDNumber(int dNumber) { this.dNumber = dNumber; }
}

// Admin class inherits from Employee and adds a skills field
class Admin extends Employee {
    private String skills; // Admin-specific skills

    // Constructor for Admin class
    public Admin(int number, String name, String dob, String address, String gender,
                 double salary, int supervisor, int dNumber, String skills) {
        super(number, name, dob, address, gender, salary, supervisor, dNumber);
        this.skills = skills;
    }

    // Getter for skills
    public String getSkills() {
        return skills;
    }

    // Reads admin data from Scanner
    @Override
    public void dataInput(Scanner sc) {
        setNumber(sc.nextInt());
        setName(sc.next());
        setDob(sc.next());
        setAddress(sc.next());
        setGender(sc.next());
        setSalary(sc.nextDouble());
        setSupervisor(sc.nextInt());
        setDNumber(sc.nextInt());
        skills = sc.next();
    }

    // Writes admin data to Formatter
    @Override
    public void dataOutput(Formatter fout) {
        fout.format("A, %d, %s, %s, %s, %s, %.1f, %d, %d, %s\n",
                getNumber(), getName(), getDob(), getAddress(), getGender(),
                getSalary(), getSupervisor(), getDNumber(), skills);
    }

    // Returns string representation of Admin object
    @Override
    public String toString() {
        return super.toString() + ", " + skills;
    }
}

// Developer class inherits from Employee and adds a languages field
class Developer extends Employee {
    private String languages; // Programming languages known

    // Constructor for Developer class
    public Developer(int number, String name, String dob, String address, String gender,
                     double salary, int supervisor, int dNumber, String languages) {
        super(number, name, dob, address, gender, salary, supervisor, dNumber);
        this.languages = languages;
    }

    // Getter for languages
    public String getLanguages() {
        return languages;
    }

    // Reads developer data from Scanner
    @Override
    public void dataInput(Scanner sc) {
        setNumber(sc.nextInt());
        setName(sc.next());
        setDob(sc.next());
        setAddress(sc.next());
        setGender(sc.next());
        setSalary(sc.nextDouble());
        setSupervisor(sc.nextInt());
        setDNumber(sc.nextInt());
        languages = sc.next();
    }

    // Writes developer data to Formatter
    @Override
    public void dataOutput(Formatter fout) {
        fout.format("D, %d, %s, %s, %s, %s, %.1f, %d, %d, %s\n",
                getNumber(), getName(), getDob(), getAddress(), getGender(),
                getSalary(), getSupervisor(), getDNumber(), languages);
    }

    // Returns string representation of Developer object
    @Override
    public String toString() {
        return super.toString() + ", " + languages;
    }
}
