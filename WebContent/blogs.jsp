<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/assets/app/images/iglogo.png">
<title><s:text name="label.productname"></s:text></title>
<script type="text/x-handlebars-template" id="blog-list">
		{{#each .}}
			<div class="row">
	<div class="col-md-12 whitebg marbottom10 pad0 boxshadow">
		<div class="col-md-3 pad5 boxshadow">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{bannerpath}}" 
				alt="post image" class="width100 height200">
		</div>
		<div class="col-md-9">
			<h4 class="cornflowerblue">{{title}}<span class="pull-right">{{{assertUserAndAddOptions}}}</span></h4>
			<h5 class="lightbrown"><i class="fa fa-user"></i> by <a class="lightbrown">{{orgpersonFirstName}} {{orgpersonMiddleName}} {{orgpersonLastName}}</a>
			</h5>
			<p class="blogdesc">{{media}}</p>
			<p><font class="marright7 lightbrown"><i class="fa fa-calendar"></i> Posted on {{dateParser date}}</font>
				<a class="btn btn-blue white readmore pull-right" blid="{{id}}"
				value="Read More">Read More</a> 
			</p>
		</div>
	</div>
</div>
{{else}}
  <div class="row-fluid individualcourserow" style="min-height:496px;padding-top:150px;">
	<div style="text-align:center;font-size:25px;">No Blogs</div>
</div>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="blog-list-recent">
<li class="leftsideliheader">
	<h3>Popular Post</h3>
</li>
{{#each .}}
	 <li class="li leftsideli"><a href="blogs.jsp" class="copyright-color"> {{title}}</a></li>
{{else}}
  <li class="border whitebg pad10">No Courses</li>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="blog-recent">
<li class="leftsideliheader">
	<h3>Popular Post</h3>
</li>
{{#each .}}
<li class="leftsideli"> 
	<a id="{{id}}" class="pop-posts cornflowerblue">
		{{title}} 
	</a> 
</li>
{{else}}
<li class="leftsideli"> No Courses</li>
{{/each}}
<br>
</script>
<script type="text/x-handlebars-template" id="topcourse-template">
<form name="previewform" method="post" action="preview">
<input type="hidden" class="count" name="count">
<input type="hidden" class="courseid" name="courseid">
</form> 
<li class="leftsideliheader">
	<h3>TOP COURSES</h3>
</li>
{{#each .}}
	<li class="leftsideli"><a  courseid="{{courseid}}" count="{{count}}" class="topcourselists cornflowerblue">{{coursetitle}}</a></li>
{{else}}
 		<li class="leftsideli"> No Courses</li>
{{/each}}
<br>
</script>

</head>
<body>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/">Home </a> / <a>Blogs </a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-9" id="blog-posts-space"></div>
				<div class="col-md-3 padright0">
					<ul class="top-courses pad0" id="topcourse-list">
					</ul>
					<ul class="top-courses pad0" id="popular-post">
					</ul>
					<form id="blog-form" name="blogform" method="post" action="blogs">
						<input name="blogid" id="blog-id" value="" type="hidden">
					</form>
				</div>
			</div>
		</div>
	</div>

	<form id="blogform" name="blogform" method="post" action="blogs">
		<input type="hidden" name="blogid" value="" />
	</form>

	<script type="text/javascript">
   	$(document).ready(function(){
   		getAndRenderPosts();
   		topcourselist();
   		$('.readmore').click(function() {
			$('[name=blogid]').val($(this).attr('blid'));
			$('[name=blogform]').submit();
		});
   		$('.pop-posts').click(function(){
   			$(this).closest('form').submit();
	   	}); 
   	   $('.topcourselists').click(function(){
			var courseid=$(this).attr('courseid');	
			var count=$(this).attr('count'); 
			$('[name=courseid]').val(courseid);
			$('[name=count]').val(count); 
			$('[name=previewform]').submit();
		});   	   
   	}); 
   	function getAndRenderPosts(){ 
   		var posts =  Ajax.ajaxHelper("GET",courseData.getContextPath()+"/rest/blogpost", undefined,"json", "application/json");
			Handlebars.registerHelper('dateParser', function(date) {
				return new Date(date);
			});
			Handlebars.registerHelper('trimString', function(passedString) {
				var theString = passedString.substring(0, 1500) + "...";
				return new Handlebars.SafeString(theString);
			});
			renderTemplate("#blog-list", posts, "#blog-posts-space");

			var lastpost = null;
			var lastposts = null;
			if (posts != "NO_CONTENT") {
				lastpost = posts.splice(0, 6);
				lastposts = lastpost.splice(0, 3);
			}
			renderTemplate("#blog-recent", lastpost, "#popular-post");

			renderTemplate("#blog-list-recent", lastposts, "#recentblogs");
			$('.pop-posts').on('click', function() {
				var self = this;
				var $form = $('#blog-form');
				$form.children('#blog-id').val($(self).attr('id'));
				$form.submit();
			});
		}

		function topcourselist() {
			var topcours = courseData.getPopularCourses();
			renderTemplate("#topcourse-template", topcours, "#topcourse-list");
		}
	</script>

</body>
</html>