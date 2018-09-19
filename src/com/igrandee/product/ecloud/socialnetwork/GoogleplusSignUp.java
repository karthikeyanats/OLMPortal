package com.igrandee.product.ecloud.socialnetwork;

import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

import com.googlecode.googleplus.Plus;
import com.igrandee.core.contact.model.Address;
import com.igrandee.core.contact.model.Contactinfo;
import com.igrandee.core.contact.model.Email;
import com.igrandee.core.contact.model.Phone;
import com.igrandee.core.login.model.Login;
import com.igrandee.core.login.service.LoginService;
import com.igrandee.core.organization.model.Department;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.core.organization.model.Role;
import com.igrandee.core.person.model.Person;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Personallocation;
import com.igrandee.product.ecloud.service.course.OrgPersonService;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/google")
public class GoogleplusSignUp {

	@InjectParam
	OrgPersonService orgPersonService;
	
	@InjectParam("usersConnectionRepository")
	UsersConnectionRepository usersConnectionRepository;
	
	@InjectParam
	DataSource dataSource;
	
	@InjectParam
	LoginService loginService;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@QueryParam("organizationid")long organizationid,@QueryParam("name")String name,@Context UriInfo uriInfo){
        	
        String baseuri=uriInfo.getBaseUri().toString().replaceAll("/rest/", "");
        
        ConnectionRepository repository=usersConnectionRepository.createConnectionRepository(name);
		Connection<Plus> connection = repository
				.findPrimaryConnection(Plus.class);
		Plus plus=connection.getApi();
		
		com.googlecode.googleplus.model.person.Person googleperson=plus.getPeopleOperations().get("me");	
        
		List<?> usernameCheck = orgPersonService.getLoginCheck(googleperson.getGoogleAccountEmail(),organizationid);
		if(usernameCheck.size()==0) 
		{   int orgpersonid =0;
			List<Object[]> userinOrg=(List<Object[]>) orgPersonService.loginCheckForOrganization(googleperson.getGoogleAccountEmail(),organizationid);
            if(userinOrg==null){  		
		Orgperson orgPerson = new Orgperson();
		Contactinfo contactinfo = new Contactinfo();			
		Email email = new Email();
		email.setUserid(googleperson.getGoogleAccountEmail()); 
		email.setEmailstatus('A');
		Set<Email> emailSet = new HashSet<Email>();
		emailSet.add(email);

		contactinfo.setEmails(emailSet) ;   
		contactinfo.setContactinfostatus('A');

		Address address =  new Address();
		address.setAddressstatus('A');
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		contactinfo.setAddresses(addresses);

		Phone phone = new Phone();
		phone.setPhonestatus('A');
		Set<Phone> phones = new HashSet<Phone>();
		phones.add(phone);
		contactinfo.setPhones(phones);

		Login login = new Login(); 			 
		login.setUserid(googleperson.getGoogleAccountEmail()); 
		login.setPassword(googleperson.getGoogleAccountEmail()+googleperson.getDisplayName());
		login.setLoginstatus('A');  

		Person person = new Person();  
		person.setFirstName(googleperson.getDisplayName()); 
		person.setLastName(googleperson.getName().getFamilyName());			 
		person.setContactinfo(contactinfo); 
		person.setPersonstatus('A');

		Personallocation personallocation = new Personallocation();
		Role role = new Role();
		role.setRolename("user");
		role.setRolestatus('A');
		role.setRolelevel('0');
		role.setRoleid(2);

		Department department = new Department();
		department.setDepartmentid(2);
		department.setDepartmentname("admin");
		department.setDepartmentstatus('A');

		HashSet<Personallocation> personallocationSet = new HashSet<Personallocation>();				
		personallocation.setRole(role);
		personallocation.setDepartment(department);
		personallocationSet.add(personallocation);
		personallocation.setPersonallocationstatus('A');
		orgPerson.setPersonallocations(personallocationSet);

		orgPerson.setLogin(login);
		orgPerson.setPersonid(person);   
		orgPerson.setOrgpersonstatus('A');		 
		
		Organization organization=new Organization();
		organization.setOrganizationid(organizationid);
		
		orgPerson.setOrganizationid(organization);			
		  if(orgpersonid!=0){			
				return Response.ok().status(Status.CREATED).entity(Status.CREATED).build();
		    	}
			  else{
				URI uri =UriBuilder.fromUri(baseuri).build();
				return Response.seeOther(uri).entity("Problem creating new account").build();			
		    	}
	     }           
	        else{
	              orgpersonid=orgPersonService.addOrgForUser(organizationid, (Integer)userinOrg.get(0)[1], (Long)userinOrg.get(0)[2]);
	              if(orgpersonid!=0){			
	      			return Response.ok().status(Status.CREATED).entity(Status.CREATED).build();
	      	     	}
	      		  else{
	      			URI uri =UriBuilder.fromUri(baseuri).build();
	      			return Response.seeOther(uri).entity("Problem creating new account").build();			
	      		  }
	            }			
		}
		else{
		    HashMap<String, String> alert=new HashMap<String, String>();
		    alert.put("errormessage", "The username already exists, Now you can also use google+ for login to your account");
			return Response.ok().status(Status.OK).entity(alert).build();
		}		
		
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserConnection(@QueryParam("name")String name,@Context UriInfo uriInfo){
        String baseuri=uriInfo.getBaseUri().toString().replaceAll("/rest/", "");
        Login login=new Login();
        login.setUserid(name);
      if(!loginService.checkDublicate(login)){
        try {
			java.sql.Connection connection=dataSource.getConnection();
			connection.createStatement().execute("delete from UserConnection where userId='"+name+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
		URI uri =UriBuilder.fromUri(baseuri).build();
		return Response.seeOther(uri).build();
		
	}
	
	
	@Path("/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("name")String name){
		
		ConnectionRepository repository=usersConnectionRepository.createConnectionRepository(name);
		Connection<Plus> connection = repository
				.findPrimaryConnection(Plus.class);
		Plus plus=connection.getApi();
		
		String emailadd=plus.getPeopleOperations().get("me").getGoogleAccountEmail();	
		String firstname=plus.getPeopleOperations().get("me").getDisplayName();
		String lastname=plus.getPeopleOperations().get("me").getName().getFamilyName();
		
		
		Map<String,String> signupDetails=new HashMap<String, String>();
		signupDetails.put("email", emailadd);
		signupDetails.put("firstname", firstname);
		signupDetails.put("lastname",lastname);
		
		return Response.ok().entity(signupDetails).build();
		
	}
}
