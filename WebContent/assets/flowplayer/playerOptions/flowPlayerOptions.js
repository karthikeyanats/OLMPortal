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
 * assets/flowplayer/playerOptions
 * flowPlayerOptions.js
 * Created Jul 3, 2014 5:09:40 PM
 * by  balajichander_r
 */


/* EVENTS HANDLING - CAPTURING USER-TIMINGS IN FLOWPLAYER
 * ================================================================================
 */

	flowplayer(function(igPlayer, root) {
		
		igPlayer.bind("load", function() {
			  
				 

		}).bind("ready", function() {
			
			var _endTime =  timConverters.secondsToHH_MM_ss(igPlayer.video.duration);
			var _startTime =  timConverters.secondsToHH_MM_ss(0);
			_timeSpent =  null;
			var _courseLectureId = 0;
			var _courseEnrollmentId = 0;
			if($('.lecturestats').hasClass('complecturestats')){
				var $lectureStatsCompletedNodeElement = $('.lecturestats.complecturestats');
				_courseLectureId = $lectureStatsCompletedNodeElement.attr('courselectureid');
				_courseEnrollmentId = $lectureStatsCompletedNodeElement.attr('courseenrollmentid');
			}else if($('.lecturestats').hasClass('ncomplecturestats')){
				var $lectureStatsNCompletedElement = $('.lecturestats.ncomplecturestats');
				_courseLectureId = $lectureStatsNCompletedElement.attr('courselectureid');
				_courseEnrollmentId = $lectureStatsNCompletedElement.attr('courseenrollmentid');
			}
			var _courseSectionId = null; 
			for(var i=0;i<window.document.getElementsByClassName('lecturetitle').length; i++ ){
				var $nodeElement = window.document.getElementsByClassName('lecturetitle')[i]; 
				if($nodeElement.getAttribute('courselectureid') == _courseLectureId ){
					_courseSectionId = $nodeElement.getAttribute('coursesectionid'); 
				}
			} 
			var __learnCourseLectureContentPlayerOptionsViewModel = {
				    
				 	 startTime 				: _startTime,
					 endTime 				: _endTime,
					 mediaDuration			: _endTime, 
					 timeSpent				: _timeSpent,
					 learningStatus			: "learning", 
					 examStatus				: null,
					 marksSecured			: null, 
					 orgPersonId			: null,
					 courseLectureId		: _courseLectureId,
					 courseSectionId		: _courseSectionId,
					 courseenrollmentid 	: _courseEnrollmentId,
					 learningstatisticsid 	: null
				};
			var _learningStatics  = Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/learnCourseStatistics/findAndGetLearnigStatisticsList",__learnCourseLectureContentPlayerOptionsViewModel,"json", "application/json; charset=utf-8");
			var _assertValue = null;
			if( Object.prototype.toString.call( _learningStatics ) === '[object Array]' ) {
				_assertValue = _learningStatics[0].learningstatisticsid;  
			} else{
				_assertValue = _learningStatics.learningstatisticsid; 
			}  
			if(_assertValue != undefined && _assertValue > 0 && _assertValue != 0){
				var timeout = 1000;
				setTimeout(function(){
					if(timConverters.HH_MM_ssToSeconds(_learningStatics[0].timespent) > 0 & timConverters.HH_MM_ssToSeconds(_learningStatics[0].timespent) != null & typeof timConverters.HH_MM_ssToSeconds(_learningStatics[0].timespent) != 'undefined' ){ 
						for(var i=0;i< _igPlayerSeekPoints.length; i++){
							var _a = Math.round(parseFloat(_igPlayerSeekPoints[i].time)/60);
							var _b = Math.round(parseFloat(timConverters.HH_MM_ssToSeconds(_learningStatics[0].timespent)/60)) ; 
							 
						 if(  _a == _b ) {
							 
							 igPlayer.seekTo((parseFloat(_igPlayerSeekPoints[i].time)/(360)).toFixed(2)); 
							 console.warn((parseFloat("SEEKED TO " + _igPlayerSeekPoints[i].time)/(360)).toFixed(2));
							 return;
						 } 
						}  
					}
					
				}, timeout);
			 }else { 
				  return;
			 }
			igPlayer = flowplayer(); 
			igPlayer.play().pause;  
			_igPlayerSeekPoints = igPlayer.video.seekpoints;
			 	   
		}).bind("click", function(e){
			
			if(! igPlayer.paused){
				
				igPlayerVideoSourceObject = igPlayer.video; 
				playerPausedTime = (igPlayerVideoSourceObject.time);   
				var _endTime =  timConverters.secondsToHH_MM_ss(igPlayer.video.duration);
				var _startTime =  timConverters.secondsToHH_MM_ss(0);
				_timeSpent =  timConverters.secondsToHH_MM_ss(playerPausedTime);
				var _courseLectureId = window.document.getElementsByClassName('btn btn-flat btn-gray lecturestats complecturestats')[0].getAttribute('courselectureid');
				var _courseEnrollmentId = window.document.getElementsByClassName('btn btn-flat btn-gray lecturestats complecturestats')[0].getAttribute('courseenrollmentid');
				var _courseSectionId = null;
				
				for(var i=0;i<window.document.getElementsByClassName('lecturetitle').length; i++ ){
					var $nodeElement = window.document.getElementsByClassName('lecturetitle')[i]; 
					if($nodeElement.getAttribute('courselectureid') == _courseLectureId ){
						_courseSectionId = $nodeElement.getAttribute('coursesectionid'); 
					}
				} 
				var learnCourseLectureContentPlayerOptionsViewModel = {
					    
					 	 startTime 				: _startTime,
						 endTime 				: _endTime,
						 mediaDuration			: _endTime, 
						 timeSpent				: _timeSpent,
						 learningStatus			: "learning", 
						 examStatus				: null,
						 marksSecured			: null, 
						 orgPersonId			: null,
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
					//new insert 
					Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/learnCourseStatistics/newLearningStatistics",learnCourseLectureContentPlayerOptionsViewModel,"json", "application/json; charset=utf-8");
					
				}else if( _learningStaticsServerID > 0){
					
					//update exising using learningstatisticsid  
					var _LearningStatisticsServerObject =   _learningStatics; 
					
					var _learnCourseLectureContentPlayerOptionsViewModel = {
							 learningstatisticsid 	:  _LearningStatisticsServerObject[0].learningstatisticsid,
						 	 startTime 				:  _LearningStatisticsServerObject[0].starttime,
							 endTime 				:  _LearningStatisticsServerObject[0].endtime,
							 mediaDuration			:  _LearningStatisticsServerObject[0].endtime, 
							 timeSpent				:  _timeSpent,
							 learningStatus			:  _LearningStatisticsServerObject[0].learningstatus, 
							 examStatus				:  _LearningStatisticsServerObject[0].examstatus,
							 marksSecured			:  _LearningStatisticsServerObject[0].markssecured, 
							 orgPersonId			:  _LearningStatisticsServerObject[0].orgpersonid,
							 courseLectureId		:  _LearningStatisticsServerObject[0].courselectureid,
							 courseSectionId		:  _LearningStatisticsServerObject[0].coursesectionid,
							 courseenrollmentid 	:  _LearningStatisticsServerObject[0].courseenrollmentid,
							 
						};
					 
					var _updatedTimeSpent = Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/learnCourseStatistics/updateALearningStatistics",_learnCourseLectureContentPlayerOptionsViewModel,"json", "application/json; charset=utf-8");
					playerPausedTime = timConverters.HH_MM_ssToSeconds(_updatedTimeSpent.timespent) /60;
					cookie.prepareDoughThenShapeAndBake("playerPausedTime", playerPausedTime, 2);
				} 
				 
			}
			
		}).bind("playing", function() {
			var remainingCookie = cookie.serveAndEatBakedCookies("playerPausedTime");  
			igPlayer = flowplayer(); 
			_igPlayerSeekPoints = igPlayer.video.seekpoints; 
			igPlayer.play();  
			var timeout = 1000;
			setTimeout(function(){
				if(remainingCookie > 0 & remainingCookie != null){ 
					for(var i=0;i< _igPlayerSeekPoints.length; i++){
						var _a = Math.round(parseFloat(_igPlayerSeekPoints[i].time)/60);
						var _b = Math.round(parseFloat(remainingCookie)) ; 
					 if(  _a == _b ) {
						 igPlayer.seekTo((parseFloat(_igPlayerSeekPoints[i].time)/(360)).toFixed(2)); 
						 console.warn((parseFloat("SEEKED TO " + _igPlayerSeekPoints[i].time)/(360)).toFixed(2));
						 return;
					 }
						 
					}  
				}
				
			}, timeout);
		}) ;

   });

//COokIe  
 var cookie = {
		 prepareDoughThenShapeAndBake : function (cookieName , cookieIngridients, noOfPreservativedays ){
			 var cookieDateTime = new Date;
			 cookieDateTime.setTime(cookieDateTime.getTime()+(noOfPreservativedays*24*60*60*1000));
			 var _expires = " expires = " + cookieDateTime.toGMTString();
			 document.cookie = cookieName + " = " + cookieIngridients + " ; " + _expires;
			 return;
		 },
		 
		 serveAndEatBakedCookies : function(cookieName) {
			 	var whatCookie = cookieName + "=";
				var cookieChunks = document.cookie.split(';');
				for(var i=0;i < cookieChunks.length;i++) {
					var chunk = cookieChunks[i];
					while (chunk.charAt(0)==' '){ 
						chunk = chunk.substring(1,chunk.length);
						};
					if (chunk.indexOf(whatCookie) == 0){ 
						return chunk.substring(whatCookie.length,chunk.length);
					}
				} 
			return; 
		},
		
		disposeRottenCookies : function(cookieName) {
			this.prepareDoughShapeAndBake(cookieName, "" , -1);
			return;
		}
};
 
//tIME  
 var timConverters = {

	secondsToNumberOfhours: function(timeInSeconds) {
			return Math.floor(parseInt(timeInSeconds, 10)/3600);
	},
	secondsToRemainingMinutes : function(timeInSeconds){
		return Math.floor(( (parseInt(timeInSeconds, 10)) - this.secondsToNumberOfhours(timeInSeconds))/60 );
	},
	secondsRemaining : function(timeInSeconds) {
		return (parseInt(timeInSeconds, 10)) - (this.secondsToNumberOfhours(timeInSeconds) * 3600 ) - (this.secondsToRemainingMinutes(timeInSeconds * 60 ) );
	},
	secondsToHH_MM_ss : function(timeInSeconds){
		var hours = this.secondsToNumberOfhours(timeInSeconds );
		var mins = this.secondsToRemainingMinutes(timeInSeconds);
		var secs = this.secondsRemaining(timeInSeconds);
		
		if (hours   < 10) {hours   = "0"+hours;}
	    if (mins < 10) {mins = "0"+mins;}
	    if (secs < 10) {secs = "0"+secs;}
	    
	    return hours+':'+mins+':'+secs;
	},
	HH_MM_ssToSeconds :function(timeInHH_MM_ss){
		
		var _timeArray =  timeInHH_MM_ss.split(':');
		var _seconds = _timeArray[2];
		var _minutes = 0;
		var _hours = 0;
		if(_timeArray.length == 1){
			_seconds = parseInt(_timeArray[0], 10);
			_minutes = 0;
			_hours = 0;
		}else if (_timeArray.length == 2){
			_seconds = parseInt(_timeArray[1], 10);
			_minutes = parseInt(_timeArray[0], 10); 
			_hours = 0;
		}else if (_timeArray.length == 3){
			_seconds = parseInt(_timeArray[2], 10);
			_minutes = parseInt(_timeArray[1], 10); 
			_hours = parseInt(_timeArray[0], 10); 
		} 
		return _totalSeconds =  (_hours*3600) + (_minutes*60) + (_seconds);  
	}

};