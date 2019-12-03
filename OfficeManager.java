package com.company;

import java.util.Scanner;

public class OfficeManager extends Warehouse {

    public OfficeManager() {

    }

    /**
     * this method allows the office manager to enter a part by name to get the information of said part
     * @param partName
     */
    public void examinePartName(String partName) {//office manager can enter a part name and receives part info
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

    /**
     * this method allows the office manager to enter a part by number to the the information for said part
     * @param partNumber
     */
    public void examinePartNumber(int partNumber) {//office manager can enter part number and get part info
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

    /**
     * this method alerts the office manager when there are part/s that are near or below their minimum quantity and need
     * to be restocked in the warehouse
     * @param minimumQuantity
     */
    public void orderPartsAlert(int minimumQuantity) {//need an additional method that notifies all parts that are at/below minimum qty
        //this method should be used in conjunction with sellpart
        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);

            if (currentPart.getQuantity() <= currentPart.getMinimumQuantity() + 1) {
                System.out.println("The quantity is getting low for this part: " + currentPart.getPartName() + " " + "The current quantity is: " + currentPart.getQuantity() + " " + "The minimum quantity is: " + currentPart.getMinimumQuantity());
            }
        }
    }

    /**
     * This method allows the office manager to order new part/s when they are near or below their minimum quantity
     * @return
     */
    public static BikePart orderParts(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Do you want to order parts for the warehouse? ");
        String choice = userInput.next();
        if(choice.equals("Y")){
            System.out.print("How many parts would you like to order? ");
            int newChoice = userInput.nextInt();
            for(int i = 0; i < newChoice; i++){
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
                    return new BikePart(partName, partNumber, listPrice, salePrice, onSale, quantity, minimumQuantity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }
        if(choice.equals("N")){
            System.exit(0);
        }
        return new BikePart();
    }

    /**
     * this method generates the commission of the salesperson that is chosen.
     */
    public void salesCommissions() {
        SalesAssociate salesAssociate = new SalesAssociate();
        salesAssociate.commission();

    }
}

