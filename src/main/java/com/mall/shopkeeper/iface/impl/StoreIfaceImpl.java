package com.mall.shopkeeper.iface.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.mall.shopkeeper.dao.model.Store;
import com.mall.shopkeeper.iface.StoreIface;
@Component
public class StoreIfaceImpl extends IfaceCommons implements StoreIface{

    @Override
    public Response create(HttpServletRequest request, HttpServletResponse response) {
        Store store = super.parseRequest(request, Store.class);
        String id = UUID.randomUUID().toString();
        store.setId(id);
        try{
            super.storeDao.create(store);
        }catch(Exception e) {
            logger.error(e.toString(), e);
            return super.buildResponseFailed("添加店铺失败");
        }
        return super.buildResponseOK("添加店铺成功", null);
    }

    @Override
    public Response update(HttpServletRequest request, HttpServletResponse response) {
        Store store = super.parseRequest(request, Store.class);
        String id = store.getId();
        if(!super.isStoreExist(id)){
            return super.buildResponseFailed("该店铺编号不存在");
        }
        try{
            super.storeDao.update(store);
        } catch(Exception e) {
            logger.error(e.toString(), e);
            return super.buildResponseFailed("更新店铺失败");
        }
        return super.buildResponseOK("更新店铺成功", null);
    }

    @Override
    public Response list(HttpServletRequest request, HttpServletResponse response, String id, String name, String tel) {
        List<Store> stores = new ArrayList<Store>();
        try{
            stores = storeDao.getStores(id, name, tel);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("查询店铺失败");
        }
        return super.buildResponseOK("success", stores);
    }

    @Override
    public Response remove(HttpServletRequest request, HttpServletResponse response, String id) {
        if(!super.isStoreExist(id)){
            return super.buildResponseFailed("该店铺编号不存在");
        }
        try{
            storeDao.remove(id);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("删除店铺失败");
        }
        return super.buildResponseOK("删除店铺成功",  null);
    }

}
