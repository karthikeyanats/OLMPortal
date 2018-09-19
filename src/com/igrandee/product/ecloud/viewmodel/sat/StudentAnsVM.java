/**
 * 
 */
package com.igrandee.product.ecloud.viewmodel.sat;

/**
 * @author selvarajan_j
 *
 */
public class StudentAnsVM {

	
	private String process;
	private int updateid;
	private int answer;
	private char ansstatus;
	private int questionid;
	private int answerid;
	private int attempt;
	
	/**
	 * @return the attempt
	 */
	public int getAttempt() {
		return attempt;
	}
	/**
	 * @param attempt the attempt to set
	 */
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	/**
	 * @return the answerid
	 */
	public int getAnswerid() {
		return answerid;
	}
	/**
	 * @param answerid the answerid to set
	 */
	public void setAnswerid(int answerid) {
		this.answerid = answerid;
	}
	/**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}
	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}
	/**
	 * @return the updateid
	 */
	public int getUpdateid() {
		return updateid;
	}
	/**
	 * @param updateid the updateid to set
	 */
	public void setUpdateid(int updateid) {
		this.updateid = updateid;
	}
	
	/**
	 * @return the ansstatus
	 */
	public char getAnsstatus() {
		return ansstatus;
	}
	/**
	 * @param ansstatus the ansstatus to set
	 */
	public void setAnsstatus(char ansstatus) {
		this.ansstatus = ansstatus;
	}
	
	/**
	 * @return the answer
	 */
	public int getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	/**
	 * @return the qustionid
	 */
	public int getQuestionid() {
		return questionid;
	}
	/**
	 * @param qustionid the qustionid to set
	 */
	public void setQuestionid(int qustionid) {
		this.questionid = qustionid;
	}
}
