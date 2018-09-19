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
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.viewmodel
 * LearnCourseLectureContentPlayerOptionsViewModel.java
 * Created Jul 10, 2014 10:35:59 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * @author balajichander_r
 *
 */

@Component
public class LearnCourseLectureContentPlayerOptionsViewModel implements Serializable {
	
	
	
	
	/**
	 * Auto generated default serial version Id stub
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;
	
	private String startTime;
	private String endTime;
	private String mediaDuration;

	private String timeSpent;
	private Timestamp completedtime;	
	private String learningStatus;

	private String examStatus;
	private String marksSecured;

	private String orgPersonId;
	private String courseLectureId;
	private String courseSectionId;
	private String courseenrollmentid;
	private String learningstatisticsid;
	private String materialdownloadstatus;
	
	
	
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the mediaDuration
	 */
	public String getMediaDuration() {
		return mediaDuration;
	}
	/**
	 * @param mediaDuration the mediaDuration to set
	 */
	public void setMediaDuration(String mediaDuration) {
		this.mediaDuration = mediaDuration;
	}
	/**
	 * @return the timeSpent
	 */
	public String getTimeSpent() {
		return timeSpent;
	}
	/**
	 * @param timeSpent the timeSpent to set
	 */
	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}
	/**
	 * @return the timeSpent
	 */
	public Timestamp getCompletedtime() {
		return completedtime;
	}
	/**
	 * @param timeSpent the timeSpent to set
	 */
	public void setCompletedTime(Timestamp completedTime) {
		this.completedtime = completedTime;
	}
	/**
	 * @return the learningStatus
	 */
	public String getLearningStatus() {
		return learningStatus;
	}
	/**
	 * @param learningStatus the learningStatus to set
	 */
	public void setLearningStatus(String learningStatus) {
		this.learningStatus = learningStatus;
	}
	/**
	 * @return the examStatus
	 */
	public String getExamStatus() {
		return examStatus;
	}
	/**
	 * @param examStatus the examStatus to set
	 */
	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}
	/**
	 * @return the marksSecured
	 */
	public String getMarksSecured() {
		return marksSecured;
	}
	/**
	 * @param marksSecured the marksSecured to set
	 */
	public void setMarksSecured(String marksSecured) {
		this.marksSecured = marksSecured;
	}
	/**
	 * @return the orgPersonId
	 */
	public String getOrgPersonId() {
		return orgPersonId;
	}
	/**
	 * @param orgPersonId the orgPersonId to set
	 */
	public void setOrgPersonId(String orgPersonId) {
		this.orgPersonId = orgPersonId;
	}
	/**
	 * @return the courseLectureId
	 */
	public String getCourseLectureId() {
		return courseLectureId;
	}
	/**
	 * @param courseLectureId the courseLectureId to set
	 */
	public void setCourseLectureId(String courseLectureId) {
		this.courseLectureId = courseLectureId;
	}
	/**
	 * @return the courseSectionId
	 */
	public String getCourseSectionId() {
		return courseSectionId;
	}
	/**
	 * @param courseSectionId the courseSectionId to set
	 */
	public void setCourseSectionId(String courseSectionId) {
		this.courseSectionId = courseSectionId;
	}
	/**
	 * @return the courseenrollmentid
	 */
	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}
	/**
	 * @param courseenrollmentid the courseenrollmentid to set
	 */
	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
	/**
	 * @return the learningstatisticsid
	 */
	public String getLearningstatisticsid() {
		return learningstatisticsid;
	}
	/**
	 * @param learningstatisticsid the learningstatisticsid to set
	 */
	public void setLearningstatisticsid(String learningstatisticsid) {
		this.learningstatisticsid = learningstatisticsid;
	}
	public String getMaterialdownloadstatus() {
		return materialdownloadstatus;
	}
	public void setMaterialdownloadstatus(String materialdownloadstatus) {
		this.materialdownloadstatus = materialdownloadstatus;
	}
	 
	
}
