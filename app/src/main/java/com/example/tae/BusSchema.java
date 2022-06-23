package com.example.tae;

public class BusSchema {
    String drivername;
    String mobile;
    String busno;
    String from;
    String to;
    String via;
    String adminuniquecode;
    String lat, lng;

    public BusSchema(String drivername, String mobile, String busno, String from, String to, String via, String uniquecodeno) {
        this.drivername = drivername;
        this.mobile = mobile;
        this.busno = busno;
        this.from = from;
        this.to = to;
        this.via=via;
        this.adminuniquecode=uniquecodeno;
        this.lat ="";
        this.lng ="";
    }


    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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
