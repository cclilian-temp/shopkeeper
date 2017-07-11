package com.mall.shopkeeper.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/type")
@Produces(MediaType.APPLICATION_JSON)
public interface TypeIface {
    
    @Path("/create")
    @GET
    public Response create(@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @QueryParam("name") String name);
    
    @Path("/list")
    @GET
    public Response list(@Context HttpServletRequest request,
            @Context HttpServletResponse response);
    
    @Path("/remove")
    @GET
    public Response remove(@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @QueryParam("name") String name);
}
