<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String roleName = userDetailsAdaptor.getUser().getProperty(
				"rolename");
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
		String coursetitle = request.getParameter("coursetitlehidden");
	%>
	<span class="pageattributes" role="<%=roleName%>"
		orgstatus="<%=orgstatus%>"></span>
	<div class="container-fluid martop30">
		<div class="row">
			<div class="col-md-12 centertext whitebg boxshadow padbottom20">
				<div class="hero-unit center">
					<h4 class="martop30">Your Course</h4>
					<h3 class="cornflowerblue martop30">
						"<%=coursetitle%>"
					</h3>
					<h4 class="martop20">has been published</h4>
					<div class="col-md-12">
						<div class="col-md-2"></div>
						<div class="col-md-8">
							<p class="martop20">
								<b>Note : </b>Your course has been forwarded to administrator
								for approval. Once it is approved, the course will be made
								available in the portal. If any issues found in the content the
								administrator can reject the content and the course will be
								moved to draft. The author will receive instructions in
								notifications for fixing the issues. After fixing the issues the
								author can again publish the course. After verification it will
								be Approved and made available in the portal.
							</p>
							<p class="centertext">
								<b>Please click on the proceed button to continue</b>
							</p>
							<a class="btn marbottom10 btn-info proceedbtn"><i
								class="fa fa-hand-o-right icon-white"></i> Proceed</a>
						</div>
						<div class="col-md-2"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form action="" name="proceedform" method="post"></form>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.proceedbtn').click(function() {
				var role = $('.pageattributes').attr('role');
				if (role === "admin") {
					$('[name=proceedform]').attr('action', 'courses?a=pc');
					$('[name=proceedform]').submit();
				} else if (role === "user") {
					$('[name=proceedform]').attr('action', 'mycourses?a=pc');
					$('[name=proceedform]').submit();
				}
			});
		});
	</script>
</body>
</html>