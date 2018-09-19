/**
 * 
 */
package com.igrandee.product.ecloud.adapter;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.sat.Evaluation;
import com.igrandee.product.ecloud.model.sat.Questionconnector;
import com.igrandee.product.ecloud.service.sat.ConnectionService;
import com.igrandee.product.ecloud.service.sat.EvaluationService;
import com.igrandee.product.ecloud.service.sat.OnlineQuizUserService;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.igrandee.product.ecloud.viewmodel.sat.BulkQuestionVM;
import com.igrandee.product.ecloud.viewmodel.sat.QuestionViewModel;
import com.igrandee.product.ecloud.viewmodel.sat.StudentAnsVM;
import com.igrandee.product.satutil.model.Difficultyfactor;
import com.igrandee.product.satutil.model.Essayquestion;
import com.igrandee.product.satutil.model.Questionmeta;
import com.igrandee.product.satutil.model.Questiontype;
import com.igrandee.product.satutil.model.Qustionanswer;
import com.igrandee.product.satutil.model.SatQuestion;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;

/**
 * @author selvarajan_j
 * 
 */
@SuppressWarnings("rawtypes")
 public class SatConnectionAdapter extends MasterActionSupport{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final Logger satQuestionServiceLogger = Logger.getLogger(SatConnectionAdapter.class);
	static final BlogCommonsUtil BLOG_COMMONS_UTIL = new BlogCommonsUtil();
	
	@InjectParam
	SatQuestion question;
	
	@InjectParam
	Essayquestion essayQuestion;
	
	@InjectParam
	Difficultyfactor difficultyfactor;
		
	@InjectParam
	Questiontype questiontype;
	
	@InjectParam
	Questionmeta questionmeta;	
	  
	@InjectParam
	Questionconnector questionconnector;
	
	@InjectParam
	Courselecture courselecture;
	
	@InjectParam
	ConnectionService connectionService;  
	
	@InjectParam
	EvaluationService evaluationService;
	
	@InjectParam
	Evaluation evaluation;
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	OnlineQuizUserService onlineQuizUserService;
 
/**
 * Save New Questions 	
 * @param questionViewModel
 * @return
 */
public int saveQuestionData(QuestionViewModel questionViewModel){
	java.util.Date date= new java.util.Date();
	satQuestionServiceLogger.info("Save Question Function");
	/** ADD Question Name Into the Question table */ 
	
	SatQuestion question=new SatQuestion();
	question.setQuestion(questionViewModel.getQuestion());
	question.setMark(questionViewModel.getMark());
	question.setStatus(questionViewModel.getStatus());
	question.setCategory(questionViewModel.getQstcategory()); 
	/** ADD Difficult id into the question table */ 
	difficultyfactor.setId(questionViewModel.getDifficultyfactor());	
	question.setDifficultyfactor(difficultyfactor);
	
	/** ADD Question Type id  into the question table*/
	questiontype.setId(questionViewModel.getQuestiontypeid());
	question.setQuestiontype(questiontype);
	
	int orgpersonid=1024;
	if(!questionViewModel.getEntryType().equals("qstionbank"))
		orgpersonid=Integer.parseInt(getAttribute("orgpersonid"));
	 
	
	questionmeta.setCreatedby(orgpersonid);
	questionmeta.setDateofcreation(new Timestamp(date.getTime()));
 	questionmeta.setLifetime(new Timestamp(date.getTime()));
	questionmeta.setNoofoccurance(questionViewModel.getNoofoccurance());
	Set<Questionmeta> questMetaSet=new HashSet<Questionmeta>();
	questMetaSet.add(questionmeta);
	question.setQuestionmetas(questMetaSet);
	
	/** ADD QuestionConnector details */
	questionconnector.setDateofcreation(new Timestamp(date.getTime()));
	questionconnector.setStatus('A');
	courselecture.setCourselectureid(questionViewModel.getCourselectureid());
	questionconnector.setCourselecture(courselecture);
	 
	/** ADD Essay details  */
	if(questionViewModel.getQuestiontypeid() == 4 ){
	 	essayQuestion.setStatus(questionViewModel.getStatus());
		Set<Essayquestion> essaySet=new HashSet<Essayquestion>();
		essaySet.add(essayQuestion);
		question.setEssayquestions(essaySet);
	}
	 /** ADD Question answer details */
	List<Qustionanswer> questAnsSet=new ArrayList<Qustionanswer>();
	for(int i=0;i<questionViewModel.getAnswer().length;i++){
		Qustionanswer qustionanswer = new Qustionanswer();			
		qustionanswer.setAnswers(questionViewModel.getAnswer()[i]);
		qustionanswer.setStatus(questionViewModel.getStatus());
		satQuestionServiceLogger.info("ANSWERT AREA ");
		if(questionViewModel.getCorrectAnswer()[i] != null ){
			satQuestionServiceLogger.info("ANSWERT NOTNULL AREA "+questionViewModel.getAnswerDescription());
			qustionanswer.setCorrectans(Integer.parseInt(questionViewModel.getCorrectAnswer()[i]));
			qustionanswer.setAnswerDescription(questionViewModel.getAnswerDescription());
		}
		questAnsSet.add(qustionanswer);
	}
	question.setQustionanswers(questAnsSet);
	questionconnector.setQuestion(question);
	int isCategory=connectionService.save(questionconnector);

	satQuestionServiceLogger.info("ANSWERT NOTNULL AREA RESULT "+isCategory);
	return isCategory;
	}

/**
 * Save Bulk Questions 	
 * @param questionViewModel
 * @return
 */
public int saveBulkQuestionData(BulkQuestionVM bulkQuestionVM){
	java.util.Date date= new java.util.Date();
	int isCategory=0;
	for(int i=0;i<bulkQuestionVM.qstobject.length;i++){
 		
		SatQuestion question  = new SatQuestion();
		
		question.setQuestion(bulkQuestionVM.qstobject[i]);
		question.setStatus('A');
		question.setCategory(bulkQuestionVM.getQstcategory());
		difficultyfactor=new Difficultyfactor();
		difficultyfactor.setId(1);	
		question.setDifficultyfactor(difficultyfactor);
		questiontype.setId(2);
		question.setQuestiontype(questiontype);
 		Questionmeta questionmeta=new Questionmeta(); 
		questionmeta.setDateofcreation(new Timestamp(date.getTime()));
 		questionmeta.setLifetime(new Timestamp(date.getTime()));
		 
		int orgpersonid=1024;
		if(!bulkQuestionVM.getEntryType().equals("qstionbank"))
			orgpersonid=Integer.parseInt(getAttribute("orgpersonid"));
			
		questionmeta.setCreatedby(orgpersonid);
		questionmeta.setDateofcreation(new Timestamp(date.getTime()));
  		questionmeta.setNoofoccurance(100);
		Set<Questionmeta> questMetaSet=new HashSet<Questionmeta>();
		questMetaSet.add(questionmeta);
		question.setQuestionmetas(questMetaSet);
		
		Questionconnector questionconnector=new Questionconnector();
		questionconnector.setDateofcreation(new Timestamp(date.getTime()));
		questionconnector.setStatus('A');
		courselecture=new Courselecture();
		courselecture.setCourselectureid(bulkQuestionVM.getCourselectureid());
		questionconnector.setCourselecture(courselecture);
		
		 //** ADD Question answer details *//*
		List<Qustionanswer> questAnsSet=new ArrayList<Qustionanswer>();
		String options=bulkQuestionVM.optionObject[i];
		String values[]=options.split(",");
		String crtStr=bulkQuestionVM.ansobject[i];
		String crtans[]=crtStr.split(",");
		for(int j=0;j<values.length;j++){
			Qustionanswer qustionanswer = new Qustionanswer();			
			qustionanswer.setAnswers(values[j]);
			qustionanswer.setAnswerDescription("-");
			qustionanswer.setStatus('A');
			qustionanswer.setCorrectans(Integer.parseInt(crtans[j]));
			questAnsSet.add(qustionanswer);
		}
		question.setQustionanswers(questAnsSet);
		questionconnector.setQuestion(question);
		satQuestionServiceLogger.info("Print COUNT "+isCategory);
		isCategory+=connectionService.save(questionconnector);
	}
	return isCategory;
}

/**
 * Save Student Answer
 * @param studentAnsVM
 * @return
 */
 public int saveAnswer(StudentAnsVM studentAnsVM){
	 java.util.Date date= new java.util.Date();
 	 question.setId(studentAnsVM.getQuestionid());
 	 evaluation.setDateofcreation(new Timestamp(date.getTime()));
 	 evaluation.setStudentanswer(studentAnsVM.getAnswer());
 	 /** SET COUESE ENROLLMENT ID */
  	 evaluation.setQuestion(question);
 	 evaluation.setAnswerid(studentAnsVM.getAnswerid());
 	 evaluation.setAttempt(studentAnsVM.getAttempt());
 	 orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
 	 evaluation.setOrgperson(orgperson);
 	 evaluation.setAnswerstate(studentAnsVM.getAnsstatus());
	return evaluationService.save(evaluation);
 	}
 
 
 /**
  * Get Student Answer
  * @return
  */
  public List getQuestionQuiz(int questionid,int userid,int sessionid,int lquizid){
	  List questions=connectionService.getQuestionQuiz(questionid);
	  List <?> report=onlineQuizUserService.getStudentQuiz(userid,lquizid);
	  ArrayList<List<?>> al=new ArrayList<List<?>>();
	  al.add(questions);
	  al.add(report);
	  
	  return al;
  }
  
 
 /**
  * Update Student Answer
  * @param studentAnsVM
  * @return
  */
  public int updateAnswer(StudentAnsVM studentAnsVM){
	  return evaluationService.updateAnswer(studentAnsVM.getUpdateid(), studentAnsVM.getAnswer(), studentAnsVM.getAnsstatus(),studentAnsVM.getAnswerid(),studentAnsVM.getAttempt());
  	}
  
  /**
   * Get Student Result
   * @param lectureid
   * @return
   */
   public List<?> getSTDResult(int lectureid,String time){
	   List<?> takentime=connectionService.checkFirstTime(Integer.parseInt(getAttribute("orgpersonid")),lectureid);
	   int wrng=0;
	   int crt=0;
	   //CONFIG FIRST TIME ANSWER STATE
		if (takentime.size() != 0) {
			Map<?, ?> map = (Map<?, ?>) takentime.get(0);
			if (map.get("takentime") == null) {
				for (int i = 0; i < takentime.size(); i++) {
					Map<?, ?> maps = (Map<?, ?>) takentime.get(i);
					if (maps.get("answerstate").toString().equals("C")) {
						crt++;
					} else if (maps.get("answerstate").toString().equals("W"))
						wrng++;
				}
				if (map.get("id") != null) {
					time = time + " " + crt + " " + wrng;
					connectionService.updateTekenTime(map.get("id").toString(),time);
				}
			}
		}
 	  return connectionService.getSTDResult(Integer.parseInt(getAttribute("orgpersonid")),lectureid);
   }
   /**
    * 
    * @param lectureid
    * @return
    */
   	public List<?> getquestionID(int lectureid){
		return connectionService.getQuestionID(Integer.parseInt(getAttribute("orgpersonid")),lectureid);
	}
   	/**
   	 * 
   	 * @param lectureid
   	 * @return
   	 */
	@SuppressWarnings("unchecked")
	public List <?> getSATSatus(String lectureid){
			return connectionService.getSATSatus(Integer.parseInt(getAttribute("orgpersonid")),lectureid);
	}
   	
   	
 /**
  * 
  * @param questionid
  * @return
  */
   	public List<?> getSTDAnswer(int questionid){
   		return connectionService.getSTDAnswer(questionid,Integer.parseInt(getAttribute("orgpersonid")));
   	}
   	
   	
   	public String uploadMedia(InputStream inputMedia, FormDataContentDisposition fileDetail){
   		String  returnURL = null;
		 try { 
			 returnURL = BLOG_COMMONS_UTIL.uploadFile(inputMedia,fileDetail,"sat"); 
		} catch (Exception e) {
			satQuestionServiceLogger.error(e);
		}
		return returnURL; 
   	}
   	
   	public String getFileName(){
   		String  returnURL = null;
		 try { 
			 returnURL = BLOG_COMMONS_UTIL.renamefile("sat"); 
		} catch (Exception e) {
			satQuestionServiceLogger.error(e);
		}
		return returnURL; 
   	}
     
}
