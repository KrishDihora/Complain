package com.complain.Models;

public class StatusModel {

    String cname,date,status;

    public StatusModel(String cname,String date,String status){
        this.cname=cname;
        this.date=date;
        this.status=status;
    }

    public String getCName() {
        return cname;
    }

    public void setCName(String cname) {
        this.cname = cname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
