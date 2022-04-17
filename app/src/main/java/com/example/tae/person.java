package com.example.tae;
public class person
{

    private String firstname;
    private String drivername;
    private String mob;
    private String busno;
    private String from;
    private String to;
    private String uniquecode;


    public person(){
    }
    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    // Getter and setter method

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
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


