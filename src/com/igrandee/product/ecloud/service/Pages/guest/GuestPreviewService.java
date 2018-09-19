package com.igrandee.product.ecloud.service.Pages.guest;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;

@Service
@Named
@Scope("prototype")
public class GuestPreviewService extends GenericEntityService<Course, Integer>{

	@Override
	protected Class<Course> entityClass() {
		// TODO Auto-generated method stub
		return Course.class;
	}

	public List<?> loadCourseInfo(String courseid){
		List<?> courseInfoList=null;
		try{
			courseInfoList= getCurrentSession().createCriteria(Course.class)

					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person")

					.createAlias("this.prices", "price")
					.createAlias("this.medium", "medium")
					.createAlias("this.coursecategory", "category")
					.createAlias("category.board", "board")

					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))
					//.add(Restrictions.eq("this.coursestatus","A"))
					.add(Restrictions.eq("medium.mediumstatus",'A'))
					.add(Restrictions.eq("category.coursecategorystatus","A"))
					.add(Restrictions.eq("board.boardstatus",'A'))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))							
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("this.coursepromevideopath").as("coursepromevideopath"))
							.add(Projections.property("this.instructoinallevel").as("instructoinallevel"))

							.add(Projections.property("price.priceid").as("priceid"))
							.add(Projections.property("price.price").as("price"))

							.add(Projections.property("medium.mediumid").as("mediumid"))
							.add(Projections.property("medium.mediumname").as("mediumname"))

							.add(Projections.property("category.coursecategoryid").as("subcategoryid"))
							.add(Projections.property("category.coursecategoryname").as("subcategoryname"))

							.add(Projections.property("board.boardname").as("categoryname"))
							.add(Projections.property("board.boardid").as("categoryid"))

							)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseInfoList;
	}

	public List<?> loadCourseCurriculum(String courseid){
		List<?> courseCurriculumList=null;
		try{
			courseCurriculumList= getCurrentSession().createCriteria(Course.class)
					.createAlias("this.coursesections", "section")
					.createAlias("section.courselectures", "lecture")
					.createAlias("lecture.lecturecontents", "content")

					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("section.coursesectionstatus","A"))
					.add(Restrictions.eq("lecture.courselecturestatus","A"))
					.add(Restrictions.eq("content.contentstatus","A"))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("section.coursesectionid").as("sectionid"))
							.add(Projections.property("section.coursesectiontitle").as("sectiontitle"))

							.add(Projections.property("lecture.courselectureid").as("lectureid"))
							.add(Projections.property("lecture.courselecturetitle").as("lecturetitle"))

							.add(Projections.property("content.contentid").as("contentid"))
							.add(Projections.property("content.contentpath").as("contentpath"))
							.add(Projections.property("content.contenttype").as("contenttype"))
							.add(Projections.property("content.duration").as("duration"))

							)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseCurriculumList;
	}

	public List<?> loadCourseReviews(String courseid){
		List<?> courseReviewsList=null;
		try{
			courseReviewsList= getCurrentSession().createCriteria(Course.class)
					.createAlias("this.courseratings", "rating")
					.createAlias("rating.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person")

					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("rating.courseratingstatus","A"))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("rating.courseratingid").as("ratingid"))
							.add(Projections.property("rating.courserating").as("rating"))
							.add(Projections.property("rating.courseratingdescription").as("ratingdescription"))
							.add(Projections.property("rating.ratingdate").as("ratingdate"))

							.add(Projections.property("person.firstName").as("raterfname"))
							.add(Projections.property("person.lastName").as("raterlname"))
							.add(Projections.property("person.personphotourl").as("raterphoto"))
							)).addOrder(Order.desc("rating.courseratingid")).
							setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseReviewsList;
	}

	public List<?> loadCourseQuestions(String courseid){
		List<?> courseQuestionsList=null;
		try{
			courseQuestionsList= getCurrentSession().createCriteria(Course.class)
					.createAlias("this.coursesections", "section")
					.createAlias("section.courselectures", "lecture")
					.createAlias("lecture.faq", "faq")
					.createAlias("faq.askedby", "askedby")
					.createAlias("askedby.personid", "person")

					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("section.coursesectionstatus","A"))
					.add(Restrictions.eq("lecture.courselecturestatus","A"))
					.add(Restrictions.eq("faq.FAQstatus","A"))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("faq.FAQid").as("questionid"))
							.add(Projections.property("faq.FAQquestion").as("question"))
							.add(Projections.property("faq.askeddate").as("askeddate"))

							.add(Projections.property("person.firstName").as("raterfname"))
							.add(Projections.property("person.lastName").as("raterlname"))
							.add(Projections.property("person.personphotourl").as("raterphoto"))

							)).addOrder(Order.desc("faq.FAQid"))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseQuestionsList;
	}

	public List<?> checkEnrollmentStatus(String courseid,String personid)	{
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)

					.createAlias("this.course", "course" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson" , JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.eq("course.courseid", Integer.parseInt(courseid)))
					.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(personid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return query;
	}
	public List<?> loadRelatedCourse(String courseid,String organizationid){
		List<?> query =null;
		try	{
			query=getCurrentSession().createSQLQuery("select (sum(b.courserating)/count(b.courseratingid)) as averagerating ,a.courseid," +
					" a.coursetitle,a.courselogo,e.price from course a left outer join courserating b on a.courseid=b.courseid and b.courseratingstatus='A' " +
					" inner join orgperson c on a.personid=c.orgpersonid inner join coursecategory d on a.coursecategoryid=d.coursecategoryid " +
					" left outer join price e on a.courseid=e.courseid and e.pricestatus='A' where a.coursestatus='A' and a.courseid not in ('"+courseid+"') and c.organizationid='"+organizationid+"' " +
					" and d.coursecategoryid in (select c.coursecategoryid from course b,coursecategory c where c.coursecategoryid=b.coursecategoryid " +
					" and c.coursecategorystatus='A' and  b.courseid='"+courseid+"') group by a.courseid order by averagerating desc limit 3;")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return query;
	}
	
}
