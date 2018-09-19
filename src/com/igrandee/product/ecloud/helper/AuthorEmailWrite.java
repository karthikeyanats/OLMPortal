
package com.igrandee.product.ecloud.helper;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.action.SMTPAuthenticator;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;


@Component
@Scope("prototype")
public class AuthorEmailWrite extends MasterActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrganizationViewService organizationviewservice;
 
	public String write(String email,int courseid,int courseinvitid){
	String orgname=""+getAttribute("orgname");
	String organizationid=""+getAttribute("organizationid");
    String username=""+getAttribute("firstname");
    String emailid=""+getAttribute("email");
    String emailaddress=null;
    String phone=""+getAttribute("phonenumber");
    String url=null;     
    String userstatus= null;
    String orgstatus=""+getAttribute("orgstatus");
    if(getAttribute("url")==null){
    	url="-";
    }else{
    	url="http://"+getAttribute("url");    
    }
    if(phone.equals("null")){
    	phone="-";
    }
   if(emailid.equals("null")){
	   emailid="-";
   }    
    if(getAttribute("email")==null){
    	emailaddress="-";
    }else{
    	emailaddress="mailto:"+getAttribute("email");
    }
    List<?> authordetails=organizationviewservice.getCourseDetails(courseid);
    List<?> Authourlist=organizationviewservice.getAuthourlogin(email,organizationid);
    if(Authourlist.size()==0){
    	userstatus="yes";
    }else{
    	userstatus="no";
    }    
    Map Authourmap=(Map) authordetails.get(0);
    
    /**
     * course details
     */
    
    String coursetitle=""+Authourmap.get("coursetitle");
     String enrollpersoncount=""+Authourmap.get("personidcount"); 
    String price=""+Authourmap.get("price"); 
    if(price==null || price.equals("null"))
    	price="Free";
    
    	
    String firstname=""+Authourmap.get("firstName");
    String lastnamechk=""+Authourmap.get("lastName");
    
    String lastname;
    if(lastnamechk.equals("null")){    
    	lastname="";
    }else{
    	lastname=lastnamechk;
    }
    String image=""+Authourmap.get("courselogo");
    
    ResourceBundle rb = ResourceBundle.getBundle("smtpconfig"); 
	ResourceBundle sb = ResourceBundle.getBundle("serversetup");
	ResourceBundle appb = ResourceBundle.getBundle("application"); 
	String smtphostname		=	rb.getString("smtp_hostname");
	String smtpport		    =	rb.getString("smtp_hostport");
	String admin_mailid		=	rb.getString("admin_mailid"); 
	String admin_mailpassword	=	rb.getString("admin_mailpassword");

	String cntxtpath=sb.getString("ecloudurl"); 
		
	String mailheading	=	appb.getString("label.productname");
	String mailsubject	=	appb.getString("invitation");
	String priceval		= 	appb.getString("label.charset");
	String freeprice	= null;
	
	if( price.equals("Free")){    
		freeprice="Price: FREE";		
	 }
	else{
		freeprice="Price : ("+priceval+") "+price;
	}
	String loginpath="";
	if(orgstatus.equals("M")){
		loginpath=cntxtpath+"/authourinvite.jsp?emailid="+email+"&courseid="+courseid+"&userstatus="+userstatus+"&courseinvitationid="+courseinvitid;
	}else if(orgstatus.equals("A")){
		loginpath=cntxtpath+"/login.jsp?emailid="+email+"&courseid="+courseid+"&userstatus="+userstatus+"&invitationid="+courseinvitid+"&emailid="+email+
					"&organizationid="+organizationid;	
	}
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
    mail_html_cont += "<html> <head><meta charset= 'ISO-8859-1'></head><body style='background-color: rgba(128, 128, 128, 0.38);'>";
    mail_html_cont +="<table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'>"+
"<tbody><tr> <td><table width='600' border='0' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' align='center'>"+
"<tbody><tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+mailheading+"</div></td></tr>"+
"<tr><td align='center' valign='top' bgcolor='#f1f69d' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'>"+
"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'><tbody><tr><td>"+
"<font style='font-family: Georgia, 'Times New Roman', Times, serif; color:#000000; font-size:25px'><strong style=''><em>"+username+" invited you to join "+orgname+"</em></strong></font>"+
"</td></tr></tbody></table></td></tr><tr><td align='left' valign='top' bgcolor='#ffffff'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"+
"<tbody><tr><td width='1%'>&nbsp;</td><td width='58%' align='left' valign='top'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"+
"<tbody><tr><td align='left' valign='top'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='95%'>"+
"<table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr>"+
"<td height='35' align='left' valign='middle' style='border-bottom:2px dotted #000000'><font style='font-family: Georgia, 'Times New Roman', Times, serif; color:#000000; font-size:25px'>"+
"<strong><em>Please join "+orgname+" for this course. The Course Details are</em></strong></font></td></tr><tr><td>&nbsp;</td><td></td></tr><tr></tr><tr><td>"+
"<table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td rowspan='3' style='width: 110px;'>"+
"<img src='"+cntxtpath+"/OpenFile?r1=serverpath&r2="+image+"' height='100' width='100'></td></tr><tr><td>"+
"<font style='font-family: Georgia, Times New Roman, Times, serif; color:#f58220; font-size:20px;'>"+coursetitle+"</fpnt> </td></tr>"+
"<tr><td><table width='100%'><tbody><tr><td><font style='font-family: Georgia, Times New Roman, Times, serif; color:#000000; font-size:20px'>Enrolled Users:"+enrollpersoncount+"<font></td><td>"+
"<font style='font-family: Georgia, Times New Roman, Times, serif; color:#000000; font-size:20px;'>"+freeprice+"</span></font></td></tr></tbody></table>"+
"<table width='100%'><tbody><tr><td><font style='font-family: Georgia, Times New Roman, Times, serif; color:#000000; font-size:20px'>"+firstname+lastname+"</font></td><td> "+
"<a href='"+loginpath+"' style='position: relative; display: block; padding: 10px 15px; background: #66c43d;background: -webkit-gradient(linear, left bottom, left top, color-stop(0, #59ba33), color-stop(1, #73ca4b)); border-radius: 4px;width: 150px ! important;text-decoration: none;color: white;text-align:center;'>"+
"Click here to login</a></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr>"+
"<td height='10' style='border-bottom:4px solid #d0d1d3'></td></tr><tr><td height='10'></td></tr></tbody></table></td></tr></tbody></table></td><td width='5%'>&nbsp;</td>"+
"</tr></tbody></table></td></tr></tbody></table></td></tr></tbody>"+
"</table><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: white;padding: 10px;'><tbody><tr><td>"+
"Contact :<span style='color:white;text-decoration:none;'>"+username+"</span><br>Phone:<span style='color:white'>"+phone+"</span><br> Email: "+
"<a href="+emailaddress+" style='color:white;'>"+emailid+"</a><br>Website: <a href="+url+" target='_blank' style='color:white; text-decoration:none;'>"+url+"</a>"+
"</td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table>";
    mail_html_cont += "</body>";
    mail_html_cont += "</html>";
    
	MimeMessage msg = new MimeMessage(session); 
	try{
	msg.setFrom(new InternetAddress(admin_mailid));  
	msg.setRecipients(Message.RecipientType.TO, email); 
	msg.setSubject(mailsubject); 
	msg.setContent(mail_html_cont.toString(),"text/html");
	Transport.send(msg);	
	return "SUCCESS";
	}
	catch(AddressException e){
		e.printStackTrace();
		return "FAILURE";
	}
	catch(MessagingException e){
		e.printStackTrace();
		return "FAILURE";
	}
}

}



