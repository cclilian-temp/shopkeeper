package com.mall.shopkeeper.iface.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mall.shopkeeper.iface.TypeIface;
import com.mall.shopkeeper.iface.model.res.CommonRes;
@Component
public class TypeIfaceImpl extends IfaceCommons implements TypeIface {
    
    @Override
    public Response create(HttpServletRequest request, HttpServletResponse response, String name) {
        CommonRes res = new CommonRes();
        if(StringUtils.isBlank(name)){
            res.setCode(0);
            res.setMessage("未输入产品类型");
            return super.buildResponse(res);
        }
        
        if(isTypeExist(name)) {
            res.setCode(0);
            res.setMessage("该类型已存在");
            return super.buildResponse(res);
        }
        
        try{
            typeDao.create(name);
        }catch(Exception e){
            logger.error(e.toString(), e);
            res.setCode(0);
            res.setMessage("创建失败");
            return super.buildResponse(res);
        }
        res.setCode(1);
        res.setMessage("创建失败");
        return super.buildResponse(res);
    }

    @Override
    public Response list(HttpServletRequest request, HttpServletResponse response) {
        CommonRes res = new CommonRes();
        List<String> types = typeDao.getTypes();
        res.setCode(1);
        res.setMessage("success");
        res.setData(types);
        return super.buildResponse(res);
    }

    @Override
    public Response remove(HttpServletRequest request, HttpServletResponse response, String name) {
        CommonRes res = new CommonRes();
        if(!isTypeExist(name)) {
            res.setCode(0);
            res.setMessage("该类型不存在");
            return super.buildResponse(res);
        }
        try{
            typeDao.remove(name);
        }catch(Exception e){
            logger.error(e.toString(), e);
            res.setCode(0);
            res.setMessage("删除失败");
            return super.buildResponse(res);
        }
        res.setCode(1);
        res.setMessage("删除成功");
        return super.buildResponse(res);
    }

}
