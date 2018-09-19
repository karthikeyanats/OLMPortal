package com.igrandee.product.ecloud.action.pages.admin;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.igrandee.core.organization.model.Role;
import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Message;
import com.igrandee.product.ecloud.model.Notification;
import com.igrandee.product.ecloud.model.Notificationreceived;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Personallocation;
import com.igrandee.product.ecloud.service.Pages.admin.AdminUsersRestService;
import com.igrandee.product.ecloud.service.admin.PersonallocationService;
import com.igrandee.product.ecloud.service.users.MessageService;
import com.igrandee.product.ecloud.service.users.NotificationService;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Path("/adminusers")
public class AdminUsersRestAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	AdminUsersRestService aurs;

	@InjectParam
	NotificationService notificationService;

	@InjectParam
	MessageService messageService;
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserCounts(){
		try{
			List<?> list = aurs.loadUserCounts(getAttribute("organizationid"), getAttribute("orgpersonid"));
			return ResponseBuilder(list);
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/registered")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegisteredUsersList(){
		try{
			List<?> list = aurs.loadRegisteredUsers(getAttribute("organizationid"));
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/enrolled")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEnrolledUsersList(){
		try{
			List personsList = aurs.loadEnrolledUsers(getAttribute("organizationid"));
			if(personsList!=null && personsList.size()!=0){
				List personsCoursesList = aurs.loadUsersCourseCount();
				List al = new ArrayList();
				al.addAll(personsList);
				al.addAll(personsCoursesList);
				return ResponseBuilder(al);
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/enrolledCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEnrolledCoursesList(@QueryParam("orgpersonid") String orgpersonid){
		try{
			List<?> list = aurs.loadUserCourses(orgpersonid);
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/paidUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaidUsersList(){
		try{
			List<?> list = aurs.loadPaidUsers(getAttribute("organizationid"));
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/deactivated")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeActivatedUsersList(){
		try{
			List<?> list = aurs.loadDeactivatedUsers(getAttribute("organizationid"));
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}


	@GET
	@Path("/invited")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvitedUsersList(){
		try{
			List<?> list = aurs.loadInvitedUsers(getAttribute("organizationid"));
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/authors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorsList(){
		try{
			List<?> list = aurs.loadAuthors(getAttribute("organizationid"));
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/authorCourses/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorCoursesList(@PathParam("id") String id){
		try{
			List<?> list = aurs.loadAuthorCourses(id);
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/authorCourseUsers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorCourseUsersList(@PathParam("id") String id){
		try{
			List<?> list = aurs.loadAuthorCourseUsers(id);
			return ResponseBuilder(list);	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/notify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response SendNotificationToGroup(@QueryParam("type") String type,
			@QueryParam("description") String description){
		try{
			Boolean notificationSentResponse = getUserListAndSendnotification(type, description);
			if(notificationSentResponse!=false){
				return Response.status(Status.OK).entity(Status.OK).build();	
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/message")
	@Produces(MediaType.APPLICATION_JSON)
	public Response SendMessageToGroup(@QueryParam("type") String type,
			@QueryParam("description") String description){
		try{
			Boolean notificationSentResponse = getUserListAndSendMessages(type, description);
			if(notificationSentResponse!=false){
				return Response.status(Status.OK).entity(Status.OK).build();	
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/singlemessage")
	@Produces(MediaType.APPLICATION_JSON)
	public Response SendMessageToSingle(@QueryParam("orgpersonid") String orgpersonid,
			@QueryParam("description") String description){
		try{
			Message message =  new Message();
			message.setMessage(description);

			Orgperson toorgperson = new Orgperson();
			toorgperson.setOrgpersonid(Integer.parseInt(orgpersonid));

			Orgperson fromorgperson = new Orgperson();
			fromorgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			message.setToorgperson(toorgperson);
			Date date=new Date();
			message.setMessagedate(new Timestamp(date.getTime()));
			message.setMessagestatus("A");
			message.setFromorgperson(fromorgperson);
			Integer messageSent = Integer.parseInt(messageService.save(message).toString());
			if(messageSent!=0){
				return Response.status(Status.OK).entity(Status.OK).build();	
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{id}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uodatePersonStatus(@PathParam("id") String id,
			@PathParam("status") String status){
		try{
			Integer updatedBoolean = aurs.changePersonStatus(Integer.parseInt(id),status);
			if(updatedBoolean!=0){
				return Response.status(Status.OK).entity(Status.OK).build();	
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private boolean getUserListAndSendnotification(String type,String description){

		try{
			List userList = aurs.getGroupPersonids(type, getAttribute("organizationid"), getAttribute("orgpersonid"));
			Notification notification = new Notification();
			Date date = new Date();
			HashSet<Notificationreceived> notificationreceiveset = new HashSet<Notificationreceived>();
			for (int i = 0; i < userList.size(); i++) {
				Map modelmapObj = (Map) userList.get(i);

				String personid = "" + modelmapObj.get("orgpersonid");
				Notificationreceived notificationreceived = new Notificationreceived();
				Orgperson orgperson = new Orgperson();
				orgperson.setOrgpersonid(Integer.parseInt(personid));
				notificationreceived.setOrgperson(orgperson);
				notificationreceived.setNotificationid(notification);
				notificationreceived.setReviewedstatus("U");
				notificationreceiveset.add(notificationreceived);
			}
			notification.setNotificationdescription(description);
			notification.setNotificationcategory(type);
			notification.setNotificationdate(new Timestamp(date.getTime()));
			notification.setNotificationstatus("A");
			Orgperson orgperson = new Orgperson();
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			notification.setOrgperson(orgperson);
			notification.setNotificationstatus("A");
			notification.setNotificationreceived(notificationreceiveset);
			notificationService.save(notification);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}

	private boolean getUserListAndSendMessages(String type,String description){
		try{
			List userList = aurs.getGroupPersonids(type, getAttribute("organizationid"), getAttribute("orgpersonid"));
			for (int i = 0; i < userList.size(); i++) {
				Map modelmapObj = (Map) userList.get(i);
				String personid = "" + modelmapObj.get("orgpersonid");
				Message message =  new Message();
				message.setMessage(description);
				Orgperson toorgperson = new Orgperson();
				toorgperson.setOrgpersonid(Integer.parseInt(personid));
				Orgperson fromorgperson = new Orgperson();
				fromorgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				message.setToorgperson(toorgperson);
				Date date=new Date();
				message.setMessagedate(new Timestamp(date.getTime()));
				message.setMessagestatus("A");
				message.setFromorgperson(fromorgperson);
				messageService.save(message);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}

	@POST
	@Path("/paymentapprove/{paymentid}/{enrollmentid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response approveOfflinePayment(@PathParam("paymentid") String paymentid,
			@PathParam("enrollmentid") String enrollmentid){
		try{
			aurs.changePaymentStatus(Integer.parseInt(paymentid), "A");
			aurs.changeEnrollmentStatus(Integer.parseInt(enrollmentid), "A");
			return Response.status(Status.OK).entity(Status.OK).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/certificateRequests/{status}")
	public Response getAllCertificateByStatus(@PathParam("status") String status){
		try	{
			List<?> validCertificatesList=aurs.loadCertificateRequestsByStatus(getAttribute("orgpersonid"),status);
			return ResponseBuilder(validCertificatesList);
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}  
	}

	private Response ResponseBuilder(List<?> list){
		if(list!=null && list.size()!=0){
			return Response.status(Status.OK).entity(list).build();	
		}else{
			return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
		}
	}

	//private static final Logger hssfSheetParser = Logger.getLogger(HSSFSheetParser.class);

	@POST
	@Path("/NewExcelParser")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sheetParser(@Context UriInfo uriInfo, @FormDataParam("file") List<FormDataBodyPart> file,@FormDataParam("filetype") String filetype){
		try{
			FormDataBodyPart this_formDataBodyPartFile = file.get(0);
			List questions=null;
			if(filetype.equals(".xlsx")){
				questions=readXLSXFile(this_formDataBodyPartFile.getValueAs(InputStream.class));
			}else{
				questions=readXLSheet(this_formDataBodyPartFile.getValueAs(InputStream.class));
			}
			if(questions!=null){
				return Response.status(Status.OK).entity(questions).build();	
			}else{
				return Response.status(Status.PRECONDITION_FAILED).entity(Status.PRECONDITION_FAILED).build();
			}
				
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	/**
	 * 		
	 * @param this_fileInputStream
	 * @return
	 */
	private List readXLSheet(InputStream this_fileInputStream){
		List list= new ArrayList();
		try{
			HSSFWorkbook workBook 			= NewExcelParser.getWorkBook(this_fileInputStream);
			HSSFSheet sheet 				= workBook.getSheetAt(0);
			int count=0;
			Iterator rows = sheet.rowIterator();
			Boolean isEmailValue = true;
			if(isEmailValue = true){
				while (rows.hasNext()) {

					HSSFRow row =(HSSFRow) rows.next();
					HSSFCell cell0 		= row.getCell(0);
					if(cell0!=null){
						if(count==0){
							if(cell0.toString().equalsIgnoreCase("Emailid")){
								isEmailValue = true;	
							}else{
								isEmailValue = false;
								return null;
							}
						}else{
							if(isEmailValue==false){
								
							}else{
								if(EmailValidator(cell0.toString())==true){
									Hashtable table = new Hashtable();
									table.put("sno",count);
									table.put("question",NewExcelParser.getValueOfCell(cell0,"CHAR"));
									list.add(table);	
								}else{
									
								}
							}	
						}					
					}
					count++;
				}	
			}else{
				Hashtable table = new Hashtable();
				table.put("sno","0");
				list.add(table);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public List readXLSXFile(InputStream this_fileInputStream) 
	{
		List Xlist= new ArrayList();
		try{
			XSSFWorkbook  wb = new XSSFWorkbook(this_fileInputStream);
			XSSFSheet sheet = wb.getSheetAt(0);
			
			int count=0;
			Iterator<?> rows = sheet.rowIterator();
			Boolean isEmailValue = true;
			if(isEmailValue = true){
				while (rows.hasNext()){
					XSSFRow row=(XSSFRow) rows.next();
					XSSFCell cell0 		= row.getCell(0);
					if(cell0!=null){
						if(count==0){
							if(cell0.toString().equalsIgnoreCase("Emailid")){
								isEmailValue = true;	
							}else{
								isEmailValue = false;
								return null;
							}
						}else{
							if(isEmailValue==false){
								
							}else{
								if(EmailValidator(cell0.toString())==true){
									Hashtable table = new Hashtable();
									table.put("sno",count);
									table.put("question",NewExcelParser.getValueOfXCell(cell0,"CHAR"));
									Xlist.add(table);	
								}else{
									
								}
							}	
						}
					}
					count++;
				}
			}else{
				Hashtable table = new Hashtable();
				table.put("sno","0");
				Xlist.add(table);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return Xlist;
	}

	
	  private Boolean EmailValidator(String email) {
	      String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      Boolean b = email.matches(EMAIL_REGEX);
	      return b;
	   }
	  
	  	@POST
		@Path("/updaterole")
		@Produces(MediaType.APPLICATION_JSON)
		public Response updaterole(@QueryParam("personallocationid") String personallocationid,
				@QueryParam("roleid") String roleid){
			try{
				int size = aurs.updaterole(Integer.parseInt(personallocationid), Integer.parseInt(roleid));
				if(size>0){
					return Response.status(Status.OK).entity(Status.OK).build();	
				}else{
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}			
			}catch(Exception e){
				e.printStackTrace();
				return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
}
