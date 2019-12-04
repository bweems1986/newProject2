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


    /**
     * convert its state to a byte stream so that the byte stream can be reverted back into a copy of the object
     * @return serializeBikePart
     */

    public String Serialize() {
        String serializeSoldBikePart = (this.partName + "," + Integer.toString(this.partNumber)
                + "," + Double.toString(this.listPrice) + "," + Double.toString(this.salePrice)
                + "," + Boolean.toString(this.onSale) + ","  + Integer.toString(this.sellQuantity) + "," + this.totalSale + "," + this.date + "\n");
        return serializeSoldBikePart;
    }


    public int getDate() {
        return date;
    }

    public double getTotalSale() {
        return totalSale;
    }

    @Override
    public String toString(){
        return partNumber + "," + partName + "," + listPrice + "," + salePrice + "," + sellQuantity + "," + date;
    }

}



