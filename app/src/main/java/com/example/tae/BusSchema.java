package com.example.tae;

public class BusSchema {
    String drivername;
    String mobile;
    String busno;
    String from;
    String to;
    String adminuniquecode;
    int lat,ing;

    public BusSchema(String drivername, String mobile, String busno, String from, String to,String uniquecodeno) {
        this.drivername = drivername;
        this.mobile = mobile;
        this.busno = busno;
        this.from = from;
        this.to = to;
        this.adminuniquecode=uniquecodeno;
        this.lat=0;
        this.ing=0;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getIng() {
        return ing;
    }

    public void setIng(int ing) {
        this.ing = ing;
    }

    public String getUniquecode() {
        return adminuniquecode;
    }

    public void setUniquecode(String uniquecodeno) {
        this.adminuniquecode = uniquecodeno;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
