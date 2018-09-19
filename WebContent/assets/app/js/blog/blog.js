$(document).ready(function(){ 
	categorylist(); 
	tagfulllist();
	skillfulllist();
	renderNewCategoryList();
	renderNewtagList();
	renderNewskillList(); 
	updatePost();  	
	$('.clearFile').click(function(){
		$('#blogpost-media-file').val("");
		$('.clearFile').css('display','none');
	});
	$("#description").keyup(function(){
		el = $(this);
		if(el.val().length >= 501){
			el.val( el.val().substr(0, 500) );
		} else {
			$('.desccharremaining').text(500-el.val().length);
		}
	});

	$("#tagdescription").keyup(function(){
		el = $(this);
		if(el.val().length >= 501){
			el.val( el.val().substr(0, 500) );
		} else {
			$('.tagdesccharremaining').text(500-el.val().length);
		}
	});
	$("#skilldescription").keyup(function(){
		el = $(this);
		if(el.val().length >= 501){
			el.val( el.val().substr(0, 500) );
		} else {
			$('.skilldesccharremaining').text(500-el.val().length);
		}
	});
	
	$('#summernote').summernote({
		height : 200
	});
	$('.note-resizebar').remove();
	$('.note-table').remove();

	$('form').on('submit', function(e) {
		/*  alert($('.summernote').code()); */
	});

	$('.note-resizebar').remove();

	$(".group").click(function() {
		$(".group").removeClass('active').css("background-color", "white");
		$(this).addClass('active').css("background-color","#6ac1e3");
	});

	$('.banner-file-add').click(function() {
		$('.banner-file-add').parent().parent().find('.upload-misc').append(
		'<li class="misc"><input type="file" name="file"  class="banner-file"></li>');
	});

	$('.banner-file-remove').click(function() {
		$('.banner-file-remove').parent().parent().find(
		'.upload-misc').find('.misc:last').remove();
	});		
	$('.postdescval').keyup(function(){
		el = $(this);
		if(el.val().length >= 501){
			el.val( el.val().substr(0, 500) );
		} else {
			$('.postdescvalrem').text(500-el.val().length);
		}
	});
	$('.postdescval').focus(function() {	
		$('.descriptext').hide();
	});
	$('#tagname').focus(function() {	
		$('.alertname').hide();
	});
	$('#description').focus(function() {
		$('.alertdescription').hide();
	});
	$('#tag').focus(function() {
		$('.tagname').hide();
	});
	$('#tagdescription').focus(function() {
		$('.tagdescription').hide();
	});
	$('#skillname').focus(function() {
		$('.skillalert').hide();
	});
	$('#skilldescription').focus(function() {
		$('.skilldescriptionalert').hide();
	});
	$('#focusedInput').keypress(function(){
		$('.addtitle').hide();
	});
	$('#categorySpace').click(function(){
		$('.addselect').hide();	
	});
	$('#tagspace').click(function(){
		$('.addselectpost').hide();
	});
	$('#skillpace').click(function(){
		$('.addselectskill').hide();
	});

	$('.note-editable p').keyup(function(){
		$('.descrip').hide();
	});
	$('.btn.btn-default.group').click(function(){
		$('.commend').hide();
	});
	$('#blogpost-media-file').click(function(){
		$('.file-upload-error').hide();
		$('.file-upload-validate-error').hide();

	});
	clearForms();
});
/*get user id*/
USER_ID = Ajax.ajaxHelper("GET", courseData.getContextPath()+"/rest/blogpost/postroll/user", undefined, "json", "application/json" );
/*uploadUtil*/
function uploadEvent(){
	var fileInput=document.getElementById("blogpost-media-file");
	var progressBar=document.getElementById("progressBar");
	progressBar.value=0;
	$(".upload-status").show();
	var xhr=new XMLHttpRequest();
	xhr.onload=function(data){
		if(xhr.status == 202){
			console.log("complete "+xhr.status);
			progressBar.value=0;
			$(".upload-status").hide(); 
			$('#uploadInput').val( xhr.responseText);
			$('#uploadInput').attr('readonly', true);
		}else{
			console.log("failed");
			$('#saveAction' ).prop( "disabled", false );
		}
	};
	xhr.upload.onprogress=function(e){
		var percentComplete=(e.loaded/e.total)*100;
		progressBar.value=percentComplete;
	};
	xhr.onerror=function(){
		console.log("Error ! upload failed.Can not connect to server");
	};
	var second =new Date().getTime();
	var fd=new FormData();
	fd.append("file",document.getElementById('blogpost-media-file').files[0]);
	var filename=fileInput.files[0].name;
	filename=second+filename.substring(filename.lastIndexOf("."),filename.length);

	xhr.open("POST",courseData.getContextPath()+"/rest/blogpost/postroll/media",false);
	xhr.send(fd);

};
/* ********************************************* CATEGORY **************************************** */
/*insert & update click functions*/
$('#addcategory').click(function(){
	if ($.trim($('#tagname').val()) != "") {
		$('.alertname').hide();
		if ($.trim($('#description').val()) != "") {
			if ($('#addcategory').is('.update')) {
				updatecat($.trim($('#tagname').val()), encodeURIComponent($.trim($('#description').val())), id);
			} else {
				addcategory($.trim($('#tagname').val()), encodeURIComponent($.trim($('#description').val())));
			}
		} else {
			$('.alertdescription').show();
		}
	} else {
		$('.alertname').show();
	}
});
/*category List*/
function categorylist(){
	categoryList =Ajax.post(courseData.getContextPath()+"/rest/blog/category/categories",undefined,"json","application/json");

	Handlebars.registerHelper('assertCatUser', function(obj){
		var self = this;
		var _orgpersonid = self.orgpersonid; 

		if(USER_ID == _orgpersonid){
			return "<td><a  id='"+self.id+"' name='"+self.name+"' description='"+self.description+"' data-toggle='tooltip' title='Edit' class='btn btn-primary categoryedit'><i class='fa fa-pencil orgcategoryedit'></i></a></td><td><a  id='"+self.id+"' name='"+self.name+"' description='"+self.description+"' data-toggle='tooltip' title='delete' class='btn btn-danger categorydelete'><i class=' fa fa-remove'></i></a></td>";
		}else{
			return "<td> - </td><td> - </td>";
		};
	});
	if(! (categoryList === "NO_CONTENT")){
		renderTemplate("#categorytempl", categoryList, "#category-list");
	}else if(categoryList === "NO_CONTENT"){
		renderTemplate("#nodatatmpl", undefined, "#category-list"); 
	} 
	editcategory();
	assertPostAvailabilityAndUpdate("moreinfomodal", "categorydelete", "/rest/blog/category/categoryroll/post/", undefined, "category-post-alert",delcategory);
}
/*create category*/
function addcategory(categoryName,categoryDescription){ 
	var createdataval = {categoryName:categoryName,categoryDescription:categoryDescription ,categoryStatus:"A"};
	categoryList1 =Ajax.post(courseData.getContextPath()+"/rest/blog/category/create",createdataval,"json","application/json");
	$('.categoryallertsuccess').show().delay(5000).fadeOut('slow');
	$('#tagname').val("");
	$('#description').val("");
	categorylist();
	renderNewCategoryList(); 
}
/*Delete category*/
function delcategory(element){
	var id= $(element).attr('id');
	categoryName=$(element).attr('name');
	categoryDescription=$(element).attr('description'); 
	if(confirm('Are you sure you want to delete this category ?')){
		var createdataval = {categoryId:id, categoryName:categoryName,categoryDescription:categoryDescription ,categoryStatus:"A"};
		categoryList1 =Ajax.post(courseData.getContextPath()+"/rest/blog/category/delete",createdataval,"json","application/json");
		$(".categoryalert").fadeIn('slow').delay(5000).fadeOut('slow');
		$('#tagname').val("");
		$('#description').val("");
		categorylist();
		renderNewCategoryList();
	}
}
/*edit category*/
function editcategory(){ 
	$(".categoryedit").click(function(){ 
		$(".submittag").val("update").removeClass("submittag").addClass("update");
		id= $(this).attr('id'); 
		categoryName=$(this).attr('name');
		categoryDescription=$(this).attr('description');
		$('#tagname').val(categoryName);
		$('#description').val(categoryDescription);
		$('#tagname').focus(); 
	});
}
/*Update Category*/
function updatecat(categoryName,categoryDescription,id){

	var createdataval = {categoryId:id,categoryName:categoryName,categoryDescription:categoryDescription ,categoryStatus:"A"};
	categoryList1 =Ajax.post(courseData.getContextPath()+"/rest/blog/category/update",createdataval,"json","application/json");
	$('#tagname').val("");
	$('#description').val("");
	$('.categoryupdatesuccess').show().delay(5000).fadeOut('slow');
	$(".update").val("submit").removeClass("update").addClass("submittag");
	categorylist();
	renderNewCategoryList();
}
/* ********************************************* TAG **************************************** */
/*insert & update */
$('#tagsubmit').click(function(){
	if ($.trim($('#tag').val()) != "") { 
		$('.tagname').hide();
		if ($.trim($('#tagdescription').val()) != "") {
			if ($('#tagsubmit').is('.update')) {
				updatetag($.trim($('#tag').val()), encodeURIComponent($.trim($('#tagdescription').val())), id);
			} else {
				addtag($.trim($('#tag').val()), encodeURIComponent($.trim($('#tagdescription').val())));
			}
		} else {
			$(".tagdescription").show();
		}
	} else {
		$(".tagname").show();
	}
});
/*tag list*/
function tagfulllist(){
	tagList =Ajax.post(courseData.getContextPath()+"/rest/blog/tag/tags",undefined,"json","application/json");
	Handlebars.registerHelper('assertTagUser', function(obj) {  
		var self = this;
		var _orgpersonid =  self.orgpersonid;
		if(USER_ID == _orgpersonid){
			return "<td><a  id='"+self.id+"' name='"+self.name+"' description='"+self.description+"' data-toggle='tooltip' title='Edit' class='btn btn-primary tagedit'><i class='fa fa-pencil orgtagedit'></i></a></td><td><a  id='"+self.id+"' name='"+self.name+"' description='"+self.description+"' data-toggle='tooltip' title='delete' class='btn btn-danger tagdelete'><i class='fa fa-remove'></i></a></td>";
		}else{
			return "<td> - </td><td> - </td>";
		};
	});
	if(! (tagList === "NO_CONTENT")){
		renderTemplate("#tagtempl", tagList, "#tag-list");
	}else if(tagList === "NO_CONTENT"){
		renderTemplate("#nodatatmpl", undefined, "#tag-list"); 
	} 

	assertPostAvailabilityAndUpdate("tagmodal", "tagdelete", "/rest/blog/tag/tagroll/post/", undefined, "tag-post-alert", deltag);
	edittag();
}
/*insert*/
function addtag(tagdataname,tagdatadescription){

	var createtagdata={tagName:tagdataname,tagDescription : tagdatadescription,tagStatus:"A"};
	addtagList =Ajax.post(courseData.getContextPath()+"/rest/blog/tag/create",createtagdata,"json","application/json");
	$('.tagallertsuccess').show().delay(5000).fadeOut('slow');
	$('#tag').val("");
	$('#tagdescription').val("");
	tagfulllist();
	renderNewtagList();
}
/*delete*/
function deltag( element){
	var id= $(element).attr('id');
	tagName=$(element).attr('name');
	tagDescription=$(element).attr('description'); 
	if(confirm('Are you sure you want to delete this Tag ?')){
		var tagdeldataval = {tagId:id, tagName:tagName,tagDescription:tagDescription ,tagStatus:"A"};
		categoryList1 =Ajax.post(courseData.getContextPath()+"/rest/blog/tag/delete",tagdeldataval,"json","application/json");
		$(".tagalert").fadeIn('slow').delay(5000).fadeOut('slow');
		$('#tag').val("");
		$('#tagdescription').val("");
		tagfulllist();
		renderNewtagList();
	}
}
/*edit*/
function edittag(){
	$(".tagedit").click(function(){

		$(".tagsubmit").val("update").addClass("update");
		id = $(this).attr('id');
		tagName=$(this).attr('name');
		tagDescription=$(this).attr('description');
		$("#tag").val(tagName);
		$("#tagdescription").val(tagDescription);
		$("#tag").focus();
	});
}
/*update*/
function updatetag(tagName,tagDescription,id){
	var tagupdateval = {tagId:id, tagName:tagName,tagDescription:tagDescription ,tagStatus:"A"};
	tagList1 =Ajax.post(courseData.getContextPath()+"/rest/blog/tag/update",tagupdateval,"json","application/json");
	$('.tagupdatesuccess').show().delay(5000).fadeOut('slow');
	$('#tag').val("");
	$('#tagdescription').val("");
	$(".update").val("submit").removeClass("update");
	tagfulllist();
	renderNewtagList();
}
/* ********************************************* SKILL **************************************** */
/*insert & update click functions*/
$('#skillsubmit').click(function(){
	$('.alert.alert-danger').hide();
	if ($.trim($('#skillname').val()) != "") {
		$('.skillalert').hide();
		if ($.trim($('#skilldescription').val()) != "") {
			if ($('#skillsubmit').is('.update')) {
				updateskill($.trim($('#skillname').val()), encodeURIComponent($.trim($('#skilldescription').val())), id);
			} else {
				addskill($.trim($('#skillname').val()), encodeURIComponent($.trim($('#skilldescription').val())));
			}
		} else {
			$(".skilldescriptionalert").show();
		}
	} else {
		$(".skillalert").show();
	}
});
/*skill list*/
function skillfulllist(){
	skillList =Ajax.post(courseData.getContextPath()+"/rest/blog/skill/skills",undefined,"json","application/json");
	Handlebars.registerHelper('assertSkillUser',function(obj){
		var self = this;
		var _orgpersonid = self.orgpersonid; 
		if(USER_ID == _orgpersonid){
			return  "<td><a  id='"+self.id+"' name='"+self.name+"' description='"+self.description+"' data-toggle='tooltip' title='Edit' class='btn btn-primary skilledit'><i class='fa fa-pencil orgskilledit'></i></a></td><td><a  id='"+self.id+"' name='"+self.name+"' description='"+self.description+"' data-toggle='tooltip' title='delete' class='btn btn-danger skilldelete'><i class='fa fa-remove'></i></a></td>";
		}else{
			return "<td> - </td><td> - </td>";
		};;
	});
	if(! (skillList === "NO_CONTENT")){
		renderTemplate("#skilltempl", skillList, "#skill-list");
	}else if(skillList === "NO_CONTENT"){
		renderTemplate("#nodatatmpl", undefined, "#skill-list"); 
	} 
	assertPostAvailabilityAndUpdate("skill", "skilldelete", "/rest/blog/skill/skillroll/post/", undefined, "skill-post-alert", delskill);
	editskill();
}
/*skill insert*/
function addskill(skilldataname,skilldatadescription){

	var createskilldata={skillName:skilldataname, skillDescription : skilldatadescription,skillStatus:"A"};
	addskillList =Ajax.post(courseData.getContextPath()+"/rest/blog/skill/create",createskilldata,"json","application/json");
	$('.skillallertsuccess').show().delay(5000).fadeOut('slow');
	$('#skillname').val("");
	$('#skilldescription').val("");
	skillfulllist();
	renderNewskillList();
}
/*skill delete*/
function delskill(element){

	var id= $(element).attr('id');
	skillName=$(element).attr('name');
	skillDescription=$(element).attr('description'); 
	if(confirm('Are you sure you want to delete this Skill ?')){
		var skilldeldataval = {skillId:id, skillName:skillName,skillDescription:skillDescription ,skillStatus:"A"};
		skillList1 =Ajax.post(courseData.getContextPath()+"/rest/blog/skill/delete",skilldeldataval,"json","application/json");
		$(".skillDeleteAlert").fadeIn('slow').delay(5000).fadeOut('slow');
		$('#skillname').val("");
		$('#skilldescription').val("");
		skillfulllist();
		renderNewskillList();
	}
}
/*skill edit*/
function editskill(){
	$(".skilledit").click(function(){ 
		$(".skillsubmit").val("update").addClass("update");
		id = $(this).attr('id'); 
		skillName=$(this).attr('name');
		skillDescription=$(this).attr('description');
		$("#skillname").val(skillName);
		$("#skilldescription").val(skillDescription);
		$("#skillname").focus();
	});
}
/*skill update*/
function updateskill(skillName,skillDescription,id){
	var skillupdateval = {skillId:id, skillName:skillName,skillDescription:skillDescription ,skillStatus:"A"};
	Ajax.post(courseData.getContextPath()+"/rest/blog/skill/update",skillupdateval,"json","application/json"); 
	$('.skillupdatesuccess').show().delay(5000).fadeOut('slow');
	$('#skillname').val("");
	$('#skilldescription').val("");
	$(".update").val("submit").removeClass("update");
	skillfulllist();
	renderNewskillList();
}
/* ********************************************* LIST CAT/TAG/SKILL **************************************** */ 
function renderNewCategoryList(){
	if(! (categoryList === "NO_CONTENT")){
		renderTemplate("#selectLoader", categoryList, "#categorySpace");
	}else if(categoryList === "NO_CONTENT"){ 
		renderTemplate("#nodataSelecttmpl", undefined, "#categorySpace");
	}

}
function renderNewtagList(){
	if(! (tagList === "NO_CONTENT")){
		renderTemplate("#selectLoader", tagList, "#tagspace");
	}else if(tagList === "NO_CONTENT"){ 
		renderTemplate("#nodataSelecttmpl", undefined, "#tagspace");
	}

}
function renderNewskillList(){
	if(! (skillList === "NO_CONTENT")){
		renderTemplate("#selectLoader", skillList, "#skillpace");
	}else if(skillList === "NO_CONTENT"){
		renderTemplate("#nodataSelecttmpl", undefined, "#skillpace"); 
	}

}
/* ********************************************* POST **************************************** */ 
/* CREATE A POST */ 
$('#post').click(function(){
	$('.note-editable').find("a").attr('target','_blank');
	bgpost=$('.note-editable').html();
	bgpost1=replaceAll(bgpost,"font","");
	bgpost2=replaceAll(bgpost1,"line","");
	bgpost3=replaceAll(bgpost2,"margin-top","");
	$('.note-editable').html(bgpost3); 
	if ($.trim($('#focusedInput').val()) != "") { 
		$('.addtitle').hide();
		if ($.trim($('#categorySpace').children().val()) != "") {
			$('.addselect').hide();	
			if($.trim($('#tagspace').children().val())){
				$('.addselectpost').hide();
				if($.trim($('#skillpace').children().val())){
					$('.addselectskill').hide();    
					if(!($('#blogpost-media-file').val().trim() === "")){
						$('.file-upload-error').hide();  
						if(validateFile( $('#blogpost-media-file').val().trim() )){
							$('.file-upload-validate-error').hide(); 

							uploadEvent();   
							if(! ($('#uploadInput').val().trim() === "")){
								var bannerPath = $('#uploadInput').val().trim(); 
								if(bannerPath != null | bannerPath != undefined | bannerPath != ""){
									if($('#summernote').code().trim().length > 12){ 
										$('.descrip').hide();
										if($.trim($('.btn.btn-default.group.active').text())){
											$('.commend').hide();
											if(! ($('.postdescval').val().trim() === "")){
												createPost($('#focusedInput').val(),$('#categorySpace').children().val(),$('#tagspace').children().val(),$('#skillpace').children().val(),$('#summernote').code(),$('.btn.btn-default.group.active').text(), $('#uploadInput').val(),$('.postdescval').val());	
											}else{
												$('.descriptext').show();
											}											
										}else{
											$('.commend').show();
										}
									}else{
										//$('#summernote').focus(); 
										$('.descrip').fadeIn('slow').delay(3000).fadeOut('slow');
									}
								}else{
									$(".upload-alerter").fadeIn('slow').delay(5000).fadeOut('slow');
								} 
							}else if($('#uploadInput').val().trim() === ""){
								$(".upload-alerter").fadeIn('slow').delay(5000).fadeOut('slow');
							}  


						}else{
							$('#blogpost-media-file').val("");
							$('.file-upload-validate-error').show();
							$('#blogpost-media-file').focus();
						} 

					}else{
						$('.file-upload-error').show();
						$('#blogpost-media-file').focus();
					}  
				}else {
					$('#skillpace').children().focus();
					$('.addselectskill').show();
				}
			}else{
				$('#tagspace').children().focus();
				$('.addselectpost').show();
			} 
		} else {
			$('#categorySpace').children().focus();
			$('.addselect').show();
		}
	} else {
		$('#focusedInput').focus();
		$(".addtitle").show();
	}
});
/*change the font use this function in editor */
function escapeRegExp(string) {
	return string.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
}
function replaceAll(string, find, replace) {
	return string.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}
function createPost(postTitle,categories,tags,skills,content,commentable, bannerpath,postMedia){ 
	var dataVal = {postTitle:postTitle,postContent:content,isPostCommentable:commentable,postStatus:"A",categoryIds:categories,skillIds:skills,tagIds:tags, postBannerPath: bannerpath,postMedia:postMedia};
	var flag = Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/blogpost",dataVal,"json","application/json");
	if(flag == "CREATED"){
		window.scrollTo(0,0);
		$(".alerter").show();
		setTimeout(function(){
			$('#focusedInput').val("");
			$('#categorySpace').children().val("");
			$('#tagspace').children("").val("");
			$('#skillpace').children("").val("");
			$('#summernote').code(""); 
			$('#blogpost-media-file').val("");
			$('.postdescval').val("");
			$(".alerter").hide();
			$("[name=backblogsform]").submit();
		},3000);

	}else{
		/*$(".alert").fadeIn('slow').delay(5000).fadeOut('slow');*/ 
	}
}
/* UPDATE POST */
function updatePost(){
	var _dataVal = constructPostJson();
	var flag = Ajax("PUT",courseData.getContextPath()+"/rest/blog/post",_dataVal,"json","application/json" );
	if(flag == "success"){
		// to-do add alert message
		console.log('blog updated');
	}
}
if($('[data-original-title = "Full Screen"]').hasClass('active')){
	$('.note-editable').css({"height" :  "100% !important ","width" : "100% !important" });
}
/* ********************************************* FORM VALIDATIONS  **************************************** */
/* BASIC FORM VALIDATIONS */ 
assertFormCharacterLength('focusedInput','title-length' );
assertFormCharacterLength('tagname','category-name-alert' );
assertFormCharacterLength('tag','tag-title-length' );
assertFormCharacterLength('skillname','skill-name-alert' ); 
if(! $('#update-post').length>0){
	assertCreateDuplicate("blogEntry","focusedInput", "/rest/blogpost/postroll/duplicate", "postTitle", "title-duplicate");	
}else if( $('#update-post').length>0){
	var _postId = $('#blogid').attr('value');
	var _postTitle = $('#focusedInput').val(); 
	assertUpdatePostDuplicate("blogEntry", "focusedInput", "/rest/blogpost/postroll/change/duplicate", "postId", _postId, "postTitle", _postTitle, "title-duplicate");
}
assertCreateDuplicate("moreinfomodal","tagname", "/rest/blog/category/duplicate", "categoryName", "category-duplicate-alert");
assertCreateDuplicate("tagmodal","tag", "/rest/blog/tag/duplicate", "tagName", "tag-title-duplicate"); 
assertCreateDuplicate("skill","skillname", "/rest/blog/skill/duplicate", "skillName", "skill-title-duplicate");
assertUpdateDuplicate("moreinfomodal","tagname", "/rest/blog/category/categoryroll/duplicate", "categoryName","categoryId", "category-update-duplicate-alert");
assertUpdateDuplicate("tagmodal","tag", "/rest/blog/tag/tagroll/duplicate", "tagName","tagId", "tag-update-duplicate-alert");
assertUpdateDuplicate("skill","skillname", "/rest/blog/skill/skillroll/duplicate", "skillName","skillId", "skill-update-duplicate-alert");
function assertFormCharacterLength(id, errorClass){
	$('.alert.alert-danger').hide();
	$('#'+id).on('keypress keyup',function(){
		var _focusedInputLength = $('#'+id).attr('maxlength');
		if($(this).val().trim().length >= _focusedInputLength){
			$('.alert.alert-danger.'+errorClass).show();  
		} else{
			$('.alert.alert-danger.'+errorClass).hide(); 
		}
	});
};
function assertCreateDuplicate(selector , id, URL, element , errorClass){

	$('#'+selector).on('blur','#'+id, function() {   
		$('.alert.alert-danger').hide();
		if( ! ( $('#'+selector).find('.btn.btn-success').hasClass('update')) ){
			var _dataVal = {};
			_dataVal[element] =  $('#'+id).val().trim();
			var _flag = Ajax.ajaxHelper("POST", courseData.getContextPath()+URL, _dataVal, "json", "application/json"); 
			if (_flag == "ACCEPTED") {
				$('.alert.alert-danger.' + errorClass).show();
				$('#'+id).val("");
				$('#'+id).on('focusin', function(){
					$('.alert.alert-danger.' + errorClass).hide();
				});
			} else {
				$('.alert.alert-danger.'+errorClass).hide(); 
			} 
		}
	});
}
function assertUpdateDuplicate(selector , newid, URL, element1 , element2 ,errorClass){
	$('.alert.alert-danger').hide();
	$('#'+selector).on('blur','#'+newid, function() {   
		if(  $('#'+selector).find('.btn.btn-success').hasClass('update') ){
			var _dataVal = {};
			_dataVal[element1] =  $('#'+newid).val().trim();
			_dataVal[element2] =  id;
			var _flag = Ajax.ajaxHelper("POST", courseData.getContextPath()+URL, _dataVal, "json", "application/json"); 
			if (_flag == "ACCEPTED") {
				$('.alert.alert-danger.' + errorClass).show();
				$('#'+newid).val("");
				$('#'+newid).on('focusin', function(){
					$('.alert.alert-danger.' + errorClass).hide();
				});
			} else {
				$('.alert.alert-danger.'+errorClass).hide(); 
			} 
		}
	}); 
}
function assertPostAvailabilityAndUpdate(selector, className, URL, element, errorClass,methodName){
	$("."+className).click( function() {    
		var _dataVal = undefined; 
		var _flag = Ajax.ajaxHelper("POST", courseData.getContextPath()+URL+$(this).attr("id"), _dataVal, "json", "application/json"); 
		if (_flag == "ACCEPTED") {
			$("."+errorClass).fadeIn('slow').delay(5000).fadeOut('slow'); 
		} else {
			$("."+errorClass).hide();  
			methodName(this);
		};
	});
}
function assertUpdatePostDuplicate(parentSelector , childSelector, URL, element1 ,element1Object, element2 ,element2Object,errorClass){ 
	$('#'+parentSelector).on('blur', '#'+childSelector, function(){
		var _dataVal = {};
		element2Object =$('#'+childSelector).val();
		_dataVal[element1] = element1Object;
		_dataVal[element2] = element2Object;
		var _flag = Ajax.ajaxHelper("POST", courseData.getContextPath()+URL, _dataVal, "json", "application/json");  
		if (_flag == "ACCEPTED") {
			$('.alert.alert-error.' + errorClass).show();
			$('#'+childSelector).val('');
			$('#'+childSelector).val(POST_TITLE);
			$('#'+childSelector).on('focusin', function(){
				$('.alert.alert-danger.' + errorClass).hide();
			});
		} else {
			$('.alert.alert-danger.'+errorClass).hide(); 
		}  
	});
}
function clearForms(){ 
	$('#moreinfomodal, #skill, #tagmodal ').on('click','.close,.reset', function(){ 
		$('#moreinfomodal,#skill,#tagmodal').find('input:text, textarea, select').val('');
		if($('#moreinfomodal').hasClass('in')){
			$('#moreinfomodal').find('.update').prop('value','Submit').addClass('submittag').removeClass('update');
			$(' #moreinfomodal ').find('.alert.alert-danger').hide();
		}else if($(' #tagmodal ').hasClass('in')){
			$('#tagmodal').find('.update').prop('value','Submit').removeClass('update');
			$(' #tagmodal ').find('.alert.alert-danger').hide();
		}else if($(' #skill ').hasClass('in')){
			$(' #skill ').find('.update').prop('value','Submit').removeClass('update');
			$(' #skill ').find('.alert.alert-danger').hide();
		}
	});  
}
function updatePost(){ 
	validateandUpdatePost();
	if($('#update-post').length > 0){
		URL = "/rest/blogpost/postroll/";
		var _postId = $('#blogid').attr('value');
		var postVal  ={};
		postVal = Ajax.ajaxHelper("GET", courseData.getContextPath()+URL+_postId, undefined, "json", "application/json");  
		var _postTitle,_postCommentable,_postDate,_postStatus,_postContent;
		var _skillIds, _tagIds, _categoryIds = [];
		_postId 			= _.uniq(_.pluck(postVal, "post_id"))[0];
		_postTitle 		= _.uniq(_.pluck(postVal, "post_title"))[0];
		_categoryIds 		= _.uniq(_.pluck(postVal, "categoryId"));
		_tagIds 			= _.uniq(_.pluck(postVal, "tagId"));
		_skillIds 			= _.uniq(_.pluck(postVal, "skillId"));
		_postContent 		= _.uniq(_.pluck(postVal,"post_content"))[0];
		_postCommentable 	= _.uniq(_.pluck(postVal, "post_commentable"))[0];
		_postDate			= _.uniq(_.pluck(postVal, "post_date"))[0];
		_postStatus		= _.uniq(_.pluck(postVal, "post_status"))[0];
		_postBannerPath    = _.uniq(_.pluck(postVal, "post_bannerpath"))[0];
		_postDescription    = _.uniq(_.pluck(postVal, "post_media"))[0];
		POST_CONTENT = _postContent;
		$('#focusedInput').val(_postTitle);
		$('.postdescval').val(_postDescription);
		$('.postdescvalrem').text(500-($('.postdescval').val().length));
		POST_TITLE = _postTitle;
		$('#summernote').summernote({
			height : 220
		});
		$('#summernote').code(POST_CONTENT); 
		for(var i =0; i<_categoryIds.length;i++ ){
			$('#categorySpace option[value='+_categoryIds[i]+']').attr('selected','selected');
		} 
		for(var i =0; i<_tagIds.length;i++ ){
			$('#tagspace option[value='+_tagIds[i]+']').attr('selected','selected');
		} 
		for(var i =0; i<_skillIds.length;i++ ){
			$('#skillpace option[value='+_skillIds[i]+']').attr('selected','selected');
		}
		var yesBtn = $('.comment .btn')[0];
		var NoBtn = $('.comment .btn')[1];
		if(_postCommentable == "Yes"){
			$(yesBtn).addClass('active');
			$(NoBtn).removeClass('active');
		}else if(_postCommentable == "No"){
			$(NoBtn).addClass('active');
			$(yesBtn).removeClass('active');
		}
		document.getElementById('update-image-showcase').src= $('#context').text()+"/OpenFile?r1=serverpath&r2="+"/"+_postBannerPath;
		$('#uploadInput').val(_postBannerPath);
		$('#uploadInput').attr('readonly', true);
		clearUpdateFileUpload();
	};

}
function  clearUpdateFileUpload(){
	$('.clearFile').click(function(){
		$('#blogpost-media-file').val("");
		$('.clearFile').css('display','none');
	});
}
function validateandUpdatePost(){
	$('#update-post').click(function(){
		$('.note-editable').find("a").attr('target','_blank');
		bgpost=$('.note-editable').html();
		bgpost1=replaceAll(bgpost,"font","");
		bgpost2=replaceAll(bgpost1,"line","");
		bgpost3=replaceAll(bgpost2,"margin-top","");
		$('.note-editable').html(bgpost3); 
		if ($.trim($('#focusedInput').val()) != "") { 
			$('.addtitle').hide();
			if ($.trim($('#categorySpace').children().val()) != "") {
				$('.addselect').hide();	
				if($.trim($('#tagspace').children().val())){
					$('.addselectpost').hide();
					if($.trim($('#skillpace').children().val())){
						$('.addselectskill').hide(); 

						if(!($('#blogpost-media-file').val().trim() === "")){
							// NEW FILE HAS BEEN ADDED SO NEW UPLOAD EVENT HAS BEEN MADE 
							$('.file-upload-error').hide();


							if(validateFile( $('#blogpost-media-file').val().trim())){
								$('.file-upload-validate-error').hide(); 

								uploadEvent();//UPLOAD EVENT ADDED   
								if(! ($('#uploadInput').val().trim() === "")){
									var bannerPath = $('#uploadInput').val().trim(); 
									if(bannerPath != null | bannerPath != undefined | bannerPath != ""){
										if($('#summernote').code().trim().length > 12){ 
											$('.descrip').hide();
											if($.trim($('.btn.btn-default.group.active').text())){
												$('.commend').hide();
												// UPDATING POST WITH NEW FILE
												if(!($('.postdescval').val().trim() === "")){
													updateThePost($('#blogid').attr('value'),$('#focusedInput').val(),$('#categorySpace').children().val(),$('#tagspace').children().val(),$('#skillpace').children().val(),$('#summernote').code(),$('.btn.btn-default.group.active').text(),$('#uploadInput').val().trim(),$('.postdescval').val().trim());	
												}else{
													$('.descriptext').show();
												}	

											}else{
												$('.commend').show();
											}
										}else{
											//$('#summernote').focus(); 
											$('.descrip').fadeIn('slow').delay(3000).fadeOut('slow');
										}
									}else{
										$(".upload-alerter").fadeIn('slow').delay(5000).fadeOut('slow');
									} 
								}else if($('#uploadInput').val().trim() === ""){
									$(".upload-alerter").fadeIn('slow').delay(5000).fadeOut('slow');
								} 


							}else{
								$('#blogpost-media-file').val("");
								$('.file-upload-validate-error').show();
								$('#blogpost-media-file').focus();
							} 

						}else{
							//NO NEW EXISTING FILE HAS BEEN ADDED HENCE EXISTING BANNER WOULD BE USED.
							if(! ($('#uploadInput').val().trim() === "")){
								var bannerPath = $('#uploadInput').val().trim(); 
								if(bannerPath != null | bannerPath != undefined | bannerPath != ""){
									if($('#summernote').code().trim().length > 12){ 
										$('.descrip').hide();
										if($.trim($('.btn.btn-default.group.active').text())){
											$('.commend').hide();
											// UPDATING POST WITH EXISTING FILE
											if(! ($('.postdescval').val().trim() === "")){
												updateThePost($('#blogid').attr('value'),$('#focusedInput').val(),$('#categorySpace').children().val(),$('#tagspace').children().val(),$('#skillpace').children().val(),$('#summernote').code(),$('.btn.btn-default.group.active').text(),$('#uploadInput').val().trim(),$('.postdescval').val().trim());	
											}else{
												$('.descriptext').show();
											}
										}else{
											$('.commend').show();
										}
									}else{
										//$('#summernote').focus(); 
										$('.descrip').fadeIn('slow').delay(3000).fadeOut('slow');
									}
								}else{
									$(".upload-alerter").fadeIn('slow').delay(5000).fadeOut('slow');
								} 
							}else if($('#uploadInput').val().trim() === ""){
								$(".upload-alerter").fadeIn('slow').delay(5000).fadeOut('slow');
							}  
						} 

					}
					else {
						$('#skillpace').children().focus();
						$('.addselectskill').show();
					}
				}else{
					$('#tagspace').children().focus();
					$('.addselectpost').show();
				} 
			} else {
				$('#categorySpace').children().focus();
				$('.addselect').show();
			}
		} else {
			$('#focusedInput').focus();
			$(".addtitle").show();
		}
	});
}
function updateThePost(postId,postTitle,categories,tags,skills,content,commentable,bannerpath,postMedia){
	var dataVal = {postId:postId,postTitle:postTitle,postContent:content,isPostCommentable:commentable,postStatus:"U",categoryIds:categories,skillIds:skills,tagIds:tags,postBannerPath:bannerpath,postMedia:postMedia};

	var flag = Ajax.ajaxHelper("PUT", courseData.getContextPath()+"/rest/blogpost/"+postId ,dataVal,"json","application/json");
	if(flag == "ACCEPTED"){ 
		window.scrollTo(0,0);
		$(".alerter").show();
		$('.update-post').hide();
		setTimeout(function(){
			$('#focusedInput').val("");
			$('#categorySpace').children().val("");
			$('#tagspace').children("").val("");
			$('#skillpace').children("").val("");
			$('#summernote').code(""); 
			$('#blogpost-media-file').val("");
			$(".alerter").hide();
			$("[name=backblogsform]").submit();
		},3000);
	}else if(flag == "NOT_MODIFIED"){
		$(".alert").fadeIn('slow').delay(5000).fadeOut('slow'); 
	};
}
function renderTemplate(templateselector,jsonData,outputSelector){
	var source   = $(templateselector).html();
	var template = Handlebars.compile(source);
	var templateHtml = template(jsonData);
	$(outputSelector).html(templateHtml);	
}; 
function isValidFileExtension(fileExtension){ 
	var allowedFileExtensions  = ["bmp", "gif", "jpg", "jpeg", "png"];
	fileExtension = fileExtension.toLowerCase();
	for(var i=0; i< allowedFileExtensions.length; i++){
		if(allowedFileExtensions[i] == fileExtension ){
			return true;
		}
	}
	return false;
}
function isValidFileName(fileName){
	console.error(fileName);
	if(/^[a-z0-9\-\_\s]+$/i.test(fileName)){
		console.warn(true);
		return true;
	}  
	console.error(false);
	return false;
} 
function validateFile(file){
	var _file = file.split("\\").pop(); 
	var _fileName = _file.split('.')[0];
	var _fileExt = _file.split('.')[1]; 
	if(isValidFileExtension(_fileExt) & isValidFileName(_fileName)){
		return true;
	} 
	return false;
};
function filechange(){
	if($("#blogpost-media-file").val().length!=0){
		$('.clearFile').css('display','inline-block');
	}else{
		$('.clearFile').css('display','none');
	}	
}