package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
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
import com.igrandee.product.ecloud.model.Lecturecontent;

/**
 * @author seenivasagan_p
 * Service to perform Lecture Content Operations
 */

@Service
@Named
@Scope("prototype")
public class LectureContentService extends GenericEntityService<Lecturecontent, Serializable>{
	private static Logger LectureContentServicelogger = Logger.getLogger(Lecturecontent.class);
	
	@Override
	protected Class<Lecturecontent> entityClass() {
		// TODO Auto-generated method stub
		return Lecturecontent.class;
	}
	
	/**
	 * @author mano_r
	 * this Methos used to get the filename for the corresponding courselecdtures.
	 * @param courselectureid
	 */
	public List<?> getLectureContents(int courselectureid){
		List<?> Query =null;
		try	{
			Query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Lecturecontent.class)
					.createAlias("this.courselecture","courselecture",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.contentapproval","contentapproval",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.add(Restrictions.eq("this.contentstatus", "A"))
					.add(Restrictions.eq("courselecture.courselectureid",courselectureid))
					.setProjection(Projections.projectionList()							
							.add(Projections.property("this.contentid").as("contentid"))
							.add(Projections.property("this.content").as("content"))
							.add(Projections.property("this.contenttype").as("contenttype"))
							.add(Projections.property("this.contentpath").as("contentpath"))
							.add(Projections.property("this.referenceurl").as("referenceurl"))
							.add(Projections.property("this.contentstatus").as("contentstatus"))

							.add(Projections.property("contentapproval.approvestatus").as("approvestatus"))
							.add(Projections.property("contentapproval.description").as("approvedescription"))
							
							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
			if(LectureContentServicelogger.isInfoEnabled())	{
				LectureContentServicelogger.error("error in getLectureContents() in LectureContentService ",e);
			}
		}
		return Query;
	}
	
	public String updateCourseContentStatus(String contentid){
		try{
			String hql = "UPDATE Lecturecontent set contentstatus = :contentstatus "  + 
					"WHERE contentid = :contentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("contentstatus", "D");
			query.setParameter("contentid", Integer.parseInt(contentid));
			Integer result = query.executeUpdate();
			if(result.SIZE>0){
				return "OK";
			}
			else{
				return "NOTOK";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			if(LectureContentServicelogger.isInfoEnabled())	{
				LectureContentServicelogger.error("error in getLectureContents() in LectureContentService ",e);
			}
			return null;
		}		
	}
}