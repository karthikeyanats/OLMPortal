<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="<%=request.getContextPath()%>/assets/plugins/summernote/summernote.css"
	rel="stylesheet">
<style type="text/css">
.note-editor button {
	height: 31px;
	width: 44px;
}

.note-editor {
	border: 1px solid #a9a9a9;
	width: 100%;
	height: 25%;
}

.loginfield {
	width: 228px;
}

.blogEntry {
	background-color: #fff;
}

.comment .btn.active,.comment .btn:active {
	background-color: #6ac1e3 !important;
	color: #fff;
}

.note-insert {
	display: none;
}

.bannerImageGrid {
	list-style-type: none;
}

.bannerImageGrid li img {
	float: left;
	margin: 10px;
	border: 5px solid #fff;
	-webkit-transition: box-shadow 0.5s ease;
	-moz-transition: box-shadow 0.5s ease;
	-o-transition: box-shadow 0.5s ease;
	-ms-transition: box-shadow 0.5s ease;
	transition: box-shadow 0.5s ease;
}

.bannerImageGrid li img:hover {
	-webkit-box-shadow: 0px 0px 7px rgba(255, 255, 255, 0.9);
	box-shadow: 0px 0px 7px rgba(255, 255, 255, 0.9);
}

.note-fontname {
	display: none !important;
}

textarea {
	resize: none;
	overflow: auto;
}
</style>
<script id="categorytempl" type="text/x-handlebars-template">

<thead>
      		<th>Category Name</th>
      		<th>Category Description</th>
      		<th>Edit</th>
			<th>Delete</th>
    	</thead>
    	
		<tbody>
      			{{#each .}}
				<tr>
          			<td class="name">{{name}}</td>
          			<td>{{description}}</td>
          			{{{assertCatUser}}}
        		</tr>
				{{/each}}
      	</tbody>

</script>
<script id="selectLoader" type="text/x-handlebars-template">

<select multiple="" class="form-control">
 	{{#each .}}
    <option value= {{id}} >{{name}}</option>
	{{/each}}
</select>

</script>
<script id="tagtempl" type="text/x-handlebars-template">

<thead>
      		<th>Tag Name</th>
      		<th>Tag Description</th>
      		<th>Edit</th>
			<th>Delete</th>
    	</thead>
    	
		<tbody>
      			{{#each .}}
				<tr>
          			<td class="name">{{name}}</td>
          			<td>{{description}}</td>
          			{{{assertTagUser}}}
        		</tr>
				{{/each}}
      	</tbody>

</script>
<script id="skilltempl" type="text/x-handlebars-template">

<thead>
      		<th>Skill Name</th>
      		<th>Skill Description</th>
      		<th>Edit</th>
			<th>Delete</th>
    	</thead>
    	
		<tbody>
      			{{#each .}}
				<tr>
          			<td class="name">{{name}}</td>
          			<td>{{description}}</td>
          			 {{{assertSkillUser}}}
        		</tr>
				{{/each}}
      	</tbody>

</script>
<script id="bannerGridListTemplate" type="text/x-handlebars-template">
<ul class="bannerImageGrid"> 
	{{#each .}}
		<li>
			<img data-src="holder.js/140x140" class="img-rounded" alt="140x140" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIwAAACMCAYAAACuwEE+AAAFJklEQVR4Xu3Y2yulfxTH8eUUVw43pCTlEModLoSIPxsp5XTFCJEklBwTF3Kevt8yjf3bP/Ya0xr70/upaTJ7TetZn/Wa5/vsqbi6uno1LhIoMYEKwJSYFGU5AcAAwZUAYFxxUQwYDLgSAIwrLooBgwFXAoBxxUUxYDDgSgAwrrgoBgwGXAkAxhUXxYDBgCsBwLjiohgwGHAlABhXXBQDBgOuBADjiotiwGDAlQBgXHFRDBgMuBIAjCsuigGDAVcCgHHFRTFgMOBKADCuuCgGDAZcCQDGFRfFgMGAKwHAuOKiGDAYcCUAGFdcFAMGA64EAOOKi2LAYMCVAGBccVEMGAy4EgCMKy6KAYMBVwKAccVFMWAw4EoAMK64KAYMBlwJAMYVF8WAwYArAcC44qIYMBhwJQAYV1wUAwYDrgQA44qLYsBgwJUAYFxxUQwYDLgSAIwrLooBgwFXAoBxxUUxYDDgSgAwrrgoBgwGXAkAxhUXxYDBgCsBwLjiohgwGHAlABhXXBQDBgOuBMoazMnJiW1ubuaBp6en/zP4/v6+pV+/f/7y8mI7Ozt2cXFhr6+v1tzcbD09PVZZWflpcNH9Pr2hf1BQtmDSwu/u7vLyi4F5fn62xcVFe3h4yDDeQB0cHNje3p719/fnv7e1tZXBtLe3fxh/dL9/YKGklmUL5vDwMC95ZmamKJjj42O7vLy08/Pzd5+vrKzY7e2tjY+PZ0gLCwtWX19vQ0NDtrS0lP9sZGTE3sDV1dXlz46Ojv5qv+Hh4ZIW9N2KyhbMW5DFwKSlp+V3d3fb+vr6OzDz8/P29PRkU1NTGcfc3JxVVVXZ5OSknZ6e2sbGhg0MDNj19XVGMjg4aI2Njb/29jf7fTcMpdyPJJizs7N8VI2Ojtrs7Ow7MIULTz9XVFRkQOlKT6DHx0e7v7+31tbWX0fXR0C/0q+UJX2nGkkwq6ur1tTUlJ8whUCKPWGqq6ttYmIi7yUt/8ePHxlRAldbW/tuX8WeMF/p950wlHIvkmDelloYwNjYWD6ibm5u3r3DNDQ05PeUdKUjKb33pOOqs7PTOjo6PgXzlX6lLOk71UiC+T3gwidCelne3d21vr6+XLa9vW29vb3W1taWv2qvra1ZV1dXfulN36gSpPRS/NGR9Kf9vhOEUu+lbMH837/qwv+PKQSTnhzp/SYdPenYaWlpyUdXArK8vGw1NTWWvsGkuvQ+k35PP6ejrNj1p/1S73K8yhZMOYatcM+AUdhi4AyACQxboRVgFLYYOANgAsNWaAUYhS0GzgCYwLAVWgFGYYuBMwAmMGyFVoBR2GLgDIAJDFuhFWAUthg4A2ACw1ZoBRiFLQbOAJjAsBVaAUZhi4EzACYwbIVWgFHYYuAMgAkMW6EVYBS2GDgDYALDVmgFGIUtBs4AmMCwFVoBRmGLgTMAJjBshVaAUdhi4AyACQxboRVgFLYYOANgAsNWaAUYhS0GzgCYwLAVWgFGYYuBMwAmMGyFVoBR2GLgDIAJDFuhFWAUthg4A2ACw1ZoBRiFLQbOAJjAsBVaAUZhi4EzACYwbIVWgFHYYuAMgAkMW6EVYBS2GDgDYALDVmgFGIUtBs4AmMCwFVoBRmGLgTMAJjBshVaAUdhi4AyACQxboRVgFLYYOANgAsNWaAUYhS0GzgCYwLAVWgFGYYuBMwAmMGyFVoBR2GLgDIAJDFuhFWAUthg4A2ACw1ZoBRiFLQbOAJjAsBVaAUZhi4EzACYwbIVWgFHYYuAMPwH8cN2mTmL0hgAAAABJRU5ErkJggg==" style="width: 140px; height: 140px;">
		</li>			
	{{/each}}			
</ul>
</script>
<script id="nodataSelecttmpl" type="text/x-handlebars-template">
<select multiple="" class="form-control">
    <option value="0" disabled>No data found</option> 
</select>
</script>
<script id="nodatatmpl" type="text/x-handlebars-template">
    <div class="alert alert-danger bs-alert-old-docs" style="margin-top: 25px;">
      <p><strong>Sorry !</strong> No Data found!</p>
    </div>
</script>
</head>
<body>
	<!-- alert message -->
	<div class="alert alert-success alerter" style="display: none;">
		<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Post</strong>
		Successfully Inserted!
	</div>

	<!-- alert message -->
	<div class="alert alert-danger upload-alerter" style="display: none;">
		<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Insert
			failed, Check uploaded file name.</strong>Name should be alphanumeric with 100
		chars length , Special characters allowed : "-".
	</div>
	<!--/ alert -->
	<!-- alert message -->
	<div class="container-fluid blogEntry marbottom20">
		<div class="col-md-12" id="blogEntry">
			<div class="bloghead">
				<h3 class="cornflowerblue">
					Add Blog Post
					<div class="navbutton pull-right">
						<a href="<%=request.getContextPath()%>/app/blogposts"
							class=" btn btn-flat btn btn-success" id="">Back to Blogs</a> <a
							href="#moreinfomodal" data-toggle="modal"
							class="btn btn-flat btn btn-info" id="">Category</a> <a
							href="#tagmodal" data-toggle="modal"
							class="btn btn-flat btn btn-info" id="">Tag</a> <a href="#skill"
							data-toggle="modal" class="btn btn-flat btn btn-info" id="">Skill</a>
					</div>
				</h3>
			</div>
			<hr>

			<!-- <form action="" method="" class="blogform"> -->
			<div class="col-md-12">
				<section id="blog-title" class="blog-options-section">
					<div class="row">
						<div class="col-md-12">
							<fieldset>
								<legend>Title Of the Post</legend>
							</fieldset>
						</div>
					</div>
				</section>
				<div class="">
					<input class="input-xlarge form-control" id="focusedInput"
						type="text" placeholder="Enter Post title" style="width: 100%;"
						maxlength="235">
					<div class="alert alert-danger addtitle" style="display: none;">Please
						Enter Title</div>
					<div class="alert alert-danger title-length" style="display: none;">Title
						length cannot be greater than 235 characters</div>
					<div class="alert alert-danger title-duplicate"
						style="display: none;">Title already exists! Please add a
						new title or use existing one.</div>
				</div>

				<div>
					<section id="blog-options-section"
						class="blog-options-section martop10">
						<!-- //category skill and tags multiple selectors -->
						<div class="row">
							<div class="col-md-4">
								<fieldset>
									<legend>Post Category</legend>
									<!-- <label>Select a Catgory/ Multiple Categories</label> -->
									<div id="categorySpace"></div>
									<div class="alert alert-danger addselect"
										style="display: none;">Please Select category</div>

								</fieldset>
							</div>
							<div class="col-md-4">
								<fieldset>
									<legend>Post Tags</legend>
									<!-- <label>Select a Tag/ Multiple Categories</label> -->
									<div id="tagspace"></div>
									<div class="alert alert-danger addselectpost"
										style="display: none;">Please Select Post</div>
								</fieldset>
							</div>
							<div class="col-md-4">
								<fieldset>
									<legend>Post Skills</legend>
									<!-- <label>Select a Skill/ Multiple Categories</label> -->
									<div id="skillpace"></div>
									<div class="alert alert-danger addselectskill"
										style="display: none;">Please Select Skill</div>
								</fieldset>
							</div>
						</div>
					</section>
					<!-- banner path  -->
					<section class="martop10">
						<fieldset>
							<legend>Add a banner Path</legend>
							<form class="form-horizontal">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="controls col-md-12">
												<input type="file" name="file" class="blogpost-media-file  "
													id="blogpost-media-file" accept="image/*"
													onchange="filechange()"><br> <a
													class="btn btn-danger clearFile martop10"
													style="display: none;">Clear</a> <span
													class="icon-remove-sign tool-tip" id="qst-remove"
													style="display: none" data-toggle="tooltip"
													title="Remove Selected Attachment"></span> <small
													style="color: chocolate;"> Acceptable only BMP,
													JPG, JPEG , PNG ,GIF</small> <span class="upload-status pull-right"
													style="color: brown; padding-top: 10px; padding-right: 33px; display: none;">Please
													wait uploading...</span>
												<progress id="progressBar" class="upload-status" max="100"
													value="0"
													style="height: 4px; width: 96%; vertical-align: text-top; display: none;"></progress>
												<div class="alert alert-danger file-upload-error"
													style="display: none;">Please choose a file to
													upload.</div>
												<div class="alert alert-danger file-upload-validate-error"
													style="display: none;">Please choose a valid file to
													upload. Check file name and extension.</div>
											</div>
											<div class="col-md-2"></div>
										</div>
									</div>
								</div>
							</form>
						</fieldset>

						<input class="input-xlarge" id="uploadInput" type="hidden"
							placeholder="upload File To Get Url" style="width: 100%;"
							maxlength="235">

					</section>

					<section class="martop10">
						<fieldset>
							<legend>Description</legend>
							<form class="form-horizontal">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="controls col-md-12">
												<textarea rows="" cols="" class="form-control postdescval"></textarea>
												<p class="help-text">
													<font class="postdescvalrem"> 500</font> Characters
													Remaining
												</p>
												<div class="alert alert-danger descriptext"
													style="display: none;">Please Enter Description</div>
											</div>
											<div class="col-md-2"></div>
										</div>
									</div>
								</div>
							</form>
						</fieldset>
					</section>
					<legend>Content</legend>
					<textarea rows="" cols="" placeholder="Enter Blog Message"
						id="summernote" class="summernote"
						style="height: 200px !important; resize: none;"></textarea>
					<div class="alert alert-danger descrip" style="display: none;">Please
						Enter Content</div>
					<small style="color: chocolate;">NOTE : DO NOT SELECT FONT
						COLOR SAME AS BACKGROUND COLOR</small><small> (Eg: red)</small> <br>
				</div>
				<br> <br> <span class="size20 marright7">Commentable
					:</span>
				<div class="btn-group comment">
					<button type="button" class="btn btn-default group active">Yes</button>
					<button type="button" class="btn btn-default group">No</button>

				</div>
				<div class="alert alert-danger commend" style="display: none;">Please
					select commentable</div>
				<br> <br>
				<div class="create_post lefttext">
					<button type="button" class="btn btn-success marbottom10" id="post">Submit
						post</button>
				</div>
			</div>
			<!-- </form> -->
		</div>


	</div>

	<!-- category modal -->
	<div id="moreinfomodal" class="modal fade">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>New Category</h3>
				</div>
				<div class="modal-body">
					<div class="alert alert-success categoryalert"
						style="display: none;">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Category
						</strong>successfully deleted!
					</div>
					<!-- / -->
					<div class="alert alert-danger post-alerter category-post-alert"
						style="display: none;">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Category
							cannot be deleted </strong>, Post(s) available under this category. Delete
						all post(s) under this category to delete this category.
					</div>
					<div
						class="alert alert-success categoryallertsuccess input-block-level"
						style="display: none;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Category Successfully Inserted
					</div>

					<div
						class="alert alert-success categoryupdatesuccess input-block-level"
						style="display: none;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Category Successfully Updated
					</div>
					<!-- 	<form class="form-horizontal" action="" method=""> -->
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-4">Name : </label>
							<div class="controls col-sm-8">
								<input type="text" id="tagname" maxlength="235"
									placeholder="Category name" class="form-control">
								<div class="alert alert-danger alertname" style="display: none;">Please
									Enter Category Name</div>
								<div class="alert alert-danger category-name-alert"
									style="display: none;">Category Name cannot be more than
									235 characters</div>
								<div class="alert alert-danger category-duplicate-alert"
									style="display: none;">Category already exists! Please
									add a new category or use existing</div>
								<div class="alert alert-danger category-update-duplicate-alert"
									style="display: none;">Category already exists! Please
									add a new category</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="desc">
								Description : </label>
							<div class="controls col-sm-8">
								<textarea class="form-control" id="description"
									placeholder="Category description"></textarea>
								<p class="help-block">
									<font class="desccharremaining">500</font> Characters Remaining
								</p>
								<div class="alert alert-danger alertdescription"
									style="display: none;">Please Enter Category Description</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="desc"></label>
							<div class="controls col-sm-8">
								<input type="button" class="btn btn-success submittag"
									id="addcategory" value="Submit"> <input type="button"
									class="btn btn-danger reset" value="Reset">
							</div>
						</div>
					</div>
					<!-- 	</form> -->
					<hr>
					<h4>Existing Category</h4>
					<table class="table table-striped" id="category-list"></table>
				</div>
			</div>
		</div>
	</div>
	<!-- Tag Modal -->
	<div id="tagmodal" class="modal fade">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>Tag</h3>
				</div>
				<div class="modal-body">
					<div class="alert alert-success post-alerter tagalert"
						style="display: none;">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Tag</strong>
						successfully deleted!
					</div>
					<!-- / -->
					<div class="alert alert-danger post-alerter tag-post-alert"
						style="display: none;">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Tag
							cannot be deleted </strong>, Post(s) available under this tag. Delete all
						post(s) under this tag to delete this tag.
					</div>
					<div class="alert alert-success tagallertsuccess input-block-level"
						style="display: none;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Tag Successfully Inserted
					</div>
					<div class="alert alert-success tagupdatesuccess input-block-level"
						style="display: none;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Tag Successfully Updated
					</div>

					<!-- 	<form class="form-horizontal" action="" method=""> -->
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-4">Tag Name : </label>
							<div class="controls col-sm-6">
								<input type="text" id="tag" maxlength="235" class="form-control"
									placeholder="Tag name">
								<div class="alert alert-danger tagname" style="display: none;">Please
									Enter Tag Name</div>
								<div class="alert alert-danger tag-title-length"
									style="display: none;">Tag name cannot be greater than
									235 characters</div>
								<div class="alert alert-danger tag-title-duplicate"
									style="display: none;">Tag already exists! Please add a
									new tag or use existing</div>
								<div class="alert alert-danger tag-update-duplicate-alert"
									style="display: none;">Tag already exists! Please add a
									new tag.</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="desc">Tag
								Description : </label>
							<div class="controls col-sm-6">
								<textarea id="tagdescription" class="form-control"
									placeholder="description"></textarea>
								<p class="help-block">
									<font class="tagdesccharremaining">500</font> Characters
									Remaining
								</p>
								<div class="alert alert-danger tagdescription"
									style="display: none;">Please Enter Tag Description</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="desc"></label>
							<div class="controls col-sm-6">
								<input type="button" class="btn btn-success tagsubmit"
									id="tagsubmit" value="Submit"> <input type="button"
									class="btn btn-danger reset" value="Reset">
							</div>
						</div>
					</div>
					<!-- 	</form> -->
					<hr>
					<h4>Existing Tag</h4>
					<table class="table table-striped" id="tag-list"></table>
				</div>
			</div>
		</div>

	</div>
	<!-- skill list -->
	<div id="skill" class="modal fade">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<!-- alert -->

				<!-- /alert -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>Skill</h3>
				</div>
				<div class="modal-body">
					<div class="alert alert-success post-alerter skillDeleteAlert"
						style="display: none;">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Skill</strong>
						successfully deleted!
					</div>
					<!-- / -->
					<div class="alert alert-danger post-alerter skill-post-alert"
						style="display: none;">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Skill
							cannot be deleted </strong>, Post(s) available under this skill. Delete
						all post(s) under this skill to delete this skill.
					</div>
					<div
						class="alert alert-success skillallertsuccess input-block-level"
						style="display: none;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Skill Successfully Inserted
					</div>
					<div
						class="alert alert-success skillupdatesuccess input-block-level"
						style="display: none;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Skill Successfully Updated
					</div>

					<!-- 	<form class="form-horizontal" action="" method=""> -->
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-4">Skill Name : </label>
							<div class="col-sm-8">
								<input type="text" id="skillname" maxlength="235"
									placeholder="Skill name" class="form-control">
								<div class="alert alert-danger skillalert"
									style="display: none;">Please Enter Skill Name</div>
								<div class="alert alert-danger skill-name-alert"
									style="display: none;">Skill name cannot be more than 235
									characters</div>
								<div class="alert alert-danger skill-title-duplicate"
									style="display: none;">Skill already exists! Please add a
									new Skill or use existing</div>
								<div class="alert alert-danger skill-update-duplicate-alert"
									style="display: none;">Skill already exists! Please add a
									new skill.</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="desc">Skill
								Description : </label>
							<div class="controls col-sm-8">
								<textarea class="form-control" id="skilldescription"
									placeholder="Skill description"></textarea>
								<p class="help-block">
									<font class="skilldesccharremaining">500</font> Characters
									Remaining
								</p>
								<div class="alert alert-danger skilldescriptionalert"
									style="display: none;">Please Enter Skill description</div>
							</div>
						</div>
						<div class="form-group">
							<div class="controls col-sm-8 col-md-offset-4">
								<input type="button" class="btn btn-success skillsubmit"
									id="skillsubmit" value="Submit"> <input type="button"
									class="btn btn-danger reset" value="Reset">
							</div>
						</div>
					</div>
					<hr>
					<!-- 	</form> -->
					<h4>Existing Skills</h4>
					<table class="table table-striped" id="skill-list">

					</table>

				</div>
			</div>
		</div>
	</div>
	<form name="backblogsform" method="post"
		action="<%=request.getContextPath()%>/app/blogposts"></form>
	<script
		src="<%=request.getContextPath()%>/assets/plugins/summernote/summernote.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/app/js/blog/blog.js"></script>
</body>
</html>