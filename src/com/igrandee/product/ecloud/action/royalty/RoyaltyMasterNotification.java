package com.igrandee.product.ecloud.action.royalty;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.igrandee.product.ecloud.service.royalty.RoyaltyConfigService;
import com.igrandee.product.ecloud.util.commons.IgCommons;

@Component
public class RoyaltyMasterNotification extends MasterActionSupport{
	
	@Autowired
	RoyaltyConfigService royaltyConfigService;
	
	@Autowired
	OrgPersonService orgPersonService;
	
	@Autowired
	IgCommons igCommons;
	
	public void sendEmail(){
		
		Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date currentDate=new Date();
        Date nextDay=calendar.getTime();
        String currdate="";
        
        String dat[]=currentDate.toString().split(" ");
        dat[3]="23:59:59";
        for(String temp:dat){
        	currdate+=" "+temp;
        }
        
		if(!royaltyConfigService.isRoyaltySet(nextDay).get("course")&&!royaltyConfigService.isRoyaltySet(nextDay).get("live")){
		
    	String email=orgPersonService.getMasterEmail();
		
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
						"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> <h4 style='font-family: Open Sans !important; text-align : left;'> Dear &nbsp;"+"Master Admin"+"</h4> " +
						"<div><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'>No Royalty is set after " +currdate+ ", Plaese create a new royalty.</h4><h3>Thanks,<br>"+mailregardsmessage+"</h3>" +
						"</div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
						"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;height: 50px;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'></table></td></tr></table>";
				mailContent += "</body>";
				mailContent += "</html>";	
				igCommons.sendEmail(email, mailSubject, mailContent, recipientType, isMailContentHtml, false, false, null, null);
		
	       }
		}

	}
}
