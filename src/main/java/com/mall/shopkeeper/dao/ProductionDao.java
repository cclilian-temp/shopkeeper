package com.mall.shopkeeper.dao;

import java.util.List;

import com.mall.shopkeeper.dao.model.Production;

public interface ProductionDao {
    public List<Production> getProductions(String model, String type);
    
    public void create(Production productio) throws Exception;
    
    public void update(Production production) throws Exception;
    
    public void delete(String model) throws Exception;
    
    public List<String> getModels();
    
    public List<Production> getProductions(List<String> models);
    
}
