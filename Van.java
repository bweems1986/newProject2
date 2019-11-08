//VanWarehouse currently reads in a van inventory file and adds it to the van warehouse DB
package com.company;

import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Van extends Warehouse {

    private ArrayList<BikePart> parts = new ArrayList<>();
    private String vanName;


    /**
     * default van constructor
     * @param name
     */
    public Van(String name) {
        this.vanName = name;
        File vanDB = new File( this.vanName + "DB.txt");
        if(vanDB.exists()){
            try {
                Scanner fileReader = new Scanner(vanDB);

                while (fileReader.hasNextLine()) { //add files to ArrayList
                    BikePart currentPart = new BikePart(fileReader.nextLine());
                    parts.add(currentPart);

                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }else {
            //create db file here
            try {
                FileWriter fw = new FileWriter((vanDB));

                for (int i = 0; parts.size() > i; i++) {
                    fw.write(parts.get(i).Serialize());
                }
            }
            catch (Exception e){
                System.out.println("Bad file name, try again.");
            }
        }
    }

    /**
     * This method updates the array list of parts during a van transfer
     * @param name
     * @param qty
     */
    public void addPart(String name, int qty){
        boolean found = false;
        for(int i=0; i < parts.size(); i++) {
            BikePart currentPart = parts.get(i);
            if(currentPart.partName.equals(name)){
                currentPart.setQuantity(currentPart.getQuantity() + qty);
                found = true; //set flag
            }
        }

        if(!found){
            Warehouse tmpWarehouse = new Warehouse();
            BikePart newPart = tmpWarehouse.retrievePart(name);
            newPart.setQuantity(qty);
            parts.add(newPart);
        }

        this.save();
    }

    /**
     * updates arraylist of parts during van transfer
     * @param name
     * @param qty
     */

    public void removePart(String name, int qty) {
        for(int i=0; i < parts.size(); i++) {
            BikePart currentPart = parts.get(i);
            if(currentPart.partName.equals(name)){
                currentPart.setQuantity(currentPart.getQuantity() - qty);
            }
        }

        this.save();
    }

    /**
     * method to sort the parts in the warehouse alphabetically
     */

    public void sortName() {
        System.out.println("Sorting by part name...");
        Collections.sort(parts, new Comparator<BikePart>() {
            @Override
            public int compare(BikePart o1, BikePart o2) {
                String n1 = o1.getPartName().toLowerCase();
                String n2 = o2.getPartName().toLowerCase();
                return n1.compareTo(n2);
            }
        });
        for (int i = 0; i < parts.size(); i++) {
            BikePart currentPart = parts.get(i);
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
        Collections.sort(parts, (o1, o2) -> Integer.valueOf(o1.getPartNumber()).compareTo(o2.getPartNumber()));

        for (int i = 0; i < parts.size(); i++) {
            BikePart currentPart = parts.get(i);
            System.out.println("Part name: " + currentPart.getPartName() +
                    "  Part Number: " + currentPart.getPartNumber() + "  List Price: "
                    + currentPart.getListPrice() + "  Sale Price: " + currentPart.getSalePrice()
                    + "  On Sale: " + currentPart.getOnSale() + "  Quantity: "
                    + currentPart.getQuantity());
        }
    }

    public void save() {
        try (FileWriter fw = new FileWriter(this.vanName + "DB.txt")) {
            for (int i = 0; parts.size() > i; i++) {
                fw.write(parts.get(i).Serialize());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




