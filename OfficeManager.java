package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OfficeManager extends Warehouse {

    Warehouse partWarehouse = new Warehouse();

    public OfficeManager() {

    }

    public void examinePartName(String partName) {//office manager can enter a part name and receives part info
        //super.findPart(partName);//figure out how to call superclass method and add additional
        boolean found = false;

        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);

            if (currentPart.getPartName().equals(partName)) {
                found = true;
                System.out.println("PartName: " + currentPart.getPartName() + "PartNumber: " + currentPart.getPartNumber() + "ListPrice: " + currentPart.getListPrice() + "SalePrice: "
                        + currentPart.getSalePrice() + "Quantity: " + currentPart.getQuantity());
            }
        }
        if (!found) {
            System.out.println("Invalid part name");
        }
    }

    public void examinePartNumber(int partNumber) {//office manager can enter part number and get part info
        //super.findPart(partNumber);
        boolean found = false;

        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);

            if (currentPart.getPartNumber() == partNumber) {
                found = true;
                System.out.println("PartName: " + currentPart.getPartName() + " " + "PartNumber: " + currentPart.getPartNumber() + " " +
                        "ListPrice: " + currentPart.getListPrice() + " " + "SalePrice: "
                        + currentPart.getSalePrice() + " " + "Quantity: " + currentPart.getQuantity());
            }
        }
        if (!found) {
            System.out.println("Invalid part number");
        }
    }

    public void orderPartsAlert(int minimumQuantity) {//need an additional method that notifies all parts that are at/below minimum qty
        //this method should be used in conjunction with sellpart
        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);

            if (currentPart.getQuantity() <= currentPart.getMinimumQuantity() + 1) {
                System.out.println("The quantity is getting low for this part: " + currentPart.getPartName() + " " + "The current quantity is: " + currentPart.getQuantity() + " " + "The minimum quantity is: " + currentPart.getMinimumQuantity());
            }

        }
    }

    public void salesCommissions() {
        SalesAssociate salesAssociate = new SalesAssociate();
        salesAssociate.commission();

    }
}


//add order part to main menu, this will be the same as the enter a part except you need to be able to order multiple parts at a time
//enter the number of parts you wish to order, enter the part
