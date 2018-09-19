package com.igrandee.product.ecloud.action.course;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Faq;
import com.igrandee.product.ecloud.model.Faqanswer;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.FaqanswerService;

public class AnswerAction extends MasterActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static Logger AnswerActionlogger = Logger.getLogger(AnswerAction.class);
	
	@Inject
	FaqanswerService faqanswerService;
	
	@Autowired
	private Faq faq;
	
	@Autowired
	private Faqanswer Faqanswerobj;
	
	@Autowired
	private Orgperson answeredby;
	
	private String FAQid;
	private String faqanswer;
	
	/**
	 * @author seenivasagan_p
	 * Method to get answer for particular question
	 * @param FAQid
	 */
	@Action(value="/loadAnswers")
 	public void loadAnswers(){
		try	{
			List<?> anwersList=faqanswerService.getAnswers(Integer.parseInt(FAQid));
			/*if(anwersList.size()!=0){
				checkobj().toJsonResponse(anwersList);	
			}else{
				checkobj().toJsonResponse(anwersList);
			}	*/
			checkobj().toJsonResponse(anwersList);
		}
		catch(Exception e){
			if(AnswerActionlogger.isInfoEnabled()){
				AnswerActionlogger.error("error in loadAnswers() in CourseLectureViewAction",e);
			}			
		}  
	}
	/**
	 * @author mahalingam_A
	 * Method to get all trash answer for particular question
	 * @param FAQid
	 */
	@Action(value="/getTrashAnswers")
 	public void loadTrashAnswers(){
		try	{
			List<?> anwersList=faqanswerService.getTrashAnswers(Integer.parseInt(FAQid));
			checkobj().toJsonResponse(anwersList);
		}
		catch(Exception e){
			if(AnswerActionlogger.isInfoEnabled()){
				AnswerActionlogger.error("error in loadAnswers() in CourseLectureViewAction",e);
			}
		}  
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to save answer for a particular question
	 * @param FAQid,faqanswer
	 */
	@Action(value="/saveQAnswer")
	public void saveQAnswer(){
		try{
			faq.setFAQid(Integer.parseInt(FAQid));
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp answereddate =new Timestamp(date.getTime());
			String orgpersonid=getAttribute("orgpersonid");
			answeredby.setOrgpersonid(Integer.parseInt(orgpersonid));
			Faqanswerobj.setAnsweredby(answeredby);
			Faqanswerobj.setAnswereddate(answereddate);
			Faqanswerobj.setFaqanswer(faqanswer);
			Faqanswerobj.setFaqanswerstatus("A");
			Faqanswerobj.setFaq(faq);
			String answersaved = faqanswerService.save(Faqanswerobj).toString();
			if(answersaved!=null){
				checkobj().toJsonResponse(answersaved);
			}else{
				checkobj().toJsonString("failure");
			}
		}
		catch(Exception e){
			if(AnswerActionlogger.isInfoEnabled()){
				AnswerActionlogger.error("error in saveQAnswer() in CourseLectureViewAction",e);
			}
		}
	}
	
	/*Getters and setters*/
	public String getFAQid() {
		return FAQid;
	}
	public void setFAQid(String fAQid) {
		FAQid = fAQid;
	}
	public String getFaqanswer() {
		return faqanswer;
	}
	public void setFaqanswer(String faqanswer) {
		this.faqanswer = faqanswer;
	}

	
}