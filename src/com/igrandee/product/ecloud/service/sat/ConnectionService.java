package com.igrandee.product.ecloud.service.sat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.sat.Questionconnector;
import com.igrandee.product.satutil.model.Questiontype;

/**
 * @author selvarajan_j 
 *
 */
@Service
@Scope("prototype")
@Named
@SuppressWarnings("rawtypes")
public class ConnectionService extends GenericEntityService<Questionconnector, Integer>{
	
	static final Logger satQuestionServiceLogger = Logger.getLogger(ConnectionService.class);
 
	@Override
	protected Class<Questionconnector> entityClass() {
 		return Questionconnector.class;
	}
	
	/**
	 * @return Type Of Question List
	 */
	public List<?> getQuestions(int lectureid,int typeId){
		List<?> questionType = null;
		Criteria query = null;
	try {
		Order order = Order.asc("question.id");
		//Order orderbyansid = Order.desc("questionanswers.id");
		query = getCurrentSession().createCriteria(Questionconnector.class)
				.createAlias("question", "question")
				.createAlias("courselecture", "courselecture")
				.createAlias("question.qustionanswers", "questionanswers")				
				.createAlias("question.questionmetas", "questionmeta")
				.createAlias("question.questiontype", "questiontype")
				.createAlias("question.difficultyfactor", "difficultyfactor");
				query.add(Restrictions.eq("courselecture.courselectureid", lectureid));
				query.add(Restrictions.eq("questiontype.id",typeId));
				query.add(Restrictions.eq("questionanswers.status",'A'));
				query.add(Restrictions.eq("question.status",'A'));
				query.addOrder(order);
				
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
			satQuestionServiceLogger.error("error in ConnectionService in getQuestions()",e);
		}
	satQuestionServiceLogger.info("questionType "+questionType);
		return questionType;
	}
	
	/**
	 * @return Type Of Question List
	 */
 	public List<?> getQuestionbyid(int questionid){
		List<?> questionType = null;
		Criteria query = null;
	try {
		Order order = Order.asc("question.id");
 			query = getCurrentSession().createCriteria(Questionconnector.class)
				.createAlias("question", "question")
				.createAlias("question.qustionanswers", "questionanswers")				
				.createAlias("question.questiontype", "questiontype");
				query.add(Restrictions.eq("question.id", questionid));
				query.add(Restrictions.eq("questionanswers.status",'A'));
				query.add(Restrictions.eq("question.status",'A'));
				query.addOrder(order);
				
		 		query.setProjection(Projections.projectionList()
		 			.add(Projections.property("question.id").as("questionid"))
		 			.add(Projections.property("question.category").as("category"))
		 			.add(Projections.property("question.mark").as("mark"))
		 			.add(Projections.property("question.question").as("question"))
		 			.add(Projections.property("questiontype.id").as("id"))
		 			.add(Projections.property("questionanswers.answers").as("answers"))
		 			.add(Projections.property("questionanswers.id").as("answerid"))
		 			.add(Projections.property("questionanswers.answerDescription").as("answerDescription"))
		 			.add(Projections.property("questionanswers.correctans").as("correctans")));
		 		questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		 			 		
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getQuestions()",e);
		}
	satQuestionServiceLogger.info("questionType "+questionType);
		return questionType;
	}
	
	public List<?> getQuestionQuiz(int questionid){
		List<?> questionType = null;
		Criteria query = null;
		
	try {
		 	query = getCurrentSession().createCriteria(Questionconnector.class)
				.createAlias("question", "question")
				.createAlias("question.qustionanswers", "questionanswers")				
				.createAlias("question.questiontype", "questiontype");
				query.add(Restrictions.eq("question.id", questionid));
				query.add(Restrictions.eq("questionanswers.status",'A'));
				query.add(Restrictions.eq("question.status",'A'));
				query.add(Restrictions.not(Restrictions.eq("questionanswers.correctans",0)));
				
		 		query.setProjection(Projections.projectionList()
		 			.add(Projections.property("question.id").as("questionid"))
 		 			.add(Projections.property("question.question").as("question"))
		 			.add(Projections.property("questiontype.id").as("id"))
		 			.add(Projections.property("questionanswers.answers").as("answers")));
		 			 
		 		questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		 			 		
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getQuestions()",e);
		}
	satQuestionServiceLogger.info("questionType "+questionType);
		return questionType;
	}
 
	/**
	 * @return Type Of Question List
	 */
	public List<?> questionID(int lectureid,int typeId,String category){
		List<?> questionType = null;
		Criteria query = null;
		System.out.println("category "+category);
	try {
		Order order = Order.asc("question.id");
		query = getCurrentSession().createCriteria(Questionconnector.class)
				.createAlias("question", "question")
				.createAlias("courselecture", "courselecture")
				.createAlias("question.questiontype", "questiontype");
				query.add(Restrictions.eq("courselecture.courselectureid", lectureid));
				query.add(Restrictions.eq("questiontype.id",typeId));
				query.add(Restrictions.eq("question.status",'A'));
				if(category != null)
					query.add(Restrictions.eq("question.category",category));
				
				query.addOrder(order);
				query.setProjection(Projections.projectionList()
		 		.add(Projections.property("question.id").as("questionid")));
		 		questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getQuestions()",e);
		}
	satQuestionServiceLogger.info("questionType "+questionType);
		return questionType;
	}
	
	/**
	 * Load questions and answers
	 */
	public List<?> getQstAns(ArrayList<?> lectureid,int typeId,int mark){
		List<?> questionType = null;
		Criteria query = null;
		 
	try {
		
		Order order = Order.asc("question.id");
		query = getCurrentSession().createCriteria(Questionconnector.class)
				.createAlias("question", "question")
				.createAlias("courselecture", "courselecture")
				.createAlias("question.qustionanswers", "questionanswers")				
				.createAlias("question.questiontype", "questiontype");
 				query.add(Restrictions.eq("courselecture.courselectureid",lectureid.get(0)));
				query.add(Restrictions.eq("questiontype.id",typeId));
				query.add(Restrictions.eq("questionanswers.status",'A'));
				if(mark != 0)
					query.add(Restrictions.eq("question.mark",mark));
				query.add(Restrictions.eq("question.status",'A'));
				query.addOrder(order);
		 		query.setProjection(Projections.projectionList()
		 			.add(Projections.property("question.id").as("questionid"))
		 			.add(Projections.property("question.mark").as("mark"))
		 			.add(Projections.property("question.question").as("question"))
		 			.add(Projections.property("questiontype.id").as("id"))
		 			.add(Projections.property("questionanswers.id").as("answerid"))
		 			.add(Projections.property("questionanswers.answers").as("answers")));
		 		questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		 			 		
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getQuestions()",e);
		}
	satQuestionServiceLogger.info("question VALUE "+questionType);
		return questionType;
	}
	
	/**
	 * @return Type Of Question List For Evaluation
	 */
	public List<?> getEvaluation(int lectureid){
		List<?> evaluation = null;
		Criteria query = null;
	try {
		Order order = Order.desc("this.id");
		query = getCurrentSession().createCriteria(Questionconnector.class)
				.createAlias("question", "question")
				.createAlias("courselecture", "courselecture")
				.createAlias("question.questiontype", "questiontype")
				.createAlias("question.qustionanswers", "questionanswers");				
				query.add(Restrictions.eq("courselecture.courselectureid", lectureid));
				query.add(Restrictions.eq("questionanswers.status",'A'));
				query.add(Restrictions.eq("question.status",'A'));
				query.addOrder(order);
		 		query.setProjection(Projections.projectionList()
		 				
		 			.add(Projections.property("question.id").as("questionid"))
		 			.add(Projections.property("question.question").as("question"))
		 			.add(Projections.property("questiontype.id").as("id"))
 		 			.add(Projections.property("questionanswers.answers").as("answers"))
		 			.add(Projections.property("questionanswers.correctans").as("anscid"))
		 			.add(Projections.property("questionanswers.id").as("answerid")));
		 		evaluation = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		 			 		
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getEvaluation()",e);
		}
		return evaluation;
	}
	
	
	/**
	 * @return Type Of Question List
	 */
	public List<?> getQuestionType(){
		List<?> questionType = null;
		Criteria query = null;
	try {
		satQuestionServiceLogger.info("Get Question Types");
		query = getCurrentSession().createCriteria(Questiontype.class);
		 		query.add(Restrictions.eq("this.status", 'A'));
		 		query.setProjection(Projections.projectionList()
		 			 .add(Projections.property("this.id").as("id"))
		 			 .add(Projections.property("this.questiontype").as("questiontype")));
		 		questionType = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getQuestionType()",e);
		}
		return questionType;
	}
	
	/**
	 * 
	 * @param questionid
	 * @return List of Student answer status
	 */
	public List <?> getStudentquizans(String questionid){
		List<?> quizstdans = null;
		try {
			quizstdans=getCurrentSession().createSQLQuery("select studentanswer,questionid from evaluation where questionid in ("+questionid+");")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				satQuestionServiceLogger.error("error in ConnectionService in getQuestionID()",e);
			}
			return quizstdans;
	}
	
	/**
	 * 
	 * @param lessionid
	 * @return get chapter list
	 */
	public List <?> getChapter(String getChapter){
		List<?> chapter = null;
		try {
			chapter=getCurrentSession().createSQLQuery("select a.courselecturetitle,a.courselectureid from courselecture a,sectionconnect b where b.lessonid='"+getChapter+"' and b.coursesectionid=a.coursesectionid and a.courselecturestatus='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				satQuestionServiceLogger.error("error in ConnectionService in getQuestionID()",e);
			}
			return chapter;
	}
	
	/**
	 * 
	 * @param lessionid
	 * @return get breadcrumb data list
	 */
	public List <?> getBreadCrumb(String lessionId){
		List<?> bread = null;
		try {
			bread=getCurrentSession().createSQLQuery("select DISTINCT c.coursesectiontitle,d.coursetitle,e.coursecategoryname,f.boardname from courselecture a,sectionconnect b,coursesection c,course d,coursecategory e, board f where b.lessonid='"+lessionId+"' and b.coursesectionid=a.coursesectionid and c.coursesectionid=a.coursesectionid and d.courseid=c.courseid and e.coursecategoryid=d.coursecategoryid and f.boardid=e.boardid and a.courselecturestatus='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				satQuestionServiceLogger.error("error in ConnectionService in getQuestionID()",e);
			}
		return bread;
	}
	
	/**
	 * @return Type Of Question ID List
	 */
	public List<?> getQuestionID(int studentid,int lectureid ){
		
		List<?> questionid = null;
		try {
			questionid=getCurrentSession().createSQLQuery("select b.id,c.answerstate from questionconnector a,question b " +
					"left outer join evaluation c on c.questionid=b.id and c.orgpersonid='"+studentid+"'  left outer join questionanswers d on c.answerid=d.id and d.status='A' " +
					"where a.courselectureid='"+lectureid+"' and a.question_id =b.id and b.status='A' order by a.id asc")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				satQuestionServiceLogger.error("error in ConnectionService in getQuestionID()",e);
			}
			return questionid;
 	}
	/**
	 * 
	 * @param studentid
	 * @param lectureid
	 * @return
	 */
	public List <?>  getSATSatus(int studentid, String lectureids ){
		List<?>  questionid =  new ArrayList ();
		try {
			questionid = getCurrentSession()
					.createSQLQuery(
							"select sum(case when c.answerstate = 'C' then 1 else 0 end) as correct,sum(case when c.answerstate = 'W' then 1 else 0 end) as wrong,sum(case when c.answerstate IS NULL then 1 else 0 end) as skip from questionconnector a,question b left outer join evaluation c on c.questionid=b.id and c.orgpersonid='"+studentid+"'  left outer join questionanswers d on c.answerid=d.id and d.status='A' where a.courselectureid in ("+lectureids+") and a.question_id =b.id and b.status='A' group by a.courselectureid  order by a.id asc")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}catch (Exception e) {
				satQuestionServiceLogger.error("error in ConnectionService in getSATSatus()",e);
			}
			return questionid;
	}
	
	/**
	 * @return Type Of Question List For student answer
	 */
	public List<?> getSTDAnswer(int questionid,int orgPersonid){
		
		List<?> studentAns = null;
		try {
			
			studentAns = getCurrentSession()
					.createSQLQuery(
							"select d.id as qtypeid,a.id as questionid,a.question,c.answers,c.correctans as anscid,c.id as answerid,c.answerDescription,b.id as	evaluationid,b.attempt,b.studentanswer,b.answerstate from question a left outer join evaluation b on a.id=b.questionid and b.orgpersonid='"
									+ orgPersonid
									+ "' ,questionanswers c,questiontype d where a.id='"
									+ questionid
									+ "' and  a.id=c.question_id and d.id=a.questiontype_id and a.status='A' and c.status='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
 		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getSTDAnswer()",e);
		}
	return studentAns;
	}
	/**
	 * @return Type Of student result list
	 */
	public List<?> getSTDResult(int studentid,int lectureid){
		List<?> studentReslut = null;
	try {
		studentReslut=getCurrentSession().createSQLQuery("select c.takentime ,c.answerstate,c.attempt from questionconnector a,question b " +
				"left outer join evaluation c on c.questionid=b.id and c.orgpersonid='"+studentid+"'  left outer join questionanswers d on c.answerid=d.id and d.status='A' " +
				"where a.courselectureid='"+lectureid+"' and a.question_id =b.id and b.status='A' order by a.id asc")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getSTDResult()",e);
		}
		return studentReslut;
	}
	
	/**
	 * @return Type Of student result list
	 */
	public List<?> checkFirstTime(int studentid,int lectureid){
		List<?> takentime = null;
	try {
		takentime=getCurrentSession().createSQLQuery("select  distinct c.takentime,c.id,c.answerstate from questionconnector a,question b,evaluation c ,questionanswers d where " +
				"d.status='A' and c.questionid=b.id and c.orgpersonid='"+studentid+"' and a.courselectureid='"+lectureid+"' " +
				"and a.question_id =b.id and b.status='A'")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in checkFirstTime()",e);
		}
		return takentime;
	}
	
	/**
	 * @return Type Of student result list
	 */
	public int updateTekenTime(String id,String time){
		int status=0;
	try {
		status = getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.sat.Evaluation set takentime='"+time+"' where id='"+id+"'").executeUpdate();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in updateTekenTime()",e);
		}
		return status;
	}
	
	/**
	 * @return Type Of Question List
	 */
	public List<?> getMetatag(int questionid){
		List<?> questionmeta = null;
	try {
		questionmeta=getCurrentSession().createSQLQuery("select id,createdby,dateofcreation from questionmeta where question_id='"+questionid+"'")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getMetatag()",e);
		}
		return questionmeta;
	}
	
	/**
	 * @return Type Of Question List
	 */
	public List<?> questionAnswer(int questionid){
		List<?> questionanswer = null;
	try {
		questionanswer=getCurrentSession().createSQLQuery("select id from questionanswers where question_id='"+questionid+"'")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in questionAnswer()",e);
		}
		return questionanswer;
	}
	
	/**
	 * @return Get Correct answer
	 */
	public List<?> getCrectAns(int questionid){
		List<?> questionmeta = null;
	try {
		questionmeta=getCurrentSession().createSQLQuery("select correctans from questionanswers where question_id='"+questionid+"'")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getCrectAns()",e);
		}
		return questionmeta;
	} 
	
	/**
	 * @return Get Correct answer
	 */
	public List<?> getQuestionAns(String questionid){
		List<?> qstAns = null;
	try {
		qstAns=getCurrentSession().createSQLQuery("select question_id,answers from questionanswers where question_id in ("+questionid+")  and correctans !=0 and status='A'")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getCrectAns()",e);
		}
		return qstAns;
	} 
	 
	public List<?> getIlligalChar(String illicaltype){
		List<?> illigal = null;
	try {
		String query="SELECT id,question as illigalchar FROM question where question like '%<!--[if gte mso 9]>%';";
		if(illicaltype.equals("answer"))
			query="SELECT id,answers as illigalchar FROM questionanswers where answers like '%<!--[if gte mso 9]>%';";
		else if(illicaltype.equals("description"))
			query="SELECT id,answerDescription as illigalchar FROM questionanswers where answerDescription like '%<!--[if gte mso 9]>%';";
		
		illigal=getCurrentSession().createSQLQuery(query)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in getCrectAns()",e);
		}
		return illigal;
	} 
	 
	public int updategetilligal(String illigalchar,String id,String type){
		int status=0;
	try {
		String query="update com.igrandee.product.satutil.model.SatQuestion set question='"+illigalchar+"' where id='"+id+"'";
		
		if(type.equals("answer"))
			query="update com.igrandee.product.satutil.model.Qustionanswer set answers='"+illigalchar+"' where id='"+id+"'";
		else if(type.equals("description"))
			query="update com.igrandee.product.satutil.model.Qustionanswer set answerDescription='"+illigalchar+"' where id='"+id+"'";
		
		System.out.println("##################### ILLIGAUL CHAR ################################# ");
		System.out.println("");
		System.out.println("ILLIGAL CHAR QUERY :  "+query);
		System.out.println("");
		System.out.println("##################### ILLIGAUL CHAR ################################# ");
		
		status = getCurrentSession().createQuery(query).executeUpdate();
		}catch (Exception e) {
			satQuestionServiceLogger.error("error in ConnectionService in updateTekenTime()",e);
		}
		return status;
	}
	
	
	
}
