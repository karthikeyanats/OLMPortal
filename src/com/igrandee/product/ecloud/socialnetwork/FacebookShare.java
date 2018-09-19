package com.igrandee.product.ecloud.socialnetwork;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.social.facebook.api.Facebook;

import com.igrandee.product.ecloud.session.SocialSessionDetail;
import com.igrandee.product.ecloud.viewmodel.socialnetwork.FacebookCountVM;
import com.igrandee.product.ecloud.viewmodel.socialnetwork.FacebookShareVM;
import com.sun.jersey.api.core.InjectParam;

@Path("/share")
public class FacebookShare {
   
   @InjectParam
   FacebookOperationStore facebookOperationStore;
   
   @InjectParam
   Facebook facebook;
   
   @InjectParam
   SocialSessionDetail socialSessionDetail;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response share(FacebookShareVM facebookShareVM){
		String status=null;
		if(facebook!=null){
		status=facebookOperationStore.sharePhotoWithLink(facebookShareVM,facebook);
		}
		else if(socialSessionDetail.getConnection()!=null){
			status=facebookOperationStore.sharePhotoWithLink(facebookShareVM,socialSessionDetail.getConnection().getApi());
		}
		
		if(status==null){
			socialSessionDetail.setSocialmsg("sharesync");
			return Response.ok().status(Status.CREATED).entity(Status.NOT_ACCEPTABLE).build();			
		}
		else{
			return Response.ok().status(Status.CREATED).entity(Status.CREATED).build();	
		}
		
		
	}	
	
	@Path("/count")
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFacebookCount(FacebookShareVM facebookShareVM){	
		if(facebookShareVM!=null){
		List<FacebookCountVM> facebookCountVMs=facebookOperationStore.getShareLikeCount(facebookShareVM);		
		if(facebookCountVMs!=null){			
			return Response.ok().status(Status.CREATED).entity(facebookCountVMs).build();			
		}
			return Response.ok().status(Status.CREATED).entity(Status.INTERNAL_SERVER_ERROR).build();	
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();	
}
	
	@Path("/like")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response like(FacebookShareVM facebookShareVM){
		String status=null;
		if(this.facebook!=null){
		status=facebookOperationStore.likeLink(facebookShareVM,facebook);
		}
		else if(socialSessionDetail.getConnection()!=null){
			status=facebookOperationStore.likeLink(facebookShareVM,socialSessionDetail.getConnection().getApi());
		}
		
		if(status==null){
			socialSessionDetail.setSocialmsg("likesync");
			return Response.ok().status(Status.CREATED).entity(Status.NOT_ACCEPTABLE).build();			
		}
		else{
			return Response.ok().status(Status.CREATED).entity(Status.CREATED).build();	
		}
		
		
	}
	
}
