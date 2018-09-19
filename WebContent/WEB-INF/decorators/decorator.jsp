<%@page language="java" import="java.util.ResourceBundle"%>
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<%
	String xReq = request.getHeader("X-Requested-With");
	if (xReq == null) {
		request.setAttribute("decorate", "true");
	}
%>
<s:if test="#attr['decorate']">
	<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords"
	content="courses, online courses,technology learning, free courses, etc">
<meta name="description"
	content="Online Education portal where one can learn and teach courses.">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/assets/app/images/ats-olm-logo.png">
<title><%-- <s:text name="label.productname"></s:text> --%>ATS-OLM</title>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/livevalidation/css/wizardlivestyle.css">
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/app/css/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/app/fonts/monstrerrat/font.css">
<link href="<%=request.getContextPath()%>/assets/app/css/main_styles.css"
	type="text/css" rel="stylesheet">	
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/plugins/font-awesome-4/css/font-awesome.css">
	<%-- <link href="<%=request.getContextPath()%>/assets/app/css/dashborad.css" type="text/css" rel="stylesheet"> --%>

<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/styles/bootstrap4/bootstrap.min.css"> --%>
<%-- <link href="<%=request.getContextPath()%>/assets/app/unicat/plugins/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/plugins/OwlCarousel2-2.2.1/owl.carousel.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/plugins/OwlCarousel2-2.2.1/owl.theme.default.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/plugins/OwlCarousel2-2.2.1/animate.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/styles/main_styles.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/app/unicat/styles/responsive.css"> --%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/livevalidation/js/livevalidation_standalone.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/livevalidation/js/livevalidation-helper.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/underscore.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/handlebars.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/app/js/helper/helper-function.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.raty.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/app/js/guest/colorpattern.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/app/js/data/courseData.js"></script>
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
<script type="text/javascript">
	$(window).load(function() {
		$(".loader").fadeOut("slow");
	});
</script>
<script type="text/x-handlebars-template" id="blog-list-recent">
{{#each .}}
	 <li class="li"><a href="<%=request.getContextPath()%>/blogspage" class="copyright-color"> {{title}}</a></li>
{{/each}}
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

<decorator:head />
</head>
<body>
	<div class="loader"></div>
	<nav class="navbar navbar-default decnavbar">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<%=request.getContextPath()%>/"><img
					src="<%=request.getContextPath()%>/assets/app/images/ats-olm-logo.png"
					alt="Reamedi Cloud Logo" style="width: 200px"></a> </a>
			</div>
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<%-- <li><a href="<%=request.getContextPath()%>/?l=c">Courses</a></li>
					<li ><a id="userauthorsearch" href="<%=request.getContextPath()%>/authorsearch">Authors</a></li>
					<li><a href="<%=request.getContextPath()%>/?l=o">Organization</a></li> --%>
					<%-- <li><a href="<%=request.getContextPath()%>/?l=p"><s:text
								name="label.presenter"></s:text></a></li> --%>
					<li><a href="<%=request.getContextPath()%>/index.jsp">Login /
							Sign-up</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<section class="container-fluid min530">
		<decorator:body />
	</section>

	<div class="modal" id="signup"
		style="display: none; border-radius: 6px;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header modal-bg">
					<button type="button" class="close" data-dismiss="modal">x</button>
					<h4 class="modal-title" style="text-align: center">Sign Up
						today and start learning</h4>
				</div>
				<div class="modal-body modal-bg">
					<div class="row-fluid">
						<div class="span5 modalresp">
							<h4 class="modal-title">Sign Up via Social Account</h4>
							<a href="auth/facebook"
								class="btn btn-facebook btn-fb margin-left-resp"> <i
								class="fa fa-facebook fb-btn-resp"></i> | Connect with Facebook
							</a>
						</div>
						<div class="span6 modalresp divider" style="padding-left: 17px;">
							<div class="row-fluid">
								<h4 class="modal-title modalpad">Sign Up With Your Email</h4>
								<form class="form-horizontal" method="post"
									name="withoutCourseSignUpForm">
									<div class="control-group ">
										<div class="controls modalpad">

											<input id="firstname" name="firstname" type="text"
												placeholder="Full name"
												class="form-control txtname modal-box">
										</div>
									</div>

									<div class="control-group modalpad">
										<div class="controls">
											<input type="text" name="username" id="usernameCheckId"
												placeholder="Email" class="form-control  modal-box">
										</div>
									</div>

									<span><h5 class="modal-text">(or)</h5></span>

									<div class="alert alert-success" hidden="true" id="alertBox">
										<strong>Message!</strong>
										<command id="checkmsg" style="margin-left: 5px"></command>
									</div>

									<input type="hidden" name="targetUrl" value="/app/mycourses">

									<div class="control-group textbox-space modalpad">
										<div class="controls">
											<input id="pass" name="password" type="password"
												placeholder="Password" class="form-control modal-box">

										</div>
									</div>

									<input type="hidden" name="organizationid" value="">
									<div class="control-group modalpad" style="text-align: center;">
										<button type="submit"
											class="btn btn-success btn-sm modal-submit"
											name="singlebutton" id="singlebutton">Sign Up</button>
										<br>
										<p class="para-size modalpad">
											By signing up, you agree to our <a data-toggle="modal"
												href="#terms">Terms of Use</a> and <a data-toggle="modal"
												href="#privacy">Privacy Policy.</a>
										</p>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer modal-footers">
					<p style="text-align: center;">
						Already Have an account? <a href="<%=request.getContextPath()%>">Login</a>
					</p>
				</div>
			</div>
		</div>
	</div>

	<!--   	............................Footer............................ -->
	<%-- <footer id="indexfooter">
		<div class="container">
			<div class="row">
				<div class="col-md-5 martop10">
					<ul class="list-inline">
						<li><a class="footerlinks" href="#privacy"
							data-toggle="modal">Privacy Policy</a></li>
						<li><a class="footerlinks" href="#terms" data-toggle="modal">Terms
								of Use</a></li>
						<li><a class="footerlinks" href="#refund" data-toggle="modal">Refund
								Policy</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/blogspage">Blogs</a></li>
						<li><a class="white"
							href="<%=request.getContextPath()%>/contact">Contact</a></li>
					</ul>
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
					<span class="copyright" style="color: white;">Copyright &copy; Aimjoro 2018</span>
				</div>
			</div>
		</div>
	</footer> --%>
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
	<!----------------------------------modal-privacy----------------------------  -->

	<div class="modal fade" id="privacy" >
		<div class="modal-dialog" role="document" style="margin-top: 200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close" style="margin-right: -321px;color: black;">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title centertext uppercase" style="margin-right: 155px;">Privacy Policy</h4>
				</div>
				<div class="modal-body">
					<div class="row pad10 padtop0">
						<h5 class="uppercase">
							<b>Personal Information You Choose to Provide</b>
						</h5>
						<p>Aimjoro Tech Solutions Pvt Ltd., recognizes the importance of maintaining your privacy.  
						   Our mission is to provide you with a better learning experience and completely proved trust in us. 
						   We value your privacy and appreciate your trust in us.  This policy describes how we treat user information. 
						   Information given to us shall be stored and managed the greatest care. We strive to provide you with access to and 
						   control over the information you give us, and we take the protection of your information very seriously. We do not sell, 
						   rent or exchange your personal information to third parties. We understand how important privacy is to you, and we are committed 
						   to creating a safe and secure environment for learners of all ages.This Privacy Policy applies to Aimjoro Tech solutions Pvt Ltd, 
						   our online services (collectively, our “Service”), which are all owned and operated by Aimjoro Tech solutions Pvt Ltd. This Privacy Policy 
						   describes how Aimjoro Tech solutions Pvt Ltd collects and uses the information you provide on our Service. </p>
						<h5 class="uppercase">
							<b>Information Automatically Collected and Stored</b>
						</h5>
						<p>Our website may deposit certain bits of information called "cookies" in a visitors computer. Generally, a cookie assigns a unique number 
						   to the visitor that has no meaning outside the assigning site. Cookies can tell us how and when pages in a website are visited and by how many people.</p>
						<h5 class="uppercase">
							<b>Linked Sites</b>
						</h5>
						<p>Links on this Site to websites are provided as a
						   Links on this Site to websites are provided as a convenience to you. Such linked sites are outside our control and 
						   responsibility and are not covered by this policy. If you use any such linked sites, you should consult the privacy policies 
						   posted on those websites.</p>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- .................................................................................................... -->

	<div class="modal fade" id="terms">
		<div class="modal-dialog" role="document" style="margin-top: 200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close" style="margin-right: -321px;">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title centertext" style="margin-right: 155px;color: black;">Terms of Service</h4>
				</div>
				<div class="modal-body">
					<div class="row pad10 padtop0">
						<h5 class="uppercase">
							<b>Welcome and Thank you for your interest in Aimjoro Tech Solutions., </b>
						</h5>
						<p>Online learning management product and services are provided by Aimjoro Tech Solutions.These Terms of Use (“Terms”) govern your use of Aimjoro Tech Solutions website, apps, and other products and services(“Services”). As some of out Services may be software that is downloaded to your computer, phone, tablet or other device, you agree that we may automatically update this software, and that these Terms will apply to such updates. Please read these Terms carefully, and contact us if you have any questions. By using our Services, you agree to be bound by these Terms, including the policies referenced in these Terms.</p>
						
						
						<h5 class="uppercase">
							<b>Services</b>
						</h5>
						<p>The Company may at its sole discretion modify the features of the Services from time to time without prior notice. The Company will provide the Services in accordance with this Agreement.The Company may at its sole discretion modify the features of the Services from time to time without prior notice. The Company will provide the Services in accordance with this Agreement..</p>
						<h5 class="uppercase">
							<b>Sensitive Data</b>
						</h5>
						<p>The Company will not share, disclose, sell, lease, modify, delete or distribute any Data provided by You in any manner. The Company will also not view the Data provided by You except when given permission by You in writing for the purpose of support. The entire sensitive data clause herein shall survive termination of this agreement indefinitely</p>
						<h5 class="uppercase">
							<b>No reselling or use outside of permitted terms</b>
						</h5>
						<p>Other than using the Services as permitted under the terms and conditions of this Agreement or other written agreements between You and the Company, You may not resell, distribute, make any commercial use of, or use on a time-share or service bureau basis.</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- .................................................................................................... -->

	<div class="modal fade" id="refund">
		<div class="modal-dialog" role="document" style="margin-top: 200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close" style="margin-right: -321px;">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title centertext" style="margin-right: 155px;color: black;">Refund Policy</h4>
				</div>
				<div class="modal-body">
					<div class="row pad10 padtop0">
						<p>If you use the free version of Teamwork Project Manager,
							you will never be charged. However you can upgrade your account
							at any time.</p>
						<p>You can also select a 30-day trial version when you sign up
							for any of the paid accounts. If you cancel within this 30 day
							trial, you will not be charged. We do not ask for payment details
							to start a 30 day trial.</p>
						<p>When your 30 day free trial period expires, you will be
							asked to create a paid subscription on the Subscription page of
							your TeamworkPM installation to continue using the paid service.
							You will be charged monthly approximately 30 days from the date
							you make the first payment.</p>
						<p>If you decide not to continue on the plan you picked for
							your 30 day trial, your account will automatically be downgraded
							to the free account which you can use forever without charge.</p>
						<p>To cancel your subscription on a paid plan, you must cancel
							your Paypal Subscription from with-in Paypal. Once your paypal
							account has been cancelled your monthly payment will be
							cancelled. You can cancel your account at any time simply by
							logging in, going to the Subscription page and clicking the
							"Cancel my account" link.</p>
						<p>If you opt to pay once a year in advance there is no part
							refund if you decide to stop using your account during the year.
							Once you pay for a year upfront your account will be live for 12
							months. After the 12 months you can either re-new for a whole
							year, pay month by month or drop to a free account.</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<span id="context" style="display: none;"><%=request.getContextPath()%></span>
</body>
	</html>
</s:if>