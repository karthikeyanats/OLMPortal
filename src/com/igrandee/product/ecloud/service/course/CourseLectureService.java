package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courselecture;

@Service
@Named
@Scope("prototype")
public class CourseLectureService extends GenericEntityService<Courselecture, Serializable>{

	private static Logger CourseLectureServicelogger = Logger.getLogger(CourseLectureService.class);

	@Override
	protected Class<Courselecture> entityClass() {
		// TODO Auto-generated method stub
		return Courselecture.class;
	}

	public List<?> loadLecturePath(int courselectureid) {
		List<?> query=null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.coursesection", "coursesection")
					.createAlias("coursesection.course", "course")
					.createAlias("course.coursecategory", "coursecategory")
					.add(Restrictions.eq("this.courselectureid",courselectureid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("coursesection.coursesectionid").as("coursesectionid"))
							.add(Projections.property("this.courselectureid").as("courselectureid"))
							)
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseLectureServicelogger.isInfoEnabled())	{
				CourseLectureServicelogger.error("error in loadLecturePath() in CourseLectureService",e);
			}
		}
		return query;
	}



	public List<?> checkLecture(String courselecturetitle, String coursesectionid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.coursesection", "coursesection")
					.add(Restrictions.eq("this.courselecturestatus","A"))
					.add(Restrictions.eq("this.courselecturetitle",courselecturetitle))
					.add(Restrictions.eq("coursesection.coursesectionid",Integer.parseInt(coursesectionid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courselectureid").as("courselectureid")))   
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			if(CourseLectureServicelogger.isInfoEnabled())	{
				CourseLectureServicelogger.error("error in checkLecture() in CourseLectureService",e);
			}
		}
		return query;
	}
	
	
	/**
	 * @author selvarajan_j
	 * @return list as course lecture id and name
	 */
	public List<?> getLecture(int lectureid){
		List<?> query=null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.coursesection", "coursesection")
					.createAlias("coursesection.course", "course")
					.createAlias("course.coursecategory", "coursecategory")
					.createAlias("coursecategory.board", "board")
					.add(Restrictions.eq("this.courselectureid",lectureid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("board.boardname").as("boardname"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
 							.add(Projections.property("coursesection.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("this.courselecturetitle").as("courselecturetitle"))) 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseLectureServicelogger.isInfoEnabled())	{
				CourseLectureServicelogger.error("error in loadLecturePath() in CourseLectureService",e);
			}
		}
		return query;
	}
	
	/**
	 * Retrieve the Course List count and Questions Count for Dashboard
	 * @return
	 */
	public List<HashMap<String, ?>> getDashboardData() {
		System.out.println("Inside the getDashboardData()");
		// TODO Auto-generated method stub
		List dashboard_data = null;
		try{
		dashboard_data = new ArrayList();
			
		List course_count = new ArrayList();
		course_count = getCurrentSession().createSQLQuery("select cc.coursecategoryname,cc.coursesubcategory, cou.coursecategoryid, count(cou.courseid) as coursecount from course cou, coursecategory cc where cc.coursecategoryid = cou.coursecategoryid group by cou.coursecategoryid order by cou.coursecategoryid")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
	
		List question_count	=	new ArrayList();
		question_count = getCurrentSession().createSQLQuery("select cc.coursecategoryname,cc.coursesubcategory, count(q.id) as questioncount from question q, questionconnector qc,courselecture cl,coursesection cs, course c, coursecategory cc,board b where q.id = qc.question_id and qc.courselectureid = cl.courselectureid and cl.coursesectionid = cs.coursesectionid and cs.courseid = c.courseid and c.coursecategoryid = cc.coursecategoryid and cc.boardid and b.boardid group by cc.coursecategoryname")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
		
		for(int i=0;i<course_count.size();i++){
			Object o = course_count.get(i);
			HashMap h = (HashMap)o;
			
			for(int j=0;j<question_count.size();j++){
				Object o1 = question_count.get(j);
				HashMap h1 = (HashMap)o1;

				if(h.get("coursecategoryname").equals(h1.get("coursecategoryname"))){
					h.put("questioncount", h1.get("questioncount"));
					dashboard_data.add(h);
				}  
			}
		}

		}catch(Exception e){
			e.printStackTrace();
		}
 		return dashboard_data;
	}
	

}
