/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.util.commons
 * IgCommons.java
 * Created Oct 8, 2014 3:33:26 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.util.commons;

import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * GENERIC JAVAMAIL API INTEGRATION UTILITY, SINGLE METHOD sendEmail() CAPABLE OF WORKING WITH ATTACHMENTS AS WELL NON ATTACHED FILES OF ANY TYPE LIKE 
 * 1. TEXT
 * 2. HTML
 * 3. MULTIPART ETC.,
 * 
 * @author balajichander_r
 *
 */
@Component 
@Scope("prototype")
public class IgCommons{ 
	
	private static final  Logger igCommonsLogger = Logger.getLogger(IgCommons.class); 
	private static  ResourceBundle rb;    
	private  Properties SMTP_PROPERTIES = getSMPTProperties();
	
	/**
	 * 
	 * <b>toUserName  : </b> <br> The recipient email address <br>
	 * <b>mailSubject : </b><br> Email subject to be notified in email<br>
	 * <b>mailContent : </b><br> Mail content whereas the mail content may be a plain text or HTML in case if mail content is html see isMailContentHtml <br>
	 * <b>recipient type : </b><br>  Standard mail recipient types <br>
	 * <blockquote> Mail recipient types may be on of the following, <br>
	 *  1. BCC<br>
	 *  2. CC<br>
	 *  3. TO <br>
	 * </blockquote> <br> 
	 * <b>isMailContentHtml: </b><br> If your content is not plain text ( most probably HTML ) then set this to <em>true</em> else if the mail content is plain 
	 * text set this to <em>false</em>.<br> 
	 * <b>ccAuthor: </b><br>If  the mail needs to be carbon copied to the admin then set this to <em>true</em> else set this to <em>false</em>.
	 *  
	 * @param toUserName
	 * @param mailSubject
	 * @param mailContent
	 * @param recipientType
	 * @param isMailContentHtml
	 * @param ccAuthor
	 * @param bccAuthor
	 * @param hasAttachment
	 * @param attachmentURI	
	 * @param attachmentName
	 */
	public String sendEmail(String toUserName, String mailSubject, String mailContent, String recipientType, boolean isMailContentHtml, boolean bccAuthor, boolean hasAttachment, String attachmentURI, String attachmentName){
		String status = "Failure";
		try {  
			Message message =null;
			Properties SMTPProperties = SMTP_PROPERTIES;
			Session currentMailSession = Session.getInstance(SMTPProperties,this.getMailPasswordAuthenticationInstance());  
			currentMailSession.setDebug(true);// mail log on to trace errors 
			 
			if(hasAttachment){
				 message = this.getMailMessageWithAttachment(currentMailSession, toUserName, mailSubject, mailContent, recipientType, isMailContentHtml, bccAuthor,attachmentURI, attachmentName);
			}else{
				 message = this.getMailMessage(currentMailSession, toUserName, mailSubject, mailContent, recipientType, isMailContentHtml, bccAuthor);
			};
			
			status = this.email(message);
			/*igCommonsLogger.info("sending  ======= ====== ======== ====== ====== ===== === ... sent ... ... .. .. . :-)  |" );
			igCommonsLogger.info("################################# EMAIL SENT SUCCESSFULLY #############################");*/ 
		} catch (Exception e) {
			e.printStackTrace();
			igCommonsLogger.error(e.getCause());
		}
		return status; 
	}
	
	/**
	 * 
	 * @param session
	 * @param toUserName
	 * @param mailSubject
	 * @param mailContent
	 * @param recipientType
	 * @param isMailContentHtml
	 * @param bccAuthor
	 * @param attachmentURI
	 * @param attachmentName
	 * @return message | Message
	 */
	private Message getMailMessageWithAttachment(Session session, String toUserName, String mailSubject, String mailContent, String recipientType, boolean isMailContentHtml, boolean bccAuthor,String attachmentURI, String attachmentName) {
		BodyPart messageBodypart;Multipart multipart;MimeBodyPart mimeBodyPart;Message message = null;
		try {
			 
			message  = new MimeMessage(session);
			messageBodypart = new MimeBodyPart();
			multipart = new MimeMultipart();
			mimeBodyPart = new MimeBodyPart(); 

			rb = ResourceBundle.getBundle("smtpconfig"); 
			message.setFrom(new InternetAddress(rb.getString("admin_mailid")));
			message.addRecipient(getRecipientType(recipientType) , new InternetAddress(toUserName ));
			
			if(bccAuthor){
				message.addRecipient(RecipientType.BCC , new InternetAddress(rb.getString("admin_mailid")));
			} 
			
			message.setSentDate(new Date());
			message.setSubject(mailSubject );   
			  
			if(isMailContentHtml){
				messageBodypart.setContent(mailContent.toString(), "text/html");
			}else{
				messageBodypart.setText( mailContent );
			}; 
			
		    DataSource dataSource = new FileDataSource(attachmentURI);  
		    mimeBodyPart.setDataHandler(new DataHandler(dataSource));
		    mimeBodyPart.setFileName(attachmentName); 
		     
			multipart.addBodyPart(messageBodypart);
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart); 
			
		} catch (Exception e) {
			
			igCommonsLogger.error(e.getCause());
		}
		
		return message;
	}

	/**
	 * 
	 * @return
	 */
	private static Properties getSMPTProperties(){
		Properties properties = null;
		rb = ResourceBundle.getBundle("smtpconfig");
		try {  
			properties = System.getProperties();
			properties.put("mail.smtp.host", rb.getString("smtp_hostname"));  
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "true"); 
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.port", rb.getString("smtp_hostport"));
			properties.put("mail.smtp.socketFactory.port", rb.getString("smtp_hostport"));
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			
		} catch (Exception e) {
			igCommonsLogger.error(e.getCause());
		}
		return properties;
	}
	 
	/**
	 * 
	 * @return
	 */
	private Authenticator getMailPasswordAuthenticationInstance(){
		Authenticator authentication = null;
		rb = ResourceBundle.getBundle("smtpconfig");
		try {
			authentication =	new javax.mail.Authenticator() { 
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication( rb.getString("admin_mailid") , rb.getString("admin_mailpassword"));
	            }
			};
		} catch (Exception e) {
			igCommonsLogger.error(e.getCause());
		}
		return authentication;
	}
	
	/**
	 * 
	 * @param session
	 * @param toUserName
	 * @param mailSubject
	 * @param mailContent
	 * @param recipientType
	 * @param isMailContentHtml
	 * @return
	 */
	private Message getMailMessage(Session session, String toUserName, String mailSubject, String mailContent, String recipientType, boolean isMailContentHtml, boolean bccAuthor){
		Message message = null;
		
		try {
			
			message  = new MimeMessage(session);
			rb = ResourceBundle.getBundle("smtpconfig"); 
			message.setFrom(new InternetAddress(rb.getString("admin_mailid")));
			message.addRecipient(getRecipientType(recipientType) , new InternetAddress(toUserName ));
			if(bccAuthor){
				message.addRecipient(RecipientType.BCC , new InternetAddress(rb.getString("admin_mailid")));
			} 
			message.setSentDate(new Date());
			message.setSubject(mailSubject );
			if(isMailContentHtml){
				message.setContent(mailContent.toString(),"text/html");
			}else{
				message.setText(  mailContent );
			} 
			
		} catch (Exception e) {
			igCommonsLogger.error(e.getCause());
		}
		
		return message;
		
	}
	
	/**
	 * 
	 * @param recipientType
	 * @return
	 */
	private RecipientType getRecipientType(String recipientType){
		RecipientType returnRecipientType =  null;
		try { 
			if(recipientType.toUpperCase().equals("CC")){
				returnRecipientType = RecipientType.CC;
			}else if(recipientType.toUpperCase().equals("BCC")){
				returnRecipientType = RecipientType.BCC;
			}else if(recipientType.toUpperCase().equals("TO")){
				returnRecipientType = RecipientType.TO;
			} 
		} catch (Exception e) {
			igCommonsLogger.error(e.getCause());
		}
		return returnRecipientType;
	}
	
	/**
	 * 
	 * @param message
	 */
	private String email(Message message){
		try {
			igCommonsLogger.info("sending  ======= ====== ======== ====== ====== ===== === ... message ... ... .. .. . :-|  " );
			Transport.send(message);
			return "success";
		} 
		catch(AddressException e){
			igCommonsLogger.error(e.getCause());
			return "Address error";
		}
		catch(MessagingException e){
			igCommonsLogger.error(e.getCause());
			return "SMTP host error";
		}
	}
	
}
