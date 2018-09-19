$(function() {
	var tour;
	var contextpath=$('#contextpath').text();
	tour = new Tour(
			{
				onStart : function() { 
					$('.popover.tour-tour  .popover-navigation .btn[data-role="end"]').text('Ok I got it')
					.attr('class','btn btn-sm btn-info'); 
				},
				onEnd : function() {
				}, 
				debug : true,
				steps : [{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=draft]",
					placement : "bottom",
					title : "Draft Courses",
					content : "Here You have seen No of Draft Courses.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=draft]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
						$('.btn-default').hide();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=draft]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=published]",
					placement : "bottom",
					title : "Published Courses",
					content : "Here You have seen No of Published Courses.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=published]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();						
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=published]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=approved]",
					placement : "bottom",
					title : "Approved Courses",
					content : "Here You have seen No of Approved Courses.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=approved]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();						
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=approved]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=trashed]",
					placement : "bottom",
					title : "Trashed Courses",
					content : "Here You have seen No of Trashed Courses.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=trashed]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=trashed]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=registered]",
					placement : "bottom",
					title : "Registered Users",
					content : "Here You have seen No of Registered Users.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=registered]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=registered]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=enrolled]",
					placement : "bottom",
					title : "Enrolled Users",
					content : "Here You have seen No of Enrolled Users.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=enrolled]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=enrolled]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=author]",
					placement : "bottom",
					title : "Authors",
					content : "Here You have seen No of Authors.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=author]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=author]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=deactivated]",
					placement : "bottom",
					title : "De-activated Users",
					content : "Here You have seen No of De-activated Users.",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=deactivated]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=deactivated]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=payment]",
					placement : "bottom",
					title : "Pending Payment Approval",
					content : "Here You have seen No of Pending Payment Approvals",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=payment]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=payment]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=certificate]",
					placement : "bottom",
					title : "Pending Certificate Approval",
					content : "Here You have seen No of Pending Certificate Approvals",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=certificate]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=certificate]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".dashbox[lt=royalty]",
					placement : "bottom",
					title : "Pending Royalty Approval",
					content : "Here You have seen No of Pending Royalty Approvals",
					backdrop : true,
					onShown : function() {	
						$('.dashlink[lt=royalty]').attr("b","tour");
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
						$('.dashlink[lt=royalty]').removeAttr("b");
					}
				},
				{
					path : contextpath+"/app/dashboard",
					element : ".amountcollectedbox",
					placement : "top",
					title : "Amount Collected",
					content : "Here You have seen amount received",
					backdrop : true,
					onShown : function() {	
						$('a').addClass('defaultcursor');
						buttonchange();
					},
					onHide:function(){
						$('a').removeClass('defaultcursor');
					}
				},
				{  
					path : contextpath+"/app/courses?origin=tour",  
					element : '.coursepartsbtn', 
					placement : "bottom",
					title : "Course Parts",
					content : "To Click and create your course Parts",
					backdrop: true,
					onShown : function() {
						$('.coursepartsbtn').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.coursepartsbtn').removeAttr("b");
						$('.coursepartsbtn').trigger('click');
					}
				},
				{  
					path : contextpath+"/app/courses?origin=tour",  
					element : '.createcoursebtn', 
					placement : "bottom",
					title : "Create Courses",
					content : "To Click and create your courses",
					backdrop: true,
					onShown : function() {
						$("#coursecreationmodal").modal("hide");
						$('.createcoursebtn').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.createcoursebtn').removeAttr("b");
						$('.createcoursebtn').trigger('click');
					}						 
				},						
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.draftcoursebtn',
					placement : "bottom",
					title : "Draft Course",
					content : "To Click and  view draft courses",
					backdrop: true,
					onShown : function() {
						$("#coursecreationmodal").modal("hide");
						$('.draftcoursebtn').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.draftcoursebtn').removeAttr("b");
						$('.draftcoursebtn').trigger('click');
					},

				},						
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.trashedcoursebtn',
					placement : "bottom",
					title : "Trashed Course",
					content : "To Click and view the trashed courses",
					backdrop: true,
					onShown : function() {
						$('.trashedcoursebtn').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.trashedcoursebtn').removeAttr("b");
						$('.trashedcoursebtn').trigger('click');
					},
				},
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.publishedcoursesbtn',
					placement : "bottom",
					title : "Published Course",
					content : "Here You can View published courses",
					backdrop: true,			
					onShown : function() {
						$('.publishedcoursesbtn').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.publishedcoursesbtn').removeAttr("b");
						$('.publishedcoursesbtn').trigger('click');
					},
				},
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.publiedcoursebtn',
					placement : "bottom",
					title : "Approved Course",
					content : "Here You can View approved courses",
					backdrop: true,			
					onShown : function() {
						$('.publiedcoursebtn').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.publiedcoursebtn').removeAttr("b");
						$('.publiedcoursebtn').trigger('click');
					},
				},
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.auc[at=Draft]',
					placement : "bottom",
					title : "Author Draft Courses",
					content : "Here You can View Authors Draft courses",
					backdrop: true,			
					onShown : function() {
						$('.auc[at=Draft]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.auc[at=Draft]').removeAttr("b");
						$('.auc[at=Draft]').trigger('click');
					},
				},
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.auc[at=Trashed]',
					placement : "bottom",
					title : "Author Trashed Courses",
					content : "Here You can View Authors Trashed courses",
					backdrop: true,			
					onShown : function() {
						$('.auc[at=Trashed]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.auc[at=Trashed]').removeAttr("b");
						$('.auc[at=Trashed]').trigger('click');
					},
				},
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.auc[at=Published]',
					placement : "top",
					title : "Author Published Courses",
					content : "Here You can View Authors Published courses",
					backdrop: true,			
					onShown : function() {
						$('.auc[at=Published]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.auc[at=Published]').removeAttr("b");
						$('.auc[at=Published]').trigger('click');
					},
				},
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.auc[at=Approved]',
					placement : "top",
					title : "Author Published Courses",
					content : "Here You can View Authors Approved courses",
					backdrop: true,			
					onShown : function() {
						$('.auc[at=Approved]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.auc[at=Approved]').removeAttr("b");
						$('.auc[at=Approved]').trigger('click');
					},
				},
				
				{
					path : contextpath+"/app/courses?origin=tour",
					element : '.myearnings',
					placement : "top",
					title : "Earnings",
					content : "Here You can View Your Earnings",
					backdrop: true,			
					onShown : function() {
						$('.myearnings').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.myearnings').removeAttr("b");
						$('.myearnings').trigger('click');
					},
				},
				{
					path : contextpath+"/app/users", 
					element : '.menulink[href="'+contextpath+'/app/courses"]', 
					placement : "bottom",
					title : "All Users",
					content : "To Click and view all users",
					backdrop: true,
					reflex : true,

					onShown : function() {	
						buttonchange();
						$('.createcoursebtn').trigger('click');
					},
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="registered"]',
					placement : "bottom",
					title : "View all registered Users",
					content : "Here You can see all Registered users",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=registered]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=registered]').removeAttr("b");
						$('.leftsidelink[al=registered]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="enrolled"]',
					placement : "bottom",
					title : "View all enrolled Users",
					content : "Here You can see all Enrolled users",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=enrolled]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=enrolled]').removeAttr("b");
						$('.leftsidelink[al=enrolled]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="paid"]',
					placement : "bottom",
					title : "View all paid Users",
					content : "Here You can see all Paid users",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=paid]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=paid]').removeAttr("b");
						$('.leftsidelink[al=paid]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="deactivated"]',
					placement : "bottom",
					title : "View all deactivated Users",
					content : "Here You can see all Deactivated users",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=deactivated]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=deactivated]').removeAttr("b");
						$('.leftsidelink[al=deactivated]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="author"]',
					placement : "bottom",
					title : "View all Authors",
					content : "Here You can see all Authors",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=author]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=author]').removeAttr("b");
						$('.leftsidelink[al=author]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="invited"]',
					placement : "bottom",
					title : "View all Invited Users",
					content : "Here You can see all Invited Users",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=invited]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=invited]').removeAttr("b");
						$('.leftsidelink[al=invited]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="onlinepayment"]',
					placement : "bottom",
					title : "View all online Payments",
					content : "Here You can see all online Payments",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=onlinepayment]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=onlinepayment]').removeAttr("b");
						$('.leftsidelink[al=onlinepayment]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="freepayment"]',
					placement : "bottom",
					title : "View all Free Payments",
					content : "Here You can see all Free Payments",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=freepayment]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=freepayment]').removeAttr("b");
						$('.leftsidelink[al=freepayment]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="pendingpayment"]',
					placement : "bottom",
					title : "View all Offline Payment Requests",
					content : "Here You can see all Offline Pending Payment Requests",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=pendingpayment]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=pendingpayment]').removeAttr("b");
						$('.leftsidelink[al=pendingpayment]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="approvedpayment"]',
					placement : "bottom",
					title : "View all approved Offline Payment Requests",
					content : "Here You can see all approved Offline Payment Requests",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=approvedpayment]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=approvedpayment]').removeAttr("b");
						$('.leftsidelink[al=approvedpayment]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="allcertificate"]',
					placement : "top",
					title : "View all Certificate Requests",
					content : "Here You can see all Certificate Requests",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=allcertificate]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=allcertificate]').removeAttr("b");
						$('.leftsidelink[al=allcertificate]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="pendingcertificate"]',
					placement : "top",
					title : "View Pending Certificate Requests",
					content : "Here You can see Pending Certificate Requests",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=pendingcertificate]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=pendingcertificate]').removeAttr("b");
						$('.leftsidelink[al=pendingcertificate]').trigger('click');
					}
				},
				{
					path : contextpath+"/app/users",
					element :  '.leftsidelink[al="issuedcertificate"]',
					placement : "top",
					title : "View issued Certificate Requests",
					content : "Here You can see Issued Certificate Requests",
					backdrop: true,
					onShown : function() {
						$('.leftsidelink[al=issuedcertificate]').attr("b","tour");
						buttonchange();								 
					},
					onHide:function(){
						$('.leftsidelink[al=issuedcertificate]').removeAttr("b");
						$('.leftsidelink[al=issuedcertificate]').trigger('click');
					},
					onNext: function (tour) {
						tour.end();
					}
				},
				{
					path : contextpath+"/app/users",
					element : '.menubar>li:last',
					placement : "bottom",
					title : "End Tour",
					content : "Here You can logout",
					backdrop: true,

					onShown : function() {	
						buttonchange();	  
						endTour();
					},	
					onNext: function (tour) {
						tour.end();
					}
					//orphan : true,

				},

				{
					path : contextpath+"/app/users",
					element : '#contentdisplay .userrow:first',
					placement : "bottom",
					title : "Send message to User",
					content : "Here You can send message by using chat window",
					onShow : function(tour) {
						$('.popwindow').show().css('z-index','1200');
					},
					backdrop: true,
					onShown : function() {	
						buttonchange();
						$('.popwindow').hide();
					},
				},
				],

			});
	function buttonchange(){
		$('.popover.tour-tour .popover-title').addClass('buttonchange');
		$('.popover.tour-tour  .popover-navigation .btn').each(function(){
			if($(this).attr('data-role') ==='end'){
				$(this).text('Ok I got it').attr('class','btn btn-sm btn-flat btn-info'); 
				$(this).on('click', function(){
					tour.end();
				});
			}
			if(! ($('.menubar>li:last') == undefined)){
				if($(this).attr('data-role') ==='prev'){
					$(this).attr('class','btn btn-sm btn-flat btn-default');								
				}
				if($(this).attr('data-role') ==='next'){
					$(this).attr('class','btn btn-sm btn-flat btn-success');								
				}
			}															
		});

	}

	function endTour(){ 
		$(this).attr('data-role').click(function(){
			$('[data-role="end"]').trigger('click');
		});
		setTimeout(function() {
			$('[data-role="end"]').trigger('click');
		}, 5000);
	}

	function clearListCookies(){
		var cookies = document.cookie.split(";");
		for (var i = 0; i < cookies.length; i++){   
			var spcook =  cookies[i].split("=");
			document.cookie = spcook[0] + "=;expires=Thu, 21 Sep 1979 00:00:01 UTC;";                                
		}
	};
	$(document).ready(function(){
		if($('#rolename').val() === 'admin'){
			tour.init().start();
		};
	});

	$(document).on("click", "#usertour", function(e) { 
		e.preventDefault(e);
		if ($(this).hasClass("disabled")) {
			clearListCookies();
			return;
		}
		$('.popover.tour-tour  .popover-navigation .btn[data-role="end"]').text('Ok I got it').attr('class','btn btn-sm btn-info');
		tour.restart();
	});

	$('.btn[data-role="end"]'). click( function(){
		tour.end();
	}); 
});