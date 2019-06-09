package com.example.bus_uni.Booking;

public class Visa {

    private String cardNum = "";
    private String cvvNum = "";
    private String expirationDate = "";
    private String costValue = "";
    private String name = "";
    private String phone = "";

    public Visa() {
    }

    public Visa(String cardNum, String cvvNum, String expirationDate, String costValue, String name, String phone) {
        this.cardNum = cardNum;
        this.cvvNum = cvvNum;
        this.expirationDate = expirationDate;
        this.costValue = costValue;
        this.name = name;
        this.phone = phone;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCvvNum() {
        return cvvNum;
    }

    public void setCvvNum(String cvvNum) {
        this.cvvNum = cvvNum;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
