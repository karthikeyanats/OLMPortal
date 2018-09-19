$(document).ready(function(){
	giftcourse().init();	
});

var giftcourse=(function(){
	var coursetitle=$('[name=coursetitle]').val();
	var courselogopath=$('[name=courselogo]').val();
	var contextpath=$('#giftcourseholder').attr('contextpath');
	var objects={
			giftcourse:function(){
				return obj={
						courseid:$('[name=courseid]').val(),
						recipientname:$('.coursenamedetail').text(),
						recipientmail:$('.courseemaildetail').text(),
						senddate:$('#dateofsend').val(),
						message:$('.msgval').text(),
						giftcoursestatus:"P"
				};
			},
			checkgifted:function(){
				return obj={
						courseid:$('[name=courseid]').val(),
						recipientmail:$('.courseemaildetail').text()
				};
			}
	};
	var controller={
			giftcourse:function(obj){
				return Ajax.ajaxHelper("POST", contextpath+ "/rest/gift", obj, "json", "application/json");
			},
			checkgifted:function(obj){
				return Ajax.ajaxHelper("POST", contextpath+ "/rest/gift/checkDup", obj, "json", "application/json");
			}
	};
	var model={
			giftcourse:function(){
				return controller.giftcourse(objects.giftcourse());
			},
			checkgifted:function(){
				return controller.checkgifted(objects.checkgifted());
			}
	};
	var templates={
			giftcourse:function(){
				return "<h3 class='centertext cornflowerblue'>Present Your Gift</h3>" +
				"<div class='row'>" +
					"<div class='col-md-5 well col-md-offset-1 form-horizontal marright7 whitebg min500'>" +
						"<h4 class='centertext cornflowerblue'>Prepare to send your gift</h4><hr>" +
						"<div class='form-group martop30'>" +
				    		"<label class='col-sm-4 righttext'>Recipient Name <font class='error'>*</font></label>" +
				    		"<div class='col-sm-8'> " +
				      			"<input type='text' class='form-control trimvalue'maxlength=\"50\" placeholder='Recipents Name' id='giftedname'>" +
								"<div class='alert alert-block alert-danger pad10 martop10 recnameerror'>Please " +
								"enter recipient Name</div>" +
				    		"</div>" +
				  		"</div>" +
						"<div class='form-group martop20'>" +
				    		"<label class='col-sm-4 righttext'>Recipient Email <font class='error'>*</font></label>" +
				    		"<div class='col-sm-8'> " +
				      			"<input type='email' class='form-control trimvalue' placeholder='Email address' id='giftmail'>" +
								"<div class='alert alert-block alert-danger recemailerror pad10 martop10'>Please " +
									"enter recipient Email Address</div>" +
								"<div class='alert alert-block alert-danger validemailerror pad10 martop10'>Please " +
									"enter valid Email Address</div>" +
				    		"</div>" +
				  		"</div>" +
						"<div class='form-group martop30 righttext control-label'>" +
				    		"<label class='col-sm-4 righttext'>To send On <font class='error'>*</font></label>" +
				    		"<div class='col-sm-8'> " +
				      			"<input type='text' name='bday' " +
				      					"class='form-control trimvalue' id='dateofsend'>" +				
				    		"</div>" +
				  		"</div>" +	  		
				  						  					  						  		
						"<div class='form-group martop30'>" +
				    		"<label class='col-sm-4 righttext '>Your Message<font class='error'>*</font></label>" +
				    		"<div class='col-sm-8'> " +
				      			"<textarea rows='5' cols='115' class='form-control giftmsg trimvalue'" +
									"maxlength=\"500\"></textarea>	" +
								"<div class='alert alert-block alert-danger martop10 pad10 msgerror'>Please enter " +
									"Message</div>" +			
				    		"</div>" +
				  		"</div>" +
						"<div class='form-group martop30'>" +
				    		"<label class='col-sm-4'></label>" +
				    		"<div class='col-sm-8'> " +
				      			"<a class='btn white btn-blue onlinepaymentbtn giftitbtn'>Proceed Pay</a>" +			
				    		"</div>" +
				  		"</div>" +
					"</div>" +
					"<div class='col-md-5 well whitebg min500'>" +
						"<img src='../assets/app/images/ping.jpg' class='height40'><span " +
							"class='coursetitl cornflowerblue size20 uppercase'>"+coursetitle+"</span>" +
						"<div class='col-md-12'>" +
							"<div class='col-md-8 col-md-offset-2'>" +
								"<img class='width100' src='"+contextpath+"/OpenFile?r1=serverpath&r2="+courselogopath+"&filetype=logo' alt='"+coursetitle+" logo'>" +
							"</div>" +
						"</div>" +
						"<div class='col-md-12 martop20'>" +
							"<p class='leftsideli leftsideseperateli '>" +
							"The gift is for : &nbsp;<span class='coursenamedetail'></span><span>" +
								"<</span><span class='courseemaildetail' style='color: green'></span><span>></span>" +
							"</p>" +
						"</div>" +
						"<div class='col-md-12'>Message :<font class='msgval marleftt7'></font></div>" +				
					"</div>" +
				"</div>	";
			},
			successmsg:function(){
				return "<div class='alert alert-block alert-success'><button type='button' class='close' " +
				"data-dismiss='alert'>&times;</button><h4>Success!</h4>Course has been successfully gifted.</div>";
			},
			errormsg:function(){
				return "<div class='alert alert-block alert-error alert-danger'><button type='button' class='close' " +
				"data-dismiss='alert'>&times;</button><h4>Failure!</h4> " +
				"Something went wrong. Check back Later</div>";
			},
			alreadyGifted:function(){
				return "<div class='alert alert-block alert-error alert-danger'><button type='button' class='close' " +
				"data-dismiss='alert'>&times;</button><h4>Failure!</h4> " +
				"You have already Gifted the Course to this user.</div>";
			},
			alreadyEnrolled:function(){
				return "<div class='alert alert-block alert-error alert-danger'><button type='button' class='close' " +
				"data-dismiss='alert'>&times;</button><h4>Failure!</h4> " +
				"This user has been enrolled this to course.</div>";
			}
	};
	var view={
			giftcourse:function(){
				helper.render(templates.giftcourse(),null,"#giftcourseholder");
				$('.recnameerror').hide();
				$('.recemailerror').hide();
				$('.validemailerror').hide();
				$('.msgerror').hide();
				$('#dateofsend').datepicker({
					dateFormat: 'dd/mm/yyyy', 
					startDate: 'd',
					autoclose:true
				});
				$('#dateofsend').datepicker('update',new Date());
				$('#giftedname').keyup(function(){
					$('.coursenamedetail').text($(this).val());
				});
				$('#giftmail').keyup(function(){
					$('.courseemaildetail').text($(this).val());
				});
				$('.giftmsg').keyup(function(){
					$('.msgval').text($(this).val());
				});
				$('.trimvalue').blur(function() {	
				     var value = $.trim( $(this).val() );
				     $(this).val( value );
				   });
				
				$('.giftitbtn').click(function(){
					emails=$('#giftmail').val();					
					var recname=$('#giftedname').val().trim().length;
					var recemail=$('#giftmail').val().trim().length;
					var recemsg=$('.giftmsg').val().trim().length;					
					if(recname!=0){
						$('.recnameerror').hide();						
						if(recemail!=0){
							if(helper.validateEmail(emails)){
							$('.recemailerror').hide();
							$('.validemailerror').hide();
							if(recemsg!=0){
								$('.msgerror').hide();
								
								swal({   
									title: "Are you sure to proceed of payment ?",  
									animation: "slide-from-top",
									showCancelButton: true,   
									confirmButtonColor: "#DD6B55",  
									confirmButtonText: "Yes, proceed it!",   
									closeOnConfirm: false 
								}, 
								function(){   
									helper.checkDuplicateGifing();
								});
							}else{
								$('.msgerror').show();
							}							
						}
						else{
							$('.validemailerror').show();
							$('.recemailerror').hide();
							}
					}
					else{						
					$('.recemailerror').show();
					}					
				}					
					else{
						$('.recnameerror').show();
					}
				});
			}
	};
	var helper={
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
			validateEmail:function (emails) { 
				var emaildata=$('#giftmail').val().trim();
				var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(emaildata);
			},
			checkDuplicateGifing:function(){
				var duplicateCheck=model.checkgifted();
				if(duplicateCheck=="CONFLICT"){
					this.render(templates.alreadyGifted(),null,"#alertholder");
				}else if(duplicateCheck=="OK"){
					this.render(templates.alreadyEnrolled(),null,"#alertholder");
				}
				else if(duplicateCheck=="ACCEPTED"){
					var msg=model.giftcourse();
					if(msg=="PRECONDITION_FAILED"){
						this.render(templates.errormsg(),null,"#alertholder");
					}else if(msg=="INTERNAL_SERVER_ERROR"){
						this.render(templates.errormsg(),null,"#alertholder");
					}else {
						this.render(templates.successmsg(),null,"#alertholder");
						$('[name=giftcourseid]').val(msg);
						var time = new Date();
						var orgpersonid = $("[name=orgperson]").val();
						var courseid = $("[name=courseid]").val();
						var priceid = $("[name=price]").val();
						var priceids = $("[name=priceid]").val();
						var giftcourseids =$('[name=giftcourseid]');
						var orderid ="GC_"+courseid+"_"+time.getTime()+"_"+orgpersonid; 
						window.location.href=contextpath+"/PaymentGateway/makegiftcoursepayment.jsp?giftcourseid="
						+ msg
						+ "&price="
						+ priceid
						+ "&priceid="
						+ priceids
						+"&orderid="
						+orderid;
					}
				}
			}
	};
	return {
		init: function () {
			initialize();
		}
	}; 
	function initialize(){
		view.giftcourse();		
	};	
});