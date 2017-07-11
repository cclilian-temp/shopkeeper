package com.mall.shopkeeper.dao;

import java.util.List;

import com.mall.shopkeeper.dao.model.Store;

public interface StoreDao {
    public void create(Store store);
    
    public void update(Store store);
    
    public List<Store> getStores(String id, String name, String tel);
    
    public void remove(String id);
    
    public List<String> getStoreIds();
}
