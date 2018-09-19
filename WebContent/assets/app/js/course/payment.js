$(document).ready(function(){
	var courseidobj={};
	courseidobj.courseid=$('[name=courseidhidden]').val();
	loadCourseDetails(courseidobj);
	$('.pricebtn').each(function(){
		var price=$(this).attr('price');
		var priceicon = $(this).attr('priceicon');
		if(price=="Free"){
			$(this).text("Free");
			$('.freecourseenrolldiv').show();
			$('.freecoursebtn').attr('courseid',$(this).attr('courseid'));
			$('.freecoursebtn').attr('priceid',$(this).attr('priceid'));
		}
		else if(price==""){
			$(this).html("");
		}
		else{
				$(this).html("<i class='fa "+priceicon+"'></i>"+price);	
			//$(this).text("$"+price);
			$('.freecourseenrolldiv').hide();
			$('.offlinepayment').show();
			$('.onlinepaymentbtn').attr('priceid',$(this).attr('priceid'));
			$('.onlinepaymentbtn').attr('courseid',$(this).attr('courseid'));
			$('.previewinvoicebtn').attr('priceid',$(this).attr('priceid'));
			$('.previewinvoicebtn').attr('courseid',$(this).attr('courseid'));
		}
	});
	$('.paymentab').click(function(){
		$('.paymentab').removeClass('btn-blue').addClass('btn-default');
		$(this).addClass('btn-blue').removeClass('btn-default');
	});
	$('#existingemail').click(function(){
		$('#email').val($(this).attr('email'));
	});
	$('.sendinvoicebtn').click(function(){
		$(this).addClass('sample');
		if(!$(this).hasClass('sample')){
			$(this).removeClass('btn btn-success');
			$(this).addClass('sample');
		}
		else{
			$(this).addClass('btn btn-success');
			$(this).removeClass('sample');
		}
	});
	$('.freecoursebtn').click(function(){
		var cid=$(this).attr('courseid');
		var courseenrollmentid='';
		var paymentid='';
		var courseidobj={};
		courseidobj.courseid=cid;
		var result=courseData.getCourseenrollchecked(courseidobj);
		if(result!=0){
			courseenrollmentid=result[0].courseenrollmentid;
			paymentid=result[0].paymentid;
		}
		$('#free-course-enroll-model').modal('show');		
		var invoiceobj={};
		var self = $(this);
		invoiceobj.courseid=self.attr('courseid');
		invoiceobj.priceid=self.attr('priceid');
		invoiceobj.paymenttype=self.attr('paymenttype');
		invoiceobj.paymentstatus=self.attr('paymentstatus');
		invoiceobj.courseenrollmentstatus=self.attr('courseenrollmentstatus');
		var time = new Date();
		var orgpersonid = $("[name=orgperson]").val();
		var orderno ="CE_"+invoiceobj.courseid+"_"+time.getTime()+"_"+orgpersonid;
		invoiceobj.orderno=orderno;
		if($('[name=courseenrollmentid]').val()!=""){
			invoiceobj.courseenrollmentid=$('[name=courseenrollmentid]').val();
			var emailObject = {};
			emailObject.courseid = self.attr('courseid');
			$('#free-course-enroll-model').on('shown.bs.modal', function() { 
				ajaxhelperwithdata ("freeCourseEmailSubscribtionStatus", emailObject); 
				freecourseReturn(invoiceobj,self);
			});
		}else if(courseenrollmentid!=""){
			invoiceobj.courseenrollmentid=courseenrollmentid;
			invoiceobj.paymentid=paymentid;
			freecourseReturn(invoiceobj,self);
		}		
		else{
			var emailObject = {};
			emailObject.courseid = self.attr('courseid');
			$('#free-course-enroll-model').on('shown.bs.modal', function() { 
				ajaxhelperwithdata ("freeCourseEmailSubscribtionStatus", emailObject); 
				freecourseReturn(invoiceobj,self);
			});
		}
	});
	$('.previewinvoicebtn').click(function(){ 
		var cid=$(this).attr('courseid');
		var courseenrollmentid='';
		var paymentid='';
		var courseidobj={};
		courseidobj.courseid=cid;
		var result=courseData.getCourseenrollchecked(courseidobj);
		if(result!=0){
			courseenrollmentid=result[0].courseenrollmentid;
			paymentid=result[0].paymentid;
		}		
		var invoiceobj={};
		var self = $(this);
		invoiceobj.courseid=self.attr('courseid');
		invoiceobj.priceid=self.attr('priceid');
		invoiceobj.paymenttype=self.attr('paymenttype');
		invoiceobj.paymentstatus=self.attr('paymentstatus');
		invoiceobj.paymenttype=self.attr('paymenttype');
		invoiceobj.courseenrollmentstatus=self.attr('courseenrollmentstatus'); 
		var time = new Date();
		var orgpersonid = $("[name=orgperson]").val();
		var courseid = $(this).attr('courseid');
		var OrderId ="CE_"+courseid+"_"+time.getTime()+"_"+orgpersonid;
		invoiceobj.orderno=OrderId;
		if($('[name=courseenrollmentid]').val()!=""){
			invoiceobj.courseenrollmentid=$('[name=courseenrollmentid]').val();
			var emailObject = {};
			emailObject.courseid = self.attr('courseid');
			$('#Searching_Modal').modal('show');
			$('#Searching_Modal').on('shown.bs.modal', function() { 
				ajaxhelperwithdata ("emailSubscribtionStatus", emailObject); 
				offlinecourseReturn(invoiceobj,self);
			});
		}else if(courseenrollmentid!=""){
			invoiceobj.courseenrollmentid=courseenrollmentid;
			invoiceobj.paymentid=paymentid;
			offlinecourseReturn(invoiceobj,self);
		}
		else{
			var emailObject = {};
			emailObject.courseid = self.attr('courseid');
			$('#Searching_Modal').modal('show');
			$('#Searching_Modal').on('shown.bs.modal', function() { 
				ajaxhelperwithdata ("emailSubscribtionStatus", emailObject); 
				offlinecourseReturn(invoiceobj,self);
			});
		}

	});
	$('.onlinepaymentbtn').click(function(){
		var onlinepaymentobj={};
		onlinepaymentobj.courseid=$(this).attr('courseid');
		onlinepaymentobj.priceid=$(this).attr('priceid');
		onlinepaymentobj.priceamount="1"; 
		//onlinepaymentobj.courseenrollmentstatus="P";		
		if(confirm("Are you sure want to make payment?")){ 
			paymentFunction(onlinepaymentobj);	
		}
	});
	
	$('.backbtnclass').click(function(){
  		
		$('[name=invoiceemailform]').attr('action','previewuser');
		$('[name=invoiceemailform]').submit();  
 	});
});

function loadCourseDetails(courseidobj){
	var courseParticularsList=courseData.getCourseDetailsForPayment(courseidobj);
	renderTemplate("#courseparticularstmpl", courseParticularsList, "#courseparticularstable");
}

function freecourseReturn(invoiceobj,selector){
	var invoicesuccessvalue=courseData.getCourseEnrolledValue(invoiceobj);
	if(invoicesuccessvalue!="false")	{
		selector.html("Go to My Courses");
		//selector.attr('href','mycourses');
		$('[name=invoiceemailform]').attr('action','freecourse');
		$('[name=invoiceemailform]').submit();
	}	
}

function offlinecourseReturn(invoiceobj,selector){
	var invoicesuccessvalue=courseData.getCourseEnrolledValue(invoiceobj);
	if(invoicesuccessvalue!="false")	{
		$('#Searching_Modal').modal('hide');
		selector.html("Show Invoice");
		$('[name=invoiceid]').val(invoicesuccessvalue);
		$('[name=invoiceemailform]').attr('action','showinvoice');
		$('[name=invoiceemailform]').submit();
	}	
}

function paymentFunction(onlinepaymentobj){
	//enrolledvalue=courseData.getCourseEnrolledValue(onlinepaymentobj);
	var context=$('#context').text();
	//if(enrolledvalue!='')	{
	var time = new Date();
	var orgpersonid = $("[name=orgperson]").val();
	var OrderId ="CE_"+onlinepaymentobj.courseid+"_"+time.getTime()+"_"+orgpersonid;
		var courseid=onlinepaymentobj.courseid;
		var priceid=onlinepaymentobj.priceid;
		var priceamount=onlinepaymentobj.priceamount;
		var type="1";
		window.location.href=context+"/PaymentGateway/makepayment.jsp?courseid="
		+ courseid
		+ "&fees="
		+ priceamount
		+ "&priceid="
		+ priceid
		+ "&OrderId="
		+ OrderId
		+ "&type="
		+ type;
	//}
}