package com.example.server.models;

import java.util.Date;

public class Contact {
    private int id;
    private String email;
    private String viewLink;
    private String phoneNumber;
    private String workNumber;
    private String homeNumber;
    private String address;
    private Date birthDate;
    private String fastConnect;
    private String visibility;

    public static String ONLY_ME = "only_me";
    public static String MY_CONNECTIONS = "my_connections";
    public static String EVERYONE = "everyone";

    public Contact(String email, String viewLink, String phoneNumber, String workNumber, String homeNumber) {
        this.email = email;
        this.viewLink = viewLink;
        this.phoneNumber = phoneNumber;
        this.workNumber = workNumber;
        this.homeNumber = homeNumber;
    }

    public Contact(int id, String email, String viewLink, String phoneNumber, String workNumber, String homeNumber, String address, Date birthDate, String fastConnect) {
        this.id = id;
        this.email = email;
        this.viewLink = viewLink;
        this.phoneNumber = phoneNumber;
        this.workNumber = workNumber;
        this.homeNumber = homeNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.fastConnect = fastConnect;
    }

    public Contact(int id, String email, String viewLink, String phoneNumber, String workNumber, String homeNumber, String address, Date birthDate, String fastConnect, String visibility) {
        this.id = id;
        this.email = email;
        this.viewLink = viewLink;
        this.phoneNumber = phoneNumber;
        this.workNumber = workNumber;
        this.homeNumber = homeNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.fastConnect = fastConnect;
        this.visibility = visibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFastConnect() {
        return fastConnect;
    }

    public void setFastConnect(String fastConnect) {
        this.fastConnect = fastConnect;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }
}
