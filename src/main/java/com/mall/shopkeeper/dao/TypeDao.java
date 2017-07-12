package com.mall.shopkeeper.dao;

import java.util.List;

import com.mall.shopkeeper.dao.model.Type;

public interface TypeDao {
    public List<Type> getTypes();
    
    public List<String> getTypeIds();
    
    public void create(Type type);
    
    public void remove(String id);

	public void update(Type type);
}
