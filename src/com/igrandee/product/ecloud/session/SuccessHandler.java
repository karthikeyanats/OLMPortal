package com.igrandee.product.ecloud.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.igrandee.core.login.util.AbstractIGActionSupport;

/**
 * @author raja_r
 * this class used to forward other pages using spring security
 */
public class SuccessHandler extends AbstractIGActionSupport implements AuthenticationSuccessHandler{
 
	
	private static final long serialVersionUID = 1L;
	private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy(); 
	
	public void onAuthenticationSuccess(HttpServletRequest arg0, 
			HttpServletResponse arg1, Authentication arg2) throws IOException,
			ServletException { 
		System.out.println("rolename ==== "+getAttribute("rolename")); 
 	    if(getAttribute("rolename").equals("admin")){
	    	if(arg0.getParameter("targetUrl").equals("/myorganization")){
	    		arg0.getSession().setAttribute("organizationid", ""+arg0.getParameter("organizationid"));
	    		arg0.getSession().setAttribute("username", ""+arg0.getParameter("username"));
	    		arg0.getSession().setAttribute("password", ""+arg0.getParameter("password"));
	    		arg0.getSession().setAttribute("orgname", ""+arg0.getParameter("organizationname"));
	    		arg0.getSession().setAttribute("targetUrl", ""+arg0.getParameter("targetUrl"));
	    		arg0.getSession().setAttribute("orglogo", ""+arg0.getParameter("orglogo"));
	    		redirectStrategy.sendRedirect(arg0, arg1, "/app/createorganizationview");
	    	}
	    	else{
	    		redirectStrategy.sendRedirect(arg0, arg1, "/app/dashboard");
	    	}
			
		} else if(getAttribute("rolename").equals("tutor")){
			if(arg0.getParameter("courseid")==null || arg0.getParameter("courseid").equals("")) {  		 			
				} else {
					arg0.getSession().setAttribute("courseid", ""+arg0.getParameter("courseid"));
					arg0.getSession().setAttribute("courseinvitationid", ""+arg0.getParameter("invitationid"));
					arg0.getSession().setAttribute("orgstatus", ""+arg0.getParameter("orgstatus"));
				}
 			redirectStrategy.sendRedirect(arg0, arg1, "/app/trainerdashboard");

		}
		else
		{  
 			if(StringUtils.isNotEmpty(""+arg0.getParameter("courseid"))){ 
 				if(arg0.getParameter("courseid")==null || arg0.getParameter("courseid").equals("")) {  		 			
  				} else {
  					arg0.getSession().setAttribute("courseid", ""+arg0.getParameter("courseid"));
  					arg0.getSession().setAttribute("courseinvitationid", ""+arg0.getParameter("invitationid"));
  					arg0.getSession().setAttribute("orgstatus", ""+arg0.getParameter("orgstatus"));
  				}
			}
 			redirectStrategy.sendRedirect(arg0, arg1, "/app/userdashboard"); 
		}
		
	}

}
