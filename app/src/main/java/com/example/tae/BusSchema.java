package com.example.tae;

public class BusSchema {
    String drivername;
    String mobile;
    String busno;
    String from;
    String to;
    String adminuniquecode;
    int latitude, longitude;

    public BusSchema(String drivername, String mobile, String busno, String from, String to,String uniquecodeno) {
        this.drivername = drivername;
        this.mobile = mobile;
        this.busno = busno;
        this.from = from;
        this.to = to;
        this.adminuniquecode=uniquecodeno;
        this.latitude =0;
        this.longitude =0;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
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
