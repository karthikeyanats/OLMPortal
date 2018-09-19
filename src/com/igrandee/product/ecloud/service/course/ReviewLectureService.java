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
import com.igrandee.product.ecloud.model.LectureReview;

@Named
@Service
@Scope("prototype")
public class ReviewLectureService extends GenericEntityService<LectureReview, Serializable> {
	private static Logger ReviewLectureServicelogger = Logger.getLogger(LectureReview.class);

	@Override
	protected Class<LectureReview> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<?> mylectratingList(int lectid,int orgpersonid){
		List<?> lectureRatingsList = null;
		
		try{
			lectureRatingsList=getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.LectureReview.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("this.courselecture", "courselecture")					
					.createAlias("this.reviewtype", "reviewtype")
					.add(Restrictions.eq("courselecture.courselectureid",lectid))
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.rank").as("rank"))
							.add(Projections.property("reviewtype.reviewtype").as("reviewtype"))							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	
		}catch(Exception e){
			if(ReviewLectureServicelogger.isInfoEnabled()){
				ReviewLectureServicelogger.error("error in deletePersonStatus() in ReviewLectureService",e);
			}
		}
		return lectureRatingsList;
}
	
	public List<?> lectureratingList(int lectid){
		List<?> lectureRatingsList = null;
		
		try{
			lectureRatingsList=getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.LectureReview.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "ratedperson")	
					.createAlias("this.courselecture", "courselecture")					
					.createAlias("this.reviewtype", "reviewtype")
					.add(Restrictions.eq("courselecture.courselectureid",lectid))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.rank").as("rank"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("ratedperson.firstName").as("raterfirstname"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	
		}catch(Exception e){
			if(ReviewLectureServicelogger.isInfoEnabled()){
				ReviewLectureServicelogger.error("error in lectureratingList() in ReviewLectureService",e);
			}
		}
		return lectureRatingsList;
}
	
	public List<?> loadLectureReviewsList(int lectureid){
		List<?> lectureReviewsList=null;
		try{
			lectureReviewsList=
					getCurrentSession().createSQLQuery("select (sum(a.rank)/count(a.lecturereviewid)) as " +
					"averagerevieworiginal,ROUND((sum(a.rank)/count(a.lecturereviewid)),0) as averagereviewrounded," +
					"a.userid,a.dateofcreation as rateddate,a.lectureid,c.firstName,c.lastName,c.personphotourl as photo from lecturereview a,orgperson b,person c " +
					"where a.lectureid='"+lectureid+"' and a.userid=b.orgpersonid and b.personid = c.personid " +
					"and b.orgpersonstatus='A' and c.personstatus='A' group by a.userid")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lectureReviewsList;
	}


}