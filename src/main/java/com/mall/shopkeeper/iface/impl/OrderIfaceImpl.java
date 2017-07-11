package com.mall.shopkeeper.iface.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mall.shopkeeper.dao.model.Order;
import com.mall.shopkeeper.dao.model.Store;
import com.mall.shopkeeper.dao.model.mapping.GroupProduction;
import com.mall.shopkeeper.iface.OrderIface;
import com.mall.shopkeeper.iface.model.OrderInfo;
import com.mall.shopkeeper.iface.model.OrderInfo.ClientInfo;
import com.mall.shopkeeper.iface.model.OrderInfo.GroupInfo;
import com.mall.shopkeeper.iface.model.OrderInfo.PriceInfo;
import com.mall.shopkeeper.iface.model.OrderInfo.ProductionInfo;
import com.mall.shopkeeper.iface.model.OrderInfo.Remark;
import com.mall.shopkeeper.iface.model.OrderInfo.Status;
@Component
public class OrderIfaceImpl extends IfaceCommons implements OrderIface {

    @Override
    public Response submit(HttpServletRequest request, HttpServletResponse response) {
        OrderInfo orderInfo = super.parseRequest(request, OrderInfo.class);
        //确认参数是否合格
        List<GroupInfo> groupInfos = orderInfo.getGroupInfos();
        List<ProductionInfo> productionInfos = orderInfo.getProductionInfos();
        
        if(this.validateOderInfo(groupInfos, productionInfos)){
            return super.buildResponseFailed("订单中商品和套系不能数量都为零");
        }
        
        double priceTotal = 0;
        double priceNeeded = 0;
        
        StringBuilder sb = new StringBuilder();
        if(null != productionInfos && productionInfos.size() > 0) {
            for(ProductionInfo productionInfo : productionInfos) {
                double discount = productionInfo.getDiscount();
                int num = productionInfo.getNum();
                double price = productionInfo.getPrice();
                
                double subtotal = num * price * discount/10;
                if(sb.length() <= 0){
                    sb.append(num + "*" + price + "*" + discount/10);
                }else{
                    sb.append("+");
                    sb.append(num + "*" + price + "*" + discount/10);
                }
                sb.append(num + "*" + price + "*" + discount/10);
                productionInfo.setSubtotal(subtotal);
                priceNeeded += subtotal;
                priceTotal += num * price;
            }
        }
        
        if(null != groupInfos && groupInfos.size() > 0){    
            for(GroupInfo groupInfo : groupInfos) {
                String groupModel = groupInfo.getModel();
                int num = groupInfo.getNum();
                double price = groupInfo.getPrice();
                
                double subtotal = num * price;
                if(sb.length() <= 0){
                    sb.append(num + "*" + price);
                }else{
                    sb.append("+");
                    sb.append(num + "*" + price);
                }
                groupInfo.setSubtotal(subtotal);
                priceNeeded += subtotal;
                priceTotal += subtotal;
                
                //将对应单品信息添加到备注中
                List<GroupProduction> groupProductions = groupProductionDao.getProductionsByGroup(groupModel);
                Remark remark = groupInfo.getRemark();
                remark.setGroupProductions(groupProductions);
                groupInfo.setRemark(remark);
            }
        }
        
        
        Order order = new Order();
        
        //生成订单编号
        String id = UUID.randomUUID().toString();
        order.setId(id);
        
        //设置店铺
        //设置客户
        
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setPriceTotal(priceTotal);
        priceInfo.setPriceNeeded(priceNeeded);
        
        order.setProductions(JSON.toJSONString(productionInfos));
        order.setGroups(JSON.toJSONString(groupInfos));
        order.setPrice(JSON.toJSONString(priceInfo));
        
        
        order.setStatus(Status.CREATED);
        
        order.setMessage(sb.toString());
        
        try{
            orderDao.create(order);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("创建订单失败");
        }
        
        return super.buildResponseOK("创建订单成功", id);
    }

    @Override
    public Response update(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response remove(HttpServletRequest request, HttpServletResponse response, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response confirm(HttpServletRequest request, HttpServletResponse response) {
        OrderInfo orderInfo = super.parseRequest(request, OrderInfo.class);
        String id = orderInfo.getId();
        List<Order> orders = orderDao.getOrders(id, null, null, null, null);
        if(null == orders || orders.size() <= 0){
            return super.buildResponseFailed("该订单编号不存在");
        }
        Order order = orders.get(0);
        order.setClient(JSON.toJSONString(orderInfo.getClientInfo()));
        order.setClientName(orderInfo.getClientInfo().getName());
        
        PriceInfo priceInfo = JSON.parseObject(order.getPrice(), PriceInfo.class);
        priceInfo.setPrepaid(orderInfo.getPriceInfo().getPrepaid());
        order.setPrice(JSON.toJSONString(priceInfo));
        
        order.setMessage(orderInfo.getMessage());
        
        try{
            orderDao.update(order);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("确认订单失败");
        }
        
        return super.buildResponseOK("确认订单成功", id);
    }

    @Override
    public Response list(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Response getOrderInfo(HttpServletRequest request, HttpServletResponse response, String id) {
        List<Order> orders = orderDao.getOrders(id, null, null, null, null);
        Order order = orders.get(0);
        OrderInfo orderInfo = this.buildOrderInfo(order);
        return super.buildResponseOK("success", orderInfo);
    }
    
    private boolean validateOderInfo(List<GroupInfo> groupInfos, List<ProductionInfo> productionInfos){
        return (groupInfos.size() <= 0 && productionInfos.size() <= 0);
    }    
    
    private OrderInfo buildOrderInfo(Order order){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(order.getId());
        orderInfo.setStore(JSON.parseObject(order.getStore(), Store.class));
        orderInfo.setClientInfo(JSON.parseObject(order.getClient(), ClientInfo.class));
        orderInfo.setPriceInfo(JSON.parseObject(order.getPrice(), PriceInfo.class));
        orderInfo.setProductionInfos(JSON.parseArray(order.getProductions(), ProductionInfo.class));
        orderInfo.setGroupInfos(JSON.parseArray(order.getGroups(), GroupInfo.class));
        orderInfo.setCt(order.getCt());
        orderInfo.setUt(order.getUt());
        orderInfo.setMessage(order.getMessage());
        orderInfo.setStatus(order.getStatus());
        return orderInfo;
    }
    
    /*private String validateProductionPrice(List<ProductionInfo> productionInfos, Map<String, Production> productionMap ){
        for(ProductionInfo productionInfo : productionInfos) {
            String productionModel = productionInfo.getModel();
            Production production = productionMap.get(productionModel);
            if(production.getSpecialDiscount() > productionInfo.getDiscount()){
                return productionModel;
            }
        }
        return null;
    }*/
    
    /*private List<String> getProductionModels(List<ProductionInfo> productionInfos){
        List<String> models = new ArrayList<String>();
        for(ProductionInfo productionInfo : productionInfos){
            models.add(productionInfo.getModel());
        }
        return models;
    }*/
    
    /*private List<String> getGroupModels(List<GroupInfo> groupInfos) {
        List<String> models = new ArrayList<String>();
        for(GroupInfo groupInfo : groupInfos){
            models.add(groupInfo.getModel());
        }
        return models;
    }*/
    
    //获取单品总价
    /*private PriceInfo getProductionPrice(List<ProductionInfo> productionInfos){
        PriceInfo priceInfo = new PriceInfo();
        double priceTotal = 0;
        double priceNeeded = 0;
        for(ProductionInfo productionInfo : productionInfos){
            int num = productionInfo.getNum();
            double discount = productionInfo.getDiscount();
            double price = productionInfo.getPrice();
            
            priceTotal += num * price;
            priceNeeded += num * price * discount/10;
        }
        priceInfo.setPriceTotal(priceTotal);
        priceInfo.setPriceNeeded(priceNeeded);
        return priceInfo;
    }*/
    
    /*private PriceInfo getPrice(List<GroupInfo> groupInfos, Map<String, Group> groupMap) {
        PriceInfo priceInfo = new PriceInfo();
        double priceTotal = 0;
        double priceNeeded = 0;
        for(GroupInfo groupInfo : groupInfos){
            String model = groupInfo.getModel();
            int num = groupInfo.getNum();
            
            Group group = groupMap.get(model);
            double price = group.getPrice();
            priceTotal += price*num;
            priceNeeded += price*num;
        }
        priceInfo.setPriceTotal(priceTotal);
        priceInfo.setPriceNeeded(priceNeeded);
        return priceInfo;
    }*/
}
