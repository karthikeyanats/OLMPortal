package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Faqanswer;

@Service
@Named
@Scope("prototype")
public class FaqanswerService extends GenericEntityService<Faqanswer, Serializable>{
	private static Logger Faqanswerlogger = Logger.getLogger(Faqanswer.class);
	
	@Override
	protected Class<Faqanswer> entityClass() {
		// TODO Auto-generated method stub
		return Faqanswer.class;
	}
	
	/**
	 * @author seenivasagan_p
	 * Service Method to get answers for a particular question
	 * @param FAQid
	 * @return answers list
	 */
	public List<?> getAnswers(int FAQid){
		List<?> query =null;
		try	{			
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Faqanswer.class)
					.createAlias("this.faq", "faqobj" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.answeredby", "answeredby",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("answeredby.personid", "answeredpersonobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
    						.add(Restrictions.eq("faqobj.FAQid",FAQid))
    						.add(Restrictions.or(Restrictions.eq("this.faqanswerstatus","A"),
    											 Restrictions.eq("this.faqanswerstatus","C")))
    						.setProjection(Projections.projectionList()
    								
    						.add(Projections.property("faqobj.FAQid").as("FAQid"))
    						.add(Projections.property("faqobj.FAQquestion").as("FAQquestion"))
    						.add(Projections.property("faqobj.FAQstatus").as("FAQstatus"))
    						
    						.add(Projections.property("answeredpersonobj.firstName").as("answeredperson"))
    						.add(Projections.property("answeredpersonobj.personphotourl").as("photo"))
 							.add(Projections.property("this.faqanswerid").as("faqanswerid"))
							.add(Projections.property("this.faqanswer").as("faqanswer"))
							.add(Projections.property("this.answereddate").as("answereddate"))
							
							.add(Projections.property("this.faqanswerstatus").as("faqanswerstatus")))
							
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Faqanswerlogger.isInfoEnabled())	{
				Faqanswerlogger.error("error in getAnswers() in faqanswerService",e);
			}
 		}
		return query;
	}
	/**
	 * This method using for add the Correct Answer in FAQ by Authors
	 * @author mahalingam_a
	 * @since 11-11-2014
	 * @return
	 */
	public String updateCorrectAnswer(int faqanswerid)
	{
		String status="";
		String hql = "UPDATE Faqanswer set faqanswerstatus = :status WHERE faqanswerid = :faqanswerid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("status", "C");
		query.setParameter("faqanswerid", faqanswerid);
		int result = query.executeUpdate();
	
		if(result==0)
			status="success";
		else
			status="failure";
			
			return status;
		
	}
	
	/**
	 * This method using for delete the Answer in FAQ by Authors
	 * @author mahalingam_a
	 * @since 14-11-2014
	 * @return
	 */
	public String deleteAnswer(int faqanswerid)
	{
		String status="";
		String hql = "UPDATE Faqanswer set faqanswerstatus = :status WHERE faqanswerid = :faqanswerid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("status", "D");
		query.setParameter("faqanswerid", faqanswerid);
		int result = query.executeUpdate();
	
		if(result==0)
			status="success";
		else
			status="failure";
			
			return status;
		
	}
	/**
	 * This method using for add the Correct Answer in FAQ by Authors
	 * @author mahalingam_a
	 * @since 11-11-2014
	 * @return
	 */
	public String trashAnswer(int faqanswerid)
	{
		String status="";
		String hql = "UPDATE Faqanswer set faqanswerstatus = :status WHERE faqanswerid = :faqanswerid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("status", "T");
		query.setParameter("faqanswerid", faqanswerid);
		int result = query.executeUpdate();
	
		if(result==0)
			status="success";
		else
			status="failure";
			
			return status;
		
	}
	/**
	 * @author mahalingam_a
	 * Service Method to get all Trash answers for a particular question
	 * @param FAQid
	 * @return answers list
	 */
	public List<?> getTrashAnswers(int FAQid){
		List<?> query =null;
		try	{			
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Faqanswer.class)
					.createAlias("this.faq", "faqobj" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.answeredby", "answeredby",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("answeredby.personid", "answeredpersonobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
    						.add(Restrictions.eq("faqobj.FAQid",FAQid))
    						.add(Restrictions.eq("this.faqanswerstatus","T"))
    						.setProjection(Projections.projectionList()
    								
    						.add(Projections.property("faqobj.FAQid").as("FAQid"))
    						.add(Projections.property("faqobj.FAQquestion").as("FAQquestion"))
    						.add(Projections.property("faqobj.FAQstatus").as("FAQstatus"))
    						
    						.add(Projections.property("answeredpersonobj.firstName").as("answeredperson"))
 							.add(Projections.property("this.faqanswerid").as("faqanswerid"))
							.add(Projections.property("this.faqanswer").as("faqanswer"))
							.add(Projections.property("this.answereddate").as("answereddate"))
							
							.add(Projections.property("this.faqanswerstatus").as("faqanswerstatus")))
							
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Faqanswerlogger.isInfoEnabled())	{
				Faqanswerlogger.error("error in getTrashAnswers() in faqanswerService",e);
			}
 		}
		return query;
	}
	
	
	
}