<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script id="ecloudcoursestmpl" type="text/x-handlebars-template">
{{#each .}}
<li class="leftsideli hover leftsidelink" coursecategoryname="{{coursecategoryname}}" 
coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}
<span class="pull-right count draftcoursecount">{{coursescount}}</span></li>
{{/each}}
</script>
<script id="nocoursestmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle"></h3>
<br><h3 style="text-align:center;color: rgb(213, 156, 156);">No Courses</h3><br>
</script>

<script id="ecloudcourseswithdesctmplbox"
	type="text/x-handlebars-template">
<i><a href="#" class="pull-right btn btn-mini icon-th-list statuschk inlisttype icondisp active" status="l"></a>
<a href="#" class="pull-right btn btn-mini icon-th inboxtype statuschk icondisp" status="b"></a> </i>
<span class="statval" style="display:none;"></span>
<div class="btn-group pull-right">
                <button class="btn btn-small dropdown-toggle" data-toggle="dropdown"><span class="selectval">Filter By</span> <span class="caret"></span></button>
                <ul class="dropdown-menu">
                  <li><a id="popcourse" class="popcourse" href="#">Popularity Courses</a></li>                  
				  <li><a id="highlow" class="highlow" href="#">Price - High to Low</a></li> 
                  <li><a id="lowhigh" class="lowhigh" href="#">Price - Low to High</a></li>
				  <li><a id="enrollwise" class="enrollwise" href="#">Most Enrolled Courses</a></li>
                </ul>
              </div>
<h3 class="cattitle"><font class="catname"></font> - Courses</h3>
{{#if catcourses}}
{{#each catcourses}}
<form name="coursemanage" method="post">

{{#each courses}}
	<div class="span4 well catlist-resp" courseid="{{courseid}}" style="margin-left:15px !important;">
	<div class="panel price panel-red"> 
		<div class="panel-body center panel-body1">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo" class="img-responsive courlogo" alt="">
		</div>
		<ul class="list-group list-group-flush text-center">
		<li class="list-group-item coursetitleli popcourselink coursetitlea" courseid={{courseid}}><a href="#">{{coursetitle}}</a><p class="para-resp">
			By {{firstName}} {{lastName}}</p></li>
<li><span class=" pull-right rupee" style = "color : #000 !important;">Price : <span class="icon_price" priceicon ="<s:text name="label.price"></s:text>" price={{price}} style="color :#0E800E !important;"></span></span></li>
			<li class="list-group-item">

				<span class="ratingcourse" rating="{{rating}}"></span>
			</li>
		</ul>
					
	</div>
</div>
{{/each}}
<input type="hidden" name="courseid" value="">
<input type="hidden" name="statusval" value="">
</form>
{{/each}} 
{{else}}
<div class="row-fluid individualcourserow" style="min-height:450px;">
<p class="center categorydescription nocoursetxt">No courses</p>
</div>
{{/if}}
</script>

<script id="ecloudcourseswithdesctmpl" type="text/x-handlebars-template">
<i><a href="#" class="pull-right btn btn-mini icon-th-list statuschk inlisttype icondisp active" status="l"></a>
<a href="#" class="pull-right btn btn-mini icon-th inboxtype statuschk icondisp" status="b"></a> </i>
<span class="statval" style="display:none;"></span>
<div class="btn-group pull-right">
                <button class="btn btn-small dropdown-toggle" data-toggle="dropdown"><span class="selectval">Filter By</span> <span class="caret"></span></button>
                <ul class="dropdown-menu">
                  <li><a id="popcourse" class="popcourse" href="#">Popularity Courses</a></li>                  
				  <li><a id="highlow" class="highlow" href="#">Price - High to Low</a></li> 
                  <li><a id="lowhigh" class="lowhigh" href="#">Price - Low to High</a></li>
				  <li><a id="enrollwise" class="enrollwise" href="#">Most Enrolled Courses</a></li>
                </ul>
              </div>
<h3 class="cattitle"><font class="catname"></font> - Courses</h3>
{{#if catcourses}}
{{#each catcourses}}
{{#each courses}}
<div class="row-fluid individualcourserow">
		<div class="span2">
			<img class="courselogo"
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
		</div>
		<div class="span10">
			<form name="coursemanage" method="post">
				<h3 class="coursetitle" courseid="{{courseid}}">
					{{coursetitle}}
				<span class="pull-right rupee" style="color :  #000;">Price : <span class="icon_price" priceicon ="<s:text name="label.price"></s:text>" price={{price}} style="color: #0E800E;"></span></span>
				</h3>
				<input type="hidden" name="courseid" value="">
				
			</form>
			<p class="description desc">{{coursedescription}}</p>
			<p class="description"><b>by {{firstName}} {{lastName}}</b></p> 
			<div class="row-fluid">
				<div class="span12 individualcourselinkspan">
				<div class="span4">
					<a class="userscountbtn label" style="padding:5px;cursor:alias;">
						<i class="icon-user"></i>&nbsp;&nbsp;{{coursesubscribers}}&nbsp;&nbsp;Enrolled
					</a>
				</div>
				<div class="span4 center">
					<div class="ratingcourse" rating="{{rating}}"></div>
				</div>
				<div class="span4 right">
					<form name="previewform" method="post">
					<a class="btn btn-flat btn-gray coursepreviewbtn" courseid="{{courseid}}" count="{{coursesubscribers}}"><i class="fa fa-play-circle"></i>
						&nbsp;&nbsp;Start Learning
					</a>
					<input type="hidden" name="courseid" value="">
					<input type="hidden" name="count" value="">
					<input type="hidden" name="statusval" value="">
					</form>
				</div>
				</div>
				
			</div>
		</div>
	</div>
{{/each}} 
{{/each}}
{{else}}
<div class="row-fluid individualcourserow" style="min-height:450px;">
<p class="center categorydescription nocoursetxt">No courses</p>
</div>
{{/if}}
</script>
<style type="text/css">
.btn-cursor {
	cursor: none;
}

.courlogo {
	display: block;
	margin-left: auto;
	margin-right: auto;
	height: 130px;
	width: 300px;
	background-position: center;
	background-size: cover;
}

.courlogo:hover {
	opacity: .7;
	box-shadow: 0, 0, 2px #ccc;
}

.para-resp {
	text-align: left;
	color: #8190A1;
	line-height: 2em;
}

.list-group-item a {
	display: block;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	text-align: left;
	font-size: 18px;
	color: black;
	text-decoration: none;
}

ul {
	margin: 10px 0px;
}

.icondisp {
	font-size: 14px;
	padding: 5px;
}

.panel-size {
	height: 250px;
	width: 30% !important;
}

@media ( min-width : 90px) and (max-width : 979px) {
	.courlogo {
		height: 225px;
		width: 100%;
	}
	.panel-size {
		height: 350px;
		width: 45% !important;
		float: left !important;
		margin-left: 20px !important;
	}
}

@media ( max-width : 767px) {
	.span3 .ratingcourse img {
		width: 13px !important;
	}
	.panel-size {
		width: 45% !important;
		margin-left: 0px;
	}
	.individualcourserow {
		height: 190px;
		width: 100%;
		float: left;
		/* margin-left: 20px !important; */
	}
	.individualcourserow .span2 {
		padding: 20px;
	}
	.individualcourserow .span2 .courselogo {
		width: 20%;
		height: 150px;
		float: left;
		margin-right: 26px;
	}
	.coursepreviewbtn {
		margin-top: -6px;
	}
	.description {
		overflow: hidden;
		text-overflow: ellipsis;
		margin-top: -5px;
	}
}

@media ( max-width : 480px) {
	.individualcourserow .span2 .courselogo {
		width: 35%;
		margin-right: 26px;
		margin-left: -10px;
	}
	.desc {
		font-size: 0px;
	}
	.description {
		margin-top: -20px;
	}
	.panel-size {
		width: 100% !important;
		margin-left: 0px !important;
	}
	.individualcourserow {
		height: 190px;
		width: 100%;
		/* margin-left: 20px !important; */
	}
}

@media ( max-width : 530px) {
	.individualcourserow {
		width: 100%;
		margin-left: 0px !important;
	}
}
</style>

</head>
<body style="background: rgb(240, 240, 240) !important;">

	<div class="container-fluid">
		<div class="row-fluid pagestart">
			<div class="span12 contentbackgound">
				<div class="span3 leftpane">
					<ul class="leftsideul">
						<form name="previewguestform" method="post">
							<input type="hidden" name="keyword" value="">
							<li class="leftsideli searchcoursetext"><input type="text"
								class="searchbox"
								placeholder="search course...(eg. lean system design)"><span
								class="pull-right searchbtn" style="padding: 10px;"><i
									class="icon-search"></i></span></li>
						</form>
						<li class="leftsideli leftsideheader"><h3
								class="categorytitle">Categories</h3></li>
						<ul class="leftsideul" id="ecloudcoursestable"></ul>
					</ul>
				</div>
				<div class="span9 rightpane">
					<div class="span12">
						<div id="ecloudcourseswithdesctable" class="tab-pane active"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/guest/ecloudindex.js"
		type="text/javascript"></script>
</body>
</html>