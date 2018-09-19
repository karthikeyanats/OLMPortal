package com.igrandee.product.ecloud.action.download;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.PresenterappDownloadhistory;
import com.igrandee.product.ecloud.model.Presenterappdetail;
import com.igrandee.product.ecloud.service.presenter.DownloadHistoryService;
import com.igrandee.product.ecloud.viewmodel.download.DownloadHistoryVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/downloadhistory")
public class DownloadHistoryAction extends AbstractIGActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1284086684304902973L;

	@InjectParam
	DownloadHistoryService downloadHistoryService;
	
	@InjectParam
    PresenterappDownloadhistory presenterappDownloadhistory;
	
	@InjectParam
	Presenterappdetail presenterappdetail;
	
	@InjectParam
	Orgperson orgperson;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDownloadHistory(@Context HttpServletRequest request,DownloadHistoryVM downloadHistoryVM){
		
		if(downloadHistoryVM!=null){
			presenterappdetail.setId(Integer.parseInt(downloadHistoryVM.getPresenterAppdetailsId()));
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			
			presenterappDownloadhistory.setIpaddress(request.getRemoteAddr());
			presenterappDownloadhistory.setHistorystatus('A');
			presenterappDownloadhistory.setDateofdownload(new Date());
			System.out.println(">>>>>>>>>>>"+orgperson);
			presenterappDownloadhistory.setOrgperson(orgperson);
			presenterappDownloadhistory.setPresenterappdetail(presenterappdetail);
			
			int status=downloadHistoryService.save(presenterappDownloadhistory);
			
			if(status>0){
				return Response.ok().entity(Status.CREATED).build();
			}
			return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();			
		}	
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDownloadHistory(){
		
		List<?> downloadHistoryList=downloadHistoryService.getDownloadHistory();
		if(downloadHistoryList!=null){
			return Response.ok().entity(downloadHistoryList).build();
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();		
	}
	
}
