<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@page language="java" import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script id="schedulepaymenttemplate" type="text/x-handlebars-template">
<form id="livesession" method="post" action="">
<h2 class="subscribetitles centertext">Live Session Payment</h2>
<h3 class="centertext">"{{coursetitle}}"</h3>
<h3 class="pricebtn centertext" courseid="{{courseid}}" price="{{price}}" priceicon ="<s:text name="label.price"></s:text>"></h3>
<input type="hidden" name="status">
<input type="hidden" name="livesessionid1" value="">
<input type="hidden" name="livesessionenrollmentid" value="">
</form>
</script>
</head>
<body> 
 <%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgperson=userDetailsAdaptor.getUser().getProperty("orgpersonid");
		String email=userDetailsAdaptor.getUser().getProperty("email");
	%>

<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/mycourses">Home </a> / Live session payment
			</div>
		</div>
	</div>
	<div
		class="alert alert-danger alert-dismissible col-md-12 martopbottom10 pad10 schedulealertmsg"
		style="display: none;">
		<b><span class="alertmsg">Already enrolled livesession to this Course </span></b>
	</div>
	<div class="container-fluid">
		<div class="row boxshadow whitebg marleft0 marbottom10">
			<div class="col-md-12">
				<div id="scheduleholder" class="livesessionpayment"></div>
			</div>
		</div>
	</div>
	<div id="livesessionenrollmodal" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="text-align: center">
					<h3>Enrolling Livesession</h3>
				</div>
				<div class="modal-body">
					<div style="height: 200px">
						<i class="fa fa-clock-o"
							style="font-size: 100px !important; padding-left: 38% !important;"></i>
						<h1 style="text-align: center !important;">Please wait...</h1>
					</div>
				</div>
			</div>
		</div>
	</div>
	<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
<s:hidden name="livesessionid" value="%{livesessionid}"></s:hidden>
<s:hidden name="price" value="%{price}"></s:hidden>
<s:hidden name="courseid" value="%{courseid}"></s:hidden>
 <input type="hidden" name="orgperson" value="<%=orgperson%>" />
 <input type="hidden" name="email" value="<%=email%>" />
 <input type="hidden" name="status" value="success" />
 <span id="context" style="display: none;"><%=request.getContextPath()%></span>
 <script type="text/javascript"	src="<%=request.getContextPath()%>/assets/app/js/users/schedulepayment.js"></script>
		
</body>
</html>