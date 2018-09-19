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
import com.igrandee.product.ecloud.model.Coursecategory;

@Service
@Named
@Scope("prototype")
public class BrowseServices extends GenericEntityService<Coursecategory, Integer>{

	@Override
	protected Class<Coursecategory> entityClass() {
		// TODO Auto-generated method stub
		return Coursecategory.class;
	}
	
	public List<?> getadminBoards(String organizationid,String personid){
		try{
			return getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.createAlias("this.courses", "course")
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(personid)))
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("this.coursecategorystatus","A"))
					.add(Restrictions.eq("course.coursestatus","A"))
					.setProjection(Projections.distinct(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							.add(Projections.property("board.boardname").as("boardname"))
							.add(Projections.property("board.boardstatus").as("boardstatus")))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<?> getadminCategories(int boardid,String organizationid,String personid){
		try{
			return getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.createAlias("this.courses", "course")
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(personid)))
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("board.boardid",boardid))
					.add(Restrictions.eq("this.coursecategorystatus","A"))
					.add(Restrictions.eq("course.coursestatus","A"))
					.setProjection(Projections.distinct(Projections.projectionList()
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public List<?> getCategoryCoursesList(String coursecategoryid,String organizationid,String personid){
		try {

			Criteria query = getCurrentSession().createCriteria(Course.class)
					.createAlias("this.coursecategory", "coursecategory")
					.createAlias("this.medium", "medium")
					.createAlias("this.standardlevel", "standardlevel")
					.createAlias("this.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.in("courseenrollmentstatus", new String[]{"A","B","C"}))
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.in("organizationid.orgstatus",new Character[] {'A','M'}))
					.add(Restrictions.eq("coursecategory.coursecategoryid",Integer.parseInt(coursecategoryid)))
					.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(personid)));
			
			return query.add(Restrictions.eq("this.coursestatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("coursecategory.coursecategorydescription").as("coursecategorydescription"))
							.add(Projections.property("coursecategory.coursecategorystatus").as("coursecategorystatus"))
							
							.add(Projections.property("medium.mediumid").as("mediumid"))
							.add(Projections.property("medium.mediumname").as("mediumname"))
							
							.add(Projections.property("standardlevel.standardlevelid").as("standardlevelid"))
							.add(Projections.property("standardlevel.standardlevelname").as("standardlevelname"))
							
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
	
	

}
