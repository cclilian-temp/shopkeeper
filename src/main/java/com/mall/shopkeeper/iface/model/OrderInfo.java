package com.mall.shopkeeper.iface.model;

import java.util.Date;
import java.util.List;

import com.mall.shopkeeper.dao.model.Store;
import com.mall.shopkeeper.dao.model.mapping.GroupProduction;

public class OrderInfo {
    private String id;
    private Store store;
    private ClientInfo clientInfo;
    private PriceInfo priceInfo;
    private List<ProductionInfo> productionInfos;
    private List<GroupInfo> groupInfos;
    private Date ct;
    private Date ut;
    private String status;
    private String message;
    
    public static class ClientInfo{
        private String name;
        private String tel;
        private String address;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getTel() {
            return tel;
        }
        public void setTel(String tel) {
            this.tel = tel;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
    }
    
    public static class PriceInfo{
        private double priceTotal;
        private double priceNeeded;
        private double prepaid;
        public PriceInfo(){
            
        }
        public PriceInfo(double priceTotal, double priceNeeded, double prepaid){
            this.priceTotal = priceTotal;
        }
        public double getPriceTotal() {
            return priceTotal;
        }
        public void setPriceTotal(double priceTotal) {
            this.priceTotal = priceTotal;
        }
        public double getPrepaid() {
            return prepaid;
        }
        public void setPrepaid(double prepaid) {
            this.prepaid = prepaid;
        }
        public double getPriceNeeded() {
            return priceNeeded;
        }
        public void setPriceNeeded(double priceNeeded) {
            this.priceNeeded = priceNeeded;
        }
    }
    
    public static class ProductionInfo{
        private String model;
        private String type;
        private double discount;
        private double price;
        private int num;
        private Remark remark;
        private double subtotal;
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
        public double getDiscount() {
            return discount;
        }
        public void setDiscount(double discount) {
            this.discount = discount;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public double getSubtotal() {
            return subtotal;
        }
        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
        public Remark getRemark() {
            return remark;
        }
        public void setRemark(Remark remark) {
            this.remark = remark;
        }
    }
    
    public static class GroupInfo{
        private String model;
        private double price;
        private int num;
        private double subtotal;
        private Remark remark;
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
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public double getSubtotal() {
            return subtotal;
        }
        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
        public Remark getRemark() {
            return remark;
        }
        public void setRemark(Remark remark) {
            this.remark = remark;
        }
    }
    
    public static class Remark{
        private String clientMessage;
        private List<GroupProduction> groupProductions;
        public String getClientMessage() {
            return clientMessage;
        }
        public void setClientMessage(String clientMessage) {
            this.clientMessage = clientMessage;
        }
        public List<GroupProduction> getGroupProductions() {
            return groupProductions;
        }
        public void setGroupProductions(List<GroupProduction> groupProductions) {
            this.groupProductions = groupProductions;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public List<ProductionInfo> getProductionInfos() {
        return productionInfos;
    }

    public void setProductionInfos(List<ProductionInfo> productionInfos) {
        this.productionInfos = productionInfos;
    }

    public List<GroupInfo> getGroupInfos() {
        return groupInfos;
    }

    public void setGroupInfos(List<GroupInfo> groupInfos) {
        this.groupInfos = groupInfos;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static final class Status{
        
        public static final String CREATED = "已创建";
        public static final String PREPAID = "已付订金";
        public static final String PLACED = "工厂已订货";
        public static final String DELIVERED = "工厂已发货";
        public static final String RECEIVED = "已收货";
        
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
