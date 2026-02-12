/**********************************************************************
 * Description:
 * This file contains the main JavaFX application for the
 * Departments, Employees, and Projects Management System.
 *
 * The DEP class extends JavaFX Application and provides a
 * graphical user interface (GUI) for managing:
 * - Departments
 * - Employees (Admin and Developer)
 * - Projects
 * - Works-On relationships
 *
 * Core Features:
 * - Load data from text files (departments, employees, projects, works-on)
 * - Display records using ListView components
 * - Visualise department budgets using a PieChart
 * - Add and delete Works-On records with validation and confirmation
 * - Save updated Works-On data back to file
 *
 * Key Concepts Demonstrated:
 * - JavaFX GUI development (Scene, Stage, Layouts, Controls)
 * - Event handling (ActionEvent, MouseEvent)
 * - Object-oriented programming (inheritance & polymorphism)
 * - File handling (Scanner, BufferedReader, PrintWriter)
 * - Collections framework (ArrayList)
 *
 * This class acts as the central controller of the application,
 * integrating the data model with the graphical user interface.
 **********************************************************************/

// JavaFX core components
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

// Java standard library for collections and I/O
import java.util.*;
import java.io.*;

// JavaFX layout and positioning
import javafx.geometry.Insets;
import javafx.geometry.Side;

// Main application class extending JavaFX Application
public class DEP extends Application implements EventHandler<ActionEvent> {

    // Lists to hold department, employee, project, and works-on data
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<WorksOn> workson = new ArrayList<>();

    // ListView components for displaying record lists
    private ListView<String> deptListView = new ListView<>();
    private ListView<String> empListView = new ListView<>();
    private ListView<String> projListView = new ListView<>();
    private ListView<String> workListView = new ListView<>();

    // Text field for displaying status messages
    private TextField statusField = new TextField();

    // Pie chart to visualise department budgets
    private PieChart budgetChart = new PieChart();

    // Buttons for add, delete, and save operations
    private Button addBtn = new Button("Add");
    private Button delBtn = new Button("Delete");
    private Button saveBtn = new Button("Save");

    // TextFields for department input fields
    private TextField deptNumberField = new TextField();
    private TextField deptNameField = new TextField();
    private TextField managerField = new TextField();
    private TextField budgetField = new TextField();
    private TextField startDateField = new TextField();

    // TextFields for employee input fields
    private TextField empNameField = new TextField();
    private TextField dobField = new TextField();
    private TextField addressField = new TextField();
    private TextField genderField = new TextField();
    private TextField salaryField = new TextField();
    private TextField supervisorField = new TextField();
    private TextField empDeptField = new TextField();
    private TextField skillLangField = new TextField(); // skill (Admin) or language (Developer)

    // TextFields for project input fields
    private TextField projTitleField = new TextField();
    private TextField sponsorField = new TextField();
    private TextField projDeptField = new TextField();
    private TextField projBudgetField = new TextField();

    // TextFields for works-on input
    private TextField empNumInput = new TextField();
    private TextField projNumInput = new TextField();
    private TextField hoursInput = new TextField();

    // Entry point for launching the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    // The start method builds the UI and loads data from files
    @Override
    public void start(Stage stage) {
        // Load existing data from files
        loadDepartments();
        loadEmployees();
        loadProjects();
        loadWorksOn();

        // Populate department list view and pie chart with budget data
        for (Department d : departments) {
            deptListView.getItems().add(String.valueOf(d.getNumber()));
            budgetChart.getData().add(new PieChart.Data(d.getNumber() + " " + d.getName(), d.getBudget()));
        }
        budgetChart.setLegendSide(Side.BOTTOM);
        budgetChart.setLegendVisible(true);

        // Populate employee list
        for (Employee e : employees) {
            empListView.getItems().add(String.valueOf(e.getNumber()));
        }

        // Populate project list
        for (Project p : projects) {
            projListView.getItems().add(String.valueOf(p.getNumber()));
        }

        // Populate works-on list
        for (WorksOn w : workson) {
            workListView.getItems().add(w.toString());
        }

        // When a department is selected, display its details in the text fields
        deptListView.setOnMouseClicked(e -> {
            String selected = deptListView.getSelectionModel().getSelectedItem();
            for (Department d : departments) {
                if (String.valueOf(d.getNumber()).equals(selected)) {
                    deptNumberField.setText(String.valueOf(d.getNumber()));
                    deptNameField.setText(d.getName());
                    managerField.setText(String.valueOf(d.getManager()));
                    budgetField.setText(String.valueOf(d.getBudget()));
                    startDateField.setText(d.getStartDate());
                    break;
                }
            }
        });

        // When an employee is selected, display their details in the text fields
        empListView.setOnMouseClicked(e -> {
            String selected = empListView.getSelectionModel().getSelectedItem();
            for (Employee emp : employees) {
                if (String.valueOf(emp.getNumber()).equals(selected)) {
                    empNameField.setText(emp.getName());
                    dobField.setText(emp.getDob());
                    addressField.setText(emp.getAddress());
                    genderField.setText(emp.getGender());
                    salaryField.setText(String.valueOf(emp.getSalary()));
                    supervisorField.setText(String.valueOf(emp.getSupervisor()));
                    empDeptField.setText(String.valueOf(emp.getDNumber()));

                    // Display either languages (Developer) or skills (Admin)
                    skillLangField.setText(emp instanceof Developer
                            ? ((Developer) emp).getLanguages()
                            : ((Admin) emp).getSkills());
                    break;
                }
            }
        });

        // When a project is selected, display its details
        projListView.setOnMouseClicked(e -> {
            String selected = projListView.getSelectionModel().getSelectedItem();
            for (Project p : projects) {
                if (String.valueOf(p.getNumber()).equals(selected)) {
                    projTitleField.setText(p.getTitle());
                    sponsorField.setText(p.getSponsor());
                    projDeptField.setText(String.valueOf(p.getDNumber()));
                    projBudgetField.setText(String.valueOf(p.getBudget()));
                    break;
                }
            }
        });

        // Register the same handler (this) for Add, Delete, Save buttons
        addBtn.setOnAction(this);
        delBtn.setOnAction(this);
        saveBtn.setOnAction(this);

        // === DEPARTMENT PANEL SETUP ===
        GridPane deptPane = new GridPane();
        deptPane.setVgap(5); // vertical spacing between rows
        deptPane.setHgap(5); // horizontal spacing between columns
        deptPane.add(new Label("Department information"), 0, 0, 2, 1);

        // Department list in a scrollable pane
        ScrollPane deptScroll = new ScrollPane(deptListView);
        deptScroll.setFitToWidth(false);
        deptScroll.setFitToHeight(false);
        deptScroll.setPrefSize(150, 200);
        deptScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        deptScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        deptPane.add(new Label("Department Number"), 0, 1);
        deptPane.add(deptScroll, 1, 1);
        deptPane.add(new Label("Name"), 0, 2);
        deptPane.add(deptNameField, 1, 2);
        deptPane.add(new Label("Manager"), 0, 3);
        deptPane.add(managerField, 1, 3);
        deptPane.add(new Label("Budget"), 0, 4);
        deptPane.add(budgetField, 1, 4);
        deptPane.add(new Label("Start date"), 0, 5);
        deptPane.add(startDateField, 1, 5);

        // === EMPLOYEE PANEL SETUP ===
        GridPane empPane = new GridPane();
        empPane.setVgap(5);
        empPane.setHgap(5);
        empPane.add(new Label("Employee information"), 0, 0, 2, 1);

        ScrollPane empScroll = new ScrollPane(empListView);
        empScroll.setFitToWidth(false);
        empScroll.setFitToHeight(false);
        empScroll.setPrefSize(150, 200);
        empScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        empScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        empPane.add(new Label("Employee Number"), 0, 1);
        empPane.add(empScroll, 1, 1);
        empPane.add(new Label("Name"), 0, 2);
        empPane.add(empNameField, 1, 2);
        empPane.add(new Label("DOB"), 0, 3);
        empPane.add(dobField, 1, 3);
        empPane.add(new Label("Address"), 0, 4);
        empPane.add(addressField, 1, 4);
        empPane.add(new Label("Gender"), 0, 5);
        empPane.add(genderField, 1, 5);
        empPane.add(new Label("Salary"), 0, 6);
        empPane.add(salaryField, 1, 6);
        empPane.add(new Label("Supervisor"), 0, 7);
        empPane.add(supervisorField, 1, 7);
        empPane.add(new Label("Department"), 0, 8);
        empPane.add(empDeptField, 1, 8);
        empPane.add(new Label("Skill/Language"), 0, 9);
        empPane.add(skillLangField, 1, 9);

// === PROJECT PANEL SETUP ===
        GridPane projPane = new GridPane();
        projPane.setVgap(5);
        projPane.setHgap(5);
        projPane.add(new Label("Project information"), 0, 0, 2, 1);

        ScrollPane projScroll = new ScrollPane(projListView);
        projScroll.setFitToWidth(false);  // Enable horizontal scroll
        projScroll.setFitToHeight(false); // Enable vertical scroll
        projScroll.setPrefSize(150, 200);
        projScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        projScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        projPane.add(new Label("Project Number"), 0, 1);
        projPane.add(projScroll, 1, 1);
        projPane.add(new Label("Title"), 0, 2);
        projPane.add(projTitleField, 1, 2);
        projPane.add(new Label("Sponsor"), 0, 3);
        projPane.add(sponsorField, 1, 3);
        projPane.add(new Label("Department"), 0, 4);
        projPane.add(projDeptField, 1, 4);
        projPane.add(new Label("Budget"), 0, 5);
        projPane.add(projBudgetField, 1, 5);

        // === WORKS-ON PANEL SETUP ===
        GridPane workPane = new GridPane();
        workPane.setVgap(5);
        workPane.setHgap(5);
        workPane.add(new Label("Works on information"), 0, 0, 2, 1);

        // ScrollPane for works-on list with vertical scroll
        ScrollPane workScroll = new ScrollPane(workListView);
        workScroll.setFitToWidth(false);
        workScroll.setFitToHeight(true);
        workScroll.setPrefSize(200, 200);
        workScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);   // No horizontal scroll
        workScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);  // Always show vertical scroll

        // When a works-on item is selected, populate fields with employee, project, and hours
        workListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String selected = workListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    String[] parts = selected.split(",\\s*"); // split by ", "
                    if (parts.length == 3) {
                        empNumInput.setText(parts[0]);
                        projNumInput.setText(parts[1]);
                        hoursInput.setText(parts[2]);
                    }
                }
            }
        });

        workPane.add(new Label("Works on"), 0, 1);
        workPane.add(workScroll, 1, 1);
        workPane.add(new Label("Employee Number"), 0, 2);
        workPane.add(empNumInput, 1, 2);
        workPane.add(new Label("Project Number"), 0, 3);
        workPane.add(projNumInput, 1, 3);
        workPane.add(new Label("Hours"), 0, 4);
        workPane.add(hoursInput, 1, 4);

        // HBox to hold Add, Delete, and Save buttons
        workPane.add(new HBox(5, addBtn, delBtn, saveBtn), 1, 5);

        // Status message area for displaying user feedback
        workPane.add(new Label("Message"), 0, 6);
        workPane.add(statusField, 1, 6);


// Wrap each panel in a VBox to ensure vertical growth/fill
        VBox deptBox = new VBox(deptPane);
        VBox empBox = new VBox(empPane);
        VBox projBox = new VBox(projPane);
        VBox workBox = new VBox(workPane);

        // Allow each VBox to grow with available vertical space
        VBox.setVgrow(deptPane, Priority.ALWAYS);
        VBox.setVgrow(empPane, Priority.ALWAYS);
        VBox.setVgrow(projPane, Priority.ALWAYS);
        VBox.setVgrow(workPane, Priority.ALWAYS);

        // Combine the four VBox panels into a horizontal layout
        HBox topPane = new HBox(20, deptBox, empBox, projBox, workBox);
        topPane.setPadding(new Insets(10)); // padding around entire HBox

        // Allow each VBox inside HBox to grow with available horizontal space
        HBox.setHgrow(deptBox, Priority.ALWAYS);
        HBox.setHgrow(empBox, Priority.ALWAYS);
        HBox.setHgrow(projBox, Priority.ALWAYS);
        HBox.setHgrow(workBox, Priority.ALWAYS);

        // Final layout combines the top pane with the pie chart underneath
        VBox mainLayout = new VBox(15, topPane, budgetChart);
        mainLayout.setPadding(new Insets(10));
        VBox.setVgrow(topPane, Priority.ALWAYS);      // top layout grows
        VBox.setVgrow(budgetChart, Priority.NEVER);   // chart stays fixed

        // Set up the scene and stage
        Scene scene = new Scene(mainLayout, 1200, 700); // set width and height
        stage.setScene(scene);                         // attach scene to stage
        stage.setTitle("Departments, employees and projects management system");
        stage.show();                                  // show the UI window
    }

    private GridPane createGrid(String title, ListView<String> listView, String[] labels, TextField[] fields) {
        GridPane pane = new GridPane();
        pane.setVgap(5);   // vertical spacing between rows
        pane.setHgap(10);  // horizontal spacing between columns

        // Add a section header that spans 2 columns
        pane.add(new Label(title + " information"), 0, 0, 2, 1);

        // Add the label and ListView in the first row
        pane.add(new Label(labels[0]), 0, 1);
        pane.add(listView, 1, 1);

        // Add all other label-field pairs starting from row 2
        for (int i = 1; i < labels.length; i++) {
            pane.add(new Label(labels[i]), 0, i + 1);
            pane.add(fields[i], 1, i + 1);
        }

        return pane; // Return the fully constructed panel
    }

    // Handles Add, Delete, and Save button actions
    @Override
    public void handle(ActionEvent e) {
        Object src = e.getSource();
        if (src == addBtn) addWorksOn();       // Add a new works-on entry
        else if (src == delBtn) deleteWorksOn(); // Delete selected works-on entry
        else if (src == saveBtn) saveWorksOn();  // Save all works-on entries to file
    }

    // Loads department records from departments.txt
    public void loadDepartments() {
        try (Scanner sc = new Scanner(new File("departments.txt"))) {
            sc.useDelimiter(",\\s*|\r\n|\n");  // Handle CSV with flexible line endings
            while (sc.hasNext()) {
                int number = sc.nextInt();
                String name = sc.next();
                int manager = sc.nextInt();
                double budget = sc.nextDouble();
                String startDate = sc.next();
                departments.add(new Department(number, name, manager, budget, startDate));
            }
        } catch (Exception e) {
            System.err.println("Error loading departments: " + e.getMessage());
            statusField.setText("Error loading departments: " + e.getMessage());
        }
    }

    // Loads employee records from employees.txt
    public void loadEmployees() {
        try (BufferedReader reader = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*"); // Split on ", "
                String type = parts[0];               // "A" for Admin, "D" for Developer
                int number = Integer.parseInt(parts[1]);
                String name = parts[2];
                String dob = parts[3];
                String address = parts[4];
                String gender = parts[5];
                double salary = Double.parseDouble(parts[6]);
                int supervisor = Integer.parseInt(parts[7]);
                int dNumber = Integer.parseInt(parts[8]);
                String skillLang = parts[9];

                // Instantiate correct subclass
                if (type.equals("A")) {
                    employees.add(new Admin(number, name, dob, address, gender, salary, supervisor, dNumber, skillLang));
                } else if (type.equals("D")) {
                    employees.add(new Developer(number, name, dob, address, gender, salary, supervisor, dNumber, skillLang));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading employees: " + e.getMessage());
            statusField.setText("Error loading employees: " + e.getMessage());
        }
    }

    // Loads project records from projects.txt
    public void loadProjects() {
        try (Scanner sc = new Scanner(new File("projects.txt"))) {
            sc.useDelimiter(",\\s*|\r\n|\n");
            while (sc.hasNext()) {
                int number = sc.nextInt();
                String title = sc.next();
                String sponsor = sc.next();
                int dNumber = sc.nextInt();
                double budget = sc.nextDouble();
                projects.add(new Project(number, title, sponsor, dNumber, budget));
            }
        } catch (Exception e) {
            System.err.println("Error loading projects: " + e.getMessage());
            statusField.setText("Error loading projects: " + e.getMessage());
        }
    }

    // Loads works-on records from workson.txt
    public void loadWorksOn() {
        try (Scanner sc = new Scanner(new File("workson.txt"))) {
            sc.useDelimiter(",\\s*|\r\n|\n");
            while (sc.hasNext()) {
                int eNumber = sc.nextInt();
                int pNumber = sc.nextInt();
                int hours = sc.nextInt();
                workson.add(new WorksOn(eNumber, pNumber, hours));
            }
        } catch (Exception e) {
            System.err.println("Error loading works-on: " + e.getMessage());
            statusField.setText("Error loading works-on: " + e.getMessage());
        }
    }


    // Adds a new WorksOn record to the list (after confirmation and validation)
    public void addWorksOn() {
        try {
            String empStr = empNumInput.getText().trim();
            String projStr = projNumInput.getText().trim();

            // Ensure both employee and project numbers are provided
            if (empStr.isEmpty() || projStr.isEmpty()) {
                statusField.setText("Please enter both employee and project number");
                return;
            }

            int empId = Integer.parseInt(empStr);
            int projId = Integer.parseInt(projStr);

            // Check that selected employee exists and is a Developer
            Employee selectedEmp = null;
            for (Employee e : employees) {
                if (e.getNumber() == empId) {
                    selectedEmp = e;
                    break;
                }
            }

            if (!(selectedEmp instanceof Developer)) {
                statusField.setText("Please select a developer");
                return;
            }

            // Prevent duplicates: same employee already working on same project
            for (WorksOn w : workson) {
                if (w.getENumber() == empId && w.getPNumber() == projId) {
                    statusField.setText("Employee " + empId + " has already worked on Project " + projId);
                    return;
                }
            }

            // Prompt user to enter number of hours
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Confirmation");
            dialog.setHeaderText("Enter hours");
            Optional<String> input = dialog.showAndWait();

            if (input.isPresent()) {
                int hours = Integer.parseInt(input.get());

                // Confirm action using confirmation dialog
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmation");
                confirm.setHeaderText("Confirmation");
                confirm.setContentText("Are you sure?");
                Optional<ButtonType> result = confirm.showAndWait();

                // If user confirms, create and add the record
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    WorksOn newRecord = new WorksOn(empId, projId, hours);
                    workson.add(newRecord);
                    workListView.getItems().add(newRecord.toString());
                    statusField.setText("Employee " + empId + " works on " + projId + " is added");
                } else {
                    statusField.setText("Addition cancelled.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding works-on: " + e.getMessage());
            statusField.setText("Error adding works-on: " + e.getMessage());
        }
    }

    // Deletes the selected WorksOn entry from the list
    public void deleteWorksOn() {
        String selected = workListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusField.setText("Please select a works-on record to delete");
            return;
        }

        // Ask for confirmation before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm the removal");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String[] parts = selected.split(", ");
            int empId = Integer.parseInt(parts[0]);
            int projId = Integer.parseInt(parts[1]);

            // Remove from data list and visual list
            workson.removeIf(w -> w.getENumber() == empId && w.getPNumber() == projId);
            workListView.getItems().remove(selected);
            statusField.setText("The selected works-on record has been deleted");
        }
    }

    // Saves all WorksOn records to the file 'workson.txt'
    public void saveWorksOn() {
        try (PrintWriter out = new PrintWriter("workson.txt")) {
            for (WorksOn w : workson) {
                out.println(w.getENumber() + ", " + w.getPNumber() + ", " + w.getHours());
            }
            statusField.setText(workson.size() + " works-on records have been saved");
        } catch (Exception e) {
            System.err.println("Error saving works-on: " + e.getMessage());
            statusField.setText("Error saving works-on: " + e.getMessage());
        }
    }
}