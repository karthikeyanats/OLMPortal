$(document).ready(function(){
	$('.selectcoursebtn').click(function(e){	
		var examstausforselectcourse=$(this).attr('examstatus');
		if(examstausforselectcourse=="F"){
			e.preventDefault();
		}
		else{
			CheckandSetStatus($(this));
			var data=setData($(this));
			if(!$(this).attr('learningstatisticsid')){
				var statisticsid=ajaxhelperwithdata("saveLearningstatistic", data);
				$(this).attr('learningstatisticsid',statisticsid);
			}
			else{	
				ajaxhelperwithdata("updateLearningstatistic", data);
			}
		}
	});
});

function setData(selector){
	var data={};	
	data.learningStatus=selector.attr('learningstatus');
	data.courselectureid=selector.attr('courselectureid');
	data.courseenrollmentid=selector.attr('courseenrollmentid');
	if(selector.attr('learningstatisticsid')){
		data.learningstatisticsid=selector.attr('learningstatisticsid');
	 }
	return data;
}

function CheckandSetStatus(selector){
	var status = selector.attr('learningstatus');
	if(status==="Not Completed"){
		selector.attr('learningstatus','Completed').text("Completed");
		SetCompleted(selector);
	}
	else if(status==="Completed"){
		selector.attr('learningstatus','Not Completed').text("Not Completed");
		SetNotCompleted(selector);
	}		
}

function getLearningstatistic(courselectureid,courseenrollementid){
	var data={};
	data.courselectureid=courselectureid;
	data.courseenrollmentid=courseenrollementid;
	var output=ajaxhelperwithdata("getLearningstatisticdetail", data);
	if(output===false){
		$(".selectcoursebtn").attr('learningstatisticsid','');
		SetNotCompleted($(".selectcoursebtn"));
	}
	else{
		var statisticsid=output[0].learningstatisticsid;
		var status=output[0].learningstatus;
		$(".selectcoursebtn").attr('learningstatisticsid',statisticsid);
		if(status==="Completed"){  
			SetCompleted($(".selectcoursebtn"));
		}
		else{  
			SetNotCompleted($(".selectcoursebtn"));
		}  
	}
}

function SetNotCompleted(selector){
	$(selector).removeClass('btn-success').addClass('btn-info').addClass('selected').attr('learningstatus','Not Completed').text("Not Completed");
}

function SetCompleted(selector){
	$(selector).removeClass('btn-info').removeClass('selected').addClass('btn-success').text("Completed").attr('learningstatus','Completed');
}

function treestructure(){$(function () {
		$('.tree li:has(ul)').addClass('parent_li').find(' > span');
		$('.tree li.parent_li > span').on('click', function (e) {
			var children = $(this).parent('li.parent_li').find(' > ul > li');
			if (children.is(":visible")) {
				children.hide('fast');
				$(this).find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
			} 
			else {
				children.show('fast');
				$(this).find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
			}
			e.stopPropagation();
		});
	});	
}