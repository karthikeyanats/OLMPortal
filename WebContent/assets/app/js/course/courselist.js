$(document).ready(function(){
	adminCourses.getFirstLoadItems();
	
});
adminCourses={
		getFirstLoadItems:function(){
			var self=this;
			self.CourseCategory();
			self.renderInitialCategoryCourses();
		},
 		CourseCategory:function(){
			var self=this;
			var categorystatus={}; 
			categorystatus.coursecategorystatus="A";
			var coursecategories=courseData.getAllActiveCategorywiseNoofCourse(categorystatus);
			self.renderCourseCategory(coursecategories);
			self.renderInitialCategoryCourses();
		},
  		renderCourseCategory:function(courseCategoryList){
			var self=this;
			renderTemplate("#coursecategorytmpl",courseCategoryList,".coursecategorylist");
			$('.webmenustyle:first').addClass('active');
			$('.courselink:first').addClass('active');
		    $('.courselink').click(function(){
				$('.courselink').removeClass('active');
				$(this).addClass('active');
				var categoryidobj={};	
				categoryidobj.coursecategoryid=$(this).attr('coursecategoryid');
				categoryidobj.coursecategorystatus="A";
				self.loadCategorywiseCourses(categoryidobj);
			});
 		},
		renderInitialCategoryCourses:function(){
			var self=this;
			var categoryidobj={};
			var coursecategoryid=$('.courselink:first').attr('coursecategoryid');
			categoryidobj.coursecategoryid=coursecategoryid;
			self.loadCategorywiseCourses(categoryidobj);
		},
		loadCategorywiseCourses:function(categoryidobj){
			self = this;
			var categoryCourses=courseData.getGuestCategoryCourses(categoryidobj);
			var catcourse={};
			catcourse.catcourses=categoryCourses;
			renderTemplate("#coursecategorywisecoursetmpl",catcourse,".ecloudcourseswithdesctable");
			$('.catname').text($('.courselink.active').attr('coursecategoryname'));
			$('.popcourselink').click(function(){
				var courseid=$(this).attr('courseid');
				self.previewCourseData(courseid);
			});
			$('.crsprice').each(function(){
				var price=$(this).attr('price');
				var priceicon = $(this).attr('priceicon');
				if(price=="Free"){
					$(this).html("Free");
				}
				else if(price==""){
					$(this).html("");
				}
				else{
						$(this).html("<i class='fa "+priceicon+"'></i>"+price);	
				}
			});
			$('.ratingcourse').each(function(){
				var rating=$(this).attr('rating');
				$(this).raty({readOnly: true, score: rating });
			});	
		},
		previewCourseData:function(courseid){
			$('[name=courseid]').val(courseid);
			$('[name=popcourseform]').attr('action','preview');
			$('[name=popcourseform]').submit();
		}
};