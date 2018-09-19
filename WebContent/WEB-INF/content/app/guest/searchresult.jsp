<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0">
<script id="coursestmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20 martop10 form-inline">
	<font class="size20">Courses</font> 
	<span class="btn-group pull-right"> 
		<a href="#learningcoursegridview" data-toggle="tab"
			class=" btn btn-default pad612 learningcoursestab active">
			<i class="fa fa-th-large"></i>
		</a>
		<a href="#learningcourslistview" data-toggle="tab" 
			class="btn btn-default pad612 learningcoursestab">
			<i class="fa fa-th-list"> </i>
		</a>
	</span>	
	<div class="btn-group pull-right marright7">
		<a class="btn btn-default dropdown-toggle whitebg pad612" data-toggle="dropdown">
			<font class="filtertitle">Filter By</font> <span class="caret"></span>
		</a>
		<ul class="dropdown-menu">
			<li><a class="filterlinks hover" a="p">Popularity Courses</a></li>
			<li><a class="filterlinks hover" a="h">Price - High to Low</a></li>
			<li><a class="filterlinks hover" a="l">Price - Low to High</a></li>
			<li><a class="filterlinks hover" a="e">Most Enrolled</a></li>
		</ul>
	</div>
	<a class="btn btn-default allresultslink marright7 pull-right">All</a>
	<span class="input-group pull-right marright7 col-md-3"> 
		<input class="authornametxt form-control" placeholder="Search by author" type="text">
		<span class="input-group-addon"> <i class="fa fa-search"></i>
		</span>
	</span>
</h3>
<div class="tab-content padtop10">
	<div class="tab-pane" id="learningcoursegridview">
		{{#if .}}  {{#each .}}
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
					<p class="description overflowtext">By {{firstName}} {{lastName}}</p>
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
				<div class="row">
					<a class="pad10 btn btn-default lightgraybg noborder centertext col-md-12 startlearninggridbtn"
						courseid={{courseid}} courseenrollmentstatus="{{courseenrollmentstatus}}"
						courseenrollmentid="{{courseenrollmentid}}"> 
						<i class="fa fa-play-circle"></i>&nbsp;Start Course
					</a>
				</div>
			</div>
		</div>
		{{/each}} {{else}}
		<div class="col-md-12 padtop200 whitebg min450 boxshadow">
			<h2 class="centertext">No courses</h2>
		</div>
		{{/if}}
	</div>
	<div class="tab-pane" id="learningcourslistview">
		{{#if .}} {{#each .}}
		<div class="row individualcourserow whitebg mar0 marbottom10 boxshadow">
			<div class="col-md-12 pad0">
				<div class="col-md-2 pad0">
					<img class="width100 height140"
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
		{{/each}} {{else}}
	<div class="col-md-12 padtop200 whitebg min450 boxshadow">
			<h2 class="centertext">No courses</h2>
		</div>
	{{/if}}
	</div>
</div>
<form name="startlearninggridform" method="post">
<input type="hidden" name="courseid" value="">
<input type="hidden" name="courseenrollmentid" value="">
<input type="hidden" name="wishlisted" value="">
</form>
</script>
<script id="categoriestmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
	<h3>Categories</h3>
</li>
{{#each .}}
	<li class="leftsideli hover catlink " coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}<span
	class="count ">{{count}}</span></li>	
{{/each}}
</script>
<script id="levelstmpl" type="text/x-handlebars-template">
<li class="leftsideliheader">
	<h3>Instructional Level</h3>
</li>
{{#each .}}
	<li class="leftsideli hover levelcrslink" level="{{instructoinallevel}}">{{instructoinallevel}}<span
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
	<%
		String keyword = request.getParameter("keyword");
	%>

<body>
	<span class="keyword" keyword="<%=keyword%>" style="display: none;" at=""></span>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/">Home </a> / <a>Search
					Results </a>
			</div>
		</div>
	</div>
	<h3 class="size20 martop10">Search Results for <font class="srkey"></font></h3>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10">
					<ul class="pad0 " id="categoriestable"></ul>
					<ul class="pad0 martop20" id="levelstable"></ul>
					<ul class="pad0 martop20" id="pricetable"></ul>
				</div>
				<div class="col-md-9 padright0">
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
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p class="mesdesc pad10"></p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/searchresult.js"></script>
</body>
</html>