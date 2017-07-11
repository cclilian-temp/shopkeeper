package com.mall.shopkeeper.iface.model.req;

import java.util.List;

public class GroupProductionReq {
    private String model;
    private double price;
    private List<ProductionReq> productions;
    
    public static class ProductionReq{
        private String model;
        private int num;
        private double specialDiscount;
        public String getModel() {
            return model;
        }
        public void setModel(String model) {
            this.model = model;
        }
        public int getNum() {
            return num;
        }
        public void setNum(int num) {
            this.num = num;
        }
        public double getSpecialDiscount() {
            return specialDiscount;
        }
        public void setSpecialDiscount(double specialDiscount) {
            this.specialDiscount = specialDiscount;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ProductionReq> getProductions() {
        return productions;
    }

    public void setProductions(List<ProductionReq> productions) {
        this.productions = productions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
