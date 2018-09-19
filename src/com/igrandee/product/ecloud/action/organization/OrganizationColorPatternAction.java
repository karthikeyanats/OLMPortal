package com.igrandee.product.ecloud.action.organization;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.core.login.util.UserDetailsAdaptor;
import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.organization.OrganizationColorPatternService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.sun.jersey.api.core.InjectParam;


@Path("/colorpattern")
public class OrganizationColorPatternAction extends MasterActionSupport{

	@InjectParam
	OrganizationColorPatternService ocps;
	
	@InjectParam
	OrganizationViewService organizationViewService;

	private static final long serialVersionUID = 1L;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizationList() {
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			System.out.println("userCheck ---->>>> "+userCheck);
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> organizationList = ocps.getActualThemeColor(organizationid);
			if(organizationList.size()!=0) {
				return Response.status(Status.OK).entity(organizationList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/patterns")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatternsList() {
		List<?> organizationList = ocps.getAvailableThemeColor();
		try{
			if(organizationList!=null?organizationList.size()!=0:false) {
				return Response.status(Status.OK).entity(organizationList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{patternid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePatternList(@PathParam("patternid") String patternid) {
		try{
			String orgid = getAttribute("organizationid");
			int updateStatus = ocps.updateOrgThemeColor(patternid,orgid);
			if(updateStatus!=0){
				UserDetailsAdaptor userDetailsAdaptor = null;
				userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				userDetailsAdaptor.getUser().setProperty("colorpattern", patternid);
				return Response.status(Status.OK).entity(Status.OK).build();
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
