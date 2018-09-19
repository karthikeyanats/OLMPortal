<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:text name="label.productname"></s:text></title>
<script type="text/x-handlebars-template" id="post-template"> 
{{#each .}}
<div class="row whitebg">
	<div class="col-md-12">
		<div class="col-md-4 pad0">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{postBannerPath}}" 
				class="width100 height200 martop10">
		</div>
		<div class="col-md-8">
			<h3 class="cornflowerblue borderbottom1 padbottom10 postitle">{{postTitle}}</h3>
			<h5 class="martop20"><i class="fa fa-user"></i> by <a>{{author}}</a></h5>
			<p class="martop30"><i class="fa fa-calendar"></i> Posted on {{postDate}}</p>
			<p class="martop30">
				<i class="fa fa-tags"></i> Tags:
				{{#each tags}}
					{{#each tagName}}
						<a class="marright5"><span class="badge badge-info pad612">{{this}}</span></a>
					{{/each}}
				{{/each}}
			</p>
		</div>
	</div>
</div>
<div class="row whitebg padtop20">
	<div class="col-md-12">
		<p>{{postMedia}}</p>
		<p>{{{postContent}}}</p>
	</div>
</div>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="recentpost-template">
<li class="leftsideliheader">
	<h3>Popular Posts</h3>
</li>
{{#each .}}
	 <li class="li leftsideli">
		<form id="blogform" name="blogform" method="post" action="blogs">
			<h5 class="recentPost"><a><i class="fa fa-angle-double-right marright5"></i>{{title}}</a></h5>
			<h6 class="lightbrown">Posted On : <font class="marleft7">{{dateParser date}}</font></h6>
			<input type="hidden" name="blogid" value="{{id}}">
 		</form>
	</li>
{{else}}
  <li class="leftsideli">No Posts</li>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="comments-template">
<ul class='comments pad0 blog-comment marbottom10'>
	{{#if .}}{{#each.}}
	<li class='clearfix'><img
		src='<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personPhotoURL}}&filetype=photo'
		class='avatar' alt=''>
		<div class='post-comments boxshadow'>
			<p class='meta'>
				On  <b>{{dateParser commentDate}}</b> <a>{{personFirstName}}</a> Commented 
			</p>
			<p>{{commnetText}}</p>
		</div></li>{{/each}}{{else}}
		<div class='boxshadow whitebg pad10 notfound'>
			<p class="centertext lightbrown">
				No Comments Given 
			</p>
		</div>
	{{/if}}
</ul>
</script>
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		String userCheck = ""
				+ SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();		
	%>
	<span class="isUser" userCheck="<%=userCheck%>" style="display: none;"></span>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
			<%if(userCheck.equalsIgnoreCase("anonymousUser")){%>
					<a href="<%=request.getContextPath()%>/blogspage">Blogs</a>  
					<%}else{%>
					<div class="span"><a href="<%=request.getContextPath()%>/app/blogposts">Blogs</a>
					<%}%> / <a class="blotitle"></a>
			</div>
		</div>
	</div></div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-9 marbottom10 boxshadow">
					<div id="posts"></div>
					<div class="col-md-12 pad0 martop10" id="commenddiv">
						<h4 class="cornflowerblue">
							Comments :
							<%
								if (userCheck.equalsIgnoreCase("anonymousUser")) {
							%>
							<%
												} else {
											%>
							<a href="javascript:void(0)" class="comments pull-right "
								data-toggle="tooltip" title="click to enter comment"><i
								class="fa fa-comment " style=""></i> Leave Comments</a>
							<div class="commenttext form-horizontal whitebg pad10 martop10"
								style="display: none;">
								<div
									class="alert alert-success 
									commentallertsuccess input-block-level size12"
									style="display: none;">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									Successfully commented
								</div>
								<div class="form-group">
									<label class="control-label"></label>
									<div class="col-sm-12">
										<input type="text" placeholder="Name" id="commentedname"
											class="form-control" readonly style="background: white;">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label"></label>
									<div class="col-sm-12">
										<textarea rows="10" cols="80" class="comtext form-control"
											placeholder="Comment"></textarea>
											<p class="help-text martop7" style="color:red;font-size:15px;"><font class="chrem">500</font> Characters Remaining</p>
										<div class="alert alert-danger commentallert  size12 "
											style="display: none;">Please Enter The Comment</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label"></label>
									<div class="col-sm-12">
										<a class="btn btn-success leave-comment"
											href="javascript:void(0)">Post Comment</a>
										<button type="button" class="resetcomment btn btn-danger"
											aria-hidden="true">Reset</button>
									</div>
								</div>
							</div>
							<%
												}
											%>
						</h4>
						<div class="comments-sub" id="comments-sub"></div>
					</div>
				</div>
				<div class="col-md-3 padright0">
					<ul class="item-list pad0" id="recent"></ul>
				</div>
			</div>
		</div>
	</div>
	
	<form method="post">
		<s:hidden name="blogid" value="%{blogid}" id="blogid"></s:hidden>
	</form>
	
	<script
		src="<%=request.getContextPath()%>/assets/app/js/blog/blog-single.js"></script>

</body>
</html>