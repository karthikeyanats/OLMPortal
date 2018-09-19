
package com.igrandee.product.ecloud.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.action.SMTPAuthenticator;

@Component
@Scope("prototype")
public class EmailWriter extends MasterActionSupport {

	private static final long serialVersionUID = 1L;

	public String write(String email,String organizationid,int orgpersonid,Long personid,int loginid){
		String orgname=""+getAttribute("orgname");
		String username=""+getAttribute("firstname");
		String emailaddress=""+getAttribute("orgemail");
		String phone=""+getAttribute("orgphonenumber");
		String url=""+getAttribute("orgurl");

		if(phone.equals("null")){
			phone="-";
		}
		if(url.equals("null")){
			url="-";    	
		}
		if(emailaddress.equals("null")){
			emailaddress="-";
		}
		ResourceBundle rb = ResourceBundle.getBundle("smtpconfig"); 
		ResourceBundle sb = ResourceBundle.getBundle("serversetup"); 
		ResourceBundle ap = ResourceBundle.getBundle("application"); 

		String smtphostname		=	rb.getString("smtp_hostname");
		String smtpport		    =	rb.getString("smtp_hostport");
		String admin_mailid		=	rb.getString("admin_mailid"); 
		String admin_mailpassword	=	rb.getString("admin_mailpassword");
		String productname = ap.getString("label.productname");
		String registration = ap.getString("registration");

		String cntxtpath=sb.getString("ecloudurl"); 
		String loginpath=cntxtpath+"/login.jsp?emailid="+email+"&organizationid="+organizationid+"&orgpersonid="+orgpersonid+"&personid="+personid+"&loginid="+loginid;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtphostname);
		props.put("mail.smtp.socketFactory.port", smtpport);
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", smtpport); 
		Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
		Session session = Session.getDefaultInstance(props,auth);
		session.setDebug(true);		
		String mail_html_cont ="";			    
		mail_html_cont += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
		mail_html_cont +="<table width='600' border='0' align='center' cellpadding='0' " +
				"cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
					"<tr><td align='left' valign='top'>" +
						"<div style='font-size: 38px;background-color:#2b3542;text-align: center;" +
							"color: white; height: 56px; padding: 8px;'>"+productname+"</div></td>" +
					"</tr> " +
					"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; " +
						"font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
							"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> " +
				"<div><br><h3>"+username+" invited you to join</h3><h2>"+orgname+"</h2><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'>Please join "+orgname+". We will be using this as a hub for all learning activities in our organization.</h4>" +
				"<div style='margin-left: 90px;'><br><br><a  href='"+loginpath+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width:200;text-decoration: none;color: white;'>Click here to login</a><br><br></div> </div></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
				"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: white;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
				"<tr><td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:<a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</font></a><br> " +
				"Website: <a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
		mail_html_cont += "</body>";
		mail_html_cont += "</html>";			
		MimeMessage msg = new MimeMessage(session); 
		try{
			msg.setFrom(new InternetAddress(admin_mailid));  
			msg.setRecipients(Message.RecipientType.TO, email); 
			msg.setSubject(registration); 
			msg.setContent(mail_html_cont.toString(),"text/html");
			Transport.send(msg);	
			return "SUCCESS";
		}
		catch(AddressException e){						
			return e.getCause().getMessage();
			
		}
		catch(MessagingException e){	
			
			return e.getCause().getMessage();	
			
		}
	}

	public String writeEmail(String email,String organizationid,String invitationid,String attachmentpath,String message) throws UnsupportedEncodingException{
		String orgname=""+getAttribute("orgname");
		String username=""+getAttribute("firstname");
		String emailaddress=""+getAttribute("orgemail");
		String phone=""+getAttribute("orgphonenumber");
		String url=""+getAttribute("orgurl");

		if(phone.equals("null")){
			phone="-";
		}
		if(url.equals("null")){
			url="-";    	
		}
		if(emailaddress.equals("null")){
			emailaddress="-";
		}
		ResourceBundle rb = ResourceBundle.getBundle("smtpconfig"); 
		ResourceBundle sb = ResourceBundle.getBundle("serversetup"); 
		ResourceBundle ap = ResourceBundle.getBundle("application"); 

		String smtphostname		=	rb.getString("smtp_hostname");
		String smtpport		    =	rb.getString("smtp_hostport");
		String admin_mailid		=	rb.getString("admin_mailid"); 
		String admin_mailpassword	=	rb.getString("admin_mailpassword");
		String productname = ap.getString("label.productname");
		String registration = ap.getString("registration");
		String mailregardsmessage = ap.getString("label.mailteam"); 

		String cntxtpath=sb.getString("ecloudurl");
		String serverpath=sb.getString("serverpath");
		String loginpath=cntxtpath+"/login.jsp?emailid="+email+"&organizationid="+organizationid+"&invitationid="+invitationid;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtphostname);
		props.put("mail.smtp.socketFactory.port", smtpport);
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", smtpport); 
		Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
		Session session = Session.getDefaultInstance(props,auth);
		session.setDebug(true);	
		
		String mail_html_cont ="";			    
		mail_html_cont += "<html><body style='background-color: rgba(128, 128, 128, 0.38);'>";
		mail_html_cont +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'>" +
				"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> " +
				"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>" +
				"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'> " +
				"<div><br><h3>"+username+" invited you to join</h3><h2>"+orgname+"</h2><h4 style='text:align:center;'><font face='Verdana' size=\"3\" color='#525252'>"+java.net.URLDecoder.decode(message)+"</h4>" +
				"<div style='margin-left: 90px;'><br><br><a  href='"+loginpath+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width:200;text-decoration: none;color: white;'>Click here to login</a><br><br></div> </div><h3>Thanks,<br>"+mailregardsmessage+"</h3></td></tr></table></td></tr><tr><td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
				"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: white;padding: 10px;border: 1px solid rgb(225, 225, 243)'>" +
				"<tr><td><font color='#ffffff'>Contact :"+username+"</font><br><font color='#ffffff'>Phone:"+phone+"</font><br><font color='#ffffff'>Email:<a href='mailto:"+emailaddress+" style='color:#ffffff; text-decoration:none;'> "+emailaddress+"</font></a><br> " +
				"Website: <a href='http://"+url+"' target='_blank' style='color:#ffffff; text-decoration:none;'>"+url+"</a></td></tr></table></td></tr></table>";
		mail_html_cont += "</body>";
		mail_html_cont += "</html>";			
		MimeMessage msg = new MimeMessage(session); 
		try{
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mail_html_cont, "text/html");
			if(attachmentpath!=null && attachmentpath.length() > 3 && StringUtils.equalsIgnoreCase(FilenameUtils.getExtension(attachmentpath), "pdf")){
				MimeBodyPart attachPart = new MimeBodyPart();
				String attachFile = serverpath+attachmentpath;
				DataSource source = new FileDataSource(attachFile);
				attachPart.setDataHandler(new DataHandler(source));
				attachPart.setFileName(new File(attachFile).getName());
				multipart.addBodyPart(attachPart);

			}
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			msg.setFrom(new InternetAddress(admin_mailid));  
			msg.setRecipients(Message.RecipientType.TO, email); 
			msg.setSubject(registration); 
			Transport.send(msg);	
			return "SUCCESS";
		}
		catch(AddressException e){
			return "Address error";
		}
		catch(MessagingException e){
			return "SMTP host error";

		}
	}
	
}

