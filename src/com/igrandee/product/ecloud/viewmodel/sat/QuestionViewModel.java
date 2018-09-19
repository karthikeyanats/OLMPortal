/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloud-blogutil 
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.viewmodel
 * CategoryViewModel.java
 * Created Jul 22, 2014 6:10:11 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.sat;

import java.io.Serializable;
import java.util.List;

/**
 * @author satheeskumaran_ds
 * 
 */
public class QuestionViewModel implements Serializable {

	/**
	 * Auto Generated default serial version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;
	
	private String question;
	private char status='A';
	private int questiontypeid=0;
	private String[] answer;
	private int difficultyfactor;
	private int mark;
	private int noofoccurance;
	private String lifetime;
	private String answerDescription;
	private String[] correctAnswer;
	private int questionid;
	private List<?> deleteAnswer;
	private String entryType;
	private String qstcategory;
	
	/**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}
	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	private String[] answerid;
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
	/**
	 * @return the update_new_answer
	 */
	public List<?> getDeleteAnswer() {
		return deleteAnswer;
	}
	/**
	 * @param update_new_answer the update_new_answer to set
	 */
	public void setDeleteAnswer(List<?> deleteAnswer) {
		this.deleteAnswer = deleteAnswer;
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
	private int courselectureid;
	
	/**
	 * @return the courselectureid
	 */
	public int getCourselectureid() {
		return courselectureid;
	}
	/**
	 * @param courselectureid the courselectureid to set
	 */
	public void setCourselectureid(int courselectureid) {
		this.courselectureid = courselectureid;
	}
	/**
	 * @return the qstcategory
	 */
	public String getQstcategory() {
		return qstcategory;
	}
	/**
	 * @param qstcategory the qstcategory to set
	 */
	public void setQstcategory(String qstcategory) {
		this.qstcategory = qstcategory;
	}
	/**
	 * @return the correctAnswer
	 */
	public String[] getCorrectAnswer() {
		return correctAnswer;
	}
	/**
	 * @param correctAnswer the correctAnswer to set
	 */
	public void setCorrectAnswer(String[] correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the status
	 */
	public char getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(char status) {
		this.status = status;
	}
	/**
	 * @return the questiontypeid
	 */
	public int getQuestiontypeid() {
		return questiontypeid;
	}
	/**
	 * @param questiontypeid the questiontypeid to set
	 */
	public void setQuestiontypeid(int questiontypeid) {
		this.questiontypeid = questiontypeid;
	}
	/**
	 * @return the answer
	 */
	public String[] getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}
	/**
	 * @return the difficultyfactor
	 */
	public int getDifficultyfactor() {
		return difficultyfactor;
	}
	/**
	 * @param difficultyfactor the difficultyfactor to set
	 */
	public void setDifficultyfactor(int difficultyfactor) {
		this.difficultyfactor = difficultyfactor;
	}
	
	/**
	 * @return the noofoccurance
	 */
	public int getNoofoccurance() {
		return noofoccurance;
	}
	/**
	 * @param noofoccurance the noofoccurance to set
	 */
	public void setNoofoccurance(int noofoccurance) {
		this.noofoccurance = noofoccurance;
	}
	/**
	 * @return the lifetime
	 */
	public String getLifetime() {
		return lifetime;
	}
	/**
	 * @param lifetime the lifetime to set
	 */
	public void setLifetime(String lifetime) {
		this.lifetime = lifetime;
	}
	/**
	 * @return the answerDescription
	 */
	public String getAnswerDescription() {
		return answerDescription;
	}
	/**
	 * @param answerDescription the answerDescription to set
	 */
	public void setAnswerDescription(String answerDescription) {
		this.answerDescription = answerDescription;
	}
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
	
}

