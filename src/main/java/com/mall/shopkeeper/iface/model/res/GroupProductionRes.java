package com.mall.shopkeeper.iface.model.res;

import java.util.List;

public class GroupProductionRes {
    private String model;
    private double price;
    private List<ProductionRes> productions;
    
    public static class ProductionRes{
        private String model;
        private int num;
        private double price;
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
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
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

    public List<ProductionRes> getProductions() {
        return productions;
    }

    public void setProductions(List<ProductionRes> productions) {
        this.productions = productions;
    }
}
