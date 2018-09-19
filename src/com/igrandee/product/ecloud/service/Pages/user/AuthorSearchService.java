package com.igrandee.product.ecloud.service.Pages.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.core.person.model.Workexperience;
import com.igrandee.product.ecloud.model.Courserating;
import com.igrandee.product.ecloud.model.Orgperson;

@Service
@Named
@Scope("prototype")
public class AuthorSearchService  extends GenericEntityService<Orgperson, Integer>{

	@Override
	protected Class<Orgperson> entityClass() {
		// TODO Auto-generated method stub
		return Orgperson.class;
	}

	public List<?> loadAuthorsList(String orgid){
		try{
			List<?> authorsList = getCurrentSession().createSQLQuery("select distinct e.personid from personallocation a," +
					"role b,orgperson c,course d,person e where a.roleid=b.roleid and b.rolestatus='A' " +
					"and a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and c.orgpersonid=d.personid " +
					"and d.coursestatus='A' and b.rolename in('user','admin') and c.personid=e.personid and e.personstatus='A' " +
					"and c.organizationid='"+orgid+"'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
					.list();

			ArrayList<Long> arry = new ArrayList<Long>();
			if(authorsList.size()!=0) {
				for(int i=0;i<authorsList.size();i++){
					Map queryMap = (Map)authorsList.get(i); 		 
					String orgpersonid = ""+queryMap.get("personid");
					arry.add(Long.parseLong((orgpersonid))); 
				}
			} 
			return getUserProfile(arry);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<?> getUserProfile(List<Long> personidArray){
		List<?> personlist = null;
		try{
			personlist = getCurrentSession().createCriteria(Workexperience.class)
					.createAlias("person", "person",JoinType.RIGHT_OUTER_JOIN)
					.createAlias("contactinfo", "contactinfo")
					.createAlias("contactinfo.urls", "urls")
					.createAlias("person.contactinfo", "contactdetail")
					.createAlias("contactdetail.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactdetail.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.in("person.personid", personidArray))
					.setProjection(Projections.projectionList()
							.add(Projections.property("workexperienceid").as("workexperienceid"))
							.add(Projections.property("designation").as("designation"))
							.add(Projections.property("organizationname").as("organization"))
							.add(Projections.property("award").as("award"))
							.add(Projections.property("natureofwork").as("aboutauthor"))
							.add(Projections.property("urls.url").as("url"))
							.add(Projections.property("person.personphotourl").as("personphoto"))
							.add(Projections.property("person.firstName").as("firstName"))
							.add(Projections.property("person.lastName").as("lastName"))
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.property("phone.number").as("number"))
							.add(Projections.property("email.userid").as("userid"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return personlist;
	}

	public List<?> getParticularUserProfile(Long personid){
		List<?> personlist = null;
		try{
			personlist = getCurrentSession().createCriteria(Workexperience.class)
					.createAlias("person", "person",JoinType.RIGHT_OUTER_JOIN)
					.createAlias("contactinfo", "contactinfo")
					.createAlias("contactinfo.urls", "urls")
					.createAlias("person.contactinfo", "contactdetail")
					.createAlias("contactdetail.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactdetail.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("person.personid", personid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("workexperienceid").as("workexperienceid"))
							.add(Projections.property("designation").as("designation"))
							.add(Projections.property("organizationname").as("organization"))
							.add(Projections.property("award").as("award"))
							.add(Projections.property("natureofwork").as("aboutauthor"))
							.add(Projections.property("urls.url").as("url"))
							.add(Projections.property("person.personphotourl").as("personphoto"))
							.add(Projections.property("person.firstName").as("firstName"))
							.add(Projections.property("person.lastName").as("lastName"))
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.property("phone.number").as("number"))
							.add(Projections.property("email.userid").as("userid"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return personlist;
	}

	public List<?> getUserRatings(String orgid){

		List<?> ratingList = null;
		try{
			ratingList = getCurrentSession().
						createSQLQuery("select (sum(a.courserating)/count(a.courseratingid)) as averagerating ," +
								"b.courseid, c.personid from courserating a,course b,orgperson c " +
								"where a.courseid=b.courseid and a.courseratingstatus='A' " +
								"and b.coursestatus='A' and b.personid=c.orgpersonid and c.orgpersonstatus='A' " +
							"and c.personid in(select distinct e.personid from personallocation a," +
							"role b,orgperson c,course d,person e where a.roleid=b.roleid and b.rolestatus='A' " +
							"and a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and c.orgpersonid=d.personid " +
							"and d.coursestatus='A' and b.rolename in ('user','admin') and c.personid=e.personid and e.personstatus='A' " +
							"and c.organizationid='"+orgid+"') group by b.courseid")
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return ratingList;
	}

	public List<?> getAuthorCourses(String orgid){
		List<?> ratingList = null;
		try{
			ratingList = getCurrentSession().
					createSQLQuery("select person3_.personid as personid, " +
							"this_.courseid as courseid,this_.coursetitle as coursetitle, orgperson1_.orgpersonid as orgpersonid from course " +
							"this_ inner join orgperson orgperson1_ on this_.personid=orgperson1_.orgpersonid  " +
							"inner join person person3_ on orgperson1_.personid=person3_.personid where person3_.personid " +
							"in (select distinct e.personid from personallocation a,role b,orgperson c,course d,person e " +
							"where a.roleid=b.roleid and b.rolestatus='A' and a.orgpersonid=c.orgpersonid and " +
							"c.orgpersonstatus='A' and c.orgpersonid=d.personid and d.coursestatus='A' " +
							"and b.rolename in ('user','admin') and c.personid=e.personid and e.personstatus='A' and " +
							"c.organizationid='"+orgid+"') and this_.coursestatus='A'")
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return ratingList;
	}


	public List<?> getIndividualAuthorCourses(String orgpersonid){
		List<?> ratingList = null;
		try{
			ratingList = getCurrentSession().
					createSQLQuery(" select (sum(c.courserating)/count(c.courseratingid)) as rating," +
							"count(b.courseenrollmentid) as enrolledusers, d.price,a.courseid, a.coursetitle," +
							"a.courselogo,a.coursedescription from course a left outer join courseenrollment b " +
							"on a.courseid=b.courseid and b.courseenrollmentstatus in ('A','B','C') " +
							"left outer join courserating c on b.courseid=c.courseid,price d where " +
							"a.personid='"+orgpersonid+"' and a.coursestatus='A' and d.pricestatus='A' " +
							"and a.courseid=d.courseid group by a.courseid")
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return ratingList;
	}

	public List<?> getAuthorRatings(String authororgpersonid){
		List<?> personlist = null;
		try{
			personlist = getCurrentSession().createCriteria(Courserating.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "raterorgperson")
					.createAlias("raterorgperson.personid", "raterperson")

					.createAlias("course.orgperson", "authororgperson")
					
					.add(Restrictions.eq("authororgperson.orgpersonid", Integer.parseInt(authororgpersonid)))
					.add(Restrictions.eq("course.coursestatus", "A"))
					
					.setProjection(Projections.projectionList()
							
							.add(Projections.property("this.courseratingid").as("courseratingid"))
							.add(Projections.property("this.courserating").as("courserating"))
							.add(Projections.property("this.courseratingdescription").as("courseratingdescription"))
							.add(Projections.property("this.ratingdate").as("ratingdate"))
							
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))

							.add(Projections.property("raterperson.firstName").as("firstName"))
							.add(Projections.property("raterperson.lastName").as("lastName"))
							.add(Projections.property("raterperson.personphotourl").as("raterphoto"))
							
							).addOrder(Order.desc("courseratingid")).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return personlist;
	}

	public List<?> getRatingCourse(String orgpersonid,String organizationid){
		List<?> ratingList = null;
		try{
			ratingList = getCurrentSession().
					createSQLQuery("select (sum(e.courserating)/count(e.courseratingid)) as averagerating,a.courseid as courseid, " +
							" a.courselogo as courselogo, a.coursetitle as coursetitle, c.price as price " +
							" from course a left outer join courserating e on e.courseid=a.courseid and e.courseratingstatus='A' " +
							" inner join orgperson b on a.personid=b.orgpersonid inner join coursecategory d " +
							" on a.coursecategoryid=d.coursecategoryid and d.coursecategorystatus='A' inner join price c on c.courseid=a.courseid " +
							" where d.coursecategoryid in (select c.coursecategoryid from courseenrollment a,course b," +
							" coursecategory c where a.coursestatus='A' and a.courseid=b.courseid and a.courseenrollmentstatus in('A','C','B','W') " +
							" and a.personid='"+orgpersonid+"' and c.coursecategoryid=b.coursecategoryid and c.coursecategorystatus='A') " +
							" and a.courseid not in (select b.courseid from courseenrollment a,course b where a.courseid=b.courseid " +
							" and a.courseenrollmentstatus in('A','C','B','W') and a.personid='"+orgpersonid+"') and b.organizationid='"+organizationid+"' group by a.courseid order by averagerating desc limit 3;")
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return ratingList;
	}
}
