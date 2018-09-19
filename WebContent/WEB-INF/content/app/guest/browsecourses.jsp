<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script id="topcategoriestmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
	<h3>Category<button type="button" class="navbar-toggle mar0 pad0"
					data-toggle="collapse" data-target="#myearningsdiv"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <i class="fa fa-bars"></i>
				</button></h3>
</li>
<div id="myearningsdiv" class="collapse navbar-collapse pad0">
{{#if .}}
{{#each .}}
	<li class="leftsideli hover leftsidelink" boardid="{{boardid}}">{{boardname}}</li>
{{/each}}
{{else}}
	<li class="leftsideli centertext">No Categories</li>
{{/if}}
</script>
<script id="nocoursestmpl" type="text/x-handlebars-template">
<div class="row nocourserow">
	<div class="col-md-12 padtop200 whitebg min450">
		<h2 class="centertext">No courses</h2>
	</div>
</div>
</script>
<script id="subcategoriestmpl" type="text/x-handlebars-template">
Sub Categories : {{#each .}}
<a class="badge subcategorylink pad10" coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}</a>
{{/each}}
</script>
<script id="coursesheadertmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20">Courses 
	<span class="btn-group pull-right"> <a href="#learningcoursegridview" data-toggle="tab"
		class=" btn btn-default pad612 learningcoursestab active"><i class="fa fa-th-large"></i>
		</a> <a href="#learningcourslistview"
		data-toggle="tab" class="btn btn-default pad612 learningcoursestab"><i class="fa fa-th-list"></i></a>
	</span>
	<div class="btn-group pull-right marright7">
		<button class="btn btn-default dropdown-toggle whitebg pad612" data-toggle="dropdown">
			<font class="filtertitle">Filter By</font> <span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><a class="filterlinks hover" a="p">Popularity Courses</a></li>
			<li><a class="filterlinks hover" a="h">Price - High to Low</a></li>
			<li><a class="filterlinks hover" a="l">Price - Low to High</a></li>
			<li><a class="filterlinks hover" a="e">Most Enrolled</a></li>
		</ul>
	</div>
	<span class="pull-right marright7 martop7"><a class="label" style="background-color:rgb(115, 179, 115);margin-left:35px;padding: 5px;padding-left: 10px;padding-right: 10px;">
		<i class="fa fa-play-circle"></i></a><span class="fontsize" style="font-size:14px;color:rgb(224, 97, 97) !important;"> - LiveSession Course </span>
	</span>
</h3>
</script>
<script id="coursestmpl" type="text/x-handlebars-template">
<form method="post" name="coursemanage">
<input type="hidden" name="courseid">
<div class="tab-content padtop7">
	<div class="tab-pane" id="learningcoursegridview">
		{{#if .}} {{#each .}}
		<div class="col-md-3 pad5">
			<div class="col-md-12 whitebg boxshadow">
				<div class="row">
					<a class="btn btn-flat btn-blue wishbtn" courseid="{{courseid}}"
						courseenrollmentstatus="{{courseenrollmentstatus}}"
						style="display: none;"></a>
					<img class="courselogo  width100 height140 hidden-xs"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="row pad5">
					<h5 class="coursetitle overflowtext" title="{{coursetitle}}">{{coursetitle}}</h5>					
				</div>
				<div class="row centertext">
					<span class="ratingcourse" rating="{{rating}}" style="width: 100px;"></span>				
				</div>				
				<div class="row martopbottom10">
					<div class="col-md-5" style=""><a class="label label-default userscountbtn" data-toggle="tooltip" title="Enrolled">
						<i class="fa fa-user marright7"></i>{{coursesubscribers}}</a>
					</div>
					<div class="col-md-3 pad0">
						<a class="label label-default livebtn" style="display:none;" data-toggle="tooltip" title="Livesession" courseid={{courseid}}><i class="fa fa-play-circle"></i></a>
					</div>
					<div class="col-md-4" >
						<a class="label label-default icon_price" price="{{price}}" style="padding: 5px;"
							priceicon ="<s:text name="label.price" ></s:text>"></a>				
					</div>
				</div>
				<div class="row centertext lightgraybg pad5">
					<a class="btn btn-default whitebg pull-left marleft10 managecourse" courseid="{{courseid}}">
						<i title="manage" class="fa fa-pencil"></i></a>	
					<a class="btn btn-default pull-right whitebg usercount" courseid="{{courseid}}" data-toggle="modal" 
						href="#courseUserModel" coursetitle="{{coursetitle}}" ><i title="users" class="fa fa-users"></i></a>					
				</div>
			</div>
		</div>
		{{/each}} {{else}}
		<div class="row-fluid individualcourserow" style="min-height: 450px;">
			<p class="center categorydescription nocoursetxt">No courses</p>
		</div>
		{{/if}}
	</div>
	<div class="tab-pane" id="learningcourslistview">
		{{#if .}} {{#each .}}
		<div class="row whitebg mar0 marbottom10 boxshadow">
			<div class="col-md-12 pad0">
				<div class="col-md-2 pad0">
					<img class="width100 height140 hidden-xs"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="col-md-10">
					<div class="row martop10">
						<div class="col-md-12">
							<div class="col-md-6">
								<h5 class="coursetitle">{{coursetitle}}</h5>
								<p class="lightbrown">By {{firstName}} {{lastName}}</p>
							</div>
							<div class="col-md-6 righttext">
								<a class="btn btn-primary pull-right  managecourse"
									courseid="{{courseid}}" ><i
									class="fa fa-pencil"><span>&nbsp;Manage</span></i></a> 
								<a class="btn btn-primary usercount pull-right marright7"
									courseid="{{courseid}}" data-toggle="modal" href="#courseUserModel"
									coursetitle="{{coursetitle}}"><i class="fa fa-users"><span>&nbsp;Users</span></i></a>								
							</div>
						</div>
					</div>
					<div class="row martop20">
						<div class="col-md-12">
							<div class="col-md-3">
								<a class="label label-primary userscountbtn"> <i
							class="icon-user"></i>&nbsp;&nbsp;{{coursesubscribers}}&nbsp;&nbsp;Enrolled
						</a>
							</div>							
							<div class="col-md-4">
								<span class="ratingcourse" rating="{{rating}}"></span>
							</div>
							<div class="col-md-2">
								<a class="label livebtn" style="display: none;"
									data-toggle="tooltip" title="Live Session" courseid={{courseid}}><i
									class="fa fa-play-circle"></i></a>
							</div>
							<div class="col-md-3 righttext">
								<a class="label label-primary icon_price" price="{{price}}"
										priceicon="<s:text name="label.price" ></s:text>"></a>								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	{{/each}}	 {{else}}
	<div class="row-fluid individualcourserow" style="min-height: 450px;">
		<p class="center categorydescription nocoursetxt">No courses</p>
	</div>
	{{/if}}
	</div>
</div>
</form>
</script>
</head>
<body>
	<div class="alert alert-success wishlistsuccess" style="display: none;">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<strong>Success!</strong> The course has been wishlisted. You can find
		them in the wishlisted courses section in mycourses.
	</div>

	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
				</a> / <a>Categories </a> <span class="pull-right searchbtn"><i
					class="fa fa-search"></i></span>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row dropbox titlecontainer form-horizontal pad5"
			style="display: none;">
			<div class="col-md-12">
				<div class="col-sm-1 righttext martop3">Search :</div>
				<div class="col-sm-8 martop7">
					<input class="searchtextbox form-control" type="text"
						placeholder="Enter Keywords to search">
				</div>
				<div class="col-sm-3 lefttext">
					<a class="btn btn-primary searchkwrdbtn marright7"> <i
						class="icon-search marright7"></i>Search
					</a> <a class="btn btn-danger closebtn"> <i
						class="icon-remove-sign marright7"></i>Cancel
					</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid" id="topcontainer">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10">
					<ul class="pad0" id="topcategoriestable">
					</ul>
				</div>
				<div class="col-md-9 martop10 padright0">
					<div id="rightcategorytable" class="whitebg pad10"></div>
					<div id="rightheadertable" class=""></div>
					<div id="righttable" class=""></div>
				</div>
			</div>
		</div>
	</div>

	<form name="browseuserform" method="post">
		<input type="hidden" name="keyword" value=""> <input
			type="hidden" name="courseid" value=""> <input type="hidden"
			name="courseenrollmentid" value=""> <input type="hidden"
			name="wishlisted" value="">
	</form>
 
	<div class="modal fade" id="courseUserModel">
		<div class="modal-dialog modal-lg" role="document">
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
					<li class="borderbottoma courseusers" a="invited"><a>Invited
							Users</a></li>
					<a class="btn btn-success pull-right martopright5 assignSubmitbtn"
						style="display: none;">Assign</a>
				</ul>
				<div class="modal-body">
					<div id="usercourselist"></div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/courseusers.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/admin/browseadmin.js"></script>
</body>
</html>