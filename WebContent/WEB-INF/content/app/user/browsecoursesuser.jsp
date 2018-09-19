<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script id="categoriestmpl" type="text/x-handlebars-template">
{{#each .}}
<li class="leftsideli hover leftsidelink" coursecategoryname="{{coursecategoryname}}" 
coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}
<span class="pull-right count draftcoursecount">{{coursescount}}</span></li>
{{/each}}
</script>
<script id="nocoursestmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle"></h3>
<div class="row-fluid">

	<div class="hero-unit" style="margin-bottom: 0px !important;background: #fff;">
         <h2>No Courses Found!</h2>
 	</div>

</div>
</script>
<script id="coursestmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle">
	<font class="catname"></font> - Courses <span
		class="btn-group pull-right"> <a href="#learningcoursegridview"
		data-toggle="tab"
		class="active btn btn-default btn-sm learningcoursestab"><i
			class="icon-th"></i></a> <a href="#learningcourslistview"
		data-toggle="tab" class="btn btn-default btn-sm learningcoursestab"><i
			class="icon-th-list"> </i></a>
	</span>
	<div class="btn-group pull-right" style="padding-right:5px;">
		<button class="btn btn-small dropdown-toggle" data-toggle="dropdown" style="padding: 5px;font-size: 13px;text-transform: uppercase;
				font-weight: bold;padding-right: 10px;">
			Filter By <span class="caret"></span>
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
</h3>
<div class="tab-content">
	<div class="tab-pane active" id="learningcoursegridview"
		style="padding-top: 15px;">
		{{#if catcourses}} {{#each catcourses}} {{#each courses}}
		<div class="span3"
			style="margin-left: 5px !important; margin-right: 5px;">
			<div class="row-fluid individualcourserow">
				<div class="span12">
					<a class="btn btn-flat btn-blue wishbtn" courseid="{{courseid}}"
						courseenrollmentstatus="{{courseenrollmentstatus}}"
						style="position: absolute; top: 0px; left: auto; right: 0; text-align: right; display: none; margin: 6px;"></a>
					<img class="courselogo"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="span12" style="margin: 0px; padding: 5px">
					<h3 class="coursetitle" title="{{coursetitle}}"
						style="text-overflow: ellipsis !important; white-space: nowrap !important; overflow: hidden !important;">{{coursetitle}}</h3>
					<p class="description">By {{firstName}} {{lastName}}</p>
				</div>
				<div class="span12" style="margin-left: 0px;margin-bottom:10px;text-align:center;">
					<span class="ratingcourse" rating="{{rating}}" style="width: 100px;"></span>				
				</div>				
				<div class="span12" style="margin-bottom:10px;">
					<div class="span6" style=""><a class="label userscountbtn" style="padding: 5px;" data-toggle="tooltip" title="Enrolled">
						<i class="icon-user"></i>&nbsp;&nbsp;{{coursesubscribers}}&nbsp;&nbsp;
					</a></div>
					<div class="span6" style="text-align:right;margin-left:0px;padding-right: 5px;">
						<a class="label icon_price" price="{{price}}" style="padding: 5px;"
							priceicon ="<s:text name="label.price" ></s:text>"></a>				
					</div>
				</div>
				<div class="span12" style="margin-left: 0px;">
					<a class="btn btn-flat btn-gray startlearninggridbtn span12"
						courseid={{courseid}}
						courseenrollmentstatus="{{courseenrollmentstatus}}"
						courseenrollmentid="{{courseenrollmentid}}"> <i
						class="fa fa-play-circle"></i>&nbsp;Start Course
					</a>
				</div>
			</div>
		</div>
		{{/each}} {{/each}} {{else}}
		<div class="row-fluid individualcourserow" style="min-height: 450px;">
			<p class="center categorydescription nocoursetxt">No courses</p>
		</div>
		{{/if}}
	</div>
	<div class="tab-pane" id="learningcourslistview"
		style="padding: 15px; padding-left: 0px;">
		{{#if catcourses}} {{#each catcourses}} {{#each courses}}
		<div class="row-fluid individualcourserow">
			<div class="span12">
				<div class="span2">
					<img class="courselogo"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="span10">
					<div class="row-fluid">
						<div class="span12" style="margin-bottom: 30px;">
							<div class="span8 style="margin-left:0px;">
								<h3 class="coursetitle">{{coursetitle}}</h3>
								<p class="description">By {{firstName}} {{lastName}}</p>
							</div>
							<div class="span4" style="margin-top: 10px;text-align: right;padding-right: 10px;">
								<a class="btn btn-flat btn-gray startlearninggridbtn"
									courseid={{courseid}}
									courseenrollmentstatus="{{courseenrollmentstatus}}"
									courseenrollmentid="{{courseenrollmentid}}"><i
									class="fa fa-play-circle"></i>&nbsp;Start Course</a>
							</div>
						</div>
					<div class="span12" style="margin-bottom:10px;margin-left:0px;">
						<div class="span4" style=""><a class="label userscountbtn" style="padding: 5px;">
							<i class="icon-user"></i>&nbsp;&nbsp;{{coursesubscribers}}&nbsp;&nbsp;Enrolled
						</a>
					</div>
					<div class="span4" style="padding-top: 7px;">
						<span class="ratingcourse" rating="{{rating}}" style="width: 100px;"></span>
					</div>
					<div class="span4" style="text-align:right;margin-left:0px;padding-right: 10px;">
						<a class="label icon_price" price="{{price}}" style="padding: 5px;" 
							priceicon ="<s:text name="label.price" ></s:text>"></a>				
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	{{/each}} {{/each}} {{else}}
	<div class="row-fluid individualcourserow" style="min-height: 450px;">
		<p class="center categorydescription nocoursetxt">No courses</p>
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
	<form action="searchresult" method="post" name="searchresultform">
		<input type="hidden" name="keyword" value="">
	</form>
	<div class="span3 leftpane">
		<ul class="leftsideul">
			<form name="previewform" method="post">
			<input type="hidden" name="keyword" value="">
				<input type="hidden" name="courseid" value="">
				<li class="leftsideli searchcoursetext"><input type="text"
					placeholder="search course...(eg. lean system design)"
					class="searchbox"><span class="pull-right searchbtn"
					style="padding: 10px;"><i class="icon-search"></i></span></li>
			</form>
			<li class="leftsideli leftsideheader"><h3 class="categorytitle">Categories</h3></li>
			<ul class="leftsideul" id="categoriestable"></ul>
		</ul>
	</div>
	<div class="span9 rightpane">
		<div class="span12">
			<div id="righttable" class="tab-pane active"></div>
		</div>
	</div>
	<div class="modal hide" id="subscribedmodal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3>Message</h3>
		</div>
		<div class="modal-body">
			<p class="categorydescription ">
				<font class="enrolstatus"> You have already this course </font>
			</p>
		</div>
		<div class="modal-footer">
			<a class="btn btn-flat btn-blue" data-dismiss="modal"
				aria-hidden="true">Close</a>
		</div>
	</div>

	<div class="modal hide" id="wishmodal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3>Message</h3>
		</div>
		<div class="modal-body">
			<p class="categorydescription ">You have added this course to
				wishlist</p>
		</div>
		<div class="modal-footer">
			<a class="btn btn-flat btn-blue wishclose" data-dismiss="modal"
				aria-hidden="true">Close</a>
		</div>
	</div>

	<form name="browseform" method="post">
		<input type="hidden" name="courseid" value=""> <input
			type="hidden" name="courseenrollmentid" value=""> <input
			type="hidden" name="wishlisted" value="">
	</form>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/browsecourseuser.js"
		type="text/javascript"></script>
</body>
</html>