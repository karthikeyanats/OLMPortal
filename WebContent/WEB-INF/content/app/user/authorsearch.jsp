<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Author Search</title>
<style>
.authorsearch .fa,.authorsearch h3 {
	color: #588C73 !important;
}

.authorsearch .fa {
	width: 20px;
	height: 20px;
	font-size: 18px;
}
</style>
<script id="authorstmpl" type="text/x-handlebars-template">
{{#if .}}
{{#each .}}
<div class="col-md-12 marbottom10 whitebg boxshadow pad5 authorsearch pagapply">
	<div class="col-md-2 pad0 centertext">
		<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphoto}}&filetype=photo"
		class="img-responsive img-rounded height140 width100"/>
		<span class="ratingcourse lineheight40" rating="{{ratings}}"></span>
	</div>
	<div class="col-md-10 padright0">
		<div class="col-md-12 pad0 borderbottom1">
			<div class="col-md-10 pad0">
				<h3 class="size20 capitalise mar0 martopbottom10"><font class="martop20">{{firstName}} {{lastName}}</font>
				</h3>								
			</div>
			<div class="col-md-2 pad0">
				<a class="btn btn-blue pull-right viewdetailslink" personid="{{personid}}" orgpersonid="{{orgpersonid}}">View Details</a>
			</div>
		</div>
		<div class="col-md-12 pad0 martopbottom10">
			{{#each course}}<a class="badge marright5 lineheight20 marbottom10">{{coursetitle}}</a>{{/each}}			
		</div>
		<div class="col-md-12 padleft0 martop10">
			<div class="col-md-6 padleft0">
				<i class="fa fa-envelope marright5"></i> : 
					<a href="mailto:{{userid}}">{{#if userid}} {{userid}} {{else}} - {{/if}}</a>	
			</div>
			<div class="col-md-6 padleft0">
				<a ><i class="fa fa-phone marright5"></i> : {{#if number}} {{number}} {{else}} - {{/if}}</a>
			</div>
		</div>

		<div class="col-md-12 padleft0 martop10">
			<div class="col-md-6 padleft0">
				<i class="fa fa-facebook marright5"></i> : 
					{{#if facebookurl}} <a href="https://{{facebookurl}}" target="_new">{{facebookurl}}</a> {{else}} <a> - {{/if}}</a>								
			</div>
			<div class="col-md-6 padleft0">
				<i class="fa fa-twitter marright5"></i> : 
				{{#if twitterurl}} <a href="https://{{twitterurl}}" target="_new">{{twitterurl}}</a> {{else}} <a> - {{/if}}</a>
			</div>
		</div>

		<div class="col-md-12 padleft0 martop10">
			<div class="col-md-6 padleft0">
				<i class="fa fa-linkedin marright5"></i> : 
					{{#if linkedinurl}} <a href="https://{{linkedinurl}}" target="_new">{{linkedinurl}}</a> {{else}} <a> - {{/if}}</a>								
			</div>
			<div class="col-md-6 padleft0">
				<i class="fa fa-globe marright5"></i> : 
				{{#if websiteurl}} <a href="//{{websiteurl}}" target="_new">{{websiteurl}}</a> {{else}} <a> - {{/if}}</a>
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
				 / <a>Authors
				</a><a class="btn btn-blue pull-right resetfilter martop3">Reset All Filters</a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0">
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>Filter By Author Name</h3>
						</li>
						<li class="leftsideli">
							<div class="input-group">
								<input class="form-control authornamesearchfield"
									placeholder="Enter Author Name To search" 
									type="text" autofocus> <span class="input-group-addon pointer authornamesearchlink">
									<i class="fa fa-search"></i>
								</span>
							</div>
						</li>
					</ul>
					
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>Filter By Course</h3>
						</li>
						<li class="leftsideli">
							<div class="input-group">
								<input class="form-control keywordsearchfield"
									placeholder="Enter course title To search (Eg., Java)"
									type="text" autofocus> <span class="input-group-addon pointer keywordsearchlink">
									<i class="fa fa-search"></i>
								</span>
							</div>
						</li>
					</ul>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>Filter By Review</h3>
						</li>
						<li class="leftsideli hover starlink" star='1'><i
							class="fa fa-star marright5"></i>1 Star</li>
						<li class="leftsideli hover starlink" star='2'><i
							class="fa fa-star"></i><i class="fa fa-star marright5"></i>2 Star</li>
						<li class="leftsideli hover starlink" star='3'><i
							class="fa fa-star"></i><i class="fa fa-star"></i><i
							class="fa fa-star marright5"></i>3 Star</li>
						<li class="leftsideli hover starlink" star='4'><i
							class="fa fa-star"></i><i class="fa fa-star"></i><i
							class="fa fa-star"></i><i class="fa fa-star marright5"></i>4 Star</li>
						<li class="leftsideli hover starlink" star='5'><i
							class="fa fa-star"></i><i class="fa fa-star"></i><i
							class="fa fa-star"></i><i class="fa fa-star"></i><i
							class="fa fa-star marright5"></i>5 Star</li>
					</ul>
				</div>
				<div class="col-md-9 padright0" id="authorstable"></div>
			</div>
		</div>
	</div>
<form method="post" name="authorsearchform" action="authorProfile">
<input type="hidden" name="personid" value="">
<input type="hidden" name="orgpersonid" value="">
</form>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/guest/authorsearch.js"
		type="text/javascript"></script>
</body>
</html>