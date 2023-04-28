package com.complain;

public class datamodel {

   public String ctype,name,number,address,remark;


    public datamodel(String name, String number, String address, String remark) {
        this.ctype = ctype;
        this.name = name;
        this.number = number;
        this.address = address;
        this.remark = remark;
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
}
