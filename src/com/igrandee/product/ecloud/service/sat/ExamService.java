/**
 * 
 */
package com.igrandee.product.ecloud.service.sat;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.sat.ExamConnector;
import com.igrandee.product.satutil.model.QuestionPaper;


/**
 * @author selvarajan_j
 *
 */
@Service
@Scope("prototype")
@Named
public class ExamService extends GenericEntityService<ExamConnector, Integer>{
	
	static final Logger examPaperServiceLogger = Logger.getLogger(ConnectionService.class);
 
	@Override
	protected Class<ExamConnector> entityClass() {
 		return ExamConnector.class;
	}
	
	
	/**
	 * @author selvarajan_j
	 * @return list as exam paper title and name
	 */
	public List<?> getExamPaperTitle(int courseid){
		List<?> query=null;
		try	{
			query = getCurrentSession().createCriteria(ExamConnector.class)
					.createAlias("this.course", "course")
					.createAlias("this.questionpaper", "questionpaper")
					.createAlias("course.coursecategory", "coursecategory")
					.add(Restrictions.eq("course.courseid",courseid))
					.add(Restrictions.eq("questionpaper.status",'A'))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("questionpaper.papername").as("papername"))
							.add(Projections.property("questionpaper.createddate").as("createddate"))
							.add(Projections.property("questionpaper.id").as("id")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(examPaperServiceLogger.isInfoEnabled())	{
				examPaperServiceLogger.error("error in loadLecturePath() in CourseLectureService",e);
			}
		}
		return query;
	}
	
	/**
	 * @author selvarajan_j
	 * @return list as Exam paper details 
	 */
	public List<?> getExamPaper(int paperId){
		List<?> query=null;
		try	{
			Order order = Order.asc("questionPaperSection.id");
			query = getCurrentSession().createCriteria(QuestionPaper.class)
					//.createAlias("this.questionPaperSection", "questionPaperSection")
					.add(Restrictions.eq("this.id",paperId))
					.add(Restrictions.eq("this.status",'A'))
					//.add(Restrictions.eq("questionPaperSection.status",'A'))
					.addOrder(order)
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.papername").as("papername"))
							.add(Projections.property("this.templatepdf").as("templatepdf"))
							.add(Projections.property("this.answerkeypdf").as("answerkeypdf"))
							.add(Projections.property("this.instruction").as("instruction"))
							
							//.add(Projections.property("questionPaperSection.sectionname").as("sectionname"))
							//.add(Projections.property("questionPaperSection.id").as("sectionid"))
							.add(Projections.property("this.id").as("id")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(examPaperServiceLogger.isInfoEnabled())	{
				examPaperServiceLogger.error("error in loadLecturePath() in CourseLectureService",e);
			}
		}
		return query;
	}
	
	 
	public List<?> getPaperQuestion(int sectionid){
			List<?> examQuestion = null;
		try {
			examQuestion=getCurrentSession().createSQLQuery("select a.id,b.question,c.answers,b.id as questionid ,b.questiontype_id,a.subquestion_id from sectionquestion a,question b, questionanswers c where a.papersection_id='"+sectionid+"' and a.question_id=b.id and b.id=c.question_id and a.status='A' " 
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				examPaperServiceLogger.error("error in examPaperServiceLogger in getPaperQuestion()",e);
			}
			return examQuestion;
	}
	
	 
	public List<?> getqstSection(String sectionid){
			List<?> sectionqst = null;
		try {
			//sectionqst=getCurrentSession().createSQLQuery("select question_id,subquestion_id,papersection_id from sectionquestion where papersection_id in (select id from questionpapersection where questionpaper_id='"+sectionid+"' and status='A') and status ='A' order by papersection_id").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			 sectionqst=getCurrentSession().createSQLQuery("select question_id,subquestion_id from sectionquestion where papersection_id ='"+sectionid+"'  and status ='A' order by papersection_id").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				examPaperServiceLogger.error("error in examPaperServiceLogger in getPaperQuestion()",e);
			}
			return sectionqst;
	}
	
	public List<?> getSection(String sectionid){
		List<?> sectionqst = null;
	try {
		sectionqst=getCurrentSession().createSQLQuery("select id,sectionname from questionpapersection where questionpaper_id='"+sectionid+"' and status='A'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			examPaperServiceLogger.error("error in examPaperServiceLogger in getPaperQuestion()",e);
		}
		return sectionqst;
	}
	
	 
	public List<?> getsubPaperQuestion(String questionid){
			List<?> examQuestion = null;
		try {
			examQuestion=getCurrentSession().createSQLQuery("select b.question ,c.answers,b.id as questionid ,b.questiontype_id as id from question b, questionanswers c where b.id ='"+questionid+"' and b.id=c.question_id;"
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				examPaperServiceLogger.error("error in examPaperServiceLogger in getPaperQuestion()",e);
			}
			return examQuestion;
	}
	
	/**
	 * @return Type Of student result list
	 */
	public int updatePaperQuestion(String sectionid){
		int status=0;
	try {
		status = getCurrentSession().createQuery("update com.igrandee.product.satutil.model.SectionQuestion set status='D' where id='"+sectionid+"'").executeUpdate();
		}catch (Exception e) {
			examPaperServiceLogger.error("error in examPaperServiceLogger in updatePaper()",e);
		}
		return status;
	}
	
	/**
	 * @return Type Of student result list
	 */
	public int updatepapertitle(String title,String id){
		int status=0;
	try {
		status = getCurrentSession().createQuery("update com.igrandee.product.satutil.model.QuestionPaper set papername='"+title+"' where id='"+id+"'").executeUpdate();
		}catch (Exception e) {
			examPaperServiceLogger.error("error in examPaperServiceLogger in updatePaper()",e);
		}
		return status;
	}
	
	/**
	 * @author selvarajan_j
	 * @return list as Exam paper details 
	 */
	public List<?> getPaperSectionid(String sectionid){
			List<?> papersectionid = null;
		try {
			papersectionid=getCurrentSession().createSQLQuery("select papersection_id from sectionquestion where id='"+sectionid+"' ;"
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				examPaperServiceLogger.error("error in examPaperServiceLogger in getPaperQuestion()",e);
			}
			return papersectionid;
	}
	
	/**	
	 * @return Type Of student result list
	 */
	public int saveSectionQuestoin(String sectionPaperid,String questionid,String subqst){
		int status=0;
	try {
		System.out.println("get types "+subqst);
		if(subqst.equals("-")) 
		status=getCurrentSession().createSQLQuery("INSERT INTO sectionquestion (status, question_id, " +
				"papersection_id ) " +
				"VALUES ('A','"+questionid+"','"+sectionPaperid+"')")
				.executeUpdate();
		else
			status=getCurrentSession().createSQLQuery("INSERT INTO sectionquestion (status, question_id, " +
					"subquestion_id, papersection_id) " +
					"VALUES ('A','"+questionid+"','"+subqst+"','"+sectionPaperid+"')")
					.executeUpdate();
		
			
		}catch (Exception e) {
			examPaperServiceLogger.error("error in examPaperServiceLogger in updatePaper()",e);
		}
		return status;
	}
	

	/**
	 * @return Type Of student result list
	 */
	public int deleteExamPaper(String id){
		int status=0;
	try {
		status = getCurrentSession().createQuery("update com.igrandee.product.satutil.model.QuestionPaper set status='D' where id='"+id+"'").executeUpdate();
		}catch (Exception e) {
			examPaperServiceLogger.error("error in examPaperServiceLogger in updatePaper()",e);
		}
		return status;
	}
}
