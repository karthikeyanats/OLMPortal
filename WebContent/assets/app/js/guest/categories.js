var boards = (function () {
	var controller = {	
			boardcategories:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/home/tc/",null,"json","application/json");	
			}
	}; 
	var view = {	
			boardcategories:function(){
				var boardcategoriesList=model.boardcategories();
				renderTemplate("#boardcategoriestmpl", boardcategoriesList, "#topcoursestable");
				$('.courseslinks').each(function(){
					$(this).find('.comma').last().hide();
				});
				$('.boardlink').click(function(){
					$('[name=boardid]').val($(this).attr('boardid'));
					$('[name=popcourseform]').attr('action','browse');
					$('[name=popcourseform]').submit();
				});
				$('.categorylink').click(function(){
					$('[name=boardid]').val($(this).attr('boardid'));
					$('[name=coursecategoryid]').val($(this).attr('coursecategoryid'));
					$('[name=popcourseform]').attr('action','browse');
					$('[name=popcourseform]').submit();
				});
			}
	}; 
	var model = {
			boardcategories:function(){
				var boardcategoriesList=controller.boardcategories();
				return formater.boardcategories(boardcategoriesList);
			}
	};
	var formater={
			boardcategories:function(boardcategoriesList){
				if(boardcategoriesList!="NO_CONTENT"){
					var boards = [];
					boardids =_.uniq(_.pluck(boardcategoriesList,'boardid'));
					_.each(boardids,function(boardid){
						var boardarray = _.where(boardcategoriesList,{boardid:boardid});
						var board = _.pick(boardarray[0],'boardid','boardname');
						var categoryids=_.uniq(_.pluck(boardarray,'coursecategoryid'));
						board["categorycount"]=categoryids.length;
						var category = [];
						_.each(categoryids,function(coursecategoryid){
							var categoryArray = _.where(boardarray,{coursecategoryid:coursecategoryid});
							var categoryOutput=_.pick(categoryArray[0],'boardid','coursecategoryid','coursecategoryname');
							categoryOutput["coursescount"]=_.uniq(_.pluck(categoryArray,'courseid')).length;
							categoryOutput["categoryimage"]=_.sample(_.uniq(_.pluck(categoryArray,"courselogo")));
							categoryOutput["price"]=_.sample(_.uniq(_.pluck(categoryArray,"price")));
							
							var courseids=_.uniq(_.pluck(categoryArray,'courseid'));
							var course = [];
							_.each(courseids,function(courseid){
								var courseArray = _.where(categoryArray,{courseid:courseid});
								var courseOutput=_.pick(courseArray[0],'courseid','coursetitle');
								course.push(courseOutput);
							});
							categoryOutput.courses=course;
							category.push(categoryOutput);
						});
						board.categories=category;
						boards.push(board);
					});
					return boards;
				}else{
					return null;
				}				
			}
	};
	return {
		init: function () {
			initialize();
		}
	}; 
	function initialize(){
		view.boardcategories();		
	};	
});