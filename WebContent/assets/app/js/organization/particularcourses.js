$(document).ready(function(){
	$('.organizationusers').click(function(){
		$('.organizationcourses').removeClass('active');
		$(this).addClass('active');
		organizationWiseUserList.getUserList();
	});	
	$(".organizationcourses").click(function(){
		$('.organizationusers').removeClass('active');
		$(this).addClass('active');
		particularCoursesList.getFirstLoadItems();
	});
	var organizationname=$('.orgname').val();
	$('.rightpanetitle').append(organizationname);
	particularCoursesList.getFirstLoadItems();	
});

particularCoursesList={
		getFirstLoadItems:function(){
			var self=this;
			self.loadCourses();			
		},	
		loadCourses:function(){
			var self=this;
			var organizationid=$('.organid').val();
			var orgobj={};
			orgobj.organizationid=organizationid;
			self.coursesData(orgobj);
		},
		coursesData:function(orgobj){
			var self=this;
			var courselist=courseData.getParticularCourses(orgobj);
			var userlist=courseData.getOrgParticularUsers(orgobj);
			self.renderCourses(courselist,userlist);	
		},
		renderCourses:function(courselist,userlist){
			renderTemplate("#orgcoursestmpl",courselist,"#orgwisecourseuserlist");
			if(courselist!=undefined){
				$('.freeusercount').text(courselist.length);
			}
			if(userlist!=undefined){
				$('.paidusercount').text(userlist[0].orgnames.length);		
			}	
		}		
};
organizationWiseUserList={
		getUserList:function(){
			var self=this;
			var organizationid=$('.organid').val();
			var orgobj={};
			orgobj.organizationid=organizationid;
			self.usersData(orgobj);
		},
		usersData:function(orgobj){
			var self=this;
			var userlist=courseData.getOrgParticularUsers(orgobj);
			self.renderUsers(userlist);
		},
		renderUsers:function(userlist){
			renderTemplate("#organizationwiseusertmplt",userlist,"#orgwisecourseuserlist");
			if(userlist!=undefined){
				$('.paidusercount').text(userlist[0].orgnames.length);		
			}			
		}
};



