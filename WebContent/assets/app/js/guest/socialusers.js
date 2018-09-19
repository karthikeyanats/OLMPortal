$(document).ready(function() {
	if($('#provider').val()=="googleplus"){
		social.googleplussignupdetails();
	}
	else{
	social.facebooksignupdetails();
	}
	$('.signup').click(function(){	
		var masterorgidobj=courseData.getMasterAdminId();
		var masterorgid=masterorgidobj[0].organizationid;
		var userid=$('#userid').val();
	if($('#provider').val()=="facebook"){
		var signupstatus=Ajax.get("rest/facebook?organizationid="+masterorgid+"&name="+userid,undefined,"json","application/json");
		console.log(signupstatus);
		if(signupstatus=="CREATED"){
			$("[name=facebooksignup]").attr('action','auth/facebook');
			$("[name=facebooksignup]").submit();
		}else{			
			renderTemplate("#erroralerttempl", signupstatus, "#erroralert");
		}
	}
	else if($('#provider').val()=="googleplus"){
		var signupstatus=Ajax.get("rest/google?organizationid="+masterorgid+"&name="+userid,undefined,"json","application/json");
		console.log(signupstatus);
		if(signupstatus=="CREATED"){
			$("[name=facebooksignup]").attr('action','auth/googleplus?scope=openid email');
			$("[name=facebooksignup]").submit();
		}else{			
			renderTemplate("#erroralerttempl", signupstatus, "#erroralert");
		}
		
	}
	});
});
social={
		facebooksignupdetails:function(){
			var name=$('#userid').val();
			var userdetails=Ajax.get("rest/facebook/user?name="+name,undefined,"json","application/json");
			renderTemplate("#facebooktmpl", userdetails, "#socialnetworkdetails");
		},	
		googleplussignupdetails:function(){
			var name=$('#userid').val();
			var userdetails=Ajax.get("rest/google/user?name="+name,undefined,"json","application/json");
			renderTemplate("#facebooktmpl", userdetails, "#socialnetworkdetails");
		}
	};