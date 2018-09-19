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
import com.igrandee.product.ecloud.model.Contentapproval;

@Service
@Named
@Scope("prototype")
public class ContentApproveService extends GenericEntityService<Contentapproval, Integer>{

	private static Logger ContentApproveServicelogger = Logger.getLogger(ContentApproveService.class);

	@Override
	protected Class<Contentapproval> entityClass() {
		// TODO Auto-generated method stub
		return Contentapproval.class;
	}

	public List<?> getWaitingContentApproval(String courseid){
		try{
			return getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)

					.createAlias("this.orgperson", "orgpersonobj")
					.createAlias("orgpersonobj.personid","personobj")
					.createAlias("personobj.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgpersonobj.organizationid", "organizationobj")
					.createAlias("this.coursesections", "sectionobj")
					.createAlias("sectionobj.courselectures", "lectureobj")
					.createAlias("lectureobj.lecturecontents", "contentobj")		
					.createAlias("contentobj.contentapproval", "contentapproval",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)	
					
					.add(Restrictions.eq("lectureobj.courselecturestatus","A"))
					.add(Restrictions.eq("sectionobj.coursesectionstatus","A"))
					.add(Restrictions.eq("contentobj.contentstatus","A"))
					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("sectionobj.coursesectionid").as("coursesectionid"))
							.add(Projections.property("sectionobj.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("lectureobj.courselectureid").as("courselectureid"))
							.add(Projections.property("lectureobj.courselecturetitle").as("courselecturetitle"))
							.add(Projections.property("contentobj.contentid").as("contentid"))
							.add(Projections.property("contentobj.contentpath").as("contentpath"))
							.add(Projections.property("email.userid").as("authoremailaddress"))
							.add(Projections.property("personobj.firstName").as("firstName"))
							.add(Projections.property("personobj.lastName").as("lastName"))
							.add(Projections.property("orgpersonobj.orgpersonid").as("authororgpersonid"))
							.add(Projections.property("contentapproval.approvestatus").as("approvestatus"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(ContentApproveServicelogger.isInfoEnabled())	{
				ContentApproveServicelogger.error("error in getWaitingContentApproval() in ContentApproveService ",e);
			}
			return null;
		}		
	}

	public List<?> contentStatuses(String courselectureid){
		try{

			return getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.lecturecontents", "contentobj")
					.createAlias("contentobj.contentapproval", "contentapproval",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courselectureid",Integer.parseInt(courselectureid)))
					.add(Restrictions.eq("contentobj.contentstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courselectureid").as("courselectureid"))
							.add(Projections.property("contentapproval.contentapprovalid").as("contentapprovalid"))
							.add(Projections.property("contentapproval.approvestatus").as("approvestatus"))
							.add(Projections.property("contentapproval.dateofapproval").as("dateofapproval"))
							.add(Projections.property("contentapproval.description").as("description"))
							.add(Projections.property("contentobj.contentpath").as("contentpath"))
							.add(Projections.property("contentobj.contentid").as("contentid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(ContentApproveServicelogger.isInfoEnabled())	{
				ContentApproveServicelogger.error("error in contentStatuses() in ContentApproveService ",e);
			}
			return null;
		}		
	}

	public List<?> contentRootPath(String contentid){
		try{

			return getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Lecturecontent.class)
					.createAlias("this.courselecture", "courselecture")
					.createAlias("courselecture.coursesection", "coursesection")
					.createAlias("coursesection.course", "course")
					.createAlias("course.coursecategory", "coursecategory")

					.add(Restrictions.eq("this.contentid",Integer.parseInt(contentid)))

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.contentpath").as("contentpath"))
							.add(Projections.property("courselecture.courselecturetitle").as("courselecturetitle"))
							.add(Projections.property("coursesection.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(ContentApproveServicelogger.isInfoEnabled())	{
				ContentApproveServicelogger.error("error in contentStatuses() in ContentApproveService ",e);
			}
			return null;
		}		
	}

	public String updateCourseStatus(String courseid,String coursestatus){
		try{
			String hql = "UPDATE Course set coursestatus = :coursestatus "  + 
					"WHERE courseid = :courseid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("coursestatus", coursestatus);
			query.setParameter("courseid", Integer.parseInt(courseid));
			Integer result = query.executeUpdate();
			if(result.SIZE>0){
				return "OK";
			}
			else{
				return "NOTOK";
			}
		}
		catch(Exception e){
			if(ContentApproveServicelogger.isInfoEnabled())	{
				ContentApproveServicelogger.error("error in updateCourseStatus() in ContentApproveService ",e);
			}
			return null;
		}		
	}


}
