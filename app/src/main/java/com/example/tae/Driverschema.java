package com.example.tae;

public class Driverschema {
    String drivername;
    String password;
    String mobile;
    public Driverschema(String drivername,String password, String mobile) {
        this.drivername = drivername;
        this.password=password;
        this.mobile = mobile;

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
