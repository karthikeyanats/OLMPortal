
package com.igrandee.product.ecloud.action.sat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.sat.ExamConnector;
import com.igrandee.product.ecloud.service.course.CourseLectureService;
import com.igrandee.product.ecloud.service.sat.ConnectionService;
import com.igrandee.product.ecloud.service.sat.ExamService;
import com.igrandee.product.ecloud.service.sat.LiveQuizAnswerService;
import com.igrandee.product.ecloud.service.sat.OnlineQuizUserService;
import com.igrandee.product.ecloud.viewmodel.sat.ExamPaperVM;
import com.igrandee.product.ecloud.viewmodel.sat.ExamVM;
import com.igrandee.product.ecloud.viewmodel.sat.OnlineQuizUser;
import com.igrandee.product.ecloud.viewmodel.sat.QuizAnswerVM;
import com.igrandee.product.satutil.model.LiveQuiz;
import com.igrandee.product.satutil.model.LiveQuizAnswer;
import com.igrandee.product.satutil.model.LiveQuizUser;
import com.igrandee.product.satutil.model.QuestionPaper;
import com.igrandee.product.satutil.model.QuestionPaperSection;
import com.igrandee.product.satutil.model.Qustionanswer;
import com.igrandee.product.satutil.model.SatQuestion;
import com.igrandee.product.satutil.model.SectionQuestion;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author selvarajan_j
 * Exam Management
 */
@Path("/exam")
public class ExamAction {
	
	@InjectParam
	ConnectionService connectionService;
	
	@InjectParam
	ExamService examservice;
	
	@InjectParam
	CourseLectureService courseLectureService;
	
	@InjectParam
	ExamConnector examConnector;
	
	@InjectParam
	Course course;
	
	@InjectParam
	QuestionPaper questionPaper;
	
	@InjectParam
	OnlineQuizUserService onlineQuizUserService;
	
	@InjectParam
	LiveQuiz liveQuiz;
	
	@InjectParam
	LiveQuizAnswer liveQuizAnswer;
	
	@InjectParam
	LiveQuizAnswerService liveQuizAnswerService;
	/**
	 * 
	 * @return List Collections of questions 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@POST
	@Path("/getQuestions") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getQuestions(ExamVM examVM){
		List<?> question=connectionService.getQstAns(examVM.getLecture(),examVM.getTypeId(),examVM.getMark());
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	 
	
	@POST
	@Path("/savepaper") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savepaper(ExamPaperVM exampapervm){
		
		java.util.Date date= new java.util.Date();
		// COURSE LECTURE ID MODAL CONFIGURATION
		course.setCourseid(exampapervm.getCourselectureid());
		System.out.println("get course id========== "+exampapervm.getCourselectureid());
		examConnector.setCourse(course);
 		questionPaper.setCreateddate(new Timestamp(date.getTime()));
		questionPaper.setInstruction(exampapervm.getPaper());
		questionPaper.setPapername(exampapervm.getTitle());
		questionPaper.setStatus('A');
		examConnector.setQuestionpaper(questionPaper);
		
		/*java.util.Date date= new java.util.Date();
		String jsonData = exampapervm.getJsonData(); 
		System.out.println("get exam paper "+jsonData);
		JSONObject examobj = new JSONObject(jsonData);
		JSONArray examarray=examobj.getJSONArray("exam");

		// COURSE LECTURE ID MODAL CONFIGURATION
		course.setCourseid(exampapervm.getCourselectureid());
		System.out.println("get course id========== "+exampapervm.getCourselectureid());
		examConnector.setCourse(course);
 		questionPaper.setCreateddate(new Timestamp(date.getTime()));
		questionPaper.setInstruction(examarray.getJSONObject(0).getString("headers"));
		questionPaper.setPapername(exampapervm.getTitle());
		questionPaper.setStatus('A');
 		
		//questionPaperSection
		ArrayList <QuestionPaperSection> al = new ArrayList<QuestionPaperSection>();
		for(int i=1;i<examarray.length();i++){
			
				QuestionPaperSection questionPaperSection = new QuestionPaperSection();
				questionPaperSection.setSectionname(examarray.getJSONObject(i).getString("headers"));
				questionPaperSection.setStatus('A');
			
				if(examarray.getJSONObject(i).has("questions")){
					ArrayList<SectionQuestion> inneral=new ArrayList<SectionQuestion>();
					JSONArray qstid=examarray.getJSONObject(i).getJSONArray("questions");
					for(int k=0;k<qstid.length();k++){
						SectionQuestion sectionQuestion=new SectionQuestion();
						SatQuestion satquestion=new SatQuestion();
						String spl[]=qstid.getJSONObject(k).getString("id").split("&");
						
						satquestion.setId(Integer.parseInt(spl[0])); 
						sectionQuestion.setQuestion(satquestion);
						if(spl.length>1){
							
							SatQuestion subsatquestion=new SatQuestion();
							subsatquestion.setId(Integer.parseInt(spl[1]));
							 sectionQuestion.setSubquestion(subsatquestion);
						}
						sectionQuestion.setStatus('A');
						inneral.add(sectionQuestion);
					}
					questionPaperSection.setSectionQuestion(inneral);
				}
				
			al.add(questionPaperSection);
		}
		questionPaper.setQuestionPaperSection(al);
		examConnector.setQuestionpaper(questionPaper);*/
		
		
		int status=examservice.save(examConnector);
		if(status != 0)
			return Response.ok().status(Status.OK).entity(Status.CREATED).build();
		else
			return Response.ok().status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/updatepaper") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatepaper(ExamPaperVM exampapervm){
		int status=0;
		/*java.util.Date date= new java.util.Date();
		String deleteQus = exampapervm.getDeleteQuestions(); 
		System.out.println("get exam paper "+deleteQus);
		JSONArray dltarray=new JSONArray(deleteQus);
		String newQus = exampapervm.getNewQuestion(); 
		System.out.println("get exam newQus "+newQus);
		
		status+=examservice.updatepapertitle(exampapervm.getTitle(),exampapervm.getTitleid());
		
		for(int j=0;j<dltarray.length();j++){
			JSONObject obj = new JSONObject(dltarray.getString(j));
			status+=examservice.updatePaperQuestion(obj.get("sectionid").toString());
		}
		JSONArray newarray=new JSONArray(newQus);
		
		for(int k=0;k<newarray.length();k++){
			JSONObject obj = new JSONObject(newarray.getString(k));
			List lt=examservice.getPaperSectionid(obj.get("sectionid").toString());
			Map<?, ?> map = (Map<?, ?>) lt.get(0);
			map.get("papersection_id").toString();
			System.out.println("obj.get(subquestionid).toString() "+obj.get("subquestionid").toString());
			String orqst="-";
			//if(obj.get("subquestionid").toString() != undefined)
			 
			 status=examservice.saveSectionQuestoin(map.get("papersection_id").toString(),obj.get("questionid").toString(),"-");
		}
		*/
		System.out.println("----------------------- "+status);	
		if(status != 0)
			return Response.ok().status(Status.OK).entity(Status.CREATED).build();
		else
			return Response.ok().status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * Load Lecture
	 * @param section id
	 */
	@GET
	@Path("/loadLecture")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadLecture(@QueryParam("lectureid") String lectureid){
		List <?> lecture=courseLectureService.getLecture(Integer.parseInt(lectureid));
		if (lecture != null && lecture.size() != 0)
			return Response.status(Status.OK).entity(lecture).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	

	@GET
	@Path("/dashboard") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDashboardData(){
		
		List<?> dashboard_data	=	courseLectureService.getDashboardData();
		if(dashboard_data != null && dashboard_data.size() > 0 )
			return Response.status(Status.OK).entity(dashboard_data).build();
		else
			return Response.status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * Load Lecture
	 * @param section id
	 */
	@GET
	@Path("/getExamPaperTitle")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamPaperTitle(@QueryParam("courseid") String courseid){
		
		System.out.println("Print data values "+courseid);
		
		List <?> lecture=examservice.getExamPaperTitle(Integer.parseInt(courseid));
		if (lecture != null && lecture.size() != 0)
			return Response.status(Status.OK).entity(lecture).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * Load Lecture
	 * @param section id
	 */
	@GET
	@Path("/getExamPaper")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamPaper(@QueryParam("paperId") String paperId){
		
		List <?> lecture=examservice.getExamPaper(Integer.parseInt(paperId));
		if (lecture != null && lecture.size() != 0)
			return Response.status(Status.OK).entity(lecture).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * Load Lecture
	 * @param section id
	 */
	@GET
	@Path("/getAnswerKey")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswerKey(@QueryParam("paperId") String paperId){
		List <?> answerkey=examservice.getExamPaper(Integer.parseInt(paperId));
		JSONArray _finalja = new JSONArray();
		
		for(int j=0;j<answerkey.size();j++){
			Map<?, ?> mp = (Map<?, ?>) answerkey.get(j);
			List<?> _secQStId=examservice.getqstSection(mp.get("sectionid").toString());
			JSONArray _ja=new JSONArray();
			JSONArray _jasub=new JSONArray();
			JSONObject _jo=new JSONObject();
			_jo.put("sectionname", mp.get("sectionname"));
			_jo.put("instruction", mp.get("instruction"));
			_jo.put("papername", mp.get("papername"));
			
			 if(_secQStId.size() >0){
				 
				 for (int k=0;k<_secQStId.size();k++){
					 Map<?, ?> _mp = (Map<?, ?>) _secQStId.get(k);
					 _ja.add(_mp.get("question_id").toString());
					 
					try{
						 if(!_mp.get("subquestion_id").equals(null))
						   _jasub.add(_mp.get("subquestion_id").toString());
						 
					}catch(NullPointerException e){
						 System.out.println("print substings "+e);
					}
				 }
			 }
			 String ids=_ja.toString();
			 ids=ids.substring(1,ids.length()-1);
			 List<?> answer=connectionService.getQuestionAns(ids);
			 _jo.put("correctans", answer);
			 
			 if(_jasub.size()!=0){
				 ids=_jasub.toString();
				 ids=ids.substring(1,ids.length()-1);
			 	 List<?> _ortype=connectionService.getQuestionAns(ids);
			 	 _jo.put("ortypeans", _ortype);	 
			 }
		
			 _finalja.add(_jo);
		}
		
		 System.out.println("get query ANSWERS answer "+_finalja);
		
		if (_finalja != null && _finalja.size() != 0)
			return Response.status(Status.OK).entity(_finalja.toString()).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	
	
	/**
	 * Load Lecture
	 * @param section id
	 */
	@GET
	@Path("/deleteExamPaper")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteExamPaper(@QueryParam("paperId") String paperId){
		
		int status =examservice.deleteExamPaper(paperId);
		if (status != 0)
			return Response.status(Status.OK).entity(Status.ACCEPTED).build();
		else
			return Response.ok().status(Status.OK).entity(Status.BAD_REQUEST).build();
	}
	
	
	/**
	 * Load Lecture
	 * @param section id
	 */
	@GET
	@Path("/getpaperquestion")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaperQuestion(@QueryParam("sectionid") String sectionid){
		List <?> examquestion=examservice.getPaperQuestion(Integer.parseInt(sectionid));
		String dublicate="";
		JSONArray list = new JSONArray();
		if(examquestion.size() != 0){
 			for (int i = 0; i < examquestion.size(); i++) {
				JSONObject obj = new JSONObject();
				Map<?, ?> map = (Map<?, ?>) examquestion.get(i);
				obj.put("question", map.get("question").toString());
				obj.put("answers", map.get("answers").toString());
				obj.put("sectionid", map.get("id").toString());
				obj.put("questionid", map.get("questionid").toString());
				obj.put("id", map.get("questiontype_id").toString());
				
				if (map.get("subquestion_id") != null && !dublicate.equals( map.get("subquestion_id").toString())) {
					dublicate=map.get("subquestion_id").toString();
				    List <?> subval=examservice.getsubPaperQuestion(dublicate);
				    obj.put("ortype", subval);
				}
				list.add(obj);
			}	
		}
		System.out.println("key value "+list);
		
		if (list.size() !=0 )
			return Response.status(Status.OK).entity(list.toString()).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	
	/**
	 * SAVE QUIZ USER DATA
	 */
	@POST
	@Path("/quizUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response quizUsers(OnlineQuizUser onlineQuizUser){
		System.out.println("get save exam user data "+onlineQuizUser.getQuestionid());
		SatQuestion satquestion=new SatQuestion();
		satquestion.setId(onlineQuizUser.getQuestionid());
		liveQuiz.setQuestion(satquestion);
		
		java.util.Date date= new java.util.Date();
		liveQuiz.setCreateddate(new Timestamp(date.getTime()));
		liveQuiz.setTeletimetableid(onlineQuizUser.getSessionid());
		liveQuiz.setTeacherid(onlineQuizUser.getTeacherid());
		
		ArrayList<LiveQuizUser> al= new ArrayList<LiveQuizUser>();
		for(int j=0;j<onlineQuizUser.getStudent().length;j++){
			LiveQuizUser liveQuizUser=new LiveQuizUser();
			liveQuizUser.setStatus('A');
			liveQuizUser.setStudentid(Integer.parseInt(onlineQuizUser.getStudent()[j]));
			al.add(liveQuizUser);
		}
		liveQuiz.setLiveQuizUser(al);
		int isCategory=onlineQuizUserService.save(liveQuiz);
		return Response.ok().status(Status.OK).entity(isCategory).build();
	}
	
	/**
	 * SAVE QUIZ USER DATA
	 */
	@POST
	@Path("/quizUsersJson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response quizUsersJson(@QueryParam("jsonobject") String jsonobject){
		System.out.println("get save exam user data"+jsonobject);
		JSONObject obj =  JSONObject.fromObject(jsonobject);
		JSONArray array=obj.getJSONArray("student");
		SatQuestion satquestion=new SatQuestion();
		satquestion.setId(Integer.parseInt(""+obj.get("questionid")));
		liveQuiz.setQuestion(satquestion);
		
		java.util.Date date= new java.util.Date();
		liveQuiz.setCreateddate(new Timestamp(date.getTime()));
		liveQuiz.setTeletimetableid(Integer.parseInt(""+obj.get("sessionid")));
		liveQuiz.setTeacherid(Integer.parseInt(""+obj.get("teacherid")));
		liveQuiz.setNostudent(array.size());
		//liveQuiz.setCourselectureid(Integer.parseInt(""+obj.get("courselectureid")));
		
		ArrayList<LiveQuizUser> al= new ArrayList<LiveQuizUser>();
		for(int i=0;i<array.size();i++){
			LiveQuizUser liveQuizUser=new LiveQuizUser();
			liveQuizUser.setStatus('A');
			liveQuizUser.setStudentid(Integer.parseInt(""+array.get(i)));
			al.add(liveQuizUser);
		}
		liveQuiz.setLiveQuizUser(al);
		int isCategory=onlineQuizUserService.save(liveQuiz);
		System.out.println("get save exam user data isCategory "+isCategory);
		return Response.ok().status(Status.OK).entity(isCategory).build();
		
	}
	
	
	/**
	 * SAVE QUIZ USER DATA
	 */
	@POST
	@Path("/quizAnswer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response quizAnswer(QuizAnswerVM quizAnswerVM){
		java.util.Date date= new java.util.Date();
		int isCategory=0;
		Timestamp timestamp=new Timestamp(date.getTime());
		for(int i=0;i<quizAnswerVM.getAnswerid().length;i++){
			LiveQuiz liveQuiz = new LiveQuiz ();
			liveQuiz.setId(quizAnswerVM.getLiveprimeryid());
 			Qustionanswer qustionanswer=new Qustionanswer();
 			qustionanswer.setId(Integer.parseInt(quizAnswerVM.getAnswerid()[i]));
			liveQuizAnswer.setLiveQuiz(liveQuiz);
			liveQuizAnswer.setQustionanswer(qustionanswer);
			liveQuizAnswer.setStudentid(quizAnswerVM.getStudentid());
			liveQuizAnswer.setAnswerstatus(quizAnswerVM.getAnsstatus());
			liveQuizAnswer.setCreateddate(timestamp);
			isCategory+=liveQuizAnswerService.save(liveQuizAnswer);
		}
		
		return Response.ok().status(Status.OK).entity(isCategory).build();
	}
	
	/**
	 * SAVE QUIZ USER DATA
	 */
	@POST
	@Path("/quizAnswerJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public Response quizAnswerJSON(@QueryParam("jsonobject") String jsonobject){
		System.out.println("Print data values "+jsonobject);
		java.util.Date date= new java.util.Date();
		int isCategory=0;
		Timestamp timestamp=new Timestamp(date.getTime());
		
		JSONObject obj = JSONObject.fromObject(jsonobject);
		JSONArray array=obj.getJSONArray("answerid");
		
		for(int i=0;i<array.size();i++){
			LiveQuiz liveQuiz = new LiveQuiz ();
			liveQuiz.setId(Integer.parseInt(""+obj.get("liveprimeryid")));
			Qustionanswer qustionanswer=new Qustionanswer();
			qustionanswer.setId(Integer.parseInt(""+array.get(i)));
		 	liveQuizAnswer.setLiveQuiz(liveQuiz);
			liveQuizAnswer.setQustionanswer(qustionanswer);
			liveQuizAnswer.setStudentid(obj.getInt("studentid"));
			liveQuizAnswer.setAnswerstatus(""+obj.get("ansstatus"));
			liveQuizAnswer.setCreateddate(timestamp);
			isCategory+=liveQuizAnswerService.save(liveQuizAnswer);
		}
		return Response.ok().status(Status.OK).entity(isCategory).build();
	}
	
	/**
	 * Load session question for student report
	 * @param section id
	 */
	@GET
	@Path("/getQuizReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuizReport(@QueryParam("userid") String userid,@QueryParam("sessionid") String sessionid){
		List <?> report=onlineQuizUserService.getQuizReport(Integer.parseInt(userid),Integer.parseInt(sessionid));
		if (report != null && report.size() != 0)
			return Response.status(Status.OK).entity(report).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * GET QUIZ QUESTION COUNT 
	 * @param session id
	 * @param teacher id
	 */
	@GET
	@Path("/getQuizQstCount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuizQstCount(@QueryParam("teacherid") String teacherid,@QueryParam("sessionid") String sessionid){
		List <?> report=onlineQuizUserService.getQuizQstCount(teacherid,sessionid);
		System.out.println("Report status  "+report);
		if (report != null && report.size() != 0)
			return Response.status(Status.OK).entity(report).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * GET QUIZ QUESTION COUNT 
	 * @param session id
	 * @param teacher id
	 */
	@GET
	@Path("/getSessionTime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionTime(@QueryParam("teacherid") String teacherid){
		List <?> sessionId=onlineQuizUserService.getSessionID(teacherid);
		JSONArray ja=new JSONArray();
			for(int j=0;j<sessionId.size();j++){
				JSONObject jo =new JSONObject();
				Map<?, ?> maps = (Map<?, ?>) sessionId.get(j);
				Map<?, ?> time = (Map<?, ?>) onlineQuizUserService.getSessionTime(maps.get("teletimetableid").toString()).get(0);
				jo.put("createddate", time.get("createddate").toString());
				jo.put("time", time.get("times").toString());
				jo.put("sessionid", maps.get("teletimetableid").toString());
				ja.add(jo);
			}
			
		if (ja != null && ja.size() !=0)
			return Response.status(Status.OK).entity(ja.toString()).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	};
	
	
	/**
	 * GET QUIZ EXAM META TAG 
	 * @param session id
	 */
	@GET
	@Path("/exammeta")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExamMeta(@QueryParam("sessionid") String sessionid){
		List <?> meta=onlineQuizUserService.getExamMeta(sessionid);
		if (meta != null && meta.size() !=0)
			return Response.status(Status.OK).entity(meta).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	};
	
	/**
	 * GET QUIZ QUESTION COUNT 
	 * @param session id
	 * @param teacher id
	 */
	@GET
	@Path("/report")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport(@QueryParam("sessionid") String sessionid){
		List <?> primary=onlineQuizUserService.getReports(sessionid);
		JSONArray ja=new JSONArray();
			for(int j=0;j<primary.size();j++){
				JSONObject jo =new JSONObject();
				Map<?, ?> maps = (Map<?, ?>) primary.get(j);
				Map<?, ?> time = (Map<?, ?>) onlineQuizUserService.getReportData(maps.get("id").toString()).get(0);
				jo.put("correct", time.get("correct").toString());
				jo.put("wrong", time.get("wrong").toString());
				jo.put("courselectureid", maps.get("courselectureid").toString());
				jo.put("questionid", maps.get("question_id").toString());
				jo.put("nostudent", maps.get("nostudent").toString());
				ja.add(jo);
			}
		if (ja != null && ja.size() !=0)
			return Response.status(Status.OK).entity(ja.toString()).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	};
	
}
