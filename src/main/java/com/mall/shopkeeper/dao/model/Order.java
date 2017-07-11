package com.mall.shopkeeper.dao.model;

import java.util.Date;

public class Order {
    private String id;
    private String storeName;
    private String clientName;
    
    private String store;
    private String client;
    private String price;
    private String productions;
    private String groups;
    
    private String status;
    
    private String message;
    
    private Date ct;
    private Date ut;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getStore() {
        return store;
    }
    public void setStore(String store) {
        this.store = store;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getProductions() {
        return productions;
    }
    public void setProductions(String productions) {
        this.productions = productions;
    }
    public String getGroups() {
        return groups;
    }
    public void setGroups(String groups) {
        this.groups = groups;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
