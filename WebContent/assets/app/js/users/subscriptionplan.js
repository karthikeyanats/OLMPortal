$(document).ready(function(){
	subscriptionobj.loadsubscriptions();	
});



function subscription(selector){
	subsplan={};
	subsplan.planname=$('.plannameval').val(); 
	subsplan.durationtype=$('.planbtn.btn-blue').text().trim();
	subsplan.duration=$('.planduration').val();
	subsplan.planamount=$('.planamount').val();
	subsplan.maxcourse=$('.planmaxcourse').val();
	subsplan.maxusers=$('.planmaxuser').val();
	courseData.newSubscriptionData(subsplan);
}
function editsubscription(selector){
	editplan={};
	editplan.id=$('#hiddenplanid').val();
	editplan.planname=$('.plannameval').val(); 
	editplan.durationtype=$('.planbtn.btn-blue').text().trim();
	editplan.duration=$('.planduration').val();
	editplan.planamount=$('.planamount').val();
	editplan.maxcourse=$('.planmaxcourse').val();
	editplan.maxusers=$('.planmaxuser').val();
	
	result=courseData.editsubscription(editplan);
	if(result="result"){
			$('.leftsideli').removeClass('active');
			$('.subscriptedplans').addClass('active');		
			$("#successalertmessage").modal('show');
			$(".successalertmsg").text("Plan has been successfully updated");
		subscriptionobj.loadsubscriptions();
	}
}

subscriptionobj={
		renderNewSubscription:function(){
			/*if(($('#orgstatus').val()=='M')&&($('#hidrole').val()=="admin")){*/
			renderTemplate("#newsubscriptiontmpl", undefined, "#personalinfotable1");
			$('.subscriptionlist').click(function(){
				subscriptionobj.loadsubscriptions();
			});
			$('.alertplan').each(function(){
				$(this).on("keypress",function(){
					var errormsg ='#'+$(this).attr('errorid');
					$(errormsg).hide();
					$(this).focus();
				});
				});
			/*}*/
			/*else{
				loadpersonalinfo();
			}*/
			$(function(){
			    $('.planduration,.planamount,.planmaxcourse,.planmaxuser').on('keyup', function(e){
			        if (/[^0-9. ]/.test(this.value)) {
			            this.value = this.value.replace(/[^0-9 ]/g, '');
			        }
			        else if(this.value>0){
			        	var errormsg ='#'+$(this).attr('errorid');
						$(errormsg).hide();
			        }
			        else{
			        	var errormsg ='#'+$(this).attr('errorid');
			        	$(errormsg).text('Please enter number greater than 0');
						$(errormsg).show();
						$(this).focus();
						this.value = '';
			        }
			    });
			});
			
			$('.leftsideli').removeClass('active');
			$('.newsubscription').addClass('active');
			$('.planbtn').removeClass('btn-blue');
			$('.monthlyplan').click(function(){
				$('.planbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('#planduration').attr('type','text');
				$('#planduration').attr('placeholder','Months');
			});
			$('.yearlyplan').click(function(){
				$('.planbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('#planduration').attr('type','text');
				$('#planduration').attr('placeholder','Years');
			});
			$('.savesubscribtn').click(function(e){
				
				/* Update Plan and Insert Plan In same Button */
				$('.alertplan').each(function(){
					if($(this).val().trim()==""){
					var errormsg ='#'+$(this).attr('errorid');
					$(errormsg).show();
					$(this).focus();
					}
					else{
						var errormsg ='#'+$(this).attr('errorid');
						$(errormsg).hide();
					}
					});
				$(".plantypebtn").each(function(){
					if($(this).is('.btn-blue')){
						$(".plantypevalmsg").text("Please Enter Plan Type");
						return false;
					}
					else{
						$(".plantypevalmsg").text("Please Select Plan Type");
					}
					});
				
				if($('.savesubscribtn').text()=="Update"){
					 if($('.plannameval').val()!='' && $('.planbtn.btn-blue').text().trim()!='' && $('.planamount').val()!='' && $('.planmaxcourse').val()!='' && $('.planmaxuser').val()!='' && $('.planduration').val()!='')
					{
						var dupplan=$('.plannameval').val().trim();
						var dupid=$('#hiddenplanid').val();
						if(dupplan!=''){
							checkupdateduplicate={};
							checkupdateduplicate.id=dupid;
							checkupdateduplicate.planname=dupplan;
							var dupcheck=courseData.checkupdateplanname(checkupdateduplicate);
							if(dupcheck=="CREATED"){
								renderTemplate("#planvalidationtmpl", undefined, "#planvalidation");							
						}else{
							editsubscription(this);
						}
						}				
					}
					else{
						$('#messageinfomodal').modal('show');
						$('.msginfo').text("Please Fill mandatory Fields");
						e.preventDefault();
					}
				}
				else
				{			
					if($('.plannameval').val()!='' && $('.planbtn.btn-blue').text().trim()!='' && $('.planamount').val()!='' && $('.planmaxcourse').val()!='' && $('.planmaxuser').val()!='' && $('.planduration').val()!='')
					{
						var dupyrmth=$('.planbtn.btn-blue').text().trim();
						var dupplan=$('.plannameval').val().trim();						
						if(dupplan!=''){
							checkduplicate={};
							checkduplicate.planname=dupplan;
							checkduplicate.durationtype=dupyrmth;
							var dupcheck=courseData.checkplanname(checkduplicate);
							if(dupcheck=="CREATED"){
								renderTemplate("#planvalidationtmpl", undefined, "#planvalidation");							
						}else{
							subscription(this);	
						}				
						}
				}
					else
					{
						$('#messageinfomodal').modal('show');
						$('.msginfo').text("Please Fill mandatory Fields");
						e.preventDefault();
					}
				}
				
				
				
				
			});
		},		
		loadsubscriptions:function(){
			var subscriptiondetails=courseData.loadSubscriptionData();
			
			/*if(subscriptiondetails!=0){*/
				var fullsub={};
				fullsub.subdetails = _.where(subscriptiondetails,{durationtype:"Monthly"});
				fullsub.subdetailsyear = _.where(subscriptiondetails,{durationtype:"Annual"});
				
				/*var planid = _.pluck(orgplansubscribe,'planid');
				var id = _.findWhere(subscriptiondetails,{id:parseInt(planid)});
				console.log("id ===>>"+id);*/
				
				renderTemplate("#allsubscriptiondetailtmpl", fullsub, "#personalinfotable1");
					
			//}
			/*else{
				renderTemplate("#nodatatmpl", undefined, "#personalinfotable1");				
			}*/
			//renderTemplate("#allsubscriptiondetailtmpl1", subdetailsyear, "#personalinfotableyear");
			
			$('.newsubscription').click(function(){
				subscriptionobj.renderNewSubscription();
			});
			$('.plantrashbtn').click(function(){
				orgplanobj={};
				var planiddata = $(this).attr("planid");
				orgplanobj.planid=planiddata;
				var orgsubscripplan = courseData.orgsubscripplanlist(orgplanobj);
				if(orgsubscripplan=="INTERNAL_SERVER_ERROR"){
					if(confirm("Do you want to Delete the Plan?"))  
				    {
					var result="";
					deleteplan={};
					deleteplan.id=$(this).attr("planid");
					deleteplan.planname=$(this).attr("name");
					deleteplan.duration=$(this).attr("duration");
					deleteplan.maxcourse=$(this).attr("maxcourse");
					deleteplan.durationtype=$(this).attr("durationtype");
					deleteplan.planamount=parseInt($(this).attr("planamount"));
					deleteplan.maxusers=$(this).attr("maxusers");
					result=courseData.deletesubscription(deleteplan);		
					if(result="result"){					
						$("#successalertmessage").modal('show');
						$(".successalertmsg").text("Plan has been successfully trashed");
						subscriptionobj.loadsubscriptions();
					}
				    }
				}
				else{
					$("#alertmessage").modal('show');
				}
			});
			$('.planeditbtn').click(function(){
				orgplanobj={};
				var planiddata = $(this).attr("planid");
				orgplanobj.planid=planiddata;
				var orgsubscripplan = courseData.orgsubscripplanlist(orgplanobj);
				if(orgsubscripplan=="INTERNAL_SERVER_ERROR"){
					$('.leftsideli').removeClass('active');
					$('.newsubscription').addClass('active');
					subscriptionobj.renderNewSubscription();
					$('#hiddenplanid').val($(this).attr("planid"));
					$('.plannameval').val($(this).attr("name")); 
					var type=$(this).attr("durationtype");
					if(type=="Annual"){
						$('.yearlyplan').addClass('btn-blue');	
						$('.planduration').attr('type','text');
					}else{
						$('.monthlyplan').addClass('btn-blue');
						$('.planduration').attr('type','text');
					}				
					$('.planduration').val($(this).attr("duration"));
					$('.planamount').val(parseInt($(this).attr("planamount")));
					$('.planmaxcourse').val($(this).attr("maxcourse"));
					$('.planmaxuser').val($(this).attr("maxusers"));
					$('.savesubscribtn').text('Update');
				}
				else{
					$("#alertmessage").modal('show');
				}
			});
			
		},
		
		
		
};