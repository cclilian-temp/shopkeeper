package com.mall.shopkeeper.dao;

import java.util.List;

import com.mall.shopkeeper.dao.model.mapping.GroupProduction;

public interface GroupProductionDao {
    
    public List<GroupProduction> getProductionsByGroup(String groupModel);
    
    public List<GroupProduction> getProductionsByProduction(String productionModel);
    
    public void insert(List<GroupProduction> groupProductions);
    
    public void remove(String groupModel, String productionModel);
    
    public void remove(String groupModel);
}