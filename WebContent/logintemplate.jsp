<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/app/fonts/monstrerrat/font.css"
	rel="stylesheet" type="text/css">

<link href="<%=request.getContextPath()%>/assets/app/css/style.css"
	type="text/css" rel="stylesheet">
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/app/css/colorstyle/default.css">


<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/styles/bootstrap4/bootstrap.min.css"> --%>
<%-- <link href="<%=request.getContextPath()%>/assets/app/unicat/plugins/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/plugins/OwlCarousel2-2.2.1/owl.carousel.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/plugins/OwlCarousel2-2.2.1/owl.theme.default.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/plugins/OwlCarousel2-2.2.1/animate.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/styles/main_styles.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/styles/responsive.css"> --%>

<style type="text/css">
#home {
	background-image: url(<%=request.getContextPath()%>/assets/app/images/header-bg.jpg);
	display: table;
	height: 100%;
	position: relative;
	width: 100%;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
	height: 640px;
}

.form_login {
	background: rgba(253, 254, 246, 0.58);
	border-radius: 31px;
	margin-top: 276px;
	padding-top: 22px;
	margin-bottom: 20px;
}
</style>
<script type="text/x-handlebars-template" id="logintmpl">
{{#each .}}
<legend>
  <div class="row marbottom10">
	  <div class="col-md-4">
	        <img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{logopath}}&filetype=logo" alt="{{orgname}}" class="width100 height70">
	  </div>				  
	  <div class="col-md-8 lefttext martop10">
	      <h3 id="orgname">{{orgname}}</h3>
	   </div>
 </div>
</legend>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="validationtmpl">
<div class="alert alert-danger alert-dismissible" role="alert" style="margin-top:10px;">
   <strong>Warning! </strong> {{message}}
</div>
</script>
<script type="text/x-handlebars-template" id="existingusertmpl">
<p class="centertext marbottom10">It seems you already have an account with Reamedi cloud. <br>Please use the Reamedi cloud credentials to login</p>
<div class="form-horizontal">
	<div class="form-group">
		<label class="col-sm-4 righttext">Email Address  :
		</label>
		<div class="col-sm-8">
			<b><%=request.getParameter("emailid")%></b>
			<input type="hidden" class="usercourseid" name="courseid" value="<%=request.getParameter("courseid")%>">
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-4 righttext">Password <span style="color: rgb(255, 10, 10); padding: 8px;">*</span> :
		</label>
		<div class="col-sm-6">
			<input type="password" name="password" value="" required="required" class="form-control expass">
			<div id="errormessage"></div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-4"></label>
		<div class="col-sm-6">
			<button class="btn btn-primary exuserlogin">Sign in</button>
		</div>
	</div>
</div>
</script>
<script type="text/x-handlebars-template" id="newusertmpl">
<div class="form-horizontal">
	<div class="form-group">
		<label class="col-sm-4 righttext">Full Name <span style="color: rgb(255, 10, 10); padding: 8px;">*</span> :
		</label>
		<div class="col-sm-6">
			<input type="text" name="firstname" required="required" value="" class="form-control">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 righttext">Email address <span style="color: rgb(255, 10, 10); padding: 8px;">*</span> :
		</label>
		<div class="col-sm-6">
			<input type="text" required="required" value="<%=request.getParameter("emailid")%>" class="form-control">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 righttext">Password <span style="color: rgb(255, 10, 10); padding: 8px;">*</span> :
		</label>
		<div class="col-sm-6">
			<input type="password" required="required" name="password" value="" class="form-control">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 righttext">Confirm Password <span style="color: rgb(255, 10, 10); padding: 8px;">*</span> :
		</label>
		<div class="col-sm-6">
			<input type="password" required="required" name="confirmpassword" value="" class="form-control">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10 col-md-offset-1" id="errormessage"></div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 righttext"></label>
		<div class="col-sm-6">			
			<button class="btn btn-primary userlogin">Sign up</button>
		</div>
	</div>
</div>
<input type="hidden" class="newusercourseid" name="courseid" value="<%=request.getParameter("courseid")%>">
</script>
</head>
<body>
	<div id="home" class="home">
		<div class="col-md-12" style="min-height: 500px;">

			<div class="col-md-6 col-md-offset-3 form_login"
				style="margin-top: 90px; padding: 15px; min-height: 250px;">
				<form name="userlogin" action="" method="post"
					class="form-vertical offset1" style="margin-top: 15px;">
					<fieldset>
						<div id="userlogin"></div>
						<div id="logintable"></div>
					</fieldset>
					<input type="hidden" name="username"
						value="<%=request.getParameter("emailid")%>"> <input
						type="hidden" name="invitationid"
						value="<%=request.getParameter("invitationid")%>"> <input
						type="hidden" name="organizationid"
						value="<%=request.getParameter("organizationid")%>"> <input
						type="hidden" name="targetUrl" value="/app/mycourses">
				</form>
			</div>
		</div>
	</div>
	<span id="emaidval" style="display: none;"><%=request.getParameter("emailid")%></span>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/underscore.js"></script>
	<script src="<%=request.getContextPath()%>/assets/lib/js/handlebars.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/helper/helper-function.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/data/courseData.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/login.js"></script>
</body>
</html>