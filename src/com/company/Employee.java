package com.company;

public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String passWord;
    private String phoneNumber;
    private String jobTitle;
    private String vanName;

    public Employee(String firstName, String lastName, String email, String userName, String passWord,
                    String phoneNumber, String jobTitle){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.vanName = null;

    }

    public Employee(String firstName, String lastName, String email, String userName, String passWord,
                    String phoneNumber, String jobTitle, String vanName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.vanName = vanName;

    }

    public String Serialize() {
        String serializedEmployee = (this.firstName + "," + (this.lastName)
                + "," + (this.email) + "," + (this.userName) + "," + (this.passWord) +
                "," + (this.phoneNumber) + "," + (this.jobTitle) + "," + (this.vanName) + ("\n"));
        return serializedEmployee;
    }

    public Employee(String serializedEmployee) {

        String[] values = serializedEmployee.split(",");
        this.firstName = values[0];
        this.lastName = values[1];
        this.email = values[2];
        this.userName = values[3];
        this.passWord = values[4];
        this.phoneNumber = values[5];
        this.jobTitle = values[6];
        this.vanName = values[7];
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getVanName() { return vanName; }
}
