package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.igrandee.product.ecloud.model.Learningstatistic;


@Service
@Named
@Scope("prototype")
public class LearningstatisticsService extends GenericEntityService<Learningstatistic, Serializable>
{
	private static Logger LearningstatisticsServicelogger = Logger.getLogger(LearningstatisticsService.class);
	@Override
	protected Class<Learningstatistic> entityClass() {
		// TODO Auto-generated method stub
		return Learningstatistic.class;
	}

	/**
	 * @author seenivasagan_p
	 * Service method to get the learning statistics
	 * @param courseenrollmentid
	 * @param courselectureid
	 * @return
	 */
	public List<?> loadLearningStats(int courseenrollmentid,int courselectureid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Learningstatistic.class)

					.createAlias("this.courseenrollment", "courseenrollmentobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courselecture", "courselectureobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("courseenrollmentobj.courseenrollmentid",courseenrollmentid))
					.add(Restrictions.eq("courselectureobj.courselectureid",courselectureid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.learningstatisticsid").as("learningstatisticsid"))
							.add(Projections.property("this.learningstatus").as("learningstatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in loadLearningStats() in LearningstatisticsService",e);
			}
		}
		return query;
	}

	/**
	 * @author seenivasagan_p
	 * method to get lecture list with learning status
	 * @param courseenrollmentid
	 * @return
	 */
	public List<?> loadCompletedLecturesList(int courseenrollmentid){
		List<?> query=null;
		try{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Learningstatistic.class)
					.createAlias("this.courseenrollment", "courseenrollmentobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courselecture", "courselectureobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("courseenrollmentobj.courseenrollmentid",courseenrollmentid))
					.add(Restrictions.eq("courseenrollmentobj.courseenrollmentid",courseenrollmentid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.learningstatisticsid").as("learningstatisticsid"))
							.add(Projections.property("this.learningstatus").as("learningstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in loadCompletedLecturesList() in LearningstatisticsService",e);
			}
		}
		return query;
	}

	public List<?> loadAllLecturesList(int courseenrollmentid){
		List<?> query=null;
		try{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)

					.createAlias("this.course", "courseobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courseobj.coursesections", "coursesectionobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("coursesectionobj.courselectures", "courselectureobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courselectureobj.lecturecontents", "lecturecontents",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courseenrollmentid",courseenrollmentid))
					.add(Restrictions.eq("coursesectionobj.coursesectionstatus","A"))
					.add(Restrictions.eq("courselectureobj.courselecturestatus","A"))
					.add(Restrictions.eq("lecturecontents.contentstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("courselectureobj.courselectureid").as("courselectureid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in loadAllLecturesList() in LearningstatisticsService",e);
			}
		}
		return query;
	}

	public String courseProgressUpdate(int courseenrollmentid,String courseprogress,String courseenrollmentstatus){
		String updated = null;
		try	{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Courseenrollment " +
					"set courseenrollmentstatus='"+courseenrollmentstatus+"',courseprogress="+courseprogress+""+
					"where courseenrollmentid="+courseenrollmentid +"").executeUpdate();
			updated="updated";
		}	
		catch(Exception e)	{
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in courseProgressUpdate() in LearningstatisticsService",e);	
			}	
		}
		return updated;
	}

	
	
	
	public Integer addCompletedDate(int courseenrollmentid){
		try	{
			java.util.Date date = new java.util.Date();

			String hql = "UPDATE Courseenrollment set courseenrollmentstatus = :courseenrollmentstatus " +
					", completeddate = :completeddate "+ 
					"WHERE courseenrollmentid = :courseenrollmentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("courseenrollmentstatus", "C");
			query.setParameter("completeddate", new Timestamp(date.getTime()));
			query.setParameter("courseenrollmentid", courseenrollmentid);
			 query.executeUpdate();
		}
		catch(Exception e){
			if(LearningstatisticsServicelogger.isInfoEnabled()){
				LearningstatisticsServicelogger.error("error in addCompletedDate() in LearningstatisticsService ",e);
			}
		}
		return 1;
	}
	public List<?> checkLearningStats(int courseenrollmentid,int courselectureid){
		List<?> query=null;
		try	{
			query=getCurrentSession().createSQLQuery("select learningstatisticsid from learningstatistics where " +
					"courseenrollmentid="+courseenrollmentid+" and courselectureid="+courselectureid+"")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}	
		catch(Exception e)	{
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in courseProgressUpdate() in LearningstatisticsService",e);	
			}	
		}
		return query;
	}

	public List<?> learningStatsEnrollList(int courseenrollmentid){
		List<?> query=null;
		try	{
			query=getCurrentSession().createSQLQuery("select * from learningstatistics where " +
					"courseenrollmentid="+courseenrollmentid+"")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}	
		catch(Exception e)	{
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in learningStatsEnrollList() in LearningstatisticsService",e);	
			}	
		}
		return query;
	}

	public String markCompleted(int learningstatisticsid){
		String updated = null;
		try	{  
 			String hql = "update learningstatistics set learningstatus='Completed' , completedtime=now() where learningstatisticsid="+learningstatisticsid+"";
			Query query = getCurrentSession().createSQLQuery(hql);
			query.executeUpdate();
			updated="updated";
		}	
		catch(Exception e)	{
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in markCompleted() in LearningstatisticsService",e);	
			}	
		}
		return updated;
	}

	public List<?> loadCoursePreviewData(String courseenrollmentid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)

					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.coursesections", "coursesection",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("coursesection.coursesectionstatus","A"))
					.createAlias("coursesection.courselectures", "courselecture",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("courselecture.courselecturestatus","A"))
					.createAlias("courselecture.lecturecontents", "lecturecontent",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("lecturecontent.contentstatus","A"))
					.createAlias("course.orgperson", "orgperson")
					.createAlias("course.prices", "price")
					.createAlias("orgperson.personid", "personobj")
					.add(Restrictions.eq("this.courseenrollmentid",Integer.parseInt(courseenrollmentid)))

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.coursesubtitle").as("coursesubtitle"))
							.add(Projections.property("course.coursestatus").as("coursestatus"))
							.add(Projections.property("course.courselogo").as("courselogo"))
							.add(Projections.property("course.instructoinallevel").as("instructoinallevel"))
							.add(Projections.property("course.coursedescription").as("coursedescription"))
							.add(Projections.property("course.coursegoal").as("coursegoal"))
							.add(Projections.property("course.coursepromevideopath").as("coursepromevideopath"))
							.add(Projections.property("course.intendedaudience").as("intendedaudience"))
							.add(Projections.property("course.coursecreateddate").as("coursecreateddate"))
							.add(Projections.property("coursesection.coursesectionid").as("coursesectionid"))
							.add(Projections.property("coursesection.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("coursesection.coursesectionstatus").as("coursesectionstatus"))
							.add(Projections.property("courselecture.courselectureid").as("courselectureid"))
							.add(Projections.property("courselecture.courselecturetitle").as("courselecturetitle"))
							.add(Projections.property("courselecture.courselecturestatus").as("courselecturestatus"))
							.add(Projections.property("lecturecontent.contentid").as("contentid"))
							.add(Projections.property("lecturecontent.contentpath").as("contentpath"))
							.add(Projections.property("lecturecontent.contentstatus").as("contentstatus"))
							.add(Projections.property("lecturecontent.duration").as("duration"))
							.add(Projections.property("lecturecontent.contenttype").as("contenttype"))
							.add(Projections.property("personobj.personid").as("personid"))
							.add(Projections.property("personobj.firstName").as("firstName"))
							.add(Projections.property("personobj.eduqualification").as("eduqualification"))
							.add(Projections.property("personobj.eduqualification").as("eduqualification"))
							.add(Projections.property("price.price").as("price"))
							.add(Projections.property("price.priceid").as("priceid"))


							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in loadLearningStats() in LearningstatisticsService",e);
			}
		}
		return query;
	}
	
	
	public List<?> loadCompletionReportData(String courseenrollmentid,String orgpersonid){
		List<?> query =null;
		try	{
			System.out.println("courseenrollmentid is "+courseenrollmentid);
			System.out.println("orgpersonid is "+orgpersonid);
			query=new ArrayList();
			
			List notesList = new ArrayList();
			notesList = 
					getCurrentSession().createSQLQuery("select enrolleddate,completeddate,coursetitle,coursesectiontitle,notesid ,notesdescription,DATE_FORMAT(e.createddate, '%m-%d-%Y-%T') as takenat," +
					"courselecturetitle,'note' as differentiator from courseenrollment a,course b,coursesection c,courselecture d,notes e " +
					"where a.courseenrollmentid='"+courseenrollmentid+"' and a.courseid=b.courseid and b.courseid=c.courseid and " +
					"d.coursesectionid=c.coursesectionid and e.courselectureid=d.courselectureid " +
					"and e.notesstatus='R' and e.notetype='N' and e.orgpersonid='"+orgpersonid+"'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			List scratchList = new ArrayList();
			scratchList = 
					getCurrentSession().createSQLQuery("select enrolleddate,completeddate,coursetitle,coursesectiontitle,notesid ," +
							"notesdescription,DATE_FORMAT(e.createddate, '%m-%d-%Y-%T') as takenat," +
					"courselecturetitle,'scratch' as differentiator,e.notespath from courseenrollment a,course b,coursesection c,courselecture d,notes e " +
					"where a.courseenrollmentid='"+courseenrollmentid+"' and a.courseid=b.courseid and b.courseid=c.courseid and " +
					"d.coursesectionid=c.coursesectionid and e.courselectureid=d.courselectureid " +
					"and e.notesstatus='R' and e.notetype='S' and e.orgpersonid='"+orgpersonid+"'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			List faqList = new ArrayList();
			faqList = 
					getCurrentSession().createSQLQuery("select enrolleddate,completeddate,coursetitle,coursesectiontitle,faqid,faqquestion,courselecturetitle," +
					"d.courselectureid,DATE_FORMAT(e.askeddate, '%m-%d-%Y-%T') as takenat,'question' as differentiator from courseenrollment a,course b," +
					"coursesection c,courselecture d,faq e where a.courseenrollmentid='"+courseenrollmentid+"' and " +
					"a.courseid=b.courseid and b.courseid=c.courseid and d.coursesectionid=c.coursesectionid " +
					"and e.courselectureid=d.courselectureid and e.askedby='"+orgpersonid+"'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			List faqanswersList = new ArrayList();
			faqanswersList = 
					getCurrentSession().createSQLQuery("select enrolleddate,completeddate,coursetitle,coursesectiontitle,e.FAQid,faqquestion,faqanswer,faqanswerid," +
					"d.courselectureid,courselecturetitle, DATE_FORMAT(answereddate, '%m-%d-%Y-%T') as takenat,'questionans' as differentiator from courseenrollment a,course b," +
					"coursesection c,courselecture d,faq e ,faqanswer f where a.courseenrollmentid='"+courseenrollmentid+"' " +
					"and a.courseid=b.courseid and b.courseid=c.courseid and d.coursesectionid=c.coursesectionid " +
					"and e.courselectureid=d.courselectureid and e.FAQid=f.FAQid and f.answeredby='"+orgpersonid+"'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			List learningStatusList = new ArrayList();
			learningStatusList = 
					getCurrentSession().createSQLQuery("select enrolleddate,completeddate,coursetitle,coursesectiontitle,e.courseenrollmentid,learningstatisticsid,timespent," +
					"d.courselectureid,courselecturetitle,DATE_FORMAT(completedtime, '%m-%d-%Y-%T') as takenat,'lecturecompleted' as differentiator from courseenrollment a,course b," +
					"coursesection c,courselecture d,learningstatistics e where a.courseenrollmentid='"+courseenrollmentid+"' " +
					"and a.courseid=b.courseid and b.courseid=c.courseid and d.coursesectionid=c.coursesectionid " +
					"and e.courselectureid=d.courselectureid and e.courseenrollmentid=a.courseenrollmentid and " +
					"learningstatus='Completed'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			List lectureReviewList = new ArrayList();
			lectureReviewList = 
					getCurrentSession().createSQLQuery("select reviewtype,rank,enrolleddate,coursetitle," +
					"coursesectiontitle,d.courselectureid,courselecturetitle, DATE_FORMAT(e.dateofcreation, '%m-%d-%Y-%T') as takenat," +
					"'lecturereview' as differentiator from courseenrollment a,course b,coursesection c," +
					"courselecture d,lecturereview e,reviewtype f where a.courseenrollmentid='"+courseenrollmentid+"' " +
					"and a.courseid=b.courseid and b.courseid=c.courseid and d.coursesectionid=c.coursesectionid " +
					"and e.lectureid=d.courselectureid and e.reviewtypeid=f.id and a.personid=e.userid ")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			List courseReviewList = new ArrayList();
			courseReviewList = 
					getCurrentSession().createSQLQuery("select courserating,courseratingdescription," +
							"enrolleddate,coursetitle,DATE_FORMAT(e.ratingdate, '%m-%d-%Y-%T') as takenat," +
							" 'coursereview' as differentiator from courseenrollment a,course b, " +
							"courserating e where a.courseenrollmentid='"+courseenrollmentid+"' and " +
							"a.courseid=b.courseid and a.personid=e.orgpersonid and " +
							"e.courseid=b.courseid ")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
 			
			query.addAll(notesList);
			query.addAll(scratchList);			
			query.addAll(faqList);
			query.addAll(faqanswersList);
			query.addAll(learningStatusList);
			query.addAll(lectureReviewList);
			query.addAll(courseReviewList);
			
		}
		catch(Exception e){
			if(LearningstatisticsServicelogger.isInfoEnabled())	{
				LearningstatisticsServicelogger.error("error in loadCompletionReportData() in LearningstatisticsService ",e);
			}
		}
		return query;
	}
}