/**
 * document ready function
 */
$(document).ready(function(){
	searchList=helper.results(model.results($('.keyword').attr('keyword')));
	searchresult.init(searchList);
});

/**
 * search result object
 */
searchresult={
		init:function(courses){
			view.results(courses);
			view.categories(helper.categories(courses));
			view.instructionallevels(helper.instructionallevels(courses));
			view.prices(helper.prices(courses));
			$('.catlink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				$('.keyword').attr('st',"");
				view.results(helper.categorycourses($(this).attr('coursecategoryid'),courses));				
			});
			$('.levelcrslink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				$('.keyword').attr('st',"");
				view.results(helper.levelcourses($(this).attr('level'),courses));
			});
			$('.pricecourselink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				$('.keyword').attr('st',"");
				view.results(helper.pricecourses($(this).attr('a'),courses));
			});
		}
};

/**
 * search result view object
 */
view={
		results:function(searchresult){
			var activetab = $('.keyword').attr('at');
			var activefiter = $('.keyword').attr('st');
			renderTemplate("#coursestmpl", searchresult, "#coursestable");
			if(activetab.length!=0){
				$('.learningcoursestab').removeClass('active');
				$('a[href="'+activetab+'"]').addClass('active');
				$('a[href="'+activetab+'"]').tab('show');
			}			
			else{
				$('.learningcoursestab').removeClass('active');
				$('.learningcoursestab:first').addClass('active');
				$('.learningcoursestab:first').tab('show');				
			}
			if(activefiter!=undefined){
				if(activefiter.length!=0){
					$('.filtertitle').text($('.filterlinks[a='+activefiter+']').text());
				}else{
					$('.filtertitle').text("Filter By");		
				}	
			}else{
				$('.filtertitle').text("Filter By");		
			}
			$('.srkey').html($('.keyword').attr('keyword'));
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
				$(this).raty({
					starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
					starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
					starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png',
					readOnly: true, score: rating });
			});	
			$('.learningcoursestab').click(function(){
				$('.learningcoursestab').removeClass('active');
				$(this).addClass('active');
				$('.keyword').attr('at',$('.learningcoursestab.active').attr('href'));
			});
			$('.authorsearch').click( function() {
				var authorname=$('.authornametxt').val().trim();
				if(authorname.length!=0){
					view.results(helper.authorcourses(authorname,searchList));
					$('.authornametxt').val(authorname);
				}
			});
			$('.authornametxt').keypress(function(event){
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if(keycode == '13'){
					var authorname=$(this).val().trim();
					if(authorname.length!=0){
						view.results(helper.authorcourses(authorname,searchList));
						$('.authornametxt').val(authorname);
					}
				}
			});
			$('.allresultslink').click(function(){
				view.results(searchList);
			});

			$('.filterlinks').click(function(){
				$('.filtertitle').text($(this).text());
				var filter=$(this).attr('a');
				switch(filter){
				case "all":
					courses=searchresult;
					break;
				case "p":
					courses=helper.filterCourses(searchresult,"p");
					break;
				case "h":
					courses=helper.filterCourses(searchresult,"h");
					break;
				case "l":
					courses=helper.filterCourses(searchresult,"l");
					break;
				case "e":
					courses=helper.filterCourses(searchresult,"e");
					break;
				}
				$('.keyword').attr('st',filter);
				return view.results(courses,$('.learningcoursestab.active'));;
			});
			$('.startlearninggridbtn').click(function(){
				helper.redirectPage($(this).attr('courseid'));
			});			
		},
		categories:function(searchresult){
			renderTemplate("#categoriestmpl", searchresult, "#categoriestable");
		},
		instructionallevels:function(searchresult){
			renderTemplate("#levelstmpl", searchresult, "#levelstable");
		},
		prices:function(searchresult){
			renderTemplate("#pricetmpl", searchresult, "#pricetable");
			$('.iconprice').each(function(){

				var priceicon = $(this).attr('priceicon');

				$(this).html("<i class='fa "+priceicon+"'></i>");	

			});
		}
};

/**
 * search result model object
 */
model={
		results:function(keyword){
			var keywordobj={};
			keywordobj["keyword"]=keyword;
			return controller.results(keywordobj);
		}
};

/**
 * search result controller object
 */
var controller={
		results:function(keywordobj){
			return courseData.getSearchResults(keywordobj);
		}
};

/**
 * search result helper object
 */
helper={
		results:function(searchResultList){
			var courses=[];
			var courseids =_.uniq(_.pluck(searchResultList,'courseid'));
			_.each(courseids,function(courseid){
				var course={};
				var coursearray = _.where(searchResultList,{courseid:courseid});
				course = _.pick(coursearray[0],'courseid','coursetitle','courselogo','price','priceid','firstName','lastName','coursecategoryid','coursecategoryname','instructoinallevel');
				if(course.price!="Free"){
					course.price=parseInt(coursearray[0].price);	
				}
				else{
					course.price=coursearray[0].price;
				}				
				course.coursesubscribers=_.uniq(_.pluck(coursearray,'courseenrollmentid')).length;
				course.courseratingids=_.compact(_.uniq(_.pluck(coursearray,'courseratingid'))).length;
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
				courses.push(course);
			});
			courses = _.sortBy(courses, function(obj){ return obj.coursetitle;});
			return courses;
		},
		categories:function(searchList){
			var categories=[];
			var uniqcategories=_.uniq(_.pluck(searchList,'coursecategoryid'));
			_.each(uniqcategories,function(coursecategoryid){
				var category={};
				var categoryarray = _.where(searchList,{coursecategoryid:coursecategoryid});
				category = _.pick(categoryarray[0],'coursecategoryid','coursecategoryname');
				category["count"]=_.uniq(_.pluck(categoryarray,'courseid')).length;
				categories.push(category);
			});
			return categories;
		},
		instructionallevels:function(searchList){
			var levels=[];
			var uniqlevels=_.uniq(_.pluck(searchList,'instructoinallevel'));
			_.each(uniqlevels,function(instructoinallevel){
				var level={};
				var levelarray = _.where(searchList,{instructoinallevel:instructoinallevel});
				level = _.pick(levelarray[0],'instructoinallevel');
				level["count"]=_.uniq(_.pluck(levelarray,'courseid')).length;
				levels.push(level);
			});
			return levels;
		},
		prices:function(searchList){
			var prices=[];
			var allprices= _.pluck(searchList,'price'),			
			freecourses = _.filter(allprices, function(num){ return num == 'Free';}),
			onttothousand = _.filter(allprices, function(num){ return num >0 && num<1001 ;}),
			oneto5k =  _.filter(allprices, function(num){ return num >1000 && num<5001 ;}),
			above5k =  _.filter(allprices, function(num){ return num >5000;});
			/*freecourses=_.difference(allprices,_.without(allprices,'Free'));
			var onttothousand=_.filter(allprices, function(num){ return num >parseInt(1) && num<parseInt(1001);});
			var oneto5k=_.filter(allprices, function(num1){ return num1 >(parseInt(1000)) && num1<(parseInt(5001));});
			var above5k=_.compact(_.difference(allprices, (_.union(freecourses, onttothousand,oneto5k))));*/
			prices["freecourses"]=freecourses.length,prices["upto1k"] = onttothousand.length;
			prices["oneto5k"]=oneto5k.length,prices["above5k"]=above5k.length;
			return prices;
		},
		categorycourses:function(categoryid,searchList){
			return _.where(searchList,{coursecategoryid:parseInt(categoryid)});
		},
		levelcourses:function(level,searchList){
			return _.where(searchList,{instructoinallevel:level});
		},
		pricecourses:function(pricerange,searchList){
			var self=this;
			returnlist=[];
			switch (pricerange) {
			case "f":
				returnlist=_.where(searchList,{price:'Free'});
				break;
			case "oo":
				returnlist=self.onetoonekkcoures(searchList);
				break;
			case "of":
				returnlist=self.onetofivekcoures(searchList);
				break;
			case "af":
				returnlist=self.abovefivekcoures(searchList);
				break;
			}
			return returnlist;
		},
		onetoonekkcoures:function(searchList){
			var courses=[];
			var courseids =_.uniq(_.pluck(searchList,'priceid'));
			_.each(courseids,function(priceid){
				var course={};
				var coursearray = _.where(searchList,{priceid:priceid});
				if(coursearray[0].price>0 && coursearray[0].price<1001){
					course = _.pick(coursearray[0],'courseid','coursetitle','courselogo','price','priceid','firstName','lastName','coursecategoryid','coursecategoryname','instructoinallevel','rating','coursesubscribers');
					courses.push(course);
				}
			});
			return courses;
		},
		onetofivekcoures:function(searchList){
			var courses=[];
			var courseids =_.uniq(_.pluck(searchList,'priceid'));
			_.each(courseids,function(priceid){
				var course={};
				var coursearray = _.where(searchList,{priceid:priceid});
				if(coursearray[0].price>1000 && coursearray[0].price<5001){
					course = _.pick(coursearray[0],'courseid','coursetitle','courselogo','price','priceid','firstName','lastName','coursecategoryid','coursecategoryname','instructoinallevel','ratingaverage','coursesubscribers');
					courses.push(course);
				}
			});
			return courses;
		},
		abovefivekcoures:function(searchList){
			var courses=[];
			var courseids =_.uniq(_.pluck(searchList,'priceid'));
			_.each(courseids,function(priceid){
				var course={};
				var coursearray = _.where(searchList,{priceid:priceid});
				if(coursearray[0].price>5001){
					course = _.pick(coursearray[0],'courseid','coursetitle','courselogo','price','priceid','firstName','lastName','coursecategoryid','coursecategoryname','instructoinallevel','ratingaverage','coursesubscribers');
					courses.push(course);
				}
			});
			return courses;
		},
		authorcourses:function(name,searchList){
			var courses=[];
			var courseids =_.uniq(_.pluck(searchList,'courseid'));
			_.each(courseids,function(courseid){
				var course={};
				var coursearray = _.where(searchList,{courseid:courseid});
				if(coursearray[0].firstName.toLowerCase()==name.toLowerCase()){
					course = _.pick(coursearray[0],'courseid','coursetitle','courselogo','price','priceid','firstName','lastName','coursecategoryid','coursecategoryname','instructoinallevel','ratingaverage','coursesubscribers');
					courses.push(course);
				}
			});
			return courses;
		},
		filterCourses:function(searchResults,ftype){
			items_to_add = [];
			_.each(searchResults, function(item){
				if(item.price != 'Free'){
					items_to_add.push(item);
				}
			});
			switch(ftype){
			case "p":
				courses=_.sortBy(searchResults, function(obj){ return obj.rating *-1;});
				break;
			case "h":
				a=_.sortBy(items_to_add, function(obj){ return parseInt(obj.price )* -1;});
				b=_.where(searchResults,{price:"Free"});				
				courses=_.union(a,b);
				break;
			case "l":
				a=_(items_to_add).sortBy(function(obj){return parseInt(obj.price);}); 
				b=_.where(searchResults,{price:"Free"});
				courses=_.union(b,a);
				break;
			case "e":
				courses=_.sortBy(searchResults, function(obj){ return obj.coursesubscribers *-1;});
				break;
			}
			return courses; 
		},
		redirectPage:function(courseid){
			$('[name=courseid]').val(courseid);
			$('[name=startlearninggridform]').attr('action','preview');
			$('[name=startlearninggridform]').submit();	

		}
};