/**
 * 
 */
package com.igrandee.product.ecloud.viewmodel.sat;

import java.io.Serializable;
import java.util.ArrayList;
 

/**
 * @author selvarajan_j
 *
 */
public class ExamVM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private String lecture[];
	private ArrayList<?> lecture;
	private int typeId=0;
	private int mark=0;
	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}
	/**
	 * @param mark the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}
	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		System.out.println("Print typeId GET "+typeId);
		return typeId;
	}
	/**
	 * @return the lecture
	 */
	public ArrayList<?> getLecture() {
		System.out.println("Print GET "+lecture);
		return lecture;
		
	}
	/**
	 * @param lecture the lecture to set
	 */
	public void setLecture(ArrayList<?> lecture) {
		System.out.println("Print SET "+lecture);
		this.lecture = lecture;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(int typeId) {
		System.out.println("Print typeId SET "+typeId);
		this.typeId = typeId;
	}
}
