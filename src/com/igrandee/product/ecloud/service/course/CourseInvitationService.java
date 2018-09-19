package com.igrandee.product.ecloud.service.course;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseinvitation;


@Service
@Named
@Scope("prototype")
public class CourseInvitationService extends GenericEntityService<Courseinvitation, Integer>{
	private static Logger CourseInvitationServicelogger = Logger.getLogger(CourseInvitationService.class);
	@Override
	protected Class<Courseinvitation> entityClass() {
		// TODO Auto-generated method stub
		return Courseinvitation.class;
	}
	
	public String updateCourseInvitation(String courseinvitationid){
		
		try	{
			String hql = "UPDATE Courseinvitation set courseinvitationstatus = :courseinvitationstatus "  + 
		             "WHERE courseinvitationid = :courseinvitationid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("courseinvitationstatus", 'A');
		query.setParameter("courseinvitationid", Integer.parseInt(courseinvitationid));
		Integer result = query.executeUpdate();
 		return result.toString();
		}
		catch(Exception e){
			if(CourseInvitationServicelogger.isInfoEnabled()){
				CourseInvitationServicelogger.error("error in updateCourseInvitation() in CourseInvitationService",e);
			}
			return "0";
		}
		
	}
	public List<?> getInvitationStatus(String courseinvitationid) 
	{
		List<?> query = null;
		try{
			query = getCurrentSession().createCriteria(Courseinvitation.class)
					.add(Restrictions.eq("this.courseinvitationid",Integer.parseInt(courseinvitationid)))
					.add(Restrictions.eq("this.courseinvitationstatus",'A'))					
					.setProjection(Projections.projectionList()                             
							.add(Projections.property("this.courseinvitationid").as("invitationid"))
							.add(Projections.property("this.courseinvitationstatus").as("courseinvitationstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}
		catch(Exception e)
		{
			if(CourseInvitationServicelogger.isInfoEnabled()){
				CourseInvitationServicelogger.error("error in updateCourseInvitation() in CourseInvitationService",e);
			}
		}
		return query;
	}
	
	public String setCourseInvitationFailed(String courseinvitationid){
		try	{
			String hql = "UPDATE Courseinvitation set courseinvitationstatus = :courseinvitationstatus "  + 
		             "WHERE courseinvitationid = :courseinvitationid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("courseinvitationstatus", 'F');
		query.setParameter("courseinvitationid", Integer.parseInt(courseinvitationid));
		Integer result = query.executeUpdate();
 		return result.toString();
		}
		catch(Exception e){
			if(CourseInvitationServicelogger.isInfoEnabled()){
				CourseInvitationServicelogger.error("error in updateCourseInvitation() in CourseInvitationService",e);
			}
			return "0";
		}
	}
    
	public String setCourseInvitationSuccess(String courseinvitationid){
		try	{
			String hql = "UPDATE Courseinvitation set courseinvitationstatus = :courseinvitationstatus "  + 
		             "WHERE courseinvitationid = :courseinvitationid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("courseinvitationstatus", 'I');
		query.setParameter("courseinvitationid", Integer.parseInt(courseinvitationid));
		Integer result = query.executeUpdate();
 		return result.toString();
		}
		catch(Exception e){
			if(CourseInvitationServicelogger.isInfoEnabled()){
				CourseInvitationServicelogger.error("error in updateCourseInvitation() in CourseInvitationService",e);
			}
			return "0";
		}
	}
	
	public String setCourseInvitationEmail(String courseinvitationid,String emailid){
		try	{
			String hql = "UPDATE Courseinvitation set emailid = :emailid "  + 
		             "WHERE courseinvitationid = :courseinvitationid";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("emailid", emailid);
		query.setParameter("courseinvitationid", Integer.parseInt(courseinvitationid));
		Integer result = query.executeUpdate();
 		return emailid;
		}
		catch(Exception e){
			if(CourseInvitationServicelogger.isInfoEnabled()){
				CourseInvitationServicelogger.error("error in updateCourseInvitation() in CourseInvitationService",e);
			}
			return "0";
		}
	}
	
}

