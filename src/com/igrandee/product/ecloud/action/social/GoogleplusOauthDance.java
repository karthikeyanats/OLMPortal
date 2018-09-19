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
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import com.googlecode.googleplus.Plus;
import com.googlecode.googleplus.core.GooglePlusConnectionFactory;
import com.sun.jersey.api.core.InjectParam;

@Path("/googleplus")
public class GoogleplusOauthDance {
	
	@InjectParam("usersConnectionRepository")
	UsersConnectionRepository usersConnectionRepository;
	
	@InjectParam
    GooglePlusConnectionFactory googlePlusConnectionFactory;
	
	OAuth2Operations auth2Operations;
	
	Connection<Plus> connection;
	
	@GET
	public void requestCode(@Context HttpServletResponse response,@Context UriInfo uriInfo){
		String baseuri=uriInfo.getBaseUri().toString();
		auth2Operations = googlePlusConnectionFactory.getOAuthOperations();
		OAuth2Parameters oAuth2Parameters=new OAuth2Parameters();
		oAuth2Parameters.setScope("https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me email profile");
		oAuth2Parameters.setRedirectUri(baseuri+"googleplus/accesstoken");
		oAuth2Parameters.set("access_type", "offline");
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
		 auth2Operations = googlePlusConnectionFactory.getOAuthOperations();
		AccessGrant accessGrant =auth2Operations.exchangeForAccess(code, baseuri+"/rest/googleplus/accesstoken", null);
		connection = googlePlusConnectionFactory.createConnection(accessGrant);
		Plus plus=connection.getApi();
        ConnectionRepository connectionRepository=usersConnectionRepository.createConnectionRepository(plus.getPeopleOperations().get("me").getGoogleAccountEmail());		
		if(connectionRepository.findConnections("googleplus").isEmpty()){
		connectionRepository.addConnection(connection);		
		}		
		URI uri =UriBuilder.fromUri(baseuri+"/socialusers.jsp?name="+plus.getPeopleOperations().get("me").getGoogleAccountEmail()+"&provider=googleplus").build();
		
		return Response.seeOther(uri).build();
		
	}

}
