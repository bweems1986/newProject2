package com.company;

import java.util.ArrayList;
import java.util.*;

public class BikePart {
    protected String partName;
    protected int partNumber;
    private double listPrice;
    private double salePrice;
    private boolean onSale;
    protected int quantity;
    protected int minimumQuantity;

    public BikePart(String partName, int partNumber, double listPrice,
                    double salePrice, boolean onSale, int quantity, int minimumQuantity) { //class constructors
        this.partName = partName;
        this.partNumber = partNumber;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.onSale = onSale;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
    }

    /**
     * store serialized bikePart into an String array of values
     *
     * @param serializedBikePart
     */
    public BikePart(String serializedBikePart) {

        String[] values = serializedBikePart.split(",");

        this.partName = values[0];
        this.partNumber = Integer.parseInt(values[1]);
        this.listPrice = Double.parseDouble(values[2]);
        this.salePrice = Double.parseDouble(values[3]);
        this.onSale = Boolean.parseBoolean(values[4]);
        this.quantity = Integer.parseInt(values[5]);
        this.minimumQuantity = Integer.parseInt(values[6]);

    }

    public BikePart() {
    }

    /**
     * convert its state to a byte stream so that the byte stream can be reverted back into a copy of the object
     *
     * @return serializeBikePart
     */

    public String Serialize() {
        String serializeBikePart = (this.partName + "," + Integer.toString(this.partNumber)
                + "," + Double.toString(this.listPrice) + "," + Double.toString(this.salePrice)
                + "," + Boolean.toString(this.onSale) + "," +
                Integer.toString(this.quantity) + "," + Integer.toString(this.minimumQuantity) + "\n");
        return serializeBikePart;
    }

    /**
     * Sets part name of a bike part
     * @param partName
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * sets if a part is on sale for a bike part
     * @param onSale
     */
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    /**
     * sets list price of a bike part
     * @param listPrice
     */
    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * sets part number of a bike part
     * @param partNumber
     */
    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * sets quantity of a bike part
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * sets sale price of a bike part
     * @param salePrice
     */
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * gets part name of a bike part
     * @return partName
     */
    public String getPartName() {
        return partName;
    }

    /**
     * gets list price of a bike part
     * @return listPrice
     */
    public double getListPrice() {
        return listPrice;
    }

    /**
     * gets sale price of a bike part
     * @return salePrice
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * gets Part number of a bike part
     * @return partNumber
     */
    public int getPartNumber() {
        return partNumber;
    }

    /**
     * gets quantity of a bike part
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * gets on sale boolean of a bike part
     * @return onSale
     */
    public boolean getOnSale() {
        return onSale;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }
}





