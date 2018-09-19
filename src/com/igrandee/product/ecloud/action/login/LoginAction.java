package com.igrandee.product.ecloud.action.login;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.igrandee.core.contact.model.Address;
import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Email;
import com.igrandee.core.contact.model.Phone;
import com.igrandee.core.login.model.Login;
import com.igrandee.core.login.service.LoginService;
import com.igrandee.core.organization.model.Department;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.core.organization.model.Role;
import com.igrandee.core.person.model.Person;
import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.action.SMTPAuthenticator;
import com.igrandee.product.ecloud.helper.JsonHelper;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Personallocation;
import com.igrandee.product.ecloud.service.course.CourseInvitationService;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.service.presenter.DownloadHistoryService;
import com.igrandee.product.ecloud.service.users.UserViewService;

 public class LoginAction extends MasterActionSupport  implements SessionAware
{

	private static final long serialVersionUID = 1L;
	private static final int PASSWORD_LENGTH = 8;
	private static final Random RANDOM = new SecureRandom();
	private static Logger LoginActionlogger = Logger.getLogger(LoginAction.class);

	@Inject
	OrgPersonService orgPersonService;

	@Inject
	private LoginService loginService;
	
	@Inject
	UserViewService UserViewService;
	
	@Inject
	DownloadHistoryService downloadHistoryService;
	
	@Inject
	OrganizationViewService organizationViewService;
	
	@Inject 
	private CourseInvitationService courseinvitationservice;
	
	private String courseid;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String courseinvitationid;
	
	private String organizationid;
	private String emailid;
	private String targetUrl;
	private String orgpersonid;
	private String personid;
	private JsonHelper jsonHelper=new JsonHelper();
	
	String emailnumber;
	String emailemailid;
	String emailurl;
	String emailfirstname;
	String emaillastname;
	String emailorganizationname;

	private HttpServletRequest request = ServletActionContext.getRequest();

	private Map<String, Object> session;  
	public static ResourceBundle resourceBundle;
	public static ResourceBundle ap = ResourceBundle.getBundle("application"); 
	String productname = ap.getString("label.productname");
	String registration = ap.getString("registration");
	
 	private Orgperson orgPerson;
	private Contactinfo  contactinfo;
	private Email email;
	private Address address;
	private Phone  phone;
	private Login login;
	private Person person;
	private Personallocation personallocation;
	private Role role;
	private Department department;	
	private Organization organization;	
	private String loginid;
	
	/**
	 *  This method is used to insert into the signup details
	 *  @author raja_r 
	 *  @return String
	 *  @since Oct-9-2013
	 */	 
	@Action(value="/newSignUp",  results={@Result(name="SUCCESS",location="app/user/courseSubscribe.jsp")})
	public String newSignUp() {
		try	{
			
			System.out.println("New Sign Up");
			
			setFirstname(firstname); 
			setLastname(lastname);  
			setUsername(emailid);    
			setCourseid(courseid);  
			setPassword(password);
			setTargetUrl(targetUrl);
			session.put("courseid", courseid);
			List<?> usernameCheck = orgPersonService.getLoginCheck(username,Long.parseLong(organizationid));
				if(usernameCheck.size()==0) 
			{
					int orgpersonid=0;
			List<Object[]> userinOrg=(List<Object[]>) orgPersonService.loginCheckForOrganization(username,Long.parseLong(organizationid));
	           if(userinOrg.isEmpty()){				
				orgPerson = new Orgperson();
				contactinfo = new Contactinfo();			
				email = new Email();
				email.setUserid(emailid);
				email.setEmailstatus('A');
				Set<Email> emailSet = new HashSet<Email>();
				emailSet.add(email);

				contactinfo.setEmails(emailSet) ;   
				contactinfo.setContactinfostatus('A');

				address =  new Address();
				address.setAddressstatus('A');
				Set<Address> addresses = new HashSet<Address>();
				addresses.add(address);
				contactinfo.setAddresses(addresses);

				phone = new Phone();
				phone.setPhonestatus('A');
				Set<Phone> phones = new HashSet<Phone>();
				phones.add(phone);
				contactinfo.setPhones(phones);

				login = new Login(); 			 
				login.setUserid(emailid); 
				login.setPassword(password);
				login.setLoginstatus('A');  

				person = new Person();  
				person.setFirstName(firstname); 
				person.setLastName(lastname);			 
				person.setContactinfo(contactinfo); 
				person.setPersonstatus('A');

				personallocation = new Personallocation();
				role = new Role();
				role.setRolename("user");
				role.setRolestatus('A');
				role.setRolelevel('0');
				role.setRoleid(2);

				department = new Department();
				department.setDepartmentid(2);
				department.setDepartmentname("admin");
				department.setDepartmentstatus('A');

				HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();				
				personallocation.setRole(role);
				personallocation.setDepartment(department);
				personallocationSet.add(personallocation);
				personallocation.setPersonallocationstatus('A');
				orgPerson.setPersonallocations(personallocationSet);
				orgPerson.setLogin(login);
				orgPerson.setPersonid(person);  				
				orgPerson.setOrgpersonstatus('A');	
				organization = new Organization();				 
				organization.setOrganizationid(Long.parseLong(organizationid));	
				orgPerson.setOrganizationid(organization);
				orgpersonid = orgPersonService.save(orgPerson);
	           }
	           else{
	        	   orgpersonid=orgPersonService.addOrgForUser(Long.parseLong(organizationid), (Integer)userinOrg.get(0)[1], (Long)userinOrg.get(0)[2]);
	               setPassword((String)userinOrg.get(0)[3]); 
	           }
				if(!courseinvitationid.trim().equals("")&&courseinvitationid!=null){
				courseinvitationservice.updateCourseInvitation(courseinvitationid);
				}
				if(orgpersonid!=0){					
					resourceBundle = ResourceBundle.getBundle("smtpconfig"); 
					String smtphostname		=	resourceBundle.getString("smtp_hostname");
					String smtpport		    =	resourceBundle.getString("smtp_hostport");
					String admin_mailid		=	resourceBundle.getString("admin_mailid"); 
					String admin_mailpassword	=	resourceBundle.getString("admin_mailpassword"); 

					Properties properties = System.getProperties();
					properties.put("mail.smtp.host", smtphostname);
					properties.put("mail.smtp.socketFactory.port", smtpport);
					properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.port", smtpport); 

					Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
					Session session = Session.getDefaultInstance(properties,auth);
					session.setDebug(true);
					
					List<?> emailreaddata = orgPersonService.emailContent(Long.parseLong(organizationid));
					getMailValue(emailreaddata);
					

					String mail_html_cont ="";			    
					mail_html_cont += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>"; 
					mail_html_cont +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>  <tr>    <td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td>  </tr>  <tr>    <td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>        <tr>          <td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><h4><font face='Verdana' size=\"3\" color=#525252;><b>Dear "+firstname+", </font></h4>   <div style='font-size:28px;color:rgb(179, 141, 92);'>Congratulations! Your account has been successfully created</div>  <div> <br><h2>Your Account details are</h2><div style='margin-left: 90px;'><br>  <b>UserName </b>: "+username+" <br>   <br><b>Password </b>: "+password+"  <br><br><a href='http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width: 118px;text-decoration: none;color: white;'>Click here to login</a><br>                         </div> </div><h3>Thanks,<br>The Reamedi Team.</h3></td>        </tr>      </table></td>  </tr>  <tr>    <td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>      <tr>        <td align='left' valign='top' style='color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px;'> "+emailorganizationname+" <br>Contact :"+emailfirstname+" "+emaillastname+" <br>          Phone: "+emailnumber+" <br>          Email: <a href='mailto:"+emailemailid+"' style='color:#ffffff; text-decoration:none;'>"+emailemailid+"</a><br>          Website: <a href='http://"+emailurl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+emailurl+"</a></td>      </tr>    </table></td>  </tr></table>";
					mail_html_cont += "</body>";
					mail_html_cont += "</html>";   

					MimeMessage msg = new MimeMessage(session); 
					msg.setFrom(new InternetAddress(admin_mailid));  
					msg.setRecipients(Message.RecipientType.TO, emailid); 

					msg.setSubject(registration); 
					msg.setContent(mail_html_cont.toString(),"text/html");
					Transport.send(msg);
				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}
		return "SUCCESS"; 
	}


	
	@SuppressWarnings("unchecked")
	@Action(value="/withoutCourseSignUp",  results={@Result(name="SUCCESS",location="app/user/courseSubscribe.jsp")})
	public String withoutCourseSignUp() {
		System.out.println("NEW SIGN UP");
		try	{
			setOrganizationid(organizationid);
            setFirstname(firstname);
 			setUsername(username);    
 			setPassword(password);
			setTargetUrl(targetUrl);
			int orgpersonid ;
			
			System.out.println("organizationid "+organizationid);
			System.out.println("firstname "+firstname);
			System.out.println("username "+username);
			System.out.println("password "+password);
			System.out.println("targetUrl "+targetUrl);
			
			List<?> usernameCheck = orgPersonService.getLoginCheck(username,Long.parseLong(organizationid));
			System.out.println("usernameCheck ===>> "+usernameCheck.size());
			if(usernameCheck.size()==0) 
			{
				List<Object[]> userinOrg=(List<Object[]>) orgPersonService.loginCheckForOrganization(username,Long.parseLong(organizationid));
               
				if(userinOrg.isEmpty()){
				orgPerson = new Orgperson();
				contactinfo = new Contactinfo();			
				email = new Email();
				email.setUserid(username); 
				email.setEmailstatus('A');
				Set<Email> emailSet = new HashSet<Email>();
				emailSet.add(email);
				
				System.out.println("Email "+username);

				contactinfo.setEmails(emailSet) ;   
				contactinfo.setContactinfostatus('A');

				address =  new Address();
				address.setAddressstatus('A');
				Set<Address> addresses = new HashSet<Address>();
				addresses.add(address);
				contactinfo.setAddresses(addresses);

				phone = new Phone();
				phone.setPhonestatus('A');
				Set<Phone> phones = new HashSet<Phone>();
				phones.add(phone);
				contactinfo.setPhones(phones);

				login = new Login(); 			 
				login.setUserid(username); 
				login.setPassword(password);
				login.setLoginstatus('A');  

				person = new Person();  
				person.setFirstName(firstname); 
				person.setLastName(lastname);			 
				person.setContactinfo(contactinfo); 
				person.setPersonstatus('A');

				personallocation = new Personallocation();
				role = new Role();
				role.setRolename("user");
				role.setRolestatus('A');
				role.setRolelevel('0');
				role.setRoleid(2);

				department = new Department();
				department.setDepartmentid(2);
				department.setDepartmentname("admin");
				department.setDepartmentstatus('A');

				HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();				
				personallocation.setRole(role);
				personallocation.setDepartment(department);
				personallocationSet.add(personallocation);
				personallocation.setPersonallocationstatus('A');
				orgPerson.setPersonallocations(personallocationSet);

				orgPerson.setLogin(login);
				orgPerson.setPersonid(person);   
				orgPerson.setOrgpersonstatus('A');
				
				organization = new Organization();
				 
				organization.setOrganizationid(Long.parseLong(organizationid));	
				orgPerson.setOrganizationid(organization);
				
				System.out.println("orgPerson -->>><<>>> "+orgPerson);
				
				//orgPersonService.s
				orgpersonid= orgPersonService.save(orgPerson);
				System.out.println("orgpersonid -->>><<>>> "+orgpersonid);
				}
                else{
                  orgpersonid=orgPersonService.addOrgForUser(Long.parseLong(organizationid), (Integer)userinOrg.get(0)[1], (Long)userinOrg.get(0)[2]);
                  setPassword((String)userinOrg.get(0)[3]);
                }
 
				if(orgpersonid!=0){
					resourceBundle = ResourceBundle.getBundle("smtpconfig");
 
					String smtphostname		=	resourceBundle.getString("smtp_hostname");
					String smtpport		    =	resourceBundle.getString("smtp_hostport");
					String admin_mailid		=	resourceBundle.getString("admin_mailid"); 
					String admin_mailpassword	=	resourceBundle.getString("admin_mailpassword"); 
					String successmaill = ap.getString("label.successmail");

					Properties properties = System.getProperties();
					properties.put("mail.smtp.host", smtphostname);
					properties.put("mail.smtp.socketFactory.port", smtpport);
					properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.port", smtpport); 

					Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
					Session session = Session.getDefaultInstance(properties,auth);
					session.setDebug(true);
					List<?> emailreaddata = orgPersonService.emailContent(Long.parseLong(organizationid));
					getMailValue(emailreaddata);
					
					String mail_html_cont ="";			    
					mail_html_cont += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>"; 
					mail_html_cont +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>  <tr>    <td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td>  </tr>  <tr>    <td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>        <tr>          <td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><h4><font face='Verdana' size=\"3\" color='#525252'><b>Dear "+firstname+", </font></h4>   <div style='font-size:28px;color:rgb(179, 141, 92);'>"+successmaill+"</div><h2>Your account details are</h2>  <div> <br><div style='margin-left: 90px;'><br>  <b>UserName </b>: "+username+" <br>   <br><b>Password </b>: "+password+"  <br><br><a  href='http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width: 118px;text-decoration: none;color: white;'>Click here to login</a><br>              <br>           </div> </div><h3>Thanks,<br>The Reamedi Team.</h3></td>        </tr>      </table></td>  </tr>  <tr>    <td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>      <tr>        <td align='left' valign='top' style='color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px;'> "+emailorganizationname+" <br>Contact :"+emailfirstname+" "+emaillastname+" <br>          Phone: "+emailnumber+" <br>          Email: <a href='mailto:"+emailemailid+"' style='color:#ffffff; text-decoration:none;'>"+emailemailid+"</a><br>          Website: <a href='http://"+emailurl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+emailurl+"</a></td>      </tr>    </table></td>  </tr></table>";					
					mail_html_cont += "</body>";
					mail_html_cont += "</html>";  
  
					MimeMessage msg = new MimeMessage(session); 
					msg.setFrom(new InternetAddress(admin_mailid));  
					msg.setRecipients(Message.RecipientType.TO, username); 
 
					msg.setSubject(registration); 
					msg.setContent(mail_html_cont.toString(),"text/html");
					Transport.send(msg);
				}

			}
		}catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				e.printStackTrace();
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}
		return "SUCCESS"; 
	}
 
	
	@Action(value="/organizationSignUp",  results={@Result(name="SUCCESS",location="app/user/courseSubscribe.jsp")})
	public String organizationSignUp() {
		Integer personstatus=0;
		Integer loginstatus=0;
		try	{
			setOrganizationid(organizationid);
            setFirstname(firstname);
 			setUsername(username);    
 			setPassword(password);
			setTargetUrl(targetUrl);
			List<?> usernameCheck = orgPersonService.getLoginCheck(username,Long.parseLong(organizationid));
 			if(usernameCheck.size()==0) 
			{
 				personstatus=orgPersonService.updatePersonStatus(Integer.parseInt(personid),firstname);
 				loginstatus=orgPersonService.updateLoginStatus(username,password,Integer.parseInt(loginid));
 				if(personstatus!=0 && loginstatus!=0){
					resourceBundle = ResourceBundle.getBundle("smtpconfig"); 
					String smtphostname		=	resourceBundle.getString("smtp_hostname");
					String smtpport		    =	resourceBundle.getString("smtp_hostport");
					String admin_mailid		=	resourceBundle.getString("admin_mailid"); 
					String admin_mailpassword	=	resourceBundle.getString("admin_mailpassword"); 
					String successmaill = ap.getString("label.successmail");

					Properties properties = System.getProperties();
					properties.put("mail.smtp.host", smtphostname);
					properties.put("mail.smtp.socketFactory.port", smtpport);
					properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.port", smtpport); 

					Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
					Session session = Session.getDefaultInstance(properties,auth);
					session.setDebug(true);
					
					List<?> emailreaddata = orgPersonService.emailContent(Long.parseLong(organizationid));
					getMailValue(emailreaddata);
					
					String mail_html_cont ="";			    
					mail_html_cont += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>"; 
					mail_html_cont +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' zstyle=' border: 1px solid rgb(225, 225, 243);'>  <tr>    <td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td>  </tr>  <tr>    <td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>        <tr>          <td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><h4><font face='Verdana' size=\"3\" color='#525252'><b>Dear "+firstname+", </font></h4>   <div style='font-size:28px;color:rgb(179, 141, 92);'>"+successmaill+"</div> <h2>Your account details are</h2> <div> <br> <div style='margin-left: 90px;'><br>  <b>UserName </b>: "+username+" <br>   <br><b>Password </b>: "+password+"  <br><br><a  href='http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width: 118px;text-decoration: none;color: white;'>Click here to login</a><br>              <br>           </div> </div><h3>Thanks,<br>The Reamedi Team.</h3></td>        </tr>      </table></td>  </tr>  <tr>    <td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>      <tr>        <td align='left' valign='top' style='color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px;'> "+emailorganizationname+" <br>Contact :"+emailfirstname+" "+emaillastname+" <br>          Phone: "+emailnumber+" <br>          Email: <a href='mailto:"+emailemailid+"' style='color:#ffffff; text-decoration:none;'>"+emailemailid+"</a><br>          Website: <a href='http://"+emailurl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+emailurl+"</a></td>      </tr>    </table></td>  </tr></table>";					
					mail_html_cont += "</body>";
					mail_html_cont += "</html>";  
 
					MimeMessage msg = new MimeMessage(session); 
					msg.setFrom(new InternetAddress(admin_mailid));  
					msg.setRecipients(Message.RecipientType.TO, username); 
 
					msg.setSubject(registration); 
					msg.setContent(mail_html_cont.toString(),"text/html");
					Transport.send(msg);
				}

			}
		}catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				e.printStackTrace();
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}
		return "SUCCESS"; 
	}
	


	/**
	 *  This method is used to Check the username
	 *  @author raja_r
	 *  @return String
	 *  @since Dec-9-2013
	 *   
	 */
	@Action (value="/checkUserName")
	public void checkUserName()
	{
		try
		{
			jsonHelper= new JsonHelper(); 
			String userdata=username.trim();
			if(username!=null &&  organizationid!=null && userdata.length()!=0){
				List<?> usernameCheck = orgPersonService.getLoginCheck(username,Long.parseLong(organizationid)); 
				if(usernameCheck.size()==0){
					List<Object[]> userinOrg=(List<Object[]>) orgPersonService.loginCheckForOrganization(username,Long.parseLong(organizationid));
					
					if(userinOrg.size()==0){
					jsonHelper.toJsonString("Success");
					}
					else{
						jsonHelper.toJsonString("Addorganization");
					}
					
				}else{
					jsonHelper.toJsonString("Exits");
				}
			}else{
				jsonHelper.toJsonString("nocontent");
			}
		}
		catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}

	}
	
	@Action(value="/logincheck")
	public void logincheck()
	{  
		Integer delPerson=0;
		try{			
			//logPerson= orgPersonService.deleteLoginStatus(Integer.parseInt(loginid)); 
		    delPerson= orgPersonService.deletePersonStatus(Integer.parseInt(orgpersonid)); 
		   if(delPerson!=0){
					jsonHelper.toJsonString("success");
			  }else{
					jsonHelper.toJsonString("ailure");
			  }
			}
		catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in logincheck() in LoginAction",e);
			}
		}
     
	}
	
	@Action(value="/restoreuser")
	public void restoreuser()
	{  
		Integer deletePerson=0;
		try{			
			//loginnPerson= orgPersonService.delLoginStatus(Integer.parseInt(loginid)); 
			deletePerson= orgPersonService.delPersonStatus(Integer.parseInt(orgpersonid)); 		  
		   if(deletePerson!=0){
					jsonHelper.toJsonString("success");
			  }else{
					jsonHelper.toJsonString("failure");
			  }
			}
		catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in restoreuser() in LoginAction",e);
			}
		}
     
	}
	
	
	public String generateRandomPassword()
    {
 		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
        String passWord = "";
        for (int i=0; i<PASSWORD_LENGTH; i++)
        { 
            int index = (int)(RANDOM.nextDouble()*letters.length());
            passWord += letters.substring(index, index+1);
        }
        return passWord;
    }
	
	/**
	 *  This method is used to get the forgot password
	 *  @author raja_r
	 *  @return String
	 *  @since Dec-9-2013
	 *   
	 */
	@Action (value="/forgetPassword")
	public void forgetPassword() throws AddressException, MessagingException{ 
		JsonHelper jsonHelper= new JsonHelper(); 
		List usernameCheck = orgPersonService.getForgetPassword(username,organizationid);
		
		if(usernameCheck.size()==0){ 
 			jsonHelper.toJsonString("invalid");
		}else{
 			if(usernameCheck.size()!=0) {
				for(int i=0;i<usernameCheck.size();i++) {
					Map loginMap = (Map)usernameCheck.get(i);
					Login login = new Login(); 			  
					login.setLoginid(Integer.parseInt(""+loginMap.get("loginid")));
					login.setUserid(""+loginMap.get("username"));   
					login.setPassword(generateRandomPassword());  
					login.setLoginstatus('A');   
					login.setDateofattempt(new Date()); 
					loginService.update(login);  
					
					ResourceBundle rb = ResourceBundle.getBundle("smtpconfig"); 
					String smtphostname		=	rb.getString("smtp_hostname");
					String smtpport		    =	rb.getString("smtp_hostport");
					String admin_mailid		=	rb.getString("admin_mailid"); 
					String admin_mailpassword	=	rb.getString("admin_mailpassword"); 
					String passwordsuccess = ap.getString("label.passwordsuccess");

					Properties props = System.getProperties();
					props.put("mail.smtp.host", smtphostname);
					props.put("mail.smtp.socketFactory.port", smtpport);
					props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", smtpport); 

					Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
					Session session = Session.getDefaultInstance(props,auth);
					session.setDebug(true);
					
 					List<?> emailreaddata = orgPersonService.emailContent(Long.parseLong(organizationid));
					getMailValue(emailreaddata);
 					
					String mail_html_cont ="";			    
				    mail_html_cont += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>"; 
				    mail_html_cont += "<table width='600' border='0' align='center' cellpadding='0' cellspacing='0'" +
				    		" style=' border: 1px solid rgb(225, 225, 243);'>  <tr>    <td align='left' valign='top'>" +
				    		"<div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; " +
				    		"height: 56px; padding: 8px;'>"+productname+"</div></td>  </tr>  <tr>    " +
				    		"<td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white;" +
				    		" font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
				    		"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>      " +
				    		"  <tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; " +
				    		"color:#525252;'> <br> <h4><font face='Verdana' size=\"3\" color='#525252;'><b>Dear "+loginMap.get("firstname")+", </font></h4>  " +
				    				"<div style='font-size:28px;color:rgb(179, 141, 92);'>"+passwordsuccess+"" +
				    		" </div>  <div> <div><h2>Your credentials details are</h2></div> <div style='margin-left: 90px;'><br>" +
				    		"  <span>Username </span>: <span>"+username+"</span> <br>   <br><b>Password </b>: " +
				    		"<span>"+login.getPassword()+"</span> <br><br><a " +
				    		"  href='http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width: 118px;text-decoration: none;color: white;'>Click here to login</a><br>              <br>           </div> </div><h3>Thanks,<br>The Reamedi Team.</h3></td>        </tr>      </table></td>  </tr>  <tr>    <td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>      <tr>        <td align='left' valign='top' style='color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px;'> "+emailorganizationname+" <br>Contact :"+emailfirstname+" "+emaillastname+" <br>          Phone: "+emailnumber+" <br>          Email: <a href='mailto:"+emailemailid+"' style='color:#ffffff; text-decoration:none;'>"+emailemailid+"</a><br>          Website: <a href='http://"+emailurl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+emailurl+"</a></td>      </tr>    </table></td>  </tr></table>";
				  	mail_html_cont += "</body>";
					mail_html_cont += "</html>";    
 					MimeMessage msg = new MimeMessage(session);  
					msg.setFrom(new InternetAddress(admin_mailid));   
					msg.setRecipients(Message.RecipientType.TO, ""+loginMap.get("userid"));   

					msg.setSubject("Forgot password");  
					msg.setContent(mail_html_cont.toString(),"text/html"); 
					Transport.send(msg);
 				}
			}  
  			jsonHelper.toJsonString("valid");  
		}
	}

	
	@Action (value="/app/getUserNameCheck")
	public void getUserNameCheck(){
		try
		{
			jsonHelper= new JsonHelper(); 
			List<?> usernameCheck = UserViewService.getUsernameCheck(username); 
			if(usernameCheck.size()!=0)
			{
				jsonHelper.toJsonString("Success");
			} 
			else
			{
				jsonHelper.toJsonString("Exits");
			}
		}
		catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}
	}
	
	@Action (value="/app/getPasswordCheck")
	public void getPasswordCheck(){
		try
		{
			jsonHelper= new JsonHelper(); 
			List<?> usernameCheck = UserViewService.getPasswordCheck(username,password); 
			if(usernameCheck.size()!=0)
			{
				jsonHelper.toJsonString("Success");
			}
			else
			{
				jsonHelper.toJsonString("Exits");
			}
		}
		catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}
	}
	
	@Action(value="/masterId")
	public void getMasterOrganizationid(){
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		
		try
		{
			List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
			if(MasterOrgId.size()!=0) {
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
			    requestAttributes.getRequest().getSession().setAttribute("organizationid",map.get("organizationid"));
				checkobj().toJsonResponse(MasterOrgId);
			}  else {
				checkobj().toJsonString("false"); 
			}
		}
		catch(Exception e)
		{
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in getMasterOrganizationid() in LoginAction",e);
			}
		}
	}
	
	 
	@Action (value="/app/getUsernamePasswordCheck")
	public void getUsernamePasswordCheck(){
		try
		{
			jsonHelper= new JsonHelper(); 
			List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
			Map<?,?> map1=(Map<?, ?>) MasterOrgId.get(0);
			List<?> usernameCheck = UserViewService.getUsernamePasswordCheck(username,password,""+map1.get("organizationid")); 
			Map valueMap = new HashMap();
			List valueMapList = new ArrayList();
			String orgpersonid = null;
			if(usernameCheck.size()!=0){  
				for(int i=0;i<usernameCheck.size();i++) {
					Map map =(Map)usernameCheck.get(i);
					String firstname = ""+map.get("firstname");
					String lastname = ""+map.get("lastname");
					String loginid = ""+map.get("loginid");
					orgpersonid = ""+map.get("orgpersonid");

					String fullname=null;  

					if(lastname==null || lastname.equalsIgnoreCase("null") || lastname=="null" || lastname==""  || lastname.equalsIgnoreCase("") )
					{
					  fullname = firstname;
					} else {
					  fullname = firstname+" "+lastname;	
					}
					valueMap.put("fullname", fullname); 
					valueMap.put("firstname", firstname);
					valueMap.put("lastname", lastname);
					valueMap.put("loginid", loginid);
					valueMap.put("orgpersonid", orgpersonid);
					
   				}
				
				List getPresenterDetail = UserViewService.getPresenterAppDetails(orgpersonid); 
 				if(getPresenterDetail.size()!=0) { 
					for(int i=0;i<getPresenterDetail.size();i++) {
						Map map =(Map)getPresenterDetail.get(i); 
						String typename 	   = ""+map.get("typename");
						String applicationname = ""+map.get("applicationname");
						String logincount 	   = ""+map.get("logincount");
						String workingduration = ""+map.get("workingduration");
						String licenseapppath  = ""+map.get("licenseapppath");
						valueMap.put("typename", typename); 
						valueMap.put("logincount", logincount);
						valueMap.put("applicationname", applicationname);
						valueMap.put("workingduration", workingduration);  
						valueMap.put("licenseapppath", licenseapppath);   
	 				} 
				} else {
					valueMap.put("typename", "-"); 
					valueMap.put("logincount", "0");
					valueMap.put("applicationname",  "-");
					valueMap.put("workingduration",  "-");  
					valueMap.put("licenseapppath",  "-");  
				}
 				
				valueMapList.add(valueMap);  

				jsonHelper.toJsonResponse(valueMapList);
			}
			else
			{
				jsonHelper.toJsonString("Exits");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(LoginActionlogger.isInfoEnabled())
			{
				LoginActionlogger.error("error in newSignUp() in LoginAction",e);
			}
		}
	}
	
	public void getMailValue(List<?> emailreaddata){
		HashMap alldata=(HashMap) emailreaddata.get(0);
		emailnumber=""+alldata.get("number");
		emailemailid=""+alldata.get("emailid");
		emailurl=""+alldata.get("url");
		emailfirstname=""+alldata.get("firstname");
		emaillastname=""+alldata.get("lastname");
		emailorganizationname=""+alldata.get("organizationname");
	}
	 
	public void setSession(Map<String, Object> session) {
		this.session = session;
	} 

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}


	public String getEmailid() {
		return emailid;
	}


	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}



	public String getOrganizationid() {
		return organizationid;
	}



	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrgpersonid() {
		return orgpersonid;
	}



	public void setOrgpersonid(String orgpersonid) {
		this.orgpersonid = orgpersonid;
	}



	public String getPersonid() {
		return personid;
	}



	public void setPersonid(String personid) {
		this.personid = personid;
	}



	public String getLoginid() {
		return loginid;
	}



	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}



	public String getCourseinvitationid() {
		return courseinvitationid;
	}



	public void setCourseinvitationid(String courseinvitationid) {
		this.courseinvitationid = courseinvitationid;
	}
	
	
	
}
