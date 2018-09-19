package com.igrandee.product.ecloud.service.livesession;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Livesession;
import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;

@Named
@Service
@Scope("prototype")
public class LivesessionService extends GenericEntityService<Livesession, Integer>{
	
	private static final Logger LivesessionLogger = Logger.getLogger(Livesession.class);
	
	
	@Override
	protected Class<Livesession> entityClass() {
		// TODO Auto-generated method stub
		return Livesession.class;
	}

	public List<?> getScheduleDetails(int courseid,Timestamp fromdate1,Timestamp todate1){
		try{
			return getCurrentSession().createCriteria(Livesession.class)
				.createAlias("this.courseid","course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.between("this.scheduledate",fromdate1,todate1))
				.add(Restrictions.eq("this.livesessionstatus","A"))
				.add(Restrictions.eq("course.courseid",courseid))
				
				.setProjection(Projections.projectionList()
						.add(Projections.property("course.courseid").as("courseid"))
						.add(Projections.property("this.scheduledate").as("scheduledate"))
						.add(Projections.property("this.description").as("description"))
						.add(Projections.property("this.starttime").as("starttime"))
						.add(Projections.property("this.endtime").as("endtime"))
						.add(Projections.property("this.price").as("price"))						
						.add(Projections.property("this.livesessionid").as("livesessionid"))
						.add(Projections.property("this.livesessionstatus").as("livesessionstatus")))
						.addOrder(Order.asc("scheduledate"))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public List<?> getScheduletimelist(int courseid,Timestamp scheduledate){				 
		try{
			return getCurrentSession().createCriteria(Livesession.class)
				.createAlias("this.courseid","course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				.createAlias("course.orgperson","orgperson")
				.add(Restrictions.eq("this.livesessionstatus","A"))
				.add(Restrictions.eq("course.courseid",courseid))
			//	.add(Restrictions.eq("orgperson.orgpersonid",personid))
				.add(Restrictions.eq("this.scheduledate",scheduledate))
				.setProjection(Projections.projectionList()
						.add(Projections.property("course.courseid").as("courseid"))
						.add(Projections.property("orgperson.orgpersonid").as("personid"))
						.add(Projections.property("course.coursestatus").as("coursestatus"))
						.add(Projections.property("this.scheduledate").as("scheduledate"))
						.add(Projections.property("this.description").as("description"))
						.add(Projections.property("this.starttime").as("starttime"))
						.add(Projections.property("this.endtime").as("endtime"))
						.add(Projections.property("this.price").as("price"))
						.add(Projections.property("this.livesessionid").as("livesessionid"))
						.add(Projections.property("this.livesessionstatus").as("livesessionstatus")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
public List<?> getScheduletimeauthorlist(int personid,Timestamp scheduledate){				 
		try{
			return getCurrentSession().createCriteria(Livesession.class)
				.createAlias("this.courseid","course")
				.createAlias("course.orgperson","orgperson")
				.add(Restrictions.eq("this.livesessionstatus","A"))
				.add(Restrictions.eq("orgperson.orgpersonid",personid))
				.add(Restrictions.eq("this.scheduledate",scheduledate))
				.add(Restrictions.in("course.coursestatus",new String[] {"A","D"}))
				.setProjection(Projections.projectionList()
						.add(Projections.property("course.courseid").as("courseid"))
						.add(Projections.property("orgperson.orgpersonid").as("personid"))
						.add(Projections.property("course.coursestatus").as("coursestatus"))
						.add(Projections.property("this.scheduledate").as("scheduledate"))
						.add(Projections.property("this.description").as("description"))
						.add(Projections.property("this.starttime").as("starttime"))
						.add(Projections.property("this.endtime").as("endtime"))
						.add(Projections.property("this.price").as("price"))
						.add(Projections.property("this.livesessionid").as("livesessionid"))
						.add(Projections.property("this.livesessionstatus").as("livesessionstatus")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	public boolean deleteSchedule(int livesessionid){
		Query query = null;
		 query=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Livesession  set livesessionstatus='D' where livesessionid="+livesessionid+"");		
	
		int result=query.executeUpdate();
		if(result>0){
			return true;
		}
		return false;
	}
	
	
	public List<?> getSchedulevalues(int livesessionid) {
		List<?> getschedulevaluelist = null;
		try {
			
			getschedulevaluelist = getCurrentSession().createCriteria(Livesession.class)
					.add(Restrictions.eq("this.livesessionstatus", "A"))
					.add(Restrictions.eq("this.livesessionid",livesessionid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.livesessionid").as("livesessionid"))
							.add(Projections.property("this.description").as("description"))
							.add(Projections.property("this.scheduledate").as("scheduledate"))
							.add(Projections.property("this.starttime").as("starttime"))
							.add(Projections.property("this.endtime").as("endtime"))
							.add(Projections.property("this.price").as("price")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			

			LivesessionLogger.error("error in  LivesessionService in getSchedulevalues() ",e);
		}
		return getschedulevaluelist;
	} 

	public Integer updateSchedule(int livesessionid,String description,String starttime,String endtime,String price,Timestamp startdatetamp,Timestamp enddatetamp){
		int updatetype = 1;
		try	{
		
		String hql= "UPDATE Livesession set description = :description "  +
				", starttime = :starttime "+ 
				", endtime = :endtime "+ 
				", price = :price "+ 
				", startdatetime = :startdatetamp "+ 
				", enddatetime = :enddatetamp "+ 
	             "WHERE livesessionid = :livesessionid";
	Query query = getCurrentSession().createQuery(hql);
	query.setParameter("description", description);    
	query.setParameter("starttime", starttime);
	query.setParameter("endtime", endtime);
	query.setParameter("livesessionid", livesessionid);
	query.setParameter("price", price);
	query.setParameter("startdatetamp", startdatetamp);
	query.setParameter("enddatetamp", enddatetamp);
	Integer result = query.executeUpdate();
	return result;
		}
		
		catch(Exception e){
			e.printStackTrace();
			updatetype=0;
			if(LivesessionLogger.isInfoEnabled()){
				LivesessionLogger.error("error in  LivesessionService in updateSchedule() ",e);		
			}
		}
		return updatetype;
		
	}
public List<?> getlivesessionschedulelist(String livesessionid){
		
		return	 getCurrentSession().createCriteria(Livesessionenrollment.class)
						.createAlias("livesession", "livesession")
						.createAlias("livesession.courseid", "course")
						.createAlias("livesessionpayments", "livesessionpayments")
							.add(Restrictions.eq("livesession.livesessionid", Integer.parseInt(livesessionid)))
								.setProjection(Projections.projectionList()
										.add(Projections.property("this.livesessionenrollmentid").as("livesessionenrollmentid"))
										.add(Projections.property("course.coursetitle").as("coursetitle"))
										.add(Projections.property("course.courseid").as("courseid"))
										.add(Projections.property("livesessionpayments.paymenttype").as("paymenttype"))
										.add(Projections.property("livesessionpayments.orderno").as("orderno"))
										.add(Projections.property("livesession.price").as("price"))
										.add(Projections.property("livesession.description").as("description"))
										.add(Projections.property("livesession.starttime").as("starttime"))
										.add(Projections.property("livesession.livesessionid").as("livesessionid"))
										.add(Projections.property("livesession.scheduledate").as("scheduledate"))
										.add(Projections.property("livesession.endtime").as("endtime")))
											.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	
public List<?> getSchedulelist(int courseid){
	try{
		return getCurrentSession().createCriteria(Livesession.class)
			.createAlias("this.courseid","course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			.add(Restrictions.eq("this.livesessionstatus","A"))
			.add(Restrictions.eq("course.courseid",courseid))
			
			.setProjection(Projections.projectionList()
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("this.scheduledate").as("scheduledate"))
					.add(Projections.property("this.description").as("description"))
					.add(Projections.property("this.starttime").as("starttime"))
					.add(Projections.property("this.endtime").as("endtime"))
					.add(Projections.property("this.price").as("price"))						
					.add(Projections.property("this.livesessionid").as("livesessionid"))
					.add(Projections.property("this.livesessionstatus").as("livesessionstatus")))
					.addOrder(Order.asc("scheduledate"))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
}
public List<?> getlivesessionenrolllist(String livesessionenrollmentid){
	
	return	 getCurrentSession().createCriteria(Livesessionenrollment.class)
					.createAlias("livesession", "livesession")
					.createAlias("livesession.courseid", "course")
					.createAlias("livesessionpayments", "livesessionpayments")
						.add(Restrictions.eq("this.livesessionenrollmentid", Integer.parseInt(livesessionenrollmentid)))
							.setProjection(Projections.projectionList()
									.add(Projections.property("this.livesessionenrollmentid").as("livesessionenrollmentid"))
									.add(Projections.property("course.coursetitle").as("coursetitle"))
									.add(Projections.property("course.courseid").as("courseid"))
									.add(Projections.property("livesessionpayments.paymenttype").as("paymenttype"))
									.add(Projections.property("livesessionpayments.orderno").as("orderno"))
									.add(Projections.property("livesession.price").as("price"))
									.add(Projections.property("livesession.description").as("description"))
									.add(Projections.property("livesession.starttime").as("starttime"))
									.add(Projections.property("livesession.livesessionid").as("livesessionid"))
									.add(Projections.property("livesession.scheduledate").as("scheduledate"))
									.add(Projections.property("livesession.endtime").as("endtime")))
										.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
}
}