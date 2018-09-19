<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reamedi Cloud || Browse Authors</title>
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
<h3 class="marbottom15 size20">Authors 
	<span class="btn-group pull-right"> <a href="#learningcoursegridview" data-toggle="tab"
		class=" btn btn-default pad612 learningcoursestab active"><i class="fa fa-th-large"></i>
		</a> <a href="#learningcourslistview"
		data-toggle="tab" class="btn btn-default pad612 learningcoursestab"><i class="fa fa-th-list"></i></a>
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
					<img class="courselogo width100 height140 hidden-xs"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphotourl}}&filetype=photo">
				</div>
				<div class="row pad5">
					<h5 class="coursetitle overflowtext" title="{{coursetitle}}">{{firstname}} {{lastname}}</h5>
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
					<img class="width70per height100 hidden-xs"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphotourl}}&filetype=photo">
				</div>
				<div class="col-md-10 height100">
					<div class="row">
						<div class="col-md-12">
							<div class="col-md-8">
								<p class="authorname size12">Name : {{firstname}} {{lastname}}</p>
								<p class="size12">Designation : {{#if designation}} {{designation}} {{else}}<span class="error">Not Found </span>{{/if}}</p>
								<p class="size12">Organization : {{#if organizationname}} {{organizationname}} {{else}}<span class="error">Not Found </span>{{/if}}</p>
								<p class="size12">Email : {{email}}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
	{{/each}}{{else}}
	<div class="row-fluid individualcourserow" style="min-height: 450px;">
		<p class="center categorydescription nocoursetxt">No courses</p>
	</div>

	{{/if}}
</div>
</div>
</script>
</head>
<body>
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
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/authors.js"
		type="text/javascript"></script>
</body>
</html>