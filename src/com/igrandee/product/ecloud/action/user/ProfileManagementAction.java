package com.igrandee.product.ecloud.action.user;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Email;
import com.igrandee.core.contact.model.Phone;
import com.igrandee.core.login.model.Login;
import com.igrandee.core.login.service.LoginService;
import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.core.person.model.Person;
import com.igrandee.core.person.service.PersonService;
import com.igrandee.product.ecloud.helper.JsonHelper;

public class ProfileManagementAction extends AbstractIGActionSupport
{
	private static final long serialVersionUID = 1L;
	static final Logger ProfileManagementActionLogger = Logger.getLogger(ProfileManagementAction.class);


	@Inject 
	private PersonService personService;
	
	@Inject
	LoginService loginService;
	
	private Person personobj;
	private Phone phoneobj;
	private Contactinfo contactinfoobj;
	private Email emailobj;
	private Login loginobj;
	private JsonHelper jsonHelper;
	
	private String contactinfoid;
	private String firstName;
	private String lastName;
	private String dob;
	private String personid;
	private String emailid;
	private String email;
	private String contactnumber;
	private String phoneid;
	private String sex;
	private String oldpassword;
	private String newpassword;
	private String personphotourl;
	private String eduQualification;
	

	/**
	 * @author selvarajan_j
	 * <b>This process are call person details updating . The person Information like profile update</b> 
	 * @throws ParseException 
	 * 
	 */
 	@Action(value="/updatePersonalInfo")
	public void updatePersonDetails() throws ParseException
	{
 		try
 		{
	 		personobj = new Person();
	 		contactinfoobj = new Contactinfo();
	 		emailobj = new Email();
	 		phoneobj = new Phone();
	 		jsonHelper=new JsonHelper();
	 		
	 		contactinfoobj.setContactinfostatus('A');
			if(contactinfoid=="" || contactinfoid==null || contactinfoid.equals("") || contactinfoid.equalsIgnoreCase("null")) {
				
			} else {
				contactinfoobj.setContactinfoid(Long.parseLong(contactinfoid)); 
			}
			personobj.setFirstName(firstName);
			personobj.setPersonstatus('A');
			personobj.setPersonphotourl(personphotourl);
			personobj.setLastName(lastName);
			personobj.setEduqualification(eduQualification);
			personobj.setContactinfo(contactinfoobj);
			personobj.setSex(Integer.parseInt(sex));
 	 		personobj.setDob(new Date(Long.parseLong(dob))); 
	 		personobj.setPersonid(Long.parseLong(getAttribute("personid")));
	
	 		
			if(emailid=="" || emailid==null || emailid.equalsIgnoreCase("")){ 	
				
			}else {
				emailobj.setEmailid(Long.parseLong(emailid));
			}
			emailobj.setUserid(email);
			emailobj.setEmailstatus('A');
			emailobj.setContactinfo(contactinfoobj);
			Set<Email> setEmail = new HashSet<Email>();
			setEmail.add(emailobj); 
			contactinfoobj.setEmails(setEmail); 
	
			
			Set<Phone> phones = new HashSet<Phone>();   
			phoneobj.setNumber(contactnumber);
			phoneobj.setPhonestatus('A');
			if(phoneid=="" || phoneid==null || phoneid.equalsIgnoreCase("")){ 
				
			}else { 
				phoneobj.setPhoneid(Long.parseLong(phoneid));
			}
	 		phones.add(phoneobj);
	 		contactinfoobj.setPhones(phones); 
	 		personobj.setContactinfo(contactinfoobj);
			personService.update(personobj);
			
	 		jsonHelper.toJsonString("Success");
 		}
		catch(Exception e)
		{
			if(ProfileManagementActionLogger.isInfoEnabled()){ 
				ProfileManagementActionLogger.error("error in updatePersonDetails() in ProfileManagementAction ",e);  
			}
		}
	}

 	
	/**
	 * @author selvarajan_j
	 * This process are call the person personal password update  
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	@Action (value="/passwordReset")
	public void passwordReset() throws AddressException, MessagingException
	{
		
		String password=getAttribute("password");
		loginobj=new Login();
 		jsonHelper=new JsonHelper();
 		loginobj=loginService.get(Integer.parseInt(getAttribute("loginid")));
		if(password.equals(oldpassword)){
			loginobj.setPassword(newpassword);
 			loginService.update(loginobj);
			jsonHelper.toJsonString("resetsuccess");
		}else{
			jsonHelper.toJsonString("nomatch");
		}
	}
 	
 	
	public String getContactinfoid() {
		return contactinfoid;
	}

	public void setContactinfoid(String contactinfoid) {
		this.contactinfoid = contactinfoid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEduQualification() {
		return eduQualification;
	}


	public void setEduQualification(String eduQualification) {
		this.eduQualification = eduQualification;
	}


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getPhoneid() {
		return phoneid;
	}

	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getPersonphotourl() {
		return personphotourl;
	}

	public void setPersonphotourl(String personphotourl) {
		this.personphotourl = personphotourl;
	}

}
