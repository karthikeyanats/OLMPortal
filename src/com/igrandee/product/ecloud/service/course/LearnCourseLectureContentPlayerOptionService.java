/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.service.course
 * LearnCourseLectureContentPlayerOptionService.java
 * Created Jul 8, 2014 10:39:12 AM
 * by balajichander_r
 */
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
import com.igrandee.product.ecloud.model.Learningstatistic;

/**
 * @author balajichander_r
 *
 */
@Service
@Named
@Scope("prototype")
public class LearnCourseLectureContentPlayerOptionService extends GenericEntityService<Learningstatistic, Integer> {

	/* (non-Javadoc)
	 * @see com.igrandee.bean.service.GenericEntityService#entityClass()
	 */
	@Override
	protected Class<Learningstatistic> entityClass() { 
		return Learningstatistic.class;
	}
	
	//Apachec log4j logger util
	private static final Logger learnCourseLectureContentPlayerOptionServiceLogger = Logger.getLogger(LearnCourseLectureContentPlayerOptionService.class);
	
	
	/**
	 * This method returns a list containing details related to learningstatistics of a particular
	 * person's particular lecture.
	 * @param LearningStatisticsId
	 * @return learningStatisticsList List<Learningstatistic>
	 */  
	public List<?> getLearningStatisticsOf(int learningStatisticsId){
		
		List<?> learningStatisticsList = null;
		Criteria query=null;
		try {
			
			
			query = getCurrentSession().createCriteria(Learningstatistic.class);
			
			query.createAlias( "this.courseenrollment" , "courseenrollment" )
				 .createAlias("this.courselecture", "courselecture")
				 .createAlias("this.coursesection", "coursesection")
				 .createAlias("this.orgperson", "orgperson");
			
			query.add(Restrictions.eq("courseenrollment.courseenrollmentstatus", "A"))
				 .add(Restrictions.eq("courselecture.courselecturestatus", "A"))
				 .add(Restrictions.eq("coursesection.coursesectionstatus", "A"))
				 .add(Restrictions.eq("orgperson.orgpersonstatus", 'A')) 
				 .add(Restrictions.eq("this.learningstatisticsid", learningStatisticsId));
			
			query.setProjection(Projections.projectionList()
					
					.add(Projections.property("this.learningstatisticsid").as("learningstatisticsid"))
					.add(Projections.property("courseenrollment.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("courselecture.courselectureid").as("courselectureid"))
					.add(Projections.property("coursesection.coursesectionid").as("coursesectionid"))
					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
					.add(Projections.property("this.starttime").as("starttime"))
					.add(Projections.property("this.endtime").as("endtime"))
					.add(Projections.property("this.timespent").as("timespent"))
					.add(Projections.property("this.learningstatus").as("learningstatus"))
					.add(Projections.property("this.markssecured").as("markssecured"))
					.add(Projections.property("this.examstatus").as("examstatus"))
					
					
					);
			
			learningStatisticsList =   query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
		} catch (Exception e) {
			
			learnCourseLectureContentPlayerOptionServiceLogger.error(e);
			
		}
		
		return learningStatisticsList;
		
		
	}
		
	
	/**
	 * 
	 * @param orgPersonId
	 * @param courseLectureId
	 * @param courseSectionId
	 * @param courseEnrollmntId
	 * @return learningStaisticList List<Learningstatistic>
	 */
	@SuppressWarnings("unchecked")
	public List<Learningstatistic> getLearningStatisticId(String orgPersonId, String courseLectureId, String courseSectionId, String courseEnrollmntId){
		List<Learningstatistic> learningStaisticList = null;
		Criteria query = null;
		try {
			
			query = getCurrentSession().createCriteria(Learningstatistic.class);
			
			query.createAlias( "this.courseenrollment" , "courseenrollment" )
			 	 .createAlias("this.courselecture", "courselecture")
			 	 .createAlias("this.coursesection", "coursesection")
			 	 .createAlias("this.orgperson", "orgperson");
			
			query.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgPersonId )))
				 .add(Restrictions.eq("courselecture.courselectureid", Integer.parseInt(courseLectureId)))
				 .add(Restrictions.eq("coursesection.coursesectionid", Integer.parseInt(courseSectionId )))
				 .add(Restrictions.eq("courseenrollment.courseenrollmentid",Integer.parseInt(courseEnrollmntId )))
 				 ;
			
			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.learningstatisticsid").as("learningstatisticsid"))
					.add(Projections.property("courseenrollment.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("courselecture.courselectureid").as("courselectureid"))
					.add(Projections.property("coursesection.coursesectionid").as("coursesectionid"))
					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
					.add(Projections.property("this.starttime").as("starttime"))
					.add(Projections.property("this.endtime").as("endtime"))
					.add(Projections.property("this.timespent").as("timespent"))
					.add(Projections.property("this.completedtime").as("completedtime"))
					.add(Projections.property("this.learningstatus").as("learningstatus"))
					.add(Projections.property("this.markssecured").as("markssecured"))
					.add(Projections.property("this.examstatus").as("examstatus")) 
					.add(Projections.property("this.meterialdownloadstatus").as("meterialdownloadstatus")) 
					);
			
			learningStaisticList =  (List<Learningstatistic>) query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			 learnCourseLectureContentPlayerOptionServiceLogger.error(e);
		}
 		return learningStaisticList;
	}
	
}
