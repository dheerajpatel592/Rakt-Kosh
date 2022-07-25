package com.g4x.bloodapp;

public class Item {
    private String name ;
    private String phoneNo ;
    private String address ;
    private String emailId ;
    private String type ;
    private String lastUpdated ;
    private String latitude ;
    private String longitude ;

    private BloodStock OPositiveStock , ONegativeStock , APositiveStock , ANegativeStock  ,
           BPositiveStock  , BNegativeStock  ,ABPositiveStock  ,ABNegativeStock  ;

    public Item() {
    }

    public Item(String name, String phoneNo, String address, String emailId, String type, String lastUpdated, String latitude, String longitude, BloodStock OPositiveStock, BloodStock ONegativeStock, BloodStock APositiveStock, BloodStock ANegativeStock, BloodStock BPositiveStock, BloodStock BNegativeStock, BloodStock ABPositiveStock, BloodStock ABNegativeStock) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
        this.emailId = emailId;
        this.type = type;
        this.lastUpdated = lastUpdated;
        this.latitude = latitude;
        this.longitude = longitude;
        this.OPositiveStock = OPositiveStock;
        this.ONegativeStock = ONegativeStock;
        this.APositiveStock = APositiveStock;
        this.ANegativeStock = ANegativeStock;
        this.BPositiveStock = BPositiveStock;
        this.BNegativeStock = BNegativeStock;
        this.ABPositiveStock = ABPositiveStock;
        this.ABNegativeStock = ABNegativeStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public BloodStock getOPositiveStock() {
        return OPositiveStock;
    }

    public void setOPositiveStock(BloodStock OPositiveStock) {
        this.OPositiveStock = OPositiveStock;
    }

    public BloodStock getONegativeStock() {
        return ONegativeStock;
    }

    public void setONegativeStock(BloodStock ONegativeStock) {
        this.ONegativeStock = ONegativeStock;
    }

    public BloodStock getAPositiveStock() {
        return APositiveStock;
    }

    public void setAPositiveStock(BloodStock APositiveStock) {
        this.APositiveStock = APositiveStock;
    }

    public BloodStock getANegativeStock() {
        return ANegativeStock;
    }

    public void setANegativeStock(BloodStock ANegativeStock) {
        this.ANegativeStock = ANegativeStock;
    }

    public BloodStock getBPositiveStock() {
        return BPositiveStock;
    }

    public void setBPositiveStock(BloodStock BPositiveStock) {
        this.BPositiveStock = BPositiveStock;
    }

    public BloodStock getBNegativeStock() {
        return BNegativeStock;
    }

    public void setBNegativeStock(BloodStock BNegativeStock) {
        this.BNegativeStock = BNegativeStock;
    }

    public BloodStock getABPositiveStock() {
        return ABPositiveStock;
    }

    public void setABPositiveStock(BloodStock ABPositiveStock) {
        this.ABPositiveStock = ABPositiveStock;
    }

    public BloodStock getABNegativeStock() {
        return ABNegativeStock;
    }

    public void setABNegativeStock(BloodStock ABNegativeStock) {
        this.ABNegativeStock = ABNegativeStock;
    }
}
