package com.igrandee.product.ecloud.action.organization;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Email;
import com.igrandee.core.contact.model.Phone;
import com.igrandee.core.contact.model.Url;
import com.igrandee.core.login.model.Login;
import com.igrandee.core.organization.model.Department;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.core.organization.model.Role;
import com.igrandee.core.organization.service.OrganizationService;
import com.igrandee.core.person.model.Person;
import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.helper.JsonHelper;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Orgplansubscription;
import com.igrandee.product.ecloud.model.Personallocation;
import com.igrandee.product.ecloud.model.Plan;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.igrandee.product.ecloud.service.organization.OrganizationCourseViewService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.service.organization.OrganizationWiseUsersService;
import com.igrandee.product.ecloud.service.plan.PlanService;
import com.igrandee.product.ecloud.service.plan.PlanSubscriptionService;
import com.igrandee.product.ecloud.service.users.UserViewService;
import com.igrandee.product.ecloud.util.commons.IgCommons;



public class OrganizationAction extends MasterActionSupport
{
	private static Logger OrganizationActionlogger = Logger.getLogger(OrganizationAction.class);
	
	@Inject
	OrgPersonService orgPersonService;
	
	@Inject
	private OrganizationViewService organizationViewService;
	
	@Inject
	OrganizationService organizationService; 
	
	@Inject
	OrganizationWiseUsersService organizationWiseUsers;
	
	@Inject
	OrganizationCourseViewService organizationCourseViewService;
	
	@Inject
	PlanSubscriptionService plansubscription;
	
	@Inject
	UserViewService userViewService;
	
	@Inject
	CourseListService courseListService;
	
	@Inject
	private PlanService planService;
	
	@Inject
	private PlanSubscriptionService planSubscriptionService;
	
	@Inject
	private IgCommons igCommons;
	
	private Organization organizationobj;
	private Department departmentobj;
	private Contactinfo contactinfoobj;
	private Person personobj;
	private Role roleobj;
	private Personallocation personallocationobj;
	private Url urlobj;
	private Email emailobj;
	private Phone phoneobj;
	private Login loginobj;
	private Orgperson orgpersonobj;	
	private OrganizationCourseInsertAction organizationcourseinsertaAction;
	private String email;
	private String contactno;
	private String organizationurl;	
	private String orgfirstname;
	private String orgpassword;
	private String orgusername;
	private String organizationname;
	private File fileUpload;  
	private String fileUploadFileName;
	private int sizeoforg; 
	private int organid;	
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String emailid;
	private String targetUrl;
	private String parentorgid;
	private Long organizationid; 
	private String orgpersonid;
	private String phoneid;
	private String contactinfoid;
	private String urlid;
	private String orglogo;
	private Orgplansubscription orgplansubscription;
	private Plan plan;
	private String url;
	private String domainurl;
	private String status;
	
	
	

	/**
	 * @author williambenjamin_g
	 * Method to Redirect to create Organization
	 * @return success string
	 */
		
	@Action(value="/organizationinfo",results={@Result(name="SUCCESS",location="app/user/courseSubscribe.jsp")})
	public String organization(){
		try	{
			
			
			setFirstname(orgfirstname);
			setLastname(lastname);  
			setUsername(email);    
 			setPassword(orgpassword);
			setTargetUrl("/myorganization");			
			personobj = new Person();
			roleobj = new Role();
			personallocationobj= new Personallocation();
			organizationobj=new Organization();
			urlobj = new Url();
			emailobj = new Email();
			phoneobj = new Phone();
			loginobj = new Login();
			orgpersonobj = new Orgperson();
			contactinfoobj=new Contactinfo();
			departmentobj=new Department();
			organizationcourseinsertaAction=new OrganizationCourseInsertAction();
			
			HashSet<Url> urls = new HashSet<Url>();
			HashSet<Phone> phones = new HashSet<Phone>();
			HashSet<Email> emails = new HashSet<Email>();
			HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();
			
			
			emailobj.setUserid(email);
			emailobj.setEmailstatus('A');			
			emailobj.setContactinfo(contactinfoobj);
			emails.add(emailobj);		
			
			phoneobj.setNumber(contactno);
			phoneobj.setPhonestatus('A');
			phoneobj.setContactinfo(contactinfoobj);
			phones.add(phoneobj);
			
			urlobj.setUrl(url);
			urlobj.setUrlstatus('A');
			urlobj.setContactinfo(contactinfoobj);
			urls.add(urlobj);
			
			contactinfoobj.setContactinfostatus('A');
			contactinfoobj.setUrls(urls);
			contactinfoobj.setPhones(phones);
			contactinfoobj.setEmails(emails);
			
			personobj.setFirstName(orgfirstname);
			personobj.setPersonstatus('A'); 
			personobj.setContactinfo(contactinfoobj);
			loginobj.setPassword(orgpassword);
			loginobj.setUserid(email);
			loginobj.setLoginstatus('A');
			loginobj.setPasswordstat("A");	
			
  			roleobj.setRolename("admin");
			roleobj.setRolestatus('A');
			roleobj.setOrganization(organizationobj);
			
 			departmentobj.setDepartmentname("admin");
			departmentobj.setDepartmentstatus('A');
			departmentobj.setOrganization(organizationobj);	
			
			orgpersonobj.setLogin(loginobj);
			orgpersonobj.setPersonid(personobj);
			orgpersonobj.setOrganizationid(organizationobj);
			orgpersonobj.setOrgpersonstatus('A');
			
			personallocationobj.setRole(roleobj);
			personallocationobj.setDepartment(departmentobj);
			personallocationSet.add(personallocationobj); 			
			orgpersonobj.setPersonallocations(personallocationSet);
			
			organizationobj.setOrgname(organizationname);		
			organizationobj.setLogopath(organizationcourseinsertaAction.fileCreateUpload(fileUpload,fileUploadFileName));
			organizationobj.setSizeoforg(sizeoforg);
			organizationobj.setDomainurl(domainurl);
			organizationobj.setCreatedon(new Date(System.currentTimeMillis())); 
			organizationobj.setOrgstatus('A');
			organizationobj.setContactinfo(contactinfoobj);			
			organizationobj.setParentorgid(Long.parseLong(parentorgid));			
 			Long orgid = organizationService.save(organizationobj);
 			organizationobj.setOrganizationid(orgid);
 			
 			plan=new Plan(); 
 			List<?> plandetails = organizationCourseViewService.getPlandetails();
 			 String durationtype = null;
 			 String duration	 = null; 
 			if(plandetails.size()==1 && plandetails!=null) { 				
 	 	 	   Map personalMap = (Map)plandetails.get(0);
 	 	 	   String planid = ""+personalMap.get("planid"); 	
 	 	 	   plan.setId(Integer.parseInt(planid));
 	 	 	   
 	 	 	   durationtype = ""+personalMap.get("durationtype");
 	 	 	   duration = ""+personalMap.get("duration");
 	 	 	 }  			
 			orgplansubscription=new Orgplansubscription();
 			orgplansubscription.setPlan(plan);
 			
 			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			Date date = new Date(System.currentTimeMillis());
 			String currentdate = dateFormat.format(date);
 			orgplansubscription.setDateofsubscription(new Date(System.currentTimeMillis()));
 			orgplansubscription.setPlanstartdate(new Date(System.currentTimeMillis()));
 			Calendar cal = Calendar.getInstance();    
 			cal.setTime( dateFormat.parse(currentdate));
 			if( durationtype.equals("Annual")){
 				cal.add(Calendar.YEAR,Integer.parseInt(duration));
 				String convertedDate=dateFormat.format(cal.getTime());    
 				orgplansubscription.setPlanenddate(dateFormat.parse(convertedDate));
 			}
 			else if(durationtype.equals("Monthly")){
 				cal.add(Calendar.MONTH,Integer.parseInt(duration));
 				String convertedDate=dateFormat.format(cal.getTime());    
 				orgplansubscription.setPlanenddate(dateFormat.parse(convertedDate));
 			}
 			orgplansubscription.setOrgplanstatus("A");
 			orgplansubscription.setOrganization(organizationobj);			    
			Integer plansubid=plansubscription.save(orgplansubscription);	
			
			orgpersonobj.setOrganizationid(organizationobj);
 			int orgpersonid=orgPersonService.save(orgpersonobj);
 			setOrganizationid(orgid); 			
 			setOrganid(orgpersonid);
 			setOrganizationname(organizationname);
			List<?> orglogo = organizationWiseUsers.getOrganizationdetail(orgid);
			if(orglogo.size()>0 && orglogo!=null){
				Map orglogopath = (Map) orglogo.get(0);
				String logopath = ""+orglogopath.get("logopath");
				setOrglogo(logopath);
			}
			subscriptionplan(plansubid,url,email,contactno,orgfirstname);
			insertDefaultColorStyle(orgid);
 		}
		catch(Exception e){
			if(OrganizationActionlogger.isInfoEnabled())
			{
				OrganizationActionlogger.error("error in organization() in OrganizationAction",e);
			}
		}
    	return "SUCCESS";
	}	
	

	private void insertDefaultColorStyle(Long orgid) {
		// TODO Auto-generated method stub
		organizationViewService.setDefaultThemeColor(orgid.toString(), "1");
	}


	private void subscriptionplan(Integer plansubid, String url, String email, String contactno, String orgfirstname) throws AddressException, MessagingException, ParseException{
		List<?> planList= null;
		List<?> planSubscripList=null;
		
		String planstartdate = null;
		String planenddate	 = null;
		String planname		 = null;
		String maxcourse 	 = null;
		String maxusers 	 = null;
		String duration 	 = null;
 		
 		planList= planService.getBasicplans();
 		
		planSubscripList=planSubscriptionService.getPlanSubscription(plansubid);
 		
		if(planList.size()==1 && planList!=null) { 				
	 	 	   Map personalMap = (Map)planList.get(0);
	 	 	   planname = ""+personalMap.get("name");
	 	 	   maxcourse = ""+personalMap.get("maxcourse");
	 	 	   maxusers = ""+personalMap.get("maxusers");
	 	 	   duration = ""+personalMap.get("duration");
	 	 	   Map plansubscripMap = (Map)planSubscripList.get(0);
	 	 	   planstartdate	=	""+plansubscripMap.get("planstartdate");
	 	 	   planenddate		=	""+plansubscripMap.get("planenddate");
	 	 	
	 	 	 }
		
		if (email != null) {
				
			ResourceBundle ap = ResourceBundle.getBundle("application"); 

			String productname = ap.getString("label.productname");
			
				String mailSubject = "Plan Subscription";
				String mailContent ="";		
				String recipientType = "To";
				boolean isMailContentHtml = true;
				mailContent += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
				mailContent +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
						"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> " +
						"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
						"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> " +
						"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'><h3>Dear "+orgfirstname+"</h3> <h3 style='color: rgb(185, 138, 116);'>Congratulations!</h3>You have successfully subscribed "+planname+". This plan is allowed</h4> maximumcourse :"+maxcourse+",maximumusers :"+maxusers+" ,duration :"+duration+".This plan will start:"+planstartdate+" and plan end date :"+planenddate+".<br>"+
						"<div> <br><h2>Your Account details are</h2><div style='margin-left: 90px;'><br>  <b>UserName </b>: "+username+" <br>   <br><b>Password </b>: "+password+"</div>"+
						"<div><h4>Thank you.</h4><p>The Reamedi Team</p></div>"+						
						"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
						"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
						"<tr><td><font color='#ffffff'>Contact :"+orgfirstname+"</font><br><font color='#ffffff'>Phone:"+contactno+"</font><br><font color='#ffffff'>Email:"+email+"</font><a href='mailto:"+email+" style='color:#ffffff; text-decoration:none;'></a><br> " +
						"<font color='#ffffff'>Website:"+url+"</font><a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'></a></td></tr></table></td></tr></table>";
				mailContent += "</body>";
				mailContent += "</html>";	
				igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
		}
	}



	@Action(value="/orgupload",results={@Result(name="SUCCESS",location="app/organization/myorganization.jsp")})
	public String organizationlogo(){
		try{
			organizationobj=new Organization();
			organizationcourseinsertaAction=new OrganizationCourseInsertAction();
			String logo= organizationcourseinsertaAction.fileCreateUpload(fileUpload,fileUploadFileName);
			organizationCourseViewService.updateOrganizationLogo(organizationid,logo);
		}catch(Exception e){
			if(OrganizationActionlogger.isInfoEnabled())
			{
				OrganizationActionlogger.error("error in organizationlogo() in OrganizationAction",e);
			}
		}
    	return "SUCCESS";		
	}
	
	@Action(value="/organizationupdate",results={@Result(name="SUCCESS",location="app/organization/myorganization.jsp")})
	public String organizationupdate(){
		try{
			organizationobj=new Organization();
			urlobj = new Url();
			emailobj = new Email();
			phoneobj = new Phone();			
			contactinfoobj=new Contactinfo();	
			
			HashSet<Url> urls = new HashSet<Url>();
			HashSet<Phone> phones = new HashSet<Phone>();
			HashSet<Email> emails = new HashSet<Email>();	
			
			emailobj.setEmailid(Long.parseLong(emailid));
			emailobj.setUserid(email);
			emailobj.setEmailstatus('A');
			emailobj.setContactinfo(contactinfoobj);
			emails.add(emailobj);		
			
			phoneobj.setPhoneid(Long.parseLong(phoneid));
			phoneobj.setNumber(contactno);
			phoneobj.setPhonestatus('A');
			phoneobj.setContactinfo(contactinfoobj);
			phones.add(phoneobj);	
			
			urlobj.setUrlid(Long.parseLong(urlid));
			urlobj.setUrl(organizationurl);
			urlobj.setUrlstatus('A');
			urlobj.setContactinfo(contactinfoobj);
			urls.add(urlobj);	
			
			contactinfoobj.setContactinfoid(Long.parseLong(contactinfoid));
			contactinfoobj.setContactinfostatus('A');
			contactinfoobj.setUrls(urls);
			contactinfoobj.setPhones(phones);
			contactinfoobj.setEmails(emails);	
			
			organizationobj.setOrganizationid(organizationid);
			organizationobj.setOrgname(organizationname);		
			organizationobj.setLogopath(orglogo);
			organizationobj.setSizeoforg(sizeoforg);
			organizationobj.setDomainurl(domainurl);
			organizationobj.setCreatedon(new Date(System.currentTimeMillis())); 
			
			organizationobj.setOrgstatus((""+getAttribute("orgstatus")).charAt(0));
			
			organizationobj.setContactinfo(contactinfoobj);	
			organizationService.saveOrUpdate(organizationobj); 		
			
		}catch(Exception e){
			if(OrganizationActionlogger.isInfoEnabled())
			{
				OrganizationActionlogger.error("error in organizationupdate() in OrganizationAction",e);
			}
		}
    	return "SUCCESS";
		
	}

	/** 
	 * @author muniyarasu_a
	 * This method is used to check the organization name and emailid
	 * @return organizationnamelist 
	 */
	@Action(value="/checkorgname")
	public void getOrganizationNameList(){
		List<?> getOrganizationList = organizationViewService.getOrganizationlist(organizationname);
		JsonHelper jsonHelper = new JsonHelper();
		if(getOrganizationList.size()==0){
			jsonHelper.toJsonString("success");
		}else{
			jsonHelper.toJsonString("nameexists");
		}
	}
	
	@Action(value="/checkemail")
	public void getcheckEmailList(){
		List<?> getEmailList = organizationViewService.getEmailList(email);
		JsonHelper jsonHelper = new JsonHelper();
		if(getEmailList.size()==0){
			jsonHelper.toJsonString("success");
		}else{
			jsonHelper.toJsonString("emailexists");
		}
	}
	
	@Action(value="/getOrganizationWiseUsers")
	public void getOrganizationWiseUserList(){
		List<?> getOrganizationWiseUserList = organizationWiseUsers.getorganizationwiseuserslist(Long.parseLong(getAttribute("organizationid")));
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.toJsonResponse(getOrganizationWiseUserList);
	}
	
	@Action(value="/getOrganizationUsers")
	public void getOrganizationUsers(){
		List<?> getOrganizationWiseUserList = organizationWiseUsers.getorganizationwiseuserslist(organizationid);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.toJsonResponse(getOrganizationWiseUserList);
	}
	
	@Action(value="/organizationCategory")
	public void getorganizationCategory(){
		List<?> getAllOrganization = organizationWiseUsers.getAllOrganization();
		JsonHelper jsonHelper = new JsonHelper();
		if(getAllOrganization.size()!=0){
			jsonHelper.toJsonResponse(getAllOrganization);
		}else{
			jsonHelper.toJsonString("failure");
		}		
	}
	
	@Action(value="/organizationplan")
	public void organizationplan(){	
	List<?> getOrganizationPlan=null;
		try{
			getOrganizationPlan = organizationWiseUsers.getOrganizationPlandetails(organizationid,status);
			JsonHelper jsonHelper = new JsonHelper();
			if(getOrganizationPlan.size()!=0){
				jsonHelper.toJsonResponse(getOrganizationPlan);
			}else{
				jsonHelper.toJsonString("failure");
			}
		}catch(Exception e){
			if(OrganizationActionlogger.isInfoEnabled())
			{
				OrganizationActionlogger.error("error in organizationplan() in OrganizationAction",e);
			}
		}
    }
	
	@Action(value="/organizationusers")
	public void organizationusers(){
		List<?> organizationusers=null;
		try{
			organizationusers = userViewService.OrganizationUsers(Long.toString(organizationid),getAttribute("personid"));						
			JsonHelper jsonHelper = new JsonHelper();
			if(organizationusers.size()!=0){
				jsonHelper.toJsonResponse(organizationusers);
			}else{
				jsonHelper.toJsonString("failure");
			}
		}catch(Exception e){
			if(OrganizationActionlogger.isInfoEnabled())
			{
				OrganizationActionlogger.error("error in organizationusers() in OrganizationAction",e);
			}
		}
    }
	
	@Action(value="/organizationcourses")
 	public void loadOtherCourses(){		
		List<?> organizationcourselist=null;
		try	{
 			organizationcourselist=courseListService.organizationCourses(Long.parseLong(orgpersonid));	
 			if(organizationcourselist.size()!=0){
				checkobj().toJsonResponse(organizationcourselist);
			}else{
				checkobj().toJsonString("failure");
			}
		}
		catch(Exception e) {
			if(OrganizationActionlogger.isInfoEnabled())	{
				OrganizationActionlogger.error("error in loadOtherCourses() in OrganizationAction"+e);
			}	
		} 
	}
	@Action(value="/getParicularOrganizationdetails")
	public void getParicularOrganizationdetails(){
		List<?> getParicularOrganizationdetailsList = organizationCourseViewService.getParicularOrganizationdetails(Integer.parseInt(getAttribute("orgpersonid")));
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.toJsonResponse(getParicularOrganizationdetailsList);
	}
	
	public OrgPersonService getOrgPersonService() {
		return orgPersonService;
	}

	public void setOrgPersonService(OrgPersonService orgPersonService) {
		this.orgPersonService = orgPersonService;
	}

	public Organization getOrganizationobj() {
		return organizationobj;
	}

	public void setOrganizationobj(Organization organizationobj) {
		this.organizationobj = organizationobj;
	}

	public Department getDepartmentobj() {
		return departmentobj;
	}

	public void setDepartmentobj(Department departmentobj) {
		this.departmentobj = departmentobj;
	}

	public Contactinfo getContactinfoobj() {
		return contactinfoobj;
	}

	public void setContactinfoobj(Contactinfo contactinfoobj) {
		this.contactinfoobj = contactinfoobj;
	}

	public Person getPersonobj() {
		return personobj;
	}

	public void setPersonobj(Person personobj) {
		this.personobj = personobj;
	}

	public Role getRoleobj() {
		return roleobj;
	}

	public void setRoleobj(Role roleobj) {
		this.roleobj = roleobj;
	}

	public Personallocation getPersonallocationobj() {
		return personallocationobj;
	}

	public void setPersonallocationobj(Personallocation personallocationobj) {
		this.personallocationobj = personallocationobj;
	}

	public Url getUrlobj() {
		return urlobj;
	}

	public void setUrlobj(Url urlobj) {
		this.urlobj = urlobj;
	}

	public Email getEmailobj() {
		return emailobj;
	}

	public void setEmailobj(Email emailobj) {
		this.emailobj = emailobj;
	}

	public Phone getPhoneobj() {
		return phoneobj;
	}

	public void setPhoneobj(Phone phoneobj) {
		this.phoneobj = phoneobj;
	}

	public Login getLoginobj() {
		return loginobj;
	}

	public void setLoginobj(Login loginobj) {
		this.loginobj = loginobj;
	}

	public Orgperson getOrgpersonobj() {
		return orgpersonobj;
	}

	public void setOrgpersonobj(Orgperson orgpersonobj) {
		this.orgpersonobj = orgpersonobj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getOrganizationurl() {
		return organizationurl;
	}

	public void setOrganizationurl(String organizationurl) {
		this.organizationurl = organizationurl;
	}

	public String getOrgfirstname() {
		return orgfirstname;
	}

	public void setOrgfirstname(String orgfirstname) {
		this.orgfirstname = orgfirstname;
	}

	public String getOrgpassword() {
		return orgpassword;
	}

	public void setOrgpassword(String orgpassword) {
		this.orgpassword = orgpassword;
	}

	public String getOrgusername() {
		return orgusername;
	}

	public void setOrgusername(String orgusername) {
		this.orgusername = orgusername;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public int getOrganid() {
		return organid;
	}

	public void setOrganid(int organid) {
		this.organid = organid;
	}

	public int getSizeoforg() {
		return sizeoforg;
	}

	public void setSizeoforg(int sizeoforg) {
		this.sizeoforg = sizeoforg;
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



	public String getEmailid() {
		return emailid;
	}



	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}



	public String getTargetUrl() {
		return targetUrl;
	}



	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}



	public String getParentorgid() {
		return parentorgid;
	}



	public void setParentorgid(String parentorgid) {
		this.parentorgid = parentorgid;
	}	

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public Long getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(Long organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrgpersonid() {
		return orgpersonid;
	}

	public void setOrgpersonid(String orgpersonid) {
		this.orgpersonid = orgpersonid;
	}

	public String getPhoneid() {
		return phoneid;
	}

	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
	}

	public String getContactinfoid() {
		return contactinfoid;
	}

	public void setContactinfoid(String contactinfoid) {
		this.contactinfoid = contactinfoid;
	}

	public String getUrlid() {
		return urlid;
	}

	public void setUrlid(String urlid) {
		this.urlid = urlid;
	}

	public String getOrglogo() {
		return orglogo;
	}

	public void setOrglogo(String orglogo) {
		this.orglogo = orglogo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getDomainurl() {
		return domainurl;
	}
	public void setDomainurl(String domainurl) {
		this.domainurl = domainurl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
