package com.igrandee.product.ecloud.action.organization;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Email;
import com.igrandee.core.login.model.Login;
import com.igrandee.core.login.service.LoginService;
import com.igrandee.core.organization.model.Department;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.core.organization.model.Role;
import com.igrandee.core.person.model.Person;
import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.helper.AuthorEmailWrite;
import com.igrandee.product.ecloud.helper.EmailWriter;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Courseinvitation;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Orgplansubscription;
import com.igrandee.product.ecloud.model.Orgplanuser;
import com.igrandee.product.ecloud.model.Personallocation;
import com.igrandee.product.ecloud.service.Pages.user.GiftCourseService;
import com.igrandee.product.ecloud.service.admin.PersonallocationService;
import com.igrandee.product.ecloud.service.course.CourseInvitationService;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.igrandee.product.ecloud.service.livesession.LivesessionService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.service.plan.PlanService;
import com.igrandee.product.ecloud.service.plan.PlanSubscriptionService;
import com.igrandee.product.ecloud.util.commons.IgCommons;
import com.sun.jersey.api.core.InjectParam;

public class OrganizationCourses extends MasterActionSupport {

	private static final long serialVersionUID = 1L;
	private static final int PASSWORD_LENGTH = 8;
	private static final Random RANDOM = new SecureRandom();

	private static Logger OrganizationCourseslogger = Logger
			.getLogger(OrganizationCourses.class);
	private String firstname;

	@Inject
	private CourseListService courseListService;

	@Inject
	private OrganizationViewService organizationViewService;

	@Inject
	private OrgPersonService orgpersonservice;

	@Inject
	private Orgperson orgperson;

	@Inject
	private LoginService loginservice;

	@Inject
	private EmailWriter emailWriter;

	@Inject 
	private CourseInvitationService courseinvitationservice;

	@Inject
	PersonallocationService personallocationService;

	@Inject
	private AuthorEmailWrite authorwrite;

	@Inject
	private IgCommons igCommons;

	@Inject
	private PlanService planService;
	@Inject
	private GiftCourseService giftCourseService;

	@Inject
	private PlanSubscriptionService planSubscriptionService;
	
	@Inject
	private LivesessionService livesessionService;
	
	private String courseid;
	private String organizationid;	

	private String email;
	private String description;
	private String orgpersonid;
	private String username;
	private String password;
	private String emailaddress;
	private String targetUrl;
	private String royaltydate;
	private String message;
	private String invitationid;
	private String attachmentpath;
	private String planid;
	private String planSubscriptionid;
	private String giftcourseid;
	private String status;
	private String livesessionenrollid;

	/**
	 * @author williambenjamin_g Method to get All non valid courses for a
	 *         passing course status in organization
	 * 
	 */
	@Action(value = "/loadOrgCourseContent")
	public void loadOrgCourseContent() {
		try {
			List<?> courseContentList = courseListService
					.getCoursePreviewData(Integer.parseInt(courseid));
			checkobj().toJsonResponse(courseContentList);
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger.error("error in loadCourseContent() in OrganizationCourses", e);
			}
		}
	}

	@Action(value = "/orgParicularCourse")
	public void orgParicularCourse() {
		try {
			List<?> orgCourseContentList = organizationViewService.getParticularCourse(Long.parseLong(getAttribute("organizationid")));
			checkobj().toJsonResponse(orgCourseContentList);
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger.error("error in orgParicularCourse() in OrganizationCourses", e);
			}
		}
	}

	/**
	 * Analystic Action
	 * @author venkatraman_v
	 */

	@Action(value = "/orgAnalysticCourse")
	public void orgAnalysticCourse() {
		try {
			List<?> orgCourseContentList = organizationViewService.getcourseAnalysisList(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(orgCourseContentList);
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in orgParicularCourse() in OrganizationCourses"
						, e);
			}
		}
	}


	@Action(value = "/courseenrolledusers")
	public void courseenrolledusers() {
		try {
			List enrolledusers=organizationViewService.getstatuswiseusers(Integer.parseInt(courseid),status,Long.parseLong(getAttribute("organizationid")));
			checkobj().toJsonResponse(enrolledusers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Action(value = "/suborgloginCheck")
	public void suborgloginCheck() {
		List<Map<?, ?>> orgCourseContentList =null;
		try {
			if((organizationid!=null)&&(username!=null)&&(password!=null)){
				orgCourseContentList = organizationViewService.getsubOrganizationInfo(organizationid,username,password);
				if(orgCourseContentList.size()!=0){
					if(orgCourseContentList.get(0).get("loginstatus").toString().equals("A")&&orgCourseContentList.get(0).get("orgstatus").toString().equals("A")){
						checkobj().toJsonString("success");					
					}else if(orgCourseContentList.get(0).get("orgstatus").toString().equals("D")){
						checkobj().toJsonString("deactivated");
					}	
				}
				else{
					checkobj().toJsonString("failure");
				}
			}
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger.error("error in orgParicularCourse() in OrganizationCourses", e);
			}
		}
	}

	@Action(value = "/getParticularCourses")
	public void getParticularCourses() {
		try {
			List<?> orgCourseContentList = organizationViewService
					.getParticularCourse(Long.parseLong(organizationid));
			checkobj().toJsonResponse(orgCourseContentList);
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in getParticularCourses() in OrganizationCourses"
						, e);
			}
		}
	}

	public String generateRandomPassword() {
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
		String passWord = "";
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			passWord += letters.substring(index, index + 1);
		}
		return passWord;
	}

	@Action(value = "/userlogin")
	public void userlogin() throws AddressException, MessagingException, ParseException, UnsupportedEncodingException {
		String status = null;
		if (email != null) {			
			setOrganizationid(getAttribute("organizationid"));
			String emailarray[] = email.split(",");
			if(getAttribute("orgstatus").equalsIgnoreCase("A")){
				for (int i = 0; i < emailarray.length; i++) {

					List<?> orgPlanLists =planSubscriptionService.getPlanSubscriptions("A",Long.parseLong(getAttribute("organizationid")));

					Map<?,?> map=(Map<?, ?>) orgPlanLists.get(0);
					String orgplansubscriptionids= ""+map.get("id");

					List<?> planList = planSubscriptionService.getorgplanuser(Integer.parseInt(orgplansubscriptionids));
					if(planList.size()>0){
						Map<?,?> map1=(Map<?, ?>) orgPlanLists.get(0);
						String maxuserss= ""+map1.get("maxusers");
						int maxusers = Integer.parseInt(maxuserss);
						int maxuser =planList.size();
						if(maxusers>maxuser){
							Courseinvitation courseinvitationobj=new Courseinvitation();
							Course courseobj =new Course();
							Orgperson orgpersonobj=new Orgperson();  
							Orgplansubscription orgplansubscription = new Orgplansubscription();
							Orgplanuser orgplanuser = new Orgplanuser();
							if(courseid!=null){
								courseobj.setCourseid(Integer.parseInt(courseid));
								courseinvitationobj.setCourse(courseobj);
							}
							orgpersonobj.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
							courseinvitationobj.setOrgperson(orgpersonobj);
							courseinvitationobj.setEmailid(emailarray[i]);
							courseinvitationobj.setMessage(java.net.URLDecoder.decode(message));
							courseinvitationobj.setAttachmentpath(attachmentpath);
							courseinvitationobj.setCourseinvitationstatus('I');
							courseinvitationobj.setDateofinvitation(new Date(System.currentTimeMillis()));
							if(getAttribute("orgstatus").equalsIgnoreCase("A")){
								List<?> orgPlanList =planSubscriptionService.getPlanSubscriptions("A",Long.parseLong(getAttribute("organizationid")));

								Map<?,?> maps=(Map<?, ?>) orgPlanList.get(0);
								String orgplansubscriptionid= ""+maps.get("id");

								orgplansubscription.setId(Integer.parseInt(orgplansubscriptionid));
								orgplanuser.setCourseinvitation(courseinvitationobj);
								orgplanuser.setOrgplansubscription(orgplansubscription);
								HashSet<Orgplanuser> orgplanusers = new HashSet<Orgplanuser>();
								orgplanusers.add(orgplanuser);
								courseinvitationobj.setOrgplanusers(orgplanusers);
							}
							Integer invitationid=courseinvitationservice.save(courseinvitationobj);
							status=emailWriter.writeEmail(emailarray[i],getAttribute("organizationid"),invitationid.toString(),attachmentpath,message);
							if(status!="SUCCESS"){
								courseinvitationservice.setCourseInvitationFailed(invitationid.toString());
							}
						}
					}
					else{
						Courseinvitation courseinvitationobj=new Courseinvitation();
						Course courseobj =new Course();
						Orgperson orgpersonobj=new Orgperson();  
						Orgplansubscription orgplansubscription = new Orgplansubscription();
						Orgplanuser orgplanuser = new Orgplanuser();
						if(courseid!=null){
							courseobj.setCourseid(Integer.parseInt(courseid));
							courseinvitationobj.setCourse(courseobj);
						}
						orgpersonobj.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
						courseinvitationobj.setOrgperson(orgpersonobj);
						courseinvitationobj.setEmailid(emailarray[i]);
						courseinvitationobj.setMessage(java.net.URLDecoder.decode(message));
						courseinvitationobj.setAttachmentpath(attachmentpath);
						courseinvitationobj.setCourseinvitationstatus('I');
						courseinvitationobj.setDateofinvitation(new Date(System.currentTimeMillis()));
						if(getAttribute("orgstatus").equalsIgnoreCase("A")){
							List<?> orgPlanList =planSubscriptionService.getPlanSubscriptions("A",Long.parseLong(getAttribute("organizationid")));

							Map<?,?> mapss=(Map<?, ?>) orgPlanList.get(0);
							String orgplansubscriptionid= ""+mapss.get("id");

							orgplansubscription.setId(Integer.parseInt(orgplansubscriptionid));
							orgplanuser.setCourseinvitation(courseinvitationobj);
							orgplanuser.setOrgplansubscription(orgplansubscription);
							HashSet<Orgplanuser> orgplanusers = new HashSet<Orgplanuser>();
							orgplanusers.add(orgplanuser);
							courseinvitationobj.setOrgplanusers(orgplanusers);
						}
						Integer invitationid=courseinvitationservice.save(courseinvitationobj);
						status=emailWriter.writeEmail(emailarray[i],getAttribute("organizationid"),invitationid.toString(),attachmentpath,message);
						if(status!="SUCCESS"){
							courseinvitationservice.setCourseInvitationFailed(invitationid.toString());
						}
					}
				}
			}
			else{
				for (int i = 0; i < emailarray.length; i++) {
					Courseinvitation courseinvitationobj=new Courseinvitation();
					Course courseobj =new Course();
					Orgperson orgpersonobj=new Orgperson();  
					if(courseid!=null){
						courseobj.setCourseid(Integer.parseInt(courseid));
						courseinvitationobj.setCourse(courseobj);
					}
					orgpersonobj.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
					courseinvitationobj.setOrgperson(orgpersonobj);
					courseinvitationobj.setEmailid(emailarray[i]);
					courseinvitationobj.setMessage(java.net.URLDecoder.decode(message));
					courseinvitationobj.setAttachmentpath(attachmentpath);
					courseinvitationobj.setCourseinvitationstatus('I');
					courseinvitationobj.setDateofinvitation(new Date(System.currentTimeMillis()));
					Integer invitationid=courseinvitationservice.save(courseinvitationobj);
					status=emailWriter.writeEmail(emailarray[i],getAttribute("organizationid"),invitationid.toString(),attachmentpath,message);
					if(status!="SUCCESS"){
						courseinvitationservice.setCourseInvitationFailed(invitationid.toString());
					}
				}
			}
		}
		checkobj().toJsonString(status);
	}

	@Action(value = "/royaltydate")
	public void royaltyDate() throws AddressException, MessagingException, ParseException {

		String username=""+getAttribute("firstname");
		String emailaddress=""+getAttribute("orgemail");
		String phone=""+getAttribute("orgphonenumber");
		String url=""+getAttribute("orgurl");
		if (email != null) {

			ResourceBundle ap = ResourceBundle.getBundle("application"); 

			String productname = ap.getString("label.productname");
			String mailregardsmessage = ap.getString("label.mailteam"); 

			String mailSubject = "Royalty expiry Reminder";
			String mailContent ="";		
			String recipientType = "To";
			boolean isMailContentHtml = true;
			mailContent += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
			mailContent +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
					"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> " +
					"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> <h4 style='font-family: Open Sans !important; text-align : left;'> Dear &nbsp;"+username+"</h4> " +
					"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'>No Royalty is set after " +royaltydate+ ", Plaese create a new royalty.</h4><h3>Thanks,<br>"+mailregardsmessage+"</h3>" +
					"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
					"<tr><td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:</font><a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</a><br> " +
					"<font color='#ffffff'>Website: </font><a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
			mailContent += "</body>";
			mailContent += "</html>";	
			igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
		}
	}

	@Action(value = "/licensepresenteremail")
	public void licensePresenter() throws AddressException, MessagingException, ParseException {
		String username=""+getAttribute("firstname");
		String emailaddress=""+getAttribute("orgemail");
		String phone=""+getAttribute("orgphonenumber");
		String url=""+getAttribute("orgurl");

		if (email != null) {

			ResourceBundle ap = ResourceBundle.getBundle("application"); 

			String productname = ap.getString("label.productname");
			String mailregardsmessage = ap.getString("label.mailteam"); 

			String mailSubject = "License Presenter";
			String mailContent ="";		
			String recipientType = "To";
			boolean isMailContentHtml = true;
			mailContent += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
			mailContent +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
					"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> " +
					"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> <h4 style='font-family: Open Sans !important; text-align : left;'> Dear &nbsp;"+username+"</h4> " +
					"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'>License presenter</h4><h3>Thanks,<br>"+mailregardsmessage+"</h3>" +
					"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
					"<tr><td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:</font><a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</a><br> " +
					"<font color='#ffffff'>Website: </font><a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
			mailContent += "</body>";
			mailContent += "</html>";	
			igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
		}
	}
	@Action(value = "/subscriptionplan")
	public void subscriptionplan() throws AddressException, MessagingException, ParseException {

		if(getPlanid()!=null){
			if(planSubscriptionid!=null){
				List<?> planList= planService.getPlanIdList(getPlanid());
				List<?> planSubscripList=planSubscriptionService.getPlanSubscription(Integer.parseInt(planSubscriptionid));

				String planstartdate = null;
				String planenddate	 = null;
				String planname		 = null;
				String maxcourse 	 = null;
				String maxusers 	 = null;
				String duration 	 = null;
				String username=""+getAttribute("firstname");
				String emailaddress=""+getAttribute("orgemail");
				String phone =""+getAttribute("orgphonenumber");
				String url=""+getAttribute("orgurl");

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
							"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'><h3>Dear "+username+"</h3> <h3 style='color: rgb(185, 138, 116);'>Congratulations!</h3>You have successfully subscribed "+planname+". This plan is allowed</h4> maximumcourse :"+maxcourse+",maximumusers :"+maxusers+" ,duration :"+duration+".This plan will start:"+planstartdate+" and plan end date :"+planenddate+".<h4>Thank you.</h4><p>The Remedi Team</p>"+
							"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
							"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
							"<tr><td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:</font><a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</a><br> " +
							"<font color='#ffffff'>Website: </font><a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
					mailContent += "</body>";
					mailContent += "</html>";	
					String status = igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
					checkobj().toJsonString(status);
				}
			}
			else{
				checkobj().toJsonString("planSubscriptionid is null");
			}
		}
		else{
			checkobj().toJsonString("planid is null");
		}
	}

	@Action(value = "/checkinvitesubscribe")
	public void checkinvitesubscribe() {
		try{
			List<?> loginpersonlist=orgpersonservice.getLoginandpersonid(emailaddress);
			if(loginpersonlist.size()>0){
				checkobj().toJsonString("available");
			}
			else{
				checkobj().toJsonString("not available");
			}
		}
		catch(Exception e){
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in checkinvitesubscribe() in OrganizationCourses"
						, e);
			}
		}
	}
	@Action(value = "/recentemil")
	public void recentemail() {
		try{
			status=emailWriter.writeEmail(email,getAttribute("organizationid"),invitationid,attachmentpath,message);
			if(status=="SUCCESS"){
				courseinvitationservice.setCourseInvitationSuccess(invitationid);
				checkobj().toJsonString(status);	
			}
		}
		catch(Exception e){
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in recentemil() in OrganizationCourses"
						, e);
			}
		}
	}
	@Action(value = "/updateemail")
	public void updateemail() {
		try{
			status=courseinvitationservice.setCourseInvitationEmail(invitationid, email);
				checkobj().toJsonString(status);	
		}
		catch(Exception e){
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in recentemil() in OrganizationCourses"
						, e);
			}
		}
	}

	@Action(value = "/invitesubscribe",results={@Result(name="SUCCESS",location="app/user/courseSubscribe.jsp")})
	public String invitesubscribe() {
		try{
			Contactinfo contactinfo = new Contactinfo(); 
			Organization organization = new Organization();
			Person person=new Person();
			Login login=new Login();
			orgperson = new Orgperson();
			contactinfo = new Contactinfo();			
			Email email = new Email();
			setCourseid(courseid);
			setInvitationid(invitationid);

			if(!invitationid.equalsIgnoreCase("null")) {
				List<?> invitationstatus=courseinvitationservice.getInvitationStatus(invitationid);
				if(invitationstatus.size()>0){
					setTargetUrl(targetUrl);
					setOrganizationid(organizationid);
					setUsername(username);
					setPassword(password);
					return "SUCCESS";
				}
			}  
			//}else{
			List<?> usernameCheck = orgpersonservice.getLoginCheck(username,Long.parseLong(organizationid));
			if(usernameCheck.size()==0) 
			{		
				List<?> loginpersonlist=orgpersonservice.getLoginandpersonid(username);

				if(loginpersonlist.size()>0){
					String loginid = null,personid=null;
					for(int j=0;j<loginpersonlist.size();j++) {
						Map loginMap = (Map)loginpersonlist.get(j);
						loginid=""+loginMap.get("loginid");
						personid=""+loginMap.get("personid");
					}
					Integer orgpersid=orgpersonservice.insertOrgperson(personid, loginid, organizationid);
					Role role = new Role();
					role.setRolename("user");
					role.setRolestatus('A');
					role.setRolelevel('0');
					role.setRoleid(2);

					Department department = new Department();
					department.setDepartmentid(2);
					department.setDepartmentname("admin");
					department.setDepartmentstatus('A');

					Personallocation personallocation = new Personallocation();
					personallocation.setPersonallocationstatus('A');
					HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();				
					personallocation.setRole(role);
					personallocation.setDepartment(department);
					orgperson.setOrgpersonid(orgpersid);
					personallocation.setOrgperson(orgperson);
					personallocationService.save(personallocation);
					personallocationSet.add(personallocation);

					setTargetUrl(targetUrl);
					setOrganizationid(organizationid);
					setUsername(username);
					setPassword(password);
					courseinvitationservice.updateCourseInvitation(invitationid);
				}
				else{
					email.setUserid(username);
					Set<Email> emailSet = new HashSet<Email>();
					emailSet.add(email);
					contactinfo.setEmails(emailSet) ;   
					contactinfo.setContactinfostatus('A');
					person = new Person();  
					Personallocation personallocation = new Personallocation();
					Role role = new Role();
					role.setRolename("user");
					role.setRolestatus('A');
					role.setRolelevel('0');
					role.setRoleid(2);
					login=new Login();
					login.setPassword(password);
					login.setUserid(username);
					login.setLoginstatus('A');
					orgperson.setLogin(login);
					Department department = new Department();
					department.setDepartmentid(2);
					department.setDepartmentname("admin");
					department.setDepartmentstatus('A');

					HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();				
					personallocation.setRole(role);
					personallocation.setDepartment(department);
					personallocationSet.add(personallocation);
					personallocation.setPersonallocationstatus('A');
					orgperson.setPersonallocations(personallocationSet);
					person.setFirstName(firstname);
					person.setContactinfo(contactinfo); 
					person.setPersonstatus('A');
					person.setRegistereddate(new Date(System.currentTimeMillis()));
					orgperson.setLogin(login);
					orgperson.setPersonid(person);  
					orgperson.setOrgpersonstatus('A');	

					organization = new Organization();				 
					organization.setOrganizationid(Long.parseLong(organizationid));	      
					organization.setCreatedon(new Date(System.currentTimeMillis())); 
					orgperson.setOrganizationid(organization);
					orgpersonservice.save(orgperson);
					setTargetUrl(targetUrl);
					setOrganizationid(organizationid);
					setUsername(username);
					setPassword(password);
					courseinvitationservice.updateCourseInvitation(invitationid);

				}
			}
			return "SUCCESS";
		}
		catch(Exception e){
			e.printStackTrace();
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in invitesubscribe() in OrganizationCourses"
						, e);
			}
			return null;
		}
	}
	@Action(value = "/authorinvite")
	public void authorInvite() throws AddressException, MessagingException, ParseException {
		String status = null;
		if (email != null) {				
			setOrganizationid(""+getAttribute("organizationid"));		
			String orgpersonid = getAttribute("orgpersonid");
			String emailarray[] = email.split(",");			
			for (int i = 0; i < emailarray.length; i++) {	
				Course course=new Course();
				course.setCourseid(Integer.parseInt(courseid));
				Orgperson orgperson=new Orgperson();
				orgperson.setOrgpersonid(Integer.parseInt(orgpersonid));
				Courseinvitation courseinvitation=new Courseinvitation();
				Orgplansubscription orgplansubscription = new Orgplansubscription();
				Orgplanuser orgplanuser = new Orgplanuser();
				courseinvitation.setEmailid(emailarray[i]);
				courseinvitation.setDateofinvitation(new Date(System.currentTimeMillis()));
				courseinvitation.setMessage(java.net.URLDecoder.decode(message));
				courseinvitation.setCourseinvitationstatus('I');
				courseinvitation.setCourse(course);
				courseinvitation.setOrgperson(orgperson);				
				if(getAttribute("orgstatus").equalsIgnoreCase("A")){
					List<?> orgPlanList =planSubscriptionService.getPlanSubscriptions("A",Long.parseLong(getAttribute("organizationid")));

					Map<?,?> map=(Map<?, ?>) orgPlanList.get(0);
					String orgplansubscriptionid= ""+map.get("id");

					orgplansubscription.setId(Integer.parseInt(orgplansubscriptionid));
					orgplanuser.setCourseinvitation(courseinvitation);
					orgplanuser.setOrgplansubscription(orgplansubscription);
					HashSet<Orgplanuser> orgplanusers = new HashSet<Orgplanuser>();
					orgplanusers.add(orgplanuser);
					courseinvitation.setOrgplanusers(orgplanusers);
				}
				Integer authorInvite = (Integer) courseinvitationservice.save(courseinvitation);
				if (authorInvite != 0) {				
					status=authorwrite.write(emailarray[i],Integer.parseInt(courseid),authorInvite);	
					if(!status.equals("SUCCESS")){
						courseinvitationservice.setCourseInvitationFailed(authorInvite.toString());
					}
					else{
						status="success";
					}
				}			
			} 				
		}
		checkobj().toJsonString(status);
	}
	@Action(value = "/giftcoursemail")
	public void giftcoursemail() throws AddressException, MessagingException, ParseException {

		List<?> giftcourselist= giftCourseService.getGiftcourseList(giftcourseid);

		String username=""+getAttribute("firstname");
		String emailaddress=""+getAttribute("orgemail");
		String phone =""+getAttribute("orgphonenumber");
		String url=""+getAttribute("orgurl");
		String senddate="";
		String coursetitle="";

		if(giftcourselist.size()==1 && giftcourselist!=null) { 				
			Map personalMap = (Map)giftcourselist.get(0);
			String senddates = ""+personalMap.get("senddate");
			String[] date=senddates.split(" ");
			senddate = date[0];
			coursetitle = ""+personalMap.get("coursetitle");
		}

		if (email != null) {

			ResourceBundle ap = ResourceBundle.getBundle("application"); 

			String productname = ap.getString("label.productname");

			String mailSubject = "Gift Course";
			String mailContent ="";		
			String recipientType = "To";
			boolean isMailContentHtml = true;
			mailContent += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
			mailContent +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
					"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> " +
					"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> " +
					"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'><h3>Dear "+username+"</h3>The course "+coursetitle+" will be gifted to "+username+" on the "+senddate+"</h4>" +
					" <h6>Note : If the gift recipient does not already have an account on Reamedi Cloud, they will need to create an account before redeeming this gift.</h6>"+
					"<h4>Thank you.</h4><p>The Remedi Team</p>"+
					"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
					"<tr><td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:</font><a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</a><br> " +
					"<font color='#ffffff'>Website: </font><a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
			mailContent += "</body>";
			mailContent += "</html>";	
			String status = igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
			checkobj().toJsonString(status);
		}
	}
	@Action(value="/checkuserOrg")
	public void checkUser() {
		try {
			List<?> userList = organizationViewService.getOrganizationUser(Long.parseLong(organizationid),email);
			if(userList.size()==0){
				checkobj().toJsonString("success");
			}else{
				checkobj().toJsonString("failure");
			}			
		}

		catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger.error("error in checkUser() in OrganizationCourses", e);
			}
		}

	}

	@Action(value="/checkAuthorcourse")
	public void checkAuthorUser() {
		try {		  
			List<?> AuthourUsers = organizationViewService.checkEmailAuthor(email,Integer.parseInt(courseid));
			if(AuthourUsers.size()==0){
				checkobj().toJsonString("success");
			}else{
				checkobj().toJsonString("failure");
			}			
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger.error("error in checkAuthorUser() in OrganizationCourses", e);
			}
		}
	}

	@Action(value = "/loadOrganization")
	public void loadOrganization() {
		try {
			List<?> loadOrganizationlist = organizationViewService
					.getOrganizationname(Long.parseLong(organizationid));
			checkobj().toJsonResponse(loadOrganizationlist);
		} catch (Exception e) {
			if (OrganizationCourseslogger.isInfoEnabled()) {
				OrganizationCourseslogger
				.error("error in loadOrganization() in OrganizationCourses"
						, e);
			}
		}
	}

	@Action(value="/registerNewUserSignUp")
	public void registerAlreadyExistingUser(){
		try{
			List<?> loginpersonlist=orgpersonservice.getLoginandpersonid(username);
			String actualpassword = "";
			if(loginpersonlist.size()>0){
				String loginid = null,personid=null;
				for(int j=0;j<loginpersonlist.size();j++) {
					Map loginMap = (Map)loginpersonlist.get(j);
					loginid=""+loginMap.get("loginid");
					personid=""+loginMap.get("personid");
					actualpassword=""+loginMap.get("password");
				}
				if(actualpassword.equalsIgnoreCase(password)){
					Integer orgpersid=orgpersonservice.insertOrgperson(personid, loginid, organizationid);
					Role role = new Role();
					role.setRolename("user");
					role.setRolestatus('A');
					role.setRolelevel('0');
					role.setRoleid(2);

					Department department = new Department();
					department.setDepartmentid(2);
					department.setDepartmentname("admin");
					department.setDepartmentstatus('A');

					Personallocation personallocation = new Personallocation();
					personallocation.setPersonallocationstatus('A');
					HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();				
					personallocation.setRole(role);
					personallocation.setDepartment(department);
					orgperson.setOrgpersonid(orgpersid);
					personallocation.setOrgperson(orgperson);
					personallocationService.save(personallocation);
					personallocationSet.add(personallocation);

					checkobj().toJsonString("success");
				}else{
					checkobj().toJsonString("passwordfailure");
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			checkobj().toJsonString("error");
		}
	}
	
	@Action(value = "/livesessionenrollemail")
	public void livesessionenrollemail() throws AddressException, MessagingException, ParseException {
		String username=""+getAttribute("firstname");
		String emailaddress=""+getAttribute("orgemail");
		String phone=""+getAttribute("orgphonenumber");
		String url=""+getAttribute("orgurl");
		String orgpersonid=""+getAttribute("orgpersonid");
		
		String livesessionenrollmentid = null;
		String coursetitle = null;
		String starttime = null;
		String endtime = null;
		String scheduledate = null;
		
		if (email != null) {
			List<?> getlivesessionschedulelist=livesessionService.getlivesessionenrolllist(livesessionenrollid);
			
			if(getlivesessionschedulelist.size()==1 && getlivesessionschedulelist!=null) { 				
				Map personalMap = (Map)getlivesessionschedulelist.get(0);
				livesessionenrollmentid= ""+personalMap.get("livesessionenrollmentid");
				starttime = ""+personalMap.get("starttime");
				endtime = ""+personalMap.get("endtime");
				String dates = ""+personalMap.get("scheduledate");
				String[] dateformat=dates.split(" ");
				String dateformats = dateformat[0];
				String[] dateschedule=dateformats.split("-");
				scheduledate = dateschedule[2]+"-"+dateschedule[1]+"-"+dateschedule[0];
				coursetitle = ""+personalMap.get("coursetitle");
			}
			ResourceBundle ap = ResourceBundle.getBundle("application");
			ResourceBundle ss = ResourceBundle.getBundle("serversetup");

			String productname = ap.getString("label.productname");
			String urllink = ss.getString("remediliveclassroomurl");
			 

			String mailSubject = "Livesession Enrollment";
			String mailContent ="";		
			String recipientType = "To";
			boolean isMailContentHtml = true;
			mailContent += "<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' " +
					" style=' border: 1px solid rgb(225, 225, 243);'><tr><td align='left' valign='top'>" +
					" <div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div>" +
					" </td></tr> <tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
					" <table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>" +
					" <tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> " +
					" <div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'><h3>Dear "+username+"</h3>" +
					" You have been enrolled in the live session(Date : "+scheduledate+"   Starttime : "+starttime+" to Endtime : "+endtime+") for the  "+coursetitle+"</h4>" +
					" <h5>Note : You can entered into live session by clicking the link below.</h5>" +
					" <a style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width: 118px;text-decoration: none;color: white;'" +
					" href='"+urllink+"?orgpersonid="+orgpersonid+"&liveentrolmentid="+livesessionenrollmentid+"'>Click Here</a>" +
					" <h4>Thank you.</h4><p>The Remedi Team</p></div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
					" <table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr>" +
					" <td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:</font><a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</a><br> " +
					" <font color='#ffffff'>Website: </font><a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
			mailContent += "</body>";
			mailContent += "</html>";	
		    String status =	igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
		    checkobj().toJsonString(status);
		}
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrgpersonid() {
		return orgpersonid;
	}

	public void setOrgpersonid(String orgpersonid) {
		this.orgpersonid = orgpersonid;
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

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getRoyaltydate() {
		return royaltydate;
	}

	public void setRoyaltydate(String royaltydate) {
		this.royaltydate = royaltydate;
	}

	public String getInvitationid() {
		return invitationid;
	}

	public void setInvitationid(String invitationid) {
		this.invitationid = invitationid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttachmentpath() {
		return attachmentpath;
	}

	public void setAttachmentpath(String attachmentpath) {
		this.attachmentpath = attachmentpath;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getPlanSubscriptionid() {
		return planSubscriptionid;
	}

	public void setPlanSubscriptionid(String planSubscriptionid) {
		this.planSubscriptionid = planSubscriptionid;
	}

	public String getGiftcourseid() {
		return giftcourseid;
	}

	public void setGiftcourseid(String giftcourseid) {
		this.giftcourseid = giftcourseid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLivesessionenrollid() {
		return livesessionenrollid;
	}

	public void setLivesessionenrollid(String livesessionenrollid) {
		this.livesessionenrollid = livesessionenrollid;
	}


}