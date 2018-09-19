package com.igrandee.product.ecloud.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.service.course.OrgPersonService;

@Component
@Scope("prototype")
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	OrgPersonService orgPersonService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		int courseid=0;
		if(request.getParameter("courseid")!=null&&!request.getParameter("courseid").equals("null")){
			courseid=Integer.parseInt(request.getParameter("courseid"));
		}
       if(orgPersonService.isLoginDeactivated(exception.getAuthentication().getName(),request.getParameter("organizationid"))){
    	   
    	   if(request.getParameter("formname")!=null?request.getParameter("formname").equals("coursepreview"):false)
    		   super.setDefaultFailureUrl("/preview?courseid="+courseid+"&error=deactivated");
    	   else
    		   super.setDefaultFailureUrl("/index?error=deactivated");   
       }
       else{
    	   
    	   if(request.getParameter("formname")!=null?request.getParameter("formname").equals("coursepreview"):false)
    		   super.setDefaultFailureUrl("/preview?courseid="+courseid+"&error=invalid");
    	   else
    		   super.setDefaultFailureUrl("/index?error=invalid");
       }
          super.onAuthenticationFailure(request, response, exception);
      }

}
