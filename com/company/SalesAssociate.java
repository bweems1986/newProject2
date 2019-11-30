package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SalesAssociate extends Van {

    private static ArrayList<BikePart> parts = new ArrayList<>();
    private String salesAssociateName;
    private static ArrayList<String> sales = new ArrayList<>();

    public SalesAssociate() {
    }

    public SalesAssociate(String name) {
        this.salesAssociateName = name;
        File associateDB = new File( this.salesAssociateName + "SalesDB.txt");
        if(associateDB.exists()){
            try {
                Scanner fileReader = new Scanner(associateDB);
                while (fileReader.hasNextLine()) { //add files to ArrayList
                    String currentSales = new String(fileReader.nextLine());
                    sales.add(currentSales);
                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }else {
            //create db file here
            try {
                FileWriter fw = new FileWriter((associateDB));
                for (String sale : sales) {
                    fw.write(sale);
                }
            }
            catch (Exception e){
                System.out.println("Bad file name, try again.");
            }
        }
    }

    public void save() {
        try (FileWriter fw = new FileWriter(this.salesAssociateName + "SalesDB.txt")) {
            for (String sale : sales) {
                fw.write(sale);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //sell by part number
    public void sellPart(int partNumber) {//this method now enables you to sell multiples of a part at one time
        Scanner partQuantity  = new Scanner(System.in);
        Date now = new Date();
        for (int i = 0; parts.size() > i; i++) {
            BikePart currentPart = parts.get(i);
            if (currentPart.getPartNumber() == partNumber) {
                System.out.println("Enter the quantity you want to sell: ");
                int sellQuantity = partQuantity.nextInt();
                currentPart.setQuantity(currentPart.getQuantity() - sellQuantity);
                if (currentPart.getOnSale()) {
                    System.out.println("You're in luck! This item is on sale! \n PartName: "
                            + currentPart.getPartName() + "  Sale Price: " + currentPart.getSalePrice()
                            + "  Sale Date and Time: " + now);
                } else {
                    System.out.println("PartName: " + currentPart.getPartName() + "  List Price: "
                            + currentPart.getListPrice() + "  Sale Date and Time: " + now);
                }
            }
        }
        save();
    }

    //sell by part name
    public void sellPart(String partName) {//this method now enables you to sell multiples of a part at one time
        Scanner partQuantity  = new Scanner(System.in);
        Date now = new Date();
        for (int i = 0; parts.size() > i; i++) {
            BikePart currentPart = parts.get(i);
            if (currentPart.getPartName().equals(partName)) {
                System.out.println("Enter the number of parts you want to sell: ");
                int sellQuantity = partQuantity.nextInt();
                System.out.println("Enter the name of the store you are selling to: ");
                String storeName = partQuantity.next();
                currentPart.setQuantity(currentPart.getQuantity() - sellQuantity);
                sales.add(serialize(currentPart,storeName,now));
            }
        }
        this.save();
    }

    /**
     * convert its state to a byte stream so that the byte stream can be reverted back into a copy of the object
     *
     * @return serializeBikePart
     */
    private static String serialize(BikePart aPart, String storeName, Date now) {
        String serializedSale = (aPart.getPartName() + "," + Integer.toString(aPart.getPartNumber())
                + "," + Double.toString(aPart.getListPrice()) + "," + Double.toString(aPart.getSalePrice())
                + "," + Boolean.toString(aPart.getOnSale()) + "," +
                Integer.toString(aPart.getQuantity()) + "," + storeName + "," + now + "\n");
        return serializedSale;
    }

    public void deSerialize(String serializedSale) {

        String[] values = serializedSale.split(",");

        String partName = values[0];
        String partNumber = values[1];
        String listPrice = values[2];
        String salePrice = values[3];
        String isOnSale = values[4];
        String quantity = values[5];
        String store = values[6];
        String date = values[7];

    }

    public ArrayList<BikePart> getVanInv() {
        return parts;
    }

    public static ArrayList<String> getSales() {
        return sales;
    }
}
