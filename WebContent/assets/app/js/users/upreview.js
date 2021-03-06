$(document).ready(function(){
	ucourse().init();

	var today=new Date();
	var date = today.toDateString();
	$('#todaydate').attr("value",date);	
});

Handlebars.registerHelper('freeChecker', function(price) 
		{ 
	if(price=="Free"){			 
		return new Handlebars.SafeString("<li class='viewtextfont' style='color: rgb(63, 190, 23);'>Free</li>");
	}
	else{
		return new Handlebars.SafeString("<li class='viewtextfont' style='color: rgb(252, 0, 0);'>" +
				"  "+"<i class='fa fa-inr'></i>"+" "+" "+price+"</li>");
	}
		});

manageschedule={
		loadSchedule:function(){
			var courseid=$('.courseid').text();
			var schedulelist = courseData.getSchedulecourseDetails(courseid); 

			Handlebars.registerHelper('checkdate',function(scheduledate,livesessionid,starttime,endtime,price,courseid){

				var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth()+1;
				var yyyy = today.getFullYear();
				if(dd<10){
					dd='0'+dd;
				} 
				if(mm<10){
					mm='0'+mm;
				} 
				var today = yyyy+'-'+mm+'-'+dd;

				var dates = new Date(scheduledate);
				var dd = dates.getDate();
				var mm = dates.getMonth()+1;
				var yyyy = dates.getFullYear();
				if(dd<10){
					dd='0'+dd;
				} 
				if(mm<10){
					mm='0'+mm;
				} 
				var dates = yyyy+'-'+mm+'-'+dd;

				var currentdate = new Date(); 
				var cuntdate =currentdate.toLocaleTimeString();
				var cntdate =cuntdate.split(' ');
				var cunttime =cntdate[0].split(':'); 
				var hour=currentdate.getHours();
				var mint=currentdate.getMinutes();
				if(mint<10){
					mint='0'+mint;
				} 
				var datetimeval = /*hour + ":" + mint;*/cunttime[0] + ":" +cunttime[1] +" "+ cntdate[1];
				/*var start=$.trim(starttime);
				    var end=$.trim(endtime);*/

				var datetime ="";
				var i=datetimeval.split(' ');
				var j=i[0].split(':');
				if(i[1]=="PM"){
					if(j[0]=="12"){
						datetime=j[0]+":"+j[1]+" "+i[1];
					}
					else{
						datetime=parseInt(j[0])+12+":"+j[1]+" "+i[1];
					}

				}
				else if(i[1]=="AM"){
					if(j[0]=="12"){
						datetime="00:"+j[1]+" "+j[1];	
					}
					else if(parseInt(j[0])<10){
						datetime="0"+j[0]+":"+j[1]+" "+j[1];
					}
					else{
						datetime=j[0]+":"+j[1]+" "+i[1];
					}
				}
				else{
					datetime=j[0]+":"+j[1]+" "+i[1];
				}
				var startstimeval=$.trim(starttime);
				var endstimeval=$.trim(endtime);

				var start ="";
				var a=startstimeval.split(' ');
				var b=a[0].split(':');
				if(a[1]=="PM"){
					if(b[0]=="12"){
						start=b[0]+":"+b[1]+" "+a[1];
					}
					else{
						start=parseInt(b[0])+12+":"+b[1]+" "+a[1];
					}

				}
				else if(a[1]=="AM"){
					if(b[0]=="12"){
						start="00:"+b[1]+" "+a[1];	
					}
					else if(parseInt(b[0])<10){
						start="0"+b[0]+":"+b[1]+" "+a[1];	
					}
					else{
						start=b[0]+":"+b[1]+" "+a[1];
					}
				}
				else{
					start=b[0]+":"+b[1]+" "+a[1];
				}

				var end="";
				var c=endstimeval.split(' ');
				var d=c[0].split(':');
				if(c[1]=="PM"){
					if(d[0]=="12"){
						end=d[0]+":"+d[1]+" "+c[1];
					}
					else{
						end=parseInt(d[0])+12+":"+d[1]+" "+c[1];
					}
				}
				else if(c[1]=="AM"){ 
					if(d[0]=="12"){
						end="00:"+d[1]+" "+c[1];
					}
					else if(parseInt(d[0])<10){
						end="0"+d[0]+":"+d[1]+" "+c[1];
					}
					else{
						end=d[0]+":"+d[1]+" "+c[1];
					}
				}
				else{
					end=d[0]+":"+d[1]+" "+c[1];
				}
				if((today == dates)){

					if ( (datetime >= start && datetime<=end))
					{
						return  "<a class='label marleft15' id='livechat' style='background-color:#449d44;' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" data-toggle='tooltip' title='Live'><i class='fa fa-play-circle'></i></a>";"<span class='btn-circle live ' id='livechat' style='background-color:#0A8F0A;' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+"  data-toggle='tooltip' title='Live'><b><i class='fa fa-play-circle' style='font-size:21px;margin-left:6px;color: white !important;'></i>&nbsp;&nbsp;</b> </span>";
					}
					else if(datetime<start && datetime< end) {
						return "<a class='label future marleft15' id='livechat' style='background-color:rgb(218, 154, 37);' data-toggle='tooltip' title='Future'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 future' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(218, 154, 37);'data-toggle='tooltip' title='Future' ><b><i class='icon-signin'  style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/
					}
					else if(datetime>start && datetime>end){
						return "<a class='label complete marleft15' id='livechat' style='background-color:rgb(73, 179, 199);' data-toggle='tooltip' title='Completed'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 complete' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(73, 179, 199);'data-toggle='tooltip' title='Complete' ><b><i class='icon-time' style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/    	
					}
				} 


				else if(today<dates/*) &&(datetime<start && datetime< end)*/){
					return "<a class='label future marleft15' id='livechat' style='background-color:rgb(218, 154, 37);' data-toggle='tooltip' title='Future'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 future' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(218, 154, 37);'data-toggle='tooltip' title='Future' ><b><i class='icon-signin'  style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*//*return "<span class='btn-circle c1 future' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(218, 154, 37);'data-toggle='tooltip' title='Future' ><b><i class='icon-signin'  style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/
				} 
				else
				{
					return "<a class='label complete marleft15' id='livechat' style='background-color:rgb(73, 179, 199);' data-toggle='tooltip' title='Completed'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 complete' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(73, 179, 199);'data-toggle='tooltip' title='Complete' ><b><i class='icon-time' style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*//*return "<span class='btn-circle c1 complete' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(73, 179, 199);'data-toggle='tooltip' title='Complete' ><b><i class='icon-time' style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/
				}
			});

			$('.live').tooltip();
			$('.future').tooltip();
			$('.complete').tooltip();
			renderTemplate("#scheduletemplate", schedulelist, "#scheduleholder");

			$('.schedule').each(function(){
				var scdate=$(this).attr('schdate');
				dates = new Date(parseInt(scdate));
				date = dates.toDateString();
				$('.scheduledate').text(date);
			});

			$('.duration').each(function(){
				var first=$(this).attr('start');
				var second=$(this).attr('end');
				/*var startstime=first.split(' ');
				var endsstime=second.split(' ');*/

				var a="";
				var b="";
				/*if(startstime[1]=="PM"){
					var starttime = startstime[0].split(':');
					a=parseInt(starttime[0])+12+":"+starttime[1]+":00";
				}
				else{
					a=startstime[0]+":00";
				}

				if(endsstime[1]=="PM"){
					var endtime = endsstime[0].split(':');
					b=parseInt(endtime[0])+12+":"+endtime[1]+":00";
				}
				else{
					b=endsstime[0]+":00";
				}*/

				var a1=first.split(' ');
				var b1=a1[0].split(':');
				if(a1[1]=="PM"){
					if(b1[0]=="12"){
						a=b1[0]+":"+b1[1]+" "+":00";
					}
					else{
						a=parseInt(b1[0])+12+":"+b1[1]+" "+":00";
					}

				}
				else if(a1[1]=="AM"){
					if(b1[0]=="12"){
						a="00:"+b1[1]+" "+":00";	
					}
					else if(parseInt(b1[0])<10){
						a="0"+b1[0]+":"+b1[1]+" "+":00";	
					}
					else{
						a=b1[0]+":"+b1[1]+" "+":00";
					}
				}
				else{
					a=b1[0]+":"+b1[1]+" "+":00";
				}

				var c=second.split(' ');
				var d=c[0].split(':');
				if(c[1]=="PM"){
					if(d[0]=="12"){
						b=d[0]+":"+d[1]+" "+":00";
					}
					else{
						b=parseInt(d[0])+12+":"+d[1]+" "+":00";
					}
				}
				else if(c[1]=="AM"){ 
					if(d[0]=="12"){
						b="00:"+d[1]+" "+":00";
					}
					else if(parseInt(d[0])<10){
						b="0"+d[0]+":"+d[1]+" "+":00";
					}
					else{
						b=d[0]+":"+d[1]+" "+":00";
					}
				}
				else{
					b=d[0]+":"+d[1]+" "+":00";
				}

				function toSeconds(time_str) {
					// Extract hours, minutes and seconds
					var parts = time_str.split(':');
					// compute  and return total seconds
					return parts[0] * 3600 + // an hour has 3600 seconds
					parts[1] * 60 + // a minute has 60 seconds
					+
					parts[2]; // seconds
				}

				var difference = Math.abs(toSeconds(a) - toSeconds(b));

				// format time differnece
				var result = [
				              Math.floor(difference / 3600), // an hour has 3600 seconds
				              Math.floor((difference % 3600) / 60), // a minute has 60 seconds
				              difference % 60
				              ];
				// 0 padding and concatation
				result = result.map(function(v) {
					return v < 10 ? '0' + v : v;
				}).join(':');
				var range = result.split(':');
				$(this).text(range[0]+" hour"+" : "+range[1]+" min");
			});

		}
};

var ucourse=(function(){
	var courseid=$('[name=courseid]').val();
	var priceic=$('#priceicon').text();
	var controller={
			info:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/info/"+courseid+"",undefined,"json","application/json");	
			},
			curriculum:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/curriculum/"+courseid+"",undefined,"json","application/json");
			},
			review:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/reviews/"+courseid+"",undefined,"json","application/json");
			},
			question:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/questions/"+courseid+"",undefined,"json","application/json");
			},
			aboutauthor:function(personid){
				return Ajax.get(courseData.getContextPath()+"/rest/professionalinfo/course/"+personid+"",undefined,"json","application/json");
			},
			morecourselist:function(orgpersonid){
				return Ajax.get(courseData.getContextPath()+"/rest/professionalinfo/courselist/"+orgpersonid+"",undefined,"json","application/json");
			},
			enrollstatus:function(courseid1){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/enrollstatus/"+courseid1+"",undefined,"json","application/json");
			},
			relatedcourse:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/related/"+courseid+"",undefined,"json","application/json");
			},
	};
	var model={
			info:function(){
				return controller.info();	
			},
			curriculum:function(){
				return controller.curriculum();
			},
			review:function(){
				var list=controller.review();
				if(list=="NO_CONTENT"){
					return null;
				} else{
					return list;	 
				}
			},
			question:function(){
				var list=controller.question();
				if(list=="NO_CONTENT"){
					return null;
				} else{
					return list;	 
				}
			},
			aboutauthor:function(){
				return controller.aboutauthor($('.coursettl').attr('personid'));
			},
			morecourselist:function(orgpersid){
				return controller.morecourselist(orgpersid);
			},
			enrollstatus:function(courseid1){
				return controller.enrollstatus(courseid1);				
			},
			relatedcourse:function(){
				var list=controller.relatedcourse();
				if(list=="NO_CONTENT"){
					return null;
				} else{
					return list;	 
				}
			},

	};

	var templates={
			info:function(){
				return "{{#each .}}<div class='row'><div class='col-md-12 whitebg pad10 boxshadow'>" +
				"<div class='col-md-8'><div class='row'><div class='col-md-3 pad0 centertext martop10'><img class='height200 width100'" +
				"src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo'>" +
				"<h5 class='badge badge-primary pad10'><span>Price :</span> <span class='icon_price1'  price='{{price}}' priceicon ='"+priceic+"'></span></h5>" +
				"</div><div class='col-md-9'><h5 >{{categoryname}}/{{subcategoryname}}</h5><h4 class='coursettl' " +
				"personid='{{personid}}'>{{coursetitle}}</h4><h6 class='lineheight20 justifytext'>{{coursedescription}}</h6>" +
				"</div></div></div>" +
				"<div class='col-md-4 centertext'>" +
				"<h5>Promo Video</h5>" +
				"<div class='row martop10'>" +
				"<div class='promvideoplayer' data-swf='"+courseData.getContextPath()+"/assets/flowplayer/igplayer.swf' " +
				"data-ratio='0.5' style='width: 95%; height: 180px;'><video data-setup='{'customControlsOnMobile': true}'> " +
				"<source type='video/flv' src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2=/{{coursepromevideopath}}'> " +
				"</source></video></div><a class='btn btn-primary startlearninglinkbtn martop7'><i class='icon-white fa fa-chevron-right marright7'></i>" +
				"Join this Course </a></div></div></div></div></div>{{/each}}";				
			},
			curriculum:function(){
				return "{{#each .}}<div class='boxshadow'> " +
				"<div class='greybgwhiteborder section'>Section : {{sectiontitle}}</div> " +
				"<div class='whitebg pad10'><ul class='timeline'> " +
				"{{#each lectures}}<li class='lecture'><div class='timeline-badge contenticon' learnstatus='{{learnstatus}}' " +
				"contenttype='{{contentytpe}}'><i class='fa fa-play'></i></div> " +
				"<div class='timeline-panel'><div class='timeline-heading'><h5 class='timeline-title'> " +
				"<span class='badge marright7'>{{duration}}</span>" +
				"{{lecturetitle}}</h5></div></div></li>{{/each}}</ul></div></div>{{/each}}";
			},
			review:function(){
				return 	"<div id='review' class='active'><ul class='comments'>{{#if .}}{{#each.}}<li class='clearfix'>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{raterphoto}}&filetype=photo' class='avatar' alt=''>"+
				"<div class='post-comments'><p class='meta'>On {{{ratingdate ratingdate}}} <a >{{raterfname}}</a>" +
				" rated :<span class='ratediv' style='margin-left:10px;' data-score='{{rating}}'></span></p>"+
				"<p style='overflow:hidden;text-overflow:ellipsis;'>{{ratingdescription}}</p></div></li>{{/each}}{{else}}" +
				"<h5 style='text-align:center;'>No Reviews Given</h5>{{/if}}</ul></div>";

			},
			question:function(){
				return 	"<div id='discussion' class=''><ul class='comments'>{{#if .}}{{#each .}}<li class='clearfix'>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{raterphoto}}&filetype=photo' class='avatar' alt=''><div class='post-comments'>" +
				"<p class='meta'>On {{askeddate askeddate}}<a> {{raterfname}} </a> asked : "+
				"</p><p style='overflow:hidden;text-overflow:ellipsis;'>{{question}}<i	class='pull-right'><a href='#answermodal' data-toggle='modal'>" +
				"<small>Answers</small></a></i></p></div></li>{{/each}}{{else}}" +
				"<h5 style='text-align:center;'>No Discussions</h5>{{/if}}</ul></div></div>";
			},
			aboutauthor:function(){
				return "<div class='ligtgreenbg pad1010'>" +
				"<h3 class='martopbottom10 centertext white'>About Author</h3>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{photourl}}&filetype=photo' " +
				"class='height140 border50per marcenterimage' alt='avatar'>" +
				"</div></div><div class='col-md-12'><div class='details padtop50 centertext'><h4>{{authorname}} " +
				"<i class='fa fa-sheild'></i></h4><h5 class='lineheight20'>{{aboutauthor}}</h5>" +
				"{{#if aboutauthor}}<div style='overflow: hidden !important;'><span>{{designation}} @ </span>{{organization}}</div><div>{{award}}</div>{{/if}}" +
				"<div class='martop10 marbottom10'><div class='social'>{{#if facebook}}<a href='http://{{facebook}}'" +
				" target='new' class='[ social-icon facebook ] roundicon fbbg white animate'><i class='fa fa-facebook'>" +
				"</i></a>{{/if}}{{#if twitter}}<a href='http://{{twitter}}' target='new'" +
				" class='[ social-icon twitter ] roundicon twittbg animate white'><i class='fa fa-twitter'></i>" +
				"</a>{{/if}}{{#if linkedin}}<a href='http://{{linkedin}}' target='new' " +
				"class='[ social-icon linkedin ] roundicon linkinbg animate white'><i class='fa fa-linkedin'>" +
				"</i></a>{{/if}}{{#if orgurl}}<a href='http://{{orgurl}}' target='new'" +
				" class='[ social-icon github ] roundicon ligtgreenbg white animate'><i class='fa'>Org</i></a>" +
				"{{/if}}{{else}}</div></div>";
			},
			morecourse:function(){
				return "<form method='post' name='popcourseform'>" +
				"<h5 class='greybgwhiteborder pad10 mar0'>More courses from Author</h5>" +
				"{{#each .}}" +
				"<div class='col-md-12 pad5 whitebg boxshadow popcourselink' " +
				"courseid='{{courseid}}'>" +
				"<div class='col-md-4 pad0'>" +
				"<a><img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo' " +
				"style='height: 87px;'height='120' border='0' class='width100 boxshadow'></a>" +
				"</div>" +
				"<div class='col-md-8 padright0'>" +
				"<a class='categorydescription pad0'> {{coursetitle}}</a>" +
				"<div class='icon_price' price='{{price}}' priceicon ='"+priceic+"' " +
				"style='color:#0E800E;'></div>" +
				"</div>" +
				"</div>" +
				"</div>" +
				"{{/each}}" +
				"<input type='hidden' name='courseid' value='{{courseid}}'/>" +
				"</form>";
			},
			facebookcount:function(){
				return "<div class='facecount centertext martop7'>" +
				"<span class='white btn btn-default fbbg fbcount marright7' rel='tooltip' data-placement='top' " +
				"data-original-title='{{likecountexp}} likes'>{{likecount}}&nbsp;&nbsp;&nbsp;" +
				"<i class='fa fa-thumbs-o-up'></i> </span><span class='marright7 white btn btn-default fbbg fbcount'" +
				"rel='tooltip' data-placement='top' data-original-title='{{commentcountexp}} comments'>" +
				"{{commentcount}}&nbsp;&nbsp;&nbsp;<i class='fa fa-comment-o'></i> </span>" +
				"<span class='marright7 white btn btn-default fbbg fbcount' rel='tooltip' data-placement='top' " +
				"data-original-title='{{sharecountexp}} shares'>{{sharecount}}&nbsp;&nbsp;&nbsp;" +
				"<i class='fa fa-share-alt'></i></span><br></div>";
			},
			relatedcourse:function(){
				return "<form method='post' name='relatedcourseform'>" +
				"<h5 class='greybgwhiteborder pad10 mar0'>Related courses</h5>" +
				"{{#each .}}" +
				"<div class='col-md-12 pad5 whitebg boxshadow relatedcourselink' " +
				"courseid='{{courseid}}'>" +
				"<div class='col-md-4 pad0'>" +
				"<a><img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo' " +
				"style='height: 87px;'height='120' border='0' class='width100 boxshadow'></a>" +
				"</div>" +
				"<div class='col-md-8 padright0'>" +
				"<a class='categorydescription relatedcourselink pad0'courseid=''> {{coursetitle}}</a>" +
				"<div class='icon_price' price='{{price}}' priceicon ='"+priceic+"' " +
				"style='color:#0E800E;'></div>" +
				"<span class='ratediv martop10' data-score='{{averagerating}}'></span>" +
				"</div>" +
				"</div>" +
				"</div>" +
				"{{/each}}" +
				"<input type='hidden' name='courseid' value='{{courseid}}'/>" +
				"</form>";
			},
	};
	var view={
			coursepreview:function(){
				this.info();	
				this.curriculum();
				this.review();
				this.aboutauthor();
				this.relatedcourse();
			},
			discussionevent:function(){
				$('.discussionholder').click(function(){
					var qlist= model.question();
					Handlebars.registerHelper('askeddate', function(askeddate) { 
						var d = new Date(parseInt(askeddate));
						var date =d.toLocaleString();
						return date;
					});
					helper.render(templates.question(),qlist,"#discussionholder");
				});
			},
			scheduleevent:function(){
				$('.scheduleholder').click(function(){
					manageschedule.loadSchedule();
				});
			},
			info:function(){
				var list= model.info();
				helper.render(templates.info(),list,"#infoholder");
				$('.crstitle').text(list[0].coursetitle);
				if(list[0].price=="Free"){
					$('.giftdiv').hide();
				}else{
					$('.giftdiv').show();
				}
				$('.courseid').text(list[0].courseid);
				$('[name=coursetitle]').val(list[0].coursetitle);
				$('[name=courselogo]').val(list[0].courselogo);
				this.morecourse(list[0].orgpersonid);
				$('[name=price]').val(list[0].price);
				$('[name=priceid]').val(list[0].priceid);
				$('.icon_price1').each(function(){
					var price=$(this).attr('price');
					var priceicon = $(this).attr('priceicon');
					if(price=="Free"){
						$(this).html("Free");
					}else if(price==""){
						$(this).html("");
					}else{
						$(this).html("<i class='fa "+priceicon+"' style='margin-right:5px;'></i>"+price);
					}
				});
				$('.promvideoplayer').flowplayer();
				$('.startlearninglinkbtn').click(function(){
					$('[name=previewuserform]').attr('action','coursepayment');
					$('[name=previewuserform]').submit();
				});
			},
			curriculum:function(){
				var list= model.curriculum();
				var modifiedList=helper.curriculum(list);
				helper.render(templates.curriculum(),modifiedList,"#curriculumholder");
				$('.contenticon').each(function(){
					var ctype=$(this).attr('contenttype');
					switch(ctype){
					case "pdf":
						$(this).html("<i class='fa fa-file-pdf-o'></i>");
						break;
					case "video":
						$(this).html("<i class='fa fa-video-camera'></i>");
						break;
					case "swf":
						$(this).html("<img style='height:30px !important;' src='"+courseData.getContextPath()+"/assets/app/images/contenttype/flashicon.png'/>");
						break;
					case "NO_CONTENT":
						console.info("this is NO_CONTENT ");
						break;
					}
				});
				this.summary();
			},
			summary:function(){
				$('.seccount').text($('.section').length);
				$('.leccount').text($('.lecture').length);
				$('.pdfcount').text($('.contenticon[contenttype=pdf]').length);
				$('.vidcount').text($('.contenticon[contenttype=video]').length);
				$('.subcount').text($('[name=coursesubscribers]').val());
			},
			review:function(){
				var list= model.review();
				Handlebars.registerHelper('ratingdate', function(ratingdate) { 
					var d = new Date(parseInt(ratingdate));
					var date =d.toLocaleString();
					return date;
				});
				helper.render(templates.review(),list,"#reviewholder");
				var fivestar,fourstar,threestar,twostar,onestar,zerostar=0;
				var clist=_.pluck(list,'rating');
				if(clist.length!=0){
					var b = (clist.toString()).split(',').map(Number);
					var obj = { };
					for (var i = 0, j = b.length; i < j; i++) {
						obj[b[i]] = (obj[b[i]] || 0) + 1;
					}
					fivestar=Math.round((obj[5]/clist.length)*100);
					if(isNaN(fivestar)){
						$('.fivestar').text(0);
						$('.fiveper').attr('style','width:0%');
					}else{
						$('.fivestar').text(obj[5]);
						$('.fiveper').attr('style','width:'+fivestar+'%');
					}
					fourstar=Math.round((obj[4]/clist.length)*100);
					if(isNaN(fourstar)){
						$('.fourstar').text(0);
						$('.fourper').attr('style','width:0%');
					}else{
						$('.fourstar').text(obj[4]);
						$('.fourper').attr('style','width:'+fourstar+'%');
					}
					threestar=Math.round((obj[3]/clist.length)*100);
					if(isNaN(threestar)){
						$('.threestar').text(0);
						$('.threeper').attr('style','width:0%');
					}else{
						$('.threestar').text(obj[3]);
						$('.threeper').attr('style','width:'+threestar+'%');
					}
					twostar=Math.round((obj[2]/clist.length)*100);
					if(isNaN(twostar)){
						$('.twostar').text(0);
						$('.twoper').attr('style','width:0%');
					}else{
						$('.twostar').text(obj[2]);
						$('.twoper').attr('style','width:'+twostar+'%');
					}
					onestar=Math.round((obj[1]/clist.length)*100);
					if(isNaN(onestar)){
						$('.onestar').text(0);
						$('.oneper').attr('style','width:0%');
					}else{
						$('.onestar').text(obj[1]);	
						$('.oneper').attr('style','width:'+onestar+'%');
					}
					zerostar=Math.round((obj[0]/clist.length)*100);
					if(isNaN(zerostar)){
						$('.zerostar').text(0);	
						$('.zeroper').attr('style','width:0%');
					}else{
						$('.zerostar').text(obj[0]);
						$('.zeroper').attr('style','width:'+zerostar+'%');
					}
					sum = _.reduce(b, function(memo, num){ return memo + num; }, 0);
					var avg=sum/(clist.length);
					$('.totreviews').text(clist.length);
					$('.rating-num').text(avg.toFixed(1));
					$('.avgrate').attr('data-score',avg);
				}
				else{
					$('.rating-num').text(0.0);
					$('.avgrate').attr('data-score',0);
					$('.ownreview').attr('style','display:');
				}
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});
				});
			},
			aboutauthor:function(){
				var list= model.aboutauthor();
				var modifiedauthorlist=helper.aboutauthor(list);
				helper.render(templates.aboutauthor(),modifiedauthorlist,"#authorholder");
			},
			morecourse:function(orgpersonid){
				var courselist = model.morecourselist(orgpersonid);
				if(courselist.length>0){
					$('#morecourseholder').show();
					helper.render(templates.morecourse(),courselist,"#morecourseholder");
					$('.icon_price').each(function(){
						var price=$(this).attr('price');
						var priceic= $('#priceicon').text();
						var priceicon = priceic;
						if(price=="Free"){
							$(this).html("Free");
						}else if(price==""){
							$(this).html("");
						}else{
							$(this).html(price).append("<i class='fa "+priceicon+"' style='margin-right:5px;float:left;margin-top: 3px;'></i>");
						}
					});
					$('.popcourselink').click(function(){
						var list = model.enrollstatus($(this).attr('courseid'));
						if(list==="NO_CONTENT"){
							$('[name=courseid]').val($(this).attr('courseid'));
							$('[name=previewuserform]').attr('action','previewuser');
							$('[name=previewuserform]').submit();
						}else{
							switch(list[0].courseenrollmentstatus){
							case "A":
								$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
								$('[name=previewuserform]').attr('action','previewcourse');
								$('[name=previewuserform]').submit();	
								break;
							case "C":
								$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
								$('[name=previewuserform]').attr('action','previewcourse');
								$('[name=previewuserform]').submit();	
								break;
							case "W":
								$('[name=courseid]').val(courseid);
								$('[name=wishlisted]').val("wishListed");
								$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
								$('[name=previewuserform]').attr('action','previewuser');
								$('[name=previewuserform]').submit();	
								break;
							case "P":
								$("#msgmodal").modal("show");
								break;
							}							
						}
					});
				}
				else{
					$('#morecourseholder').hide();
				}
			},
			relatedcourse:function(){
				var list= model.relatedcourse();
				if(list.length>0){
				helper.render(templates.relatedcourse(),list,"#relatedcourseholder");
				$('.icon_price').each(function(){
					var price=$(this).attr('price');
					var priceic= $('#priceicon').text();

					var priceicon = priceic;
					if(price=="Free"){
						$(this).html("Free");
					}else if(price==""){
						$(this).html("");
					}else{								
						$(this).html(price).append("<i class='fa "+priceicon+"' style='margin-right:5px;float:left;margin-top: 3px;'></i>");
					}
				});
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});
				});
				$('.relatedcourselink').click(function(){
					var list = model.enrollstatus($(this).attr('courseid'));
					if(list==="NO_CONTENT"){
						$('[name=courseid]').val($(this).attr('courseid'));
						$('[name=previewuserform]').attr('action','previewuser');
						$('[name=previewuserform]').submit();
					}else{
						switch(list[0].courseenrollmentstatus){
						case "A":
							$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
							$('[name=previewuserform]').attr('action','previewcourse');
							$('[name=previewuserform]').submit();	
							break;
						case "C":
							$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
							$('[name=previewuserform]').attr('action','previewcourse');
							$('[name=previewuserform]').submit();	
							break;
						case "W":
							$('[name=courseid]').val(courseid);
							$('[name=wishlisted]').val("wishListed");
							$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
							$('[name=previewuserform]').attr('action','previewuser');
							$('[name=previewuserform]').submit();	
							break;
						case "P":
							$("#msgmodal").modal("show");
							break;							
						}							
					}			
				});
				}
			},
	};
	var helper={
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
			curriculum:function(list){
				sections = [];
				sectionids =_.uniq(_.pluck(list,'sectionid'));
				if(sectionids!='' && sectionids!=undefined){
					_.each(sectionids,function(sectionid){
						var sectionarray = _.where(list,{sectionid:sectionid});
						var lectureOutputArray = [];
						var section = {};
						section.sectionid = sectionid;
						section.sectiontitle = sectionarray[0].sectiontitle;
						lectureOutputArray=[];
						var lectureids=_.uniq(_.pluck(sectionarray,'lectureid'));
						_.each(lectureids,function(lectureid){
							lectureArray = _.where(sectionarray,{lectureid:lectureid});
							if(lectureArray[0]){
								var courselectureid=lectureArray[0].lectureid;
								if(courselectureid!=undefined){
									var lecture=_.pick(lectureArray[0],'lectureid','lecturetitle','contentpath','duration');
									var contentype=lectureArray[0].contentpath;
									if(contentype==undefined){
										lecture.contentytpe="nocontent";
										lecture.filename="No Content";
									}
									else{
										var filetypesplit= contentype.split('.');
										var filetype= filetypesplit[filetypesplit.length-1];
										var filenamesplit= contentype.split('/');
										var filename= filenamesplit[filenamesplit.length-1];
										lecture.filename=filename;
										if(filetype=="pdf"){
											lecture.contentytpe="pdf";
										}
										else if(filetype=="swf"){
											lecture.contentytpe="swf";
										}
										else{
											lecture.contentytpe="video";
										}
									}
									lectureOutputArray.push(lecture);
								}
							}
						});
						section.lectures=lectureOutputArray;
						sections.push(section);
					});	
				}			
				return sections;
			},

			aboutauthor:function(workexperienceList){
				var professionaldetails={};
				for(i=0;i<workexperienceList.length;i++){
					professionaldetails.organization=workexperienceList[i].organization;
					professionaldetails.designation=workexperienceList[i].designation;
					professionaldetails.award=workexperienceList[i].award;
					professionaldetails.workexperienceid=workexperienceList[i].workexperienceid;
					professionaldetails.aboutauthor=workexperienceList[i].aboutauthor;
					professionaldetails.photourl=workexperienceList[i].personphoto;
					professionaldetails.authorname=workexperienceList[i].authorname;
					if(workexperienceList[i].url!=null){
						if(workexperienceList[i].url.search("facebook")==4){
							professionaldetails.facebook=workexperienceList[i].url;
						}
						else if(workexperienceList[i].url.search("twitter")==4){
							professionaldetails.twitter=workexperienceList[i].url;
						}
						else if(workexperienceList[i].url.search("linkedin")==4){
							professionaldetails.linkedin=workexperienceList[i].url;
						}
						else{
							professionaldetails.orgurl=workexperienceList[i].url;	
						}
					}
				}
				return professionaldetails;
			}

	};
	return {
		init:function(){
			initialize();
		}
	};	
	function initialize(){
		view.discussionevent();
		view.scheduleevent();
		view.coursepreview();
		$('.giftcoursebtn').click(function(){
			$('[name=previewuserform]').attr('action','giftcourse');
			$('[name=previewuserform]').submit();
		});
		$('.fbcaret').click(function(){
			$("#frep").toggle("slow");
			var facebookurl={};
			facebookurl.link="http://"+$('#metaURI').html()+courseData.getContextPath()+"/preview?courseid="+$('.courseid').text();
			var facebookcountlist=courseData.getFacebookCount(facebookurl);
			var facebookcounttmp=[];
			facebookcounttmp[0]={};
			if(facebookcountlist.length>0){
				facebookcounttmp[0].likecount=facebookcountlist[0].likecount;
				facebookcounttmp[0].sharecount=facebookcountlist[0].sharecount;
				facebookcounttmp[0].commentcount=facebookcountlist[0].commentcount;
				facebookcounttmp[0].totalcount=facebookcountlist[0].totalcount;
			}
			if(facebookcountlist.length==0 || facebookcountlist=="INTERNAL_SERVER_ERROR"){
				facebookcountlist=[];
				facebookcountlist[0]={};
				facebookcountlist[0].likecount=0;
				facebookcountlist[0].sharecount=0;
				facebookcountlist[0].commentcount=0;
				facebookcountlist[0].totalcount=0;				
			};
			var properties=[];
			properties=Object.keys(facebookcountlist[0]);
			for(var i=0;i<properties.length;i++){
				var result=largeBitConversion(facebookcountlist[0][properties[i]]);
				facebookcountlist[0][properties[i]]=result;
			};
			if(facebookcounttmp.length>0){
				facebookcountlist[0].likecountexp=facebookcounttmp[0].likecount;
				facebookcountlist[0].sharecountexp=facebookcounttmp[0].sharecount;
				facebookcountlist[0].commentcountexp=facebookcounttmp[0].commentcount;
				facebookcountlist[0].totalcountexp=facebookcounttmp[0].totalcount;
			}
			helper.render(templates.facebookcount(),facebookcountlist[0],"#frep");

		});
	}
	function largeBitConversion(value){
		var choice=0;
		var enumValue = {
				"BILLION" : "B",
				"MILLION" : "M",
				"LAKH" : "L",
				"THOUSAND" : "K",
		};
		if(value>=1000 & value<100000){
			choice= 1;
		}else if (value>=100000 & value<1000000){
			choice= 2;
		}else if (value>=1000000 & value<10000000){
			choice= 3;
		}else if(value>=100000000) {
			choice= 4;
		};

		switch (choice){ 			
		case 1:		
			return  Math.floor(value/1000)+enumValue.THOUSAND;						
			break;	             
		case 2:					
			return  Math.floor(value/10000)+enumValue.LAKH;						
			break;	                
		case 3:	
			return  Math.floor(value/1000000)+enumValue.MILLION;						
			break;	                
		case 4:	
			return  Math.floor(value/100000000)+enumValue.BILLION;						
			break; 			            
		default:
			return value; 
		}
	}
});