package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SalesAssociate extends Warehouse{

    private ArrayList<String> vanParts = new ArrayList<>();
    private ArrayList<String> partsInvoice = new ArrayList<String>();
    protected static ArrayList<BikePart> parts = new ArrayList<>();
    private final String vanName;


    public SalesAssociate(String name){//takes in a van name and create van DB
        this.vanName = name;

        parts = new ArrayList();//warehouse array
        File warehouseDB = new File(this.vanName + "DB.txt");

        try {
            Scanner fileReader = new Scanner(warehouseDB);

            while (fileReader.hasNextLine()) { //add files to ArrayList
                BikePart currentPart = new BikePart(fileReader.nextLine());
                parts.add(currentPart);

            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    /**
     * method to save the state of the vanDB
     */

    public void save() {//saves state of van DB
        try (FileWriter fw = new FileWriter(this.vanName + "DB.txt")) {
            for (int i = 0; parts.size() > i; i++) {
                fw.write(parts.get(i).Serialize());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method saves the sold vanpart to the van salesDB
     * saves the complete part information with the quantity sold and the date and time of the sale
     */

    public void saveSales() {//when a van part is sold, the van part info and the quantity sold and date/time saved to salesDB for that van
        try (FileWriter fw = new FileWriter(this.vanName + "SalesDB.txt", true)) {
            for(int i = 0; vanParts.size() > i; i++)
                fw.write(String.valueOf(vanParts.get(i)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes in a van part number and looks for a match, if a match is found then enter the quantity that
     * you want to sell. If the currentpart quantity is less than sell quantity you cant make the sale.
     * If currentpart quantity is more than sell quantity it updates currentpart quantity and sell quantity,
     * adds the vanparts to vanparts arraylist. If the current part is onsale then adds the part info the sale price/quantity
     * to the partsinvoice arraylist and will give the total of that sales(sale price * quantity sold). If not onsale
     * add the part info and the list price to partsinvoice arraylist, will also give the total of the sale.
     * After this the method saves the state of the vanParts arraylist to the vanName + salesDB. Then saves the state of
     * the vanDB. The vanParts arraylist is then cleared out then an invoice is generated.
     * @param partNumber
     */
    @Override
    public void sellPart(int partNumber) {
        Scanner partQuantity  = new Scanner(System.in);
        Date now = new Date();

            for (int i = 0; parts.size() > i; i++) {
                BikePart currentPart = parts.get(i);
                if (currentPart.getPartNumber() == partNumber) {
                    System.out.println("Enter the quantity you want to sell: ");
                    int sellQuantity = partQuantity.nextInt();
                    if (currentPart.getQuantity() < sellQuantity) {
                        System.out.println("You do not have enough inventory in stock, select a different quantity.");
                    } else {

                        currentPart.setQuantity(currentPart.getQuantity() - sellQuantity);
                        currentPart.setSellQuantity(sellQuantity);
                        currentPart.setDate(now);

                        vanParts.add(currentPart.getPartName() + "," + currentPart.getPartNumber() + "," + currentPart.getListPrice()
                                + "," + currentPart.getSalePrice() + "," + currentPart.getOnSale() + ","
                                + currentPart.getSellQuantity(sellQuantity) + "," + currentPart.getDate() + "\n");

                    if(currentPart.getPartNumber() == partNumber) {

                        if(currentPart.getOnSale() == Boolean.parseBoolean("true")) {
                            partsInvoice.add(currentPart.getPartName() + " " + currentPart.getPartNumber() + " " +
                                    currentPart.getListPrice() + " " + currentPart.getSalePrice() + " " +
                                    currentPart.getSellQuantity(sellQuantity) + " " + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getSalePrice()));
                        }else {
                            partsInvoice.add(currentPart.getPartName() + " " + currentPart.getPartNumber() + " " +
                                    currentPart.getListPrice() + " " + currentPart.getSalePrice() + " " +
                                    currentPart.getSellQuantity(sellQuantity) + " " + "Total Cost:" + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getListPrice()));
                        }
                    }
                }
            }
        }
            this.saveSales();
            this.save();
            vanParts.clear();
            createInvoice();
    }

    /**
     * This method is identical to the sellPart method above except that it takes in a partName and not a partNumber
      * @param partName
     */

    public void sellPart(String partName) {//this method now enables you to sell multiples of a part at one time

        Scanner partQuantity = new Scanner(System.in);
        Date now = new Date();
        for (int i = 0; parts.size() > i; i++) {
            BikePart currentPart = parts.get(i);
            if (currentPart.getPartName().equals(partName)) {
                System.out.println("Enter the quantity you want to sell: ");
                int sellQuantity = partQuantity.nextInt();
                if (currentPart.getQuantity() < sellQuantity) {
                    System.out.println("You do not have enough inventory in stock, select a different quantity.");
                } else {
                    currentPart.setQuantity(currentPart.getQuantity() - sellQuantity);
                    currentPart.setSellQuantity(sellQuantity);
                    currentPart.setDate(now);


                    vanParts.add(currentPart.getPartName() + "," + currentPart.getPartNumber() + "," + currentPart.getListPrice()
                            + "," + currentPart.getSalePrice() + "," + currentPart.getOnSale() + ","
                            + currentPart.getSellQuantity(sellQuantity) + "," + currentPart.getDate() + "\n");

                    if (currentPart.getPartName() == partName) {

                        if (currentPart.getOnSale() == Boolean.parseBoolean("true")) {
                            partsInvoice.add(currentPart.getPartName() + " " + currentPart.getPartNumber() + " " +
                                    currentPart.getListPrice() + " " + currentPart.getSalePrice() + " " +
                                    currentPart.getSellQuantity(sellQuantity) + " " + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getSalePrice()));
                        } else {
                            partsInvoice.add(currentPart.getPartName() + " " + currentPart.getPartNumber() + " " +
                                    currentPart.getListPrice() + " " + currentPart.getSalePrice() + " " +
                                    currentPart.getSellQuantity(sellQuantity) + " " + "Total Cost:" + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getListPrice()));
                        }
                    }
                }
            }
        }
        this.saveSales();
        this.save();
        vanParts.clear();
        createInvoice();

    }

    /**
     * this method finds a part by partNumber
     * @param partNumber
     * @return found
     */

    @Override
    public boolean findPart(int partNumber) {
        boolean found = false;
        for (int i = 0; parts.size() > i; i++) {
            if (parts.get(i).getPartNumber() == partNumber) {//see if partNumber exists in arraylist
                found = true;
            }
        }
        return found;

    }
    /**
     * Finds part by name. If part is not in the vanwarehouse display invalid part name
     * @param partName
     * @return
     */
    @Override
    public boolean findPart(String partName) {
        boolean found = false;

        for (int i = 0; parts.size() > i; i++) {
            BikePart currentPart = (BikePart) parts.get(i);

            if (currentPart.getPartName().equals(partName)) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Invalid part name");
        }
        return found;
    }

    /**
     * This method is used to generate an invoice, currently not working***** if you enter in multiple parts it
     * will print the first part twice.
     */

    public void createInvoice(){
        System.out.println(partsInvoice.toString().replace("[","").replace("]","") + "\n");
    }
}
