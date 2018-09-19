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
 * ecloudbaseweb
 * assets/app/js/users
 * calculatetimespend.js
 * Created Aug 30, 2014 
 * by  mahalingam_a
 */


/* EVENTS HANDLING - CAPTURING USER-TIMINGS IN LECTURES
 * ================================================================================
 */
var sttime,entime,_lecstartTime,_lecendTime,_lectimeSpent;

function getLectureEndTime(){

	var endtime=new Date($.now());
	var hrs=endtime.getHours();
	var mins=endtime.getMinutes();
	var secs=endtime.getSeconds();
	entime=hrs+":"+mins+":"+secs;
	return hrs+":"+mins+":"+secs;
}

function getLectureStartTime(){

	var starttime=new Date($.now());
	var hrs=starttime.getHours();
	var mins=starttime.getMinutes();
	var secs=starttime.getSeconds();
	sttime=hrs+":"+mins+":"+secs;
	return hrs+":"+mins+":"+secs;
}

function calculateLectureTimeSpend(){

	_lecstartTime=$("#contentlecstarttime").attr("starttime");
	if(_lecstartTime!=undefined){
		_lecendTime=$("#contentlecendtime").attr("endtime");
		if(_lecendTime!=undefined) {
			_lectimeSpent=getTimeSpent(sttime,entime);
		}
	}
}

function insertTimeSpend(_courseSectionId,_courseLectureId){
	var _courseEnrollmentId = $(".lecturestats").attr("courseenrollmentid");
	var learnCourseLectureContentPlayerOptionsViewModel = {
			startTime 				: _lecstartTime,
			endTime 				: _lecendTime,
			mediaDuration			: _lecendTime,
			timeSpent				: _lectimeSpent,
			learningStatus			: "learning", 
			examStatus				: null,
			marksSecured			: null, 
			courseLectureId		: _courseLectureId,
			courseSectionId		: _courseSectionId,
			courseenrollmentid 	: _courseEnrollmentId,
			learningstatisticsid 	: null
	};

	_learningStatics  = Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/learnCourseStatistics/findAndGetLearnigStatisticsList",learnCourseLectureContentPlayerOptionsViewModel,"json", "application/json; charset=utf-8");

	var _assertValue = null;
	if( Object.prototype.toString.call( _learningStatics ) === '[object Array]' ) {
		_assertValue = _learningStatics[0].learningstatisticsid;  
	} else{
		_assertValue = _learningStatics.learningstatisticsid; 
	}  

	if(_assertValue != undefined && _assertValue > 0 && _assertValue != 0){
		_learningStaticsServerID = _assertValue;
	}else { 
		_learningStaticsServerID = 0;
	} 
	if( _learningStaticsServerID == 0){
		Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/learnCourseStatistics/newLearningStatistics",learnCourseLectureContentPlayerOptionsViewModel,"json", "application/json; charset=utf-8");
	}else if( _learningStaticsServerID > 0){
		var _tottimespent,completedtime;
		var _LearningStatisticsServerObject =   _learningStatics; 
		if(_LearningStatisticsServerObject[0].timespent!=undefined)
			_tottimespent=calculateWithExistTime(_LearningStatisticsServerObject[0].timespent,_lectimeSpent);
		else
			_tottimespent=_lectimeSpent;

		var _learnCourseLectureContentPlayerOptionsViewModel = {
				learningstatisticsid 	:  _LearningStatisticsServerObject[0].learningstatisticsid,
				startTime 				:  _LearningStatisticsServerObject[0].starttime,
				endTime 				:  _LearningStatisticsServerObject[0].endtime,
				mediaDuration			:  _LearningStatisticsServerObject[0].learningstatus, 
				timeSpent				:  _tottimespent,
				completedtime			: _LearningStatisticsServerObject[0].completedtime,							 
				learningStatus			:  _LearningStatisticsServerObject[0].learningstatus, 
				examStatus				:  _LearningStatisticsServerObject[0].examstatus,
				marksSecured			:  _LearningStatisticsServerObject[0].markssecured, 
				courseLectureId		:  _LearningStatisticsServerObject[0].courselectureid,
				courseSectionId		:  _LearningStatisticsServerObject[0].coursesectionid,
				courseenrollmentid 	:  _LearningStatisticsServerObject[0].courseenrollmentid,
				materialdownloadstatus 	:  _LearningStatisticsServerObject[0].materialdownloadstatus,
		};
		var _updatedTimeSpent = Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/learnCourseStatistics/updateALearningStatistics",_learnCourseLectureContentPlayerOptionsViewModel,"json", "application/json; charset=utf-8");
	} 
}

function calculateWithExistTime(existhrs,currenthrs){	
	curtime=currenthrs.split(/\D/);
	exttime=existhrs.split(/\D/);
	var x1=parseInt(curtime[0])*60*60 + parseInt(curtime[1])*60 + parseInt(curtime[2]);
	var x2=parseInt(exttime[0])*60*60 + parseInt(exttime[1])*60 + parseInt(exttime[2]);
	var s=x1+x2;
	var m=Math.floor(s/60); s=s%60;
	var h=Math.floor(m/60); m=m%60;
	var result = [
	              h , // HOURS		 
	              m, // MINUTES			  		   
	              s // SECONDS
	              ];
	result = result.map(function(v) {
		return v < 10 ? '0' + v : v;
	}).join(':');
	return result;
}

function toSeconds(time_str) {
	var parts = time_str.split(':');
	return parts[0] * 3600 + // an hour has 3600 seconds
	parts[1] * 60 +   // a minute has 60 seconds
	+parts[2];        // seconds
}

function getTimeSpent(sttime,entime){
	var difference = Math.abs(toSeconds(sttime) - toSeconds(entime));
	var result = [
	              Math.floor(difference / 3600), // HOURS
	              Math.floor((difference % 3600) / 60), // MINUTES
	              difference % 60 // SECONDS
	              ];

	// formatting (0 padding and concatenation)
	result = result.map(function(v) {
		return v < 10 ? '0' + v : v;
	}).join(':');
	return result;
}
