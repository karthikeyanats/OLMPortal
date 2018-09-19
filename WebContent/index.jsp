<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/assets/app/images/ats-olm-logo.png">
<title><%-- <s:text name="label.productname"></s:text> --%>ATS-OLM</title>
<link
	href="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/plugins/font-awesome-4/css/font-awesome.css"
	type="text/css" rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/app/fonts/monstrerrat/font.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/assets/app/css/style.css"
	type="text/css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/assets/app/css/main_styles.css"
	type="text/css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/assets/app/css/main.css"
	type="text/css" rel="stylesheet">
	
<style type="text/css">
.loader {
	background:
		url('<%=request.getContextPath()%>/assets/app/images/45.gif') 50%
		50% no-repeat rgb(249, 249, 249);
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	z-index: 9999;
	font-size: 50px;
}

</style>
<style type="text/css">
.input-group-addon {
    padding: 6px 12px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #555;
    text-align: center;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 4px;
}
</style>
<style>
hr {
    margin-top: 8px;
    margin-bottom: 20px;
    border: 0;
    border-top: 2px solid #14bdee;
}
</style>
<style>
.course:hover {
  box-shadow: 0 0 11px rgba(33,33,33,.2); 
}
.modal-content{
  border-radius: 0px;
}
.btn-lg{
  border-radius: 0px;
}
.input-group .form-control:last-child{
   
   border-top-left-radius: 0px;
    /* border-bottom-left-radius: 60px; */
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-top-right-radius: 0px;

}
.input-group .form-control{
    width: 100%;
}
.input-group-addon:first-child{
       border-right: 0;
    border-top-left-radius: 0px;
    /* border-bottom-left-radius: 60px; */
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-top-right-radius: 0px;
}
.input-group-addon {
    padding: 10px 16px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #fff;
    text-align: center;
    background-color: #14bdee;
    border: 1px solid #14bdee;
    border-radius: 4px;
}

.input100{
    height: 50px;
    /* font-family: 'Poiret One', cursive; */
    font-size: 14px;
}

input[type=submit].btn-block{
      width: 100%;
}
.txt1{
  font-size: 14px;
    font-weight: 500;
    color: #212121;
    letter-spacing: 1px;
    text-transform: uppercase;
}
.modal-body{
     margin-left: 10px;
}
.input-group-addon:last-child{

    border-left: 0;
    border-top-left-radius: 0px;
    /* border-bottom-left-radius: 60px; */
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-top-right-radius: 0px;
}
.modal-footer {
    padding: 15px;
    text-align: right;
    border-top: 2px solid #14bdee;
}
a{

  font-size: 16px;
}
</style>


<script id="boardcategoriestmpl" type="text/x-handlebars-template">
{{#if .}}{{#each .}}
<div class="row boxshadow marbottom10 whitebg marbottom20">
	<h4 class="lineheight40 borderbottom1 col-md-12" style="margin-top:45px;">
		<font boardid="{{boardid}}"> {{boardname}} </font>- {{categorycount}} categories
		<a class="boardlink btn btn-primary pull-right" boardid="{{boardid}}" style="backgrpund-color:#14bdee;">Browse Courses</a><hr>
	</h4>
	{{#each categories}}
	<div class="col-lg-4 course_col">
					<div class="course">
						<div class="course_image"><img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{categoryimage}}&filetype=logo" alt="" style="width: 360px;height: 229px;"></div>
						<div class="course_body">
							<h3 class="course_title" style="margin-top: -20px;"><a href="course.html">{{coursecategoryname}}</a></h3>
							 <div class="row overflowtext pad10">
				                {{#each courses}} <a class="pad5 courselink">{{coursetitle}}<font
					            class="comma">,</font>
				                </a> {{/each}}
			                   </div>
                            </div>
						
                       <div class="course_footer">
							<div class="course_footer_content d-flex flex-row align-items-center justify-content-start">
								<div class="course_info">
									<i class="fa fa-graduation-cap" aria-hidden="true"></i>
									<span>{{coursescount}} Student</span>
								</div>
								<div class="course_info">
									<i class="fa fa-star" aria-hidden="true"></i>
									<span>5 Ratings</span>
								</div>
								<div class="course_price ml-auto">{{price}}</div> 
                              
							</div>
						</div>
                    </div>
				</div>
	
	<%-- <div class="col-md-3 marbottom20">		
		<div class="col-md-12 boxshadow whitebg">		
			<div class="row lightgraybg pad10">
				<h5 class="width100 noborder mar0">{{coursecategoryname}}</h5>
			</div>
			<div class="row">
				<img class="width100 height140" alt="logo" 
					src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{categoryimage}}&filetype=logo" >
			</div>
			<div class="row overflowtext pad10">
				{{#each courses}} <a class="pad5 courselink">{{coursetitle}}<font
					class="comma">,</font>
				</a> {{/each}}
			</div>
		</div>	
	</div> --%>
	{{/each}}
</div>

{{/each}}
<%-- <div class="row centertext">
	<div class="courses_button trans_200" style="vertical-align: top;margin-right: 0px;"><a href="<%=request.getContextPath()%>/browse">Browse All Courses</a></div>
	 <a href="<%=request.getContextPath()%>/browse"
		class="btn btn-yellow brbtn">
		Browse All Courses
	</a>  
</div> --%>
{{else}}
<div class="row">
	<div class="col-lg-12 text-center">
		<h3 class="section-subheading text-muted"
			style="margin-bottom: 50px;">
			No Courses
		</h3>
	</div>
</div>
{{/if}}	
</script>

<script type="text/x-handlebars-template" id="privacytmpl">
	<h5>Personal Information You Choose to
		Provide</h5>
	<p>Aimjoro Tech Solutions Pvt Ltd., recognizes the importance of maintaining your privacy.  
       Our mission is to provide you with a better learning experience and completely proved trust in us. 
       We value your privacy and appreciate your trust in us.  This policy describes how we treat user information. 
       Information given to us shall be stored and managed the greatest care. We strive to provide you with access 
       to and control over the information you give us, and we take the protection of your information very seriously. 
       We do not sell, rent or exchange your personal information to third parties. We understand how important privacy 
       is to you, and we are committed to creating a safe and secure environment for learners of all ages.This Privacy Policy 
       applies to Aimjoro Tech solutions Pvt Ltd, our online services (collectively, our “Service”), which are all owned and 
       operated by Aimjoro Tech solutions Pvt Ltd. This Privacy Policy describes how Aimjoro Tech solutions Pvt Ltd collects 
       and uses the information you provide on our Service.</p>
	<h5>Information Automatically Collected and
		Stored</h5>
	<p>Our website may deposit certain bits of information called "cookies" in a visitors computer. Generally, a cookie assigns 
       a unique number to the visitor that has no meaning outside the assigning site. Cookies can tell us how and when pages in 
       a website are visited and by how many people.</p>
	<h5>Linked Sites</h5>
	<p>Links on this Site to websites are provided as a convenience to you. Such linked sites are outside our control and responsibility 
       and are not covered by this policy. If you use any such linked sites, you should consult the privacy policies posted on those websites.</p>
</div>
</script>
<script type="text/x-handlebars-template" id="termstmpl">
	<h5>Welcome and Thank you for your interest in Aimjoro Tech Solutions.,</h5>
	<p>Online learning management product and services are provided by Aimjoro Tech Solutions.
       These Terms of Use (“Terms”) govern your use of Aimjoro Tech Solutions website, apps, and 
       other products and services(“Services”). As some of out Services may be software that is 
       downloaded to your computer, phone, tablet or other device, you agree that we may automatically 
       update this software, and that these Terms will apply to such updates. Please read these Terms carefully, 
       and contact us if you have any questions. By using our Services, you agree to be bound by these Terms, 
       including the policies referenced in these Terms.</p>
	<h5>Services</h5>
	<p>The Company may at its sole discretion modify the features of the Services from time to time without prior notice. 
       The Company will provide the Services in accordance with this Agreement.The Company may at its sole discretion modify the 
       features of the Services from time to time without prior notice. The Company will provide the Services in accordance with this Agreement.</p>
    <h5>Sensitive Data</h5>
	<p>The Company will not share, disclose, sell, lease, modify, delete or distribute any Data provided by You in any manner. The Company will also 
       not view the Data provided by You except when given permission by You in writing for the purpose of support. The entire sensitive data clause herein 
       shall survive termination of this agreement indefinitely.</p>
	<h5>No reselling or use outside of permitted
		terms</h5>
	<p>Other than using the Services as permitted under the terms and conditions of this Agreement or other written agreements between You and the Company, 
       You may not resell, distribute, make any commercial use of, or use on a time-share or service bureau basis.</p>
</script>
<script type="text/x-handlebars-template" id="refundtmpl">
	<p>If you use the free version of Reamedi Cloud, you will never be
		charged. However you can upgrade your account at any time.</p>
	<p>You can also select a 30-day trial version when you sign up for
		any of the paid accounts. If you cancel within this 30 day trial, you
		will not be charged. We do not ask for payment details to start a 30
		day trial.</p>
	<p>When your 30 day free trial period expires, you will be asked to
		create a paid subscription on the Subscription page of your TeamworkPM
		installation to continue using the paid service. You will be charged
		monthly approximately 30 days from the date you make the first
		payment.</p>
	<p>If you decide not to continue on the plan you picked for your 30
		day trial, your account will automatically be downgraded to the free
		account which you can use forever without charge.</p>
	<p>To cancel your subscription on a paid plan, you must cancel your
		Paypal Subscription from with-in Paypal. Once your paypal account has
		been cancelled your monthly payment will be cancelled. You can cancel
		your account at any time simply by logging in, going to the
		Subscription page and clicking the "Cancel my account" link.</p>
	<p>If you opt to pay once a year in advance there is no part refund
		if you decide to stop using your account during the year. Once you pay
		for a year upfront your account will be live for 12 months. After the
		12 months you can either re-new for a whole year, pay month by month
		or drop to a free account.</p>
</script>
</head>
<body id="page-top">
<div class="loader"></div>
	<%
		String error = (String) request.getParameter("error");
		String link = (String) request.getParameter("a");
		String drlink = (String) request.getParameter("l");
	%><span class="invaliderror" error="<%=error%>" a="<%=link%>" l="<%=drlink%>"></span>
	<span id="context" style="display: none"><%=request.getContextPath()%></span>
	<!-- Navigation -->
	<nav class="navbar navbar-default decnavbar">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand page-scroll padtop0" href="#page-top"> <img style="width: 218px;height: 61px;"
					src="<%=request.getContextPath()%>/assets/app/images/ats-olm-logo.png"
					class="img-responsive" alt=""></a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
<!-- 					<li class="hidden"><a href="#page-top"></a></li> -->
<!-- 					<li class=""><a class="page-scroll" href="#courses">Courses</a></li> -->
<%-- 					<li class=""><a class="page-scroll" href="<%=request.getContextPath()%>/authorsearch">Authors</a></li> --%>
<!-- 					<li><a class="page-scroll" href="#organization">Organization</a></li> -->
<!-- 					<li><a class="page-scroll" href="#services">Reamedi -->
<!-- 							Presenter</a></li> -->
					<li><a class="signupmodal" href="#newsign" style="background-color: #14bdee;color: white;">Login /
							Signup</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<!-- Header -->
	<header id="" class="container-fluid">
		<div class="intro-text">
			<form name="previewguestform" method="post">
				<input type="hidden" name="keyword" value=""> <input
					type="hidden" name="masteradminid">
<!-- 				<div class="input-group col-md-6 col-md-offset-3"> -->
<!-- 					<input type="text" class="form-control searchtxt" -->
<!-- 						placeholder="Search Course" style="height: 50px; font-size: 16px;" -->
						aria-describedby="basic-addon2"> <%-- <span
						class="input-group-addon searchbtn" id="basic-addon2"><i
						class="fa fa-search" aria-hidden="true" style="font-size: 20px;"></i></span> --%>
<!-- 				</div> -->
			</form>
		</div>
	</header> 


	<!-- Portfolio Grid Section -->
	<section id="courses" class="container-fluid lightgraybg" style="margin-top: -40px;">
		
	   
		<div class="container">
		<div class="row">
		<div class="col-md-6"><h2 class="section-heading" style="color: #384158;margin-left: -4px;margin-top: -30px;padding-bottom: 9px !important;">Courses</h2></div>
		<div class="col-md-6 centertext">
		<div class="courses_button trans_200" style="vertical-align: top;margin-left: 348px;"><a href="<%=request.getContextPath()%>/browse">Browse All Courses</a></div>
	 <%-- <a href="<%=request.getContextPath()%>/browse"
		class="btn btn-yellow brbtn">
		Browse All Courses
	</a> --%>
	</div>
</div><hr>  
		</div>
		
		
		
		
		
		<form name="popcourseform" method="POST" style="margin-top: -48px;">
			<div class="container" id="topcoursestable"></div>
			<input type="hidden" name="boardid" value=""> <input
				type="hidden" name="courseid" value=""> <input type="hidden"
				name="coursecategoryid" value="">
		</form>

	</section>
	<!-- Services Section -->
	
	<section id="newsign">
	<div class="container">
	<div class="" id="signupmodal" data-backdrop="static" style="padding-top: 95px;">
			<div class="modal-dialog" style="width: 75%;">
				<div class="modal-content">
					<div class="modal-header modal-bg modal-header-resp" style="background-color: #14bdee;">
						<!-- <button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button> -->
						<h4 class="text-center modal-title" style="color: #fff;">LOGIN / SIGNUP</h4>
					</div>
					<div class="modal-body modal-bg">
						<form action="j_spring_security_check" method="POST"
							class="signinform">
							<fieldset>
								<div class="row">
									<%-- <div class="center-block centertext">
										<img class="profile-img"
											src="<%=request.getContextPath()%>/assets/app/images/index/user.png"
											alt="">
									</div> --%>
								</div>
								<div class="row">
									<div class="col-sm-12 col-md-10  col-md-offset-1 ">
										<div class="form-group">
											<div
												class="alert alert-danger alert-dismissible invalidsuercls"
												role="alert">											
												<span class="errormsg"> <strong>Warning!</strong>
													Please check your user name and password.
												</span> <span class="socialerrormsg" style="display: none;">
													<strong>Warning!</strong> <%-- <%                      
	                    Exception lastexception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	                   if (lastexception != null)
	                   out.println(lastexception.getMessage());%> --%>Please
													signup using social networking sites
												</span>
											</div>
										</div>
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Username</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-user"></i>
												</span> <input required class="form-control input100"
													placeholder="Your Email Address" name="j_username"
													type="text" autofocus>
											</div>
										</div>
										
										
										<div class="p-t-13 p-b-9">
						                   <span class="txt1">Password</span>
                                               <a href="#" class="txt2 bo1 m-l-5 forgot">Forgot?</a>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-lock"></i>
												</span> <input required class="form-control input100"
													placeholder="Your Password" name="j_password"
													type="password" value="">
											</div>
										</div>
	                                    <div class="form-group">
											<input type="submit" class="btn btn-lg btn-primary btn-block"
												value="Login">
										</div>
									</div>
								</div>
							</fieldset>
							<input type="hidden" name="targetUrl" value="/app/mycourses">
							<input type="hidden" name="organizationid">
						</form>
						<form role="form" action="" method="POST" class="signupform"
							style="display: none;">
							<fieldset>
								<div class="row">
									<div class="center-block centertext">
										<%-- <img class="profile-img"
											src="<%=request.getContextPath()%>/assets/app/images/index/user.png"
											alt=""> --%>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12 col-md-10  col-md-offset-1 ">
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">FirstName</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-user"></i>
												</span> <input class="form-control input100" placeholder="Name"
													name="firstname" type="text" autofocus maxlength=45>
											</div>
										</div>
										<div class="alert alert-danger alertfirstname" role="alert"
											style="display: none;"></div>
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Username</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-envelope"></i></span>
												<input class="form-control input100 userch"
													placeholder="Email Address" name="username" type="email">
											</div>
										</div>
										<div class="alert alert-danger alreadyuser" role="alert"
											style="display: none;">
											<span class="glyphicon glyphicon-exclamation-sign"
												aria-hidden="true"></span> <span class="sr-only">Error:</span>
											User Name Already Used
										</div>
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Password</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-lock"></i>
												</span> <input class="form-control input100 newpwdval" placeholder="Password"
													name="password" type="password" value=""> <span
													class="input-group-addon showpassword"><i
													class="fa fa-eye showpass"></i></span>
											</div>
										</div>
										<div class="alert alert-danger alertwrongpassword" role="alert"
											style="display: none;"></div>
										<div class="alert alert-danger alertpassword" role="alert"
											style="display: none;"></div>
										<div class="control-group progcntrl"
											style="margin-bottom: 0px; display: none;">
											<div class="controls">
												<div class="span6">
													<span id="strengthmsg"></span>
													<div class="progress" style="height: 4px !important;">
														<div class="progress-bar progress-bar-success one"
															style="background-color: #5eb95e !important;"></div>
														<div class="progress-bar progress-bar-success two"
															style="background-color: #5eb95e !important;"></div>
														<div class="progress-bar progress-bar-warning three"
															style="background-color: #faa732 !important;"></div>
														<div class="progress-bar progress-bar-danger four"
															style="background-color: #dd514c !important;"></div>
														<div class="progress-bar progress-bar-danger five"
															style="background-color: #dd514c !important;"></div>
													</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<a class="btn btn-lg btn-primary btn-block sinupformsubmit">Sign
												Up</a>
										</div>
										<%-- <div class="or-box">
											<h6 class="or centertext">OR</h6>
											<h6 style="text-align: center;">Connect with</h6>
											<div class="row">
												<div class="col-md-6 row-block">
													<a href="<%=request.getContextPath()%>/rest/social/facebook"
														class="btn btn-facebook btn-block fbbg white"> <i
														class="fa fa-facebook"></i> | Facebook
													</a>
												</div>
												<div class="col-md-6 row-block">
													<a href="rest/googleplus"
														class="btn btn-google btn-block btn-danger white"> <i
														class="fa fa-google-plus"></i> | Google+
													</a>
												</div>
											</div>
										</div> --%>
									</div>
								</div>
							</fieldset>
							<input type="hidden" name="targetUrl" value="/app/mycourses">
							<input type="hidden" name="organizationid" value="">
						</form>
					</div>
					<div class="modal-footer">
						<p class="centertext">
							<span class="signupspan" style="margin-left: 700px;"><a
								class="signupa"
								style="color: #14bdee !important; font-weight: bold; font-size: 14px; cursor: pointer;">Sign
									Up Here>>></a></span> <span class="loginspan" style="display: none;color:#464646;">Already
								Have an account! <a class="signina"
								style="font-size: 14px; color: #14bdee; font-weight: bold; cursor: pointer;">Login
									Here</a>
							</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	
		<div class="modal" id="footermodal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header modal-bg modal-header-resp">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button>
						<h4 class="text-center modal-title" id="footermodaltitle">LOGIN
							/ SIGNUP</h4>
					</div>
					<div class="modal-body modal-bg" id="footermodalbody"></div>
				</div>
			</div>
		</div>
	
		<div class="modal fade" id="forgotpassword">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header modal-bg modal-header-resp">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button>
						<h4 class="text-center modal-title">Forgot Password</h4>
					</div>
					<div class="modal-body modal-bg">
						<div class="row">
							<div class="col-md-offset-3 col-md-8">
								<input type="text"
									class="form-control loginfield forgotpwd-modal" name="Username"
									value="" id="usernameId" placeholder="Username">
								<div class="alert-danger alert forgotalert" hidden="true"
									style="display: none; margin-top: 10px; padding: 10px;">
									<strong style="margin-right: 7px;">Message !</strong><span
										id="msg"></span>
								</div>
							</div>
							<div class="col-md-offset-3 col-md-6"
								style="text-align: center; margin-top: 10px;">
								<input type="button"
									class="btn btn-success newpassword sendnew-pwd"
									value="Send New Password"><i
									class="icon-spinner loadingicon icon-spin icon-2x"
									style="display: none;"></i>
							</div>
							<div></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<form name="newuserSignupform" method="post" action="newsignuplogin.jsp">
	<input type="hidden" name="newjusername" value="">
	<input type="hidden" name="newjpassword" value="">
	<input type="hidden" name="newtargeturl" value="">
	<input type="hidden" name="neworganid" value="">
	</form>
	</div>
	</section>
	
	<section id="">
	</section>
	<%-- <section id="organization">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h2 class="section-heading">Organization</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					
					<p style="text-align: justify;font-size: 18px;color:#fff;">Organisation is able to meet their needs online and on-demand. It provides seamless unique Elearning planned program. They swiftly incorporates an organisation's updated training demands, which are then promptly distributed to the Elearners on time.</p>
					<!-- <ul>
						<li><i class="fa fa-angle-double-right"></i>Planned</li>
						<li><i class="fa fa-angle-double-right"></i>Organization-wide</li>
						<li><i class="fa fa-angle-double-right"></i>Managed from the
							top</li>
						<li><i class="fa fa-angle-double-right"></i>Increase
							organization effectiveness and health through</li>
						<li><i class="fa fa-angle-double-right"></i>Planned
							interventions in the organizations</li>
					</ul> -->
					<a href="<%=request.getContextPath()%>/createorganization"
						class="btn btn-success" style="margin-top: 82px;">Sign Up Your Organization</a> <a
						href="<%=request.getContextPath()%>/suborganization"
						class="btn btn-success" style="margin-top: 82px;">Sign in</a>
				</div>
			</div>
		</div>
	</section> --%>

	<!-- Services Section -->
	<%-- <section id="services">
		<div class="container">
			<div class="row">
				<div class="col-lg-12" style="margin-top: 68px;">
					<h2 style="color: #666666;">Reamedi Presenter</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<p class="size17 marbottom10" style="margin-top: 23px;">Nailed advanced learning system, where it exactly meets your expecting reformed pathway platform that help you to give better classroom experience to the students. We help you identify your needs and mapped the training objectives to theses needs. (For more details) please see the demos of some Elearning courses framed domain-wise as specific expected business needs.</p>
					<a class="btn btn-success downloadbtn martop10"><i
						class="fa fa-download"></i> Download</a>
				</div>
			</div>
		</div>
	</section>  --%>

	<footer id="indexfooter">
	      <div class="row">
				<div class="col-md-4 martop10">
					<ul class="list-inline" style="margin-left: 130px;">
						<%-- <li><a class="footerlinks" a="p">Privacy Policy</a></li>
						<li><a class="footerlinks" a="t">Terms of Use</a></li>
						<li><a class="footerlinks" a="r">Refund Policy</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/blogspage">Blogs</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/contact">Contact</a></li> --%>
						<li><a class="white"
							href="<%=request.getContextPath()%>/createorganization">Sign Up Your Organization</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/suborganization">Sign in</a></li>		
					</ul>
					
				</div>
				<div class="col-md-4 martop10">
					<!-- <ul class="list-inline"> -->
						<span class="copyright" style="color:#fff;">Copyright &copy; Aimjoro 2018</span>
						<ul class="list-inline social-buttons" style="margin-right: 130px;text-align: right;margin-top: -30px;">
						<li><a class=" roundicon twittbg white"
							href="" target="_blank"><!-- <i
								class="fa fa-twitter "></i> --><i class="fa fa-twitter" style="font-size:25px;color:white"></i>

								</a></li>
						<li><a class="roundicon fbbg white"
							href="" target="_blank"><i
								class="fa fa-facebook" style="font-size:25px;color:white"></i></a></li>
						<li><a class="roundicon gooplusbg white"
							href="" target="_blank"><i
								class="fa fa-google-plus" style="font-size:25px;color:white"></i></a></li>
					</ul>
						<%-- <li><a class="footerlinks" a="p">Privacy Policy</a></li>
						<li><a class="footerlinks" a="t">Terms of Use</a></li>
						<li><a class="footerlinks" a="r">Refund Policy</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/blogspage">Blogs</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/contact">Contact</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/createorganization">Sign Up Your Organization</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/suborganization">Sign in</a></li> --%>		
					<!-- </ul> -->
					
				</div>
				<!-- <div class="col-md-3 centertext">
					<ul class="list-inline social-buttons">
						<li><a class=" roundicon twittbg white"
							href="https://twitter.com/iGIITE" target="_blank"><i
								class="fa fa-twitter "></i></a></li>
						<li><a class="roundicon fbbg white"
							href="https://www.facebook.com/igstmdu" target="_blank"><i
								class="fa fa-facebook"></i></a></li>
						<li><a class="roundicon gooplusbg white"
							href=" https://plus.google.com/" target="_blank"><i
								class="fa fa-google-plus"></i></a></li>
					</ul>
				</div> -->
				<div class="col-md-4 righttext martop10">
				     <ul class="list-inline" style="margin-right: 130px;">
						<li><a class="footerlinks" a="p">Privacy Policy</a></li>
						<li><a class="footerlinks" a="t">Terms of Use</a></li>
						<li><a class="footerlinks" a="r">Refund Policy</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/contact">Contact</a></li>
					
					 <%-- <span class="copyright" style="color:#fff;">Copyright &copy; Aimjoro 2018</span> --%>
					 </ul> 
				</div>
			</div>
	</footer>

	<%-- <div class="modal" id="signupmodal" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header modal-bg modal-header-resp">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="text-center modal-title">LOGIN / SIGNUP</h4>
				</div>
				<div class="modal-body modal-bg">
					<form action="j_spring_security_check" method="POST"
						class="signinform">
						<fieldset>
							<div class="row">
								<div class="center-block centertext">
									<img class="profile-img"
										src="<%=request.getContextPath()%>/assets/app/images/index/user.png"
										alt="">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 col-md-10  col-md-offset-1 ">
									<div class="form-group">
										<div
											class="alert alert-danger alert-dismissible invalidsuercls"
											role="alert">											
											<span class="errormsg"> <strong>Warning!</strong>
												Please check your user name and password.
											</span> <span class="socialerrormsg" style="display: none;">
												<strong>Warning!</strong> <%                      
                    Exception lastexception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                   if (lastexception != null)
                   out.println(lastexception.getMessage());%>Please
												signup using social networking sites
											</span>
										</div>
									</div>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-user"></i>
											</span> <input required class="form-control"
												placeholder="Your Email Address" name="j_username"
												type="text" autofocus>
										</div>
									</div>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-lock"></i>
											</span> <input required class="form-control"
												placeholder="Your Password" name="j_password"
												type="password" value="">
										</div>
									</div>

									<div class="form-group">
										<a class="forgot" style="color: #6BA1DB;"> Forgot
											password?</a>
									</div>
									<div class="form-group">
										<input type="submit" class="btn btn-lg btn-primary btn-block"
											value="Login">
									</div>
									<div class="or-box">
										<h6 class="or centertext">OR</h6>
										<h6 style="text-align: center;">Connect with</h6>
										<div class="row">
											<div class="col-md-6 row-block">
												<a href="<%=request.getContextPath()%>/auth/facebook"
													class="btn btn-facebook fbbg white btn-block"> <i
													class="fa fa-facebook"></i> | Facebook
												</a>
											</div>
											<div class="col-md-6 row-block">
												<a
													href="<%=request.getContextPath()%>/auth/googleplus?scope=openid email"
													class="btn btn-google btn-block btn-danger white"> <i
													class="fa fa-google-plus"></i> | Google+
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</fieldset>
						<input type="hidden" name="targetUrl" value="/app/mycourses">
						<input type="hidden" name="organizationid">
					</form>
					<form role="form" action="" method="POST" class="signupform"
						style="display: none;">
						<fieldset>
							<div class="row">
								<div class="center-block centertext">
									<img class="profile-img"
										src="<%=request.getContextPath()%>/assets/app/images/index/user.png"
										alt="">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 col-md-10  col-md-offset-1 ">
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-user"></i>
											</span> <input class="form-control" placeholder="Name"
												name="firstname" type="text" autofocus maxlength=45>
										</div>
									</div>
									<div class="alert alert-danger alertfirstname" role="alert"
										style="display: none;"></div>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
											</span> <input class="form-control userch"
												placeholder="Email Address" name="username" type="email">
										</div>
									</div>
									<div class="alert alert-danger alreadyuser" role="alert"
										style="display: none;">
										<span class="glyphicon glyphicon-exclamation-sign"
											aria-hidden="true"></span> <span class="sr-only">Error:</span>
										User Name Already Used
									</div>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-lock"></i>
											</span> <input class="form-control newpwdval" placeholder="Password"
												name="password" type="password" value=""> <span
												class="input-group-addon showpassword"><i
												class="fa fa-eye showpass"></i></span>
										</div>
									</div>
									<div class="alert alert-danger alertwrongpassword" role="alert"
										style="display: none;"></div>
									<div class="alert alert-danger alertpassword" role="alert"
										style="display: none;"></div>
									<div class="control-group progcntrl"
										style="margin-bottom: 0px; display: none;">
										<div class="controls">
											<div class="span6">
												<span id="strengthmsg"></span>
												<div class="progress" style="height: 4px !important;">
													<div class="progress-bar progress-bar-success one"
														style="background-color: #5eb95e !important;"></div>
													<div class="progress-bar progress-bar-success two"
														style="background-color: #5eb95e !important;"></div>
													<div class="progress-bar progress-bar-warning three"
														style="background-color: #faa732 !important;"></div>
													<div class="progress-bar progress-bar-danger four"
														style="background-color: #dd514c !important;"></div>
													<div class="progress-bar progress-bar-danger five"
														style="background-color: #dd514c !important;"></div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<a class="btn btn-lg btn-primary btn-block sinupformsubmit">Sign
											Up</a>
									</div>
									<div class="or-box">
										<h6 class="or centertext">OR</h6>
										<h6 style="text-align: center;">Connect with</h6>
										<div class="row">
											<div class="col-md-6 row-block">
												<a href="<%=request.getContextPath()%>/rest/social/facebook"
													class="btn btn-facebook btn-block fbbg white"> <i
													class="fa fa-facebook"></i> | Facebook
												</a>
											</div>
											<div class="col-md-6 row-block">
												<a href="rest/googleplus"
													class="btn btn-google btn-block btn-danger white"> <i
													class="fa fa-google-plus"></i> | Google+
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</fieldset>
						<input type="hidden" name="targetUrl" value="/app/mycourses">
						<input type="hidden" name="organizationid" value="">
					</form>
				</div>
				<div class="modal-footer">
					<p class="centertext">
						<span class="signupspan">Don't have an account! <a
							class="signupa"
							style="color: #6BA1DB !important; font-weight: bold; font-size: 16px; cursor: pointer;">Sign
								Up Here</a></span> <span class="loginspan" style="display: none;">Already
							Have an account! <a class="signina"
							style="font-size: 16px; color: #6BA1DB; font-weight: bold; cursor: pointer;">Login
								Here</a>
						</span>
					</p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="footermodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header modal-bg modal-header-resp">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="text-center modal-title" id="footermodaltitle">LOGIN
						/ SIGNUP</h4>
				</div>
				<div class="modal-body modal-bg" id="footermodalbody"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="forgotpassword">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header modal-bg modal-header-resp">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="text-center modal-title">Forgot Password</h4>
				</div>
				<div class="modal-body modal-bg">
					<div class="row">
						<div class="col-md-offset-3 col-md-8">
							<input type="text"
								class="form-control loginfield forgotpwd-modal" name="Username"
								value="" id="usernameId" placeholder="Username">
							<div class="alert-danger alert forgotalert" hidden="true"
								style="display: none; margin-top: 10px; padding: 10px;">
								<strong style="margin-right: 7px;">Message !</strong><span
									id="msg"></span>
							</div>
						</div>
						<div class="col-md-offset-3 col-md-6"
							style="text-align: center; margin-top: 10px;">
							<input type="button"
								class="btn btn-success newpassword sendnew-pwd"
								value="Send New Password"><i
								class="icon-spinner loadingicon icon-spin icon-2x"
								style="display: none;"></i>
						</div>
						<div></div>
					</div>
				</div>
			</div>
		</div>
	</div>
<form name="newuserSignupform" method="post" action="newsignuplogin.jsp">
<input type="hidden" name="newjusername" value="">
<input type="hidden" name="newjpassword" value="">
<input type="hidden" name="newtargeturl" value="">
<input type="hidden" name="neworganid" value="">
</form> --%>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>

	<script type="text/javascript">
		$(window).load(function() {
			$(".loader").fadeOut("slow");
		});
	</script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/typeahead.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.raty.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.easing.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/classie.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/cbpAnimatedHeader.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/underscore.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/handlebars.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/helper/helper-function.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/data/courseData.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/categories.js"></script>
		<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/app/js/guest/colorpattern.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/index.js"></script>
</body>
</html>