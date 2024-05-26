package com.example.server.models;

import java.util.Date;

public class Contact {
    private int id;
    private String email;
    private String viewLink;
    private String phoneNumber;
    private String address;
    private Date birthDate;
    private String fastConnect;
    private String visibility;

    public static String ONLY_ME = "only_me";
    public static String MY_CONNECTIONS = "my_connections";
    public static String PRIVATE = "private";

    public Contact(String email, String viewLink, String phoneNumber) {
        this.email = email;
        this.viewLink = viewLink;
        this.phoneNumber = phoneNumber;
    }

    public Contact(int id, String email, String viewLink, String phoneNumber, String address, Date birthDate, String fastConnect) {
        this.id = id;
        this.email = email;
        this.viewLink = viewLink;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.fastConnect = fastConnect;
    }

    public Contact(int id, String email, String viewLink, String phoneNumber, String address, Date birthDate, String fastConnect, String visibility) {
        this.id = id;
        this.email = email;
        this.viewLink = viewLink;
        this.phoneNumber = phoneNumber;
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
}
