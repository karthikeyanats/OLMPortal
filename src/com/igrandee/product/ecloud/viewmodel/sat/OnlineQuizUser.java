/**
 * 
 */
package com.igrandee.product.ecloud.viewmodel.sat;

import java.io.Serializable;

/**
 * @author selvarajan_j
 *
 */
public class OnlineQuizUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int sessionid;
	int questionid;
	int teacherid;
	String student[];
	/**
	 * @return the sessionid
	 */
	public int getSessionid() {
		return sessionid;
	}
	/**
	 * @param sessionid the sessionid to set
	 */
	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}
	/**
	 * @return the questionid
	 */
	public int getQuestionid() {
		return questionid;
	}
	/**
	 * @param questionid the questionid to set
	 */
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
	/**
	 * @return the teacherid
	 */
	public int getTeacherid() {
		return teacherid;
	}
	/**
	 * @param teacherid the teacherid to set
	 */
	public void setTeacherid(int teacherid) {
		this.teacherid = teacherid;
	}
	/**
	 * @return the student
	 */
	public String[] getStudent() {
		return student;
	}
	/**
	 * @param student the student to set
	 */
	public void setStudent(String[] student) {
		this.student = student;
	}
}
