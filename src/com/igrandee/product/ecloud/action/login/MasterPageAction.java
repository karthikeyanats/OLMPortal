package com.igrandee.product.ecloud.action.login;

import java.util.HashSet;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.igrandee.core.contact.model.Address;
import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Email;
import com.igrandee.core.contact.model.Phone;
import com.igrandee.core.contact.model.Url;
import com.igrandee.core.login.model.Login;

import com.igrandee.core.organization.model.Department;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.core.organization.model.Role;
import com.igrandee.core.person.model.Person;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Personallocation;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.igrandee.core.organization.service.OrganizationService;;


public class MasterPageAction 
{
	private static Logger MasterPagelogger = Logger.getLogger(MasterPageAction.class);

	@Inject
	OrgPersonService orgPersonService;
	
	@Inject
	OrganizationService organizationService;
	
	private Organization organizationobj;
	private Department departmentobj;
	private Contactinfo contactinfoobj;
	private Address addressobj;
	private Person personobj;
	private Role roleobj;
	private Personallocation personallocationobj;
	private Url urlobj;
	private Email emailobj;
	private Phone phoneobj;
	private Login loginobj;
	private Orgperson orgpersonobj;
	
	private String email;
	private String contactno;
	private String portalname;	
	private String organizationurl;	
	private String orgfirstname;
	private String orglastname;	
	private String orgemail;
	private String orgcontactno;	
	private String orgpassword;
	private String orgusername;
	private String organizationname;
	private String street1;
	private String street2;
	private String city;
	private String country;
	
	private String username;
	private String password;
	private String targetUrl;
	private String organizationid;

	@Action(value="/masterReg",results={@Result(name="SUCCESS",location="app/admin/course/masteradminreg.jsp")})
	public String masterReg() {
		try	{		 
			
		} 
		catch(Exception e){
			if(MasterPagelogger.isInfoEnabled())
			{
				MasterPagelogger.error("error in masterreg() in MasterPageAction",e);
			}
		}
		return "SUCCESS";
	}
		
	@Action(value="/saveorganization",results={@Result(name="scussesorg",location="app/admin/organization/organization.jsp")})
    public String saveOrg(){
		try{
			organizationobj = new Organization();
			departmentobj = new Department();
			contactinfoobj = new Contactinfo();
			addressobj = new Address();
			personobj = new Person();
			roleobj = new Role();
			personallocationobj= new Personallocation();
			
			urlobj = new Url();
			emailobj = new Email();
			phoneobj = new Phone();
			loginobj = new Login();
			orgpersonobj = new Orgperson();
		
			HashSet<Address> set = new HashSet<Address>();
			HashSet<Url> urls = new HashSet<Url>();
			HashSet<Phone> phones = new HashSet<Phone>();
			HashSet<Email> emails = new HashSet<Email>();
			HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();
			
			emailobj.setUserid(email);
			emailobj.setEmailstatus('A');
			emailobj.setContactinfo(contactinfoobj);
			emails.add(emailobj);
			
			phoneobj.setNumber(contactno);
			phoneobj.setPhonestatus('A');
			phoneobj.setContactinfo(contactinfoobj);
			phones.add(phoneobj);
			
			urlobj.setUrl(organizationurl);
			urlobj.setUrlstatus('A');
			urlobj.setContactinfo(contactinfoobj);
			urls.add(urlobj);
			
			addressobj.setStreet1(street1);
			addressobj.setStreet2(street2);
			addressobj.setCity(city);
			addressobj.setCountry(country);
			addressobj.setAddressstatus('A');
			addressobj.setContactinfo(contactinfoobj);
			set.add(addressobj);
			contactinfoobj.setAddresses(set);
			contactinfoobj.setContactinfostatus('A');
			contactinfoobj.setUrls(urls);
			contactinfoobj.setPhones(phones);
			contactinfoobj.setEmails(emails); 
		
			personobj.setFirstName(orgfirstname);
			personobj.setLastName(orglastname);
 			personobj.setPersonstatus('A');
  			
			loginobj.setPassword(orgpassword);
			loginobj.setUserid(orgusername);
			loginobj.setLoginstatus('A');
			loginobj.setPasswordstat("A");
			
  			roleobj.setRolename("admin");
			roleobj.setRolestatus('A');
			roleobj.setOrganization(organizationobj);
			
 			departmentobj.setDepartmentname("admin");
			departmentobj.setDepartmentstatus('A');
			departmentobj.setOrganization(organizationobj);
			
			orgpersonobj.setLogin(loginobj);
			orgpersonobj.setPersonid(personobj);
			orgpersonobj.setOrganizationid(organizationobj);
			orgpersonobj.setOrgpersonstatus('A');
			
			personallocationobj.setRole(roleobj);
			personallocationobj.setDepartment(departmentobj);
			personallocationSet.add(personallocationobj); 
			orgpersonobj.setPersonallocations(personallocationSet);

 			organizationobj.setOrgname(organizationname);
			organizationobj.setOrgstatus('A');
			organizationobj.setContactinfo(contactinfoobj);
			
			Long orgid = organizationService.save(organizationobj);
			
			organizationobj.setOrganizationid(orgid);
			orgpersonobj.setOrganizationid(organizationobj);
			
 			orgPersonService.save(orgpersonobj);
			 
 		}catch(Exception e){
 			if(MasterPagelogger.isInfoEnabled())
			{
 				MasterPagelogger.error("error in SaveOrg() in MasterPageAction",e);
			}
		}
    	return "scussesorg";
    }
	
	@Action(value="/saveMasterAdmin",results={@Result(name="scussesorg",location="app/user/courseSubscribe.jsp")})
    public String saveMasterAdmin(){
		try{
			organizationobj = new Organization();
			departmentobj = new Department();
			contactinfoobj = new Contactinfo();
			addressobj = new Address();
			personobj = new Person();
			roleobj = new Role();
			personallocationobj= new Personallocation();
			
			urlobj = new Url();
			emailobj = new Email();
			phoneobj = new Phone();
			loginobj = new Login();
			orgpersonobj = new Orgperson();
		
			HashSet<Address> set = new HashSet<Address>();
			HashSet<Url> urls = new HashSet<Url>();
			HashSet<Phone> phones = new HashSet<Phone>();
			HashSet<Email> emails = new HashSet<Email>();
			HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();
			
			emailobj.setUserid(email);
			emailobj.setEmailstatus('A');
			emailobj.setContactinfo(contactinfoobj);
			emails.add(emailobj);
			
			phoneobj.setNumber(contactno);
			phoneobj.setPhonestatus('A');
			phoneobj.setContactinfo(contactinfoobj);
			phones.add(phoneobj);
			
			urlobj.setUrl(organizationurl);
			urlobj.setUrlstatus('A');
			urlobj.setContactinfo(contactinfoobj);
			urls.add(urlobj);
			
			addressobj.setStreet1(street1);
			addressobj.setStreet2(street2);
			addressobj.setCity(city);
			addressobj.setCountry(country);
			addressobj.setAddressstatus('A');
			addressobj.setContactinfo(contactinfoobj);
			set.add(addressobj);
			contactinfoobj.setAddresses(set);
			contactinfoobj.setContactinfostatus('A');
			contactinfoobj.setUrls(urls);
			contactinfoobj.setPhones(phones);
			contactinfoobj.setEmails(emails); 
		
			personobj.setFirstName(orgfirstname);
			personobj.setLastName(orglastname);
 			personobj.setPersonstatus('A');
 			
  			
			loginobj.setPassword(orgpassword);
			loginobj.setUserid(orgusername);
			loginobj.setLoginstatus('A');
			loginobj.setPasswordstat("A");
			
  			roleobj.setRolename("admin");
			roleobj.setRolestatus('A');
			roleobj.setOrganization(organizationobj);
			
 			departmentobj.setDepartmentname("admin");
			departmentobj.setDepartmentstatus('A');
			departmentobj.setOrganization(organizationobj);
			
			orgpersonobj.setLogin(loginobj);
			orgpersonobj.setPersonid(personobj);
			orgpersonobj.setOrganizationid(organizationobj);
			orgpersonobj.setOrgpersonstatus('A');
			
			personallocationobj.setRole(roleobj);
			personallocationobj.setDepartment(departmentobj);
			personallocationSet.add(personallocationobj); 
			orgpersonobj.setPersonallocations(personallocationSet);

 			organizationobj.setOrgname(organizationname);
			organizationobj.setOrgstatus('M');
			organizationobj.setContactinfo(contactinfoobj);
   			
 			Long orgid = organizationService.save(organizationobj); 
 			
 			organizationobj.setOrganizationid(orgid);
			orgpersonobj.setOrganizationid(organizationobj);
			personobj.setContactinfo(contactinfoobj);
			orgpersonobj.setPersonid(personobj);
  			orgPersonService.save(orgpersonobj);
 			
 			setUsername(orgusername);
 			setPassword(orgpassword);    
 			setTargetUrl("/courses");
 			setOrganizationid(String.valueOf(orgid)); 
		}
		catch(Exception e){
			if(MasterPagelogger.isInfoEnabled())
			{
				MasterPagelogger.error("error in saveMasterAdmin() in MasterpageAction",e);
			}
		}
    	return "scussesorg";
    }
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getPortalname() {
		return portalname;
	}

	public void setPortalname(String portalname) {
		this.portalname = portalname;
	}

	public String getOrganizationurl() {
		return organizationurl;
	}

	public void setOrganizationurl(String organizationurl) {
		this.organizationurl = organizationurl;
	}

	public String getOrgfirstname() {
		return orgfirstname;
	}

	public void setOrgfirstname(String orgfirstname) {
		this.orgfirstname = orgfirstname;
	}

	public String getOrglastname() {
		return orglastname;
	}

	public void setOrglastname(String orglastname) {
		this.orglastname = orglastname;
	}

	public String getOrgemail() {
		return orgemail;
	}

	public void setOrgemail(String orgemail) {
		this.orgemail = orgemail;
	}

	public String getOrgcontactno() {
		return orgcontactno;
	}

	public void setOrgcontactno(String orgcontactno) {
		this.orgcontactno = orgcontactno;
	}

	public String getOrgpassword() {
		return orgpassword;
	}

	public void setOrgpassword(String orgpassword) {
		this.orgpassword = orgpassword;
	}

	public String getOrgusername() {
		return orgusername;
	}

	public void setOrgusername(String orgusername) {
		this.orgusername = orgusername;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getTargetUrl() {
		return targetUrl;
	}



	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}


}
