package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Warehouse {

    protected static ArrayList<BikePart> bikeParts = new ArrayList<>();
    protected static ArrayList<Van> vans =  new ArrayList<>();


    public Warehouse() {
        this.bikeParts = new ArrayList();//warehouse array
        File warehouseDB = new File("warehouseDB.txt");

        try {
            Scanner fileReader = new Scanner(warehouseDB);

            while (fileReader.hasNextLine()) { //add files to ArrayList
                BikePart currentPart = new BikePart(fileReader.nextLine());
                bikeParts.add(currentPart);

            }
        } catch (IOException e) {
            System.out.println("File not found");
        }

    }

    public void addPart(String name, int qty){
        for(int i=0; i < bikeParts.size(); i++) {
            BikePart currentPart = bikeParts.get(i);
            if(currentPart.partName.equals(name)){
                currentPart.setQuantity(currentPart.getQuantity() + qty);
            }
        }

        this.save();
    }

    public void removePart(String name, int qty) {
        for(int i=0; i < bikeParts.size(); i++) {
            BikePart currentPart = bikeParts.get(i);
            if(currentPart.partName.equals(name)){
                currentPart.setQuantity(currentPart.getQuantity() - qty);
            }
        }

        this.save();
    }

    public BikePart retrievePart(String name){
        BikePart thePart = new BikePart();
        for(int i=0; i < bikeParts.size(); i++) { //find the part we want
            BikePart currentPart = bikeParts.get(i);
            if(currentPart.partName.equals(name)){
                thePart.setQuantity(currentPart.getQuantity());
                thePart.setPartName(currentPart.getPartName());
                thePart.setPartNumber(currentPart.getPartNumber());
                thePart.setListPrice(currentPart.getListPrice());
                thePart.setSalePrice(currentPart.getSalePrice());
                thePart.setOnSale(currentPart.getOnSale());
            }
        }
        return thePart; //return the part we want
    }

    /**
     * method to sell a part by partNumber. If onsale displays salePrice, if not display listPrice. Displays date
     * and time of sale.
     * adjusts the quantity of the part sold
     *
     * @param partNumber
     */

    public void sellPart(int partNumber) {//this method now enables you to sell multiples of a part at one time
        Scanner partQuantity  = new Scanner(System.in);
        Date now = new Date();
        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);
            if (currentPart.getPartNumber() == partNumber) {
                System.out.println("Enter the quantity you want to sell: ");
                int sellQuantity = partQuantity.nextInt();
                currentPart.setQuantity(currentPart.getQuantity() - sellQuantity);
                if (currentPart.getOnSale()) {
                    System.out.println("PartName: "
                            + currentPart.getPartName() + "  Sale Price: " + currentPart.getSalePrice()
                            + "  Sale Date and Time: " + now);
                } else {
                    System.out.println("PartName: " + currentPart.getPartName() + "  List Price: "
                            + currentPart.getListPrice() + "  Sale Date and Time: " + now);
                }
            }
        }


        this.save();

    }

    /**
     * method to see if partNumber in the BikeParts array is equal to the partNumber inputted by the user
     *
     * @param partNumber
     * @return found
     */

    public boolean findPart(int partNumber) {
        boolean found = false;
        for (int i = 0; bikeParts.size() > i; i++) {
            if (bikeParts.get(i).getPartNumber() == partNumber) {//see if partNumber exists in arraylist
                found = true;
            }
        }
        return found;

    }

    /**
     * finds part by name, if the part is onsale it displays the sale price if not display
     * list price. If part is not in the warehouse display invalid part name
     *
     * @param partName
     * @return
     */
    public boolean findPart(String partName) {
        boolean found = false;

        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);

            if (currentPart.getPartName().equals(partName)) {
                found = true;
                if (currentPart.getOnSale()) {
                    System.out.println("PartName: " + currentPart.getPartName() + " Sale Price: " + currentPart.getSalePrice());
                } else {
                    System.out.println("PartName: " + currentPart.getPartName() + " List Price: " + currentPart.getListPrice());
                }
            }
        }
        if (!found) {
            System.out.println("Invalid part name");
        }
        return found;
    }

    /**
     * method to sort the parts in the warehouse alphabetically
     */

    public void sortName(ArrayList<BikePart> bikePartList) {
        System.out.println("Sorting by part name...");
        Collections.sort(bikePartList, new Comparator<BikePart>() {
            @Override
            public int compare(BikePart o1, BikePart o2) {
                String n1 = o1.getPartName().toLowerCase();
                String n2 = o2.getPartName().toLowerCase();
                return n1.compareTo(n2);
            }
        });
        for (int i = 0; i < bikeParts.size(); i++) {
            BikePart currentPart = bikeParts.get(i);
            System.out.println("Part name: " + currentPart.getPartName() + "  Part Number: "
                    + currentPart.getPartNumber() + "  List Price: " +
                    currentPart.getListPrice() + "  Sale Price: " + currentPart.getSalePrice() +
                    "  On Sale: " + currentPart.getOnSale() + "  Quantity: " + currentPart.getQuantity());
        }
    }

    /**
     * method to sort the parts in the warehouse by part number
     */

    public void sortNumber() {
        Collections.sort(bikeParts, (o1, o2) -> Integer.valueOf(o1.getPartNumber()).compareTo(o2.getPartNumber()));

        for (int i = 0; i < bikeParts.size(); i++) {
            BikePart currentPart = bikeParts.get(i);
            System.out.println("Part name: " + currentPart.getPartName() +
                    "  Part Number: " + currentPart.getPartNumber() + "  List Price: "
                    + currentPart.getListPrice() + "  Sale Price: " + currentPart.getSalePrice()
                    + "  On Sale: " + currentPart.getOnSale() + "  Quantity: "
                    + currentPart.getQuantity());
        }
    }

    /**
     * adds bikePart to the warehouse via user input, if the part is already in the warehouse
     * update listPrice, salePrice, and quantity
     *
     * @param bikePart
     */

    public void userPart(BikePart bikePart) { //take in bikepart and add it to warehouse
        if (bikeParts.size() == 0) {  //test to see if anything is in BikeParts
            bikeParts.add(bikePart);//nothing in BikeParts add userPart
        } else {
            boolean isDuplicate = false;
            for (int i = 0; bikeParts.size() > i; i++) {
                if (bikeParts.get(i).getPartNumber() == bikePart.getPartNumber()) {//this confirms duplicate
                    //update pricing/onsale/qty
                    isDuplicate = true;
                    bikeParts.get(i).setQuantity(bikePart.getQuantity() + bikeParts.get(i).getQuantity());
                    bikeParts.get(i).setListPrice(bikePart.getListPrice());
                    bikeParts.get(i).setSalePrice(bikePart.getSalePrice());
                    bikeParts.get(i).setMinimumQuantity(bikePart.getMinimumQuantity());
                }
            }
            if (isDuplicate == false) {
                bikeParts.add(bikePart);
            }
        }

        this.save();
    }

    /**
     * method to read in inventory file to warehouseDB file. Checks for duplicates and if found handle accordingly(update
     * listPrice, salePrice, quantity)
     */

    public void readFile() {

        ArrayList<BikePart> inventory = new ArrayList<>();
        Scanner fileReader = null;
        Scanner stdin;
        String fileName;
        File bikeInfo;

        stdin = new Scanner(System.in);
        System.out.println("Enter input file name: ");
        fileName = stdin.next();

        try {
            bikeInfo = new File(fileName);
            fileReader = new Scanner(bikeInfo);
            while (fileReader.hasNextLine()) { //add files to ArrayList
                BikePart currentPart = new BikePart(fileReader.nextLine());
                inventory.add(currentPart);

            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.out.println("Please enter another file name and try again");
        }


        if (bikeParts.size() == 0) {
            for (int i = 0; inventory.size() > i; i++) {
                bikeParts.add(inventory.get(i));
            }
        } else {
            boolean isDuplicate = false;
            for (int i = 0; inventory.size() > i; i++) {
                for (int j = 0; bikeParts.size() > j; j++) {
                    if (inventory.get(i).getPartNumber() == bikeParts.get(j).getPartNumber()) {//this confirms duplicate
                        //update pricing/onsale/qty
                        isDuplicate = true;
                        bikeParts.get(i).setQuantity(inventory.get(i).getQuantity() + bikeParts.get(i).getQuantity());
                        bikeParts.get(i).setListPrice(inventory.get(i).getListPrice());
                        bikeParts.get(i).setSalePrice(inventory.get(i).getSalePrice());
                    }

                }
                if (isDuplicate == false) {
                    bikeParts.add(inventory.get(i));
                }
            }
        }

        this.save();
    }

    /**
     * method to save state of warehouse file
     */
    public void save() {
        try (FileWriter fw = new FileWriter("warehouseDB.txt")) {
            for (int i = 0; bikeParts.size() > i; i++) {
                fw.write(bikeParts.get(i).Serialize());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BikePart> getBikeParts() {
        return bikeParts;
    }

    public static ArrayList<Van> getVans() {
        return vans;
    }

}





