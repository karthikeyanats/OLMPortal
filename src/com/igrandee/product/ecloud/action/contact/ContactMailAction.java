package com.igrandee.product.ecloud.action.contact;


import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.SMTPAuthenticator;
import com.igrandee.product.ecloud.service.Pages.user.GiftCourseService;
import com.igrandee.product.ecloud.util.commons.IgCommons;
import com.igrandee.product.ecloud.viewmodel.ContactMailVM;
import com.sun.jersey.api.core.InjectParam;
import com.sun.mail.iap.ConnectionException;

@Path("/contactmail")
public class ContactMailAction{

	@InjectParam
	GiftCourseService giftCourseService;

	@Autowired
	IgCommons igCommons;

	private static Logger ContactMailActionlogger=Logger.getLogger(ContactMailAction.class);
	public static ResourceBundle resourcebundle;

	/**
	 * @author vetrichelvi
	 * @param contactmail
	 * @return 
	 * @return 
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createContactmail(ContactMailVM contactmailVM) throws ConnectionException{

		try{
			System.out.println("inside Post id");
			System.out.println("giftCourseService is "+giftCourseService);
			resourcebundle	= ResourceBundle.getBundle("smtpconfig");
			String smtphostname = resourcebundle.getString("smtp_hostname");
			String smtpport		= resourcebundle.getString("smtp_hostport");
			String admin_mailid = resourcebundle.getString("admin_mailid");
			String admin_mailpassword = resourcebundle.getString("admin_mailpassword");

			ResourceBundle ap = ResourceBundle.getBundle("application");
			String productname = ap.getString("label.productname");

			List<?> masterdetails = giftCourseService.getMasterAdminDetailsinList();

			String adminname="";		
			String phonenumber="";
			String emailid="";
			String url="";
			System.out.println("masterdetails is "+masterdetails);
			if(masterdetails!=null?masterdetails.size()!=0:false) {
				for(int i=0;i<masterdetails.size();i++) {
					Map<?,?> personalMap = (Map<?,?>)masterdetails.get(i);
					adminname = ""+personalMap.get("contactperson");
					phonenumber = ""+personalMap.get("number"); 
					emailid=""+personalMap.get("emailid");
					url = ""+personalMap.get("orgurl");
				}
			}
			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", smtphostname);
			properties.put("mail.smtp.socketFactory.port", smtpport);
			properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", smtpport); 


			Authenticator auth = new SMTPAuthenticator(admin_mailid,admin_mailpassword); 
			Session session = Session.getDefaultInstance(properties,auth);
			session.setDebug(true);

			String mail_html_cont ="";
			mail_html_cont += "<html><body style='background-color: #666;'>"; 
			mail_html_cont +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'> " +
					"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> "+
					"<tr><td align='left' valign='top'><div style='font-size: 20px;background-color:white;text-align: center;color: white; height: 56px; padding: 8px;'></div></td>  </tr>  <tr>    <td align='center' valign='top' bgcolor='white' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>" +				
					"<tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'><h4><font face='Verdana' size=\"3\" color=#FF6600;><b> Get in Touch, </font></h4>   <div style='font-size:15px;color:black;'> You are welcome to contact Reamedi cloud for a free quote or for discussing about your specific needs. We are here to identify the most suitable solutions for you. Our goal is to reply to every email within two business days." +
					"<br></br><br>Sincerely,<br>Reamedi Cloud</div>  <div> <br><h2></h2> </div></td> </tr>" +
					"</table></td></tr><tr>" +
					"<td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'><table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr><td align='left' valign='top' style='color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px;'>" +
					"Contact :"+adminname+"<br>Phone: "+phonenumber+"<br>Email: "+emailid+"<br>Website: "+url+"</td> </tr></table></td>  </tr></table>";
			mail_html_cont += "</body>";
			mail_html_cont += "</html>";   
			System.out.println("mail_html_cont us <<<<<<< "+mail_html_cont);
		
		

			MimeMessage msg = new MimeMessage(session); 
			msg.setFrom(new InternetAddress(admin_mailid));  
			msg.setRecipient(Message.RecipientType.TO,  new InternetAddress(contactmailVM.getEmail().toString()));


			msg.setSubject(" Contact Us Mail");			
			msg.setContent(mail_html_cont.toString(),"text/html");
			Transport.send(msg);
			System.out.println("successfuly sent");	
			
					

			/*<--------------------------------------AdminId-------------------------------------->*/


			String name= contactmailVM.getName();
			String email = contactmailVM.getEmail();
			String organization = contactmailVM.getOrganizationname();
			String message= contactmailVM.getMessage();
			String address = contactmailVM.getAddress();


			String mail_html ="";
			mail_html += "<html><body style='background-color: #666;'>"; 
			mail_html +="<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style=' border: 1px solid rgb(225, 225, 243);'> " +
					"<tr><td align='left' valign='top'><div style='font-size: 38px;background-color:#2b3542;text-align: center;color: white; height: 56px; padding: 8px;'>"+productname+"</div></td></tr> "+
					"<tr><td align='left' valign='top'><div style='font-size: 13px;background-color:white;text-align: center;color: white; height: 16px; padding: 8px;'></div></td>  </tr>  <tr>    <td align='center' valign='top' bgcolor='white' style='background-color:white; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:10px;'><table width='100%' border='0' cellspacing='0' cellpadding='0' style='margin-top:10px;'>  <tr><td align='left' valign='top' style='font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#525252;'>" +
					"<tr><td><h4><font face='Verdana' font-size: '20px' color=#666;>" +
					"<h4>Dear Team,<br><br>New Query from the below customer</font></h4>" +
					"<h4 style='color:#FF0000'>Customer Details</h4><hr><div style='font-size:13px;font-weight:700'>Name : "+name+"<br>E-mail : "+email+"<br>Organization : "+organization+"<br>Message : "+message+"<br>Address:"+address+"</div>  <div> <br><h2></h2> </div></td></tr></table></td></tr><tr>  " +
					"<td align='left' valign='top' bgcolor='#478730' style='background-color:#478730;'>" +
					"<table width='100%' border='0' cellspacing='0' cellpadding='15' style='background-color: #2b3542;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #000000;padding: 10px;border: 1px solid rgb(225, 225, 243)'><tr>" +
					"<td align='left' valign='top'" +
					"style='color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px;'><b>Contact :"+adminname+"<br>Phone: "+phonenumber+"<br>Email: "+emailid+"<br>Website: "+url+" </td>      </tr>    </table></td>  </tr></table>";
			mail_html += "</body>";
			mail_html += "</html>";   

			MimeMessage admin = new MimeMessage(session); 
			admin.setFrom(new InternetAddress(admin_mailid));
			
			admin.setRecipient(Message.RecipientType.TO,  new InternetAddress(admin_mailid)); 


			admin.setSubject("Customer Query Notification"); 
			
			admin.setContent(mail_html.toString(),"text/html");
			Transport.send(admin);
				
			/*<--------------------------------------AdminId End-------------------------------------->*/
			return Response.status(Status.OK).entity(Status.OK).build();	
		}

		catch(MessagingException e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
		}
		catch(Exception e)
		{
			if(ContactMailActionlogger.isInfoEnabled())
			{
				ContactMailActionlogger.error("error in newSignUp() in LoginAction",e);

			}
			return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
		}		

	}
}





