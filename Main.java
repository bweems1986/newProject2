package com.company; /**
 * This program is inventory management system for bike parts. It allows a user to read in an inventory file to the warehouseDB text file.
 * The user can read in a file, enter a part manually, sell a part by part number, display a part by part name, sort the inventory by part name and
 * sort the inventory by part number.
 *
 * @author Brad Weems & Brandon Frulla
 * @version 2.0
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Main {

    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        SystemAdministrator.logIn();
        int choice = 0;
        Scanner userInput = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();
        SystemAdministrator systemAdministrator = new SystemAdministrator();
        OfficeManager officeManager = new OfficeManager();
        BikePart bikePart = new BikePart();
        WarehouseManager warehouseManager = new WarehouseManager();
        //Van van = new Van();

        while (choice != 99) {
            printMenu();
            choice = userInput.nextInt();
            if (choice == 1) {
                warehouse.readFile();
            } else if (choice == 2) {
                warehouse.userPart(createNewBikePart());
            } else if (choice == 3) {
                System.out.print("Please enter a part number: ");
                int partNumber = userInput.nextInt();
                if (warehouse.findPart(partNumber)) {//returns true or false, if true call sell method
                    warehouse.sellPart(partNumber);
                    officeManager.orderParts(bikePart.getMinimumQuantity());
                } else {
                    System.out.println("Invalid part number");
                }
            } else if (choice == 4) {
                System.out.print("Please enter a part name: ");
                String partName = userInput.next();
                warehouse.findPart(partName);
            } else if (choice == 5) {
                warehouse.sortName(warehouse.getBikeParts());
            } else if (choice == 6) {
                warehouse.sortNumber();
            } else if (choice == 7) {
                readTransfer();
            } else if (choice == 8) {//read in van inventory file and add to van warehouse DB file, updates quantity in main warehouse
                createVanInventoryFile();
                readTransfer();
            } else if (choice == 9) { //sort a specific van warehouse by name
                System.out.print("Please enter a van name:");
                String vanName = userInput.next();
                Van vanToSort = new Van(vanName);
                vanToSort.sortName();
            } else if (choice == 10) { //sort a specific van warehouse by part number
                System.out.print("Please enter a van name:");
                String vanName = userInput.next();
                Van vanToSort = new Van(vanName);
                vanToSort.sortNumber();
            } else if (choice == 11) {
                System.out.print("Please enter each warehouse name separated by commas: (i.e. vanA,vanB,vanC)");
                String vans = userInput.next();
                String[] vansArr = vans.split(",");
                for(int i = 0; i<vansArr.length; i++){
                    if(vansArr[i] != "warehouse"){
                        Van vanToSort = new Van(vansArr[i]);
                        vanToSort.sortName();
                    }
                }
            } else if (choice == 12) {
                System.out.print("Please enter each warehouse name separated by commas: (i.e. vanA,vanB,vanC)");
                String vans = userInput.next();
                String[] vansArr = vans.split(",");
                for(int i = 0; i<vansArr.length; i++){
                    if(vansArr[i] != "warehouse"){
                        Van vanToSort = new Van(vansArr[i]);
                        vanToSort.sortNumber();
                    }
                }
            }else if(choice == 13){//creates a database of user accounts, needs to be available only to system administrator
                    systemAdministrator.addUserAccount(SystemAdministrator.createAccount(1));
            }else if(choice == 14){//needs to be available only to office manager
                officeManager.examinePartName();

            }else if(choice == 15){//available only to office manager
                officeManager.examinePartNumber();

            }else if(choice == 16){//these need to be available only to warehouse manager
                System.out.println("Please enter a part name: ");
                int partName = userInput.nextInt();
                warehouseManager.examinePartNumber(partName);
            }else if(choice == 17){//this needs to be available only to warehouse manager
                System.out.println("Please enter a part number: ");
                int partNumber = userInput.nextInt();
                warehouseManager.examinePartNumber(partNumber);
            }else if(choice == 18){

            }

            else {
                System.out.print("Bye!");
                System.exit(7);
            }
        }

    }//closes main method


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
            return new BikePart(partName, partNumber, listPrice, salePrice,
                    onSale, quantity, minimumQuantity);//create bikepart object

        } catch (InputMismatchException e) {
            throw e;
        }
    }


    /**
     * This method is simply a clump of print statements, intended to keep main method cleaner
     * @return void
     */
    private static void printMenu() {
        System.out.print("Please enter a choice from the menu:\n" +
                "1: Read an inventory delivery file\n" +
                "2: Enter a part\n3: Sell a part\n" +
                "4: Display a part\n5: Sort parts by part name\n" +
                "6: Sort parts by part number\n"+
                "7: Transfer Parts\n" +
                "8: Create a van inventory file\n"+
                "9: Sort van warehouse by name\n" +
                "10: Sort van warehouse by part number\n" +
                "11: Sort all warehouses by part name\n" +
                "12: Sort all warehouses by part number\n" +
                "13: Quit\n" +
                "Enter your choice: "
        );
    }

    /**
     * This method reads in a van inventory transfer file and updates the associated warehouses
     * @return void
     */
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
            String originDestination = fileReader.nextLine();//skipping first line of van inventory file
            String origin = originDestination.split(",")[0];
            String destination = originDestination.split(",")[1];

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
        System.out.println("Please enter the number of parts you want to put in the van: ");
        int partNumber = input.nextInt();


        for(int i = 0; i < partNumber; i++) {

            System.out.println("Please enter the part name: ");
            vanPartName = input.next();
            System.out.println("Please enter the quantity: ");
            vanPartQuantity = input.nextInt();
            vanInventory.add(vanPartName+","+ vanPartQuantity);
        }

        try {
            File file = new File(fileFile);
            FileWriter write = new FileWriter((fileFile));

            write.write(transferWarehouse + "," + vanName);
            write.write("\n");

            for(String str: vanInventory){
                write.write(str + System.lineSeparator());
            }
            write.flush();
            file.createNewFile();
        }
        catch (Exception e){
            System.out.println("Bad file name, try again.");
        }
    }

}

