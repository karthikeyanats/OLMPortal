<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Blog Post</title>
<script type="text/x-handlebars-template" id="blogPostTemplate">  
{{#each .}}
<div class="row">
	<div class="col-md-12 whitebg marbottom10 pad0 boxshadow">
		<div class="col-md-2 pad5 boxshadow">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{bannerpath}}" 
				alt="post image" class="width100 height200">
		</div>
		<div class="col-md-10">
			<h4 class="cornflowerblue">{{title}}<span class="pull-right">{{{assertUserAndAddOptions}}}</span></h4>
			<h5 class="lightbrown"><i class="fa fa-user"></i> by <a class="lightbrown">{{orgpersonFirstName}} {{orgpersonMiddleName}} {{orgpersonLastName}}</a>
			</h5>
			<p class="min95">{{media}}</p>
			<p><font class="marright7 lightbrown"><i class="fa fa-calendar"></i> Posted on {{dateParser date}}</font>
				<a class="btn btn-blue white readmore pull-right" blid="{{id}}"
				value="Read More">Read More</a> 
			</p>
		</div>
	</div>
</div>
{{/each}}
</script>
<script id="nodatatmpl" type="text/x-handlebars-template">
    <div class="alert alert-danger bs-alert-old-docs" style="margin-top: 25px;">
      <strong>Sorry !</strong> No Data found!
    </div>
</script>
</head>
<body>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
			userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String roleName = userDetailsAdaptor.getUser().getProperty(
					"rolename");
			String orgstatus = userDetailsAdaptor.getUser().getProperty(
					"orgstatus");
					%>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<%
					if (! roleName.equals("user")) {
				%>
				<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
				</a> / <a>Blogs </a>
				<%
					if (! roleName.equals("user")) {
				%>
				<a href="<%=request.getContextPath()%>/app/blog"
					class="btn btn-blue white pull-right martop3 ligtgreenbg"><i
					class="fa fa-plus marright7" style="color:white;"></i><span style="color:white;">New blog post</span></a>

				<%	}					
				} else{
				%>
				<a href="<%=request.getContextPath()%>/app/mycourses">My Courses
				</a> / <a>Blogs </a>
				<%						
				} 
				%>
			</div>
		</div>
	</div>
	<div class="alert alert-success alerter" style="display: none;">
		<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Blog!</strong>
		Successfully deleted!
	</div>
	<div class="alert alert-danger alerterror" style="display: none">
		<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Blog!</strong>
		Some thing went Wrong!
	</div>
	<div class="container-fluid" id="blogPostContent"></div>
	<form id="blogform" name="blogform" method="post" action="blogs">
		<input type="hidden" name="blogid" value="" />
	</form>

	<!-- JAVASCRIPT -->

	<script type="text/javascript">
		USER_ID = Ajax.ajaxHelper("GET", courseData.getContextPath()
				+ "/rest/blogpost/postroll/user", undefined, "json",
				"application/json");

		$(document).ready(function() {
			postlist();
			clickeve();
			
			$(window).load(function() {
				var $element = $('#entry-content').find('p');
				var length = $element.length;
				var count = 0;
				$element.each(function() {
					$eElement = $($element);
					if ($eElement.is('p') & count < length) {

					} else if ($eElement.is('h')) {

					}
					count++;
				});
			});
		});

		/* click function */
		function clickeve() {
			$('.readmore').click(function() {
				$('[name=blogid]').val($(this).attr('blid'));
				$('[name=blogform]').submit();
			});
		}

		/*post list*/
		function postlist() {
			var wholepost = Ajax.ajaxHelper("GET", courseData.getContextPath()
					+ "/rest/blogpost", undefined, "json", "application/json");
			Handlebars.registerHelper('dateParser', function(date) {
				return new Date(date).toDateString();
			});
			Handlebars.registerHelper("assertUserAndAddOptions",function(option) {
				var _orgpersonId = this.authororgpersonid;
				var editDeleteOptionTemplate = '<span class="pull-right" style=""><form id="blog-update-form" name="blog-update-form" method="post" action="<%= request.getContextPath() %>/app/blogupdate" style="display: inline;margin-right: 10px;"><a href="javascript:void(0);" id="'+this.id +'" class=" btn btn-primary blog-post-edit blog-post-sub"><i class="fa fa-pencil"></i></a><input type="hidden" name="blogid" value="'+ this.id +'"/></form><a href="javascript:void(0);" id="'+this.id +'" class="btn  btn-danger blog-post-delete blog-post-sub"><i class="fa fa-trash-o"></i></a></span>';
				if (USER_ID === _orgpersonId){
					return editDeleteOptionTemplate;
				}
			});
			Handlebars.registerHelper('trimString', function(passedString) {
			    var theString = passedString.substring(0,500)+"..";
			    return new Handlebars.SafeString(theString);
			});
			if(!(wholepost === "NO_CONTENT")){
				renderTemplate("#blogPostTemplate", wholepost, "#blogPostContent");
				editRedirect();
			}else if(wholepost === "NO_CONTENT"){
				renderTemplate("#nodatatmpl", wholepost, "#blogPostContent");
			}
			
			delpost();
			clickeve();
		}
		
		/* DELETE post */
		function delpost(){
			$(".blog-post-delete").click(function(){
				var id= $(this).attr('id');
				if(confirm('Are you sure you want to delete this blog post ?')){
					poststatus =Ajax.ajaxHelper("DELETE",courseData.getContextPath()+"/rest/blogpost/"+id, undefined,"json","application/json");
					if(poststatus == "ACCEPTED"){
						window.scrollTo(0,0);
						$(".alerter").fadeIn('slow').delay(5000).fadeOut('slow');
						postlist();
					}	
					else{
						window.scrollTo(0,0);
						$(".alerterror").fadeIn('slow').delay(5000).fadeOut('slow');
					} postlist();
				}
			});
		};
		
		function editRedirect(){
			$('.blog-post-edit').click(function(){
				var _blogId= $(this).attr('id');
				 $(this).parent().submit(); 
			});
		};
	</script>
</body>
</html>