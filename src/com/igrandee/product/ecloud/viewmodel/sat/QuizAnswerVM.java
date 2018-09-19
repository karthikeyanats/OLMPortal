/**
 * 
 */
package com.igrandee.product.ecloud.viewmodel.sat;

import java.io.Serializable;

/**
 * @author selvarajan_j
 *
 */
public class QuizAnswerVM implements Serializable{
	
	private static final long serialVersionUID = 1L;

	int studentid;
	String ansstatus;
	String answerid[];
	int liveprimeryid;
	
	 
	/**
	 * @return the liveprimeryid
	 */
	public int getLiveprimeryid() {
		return liveprimeryid;
	}
	/**
	 * @param liveprimeryid the liveprimeryid to set
	 */
	public void setLiveprimeryid(int liveprimeryid) {
		this.liveprimeryid = liveprimeryid;
	}
	/**
	 * @return the studentid
	 */
	public int getStudentid() {
		return studentid;
	}
	/**
	 * @param studentid the studentid to set
	 */
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	/**
	 * @return the ansstatus
	 */
	public String getAnsstatus() {
		return ansstatus;
	}
	/**
	 * @param ansstatus the ansstatus to set
	 */
	public void setAnsstatus(String ansstatus) {
		this.ansstatus = ansstatus;
	}
	/**
	 * @return the answerid
	 */
	public String[] getAnswerid() {
		return answerid;
	}
	/**
	 * @param answerid the answerid to set
	 */
	public void setAnswerid(String[] answerid) {
		this.answerid = answerid;
	} 
}
