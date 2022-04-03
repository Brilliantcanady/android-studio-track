package com.example.tae;

public class BusSchema {
    String drivername;
    String mobile;
    String busno;
    String from;
    String to;

    public BusSchema(String drivername, String mobile, String busno, String from, String to) {
        this.drivername = drivername;
        this.mobile = mobile;
        this.busno = busno;
        this.from = from;
        this.to = to;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getMob() {
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
