package com.complain.Models;

public class datamodel {

   public String ctype,name,number,address,remark,date,status,quickc;


    public datamodel(String ctype,String name, String number, String address, String remark,String date,String status,String quickc) {
        this.ctype = ctype;
        this.name = name;
        this.number = number;
        this.address = address;
        this.remark = remark;
        this.date=date;
        this.status=status;
        this.quickc=quickc;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getQuickc() {
        return quickc;
    }

    public void setQuickc(String quickc) {
        this.quickc = quickc;
    }
}
