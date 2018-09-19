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
	href="<%=request.getContextPath()%>/assets/plugins/tour/css/bootstrap-tour.css">
<link href="<%=request.getContextPath()%>/assets/app/css/style.css"
	type="text/css" rel="stylesheet">
	
<link href="<%=request.getContextPath()%>/assets/app/css/main_styles.css"
	type="text/css" rel="stylesheet">
		
	
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/app/fonts/monstrerrat/font.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/plugins/font-awesome-4/css/font-awesome.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/plugins/sweetalert/sweetalert.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/underscore.js"></script>
<script src="<%=request.getContextPath()%>/assets/lib/js/handlebars.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/app/js/helper/helper-function.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.slimscroll.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/assets/lib/js/jquery.raty.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/assets/plugins/sweetalert/sweetalert.min.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/assets/app/js/data/courseData.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/plugins/tour/js/bootstrap-tour.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/plugins/tour/js/usertour.js"></script>
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
<script type="text/javascript">
$(document).ready(function(){
	getNotifyForIndividualUser();
	$("#orgsignout").click(function(){
	ajaxhelper("<%=request.getContextPath()%>/logout");
		});
		$(".signout").click(function() {
			sessionStorage.removeItem("lastTab");
			sessionStorage.removeItem("coursecategoryid");
		});

	});
</script>
<script type="text/x-handlebars-template" id="messagetmpl">
<li class=""><h4 class="borderbottom1 pad10 lightgraybg mar0 cornflowerblue">Messages</h4></li>
<li>
	<ul class="menu pad0">
		{{#each .}}
		<li class="borderbottom1 pad5">
			<a href="<%=request.getContextPath()%>/app/manageprofile?ref=msg">
				<h6 class="messages pad5 lightbrown mar0 capitalise "><i class="fa fa-user marright7"></i>{{fromUserFirstName}}</h6>
				<h6 class ="messages pad5 mar0 breakword" id="Messageholderno" style="color:black;" >{{message}}</h6>
				<h6 class ="messages pad5 mar0 lightbrown">On {{messagedate}}</h6>
			</a>
		</li> 
		{{/each}}
	</ul>
</li>
<li class="footer"><a class="borderbottom1 pad10 graybg mar0 centertext" style="color:white !important"  
		href="<%=request.getContextPath()%>/app/manageprofile?ref=msg"">See All Messages</a></li>
</script>
<script type="text/x-handlebars-template" id="notifytmpl">
<li class=""><h4 class="borderbottom1 pad10 lightgraybg mar0 cornflowerblue">Notifcations</h4></li>
<li>
	<ul class="menu pad0 borderbottom1">
	{{#if .}}		
	{{#each .}}		
		<li viewstatus="{{reviewedstatus}}"><a href="<%=request.getContextPath()%>/app/manageprofile?ref=not">
				<p class="msgcroped borderbottom1 pad10 justifytext lowercase lightbrown" style="color:black;">{{notificationdescription}}</p>
		</a></li> 
	{{/each}}
	 {{else}}
	<h5 class="centertext martop20"><i>No New Notifications</i></h5>
{{/if}}
	</ul>
</li>
<li class="footer">
   <a class="borderbottom1 pad10 graybg mar0 centertext" style="color:white !important" 
	href="<%=request.getContextPath()%>/app/manageprofile?ref=not"">Read All Notifications</a>
</li>
</script>
<script type="text/x-handlebars-template" id="nomessagetmpl">
<li class="header"><b>Messages</b></li>
<li>
	<ul class="menu">
<li><h4 class="center" style="font-size: 14px;">No messages Available</h4></li>		
	</ul>
</li>
</script>
<script type="text/x-handlebars-template" id="nonotificationstmpl">
<li class="header"><b>Notifcations</b></li>
<li>
	<ul class="menu pad0">
		<li class="centertext"><h4 style="font-size: 14px;">No Notifications Available</h4></li>		
	</ul>
</li>
</script>
<decorator:head />

</head>
<body>
	<div class="loader"></div>

	<span id="context" style="display: none;"><%=request.getContextPath()%></span>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
			userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String roleName = userDetailsAdaptor.getUser().getProperty(
					"rolename");
			String personid = userDetailsAdaptor.getUser().getProperty(
					"orgpersonid");
			String firstname = userDetailsAdaptor.getUser().getProperty(
					"firstname");
			String organizationid = userDetailsAdaptor.getUser()
					.getProperty("organizationid");
			String orgstatus = userDetailsAdaptor.getUser().getProperty(
					"orgstatus");
			String orgname = userDetailsAdaptor.getUser().getProperty(
					"orgname");
			String orglogo = ""
					+ userDetailsAdaptor.getUser().getProperty("orglogo");
			String colorpattern = ""
					+ userDetailsAdaptor.getUser().getProperty(
							"colorpattern");
	%>
	<input type="hidden" id="orgstatus" value="<%=orgstatus%>">
	<input type="hidden" id="rolename" value="<%=roleName%>">
	<%
		try {

				switch (Integer.parseInt(colorpattern)) {
				case 1:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/default.css">
	<%
		break;
				case 2:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/1.css">
	<%
		break;
				case 3:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/2.css">
	<%
		break;
				case 4:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/3.css">
	<%
		break;
				case 5:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/4.css">
	<%
		break;
				case 6:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/5.css">
	<%
		break;
				default:
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/default.css">
	<%
		}
			} catch (Exception e) {
	%>
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/assets/app/css/colorstyle/default.css">
	<%
		}
	%>

	<%
		if (roleName.equals("user")) {
	%>

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
				<%
					if (orgstatus.equals("M")) {
				%>
				<a class="navbar-brand"
					href="<%=request.getContextPath()%>/app/mycourses"><img
					src="<%=request.getContextPath()%>/assets/app/images/ats-olm-logo.png"
					alt="" style="width: 200px"></a> </a>
				<%
					} else {
								if (orglogo.equals("null")) {
				%>
				<a class="navbar-brand padtop20 white"
					href="<%=request.getContextPath()%>/app/mycourses"><b><%=orgname%></b></a>
				<%
					} else {
				%>
				<a href="<%=request.getContextPath()%>/app/mycourses"
					class="navbar-brand martop7"> <img
					src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=<%=orglogo%>"
					alt=""  style="width: 200px;height: 40px;"><span
					class="padtop20 marleft15"><b><%=orgname%></b></span>
				</a>
				<%
					}
							}
				%>
			</div>
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a id="userbrowsecourse" href="<%=request.getContextPath()%>/app/browseu">Browse
							Courses</a></li>
					<li><a id="usermycourse" href="<%=request.getContextPath()%>/app/mycourses">My
							Courses</a></li>
					<%
					if (orgstatus.equals("M")) {
				%>
					<li><a id="userauthorsearch"  href="<%=request.getContextPath()%>/app/authorsearch">
							Authors </a></li>
					<%
					}
				%>
					<li class="dropdown messages-menu tab-list" id="org-person-id"><a
						href="#" class="message dropdown-toggle" id=<%=personid%>
						data-toggle="dropdown"> <i class="fa fa-envelope"></i>
					</a>
						<div class="dropdown-menu logindropdown pad0" id="Messageholder"></div></li>
					<li class="dropdown"><a href="#" id="Notificationid"
						class=" notify dropdown-toggle" data-toggle="dropdown"> <i
							class="fa fa-bell icon-1x"></i></a> <i class="notificationcount">0</i>
						<div class="dropdown-menu logindropdown pad0"
							id="notificationholder"></div></li>
					<li class="dropdown "><a href="#"
						class="dropdown-toggle user-name" data-toggle="dropdown"
						role="button" aria-haspopup="true" aria-expanded="false"><%=firstname%>
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a
								href="<%=request.getContextPath()%>/app/manageprofile"><i
									class="fa fa-cog userdropicons"></i>Preferences</a></li>
							<li><a
								href="<%=request.getContextPath()%>/app/purchaseHistory"><i
									class="fa fa-history"></i>Purchase History</a></li>
							<li><a href="<%=request.getContextPath()%>/app/blogposts"><i
									class="fa fa-rss"></i>Blog</a></li>
							<li><a
								href="<%=request.getContextPath()%>/app/downloadpresenter"><i
									class="fa fa-download"></i>Download</a></li>
							<li role="separator" class="divider"></li>
							<li class="signout">
								<%
									if (orgstatus.equals("M")) {
								%> <a href="<%=request.getContextPath()%>/logout"><i
									class="fa fa-power-off"></i>Sign-Out</a> <%
 	} else {
 %> <a id="orgsignout"
								href="<%=request.getContextPath()%>/suborganization"> <i
									class="fa fa-power-off"></i>Sign-Out
							</a> <%
 	}
 %>
							</li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<%
		} else {
	%>

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
				<%
					if (orgstatus.equals("M")) {
				%>
				<a class="navbar-brand"
					href="<%=request.getContextPath()%>/app/dashboard"><img
					src="<%=request.getContextPath()%>/assets/app/images/ats-olm-logo.png"
					alt="" style="width: 200px"></a> </a>
				<%
					} else {
								if (orglogo.equals("null")) {
				%>
				<a class="navbar-brand padtop20"
					href="<%=request.getContextPath()%>/app/dashboard"><b><%=orgname%></b></a>
				<%
					} else {
				%>
				<a href="<%=request.getContextPath()%>/app/dashboard"
					class="navbar-brand martop7" id="brand"> <img
					src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=<%=orglogo%>"
					alt="" width="35" style="height: 40px;"><span
					class="padtop20 marleft15"><b><%=orgname%></b></span>
				</a>
				<%
					}
							}
				%>

			</div>
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a id="adminbrowsecourse" href="<%=request.getContextPath()%>/app/browseA">Browse
							Course</a></li>
					<li><a id="admincourselist" href="<%=request.getContextPath()%>/app/courses">
							Courses </a></li>
					<li><a id="adminuserlist" href="<%=request.getContextPath()%>/app/users">
							Users </a></li>
					<%
						if (orgstatus.equals("M")) {
					%>
					<li><a id="adminorganizationlist"
						href="<%=request.getContextPath()%>/app/organizations">
							Organization </a></li>
					<%
						} else {
								}
					%>
					<li class="dropdown messages-menu tab-list" id="org-person-id"><a
						href="#" class="message dropdown-toggle" id=<%=personid%>
						data-toggle="dropdown"> <i class="fa fa-envelope"></i>
					</a>
						<div class="dropdown-menu logindropdown pad0" id="Messageholder"
							style="padding: 10px; background-color: white; margin-top: 5px !important;"></div></li>
					
					
					<li class="dropdown notifications-menu tab-list"><a href="#"
						id="Notificationid" class=" notify dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-bell icon-1x"></i></a> <i
						class="notificationcount">0</i>
						
						<div class="dropdown-menu logindropdown pad0"
							id="notificationholder"></div></li>
					
					
					
					<li class="dropdown"><a href="#"
						class="dropdown-toggle user-name" data-toggle="dropdown"
						role="button" aria-haspopup="true" aria-expanded="false"><%=firstname%>
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a
								href="<%=request.getContextPath()%>/app/manageprofile"><i
									class="fa fa-cog userdropicons"></i>Preferences</a></li> 
							<%
								if (orgstatus.equals("M")) {
							%>
							<%-- <li><a href="<%=request.getContextPath()%>/app/plan"> <i
									class="fa fa-tint"></i>Plan
							</a></li> --%>
							<%-- <li><a href="<%=request.getContextPath()%>/app/royalty">
									<i class="fa fa-stack-exchange"></i>Royalty
							</a></li> --%>
							

							<%-- <li><a
								href="<%=request.getContextPath()%>/app/downloadpresenter">
									<i class="fa fa-download"></i><s:text name="label.presenter"></s:text>
							</a></li> --%>
							<%
								}
										if (orgstatus.equals("A")) {
							%>
							<%-- <li><a
								href="<%=request.getContextPath()%>/app/downloadpresenter"><i
									class="fa fa-download"></i>Download</a></li> --%>
							<%
								}
							%>
							 <li><a href="#" id="usertour"><i
									class="fa fa-info-circle"></i>Help</a></li>
							<li><a
								href="<%=request.getContextPath()%>/app/myorganization"> <i
									class="fa fa-user"></i>Organization
							</a></li>
							<%-- <li><a href="<%=request.getContextPath()%>/app/blogposts"><i
									class="fa fa-rss"></i>Blog</a></li> --%>
							<li role="separator" class="divider"></li>
							<li class="signout">
								<%
									if (orgstatus.equals("M")) {
								%> <a href="<%=request.getContextPath()%>/logout"><i
									class="fa fa-power-off"></i>Sign-Out</a> <%
 	} else {
 %> <a id="orgsignout"
								href="<%=request.getContextPath()%>/suborganization"> <i
									class="fa fa-power-off"></i>Sign-Out
							</a> <%
 	}
 %>
							</li>

						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<%
		}	 
	%>
	<section class="container-fluid min530">
		<input type="hidden" id="organizationid" value="<%=organizationid%>">
		<decorator:body />
	</section>
	<footer>
		<div class="container-fluid">
			<!-- <p class="pull-left"></p> -->
			<p align="center" style="color: #fff;margin-left: 44%;padding: 18px;">Copyright © 2018 Aimjoro All rights reserved.</p>
		</div>
	</footer>
	<span id="contextpath" style="display: none"><%=request.getContextPath()%></span>
	<span id="metaURI" style="display: none"><%=request.getHeader("host")%></span>
</body>
	</html>
</s:if>