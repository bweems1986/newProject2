package com.company;


public class SoldBikePart {
    protected String partName;
    protected int partNumber;
    private double listPrice;
    private double salePrice;
    private boolean onSale;
    protected int quantity;
    private int sellQuantity;
    private double totalSale;
    private int date;

    public SoldBikePart(String partName, int partNumber, double listPrice,
                        double salePrice, boolean onSale, int quantity, int sellQuantity, double totalSale, int date) { //class constructors
        this.partName = partName;
        this.partNumber = partNumber;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.onSale = onSale;
        this.quantity = quantity;
        this.sellQuantity = sellQuantity;
        this.totalSale = totalSale;
        this.date = date;
    }

    /**
     * store serialized bikePart into an String array of values
     *
     * @param serializedSoldBikePart
     */
    public SoldBikePart(String serializedSoldBikePart) {

        String[] values = serializedSoldBikePart.split(",");

        this.partName = values[0];
        this.partNumber = Integer.parseInt(values[1]);
        this.listPrice = Double.parseDouble(values[2]);
        this.salePrice = Double.parseDouble(values[3]);
        this.onSale = Boolean.parseBoolean(values[4]);
        this.sellQuantity = Integer.parseInt(values[5]);
        this.totalSale = Double.parseDouble(values[6]);
        this.date = Integer.parseInt(values[7]);


    }

    public SoldBikePart() {
    }

    /**
     * convert its state to a byte stream so that the byte stream can be reverted back into a copy of the object
     *
     * @return serializeBikePart
     */

    public String Serialize() {
        String serializeSoldBikePart = (this.partName + "," + Integer.toString(this.partNumber)
                + "," + Double.toString(this.listPrice) + "," + Double.toString(this.salePrice)
                + "," + Boolean.toString(this.onSale) + ","  + Integer.toString(this.sellQuantity) + "," + this.totalSale + "," + this.date + "\n");
        return serializeSoldBikePart;
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
     * gets on sale boolean of a bike part
     * @return onSale
     */
    public boolean getOnSale() {
        return onSale;
    }

    public int getSellQuantity(int sellQuantity){return this.sellQuantity;}

    public void setSellQuantity(int sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public int getDate() {
        return date;
    }

    public void setDate() {
        this.date = date;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        this.totalSale = totalSale;
    }

    @Override
    public String toString(){
        return partNumber + "," + partName + "," + listPrice + "," + salePrice + "," + sellQuantity + "," + date;
    }

}



