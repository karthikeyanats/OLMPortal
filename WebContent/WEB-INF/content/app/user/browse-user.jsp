<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reamedi Cloud || Browse Courses</title>
<script id="topcategoriestmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
	<h3>Category<button type="button" class="navbar-toggle mar0 pad0"
					data-toggle="collapse" data-target="#brcoursediv"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <i class="fa fa-bars"></i>
				</button></h3>
</li>
<div id="brcoursediv" class="collapse navbar-collapse pad0">
{{#each .}}
	<li class="leftsideli hover leftsidelink" boardid="{{boardid}}">{{boardname}}</li>
{{/each}}
</div>
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
<a class="badge subcategorylink white pad10" coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}</a>
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
<div class="tab-content">
	<div class="tab-pane" id="learningcoursegridview">
		{{#if .}} {{#each .}}
		<div class="col-md-3 pad5 individualcourserow">
			<div class="col-md-12 whitebg boxshadow">
				<div class="row">
					<a class="btn btn-flat btn-blue wishbtn" courseid="{{courseid}}"
					courseenrollmentstatus="{{courseenrollmentstatus}}"
					style="display: none;"></a> <img
					class="courselogo width100 height140 hidden-xs"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="row pad5">
					<h5 class="coursetitle overflowtext" title="{{coursetitle}}">{{coursetitle}}</h5>
						<p class="description overflowtext">By {{firstName}} {{lastName}}</p>
				</div>
				<div class="row centertext">
					<span class="ratingcourse" rating="{{rating}}" style="width: 100px;"></span>
				</div>
				<div class="row martopbottom10">
					<div class="col-md-5">
						<a class="label label-default userscountbtn" data-toggle="tooltip" title="Enrolled">
							<i class="fa fa-user marright7"></i>{{coursesubscribers}}
						</a>
					</div>
					<div class="col-md-3 pad0">
						<a class="label livebtn" style="display: none;"
							data-toggle="tooltip" title="Livesession" courseid={{courseid}}><i
							class="fa fa-play-circle"></i></a>
					</div>
					<div class="col-md-4">
						<a class="label label-default icon_price" price="{{price}}"
							priceicon="<s:text name="label.price" ></s:text>"></a>
					</div>
				</div>
				<div class="row">
					<a class="pad10 btn btn-default lightgraybg noborder centertext col-md-12 startlearninggridbtn"
						courseid={{courseid}} courseenrollmentstatus="{{courseenrollmentstatus}}"
						courseenrollmentid="{{courseenrollmentid}}">
						<i class="fa fa-play-circle marright7"></i>Start Course</a>
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
		<div class="row whitebg mar0 marbottom10 boxshadow individualcourserow">
			<div class="col-md-12 pad0">
				<div class="col-md-2 pad0">
					<img class="width100 height140 hidden-xs"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="col-md-10">
					<div class="row martop10">
						<div class="col-md-12">
							<div class="col-md-8">
								<h5 class="coursetitle">{{coursetitle}}</h5>
								<p class="lightbrown">By {{firstName}} {{lastName}}</p>
							</div>
							<div class="col-md-4 righttext">
								<a class="btn btn-primary startlearninggridbtn"
									courseid={{courseid}}
									courseenrollmentstatus="{{courseenrollmentstatus}}"
									courseenrollmentid="{{courseenrollmentid}}"><i
									class="fa fa-play-circle"></i>&nbsp;Start Course</a>
							</div>
						</div>
					</div>
					<div class="row martop20">
						<div class="col-md-12">
							<div class="col-md-3">
								<a class="label label-primary userscountbtn pad5"> <i class="fa fa-user"></i>&nbsp;&nbsp;{{coursesubscribers}}&nbsp;&nbsp;Enrolled
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
								<a class="label label-primary icon_price pad5 padleft10" price="{{price}}"
									priceicon="<s:text name="label.price" ></s:text>"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
	{{/each}}{{else}}
	<div class="row-fluid individualcourserow" style="min-height: 450px;">
		<p class="center categorydescription nocoursetxt overflowtext">No courses</p>
	</div>

	{{/if}}
</div>
</div>
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
				<a href="<%=request.getContextPath()%>/app/mycourses">Home </a> / <a>Categories
				</a> <span class="pull-right searchbtn"><i class="fa fa-search"></i></span>
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
			name="courseenrollmentstatus" value=""> <input type="hidden"
			name="wishlisted" value=""> <input type="hidden" name="price"
			value=""> <input type="hidden" name="priceid" value="">
	</form>

	<div class="modal fade" id="subscribedmodal">
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
					<p class="enrolstatus"></p>
				</div>				
			</div>
		</div>
	</div>
	<s:hidden name="courseenrollmentstatus"
		value="%{courseenrollmentstatus}"></s:hidden>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/browse.js"
		type="text/javascript"></script>
</body>
</html>