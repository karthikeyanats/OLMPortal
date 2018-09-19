package com.igrandee.product.ecloud.service.course;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursesection;

@Service
@Named
@Scope("prototype")
public class CourseSectionService  extends GenericEntityService<Coursesection, Integer>{

	private static Logger Coursesectionlogger = Logger.getLogger(Coursesection.class);
	@Override
	protected Class<Coursesection> entityClass() {
		// TODO Auto-generated method stub
		return Coursesection.class;
	}
	
	public List checkSection(String coursesectiontitle,String courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursesection.class)
					    .createAlias("this.course", "course")
    					.add(Restrictions.eq("this.coursesectionstatus","A"))
    					.add(Restrictions.eq("this.coursesectiontitle",coursesectiontitle))
    					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
    					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursesectionid").as("coursesectionid")))   
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 		}
		catch(Exception e){
			if(Coursesectionlogger.isInfoEnabled())	{
				Coursesectionlogger.error("error in checkSection() in CoursesectionService",e);
			}
		}
		return query;
	}
} 
