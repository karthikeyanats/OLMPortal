<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%--  <meta http-equiv="refresh" content="${ecloudbaseweb.session.maxInactiveInterval};url=#{parameterNames.timeoutUrl}"></meta> --%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reamedi Cloud || Live Session</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/flowplayer/skin/minimalist.css">
<style type="text/css">
body {
	font-family: "Montserrat" !important;
}

.contentbackgound {
	margin-top: 0px;
	padding-top: 0px;
}

.contentapprovecontainer {
	background: white;
	border-bottom: 5px solid rgb(216, 216, 216);
	border-bottom-left-radius: 7px;
	border-bottom-right-radius: 7px;
	line-height: 40px;
	margin-bottom: 10px;
}

.lectureactive {
	background: rgb(172, 172, 172);
	color: white;
}
.groupChat {
	margin-left: 2px;
/* 	float: left; */
	/* 	width:710px; */
	word-wrap: break-word !important;
}

.chatHistory {
	height: 200px;
	background-color: white !important;
	border-color: rgba(82, 168, 236, 0.8);
}

.chatBox {
	margin: -11px 0px 7px;
	height: 35px !important;
}

.groupChatBox {
	margin-left: 2px;
	float: left;
	width: 710px;
	word-wrap: break-word !important;
}

.container {
	display: none;
	width: 100%;
}

.highlightColor {
	background-color: #ff8000;
}

.gchatbox {
	margin: 0px 0px 10px;
	width: 380px !important;
	height: 35px !important;
}

.onlineGreen {
	margin-top: -12px;
	float: left;
	width: 5%;
 	background: rgb(100, 226, 100);
	border-radius: 20px;
	margin-left: 91px;
}

.onlineblue {
	background: blue;
}

.chatDiv {
	float: right;
}

.allChatDivs {
	right :0px;
	bottom: 0px;
	position: absolute;
	z-index: 9000;
	height: auto;
}

.chatDivTitle {
	height: 30px;
	padding: 5px;
	/* 	background-color:#9fb6f2; */
	background: rgb(170, 219, 228);
}

.loginUserDetails {
	float: right;
	/* 	background-color:green; */
	/* 	padding-top:10px; */
	color: black;
}

.chatBox {
	margin: -9px 0px 10px;
	height: 35px !important;
}

.onlineUsersUL {
	cursor: auto;
}
</style>
</head>
<body>

	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgpersonid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
		String username = userDetailsAdaptor.getUser().getProperty(
				"username");
		String firstname = userDetailsAdaptor.getUser().getProperty(
				"firstname");
		String courseenrollmentid = userDetailsAdaptor.getUser().getProperty(
				"courseenrollmentid");
		/* String courseenrollmentstatus = userDetailsAdaptor.getUser().getProperty(
				"courseenrollmentstatus"); */
		
		
		%>
	<span style="display: none;" class="hiddenclss"
		orgpersonid="<%=orgpersonid%>"></span>
	<span style="display: none;" class="hiddenclss"
		username="<%=username%>"></span>
	<span style="display: none;" class="hiddenclss"
		firstname="<%=firstname%>"></span>

	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a class="lecturedropdown"><i class="fa fa-align-justify"></i></a>
				<span id="desccontainer"></span>
				<div id="lectureslistholder" class="dropdownlecturestable whitebg padtop10"
				 style="display: none;"></div>
			</div>

		</div>
	</div>
	<div class="container-fluid contentapprovecontainer"
		style="border-bottom: none;">
		<div class="row">
			<div class="col-md-12 pad0" style="background: white; min-height: 300px;">
				<div class="col-md-8">
					<%-- <span class="dropdown"> <a class="lecturedropdown"><i
							class="icon-align-justify" style="font-size: 16px;"></i></a>
						<div id="lectureslistholder"
							style="background: white; border: 1px solid rgb(236, 236, 236); display: none; position: absolute; min-width: 300px; padding-left: 7px;"></div>
					</span> --%> <font class="contenttitle"
						style="border-bottom: 1px solid rgb(238, 236, 236); padding-bottom: 5px; margin-bottom: 0px;">
						Content For Lecture Lecture 1</font>
						 <div id="contentdisplayholder"></div>
				</div>
				<div class="col-md-4">
					<div class="row">
						<div class="col-md-12">
							<div class="col-md-6"
								style="height: 250px; margin-top: 20px; border: 4px solid rgb(228, 246, 228);">
							</div>
							<div class="col-md-6 boxTitle" style="margin-top: 20px;">
								<h5 style="margin-top: 0px;">Participants</h5>
								<ul id="slimstudents" class="onlineUsersUL"
									style="margin-left: 0px; border-bottom: 1px solid gray;">
								</ul>
								<span id="groupChatBox"></span>
							</div>
						</div>
					</div>
					<div class="row" style="margin-top: 20px;">
						<div class="col-md-12 pad0" style="border: 1px solid rgb(233, 233, 233);">
							<a href='#' class="loginUserDetails"></a>
							<h5
								style="background-color: rgb(170, 219, 228); padding: 10px; margin-top: 0px; border: 1px solid rgb(233, 233, 233);">Chat
								Room</h5>
							<div id="chatroomholder" class="groupChat">
								<span id="groupChatHistory" readonly="true"
									class="groupChat groupChatHistory"></span>							
							</div>
							<textarea id="groupChatBox" class="col-md-12 groupChatBox gchatbox form-control"
								placeholder="Type your message here "></textarea>
						</div>
						<div class="chatRoom" style="float: right; width: 200px;"></div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="allChatDivs"></div>
	<input type="hidden" name="firstname" value="<%=firstname%>">
		
	<form method="post" name="previewform" action="">
		<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
		<s:hidden name="courseenrollmentstatus" value="%{courseenrollmentstatus}"></s:hidden>		
	</form>

	
	<s:hidden name="livesessionid" value="%{livesessionid}"></s:hidden>
	<s:hidden name="orgpersonid" value="%{orgpersonid}"></s:hidden>
	<s:hidden name="firstname" value="%{firstname}"></s:hidden>
	<s:hidden name="username" value="%{username}"></s:hidden>
	<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
	<s:hidden name="courseid" value="%{courseid}"></s:hidden>
	<s:hidden name="courseenrollmentstatus" value="%{courseenrollmentstatus}"></s:hidden>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/admin/livesession/livesession.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/chat/clientServerOperationsHandler.js"></script>
	</body>
</html>