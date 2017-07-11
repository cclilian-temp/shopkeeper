package com.mall.shopkeeper.dao;

import java.util.List;

public interface TypeDao {
    public List<String> getTypes();
    
    public void create(String name);
    
    public void remove(String name);
}
