package com.igrandee.product.ecloud.action.pages.user;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.service.Pages.user.GiftCourseService;
import com.igrandee.product.ecloud.util.commons.IgCommons;

@Component
public class GiftCourseRemainder {
	
	@Autowired
	GiftCourseService giftCourseService;
	
	@Autowired
	IgCommons igCommons;
	
	public void sendRemainderEmail(){
		
		ResourceBundle ap = ResourceBundle.getBundle("application"); 		
		ResourceBundle sb = ResourceBundle.getBundle("serversetup");
		String productname = ap.getString("label.productname");		
		String contxtpath=sb.getString("ecloudurl"); 
		
		Object masterdetails[]=giftCourseService.getMasterAdminDetails();
		
		String adminname="";		
		String phonenumber="";
		String emailid="";
		String url="";
		
		if(masterdetails.length!=0){
			adminname=(String) masterdetails[0]!=null?(String) masterdetails[0]:"";
			phonenumber=(String) masterdetails[1]!=null?(String) masterdetails[1]:"";
			emailid=(String) masterdetails[2]!=null?(String) masterdetails[2]:"";
			url=(String) masterdetails[3]!=null?(String) masterdetails[3]:"";
		}
 		
		List<?> remainderList=giftCourseService.getTodaysGifts();
				
		if (remainderList.size()>0) {
			
			 Iterator<?> listIterator=remainderList.iterator();
			    while(listIterator.hasNext()){
			         Map<?,?> remainderMap=(Map<?, ?>) listIterator.next();
			         String recipientname=remainderMap.get("recipientname").toString();
			         String recipientmail=remainderMap.get("recipientmail").toString();
			         String username=remainderMap.get("username").toString();
			         String password=remainderMap.get("password").toString();
			         String message=remainderMap.get("message").toString();
			         String sender=remainderMap.get("sender").toString();
			         String coursetitle=remainderMap.get("coursetitle").toString();
			         Integer courseid=Integer.parseInt(remainderMap.get("courseid").toString());
			
				String mailSubject = "Gift Course";
				String mailContent ="";		
				String recipientType = "To";
				boolean isMailContentHtml = true;
				mailContent += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
				mailContent +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
						"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> " +
						"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
						"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> " +
						"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'><h3>Dear "+recipientname+"</h3>The course "+coursetitle+" has been gifted to you by "+sender+"</h4>" +
						"<h2>Message: </h2>"+"<h4>"+message+" </h4>"+
						"<a href=\""+contxtpath+"/authourinvite.jsp?courseid="+courseid+"\">"+" <button style=\"{background-color:#5bb75b;border-radius: 5px;height:35px;color:white;border-color: #5bb75b;}\"><b>Click here to see the Gifted Course<b></button></a>"+
						" <h6>Username:"+username+"</h6>"+" <h6>Password:"+password+"</h6>"+
						"<h4>Thank you.</h4><p>The Reamedi Team</p>"+
						"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
						"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
						"<tr><td><font color='#ffffff'>Contact : "+adminname+"</font><br><font color='#ffffff'>Phone: "+phonenumber+"</font><br><font color='#ffffff'>Email: "+emailid+"</font><a href='mailto: style='color:#ffffff; text-decoration:none;'> </a><br> " +
						"<font color='#ffffff'>Website: "+url+"</font><a href='http://' target='_blank' style='color:#ffffff; text-decoration:none;'></a></td></tr></table></td></tr></table>";
				mailContent += "</body>";
				mailContent += "</html>";	
				igCommons.sendEmail(recipientmail, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
		  }
		}
		
	}

}
