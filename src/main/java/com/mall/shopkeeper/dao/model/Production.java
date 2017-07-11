package com.mall.shopkeeper.dao.model;

import java.util.Date;

public class Production {
    private String id;
    private String model;
    private String type;
    private double price;
    private double lowestDiscount;
    private double specialDiscount;
    private Date ct;
    private Date ut;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getLowestDiscount() {
        return lowestDiscount;
    }
    public void setLowestDiscount(double lowestDiscount) {
        this.lowestDiscount = lowestDiscount;
    }
    public double getSpecialDiscount() {
        return specialDiscount;
    }
    public void setSpecialDiscount(double specialDiscount) {
        this.specialDiscount = specialDiscount;
    }
    public Date getCt() {
        return ct;
    }
    public void setCt(Date ct) {
        this.ct = ct;
    }
    public Date getUt() {
        return ut;
    }
    public void setUt(Date ut) {
        this.ut = ut;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
