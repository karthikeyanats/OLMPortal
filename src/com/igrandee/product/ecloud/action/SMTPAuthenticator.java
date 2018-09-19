package com.igrandee.product.ecloud.action;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/** 
 * @author raja_r
 *
 */
public class SMTPAuthenticator extends Authenticator {
	String MailUser = null;
	String MailUserKey = null;
	public SMTPAuthenticator(String MailUser,String MailUserKey){
		this.MailUser = MailUser;
		this.MailUserKey = MailUserKey;		
	} 
	
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(MailUser, MailUserKey);
	}
	 
}
