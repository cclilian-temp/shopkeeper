package com.mall.shopkeeper.dao;

import java.util.List;

import com.mall.shopkeeper.dao.model.Group;

public interface GroupDao {
    
    public void create(Group group);
    
    public void update(Group group);
    
    public List<Group> getGroups(String model);
    
    public void remove(String model);
    
    public List<String> getModels();
    
    public List<Group> getGroups(List<String> models);
}
