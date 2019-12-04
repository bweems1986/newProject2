package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SystemAdministrator {
    private static String vanLetter;
    private Scanner login = new Scanner(System.in);
    private int numChoice;


    private static Scanner userInput = new Scanner(System.in);
    private OfficeManager officeManager = new OfficeManager();
    private Warehouse warehouse = new Warehouse();
    SalesAssociate salesAssociate = new SalesAssociate();
    private WarehouseManager warehouseManager = new WarehouseManager();
    BikePart bikePart = new BikePart();





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

    private static Employee createAccount() {//allows system admin to add employees to the account DB

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
            if(jobTitle.equals("sales")){
                System.out.println("Enter the sales van letter for the sales associate: ");
                vanLetter = userInput.next();
                jobTitle = jobTitle + vanLetter;

            }

            return new Employee(firstName, lastName, email, userName, passWord, phoneNumber, jobTitle);//create account object

        } catch (InputMismatchException e) {
            throw e;
        }
    }

    private void addUserAccount(Employee employee) { //take in employee and add it to accounts
        if (employees.size() == 0) {  //test to see if anything is in accounts arraylist
            employees.add(employee);//nothing in accounts add employee
        } else {
            boolean isDuplicate = false;
            for (int i = 0; employees.size() > i; i++) {
                if (employees.get(i).getFirstName().equals(employee.getFirstName())) {//this confirms duplicate
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
        System.out.println("Please enter your username: ");
        String userName = login.next();
        System.out.println("Please enter your password: ");
        String passWord = login.next();
        if((userName.equals("admin") && passWord.equals("madni"))) {
            //System.out.println();
            while( numChoice != 3){
                System.out.println("You are logged in as a System Administrator\n" + "1. Create a user account\n" + "2. Create a sales van\n" +
                        "3. Delete a user account" + "4. Reset an account password" + "5. Exit\n" + "Please select an option: ");
            numChoice = login.nextInt();
                if (numChoice == 1) {
                    //can create sales van
                    addUserAccount(SystemAdministrator.createAccount());
                }
                if (numChoice == 2) {
                    createVanInventoryFile();
                    readTransfer();
                    officeManager.orderPartsAlert(bikePart.getMinimumQuantity());

                }
                if (numChoice == 3){
                    System.out.println("Enter an account username to delete: ");
                    String aUser = login.next();
                    deleteAUser(aUser);
                }
                if (numChoice == 4){
                    System.out.println("Enter an account username to reset password: ");
                    String aUser = login.next();
                    resetAPassword(aUser);
                }
                if (numChoice == 5) {
                    System.exit(0);
                }
            }
        }else {
            for (int i = 0; employees.size() > i; i++) {
                if ((employees.get(i).getUserName().equals(userName) && (employees.get(i).getPassWord().equals(passWord)))) {
                    System.out.println("You are logged in as a: " + employees.get(i).getJobTitle());
                    System.out.println("Enter the van letter assigned to you: ");
                    String vanLetter = login.next();
                    if (employees.get(i).getJobTitle().equals("salesassociate"+vanLetter)) {
                        while(numChoice != 6) {
                            System.out.println("Please select an option from the Sales Associate menu: \n" + "1. Load sales van\n" + "2. Make sale by number and generate invoice\n" +
                                    "3. Make sale by name and generate invoice\n" + "4. Sort van by part name\n" + "5. Sort van by part number\n" + "6. Exit\n");
                            numChoice = login.nextInt();
                            if (numChoice == 1) {
                                readTransfer();
                            }
                            if (numChoice == 2) {
                                System.out.println("Enter your van name: ");//example vanA
                                String vanName = userInput.next();
                                SalesAssociate salesAssociate = new SalesAssociate(vanName);
                                if(!vanName.equals("van"+vanLetter)){
                                    System.out.println("You are not allowed to access this sales van.");
                                    System.exit(0);
                                }

                                Scanner partQuantity = new Scanner(System.in);

                                System.out.println("Please enter the number of unique parts you are selling: ");
                                int uniqueParts = partQuantity.nextInt();
                                for (int j = 0; j < uniqueParts; j++) {
                                    System.out.print("Please enter a part number: ");

                                    int partNumber = userInput.nextInt();
                                    if (salesAssociate.findPart(partNumber)) {
                                        salesAssociate.sellPart(partNumber);
                                    } else {
                                        System.out.println("Part not found. Try again.");
                                    }
                                }
                                salesAssociate.createInvoice();
                                officeManager.orderPartsAlert(bikePart.getMinimumQuantity());


                            }
                            if (numChoice == 3) {
                                System.out.println("Enter your van name: ");
                                String vanName = userInput.next();
                                SalesAssociate salesAssociate = new SalesAssociate(vanName);
                                if(!vanName.equals("van"+vanLetter)){
                                    System.out.println("You are not allowed to access this sales van.");
                                    System.exit(0);
                                }

                                Scanner partQuantity = new Scanner(System.in);

                                System.out.println("Please enter the number of unique parts you are selling: ");
                                int uniqueParts = partQuantity.nextInt();
                                for (int j = 0; j < uniqueParts; j++) {
                                    System.out.print("Please enter a part name: ");

                                    String partName = userInput.next();
                                    if (salesAssociate.findPart(partName)) {
                                        salesAssociate.sellPart(partName);
                                    } else {
                                        System.out.println("Part not found. Try again.");
                                    }
                                }
                                salesAssociate.createInvoice();
                                officeManager.orderPartsAlert(bikePart.getMinimumQuantity());

                            }
                            if (numChoice == 4) {
                                System.out.print("Please enter a van name:");
                                String vanName = userInput.next();
                                Van vanToSort = new Van(vanName);
                                vanToSort.sortName();
                            }
                            if (numChoice == 5) {
                                System.out.print("Please enter a van name:");
                                String vanName = userInput.next();
                                Van vanToSort = new Van(vanName);
                                vanToSort.sortNumber();
                            }
                            if (numChoice == 6) {
                                System.exit(0);
                            }
                        }
                    }
                    if (employees.get(i).getJobTitle().equals("officemanager")) {
                        while (numChoice != 5) {
                            System.out.println();
                            System.out.println("Please select an option from the Office Manager menu:\n" + "1. Examine parts by name\n" + "2. Examine parts by name\n" + "3. Order new parts\n" + "4. Calculate sales commission\n" +
                                    "5. Exit\n" + "Enter your choice: ");
                            numChoice = login.nextInt();
                            if (numChoice == 1) {
                                System.out.print("Please enter a part name: ");
                                String partName = userInput.next();
                                officeManager.examinePartName(partName);
                            }
                            if (numChoice == 2) {
                                System.out.println("Please enter a part number: ");
                                int partNumber = userInput.nextInt();
                                officeManager.examinePartNumber(partNumber);
                            }
                            if (numChoice == 3) {
                                warehouse.userPart(officeManager.orderParts());
                            }
                            if (numChoice == 4) {
                                officeManager.salesCommissions();
                            }
                            if (numChoice == 5) {
                                System.exit(0);
                            }
                        }
                    }
                }
                if (employees.get(i).getJobTitle().equals("warehousemanager")) {
                    while (numChoice != 10) {
                    System.out.println("Please select a option from the Warehouse manager menu:\n" + "1. Load warehouse with delivery file\n" + "2. Load warehouse manually\n" + "3. Examine part by name\n" +
                            "4. Examine part by number\n" + "5. Display a part\n" + "6. Sort all warehouses by name\n" +
                            "7. Sort all warehouses by number\n" + "8. Sort main warehouse by name\n" +
                            "9. Sort main warehouse by number\n" + "10. Exit");
                    numChoice = login.nextInt();


                        if (numChoice == 1) {
                            warehouse.readFile();
                        }
                        if (numChoice == 2) {
                            warehouse.userPart(createNewBikePart());
                        }
                        if (numChoice == 3) {
                            System.out.println("Please enter a part name: ");
                            String partName = userInput.next();
                            warehouseManager.examinePartName(partName);
                        }
                        if (numChoice == 4) {
                            System.out.println("Please enter a part number: ");
                            int partNumber = userInput.nextInt();
                            warehouseManager.examinePartNumber(partNumber);
                        }
                        if (numChoice == 5) {
                            System.out.print("Please enter a part name: ");
                            String partName = userInput.next();
                            warehouse.findPart(partName);
                        }
                        if (numChoice == 6) {
                            System.out.print("Please enter each warehouse name separated by commas: (i.e. vanA,vanB,vanC)");
                            String vans = userInput.next();
                            String[] vansArr = vans.split(",");
                            for (i = 0; i < vansArr.length; i++) {
                                if (vansArr[i] != "warehouse") {
                                    Van vanToSort = new Van(vansArr[i]);
                                    vanToSort.sortName();
                                }
                            }
                        }
                        if (numChoice == 7) {
                            System.out.print("Please enter each warehouse name separated by commas: (i.e. vanA,vanB,vanC)");
                            String vans = userInput.next();
                            String[] vansArr = vans.split(",");
                            for (i = 0; i < vansArr.length; i++) {
                                if (vansArr[i] != "warehouse") {
                                    Van vanToSort = new Van(vansArr[i]);
                                    vanToSort.sortNumber();
                                }
                            }
                        }
                        if (numChoice == 8) {
                            warehouse.sortName(warehouse.getBikeParts());
                        }
                        if (numChoice == 9) {
                            warehouse.sortNumber();
                        }
                        if (numChoice == 10) {
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }





    private void deleteAUser(String aUser) {
        Scanner userInput = new Scanner(System.in);
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
                break;
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
                break;
            }
        }
        this.save();
    }


    public static void readTransfer() {
        //setup for reading a file
        Scanner fileReader = null;
        Scanner stdin;
        String fileName;
        File vanInv;

        stdin = new Scanner(System.in);
        System.out.println("Enter input file name: ");
        fileName = stdin.next();

        //actually read file, and pull out origin and destination
        try {
            vanInv = new File(fileName);
            fileReader = new Scanner(vanInv);

            //from first line create string with origin and destination
            //then split into array, grabbing appropriate values and storing them in string vars
            String originDestinationAssociate = fileReader.nextLine();//skipping first line of van inventory file
            String origin = originDestinationAssociate.split(",")[0];
            String destination = originDestinationAssociate.split(",")[1];

            //add check here so that a sales associate cannot load another ones van, only worried about an associate loading
            //the correct van from the warehouse, transfer from van to van are fine

            //handle all scenarios for transfer
            if(origin.equals("warehouse") && !destination.equals("warehouse")) { //warehouse to van
                Warehouse originWarehouse = new Warehouse(); //create warehouse object for transfer purposes
                Van destWarehouse = new Van(destination); //create van object for transfer purposes

                while (fileReader.hasNextLine()) {
                    String currentLine = fileReader.nextLine(); //create var currentline and set to current line of the file
                    String name = currentLine.split(",")[0]; //store part name
                    int qty = Integer.parseInt(currentLine.split(",")[1]); //store part qty
                    originWarehouse.removePart(name,qty);
                    destWarehouse.addPart(name,qty);
                }
            }else if(!origin.equals("warehouse") && !destination.equals("warehouse")) {//van to van
                Van originWarehouse = new Van(origin);
                Van destWarehouse = new Van(destination);
                while (fileReader.hasNextLine()) {
                    String currentLine = fileReader.nextLine();
                    String name = currentLine.split(",")[0];
                    int qty = Integer.parseInt(currentLine.split(",")[1]);
                    originWarehouse.removePart(name,qty);
                    destWarehouse.addPart(name,qty);
                }
            } else if(!origin.equals("warehouse") && destination.equals("warehouse")) {//van to warehouse
                Van originWarehouse = new Van(origin);
                Warehouse destWarehouse = new Warehouse();
                while (fileReader.hasNextLine()) {
                    String currentLine = fileReader.nextLine();
                    String name = currentLine.split(",")[0];
                    int qty = Integer.parseInt(currentLine.split(",")[1]);
                    originWarehouse.removePart(name,qty);
                    destWarehouse.addPart(name,qty);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.out.println("Please enter another file name and try again");
        }
    }

    /**
     * This method creates a new van inventory file from user input.
     * @return void
     */
    public static void createVanInventoryFile() {
        ArrayList<String> vanInventory = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        String vanPartName = "";
        int vanPartQuantity = 0;


        System.out.println("Enter the van inventory file: ");
        String fileFile = input.nextLine();
        fileFile = fileFile + ".txt";

        System.out.println("Please enter the warehouse you are transferring from: ");
        String transferWarehouse = input.next();

        System.out.println("Please enter the warehouse you are transferring to: ");
        String vanName = input.next();

        System.out.println("Please enter the sales associate for this sales van: ");
        String associateName = input.next();

        System.out.println("Please enter the number of parts you want to put in the van: ");
        int partNumber = input.nextInt();


        for (int i = 0; i < partNumber; i++) {

            System.out.println("Please enter the part name: ");
            vanPartName = input.next();
            System.out.println("Please enter the quantity: ");
            vanPartQuantity = input.nextInt();
            vanInventory.add(vanPartName + "," + vanPartQuantity);
        }

        try {
            File file = new File(fileFile);
            FileWriter write = new FileWriter((fileFile));

            write.write(transferWarehouse + "," + vanName + "," + associateName);
            write.write("\n");

            for (String str : vanInventory) {
                write.write(str + System.lineSeparator());
            }
            write.flush();
            file.createNewFile();
        } catch (Exception e) {
            System.out.println("Bad file name, try again.");
        }
    }
    /**
     * This method makes the creation of a BikePart Object more smooth and keeps the main method cleaner
     * @return BikePart
     */
    private static BikePart createNewBikePart() {
        String partName = "";
        int partNumber = 0;
        double listPrice = 0.0;
        double salePrice = 0.0;
        boolean onSale = false;
        int quantity = 0;
        int minimumQuantity = 0;
        try {
            System.out.print("Please enter a part name: ");
            partName = userInput.next();
            System.out.print("Please enter a part number: ");
            partNumber = userInput.nextInt();
            System.out.print("Please enter a part list price: ");
            listPrice = userInput.nextDouble();
            System.out.print("Please enter a part sale price: ");
            salePrice = userInput.nextDouble();
            System.out.print("Please enter if part is on sale: ");
            onSale = userInput.nextBoolean();
            System.out.print("Please enter part quantity: ");
            quantity = userInput.nextInt();
            System.out.print("Please enter the minimum quantity you want in inventory: ");
            minimumQuantity = userInput.nextInt();
            return new BikePart(partName, partNumber, listPrice, salePrice, onSale, quantity, minimumQuantity);//create bikepart object

        } catch (InputMismatchException e) {
            throw e;
        }
    }
}
