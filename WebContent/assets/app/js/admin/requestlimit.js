$(document).ready(function(){
	$(".requestlimit").click(function(){
		getRequestList.requestLimitListDetails();
	});
		
	$(".submitrequestbtn").click(function(){
		getRequestList.submitrequestprocess();			
	});
	$(".updaterequestbtn").click(function(){
		getRequestList.updaterequestprocess();
	});
	$(".requestlimitamount").focus(function(){
		$(".addrequestlimit").css('display','none');
	});
	$(".requestlimitamount").on("keyup focusout",function(){
		validateRequestLimit($(".requestlimitamount").val());
	});
	
});
function validateRequestLimit(limit){
	var regex = /^[0-9\b]+$/;
	if(!regex.test(limit)){
		$(".addrequestlimit").html("Please enter numbers");
		$(".addrequestlimit").css('display','block');
	}
	else if(limit>10000000){
			$(".addrequestlimit").html("The request limit should be less than one crore");
			$(".addrequestlimit").css('display','block');	
	}
	else{
		$(".addrequestlimit").css('display','none');
	}
}
getRequestList = {
		requestLimitListDetails:function(){
			$(".royaltysetting").removeClass("active");
			$(".requestlimit").addClass("active");
			$(".paymentrequest").removeClass("active");
			$(".cleardate").removeClass("active");
			$(".issuedhistory").removeClass("active");
			$(".royaltysettingmsg").css('display','none');
			var requestLimitList = courseData.getRequestLimit();
			var data = _.pluck(requestLimitList,"id");
			var requestlimitiddata = parseInt(data);
			var paymentrequestiddata={};
			paymentrequestiddata.paymentrequestid=requestlimitiddata;
			var paymentRequestList = courseData.AuthorPaymentRequestList(paymentrequestiddata);
			var paymentrequestdata = _.pluck(paymentRequestList,"requestlimitid");
			var requestlimitid	=	parseInt(paymentrequestdata);
			 Handlebars.registerHelper('creationdate', function(dateofcreation) 
					 { 
					 var d = new Date(dateofcreation);
				         var date =d.toDateString().split(' ');				         
					 return date[2]+"/"+date[1]+"/"+date[3]; 
					 });
			renderTemplate("#requestlimittmpl", requestLimitList,"#royaltytable");
			if(requestlimitiddata ==requestlimitid){
				$(".actiondata").hide();
				$(".actionbtn").hide();
			}
			else{
				$(".actiondata").show();
				$(".actionbtn").show();
			}
			//$("#addrequestmodal").modal("hide");
			$(".previousroyalty").css('display','none');
			$(".nextroyalty").css('display','none');
			$(".limitmodal").click(function(){
				$(".addrequestlimit").css('display','none');
				$("#addrequestmodal").modal("show");
				$(".requestlimitamount").val("");
				$('#requestModal').text('New Request Limit');
				$(".submitrequest").show();
				$(".updaterequest").hide();
			});
			
			$(".requesteditbtn").click(function(){
				$(".addrequestlimit").css('display','none');
				$('#requestModal').text('Update Request Limit');
				$("#addrequestmodal").modal("show");
				$(".requestlimitamount").val($(this).attr('minimumamount'));
				$(".idrequest").val($(this).attr('id'));
				$(".updaterequestbtn").show();
				$(".submitrequest").hide();
			});
		},
		submitrequestprocess:function(){
				self = this;
				var requestLimitList = courseData.getRequestLimit();
				var data = _.pluck(requestLimitList,"id");
				var requestlimitiddata = parseInt(data);
				var paymentrequestiddata={};
				paymentrequestiddata.paymentrequestid=requestlimitiddata;
				var paymentRequestList = courseData.AuthorPaymentRequestList(paymentrequestiddata);
				var paymentrequestdata = _.pluck(paymentRequestList,"requestlimitid");
				var requestlimitid	=	parseInt(paymentrequestdata);
				var minimumamount = $(".requestlimitamount").val();
				if(minimumamount==""){
					$(".addrequestlimit").html("Please enter numbers");
					$(".addrequestlimit").css('display','block');
				}
				else if(minimumamount<=0){
					$(".addrequestlimit").html("The value must be greater than 0");
					$(".addrequestlimit").css('display','block');
				}
				else{
					if(paymentRequestList.length>0){
						var requestLimitTrashId= {};
						requestLimitTrashId.requestlimitid = requestlimitiddata;
						var requestList = courseData.createTrashRequestLimit(requestLimitTrashId);
						if(requestList=="ACCEPTED"){
							var requestLimitDetails= {};
							requestLimitDetails.minimumamount =minimumamount;
							requestLimitDetails.requestlimitstatus="A";
							if($(".addrequestlimit").attr('style')=="display: none;"){
							var requestlimitLists = courseData.createRequestLimit(requestLimitDetails);							
							if(requestlimitLists=="CREATED"){
							self.requestLimitListDetails();
							$(".alertmessage").show();
							$(".alertmessage").text("Request Limit has been created sucessfully");
							setTimeout(function(){ 
								$(".alertmessage").hide();
								$("#addrequestmodal").modal("hide");
							}, 3000);
							}
						}
					}
					}
					else if(requestLimitList!="BAD_REQUEST"){
						var requestLimit= {};
						requestLimit.requestlimitid = requestlimitiddata;
						requestLimit.minimumamount =minimumamount;
						requestLimit.requestlimitstatus="A";
						if($(".addrequestlimit").attr('style')=="display: none;"){
						var requestList = courseData.createUpdateRequestLimit(requestLimit);
						if(requestList=="CREATED"){
						self.requestLimitListDetails();
						$(".alertmessage").show();
						$(".alertmessage").text("Request Limit has been created sucessfully");
						setTimeout(function(){ 
							$(".alertmessage").hide();
							$("#addrequestmodal").modal("hide");
						}, 3000);
						}
					}
					}
					else{
						var requestLimitDetail= {};
						requestLimitDetail.minimumamount =minimumamount;
						requestLimitDetail.requestlimitstatus="A";
						if($(".addrequestlimit").attr('style')=="display: none;"){
						var requestList = courseData.createRequestLimit(requestLimitDetail);
						if(requestList=="CREATED"){
						self.requestLimitListDetails();
						$(".alertmessage").show();
						$(".alertmessage").text("Request Limit has been created sucessfully");
						setTimeout(function(){ 
							$(".alertmessage").hide();
							$("#addrequestmodal").modal("hide");
						}, 3000);
						}
					}
					}
				}
		},
		updaterequestprocess:function(){
					self = this;
					var minimumamount = $(".requestlimitamount").val();
					var id =$(".idrequest").val();
					if(minimumamount==""){
						$(".addrequestlimit").css('display','block');
					}
					else{
					var requestLimitDetail= {};
					requestLimitDetail.minimumamount =minimumamount;
					requestLimitDetail.id =id;
					requestLimitDetail.requestlimitstatus="A";
					if($(".addrequestlimit").attr('style')=="display: none;"){
					var requestList = courseData.updateRequestLimit(id,requestLimitDetail);
					if(requestList=="ACCEPTED"){
						self.requestLimitListDetails();
						$(".alertmessage").show();
						$(".alertmessage").text("Request Limit has been updated sucessfully");
						setTimeout(function(){ 
							$(".alertmessage").hide();
							$("#addrequestmodal").modal("hide");
						}, 3000);
					}
					}
					}
			}
};