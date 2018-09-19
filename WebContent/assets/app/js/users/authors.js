$(document).ready(function(){
	browse().init();
	browse().pageevents();
});	

var browse = (function () {
	var controller = {	
			board:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/browse/",undefined,"json","application/json");				
			},
			category:function(boardid){
				return Ajax.get(courseData.getContextPath()+"/rest/browse/category?boardid="+boardid+"",undefined,"json","application/json");
			},
			authors:function(coursecategoryid){
				return Ajax.get(courseData.getContextPath()+"/rest/boardcategory?coursecategoryid="+coursecategoryid,undefined,"json","application/json");
			}
	}; 
	var model = {
			board:function(){
				return controller.board();				
			},
			category:function(boardid){
				return controller.category(boardid);
			},
			authors:function(categoryid){
				return controller.authors(categoryid);
			},
			searchcourse:function(keyword){
				$('[name=keyword]').val(keyword);				
				$('[name=browseuserform]').attr('action','searchresult');
				$('[name=browseuserform]').submit();
			}
	};
	var view = {	
			board:function(){
				var list=model.board();
				if(list!="NO_CONTENT"){
					renderTemplate("#topcategoriestmpl",list , "#topcategoriestable");
					$('#topcategoriestable .leftsidelink:first').addClass('active');
					view.category();
					$('#topcategoriestable .leftsidelink').click(function(){
						$('#topcategoriestable .leftsidelink').removeClass('active');
						$(this).addClass('active');
						view.category();
					});	
				}else{
					renderTemplate("#nocoursestmpl",null,"#topcontainer");
					$('.searchbtn').hide();
				}
			},
			category:function(){
				var boardid=$('#topcategoriestable .leftsidelink.active').attr('boardid');
				var list=model.category(boardid);
				renderTemplate("#subcategoriestmpl",list , "#rightcategorytable");
				$("#rightcategorytable .subcategorylink:first").addClass('active');
				view.authorHeader();
				$("#rightcategorytable .subcategorylink").click(function(){
					$("#rightcategorytable .subcategorylink").removeClass('active');
					$(this).addClass('active');
					view.authorHeader();
				});
			},
			authorHeader:function(){
				renderTemplate("#coursesheadertmpl",null , "#rightheadertable");
				var categoryid=$('.subcategorylink.active').attr('coursecategoryid');
				view.authors(model.authors(categoryid));
				$('.learningcoursestab').click(function(){
					$('.learningcoursestab').removeClass('active');
					$(this).addClass('active');
					$('.leftsidelink').removeAttr('activetab');
					$('.leftsidelink.active').attr('activetab',$('.learningcoursestab.active').attr('href'));
				});
			},
			authors:function(list){
				var activetab = $('.leftsidelink[activetab]').attr('activetab');
				renderTemplate("#coursestmpl",list , "#righttable");
				if(activetab!=undefined){
					$('.learningcoursestab').removeClass('active');
					$('a[href="'+activetab+'"]').addClass('active');
					$('a[href="'+activetab+'"]').tab('show');
				}			
				else{
					$('.learningcoursestab').removeClass('active');
					$('.learningcoursestab:first').addClass('active');
					$('.learningcoursestab:first').tab('show');				
				}
			}
	}; 
	return {
		init: function () {
			initialize();
		},
		pageevents:function(){
			eventsfn();
		}
	}; 
	function initialize(){
		view.board();
	};	
	function eventsfn(){
		$('.searchbtn').click(function(e)  {
			$('.dropbox').slideDown(400);
			$('.searchtextbox').val('');
		});
		$('.closebtn').click(function(){
			$('.dropbox').slideUp(400);
		});
		$('.searchkwrdbtn').click(function(e){
			var searchkeyword=$('.searchtextbox').val().trim();
			if(searchkeyword!=undefined && searchkeyword.length!=0){
				model.searchcourse(searchkeyword);		
			}else{
				e.preventDefault();
			}
		});
		$('.searchtextbox').keypress(function(event){
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(keycode == '13'){
				var searchkeyword=$('.searchtextbox').val().trim();
				if(searchkeyword!=undefined && searchkeyword.length!=0){
					model.searchcourse(searchkeyword);
				}else{
					event.preventDefault();
				}
			}
		});
	}
});