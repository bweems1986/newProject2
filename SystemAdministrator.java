package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SystemAdministrator  {



    public static ArrayList<Employee> employees = new ArrayList<>();

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

    public static Employee createAccount() {//allows system admin to add employees to the account DB

        Scanner userInput = new Scanner(System.in);

        String firstName = "";
        String lastName = "";
        String email = "";
        String userName = "";
        String passWord = "";
        String phoneNumber = "";
        String jobTitle = "";

        try {
            System.out.print("Please enter a first name: ");
            firstName = userInput.next();
            System.out.print("Please enter a last name: ");
            lastName = userInput.next();
            System.out.print("Please enter a email address: ");
            email = userInput.next();
            System.out.print("Please enter a userName: ");
            userName = userInput.next();
            System.out.print("Please enter a password: ");
            passWord = userInput.next();
            System.out.print("Please enter a phone number: ");
            phoneNumber = userInput.next();
            System.out.print("Please enter a job title: ");
            jobTitle = userInput.next();
            return new Employee(firstName, lastName, email, userName, passWord, phoneNumber, jobTitle);//create account object

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
                if (employees.get(i).getFirstName() == employee.getFirstName()) {//this confirms duplicate
                    //duplicate check not working
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

    public void addSalesAssociateAndVan(){
        //could tie this into the add user account with if statement, if jobtitle = "sales associate" then perform
        //necessary steps
    }

    public void  logIn() {
        //this method will prompt a user to enter their username and password, if there is a match then it will look
        //at the jobtitle and output the appropriate menus. if jobtitle = sales associate then display sales associate menu
        //should probably put this method in the main class
        Scanner login = new Scanner(System.in);

        System.out.println("Please enter your username and password: ");
        String userName = login.next();
        String passWord = login.next();
        for (int i = 0; employees.size() > i; i++) {
            if((userName.equals("admin") && passWord.equals("madni"))){
                System.out.println("You are logged in as a System Administrator");
                //addUserAccount(SystemAdministrator.createAccount());
            }
            else if ((employees.get(i).getUserName().equals(userName) && (employees.get(i).getPassWord().equals(passWord)))) {
                System.out.println("You are logged in as a: " + employees.get(i).getJobTitle());
                if(employees.get(i).getJobTitle().equals("salesassociate")){
                    System.out.println("Please select an option from the Sales Associate menu: ");
                    //add menu options with corresponding methods
                }
                if(employees.get(i).getJobTitle().equals("officemanager")){
                    System.out.println("Please select an option from the Office Manager menu: ");
                    //menu options with corresponding methods
                }
                if(employees.get(i).getJobTitle().equals("warehousemanager")){
                    System.out.println("Please select a option from the Warehouse manager menu: ");
                    //menu options with corresponding methods
                }


            }
        }
    }
}
