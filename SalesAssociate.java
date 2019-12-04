package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class SalesAssociate extends Warehouse {

    public static ArrayList<SoldBikePart> soldParts = new ArrayList<>();
    private ArrayList<String> vanParts = new ArrayList<>();
    private ArrayList<String> partsInvoice = new ArrayList<>();
    protected static ArrayList<BikePart> parts = new ArrayList<>();
    private String vanName;


    public SalesAssociate(String name) {//takes in a van name and create van DB
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

    public SalesAssociate() {

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

    public void saveSales() {
        try (FileWriter fw = new FileWriter(this.vanName + "SalesDB.txt", true)) {
            for (int i = 0; vanParts.size() > i; i++)
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
     *
     * @param partNumber
     */
    @Override
    public void sellPart(int partNumber) {
        Scanner partQuantity = new Scanner(System.in);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String date = dateFormat.format(now);


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
                    currentPart.setDate(date);

                    vanParts.add(currentPart.getPartName() + "," + currentPart.getPartNumber() + "," + currentPart.getListPrice()
                            + "," + currentPart.getSalePrice() + "," + currentPart.getOnSale() + ","
                            + currentPart.getSellQuantity(sellQuantity) + "," + (currentPart.getSellQuantity(sellQuantity) *
                            currentPart.getSalePrice()) + "," + currentPart.getDate() + "\n");
                    //soldPartInfo.add(vanParts);

                    if (currentPart.getPartNumber() == partNumber) {
                        if (currentPart.getOnSale() == Boolean.parseBoolean("true")) {
                            partsInvoice.add(currentPart.getPartName() + "    " + currentPart.getPartNumber() + "      " +
                                    currentPart.getListPrice() + "    " + currentPart.getSalePrice() + "        " +
                                    currentPart.getSellQuantity(sellQuantity) + "      " + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getSalePrice()));
                        } else {
                            partsInvoice.add(currentPart.getPartName() + "    " + currentPart.getPartNumber() + "      " +
                                    currentPart.getListPrice() + "    " + currentPart.getSalePrice() + "        " +
                                    currentPart.getSellQuantity(sellQuantity) + "      " + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getListPrice()));
                        }
                    }
                }
            }
        }
        this.saveSales();
        this.save();
        vanParts.clear();
    }

    /**
     * This method is identical to the sellPart method above except that it takes in a partName and not a partNumber
     *
     * @param partName
     */

    public void sellPart(String partName) {

        Scanner partQuantity = new Scanner(System.in);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String date = dateFormat.format(now);

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
                    currentPart.setDate(date);


                    vanParts.add(currentPart.getPartName() + "," + currentPart.getPartNumber() + "," + currentPart.getListPrice()
                            + "," + currentPart.getSalePrice() + "," + currentPart.getOnSale() + ","
                            + currentPart.getSellQuantity(sellQuantity) + "," +
                            (currentPart.getSellQuantity(sellQuantity) * currentPart.getSalePrice()) +
                                    "," + currentPart.getDate() + "\n");
                    //soldPartInfo.add(vanParts);


                    if (currentPart.getPartName().equals(partName)) {

                        if (currentPart.getOnSale() == Boolean.parseBoolean("true")) {
                            partsInvoice.add(currentPart.getPartName() + "    " + currentPart.getPartNumber() + "      " +
                                    currentPart.getListPrice() + "    " + currentPart.getSalePrice() + "        " +
                                    currentPart.getSellQuantity(sellQuantity) + "      " + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getSalePrice()));
                        } else {
                            partsInvoice.add(currentPart.getPartName() + "    " + currentPart.getPartNumber() + "      " +
                                    currentPart.getListPrice() + "    " + currentPart.getSalePrice() + "        " +
                                    currentPart.getSellQuantity(sellQuantity) + "      " + (currentPart.getSellQuantity(sellQuantity) *
                                    currentPart.getListPrice()));
                        }
                    }
                }
            }
        }
        this.saveSales();
        this.save();
        vanParts.clear();
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
     * This method is used to generate an invoice
     */
    public void createInvoice() {
        Date date = new Date();
        Scanner stdin = new Scanner(System.in);
        System.out.println("Enter the shop name: ");
        String shopName = stdin.next();


        System.out.println("Sales invoice for" + " " + shopName + "," + " " + date);
        System.out.println("Part Name   Part Number     Price   Sale Price  Qty     Total Cost");


        for (int i = 0; i < partsInvoice.size(); i++)
            System.out.println(partsInvoice.get(i));
    }

    /**
     * this method is used to generate the commissions. The van sales DB holds all of the sales of that van/person.
     * The method uses the start date and end date entered by the user to get those sales for the given dates.
     * The total sales for those dates are added together and them multiplied by the commission rate. Which then returns
     * the salary commission for the sale rep for the time period specified.
     * @return
     */
    public double commission() {
        ArrayList<SoldBikePart> inventory = new ArrayList<>();
        ArrayList<Double> totalSales = new ArrayList<>();
        Scanner fileReader = null;
        Scanner stdin;
        String fileName;
        File bikeInfo;
        double salary;

        stdin = new Scanner(System.in);
        System.out.println("Enter van name: ");
        fileName = stdin.next();

        try {
            bikeInfo = new File("van" + fileName + "SalesDB.txt");
            fileReader = new Scanner(bikeInfo);
            while (fileReader.hasNextLine()) { //add files to ArrayList
                SoldBikePart currentPart = new SoldBikePart(fileReader.nextLine());
                inventory.add(currentPart);

            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.out.println("Please enter another file name and try again");
        }


        if (soldParts.size() == 0) {
            for (int i = 0; inventory.size() > i; i++) {
                soldParts.add(inventory.get(i));
            }
        }
        System.out.println("Enter the start date: ");
        int start = stdin.nextInt();
        System.out.println("Enter the end date: ");
        int end = stdin.nextInt();
        for (int i = 0; i < soldParts.size(); i++) {
            SoldBikePart currentPart = soldParts.get(i);
            if((currentPart.getDate() >= start) &&  (currentPart.getDate() <= end)){
                //System.out.print(currentPart.getTotalSale() + ",");
                totalSales.add(currentPart.getTotalSale());
            }
        }

        double sum = 0;
        Iterator<Double> iterator = totalSales.iterator();
        while (iterator.hasNext()){
            sum += iterator.next();
        }

        /*for(int i = 0; i < totalSales.size(); i++){
             sum += totalSales.get(i);

        }*/
        DecimalFormat numberFormat = new DecimalFormat("#.00");

        salary = (sum * .15);
        System.out.print("Your salary for the sales period is: " + numberFormat.format(salary) + "\n");
        return sum;
    }
}



