<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.header {
	margin-left: 7%;
}

.picture {
	height: 150px;
	width: 150px;
	position: absolute;
	top: 50px;
	left: -70px;
}

.header.url .fa {
	color: #588C73 !important;
}
</style>
<script id="personalinfotmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-1 bg1 height250"></div>
<div class="col-md-11">
	<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphoto}}&filetype=photo"
	class="img-thumbnail picture" />
	<div class="header martop30">
		<h3 class="capitalise">{{firstName}} {{lastName}}
			<a class="btn btn-success messageauthor pull-right">
				<i class="fa fa-envelope marright5"></i>Send A Message</a></h3>
		<h5>{{designation}}</h5>
		<span>{{aboutauthor}}</span>
	</div>
	<div class="col-md-12 padleft0 martop10 header url">
			<div class="col-md-6 padleft0">
				<i class="fa fa-facebook marright5"></i> : 
					{{#if facebookurl}} <a href="https://{{facebookurl}}" target="_new">{{facebookurl}}</a> {{else}} <a> - {{/if}}</a>								
			</div>
			<div class="col-md-6 padleft0">
				<i class="fa fa-twitter marright5"></i> : 
				{{#if twitterurl}} <a href="https://{{twitterurl}}" target="_new">{{twitterurl}}</a> {{else}} <a> - {{/if}}</a>
			</div>
		</div>

		<div class="col-md-12 padleft0 martop10 header url">
			<div class="col-md-6 padleft0">
				<i class="fa fa-linkedin marright5"></i> : 
					{{#if linkedinurl}} <a href="https://{{linkedinurl}}" target="_new">{{linkedinurl}}</a> {{else}} <a> - {{/if}}</a>								
			</div>
			<div class="col-md-6 padleft0">
				<i class="fa fa-globe marright5"></i> : 
				{{#if websiteurl}} <a href="https://{{websiteurl}}" target="_new">{{websiteurl}}</a> {{else}} <a> - {{/if}}</a>
			</div>
		</div>
</div>
{{/each}}
</script>

<script id="authorcoursestmpl" type="text/x-handlebars-template">
{{#if .}}
{{#each .}}
<div class="col-md-12 marbottom10 lightgraybg boxshadow border pad5 pagapplycourses">
	<div class="col-md-2 pad0 centertext">
		<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo"
		class="img-responsive img-rounded height200 width100"/>
		<span class="ratingcourse lineheight40" rating="{{rating}}"></span>
	</div>
	<div class="col-md-10 padright0">
		<div class="col-md-12 pad0">			
				<h3 class="size20 capitalise mar0 martopbottom10"><font class="martop20">{{coursetitle}}</font>
				<a class="btn btn-blue pull-right viewcoursedetailslink" courseid="{{courseid}}">View Details</a></h3>								
				<p class="min95 shorten">{{coursedescription}}</p>			
		</div>
		<div class="col-md-12 martop10">
			<div class="col-md-4">				
				<span class="label label-primary pad10"><i class="fa fa-users marright5"></i>{{enrolledusers}}</span>
			</div>
			<div class="col-md-4">
				<span class='ratediv' data-score='{{rating}}'></span>
			</div>
			<div class="col-md-4 righttext">				
				<span class="label label-primary pad10"><font>Price : </font>
				<font class="icon_price" price="{{price}}" priceicon ="<s:text name="label.price"></s:text>"></font></span>
			</div>
		</div>
	</div>
</div>
{{/each}}
{{else}}
<div class="col-md-12 padtop200 whitebg min450">
	<h3 class="centertext">No Records to Display</h3>
</div>
{{/if}}
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="authorreviewstmpl" type="text/x-handlebars-template">
<div class="blog-comment">
<ul class='comments'>
	{{#if .}}{{#each.}}
	<li class='clearfix pagapplyreviews'><img
		src='<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{raterphoto}}&filetype=photo'
		class='avatar' alt=''>
		<div class='post-comments lightgraybg  boxshadow'>
			<p class='meta'>
				On {{{ratingdate ratingdate}}} <a>{{firstName}} {{lastName}}</a> rated :<span
					class='ratediv marright5' style='margin-left: 10px;' data-score='{{courserating}}'></span> for the course <a>{{coursetitle}}</a>
			</p>
			<p style='overflow: hidden; text-overflow: ellipsis;'>{{courseratingdescription}}</p>
		</div></li>{{/each}}{{else}}
	<h5 style='text-align: center;'>No Reviews Given</h5>
	{{/if}}
</ul>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		String userCheck = ""
				+ SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		String orgstatusa = null;
		if (userCheck.equalsIgnoreCase("anonymousUser")) {
			orgstatusa = "guest";
		} else {
			orgstatusa = "user";
		}
	%>
	<span class="userconfirm" at="<%=orgstatusa%>"></span>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
			<%
			if (orgstatusa.equalsIgnoreCase("guest")){
			%>
			<a href="<%=request.getContextPath()%>">Home </a>
			<%
			}else{
			%>
			<a href="<%=request.getContextPath()%>/app/mycourses">My Courses </a>
			<%
			}
			%>
				 / <%
			if (orgstatusa.equalsIgnoreCase("guest")){
			%>
			<a href="<%=request.getContextPath()%>/authorsearch">Authors </a>
			<%
			}else{
			%>
			<a href="<%=request.getContextPath()%>/app/authorsearch">Authors </a>
			<%
			}
			%> / <a >Author Profile</a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row panel boxshadow" id="personalinfotable"></div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 whitebg">
				<div class=''>
					<ul class="nav nav-tabs informationview pad0" id="myTab"
						style="margin-bottom: 10px;">
						<li class="borderbottoma authprofilelink"><a
							class="learningtablinksec" at="courses"> Courses </a></li>
						<li class="borderbottoma authprofilelink"><a class="learningtablinksec" at="review">
								Reviews </a></li>
					</ul>
					<div id="authorprofileholder"></div>					
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="authorprofilemessagemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Message</h4>
				</div>
				<div class="modal-body">
					<p class="authorprofilemessage"></p>
				</div>
			</div>
		</div>
	</div>

	<div id="sendmsgmodal" class="modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="myModalLabel" class="modal-title">Message</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-3 righttext">Message <font
								color="red">*</font> :
							</label>
							<div class="col-md-8">
								<textarea class="form-control" id="messagetosend"
									placeholder="Enter your message here...." cols=""></textarea>
								<div class="alert alert-danger descnullalert"
									style="display: none;">Please Enter A Message</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 righttext"></label>
							<div class="col-md-8">
								<button class="btn btn-blue" id="sendtouser">Send</button>
								<div class="alert alert-success msgsentalert"
									style="display: none;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form action="" name="authorprofileform" method="post">
		<input type="hidden" name="courseid" value="">
		<input type="hidden" name="courseenrollmentid" value="">
		<input type="hidden" name="wishlisted" value="">		
		 <input
			type="hidden" name="orgpersonid"
			value="<%=request.getParameter("orgpersonid")%>"> <input
			type="hidden" name="personid"
			value="<%=request.getParameter("personid")%>">

	</form>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/authorprofile.js"></script>
</body>
</html>