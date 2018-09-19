$(function(){ 
	$("#userauthorsearch").addClass('hover').addClass('active');
	authorsearch.authors.init();
});

var authorsearch = authorsearch || {};

authorsearch.authors = (function(){

	var controller = {
			authors:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/tutors",undefined,"json","application/json");
			},
			ratings:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/tutors/ratings",undefined,"json","application/json");
			},
			courses:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/tutors/courses",undefined,"json","application/json");
			}
	};

	var model = {
			authors:function(){
				var modifiedList = util.authors(controller.authors(),controller.ratings(),controller.courses()); 
				return modifiedList;
			}
	};

	var view = {
			authors:function(){
				var list = model.authors();
				view.filteredView(list);
				
				$('.resetfilter').click(function(){
					view.filteredView(list);
				});
				
				$('.starlink').click(function(){
					var at = $(this).attr('star');
					view.filteredView(_.where(list, {ratings: parseInt(at)}));
				});
				
				$('.authornamesearchfield').keypress(function(e){
					var authorname = $(this).val().trim();
					if (e.which == 13){
						if(authorname.length!=0){				
							var personids = util.authorNameFilters(authorname,list);
							var modifiedList = util.getPersonObjects(personids,list); 
							view.filteredView(modifiedList);
						}
					}
				});
				
				$('.authornamesearchlink').click(function(){
					var authorname = $('.authornamesearchfield').val().trim();
					if(authorname.length!=0){
						var personids = util.authorNameFilters(authorname,list);
						var modifiedList = util.getPersonObjects(personids,list); 
						view.filteredView(modifiedList);	
					}
				});

				$('.keywordsearchfield').keypress(function(e){
					var keyword = $(this).val().trim();
					if (e.which == 13){
						if(keyword.length!=0){				
							var personids = util.keywordFilters(keyword,list);
							var modifiedList = util.getPersonObjects(personids,list); 
							view.filteredView(modifiedList);
						}
					}
				});
				$('.keywordsearchlink').click(function(){
					var keyword = $('.keywordsearchfield').val().trim();
					if(keyword.length!=0){
						var personids = util.keywordFilters(keyword,list);
						var modifiedList = util.getPersonObjects(personids,list); 
						view.filteredView(modifiedList);	
					}
				});
			},
			filteredView:function(list){
				renderTemplate("#authorstmpl", list, "#authorstable");
				applypagination($('.pagapply'),8);
				$('.ratingcourse').each(function(){
					var rating=$(this).attr('rating');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});
				});
				
				$('.viewdetailslink').click(function(){
					var personid = $(this).attr('personid');
					var orgpersonid = $(this).attr('orgpersonid');
					$('[name=personid]').val(personid);
					$('[name=orgpersonid]').val(orgpersonid);
					$('[name=authorsearchform]').submit();
				});

			}
	};

	var util = {
			authors:function(list,ratings,courseList){
				var ratingsList = util.authorsrating(ratings);
				var persons = [];
				var personids =_.uniq(_.pluck(list,'personid'));
				_.each(personids,function(personid){
					var personidarray = _.where(list,{personid:personid});
					var person = _.pick(personidarray[0],'personphoto','number','userid','firstName','lastName','personid');
					var personrating = _.where(ratingsList, {personid: personid});
					if(personrating[0]){
						person["ratings"] = personrating[0].rating;	
					}else{
						person["ratings"] = 0;
					}
					person.course = util.authorCourses(courseList,personid);
					person["orgpersonid"] = person.course[0].orgpersonid;
					var urls = _.pluck(personidarray,'url');
					if(urls[0]!=null){
						_.each(urls,function(url){
							if(_.contains(url.split('.'), 'twitter')){
								person["twitterurl"] = url;
							}else if(_.contains(url.split('.'), 'facebook')){
								person["facebookurl"] = url;
							}else if(_.contains(url.split('.'), 'linkedin')){
								person["linkedinurl"] = url;
							}else{
								person["websiteurl"] = url;
							}
						});
					}
					persons.push(person);
				});				
				persons = _.sortBy(persons, function(obj){ return obj.ratings *-1;});
				return persons;
			},
			authorsrating:function(list){
				var ratings=[];
				var personids = (_.uniq(_.pluck(list,'personid')));
				_.each(personids,function(personid){
					var authorrating={};
					var ratingArray =_.where(list,{personid:personid});
					var averageratings=(_.compact(_.pluck(ratingArray,'averagerating')));
					var averageratingsum = _.reduce(averageratings, function(memo, num){return memo + parseInt(num);}, 0);
					var ratingaverage=averageratingsum/(ratingArray.length);
					authorrating.personid = personid;
					if(_.isNaN(ratingaverage)){
						authorrating.rating=0;
					}else{
						authorrating.rating=Math.round(ratingaverage);	
					}
					ratings.push(authorrating);
				});
				return ratings;
			},
			authorCourses:function(courseList,personid){
				var coursesarray = _.where(courseList, {personid: personid});
				var courseids=_.uniq(_.pluck(coursesarray,'courseid'));
				var courses = [];
				_.each(courseids,function(courseid){
					var thiscoursesarray = _.where(coursesarray, {courseid: courseid});
					course=_.pick(thiscoursesarray[0],'courseid','coursetitle','orgpersonid');
					courses.push(course);
				});
				return courses;				
			},
			authorNameFilters:function(name,searchList){
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'personid'));
				_.each(personids,function(personid){
					var person={};
					var personarray = _.where(searchList,{personid:personid});
					var firstnamestr =personarray[0].firstName.toLowerCase();
					if(util.contains(firstnamestr, name.toLowerCase())){
						person = _.pick(personarray[0],'personid');
						persons.push(person);
					}
					if(personarray[0].lastName!=null){
						var lastnamestr =personarray[0].lastName.toLowerCase();
						if(util.contains(lastnamestr, name.toLowerCase())){
							person = _.pick(personarray[0],'personid');
							persons.push(person);
						}
					}
				});
				return persons;
			},
			keywordFilters:function(keyword,searchList){
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'personid'));
				_.each(personids,function(personid){
					var person={};
					var personarray = _.where(searchList,{personid:personid});
					var courses = personarray[0].course;
					var coursesarray = _.pluck(courses,'coursetitle');
					var coursepresence = util.containsAny(keyword.toLowerCase(),coursesarray);
					console.info("coursepresence is "+coursepresence);
					if(coursepresence==true){
						person = _.pick(personarray[0],'personid');
						persons.push(person);	
					}					
				});
				return persons;
			},
			getPersonObjects:function(personidarray,list){
				var persons = [];
				var personids =_.uniq(_.pluck(personidarray,'personid'));
				_.each(personids,function(personid){
					var personidarray = _.where(list,{personid:personid});
					var person = _.pick(personidarray[0],'personphoto','number','userid',
							'firstName','lastName','personid','ratings','twitterurl','facebookurl',
							'linkedinurl','websiteurl','course','orgpersonid');
					persons.push(person);
				});				
				persons = _.sortBy(persons, function(obj){ return obj.ratings *-1;});
				return persons;
			},
			contains:function(r, s) {
				return r.indexOf(s) > -1;
			},
			containsAny:function(str, substrings) {
				for (var i = 0; i != substrings.length; i++) {
					var substring = substrings[i].toLowerCase();
					if (substring.split(str).length > 1) {
						return true;
					}
				}
				return false; 
			}			
	};

	return {
		init:function(){
			initialize();	
		}
	};	

	function initialize(){
		view.authors();
	}

})();