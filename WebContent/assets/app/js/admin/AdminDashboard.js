$(document).ready(function(){
	getRoyaltyAlertmsg.royaltyAlertmsg();
	adminDashboard.loadDashboardData();	
});

adminDashboard={		
		loadDashboardData:function(){
			var self=this;
			//self.getCount();
			self.coursesCountList();
			self.usersCountList();
			self.getTopUsersCount();
			self.getTopCoursesEnrolled();
			self.getCollectedAmount();
			$('.dashlink').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					var url = courseData.getContextPath()+self.redirect($(this).attr('lt'));
					$('[name=dashform]').attr('action',url);
					$('[name=dashform]').submit();	
				}				
			});
		},

		coursesCountList : function(){

			var coursesList=ajaxhelper("app/loadCoursesList");

			var obj = {
					approvedCoursesCount : _.where(coursesList,{coursestatus:'A'}).length,
					draftCoursesCount : _.where(coursesList,{coursestatus:'D'}).length,
					publishedCoursesCount : _.where(coursesList,{coursestatus:'P'}).length,
					trashedCoursesCount : _.where(coursesList,{coursestatus:'T'}).length,
			};

			renderTemplate("#newCourseCountstmpl", obj, "#newcoursescountholder");
		},
		
		usersCountList : function(){
			var thislist = Ajax.get(courseData.getContextPath()+"/rest/adminusers/count",undefined,"json","application/json");
			
			if($('.orgstatusspan').attr('st')=="M"){
				renderTemplate("#newuserCountstmpl", thislist, "#newuserscountholder");	
					
			}else{
				renderTemplate("#newuserCountsSubOrgtmpl", thislist, "#newuserscountholder");
			}
			if($('.orgstatusspan').attr('st')=="M"){
				var royaltyrequestList = ajaxhelper(courseData.getContextPath()+"/rest/paymentrequest");
				thislist[0].royaltyrequests = royaltyrequestList.length;
				renderTemplate("#newuserRequeststmpl", thislist, "#newusersrequestholder");	
			}else{
				renderTemplate("#newuserRequestsforsuborgtmpl", thislist, "#newusersrequestholder");
			}
		},

		getCount:function(){
			var self=this;
			var coursesCount=courseData.getCoursesCount();			
			var usersCount=courseData.getUsersCount();
			if(coursesCount!=undefined){
				if(coursesCount.publishedCourses!=undefined){
					$('.pubcoursescount').text(coursesCount.publishedCourses);	
				}else{
					$('.pubcoursescount').text("0");
				}
				if(coursesCount.DraftCourses!=undefined){
					$('.draftcoursescount').text(coursesCount.DraftCourses);	
				}else{
					$('.draftcoursescount').text("0");
				}
			}else{
				$('.draftcoursescount').text("0");	
				$('.pubcoursescount').text("0");
			}
			if(usersCount!=undefined){
				if(usersCount.enrolledUsers!=undefined){
					$('.alluserscount').text(usersCount.enrolledUsers);	
				}else{
					$('.alluserscount').text("0");
				}				
			}else{
				$('.alluserscount').text("0");
			}
			if(self.pendingUsersCount()!=undefined){
				$('.pendingusercount').text(self.pendingUsersCount().length);	
			}else{
				$('.pendingusercount').text("0");
			}						
		},
		pendingUsersCount:function(){
			var paymentstatusobj={};
			paymentstatusobj.paymenttype="All";
			paymentstatusobj.approvedstatus="P";
			var pendingusers=courseData.getInvoices(paymentstatusobj);
			return pendingusers;
		},
		getTopUsersCount:function(){
			var self=this;
			var categoryusersList=courseData.getTopCategoryCourses();
			self.renderTopCategoryUsers(categoryusersList);
		},
		renderTopCategoryUsers:function(categoryusersList){
			var self=this;
			if(categoryusersList!=undefined){
				self.rendercategoryusersDetailsChart(categoryusersList);
			}else{
				self.rendernodata("topcategoriestable");
			}
		},
		rendercategoryusersDetailsChart:function(categoryusersList){
			var randomColorGeneator = function () {
				return '#' + (Math.random().toString(16) + '0000000').slice(2, 8); 
			};
			var pieData=[];
			coursecategoryvalues=_.uniq(_.pluck(categoryusersList,'coursecategoryid'));
			_.each(coursecategoryvalues,function(coursecategoryid){
				var coursecategory={};
				var coursecategoryArray =_.where(categoryusersList,{coursecategoryid:coursecategoryid});
				//coursecategory=_.pick(coursecategoryArray[0],'coursecategoryname','studentcount');
				
				if(coursecategoryArray[0].studentcount!=0){
					coursecategory["label"] =  coursecategoryArray[0].coursecategoryname;
					coursecategory["nofcount"] =  coursecategoryArray[0].studentcount;
					var studentvalue =  coursecategoryArray[0].studentcount;
					var totalcal = (studentvalue/100);
					var finall = totalcal*99;
					var totalcount=finall.toFixed(0);
					coursecategory["value"] = totalcount;
					coursecategory["color"] =  randomColorGeneator();
					coursecategory["highlight"] =  randomColorGeneator();
					pieData.push(coursecategory);	
				}
				
			});
			if(pieData.length==0){
				renderTemplate("#nodatatmpl", null,"#topcategoriestable");
			}else{
				renderTemplate("#topcategoriestmpl", pieData,"#topcategoriestable");
				/*var ctx2 = document.getElementById("pieChart").getContext("2d");
				window.myPie = new Chart(ctx2).Pie(pieData,{
					  showTooltips: false
				});*/
			}			
		},
		getTopCoursesEnrolled:function(){
			var self=this;
			var courseUsersList=courseData.getTopCoursesEnrolled();
			self.renderTopCourseUsers(courseUsersList);
		},
		renderTopCourseUsers:function(courseUsersList){
			var self=this;
			if(courseUsersList!=undefined){
				
				self.rendercourseusersDetailsChart(courseUsersList);
				$('.pricebtn').each(function(){   
					var priceicon = $(this).attr('priceicon');
					$(this).html("("+"<i class='fa "+priceicon+"'></i>"+")");	
				});
			}else{
				self.rendersnodata("topcoursestable");
			}
		},
		rendercourseusersDetailsChart:function(categoryusersList){
			var randomColorGeneator = function () {
				return '#' + (Math.random().toString(16) + '0000000').slice(2, 8); 
			};
			var pieData=[];
			coursecategoryvalues=_.uniq(_.pluck(categoryusersList,'courseid'));
			_.each(coursecategoryvalues,function(courseid){
				var coursecategory={};
				var coursecategoryArray =_.where(categoryusersList,{courseid:courseid});
				
				if(coursecategoryArray[0].studentcount!=0){
					coursecategory["label"] =  coursecategoryArray[0].coursecategoryname;
					coursecategory["label"] =  coursecategoryArray[0].coursetitle;
					coursecategory["nofcount"] =  coursecategoryArray[0].studentcount;
					var studentvalue =  coursecategoryArray[0].studentcount;
					var totalcal = (studentvalue/100);
					var finall = totalcal*99;
					var totalcount=finall.toFixed(0);
					coursecategory["value"] = totalcount;
					coursecategory["color"] =  randomColorGeneator();
					pieData.push(coursecategory);	
				}
			});
			
			if(pieData.length==0){
				renderTemplate("#nodatatmpl", null,"#topcoursestable");
			}else{
				renderTemplate("#topcoursestmpl", pieData,"#topcoursestable");
				/*var ctx3 = document.getElementById("doughnutChart").getContext("2d");
				window.myPie = new Chart(ctx3).Pie(pieData,{
					  showTooltips: false
				});*/
			}
			
		},
		getCollectedAmount:function(){
			var self=this;
			var collectedamountobj={};
			collectedamountobj.weekamount=courseData.getWeeklyPayment();
			collectedamountobj.monthamount=courseData.getmonthlyPayment();
			collectedamountobj.yearamount=courseData.getYearlyPayment();
			self.rendercollectedAmountData(collectedamountobj);			
		},
		rendersnodata:function(parentdiv){	
			renderTemplate("#nodatatmpla", "","#"+parentdiv);
		},	
		rendernodata:function(parentdiv){
			renderTemplate("#nodatatmpl", "","#"+parentdiv);
		},	
		rendercollectedAmountData:function(collectedamountobj){
			renderTemplate("#newuserAmountCollectedtmpl", null, "#amountCollectedHolder");
			var weekamountdata=collectedamountobj.weekamount;
			weekamountdata=weekamountdata[0].weekamount;
			var monthamountdata=collectedamountobj.monthamount;
			monthamountdata=monthamountdata[0].monthamount;
			var yearamountdata=collectedamountobj.yearamount;
			yearamountdata=yearamountdata[0].yearamount;
			$('.iconprice').each(function(){});
			if(weekamountdata!=undefined){
				$('.weeklycollected').each(function(){
					var priceicon = $(this).attr('iconprice');
					$(this).html("<i class='fa "+priceicon+"'></i>"+weekamountdata);	
				});
			}else{
				$('.weeklycollected').each(function(){
					var priceicon = $(this).attr('iconprice');
					$(this).html("<i class='fa "+priceicon+"'></i>"+"0");	
				});
			}
			if(monthamountdata!=undefined){
				$('.monthlycollected').each(function(){
					var priceicon = $(this).attr('iconprice');
					$(this).html("<i class='fa "+priceicon+"'></i>"+monthamountdata);	
				});
			}else{
				$('.monthlycollected').each(function(){
					var priceicon = $(this).attr('iconprice');
					$(this).html("<i class='fa "+priceicon+"'></i>"+"0");	
				});
			}
			if(yearamountdata!=undefined){
				$('.yearlycollected').each(function(){
					var priceicon = $(this).attr('iconprice');
					$(this).html("<i class='fa "+priceicon+"'></i>"+yearamountdata);	
				});
			}else{
				$('.yearlycollected').each(function(){
					var priceicon = $(this).attr('iconprice');
					$(this).html("<i class='fa "+priceicon+"'></i>"+"0");	
				});
			}			
		},
		redirect:function(link){
			var redirecturl=null;
			switch (link) {
			case "draft":
				redirecturl="/app/courses?a=dc";
				break;
			case "published":
				redirecturl="/app/courses?a=pc";
				break;
			case "approved":
				redirecturl="/app/courses?a=ac";
				break;
			case "trashed":
				redirecturl="/app/courses?a=tc";
				break;
			case "enrolled":
				redirecturl="/app/users?a=eu";
				break;
			case "registered":
				redirecturl="/app/users?a=ru";
				break;
			case "author":
				redirecturl="/app/users?a=authors";
				break;
			case "deactivated":
				redirecturl="/app/users?a=du";
				break;
			case "payment":
				redirecturl="/app/users?a=cr";
				break;
			case "certificate":
				redirecturl="/app/users?a=cer";
				break;
			case "royalty":
				redirecturl="/app/royalty?a=roy";
				break;
			}
			return redirecturl;
		}
};

getRoyaltyAlertmsg={
		royaltyAlertmsg:function(){
			var royaltyList = courseData.getExpiryDate();
			if(royaltyList!="ACCEPTED"){
				if(royaltyList=="INTERNAL_SERVER_ERROR"){
					$(".addauthorroyalty").fadeIn('slow').delay(5000).fadeOut('slow');
					$('.royalemptyalert').text("No Royalty has been set, Please configure the Royalty");
				}
				else if(royaltyList.length>1){
					if(royaltyList[0].expirydate!=null&&royaltyList[1].expirydate!=null){
						$(".addauthorroyalty").fadeIn('slow').delay(5000).fadeOut('slow');						
						var date=new Date(royaltyList[0].expirydate).toString().split(' ');
						var date1=new Date(royaltyList[1].expirydate).toString().split(' ');
						$(".alertmsg").text("for course after "+date[0]+" "+date[1]+" "+date[2]+" "+date[3]+", and live session after "+date1[0]+" "+date1[1]+" "+date1[2]+" "+date1[3]);						
					}
				}
				else if(royaltyList[0].expirydate!=null){
					$(".addauthorroyalty").fadeIn('slow').delay(5000).fadeOut('slow');
					var date=new Date(royaltyList[0].expirydate).toString().split(' ');
					if(royaltyList[0].royaltytypeid==1)
						$(".alertmsg").text("for course after "+date[0]+" "+date[1]+" "+date[2]+" "+date[3]);
					else
						$(".alertmsg").text("for livesession after "+date[0]+" "+date[1]+" "+date[2]+" "+date[3]);
				}
			}else{
				$(".addauthorroyalty").hide();
			}
		},
}; 