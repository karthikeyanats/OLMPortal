package com.igrandee.product.ecloud.action.course;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Payment;
import com.igrandee.product.ecloud.model.Paymentdetail;
import com.igrandee.product.ecloud.model.Price;
import com.igrandee.product.ecloud.service.course.CourseEnrollmentService;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.InvoicePaymentService;
import com.igrandee.product.ecloud.util.commons.IgCommons;

public class CoursePaymentAction extends MasterActionSupport implements SessionAware
{
	private static final long serialVersionUID = 1L;
	
	 

	private static Logger CoursePaymentActionLogger = Logger.getLogger(CoursePaymentAction.class);

	@Inject
	CourseListService courselistservice;
	
	@Inject
	CourseEnrollmentService courseEnrollmentService;

	@Inject
	InvoicePaymentService invoicePaymentService;

	@Autowired
	private Courseenrollment courseenrollment; 
		
	@Autowired
	private Course course;
	
	@Autowired
	private Orgperson orgperson;
	
	@Autowired
	private Price price;
	
	@Autowired
	private Payment payment;
	
	@Autowired
	private Paymentdetail paymentdetail;
	
	@Autowired
	IgCommons igCommons;
	
	private String courseid;
	private String priceid;
	private String priceamount;
	private String paymentid;
	private String paymentdesc;
	private String paymenttype;
	private String paymentemailaddress;
	private String paymentamount;
	private String bankname;
	private String chequenumber;
	private String paymentstatus;
	private String courseenrollmentstatus;
	private String status;
	private String courseenrollmentid;
	private String invoiceid;
	private String personid;
	private String orderno;
	private String livesessionid;
	private String livesessionarrays;
	
	private Map<String, Object> session;  

	/**
	 * @author mano_r this method is used to insert the course Direct payment.
	 * 
	 */
	@Action(value = "/payOnline",results = {@Result(name = "SUCCESS", type = "redirectAction", params = {"namespace", "/", "actionName", "mycourses" }) })
	public void payOnline() {
		try	{
			course.setCourseid(Integer.parseInt(courseid.trim()));
			String personid = getAttribute("orgpersonid");
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			courseenrollment.setOrgperson(orgperson);
			courseenrollment.setCourseenrollmentstatus("P");
			courseenrollment.setCourse(course);
			price.setPriceid(Integer.parseInt(priceid.trim()));
			payment.setPaymentamount(priceamount.trim());
			payment.setPaymenttype("Online");
			payment.setCourseenrollment(courseenrollment);
			java.util.Date date = new java.util.Date();
			payment.setPaymentdate(new Timestamp(date.getTime()));
			payment.setPaymentstatus("A");
			payment.setPrice(price);
			HashSet<Payment> paymenthashset = new HashSet<Payment>();
			paymenthashset.add(payment);
			courseenrollment.setPayment(paymenthashset);
			courseenrollment.setEnrolleddate(new Timestamp(date.getTime()));
			String courseenrollmentid = courseEnrollmentService.save(courseenrollment).toString();
			checkobj().toJsonResponse(courseenrollmentid);
		}
		catch(Exception e){
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in payOnline() in CoursePaymentAction",e);
			}
		}
	}
	
	/**
	 * @author raja_r
	 * Method to redirect to courseEnrollment history 
	 * @return
	 */
	@Action(value = "/courseEnroll", results = {@Result(name = "SUCCESS", type = "redirectAction", params = {"namespace", "/", "actionName", "mycourses" }) })
	public String courseEnroll(){
		try	{ 
			if(session!=null) {
				if(session.get("courseid")==null ||  session.get("courseid").toString().equalsIgnoreCase("null") || session.get("courseid")=="" || session.get("courseid").equals("") || session.get("courseid").toString().equalsIgnoreCase("")){
					course.setCourseid(Integer.parseInt(courseid));
				} 
				else {
					String courseid =""+session.get("courseid");
					course.setCourseid(Integer.parseInt(courseid));
				}
			}
			courseenrollment.setCourse(course);			  
			String personid=getAttribute("orgpersonid"); 
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			courseenrollment.setOrgperson(orgperson);
			courseenrollment.setCourseenrollmentstatus("A"); 
			java.util.Date date = new java.util.Date();
			courseenrollment.setEnrolleddate(new Timestamp(date.getTime()));
			courseEnrollmentService.save(courseenrollment);  
			session.remove("courseid");
		}
		catch(Exception e){
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in courseEnroll() in CoursePaymentAction",e);
			}
		}
		return "SUCCESS";
	}
	
	/**
	 * @author raja_r
	 * Method to redirect to courseEnrollment history 
	 * @return
	 */
	@Action(value = "/wishlistCourse")
	public void wishlistCourse(){
		try	{ 
			course.setCourseid(Integer.parseInt(courseid));
			courseenrollment.setCourse(course);			  
			String personid=getAttribute("orgpersonid"); 
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			courseenrollment.setOrgperson(orgperson);
			courseenrollment.setCourseenrollmentstatus("W"); 
			courseEnrollmentService.save(courseenrollment);
			checkobj().toJsonString("wishslisted");
		}
		catch(Exception e){
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in wishlistCourse() in CoursePaymentAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to Check whether a person is enrolled for a particular course
	 * 
	 */
	@Action(value="/loadEnrollmentStatus")
 	public void loadEnrollmentStatus(){		
		try	{
			List<?> enrollmentStatusList=courseEnrollmentService.checkEnrollmentStatus(Integer.parseInt(courseid),Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(enrollmentStatusList);
		}
		catch(Exception e){
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in loadEnrollmentStatus() in CoursePaymentAction",e);
			}
		} 
	}

	@Action(value="/getCourseenrollchecked")
	public void getCourseenrollchecked(){
		try{
			List<?> enrollmentStatusList=courseEnrollmentService.checkEnrollment(Integer.parseInt(courseid),Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(enrollmentStatusList);
			
			if(!(enrollmentStatusList.isEmpty())){
			Iterator<?> ite=enrollmentStatusList.iterator();
			HashMap<?, ?> courseenrollments=(HashMap<?, ?>) ite.next();
			String courseenrollmentid=""+courseenrollments.get("courseenrollmentid");
			String courseenrollmentstatus=""+courseenrollments.get("courseenrollmentstatus");
			if(courseenrollmentstatus.equals("W")){
				courseEnrollmentService.updateCourseenrollment(Integer.parseInt(courseenrollmentid),"A");
			}
			}
		}catch(Exception e){
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in getCourseenrollchecked() in CoursePaymentAction",e);
			}
		}
	}
	/**
	 * @author seenivasagan_p
	 * Update course enrollment
	 */
	@Action(value = "/updateCourseEnrollment")
	public void updateCourseEnrollment(){
		try {
			courseEnrollmentService.updateCourseenrollment(Integer.parseInt(courseenrollmentid),courseenrollmentstatus);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in updateCourseEnrollment() in CoursePaymentAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Update course Approvement
	 */
	@Action(value = "/updatePaymentStatus")
	public void updateApproveStatus(){
		try {
			courseEnrollmentService.updateCoursePaymentStatus(Integer.parseInt(paymentid),paymentstatus,orderno);
			checkobj().toJsonString("paymentsuccess");
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in updateApproveStatus() in CoursePaymentAction",e);
			}
		}
	}

	/**
	 * @author mano_r this method is used to insert the invoice details.
	 * @throws ParseException
	 */
	@Action(value = "/admission")
	public void admission() throws ParseException{
		try {
			course.setCourseid(Integer.parseInt(courseid));
			courseenrollment.setCourse(course);
			java.util.Date date1 = new java.util.Date();
			courseenrollment.setEnrolleddate(new Timestamp(date1.getTime()));
			String personid = getAttribute("orgpersonid");
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			courseenrollment.setOrgperson(orgperson);
			courseenrollment.setCourseenrollmentstatus(courseenrollmentstatus);
			HashSet<Payment> setpayment = new HashSet<Payment>();
			setpayment.add(payment);
			price.setPriceid(Integer.parseInt(priceid));
			price.setPayments(setpayment);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date = new Date();
			String askeddate1=dateFormat.format(date);
			java.util.Date enrolledDatetemp = dateFormat.parse(askeddate1);
			java.sql.Timestamp enrolledDate = new java.sql.Timestamp(enrolledDatetemp.getTime());
			if (paymentdesc != null){
				payment.setPaymentdescription(paymentdesc);
			}
			else{
				payment.setPaymentdescription("null");
			}
			payment.setPaymentdate(enrolledDate);
			payment.setPaymenttype(paymenttype);
			payment.setPaymentstatus(paymentstatus);
			payment.setOrderno(orderno);
			if (paymentamount != null) {
				payment.setPaymentamount(paymentamount);
			}
			else{
				payment.setPaymentamount("null");
			}
			payment.setCourseenrollment(courseenrollment);
			payment.setPrice(price);
			paymentdetail.setPayment(payment);
			if (bankname != null) {
				paymentdetail.setBankname(bankname);
			}
			else{
				paymentdetail.setBankname("null");
			}
			if (chequenumber != null) {
				paymentdetail.setChequenumber(chequenumber);
			}
			else{
				paymentdetail.setChequenumber("null");
			}
 			if(StringUtils.isNotEmpty(courseenrollmentid)){
  				if(courseenrollmentid!=null && !courseenrollmentid.equals("")) { 
  					courseenrollment.setCourseenrollmentid(Integer.parseInt(courseenrollmentid.trim()));
				} 
  				if(paymentid!=null && !paymentid.equals("")){  					
  					payment.setPaymentid(Integer.parseInt(paymentid));
  					invoicePaymentService.update(payment); 
  				}  else {
  					HashSet<Paymentdetail> setpaPaymentdetails = new HashSet<Paymentdetail>();
  					setpaPaymentdetails.add(paymentdetail);
  					payment.setPaymentdetails(setpaPaymentdetails);
  					paymentdetail.setPayment(payment);
  					invoicePaymentService.save(payment).toString(); 
   				}
 				courseEnrollmentService.update(courseenrollment);
				
				checkobj().toJsonString(courseenrollmentid);
			}  else {
				HashSet<Paymentdetail> setpaPaymentdetails = new HashSet<Paymentdetail>();
				setpaPaymentdetails.add(paymentdetail);
				payment.setPaymentdetails(setpaPaymentdetails);
				paymentdetail.setPayment(payment);
				
				courseenrollmentid=courseEnrollmentService.save(courseenrollment).toString();
				invoicePaymentService.save(payment).toString();
				checkobj().toJsonString(courseenrollmentid);
				setPaymentemailaddress(paymentemailaddress);
 			}
			
  			session.remove("courseid");
		} 
		catch (Exception e) {
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in admission() in CoursePaymentAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to get particulars of a particular invoice
	 * 
	 */
	@Action(value = "/getInvoiceParticulars")
	public void getInvoiceParticulars(){
		try	{
			List<?> invoiceParticularsList=invoicePaymentService.getInvoiceListValue(Integer.parseInt(invoiceid));
			checkobj().toJsonResponse(invoiceParticularsList);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in getgetInvoiceParticulars() in CoursePaymentAction",e);
			}
		}
	}
	
	@Action(value = "/livesessioninvoice")
	public void getLivesessionInvoiceParticulars(){
		try	{
			List<?> invoiceParticularsList=invoicePaymentService.getLivesessionInvoice(Integer.parseInt(invoiceid));
			checkobj().toJsonResponse(invoiceParticularsList);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in livesessioninvoice() in CoursePaymentAction",e);
			}
		}
	}
	
	/**
	 * @author williambenjamin_g
	 * Method to insert the user for this courses 
	 */
	 @Action(value = "/submitCoursesforUsers")
	 public void userEnrollmentMtd() {
		try{
			int courseenroll=0;
			
			String[] personidvalnew = personid.split(",");
 			for (int y = 0; y < personidvalnew.length; y++) {
 				Course course =new Course();
				course.setCourseid(Integer.parseInt(courseid));
				orgperson.setOrgpersonid(Integer.parseInt(personidvalnew[y]));
 				if(courseenrollmentid!=null){
					courseenrollment.setCourseenrollmentid(Integer.parseInt(courseenrollmentid));
				}
 				courseenrollment.setCourseenrollmentstatus("A");
				courseenrollment.setOrgperson(orgperson);
				courseenrollment.setCourse(course); 
 				List<?> checkenroll=courselistservice.chkentoenroll(Integer.parseInt(personidvalnew[y]),Integer.parseInt(courseid));
 					if(checkenroll.isEmpty()){
						courseenroll=courseEnrollmentService.save(courseenrollment);
					}else{
						Iterator<?> ite=checkenroll.iterator();
						HashMap<?, ?> courseenrollments=(HashMap<?, ?>) ite.next();
						int courseenrollmentid=(Integer) courseenrollments.get("courseenrollmentid");
						courseenrollment.setCourseenrollmentid(courseenrollmentid);						
						courseEnrollmentService.update(courseenrollment);
						courseenroll=1;
					}
					
 			}	 
			if(courseenroll!=0){
				checkobj().toJsonString("SUCCESS");
		     }
			else{ 
				checkobj().toJsonString("FAILURE");
			} 
		}
		catch(Exception e){
			if(CoursePaymentActionLogger.isInfoEnabled())	{
				CoursePaymentActionLogger.error("error in userEnrollmentMtd() in CoursePaymentAction",e);
			}
		}
	}
	 
	@SuppressWarnings("unused")
	@Action("/freeCourseEmailSubscribtionStatus") 
	public void emailFreeCourseSubscriptionStatusToCourseAuthorAndSubscriber(){
		 try { 
			 
			 ResourceBundle ap = ResourceBundle.getBundle("application");
  			 String productname = ap.getString("label.productname");
  			String mailregardsmessage = ap.getString("label.mailteam");
 			 
			 String subscriber = getAttribute("orgpersonid");
			 String subscribedCourseId = courseid;  
			 List<?> authorDetailsList = invoicePaymentService.getCourseAuthorDetails(Integer.parseInt(courseid)); 
			 Iterator<?> authorDetailsListIterator =  authorDetailsList.listIterator(); 
			 while (authorDetailsListIterator.hasNext()) { 
				Map<?,?> object = (Map<?, ?>) authorDetailsListIterator.next();
				final String  authorfirstname, authorlastname, authoremail,  coursetitle, organizationName, learnerFirstName, learnerLastName, learnerEmailAddress,
				organizationLogo, phoneNumber,doctype,learnerMailHTML,organizationEmailId,orgPhoneNumber,orgUrl;
				final String authorMailHTML; 
				final int  courseauthorid ,course_id;  
				 courseauthorid = (Integer.parseInt("" + object.get("authorid")));
					authorfirstname = "" + object.get("authorfirstname");
					authorlastname = "" + object.get("authorlastname");
					authoremail = "" + object.get("authoremailid");
					course_id = (Integer.parseInt("" + courseid));
					coursetitle = "" + object.get("coursetitle");

					learnerFirstName = getAttribute("firstname");
					learnerLastName = getAttribute("lastname");
					learnerEmailAddress = getAttribute("email");
					organizationLogo = getAttribute("orglogo");
					phoneNumber = getAttribute("phonenumber");

					organizationName = getAttribute("orgname");
					organizationEmailId = getAttribute("orgemail");
					orgPhoneNumber = getAttribute("orgphonenumber");  
					orgUrl = getAttribute("orgurl"); 
					
			    doctype = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"; 
			    learnerMailHTML = doctype+"<html><body style='background-color: rgba(128, 128, 128, 0.38);'><table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'><tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr><tr><td align='center' valign='top' bgcolor='#f1f69d'style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><div><br><h4 style='font-family: Open Sans !important; text-align : left;'> Dear &nbsp;"+learnerFirstName.toUpperCase()+"</h4>  <h1 style='font-family: Open Sans !important; color:#B8860B;'> Congratulations!</h1></div><br><div><p style='font-family : Open Sans; fontt-style : normal; font-weight : 400; color: #0E0B0B; font-size: 16px'> You have been successfully subscribed to "+ coursetitle +". you can start learning. </p><br><h3>Thanks,<br>"+mailregardsmessage+"</h3></div></td></tr><tr><td></td></tr></table><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #ffffff;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr><td>Contact : "+authorfirstname+"<br> Phone:"+orgPhoneNumber+"<br> Email: <a href='mailto:"+organizationEmailId+"' style='color:#ffffff; text-decoration:none;'> "+organizationEmailId+"</a><br>Website: <a href='"+orgUrl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+orgUrl+"</a></td></tr></table></td></tr></table></body></html>";
			    authorMailHTML  = doctype + "<html><body style='background-color: rgba(128, 128, 128, 0.38);'><table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'><tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr><tr><td align='center' valign='top' bgcolor='#f1f69d'style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><div><br><h4 style='font-family: Open Sans !important; text-align : left;'> Dear &nbsp;"+authorfirstname+"</h4>  <h1 style='font-family: Open Sans !important; color:#B8860B;'>Congratulations!</h1> <br><p style='font-family : Open Sans; fontt-style : normal; font-weight : 400; color: #0E0B0B; font-size: 16px'> "+learnerFirstName.toUpperCase()+" has subscribed to your course : "+ coursetitle +".</p></div><div><br><br><h3>Thanks,<br>"+mailregardsmessage+"</h3></div></td></tr></table></td></tr><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #ffffff;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr><td>Contact : "+authorfirstname+"<br> Phone:"+orgPhoneNumber+"<br> Email: <a href='mailto:"+organizationEmailId+"' style='color:#ffffff; text-decoration:none;'> "+organizationEmailId+"</a><br>Website: <a href='"+orgUrl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+orgUrl+"</a></td></tr></table></body></html>";
				ExecutorService executorService = Executors.newFixedThreadPool(2);
				 executorService.submit(new Runnable() {
					
					public void run() {
						try {
							igCommons.sendEmail(learnerEmailAddress,"Reg: Subscription " + coursetitle,learnerMailHTML, "TO", true, true, false, null, null);
							igCommons.sendEmail(authoremail,"Reg: Subscription " + coursetitle,authorMailHTML, "TO", true, true, false, null,null);
						} catch (Exception e) {
							CoursePaymentActionLogger.error(e.getCause());
						} 
					}
				}).get();    
				executorService.shutdown();
				executorService.awaitTermination( 20,  TimeUnit.SECONDS ); 
			}
		} catch (Exception e) {
			 CoursePaymentActionLogger.error("emailFreeCourseSubscriptionStatusToCourseAuthorAndSubscriber==>"+ e.getCause());
		}  
		
	}
	 
	@SuppressWarnings("unused")
	@Action("/emailSubscribtionStatus")
	 public void emailSubscribtionStatusToCourseAuthorAndSubscriber(){
		 
		 try { 
			 ResourceBundle ap = ResourceBundle.getBundle("application");
  			 String productname = ap.getString("label.productname");
  			 String mailregardsmessage =ap.getString("label.mailteam");
			 
			 String subscriber = getAttribute("orgpersonid");
			 String subscribedCourseId = courseid; 
			 List<?> authorDetailsList = invoicePaymentService.getCourseAuthorDetails(Integer.parseInt(courseid));
 			 Iterator<?> authorDetailsListIterator =  authorDetailsList.listIterator(); 
			 while (authorDetailsListIterator.hasNext()) { 
				Map<?,?> object = (Map<?, ?>) authorDetailsListIterator.next();
				final String authorfirstname, authorlastname, authoremail,  coursetitle, organizationName, learnerFirstName, learnerLastName, learnerEmailAddress,
				organizationLogo, phoneNumber,doctype,learnerMailHTML, authorMailHTML, organizationEmailId, orgPhoneNumber, orgUrl,price; 
			    final int courseauthorid ,course_id;  
			      
				 
			    
			    courseauthorid = (Integer.parseInt("" + object.get("authorid")));
				authorfirstname = "" + object.get("authorfirstname");
				authorlastname = "" + object.get("authorlastname");
				authoremail = "" + object.get("authoremailid");
				course_id = (Integer.parseInt("" + courseid));
				coursetitle = "" + object.get("coursetitle");
				price=""+object.get("price");

				learnerFirstName = getAttribute("firstname");
				learnerLastName = getAttribute("lastname");
				learnerEmailAddress = getAttribute("email");
				organizationLogo = getAttribute("orglogo");
				phoneNumber = getAttribute("phonenumber");

				organizationName = getAttribute("orgname");
				organizationEmailId = getAttribute("orgemail");
				orgPhoneNumber = getAttribute("orgphonenumber");  
				orgUrl = getAttribute("orgurl"); 
			    
			 
			    
			       
			    doctype = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional //EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"; 
			    learnerMailHTML = doctype+"<html><body style='background-color: rgba(128, 128, 128, 0.38);'><table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'><tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr><tr><td align='center' valign='top' bgcolor='#f1f69d'style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><div><br>  <h4 style='font-family: Open Sans !important; color:#B8860B; text-align : left;'> Dear &nbsp;"+learnerFirstName.toUpperCase()+" <br></h4><br> <h1 style='font-family: Open Sans !important; color:#B8860B; text-align : center;'> Congratulations! You have been successfully subscribed to " + coursetitle + "</h1></div><br><div><p style='font-family : Open Sans; fontt-style : normal; font-weight : 400; color: #0E0B0B; font-size: 16px'> You have been enqueued for approval from your author -  " + authorfirstname+ " Once your author - "+ authorfirstname +" has approved, you can start learning. We are requesting you to be patient until the approval gets confirmed. For further more details please feel free to contact our corporate mail address or make a call back to our office. <br><br>" +
			    		"" +"<table width='580' border="+1+" cellpadding="+5+" cellspacing="+0+"><tr><th>Course Title</th><th>Course Author</th><th>Price(Rs)</th></tr><tr><td>"+ coursetitle +"</td><td>"+authorfirstname+"</td><td>"+price+"</td></tr></table>"+
			    		"<br>You  can find the phone number and email address below.</p><br><br><h3>Thanks,<br>"+mailregardsmessage+"</h3></div></td></tr></table><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #ffffff;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr><td>Contact : "+authorfirstname+"<br> Phone:"+orgPhoneNumber+"<br> Email: <a href='mailto:"+organizationEmailId+"' style='color:#ffffff; text-decoration:none;'> "+organizationEmailId+"</a><br>Website: <a href='"+orgUrl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+orgUrl+"</a></td></tr></table></td></tr></table></body></html>";
			    authorMailHTML  = doctype + "<html><body style='background-color: rgba(128, 128, 128, 0.38);'><table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'><tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr><tr><td align='center' valign='top' bgcolor='#f1f69d'style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><div><br><h4 style='font-family: Open Sans !important; text-align : left;'> Dear &nbsp;"+authorfirstname+"</h4> <h1 style='font-family: Open Sans !important; color:#B8860B;'> "+learnerFirstName.toUpperCase()+" has subscribed for your course : "+ coursetitle +".</h1></div><div><p style='font-family : Open Sans; fontt-style : normal; font-weight : 400; color: #0E0B0B; font-size: 16px'>You are requested to make approval for the student named :  "+ learnerFirstName +" who has subscribed for your course  titled "+ coursetitle  +". Kindly refer through the details and make approval as soon as possible. For further more details please feel free to email or make  a call to our corporate addresses which you can refer below.</p><br><h3>Thanks,<br>"+mailregardsmessage+"</h3></div></td></tr></table><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #ffffff;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr><td>Contact : "+authorfirstname+"<br> Phone:"+orgPhoneNumber+"<br> Email: <a href='mailto:"+organizationEmailId+"' style='color:#ffffff; text-decoration:none;'> "+organizationEmailId+"</a><br>Website: <a href='"+orgUrl+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+orgUrl+"</a></td></tr></table></td></tr></table></body></html>"; 
			    ExecutorService executorService = Executors.newFixedThreadPool(2);  
			    executorService.submit(new Runnable() {
					
					public void run() {
						igCommons.sendEmail(learnerEmailAddress, "Reg: Subscription " + coursetitle, learnerMailHTML, "TO", true, true, false, null, null);
						igCommons.sendEmail(authoremail, "Reg: Subscription " + coursetitle , authorMailHTML, "TO", true, true, false, null, null);
						
					}
				}).get();   
				executorService.shutdown();
				executorService.awaitTermination( 20, TimeUnit.SECONDS );
			}
		} catch (Exception e) {
			 CoursePaymentActionLogger.error(e.getCause());
		}  
	 }

	/**
	 * This method used to get purchase hostory from user
	 * @author raja_r
	 * @param orgpersonid
	 * @return response 
	 * 
	 * */	
	@Action(value = "/getPurchaseHistory")
	public void getPurchaseHistory(){
		try	{
			String personid = getAttribute("orgpersonid");
 			List<?> purchaseHistroyList=courseEnrollmentService.getPurchaseHistory(personid);
			checkobj().toJsonResponse(purchaseHistroyList);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in getPurchaseHistory() in CoursePaymentAction",e);
			}
		}
	}
	
	@Action(value="/livepurchasehistory")
	public void getLivePurchaseHistory(){
		try	{
			String personid = getAttribute("orgpersonid");
 			List<?> livePurchaseHistroyList=courseEnrollmentService.getLivePurchaseHistory(personid);
			checkobj().toJsonResponse(livePurchaseHistroyList);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in livepurchasehistory() in CoursePaymentAction",e);
			}
		}
	}
	
	@Action(value="/getlivesessionschedulelist")
	public void getlivesessionschedulelist(){
		try	{
 			List<?> livePurchaseHistroyList=courseEnrollmentService.getlivesessionschedulelist(livesessionid,getAttribute("orgpersonid"));
			checkobj().toJsonResponse(livePurchaseHistroyList);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in getlivesessionschedulelist() in CoursePaymentAction",e);
			}
		}
	}
	
	/** 
	 * @author muniyarasu_a
	 * @return livesessionlist
	 * @param livesessionid Array
	 * 
	 * **/
	
	@Action(value="/livesessionlist")
	public void getlivesessionlist(){
		try	{
 			List<?> livesessionList=courseEnrollmentService.livesessionlist(livesessionarrays,getAttribute("orgpersonid"));
			checkobj().toJsonResponse(livesessionList);
		} 
		catch (Exception e)	{
			if(CoursePaymentActionLogger.isInfoEnabled()){
				CoursePaymentActionLogger.error("error in livesessionlist() in CoursePaymentAction",e);
			}
		}
	}
	
	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getPriceid() {
		return priceid;
	}

	public void setPriceid(String priceid) {
		this.priceid = priceid;
	}

	public String getPriceamount() {
		return priceamount;
	}

	public void setPriceamount(String priceamount) {
		this.priceamount = priceamount;
	}
	
	public void setSession(Map<String, Object> session) {
		 this.session = session;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	
	public String getPaymentdesc() {
		return paymentdesc;
	}
	public void setPaymentdesc(String paymentdesc) {
		this.paymentdesc = paymentdesc;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getPaymentamount() {
		return paymentamount;
	}
	public void setPaymentamount(String paymentamount) {
		this.paymentamount = paymentamount;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getChequenumber() {
		return chequenumber;
	}
	public void setChequenumber(String chequenumber) {
		this.chequenumber = chequenumber;
	}
	public String getPaymentemailaddress() {
		return paymentemailaddress;
	}
	public void setPaymentemailaddress(String paymentemailaddress) {
		this.paymentemailaddress = paymentemailaddress;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public String getCourseenrollmentstatus() {
		return courseenrollmentstatus;
	}
	public void setCourseenrollmentstatus(String courseenrollmentstatus) {
		this.courseenrollmentstatus = courseenrollmentstatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}
	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
	public String getInvoiceid() {
		return invoiceid;
	}
	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}
	public String getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getLivesessionid() {
		return livesessionid;
	}

	public void setLivesessionid(String livesessionid) {
		this.livesessionid = livesessionid;
	}

	public String getLivesessionarrays() {
		return livesessionarrays;
	}

	public void setLivesessionarrays(String livesessionarrays) {
		this.livesessionarrays = livesessionarrays;
	}
	
	
}