package com.igrandee.product.ecloud.service.Pages.user;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Courselecture;

@Service
@Named
@Scope("prototype")
public class LearnService extends GenericEntityService<Courseenrollment, Integer>{

	@Override
	protected Class<Courseenrollment> entityClass() {
		// TODO Auto-generated method stub
		return Courseenrollment.class;
	}

	public List<?> getLectureList(String courseenrollmentid){
		try{
			return getCurrentSession().createCriteria(Courseenrollment.class)
					
					.createAlias("this.course", "course")
					.createAlias("course.coursesections", "section")
					.createAlias("section.courselectures", "lecture")
					
					.add(Restrictions.eq("this.courseenrollmentid",Integer.parseInt(courseenrollmentid)))
					.add(Restrictions.eq("course.coursestatus","A"))
					.add(Restrictions.eq("section.coursesectionstatus","A"))
					.add(Restrictions.eq("lecture.courselecturestatus","A"))

					.setProjection(Projections.projectionList()

							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))

							.add(Projections.property("section.coursesectionid").as("coursesectionid"))
							.add(Projections.property("section.coursesectiontitle").as("coursesectiontitle"))
							
							.add(Projections.property("lecture.courselectureid").as("courselectureid"))
							.add(Projections.property("lecture.courselecturetitle").as("courselecturetitle"))
							

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<?> getContentList(String courselectureid,String enrollid){
		try{
			return getCurrentSession().createCriteria(Courselecture.class)
					
					.createAlias("this.lecturecontents", "content")
					.createAlias("this.learningstatistic", "learningstatistics")
					.createAlias("learningstatistics.courseenrollment", "courseenrollment")
					
					.add(Restrictions.eq("this.courselectureid",Integer.parseInt(courselectureid)))
					.add(Restrictions.eq("this.courselecturestatus","A"))
					.add(Restrictions.eq("content.contentstatus","A"))
					.add(Restrictions.eq("courseenrollment.courseenrollmentid",Integer.parseInt(enrollid)))
					
					.setProjection(Projections.projectionList()

							.add(Projections.property("this.courselectureid").as("courselectureid"))
							.add(Projections.property("this.courselecturetitle").as("courselecturetitle"))
							
							.add(Projections.property("content.contentid").as("contentid"))
							.add(Projections.property("content.contentpath").as("contentpath"))
							
							.add(Projections.property("learningstatistics.learningstatus").as("learningstatus"))
							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}