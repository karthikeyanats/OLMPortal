package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecertificate;

@Service
@Named
@Scope("prototype")
public class CoursecertificateService extends GenericEntityService<Coursecertificate, Serializable>{
	private static Logger CoursecertificateServicelogger = Logger.getLogger(CoursecertificateService.class);
	@Override
	protected Class<Coursecertificate> entityClass() {
		// TODO Auto-generated method stub
		return Coursecertificate.class;
	}
	
	public List<?> checkcertificateStatus(int courseenrollmentid){
		Criteria Query = null;
		try	{
			Query =  getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecertificate.class)
					.createAlias("this.courseenrollment", "courseenrollment",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("courseenrollment.courseenrollmentid",courseenrollmentid))	
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursecertificateid").as("coursecertificateid"))
							.add(Projections.property("this.coursecertificatestatus").as("coursecertificatestatus")));
		}
		catch(Exception e)	{
			if(CoursecertificateServicelogger.isInfoEnabled())	{
				CoursecertificateServicelogger.error("error in checkcertificateStatus() in CoursecertificateService",e);
			}
		}
		return Query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	public int updateCourseCertificateStatus(int coursecertificateid){
		int Query = 0 ;
		try	{
			Timestamp timestamp=new Timestamp(new Date().getTime());
			Query =  getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Coursecertificate set " +
					"coursecertificatestatus='A',dateofissue='"+timestamp+"' where coursecertificateid="+coursecertificateid +" ").executeUpdate();
		}
		catch(Exception e)	{
			e.printStackTrace();
			if(CoursecertificateServicelogger.isInfoEnabled())	{
				CoursecertificateServicelogger.error("error in checkcertificateStatus() in CoursecertificateService",e);
			}
		}
		return Query;
	}
	
	public List<?> getCertificateDetails(int courseenrollmentid)	{
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecertificate.class)
					.createAlias("this.courseenrollment", "courseenrollment")
					.createAlias("courseenrollment.orgperson", "enrolledorgperson")
					.createAlias("enrolledorgperson.personid", "enrolledperson")
					
					.createAlias("courseenrollment.course", "course")
					.createAlias("course.orgperson", "authororgperson")
					.createAlias("authororgperson.personid", "authorperson")
					
					.add(Restrictions.eq("courseenrollment.courseenrollmentid", courseenrollmentid))
					.setProjection(Projections.projectionList()
							
							.add(Projections.property("enrolledperson.firstName").as("userfirstname"))
							.add(Projections.property("enrolledperson.lastName").as("userlastname"))
							
							.add(Projections.property("authorperson.firstName").as("authorfirstname"))
							.add(Projections.property("authorperson.lastName").as("authorlastname"))
							
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							
							.add(Projections.property("this.coursecertificatestatus").as("coursecertificatestatus"))
							.add(Projections.property("this.dateofissue").as("dateofissue"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CoursecertificateServicelogger.isInfoEnabled()){
				CoursecertificateServicelogger.error("error in getCertificateDetails() in CoursecertificateService",e);
			}
		}

		return query;
	}

}
