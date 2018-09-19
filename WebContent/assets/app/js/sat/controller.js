var controllers=function(){
	var context=courseData.getContextPath();
 return {
	 	/**
		 * CONNECT DELEGATES OBJECT FOR GET DATA
		 * @Param String 
		 * @returns JSONData
		 */
	 	connectDelegate:function(extension){
	 	  return Ajax.get(context+"/rest/connector"+extension,null,"json","application/json");
		},
		/**
 		 * CONNECT DELEGATES OBJECT FOR SAVE QUESTION DATA
 		 * @param String
 		 * @param Object
 		 * @returns String
 		 */
 		saveDelegateData:function(url,object){
			return Ajax.post(context+url, object,"json","application/json");
 		},
		/**
		 * CONNECT DELEGATES BOJECT FOR QUESTION DELETING
		 * @param String Id
		 * @param String Status
		 */
		deleteDelegateData:function(questionid){
			return Ajax.get(context+"/rest/sat/delete?questionid="+questionid,null,"json","application/json");
		},
		/**
		 * CONNECT DELEGATES OBJECT FOR BULK QUESTION UPLOAD
		 * @parm Object
		 * @return String
		 */
		savebulkDelegate:function(object){
			return Ajax.post(context+"/rest/connector/saveBulkQst",object,"json","application/json");
		},
		/**
		 * CONNECT DELEGATES OBJECT FOR EXAM
		 * @param String id
		 */
		examDelegate:function(extension,object){
			return Ajax.post(context+"/rest/exam"+extension,object,"json","application/json");
		},
		/**
		 * CONNECT DELEGATES OBJECT FOR GET DATA IN EXAM ACTION
		 */
		getExamDelegate:function(extension,object){
			return Ajax.get(context+"/rest/exam"+extension,object,"json","application/json");
		}
	 };
}();