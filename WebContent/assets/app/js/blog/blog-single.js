 $(document).ready(function() {
	$(".comments").click(function() {
		$(".commenttext").slideToggle("slow");
		 var comName = $.trim( $('.user-name').text() );
		  $('#commentedname').val(comName);
	});
	$('.resetcomment').click(function() {
		$(".comtext").val("");
		$('.chrem').text("500");
	});
	$('.comtext').keyup(function(){
	    el = $(this);
	    if(el.val().length >= 501){
	        el.val( el.val().substr(0, 500) );
	    } else {
	    	$('.chrem').text(500-el.val().length);
	    }
	});
	getPostComments();
	AllPosts();
	getPosts();
	commentView();
	
	 $('.leave-comment').click(function(){
		
		addcomments();
		commentView();
	});

	$('.comtext').focus(function(){
		$('.commentallert').hide();
	});
	 clickeve();
	 $('.comments').tooltip();
	 $('.icon-circle-arrow-down').tooltip();
	 
});
var postid = $("#blogid").val();
function AllPosts() {
	var posts = Ajax.ajaxHelper("GET", courseData.getContextPath()
			+ "/rest/blogpost/postroll/" + postid, undefined, "json",
			"application/json");
	
	Handlebars.registerHelper('dateParser', function(date) {
		return new Date(date).toDateString();
	});
	var newPost = post(posts);
	
	Handlebars.registerHelper('commentsLength', function() {
		return commentsLength;
	});
	renderTemplate("#post-template", newPost, "#posts");
	$('.blotitle').text($('.postitle').text());
	/* renderTemplate("#skills-template",newPost,"#skilllist"); */
	var commentable=_.pluck(posts,'post_commentable');
	var commentsdata=commentable.toString();
	if(commentsdata=="Yes"){
		$(".comments").show();
	}
	else if(commentsdata=="No"){
		$(".comments").hide();
		$("#commenddiv").hide();		
	}

}
function getPosts() {

 	var posts = Ajax.ajaxHelper("GET", courseData.getContextPath()
			+ "/rest/blogpost", undefined, "json", "application/json");
	var lastpost = posts.splice(0, 6);
	Handlebars.registerHelper('dateParser', function(date) {
		return new Date(date).toDateString();
	});
	renderTemplate("#recentpost-template", lastpost, "#recent");

}
	
function post(object) {
	var _postID = _.unique(_.pluck(object, "post_id"))[0];
	var _postContent = _.unique(_.pluck(object, "post_content"))[0];
	var _postTitle = _.unique(_.pluck(object, "post_title"))[0];
	var _postMedia = _.unique(_.pluck(object, "post_media"))[0];
	var _postdate = new Date(_.unique(_.pluck(object, "post_date"))[0]);
	var _postDate = _postdate.toDateString();
	
	var _postBannerPath = _.unique(_.pluck(object, "post_bannerpath"))[0];
	var _postStatus = _.unique(_.pluck(object, "post_status"))[0];
	var _author = _.unique(_.pluck(object, "authorPersonFirstName"))[0];

	var tags = _.unique(_.pluck(object, "tagId"));
	var categories = _.unique(_.pluck(object, "categoryId"));
	var skills = _.unique(_.pluck(object, "skillId"));

	var _categoriesJSON = [];
	var _tagsJSON = [];
	var _tagvalue=[];
	var _skillJSON = [];

	var i = 0, j = 0, k = 0;
	i;
	
//	var afterDot = tags.substr(tags.indexOf('.'));
	
	for (i; i < tags.length; i++) {

		var tagObject = _.where(object, {
			tagId : tags[i]
		})[0];
		var _tagName = tagObject.tagName;
		var _tagId = tagObject.tagId;
		
			 		
		var _tag = {
			tagId : _tagId,
			tagName : _tagName,
			
					};
			
		_tagsJSON.push(_tag);
		
	};	
	var id=_.pluck(_tagsJSON,'tagId');
	var name=_.pluck(_tagsJSON,'tagName');
	var _tagdetails={
			tagId:id,
			tagName:name
				
	};
	_tagvalue.push(_tagdetails);
	 
	for (j; j < categories.length; j++) {

		var categoryObject = _.where(object, {
			categoryId : categories[j]
		})[0];
		var _categoryName = categoryObject.categoryName;
		var _categoryId = categoryObject.categoryId;
		var _category = {
			categoryId : _categoryId,
			categoryName : _categoryName

		};
		_categoriesJSON.push(_category);
	}
	;

	for (k; k < skills.length; k++) {

		var skillObject = _.where(object, {
			skillId : skills[k]
		})[0];
		var _skillName = skillObject.skillName;
		var _skillId = skillObject.skillId;
		var _skill = {
			skillId : _skillId,
			skillName : _skillName

		};
		_skillJSON.push(_skill);
	}
	;
	var postArray = [];
	var postJSON = {

		"postID" : _postID,
		"postContent" : _postContent,
		"postTitle" : _postTitle,
		"postMedia" : _postMedia,
		"postDate" : _postDate,
		"postBannerPath" : _postBannerPath,
		"postStatus" : _postStatus,
		"author" : _author,
		"cateogries" : _categoriesJSON,
		"tags" : _tagvalue,
		"skills" : _skillJSON
	};
 
	postArray.push(postJSON);
	return postArray;

};
/* comments */
function getPostComments() {
	comments = {};
	comments = Ajax.ajaxHelper("GET", courseData.getContextPath()
			+ "/rest/blog/comment/commentroll/post/" + postid, undefined,
			"json", "application/json"); 
	commentsLength = comments.length;
}
function commentView() {
	//$('#view-comments').click(function() { 
		Handlebars.registerHelper('dateParser', function(date) {
			var newDate = new Date(date);
			return newDate.customFormat("#DD# #MMM# #YYYY# #hh#:#mm# #AMPM#");
			return newDate;
		});
		getPostComments();
		AllPosts();
		renderTemplate("#comments-template", comments, "#comments-sub");
		
} 
function addcomments() {
		var postid=$('#blogid').val();
		var commenttx=$('.comtext').val().trim();
		if ($.trim($('.comtext').val()) != "") {
			$(".commentallertsuccess").show();
			var commentdata={commentText:commenttx,commentDate:null,commentStatus:"A",postId:postid};
			Ajax.ajaxHelper('POST',courseData.getContextPath()+"/rest/blog/comment",commentdata,"json","application/json");
			$('.comtext').val("");
			setTimeout(function(){
				$(".commentallertsuccess").hide();
				$(".commenttext").slideToggle("slow");
				getPostComments();
			},3000);
		}
		else{
			$('.commentallert').show();
			$(".commentallertsuccess").hide();
		}
		
	}
function clickeve(){
	$('.recentPost').click(function(){
		   $(this).closest("form").submit();
	   }); 
}
Date.prototype.customFormat = function(formatString) {
	var YYYY, YY, MMMM, MMM, MM, M, DDDD, DDD, DD, D, hhh, hh, h, mm, m, ss, s, ampm, AMPM, dMod, th;
	var dateObject = this;
	YY = ((YYYY = dateObject.getFullYear()) + "").slice(-2);
	MM = (M = dateObject.getMonth() + 1) < 10 ? ('0' + M) : M;
	MMM = (MMMM = [ "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" ][M - 1])
			.substring(0, 3);
	DD = (D = dateObject.getDate()) < 10 ? ('0' + D) : D;
	DDD = (DDDD = [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday" ][dateObject.getDay()]).substring(0, 3);
	th = (D >= 10 && D <= 20) ? 'th' : ((dMod = D % 10) == 1) ? 'st'
			: (dMod == 2) ? 'nd' : (dMod == 3) ? 'rd' : 'th';
	formatString = formatString.replace("#YYYY#", YYYY).replace("#YY#", YY)
			.replace("#MMMM#", MMMM).replace("#MMM#", MMM).replace("#MM#", MM)
			.replace("#M#", M).replace("#DDDD#", DDDD).replace("#DDD#", DDD)
			.replace("#DD#", DD).replace("#D#", D).replace("#th#", th);

	h = (hhh = dateObject.getHours());
	if (h == 0)
		h = 24;
	if (h > 12)
		h -= 12;
	hh = h < 10 ? ('0' + h) : h;
	AMPM = (ampm = hhh < 12 ? 'am' : 'pm').toUpperCase();
	mm = (m = dateObject.getMinutes()) < 10 ? ('0' + m) : m;
	ss = (s = dateObject.getSeconds()) < 10 ? ('0' + s) : s;
	return formatString.replace("#hhh#", hhh).replace("#hh#", hh).replace(
			"#h#", h).replace("#mm#", mm).replace("#m#", m).replace("#ss#", ss)
			.replace("#s#", s).replace("#ampm#", ampm).replace("#AMPM#", AMPM);
};	