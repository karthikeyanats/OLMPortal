<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="<%=request.getContextPath()%>/assets/newtheme/font/Montserrat.css"
	rel="stylesheet" type="text/css">
<style type="text/css">
body {
	font-family: "Montserrat" !important;
}

.courselink {
	font-size: 10px;
}

.courseslinks {
	text-overflow: ellipsis !important;
	white-space: nowrap !important;
	overflow: hidden !important;
}
/* .courselink .comma:last-child {
	display: none !important;
} */
</style>
<script id="boardcategoriestmpl" type="text/x-handlebars-template">
{{#each .}}
<div
	style="background: white; padding: 15px; margin-bottom: 10px; margin-top: 10px;">
	<h4 boardid="{{boardid}}"
		style="border-bottom: 1px solid #eeeeee; margin-top: 5px; text-transform: uppercase;">
		{{boardname}} <span class=""
			style="font-weight: normal; font-size: 15px; text-transform: lowercase;">
			- {{categorycount}} categories<span class="boardlink pull-right" boardid="{{boardid}}"><i class="icon-hand-right"></i></span></span>
	</h4>
	<div class="row-fluid">
		{{#each categories}}
		<div class="span3 well" style="padding: 5px; padding-top: 0px;">
			<h5 coursecategoryid="{{coursecategoryid}}"
				style="margin: 7px 0px; text-transform: uppercase;">
				{{coursecategoryname}}<span class="categorylink pull-right" boardid="{{boardid}}" coursecategoryid="{{coursecategoryid}}"><i class="icon-hand-right"></i></span>
			</h5>
			<img class="courselogo" style="height: 100px;"
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo"
				alt="logo"><br />
			<div class="" style="margin-top: 5px;">
				<p class="courseslinks">
					{{#each courses}} <a class="courselink" courseid="{{courseid}}">{{coursetitle}}<font
						class="comma">,</font>
					</a> {{/each}}
				</p>
			</div>
		</div>
		{{/each}}
	</div>
</div>
{{/each}}	
</script>
</head>
<body>
	<div class="container">
		<div id="boardcategoriestable"></div>
	</div>
	<form name="categoryform" method="post">
		<input type="hidden" name="boardid" value=""> <input
			type="hidden" name="courseidid" value=""> <input
			type="hidden" name="coursecategoryid" value="">
	</form>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/categories.js"></script>

</body>
</html>