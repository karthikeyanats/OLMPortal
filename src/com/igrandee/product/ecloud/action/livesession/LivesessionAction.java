package com.igrandee.product.ecloud.action.livesession;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Livesession;
import com.igrandee.product.ecloud.service.livesession.LivesessionService;
import com.igrandee.product.ecloud.viewmodel.livesession.LivesessionVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;


@Path("/livesession")
public class LivesessionAction extends AbstractIGActionSupport{

	private static final long serialVersionUID = 1L;

	private static Logger livesessionActionLogger = Logger.getLogger(LivesessionAction.class);

	@InjectParam
	LivesessionService livesessionService;

	@InjectParam
	SessionFactory sessionFactory;

	@InjectParam
	Course course;

	@InjectParam
	Livesession livesession;

	@InjectParam
	LivesessionVM livesessionVM;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createlive(@Context HttpServletRequest request,LivesessionVM livesessionVM){		
		try{
			Date date = new Date() ;
			String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());	
			SimpleDateFormat dateFormats = new SimpleDateFormat("HH:mm") ;
			dateFormats.format(date);
			
			SimpleDateFormat dateFormatss = new SimpleDateFormat("MMM-dd-yyyy HH:mm");
			
			/* ************* Start time  *************** */
			String starttime = livesessionVM.getStarttime();
			String[] part = starttime.split(" ");
			String starttime1 = part[0];
			String starttime2 = part[1];
			String[] part1 = starttime1.split(":");
			String starttime3 =part1[0]; 
			
			String starttimes=null;
			if(starttime2.equalsIgnoreCase("PM")){
				if(starttime3.equalsIgnoreCase("12")){
					int a = Integer.parseInt(starttime3);
					starttimes = ""+a+":"+part1[1];
				}
				else{
					int a = Integer.parseInt(starttime3)+12;
					starttimes = ""+a+":"+part1[1];
				}
			}
			else if(starttime2.equalsIgnoreCase("AM")){
				if(starttime3.equalsIgnoreCase("12")){
					starttimes = "00:"+part1[1];
				}
				else{
					starttimes = starttime1;
				}
			}
			/* ***************** get Start Date Time **************** */
			Date startparsedDate =dateFormatss.parse(livesessionVM.getScheduledate()+" "+starttimes);
			Timestamp startdatetamp=new java.sql.Timestamp(startparsedDate.getTime());
			
			/* ************* To time  *************** */
			String endtime = livesessionVM.getEndtime();
			String[] endtimepart = endtime.split(" ");
			String endtime1 = endtimepart[0];
			String endtime2 = endtimepart[1];
			String[] endtimepart1 = endtime1.split(":");
			String endtime3 =endtimepart1[0]; 
			
			String endtimes=null;
			
			if(endtime2.equalsIgnoreCase("PM")){
				if(endtime3.equalsIgnoreCase("12")){
					int a = Integer.parseInt(endtime3);
					endtimes = ""+a+":"+endtimepart1[1];
				}
				else{
					int a = Integer.parseInt(endtime3)+12;
					endtimes = ""+a+":"+endtimepart1[1];
				}
			}
			else if(endtime2.equalsIgnoreCase("AM")){
				if(endtime3.equalsIgnoreCase("12")){
					endtimes = "00:"+endtimepart1[1];
				}
				else{
					endtimes = endtime1;
				}
			}
			/* ***************** get End Date Time **************** */
			Date endparsedDate =dateFormatss.parse(livesessionVM.getScheduledate()+" "+endtimes);
			Timestamp enddatetamp=new java.sql.Timestamp(endparsedDate.getTime());
			
			if(today.equalsIgnoreCase(livesessionVM.getScheduledates())){
				if(dateFormats.parse(starttimes).after(dateFormats.parse(dateFormats.format(date))) || dateFormats.parse(dateFormats.format(date)).equals(dateFormats.parse(starttimes)))
				{
					if(dateFormats.parse(dateFormats.format(date)).after(dateFormats.parse(endtimes)))
					{
						return Response.status(Status.OK).entity(Status.REQUEST_TIMEOUT).build();
					}else{
						course.setCourseid(Integer.parseInt(livesessionVM.getCourseid()));
						livesession.setCourseid(course);
						livesession.setDescription(livesessionVM.getDescription());
					    livesession.setStarttime(livesessionVM.getStarttime());
					    livesession.setEndtime(livesessionVM.getEndtime());
						livesession.setFromipaddress(request.getRemoteAddr());
						livesession.setPrice(livesessionVM.getPrice());		
						livesession.setLivesessionstatus(livesessionVM.getLivesessionstatus());
						SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
						Date schduleparsedDate =dateFormat.parse(livesessionVM.getScheduledate());
					    Timestamp schduletimestamp=new java.sql.Timestamp(schduleparsedDate.getTime());
						livesession.setScheduledate(schduletimestamp);
						livesession.setStartdatetime(startdatetamp);
						livesession.setEnddatetime(enddatetamp);
						livesession.setCreateddate(new Timestamp(new Date().getTime()));
						livesession.setLivesessionstatus("A");
						int livesessiondetail=livesessionService.save(livesession);
						if(livesessiondetail > 0){			
							return Response.status(Status.OK).entity(Status.CREATED).build();
						}else{
							return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
						}
					}
					
				}else{
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}
			}
			else{
					if(dateFormats.parse(starttimes).after(dateFormats.parse(endtimes)))
					{
						return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
					}else{
						course.setCourseid(Integer.parseInt(livesessionVM.getCourseid()));
						livesession.setCourseid(course);
						livesession.setDescription(livesessionVM.getDescription());
					    livesession.setStarttime(livesessionVM.getStarttime());
					    livesession.setEndtime(livesessionVM.getEndtime());
						livesession.setFromipaddress(request.getRemoteAddr());
						livesession.setPrice(livesessionVM.getPrice());		
						livesession.setLivesessionstatus(livesessionVM.getLivesessionstatus());
						SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
						Date schduleparsedDate =dateFormat.parse(livesessionVM.getScheduledate());
					    Timestamp schduletimestamp=new java.sql.Timestamp(schduleparsedDate.getTime());
						livesession.setScheduledate(schduletimestamp);
						livesession.setStartdatetime(startdatetamp);
						livesession.setEnddatetime(enddatetamp);
						livesession.setCreateddate(new Timestamp(new Date().getTime()));
						livesession.setLivesessionstatus("A");
						int livesessiondetail=livesessionService.save(livesession);
						if(livesessiondetail > 0){			
							return Response.status(Status.OK).entity(Status.CREATED).build();
						}else{
							return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
						}
					}
			}
		}catch(Exception e){
			if(livesessionActionLogger.isInfoEnabled()){
				livesessionActionLogger.error("error in createlive() in LivesessionAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 
	}
	
	@GET
	@Path("/{courseid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScheduledetailslist(@PathParam("courseid") int courseid) throws ParseException{
		
		
		try {
			
			String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());	
			String dates=today.substring(2);
			String fromdate="01"+dates;
			String todate="31"+dates;
			 
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String dateFromString =fromdate;
			String dateToString =todate; 
		Date date = formatter.parse(dateFromString);
		Date date1 = formatter.parse(dateToString);
		
	    Timestamp fromdate1 = new java.sql.Timestamp(date.getTime());
	    Timestamp todate1 = new java.sql.Timestamp(date1.getTime());
        List<?> schedule=livesessionService.getScheduleDetails(courseid,fromdate1,todate1); 
 		
		if(schedule!=null){
			return Response.ok().entity(schedule).build();
		}
		
		}
		catch (Exception e) {
			e.printStackTrace();
			}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	
	}
	
	
	@GET
	@Path("/{courseid}/{scheduledate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScheduletimelist(@PathParam("courseid") int courseid,@PathParam("scheduledate") String scheduledate) throws ParseException{
  	
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
		Date schduleparsedDate = dateFormat.parse(scheduledate);
		 Timestamp schduletimestamp=new java.sql.Timestamp(schduleparsedDate.getTime()); 
 		List<?> schedulelist=livesessionService.getScheduletimelist(courseid,schduletimestamp);
		if(schedulelist!=null){
			return Response.ok().entity(schedulelist).build();
		}	
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	
	
	@GET
	@Path("/liveauthor/{scheduledate}")
	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScheduletimeauthorlist(@PathParam("scheduledate") String scheduledate) throws ParseException{
  	
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
		Date schduleparsedDate = dateFormat.parse(scheduledate);
		 Timestamp schduletimestamp=new java.sql.Timestamp(schduleparsedDate.getTime()); 
		 String personid = getAttribute("orgpersonid");
 		List<?> scheduleauthorlist=livesessionService.getScheduletimeauthorlist(Integer.parseInt(personid),schduletimestamp);
		if(scheduleauthorlist!=null){
			return Response.ok().entity(scheduleauthorlist).build();
		}	
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	
	
	@DELETE

	@Path("/livestatus/{livesessionid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSchedule(@PathParam("livesessionid")int livesessionid){
 		if(livesessionid>0){
		if(livesessionService.deleteSchedule(livesessionid)){
			return Response.ok(Status.ACCEPTED).build();
		}		
		return Response.ok(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(Status.BAD_REQUEST).build();
	}
	
	
	@POST
	@Path("/editschedule")	
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getSchedule(int livesessionid){   
		List<?> getschedulelist = null;  
		try{  
			getschedulelist =  livesessionService.getSchedulevalues(livesessionid); 
			if(getschedulelist.size() > 0 & ! getschedulelist.isEmpty()){
				return Response.status(Status.OK).entity(getschedulelist).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			if(livesessionActionLogger.isInfoEnabled()){
				livesessionActionLogger.error("error in getScheduledetails() in LivesessionAction",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		} 

	}
	
	  
		@PUT
		@Path("/updateschedule")
		@Consumes( MediaType.APPLICATION_JSON)
		@Produces( MediaType.APPLICATION_JSON)
		public Response updateSchedule(LivesessionVM livesessionVM){		
			try{
				int type = 1;
				{	
					List<?> getschedulelist =  livesessionService.getSchedulevalues(Integer.parseInt(livesessionVM.getLivesessionid())); 
					if(getschedulelist.size() > 0 & ! getschedulelist.isEmpty()){
						HashMap hashmap1 = (HashMap)getschedulelist.get(0);
						 String starttimess=""+hashmap1.get("starttime");
						 String endtimess=""+hashmap1.get("endtime");
						 
						 Date date = new Date() ;
						 String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());	
						 SimpleDateFormat dateFormats = new SimpleDateFormat("HH:mm") ;
							dateFormats.format(date);
							
							SimpleDateFormat dateFormatss = new SimpleDateFormat("MMM-dd-yyyy HH:mm");
							
							/* ************* Start time  *************** */
							String starttime = livesessionVM.getStarttime();
							String[] part = starttime.split(" ");
							String starttime1 = part[0];
							String starttime2 = part[1];
							String[] part1 = starttime1.split(":");
							String starttime3 =part1[0]; 
							
							String starttimes=null;
							if(starttime2.equalsIgnoreCase("PM")){
								if(starttime3.equalsIgnoreCase("12")){
									int a = Integer.parseInt(starttime3);
									starttimes = ""+a+":"+part1[1];
								}
								else{
									int a = Integer.parseInt(starttime3)+12;
									starttimes = ""+a+":"+part1[1];
								}
							}
							else if(starttime2.equalsIgnoreCase("AM")){
								if(starttime3.equalsIgnoreCase("12")){
									starttimes = "00:"+part1[1];
								}
								else{
									starttimes = starttime1;
								}
							}
							
							/* ***************** get Start Date Time **************** */
							Date startparsedDate =dateFormatss.parse(livesessionVM.getScheduledate()+" "+starttimes);
							Timestamp startdatetamp=new java.sql.Timestamp(startparsedDate.getTime());
							
							/* ************* To time  *************** */
							String endtime = livesessionVM.getEndtime();
							String[] endtimepart = endtime.split(" ");
							String endtime1 = endtimepart[0];
							String endtime2 = endtimepart[1];
							String[] endtimepart1 = endtime1.split(":");
							String endtime3 =endtimepart1[0]; 
							
							String endtimes=null;
							
							if(endtime2.equalsIgnoreCase("PM")){
								if(endtime3.equalsIgnoreCase("12")){
									int a = Integer.parseInt(endtime3);
									endtimes = ""+a+":"+endtimepart1[1];
								}
								else{
									int a = Integer.parseInt(endtime3)+12;
									endtimes = ""+a+":"+endtimepart1[1];
								}
							}
							else{
								endtimes = endtime1;
							}
							/* ***************** get End Date Time **************** */
							Date endparsedDate =dateFormatss.parse(livesessionVM.getScheduledate()+" "+endtimes);
							Timestamp enddatetamp=new java.sql.Timestamp(endparsedDate.getTime());
							
							if(today.equalsIgnoreCase(livesessionVM.getScheduledates())){
								if(starttimess.equalsIgnoreCase(livesessionVM.getStarttime())){
									 if(endtimess.equalsIgnoreCase(livesessionVM.getEndtime())){
										 type=livesessionService.updateSchedule(Integer.parseInt(livesessionVM.getLivesessionid()),livesessionVM.getDescription(),livesessionVM.getStarttime(),livesessionVM.getEndtime(),livesessionVM.getPrice(),startdatetamp,enddatetamp);
											if(type!=0){
												return Response.status(Status.CREATED).entity(Status.CREATED).build();
											}			
											else{
												return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
											} 
									 }
									 else{
										 if(dateFormats.parse(dateFormats.format(date)).after(dateFormats.parse(endtimes)))
											{
												return Response.status(Status.OK).entity(Status.REQUEST_TIMEOUT).build();
											}else{
													type=livesessionService.updateSchedule(Integer.parseInt(livesessionVM.getLivesessionid()),livesessionVM.getDescription(),livesessionVM.getStarttime(),livesessionVM.getEndtime(),livesessionVM.getPrice(),startdatetamp,enddatetamp);
												if(type!=0){
													return Response.status(Status.CREATED).entity(Status.CREATED).build();
												}			
												else{
													return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
												}
											} 
									 }
								 }
								 else{
										if(dateFormats.parse(starttimes).after(dateFormats.parse(dateFormats.format(date))) || dateFormats.parse(dateFormats.format(date)).equals(dateFormats.parse(starttimes)))
										{
											if(dateFormats.parse(dateFormats.format(date)).after(dateFormats.parse(endtimes)))
											{
												return Response.status(Status.OK).entity(Status.REQUEST_TIMEOUT).build();
											}else{
													type=livesessionService.updateSchedule(Integer.parseInt(livesessionVM.getLivesessionid()),livesessionVM.getDescription(),livesessionVM.getStarttime(),livesessionVM.getEndtime(),livesessionVM.getPrice(),startdatetamp,enddatetamp);
												if(type!=0){
													return Response.status(Status.CREATED).entity(Status.CREATED).build();
												}			
												else{
													return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
												}
											}
											
										}
										else{
											return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
										}
								 }
							}
							else{
								if(starttimess.equalsIgnoreCase(livesessionVM.getStarttime())){
									 if(endtimess.equalsIgnoreCase(livesessionVM.getEndtime())){
										 type=livesessionService.updateSchedule(Integer.parseInt(livesessionVM.getLivesessionid()),livesessionVM.getDescription(),livesessionVM.getStarttime(),livesessionVM.getEndtime(),livesessionVM.getPrice(),startdatetamp,enddatetamp);
											if(type!=0){
												return Response.status(Status.CREATED).entity(Status.CREATED).build();
											}			
											else{
												return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
											} 
									 }
									 else{
										 if(dateFormats.parse(starttimes).after(dateFormats.parse(endtimes)))
											{
												return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
											}else{
													type=livesessionService.updateSchedule(Integer.parseInt(livesessionVM.getLivesessionid()),livesessionVM.getDescription(),livesessionVM.getStarttime(),livesessionVM.getEndtime(),livesessionVM.getPrice(),startdatetamp,enddatetamp);
												if(type!=0){
													return Response.status(Status.CREATED).entity(Status.CREATED).build();
												}			
												else{
													return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
												}
											} 
									 }
								 }
								 else{
											if(dateFormats.parse(starttimes).after(dateFormats.parse(endtimes)))
											{
												return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
											}else{
													type=livesessionService.updateSchedule(Integer.parseInt(livesessionVM.getLivesessionid()),livesessionVM.getDescription(),livesessionVM.getStarttime(),livesessionVM.getEndtime(),livesessionVM.getPrice(),startdatetamp,enddatetamp);
												if(type!=0){
													return Response.status(Status.CREATED).entity(Status.CREATED).build();
												}			
												else{
													return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
												}
											}
								 }
							}
					}else{
						return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
					}
				}
			}
			catch(Exception e){
				if(livesessionActionLogger.isInfoEnabled()){
					livesessionActionLogger.error("error in updateschedule() in LivesessionAction",e);
				}
				return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
			}
			
			/*if(type!=0){
				return Response.status(Status.CREATED).entity(Status.CREATED).build();
			}			
			else{
				return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
			}*/	
		}
		
		/**
		 * @author muniyarasu_a
		 * @return getlivesessionschedulelist
		 * @param livesessionid
		 */
		@GET
		@Path("/getlivesessionschedulelist/{livesessionid}")
		
		@Produces(MediaType.APPLICATION_JSON)
		public Response getlivesessionschedulelist(@PathParam("livesessionid") String livesessionid){
	  	
			List<?> getlivesessionschedulelist=livesessionService.getlivesessionschedulelist(livesessionid);
			if(getlivesessionschedulelist!=null){
				return Response.ok().entity(getlivesessionschedulelist).build();
			}	
			return Response.ok().entity(Status.BAD_REQUEST).build();
		}
	
		/**
		 * @author muniyarasu_a
		 * @return getlivesessionschedulelist
		 * @param livesessionid
		 */
		@GET
		@Path("/scheduleList/{courseid}")
		
		@Produces(MediaType.APPLICATION_JSON)
		public Response getschedulelist(@PathParam("courseid") int courseid){
	  	
			List<?> getschedulelist=livesessionService.getSchedulelist(courseid);
			if(getschedulelist!=null){
				return Response.ok().entity(getschedulelist).build();
			}	
			return Response.ok().entity(Status.BAD_REQUEST).build();
		}
		
		/**
		 * @author muniyarasu_a
		 * @return getcurrenttime
		 * @param livesessionid
		 */
		@GET
		@Path("/currenttime")
		
		@Produces(MediaType.APPLICATION_JSON)
		public Response getcurrenttime(){
			
			Date date = new Date() ;
			String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());	
			SimpleDateFormat dateFormats = new SimpleDateFormat("HH:mm") ;
			String time = dateFormats.format(date);
			
			ArrayList<String> al=new ArrayList<String>();
			  al.add(today);
			  al.add(time);
				return Response.ok().entity(al).build();
		}
		
		
}