$(document).ready(function ($) {
	$.validator.addMethod("regexno", function(value, element, regexpr) {          
	     return regexpr.test(value);
	 }, "Please Enter Number.");	
	$.validator.addMethod("regname", function(value, element, regexpr) {          
	     return regexpr.test(value);
	   }, "Invalid Full Name.");
	$.validator.addMethod("regeurl", function(value, element, regexpr) {          
	     return regexpr.test(value);
	   }, "Invalid url.");
	$.validator.addMethod("regdomain", function(value, element, regexpr) {          
	     return regexpr.test(value);
	   }, "Invalid Domain."); 
	$.validator.addMethod("regexsize", function(value, element, regexpr) {          
		     return regexpr.test(value);
		   }, "Invalid size");
		
	$("#organizationinfo").validate({		
		rules: {
			organizationname: {
				required: true,
				minlength:3,
				maxlength: 45,	
				
			},
			orgfirstname: {
				required: true,
				minlength:3,
				maxlength: 45,
				regname:/^[A-Za-z\s]+$/,				
			},
			orgpassword: {
				required: true,
				maxlength: 45,
				minlength:6
			
			},
			email: {
				required: true,
				email: true,
			},
			domain: {
				required: true,
//				regdomain:/(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/,
				regdomain:/^[A-Za-z\s]+$/,
			},
			contactno: {
				required: true,	
				regexno: /^[0-9]+$/,
				number: true,
				maxlength: 15,
				minlength:10,
				digits: true,				
			},
			sizeoforg:{
				required: true,	
				number: true,
				regexsize: /^[0-9]+$/,
				minlength: 1,
				
			},
			url:{
				required: true,
				regeurl: /(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/,	
//				regeurl:/^[A-Za-z\s]+$/,
			   	}
		},
		messages: {
			organizationname: {
				required: "Please enter a organizationname",				
				minlength: "organizationname must be at least 3 characters",
				maxlength: "organizationname must be no more than 50 characters"
			},
			orgfirstname: {
				required: "Please enter a fullname",
				minlength: "fullname must be at least 3 characters",
				maxlength: "fullname must be no more than 50 characters"
				
			},
			orgpassword: {
				required: "Please provide a password",				
				
			},
			domain: {
				required: "Please provide a domain",					
			},
			contactno: {
				required: "Please provide a phonenumber",
			
			},
			sizeoforg: {	
				required: "Please provide a Size",
				
			},
			url: {
				required: "Please provide URL",
				
			},
			email: "Please enter a valid email address"			
		}
	});
});
