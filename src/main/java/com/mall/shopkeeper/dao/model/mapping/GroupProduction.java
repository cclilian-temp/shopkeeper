package com.mall.shopkeeper.dao.model.mapping;

public class GroupProduction {
    private String id;
    private String groupModel;
    private String productionModel;
    private int num;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGroupModel() {
        return groupModel;
    }
    public void setGroupModel(String groupModel) {
        this.groupModel = groupModel;
    }
    public String getProductionModel() {
        return productionModel;
    }
    public void setProductionModel(String productionModel) {
        this.productionModel = productionModel;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
}
