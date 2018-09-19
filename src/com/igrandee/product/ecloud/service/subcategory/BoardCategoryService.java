package com.igrandee.product.ecloud.service.subcategory;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Coursecategory;

@Service
@Scope("prototype")
@Named
public class BoardCategoryService extends GenericEntityService<Coursecategory, Integer>{

	@Override
	protected Class<Coursecategory> entityClass() {
		// TODO Auto-generated method stub
		return Coursecategory.class;
	}

	public List<?> getBoardCategoriesList(String status,String boardid){
		try {
			return getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.add(Restrictions.eq("board.boardid", Integer.parseInt(boardid)))
					.add(Restrictions.eq("this.coursecategorystatus", status))
					.setProjection(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("this.coursecategorydescription").as("coursecategorydescription"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<?> getCategoryCoursesList(String coursecategoryid,String organizationid){
		try {

			Criteria query = getCurrentSession().createCriteria(Course.class)
					.createAlias("this.coursecategory", "coursecategory")
					.createAlias("this.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.in("courseenrollmentstatus", new String[] {"A","B","C"}))
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.in("organizationid.orgstatus",new Character[] {'A','M'}))
					.add(Restrictions.eq("coursecategory.coursecategoryid",Integer.parseInt(coursecategoryid)))
					.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)));
			
			return query.add(Restrictions.eq("this.coursestatus","A"))
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
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.property("author.firstName").as("firstName"))
							.add(Projections.property("author.lastName").as("lastName"))
							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<?> getCategoryWiseAuthors(int coursecategoryid,String organizationid){
		Criteria query = getCurrentSession().createCriteria(Coursecategory.class)
				.createAlias("courses", "courses")
				.createAlias("courses.orgperson", "orgperson")
				.createAlias("orgperson.organizationid", "organization")
				.createAlias("orgperson.personid", "person")
				.createAlias("person.contactinfo", "contactinfo")
				.createAlias("contactinfo.emails", "emails")
				.createAlias("person.workexperiences", "workexperiences",JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.in("organization.orgstatus",new Character[] {'A','M'}))
				.add(Restrictions.eq("courses.coursestatus", "A"))
				.add(Restrictions.eq("coursecategoryid",coursecategoryid))
				.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)));
		          
			return	query.setProjection(Projections.projectionList()
						.add(Projections.distinct(Projections.property("person.personid")))
						.add(Projections.property("person.firstName").as("firstname"))
						.add(Projections.property("person.lastName").as("lastname"))
						.add(Projections.property("person.personphotourl").as("personphotourl"))						
						.add(Projections.property("emails.userid").as("email"))
						.add(Projections.property("workexperiences.designation").as("designation"))
						.add(Projections.property("workexperiences.organizationname").as("organizationname"))
						.add(Projections.property("workexperiences.award").as("award")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<?> getCourseCategoryavailability(String boardid,String coursecategoryname){
		try {
			return getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.add(Restrictions.eq("board.boardid", Integer.parseInt(boardid)))
					.add(Restrictions.in("this.coursecategorystatus",new String[] {"A","D"}))
					.add(Restrictions.like("this.coursecategoryname",coursecategoryname ).ignoreCase())
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isCoursesPublishedorDrafted(int categoryid){
		List<?> courseCategoryList=getCurrentSession().createCriteria(Coursecategory.class)
						.createAlias("courses", "courses")
						.add(Restrictions.in("courses.coursestatus", new String[]{"A","P","T"}))
						.add(Restrictions.eq("coursecategoryid", categoryid))
						.setProjection(Projections.projectionList()
								.add(Projections.distinct(Projections.property("coursecategoryid")))).list();
		if(courseCategoryList.size()!=0){
			return true;
		}
		else{
			return false;
		}
		
	}
	
}