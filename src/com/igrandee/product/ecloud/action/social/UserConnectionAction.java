package com.igrandee.product.ecloud.action.social;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.sun.jersey.api.core.InjectParam;

@Path("/sociallogins")
public class UserConnectionAction extends AbstractIGActionSupport {
	
	@InjectParam
	SessionFactory sessionFactory;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSocialLogins(){
		Session session=sessionFactory.openSession();				
		Query query=session.createSQLQuery("select userId,providerId from UserConnection where userId='"+getAttribute("email")+"'");
		List<?> sociallogindetails=query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		session.close();
		if(sociallogindetails!=null){
			return Response.ok().entity(sociallogindetails).build();
		}
		else{
			return Response.ok().entity(Status.NOT_FOUND).build();
		}
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSocialLogins(@QueryParam("providerid")String providerId){
		Session session=sessionFactory.openSession();				
		Query query=session.createSQLQuery("delete from UserConnection where providerId='"+providerId+"'");
		int sociallogindetails=query.executeUpdate();
		session.close();
		if(sociallogindetails>0){
			return Response.ok().entity(Status.ACCEPTED).build();
		}
		else{
			return Response.ok().entity(Status.NOT_FOUND).build();
		}
	}

}
