package com.mall.shopkeeper.iface;

import javax.ws.rs.core.Response;

public interface UserIface {
    
    public Response regist();
    
    public Response checkLogin();
    
    public Response approve();
}
