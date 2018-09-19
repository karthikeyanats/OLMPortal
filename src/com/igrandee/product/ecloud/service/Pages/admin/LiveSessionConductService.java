package com.igrandee.product.ecloud.service.Pages.admin;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Livesession;
import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;

@Service
@Named
@Scope("prototype")
public class LiveSessionConductService extends GenericEntityService<Livesessionenrollment, Integer>{

	@Override
	protected Class<Livesessionenrollment> entityClass() {
		// TODO Auto-generated method stub
		return Livesessionenrollment.class;
	}

	public List<?> loadParticipantsList(String livesessionid){
		List<?> participantsList = null;
		try{
			participantsList = getCurrentSession().createCriteria(Livesessionenrollment.class)

					.createAlias("this.livesession", "livesession")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person")

					.add(Restrictions.eq("livesession.livesessionid",Integer.parseInt(livesessionid)))
					.add(Restrictions.eq("this.status","A"))

					.setProjection(Projections.projectionList()
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("person.firstName").as("firstName"))
							.add(Projections.property("person.lastName").as("lastName"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return participantsList;
	}

	public List<?> loadSessionDescription(String livesessionid){
		List<?> sessionDescriptionList = null;
		try{
			sessionDescriptionList = getCurrentSession().createCriteria(Livesession.class)

					.createAlias("this.courseid", "course")

					.add(Restrictions.eq("this.livesessionid",Integer.parseInt(livesessionid)))

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.livesessionid").as("livesessionid"))
							.add(Projections.property("this.description").as("description"))
							.add(Projections.property("this.starttime").as("starttime"))
							.add(Projections.property("this.endtime").as("endtime"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sessionDescriptionList;
	}


	public List<?> getLectureContentList(String courseid){
		try{
			return getCurrentSession().createCriteria(Course.class)

					.createAlias("this.coursesections", "section")
					.createAlias("section.courselectures", "lecture")
					.createAlias("lecture.lecturecontents", "content")

					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("section.coursesectionstatus","A"))
					.add(Restrictions.eq("lecture.courselecturestatus","A"))
					.add(Restrictions.eq("content.contentstatus","A"))

					.setProjection(Projections.projectionList()
							.add(Projections.property("section.coursesectionid").as("coursesectionid"))
							.add(Projections.property("section.coursesectiontitle").as("coursesectiontitle"))

							.add(Projections.property("lecture.courselectureid").as("courselectureid"))
							.add(Projections.property("lecture.courselecturetitle").as("courselecturetitle"))

							.add(Projections.property("content.contentid").as("contentid"))
							.add(Projections.property("content.contentpath").as("contentpath"))							

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}