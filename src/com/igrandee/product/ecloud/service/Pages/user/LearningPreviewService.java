package com.igrandee.product.ecloud.service.Pages.user;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Faqanswer;

@Service
@Named
@Scope("prototype")
public class LearningPreviewService extends GenericEntityService<Faqanswer, Integer>{

	public List<?> loadCourseInfo(String courseenrollmentid){

		List<?> courseInfoList=null;
		try{
			courseInfoList= getCurrentSession().createCriteria(Courseenrollment.class)

					.createAlias("this.course", "course")
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person")

					.createAlias("course.prices", "price")
					.createAlias("course.medium", "medium")
					.createAlias("course.coursecategory", "category")
					.createAlias("category.board", "board")

					.add(Restrictions.eq("this.courseenrollmentid",Integer.parseInt(courseenrollmentid)))
					.add(Restrictions.eq("course.coursestatus","A"))
					.add(Restrictions.eq("medium.mediumstatus",'A'))
					.add(Restrictions.eq("category.coursecategorystatus","A"))
					.add(Restrictions.eq("board.boardstatus",'A'))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.property("this.courseprogress").as("courseprogress"))

							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.coursedescription").as("coursedescription"))							
							.add(Projections.property("course.courselogo").as("courselogo"))
							.add(Projections.property("course.coursepromevideopath").as("coursepromevideopath"))
							.add(Projections.property("course.instructoinallevel").as("instructoinallevel"))

							.add(Projections.property("medium.mediumid").as("mediumid"))
							.add(Projections.property("medium.mediumname").as("mediumname"))

							.add(Projections.property("category.coursecategoryid").as("subcategoryid"))
							.add(Projections.property("category.coursecategoryname").as("subcategoryname"))

							.add(Projections.property("board.boardname").as("categoryname"))
							.add(Projections.property("board.boardid").as("categoryid"))
							
							.add(Projections.property("price.price").as("price"))
							.add(Projections.property("price.priceid").as("priceid"))
							
							)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseInfoList;
	}

	public List<?> loadCourseCurriculum(String courseenrollmentid){
		List<?> courseCurriculumList=null;
		try{
			courseCurriculumList= getCurrentSession().createCriteria(Courseenrollment.class)

					.createAlias("this.course", "course")
					.createAlias("course.coursesections", "section")
					.createAlias("section.courselectures", "lecture")
					.createAlias("lecture.lecturecontents", "content")

					.add(Restrictions.eq("this.courseenrollmentid",Integer.parseInt(courseenrollmentid)))
					.add(Restrictions.eq("course.coursestatus","A"))
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

	public List<?> loadCourseReviews(String courseenrollmentid){
		List<?> courseReviewsList=null;
		try{
			courseReviewsList= getCurrentSession().createCriteria(Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("course.courseratings", "rating")
					.createAlias("rating.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person")

					.add(Restrictions.eq("this.courseenrollmentid",Integer.parseInt(courseenrollmentid)))
					.add(Restrictions.eq("rating.courseratingstatus","A"))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("rating.courseratingid").as("ratingid"))
							.add(Projections.property("rating.courserating").as("rating"))
							.add(Projections.property("rating.courseratingdescription").as("ratingdescription"))
							.add(Projections.property("rating.ratingdate").as("ratingdate"))

							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("person.firstName").as("raterfname"))
							.add(Projections.property("person.lastName").as("raterlname"))
							.add(Projections.property("person.personphotourl").as("raterphoto"))
							)).addOrder(Order.desc("rating.courseratingid"))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseReviewsList;
	}

	public List<?> loadCourseQuestions(String courseenrollmentid){
		List<?> courseQuestionsList=null;
		try{
			courseQuestionsList= getCurrentSession().createCriteria(Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("course.coursesections", "section")
					.createAlias("section.courselectures", "lecture")
					.createAlias("lecture.faq", "faq")
					.createAlias("faq.askedby", "askedby")
					.createAlias("askedby.personid", "person")

					.add(Restrictions.eq("this.courseenrollmentid",Integer.parseInt(courseenrollmentid)))
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

	public List<?> loadCourseQuestionAnswers(String questionid){
		List<?> courseQuestionsList=null;
		try{
			courseQuestionsList= getCurrentSession().createCriteria(Faqanswer.class)
					.createAlias("this.faq", "faq")
					.createAlias("this.answeredby", "answeredby")
					.createAlias("answeredby.personid", "person")

					.add(Restrictions.eq("faq.FAQid",Integer.parseInt(questionid)))
					.add(Restrictions.eq("faq.FAQstatus","A"))
					.add(Restrictions.eq("this.faqanswerstatus","A"))

					.setProjection(Projections.distinct(Projections.projectionList()

							.add(Projections.property("this.faqanswerid").as("answerid"))
							.add(Projections.property("this.faqanswer").as("answer"))
							.add(Projections.property("this.answereddate").as("answereddate"))

							.add(Projections.property("person.firstName").as("answeredfname"))
							.add(Projections.property("person.lastName").as("answeredlname"))
							.add(Projections.property("person.personphotourl").as("answeredphoto"))

							)).addOrder(Order.desc("this.faqanswerid"))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseQuestionsList;
	}


	public List<?> learningStatuses(int courseenrollmentid){
		List<?> query=null;
		try	{
			query=getCurrentSession().createSQLQuery("select * from learningstatistics where " +
					"courseenrollmentid="+courseenrollmentid+"")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}	
		catch(Exception e)	{
			e.printStackTrace();
		}
		return query;
	}


	public List<?> loadQuizStatus(int studentid, String lectureids){
		List<?>  quizStatuses =  null;
		try {
			quizStatuses = getCurrentSession().createSQLQuery("select a.courselectureid,sum(case when c.answerstate = 'C' then 1 " +
					"else 0 end) as correct,sum(case when c.answerstate = 'W' then 1 else 0 end) as wrong," +
					"sum(case when c.answerstate IS NULL then 1 else 0 end) as skip from questionconnector a," +
					"question b left outer join evaluation c on c.questionid=b.id and c.orgpersonid='"+studentid+"'  " +
					"left outer join questionanswers d on c.answerid=d.id and d.status='A' where a.courselectureid " +
					"in ("+lectureids+") and a.question_id =b.id and b.status='A' group by a.courselectureid  " +
					"order by a.id asc").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return quizStatuses;
	}

	@Override
	protected Class<Faqanswer> entityClass() {
		// TODO Auto-generated method stub
		return Faqanswer.class;
	}
}
