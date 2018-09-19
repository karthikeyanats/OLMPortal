package com.igrandee.product.ecloud.action.download;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Presenterapp;
import com.igrandee.product.ecloud.model.Presenterappdetail;
import com.igrandee.product.ecloud.model.Presentertype;
import com.igrandee.product.ecloud.service.presenter.DownloadService;
import com.igrandee.product.ecloud.service.presenter.PresenterAppDetailService;
import com.igrandee.product.ecloud.service.presenter.PresentertypeService;
import com.igrandee.product.ecloud.viewmodel.download.DownloadDetialsViewModel;
import com.sun.jersey.api.core.InjectParam;

@Path("/presenterapp")
public class DownloadAction<E> extends AbstractIGActionSupport{
	private static final long serialVersionUID = 1L;
	private final Logger downloadActionLogger = Logger.getLogger(DownloadAction.class);
	
	@InjectParam
	DownloadService downloadService;	
	
	@InjectParam
	PresenterAppDetailService presenterappdetailservice;

	@InjectParam
	DownloadService presenterservice;
	
	@InjectParam
	PresentertypeService presentertypeservice;	
	
	@GET
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPresenterapps(){   
		List<?> getpresenterlist = null;  
		try{  
			getpresenterlist =  downloadService.getPresenterapps();	 
			if(getpresenterlist.size() > 0 & ! getpresenterlist.isEmpty()){
				return Response.status(Status.OK).entity(getpresenterlist).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getPresenterapps() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 
	}

	@GET
	@Path("/presentertype")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPresentertype(){   
		List<?> getpresentertypelist = null;  
		try{  
			getpresentertypelist =  downloadService.getPresentertype();	 
			if(getpresentertypelist.size() > 0 & ! getpresentertypelist.isEmpty()){
				return Response.status(Status.OK).entity(getpresentertypelist).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getPresentertype() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}

	@GET
	@Path("/editpresenterapp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Editdetails(@QueryParam("presenterid") int presenterid){
		List<?> getpresenterdetails = null;
		try{  
			getpresenterdetails =  downloadService.getEditpresenter(presenterid);	 
			if(getpresenterdetails.size() > 0 & ! getpresenterdetails.isEmpty()){
				return Response.status(Status.OK).entity(getpresenterdetails).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in Editdetails() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
	@GET
	@Path("/presenterdetails")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPresenterdetails(){   
		List<?> getpresenterdetailslist = null;  
		try{  
			getpresenterdetailslist =  downloadService.getPresenterdetails();	 
			if(getpresenterdetailslist.size() > 0 & ! getpresenterdetailslist.isEmpty()){
				return Response.status(Status.OK).entity(getpresenterdetailslist).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getpresenterdetailslist() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}

	@POST
	@Path("/presenterappdetails")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getpresenterappdetails(DownloadDetialsViewModel downloaddetailsviewmodel){   
		try{  
			String[] array1=downloaddetailsviewmodel.getWorkingduration();
			HashSet<Presenterappdetail> presentAppSet = new HashSet<Presenterappdetail>();	 
			for(int i=0; i<array1.length;i++){
				Presentertype presentertype =new Presentertype();
				Presenterappdetail presenterappdetial = new Presenterappdetail();		
				presenterappdetial.setWorkingduration(Integer.parseInt(array1[i]));
				presenterappdetial.setLogincount(Integer.parseInt(downloaddetailsviewmodel.getLogincount()[i])); 

				presenterappdetial.setPrice(downloaddetailsviewmodel.getPrice()[i]); 			
				presenterappdetial.setDateofcreation(new Date());
				presenterappdetial.setDetailstatus('A');
				presentertype.setId(Integer.parseInt(downloaddetailsviewmodel.getPresentertypeid()[i]));	
				presenterappdetial.setPresentertype(presentertype);
				presentAppSet.add(presenterappdetial);
			}	 		 
			Presenterapp presenterapp = new Presenterapp();		
			presenterapp.setApplicationname(downloaddetailsviewmodel.getApplicationname());
			presenterapp.setApppath(downloaddetailsviewmodel.getPath());
			presenterapp.setHelpurlpath(downloaddetailsviewmodel.getHelpurl());
			presenterapp.setPresenterstatus('A');
			Orgperson orgperson=new Orgperson();
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			presenterapp.setOrgperson(orgperson);
			presenterapp.setPresenterappdetails(presentAppSet);		
			int presenterdetail =  presenterservice.save(presenterapp);
			if(presenterdetail > 0){			
				return Response.status(Status.OK).entity(Status.OK).build();
			}
			return null;
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getpresenterappdetails() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}

	@POST
	@Path("/updatepresenter")
	@Produces( MediaType.APPLICATION_JSON)
	public Response updatePresenterValues(DownloadDetialsViewModel downloaddetailsviewmodel){   
		try{
			Presenterapp presenterapp=new Presenterapp();
			presenterapp.setApplicationname(downloaddetailsviewmodel.getApplicationname());
			presenterapp.setHelpurlpath(downloaddetailsviewmodel.getHelpurl());
			presenterapp.setApppath(downloaddetailsviewmodel.getPath());
			presenterapp.setPresenterstatus('A');
			presenterapp.setPresenterid(Integer.parseInt(downloaddetailsviewmodel.getPresenterid()));			
			Orgperson orgperson=new Orgperson();
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			presenterapp.setOrgperson(orgperson);
			presenterservice.update(presenterapp);
			List<?> presenterTypeList = downloaddetailsviewmodel.getTypeobj();
			for (int i = 0; i < presenterTypeList.size(); i++) {
				Map map = (Map) presenterTypeList.get(i);
				if(StringUtils.equalsIgnoreCase(""+map.get("updatestatus"),"save")){
					Presenterappdetail presenterappdetail=new Presenterappdetail();
					Presenterapp presenterapp1=new Presenterapp();
					presenterapp1.setPresenterid(Integer.parseInt(downloaddetailsviewmodel.getPresenterid()));
					presenterappdetail.setPresenterapp(presenterapp1);

					Presentertype presentertype1=new Presentertype();
					presentertype1.setId(Integer.parseInt(""+map.get("typeid")));
					presenterappdetail.setPresentertype(presentertype1);

					String dstatus=""+map.get("detailstatus");
					presenterappdetail.setDetailstatus(dstatus.charAt(0));
					presenterappdetail.setPrice(""+map.get("price"));				
					presenterappdetail.setLogincount(Integer.parseInt(""+map.get("logincount")));
					presenterappdetail.setWorkingduration(Integer.parseInt(""+map.get("workduration")));
					presenterappdetail.setDateofcreation(new Date());
					presenterappdetailservice.save(presenterappdetail);
				}
				else if(StringUtils.equalsIgnoreCase(""+map.get("updatestatus"),"update")){
					Presenterappdetail presenterappdetail=new Presenterappdetail();
					Presenterapp presenterapp1=new Presenterapp();
					presenterapp1.setPresenterid(Integer.parseInt(downloaddetailsviewmodel.getPresenterid()));
					presenterappdetail.setPresenterapp(presenterapp1);

					Presentertype presentertype1=new Presentertype();
					presentertype1.setId(Integer.parseInt(""+map.get("typeid")));
					presenterappdetail.setPresentertype(presentertype1);

					presenterappdetail.setId(Integer.parseInt(""+map.get("presenterdetailid")));
					String dstatus=""+map.get("detailstatus");
					presenterappdetail.setDetailstatus(dstatus.charAt(0));
					presenterappdetail.setPrice(""+map.get("price"));				
					presenterappdetail.setLogincount(Integer.parseInt(""+map.get("logincount")));
					presenterappdetail.setWorkingduration(Integer.parseInt(""+map.get("workduration")));
					presenterappdetail.setDateofcreation(new Date());
					presenterappdetailservice.update(presenterappdetail);
				}
			}
			return Response.status(Status.OK).entity(Status.OK).build();
		}
		catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in updatePresenterValues() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
	
	
	@POST
	@Path("/newpresenter")
	@Produces( MediaType.APPLICATION_JSON)
	public Response newPresenterValues(DownloadDetialsViewModel downloaddetailsviewmodel){   
		try{
			Integer a=presenterservice.deactivatePresenter();
			if(a!=0){
				List<?> presenterTypeList = downloaddetailsviewmodel.getTypeobj();
				HashSet<Presenterappdetail> presentAppSet = new HashSet<Presenterappdetail>();
				for (int i = 0; i < presenterTypeList.size(); i++) {
					Map map = (Map) presenterTypeList.get(i);
					Presentertype presentertype =new Presentertype();
					Presenterappdetail presenterappdetial = new Presenterappdetail();		
					presenterappdetial.setWorkingduration(Integer.parseInt(""+map.get("workduration")));
					presenterappdetial.setLogincount(Integer.parseInt(""+map.get("logincount"))); 
					presenterappdetial.setPrice(""+map.get("price")); 			
					presenterappdetial.setDateofcreation(new Date());
					presenterappdetial.setDetailstatus('A');
					presentertype.setId(Integer.parseInt(""+map.get("typeid")));	
					presenterappdetial.setPresentertype(presentertype);
					presentAppSet.add(presenterappdetial);
				}
				
				Presenterapp presenterapp = new Presenterapp();		
				presenterapp.setApplicationname(downloaddetailsviewmodel.getApplicationname());
				presenterapp.setApppath(downloaddetailsviewmodel.getPath());
				presenterapp.setHelpurlpath(downloaddetailsviewmodel.getHelpurl());
				presenterapp.setPresenterstatus('A');
				Orgperson orgperson=new Orgperson();
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				presenterapp.setOrgperson(orgperson);
				presenterapp.setPresenterappdetails(presentAppSet);		
				int presenterdetail =  presenterservice.save(presenterapp);
				if(presenterdetail!=0){
					return Response.status(Status.OK).entity(Status.OK).build();	
				}
				else{
					return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
				}		
			}
			else{
				return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
			}
			
		}
		catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in newPresenterValues() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}


	@POST
	@Path("/downloaddetails")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response deletedownload(DownloadDetialsViewModel downloaddetailsviewmodel){
		downloadActionLogger.info("*************** DELETE A PLAN ****************  ");
		downloadActionLogger.info("ACTION :: CLIENT deleteViewModel == > " +downloaddetailsviewmodel);
		String presenterid=downloaddetailsviewmodel.getPresenterid();
		int presentapp=presenterservice.updatePresenter(Integer.parseInt(presenterid));
		if(presentapp>0){		
			return Response.status(Status.OK).entity(Status.CREATED).build();
		}else{
			return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
		}		
	}

	@POST
	@Path("/presenterappdetails_id")
	@Produces( MediaType.APPLICATION_JSON)
	public Response presenterappdetails_id(@QueryParam("presenterid") String presenterid){

		List<?> presenterappDetailList = presenterservice.getPresenterAppDetailsList(presenterid);

		if(presenterappDetailList.size()>0){		
			return Response.status(Status.OK).entity(presenterappDetailList).build();
		}else{
			return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
		}		
	}

	/*update*/
	@PUT
	@Path("/updatepresenter")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getpresenterupdate(DownloadDetialsViewModel downloaddetailsviewmodel){   
		try{  
			String[] array1=downloaddetailsviewmodel.getWorkingduration();
			HashSet<Presenterappdetail> presentAppSet = new HashSet<Presenterappdetail>();	 
			for(int i=0; i<array1.length;i++){
				Presentertype presentertype =new Presentertype();
				Presenterappdetail presenterappdetial = new Presenterappdetail();		
				presenterappdetial.setWorkingduration(Integer.parseInt(array1[i]));

				presenterappdetial.setLogincount(Integer.parseInt(downloaddetailsviewmodel.getLogincount()[i]));
				presenterappdetial.setPrice(downloaddetailsviewmodel.getPrice()[i]); 	
				presenterappdetial.setId(Integer.parseInt(downloaddetailsviewmodel.getPresenterid()));
				presenterappdetial.setDateofcreation(new Date());
				presenterappdetial.setDetailstatus('A');
				presentertype.setId(Integer.parseInt(downloaddetailsviewmodel.getPresentertypeid()[i]));	
				presenterappdetial.setPresentertype(presentertype);
				presentAppSet.add(presenterappdetial);
			}	 		 
			Presenterapp presenterapp = new Presenterapp();		
			presenterapp.setApplicationname(downloaddetailsviewmodel.getApplicationname());
			presenterapp.setApppath(downloaddetailsviewmodel.getPath());
			presenterapp.setHelpurlpath(downloaddetailsviewmodel.getHelpurl());
			presenterapp.setPresenterstatus('A');
			Orgperson orgperson=new Orgperson();
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));

			presenterapp.setOrgperson(orgperson);
			presenterapp.setPresenterappdetails(presentAppSet);
			presenterapp.setPresenterid(Integer.parseInt(downloaddetailsviewmodel.getPresenterid()));




			/*if(presenterservice.update(presenterapp)!=null){			
				return Response.status(Status.OK).entity(Status.OK).build();
			}*///else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
			//}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getpresenterupdate() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}  
	
	/*
	 * presentertype process 
	*/
	
	@POST
	@Path("/presentertypelist")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPresentertypelist(DownloadDetialsViewModel downloaddetailsviewmodel){   
		List<?> getpresenterdetailslist = null;  
		try{  
			getpresenterdetailslist =  presentertypeservice.getPresentertypelist(downloaddetailsviewmodel.getStatus());	 
			if(getpresenterdetailslist.size() > 0 & ! getpresenterdetailslist.isEmpty()){
				return Response.status(Status.OK).entity(getpresenterdetailslist).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getPresentertypelist() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
	
	@DELETE
	@Path("/presentertypelist/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePresentertypelist(@PathParam("id")int id){
		if(id>0){
		if(presentertypeservice.deletePresentertypelist(id)){
			return Response.ok(Status.ACCEPTED).build();
		}		
		return Response.ok(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(Status.BAD_REQUEST).build();
	}
		
	@POST
	@Path("/presenterapptype")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response presenterapptype(DownloadDetialsViewModel downloaddetailsviewmodel){		
		String typename=downloaddetailsviewmodel.getApplicationname();
		Presentertype presentapp=new Presentertype();
		presentapp.setTypename(typename);
		presentapp.setTypestatus('A');
		presentapp.setDateofcreation(new Date());
		int presenterdetail =  presentertypeservice.save(presentapp);
		if(presenterdetail>0){		
			return Response.status(Status.CREATED).entity(Status.CREATED).build();
		}else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.BAD_REQUEST).build();
		}		
	}
	
	
    
	@PUT
	@Path("/updatepresentertype")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response updatePresentertype(DownloadDetialsViewModel downloaddetailsviewmodel){		
		Integer type = null;
		{		
			type=presentertypeservice.updatePresentertype(Integer.parseInt(downloaddetailsviewmodel.getTypeid()), downloaddetailsviewmodel.getApplicationname());
		
		}
		if(type!=null){
			return Response.status(Status.CREATED).entity(Status.CREATED).build();
		}			
		else{
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
		}	
	}
	 
	@POST
	@Path("/statuspresentertype")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response statusPresentertype(DownloadDetialsViewModel downloaddetailsviewmodel){		
		Integer type = null;		List<?> checkpresenterdetailslist = null; 
		checkpresenterdetailslist =  presentertypeservice.checkPresentertypeupdate(Integer.parseInt(downloaddetailsviewmodel.getTypeid()),downloaddetailsviewmodel.getApplicationname());	
		if(checkpresenterdetailslist.size() == 0 & checkpresenterdetailslist.isEmpty()){		
			type=presentertypeservice.statusPresentertype(Integer.parseInt(downloaddetailsviewmodel.getTypeid()),downloaddetailsviewmodel.getStatus());
		  
			if(type!=null){				  
				   return Response.status(Status.CREATED).entity(Status.CREATED).build();
			   }else{
				   return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
			   }
		}else{
			 return Response.status(Status.OK).entity(Status.CONFLICT).build();
		}
			
	}
	
	@POST
	@Path("/editpresentertype")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPresentertypedetails(DownloadDetialsViewModel downloaddetailsviewmodel){   
		List<?> getpresenterdetailslist = null;  
		try{  
			getpresenterdetailslist =  presentertypeservice.getPresentertype(downloaddetailsviewmodel.getTypeid());	 
			if(getpresenterdetailslist.size() > 0 & ! getpresenterdetailslist.isEmpty()){
				return Response.status(Status.OK).entity(getpresenterdetailslist).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getPresentertypedetails() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
	
	@POST
	@Path("/checkpresentertype")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCheckpresentertype(DownloadDetialsViewModel downloaddetailsviewmodel){   
		List<?> checkpresenterdetailslist = null;  
		try{  
			checkpresenterdetailslist =  presentertypeservice.checkPresentertype(downloaddetailsviewmodel.getApplicationname());	 
			if(checkpresenterdetailslist.size() > 0 & ! checkpresenterdetailslist.isEmpty()){
				return Response.status(Status.OK).entity(checkpresenterdetailslist).build();
			}else{
				return Response.status(Status.OK).entity(Status.CREATED).build();
			}
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in getCheckpresentertype() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
	@POST
	@Path("/checkpresentertypeupdate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response checkpresentertypeupdate(DownloadDetialsViewModel downloaddetailsviewmodel){   
		List<?> checkpresenterdetailslist = null; 
		List<?> checkpresenterlist = null;  HashMap hashmap1=null;
		try{  
			checkpresenterdetailslist =  presentertypeservice.checkPresentertypeupdate(Integer.parseInt(downloaddetailsviewmodel.getTypeid()),downloaddetailsviewmodel.getApplicationname());
			checkpresenterlist =  presentertypeservice.checkPresentertype(downloaddetailsviewmodel.getApplicationname());				
			String applicationid=null;			
			if(checkpresenterdetailslist.size() > 0 & ! checkpresenterdetailslist.isEmpty()){					
				return Response.status(Status.OK).entity("typeexists").build();
			}else if(checkpresenterlist.size()>0 & !checkpresenterlist.isEmpty()){
				  hashmap1 = (HashMap)checkpresenterlist.get(0);
					 applicationid=""+hashmap1.get("id");
					 if(applicationid.equals(downloaddetailsviewmodel.getTypeid())){
						 return Response.status(Status.OK).entity("nodata").build();
					 }else{
					    return Response.status(Status.OK).entity("nameexists").build();
				 }			
		      }else{
		        return Response.status(Status.OK).entity("nodata").build();
		   }
		}catch(Exception e){
			if(downloadActionLogger.isInfoEnabled()){
				downloadActionLogger.error("error in checkpresenterdetailslist() in DownloadAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
}

