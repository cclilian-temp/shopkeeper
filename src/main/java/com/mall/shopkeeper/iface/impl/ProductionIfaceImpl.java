package com.mall.shopkeeper.iface.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mall.shopkeeper.dao.model.Production;
import com.mall.shopkeeper.dao.model.mapping.GroupProduction;
import com.mall.shopkeeper.iface.ProductionIface;
@Component
public class ProductionIfaceImpl extends IfaceCommons implements ProductionIface {

    @Override
    public Response create(HttpServletRequest request, HttpServletResponse response) {
        Production production = super.parseRequest(request, Production.class);
        if(!checkProduction(production)){
            return super.buildResponseFailed("参数缺失，请校验参数");
        }
        
        String model = production.getModel();
        if(isProductionExist(model)) {
            return super.buildResponseFailed("该型号已存在");
        }
        
        //生成id
        String id = UUID.randomUUID().toString();
        production.setId(id);
        //如果没有上传特价折扣，默认特殊折扣为最低折扣
        if(production.getSpecialDiscount() <= 0){
            production.setSpecialDiscount(production.getLowestDiscount());
        }
        
        try{
            productionDao.create(production);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("创建产品失败");
        }
        return super.buildResponseOK("添加产品成功", null);
    }

    @Override
    public Response update(HttpServletRequest request, HttpServletResponse response) {
        Production production = super.parseRequest(request, Production.class);
        if(!checkProduction(production)){
            return super.buildResponseFailed("参数缺失，请校验参数");
        }
        if(!isProductionExist(production.getModel())){
            return super.buildResponseFailed("该型号不存在");
        }
        
        try{
            productionDao.update(production);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("更新产品失败");
        }
        return super.buildResponseOK("更新产品成功", null);
    }

    @Override
    public Response list(HttpServletRequest request, HttpServletResponse response, String model, String type) {
        List<Production> productions = productionDao.getProductions(model, type);
        return super.buildResponseOK("success", productions);
    }

    @Override
    public Response remove(HttpServletRequest request, HttpServletResponse response, String model) {
        if(!isProductionExist(model)){
            return super.buildResponseFailed("该型号不存在");
        }
        
        List<GroupProduction> groupProductions = groupProductionDao.getProductionsByProduction(model);
        if(groupProductions.size() > 0){
            return super.buildResponseFailed("该产品型号存在于套系中，请先调整相关套系");
        }
        
        try{
            productionDao.delete(model);
        }catch (Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("删除失败");
        }
        return super.buildResponseOK("删除单品成功", null);
    }
    
    private boolean checkProduction(Production production) {
        if((null == production) || StringUtils.isBlank(production.getModel()) || StringUtils.isBlank(production.getType())
                || (production.getPrice() <= 0) || (production.getLowestDiscount() <= 0)) {
            return false;
        }else {
            return true;
        }
    }
}
