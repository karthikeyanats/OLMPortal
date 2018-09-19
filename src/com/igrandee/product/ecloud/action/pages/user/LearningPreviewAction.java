package com.igrandee.product.ecloud.action.pages.user;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Faq;
import com.igrandee.product.ecloud.model.Faqanswer;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.Pages.user.LearningPreviewService;
import com.igrandee.product.ecloud.viewmodel.AnswerVM;
import com.sun.jersey.api.core.InjectParam;

@Path("lpreview")
public class LearningPreviewAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	LearningPreviewService lps;
	
	@InjectParam
	Faq faq;
	
	@InjectParam
	Faqanswer faqanswerobj;
	
	@InjectParam
	Orgperson answeredby; 
	
	@GET
	@Path("/info/{courseenrollmentid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseInfo(@PathParam("courseenrollmentid") String courseenrollmentid){
		try{
			List<?> coursepreviewlist=lps.loadCourseInfo(courseenrollmentid);
			if(coursepreviewlist.size()!=0){
				return Response.status(Status.OK).entity(coursepreviewlist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/curriculum/{courseenrollmentid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseCurriculum(@PathParam("courseenrollmentid") String courseenrollmentid){
		try{
			List<?> coursecurriculumlist=lps.loadCourseCurriculum(courseenrollmentid);
			if(coursecurriculumlist.size()!=0){
				return Response.status(Status.OK).entity(coursecurriculumlist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/reviews/{courseenrollmentid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseReviews(@PathParam("courseenrollmentid") String courseenrollmentid){
		try{
			List<?> coursereviewslist=lps.loadCourseReviews(courseenrollmentid);
			if(coursereviewslist.size()!=0){
				return Response.status(Status.OK).entity(coursereviewslist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/questions/{courseenrollmentid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseDiscussions(@PathParam("courseenrollmentid") String courseenrollmentid){
		try{
			List<?> coursediscussionlist=lps.loadCourseQuestions(courseenrollmentid);
			if(coursediscussionlist.size()!=0){
				return Response.status(Status.OK).entity(coursediscussionlist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/answers/{questionid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseDiscussionsAnswers(@PathParam("questionid") String questionid){
		try{
			List<?> coursediscussionanswerslist=lps.loadCourseQuestionAnswers(questionid);
			if(coursediscussionanswerslist.size()!=0){
				return Response.status(Status.OK).entity(coursediscussionanswerslist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/newanswer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveNewAnswers(AnswerVM answerVM){
		try{
			faq.setFAQid(Integer.parseInt(answerVM.getQuestionid()));
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp answereddate =new Timestamp(date.getTime());
			String orgpersonid=getAttribute("orgpersonid");
			answeredby.setOrgpersonid(Integer.parseInt(orgpersonid));
			faqanswerobj.setAnsweredby(answeredby);
			faqanswerobj.setAnswereddate(answereddate);
			faqanswerobj.setFaqanswer(answerVM.getAnswerdesc());
			faqanswerobj.setFaqanswerstatus("A");
			faqanswerobj.setFaq(faq);
			Integer ansstatus=lps.save(faqanswerobj);
			if(ansstatus!=0){
				return Response.status(Status.OK).entity(Status.OK).build();
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/statuses/{courseenrollmentid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getLearningStatuses(@PathParam("courseenrollmentid") String courseenrollmentid){
		try{
			List<?> learningstatuslist=lps.learningStatuses(Integer.parseInt(courseenrollmentid));
			if(learningstatuslist.size()!=0){
				return Response.status(Status.OK).entity(learningstatuslist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/quizStatus") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getSATSatus(@QueryParam("lectureids") String lectureids){
		List<?> satStatus=lps.loadQuizStatus(Integer.parseInt(getAttribute("orgpersonid")),lectureids);
		if (satStatus != null && satStatus.size() != 0)
			return Response.status(Status.OK).entity(satStatus).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
}