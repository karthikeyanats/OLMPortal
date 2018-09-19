$(document).ready(function(){
	//indexPage.loadCategories();
	$('.searchbtn').click(function(e){
		var searchkeyword=$('.searchbox').val().trim();
		if(searchkeyword!=undefined && searchkeyword.length!=0){
			$('[name=keyword]').val(searchkeyword);
			$('[name=previewguestform]').attr('action','ssearchresult');
			$('[name=previewguestform]').submit();			
		}
		else{
			e.preventDefault();
		}
	});
	$('.searchbox').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13'){
			var searchkeyword=$('.searchbox').val().trim();
			if(searchkeyword!=undefined && searchkeyword.length!=0){
				$('[name=keyword]').val(searchkeyword);				
				$('[name=previewguestform]').attr('action','ssearchresult');
				$('[name=previewguestform]').submit();
			}
			else{
				event.preventDefault();
			}
		}
	});
	indexPage.loadNoofCategories();
	
});

indexPage={
		loadCategories:function(){
			var self=this;
			var categorystatus={};
			categorystatus.coursecategorystatus="A";
			var coursecategories=courseData.getGuestCategories(categorystatus);
			self.renderCourseCategories(coursecategories);
			self.renderInitialCategoryCourses();
		},
		loadNoofCategories:function(){
			var self=this;
			var categorystatus={};
			categorystatus.coursecategorystatus="A";
			var coursecategories=courseData.getAllActiveCategorywiseNoofCourse(categorystatus);
			self.renderCourseCategories(coursecategories);
			self.renderInitialCategoryCourses();
		},
		renderCourseCategories:function(coursecategories){
			var self=this;
			renderTemplate("#ecloudcoursestmpl",coursecategories,"#ecloudcoursestable");
			$('.leftsidelink:first').addClass('active');
			$('.leftsidelink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				var categoryidobj={};	
				categoryidobj.coursecategoryid=$(this).attr('coursecategoryid');
				categoryidobj.coursecategorystatus="A";
				self.loadCategorywiseCourses(categoryidobj);
				window.scrollTo(0,0);
			});	
		},
		renderInitialCategoryCourses:function(){
			var self=this;
			var categoryidobj={};
			var coursecategoryid=$('.leftsidelink.active').attr('coursecategoryid');
			//var data=courseData.getMasterAdminId();
			//categoryidobj.organizationid=data[0].organizationid;
			categoryidobj.coursecategoryid=coursecategoryid;
			self.loadCategorywiseCourses(categoryidobj);
		},
		loadCategorywiseCourses:function(categoryidobj){
	
			//categoryidobj.coursecategoryid=coursecategoryid;
			var categoryCourses=courseData.getGuestCategoryCourses(categoryidobj);
			var catcourse={};
			catcourse.catcourses=categoryCourses;			
			templateData(catcourse);
			$('.catname').text($('.leftsidelink.active').attr('coursecategoryname'));

			$('.icon_price').each(function(){
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
				$(this).raty({ readOnly: true,score: rating, 
					starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
				    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
				    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
				    });
			});
			$('.coursepreviewbtn').click(function(){
				var courseid=$(this).attr('courseid');	 
				$('[name=courseid]').val(courseid); 
				$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
				var orgpersonid = $('#org-person-id').children().attr('id'); 
				if(orgpersonid != null & parseInt(orgpersonid)>0 ){
					$('[name=previewform]').attr('action','previewuser');
				}else{
					$('[name=previewform]').attr('action','preview');
				}
				
				$('[name=previewform]').submit();
			});
			
		}
};
function templateData(catcourse){
	renderTemplate("#ecloudcourseswithdesctmpl",catcourse,"#ecloudcourseswithdesctable");
	filteroption();
	listbox(catcourse); 
}
function listbox(catcourse){
	$('.inlisttype').click(function(){
		$('.selectval').text('Filter by');
		var selectval=$('.selectval').text();
		$(".statval").text('l');
		var stat=$(".statval").text();
		renderTemplate("#ecloudcourseswithdesctmpl",catcourse,"#ecloudcourseswithdesctable");
		filteroption();
		listbox(catcourse); 
		ratingandcourse(selectval,stat);
	});
	$('.inboxtype').click(function(){
		$('.selectval').text('Filter by');
		var selectval=$('.selectval').text();
		$(".statval").text('b');
		var stat=$(".statval").text();
		renderTemplate("#ecloudcourseswithdesctmplbox",catcourse,"#ecloudcourseswithdesctable");
		filteroption();
		listbox(catcourse); 
		ratingandcourse(selectval,stat);
	});
}
function filteroption(){
	$('.lowhigh').click(function(){
		var selectval	=	$(this).text();
		var stat=/*$('.statuschk').attr('status');*/$(".statval").text();
		var categoryidobj={};
		var coursecategoryid=$('.leftsidelink.active').attr('coursecategoryid');
	/*	var data=courseData.getMasterAdminId();
		categoryidobj.organizationid=data[0].organizationid;*/
		categoryidobj.coursecategoryid=coursecategoryid;
		var categoryCourses=courseData.getGuestCategoryCourses(categoryidobj);
		catcourse={};
		courses=[];
		catcourses=[];
		catcourses[0]={};
		//Other than Free
		plandata=categoryCourses[0].courses;
		 items_to_add = [];
		 _.each(plandata, function(item){
		     if(item.price != 'Free'){
		         items_to_add.push(item);
		     }
		 });
		//a=_.sortBy(items_to_add, function(obj){ return obj.price;});
		a=_(items_to_add).sortBy(function(obj){return parseInt(obj.price)}); 

		 //only Free 
		b=_.where(categoryCourses[0].courses,{price:"Free"});
		catcourses[0].courses=_.union(b,a);
		catcourses[0].coursecategoryid=categoryCourses[0].coursecategoryid;
		catcourses[0].coursecategoryname=categoryCourses[0].coursecategoryname;
		catcourse.catcourses=catcourses;
		/*templateData(catcourse);
		ratingandcourse();*/
		if(stat=='l'){
			renderTemplate("#ecloudcourseswithdesctmpl",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}else if(stat=='b'){
			renderTemplate("#ecloudcourseswithdesctmplbox",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}
		
	});
	
	$('.highlow').click(function(){
		var selectval	=	$(this).text();
		var stat=/*$('.statuschk').attr('status');*/$(".statval").text();
		var categoryidobj={};
		var coursecategoryid=$('.leftsidelink.active').attr('coursecategoryid');
		categoryidobj.coursecategoryid=coursecategoryid;
		var categoryCourses=courseData.getGuestCategoryCourses(categoryidobj);
		catcourse={};
		courses=[];
		catcourses=[];
		catcourses[0]={};
		//Other than Free
		plandata=categoryCourses[0].courses;
		items_to_add = [];
		 _.each(plandata, function(item){
		     if(item.price != 'Free'){
		    	 items_to_add.push(item);
		     }
		 });
		a=_.sortBy(items_to_add, function(obj){ return parseInt(obj.price )* -1;});
		b=_.where(categoryCourses[0].courses,{price:"Free"});				
		catcourses[0].courses=_.union(a,b);
		catcourses[0].coursecategoryid=categoryCourses[0].coursecategoryid;
		catcourses[0].coursecategoryname=categoryCourses[0].coursecategoryname;
		catcourse.catcourses=catcourses;
		/*templateData(catcourse);
		ratingandcourse();*/
		if(stat=='l'){
			renderTemplate("#ecloudcourseswithdesctmpl",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}else if(stat=='b'){
			renderTemplate("#ecloudcourseswithdesctmplbox",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}
		
	});
	
	$('#popcourse').click(function(){
		var selectval	=	$(this).text();
		var stat=/*$('.statuschk').attr('status');*/$(".statval").text();
		var categoryidobj={};
		var coursecategoryid=$('.leftsidelink.active').attr('coursecategoryid');
		categoryidobj.coursecategoryid=coursecategoryid;
		var categoryCourses=courseData.getGuestCategoryCourses(categoryidobj);
		catcourse={};
		courses=[];
		catcourses=[];
		catcourses[0]={};
	
		plandata=categoryCourses[0].courses;
		catcourses[0].courses=_.sortBy(plandata, function(obj){ return obj.rating *-1;});
		catcourses[0].coursecategoryid=categoryCourses[0].coursecategoryid;
		catcourses[0].coursecategoryname=categoryCourses[0].coursecategoryname;
		catcourse.catcourses=catcourses;
		/*templateData(catcourse);
		ratingandcourse();*/	
		if(stat=='l'){
			renderTemplate("#ecloudcourseswithdesctmpl",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}else if(stat=='b'){
			renderTemplate("#ecloudcourseswithdesctmplbox",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}
		
	});
	$('#enrollwise').click(function(){
		var selectval	=	$(this).text();
		var stat=/*$('.statuschk').attr('status');*/$(".statval").text();
		var categoryidobj={};
		var coursecategoryid=$('.leftsidelink.active').attr('coursecategoryid');
		categoryidobj.coursecategoryid=coursecategoryid;
		var categoryCourses=courseData.getGuestCategoryCourses(categoryidobj);
		catcourse={};
		courses=[];
		catcourses=[];
		catcourses[0]={};
	
		plandata=categoryCourses[0].courses;
		catcourses[0].courses=_.sortBy(plandata, function(obj){ return obj.coursesubscribers *-1;});
		catcourses[0].coursecategoryid=categoryCourses[0].coursecategoryid;
		catcourses[0].coursecategoryname=categoryCourses[0].coursecategoryname;
		catcourse.catcourses=catcourses;
		/*templateData(catcourse);
		ratingandcourse();*/	
		if(stat=='l'){
			renderTemplate("#ecloudcourseswithdesctmpl",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}else if(stat=='b'){
			renderTemplate("#ecloudcourseswithdesctmplbox",catcourse,"#ecloudcourseswithdesctable");
			filteroption();
			listbox(catcourse); 
			ratingandcourse(selectval,stat);
		}
		
	});
}
function ratingandcourse(selectval,stat){
	$('.catname').text($('.leftsidelink.active').attr('coursecategoryname'));
	$('.selectval').text(selectval);
	 $("[name=statusval]").val(stat);
	 var statval =$("[name=statusval]").val();
	$(".statval").text(statval);

	$('.icon_price').each(function(){
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
		$(this).raty({readOnly: true,score: rating }); 
	});
	$('.popcourselink').click(function(){
		var courseid = $(this).attr('courseid');
			$('[name=courseid]').val(courseid);
			$('[name=coursemanage]').attr('action','preview');
			$('[name=coursemanage]').submit();
		});
	$('.coursepreviewbtn').click(function(){
		var courseid=$(this).attr('courseid');	 
		$('[name=courseid]').val(courseid); 
		$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
		var orgpersonid = $('#org-person-id').children().attr('id').trim(); 
		if(orgpersonid != null & parseInt(orgpersonid)>0 ){
			$('[name=previewform]').attr('action','previewuser');
		}else{
			$('[name=previewform]').attr('action','preview');
		}
		
		$('[name=previewform]').submit();
	});
}

var data=[];
var index=[];
function searchCourses(keyword){
	var courses=ajaxhelper("searchOrgcourses?keyword="+keyword);
	if(courses!="false"){
	for (var i = 0; i < courses.length;i++ ) {
		data[i]=courses[i].coursetitle+" - "+courses[i].author;
		index[i]=courses[i].courseid;
	}
	return data;
	}
	else{
		return "false";
	}
}
var timer = null;
$('.searchbox').typeahead({
			matcher: function () { return true; },
			source:function(query,process){
				if(timer){
					 clearTimeout(timer);
				}
				timer=setTimeout(function(){
				if(query.trim()!="")
				{
					var result=searchCourses(query);
					if(result!="false"){
						process(result);
					    }
					else{
						process(["no match found"]);
					}
					
				}
			},1500);
		},
	items:20,
	updater:function (item) {
	if(item!="no match found"){
	return previewReDirector(index[data.indexOf(item)]);
	}
	} 
});
function previewReDirector(courseid){	
	$('[name=courseid]').val(courseid);
	$('[name=previewform]').attr('action','preview');
	$('[name=previewform]').submit();

}