$(document).ready(function(){
	$('.searchbtn').click(function(e){
		var searchkeyword=$('.searchbox').val().trim();
		if(searchkeyword!=undefined && searchkeyword.length!=0){
			$('[name=keyword]').val(searchkeyword);
			$('[name=searchresultform]').submit();
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
				$('[name=previewform]').attr('action','searchresult');
				$('[name=previewform]').submit();
			}
			else{
				event.preventDefault();
			}
		}
	});
	browsecourse.domready();
	$('.userscountbtn').tooltip();
});

var browsecourse={
		domready:function(){
			browsecourse.view.categories();
			
		}
};

browsecourse.view = {
		categories:function(){
			var dd = browsecourse.data.categories();
			if(dd!=undefined){
				
			renderTemplate("#categoriestmpl",browsecourse.data.categories(),"#categoriestable");
			browsecourse.view.courses($('.leftsidelink:first').attr('coursecategoryid'),"all");
			$('.leftsidelink:first').addClass('active');
			}else{
				renderTemplate("#nocoursestmpl",'',"#righttable");
			}
			$('.leftsidelink').click(function(){
				$('.leftsidelink').removeClass('active');
				$(this).addClass('active');
				browsecourse.view.courses($(this).attr('coursecategoryid'),"all");
			});
		},
		courses:function(coursecategoryid,filter,active){
			var self=this;
			var catcourse=browsecourse.data.courses(coursecategoryid,filter);
			
			if(catcourse!=undefined){
				renderTemplate("#coursestmpl",catcourse,"#righttable");	
			}
			else{
				renderTemplate("#nocoursestmpl",'',"#righttable");
			}
			
			if(active!=undefined){
				$('.learningcoursestab').removeClass('active');
				$('a[href="'+active.attr('href')+'"]').addClass('active');
				$('a[href="'+active.attr('href')+'"]').tab('show');
			}			
			else{
				$('.learningcoursestab').removeClass('active');
				$('.learningcoursestab:first').addClass('active');
				$('.learningcoursestab:first').tab('show');				
			}
			$('.learningcoursestab').click(function(){
				$('.learningcoursestab').removeClass('active');
				$(this).addClass('active');
			});
			$('.filterlinks').each(function(){
				$(this).attr('coursecategoryid',catcourse.catcourses[0].coursecategoryid);
			});
			$('.filterlinks').click(function(){
				self.courses($(this).attr('coursecategoryid'),$(this).attr('a'),$('.learningcoursestab.active'));
			});
			$('.catname').html(catcourse.catcourses[0].coursecategoryname);
			$('.ratingcourse').each(function(){
				var rating=$(this).attr('rating');
				$(this).raty({
					starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
					starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
					starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png',
					readOnly: true, score: rating });
			});	
			$('.icon_price').each(function(){
				var price=$(this).attr('price');
				var priceicon = $(this).attr('priceicon');
				if(price=="Free"){
					$(this).html("Price : Free");
				}
				else if(price==""){
					$(this).html("");
				}
				else{
					$(this).html("Price : <i class='fa "+priceicon+"'></i>"+price);	
				}
			});
			$('.individualcourserow').hover(function(){
				var wishbtn=$(this).find('.wishbtn');
				var enrstatus=wishbtn.attr('courseenrollmentstatus');
				switch(enrstatus){
				case "A","C","B","W":
					wishbtn.hide();
				break;
				case "N":
					wishbtn.toggle().html("wishlist").toggleClass('newwishlist');
					$('.newwishlist').off().click(function(e){
						var status=browsecourse.data.wishlist($(this).attr('courseid'));
						if(status=="success"){
							setTimeout(function(){
								$('.wishlistsuccess').hide();
								status="";
								browsecourse.view.courses($('.leftsidelink.active').attr('coursecategoryid'),"all");
							},2000);
							e.preventDefault();
						}
						else{
							
						}
					});
					break;
				}				
			});

			$('.startlearninggridbtn').each(function(){
				var status=$(this).attr('courseenrollmentstatus');
				switch(status){
				case "W":
					$(this).html("<i class='fa fa-heart'></i>&nbsp;Wishlisted");
					break;
				case "A":
					$(this).html("<i class='fa fa-play-circle'></i>&nbsp;Learning");
					break;
				case "C":
					$(this).html("<i class='fa fa-play-circle'></i>&nbsp;Completed");
					break;
				case "B":
					$(this).html("<i class='fa fa-exclamation-triangle'></i>&nbsp;Blocked");
					break;
				case "P":
					$(this).html("<i class='fa fa-clock-o'></i></i>&nbsp;Waiting for approval");
					break;
				case "N":
					$(this).html("<i class='fa fa-play-circle'></i>&nbsp;Start Course");
					break;
				}
			});
			$('.startlearninggridbtn').click(function(){
				browsecourse.data.startlearning($(this).attr('courseenrollmentid'), $(this).attr('courseenrollmentstatus'), $(this).attr('courseid'));
			});
		}
};

browsecourse.data = {
		categories:function(){
			var categorystatusobj={};
			categorystatusobj.coursecategorystatus="A";
			return browsecourse.controller.categories(categorystatusobj);
		},
		courses:function(coursecategoryid,filter){
			var categoryidobj={};	
			categoryidobj.coursecategoryid=coursecategoryid;
			categoryidobj.coursecategorystatus="A";
			categoryidobj.filter=filter;
			return browsecourse.helper.courses(browsecourse.controller.courses(categoryidobj));
		},
		wishlist:function(courseid){
			var courseidobj={};
			courseidobj.courseid=courseid;
			return browsecourse.helper.wishlist(browsecourse.controller.wishlist(courseidobj));			
		},
		startlearning:function(ceid,cest,cid){
			var courseidobj={};
			courseidobj.courseid=cid;
			courseidobj.courseenrollmentstatus=cest;
			courseidobj.courseenrollmentid=ceid;
			browsecourse.helper.startlearning(courseidobj);
		},
		searchcheck:function(courseid){
			var self=this;
			var courseidobj={};
			if(courseid!=null){
				courseidobj.courseid=courseid;
				self.newCourseLearning(courseidobj,"browseform","previewuser");
			}
		},
		newCourseLearning:function(courseidobj,formname,actionname){
			var self=this;
			self.newCourseSubscribe(courseidobj,formname,actionname);
		},
		newCourseSubscribe:function(courseidobj,formname,actionname){
			$('[name=courseid]').val(courseidobj.courseid);
			$("[name='"+formname+"']").attr('action',actionname);
			$("[name='"+formname+"']").submit();
		},
};

browsecourse.controller = {
		categories:function(categorystatusobj){			
			return courseData.getAllActiveCategorywiseNoofCourse(categorystatusobj);
		},
		courses:function(categoryidobj){
			var courseslist={};
			cou=courseData.getGuestCategoryCourses(categoryidobj);
			switch(categoryidobj.filter){
			case "all":
				courseslist.normalCourses=cou;
				break;
			case "p":
				courseslist.normalCourses=browsecourse.helper.filterCourses(cou,"p");
				break;
			case "h":
				courseslist.normalCourses=browsecourse.helper.filterCourses(cou,"h");
				break;
			case "l":
				courseslist.normalCourses=browsecourse.helper.filterCourses(cou,"l");
				break;
			case "e":
				courseslist.normalCourses=browsecourse.helper.filterCourses(cou,"e");
				break;
			}
			courseslist.userCategoryCourses=ajaxhelperwithdata("loadUserCategoryCourses",categoryidobj);
			return courseslist;
		},
		wishlist:function(courseidobj){
			return courseData.EnrollUser(courseidobj);
		}
};

browsecourse.helper = {
		courses:function(helpercourses){
			var catcourse={};
			catcourse.catcourses=helpercourses.normalCourses;
			var _userCategoryCourses=helpercourses.userCategoryCourses;
			var indexObject = {};
			_.each(_userCategoryCourses, function(userItem, userIndex){
				_.each(catcourse.catcourses[0].courses, function(colelctionItem, colelctionIndex){
					if(colelctionItem.courseid == userItem.courseid ){ 
						indexObject[colelctionIndex] = userIndex;
					};		                
				});
			});  
			var keys = Object.keys(indexObject) ;
			for (var i = 0; i < keys.length; i++) {
				var val = indexObject[keys[i]];   
				catcourse.catcourses[0].courses[keys[i]].courseenrollmentstatus = _userCategoryCourses[val].courseenrollmentstatus;
				catcourse.catcourses[0].courses[keys[i]].courseenrollmentid = _userCategoryCourses[val].courseenrollmentid;
			};
			var collectionLength = catcourse.catcourses[0].courses.length;
			var parseCollectioncourseindex = []; 
			for(var i =0; i< collectionLength ; i++){
				var boolean = true;
				_.each( keys , function(item,index ){
					if(i == parseInt(item)){ 
						boolean = false;			
					}
				});
				if( boolean){
					parseCollectioncourseindex.push(i);	
				} 
			};  
			_.each(parseCollectioncourseindex, function(item, index){
				catcourse.catcourses[0].courses[item].courseenrollmentstatus = 'N';
			});
			return catcourse;
		},
		wishlist:function(status){
			if(status==="wishslisted"){
				$('.wishlistsuccess').show();
				window.scrollTo(0,0);
				return "success";
			}
			else if(status=""){
				return "nc";					
			}
		},
		startlearning:function(courseidobj){
			switch(courseidobj.courseenrollmentstatus){
			case "W":
				$('[name=courseid]').val(courseidobj.courseid);
				$('[name=courseenrollmentid]').val(courseidobj.courseenrollmentid);
				$('[name=wishlisted]').val("wishListed");
				$('[name=browseform]').attr('action','previewuser');
				$('[name=browseform]').submit();
				break;
			case "A":
				$('[name=courseid]').val(courseidobj.courseid);
				$('[name=courseenrollmentid]').val(courseidobj.courseenrollmentid);
				$('[name=browseform]').attr('action','previewcourse');
				$('[name=browseform]').submit();
				break;
			case "C":
				$('[name=courseid]').val(courseidobj.courseid);
				$('[name=courseenrollmentid]').val(courseidobj.courseenrollmentid);
				$('[name=browseform]').attr('action','previewcourse');
				$('[name=browseform]').submit();
				break;
			case "B":
				$('#subscribedmodal').modal('show');
				$('.enrolstatus').text("You have been blocked for this course. Please contact administrator");
				break;
			case "P":
				$('#subscribedmodal').modal('show');
				$('.enrolstatus').text("Your subsription for the course is wating for approval from the author. Please wait for a while.");
				break;
			case "N":
				$('[name=courseid]').val(courseidobj.courseid);
				$("[name='browseform']").attr('action','previewuser');
				$("[name='browseform']").submit();
				break;
			}
		},
		filterCourses:function(categoryCourses,ftype){
			var categoryidobj={};
			var coursecategoryid=$('.leftsidelink.active').attr('coursecategoryid');
			categoryidobj.coursecategoryid=coursecategoryid;
			catcourse={};
			courses=[];
			catcourses=[];
			catcourses[0]={};
			plandata=categoryCourses[0].courses;
			items_to_add = [];
			_.each(plandata, function(item){
				if(item.price != 'Free'){
					items_to_add.push(item);
				}
			});
			switch(ftype){
			case "p":
				catcourses[0].courses=_.sortBy(plandata, function(obj){ return obj.rating *-1;});
				break;
			case "h":
				a=_.sortBy(items_to_add, function(obj){ return parseInt(obj.price )* -1;});
				b=_.where(categoryCourses[0].courses,{price:"Free"});				
				catcourses[0].courses=_.union(a,b);
				break;
			case "l":
				a=_(items_to_add).sortBy(function(obj){return parseInt(obj.price);}); 
				b=_.where(categoryCourses[0].courses,{price:"Free"});
				catcourses[0].courses=_.union(b,a);
				break;
			case "e":
				catcourses[0].courses=_.sortBy(plandata, function(obj){ return obj.coursesubscribers *-1;});
				break;
			}			
			catcourses[0].coursecategoryid=categoryCourses[0].coursecategoryid;
			catcourses[0].coursecategoryname=categoryCourses[0].coursecategoryname;
			catcourse.catcourses=catcourses;
			return catcourse.catcourses; 
		}
};
var data=[];
var index=[];
function searchCourses(keyword){
	data.length=0;
	index.length=0;
	var courses=ajaxhelper("searchCourses?keyword="+keyword);
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
			return browsecourse.data.searchcheck(index[data.indexOf(item)]);
		}
	} 
}); 	