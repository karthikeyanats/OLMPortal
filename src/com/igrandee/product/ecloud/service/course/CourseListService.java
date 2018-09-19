package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Learningstatistic;
import com.igrandee.product.ecloud.model.Metainfodescription;
import com.igrandee.product.ecloud.model.sat.Questionconnector;


/**
 * @author seenivasagan_p
 * Course List Service
 */
@Service
@Named
@Scope("prototype")
public class CourseListService extends GenericEntityService<Course, Serializable>{

	private static Logger CourseListServicelogger = Logger.getLogger(CourseListService.class);

	@Override
	protected Class<Course> entityClass() {
		return Course.class;
	}

	public List<?> loadCourseLogo(int courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.add(Restrictions.eq("this.courseid",courseid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))
							.add(Projections.property("this.courselogo").as("courselogo")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in loadCourseLogo() in CourseListService",e);
			}
		}
		return query;
	}
	/**
	 * @author seenivasagan_p
	 * @param coursecategorystatus
	 * @return all valid categories
	 */
	public List<?> getCourseDetailsforPayment(int courseid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)

					.createAlias("this.prices", "priceobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courseid",courseid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("priceobj.priceid").as("priceid"))
							.add(Projections.property("priceobj.price").as("price"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getCourseDetailsforPayment() in CourseListService",e);
			}
		}
		return query;
	}

	/**
	 * @author seenivasagan_p
	 * To Get courses for particular category
	 * @param coursecategoryid
	 * @return courses respective to the passed category id
	 */
	public List<?> getAllValidCategoryWiseCourses(int coursecategoryid,String personid,String organizationid){
		List<?> categoryCourses=null;
		Criteria query =null;
		List<Character> status = new ArrayList<Character>();
		status.add('A'); 
		status.add('M');
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursecategory", "coursecategory",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("courseenrollmentstatus", "A"))
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.in("organizationid.orgstatus",status))
					.add(Restrictions.eq("coursecategory.coursecategoryid",coursecategoryid))
					.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)));
			if(!personid.equals("null")){ 
				query.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(personid)));	
			}

			categoryCourses=query.add(Restrictions.eq("this.coursestatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("coursecategory.coursecategorydescription").as("coursecategorydescription"))
							.add(Projections.property("coursecategory.coursecategorystatus").as("coursecategorystatus"))
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("courseratings.courseratingid").as("courseratingid"))
							.add(Projections.property("courseratings.courserating").as("courserating"))
							.add(Projections.property("courseenrollments.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("courseenrollments.courseenrollmentstatus").as("courseenrollmentstatus"))
							.add(Projections.property("prices.priceid").as("priceid"))
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.property("author.firstName").as("firstName"))
							.add(Projections.property("author.lastName").as("lastName")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getAllValidCategoryWiseCourses() in CourseListService",e);
			}
		}
		return categoryCourses;
	}

	public List<?> getAllCategoryPersonCourses(String orgPersonId, String courseCategoryId, String organizationId){
		List<?> returnList = null;
		Criteria query = null;
		try {

			query = getCurrentSession().createCriteria(Courseenrollment.class);  
			query.createAlias("this.course", "course", JoinType.LEFT_OUTER_JOIN) 
			.createAlias("course.coursecategory", "coursecategory", JoinType.LEFT_OUTER_JOIN) 
			.createAlias("this.orgperson", "userOrgPerson", JoinType.LEFT_OUTER_JOIN)  
			.createAlias("userOrgPerson.organizationid", "organizationid" ) ;

			query.add(Restrictions.eq("course.coursestatus", "A"))
			.add(Restrictions.eq("coursecategory.coursecategorystatus", "A")) 
			.add(Restrictions.eq("userOrgPerson.orgpersonstatus", 'A'));

			query.add(Restrictions.in("organizationid.orgstatus", new Character[] {'A','M'} ))
			.add(Restrictions.eq("coursecategory.coursecategoryid", Integer.parseInt(courseCategoryId )))
			.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(organizationId )))
			.add(Restrictions.eq("userOrgPerson.orgpersonid", Integer.parseInt(orgPersonId) ));


			ProjectionList projectionList = Projections.projectionList(); 

			projectionList.add(Projections.property("course.courseid").as("courseid")) 
			.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid")) 
			.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"));


			query.setProjection(projectionList);

			returnList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();


		} catch (Exception e) {
			if(CourseListServicelogger.isInfoEnabled()){ 
				CourseListServicelogger.error("error in getAllCategoryPersonCourses() in CourseListService",e);
			}
		}
		return returnList;
	}

	public List<?> getOtherCourses(String coursestatus,int personid,long orgid){
		List<?> otherCourses=null;
		Criteria query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")	
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.eq("orgperson.orgpersonid",personid))
					.add(Restrictions.eq("organizationid.organizationid",orgid));
			if(!coursestatus.equals("All")){
				query.add(Restrictions.eq("this.coursestatus",coursestatus));	
			}			
			else{
				query.add(Restrictions.ne("this.coursestatus","X"));
			}
			otherCourses=query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("this.coursestatus").as("coursestatus"))
					.add(Projections.property("organizationid.orgname").as("orgname"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("author.firstName").as("firstName"))
					.add(Projections.property("author.lastName").as("lastName"))
					.add(Projections.property("prices.price").as("price")))							
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getOtherCourses() in CourseListService",e);
			}
		}
		return otherCourses;
	}

	public List<?> getAllPublishedCourses(String coursestatus,Long organizationid,String orgpersonid){
		List<?> otherCourses=null;
		Criteria query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")	
					.createAlias("orgperson.personid", "author")
					.createAlias("author.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("organizationid.organizationid",organizationid))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)));
			if(!coursestatus.equals("All")){
				query.add(Restrictions.eq("this.coursestatus",coursestatus));	
			}			
			otherCourses=query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("this.coursestatus").as("coursestatus"))
					.add(Projections.property("organizationid.orgname").as("orgname"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("author.firstName").as("firstName"))
					.add(Projections.property("author.lastName").as("lastName"))
					.add(Projections.property("email.userid").as("authoremailid"))
					.add(Projections.property("prices.price").as("price")))							
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getOtherCourses() in CourseListService",e);
			}
		}
		return otherCourses;
	}


	public List<?> getPublishCourses(String coursestatus,String personid,String organizationid){
		List<?> publishCourses=null;
		Criteria query =null;
		List status = new ArrayList();
		status.add('A'); 
		status.add('M');

		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("orgperson.personid", "author")	
					.createAlias("author.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.in("organizationid.orgstatus",status))
					.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)));
			if(!personid.equals("null")){ 
				query.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(personid)));	
			}

			publishCourses=query.add(Restrictions.eq("this.coursestatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))
							.add(Projections.property("organizationid.orgname").as("orgname"))
							.add(Projections.property("author.firstName").as("firstName"))
							.add(Projections.property("author.lastName").as("lastName"))
							.add(Projections.property("email.userid").as("authoremailid"))
							.add(Projections.property("prices.priceid").as("priceid"))
							.add(Projections.property("prices.price").as("price")))							
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			//e.printStackTrace();
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in  () in CourseListService",e);
			}
		}
		return publishCourses;

	}


	public List<?> organizationCourses(Long orgpersonid){
		List<?> otherCourses=null;
		Criteria query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")	
					.createAlias("orgperson.personid", "author")		
					.add(Restrictions.eq("this.coursestatus","A"))
					.add(Restrictions.eq("author.personid",orgpersonid));
			otherCourses= query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("this.coursestatus").as("coursestatus"))
					.add(Projections.property("organizationid.orgname").as("orgname"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price")))							
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in organizationCourses() in CourseListService",e);
			}
		}
		return otherCourses;
	}


	/**
	 * @author seenivasagan_p
	 * Method to get the individual's courses
	 * @param enrollmentstatus
	 * @param orgpersonid
	 * @return
	 */
	public List<?> getIndividualCourseList(int orgpersonid,String authorid){
		List<?> individualCourses=null;
		Criteria query =null;
		try	{

			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.coursecategory", "coursecategory")
					.createAlias("coursecategory.board", "board")

					.createAlias("course.orgperson", "author")
					.createAlias("author.personid","authorperson")

					.createAlias("course.prices", "prices")
					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					.add(Restrictions.or(Restrictions.eq("this.courseenrollmentstatus","A"), Restrictions.eq("this.courseenrollmentstatus","B")))
					;
			if(!authorid.equals("null")){
				query.add(Restrictions.eq("author.orgpersonid",Integer.parseInt(authorid)));	
			}

			individualCourses=	query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
					.add(Projections.property("this.courseprogress").as("courseprogress"))							
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.property("course.coursedescription").as("coursedescription"))
					.add(Projections.property("course.courselogo").as("courselogo"))
					.add(Projections.property("course.coursestatus").as("coursestatus"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("authorperson.firstName").as("firstName"))
					.add(Projections.property("authorperson.lastName").as("lastName"))

					.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
					.add(Projections.property("board.boardname").as("boardname"))

					).addOrder(Order.desc("courseenrollmentid")).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourses() in CourseListService",e);
			}
		}
		return individualCourses;
	}

	public List<?> getIndividualCourses(int orgpersonid,String authorid){
		List<?> individualCourses=null;
		Criteria query =null;
		try	{

			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "author")
					.createAlias("author.personid","authorperson")
					.createAlias("course.prices", "prices")
					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid));
			if(!authorid.equals("null")){
				query.add(Restrictions.eq("author.orgpersonid",Integer.parseInt(authorid)));	
			}

			individualCourses=	query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
					.add(Projections.property("this.courseprogress").as("courseprogress"))							
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.property("course.coursedescription").as("coursedescription"))
					.add(Projections.property("course.courselogo").as("courselogo"))
					.add(Projections.property("course.coursestatus").as("coursestatus"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("authorperson.firstName").as("firstName"))
					.add(Projections.property("authorperson.lastName").as("lastName"))

					)			

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourses() in CourseListService",e);
			}
		}
		return individualCourses;
	}

	/**
	 * @author mahalingam_a
	 * Method to get the User Earning courses	 
	 * @param orgpersonid
	 * @return
	 */
	public List<?> getMyEarningCourses(int orgpersonid){
		List<?> earninglistCourses=null;
		Criteria query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.royalty.Royaltypayment.class)
					.createAlias("this.courseenrollmentid", "courseenrollments",JoinType.RIGHT_OUTER_JOIN)
					.createAlias("courseenrollments.course", "course")
					.createAlias("course.prices", "price")
					.createAlias("course.orgperson", "orgperson")		
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					.add(Restrictions.in("courseenrollments.courseenrollmentstatus", new String[] {"A","B","C"} ));

			earninglistCourses=	query.setProjection(Projections.projectionList()													
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.count("courseenrollments.courseenrollmentid").as("usercount"))
					.add(Projections.property("price.price").as("price"))
					.add(Projections.sum("price.price").as("totalprice"))
					.add(Projections.sum("authorroyaltyamount").as("authorroyaltyamount"))
					.add(Projections.groupProperty("course.courseid")))							
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourses() in CourseListService",e);
			}
		}

		return earninglistCourses;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get Content and other related information about a particular course
	 * @param courseid
	 * @param coursestatus
	 * @return Content and other related information about a particular course
	 */
	public List<?> getCourseContent(int courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "personobj")
					.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.privacies", "privacies",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.coursecategory", "coursecategory",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.coursesections", "coursesections",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("coursesections.coursesectionstatus","A"))
					.createAlias("coursesections.courselectures", "courselectures",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("courselectures.courselecturestatus","A"))
					.createAlias("courselectures.lecturecontents", "lecturecontents",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("lecturecontents.contentstatus","A"))

					.createAlias("courselectures.notes", "notes",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courselectures.faq", "faq",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("faq.faqanswer", "faqanswerobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courseid",courseid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("coursecategory.coursecategorystatus").as("coursecategorystatus"))
							.add(Projections.property("courseenrollments.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.coursesubtitle").as("coursesubtitle"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("this.instructoinallevel").as("instructoinallevel"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))
							.add(Projections.property("this.coursegoal").as("coursegoal"))
							.add(Projections.property("this.coursepromevideopath").as("coursepromevideopath"))
							.add(Projections.property("this.intendedaudience").as("intendedaudience"))
							.add(Projections.property("this.coursecreateddate").as("coursecreateddate"))
							.add(Projections.property("coursesections.coursesectionid").as("coursesectionid"))
							.add(Projections.property("coursesections.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("coursesections.coursesectionstatus").as("coursesectionstatus"))
							.add(Projections.property("courselectures.courselectureid").as("courselectureid"))
							.add(Projections.property("courselectures.courselecturetitle").as("courselecturetitle"))
							.add(Projections.property("courselectures.courselecturestatus").as("courselecturestatus"))
							.add(Projections.property("lecturecontents.contentid").as("contentid"))
							.add(Projections.property("lecturecontents.contentpath").as("contentpath"))
							.add(Projections.property("lecturecontents.contentstatus").as("contentstatus"))
							.add(Projections.property("notes.notesid").as("notesid"))
							.add(Projections.property("notes.notesdescription").as("notesdescription"))
							.add(Projections.property("notes.notesstatus").as("notesstatus"))
							.add(Projections.property("faq.FAQid").as("FAQid"))
							.add(Projections.property("faq.FAQquestion").as("FAQquestion"))
							.add(Projections.property("faq.FAQstatus").as("FAQstatus"))
							.add(Projections.property("faqanswerobj.faqanswerid").as("faqanswerid"))
							.add(Projections.property("faqanswerobj.faqanswer").as("faqanswer"))
							.add(Projections.property("faqanswerobj.faqanswerstatus").as("faqanswerstatus"))
							.add(Projections.property("personobj.firstName").as("firstName"))

							.add(Projections.property("prices.priceid").as("priceid"))
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.property("prices.pricestatus").as("pricestatus"))
							.add(Projections.property("privacies.privacyid").as("privacyid"))
							.add(Projections.property("privacies.privacyid").as("privacyid"))
							.add(Projections.property("privacies.privacydescription").as("privacydescription"))
							.add(Projections.property("privacies.privacystatus").as("privacystatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in getCourseContent() in CourseListService",e);
			}
		}
		return query;
	}

	public List<?> getCoursePreviewData(int courseid){

		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("course", "course",JoinType.RIGHT_OUTER_JOIN,Restrictions.eq("this.courseenrollmentstatus","A"))
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.personid", "personobj")  
					.createAlias("course.medium", "medium",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.standardlevel", "standard",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.privacies", "privacies",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.coursecategory", "coursecategory",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN) 
					.createAlias("coursecategory.board", "board",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.coursesections", "coursesections",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("coursesections.coursesectionstatus","A"))
					.createAlias("coursesections.courselectures", "courselectures",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("courselectures.courselecturestatus","A"))
					.createAlias("courselectures.lecturecontents", "lecturecontents",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.in("lecturecontents.contentstatus", new String[] {"A","W"}))
							.createAlias("courselectures.metainfo", "metainfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
									Restrictions.eq("metainfo.metainfostatus", "A"))
									.createAlias("courselectures.notes", "notes",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
									.createAlias("courselectures.faq", "faq",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
									.createAlias("faq.faqanswer", "faqanswerobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

									.add(Restrictions.eq("course.courseid",courseid))
									/*.add(Restrictions.eq("this.courseenrollmentstatus","A"))*/
									.setProjection(Projections.projectionList()
											.add(Projections.property("courseenrollmentid").as("courseenrollmentid"))
											.add(Projections.property("board.boardid").as("boardid"))
											.add(Projections.property("board.boardname").as("boardname"))
											.add(Projections.property("medium.mediumid").as("mediumid"))
											.add(Projections.property("medium.mediumname").as("mediumname"))
											.add(Projections.property("standard.standardlevelid").as("standardid"))
											.add(Projections.property("standard.standardlevelname").as("standardname"))
											.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
											.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
											.add(Projections.property("coursecategory.coursecategorystatus").as("coursecategorystatus"))
											.add(Projections.property("course.courseid").as("courseid"))
											.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
											.add(Projections.property("course.coursetitle").as("coursetitle"))
											.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
											.add(Projections.property("course.coursesubtitle").as("coursesubtitle"))
											.add(Projections.property("course.coursestatus").as("coursestatus"))
											.add(Projections.property("course.courselogo").as("courselogo"))
											.add(Projections.property("course.instructoinallevel").as("instructoinallevel"))
											.add(Projections.property("course.coursedescription").as("coursedescription"))
											.add(Projections.property("course.coursegoal").as("coursegoal"))
											.add(Projections.property("course.coursepromevideopath").as("coursepromevideopath"))
											.add(Projections.property("course.intendedaudience").as("intendedaudience"))
											.add(Projections.property("course.coursecreateddate").as("coursecreateddate"))
											.add(Projections.property("course.coursepromevideopath").as("coursepromevideopath"))							
											.add(Projections.property("coursesections.coursesectionid").as("coursesectionid"))
											.add(Projections.property("coursesections.coursesectiontitle").as("coursesectiontitle"))
											.add(Projections.property("coursesections.coursesectionstatus").as("coursesectionstatus"))
											.add(Projections.property("courselectures.courselectureid").as("courselectureid"))
											.add(Projections.property("courselectures.courselecturetitle").as("courselecturetitle"))
											.add(Projections.property("courselectures.seriallno").as("seriallno"))
											.add(Projections.property("courselectures.courselecturestatus").as("serail"))
											.add(Projections.property("lecturecontents.contentid").as("contentid"))
											.add(Projections.property("lecturecontents.contentpath").as("contentpath"))
											.add(Projections.property("lecturecontents.contentstatus").as("contentstatus"))
											.add(Projections.property("lecturecontents.duration").as("duration"))
											.add(Projections.property("notes.notesid").as("notesid"))
											.add(Projections.property("notes.notesdescription").as("notesdescription"))
											.add(Projections.property("notes.notesstatus").as("notesstatus"))
											.add(Projections.property("faq.FAQid").as("FAQid"))
											.add(Projections.property("faq.FAQquestion").as("FAQquestion"))
											.add(Projections.property("faq.FAQstatus").as("FAQstatus"))
											.add(Projections.property("faqanswerobj.faqanswerid").as("faqanswerid"))
											.add(Projections.property("faqanswerobj.faqanswer").as("faqanswer"))
											.add(Projections.property("faqanswerobj.faqanswerstatus").as("faqanswerstatus"))
											.add(Projections.property("personobj.firstName").as("firstName"))
											.add(Projections.property("personobj.personid").as("personid"))
											.add(Projections.property("prices.priceid").as("priceid"))
											.add(Projections.property("prices.price").as("price"))
											.add(Projections.property("prices.pricestatus").as("pricestatus"))
											.add(Projections.property("privacies.privacyid").as("privacyid"))
											.add(Projections.property("privacies.privacyname").as("privacyname"))
											.add(Projections.property("privacies.privacystatus").as("privacystatus"))
											.add(Projections.property("personobj.eduqualification").as("eduqualification"))
											.add(Projections.property("metainfo.metainfoid").as("metainfoid"))
											).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in getCourseContent() in CourseListService",e);
			}
		}
		return query;
	}


	/**/


	/*
	public List<?> getCourseListData(int personid){

 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "personobj")  
 					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)


  					.add(Restrictions.eq("this.personid",personid))
					.setProjection(Projections.projectionList()

 							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.coursesubtitle").as("coursesubtitle"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("this.instructoinallevel").as("instructoinallevel"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))
							.add(Projections.property("this.coursegoal").as("coursegoal"))

							.add(Projections.property("personobj.firstName").as("firstName"))
							.add(Projections.property("personobj.lastName").as("lastName"))
							.add(Projections.property("personobj.eduqualification").as("eduqualification"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in getCourseListData() in CourseListService",e);
			}
		}
		return query;
	}










	 */


















	/**
	 * @author selvapriya_m
	 * Method to check the coursestatus 
	 * @param courseid
	 * @return
	 */
	public List<?> checkcoursestatus(int courseid){		
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursesections", "coursesection")
					.add(Restrictions.eq("this.courseid",courseid))
					.add(Restrictions.eq("this.coursestatus","A"))		
					.add(Restrictions.eq("coursesection.coursesectionstatus","A"))		
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))		
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("coursesection.coursesectionid").as("coursesectionid"))
							.add(Projections.property("coursesection.coursesectiontitle").as("coursesectiontitle"))												
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in checkcoursestatus() in CourseListService",e);
			}
		}
		return query;
	}

	public List<?> checkcourselecturestatus(int coursesectionid){		
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursesection.class)
					.createAlias("this.courselectures","courselecture")
					.add(Restrictions.eq("this.coursesectionid",coursesectionid))
					.add(Restrictions.eq("this.coursesectionstatus","A"))		
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursesectionid").as("coursesectionid"))
							.add(Projections.property("this.coursesectiontitle").as("coursesectiontitle"))												
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in checkcoursestatus() in CourseListService",e);
			} 
		}
		return query;
	}

	public Integer checksectionstatus(int coursesectionid,String coursesectionstatus){
		Integer Coursesection = null;
		try	{
			Coursesection=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Coursesection set coursesectionstatus='"+coursesectionstatus+"' where coursesectionid='"+coursesectionid+"'").executeUpdate();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in checksectionstatus() in CourseListService",e);
			}
		}
		return Coursesection;
	}

	public Integer checklecturestatus(int courselectureid,String courselecturestatus){
		Integer Courselecture = null;
		try	{
			Courselecture=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Courselecture set courselecturestatus='"+courselecturestatus+"' where courselectureid='"+courselectureid+"'").executeUpdate();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in checksectionstatus() in CourseListService",e);
			}
		}
		return Courselecture;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the individual's courses
	 * @param enrollmentstatus
	 * @param orgpersonid
	 * @return
	 */
	public List<?> getIndividualCourseForUser(int courseenrollmentid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.coursesections", "coursesections",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("coursesections.courselectures", "courselectures",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courselectures.lecturecontents", "lecturecontents",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("coursesections.coursesectionstatus","A"))
					.add(Restrictions.eq("courselectures.courselecturestatus","A"))
					.add(Restrictions.eq("lecturecontents.contentstatus","A"))
					.add(Restrictions.eq("this.courseenrollmentid",courseenrollmentid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("coursesections.coursesectionid").as("coursesectionid"))
							.add(Projections.property("coursesections.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("courselectures.courselectureid").as("courselectureid"))
							.add(Projections.property("courselectures.courselecturetitle").as("courselecturetitle")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourseForUser() in CourseListService",e);
			}
		}
		return query;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get Individual's Learning Status for a particular lecture
	 * @param courseenrollmentid
	 * @param courselectureid
	 * @return
	 */
	public List<?> getIndividualLearningStatus(int courseenrollmentid,int courselectureid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Learningstatistic.class)
					.createAlias("this.courselecture", "courselectureobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courseenrollment", "courseenrollmentobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("courselectureobj.courselectureid",courselectureid))
					.add(Restrictions.eq("courseenrollmentobj.courseenrollmentid",courseenrollmentid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("courseenrollmentobj.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("courselectureobj.courselectureid").as("courselectureid"))
							.add(Projections.property("this.learningstatus").as("learningstatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualLearningStatus() in CourseListService",e);
			}
		}
		return query;
	}

	/**
	 * @author seenivasagan_p
	 * Method to publish the course
	 * @param courseid
	 * @return success value for update
	 */
	public Integer publishCourse(int courseid){
		Integer i = null;
		try	{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Course set coursestatus='P' " +
					"where courseid="+courseid +" ").executeUpdate();
			i=1;
		}	
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in publishCourse() in CourseListService",e);	
			}	
		}
		return i;
	}

	public Integer approveCourse(int courseid){
		Integer i = null;
		try	{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Course set coursestatus='A' " +
					"where courseid="+courseid +" ").executeUpdate();
			i=1;
		}	
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in approveCourse() in CourseListService ",e);	
			}	
		}
		return i;
	}

	public List<?> getEnrollmentCourse(int courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.add(Restrictions.eq("course.courseid",courseid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourseForUser() in CourseListService ",e);
			}
		}
		return query;
	}

	/**
	 * @author williambenjamin_g
	 * Method to assign the course for users
	 * @param courseid
	 * @return success list
	 */
	public List<?> getPersonDetailsToAssignFn(String courseid,String orgid){
		List<?> personlist = null;
		try{
			List<?> query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("this.courseenrollmentstatus","A"))
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(orgid))) 

					.setProjection(Projections.projectionList()
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			ArrayList<Integer> arry = new ArrayList<Integer>();
			if(query.size()!=0) {
				for(int i=0;i<query.size();i++){
					Map queryMap = (Map)query.get(i); 		 
					String orgpersonid = ""+queryMap.get("orgpersonid");
					arry.add(Integer.parseInt(orgpersonid)); 
				}
			} 

			Criteria personCreteria = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Orgperson.class)
					//.createAlias("this.orgperson", "orgperson")
					.createAlias("this.organizationid", "organization")
					.createAlias("this.personid", "personid")  
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personallocations", "personallocations", JoinType.LEFT_OUTER_JOIN) 
					.createAlias("personallocations.role", "role")
					.add(Restrictions.ne("role.rolename", "admin")).add(Restrictions.eq("organization.organizationid",Long.parseLong(orgid)))
					; 	

			if(arry.isEmpty()){

			} else {
				personCreteria.add(Restrictions.not(Restrictions.in("this.orgpersonid", arry)));
			}

			personlist=	personCreteria.setProjection(Projections.projectionList()
					.add(Projections.property("personid.firstName").as("firstName"))
					.add(Projections.property("personid.lastName").as("lastName"))
					.add(Projections.property("personid.middleName").as("middleName"))
					.add(Projections.property("personid.personphotourl").as("personphotourl"))
					.add(Projections.property("email.userid").as("emailaddress"))
					.add(Projections.property("this.orgpersonid").as("personid")) 
					.add(Projections.groupProperty("this.orgpersonid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 

		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getPersonDetailsToAssignFn() in CourseListService ",e);
			}
		}

		return personlist;
	}	 

	public List<?> chkentoenroll(int personid,int courseid){
		List<?> chkresult=null;
		try{
			chkresult=getCurrentSession().createCriteria(Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.add(Restrictions.eq("course.courseid", courseid))
					.add(Restrictions.eq("orgperson.orgpersonid",personid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid")))										
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in chkentoenroll() in CourseListService ",e);
			}
		}
		return chkresult;
	}
	public List<?> chkentoenrollMent(int personid,int courseid){
		List<?> chkresult=null;
		try{

			chkresult=getCurrentSession().createCriteria(Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.add(Restrictions.eq("course.courseid", courseid))
					.add(Restrictions.eq("orgperson.orgpersonid",personid))
					.add(Restrictions.in("this.courseenrollmentstatus", new String[] {"A","B","C","P"} ))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus")))										
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in chkentoenrollMent() in CourseListService ",e);
			}
		}
		return chkresult;
	}
	/**
	 * @author raja_r
	 * @return List
	 */
	public List<?> loadTopCourses(){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("course.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  					
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.personid", "personid")

					.createAlias("course.prices", "prices")
					.add(Restrictions.eq("course.coursestatus","A")) 
					.add(Restrictions.eq("courseenrollmentstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.count("course.courseid").as("count")) 
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.courselogo").as("courselogo"))
							.add(Projections.property("course.coursedescription").as("coursedescription"))
							.add(Projections.property("personid.firstName").as("createdby"))
							.add(Projections.property("personid.personphotourl").as("authorphoto"))
							.add(Projections.property("prices.priceid").as("priceid"))
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.property("courseratings.courserating").as("courserating"))
							.add(Projections.groupProperty("course.courseid"))) .addOrder(Order.desc("count"))  
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setMaxResults(8).list(); 

		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in loadTopCourses() in CourseListService ",e);
			}
		}
		System.out.println("List>>>>>>"+query);
		
		return query;
	}


	public List<?> getCourseCount(String courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					/*.createAlias("course.orgperson", "orgperson")
  					.createAlias("orgperson.personid", "personid")
  					.createAlias("course.prices", "prices")*/ 
					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("course.coursestatus","A"))

					.setProjection(Projections.projectionList()
							.add(Projections.count("course.courseid").as("count")) 
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.courselogo").as("courselogo"))
							.add(Projections.property("course.coursedescription").as("coursedescription"))
							.add(Projections.property("personid.firstName").as("createdby"))
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							.add(Projections.property("prices.priceid").as("priceid"))
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.groupProperty("course.courseid"))) .addOrder(Order.desc("count"))  
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setMaxResults(12).list(); 

		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in loadTopCourses() in CourseListService",e);
			}
		}
		return query;
	}

	public List<?> getDraftAndTrashCourseCount(){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.add(Restrictions.or(Restrictions.eq("this.coursestatus","D"),Restrictions.eq("this.coursestatus","T")))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursestatus").as("coursestatus")))   
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getDraftCourseCount() in CourseListService",e);
			}
		}
		return query;
	}

	public List checkCourse(String coursetitle, String coursecategoryid) {
		List query = null;
		try{
			query = getCurrentSession().createCriteria(Course.class)
					.createAlias("this.coursecategory", "coursecategory")
					.add(Restrictions.eq("coursecategory.coursecategoryid", Integer.parseInt(coursecategoryid)))
					.add(Restrictions.eq("this.coursetitle", coursetitle))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in checkCourse() in CourseListService",e);
			}
		}

		return query;
	}
	public List<?> getCourseSpentTime(String courseid,String orgpersonid) {
		List<?> query = null;
		try{



			query = getCurrentSession().createCriteria(Learningstatistic.class)
					.createAlias("this.coursesection", "coursesection")
					.createAlias("coursesection.course", "course")
					.createAlias("this.orgperson", "orgperson")					
					.add(Restrictions.eq("course.courseid", Integer.parseInt(courseid)))
					.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.timespent").as("timespent")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getCourseSpentTime() in CourseListService",e);
			}
		}
		return query;
	}
	/**
	 * @author mahalingam_a
	 * Method to get the individual's courses with User wise Time Spent
	 * @param enrollmentstatus
	 * @param orgpersonid
	 * @return
	 */
	public List<?> getIndividualCourseswithTimeSpent(int orgpersonid,String authorid){
		List<?> individualCourses=null;
		Criteria query =null;
		try	{



			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.learningstatistic", "learningstatistic",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "author")
					.createAlias("course.prices", "prices")
					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid));
			/*		if(!authorid.equals("null")){
						query.add(Restrictions.eq("author.orgpersonid",Integer.parseInt(authorid)));	
					}*/

			individualCourses=	query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
					.add(Projections.property("this.courseprogress").as("courseprogress"))							
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.property("course.coursedescription").as("coursedescription"))
					.add(Projections.property("course.courselogo").as("courselogo"))
					.add(Projections.property("course.coursestatus").as("coursestatus"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("learningstatistic.timespent").as("timespent"))
					)			

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourses() in CourseListService",e);
			}
		}
		return individualCourses;
	}

	/**
	 * @author sridhar_m
	 * method to individual course with out time spend
	 */
	public List<?> getIndividualCourseswithoutTime(int orgpersonid,String authorid){
		List<?> individualCourses=null;
		Criteria query =null;
		try	{


			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.createAlias("course.orgperson", "author")
					.createAlias("course.prices", "prices")
					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid));


			individualCourses=	query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
					.add(Projections.property("this.courseprogress").as("courseprogress"))							
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.property("course.coursedescription").as("coursedescription"))
					.add(Projections.property("course.courselogo").as("courselogo"))
					.add(Projections.property("course.coursestatus").as("coursestatus"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					)			

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getIndividualCourses() in CourseListService",e);
			}
		}
		return individualCourses;
	}

	public String setVideoDuration(String contentid, String duration){

		try	{
			String hql = "UPDATE Lecturecontent set duration = :duration "  + 
					"WHERE contentid = :contentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("duration", duration);
			query.setParameter("contentid", Integer.parseInt(contentid));
			Integer result = query.executeUpdate();
			return result.toString();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in setVideoDuration() in CourseListService",e);
			}
			return "0";
		}

	}

	public List<?> getMetaInfo(int lectid){
		List<?> metadetaillist=null;
		Criteria query=null;
		try{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Metainfodescription.class)
					.createAlias("this.metainfo", "metainfo")	
					.createAlias("metainfo.courselecture", "courselecture")		

					.add(Restrictions.eq("courselecture.courselectureid",lectid))
					.add(Restrictions.eq("this.status","A"));
			metadetaillist=	query.setProjection(Projections.projectionList()
					.add(Projections.property("courselecture.courselectureid").as("lectid"))
					.add(Projections.property("metainfo.keywords").as("keywords"))
					.add(Projections.property("metainfo.summary").as("summary"))
					.add(Projections.property("metainfo.weblinks").as("weblinks"))							
					.add(Projections.property("metainfo.reference").as("reference"))
					.add(Projections.property("metainfo.metainfoid").as("metainfoid"))


					.add(Projections.property("metainfo.diagrams").as("diagrams"))
					.add(Projections.property("metainfo.maps").as("maps"))
					.add(Projections.property("metainfo.tables").as("tables"))
					.add(Projections.property("metainfo.boxitem").as("boxitem"))
					.add(Projections.property("metainfo.projectideas").as("projectideas"))
					.add(Projections.property("metainfo.activity").as("activity"))
					.add(Projections.property("this.description").as("description"))
					.add(Projections.property("this.id").as("metainfodescriptionid"))
					.add(Projections.property("this.originalfilename").as("originalfilename"))
					.add(Projections.property("this.descriptiontype").as("descriptiontype"))
					)			

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled())	{
				CourseListServicelogger.error("error in getMetaInfo() in CourseListService",e);
			}
		}
		return metadetaillist;
	}

	public List<?> getLectureMetaDescription(int courselectureid,String descriptiontype){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createCriteria(Metainfodescription.class)
					
					.createAlias("this.metainfo", "metainfo")
					.createAlias("metainfo.courselecture", "courselecture")
					
					.add(Restrictions.eq("courselecture.courselectureid",courselectureid))
					.add(Restrictions.eq("this.descriptiontype",descriptiontype))
					.add(Restrictions.eq("this.status","A"))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("descid"))
							.add(Projections.property("this.description").as("description"))
							.add(Projections.property("this.descriptiontype").as("descriptiontype"))							
							.add(Projections.property("this.originalfilename").as("originalfilename"))
							.add(Projections.property("this.descriptiontype").as("descriptiontype")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public List<?> getLectureApprovalStatus(int courselectureid){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createCriteria(Courselecture.class)
					
					.createAlias("this.lecturecontents", "lecturecontents")
					.createAlias("lecturecontents.contentapproval", "contentapproval")
					
					.add(Restrictions.eq("this.courselectureid",courselectureid))
					.add(Restrictions.eq("lecturecontents.contentstatus","A"))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("contentapproval.approvestatus").as("approvestatus"))
							.add(Projections.property("contentapproval.contentapprovalid").as("contentapprovalid"))
							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public List<?> getLectureQuiz(int lectureid){
		List<?> questionType = null;
		Criteria query = null;
		try {
			Order order = Order.asc("question.id");
			query = getCurrentSession().createCriteria(Questionconnector.class)
					.createAlias("question", "question")
					.createAlias("courselecture", "courselecture")
					.createAlias("question.qustionanswers", "questionanswers")				
					.createAlias("question.questionmetas", "questionmeta")
					.createAlias("question.questiontype", "questiontype")
					.createAlias("question.difficultyfactor", "difficultyfactor")
					
					.add(Restrictions.eq("courselecture.courselectureid", lectureid))
					.add(Restrictions.eq("questionanswers.status",'A'))
					.add(Restrictions.eq("question.status",'A'))
					.addOrder(order);

			query.setProjection(Projections.projectionList()
					.add(Projections.property("question.id").as("questionid"))
					.add(Projections.property("question.mark").as("mark"))
					.add(Projections.property("question.question").as("question"))
					.add(Projections.property("questiontype.id").as("id"))
					.add(Projections.property("difficultyfactor.id").as("qstdiffiid"))
					.add(Projections.property("questionanswers.answers").as("answers"))
					.add(Projections.property("questionanswers.id").as("answerid"))
					.add(Projections.property("questionanswers.answerDescription").as("answerDescription"))
					.add(Projections.property("questionanswers.correctans").as("correctans"))
					.add(Projections.property("questionmeta.noofoccurance").as("noofoccurance"))
					.add(Projections.property("questionmeta.lifetime").as("lifetime")));
			questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}catch (Exception e) {
			e.printStackTrace();
		}

		return questionType;
	}
	
	public List<?> getCourseApprovalStatus(int courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					
					.createAlias("this.coursesections", "coursesections",
							org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.eq("coursesections.coursesectionstatus","A"))
					.createAlias("coursesections.courselectures", "courselectures",
							org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.eq("courselectures.courselecturestatus","A"))
					.createAlias("courselectures.lecturecontents", "lecturecontents",
							org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.eq("lecturecontents.contentstatus","A"))
							
							.createAlias("lecturecontents.contentapproval", "contentapproval",
							org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.eq("this.courseid",courseid))
					.setProjection(Projections.projectionList()
							
							//.add(Projections.property("this.courseid").as("courseid"))
							//.add(Projections.property("this.coursetitle").as("coursetitle"))
							//.add(Projections.property("coursesections.coursesectionid").as("coursesectionid"))
							.add(Projections.property("lecturecontents.contentid").as("contentid"))
							.add(Projections.property("contentapproval.contentapprovalid").as("contentapprovalid"))
							.add(Projections.property("contentapproval.approvestatus").as("approvestatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseListServicelogger.isInfoEnabled()){
				CourseListServicelogger.error("error in getCourseContent() in CourseListService",e);
			}
			
		}
		return query;
	}
	
	public boolean isDuplicateCourse(String coursetitle,String orgpersonid){
		Integer courseid=	 (Integer) getCurrentSession().createCriteria(Course.class)
				.createAlias("orgperson", "orgperson")
				.add(Restrictions.eq("coursetitle", coursetitle))
				.add(Restrictions.ne("coursestatus", "X"))
				.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
				.setProjection(Projections.property("courseid"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
		if(courseid!=null){
			return true;
		}
		else{
			return false;
		}		
	}

}