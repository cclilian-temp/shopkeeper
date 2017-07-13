package com.mall.shopkeeper.iface.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.mall.shopkeeper.dao.model.Type;
import com.mall.shopkeeper.iface.TypeIface;
@Component
public class TypeIfaceImpl extends IfaceCommons implements TypeIface {
    
    @Override
    public Response create(HttpServletRequest request, HttpServletResponse response) {
    	Type type = super.parseRequest(request, Type.class);
    	if(isTypeNameExist(type.getName())) {
    		return super.buildResponseFailed("该类型已存在");
    	}
        String id = UUID.randomUUID().toString();
        type.setId(id);
        try{
            super.typeDao.create(type);
        }catch(Exception e) {
            logger.error(e.toString(), e);
            return super.buildResponseFailed("添加类型失败");
        }
        return super.buildResponseOK("添加类型成功", null);
    }

    @Override
    public Response list(HttpServletRequest request, HttpServletResponse response, String name) {
        List<Type> types = new ArrayList<Type>();
        try{
        	types = typeDao.getTypes(name);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("查询店铺失败");
        }
        return super.buildResponseOK("success", types);
    }

    @Override
    public Response remove(HttpServletRequest request, HttpServletResponse response, String id) {
    	if(!super.isTypeExist(id)){
            return super.buildResponseFailed("该类型编号不存在");
        }
        try{
            typeDao.remove(id);
        }catch(Exception e){
            logger.error(e.toString(), e);
            return super.buildResponseFailed("删除类型失败");
        }
        return super.buildResponseOK("删除类型成功",  null);
    }

    @Override
	public Response update(HttpServletRequest request, HttpServletResponse response) {
    	Type type = super.parseRequest(request, Type.class);
        String id = type.getId();
        if(!super.isTypeExist(id)){
            return super.buildResponseFailed("该类型编号不存在");
        }
        if(isTypeNameExist(type.getName())) {
    		return super.buildResponseFailed("该类型已存在");
    	}
        try{
            super.typeDao.update(type);
        } catch(Exception e) {
            logger.error(e.toString(), e);
            return super.buildResponseFailed("更新店铺失败");
        }
        return super.buildResponseOK("更新店铺成功", null);
	}
    
    private boolean isTypeNameExist(String name) {
    	List<String> types = typeDao.getTypeNames();
		return types.contains(name);
    }
}
