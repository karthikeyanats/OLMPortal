package com.igrandee.product.ecloud.session;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.igrandee.core.login.util.User;
import com.igrandee.core.login.util.UserDetailsService;
import com.igrandee.product.ecloud.service.session.SessionService;

public class SessionDetail extends  UserDetailsService 
{
	@Inject
	SessionService sessionService;

	@Inject
	UsersConnectionRepository usersConnectionRepository;

	public void loadSessionAttributes(User user) {  		

		try{
			ServletRequestAttributes requestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			String orgid=requestAttributes.getRequest().getParameter("organizationid");
			if(orgid==null){
				orgid=requestAttributes.getRequest().getSession().getAttribute("organizationid").toString();
			}
			List<?> personList=null;
			if(user.getProperty("loginid")!=null){
				personList =sessionService.getLoginDetails(user.getProperty("loginid"),orgid);
			}
			if(personList!=null?personList.size()!=0:false) {
				for(int i=0;i<personList.size();i++) {
					Map<?,?> personalMap = (Map<?,?>)personList.get(i);
					String orgname = ""+personalMap.get("orgname");
					String orgstatus = ""+personalMap.get("orgstatus"); 
					String organizationid=""+personalMap.get("organizationid");
					String personid = ""+personalMap.get("personid"); 
					String firstname = ""+personalMap.get("firstname");
					String lastname = ""+personalMap.get("lastname");
					String orgpersonid=""+personalMap.get("orgpersonid");
					String rolename=""+personalMap.get("rolename");
					String emailaddress=""+personalMap.get("userid");
					String orglogo=""+personalMap.get("orglogo"); 
					String phonenumber=""+personalMap.get("phonenumber");
					String orgphonenumber=""+personalMap.get("orgnumber");
					String orgemail=""+personalMap.get("orgemailid");
					String orgurl=""+personalMap.get("url");
					String username	=	null;
					String password	=	null;
					String targetUrl =	null;

					if(rolename==null || rolename=="" || rolename.equals("") || rolename.equals("null")) 
					{   
						user.setProperty("rolename", rolename);
					}
					else 
					{ 					
						user.setProperty("rolename", rolename);  
					}	 			
					
					String colorpattern = getOrgColorPatternString(orgid);
					user.setProperty("colorpattern", colorpattern);
					user.setProperty("orgname", orgname);
					user.setProperty("organizationid", organizationid);
					user.setProperty("firstname", firstname);
					user.setProperty("lastname", lastname); 
					user.setProperty("personid", personid);
					user.setProperty("orgpersonid", orgpersonid);
					user.setProperty("email", emailaddress);
					user.setProperty("orglogo", orglogo);
					user.setProperty("orgstatus", orgstatus);
					user.setProperty("phonenumber", phonenumber);
					user.setProperty("orgphonenumber", orgphonenumber);
					user.setProperty("orgemail", orgemail);
					user.setProperty("orgurl", orgurl);
					user.setProperty("username", username);
					user.setProperty("password", password);
					user.setProperty("targetUrl", targetUrl);

				} 
			} 
			else{
				user.setProperty("password", "*****");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}	

	private String getOrgColorPatternString(String orgid){
		List<?> colorList = sessionService.getThemeColor(orgid);
		String colorpattern = null;
		if(colorList!=null?colorList.size()!=0:false) {
			for(int j=0;j<colorList.size();j++) {
				Map<?,?> colorMap = (Map<?,?>)colorList.get(j);
				colorpattern = ""+colorMap.get("patternnumber");
			}
		}else{
			colorpattern = "0";
		}
		return colorpattern;
	}

}