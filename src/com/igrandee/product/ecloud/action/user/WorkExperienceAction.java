package com.igrandee.product.ecloud.action.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Url;
import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.core.person.model.Person;
import com.igrandee.core.person.model.Workexperience;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.service.users.WorkExperienceService;
import com.igrandee.product.ecloud.viewmodel.WorkExperienceVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/professionalinfo")
public class WorkExperienceAction extends AbstractIGActionSupport {
	
	@InjectParam
	Workexperience workexperience;
	
	@InjectParam
	Contactinfo contactinfo;
	
	@InjectParam
	WorkExperienceService workExperienceService;
	
	@InjectParam
	Person person;
	
	@InjectParam
	WorkExperienceVM workExperienceVM;
	
	@InjectParam
	OrganizationViewService organizationViewService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(WorkExperienceVM workExperienceVM){
		
		if(workExperienceVM!=null){
		workexperience.setOrganizationname(workExperienceVM.getOrganizationname());
		workexperience.setDesignation(workExperienceVM.getDesignation());
		workexperience.setNatureofwork(workExperienceVM.getAboutauthor());
		workexperience.setAward(workExperienceVM.getAwards());
		workexperience.setWorkexperiencestatus('A');
		workexperience.setPerson(person);
		
		person.setPersonid(Long.parseLong(getAttribute("personid")));
		
		int workexperienceid=workExperienceService.getWorkExperienceId(Long.parseLong(getAttribute("personid")));
		
		if(workexperienceid!=0){
			workexperience.setWorkexperienceid(workexperienceid);
		}
		
		Url url=new Url();
		url.setUrl(workExperienceVM.getWebsiteurl());
		url.setUrlstatus('A');
		
		Url url1=new Url();
		url1.setUrl(workExperienceVM.getFacebookurl());
		url1.setUrlstatus('A');
		
		Url url2=new Url();
		url2.setUrl(workExperienceVM.getLinkedinurl());
		url2.setUrlstatus('A');
		
		Url url3=new Url();
		url3.setUrl(workExperienceVM.getTwitterurl());
		url3.setUrlstatus('A');
		
		Set<Url> urls=new HashSet<Url>();
		urls.add(url);
		urls.add(url1);
		urls.add(url2);
		urls.add(url3);
		
		contactinfo.setUrls(urls);
		contactinfo.setContactinfostatus('A');
		workexperience.setContactinfo(contactinfo);
		
		Workexperience status=workExperienceService.saveOrUpdate(workexperience);
		
		if(status!=null){
			return Response.ok().entity(Status.CREATED).build();
		}		
		return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorkExperience(){		
	List<?> workexperience=workExperienceService.getWorkExperience(Integer.parseInt(getAttribute("personid")));
	if(workexperience!=null){
		return Response.ok().entity(workexperience).build();
	}	
	return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	@Path("/course/{personid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWeByPublicPersonId(@PathParam("personid")int personid){
		List<?> workexperience=workExperienceService.getWorkExperience(personid);
		if(workexperience!=null){
			return Response.ok().entity(workexperience).build();
		}	
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	
	
	@Path("/courselist/{orgpersonid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourselistperson(@PathParam("orgpersonid")int orgpersonid){
		String organizationid = "";
		String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userCheck.equalsIgnoreCase("anonymousUser"))	{
			List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
			Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
			organizationid = ""+map.get("organizationid");	
		} else {
			organizationid=""+getAttribute("organizationid"); 
		}
		List<?> coursedetails=workExperienceService.getCourseDetails(orgpersonid,Long.parseLong(organizationid));
		if(coursedetails!=null){
			return Response.ok().entity(coursedetails).build();
		}	
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
}
