package com.igrandee.product.ecloud.action.social;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/suborg")
public class OrganizationSelection {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectOrganization(@QueryParam("id")int id){
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		requestAttributes.getRequest().getSession().setAttribute("organizationid", id);
		return Response.ok().entity(Status.OK).build();
	}

	
	
}
