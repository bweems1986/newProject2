package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.company.Warehouse.vans;

public class SystemAdministrator  {

    private static ArrayList<Employee> employees = new ArrayList<>();

    public SystemAdministrator() {
        this.employees = new ArrayList<>();
        File accountsDB = new File("accountsDB.txt");
        try {
            Scanner fileReader = new Scanner(accountsDB);

            while (fileReader.hasNextLine()) { //add files to ArrayList
                Employee currentEmployee = new Employee(fileReader.nextLine());
                employees.add(currentEmployee);

            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static Employee createAccount(int menuOption) {//allows system admin to add employees to the account DB

        Scanner userInput = new Scanner(System.in);

        String firstName = "";
        String lastName = "";
        String email = "";
        String userName = "";
        String passWord = "";
        String phoneNumber = "";
        String jobTitle = "";
        String vanName = "";

        try {
            System.out.print("Please enter a first name: ");
            firstName = userInput.next().toLowerCase();
            System.out.print("Please enter a last name: ");
            lastName = userInput.next().toLowerCase();
            email = firstName + lastName + "@BikePartsDirect.com";
            userName = firstName.charAt(0) + lastName;
            System.out.print("Please enter a password: ");
            passWord = userInput.next();
            System.out.print("Please enter a phone number: ");
            phoneNumber = userInput.next();
            if (menuOption == 1) {
                jobTitle = "Office Manager";
            } else if ( menuOption == 2) {
                jobTitle = "Warehouse Manager";
            } else if (menuOption == 3) {
                jobTitle = "Sales Associate";
                vanName = firstName + "sVan";
                return new Employee(firstName, lastName, email, userName, passWord, phoneNumber, jobTitle, vanName);
            }
            return new Employee(firstName, lastName, email, userName, passWord, phoneNumber, jobTitle);
        } catch (InputMismatchException e) {
            throw e;
        }
    }

    public void addUserAccount(Employee employee) { //take in employee and add it to accounts
        if (employees.size() == 0) {  //test to see if anything is in accounts arraylist
            employees.add(employee);//nothing in accounts add employee
        } else {
            boolean isDuplicate = false;
            for (int i = 0; employees.size() > i; i++) {
                if (employees.get(i).getFirstName() == employee.getFirstName()
                        && (employees.get(i).getLastName() == employee.getLastName())) {
                    isDuplicate = true;
                    employees.get(i).setLastName(employee.getLastName());
                    employees.get(i).setEmail(employee.getEmail());
                    employees.get(i).setUserName(employee.getUserName());
                    employees.get(i).setPassWord(employee.getPassWord());
                    employees.get(i).setPhoneNumber(employee.getPhoneNumber());
                    employees.get(i).setJobTitle(employee.getJobTitle());
                }
            }
            if (isDuplicate == false) {
                employees.add(employee);
            }
        }
        this.save();
    }

    public void save() {
        try (FileWriter fw = new FileWriter("accountsDB.txt")) {
            for (int i = 0; employees.size() > i; i++) {
                fw.write(employees.get(i).Serialize());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logIn() {
        //this method will prompt a user to enter their username and password, if there is a match then it will look
        //at the jobtitle and output the appropriate menus. if jobtitle = sales associate then display sales associate menu
        //should probably put this method in the main class
        new SystemAdministrator();
        Scanner login = new Scanner(System.in);
        System.out.print("Please enter username: ");
        String userName = login.next();
        System.out.print("Please enter password: ");
        String passWord = login.next();

        if ((userName.equals("admin") && passWord.equals("madni"))) {
            System.out.println("You are logged in as a System Administrator");
            String userIn = "";
            while (!userIn.equals("4")) {
                displayAdminMenu();
                userIn = login.next();
                if (userIn.equals("1") || userIn.equals("2") || userIn.equals("3")) {
                    int menuChoice = Integer.parseInt(userIn);
                    Employee tmpEmployee = createAccount(menuChoice);
                    addUserAccount(tmpEmployee);
                    if ((menuChoice == 3) && !(tmpEmployee.getVanName().equals("null"))) {
                        Van tempVan = new Van(tmpEmployee.getVanName());
                        vans.add(tempVan);
                    }
                } else if (userIn.equals("4")) {
                    System.out.print("Enter the Username of the user you want to delete: ");
                    String userNameToDelete = login.next();
                    deleteAUser(userNameToDelete);
                } else if (userIn.equals("5")) {
                    System.out.print("Enter the Username of the user who's password you want to reset: ");
                    String userToResetPW = login.next();
                    resetAPassword(userToResetPW);
                } else if (userIn.equals("6")) {
                    if (employees.size() == 1) {
                        System.out.print("There is only one account currently in the system! Cannot switch.\n");
                        userIn = "";
                    } else {
                        logIn();
                    }
                }
            }
        } else {
            for (int i = 0; employees.size() > i; i++) {
                if ((employees.get(i).getUserName().equals(userName) &&
                        (employees.get(i).getPassWord().equals(passWord)))) {
                    if (employees.get(i).getJobTitle().equals("Sales Associate")) {
                        String salesAssociateChoice = "";
                        while (salesAssociateChoice != "3") {
                            salesAssociateMenu();
                            salesAssociateChoice = login.next();
                            if (salesAssociateChoice.equals("1")) {
                                //print the parts for the sales associate's specific van
                            } else if (salesAssociateChoice.equals("2")) {
                                //generate a transfer order and execute a movement of parts from main warehouse to the
                                //sales associate's van
                            } else if (salesAssociateChoice.equals("3")) {
                                //sell parts off of the van and generate the invoice (do we print the invoice or do we
                                //create a file that contains it? I'm thinking we just print it.)
                            } else {
                                logIn();
                            }
                        }
                    } else if (employees.get(i).getJobTitle().equals("Office Manager")) {
                        OfficeManager om = new OfficeManager();
                        String officeManagerChoice = "";
                        while (officeManagerChoice != "4") {
                            officeManagerMenu();
                            officeManagerChoice = login.next();
                            if (officeManagerChoice.equals("1")) {
                                System.out.println("Do you want to: ");
                                System.out.print("1) Sort by part name\n" +
                                        "2) Sort by part number\n" +
                                        "Enter your choice: ");
                                String sortChoice = login.next();
                                if (sortChoice.equals("1")) {
                                    //om.examinePartName();
                                } else {
                                    //om.examinePartNumber();
                                }
                            } else if (officeManagerChoice.equals("2")) {
                                //generate a transfer order and execute to give new parts to main warehouse
                            } else if (officeManagerChoice.equals("3")) {
                                //calculate commission for a sales associate
                            } else {
                                logIn();
                            }
                        }
                    } else if (employees.get(i).getJobTitle().equals("Warehouse Manager")) {
                        WarehouseManager wm = new WarehouseManager();
                        String warehouseManagerChoice = "";
                        while (warehouseManagerChoice != "4") {
                            warehouseManagerMenu();
                            warehouseManagerChoice = login.next();
                            if (warehouseManagerChoice.equals("1")) {
                                System.out.println("Do you want to: ");
                                System.out.print("1) Sort by part name\n" +
                                        "2) Sort by part number\n" +
                                        "Enter your choice: "
                                );
                                String sortChoice = login.next();
                                if (sortChoice.equals("1")) {
                                    //wm.examinePartName();
                                } else {
                                    //wm.examinePartNumber();
                                }
                            } else if (warehouseManagerChoice.equals("2")) {
                                //load van w parts
                            } else if (warehouseManagerChoice.equals("3")) {
                                // create invoice
                            } else {
                                logIn();
                            }
                        }
                    }
                }
            }
        }
    }

    private static void displayAdminMenu() {
        System.out.print("Please enter a choice from the System Administrator menu:\n" +
                "1: Add an Office Manager\n" +
                "2: Add a Warehouse Manager\n" +
                "3: Create an Associate-Van Pair\n" +
                "4: Delete an account\n" +
                "5: Reset a password\n" +
                "6: Switch Accounts\n" +
                "Enter your choice: "
        );
    }

    private static void salesAssociateMenu() {
        System.out.print("Please enter a choice from the Sales Associate menu:\n" +
                "1: Print van inventory\n" +
                "2: Transfer parts to van\n" +
                "3: Generate an invoice\n" +
                "4: Switch Accounts\n" +
                "Enter your choice: "
        );
    }

    private static void officeManagerMenu() {
        System.out.print("Please enter a choice from the Office Manager menu:\n" +
                "1: Examine parts\n" +
                "2: Order new parts\n" + //this is the one that will query the warehouse DB and order parts to bring up
                //part numbers to the minimum denoted in the serialized number, ex. if a part is a 3, minimum 5, it will
                // order 2 parts to bring main warehouse to 5
                "3: Calculate sales commission\n" +
                "4: Switch Accounts\n" +
                "Enter your choice: "
        );
    }

    private static void warehouseManagerMenu() {
        System.out.print("Please enter a choice from the Warehouse Manager menu:\n" +
                "1: Examine parts\n" +
                "2: Load a van with parts\n" +
                "3: Generate sales invoice\n" +
                "4: Switch accounts\n"
        );
    }

    private void deleteAUser(String aUser) {
        boolean deleted = false;
        while (!deleted) {
            for (Employee employee : employees) {
                if (employee.getUserName().equals(aUser)) {
                    employees.remove(employee);
                    deleted = true;
                    break;
                }
            }
            if (deleted) {
                System.out.println("User deleted.");
            } else {
                System.out.println("User wasn't found.");
            }
        }
        this.save();
    }

    private void resetAPassword(String aUser) {
        Scanner userInput = new Scanner(System.in);
        boolean reset = false;
        while (!reset) {
            for (Employee employee : employees) {
                if (employee.getUserName().equals(aUser)) {
                    System.out.print("Enter the new password for " + aUser + ": ");
                    String tmpPsswd = userInput.next();
                    employee.setPassWord(tmpPsswd);
                    reset = true;
                    break;
                }
            }
            if (reset) {
                System.out.println("Password reset successfully.");
            } else {
                System.out.println("User wasn't found, cannot reset password.");
            }
        }
        this.save();
    }
}