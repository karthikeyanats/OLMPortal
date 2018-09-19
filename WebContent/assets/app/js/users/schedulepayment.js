$(document).ready(function(){

	manageschedule.loadSchedule();
	
	$('.pricebtn').each(function(){
		var price=$("[name=price]").val();
		var priceicon = $(this).attr('priceicon');
		if(price=="Free"){
			$(this).text("Free");
			$(this).html(""+price+""+"<br>"+"<a class='btn btn-flat btn-success offlinepaymentbtn martop30'>Enroll</a>");				
				}
		
		else if(price==""){
			$(this).html("");

		}
		else{
			
		$(this).html("<i class='fa "+priceicon+"'></i>"+price +"<br>"+
					"<a class='btn btn-flat btn-success onlinepaymentbtn martop30'>Payment</a>");
				
		}
	});
	$(".onlinepaymentbtn").click(function(){
		obj={};
		obj.livesessionid=$("[name=livesessionid]").val();
		var enrolldate = courseData.getlivesessionschedulelist(obj);
		if(enrolldate!=undefined && enrolldate!="false"){
		$('.schedulealertmsg').show();
		$('.schedulealertmsg').removeClass('alert-success').addClass('alert-danger');
		$('.alertmsg').text('Already enrolled livesession to this Course');
		setTimeout(function(){ 
			$('.schedulealertmsg').hide();
		}, 3000);
		}
		else{
			manageschedule.onlinePaymentFunction();	
		}
	});
	$(".offlinepaymentbtn").click(function(){
		obj={};
		obj.livesessionid=$("[name=livesessionid]").val();
		var enrolldate = courseData.getlivesessionschedulelist(obj);
		if(enrolldate!=undefined && enrolldate!="false"){
		$('.schedulealertmsg').show();
		$('.schedulealertmsg').removeClass('alert-success').addClass('alert-danger');
		$('.alertmsg').text('Already enrolled livesession to this Course');
		setTimeout(function(){ 
			$('.schedulealertmsg').hide();
		}, 3000);
		}
		else{
			manageschedule.offlinePaymentFunction();
		}
	});
});
manageschedule={
		loadSchedule:function(){
			var livevalue={};
			livevalue.price=$('[name="price"]').val();
			livevalue.coursetitle=$('[name="coursetitle"]').val();	
			livevalue.courseid=$('[name="courseid"]').val();
			renderTemplate("#schedulepaymenttemplate", livevalue, "#scheduleholder");
		},
		onlinePaymentFunction:function(){
			var context=$('#context').text();
			var time = new Date();
			var orgpersonid = $("[name=orgperson]").val();
			var courseid=$("[name=courseid]").val();
			var livesessionid = $("[name=livesessionid]").val();
			var OrderId ="LE_"+livesessionid+"_"+time.getTime()+"_"+orgpersonid;
			var priceamount=/*$('[name="price"]').val();*/"1";
			var type="2";
			
			window.location.href=context+"/PaymentGateway/makepayment.jsp?courseid="
				+ courseid
				+ "&fees="
				+ priceamount
				+ "&livesessionid="
				+ livesessionid
				+ "&OrderId="
				+ OrderId
				+ "&type="
				+ type;
		},
		offlinePaymentFunction:function(){
			var self = this;
			var time = new Date();
			var orgpersonid = $("[name=orgperson]").val();
			var livesessionid=$("[name=livesessionid]").val();
			var OrderId ="LE_"+livesessionid+"_"+time.getTime()+"_"+orgpersonid;
			
			var livesessionidobj ={}; 
			 livesessionidobj.livesessionid=$("[name=livesessionid]").val();
			 livesessionidobj.orderno=OrderId;
			 livesessionidobj.paymenttype="Offline";
			 livesessionenrollmentid = courseData.enrolllivesession(livesessionidobj);
			 if(livesessionenrollmentid>0){
				 $('[name=livesessionenrollmentid]').val(livesessionenrollmentid);
				 id={};
				 id.livesessionenrollid=$('[name=livesessionenrollmentid]').val();
				 id.email=$("[name=email]").val();
				var data=self.ajaxHelper("livesessionenrollemail",id);
			 }
			 else{
				 $('[name=livesessionenrollmentid]').val(livesessionenrollmentid);
				 $("[name=status]").val("failed");
				 $("[name=livesessionid1]").val($("[name=livesessionid]").val());
					$("#livesession").attr('action','enrollpayment');
					$("#livesession").submit();
			 }
		},
		ajaxHelper : function(url,data){
			var self=this;
			if(_.isString(url))	{
				if(_.isObject(data) && !_.isArray(data)){
					$.ajax({
						type : 'GET',
						url : url,			
						data:data,	
						beforeSend:this.startProgress(),
						success : function(data, textStatus, jqXHR){								
							if(data=="success"){
								$('.schedulealertmsg').show();
								$('.schedulealertmsg').removeClass('alert-danger').addClass('alert-success');
								$('.alertmsg').text('Livesession enrollment has been sent');
								self.endProgress();
								setTimeout(function(){ $('.schedulealertmsg').fadeOut();
								$("[name=status]").val("sucess");
								$("[name=livesessionid1]").val($("[name=livesessionid]").val());				
								$("#livesession").attr('action','enrollpayment');
								$("#livesession").submit();
								}, 3000);
							}
							else{
								$('.schedulealertmsg').show();
								$('.schedulealertmsg').removeClass('alert-success').addClass('alert-danger');
								$('.alertmsg').text('Problem in sending the mail, Please try after sometime..');
								self.endProgress();
								setTimeout(function(){ $('.schedulealertmsg').fadeOut(); }, 3000);	
							}
						},
						error : function(jqXHR, textStatus, errorThrown){
							$('.schedulealertmsg').show();
							$('.schedulealertmsg').removeClass('alert-success').addClass('alert-danger');
							$('.alertmsg').text('Problem in sending the mail, Please try after sometime..');
							self.endProgress();
							setTimeout(function(){ $('.schedulealertmsg').fadeOut(); }, 3000);
							console.log("Couldn't connect to server " + textStatus);
						}
					});	
				}
				else{
					console.log("data is not an object "+data);	
				}
			}
			else{
				console.log("url is not a String "+url);	
			}
		},
		startProgress:function(){
			$('#livesessionenrollmodal').modal('show');
		},
		endProgress:function(){
			$('#livesessionenrollmodal').modal('hide');
		}
};

