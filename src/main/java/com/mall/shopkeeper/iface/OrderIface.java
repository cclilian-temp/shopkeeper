package com.mall.shopkeeper.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public interface OrderIface {
    
    @Path("/submit")
    @POST
    public Response submit(@Context HttpServletRequest request,
            @Context HttpServletResponse response);
    
    @Path("/update")
    @POST
    public Response update(@Context HttpServletRequest request,
            @Context HttpServletResponse response);
    
    @Path("/remove")
    @GET
    public Response remove(@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @QueryParam("/id") String id);
    
    @Path("/confirm")
    @POST
    public Response confirm(@Context HttpServletRequest request,
            @Context HttpServletResponse response);
    
    @Path("/list")
    @GET
    public Response list(@Context HttpServletRequest request,
            @Context HttpServletResponse response);
    
    @Path("/orderInfo")
    @GET
    public Response getOrderInfo(@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @QueryParam("id") String id);
}
