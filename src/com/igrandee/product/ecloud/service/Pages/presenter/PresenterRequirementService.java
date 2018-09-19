package com.igrandee.product.ecloud.service.Pages.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.product.ecloud.model.Board;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.PresenterappDownloadhistory;

@Service
@Named
@Scope("prototype")
public class PresenterRequirementService extends GenericEntityService<Course, Integer>{


	@Override
	protected Class<Course> entityClass() {
		// TODO Auto-generated method stub
		return Course.class;
	}


	public List loadMasterUserLoginCheck(String username,String password){
		List valueMapList = new ArrayList();
		try{
			List<?> MasterOrgId = getMasterOrgid(); 
			Map<?,?> map1=(Map<?, ?>) MasterOrgId.get(0);
			List<?> usernameCheck = getUsernamePasswordCheck(username,password,""+map1.get("organizationid")); 
			Map valueMap = new HashMap();
			String orgpersonid = null;
			if(usernameCheck.size()!=0){  
				for(int i=0;i<usernameCheck.size();i++) {
					Map map =(Map)usernameCheck.get(i);
					String firstname = ""+map.get("firstname");
					String lastname = ""+map.get("lastname");
					String loginid = ""+map.get("loginid");
					orgpersonid = ""+map.get("orgpersonid");
					String fullname=null;  
					if(lastname==null || lastname.equalsIgnoreCase("null") || lastname=="null" || lastname==""  || lastname.equalsIgnoreCase("") ){
						fullname = firstname;
					} else {
						fullname = firstname+" "+lastname;	
					}
					valueMap.put("fullname", fullname); 
					valueMap.put("firstname", firstname);
					valueMap.put("lastname", lastname);
					valueMap.put("loginid", loginid);
					valueMap.put("orgpersonid", orgpersonid);
					valueMap.put("organizationid", ""+map1.get("organizationid"));
					
				}
				List getPresenterDetail = getPresenterAppDetails(orgpersonid); 
				if(getPresenterDetail.size()!=0) { 
					for(int i=0;i<getPresenterDetail.size();i++) {
						Map map =(Map)getPresenterDetail.get(i); 
						String typename 	   = ""+map.get("typename");
						String applicationname = ""+map.get("applicationname");
						String logincount 	   = ""+map.get("logincount");
						String workingduration = ""+map.get("workingduration");
						String licenseapppath  = ""+map.get("licenseapppath");
						valueMap.put("typename", typename); 
						valueMap.put("logincount", logincount);
						valueMap.put("applicationname", applicationname);
						valueMap.put("workingduration", workingduration);  
						valueMap.put("licenseapppath", licenseapppath);   
					} 
				} else {
					valueMap.put("typename", "-"); 
					valueMap.put("logincount", "0");
					valueMap.put("applicationname",  "-");
					valueMap.put("workingduration",  "-");  
					valueMap.put("licenseapppath",  "-");  
				}
				valueMapList.add(valueMap);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return valueMapList;
	}

	public List<?> loadOrganizations(){
		List<?> organizationsList =null;
		try	{
			organizationsList = getCurrentSession().createCriteria(Organization.class)
					.add(Restrictions.eq("this.orgstatus",'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgname").as("organizationname"))
							.add(Projections.property("this.organizationid").as("organizationid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return organizationsList;
	}

	public List<?> loadOtherUserLoginCheck(String username,String password,String orgid){
		List valueMapList = new ArrayList();
		try{
			List<?> usernameCheck = getUsernamePasswordCheck(username,password,orgid); 
			Map valueMap = new HashMap();
			String orgpersonid = null;
			if(usernameCheck.size()!=0){  
				for(int i=0;i<usernameCheck.size();i++) {
					Map map =(Map)usernameCheck.get(i);
					String firstname = ""+map.get("firstname");
					String lastname = ""+map.get("lastname");
					String loginid = ""+map.get("loginid");
					orgpersonid = ""+map.get("orgpersonid");
					String fullname=null;  
					if(lastname==null || lastname.equalsIgnoreCase("null") || lastname=="null" || lastname==""  || lastname.equalsIgnoreCase("") ){
						fullname = firstname;
					} else {
						fullname = firstname+" "+lastname;	
					}
					valueMap.put("fullname", fullname); 
					valueMap.put("firstname", firstname);
					valueMap.put("lastname", lastname);
					valueMap.put("loginid", loginid);
					valueMap.put("orgpersonid", orgpersonid);
				}
				List getPresenterDetail = getPresenterAppDetails(orgpersonid); 
				if(getPresenterDetail.size()!=0) { 
					for(int i=0;i<getPresenterDetail.size();i++) {
						Map map =(Map)getPresenterDetail.get(i); 
						String typename 	   = ""+map.get("typename");
						String applicationname = ""+map.get("applicationname");
						String logincount 	   = ""+map.get("logincount");
						String workingduration = ""+map.get("workingduration");
						String licenseapppath  = ""+map.get("licenseapppath");
						valueMap.put("typename", typename); 
						valueMap.put("logincount", logincount);
						valueMap.put("applicationname", applicationname);
						valueMap.put("workingduration", workingduration);  
						valueMap.put("licenseapppath", licenseapppath);   
					} 
				} else {
					valueMap.put("typename", "-"); 
					valueMap.put("logincount", "0");
					valueMap.put("applicationname",  "-");
					valueMap.put("workingduration",  "-");  
					valueMap.put("licenseapppath",  "-");  
				}
				valueMapList.add(valueMap);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return valueMapList;
	}

	public List<?> loadCategoriesList(){
		List<?> categoriesList=null;
		try {
			categoriesList= getCurrentSession().createCriteria(Board.class)
					.add(Restrictions.eq("this.boardstatus", 'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.boardid").as("boardid"))
							.add(Projections.property("this.boardname").as("boardname"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoriesList;
	}


	public List<?> loadSubCategoriesList(String boardid){
		List<?> subCategoriesList=null;
		try {
			subCategoriesList= getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.add(Restrictions.eq("board.boardid", Integer.parseInt(boardid)))
					.add(Restrictions.eq("this.coursecategorystatus", "A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("this.coursecategorydescription").as("coursecategorydescription"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subCategoriesList;
	}

	public List<?> loadCourseList(String categoryid,String orgpersonid){
		List<?> userCourses=null;
		try	{
			userCourses = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursecategory", "coursecategory")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.eq("coursecategory.coursecategoryid",Integer.parseInt(categoryid)))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
					.add(Restrictions.eq("this.coursestatus","D"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userCourses;
	}
	
	public List<?> loadPrivateCourseList(String orgpersonid){
		List<?> userCourses=null;
		try	{
			userCourses = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
					.add(Restrictions.eq("this.coursestatus","D"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userCourses;
	}

	public List<?> loadSectionsList(String courseid){
		List<?> courseSectionsList=null;
		try	{
			courseSectionsList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursesections", "coursesections")			
					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("coursesections.coursesectionstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("coursesections.coursesectionid").as("coursesectionid"))
							.add(Projections.property("coursesections.coursesectiontitle").as("coursesectiontitle"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return courseSectionsList;
	}

	private List<?> getMasterOrgid(){
		List<?> masterOrgId =null;
		try	{
			masterOrgId = getCurrentSession().createCriteria(Organization.class)
					.add(Restrictions.eq("this.orgstatus",'M'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.organizationid").as("organizationid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return masterOrgId;
	}

	private List<?> getUsernamePasswordCheck(String username,String password,String orgid) {
		List<?> loginlist=null;
		try	{
			loginlist = getCurrentSession().createCriteria(Orgperson.class) 

					.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.organizationid", "organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					.add(Restrictions.eq( "login.userid", StringUtils.lowerCase(username)))
					.add(Restrictions.eq( "login.password", StringUtils.lowerCase(password)))
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(orgid))) 
					.add(Restrictions.eq( "login.loginstatus", 'A'))
					.setProjection(Projections.projectionList() 
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname")) 
							.add(Projections.property("login.loginid").as("loginid"))
							.add(Projections.property("this.orgpersonid").as("orgpersonid"))) 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return loginlist; 
	}

	private List<?> getPresenterAppDetails(String orgpersonid) {
		List<?> persenterappList=null;
		try{
			persenterappList = getCurrentSession().createCriteria(PresenterappDownloadhistory.class)
					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.presenterappdetail", "presenterappdetail",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("presenterappdetail.presenterapp", "presenterapp")
					.createAlias("presenterappdetail.presentertype", "presentertype") 
					.add(Restrictions.eq( "orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
					.setProjection(Projections.projectionList() 
							.add(Projections.property("presentertype.typename").as("typename"))
							.add(Projections.property("presenterapp.applicationname").as("applicationname")) 
							.add(Projections.property("presenterappdetail.logincount").as("logincount"))
							.add(Projections.property("presenterappdetail.workingduration").as("workingduration"))
							.add(Projections.property("presenterapp.licenseapppath").as("licenseapppath"))) 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return persenterappList; 
	}
}
