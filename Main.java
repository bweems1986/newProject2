package com.company;

/**
 * This program is inventory management system for bike parts. It allows a user to read in an inventory file to the warehouseDB text file.
 * The user can read in a file, enter a part manually, sell a part by part number, display a part by part name, sort the inventory by part name and
 * sort the inventory by part number.
 *
 * @author Brad Weems & Brandon Frulla
 * @version 2.0
 */


public class Main {


    public static void main(String[] args) {
        SystemAdministrator systemAdministrator = new SystemAdministrator();
        systemAdministrator.logIn();
    }
}