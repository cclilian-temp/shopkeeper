package com.mall.shopkeeper.iface.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mall.shopkeeper.dao.GroupDao;
import com.mall.shopkeeper.dao.GroupProductionDao;
import com.mall.shopkeeper.dao.OrderDao;
import com.mall.shopkeeper.dao.ProductionDao;
import com.mall.shopkeeper.dao.StoreDao;
import com.mall.shopkeeper.dao.TypeDao;
import com.mall.shopkeeper.dao.model.Group;
import com.mall.shopkeeper.dao.model.Production;
import com.mall.shopkeeper.exceptions.ExceptionWrapper;
import com.mall.shopkeeper.exceptions.JsonProcessException;
import com.mall.shopkeeper.iface.model.res.CommonRes;
import com.mall.shopkeeper.utils.Utils;
@Component
public class IfaceCommons {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    protected ProductionDao productionDao;
    
    @Resource
    protected TypeDao typeDao;
    
    @Resource
    protected GroupDao groupDao;
    
    @Resource
    protected GroupProductionDao groupProductionDao;
    
    @Resource
    protected StoreDao storeDao;
    
    @Resource
    protected OrderDao orderDao;
    
    protected boolean isGroupExist(String model){
        List<String> models = groupDao.getModels();
        return models.contains(model);
    }
    
    protected boolean isProductionExist(String model) {
        List<String> models = productionDao.getModels();
        return models.contains(model);
    }
    
    protected  boolean isTypeExist(String type) {
        List<String> types = typeDao.getTypes();
        return types.contains(type);
    }
    
    protected boolean isStoreExist(String id) {
        List<String> storeIds = storeDao.getStoreIds();
        return storeIds.contains(id);
    }
    
    protected Map<String, Production> productions2Map(List<Production> productions){
        Map<String, Production> productionMap = new HashMap<String, Production>();
        for(Production production : productions){
            String model = production.getModel();
            productionMap.put(model, production);
        }
        return productionMap;
    }
    
    protected Map<String, Group> groups2Map(List<Group> groups){
        Map<String, Group> groupMap = new HashMap<String, Group>();
        for(Group group : groups){
            String model = group.getModel();
            groupMap.put(model, group);
        }
        return groupMap;
    }
    
    protected <K, V> Map<K, V> parseConfig2Map(String config, Class<K> keyClass, Class<V> valueClass) {
        try {
            return JSON.parseObject(config, new TypeReference<Map<K, V>>() {
            });
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unrecognizable config for '%s'.", config), e);
        }
    }

    protected <T> List<T> parseConfig2List(String config, Class<T> tClass) {
        try {
            return JSON.parseArray(config, tClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unrecognizable config for '%s'.", config), e);
        }
    }
    
    protected Response buildResponse(Object obj) {
        try {
            String retJson = Utils.obj2Json(obj);
            return Response.ok(retJson).type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            throw new ExceptionWrapper(e);
        }
    }
    
    protected Response buildResponseOK(String message, Object obj) {
        CommonRes res = new CommonRes();
        res.setCode(1);
        res.setMessage(message);
        res.setData(obj);
        return this.buildResponse(res);
    }
    
    protected Response buildResponseFailed(String message) {
        CommonRes res = new CommonRes();
        res.setCode(1);
        res.setMessage(message);
        return this.buildResponse(res);
    }
    
    protected <T> T parseRequest(HttpServletRequest request, Class<T> clazz) {
        try {
            byte[] data = IOUtils.toByteArray(request.getInputStream());
            String reqJson = new String(data, "UTF-8");
            return Utils.json2Obj(reqJson, clazz);
        } catch (JsonProcessException e) {
            throw new ExceptionWrapper(e);
        } catch (IOException e) {
            throw new ExceptionWrapper(e);
        }
    }
}
