package com.mall.shopkeeper.dao.model;

import java.util.Date;

public class Group {
    private String id;
    private String model;
    private double price;
    private Date ct;
    private Date ut;
    
    public Group(){
        
    }
    
    public Group(String id, String model, double price){
        Date now = new Date();
        this.id = id;
        this.model = model;
        this.price = price;
        this.ct = now;
        this.ut = now;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
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
}
