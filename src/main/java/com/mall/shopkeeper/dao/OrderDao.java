package com.mall.shopkeeper.dao;

import java.util.Date;
import java.util.List;

import com.mall.shopkeeper.dao.model.Order;

public interface OrderDao {
    
    public void create(Order order);
    
    public List<Order> getOrders(String id, String storeName, String clientName, Date frDate, Date toDate);
    
    public void update(Order order);
}
