package com.mall.shopkeeper.dao.model;

import java.util.Date;

public class Type {
	private String id;
    private String name;
    private Date ct;
    private Date ut;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
