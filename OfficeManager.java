package com.company;

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

    public void orderParts(int minimumQuantity) {//need an additional method that notifies all parts that are at/below minimum qty
        //this method should be used in conjunction with sellpart
        for (int i = 0; bikeParts.size() > i; i++) {
            BikePart currentPart = bikeParts.get(i);

            if(currentPart.getQuantity() <= currentPart.getMinimumQuantity()){
                System.out.println("The quantity is getting low for this part, please order more.");
            }
        }
    }

    public void salesCommissions(){
        //parameters sales associate, start date, end date
    }
}