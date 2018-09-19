package com.igrandee.product.ecloud.action.course;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Notification;
import com.igrandee.product.ecloud.model.Notificationreceived;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.Pages.user.GiftCourseService;
import com.igrandee.product.ecloud.service.users.NotificationService;


public class NotificationAction extends MasterActionSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger Notificationlogger = Logger.getLogger(NotificationAction.class);
	
	private Integer status;
	private String notificationdesc;
	private String categorystatus;
	private String notificationStatus;
	private String message;
	private String topersonid;
	private String giftcourseid;	

	@Inject
	NotificationService notificationService;
	
	@Inject
	GiftCourseService giftCourseService;
	/**
	 * @author mano_r
	 * @return
	 */
	@Action(value = "/notificationSave")
	public void notificationSave() {
		try {
			String sesid=getAttribute("organizationid");
 			List<?> userList = null;
 			if(notificationStatus.equalsIgnoreCase("ALL")){
				userList = notificationService.getPersonDetailsList(sesid);				
			} else if(notificationStatus.equalsIgnoreCase("LEARN")){
				userList = notificationService.getLearningUsersList(sesid);
			} else if(notificationStatus.equalsIgnoreCase("PAID")){
				userList = notificationService.getPaidUsersPersonDetailsList(sesid);
			} else if(notificationStatus.equalsIgnoreCase("PENDING")){
				userList = notificationService.getPendingUsersPersonDetailsList(sesid); 
			}
			if(userList.size()>0){
 				String msg=checklist(userList) ;
				if(msg.equalsIgnoreCase("error")){
					checkobj().toJsonResponse(msg);	
				}
				else{
	 				checkobj().toJsonString("error");
				}
				
			}else{
 				checkobj().toJsonString("nodata");
			}
		} 
		catch (Exception e) {
			if(Notificationlogger.isInfoEnabled()){
				Notificationlogger.error("error in notificationSave() in NotificationAction",e);
			}
		}
	}

	private String checklist(List userList) {
		try{
			String fromorgid = getAttribute("orgpersonid");
			String msg=notificationdesc;
			String notimsg=notificationStatus;
			List toorgid=userList;
  			notificationDetails(fromorgid,toorgid,msg,notimsg,notificationService); 
			
			return notificationStatus;	
		}
		catch(Exception e){
			if(Notificationlogger.isInfoEnabled()){
				Notificationlogger.error("error in checklist() in NotificationAction",e);
			}
			return "error";
		}
	}
	
	@Action("/notifythisperson")
	public void notifythisperson() {
		try {
			
			List personList=null;
			personList=new ArrayList();
			HashMap persson=new HashMap();
			persson.put("orgpersonid",topersonid );
			personList.add(persson);
 			String fromorgid = getAttribute("orgpersonid");
			List toorgid=personList;
			String msg=message;
			String notimsg="Block";
 			notificationDetails(fromorgid,personList,msg,notimsg,notificationService);
 			 
		}catch(Exception e){
			if(Notificationlogger.isInfoEnabled()){
				Notificationlogger.error("error in notifythisperson() in NotificationAction",e);
			}
		}
	}
	
	@Action("/giftnotifythisperson")
	public void giftnotifythisperson() {
		try {
			
			String organizationid=""+getAttribute("organizationid");
			String username=""+getAttribute("firstname");
			String senddate="";
			String coursetitle="";
			String fromorgid="";
			
			List<?> giftcourselist= giftCourseService.getGiftcourseList(giftcourseid);
	 		List<?> getOrgpersonid = notificationService.getOrgpersonid(organizationid);
	 		
			if(giftcourselist.size()==1 && giftcourselist!=null) { 				
		 	 	   Map personalMap = (Map)giftcourselist.get(0);
		 	 	   String senddates = ""+personalMap.get("senddate");
		 	 	   String[] date=senddates.split(" ");
		 	 	   senddate = date[0];
		 	 	   coursetitle = ""+personalMap.get("coursetitle");
		 	 	 }
			if(getOrgpersonid.size()==1 && getOrgpersonid!=null){
				Map map = (Map)getOrgpersonid.get(0);
				fromorgid=""+map.get("orgpersonid");
			}
			
			List personList=new ArrayList();
			HashMap persson=new HashMap();
			persson.put("orgpersonid",getAttribute("orgpersonid") );
			personList.add(persson);
			
 			String msg	=	"The course "+coursetitle+" will be gifted to "+username+" on the "+senddate+"";
			List toorgid = personList;
			String notimsg="GiftCourse";
  			notificationDetails(fromorgid,toorgid,msg,notimsg,notificationService); 
			
		}catch(Exception e){
			if(Notificationlogger.isInfoEnabled()){
				Notificationlogger.error("error in notifythisperson() in NotificationAction",e);
			}
		}
	}
	/**
	 * @author mano_r
	 * 
	 */
	@Action(value = "/getNotificationforindividualUserAction")
	public void getNotificationforindividualUserMtd() {
		String personid = getAttribute("orgpersonid");
		List<?> userList = notificationService.getNotificationMessage(Integer.parseInt(personid));
		checkobj().toJsonResponse(userList);
	}
	
	/**
	 * To make notification as Read
	 * @author venkatraman_v
	 */
	@Action(value = "/NotificationReadAction")
	public void getNotificationAsRead() {
		String personid = getAttribute("orgpersonid");
		int userList = notificationService.getNotificationReadMessage(personid);  
		checkobj().toJsonString("success");
	}
	

	/**
	 * @author mano_r
	 * @return
	 */  
	@Action(value="/getNotifcationToViewAction")
	public void getNotificationToViewMtd(){
		String organizationid=getAttribute("organizationid");
		List<?> notificationlist = notificationService.getNotification(categorystatus,Long.parseLong(organizationid));
		checkobj().toJsonResponse(notificationlist);
	}

	
	/**
	 * Notification From To Msg Goes here
	 * @author venkatraman_v
	 *  
	 */
	
	public void notificationDetails(String fromorgid,List userList,String msg,String textmsg,NotificationService notificationService){	
		 
		
		Notification notification = new Notification();
		Date date = new Date();
		HashSet<Notificationreceived> notificationreceiveset = new HashSet<Notificationreceived>();
		for (int i = 0; i < userList.size(); i++) {
			Map modelmapObj = (Map) userList.get(i);
			String personid = "" + modelmapObj.get("orgpersonid");
			Notificationreceived notificationreceived = new Notificationreceived();
			Orgperson orgperson = new Orgperson();
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			notificationreceived.setOrgperson(orgperson);
			notificationreceived.setNotificationid(notification);
			notificationreceived.setReviewedstatus("U");
			notificationreceiveset.add(notificationreceived);
		}
		notification.setNotificationdescription(msg);
		notification.setNotificationcategory(textmsg);
		notification.setNotificationdate(new Timestamp(date.getTime()));
		notification.setNotificationstatus("A");
		Orgperson orgperson = new Orgperson();
		orgperson.setOrgpersonid(Integer.parseInt(fromorgid));
		notification.setOrgperson(orgperson);
		notification.setNotificationstatus("A");
		notification.setNotificationreceived(notificationreceiveset);
		notificationService.save(notification);
	
	}
	
	/* Getter Setter For Notification Action class */

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNotificationdesc() {
		return notificationdesc;
	}

	public void setNotificationdesc(String notificationdesc) {
		this.notificationdesc = notificationdesc;
	}

	public String getCategorystatus() {
		return categorystatus;
	}

	public void setCategorystatus(String categorystatus) {
		this.categorystatus = categorystatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPersonid() {
		return topersonid;
	}

	public void setPersonid(String personid) {
		this.topersonid = personid;
	}

	public String getGiftcourseid() {
		return giftcourseid;
	}

	public void setGiftcourseid(String giftcourseid) {
		this.giftcourseid = giftcourseid;
	}
	
}
