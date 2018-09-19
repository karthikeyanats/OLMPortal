package com.igrandee.product.ecloud.action.pages.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.service.Pages.admin.CloudQueryHelperService;
import com.sun.jersey.api.core.InjectParam;

@Path("/cqhs")
public class CloudQueryHelperAction {

	@InjectParam
	CloudQueryHelperService cqhs;

	@GET
	@Path("/select/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectQueryList(@PathParam("query") String query){
		try{
			List<?> descList=cqhs.getSelectQueryResultList(query);
			if(descList.size()>0){
				return Response.status(Status.OK).entity(descList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/update/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateQueryResult(@PathParam("query") String query){
		try{
			List<?> descList=cqhs.getSelectQueryResultList(query);
			if(descList.size()>0){
				return Response.status(Status.OK).entity(descList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/properties/{propertytype}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response applicationPropertyValues(@PathParam("propertytype") String propertytype){
		try{
			ResourceBundle resourcebundle = ResourceBundle.getBundle(propertytype);
			Enumeration<String> en = resourcebundle.getKeys();
			ArrayList al = new ArrayList();
			LinkedHashMap  map  = new LinkedHashMap(); ;
			map.put("propertytype", propertytype);
			for (String key : Collections.list(en)) {
				map.put(key, resourcebundle.getString(key));				
			}
			al.add(map);
			return Response.status(Status.OK).entity(al).build();
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
