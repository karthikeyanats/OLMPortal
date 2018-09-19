

courseData={
		coursecontentlist:null,
		learningDetailsList:null,
		coursecontentformattedlist:null,
		formatedenrolledCoursesList:null,
		getContextPath:function(){
			return $('#context').text();
		},
		/* Standard/Level CRUD start */
		sendStandardDetails:function(standobj){
			var boardresult=Ajax.ajaxHelper("POST",this.getContextPath()+"/rest/standard",standobj,"json","application/json");
			return boardresult;
		},
		loadstandardList:function(){
			return Ajax.get(courseData.getContextPath()+"/rest/standard/A",undefined,"json","application/json");
		},
		Deletestandard:function(standardid){
			var deletedborad=Ajax.ajaxHelper("DELETE",this.getContextPath()+"/rest/standard?standardlevelid="+standardid,undefined,"json","application/json");
			return deletedborad;
		},
		updatestandard:function(standobj){
			var updateboard=Ajax.ajaxHelper("PUT",this.getContextPath()+"/rest/standard",standobj,"json","application/json");
			return updateboard;
		},
		/* Standard/Level CRUD Ends */
		/* Medium CRUD start */
		sendMediumDetails:function(mediumobj){
			var boardresult=Ajax.ajaxHelper("POST",this.getContextPath()+"/rest/medium",mediumobj,"json","application/json");
			return boardresult;
		},
		loadmediumList:function(){
			return Ajax.get(courseData.getContextPath()+"/rest/medium/A",undefined,"json","application/json");
		},
		Deletemedium:function(mediumid){
			var deletedborad=Ajax.ajaxHelper("DELETE",this.getContextPath()+"/rest/medium?mediumid="+mediumid,undefined,"json","application/json");
			return deletedborad;
		},
		updatemedium:function(mediumobj){
			var updateboard=Ajax.ajaxHelper("PUT",this.getContextPath()+"/rest/medium",mediumobj,"json","application/json");
			return updateboard;
		},
		/* Medium CRUD Ends */
		/* Board CRUD start */
		sendBoardDetails:function(boardobj){
			var boardresult=Ajax.ajaxHelper("POST",this.getContextPath()+"/rest/board",boardobj,"json","application/json");
			return boardresult;
		},
		loadboardList:function(){
			return Ajax.get(courseData.getContextPath()+"/rest/board/A",undefined,"json","application/json");			
		},
		Deleteboard:function(boardid){
			var deletedborad=Ajax.ajaxHelper("DELETE",this.getContextPath()+"/rest/board?boardid="+boardid,undefined,"json","application/json");
			return deletedborad;
		},
		updateboard:function(boardobj){
			var updateboard=Ajax.ajaxHelper("PUT",this.getContextPath()+"/rest/board",boardobj,"json","application/json");
			return updateboard;
		},
		/* Board CRUD Ends */
		sendtoMultiuser:function(dataobj){
			var result=ajaxhelperwithdata("messageToMultiuser",dataobj);
			return result;
		},
		savelectureReview:function(courseratingobj){
			var result=ajaxhelperwithdata("savelectureReview",courseratingobj);
			return result;
		},
		getSearchResults:function(keywordobj){
			var searchResults=ajaxhelperwithdata("ssearchCourses",keywordobj);
			if(searchResults!="false"){
				return searchResults;	
			}
			else{
				console.log("No data got in getSearchResults  "+keywordobj);
			}
		},
		loadReviewType:function(){
			var coursetype=ajaxhelper("getcoursetype");
			return coursetype;
		},
		getMasterAdminId:function(){
			var masterid=ajaxhelper("masterId");
			if(masterid!="false"){				
				return masterid;	
			}
			else{
				console.log("No data got in getMasterAdminId");
			}
		},
		getPopularCourses:function(){
			var popularCourses=ajaxhelper("getTopCourses");
			console.log("Dataaaaa"+popularCourses);
			if(popularCourses!="false"){
				return popularCourses;
				
			}
			else{
				console.log("No data got");
			}
		},
		getmetainfo:function(metamain){
			var metainfo=ajaxhelperwithdata("loadmetainfo",metamain);
			if(metainfo!="false"){
				return metainfo;
			}
		},
		getLearningStatuses:function(enrollobj){
			var learningStatuses=ajaxhelperwithdata("getCompletedLearningStatuses",enrollobj);
			if(learningStatuses!="false"){
				return learningStatuses;	
			}
			else{
				console.log("No data got in getLearningStatuses "+enrollobj);
			}
		},		
		checkinvitees:function(emailobj){
			var inviteeStatus=ajaxhelperwithdata("checkinvitesubscribe",emailobj);
			if(inviteeStatus!="false"){
				return inviteeStatus;	
			}
			else{
				console.log("No data got in checkinvitees "+emailobj);
			}
		},
		getGuestCategories:function(categorystatusobj){
			var categories=ajaxhelperwithdata("loadGuestCategories",categorystatusobj);
			if(categories!="false"){
				coursecategories=formater.formatCourseCategories(categories);
				return coursecategories;	
			}
			else{
				console.log("No data got"+categorystatusobj);
			}
		},
		getAllActiveCategorywiseNoofCourse:function(categorystatusobj){
			var categories=ajaxhelperwithdata("loadCategorieswiseCourse",categorystatusobj);
			if(categories!="false"){
				return categories;	
				alert(categories);
				console.log("???????????"+categories); 
			}
			else{
				console.log("No data got"+categorystatusobj);
			}
		}
		,
		getGuestCourseCategories:function(categorystatusobj){
			var categories=ajaxhelperwithdata("loadGuestCategories",categorystatusobj);
			if(categories!="false"){
				coursecategories=formater.formatCourseCategorieses(categories);

				return coursecategories;	
			}
			else{
				console.log("No data got"+categorystatusobj);
			}
		},
		getCourseCategories:function(categorystatusobj){
			var categories=ajaxhelperwithdata("loadAllValidCategories",categorystatusobj);
			if(categories!="false"){
				coursecategories=formater.formatCourseCategories(categories);
				return coursecategories;	
			}
			else{
				console.log("No data got"+categorystatusobj);
			}
		}, 
		getCaterorywiseCourses:function(categoryidobj){
			var categorycourses=ajaxhelperwithdata("loadCategoryCourses",categoryidobj);
			if(categorycourses!="false"){
				var categorywisecourses=formater.formatCategoryCourses(categorycourses);
				return categorywisecourses;
			}
			else{
				console.log("No data got"+categoryidobj);	
			}
		},
		getGuestCategoryCourses:function(categoryidobj){
			var categorycourses=ajaxhelperwithdata("loadGuestCategoryCourses",categoryidobj);
			if(categorycourses!="false"){
				var categorywisecourses=formater.formatCategoryCourses(categorycourses);
				return categorywisecourses;
			}
			else{
				console.log("No data got"+categoryidobj);	
			}
		},
		getPublishedCourses:function(courseobj){
			var publishCourses=ajaxhelperwithdata("loadPublishCourses",courseobj);
			if(publishCourses!="false"){
				return publishCourses;	
			}
			else{
				console.log("No data got"+courseobj);
			}
		},
		getOtherCourses:function(courseidobj){
			var draftsCourses=ajaxhelperwithdata("loadOtherCourses",courseidobj);
			if(draftsCourses!="false"){
				return draftsCourses;	
			}
			else{
				console.log("No data got"+courseidobj);
			}
		},		
		getOrganizationcourses:function(orgplansubscriptionid){
		
			var orgdraftcourses=	Ajax.ajaxHelper("GET",this.getContextPath()+"/rest/blog/plansubscription/orgsubscriptionplandetail?orgplansubscriptionid="+orgplansubscriptionid,undefined,"json","application/json");
			if(orgdraftcourses!="false"){
				return orgdraftcourses;	
			}
			else{
				console.log("No data got"+obj);
			}
		},
		getAuthorcourseusers:function(obj){
			var authorCourseusers=ajaxhelperwithdata("authorcourseusers",obj);
			if(authorCourseusers!="false"){
				return authorCourseusers;	
			}
			else{
				console.log("No data got in getAuthorcourseusers "+obj);
			}
		},	
		getNewpresenter:function(newpresenterdata){			
			var self=this;
			var newpresenterdetails=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/newpresenter",newpresenterdata,"json","application/json");
			if(newpresenterdetails!="false"){				
				return newpresenterdetails;	
			}
			else{
				console.log("No data got"+obj);
			}
		},
		getNewPresenterapp:function(presenterobj){
			var self=this;
			var newpresenterdetails=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/presenterapptype",presenterobj,"json","application/json");
			if(newpresenterdetails!="false"){				
				return newpresenterdetails;	
			}
			else{
				console.log("No data got"+presenterobj);
			}	
		},
		getEditPresenterapp:function(presenterobj){
			var self=this;
			var editpresenterdetails=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/editpresentertype",presenterobj,"json","application/json");
			if(editpresenterdetails!="false"){				
				return editpresenterdetails;	
			}
			else{
				console.log("No data got"+presenterobj);
			}
		},	
		getUpdatePresenterapp:function(presenterobj){
			var self=this;
			var updatepresenterdetails=Ajax.ajaxHelper("PUT",self.getContextPath()+"/rest/presenterapp/updatepresentertype",presenterobj,"json","application/json");
			if(updatepresenterdetails!="false"){				
				return updatepresenterdetails;	
			}
			else{
				console.log("No data got"+presenterobj);
			}
		},	
		//status
		getStatusPresenterapp:function(presenterobj){
			var self=this;
			var statuspresenterdetails=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/statuspresentertype",presenterobj,"json","application/json");
					if(statuspresenterdetails!="false"){				
				return statuspresenterdetails;	
			}
			else{
				console.log("No data got"+presenterobj);
			}
		},	
		deleteTrashtypelist:function(id){
			var self=this;
			var statustrashtypelist=Ajax.ajaxHelper("DELETE",self.getContextPath()+"/rest/presenterapp/presentertypelist/"+id,undefined,"json","application/json");			
			if(statustrashtypelist=="ACCEPTED"){				
				return statustrashtypelist;	
			}
			else{
				console.log(statustrashtypelist);
			}
		},
		getCheckpresenterType:function(presenterobj){
			var self=this;
			var checkpresentertype=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/checkpresentertype",presenterobj,"json","application/json");
			if(checkpresentertype!="false"){				
				return checkpresentertype;	
			}
			else{
				console.log("No data got"+presenterobj);
			}
		},		
		getCheckpresentertypeUpdate:function(presenterobj){
			var self=this;
			var checkpresentertypeupdate=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/checkpresentertypeupdate",presenterobj,"text","application/json");
			if(checkpresentertypeupdate!="false"){				
				return checkpresentertypeupdate;	
			}
			else{
				console.log("No data got"+presenterobj);
			}
		},
		getEditpresenter:function(presenterid){
			var self=this;
			var presenterdetails=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/presenterapp/editpresenterapp?presenterid="+presenterid,undefined,"json","application/json");
			var presentertype=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/presenterapp/presentertype",undefined,"json","application/json");
			if(presenterdetails!="false"){				
				return formater.formatterdownloadapp(presenterdetails,presentertype);	
			}
			else{
				console.log("No data got"+obj);
			}
		},
		getPresenterAppDetailsForId:function(presenterid){
			var self=this;
			var presenterAppDetailsForId=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/presenterappdetails_id?presenterid="+presenterid,undefined,"json","application/json");
			if(presenterAppDetailsForId!=false){
				return presenterAppDetailsForId;
			}
			else{
				console.log("No data got in getPresenterAppDetailsForId"+presenterid);
			}
		},
		getPresentertypeList:function(presenterobj){
			var self=this;
			var presentertypelist=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/presentertypelist",presenterobj,"json","application/json");
			if(presentertypelist!=false){
				return presentertypelist;
			}
			else{
				console.log("No data got in getPresentertypeList"+presenterobj);
			}
		},
		getOrganizationUsers:function(orgplansubscriptionid){
			var orgusers=Ajax.ajaxHelper("GET",this.getContextPath()+"/rest/blog/plansubscription/enrolledusers?orgplansubscriptionid="+orgplansubscriptionid,undefined,"json","application/json");
			if(orgusers!="false"){
				return orgusers;	
			}
			else{
				console.log("No data got"+obj);
			}
		},
		getOrgzUsers:function(obj){
			var orgusers=ajaxhelperwithdata("organizationusers",obj);
			if(orgusers!="false"){
				return orgusers;	
			}
			else{
				console.log("No data got"+obj);
			}
		},
		getSuborgcheckUsers:function(obj){
			var suborgcheckusers=ajaxhelperwithdata("suborgloginCheck",obj);
			if(suborgcheckusers!="false"){
				return suborgcheckusers;	
			}else{
				console.log("No data got"+obj);
			}
		},		
		updateCourseStatus:function(courseobj){
			var statusupdate=ajaxhelperwithdata("courseStatusUpdate", courseobj);
			return statusupdate;
		},
		getCertificateDetails:function(enrollmentidobj){
			var certificateDetails=ajaxhelperwithdata("loadCertificateDetails", enrollmentidobj);
			if(certificateDetails!="false"){
				return certificateDetails;
			}
			else{
				console.log("No data got"+enrollmentidobj);	
			}
		},
		getCourseContent:function(courseidobj){
			var coursecontentlist=ajaxhelperwithdata("loadCourseContent",courseidobj);
			if(coursecontentlist!=null){
				if(coursecontentlist!="false"){
					return coursecontentlist;
				}
				else{
					console.log("No data got"+courseidobj);	
				}
			}
			else{
				console.log("coursecontentlist is null"+coursecontentlist);
			}
		},
		getPresentertype:function(){
			var self=this;
			var presentertype=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/presenterapp/presentertype",undefined,"json","application/json");
			if(presentertype!="false"){
				return presentertype;
			}else{
				console.log("No data got getPresentertype()");	
			}
		},
		getCoursePreviewValues:function(enrollmentobj){
			var coursecontentlist=ajaxhelperwithdata("getCoursePreviewData",enrollmentobj);
			if(coursecontentlist!="false"){
				return coursecontentlist;				
			}
			else{
				console.log("coursecontentlist is null"+coursecontentlist);
			}
		},
		
		getCourseDetails:function(orgpersonid){
			var self=this;
			var coursedetaillist=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/professionalinfo/courselist/"+orgpersonid,undefined,"json","application/json");
			if(coursedetaillist!="false"){
				return coursedetaillist;				
			}
			else{
				console.log("coursedetaillist is null"+coursedetaillist);
			}
		},
		getLivestatusDetails:function(livesessionid){
			var self = this;
			var statusdetails = Ajax.ajaxHelper("DELETE",self.getContextPath()+"/rest/livesession/livestatus/"+livesessionid,undefined,"json","application/json");
			if(statusdetails=="ACCEPTED"){				
				return statusdetails;	
			}
			else{
				console.log(statusdetails);
			}
			
		},
		getcurrentdatetime:function(){
			var self=this;
			var getcurrenttime=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/livesession/currenttime",undefined,"json","application/json");
			if(getcurrenttime!="false"){				
				return getcurrenttime;	
			}
			else{
				console.log("No data got"+getcurrenttime);
			}
		},
		getEditSchedule:function(scheduleobj){
			var self=this;
			var editscheduledetails=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/livesession/editschedule",scheduleobj,"json","application/json");
			if(editscheduledetails!="false"){				
				return editscheduledetails;	
			}
			else{
				console.log("No data got"+scheduleobj);
			}
		},	
		getSchedulecourseDetails:function(courseid){
			var self = this;
			var scheduledcourselist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/"+courseid,undefined,"json","application/json");
			if(scheduledcourselist!="false"){
				
				//scheduledetailFormatList=formater.formatscheduleCategories(scheduledetaillist);
				return scheduledcourselist;
			}
			else{
				console.log("scheduledcourselist is null"+scheduledcourselist);
				
			}
		},
		getScheduleDetails:function(courseid){
			var self = this;
			var scheduledetaillist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/"+courseid,undefined,"json","application/json");
			if(scheduledetaillist!="false"){
				
				/*scheduledetailFormatList=formater.formatscheduleCategories(scheduledetaillist);
				return scheduledetailFormatList;*/
				return scheduledetaillist;
			}
			else{
				console.log("scheduledetaillist is null"+scheduledetaillist);
				
			}
		},
		Schedulelist:function(courseid){
			var self = this;
			var scheduledetaillist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/scheduleList/"+courseid,undefined,"json","application/json");
			if(scheduledetaillist!="false"){
				
				/*scheduledetailFormatList=formater.formatscheduleCategories(scheduledetaillist);
				return scheduledetailFormatList;*/
				return scheduledetaillist;
			}
			else{
				console.log("scheduledetaillist is null"+scheduledetaillist);
				
			}
		},
		checkScheduleDetails:function(courseid){
			var self = this;
			var scheduledetaillist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/"+courseid,undefined,"json","application/json");
			if(scheduledetaillist!="false"){
				
				 datevalues=_.uniq(_.pluck(scheduledetaillist,'starttime','endtime'));
                   return datevalues;
			}
			else{
				console.log("datevalues is null"+datevalues);
				
			}
		},
		getTimecourseDetails:function(courseid,scheduledate){
			var self = this;
			var scheduledetailcourselist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/"+courseid+"/"+scheduledate,undefined,"json","application/json");
			if(scheduledetailcourselist!="false"){
				
                   return scheduledetailcourselist;
			}
			else{
				console.log("scheduledetailcourselist is null"+scheduledetailcourselist);
				
			}
		},
		
		getTimeDetails:function(scheduledate){
			var self = this;
			var scheduledetailslist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/liveauthor/"+scheduledate,undefined,"json","application/json");

			if(scheduledetailslist!="false"){
				
                   return scheduledetailslist;
			}
			else{
				console.log("scheduledetailslist is null"+scheduledetailslist);
				
			}
		},
		getUpdateschedule:function(liveedit){
			var self=this;
			var updatescheduledetails=Ajax.ajaxHelper("PUT",self.getContextPath()+"/rest/livesession/updateschedule",liveedit,"json","application/json");
			if(updatescheduledetails!="false"){				
				return updatescheduledetails;	
			}
			else{
				console.log("No data got"+liveedit);
			}
		},
		checkeditdeletelivesession:function(livesessionid){
			var self = this;
			var scheduledetailslist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/livesession/getlivesessionschedulelist/"+livesessionid,undefined,"json","application/json");

			if(scheduledetailslist!="false"){
				
                   return scheduledetailslist;
			}
			else{
				console.log("scheduledetailslist is null"+scheduledetailslist);
				
			}
		},
		/* Live Session Enrollment Process ****************/
		enrolllivesession:function(livesessionidobj){
			var self=this;
			var livesession=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/livesessionenroll",livesessionidobj,"json","application/json");
			if(livesession!="false"){				
				return livesession;	
			}
			else{
				console.log("No data got"+livesession);
			}
		},
		
		/* Live Session Royalty Payment Process ****************/
		paymentlivesession:function(livesessionenrollidobj){
			var self=this;
			var livesession=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/livesessionroyalpayment",livesessionenrollidobj,"json","application/json");
			if(livesession!="false"){				
				return livesession;	
			}
			else{
				console.log("No data got"+livesession);
			}
		},
		/* Live Session Royalty Total Payment  ****************/
		getlivesessiontotalpayment:function(){
			var self=this;
			var getlivesessiontotalpayment=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/livesessionroyalpayment/live",undefined,"json","application/json");
			if(getlivesessiontotalpayment!=null){
					return getlivesessiontotalpayment;
			}
			else{
				console.log("getlivesessiontotalpayment is null"+getlivesessiontotalpayment);
			}
		},
		
		/* Live Session Royalty User List ****************/
		getlivesessionuserlist:function(livesessionid){
			var self=this;
			var getlivesessionuserlist=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/livesessionroyalpayment/live/"+livesessionid+"/payment",undefined,"json","application/json");
			if(getlivesessionuserlist!=null){
					return getlivesessionuserlist;
			}
			else{
				console.log("getlivesessionuserlist is null"+getlivesessionuserlist);
			}
		},
		/* Live Session Payment List ****************/
		getlivesessionschedulelist:function(livesessionid){
			var getlivesessionschedulelist=ajaxhelperwithdata("getlivesessionschedulelist", livesessionid);
			if(getlivesessionschedulelist!="false"){
				return getlivesessionschedulelist;	
			}
			else{
				return getlivesessionschedulelist;
				console.log("No Data got"+livesessionid);
			}
		},
		enrolllivesessionemail:function(id){
			var livesession=ajaxhelperwithdata("livesessionenrollemail", id);
			if(livesession!="false"){				
				return livesession;	
			}
			else{
				console.log("No data got"+livesession);
			}
		},
		getWorkExperienceByPersonId:function(personid){
			var self=this;
			var workexperience=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/professionalinfo/course/"+personid,undefined,"json","application/json");
			if(workexperience!=null){
				if(workexperience!="false"){
					return workexperience;
				}
				else{
					console.log("No data got for"+personid);	
				}
			}
			else{
				console.log("workexperience is null"+workexperience);
			}
		},
		getCourseSpentTime:function(courseidobj){
			var coursespenttimelist=ajaxhelperwithdata("getCourseSpentTime",courseidobj);
			if(coursespenttimelist!=null){
				if(coursespenttimelist!="false"){
					return coursespenttimelist;
				}
				else{
					console.log("No data got"+courseidobj);	
				}
			}
			else{
				console.log("coursespenttimelist is null"+coursespenttimelist);
			}
		},
		getOrgCourseContent:function(courseidobj){
			var coursecontentlist=ajaxhelperwithdata("loadOrgCourseContent",courseidobj);
			if(coursecontentlist!=null){
				if(coursecontentlist!="false"){
					return coursecontentlist;
				}
				else{
					console.log("No data got"+courseidobj);	
				}
			}
			else{
				console.log("coursecontentlist is null"+coursecontentlist);
			}
		},
		getParticularCourses:function(orgobj){
			var courselist=ajaxhelperwithdata("getParticularCourses",orgobj);
			if(courselist!="false"){
				courselist=formater.formatCourselistCategories(courselist);
				return courselist;	
			}
			else{
				console.log("No data got in"+orgobj);
			}
		},
		getOrgCourses:function(){
			var courselist=ajaxhelper("orgAnalysticCourse");
			if(courselist!="false"){				
				return courselist;	
			}
			else{
				console.log("No data got in analystic");
			}
		}, 
		allOrganization:function(){
			var organizationCategory=ajaxhelper("organizationCategory");
			if(organizationCategory!="false"){			
				return organizationCategory;	
			}
			else{
				console.log("No data got in allOrganization()");
			}
		},        
		getOrgUsers:function(){
			var userlist=ajaxhelper("getOrganizationWiseUsers");
			if(userlist!="false"){
				userlist=formater.formatUserlistCategories(userlist);
				return userlist;	
			}
			else{
				console.log("No data got"+orgidobj);
			}
		}, 
		getOrgParticularUsers:function(orgidobj){
			var userlist=ajaxhelperwithdata("getOrganizationUsers",orgidobj);
			if(userlist!="false"){

				userlist=formater.formatUserlistCategories(userlist);
				return userlist;	
			}
			else{
				console.log("No data got"+orgidobj);
			}
		}, 		
		deleteCoursesection:function(trashobj){
			var deletelist=ajaxhelperwithdata("checkcoursesection",trashobj);
			if(deletelist!="false"){
				return deletelist;	
			}else{
				console.log("No data got"+trashobj);
			}
		},		
		deleteCourselecture:function(trashobj){
			var deletelist=ajaxhelperwithdata("checkcourselecture",trashobj);
			if(deletelist!="false"){
				return deletelist;	
			}else{
				console.log("No data got"+trashobj);
			}
		},
		checkuserData:function(obj){
			var checklist=ajaxhelperwithdata("checkuserOrg",obj);
			if(checklist!="false"){
				return checklist;	
			}else{
				console.log("No data got"+obj);
			}
		},
		checkAuthourData:function(obj){
			var checklist=ajaxhelperwithdata("checkAuthorcourse",obj);
			if(checklist!="false"){
				return checklist;	
			}else{
				console.log("No data got"+obj);
			}
		},	
		getCourseLogo:function(courseidobj){
			var courseLogoList=ajaxhelperwithdata("getCourseLogoImage", courseidobj);
			if(courseLogoList!="false"){
				return courseLogoList;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		getPaymentDetails:function(paymentstatusobj){
			var paymentDetailList=ajaxhelperwithdata("invoiceapproval", paymentstatusobj);
			if(paymentDetailList!="false"){
				return paymentDetailList;	
			}
			else{
				console.log("No Data got"+paymentstatusobj);
			}
		},
		getParicularOrganizationdetails:function(){
			var paricularOrganizationdetailsList=ajaxhelper("getParicularOrganizationdetails");
			if(paricularOrganizationdetailsList!="false"){
				return paricularOrganizationdetailsList;	
			}
			else{
				console.log("No Data got in getParicularOrganizationdetails");
			}
		},
		getOrganizationdetails:function(org){
			var organizationupdatelist=ajaxhelperwithdata("organizationupdate",org);
			if(organizationupdatelist!="false"){
				return organizationupdatelist;	
			}
			else{
				console.log("No Data got"+org);
			}
		},
		getOrganizationplan:function(obj){
			var plandetails=ajaxhelperwithdata("organizationplan",obj);			
			if(plandetails!="false"){
				return plandetails;	
			}
			else{
				console.log("No Data got in getOrganizationplan()");
			}
		},		
		getEnrollcourses:function(){
			var enrolledCoursesList=ajaxhelper("loadEnrollCourses");
			if(enrolledCoursesList!="false"){
				var formatedenrolledCoursesList={};
				formatedenrolledCoursesList.learningCourses=formater.formatLearningCourses(enrolledCoursesList);
				formatedenrolledCoursesList.learningCoursesCount=formatedenrolledCoursesList.learningCourses.length;
				formatedenrolledCoursesList.completedCourses=formater.formatCompletedCourses(enrolledCoursesList);
				formatedenrolledCoursesList.completedCoursesCount=formatedenrolledCoursesList.completedCourses.length;
				formatedenrolledCoursesList.wishlistCourses=formater.formatWishlistCourses(enrolledCoursesList);
				formatedenrolledCoursesList.wishlistCoursesCount=formatedenrolledCoursesList.wishlistCourses.length;
				return formatedenrolledCoursesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getEnrolledcourses:function(){
			var enrolledCoursesList=ajaxhelper("loadEnrolledCourses");
			if(enrolledCoursesList!="false"){
				var formatedenrolledCoursesList={};
				formatedenrolledCoursesList.learningCourses=formater.formatLearningCourses(enrolledCoursesList);
				formatedenrolledCoursesList.learningCoursesCount=formatedenrolledCoursesList.learningCourses.length;
				formatedenrolledCoursesList.completedCourses=formater.formatCompletedCourses(enrolledCoursesList);
				formatedenrolledCoursesList.completedCoursesCount=formatedenrolledCoursesList.completedCourses.length;
				formatedenrolledCoursesList.wishlistCourses=formater.formatWishlistCourses(enrolledCoursesList);
				formatedenrolledCoursesList.wishlistCoursesCount=formatedenrolledCoursesList.wishlistCourses.length;
				return formatedenrolledCoursesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getIndividualCourses:function(personidobj){
			var individualCoursesList=ajaxhelperwithdata("loadIndividualsCourses",personidobj);
			if(individualCoursesList!="false"){
				return individualCoursesList;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		getIndividualCourseswithoutTime:function(personidobj){
			var individualCoursesList=ajaxhelperwithdata("loadIndividualCoursewithoutTimespend",personidobj);
			if(individualCoursesList!="false"){
				return individualCoursesList;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		getMyEarningCourses:function(){
			var individualCoursesList=ajaxhelper("loadMyEarningCourses");
			if(individualCoursesList!="false"){
				return individualCoursesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getMyCourses:function(){
			var myCoursesList=ajaxhelper("loadEnrolledCourses");
			if(myCoursesList!="false"){
				return myCoursesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getLearningDetails:function(enrollmentobj){
			var learningDetailsList=ajaxhelperwithdata("loadLearningDetails",enrollmentobj);
			if(learningDetailsList!="false"){
				learningdetailFormatList=formater.formatLectureList(learningDetailsList);
				return learningdetailFormatList;	
			}
			else{
				console.log("No Data got"+enrollmentobj);
			}
		},
		getLectureContent:function(lectureidobj){
			var lecturecontentlist=ajaxhelperwithdata("loadLectureContent",lectureidobj);
			if(lecturecontentlist!="false"){
				var lecturecontentformatlist={};
				lecturecontentformatlist=formater.formatContent(lecturecontentlist);
				return lecturecontentformatlist;	
			}
			else{
				console.log("No Data got"+lectureidobj);
			}
		},
		getLectureNotes:function(lectureidobj){
			var lectursnoteslist=ajaxhelperwithdata("loadLectureNotes",lectureidobj);
			if(lectursnoteslist!="false"){
				return lectursnoteslist;	
			}
			else{
				console.log("No Data got"+lectureidobj);
			}
		},
		getNotesDescription:function(notesidobj){
			var notedesclist=ajaxhelperwithdata("loadNotesDescription",notesidobj);
			if(notedesclist!="false"){
				return notedesclist;	
			}
			else{
				console.log("No Data got"+notesidobj);
			}
		},
		updateNotesDescription:function(notesidobj){
			var updateNote=ajaxhelperwithdata("updateNotesDescription",notesidobj);
			if(updateNote!="false"){
				return updateNote;	
			}
			else{
				console.log("No Data got"+notesidobj);
			}
		},
		deleteNotes:function(notesidobj){
			var deletedNote=ajaxhelperwithdata("deleteNotes",notesidobj);
			if(deletedNote!="false"){
				return deletedNote;	
			}
			else{
				console.log("No Data got"+notesidobj);
			}
		},
		getLectureQuestions:function(lectureidobj){
			var lectursequestionslist=ajaxhelperwithdata("loadLectureQuestions",lectureidobj);
			if(lectursequestionslist!="false"){
				return lectursequestionslist;	
			}
			else{
				console.log("No Data got"+lectureidobj);
			}
		},
		getAllUsersDetailss:function(courseidobj){
			var userDetailsList=ajaxhelperwithdata("loadValidUsers",courseidobj);
			if(userDetailsList!="false"){
				return userDetailsList;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		getAllUsersDetails:function(){
			var userDetailsList=ajaxhelper("loadAllUsers");
			if(userDetailsList!="false"){
				return userDetailsList;	
			}
			else{
				console.log("No Data got getAllUsersDetails()");
			}
		},	
		/* Download*/
		getPresenterDetails:function(){
			var self=this;
			var presenterList=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/presenterapp/presenterdetails",undefined,"json","application/json");
			if(_.isObject(presenterList)){ 
				var formatdetails=formater.formatPresenterdetails(presenterList);			
				return formatdetails;
			
			
			}
			else{
				console.log("No Data got getPresenterDetails()");
			}
		},
		getDownloadDetails:function(){
			var self=this;	
			var downloadList=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/presenterapp",undefined,"json","application/json");
			if(downloadList!="false"){
				return downloadList;
			}
			else{
				console.log("No Data got getDownloadDetails()");
			}
		},
		getPresenterDownloadDetails:function(presenterappdetail){
			var self=this;	
			var presenterappdetailsList=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/downloadhistory",presenterappdetail,"json","application/json");
			if(presenterappdetailsList!="false"){
				return presenterappdetailsList;
			}
			else{
				console.log("No Data got getDownloadDetails()");
			}
		},
		getMyLoginDetails:function(){
			var self=this;	
			var logindetails=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/sociallogins",undefined,"json","application/json");
			if(logindetails!="false"){
				return logindetails;	
			}
			else{
				console.log("No Data got getMyLoginDetails()");
			}
		},
		deleteMyLoginDetails:function(providerid){
			var self=this;	
			var deletestatus=Ajax.ajaxHelper("DELETE",self.getContextPath()+"/rest/sociallogins?providerid="+providerid,undefined,"json","application/json");
			if(deletestatus!="false"){
				return deletestatus;	
			}
			else{
				console.log("No Data got deleteMyLoginDetails()");
			}
		},
		updatePresenterValues:function(presenterappdata){
			var presenterappdetailsList=Ajax.ajaxHelper("POST", this.getContextPath()+"/rest/presenterapp/updatepresenter",presenterappdata,"json","application/json");
			if(presenterappdetailsList!="false"){
				return presenterappdetailsList;
			}
			else{
				console.log("No Data getPresenteraappDetails()");
			}
		},
		getDownloadHistory:function(){
			var self=this;	
			var downloadHistoryList=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/downloadhistory",undefined,"json","application/json");
			if(downloadHistoryList!="false"){
				for(var i=0;i<downloadHistoryList.length;i++){
					downloadHistoryList[i].dateofdownload=new Date(downloadHistoryList[i].dateofdownload);
				}
				return downloadHistoryList;	
			}
			else{
				console.log("No Data got getDownloadHistoryDetails()");
			}
		},		
		getPresenteraappDetails:function(presenterappdata){
			var self=this;
			var presenterappdetailsList=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/presenterapp/presenterappdetails",presenterappdata,"json","application/json");
			if(presenterappdetailsList!="false"){
				return presenterappdetailsList;
			}
			else{
				console.log("No Data getPresenteraappDetails()");
			}
		},
		/*Remove*/
		getRemoveUserDetails:function(){
			var removeuserDetailsList=ajaxhelper("loadRemovedUsers");
			if(removeuserDetailsList!="false"){
				return removeuserDetailsList;
			}
			else{
				console.log("No Data to getRemoveUserDetails()");
			}
		},
		/*Retrive User*/
		getRetriveUserDetails:function(loginobj){
			var retriveuserDetailsList=ajaxhelperwithdata("restoreuser",loginobj);
			if(retriveuserDetailsList!="false"){
				return retriveuserDetailsList;
			}
			else{
				console.log("No Data to getRetriveUserDetails()");
			}
		},
		getAllAuthorsDetails:function(){
			var authorsDetailsList=ajaxhelper("loadAllAuthors");
			if(authorsDetailsList!="false"){
				return authorsDetailsList;	
			}
			else{
				console.log("No Data got getAllAuthorsDetails()");
			}
		},	
		loadAuthorCourses:function(authoridobj){
			var authorsCoursesList=ajaxhelperwithdata("loadAuthorCourses",authoridobj);
			if(authorsCoursesList!="false"){
				return authorsCoursesList;	
			}
			else{
				console.log("No Data got"+authoridobj);
			}
		},
		loadAuthorCourseStudents:function(stcourseidobj){
			var authorCourseStudentsList=ajaxhelperwithdata("loadAuthorCourseStudents",stcourseidobj);
			if(authorCourseStudentsList!="false"){
				return authorCourseStudentsList;	
			}
			else{
				console.log("No Data got"+stcourseidobj.courseid);
			}
		},

		getInvoices:function(paymentstatusobj){
			var invoicesList=ajaxhelperwithdata("getUsersInvoices", paymentstatusobj);
			if(invoicesList!="false"){
				return invoicesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getCertificates:function(courseidobj){
			var certificatesList=ajaxhelperwithdata("getAllCertificates",courseidobj);
			if(certificatesList!="false"){
				var certificateformatlist={};
				certificateformatlist.allCerticates=certificatesList;
				certificateformatlist.issuedCertificates=formater.formatIssuedCertificates(certificatesList);
				certificateformatlist.pendingCertificates=formater.formatPendingCertificates(certificatesList);
				return certificateformatlist;	
			}
			else{
				console.log("No Data got "+courseidobj);
			}
		},
		getOrganizations:function(){
			var organizationslist=ajaxhelper("getOrganizationList");
			if(organizationslist!="false"){
				return organizationslist;	
			}
			else{
				console.log("No Data got");
			}
		},
		getCourseDetailsForPayment:function(courseidobj){
			var courseDetailsList=ajaxhelperwithdata("loadCourseDetailsforPayment", courseidobj);
			if(courseDetailsList!="false"){
				return courseDetailsList;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		getCourseEnrolledValue:function(onlinepaymentobj){
			var courseEnrolledValue=ajaxhelperwithdata("admission", onlinepaymentobj);
			if(courseEnrolledValue!="false"){
				return courseEnrolledValue;	
			}
			else{
				console.log("No Data got"+onlinepaymentobj);
			}
		},
		getUserNameAvailability:function(usernameobj){
			var useravailability=ajaxhelperwithdata("checkUserName",usernameobj);
			if(useravailability!="false"){
				return useravailability;	
			}
			else{
				console.log("No Data got"+usernameobj);
			}
		},
		getDeletelogin:function(loginidobj){
			var loginremoved=ajaxhelperwithdata("logincheck",loginidobj);
			if(loginremoved!="false"){
				return loginremoved;
			}
			else{
				console.log("No Data removed"+loginidobj);
			}
		},
		getInvoiceDetails:function(invoiceidobj){
			var invoiceDetailsList=ajaxhelperwithdata("getInvoiceParticulars", invoiceidobj);
			if(invoiceDetailsList!="false"){
				return invoiceDetailsList;	
			}
			else{
				console.log("No Data got"+invoiceidobj);
			}
		},
		getLivesessionInvoiceDetails:function(invoiceidobj){
			var invoiceDetailsList=ajaxhelperwithdata("livesessioninvoice", invoiceidobj);
			if(invoiceDetailsList!="false"){
				return invoiceDetailsList;	
			}
			else{
				console.log("No Data got"+invoiceidobj);
			}
		},
		updateCourseEnrollmentStatus:function(enrollmentidobj){
			var approvedstatus=ajaxhelperwithdata("updateApproveStatus", enrollmentidobj);
			if(approvedstatus!="false"){
				return approvedstatus;	
			}
			else{
				console.log("No Data got"+enrollmentidobj);
			}
		},
		updateCourseEnrollmentStatus:function(enrollmentidobj){
			ajaxhelperwithdata("updateCourseEnrollment", enrollmentidobj);
		},
		updateCoursePaymentStatus:function(paymentidobj){
			var successPayment=ajaxhelperwithdata("updatePaymentStatus", paymentidobj);
			if(successPayment!="false"){
				return successPayment;	
			}
			else{
				console.log("No Data got"+paymentidobj);
			}
		},
		getMessagesForuser:function(personidobj){
			var MessagesList=ajaxhelperwithdata("getMessageforParticularUser", personidobj);
			if(MessagesList!="false"){
				return MessagesList;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		getReceivedMessages:function(){
			var MessagesList=ajaxhelper("getReceivedMessages");
			if(MessagesList!="false"){
				return MessagesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getSentMessages:function(){
			var MessagesList=ajaxhelper("getSentMessages");
			if(MessagesList!="false"){
				return MessagesList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getInboxMessages:function(personidobj){
			var MessagesList=ajaxhelperwithdata("getMyMessages",personidobj);
			if(MessagesList!="false"){
				return MessagesList;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		sendMessage:function(personidobj){
			var messageSuccess=ajaxhelperwithdata("messageSave", personidobj);
			if(messageSuccess!="false"){
				return messageSuccess;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		deleteMessage:function(personidobj){
			var messageSuccess=ajaxhelperwithdata("deleteMessage", personidobj);
			if(messageSuccess!="false"){
				return messageSuccess;	 
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		loadProfileData:function(personidobj){
			var profileData=ajaxhelperwithdata("loadUserProfile", personidobj);
			if(profileData!="false"){
				return profileData;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		updateProfileData:function(personidobj){
			var updatedProfileData=ajaxhelperwithdata("updatePersonalInfo", personidobj);
			if(updatedProfileData!="false"){
				return updatedProfileData;	
			}
			else{
				console.log("No Data got"+personidobj);
			}
		},
		loadIndividualProfileData:function(){
			var individualProfileData=ajaxhelper("loadIndividualProfile");
			if(individualProfileData!="false"){
				return individualProfileData;	
			}
			else{
				console.log("No Data got");
			}
		},
		loadIndividualProfessionalPro:function(){
			var self = this;													
			var individualProfessPro=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/professionalinfo",undefined,"json","application/json");
			if(individualProfessPro!="false"){
				return individualProfessPro;	
			}
			else{
				console.log("No Data got");
			}
		},
		getExistingContent:function(lectureidobj){
			var contentData=ajaxhelperwithdata("getContent",lectureidobj);
			if(contentData!="false"){
				return contentData;	
			}
			else{
				console.log("No Data got"+lectureidobj);
			}
		},
		removeCourseContent:function(contentidobj){
			var contentRemoved=ajaxhelperwithdata("removeContent", contentidobj);
			if(contentRemoved!="false"){
				return contentRemoved;	
			}
			else{
				console.log("No Data got"+contentidobj);
			}
		},
		resetPassword:function(passwordobj){
			var resetsuccessvalue=ajaxhelperwithdata("passwordReset",passwordobj);
			if(resetsuccessvalue=="resetsuccess"){
				return "success";	
			}
			else if(resetsuccessvalue=="nomatch"){
				return "nomatch";
			}
			else{
				console.log("No Data got"+passwordobj);
			}
		},
		getEnrolledBoolean:function(courseidobj){
			var enrolledBoolean=ajaxhelperwithdata("loadEnrollmentStatus", courseidobj);
			if(enrolledBoolean!="false"){
				return enrolledBoolean;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		EnrollUser:function(courseidobj){
			var enrolledUser=ajaxhelperwithdata("wishlistCourse", courseidobj);
			if(enrolledUser!="false"){
				return enrolledUser;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		setCompletedUnit:function(learnstatsobj){
			var status=ajaxhelperwithdata("setCompletedStatusUnit", learnstatsobj);
			if(status!="false"){
				return status;	
			}
			else{
				console.log("No Data got"+learnstatsobj);
			}
		},
		forgotpassword:function(passwordobj){
			var status=ajaxhelperwithdata("forgetPassword", passwordobj);
			if(status!="false"){
				return status;	
			}
			else{
				console.log("No Data got"+passwordobj);
			}
		},
		getLearningstatus:function(lectureidobj){
			var learningStatus=ajaxhelperwithdata("learningstats", lectureidobj);
			if(learningStatus!="false"){
				return learningStatus;	
			}
			else{
				console.log("No Data got"+lectureidobj);
			}
		},
		getCompletedLectureDetails:function(couresenrollmentobj){
			var compLectureList=ajaxhelperwithdata("learningStatusList", couresenrollmentobj);
			if(compLectureList!="false"){
				return compLectureList;	
			}
			else{
				console.log("No Data got"+couresenrollmentobj);
			}
		},
		getAllLearningLecturesList:function(couresenrollmentobj){
			var allLectureList=ajaxhelperwithdata("allLearningLectureList", couresenrollmentobj);
			if(allLectureList!="false"){
				return allLectureList;	
			}
			else{
				console.log("No Data got"+couresenrollmentobj);
			}
		},
		setCourseProgressLength:function(progressobj){
			ajaxhelperwithdata("updateCourseProgress", progressobj);
		},
		getCompletedCourseReport:function(couresenrollmentobj){
			var reportList=ajaxhelperwithdata("getCompletedCourseReport", couresenrollmentobj);
			if(reportList!="false"){
				return reportList;	
			}
			else{
				console.log("No Data got in getCompletedCourseReport "+couresenrollmentobj);
			}
		},
		
		SaveNewCategory:function(categoryobj){
			var catinsertedStatus=ajaxhelperwithdata("saveCategory", categoryobj);
			if(catinsertedStatus!="false"){
				return catinsertedStatus;	
			}
			else{
				console.log("No Data got"+categoryobj);
			}
		},
		updateCategory:function(categoryobj){
			var catupdatedStatus=ajaxhelperwithdata("editCategory", categoryobj);
			if(catupdatedStatus!="false"){
				return catupdatedStatus;	
			}
			else{
				console.log("No Data got"+categoryobj);
			}
		},
		trashCategory:function(categoryobj){
			var catdeletedStatus=ajaxhelperwithdata("deleteCategory", categoryobj);
			if(catdeletedStatus!="false"){
				return catdeletedStatus;	
			}
			else{  
				console.log("No Data got"+categoryobj);
			}
		},
		restoreCategory:function(categoryobj){
			var catdeletedStatus=ajaxhelperwithdata("restoreCategory", categoryobj);
			if(catdeletedStatus!="false"){
				return catdeletedStatus;	
			}
			else{  
				console.log("No Data got"+categoryobj);
			}
		},
		checkCategory:function(coursecategorynameobj){
			var catStatus=ajaxhelperwithdata("checkCategory", coursecategorynameobj);
			if(catStatus!="false"){
				return catStatus;	
			}
			else{  
				console.log("No Data got"+coursecategorynameobj);
			}
		},
		coursecategoryexists:function(categoryobj){
			var catdeletedStatus=ajaxhelperwithdata("coursecategoryexists", categoryobj);
			if(catdeletedStatus!="false"){
				return catdeletedStatus;	
			}
			else{
				console.log("No Data got"+categoryobj);
			}
		},
		getAnswers:function(faqidobj){
			var answersList=ajaxhelperwithdata("loadAnswers", faqidobj);
			if(answersList!="false"){
				return answersList;	
			}
			else{
				console.log("No Data got"+faqidobj);
			}
		},
		getTrashAnswers:function(faqidobj){
			var answersList=ajaxhelperwithdata("getTrashAnswers", faqidobj);
			if(answersList!="false"){
				return answersList;	
			}
			else{
				console.log("No Data got"+faqidobj);
			}
		},
		saveAnswers:function(anwerobj){
			var answerinsertsuccess=ajaxhelperwithdata("saveQAnswer", anwerobj);
			if(answerinsertsuccess!="false"){
				return answerinsertsuccess;	
			}
			else{
				console.log("No Data got"+anwerobj);
			}
		},
		getCoursesCount:function(){
			var coursesList=ajaxhelper("app/loadCoursesList");
			if(coursesList!="false"){
				var coursesformatedList=formater.formatCoursesCount(coursesList);
				return coursesformatedList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getUsersCount:function(){
			var usersList=ajaxhelper("app/loadEnrolledPersonsList");
			if(usersList!="false"){
				var usersformatedList=formater.formatUsersCount(usersList);
				return usersformatedList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getEnrolledUsers:function(orgidobj){
			var usersList=ajaxhelper("app/loadEnrolledPersonsList",orgidobj);
			if(usersList!="false"){
				var enrolledformatedList=formater.formatEnrolledUsers(usersList);
				return enrolledformatedList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getEnrolledCourses:function(orgidobj){
			var usersList=ajaxhelper("app/loadEnrolledPersonsList",orgidobj);
			if(usersList!="false"){
				var enrolledformatedList=formater.formatEnrollCourse(usersList,orgidobj);
				return enrolledformatedList;	
			}
			else{
				console.log("No Data got");
			}
		},
		getTopCategoryCourses:function(){
			var topCategoryCourses=ajaxhelper("app/loadCategorywiseUsersList");
			if(topCategoryCourses!="false"){
				return topCategoryCourses;	
			}
			else{
				console.log("No Data got");
			}
		},
		getTopCoursesEnrolled:function(){
			var topCourses=ajaxhelper("app/loadCoursewiseUsersList");
			if(topCourses!="false"){
				return topCourses;	
			}
			else{
				console.log("No Data got");
			}
		},
		getWeeklyPayment:function(){
			var weeklyPayment=ajaxhelper("app/loadWeeklyPayments");
			if(weeklyPayment!="false"){
				return weeklyPayment;	
			}
			else{
				console.log("No Data got");
			}
		},
		getmonthlyPayment:function(){
			var monthlyPayment=ajaxhelper("app/loadMonthlyPayments");
			if(monthlyPayment!="false"){
				return monthlyPayment;	
			}
			else{
				console.log("No Data got");
			}
		},
		getYearlyPayment:function(){
			var yearlyPayment=ajaxhelper("app/loadYearlyPayments");
			if(yearlyPayment!="false"){
				return yearlyPayment;	
			}
			else{
				console.log("No Data got");
			}
		},
		approveCertificateRequest:function(certificateobj){
			var approveCertificateStatus=ajaxhelperwithdata("approveCertificate", certificateobj);
			if(approveCertificateStatus!="false"){
				return approveCertificateStatus;	
			}
			else{
				console.log("No Data got");
			}
		},
		loadMyCourseReviews:function(courseidobj){
			var myCourseReview=ajaxhelperwithdata("getMyCourseReviews", courseidobj);
			if(myCourseReview!="false"){
				return myCourseReview;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		loadMylectureReviews:function(lectureid){
			var lecdata={};
			lecdata.lectureid=lectureid;
			var myCourseReview=ajaxhelperwithdata("getMylectureReviews", lecdata);
			if(myCourseReview!=false){
				var lecval=_.pluck(myCourseReview, 'rank');
				var summ=0;
				$.each(lecval, function( index, value ) {
				summ=parseInt(summ)+parseInt(value);				
				});
				return summ;				
			}else{
				console.log("No Data got"+lectureid);
			}
		},
		loadAlllectureReviews:function(lectureid){
			var lecdata={}, lect1data =[];
			lecdata.lectureid=lectureid;
			var courseReview=ajaxhelperwithdata("getAlllectureReviews", lecdata);
			if(courseReview!="false"){				
				 uniqids=_.uniq(courseReview,"orgpersonid");
				 var returnObject = {};
				 var RankArray=[],nameArray = [], value = {};
				_.each(uniqids,function(uids){
					console.log("uids"+uids);
				   ranks=0; var name = null;
				   var obj = {};
				 _.each(courseReview,function(course){
					 
				    if(course.orgpersonid==uids.orgpersonid){
				    	
				      ranks=ranks+parseInt(course.rank);
				      obj.name = course.raterfirstname;
				     } 
				    
				    obj.rank = (ranks+parseInt(course.rank))/6;
				    
				    
				  });
				 RankArray.push(obj);
				});
				return  RankArray;
			}
			else{
				console.log("No Data got"+lectureid);
			}
		},
		loadCourseReviews:function(courseidobj){
			var courseReview=ajaxhelperwithdata("getCourseReviews", courseidobj);
			if(courseReview!="false"){				
				return courseReview;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		getCourseenrollchecked:function(courseidobj){
			var courseReview=ajaxhelperwithdata("getCourseenrollchecked", courseidobj);
			if(courseReview!="false"){
			return courseReview;
			}
			else{
				console.log("No Data got");
				return 0;
			}
		},
		saveRating:function(ratingidobj){
			var savedCourseReview=ajaxhelperwithdata("saveCourseReviews", ratingidobj);
			if(savedCourseReview!="false"){
				return savedCourseReview;	
			}
			else{
				console.log("No Data got"+ratingidobj);
			}
		},
		getMyNotifications:function(){
			var individualNotifications=ajaxhelper("getNotificationforindividualUserAction");
			if(individualNotifications!="false"){
				return individualNotifications;	
			}
			else{
				console.log("No Data got");
			}
		},
		makeAllReadnotify:function(){
			var readednotify=ajaxhelper("NotificationReadAction");
			if(readednotify!="false"){
				return readednotify;	
			}
			else{
				console.log("No Data got");
			}
		},
		getOtherNotifications:function(statusobj){
			var otherNotifications=ajaxhelperwithdata("getNotifcationToViewAction", statusobj);
			if(otherNotifications!="false"){
				return otherNotifications;	
			}
			else{
				console.log("No Data got"+statusobj);
			}
		},
		getWaitingUsers:function(){
			var waitingUserlists=ajaxhelper("loadWaitingUsers");
			if(waitingUserlists!="false"){
				return waitingUserlists;	
			}
			else{
				console.log("No Data got getWaitingUsers()");
			}			
		},
		
		
		getInvitingUsers:function(courseidobj){
			var inviteUserList=ajaxhelperwithdata("loadinviteUsers",courseidobj);
			if(inviteUserList!="false"){
				return inviteUserList;	
			}
			else{
				console.log("No Data got"+courseidobj);
			}
		},
		
		getOrgNameAvalibility:function(orgnameobj){
			var orgnameList=ajaxhelperwithdata("checkorgname", orgnameobj);
			if(orgnameList!="false"){
				return orgnameList;	
			}
			else{
				console.log("No Data got"+userobj);
			}
		},
		getEmailAvalibility:function(orgnameobj){
			var orgnameList=ajaxhelperwithdata("checkemail", orgnameobj);
			if(orgnameList!="false"){
				return orgnameList;	
			}
			else{
				console.log("No Data got"+userobj);
			}
		},
		saveNewQuiz:function(learnobj){
			var savedQuiz=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/quiz/save",learnobj,"json","application/json");
			if(savedQuiz!="false"){
				return savedQuiz;	
			}
			else{
				console.log("No Data got"+learnobj);
			}
		},
		/* New Subscription Plan */
		newSubscriptionData:function(subsplan){
			var self=this;
			var subscriptionNewData=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plan/savesubscribeplan",subsplan,"json","application/json");
			if(subscriptionNewData=="CREATED"){
				$('.leftsideli').removeClass('active');
				$('.subscriptedplans').addClass('active');
				$("#successalertmessage").modal('show');
				$(".successalertmsg").text("Plan successfully inserted");
				subscriptionobj.loadsubscriptions();								
			}
			else{
				loadSubscriptionData();	
				console.log("No Data got");
			}
		},
		getOrganizationplanlist:function(organizationid){
			var self=this;
			var orgplandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/orgplanlist/"+organizationid,undefined,"json","application/json");			
			return orgplandetails;
		},
		getOrganizationSubsplanlist:function(){
			var self=this;
			var orgplandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/orgsubscriptionplandetail",undefined,"json","application/json");			
			return orgplandetails;
		},
		getmaxuser:function(){
			var self=this;
			var orgplandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/getmaxuser",undefined,"json","application/json");			
			return orgplandetails;
		},
		getmaxcourseuser:function(){
			var self=this;
			var orgplandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/getmaxcourseuser",undefined,"json","application/json");			
			return orgplandetails;
		},
		/* Load Subscription Plan details */
		loadSubscriptionData:function(){
			var self=this;
			var subscriptionDatas=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/blog/plan/loadsubscribeplans",undefined,"json","application/json");
			if(subscriptionDatas!="false"){
				return subscriptionDatas;	
			}else{
				console.log("No Data got loadSubscriptionData()");
			}
		},
		
		
		createLivesession:function(livedata){
			var self = this;
			var livesessiondetails = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/livesession",livedata,"json","application/json");
			return livesessiondetails;
		},
		
		
		
		/* to delete subscription plan */
		deletesubscription:function(deleteplan){
			var self=this;
			var deleteresult=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plan/deletesubscribeplan",deleteplan,"json","application/json");
			return "result";
		},
		deletedownloadlist:function(downloadobj){
			var self=this;
			var deleteresult=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/presenterapp/downloaddetails",downloadobj,"json","application/json");
			return deleteresult;
		},
		/* To Edit Plan*/
		editsubscription:function(editplan){
			var self=this;
			var subscriptionEditData=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plan/updatesubscribeplan",editplan,"json","application/json");
			return "result";
		},
		loadBasicplanData:function(){
			var self=this;
			var basicplanDatas=Ajax.ajaxHelper("POST",self.getContextPath()+"/rest/blog/plan/loadbasicplans",undefined,"json","application/json");
			if(basicplanDatas!="false"){
				return basicplanDatas;	
			}else{
				console.log("No Data got loadBasicplanData()");
			}
		},
		/*To Check Duplicate name while Insert*/
		checkplanname:function(check){
			var self=this;
			var dupcheck=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plan/checksubscribeplan",check,"json","application/json");			
			return dupcheck;
		},
		/*To Check Duplicate name while Update with Id*/
		checkupdateplanname:function(check){
			var self=this;
			var dupcheck=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plan/duplicatesubscribeplan",check,"json","application/json");			
			return dupcheck;
		},
		/*To Load Planid Details*/
		planiddetails:function(planidobj){
			var self=this;
			var planiddetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plan/loadsubscribeplanidlist",planidobj,"json","application/json");			
			return planiddetails;
		},
		/*To Create orgPlanSubscription */
		createorgplan:function(planidobj){
			var self=this;
			var plandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/create",planidobj,"json","application/json");			
			return plandetails;
		},	
		/*To orgPlanList orgPlanSubscription */
		orgplanlist:function(orgplanobj){
			var self=this;
			var plandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/categories",orgplanobj,"json","application/json");			
			return plandetails;
		},
		/*To orgPlanList orgPlanSubscription */
		orgsubscripplanlist:function(orgplanobj){
			var self=this;
			var plandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/category",orgplanobj,"json","application/json");			
			return plandetails;
		},
		/*To orgSubscriptionPlanList orgPlanSubscription */
		SubscriptionPlanList:function(){
			var self=this;
			var plandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/subscriptionplandetail",undefined,"json","application/json");			
			return plandetails;
		},
		/*To Update orgPlanSubscription */
		updateorgplan:function(orgplanobj){
			var self=this;
			var plandetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/blog/plansubscription/orgplanstatusupdate",orgplanobj,"json","application/json");			
			return plandetails;
		},
		/*To Create planPayment */
		createplanpayment:function(planpaymentobj){
			var self=this;
			var planpaymentdetails=Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/planpayment/planpaymentcreate",planpaymentobj,"json","application/json");			
			return planpaymentdetails;
		},
		/* To Get Royalty List*/
		getRoyaltyList:function(type){
			var self = this;
			var royaltydetails = ajaxhelper(self.getContextPath()+"/rest/royalty?type="+type+"");
			return royaltydetails;
		},
		getDefaultRoyalty:function(type){
			var self = this;
			var royaltydetails = ajaxhelper(self.getContextPath()+"/rest/royalty/default?type="+type+"");
			return royaltydetails;
		},
		getExpiryDate:function(){
			var self=this;
			var expiryDetails= ajaxhelper(self.getContextPath()+"/rest/royalty/expiration");
			return expiryDetails;
		},		
		/* To Create Royalty*/
		createRoyalty:function(royaltyconfig){
			var self = this;
			var royaltydetails = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/royalty",royaltyconfig,"json","application/json");
			return royaltydetails;
		},
		/* To Delete Royalty*/
		deleteRoyalty:function(id){
			var self = this;
			var royaltydetails = ajaxhelperwithqueryparamdata(self.getContextPath()+"/rest/royalty",id,"DELETE");
			return royaltydetails;
		},
		deleteCurrentRoyalty:function(id){
			var self = this;
			var royaltydetails = ajaxhelperwithqueryparamdata(self.getContextPath()+"/rest/royalty/current",id,"DELETE");
			return royaltydetails;
		},
		/* To Finished List Royalty*/
		finishedRoyalty:function(id,type){
			var self = this;
			var royaltydetails = ajaxhelper(self.getContextPath()+"/rest/royalty?page="+id+"&type="+type);
			return royaltydetails;
		},
		/* To Next List Royalty*/
		nextRoyalty:function(id){
			var self = this;
			var royaltydetails = ajaxhelper(self.getContextPath()+"/rest/royalty?page="+id);
			return royaltydetails;
		},
		/* To Update Royalty*/
		updateRoyalty:function(id,royaltyconfig){
			var self = this;
			var royaltydetails = Ajax.ajaxHelper("PUT", self.getContextPath()+"/rest/royalty?id="+id+"",royaltyconfig,"json","application/json");
			return royaltydetails;
		},
		updateCurrentRoyalty:function(id,royaltyconfig){
			var self = this;
			var royaltydetails = Ajax.ajaxHelper("PUT", self.getContextPath()+"/rest/royalty/current?id="+id+"",royaltyconfig,"json","application/json");
			return royaltydetails;
		},
		/* To Get Request Limit*/
		getRequestLimit:function(){
			var self = this;
			var requestlimitdetails = ajaxhelper(self.getContextPath()+"/rest/requestlimit");
			return requestlimitdetails;
		},
		ensureRequestLimit:function(){
			var self = this;
			var requestlimitdetails = ajaxhelper(self.getContextPath()+"/rest/requestlimit/requestlimitstatus");
			return requestlimitdetails;
		},
		/* To Create Request Limit*/
		createRequestLimit:function(requestLimitDetail){
			var self = this;
			var requestlimitdetails = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/requestlimit",requestLimitDetail,"json","application/json");
			return requestlimitdetails;
		},
		/* To Create Request Limit*/
		createUpdateRequestLimit:function(requestLimit){
			var self = this;
			var requestlimitdetails = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/requestlimit/createrequestlimit",requestLimit,"json","application/json");
			return requestlimitdetails;
		},
		/* To Update Request Limit*/
		updateRequestLimit:function(id,requestLimitDetail){
			var self = this;
			var requestlimitdetails = Ajax.ajaxHelper("PUT", self.getContextPath()+"/rest/requestlimit?id="+id+"",requestLimitDetail,"json","application/json");
			return requestlimitdetails;
		},
		/* To Create Request Limit*/
		createTrashRequestLimit:function(requestLimitTrashId){
			var self = this;
			var requestlimitdetails = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/requestlimit/requestlimitstatus",requestLimitTrashId,"json","application/json");
			return requestlimitdetails;
		},
		/* To Get Payment List*/
		getPaymentRequest:function(){
			var self = this;
			var paymentList = ajaxhelper(self.getContextPath()+"/rest/paymentrequest");
			return paymentList;
		},
		/* To Create Payment Request*/
		createPaymentRequest:function(paymentRequest){
			var self = this;
			var paymentList = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/paymentrequest",paymentRequest,"json","application/json");
			return paymentList;
		},
		/* To Get Payment List*/
		getPaymentIssue:function(){
			var self = this;
			var paymentIssueList = ajaxhelper(self.getContextPath()+"/rest/paymentissue");
			return paymentIssueList;
		},
		/* To Create Payment Issue*/
		createPaymentIssue:function(paymentissue){
			var self = this;
			var paymentIssueList = Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/paymentissue",paymentissue,"json","application/json");
			return paymentIssueList;
		},
		/* To Delete Payment Issue*/
		deletePaymentRequest:function(id){
			var self = this;
			var paymentrequestdetails = ajaxhelperwithqueryparamdata(self.getContextPath()+"/rest/paymentrequest",id,"DELETE");
			return paymentrequestdetails;
		},
		/* To Get Payment Issue*/
		getPaymentIssueList:function(){
			var self = this;
			var paymentissuedetails =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/paymentissue/getPaymentIssue",undefined,"json","application/json"); 
			return paymentissuedetails;
		},
		/* To Get Author Total Amount List*/
		getAuthorTotalList:function(){
			var self = this;
			var paymentissuedetails =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/paymentissue/getAuthorTotalAmountList",undefined,"json","application/json"); 
			return paymentissuedetails;
		},
		/* To Get Royalty Total Amount List*/
		getTotaltAmountList:function(){
			var self = this;
			var royaltytotalamount =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/royaltytotalamount/getRoyaltyTotalAmount",undefined,"json","application/json"); 
			return royaltytotalamount;
		},		
		/* To Get Payment Request Author List*/
		getPaymentRequestAuthorList:function(){
			var self = this;
			var paymentrequestlist =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/paymentrequest/author",undefined,"json","application/json"); 
			return paymentrequestlist;
		},

		/* Get Author Payment List */
		AuthorPaymentRequestList:function(paymentrequestid){
			var self = this;
			var AuthorPaymentRequestList =Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/paymentrequest/PaymentRequestList",paymentrequestid,"json","application/json"); 
			return AuthorPaymentRequestList;
		},	
		createRoyaltyPayment:function(enrollobj,type){
			var self = this;
			var royaltyPayment =Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/royaltypayment?type="+type+"",enrollobj,"json","application/json"); 
			return royaltyPayment;
		},
		/* Royalty Payment Details*/
		getRoyaltyPayment:function(){
			var self = this;
			var royaltyPayment =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/royaltypayment/course",undefined,"json","application/json"); 
			return royaltyPayment;
		},
		/* Royalty Total Payment */
		getRoyaltyOrganizationTotalPayment:function(type){
			var self = this;
			var royaltyOrganizationTotalPayment =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/royaltypayment/org?type="+type,undefined,"json","application/json"); 
			return royaltyOrganizationTotalPayment;
		},
		/* Royalty Total Payment */
		getNoUserDetails:function(courseid){
			var self = this;
			var royaltyOrganizationTotalPayment =Ajax.ajaxHelper("GET", self.getContextPath()+"/rest/royaltypayment/course/"+courseid+"/payment",undefined,"json","application/json"); 
			return royaltyOrganizationTotalPayment;
		},
		updateVideoDuration:function(durationobj){
			var updatedVideoDuration=ajaxhelperwithdata("updateVideoTime", durationobj);
			return updatedVideoDuration;
			if(orgnameList!="error"){
				return updatedVideoDuration;	
			}
			else{
				console.log("No Data got in updateVideoDuration"+durationobj);
			}
		},
		getFacebookCount:function(obj){
			var self = this;
			var facebookcount =Ajax.ajaxHelper("POST", self.getContextPath()+"/rest/share/count",obj,"json","application/json"); 
			return facebookcount;
		},
		/* Get Organization Detail List */
		Organizationdetail:function(organizationid,obj){
			var self=this;
			var OrganizationdetailList=Ajax.ajaxHelper("GET",self.getContextPath()+"/rest/organizationlist/organizationdetail/"+organizationid+"/"+obj,undefined,"json","application/json");			
			return OrganizationdetailList;
		},
		setOrganization:function(id){
			var self = this;
			var status = ajaxhelper(self.getContextPath()+"/rest/suborg?id="+id);
			return status;
		},
		checkDuplicateCourse:function(coursetitle){
			var self = this;
			var result = ajaxhelper(self.getContextPath()+"/app/checkDuplicateCourse?coursetitle="+coursetitle);
			return result;
		},
		getEnrolledDiffList:function(enrollobj){
			var EnrolledList=ajaxhelperwithdata("courseenrolledusers",enrollobj);
			if(EnrolledList!="false"){
				return EnrolledList;	
			}else{
				console.log("No data");
			}
		}
		
};

formater={
		
		formatscheduleCategories:function(scheduleedetaillist){
			var schedulecategories=[];
			schedulevalues=_.uniq(_.pluck(scheduleedetaillist,'scheduledate'));
			_.each(schedulevalues,function(scheduledate){
			var schedulecategeory={};
			schedulecategeory=new Date(scheduledate).toString();
			var dates = new Date(schedulecategeory);
 			schedulecategories.push(dates);
			});
			return schedulecategories;
		},
  		formatCourseCategories:function(courselist){
			var coursecategories=[];
			coursecategoryvalues=_.uniq(_.pluck(courselist,'coursecategoryid'));
			_.each(coursecategoryvalues,function(coursecategoryid){
				var coursecategory={};
				var coursecategoryArray =_.where(courselist,{coursecategoryid:coursecategoryid});
				coursecategory=_.pick(coursecategoryArray[0],'coursecategoryid','coursecategoryname','coursecategorydescription','coursescount');
 				coursecategories.push(coursecategory);
			});
			return coursecategories;
		},
		formatCourseCategorieses:function(courselist){
			var coursecategories=[];
			var coursecategorieses = [];
			coursecategoryvalues=_.uniq(_.pluck(courselist,'coursecategoryid'));
			_.each(coursecategoryvalues,function(coursecategoryid){
				var coursecategory={};
				var coursecategoryArray =_.where(courselist,{coursecategoryid:coursecategoryid});
				coursecategory=_.pick(coursecategoryArray[0],'coursecategoryid','coursecategoryname','coursecategorydescription','courseid');
				coursecategory.coursescount=_.compact(_.uniq(_.pluck(coursecategoryArray,'courseid'))).length;
				coursecategories.push(coursecategory);
			});
			var data = _.pluck(coursecategories,"courseid");
			var courseCategories = _.compact(data);
			_.each(courseCategories,function(coursecategoryid){
				var coursecategory={};
				var coursecategoryArray =_.where(coursecategories,{courseid:coursecategoryid});
				coursecategory=_.pick(coursecategoryArray[0],'coursecategoryid','coursecategoryname','coursecategorydescription','courseid');
				coursecategory.coursescount=_.compact(_.uniq(_.pluck(coursecategoryArray,'courseid'))).length;
				coursecategorieses.push(coursecategory);
			});
			return coursecategorieses;
		},
		formatCourselistCategories:function(courselist){
			var coursecategories=[];
			coursecategoryvalues=_.uniq(_.pluck(courselist,'courseid'));
			_.each(coursecategoryvalues,function(courseid){
				var coursecategory={};
				var coursecategoryArray =_.where(courselist,{courseid:courseid});
				coursecategory=_.pick(coursecategoryArray[0],'courseid','coursecategoryname','coursedescription','coursetitle','price','courselogo');
				coursecategory.coursescount=_.compact(_.uniq(_.pluck(coursecategoryArray,'courseid'))).length;
				coursecategories.push(coursecategory);
			});
			return coursecategories;
		},
		formatUserlistCategories:function(userlist){
			var organizationids=_.unique(_.pluck(userlist,"organizationid"));
			var organizations=[];
			_.each(organizationids,function(organizationid){
				tempusers=_.where(userlist,{"organizationid":organizationid});
				organization={};
				orgnames=[];
				_.each(tempusers,function(tempuser){
					organization.orgname=tempuser.orgname;
					organization.email=tempuser.email;
					if(tempuser.organizationid){
						orgname={};
						orgname.users=tempuser.users;
						orgname.firstname=tempuser.firstname;
						orgnames.push(orgname);
					}
				});
				organization.orgnames=orgnames;
				organizations.push(organization);
			});
			return organizations;
		},
		formatCategoryCourses:function(categorycourseslist){
			var coursecategories=[];
			coursecategoryvalues=_.uniq(_.pluck(categorycourseslist,'coursecategoryid'));
			_.each(coursecategoryvalues,function(coursecategoryid){
				var coursecategory={};
				var coursecategoryArray =_.where(categorycourseslist,{coursecategoryid:coursecategoryid});
				coursecategory=_.pick(coursecategoryArray[0],'coursecategoryid','coursecategoryname','coursecategorydescription');
				var courseids =_.uniq(_.pluck(coursecategoryArray,'courseid'));
				courses=[];
				_.each(courseids,function(courseid){
					var coursearray = _.where(coursecategoryArray,{courseid:courseid});
					var course={};
					course=_.pick(coursearray[0],'coursecategoryid','coursedescription','coursecategoryname','courselogo','coursetitle','courseid','price','priceid','courseratingid','courserating','firstName','lastName','courseenrollmentstatus','courseenrollmentid');
					courseratingids=[];
					var courseratingids=(_.compact(_.pluck(coursearray,'courserating')));
					var courseratingsum = _.reduce(courseratingids, function(memo, num){return memo + parseInt(num);}, 0);
					var ratingaverage=courseratingsum/(courseratingids.length);
					if(_.isNaN(ratingaverage)){
						course.rating=0;
					}
					else{
						course.rating=ratingaverage;	
					}
					var coursesubscribers=(_.compact(_.uniq(_.pluck(coursearray,'courseenrollmentid')))).length;
					course.coursesubscribers=coursesubscribers;
					courses.push(course);
				});
				coursecategory.courses=courses;
				coursecategories.push(coursecategory);
			});
			return coursecategories;
		},
		formatCourseInfoContent:function(coursecontentlist){
			var basiccourseinfo=[];
			var courseinfo={};
			courseinfo=_.pick(coursecontentlist[0],'firstName','price','coursedescription','eduqualification','courselogo','coursetitle','courseid');
			courseinfo.notescount=_.compact(_.uniq(_.pluck(coursecontentlist,'notesid'))).length;
			var noteslist=[];
			notesValues=_.compact(_.uniq(_.pluck(coursecontentlist,'notesid')));
			_.each(notesValues,function(notesid){
				var notesobj={};
				var notesArray =_.where(coursecontentlist,{notesid:notesid});
				notesobj=_.pick(notesArray[0],'notesid','notesdescription');
				noteslist.push(notesobj);
			});
			courseinfo.notes=noteslist;
			courseinfo.questionscount=_.compact(_.uniq(_.pluck(coursecontentlist,'FAQid'))).length;
			var questionslist=[];
			questionsValues=_.compact(_.uniq(_.pluck(coursecontentlist,'FAQid')));
			_.each(questionsValues,function(FAQid){
				var questionsobj={};
				var questionsArray =_.where(coursecontentlist,{FAQid:FAQid});
				questionsobj=_.pick(questionsArray[0],'FAQid','FAQquestion');
				questionslist.push(questionsobj);
			});
			courseinfo.questions=questionslist;
			basiccourseinfo.push(courseinfo);
			return basiccourseinfo;
		},
		formatCourseHeaderContent:function(coursecontentlist){
			var courseheader=[];
			var courses={};
			courses=_.pick(coursecontentlist[0],'courseid','coursecategoryname','coursestatus','coursetitle','courselogo','price','priceid','coursedescription');
			courses.sectioncount=_.compact(_.uniq(_.pluck(coursecontentlist,'coursesectionid'))).length;
			courses.lecturecount=_.compact(_.uniq(_.pluck(coursecontentlist,'courselectureid'))).length;
			courses.contentcount=_.compact(_.uniq(_.pluck(coursecontentlist,'contentid'))).length;
			courses.notescount=_.compact(_.uniq(_.pluck(coursecontentlist,'notesid'))).length;
			courses.questionscount=_.compact(_.uniq(_.pluck(coursecontentlist,'FAQid'))).length;
			courses.subscribercount=_.compact(_.uniq(_.pluck(coursecontentlist,'courseenrollmentid'))).length;
			courseheader.push(courses);
			return courseheader;
		},
		formatbasicInfo:function(coursecontentlist){
			var basicinfo=[];
			var info={};
			info=_.pick(coursecontentlist[0],'courseid','coursecategoryid','coursecategoryname','coursestatus','coursetitle','courselogo','price','priceid','instructoinallevel','coursedescription','coursepromevideopath','intendedaudience','coursegoal','coursesubtitle','privacyid','privacyname','boardid','boardname','mediumid','mediumname','standardid','standardname');
			if(info.coursepromevideopath!=null&&info.coursepromevideopath!=undefined){
			var promoarray=info.coursepromevideopath.split('/');
			info.promofilename=promoarray[promoarray.length-1];
			}
			else{
			   info.promofilename="";	
			}
			basicinfo.push(info);
			return basicinfo;
		},
		formatCourseMainContent:function(coursecontentlist){
			sections = [];
			var courseid=coursecontentlist[0].courseid;
			var courseArray =_.where(coursecontentlist,{courseid:courseid});
			sectionids =_.uniq(_.pluck(courseArray,'coursesectionid'));
			if(sectionids!='' && sectionids!=undefined){
				_.each(sectionids,function(coursesectionid){
					var sectionarray = _.where(courseArray,{coursesectionid:coursesectionid});
					var lectureOutputArray = [];
					var section = {};
					section.coursesectionid = coursesectionid;
					section.coursesectiontitle = sectionarray[0].coursesectiontitle;
					section.courseid = sectionarray[0].courseid;
					section.coursestatus = sectionarray[0].coursestatus;				
					lectureOutputArray=[];
					var lectureids=_.uniq(_.pluck(sectionarray,'courselectureid'));
					_.each(lectureids,function(courselectureid){
						lectureArray = _.where(sectionarray,{courselectureid:courselectureid});
						if(lectureArray[0]){
							var courselectureid=lectureArray[0].courselectureid;
							if(courselectureid!=undefined){
								var lecture=_.pick(lectureArray[0],'courselectureid','courselecturetitle','courseid','coursesectionid','coursetitle','coursesectiontitle','seriallno','coursestatus','contentpath','duration','learningstatus','timespent','duration','contentstatus','metainfoid');
								var contentype=lectureArray[0].contentpath;
								if(contentype==undefined){
									lecture.contentytpe="nocontent";
									lecture.filename="No Content";
								}
								else{
									var filetypesplit= contentype.split('.');
									var filetype= filetypesplit[filetypesplit.length-1];
									var filenamesplit= contentype.split('/');
									var filename= filenamesplit[filenamesplit.length-1];
									lecture.filename=filename;
									if(filetype=="pdf"){
										lecture.contentytpe="pdf";
									}
									else if(filetype=="swf"){
										lecture.contentytpe="swf";
									}
									else{
										lecture.contentytpe="video";
									}
								}
								lectureOutputArray.push(lecture);
							}
						}
					});
					section.lectures=lectureOutputArray;
					sections.push(section);
				});	
			}			
			return sections;
		},
		formatLectureList:function(coursecontentlist){
			sections = [];
			var courseid=coursecontentlist[0].courseid;
			var courseArray =_.where(coursecontentlist,{courseid:courseid});
			sectionids =_.uniq(_.pluck(courseArray,'coursesectionid'));
			_.each(sectionids,function(coursesectionid){
				var sectionarray = _.where(courseArray,{coursesectionid:coursesectionid});
				var section = _.pick(sectionarray[0],'coursesectionid','coursesectiontitle','coursetitle','courseid');
				var lectureids=_.uniq(_.pluck(sectionarray,'courselectureid'));
				var lecture = [];
				_.each(lectureids,function(courselectureid){
					var lectureArray = _.where(sectionarray,{courselectureid:courselectureid});
					var lectureOutput=_.pick(lectureArray[0],'coursesectionid','coursesectiontitle','courseenrollmentid','courselectureid','courselecturetitle','compstatus');
					lecture.push(lectureOutput);
				});
				section.lectures=lecture;
				sections.push(section);
			});
			return sections;
		},
		formatContent:function(lecturecontentlist){
			var courseLectureContent=[];
			var contents={};
			contents=_.pick(lecturecontentlist[0],'courselecturetitle','contentpath');
			courseLectureContent.push(contents);
			return courseLectureContent;
		},
		formatQuestions:function(lectursequestionslist){
			var questions=[];
			questionvalues=_.uniq(_.pluck(lectursequestionslist,'FAQid'));
			_.each(questionvalues,function(FAQid){
				var question={};
				var questionArray =_.where(lectursequestionslist,{FAQid:FAQid});
				question=_.pick(questionArray[0],'FAQquestion','FAQid','askeddate','askedperson');
				questions.push(question);
			});
			return questions;
		},
		formatPresenterdetails:function(presenterList){
			presenterapp=[];
			presenterdetails=_.uniq(_.pluck(presenterList,'presenterid'));
			_.each(presenterdetails,function(presenterid){				
				var presenterArray=_.where(presenterList,{presenterid:presenterid});
				presentercategeory=_.pick(presenterArray[0],'applicationname','apppath','helpurlpath','presenterid','presenterstatus');
				typedetails=_.uniq(_.pluck(presenterArray,'typename'));
				var presentertypeapp=[];
				_.each(typedetails,function(typename){
					var presentertypeArray=_.where(presenterArray,{typename:typename});
					presentertypecategeory=_.pick(presentertypeArray[0],'typename','presenterappdetailsid','workingduration','logincount','price');
					presentertypeapp.push(presentertypecategeory);
				});
				presentercategeory.types=presentertypeapp;
				presenterapp.push(presentercategeory);
			});
			return presenterapp;
		},
		formatPresenter:function(downloadList){
			presenterapp=[];
			presenterdetails=_.uniq(_.pluck(downloadList,'presenterid'));
			_.each(presenterdetails,function(presenterid){				
				var presenterArray=_.where(downloadList,{presenterid:presenterid});
				presentercategeory=_.pick(presenterArray[0],'applicationname','downloadcount');
				typedetails=_.uniq(_.pluck(presenterArray,'typename'));
				var presentertypeapp=[];
				_.each(typedetails,function(typename){
					var presentertypeArray=_.where(presenterArray,{typename:typename});
					presentertypecategeory=_.pick(presentertypeArray[0],'typename','presenterappdetailsid');
					presentertypeapp.push(presentertypecategeory);
				});
				presentercategeory.types=presentertypeapp;
				presenterapp.push(presentercategeory);
			});
			return presenterapp;
		},
		formatIssuedCertificates:function(certificatelist){
			var issuedCertificates =_.where(certificatelist,{coursecertificatestatus:'A'});
			return issuedCertificates;
		},
		formatPendingCertificates:function(certificatelist){
			var pendingCertificates =_.where(certificatelist,{coursecertificatestatus:'P'});
			return pendingCertificates;
		},
		formatLearningCourses:function(mycourseslist){
			var learningCourses1 =_.where(mycourseslist,{courseenrollmentstatus:'A'});
			var learningCourses2 =_.where(mycourseslist,{courseenrollmentstatus:'B'});
			var learningCourses = learningCourses1.concat(learningCourses2);
			return learningCourses;
		},
		formatCompletedCourses:function(mycourseslist){
			var completedCourses =_.where(mycourseslist,{courseenrollmentstatus:'C'});
			return completedCourses;
		},
		formatWishlistCourses:function(mycourseslist){
			var wishlistCourses =_.where(mycourseslist,{courseenrollmentstatus:'W'});
			return wishlistCourses;
		},
		formatCoursesCount:function(coursesList){
			var coursesCount={};
			coursesCount.publishedCourses=_.where(coursesList,{coursestatus:'A'}).length;
			coursesCount.DraftCourses=_.where(coursesList,{coursestatus:'D'}).length;
			return coursesCount;
		},
		formatUsersCount:function(usersList){
			var usersCount={};
			usersCount.enrolledUsers=_.uniq(_.pluck(_.where(usersList,{courseenrollmentstatus:"A"}),"orgpersonid")).length;
			return usersCount;
		},
		formatterdownloadapp:function(datadetails,presentertypelist){
			var presenters={};
			presenters=_.pick(datadetails[0], 'applicationname', 'apppath','helpurlpath','presenterid');
			var alltypename=_.uniq(_.pluck(presentertypelist,'typename'));
			var types=[];
			_.each(alltypename,function(typename){
				var presentertypecategeory={};
				var presentertypeArray=_.where(datadetails,{typename:typename});
				if(presentertypeArray.length>0){
					presentertypecategeory=_.pick(presentertypeArray[0],'typeid','typename','logincount','workingduration','price','detailstatus');
					if(presentertypeArray[0].detailstatus=="A"){
						presentertypecategeory.checkstatus="1";	
					}
					else{
						presentertypecategeory.checkstatus="0";
					}						 
				}
				else{
					var presnterarr=_.where(presentertypelist, {typename: typename});
					presentertypecategeory.typeid=presnterarr[0].id;
					presentertypecategeory.typename=typename;
					presentertypecategeory.logincount="0";
					presentertypecategeory.workingduration="0";
					presentertypecategeory.price="0";				 
					presentertypecategeory.checkstatus="0";
				}			 
				types.push(presentertypecategeory);
			});
			presenters.types=types;
			return presenters;
		},	
		formatEnrolledUsers:function(usersList){			
			var enrolledUsers=_.uniq(_.pluck(usersList,"orgpersonid"));
			var	userArray=[];
			_.each(enrolledUsers,function(orgperid){
				var obj={};
				var enrolllistArray=_.where(usersList,{orgpersonid:orgperid});
				obj=_.pick(enrolllistArray[0],'firstname','coursetitle','orgpersonid','lastName');
				obj.coursecount=_.compact(_.pluck(enrolllistArray,'coursetitle')).length;
				userArray.push(obj);
			});
			return userArray;
		},	
		formatEnrollCourse:function(usersList,orgidobj){	
			var org=parseInt(orgidobj.orgpersonid);
			var enrolllistArray=_.where(usersList,{orgpersonid:org});
			return enrolllistArray;
		}
};

$(document).ready(function(){
	$('.message').click(function(){
		var personid=$(this).attr('id');
		var data={};
		data.toOrgpersonid=personid;
		getMessageForIndividualUser(data);
	});
	$('.notify').click(function(){
		getNotifyForIndividualUser();
	});	
	$('.menulink').click(function(){
		$('.menulink').removeClass('menuhover');
		$(this).addClass('menuhover');	
	});

	var textareaval=$('textarea').val();
	if(textareaval!==undefined){
		$('textarea').val(textareaval.trim());	
	}
});

/*function loadnotificationcount(){
	var notificaioncount=ajaxhelper("getNotificationforindividualUserAction");
	var countsize=(_.where(notificaioncount,{receivedstatus:'U'})).length;
	if(countsize>0){
	$('.notificationcount').text(countsize);
	}else{
		$('.noticircle').attr("style","display:none");
	}
}*/
function getMessageForIndividualUser(data){
	var individualmessages=courseData.getMessagesForuser(data);
	if(individualmessages!=undefined) {
		if(individualmessages.length!=0){
			var listMessages=countMessagesNotifications(individualmessages);
			renderTemplate('#messagetmpl',listMessages,'#Messageholder');
		}
		else{		
			renderTemplate('#nomessagetmpl',individualmessages,'#Messageholder');
		}
	} else { 
		renderTemplate('#nomessagetmpl',"No message found",'#Messageholder'); 
	}
	$(".navbar .menu").slimscroll({
		height: "200px",
		alwaysVisible: false,
		size: "3px"
	}).css("width", "100%");
}

function getNotifyForIndividualUser(){
	var individualNotifyData=ajaxhelper("getNotificationforindividualUserAction");
	if(individualNotifyData!="false"){
		if(individualNotifyData.length!=0){
			var listNotifications=countMessagesNotifications(_.where(individualNotifyData,{receivedstatus:'U'}));
			var notcount = (_.where(individualNotifyData,{receivedstatus:'U'})).length;
			if(notcount!=0){
				if(notcount>9){
					$('.notificationcount').text("9+");
				}else{
					$('.notificationcount').text(notcount);
				}	
			}else{
				$('.notificationcount').css("display","none");
			}
			renderTemplate('#notifytmpl',listNotifications,'#notificationholder');	
		}
		else{
			$('.notificationcount').attr("style","display:none");
			renderTemplate('#nomessagetmpl',individualNotifyData,'#notificationholder');
		}
	}
	else{	
		$('.notificationcount').attr("style","display:none");
		renderTemplate('#nonotificationstmpl',individualNotifyData,'#notificationholder');
	}
	$(".navbar .menu").slimscroll({
		height: "200px",
		alwaysVisible: false,
		size: "3px"
	}).css("width", "100%");	
}

function countMessagesNotifications(messaegeNotificationList){
	var messageNot=[];
	_.each(messaegeNotificationList,function(messaegeNotList) {
		if(messageNot.length<5){
			messageNot.push(messaegeNotList);
		}
	});		
	return messageNot;		
}
