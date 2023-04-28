package com.complain;

public class datamodel {

    String ctype,name,number,address,landmard,remark;

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

    public String getLandmard() {
        return landmard;
    }

    public void setLandmard(String landmard) {
        this.landmard = landmard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public datamodel(String ctype, String name, String number, String address, String landmard, String remark) {
        this.ctype = ctype;
        this.name = name;
        this.number = number;
        this.address = address;
        this.landmard = landmard;
        this.remark = remark;
    }
}
