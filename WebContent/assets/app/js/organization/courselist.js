$(document).ready(function(){
	orgCourses.getFirstLoadItems();	
});

orgCourses={
		getFirstLoadItems:function(){
			var self=this;
			self.allCourseItems();			
		},
		allCourseItems:function(){
			var self=this;
			self.categoriesData();
			var categoryidobj={};
			$('.courselinktodescbtn:first').addClass('active');
			categoryidobj.coursecategoryid=$('.courselinktodescbtn:first').attr('coursecategoryid');
			categoryidobj.coursecategorystatus="A";
			//self.categoryCoursesData(categoryidobj);
		},
		/*categoryCoursesData:function(categoryidobj){
			var self=this;
			self.loadCategorywiseCourses(categoryidobj);
		},*/
		loadCategories:function(){
			var self=this;
			var categorystatusobj={};
			categorystatusobj.coursecategorystatus="A";
			self.categoriesData();
		},
		categoriesData:function(){
			var orgid=$('.organization').attr('organizationid');
			var categories=Ajax.get("organizationcourselist?organizationid="+parseInt(orgid),undefined,"json","application/json");
			renderTemplate("#coursestmpl",categories,"#coursestable");
			
		},
};		
