package com.g4x.bloodapp;

public class Donor {
    String bloodGrp ;
    String latitude ;
    String longitude ;
    String token ;

    public Donor(String bloodGrp, String latitude, String longitude, String token) {
        this.bloodGrp = bloodGrp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.token = token;
    }

    public Donor() {
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
