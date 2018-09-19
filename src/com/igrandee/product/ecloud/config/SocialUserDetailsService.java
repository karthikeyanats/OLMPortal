package com.igrandee.product.ecloud.config;


import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.igrandee.core.login.util.UserDetailsAdaptor;
import com.igrandee.core.login.util.UserDetailsService;
import com.igrandee.product.ecloud.service.course.OrgPersonService;

public class SocialUserDetailsService implements org.springframework.social.security.SocialUserDetailsService{

	@Inject
	UserDetailsService userDetailsService;
	
	@Inject
	OrgPersonService orgPersonService;
	
	private static final Logger socialUserDetailsServiceLogger = Logger.getLogger(SocialUserDetailsService.class);
	
	
	public SocialUserDetails loadUserByUserId(String arg0)
			throws UsernameNotFoundException, DataAccessException {
 		socialUserDetailsServiceLogger.info("socialuserdetailsservice ===> " +arg0 );
		UserDetailsAdaptor userDetailsAdaptor= (UserDetailsAdaptor) userDetailsService.loadUserByUsername(arg0);
		if(userDetailsAdaptor.getUser().getProperty("organizationid")==null){
			ServletRequestAttributes requestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
 	 		String orgid=requestAttributes.getRequest().getParameter("organizationid");
 	 		if(orgid==null){
 	 			orgid=requestAttributes.getRequest().getSession().getAttribute("organizationid").toString();
 	 		}
 	 		if(orgPersonService.isLoginDeactivated(arg0, orgid)){
 	 			throw new UsernameNotFoundException("Your account has been deactivated, please contact the administrator");	
 	 		}
 	 		else{
			    throw new UsernameNotFoundException("Please signup using social networking sites");
 	 		}
		}
		return userDetailsAdaptor;
	}

}
