/**
 * 
 */
package com.igrandee.product.ecloud.service.sat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.satutil.model.LiveQuiz;
import com.igrandee.product.satutil.model.LiveQuizAnswer;

/**
 * @author selvarajan_j
 *
 */
@Service
@Scope("prototype")
public class OnlineQuizUserService extends GenericEntityService<LiveQuiz, Integer>{
	static final Logger onlineQuizUserService = Logger.getLogger(OnlineQuizUserService.class);
	
	@Override
	protected Class<LiveQuiz> entityClass() {
 		return LiveQuiz.class;
	}
	
	 
	public List<?> getStudentQuiz(int userid,int lquizid){
		List<?> questionType = null;
 		
		try {
 			questionType=getCurrentSession().createSQLQuery("select qa.answers,la.answerstatus,la.createddate as answereddate from livequizanswer la,questionanswers qa where la.studentid='"+userid+"' and  la.livequiz_id='"+lquizid+"' and qa.id=la.livequizans_id and DATE_FORMAT(la.createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y') order by la.id desc;")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			}catch (Exception e) {
				onlineQuizUserService.error("error in onlineQuizUserService in QuizREPORT",e);
			}
			onlineQuizUserService.info("onlineQuizUserService VALUE "+questionType);
			return questionType;
		
		}
	
	public List<?> getQuizReport(int userid,int sessionid){
	List<?> questionType = null;
	Criteria query = null;
	Date d1=null;
	Date d2=null;
	
	try{
		 DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		 Calendar calobj = Calendar.getInstance();
		 String datestring[]=(df.format(calobj.getTime())).toString().split("/");
		 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		  
		 d1=dateformat.parse(datestring[0]+"-"+datestring[1]+"-"+datestring[2]+" 00:00:00");
		 d2= dateformat.parse(datestring[0]+"-"+datestring[1]+"-"+datestring[2]+" 59:59:59");
		 
	}catch(ParseException e){
		e.printStackTrace();
	}
	
	try {
		Order order = Order.asc("livequiz.id");
		
			query = getCurrentSession().createCriteria(LiveQuizAnswer.class)
			.createAlias("liveQuiz", "livequiz")
 			.createAlias("liveQuiz.question", "question");
			query.add(Restrictions.eq("livequiz.teletimetableid",sessionid));
			query.add(Restrictions.eq("this.studentid",userid));
 			query.add(Expression.between("this.createddate", d1,d2));
			query.addOrder(order);
			query.setProjection(Projections.projectionList()
 	 			.add(Projections.property("question.id").as("questionid"))
 				.add(Projections.property("livequiz.id").as("livequizid"))
	 			.add(Projections.property("this.answerstatus").as("answerstatus")));
			
	 		questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			onlineQuizUserService.error("error in onlineQuizUserService in QuizREPORT",e);
		}
		onlineQuizUserService.info("onlineQuizUserService VALUE "+questionType);
		return questionType;
	
	}
	/**
	 * GET QUIZ QUESTION COUNT 
	 * @param userid
	 * @param sessionid
	 * @return
	 */
	
	public List <?> getQuizQstCount(String teacherid,String sessionid){
		List<?> getCount = null;
		try {
			getCount=getCurrentSession().createSQLQuery("select question_id,id from livequiz where teletimetableid='"+sessionid+"' and DATE_FORMAT(createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y') and teacherid='"+teacherid+"';")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				onlineQuizUserService.error("error in ConnectionService in getQuizQstCount()",e);
			}
		System.out.println("PRINT COUNT "+getCount);
			return getCount;
	}
	
	/**
	 * GET SESSION ID 
	 * @param teacherid
	 * @return List as session id
	 */
	public List <?> getSessionID(String teacherid){
		List<?> sessionIDs = null;
		try {
			sessionIDs=getCurrentSession().createSQLQuery("select DISTINCT teletimetableid from livequiz where teacherid='"+teacherid+"' and DATE_FORMAT(createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y') order by createddate desc;")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				onlineQuizUserService.error("error in ConnectionService in getSessionIDs()",e);
			}
			return sessionIDs;
	}
	/**
	 * GET SESSION DATE TIME 
	 * @param teacherid
	 * @return List
	 */
	public List <?> getSessionTime(String sessionid){
		List<?> sessionTime = null;
		try {
			sessionTime=getCurrentSession().createSQLQuery("select DATE_FORMAT(createddate,'%b %d %Y' ) as createddate,DATE_FORMAT(createddate,'%T' ) as times from livequiz where teletimetableid='"+sessionid+"' and DATE_FORMAT(createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y')  order by id asc limit 1;")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				onlineQuizUserService.error("error in ConnectionService in getSessionTime()",e);
			}
			return sessionTime;
	}
	
	
	/**
	 * GET SESSION Primary ID
	 * @param teacherid
	 * @return List as session id
	 */
	public List <?> getExamMeta(String sessionid){
		List<?> meta = null;
		try {
			meta=getCurrentSession().createSQLQuery("select DISTINCT a.courselectureid,a.courselecturetitle,c.coursesectiontitle,d.coursetitle,e.coursecategoryname,f.boardname from courselecture a,coursesection c,course d,coursecategory e, board f,livequiz g,questionconnector h where g.question_id=h.question_id and h.courselectureid=a.courselectureid and c.coursesectionid=a.coursesectionid and d.courseid=c.courseid and e.coursecategoryid=d.coursecategoryid and f.boardid=e.boardid and a.courselecturestatus='A' and DATE_FORMAT(g.createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y') and g.teletimetableid='"+sessionid+"';")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				onlineQuizUserService.error("error in ConnectionService in getSessionIDs()",e);
			}
			return meta;
	}
	
	/**
	 * GET SESSION Primary ID
	 * @param teacherid
	 * @return List as session id
	 */
	public List <?> getReports(String sessionid){
		List<?> primaryid = null;
		try {
			//primaryid=getCurrentSession().createSQLQuery("select id,nostudent from livequiz where DATE_FORMAT(createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y') and teletimetableid='"+sessionid+"' order by id;")
			primaryid=getCurrentSession().createSQLQuery("select a.id,a.nostudent,b.courselectureid,a.question_id from livequiz a,questionconnector b where DATE_FORMAT(a.createddate,'%m-%d-%Y')=DATE_FORMAT(now(),'%m-%d-%Y') and teletimetableid='"+sessionid+"' and a.question_id=b.question_id order by a.id")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				onlineQuizUserService.error("error in ConnectionService in getSessionIDs()",e);
			}
			return primaryid;
	}
	/**
	 * GET Report Data 
	 * @param teacherid
	 * @return List
	 */
	public List <?> getReportData(String livequizid){
		List<?> reportData = null;
		try {
			//reportData=getCurrentSession().createSQLQuery("select count(CASE WHEN answerstatus = 'C' then answerstatus  END) as correct,count(CASE WHEN answerstatus = 'W' then answerstatus  END) as wrong from livequizanswer  where livequiz_id ='"+livequizid+"' order by id;")
				//	.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			reportData=getCurrentSession().createSQLQuery("select count(CASE WHEN  driv.answerstatus = 'C' then driv.answerstatus  END) as correct,count(CASE WHEN driv.answerstatus = 'W' then driv.answerstatus  END) as wrong from (select DISTINCT studentid,answerstatus from livequizanswer where livequiz_id ='"+livequizid+"' order by id) as driv")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
			}catch (Exception e) {
				onlineQuizUserService.error("error in ConnectionService in getSessionTime()",e);
			}
			return reportData;
	}
}
