package com.igrandee.product.ecloud.action.course;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Faq;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.FAQService;

/**
 * @author seenivasagan_p
 * Action Class for Question 
 */
public class QuestionAction extends MasterActionSupport{
	
	private static final long serialVersionUID = 1L;
	private static Logger QuestionActionlogger = Logger.getLogger(QuestionAction.class);
	
	@Inject
	FAQService fAQService;
	
	@Autowired
	private Courselecture courselecture;
	
	@Autowired
	private Faq faq;
	
	@Autowired
	private Orgperson askedby;
	
	private String courselectureid;
	private String FAQquestion;
	
	/**
	 * @author seenivasagan_p
	 * Method to get questions for particular lecture
	 */
	@Action(value="/loadLectureQuestions")
 	public void loadLectureQuestions(){
		try	{
			List<?> lectureQuestionsList=fAQService.getLectureQuestions(Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(lectureQuestionsList);
		}
		catch(Exception e){
			if(QuestionActionlogger.isInfoEnabled()){
				QuestionActionlogger.error("error in loadLectureQuestions() in CourseLectureViewAction",e);
			}
		}  
	}
	
	@Action(value="/loadLectureDiscussions")
 	public void loadLectureDiscussion(){
		try	{
			List<?> lectureQuestionsList=fAQService.getLectureQuestions(Integer.parseInt(courselectureid));
			if(lectureQuestionsList.size()!=0){
				List encodedNotesList=new ArrayList();
				Iterator<?> iterator=lectureQuestionsList.iterator();
				while(iterator.hasNext()){
					Map<String, String> map=(Map<String, String>) iterator.next();
					if(map.get("FAQquestion")!=null){
						map.put("FAQquestion", URLEncoder.encode(map.get("FAQquestion"),"UTF-8"));
					}
					encodedNotesList.add(map);
				}
				checkobj().toJsonResponse(encodedNotesList);	
			}else{
				checkobj().toJsonString("empty");
			}
			
		}
		catch(Exception e){
			if(QuestionActionlogger.isInfoEnabled()){
				QuestionActionlogger.error("error in loadLectureQuestions() in CourseLectureViewAction",e);
			}
			checkobj().toJsonString("error");
		}  
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to save the question for a particular lecture
	 */
	@Action(value="/saveQuestion")
	public void saveQuestion(){
		try{
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp askeddate =new Timestamp(date.getTime());
			courselecture.setCourselectureid(Integer.parseInt(courselectureid));
			faq.setCourselecture(courselecture);
			String orgpersonid=getAttribute("orgpersonid");
			askedby.setOrgpersonid(Integer.parseInt(orgpersonid));
			faq.setAskedby(askedby);
			faq.setAskeddate(askeddate);
			faq.setFAQquestion(java.net.URLDecoder.decode(FAQquestion,"UTF-8"));
			faq.setFAQstatus("A");
			String savedResult=fAQService.save(faq).toString();
			checkobj().toJsonResponse(savedResult);
		}
		catch(Exception e){
			if(QuestionActionlogger.isInfoEnabled()){
				QuestionActionlogger.error("error in saveQuestion() in QuestionAction",e);
			}
			checkobj().toJsonString("error");
		}
	}

	/*Getters and setters*/
	public String getCourselectureid() {
		return courselectureid;
	}
	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}
	public String getFAQquestion() {
		return FAQquestion;
	}
	public void setFAQquestion(String fAQquestion) {
		FAQquestion = fAQquestion;
	}
}