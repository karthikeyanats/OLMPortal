package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courselecture;


@Service
@Named
@Scope("prototype")
public class CourseLectureViewService extends GenericEntityService<Courselecture, Serializable>{
	private static Logger CourseLectureViewServicelogger = Logger.getLogger(CourseLectureViewService.class);

	@Override
	protected Class<Courselecture> entityClass(){
		return Courselecture.class;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the content for individual lecture
	 * @param courselectureid
	 * @return
	 */
	public List<?> loadLectureContent(int courselectureid)	{
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.lecturecontents", "lecturecontents",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courselectureid",courselectureid))
					.add(Restrictions.eq("lecturecontents.contentstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courselectureid").as("courselectureid"))
							.add(Projections.property("this.courselecturetitle").as("courselecturetitle"))
							.add(Projections.property("lecturecontents.contentpath").as("contentpath")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseLectureViewServicelogger.isInfoEnabled())	{
				CourseLectureViewServicelogger.error("error in loadLectureContent() in CourseLectureViewService",e);
			}
		}
		return query;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the notes for individual lecture
	 * @param courselectureid
	 * @return
	 */
	public List<?> loadLectureReview(int courselectureid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.notes", "notes",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courselectureid",courselectureid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courselectureid").as("courselectureid"))
							.add(Projections.property("this.courselecturetitle").as("courselecturetitle"))
							.add(Projections.property("notes.notesid").as("notesid"))
							.add(Projections.property("notes.notesdescription").as("notesdescription"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseLectureViewServicelogger.isInfoEnabled())	{
				CourseLectureViewServicelogger.error("error in loadLectureContent() in CourseLectureViewService",e);
			}
		}
		return query;
	}
}