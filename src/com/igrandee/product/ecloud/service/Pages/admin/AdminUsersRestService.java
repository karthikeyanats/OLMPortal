package com.igrandee.product.ecloud.service.Pages.admin;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Orgperson;

@Service
@Named
public class AdminUsersRestService  extends GenericEntityService<Orgperson, Integer> {

	@Override
	protected Class<Orgperson> entityClass() {
		// TODO Auto-generated method stub
		return Orgperson.class;
	}

	public List<?> loadUserCounts(String orgid,String orgpersonid){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createSQLQuery("" +
					"select count(distinct a.orgpersonid) as users," +
					"(select count(distinct c.orgpersonid)from personallocation a,role b," +
					"orgperson c where a.roleid=b.roleid and b.rolestatus='A' and " +
					"a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and a.personallocationstatus='A' " +
					"and b.rolename='user' and c.organizationid='"+orgid+"') as registedusers ," +
					"(select count(distinct a.personid) from courseenrollment a, " +
					"payment b, orgperson d where a.courseenrollmentid=b.courseenrollmentid " +
					"and a.personid=d.orgpersonid and d.orgpersonstatus='A' and b.paymenttype " +
					"in ('invoice-email','Online') and a.courseenrollmentstatus in ('A','C','B') " +
					"and d.organizationid='"+orgid+"') as paidusers, " +
					"(select count(distinct c.orgpersonid)from personallocation a,role b,orgperson c " +
					"where a.roleid=b.roleid and b.rolestatus='A' and a.orgpersonid=c.orgpersonid and " +
					"c.orgpersonstatus='D' and a.personallocationstatus='A' and b.rolename='user' " +
					"and c.organizationid='"+orgid+"') as deactivatedusers," +
					"(select count(distinct c.orgpersonid) from personallocation a,role b,orgperson c," +
					"course d where a.roleid=b.roleid and b.rolestatus='A' and a.orgpersonid=c.orgpersonid " +
					"and c.orgpersonstatus='A' and c.orgpersonid=d.personid and d.coursestatus='A' and " +
					"b.rolename='user' and c.organizationid='"+orgid+"') as authors," +
					"(select count(distinct c.orgpersonid) from personallocation a,role b,orgperson c," +
					"courseenrollment d where a.roleid=b.roleid and b.rolestatus='A' and " +
					"a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and c.orgpersonid=d.personid " +
					"and d.courseenrollmentstatus in('A','C','B') and b.rolename='user' " +
					"and c.organizationid='"+orgid+"') as enrolleduser ," +
					"(select count(a.courseinvitationid) from courseinvitation a,orgperson b " +
					"where courseinvitationstatus in ('I','F') and a.invitedby=b.orgpersonid and b.organizationid='"+orgid+"') " +
					"as invitedusers," +
					"(select count(a.paymentid) from payment a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and b.courseid=c.courseid and " +
					"c.coursestatus='A' and b.courseenrollmentstatus = 'P' and a.paymentstatus='P'  and c.personid='"+orgpersonid+"') as allpayment," +
					"(select count(a.paymentid) from payment a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and b.courseenrollmentstatus='P' and a.paymentstatus='P' " +
					"and b.courseid=c.courseid and c.coursestatus='A' and c.personid='"+orgpersonid+"') as pendingpayment," +
					"(select count(a.paymentid) from payment a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and a.paymentstatus='A' " +
					"and b.courseid=c.courseid and a.paymenttype='Free' and c.coursestatus='A' and b.courseenrollmentstatus in ('A','C','B')  " +
					"and c.personid='"+orgpersonid+"') as freepaidusers," +
					"(select count(a.paymentid) from payment a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and a.paymentstatus='A' " +
					"and b.courseid=c.courseid and a.paymenttype='Online' and c.coursestatus='A' and b.courseenrollmentstatus in ('A','C','B')  " +
					"and c.personid='"+orgpersonid+"') as onlinepaidusers," +
					"(select count(a.paymentid) from payment a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and a.paymentstatus='A' " +
					"and b.courseid=c.courseid and a.paymenttype='invoice-email' and c.coursestatus='A' and b.courseenrollmentstatus in ('A','C','B')  " +
					"and c.personid='"+orgpersonid+"') as paidpayment," +
					"(select count(a.coursecertificateid) from coursecertificate a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and  b.courseid=c.courseid and c.coursestatus='A' " +
					"and c.personid='"+orgpersonid+"') as allcertificaterequests," +
					"(select count(a.coursecertificateid) from coursecertificate a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and a.coursecertificatestatus='P' and " +
					"b.courseid=c.courseid and c.coursestatus='A' and c.personid='"+orgpersonid+"') as pendingcertificates," +
					"(select count(a.coursecertificateid) from coursecertificate a,courseenrollment b,course c " +
					"where a.courseenrollmentid=b.courseenrollmentid and a.coursecertificatestatus='A' " +
					"and b.courseid=c.courseid and c.coursestatus='A' and c.personid='"+orgpersonid+"') as approvedcertificates " +
					"from orgperson a where a.orgpersonstatus='A' and a.organizationid='"+orgid+"'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadPaidUsers(String orgid){
		List<?> descriptionsList =null;
		try	{

			@SuppressWarnings("unchecked")
			List<Integer> orgpersonList = getCurrentSession().createSQLQuery("select a.personid from courseenrollment a, " +
					"payment b, orgperson d where a.courseenrollmentid=b.courseenrollmentid " +
					"and a.personid=d.orgpersonid and d.orgpersonstatus='A' and b.paymenttype " +
					"in ('invoice-email','Online') and a.courseenrollmentstatus in ('A','C','B') " +
					"and d.organizationid='"+orgid+"' group by a.personid").list();
			if(orgpersonList!=null && orgpersonList.size()!=0){
			descriptionsList =  getCurrentSession().createCriteria(Orgperson.class) 

					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.in("this.orgpersonid",orgpersonList))   

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgpersonid").as("orgpersonid"))
							.add(Projections.property("personid.personid").as("personid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							.add(Projections.property("email.userid").as("emailid"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}else{
				descriptionsList =null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadRegisteredUsers(String orgid){
		List<?> descriptionsList =null;
		try	{

			@SuppressWarnings("unchecked")
			List<Integer> orgpersonList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid from " +
					"personallocation a,role b,orgperson c where a.roleid=b.roleid and b.rolestatus='A' and " +
					"a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and a.personallocationstatus='A' " +
					"and b.rolename in ('user','tutor') and c.organizationid='"+orgid+"'").list();
			if(orgpersonList!=null && orgpersonList.size()!=0){
			descriptionsList =  getCurrentSession().createCriteria(Orgperson.class) 
					.createAlias("this.personallocations", "personallocation",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personallocation.role", "role",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.in("this.orgpersonid",orgpersonList))   

					.setProjection(Projections.projectionList()
							.add(Projections.property("personallocation.personallocationid").as("personallocationid"))
							.add(Projections.property("this.orgpersonid").as("orgpersonid"))
							.add(Projections.property("role.roleid").as("roleid"))
							.add(Projections.property("personid.personid").as("personid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							.add(Projections.property("email.userid").as("emailid"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}else{
				descriptionsList =null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadEnrolledUsers(String orgid){
		List<?> descriptionsList =null;
		try	{

			@SuppressWarnings("unchecked")
			List<String> orgpersonList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid " +
					"from personallocation a,role b,orgperson c," +
					"courseenrollment d where a.roleid=b.roleid and b.rolestatus='A' and " +
					"a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and c.orgpersonid=d.personid " +
					"and d.courseenrollmentstatus in('A','C','B') and b.rolename='user' " +
					"and c.organizationid='"+orgid+"'").list();
			if(orgpersonList!=null && orgpersonList.size()!=0){
				descriptionsList = getCurrentSession().createSQLQuery("select 'persons' as seperator," +
						"this_.orgpersonid as orgpersonid, " +
						"personid1_.personid as personid, personid1_.firstName as firstname, " +
						"personid1_.lastName as lastname, email3_.userid as emailid from orgperson " +
						"this_ left outer join person personid1_ on this_.personid=personid1_.personid " +
						"left outer join contactinfo contactinf2_ on personid1_.contactinfoid=contactinf2_.contactinfoid " +
						"left outer join email email3_ on contactinf2_.contactinfoid=email3_.contactinfoid " +
						"where this_.orgpersonid in ("+orgpersonList.toString().replaceAll("[\\s\\[\\]]", "")+")")
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	
			}else{
				descriptionsList =null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadDeactivatedUsers(String orgid){
		List<?> descriptionsList =null;
		try	{

			@SuppressWarnings("unchecked")
			List<Integer> orgpersonList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid " +
					"from personallocation a,role b,orgperson c " +
					"where a.roleid=b.roleid and b.rolestatus='A' and a.orgpersonid=c.orgpersonid and " +
					"c.orgpersonstatus='D' and a.personallocationstatus='A' and b.rolename='user' " +
					"and c.organizationid='"+orgid+"'").list();
			if(orgpersonList!=null && orgpersonList.size()!=0){
				descriptionsList =  getCurrentSession().createCriteria(Orgperson.class) 

						.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
						.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
						.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

						.add(Restrictions.in("this.orgpersonid",orgpersonList))   

						.setProjection(Projections.projectionList()
								.add(Projections.property("this.orgpersonid").as("orgpersonid"))
								.add(Projections.property("personid.personid").as("personid"))
								.add(Projections.property("personid.firstName").as("firstname"))
								.add(Projections.property("personid.lastName").as("lastname"))
								.add(Projections.property("email.userid").as("emailid"))

								).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}else{
				descriptionsList =null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadUsersCourseCount(){
		List<?> descriptionsList =null;
		try	{

			descriptionsList = getCurrentSession().createSQLQuery("select 'countval' as seperator, a.orgpersonid," +
					"count(b.courseenrollmentid) as enrolledcoursescount from orgperson a ,courseenrollment b,course c " +
					"where a.orgpersonid=b.personid and b.courseenrollmentstatus in ('A','C','B') and " +
					"b.courseid=c.courseid group by a.orgpersonid")

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadUserCourses(String orgpersonid){
		List<?> descriptionsList =null;
		try	{

			descriptionsList =  getCurrentSession().createCriteria(Courseenrollment.class) 

					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)


					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))   
					.add(Restrictions.in("this.courseenrollmentstatus",new String[] {"A","C","B"}))

					.setProjection(Projections.projectionList()
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}


	public List<?> loadInvitedUsers(String orgid){
		List<?> descriptionsList =null;
		try	{

			descriptionsList = getCurrentSession().createSQLQuery("select a.courseinvitationid,a.emailid,a.dateofinvitation,a.courseinvitationstatus,a.message " +
					"from courseinvitation a,orgperson b where courseinvitationstatus in ('I','F') " +
					"and a.invitedby=b.orgpersonid and b.organizationid='"+orgid+"' order by a.courseinvitationid desc ")

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadAuthors(String orgid){
		List<?> descriptionsList =null;
		try	{

			@SuppressWarnings("unchecked")
			List<Integer> orgpersonList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid " +
					"from personallocation a,role b,orgperson c,course d where a.roleid=b.roleid " +
					"and b.rolestatus='A' and a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' " +
					"and c.orgpersonid=d.personid and d.coursestatus='A' and b.rolename='user' " +
					"and c.organizationid='"+orgid+"'").list();
			if(orgpersonList!=null && orgpersonList.size()!=0){
				descriptionsList =  getCurrentSession().createCriteria(Course.class) 

						.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
						.createAlias("orgperson.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
						.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
						.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

						.add(Restrictions.in("orgperson.orgpersonid",orgpersonList))   
						.add(Restrictions.eq("this.coursestatus","A"))

						.setProjection(Projections.projectionList()
								.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
								.add(Projections.property("personid.personid").as("personid"))
								.add(Projections.property("personid.firstName").as("firstname"))
								.add(Projections.property("personid.lastName").as("lastname"))
								.add(Projections.property("email.userid").as("emailid"))
								.add(Projections.count("this.courseid").as("courses"))
								.add(Projections.groupProperty("orgperson.orgpersonid"))

								).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}else{
				descriptionsList =null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List getGroupPersonids(String type,String orgid,String orgpersonid){

		List notificationPersonidsList =null;
		try	{

			if(type.equalsIgnoreCase("registered")){

				notificationPersonidsList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid from " +
						"personallocation a,role b,orgperson c where a.roleid=b.roleid and b.rolestatus='A' and " +
						"a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and a.personallocationstatus='A' " +
						"and b.rolename='user' and c.organizationid='"+orgid+"'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			}else if(type.equalsIgnoreCase("enrolled")){
				notificationPersonidsList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid from " +
						"personallocation a,role b,orgperson c," +
						"courseenrollment d where a.roleid=b.roleid and b.rolestatus='A' and " +
						"a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and c.orgpersonid=d.personid " +
						"and d.courseenrollmentstatus in('A','C','B') and b.rolename='user' " +
						"and c.organizationid='"+orgid+"'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			}else if(type.equalsIgnoreCase("paid")){
				notificationPersonidsList = getCurrentSession().createSQLQuery("select distinct d.orgpersonid from " +
						"courseenrollment a, payment b, orgperson d where a.courseenrollmentid=b.courseenrollmentid " +
						"and a.personid=d.orgpersonid and d.orgpersonstatus='A' and b.paymenttype " +
						"in ('invoice-email','Online') and a.courseenrollmentstatus in ('A','C','B') " +
						"and d.organizationid='"+orgid+"'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			}else if(type.equalsIgnoreCase("deactivated")){
				notificationPersonidsList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid from " +
						"personallocation a,role b,orgperson c " +
						"where a.roleid=b.roleid and b.rolestatus='A' and a.orgpersonid=c.orgpersonid and " +
						"c.orgpersonstatus='D' and a.personallocationstatus='A' and b.rolename='user' " +
						"and c.organizationid='"+orgid+"'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			}else if(type.equalsIgnoreCase("author")){
				notificationPersonidsList = getCurrentSession().createSQLQuery("select distinct c.orgpersonid from " +
						"personallocation a,role b,orgperson c,course d where a.roleid=b.roleid and b.rolestatus='A' " +
						"and a.orgpersonid=c.orgpersonid and c.orgpersonstatus='A' and c.orgpersonid=d.personid " +
						"and d.coursestatus='A' and b.rolename='user' and c.organizationid='"+orgid+"'").
						setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}
			else if(type.equalsIgnoreCase("pendingpayment")){
				notificationPersonidsList = getCurrentSession().createSQLQuery("select distinct b.personid as orgpersonid " +
						"from payment a,courseenrollment b,course c where a.courseenrollmentid=b.courseenrollmentid " +
						"and b.courseenrollmentstatus='P' and a.paymentstatus='P' and b.courseid=c.courseid " +
						"and c.coursestatus='A' and c.personid='"+orgpersonid+"'").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return notificationPersonidsList;
	}

	public List<?> loadAuthorCourses(String authorid){
		List<?> authorsList =null;
		try	{
			authorsList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)			
					.createAlias("this.orgperson", "orgperson")
					.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(authorid)))
					.add(Restrictions.eq("this.coursestatus", "A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursetitle").as("coursetitle"))							
							.add(Projections.property("this.courseid").as("courseid"))	
							.add(Projections.count("courseenrollments.courseenrollmentid").as("studentcount"))
							.add(Projections.groupProperty("this.courseid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return authorsList	;
	}

	public List<?> loadAuthorCourseUsers(String courseid){
		List<?> courseUsersList = null;
		try{
			courseUsersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson") 
					.createAlias("orgperson.personid", "personid") 
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("email.userid").as("emailid"))
							.add(Projections.groupProperty("orgperson.orgpersonid"))) 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
		return courseUsersList;	
	}


	public Integer changePersonStatus(int id,String status){
		Integer updatedpersonId = 0;		
		try	{
			String hql = "UPDATE Orgperson set orgpersonstatus = :orgpersonstatus "  + 
					"WHERE orgpersonid = :orgpersonid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("orgpersonstatus", status.charAt(0));
			query.setParameter("orgpersonid",id);
			Integer result = query.executeUpdate();
			updatedpersonId = result.SIZE;			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return updatedpersonId;		
	}

	public Integer changePaymentStatus(int id,String status){
		Integer updatedpersonId = 0;		
		try	{
			String hql = "UPDATE Payment set paymentstatus = :paymentstatus "  + 
					"WHERE paymentid = :paymentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("paymentstatus", status);
			query.setParameter("paymentid",id);
			Integer result = query.executeUpdate();
			updatedpersonId = result.SIZE;			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return updatedpersonId;		
	}

	public Integer changeEnrollmentStatus(int id,String status){
		Integer updatedpersonId = 0;		
		try	{
			String hql = "UPDATE Courseenrollment set courseenrollmentstatus = :courseenrollmentstatus "  + 
					"WHERE courseenrollmentid = :courseenrollmentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("courseenrollmentstatus", status);
			query.setParameter("courseenrollmentid",id);
			Integer result = query.executeUpdate();
			updatedpersonId = result.SIZE;			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return updatedpersonId;		
	}

	public List<?> loadCertificateRequestsByStatus(String orgpersonid,String coursecertificatestatus){
		List<?> certificatesList = null;
		Criteria query = null;
		try	{
			query =  getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecertificate.class)
					.createAlias("this.courseenrollment", "courseenrollment",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courseenrollment.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courseenrollment.orgperson", "orgperson" ,org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgperson.personid", "personobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personobj.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "author", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

			if(coursecertificatestatus.equalsIgnoreCase("all")){

			}else{
				query.add(Restrictions.eq("this.coursecertificatestatus",coursecertificatestatus));
			}
			certificatesList=	query.add(Restrictions.eq("author.orgpersonid",Integer.parseInt(orgpersonid)))

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursecertificateid").as("coursecertificateid"))
							.add(Projections.property("this.certificatedescription").as("certificatedescription"))
							.add(Projections.property("this.coursecertificatestatus").as("coursecertificatestatus"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.coursestatus").as("coursestatus"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("personobj.firstName").as("firstname"))
							.add(Projections.property("personobj.lastName").as("lastname"))
							.add(Projections.property("email.emailid").as("emailid"))
							.add(Projections.property("email.userid").as("emailaddress")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return certificatesList;
	}
	
	public Integer updaterole(int personallocationid,int roleid){
		Integer updatedpersonId = 0;		
		try	{
			String hql = "UPDATE Personallocation set roleid = :roleid "  + 
					"WHERE personallocationid = :personallocationid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("personallocationid", personallocationid);
			query.setParameter("roleid",roleid);
			Integer result = query.executeUpdate();
			updatedpersonId = result.SIZE;			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return updatedpersonId;		
	}
}