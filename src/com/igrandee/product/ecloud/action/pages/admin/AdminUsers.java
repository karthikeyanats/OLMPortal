package com.igrandee.product.ecloud.action.pages.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Message;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.igrandee.product.ecloud.service.users.MessageService;
import com.igrandee.product.ecloud.service.users.UserViewService;

/**
 * @author seenivasagan_p
 * Class for user management by admin
 */
public class AdminUsers extends MasterActionSupport{

	private static final long serialVersionUID = -4990114274698366830L;
	private static Logger AdminUserslogger = Logger.getLogger(AdminUsers.class);

	@Inject
	UserViewService userViewService;
	
	@Inject
	OrgPersonService orgPersonService;
	
	@Inject
	MessageService messageService;
	
	@Autowired
	private Message message;
	
	@Autowired
	private Orgperson toorgperson;
	
	@Autowired
	private Orgperson fromorgperson;
	
	private String courseid;
	private String personid;
	private String toOrgpersonid;
	private String messagedescription;
	private String paymenttype;
	private String approvedstatus;
	private String authorcourseid;
	private String authorid;
	private String messageid;

	
	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user management for admin
	 * @return success string
	 */
	/*@Action(value="/subscriber",results={@Result(name="SUCCESS",location="app/admin/admin-users.jsp")})
	public String users(){
		try	{}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in users() in AdminUsers ",e);
			}
		}
		return "SUCCESS";
	}*/

	@Action(value="/users",results={@Result(name="SUCCESS",location="app/admin/users.jsp")})
	public String subscriber(){
		try	{}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in users() in AdminUsers ",e);
			}
		}
		return "SUCCESS";
	}	
	/**
	 * @author seenivasagan_p
	 * Method to get All valid users
	 * 
	 */
	@Action(value="/loadValidUsers")
	public void loadValidUsers(){
		String crsid=null;
		try{
			if(courseid!="null"){
				crsid=courseid;
			}
			List<?> validUsersList=userViewService.loadAllValidUsers(getAttribute("orgpersonid"),crsid);
			checkobj().toJsonResponse(validUsersList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadValidUsers() in AdminUsers ",e);
			}
		}  
	}
	
	/**
	 * @author selvapriya_m
	 * Method to get All Removed users
	 * 
	 */
	
	
	@Action(value="/loadRemovedUsers")
	public void loadRemovedUsers(){
		try{
			
			List<?> RemoveduserList=userViewService.loadRemovedUsers(getAttribute("organizationid"));
			checkobj().toJsonResponse(RemoveduserList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadRemovedUsers() in AdminUsers ",e);
			}
		}  
	}
	
	/**
	 * @author williambenjamin_g
	 * Method to get All users
	 * 
	 */
	@Action(value="/loadAllUsers")
	public void loadAllUsers(){
		try{
			List<?> UsersList=userViewService.loadValidUsers(getAttribute("organizationid"));
			checkobj().toJsonResponse(UsersList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadValidUsers() in AdminUsers ",e);
			}
		}  
	}
	/**
	 * @author mahalingam_a
	 * Method to get All Authors of Courses
	 * 
	 */
	@Action(value="/loadAllAuthors")
	public void loadAllAuthors(){
		try{
			List<?> AuthorsList=userViewService.loadAllAuthors(getAttribute("organizationid"));
			checkobj().toJsonResponse(AuthorsList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadAllAuthors() in AdminUsers ",e);
			}
		}  
	}
	/**
	 * @author mahalingam_a
	 * @since 16-Aug-2014
	 * Method to get All Author Courses
	 * 
	 */
	@Action(value="/loadAuthorCourses")
	public void loadAuthorCourses(){
		try{
						
			List<?> AuthorsCoursesList=userViewService.loadAuthorCourses(authorid);
			checkobj().toJsonResponse(AuthorsCoursesList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadAuthorCourses() in AdminUsers ",e);
			}
		}  
	}
	/**
	 * @author mahalingam_a
	 * @since 18-Aug-2014
	 * Method to get All Students of the Author Courses
	 * 
	 */
	@Action(value="/loadAuthorCourseStudents")
	public void loadAuthorCourseStudents(){
		try{
						
			List<?> AuthorsCourseStudentList=userViewService.loadAuthorCourseStudents(authorcourseid);
			checkobj().toJsonResponse(AuthorsCourseStudentList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadAuthorCourseStudents() in AdminUsers ",e);
			}
		}  
	}
	
	
	/**
	 * @author williambenjamin_g
	 * Method to get All users
	 * 
	 */
	@Action(value="/loadWaitingUsers")
	public void loadWaitingUsers(){
		try{
			List<?> UsersList=userViewService.loadWaitingUsers(getAttribute("organizationid"));
			checkobj().toJsonResponse(UsersList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadValidUsers() in AdminUsers ",e);
			}
		}  
	}
	
	@Action(value="/loadinviteUsers")
	public void loadinviteUsers(){
		
		try{
			
			List<?> inviteUsersList=userViewService.loadInviteUsers(getAttribute("orgpersonid"),Integer.parseInt(courseid));
			checkobj().toJsonResponse(inviteUsersList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadinviteUsers() in AdminUsers ",e);
			}
		}  
	}	
	
	
	
	
	
	
	
	@Action(value="/authorcourseusers")
	public void authorCourseusers(){
		try{
			List<?> UsersList=userViewService.courseAuthourUsers(courseid,getAttribute("orgpersonid"));
			checkobj().toJsonResponse(UsersList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadValidUsers() in AdminUsers ",e);
			}
		}  
	}
	/**
	 * @author seenivasagan_p
	 * Method to get All valid users
	 * 
	 */
	@Action(value="/loadUserProfile")
	public void loadUserProfile(){
		try	{
			List<?> validUsersList=orgPersonService.getUserProfile(Integer.parseInt(personid));
			checkobj().toJsonResponse(validUsersList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadUserProfile() in AdminUsers ",e);
			}
		}  
	}
	
	@Action(value="/getMessageforParticularUser")
	public void getMessageforParticularUser(){
		try	{
			List<?> MessagesForParticularUsers = messageService.getMessage(Integer.parseInt(toOrgpersonid));
			checkobj().toJsonResponse(MessagesForParticularUsers);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in getMessageforParticularUser() in AdminUsers ",e);
			}
		}
	}
	
	/**
	 * @author raja_r
	 * Method to save the message
	 * @param no parameters
	 * @return response
	 */
	@Action(value="/messageSave")
	public void messageSave(){
		try	{
			message.setMessage(messagedescription);
			toorgperson.setOrgpersonid(Integer.parseInt(toOrgpersonid));
			fromorgperson = new Orgperson();
			fromorgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			message.setToorgperson(toorgperson);
			Date date=new Date();
			message.setMessagedate(new Timestamp(date.getTime()));
			message.setMessagestatus("A");
			message.setFromorgperson(fromorgperson);
			Integer successvalue =(Integer)messageService.save(message);
			checkobj().toJsonResponse(successvalue.toString());			
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in messageSave() in AdminUsers ",e);
			}
		}
	}
	
	/**
	 * @author venkatraman_v
	 * Method to send message to Multi user 
	 */
	 @Action(value="/messageToMultiuser")
	 public void messageToMultiuser(){
		 try{
			
			 String words[] = toOrgpersonid.split(",");
		        for (int i = 0; i < words.length; i++) {
		        	message.setMessage(messagedescription);
					toorgperson.setOrgpersonid(Integer.parseInt(words[i]));
					fromorgperson = new Orgperson();
					fromorgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
					message.setToorgperson(toorgperson);
					Date date=new Date();
					message.setMessagedate(new Timestamp(date.getTime()));
					message.setMessagestatus("A");
					message.setFromorgperson(fromorgperson);
					messageService.save(message);
		        }
		        checkobj().toJsonResponse("0");
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	/**
	 * @author seenivasagan_p
	 * Method to get All valid users
	 * 
	 */
	@Action(value="/getUsersInvoices")
	public void getUsersInvoices()	{
		String crsid=null;
		try	{
			if(courseid!="null"){
				crsid=courseid;
			}
			List<?> validUsersList=userViewService.getUsersInvoices(paymenttype,approvedstatus,getAttribute("orgpersonid"),crsid);
			checkobj().toJsonResponse(validUsersList);
		}
		catch(Exception e)	{
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in getUsersInvoices() in AdminUsers ",e);
			}
		}  
	}

	/* @author seenivasagan_p
	 * Method to get individual use's profile
	 * 
	 */
	@Action(value="/loadIndividualProfile")
	public void loadIndividualProfile() {
		try	{
			List<?> validUsersList=orgPersonService.getUserProfile(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(validUsersList);
		}
		catch(Exception e){
			e.printStackTrace();
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in loadIndividualProfile() in AdminUsers ",e);
			}
		}  
	}

	/**
	 * @author seenivasagan_p
	 * Method to get All valid users
	 * 
	 */
	@Action(value="/getAllCertificates")
	public void getAllCertificates(){
		String crsid=null;
		try	{
			if(courseid!="null"){
				crsid=courseid;
			}
			List<?> validCertificatesList=userViewService.loadAllCourseCertificates(getAttribute("orgpersonid"),crsid);
			checkobj().toJsonResponse(validCertificatesList);
		}
		catch(Exception e){
			
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in getAllCertificates() in AdminUsers ",e);
			}
		}  
	}
		

	@Action(value="/getReceivedMessagesForUser")
	public void getReceivedMessagesForUser(){
		try	{
			List<?> ReceivedMessageList = messageService.getMyMessage(Integer.parseInt(personid));
			checkobj().toJsonResponse(ReceivedMessageList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in getAllCertificates() in AdminUsers ",e);
			}
		}
	}

	@Action(value="/getReceivedMessages")
	public void getReceivedMessages(){
		try	{
			List<?> SentMessagesList = messageService.getMessage(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(SentMessagesList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in getAllCertificates() in AdminUsers ",e);
			}
		}
	}

	@Action(value="/getSentMessages")
	public void getSentMessages(){
		try{
			List<?> ReceivedMessageList = messageService.getMyMessage(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(ReceivedMessageList);
		}
		catch(Exception e){
			if(AdminUserslogger.isInfoEnabled()){
				AdminUserslogger.error("error in getAllCertificates() in AdminUsers ",e);
			}
		}
	}
	
	@Action(value="/deleteMessage") 
	public void deleteMessage(){
		try{
			int messageId = messageService.deleteMessage(Integer.parseInt(messageid));
 			if(messageId==0) {
				checkobj().toJsonString("false");
			} else {				
				checkobj().toJsonString("success"); 
			} 
		} 
		catch(Exception e){  
			if(AdminUserslogger.isInfoEnabled()){ 
				AdminUserslogger.error("error in deleteMessage() in AdminUsers ",e);  
			}
		}
	}
	
	
	
	/*getters and setters*/
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getToOrgpersonid() {
		return toOrgpersonid;
	}
	public void setToOrgpersonid(String toOrgpersonid) {
		this.toOrgpersonid = toOrgpersonid;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getApprovedstatus() {
		return approvedstatus;
	}
	public void setApprovedstatus(String approvedstatus) {
		this.approvedstatus = approvedstatus;
	}

	public Orgperson getFromorgperson() {
		return fromorgperson;
	}

	public void setFromorgperson(Orgperson fromorgperson) {
		this.fromorgperson = fromorgperson;
	}

	public String getMessagedescription() {
		return messagedescription;
	}

	public void setMessagedescription(String messagedescription) {
		this.messagedescription = messagedescription;
	}

	public String getAuthorcourseid() {
		return authorcourseid;
	}

	public void setAuthorcourseid(String authorcourseid) {
		this.authorcourseid = authorcourseid;
	}

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
}