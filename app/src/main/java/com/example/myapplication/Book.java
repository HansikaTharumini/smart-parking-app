package com.example.myapplication;

import java.time.Duration;

public class Book {

    private String vehicaltype;
    private String  vehicalnumber;
    private String date;
    private String startTime;
    private String endTime;
    private String Imageaddress;
    private int slot;

    public Book(String type, String number, String date, String from, String to, int slotnum, String imageadd) {

        this.vehicaltype=type;
        this.vehicalnumber=number;
        this.date=date;
        this.startTime=from;
        this.endTime=to;
        this.slot=slotnum;
        this.Imageaddress=imageadd;
    }


    public String getvehicaltype() {
        return vehicaltype;
    }

    public void setvehicaltype(String type) {
        this.vehicaltype = type;
    }

    public String getvehicalnumber() {
        return vehicalnumber;
    }

    public void setvehicalnumber(String number) {
        this.vehicalnumber = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getstartTime() {
        return startTime;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setstartTime(String from) {
        this.startTime = from;
    }

    public String getendTime() {
        return endTime;
    }

    public void setendTime(String to) {
        this.endTime = to;
    }

    public String getImageaddress() {
        return Imageaddress;
    }

    public void setImageaddress(String imageaddress) {
        Imageaddress = imageaddress;
    }
}
