<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0">

<script id="userlisttempl" type="text/x-handlebars-template">
<div class="row">
	<div class="col-md-12">
		{{#if assignusers}}
		<div class="row">
			<div class="col-md-10">
				<font class="categorydescription"> <b>Course : <font
						class="coursetitleuser"></font></b></font> <label class="coursehiddenid"
					style="display: none;"></label>
			</div>
			<div class="col-md-2">
				<a class="btn btn-primary coursesubmit white">Assign</a>
			</div>
		</div>
		<table class="table table-bordered">
			<thead>
				<tr class="success">
					<th>Name</th>
					<th class="center"><font class="btn btn-primary selectbtn"
						style="width: 80%">Select All</font></th>
				</tr>
			</thead>
			<tbody>
				{{#each assignusers}}
				<tr>
					<td><font class="username">{{firstName}}</font></td>
					<td class="center"><font class="btn btn-primary white assignbtn"
						style="width: 80%" personid="{{personid}}"
						courseenrollmentid="{{courseenrollmentid}}">Select</font></td>
				</tr>
				{{/each}}
			</tbody>
		</table>
		{{else}}
		<div class="row-fluid" style="margin-bottom: 10px;">
			<div class="span10">
				<font class="categorydescription"> <b>Course : <font
						class="coursetitleuser"></font></b></font> <label class="coursehiddenid"
					style="display: none;"></label>
			</div>
			<div class="span2"></div>
		</div>
		<p class="description center">No users to assign</p>
		{{/if}}
	</div>
</div>
</script>
<script id="usercourselisttempl" type="text/x-handlebars-template">
<div class="row-fluid" style="margin-bottom: 10px;">
	<div class="span10">
		<font class="categorydescription"> <b>Course : <font
				class="coursetitleuser"></font></b></font> <label class="coursehiddenid"
			style="display: none;"></label>
	</div>
	<div class="span2"></div>
</div>
{{#if .}}
<table class="table table-bordered">
	<thead>
		<tr class="success">
			<th>Name</th>
			<th class="center"><font>Actions</font></th>
		</tr>
	</thead>
	<tbody>
		{{#each .}}
		<tr>
			<td><font class="username">{{firstName}}</font></td>
			<td class="center"><font class="btn btn-default lightgraybg blockUserbtn"
				style="width: 80%" personid="{{orgpersonid}}" message="you are blocked from a course . Contact Admin"
				courseenrollmentid="{{courseenrollmentid}}">Block User</font></td>
		</tr>
		{{/each}}
	</tbody>
</table>
{{else}}
<p class="description center">User is not available</p>
{{/if}}
</div>
</script>
<script id="coursestmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20 martop10 form-inline">
	<span class="">Courses</span> 
	<span class="btn-group pull-right"> 
		<a href="#learningcoursegridview" data-toggle="tab"
			class=" btn btn-default learningcoursestab pad612">
			<i class="fa fa-th-large"></i></a>
		<a href="#learningcourslistview" data-toggle="tab" 
			class="btn btn-default learningcoursestab pad612">
			<i class="fa fa-th-list"></i></a>
	</span>
	<div class="btn-group pull-right marright7">
		<button class="btn btn-default dropdown-toggle whitebg pad612" data-toggle="dropdown">
			<font class="filtertitle">Filter By </font><span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><a class="filterlinks hover" a="p">Popularity
					Courses</a></li>
			<li><a class="filterlinks hover" a="h">Price - High to
					Low</a></li>
			<li><a class="filterlinks hover" a="l">Price - Low to
					High</a></li>
			<li><a class="filterlinks hover" a="e">Most Enrolled</a></li>
		</ul>
	</div>
		<a class="btn btn-default allresultslink marright7" style="float: right;">All</a>
<span class="input-group col-md-3 authorsearchbox pull-right marright7"> 
		<input class="authornametxt form-control" placeholder="Search by author" type="text">
		<span class="input-group-addon"> <i class="fa fa-search"></i></span>
	</span>
</h3>

<div class="tab-content padtop10">
	<div class="tab-pane " id="learningcoursegridview">
		{{#if .}} {{#each .}}
		<div class="col-md-3 pad5">
			<div class="col-md-12 whitebg boxshadow">
				<div class="row">
					<a class="btn btn-flat btn-blue wishbtn" courseid="{{courseid}}"
						courseenrollmentstatus="{{courseenrollmentstatus}}"
						style="display: none;"></a>
					<img class="courselogo width100 height140"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="row pad5">
					<h5 class="overflowtext" title="{{coursetitle}}">{{coursetitle}}</h5>
					<p class="description">By {{firstName}} {{lastName}}</p>
				</div>
				<div class="row centertext">
					<span class="ratingcourse" rating="{{rating}}" style="width: 100px;"></span>
				</div>
				<div class="row martopbottom10">
					<div class="col-md-6">
						<a class="label label-default userscountbtn"
							data-toggle="tooltip" title="Enrolled">
							<i class="fa fa-user marright7"></i>{{coursesubscribers}}</a>
					</div>
					<div class="col-md-6 righttext">
						<a class="label label-default icon_price" price="{{price}}"
							priceicon="<s:text name="label.price" ></s:text>"></a>
					</div>
				</div>

				<div class="startlearndiv row" style="display: none;">
					<a class="pad10 btn btn-default pad0 lightgraybg noborder centertext col-md-12 startlearninggridbtn"
						courseid={{courseid}}
						courseenrollmentstatus="{{courseenrollmentstatus}}"
						courseenrollmentid="{{courseenrollmentid}}"> <i
						class="fa fa-play-circle"></i>&nbsp;Start Course
					</a>
				</div>
				
				<div class="managediv row pad5 centertext lightgraybg" style="display: none;">
					<a class="btn btn-default whitebg pull-left  managecourse" courseid="{{courseid}}" >
						<i title="manage" class="fa fa-pencil"></i></a> 
					<a class="btn btn-default whitebg usercount pull-right" courseid="{{courseid}}" 
						data-toggle="modal" href="#courseUserModel" coursetitle="{{coursetitle}}">
						<i title="users" class="fa fa-users"></i></a>
				</div>
			</div>
		</div>
		{{/each}} {{else}}		
		<div class="col-md-12 padtop200 whitebg min450">
			<h2 class="centertext">No courses</h2>
		</div>		
		{{/if}}
	</div>
	<div class="tab-pane" id="learningcourslistview">
		{{#if .}} {{#each .}}
		<div class="row individualcourserow whitebg martopbottom15 boxshadow">
			<div class="col-md-12 pad0">
				<div class="col-md-2 pad0">
					<img class="width100 height140"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="col-md-10 pad0">
					<div class="row">
						<div class="col-md-6">
							<div class="col-md-12">
								<h5 class="overflowtext" title="{{coursetitle}}">{{coursetitle}}</h5>
								<p class="description">By {{firstName}} {{lastName}}</p>
							</div>
						</div>
						<div class="col-md-6 martop20 pad0">
							<div class="startlearndiv col-md-11 righttext pad0" style="display: none;">
								<a class="btn btn-primary btn-gray startlearninggridbtn pull-right"
									courseid={{courseid}}
									courseenrollmentstatus="{{courseenrollmentstatus}}"
									courseenrollmentid="{{courseenrollmentid}}"><i
									class="fa fa-play-circle"></i>&nbsp;Start Course</a>
							</div>
							<div class="managediv col-md-11 righttext pad0" style="display: none;">
								 <a class="btn btn-primary usercount pull-right marright7"
									courseid="{{courseid}}" title="Users"
									data-toggle="modal" href="#courseUserModel"
									coursetitle="{{coursetitle}}"><i class="fa fa-users"></i></a>
								<a class="btn btn-primary pull-right managecourse marright7" 
									title="Manage the Course" courseid="{{courseid}}"><i
									class="fa fa-pencil"></i> </a>								
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 martop20">
							<div class="col-md-4" style="">
								<a class="label label-default userscountbtn"
									style="padding: 5px;"> <i class="fa fa-user"></i>&nbsp;&nbsp;{{coursesubscribers}}&nbsp;&nbsp;Enrolled
								</a>
							</div>
							<div class="col-md-4">
								<span class="ratingcourse" rating="{{rating}}"
									style="width: 100px;"></span>
							</div>
							<div class="col-md-4 righttext">
								<a class="label label-default icon_price" price="{{price}}"
									style="padding: 5px;"
									priceicon="<s:text name="label.price" ></s:text>"></a>
							</div>
						</div>
					</div>					
				</div>
			</div>
		
	</div>
	{{/each}} {{else}}	
		<div class="col-md-12 padtop200 whitebg min450 boxshadow">
			<h2 class="centertext">No courses</h2>
		</div>	
	{{/if}}
	</div>
</div>
</div>
<form name="startlearninggridform" method="post">
	<input type="hidden" name="courseid" value=""> <input
		type="hidden" name="courseenrollmentid" value=""> <input
		type="hidden" name="wishlisted" value="">
</form>
</script>
<script id="categoriestmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
	<h3>Categories</h3>
</li>
{{#each .}}
	<li class="leftsideli hover catlink" coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}<span
	class="count ">{{count}}</span></li>	
{{/each}}
</script>
<script id="levelstmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
	<h3>Instructional Level</h3>
</li>
{{#each .}}
	<li class="leftsideli hover border levelcrslink" level="{{instructoinallevel}}">{{instructoinallevel}}<span
	class="count ">{{count}}</span></li>	
{{/each}}
</script>
<script id="pricetmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
				<h3>Price</h3>
			</li>
			<li class="leftsideli hover pricecourselink" a="f">Free<span
				class="count ">{{freecourses}}</span></li>
				<li class="leftsideli hover pricecourselink" a="oo">From <span class="iconprice" priceicon ="<s:text name="label.price" ></s:text>"></span>1 - <span class="iconprice" priceicon ="<s:text name="label.price" ></s:text>"></span>1000<span
				class="count ">{{upto1k}}</span></li>
				<li class="leftsideli hover pricecourselink" a="of">From <span class="iconprice" priceicon ="<s:text name="label.price" ></s:text>"></span>1001 - <span class="iconprice" priceicon ="<s:text name="label.price" ></s:text>"></span>5000<span
				class="count ">{{oneto5k}}</span></li>
				<li class="leftsideli hover pricecourselink" a="af">><span class="iconprice" priceicon ="<s:text name="label.price" ></s:text>"></span>5000<span
				class="count ">{{above5k}}</span></li>

</script>
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String rolename = userDetailsAdaptor.getUser().getProperty(
				"rolename");
	%>
	<%
		String keyword = request.getParameter("keyword");
	%>
	<span class="keyword" keyword="<%=keyword%>" style="display: none;" at=''></span>
	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/browseu">Browse
					Courses </a> / Search Results
			</div>
		</div>
	</div>
<h3 class="size20 martop10">Search Results for <font class="srkey"></font></h3>
	<div class="container-fluid ">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0">
					<ul class="leftsideul pad0 martop10" id="categoriestable"></ul>
					<ul class="leftsideul pad0 martop20" id="levelstable"></ul>
					<ul class="leftsideul pad0 martop20" id="pricetable">
					</ul>
				</div>
				<div class="col-md-9 martop10 padright0">
					<div id="coursestable"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="messagemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<font><b>Message</b></font>
				</div>
				<div class="modal-body">
					<p class="mesdesc" style="padding: 10px;"></p>
				</div>
			</div>
		</div>
	</div>
	<div id="assignUserModel" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">Assign Course</h3>
					<div class="alert alert-success" hidden="true" id="alertBox">
						<strong>Message!</strong>
						<command id="msg" style="margin-left: 0px"></command>
					</div>
				</div>
				<div class="modal-body" style="padding: 10px;">
					<div id="userlist"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="courseUserModel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						Course : <font class="cusertitle">Course</font>
					</h4>
				</div>
				<ul id="" class="nav nav-tabs firsttab pad0 mar0 marbottom10">
					<li class="borderbottoma courseusers" a="learning"><a>Learning
							Users</a></li>
					<li class="borderbottoma courseusers" a="completed"><a>Completed
						Users</a></li>
					<li class="borderbottoma courseusers" a="blocked"><a>Blocked
							Users</a></li>
					<li class="borderbottoma courseusers" a="available"><a>Avaliable
							Users</a></li>
					<a class="btn btn-success pull-right martopright5 assignSubmitbtn" style="display:none;">Assign</a>
				</ul>
				<div class="modal-body">
					<div id="usercourselist"></div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" class="orgstatusid" value="<%=rolename%>">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/courseusers.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/searchresult.js"></script>
</body>
</html>