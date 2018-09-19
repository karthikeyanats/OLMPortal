package com.igrandee.product.ecloud.action.social;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.sun.jersey.api.core.InjectParam;

@Path("/social")
public class OauthDance {
		
	@InjectParam("usersConnectionRepository")
	UsersConnectionRepository usersConnectionRepository;
	
	@InjectParam
	FacebookConnectionFactory facebookFactory;
	
	OAuth2Operations auth2Operations;
	
	Connection<Facebook> connection;
	
	@InjectParam
	OrgPersonService orgPersonService;
	

	@GET
	@Path("/facebook")
	public void requestCode(@Context HttpServletResponse response,@Context UriInfo uriInfo){
		String baseuri=uriInfo.getBaseUri().toString();
		auth2Operations = facebookFactory.getOAuthOperations();
		OAuth2Parameters oAuth2Parameters=new OAuth2Parameters();
		oAuth2Parameters.setScope("publish_actions,email,public_profile");
		oAuth2Parameters.setRedirectUri(baseuri+"social/accesstoken");
		String authurl=auth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		try {
			response.sendRedirect(authurl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Path("/accesstoken")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response requestToken(@QueryParam("code")String code,@Context UriInfo uriInfo){
		String baseuri=uriInfo.getBaseUri().toString().replaceAll("/rest/", "");
		auth2Operations = facebookFactory.getOAuthOperations();
		AccessGrant accessGrant =auth2Operations.exchangeForAccess(code, baseuri+"/rest/social/accesstoken", null);
		connection = facebookFactory.createConnection(accessGrant);
		Facebook facebook=connection.getApi();
		ConnectionRepository connectionRepository=usersConnectionRepository.createConnectionRepository(facebook.userOperations().getUserProfile().getEmail());		
		if(connectionRepository.findConnections("facebook").isEmpty()){
		connectionRepository.addConnection(connection);		
		}
		
		URI uri =UriBuilder.fromUri(baseuri+"/socialusers.jsp?name="+facebook.userOperations().getUserProfile().getEmail()+"&provider=facebook").build();
		
		return Response.seeOther(uri).build();
		
	}


}
