package com.mall.shopkeeper.iface.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mall.shopkeeper.dao.model.Group;
import com.mall.shopkeeper.dao.model.Production;
import com.mall.shopkeeper.dao.model.mapping.GroupProduction;
import com.mall.shopkeeper.iface.GroupIface;
import com.mall.shopkeeper.iface.model.req.GroupProductionReq;
import com.mall.shopkeeper.iface.model.req.GroupProductionReq.ProductionReq;
import com.mall.shopkeeper.iface.model.res.GroupProductionRes;
import com.mall.shopkeeper.iface.model.res.GroupProductionRes.ProductionRes;
@Component
public class GroupIfaceImpl extends IfaceCommons implements GroupIface {

    @Override
    public Response create(HttpServletRequest request, HttpServletResponse response) {
        GroupProductionReq req = super.parseRequest(request, GroupProductionReq.class);
        if(!checkGroup(req)){
            return super.buildResponseFailed("参数缺失，请校验参数");
        }
        String id = UUID.randomUUID().toString();
        String groupModel = req.getModel();
        double price = req.getPrice();
        if(isGroupExist(groupModel)){
            return super.buildResponseFailed("该套系型号已存在");
        }
        Group group = new Group(id, groupModel, price);
        try{
            List<ProductionReq> productionReqs = req.getProductions();
            if((null != productionReqs) && productionReqs.size() > 0) {
                //如果套系中商品的特价折扣低于商品中特价折扣即覆盖特价折扣
                this.updateProductions(productionReqs);
                List<GroupProduction> groupProductions = this.buildGroupProductions(groupModel, productionReqs);
                super.groupProductionDao.insert(groupProductions);
            }
            super.groupDao.create(group);
        }catch(Exception e) {
            logger.error(e.toString(), e);
            return super.buildResponseFailed("创建套系失败");
        }
        return super.buildResponseOK("添加套系成功", null);
    }

    @Override
    public Response update(HttpServletRequest request, HttpServletResponse response) {
        GroupProductionReq req = super.parseRequest(request, GroupProductionReq.class);
        if(!checkGroup(req)){
            return super.buildResponseFailed("参数缺失，请校验参数");
        }
        String groupModel = req.getModel();
        if(!isGroupExist(groupModel)){
            return super.buildResponseFailed("该套系型号不存在");
        }
        Group group = new Group();
        group.setModel(groupModel);
        group.setPrice(req.getPrice());
        
        try{
            groupDao.update(group);
            List<ProductionReq> productionReqs = req.getProductions();
            if((null != productionReqs) && productionReqs.size() > 0) {
                List<GroupProduction> groupProductions = this.buildGroupProductions(groupModel, productionReqs);
                
                groupProductionDao.remove(groupModel);
                super.groupProductionDao.insert(groupProductions);
            }
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("更新套系失败");
        }
        return super.buildResponseOK("更新套系成功", null);
    }

    @Override
    public Response getGroupProduction(HttpServletRequest request, HttpServletResponse response, String model) {
        Group group = groupDao.getGroups(model).get(0);
        List<GroupProduction> groupProductions = groupProductionDao.getProductionsByGroup(model);
        GroupProductionRes groupProductionRes = this.buildGroupProductionRes(group, groupProductions);
        return super.buildResponseOK("success", groupProductionRes);
    }
    
    @Override
    public Response list(HttpServletRequest request, HttpServletResponse response, String model) {
        List<Group> groups = groupDao.getGroups(model);
        return super.buildResponseOK("success", groups);
    }

    @Override
    public Response remove(HttpServletRequest request, HttpServletResponse response, String model) {
        if(!isGroupExist(model)){
            return super.buildResponseFailed("该套系型号不存在");
        }
        try{
            groupDao.remove(model);
            groupProductionDao.remove(model);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("删除套系失败");
        }
        return super.buildResponseOK("删除套系成功", null);
    }
    
    private boolean checkGroup(GroupProductionReq req){
        if((null == req) || (StringUtils.isBlank(req.getModel()) || (req.getPrice() <= 0))) {
            return false;
        } else {
            return true;
        }
    }
    
    private List<GroupProduction> buildGroupProductions(String groupModel, List<ProductionReq> productionReqs){
        List<GroupProduction> groupProductions = new ArrayList<GroupProduction>();
        for(ProductionReq productionReq : productionReqs) {
            String id = UUID.randomUUID().toString();
            GroupProduction groupProduction = new GroupProduction();
            String productionModel = productionReq.getModel();
            int num = productionReq.getNum();
            groupProduction.setId(id);
            groupProduction.setGroupModel(groupModel);
            groupProduction.setNum(num);
            groupProduction.setProductionModel(productionModel);
            
            groupProductions.add(groupProduction);
        }
        return groupProductions;
    }
    
    private void updateProductions(List<ProductionReq> productionReqs) throws Exception {
        for(ProductionReq productionReq : productionReqs){
            String model = productionReq.getModel();
            double reqSpecialDiscount = productionReq.getSpecialDiscount();
            if(null == productionDao.getProductions(model, null) || productionDao.getProductions(model, null).size() <= 0) {
                throw new Exception(String.format("no production found like %s", model));
            }
            Production production = productionDao.getProductions(model, null).get(0);
            double specialDiscount = production.getSpecialDiscount();
            production.setSpecialDiscount(specialDiscount > reqSpecialDiscount ? reqSpecialDiscount : specialDiscount);
            productionDao.update(production);
        }
    }
    
    public GroupProductionRes buildGroupProductionRes(Group group, List<GroupProduction> groupProductions){
        GroupProductionRes res = new GroupProductionRes();
        res.setModel(group.getModel());
        res.setPrice(group.getPrice());
        List<ProductionRes> productionReses = new ArrayList<ProductionRes>();
        for(GroupProduction groupProduction : groupProductions){
            ProductionRes productionRes = new ProductionRes();
            String model = groupProduction.getProductionModel();
            
            productionRes.setModel(model);
            productionRes.setNum(groupProduction.getNum());
            
            Production production = productionDao.getProductions(model, null).get(0);
            productionRes.setSpecialDiscount(production.getSpecialDiscount());
            productionRes.setPrice(production.getPrice());
            productionReses.add(productionRes);
        }
        res.setProductions(productionReses);
        return res;
    }
}
