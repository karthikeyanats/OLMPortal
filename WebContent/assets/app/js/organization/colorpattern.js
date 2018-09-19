Handlebars.registerHelper('color1',function(patternnumber) {
	if(patternnumber==1){
		return new Handlebars.SafeString("" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#34b3a0'>1</a>" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#64C7B9'>1</a>");
	}else if(patternnumber==2){
		return new Handlebars.SafeString("" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#1C88BD'>1</a>" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#6EB1D2'>1</a>");		
	}else if(patternnumber==3){
		return new Handlebars.SafeString("" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#588C73'>1</a>" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#8FA99C'>1</a>");		
	}else if(patternnumber==4){
		return new Handlebars.SafeString("" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#565C63'>1</a>" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#8B8C8E'>1</a>");
	}else if(patternnumber==5){
		return new Handlebars.SafeString("" +
				"<a class='btn btn-default defaultcursor white marright7 pad612' style='background:#8DB87C'>1</a>");
	}
});

var orgcolorpattern=(function(){
	var controller={
			colorpattern:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/colorpattern",undefined,"json","application/json");	
			},
			patterns:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/colorpattern/patterns",undefined,"json","application/json");
			},
			updatecolorpattern:function(id){
				return Ajax.put(courseData.getContextPath()+"/rest/colorpattern/"+id,undefined,"json","application/json");
			}
	};
	var model={
			colorpattern:function(){
				return controller.colorpattern();	
			},
			patterns:function(){
				return controller.patterns();
			},
			updatecolorpattern:function(id){
				return controller.updatecolorpattern(id);
			}
	};

	var templates={
			colorpattern:function(){
				return "<h3 class='marbottom15 martop10 size20'>Colour Pattern</h3>" +
				"{{#each .}}" +
				"<div class='form-horizontal whitebg pad10 manageprofilecont boxshadow marbottom10'>" +
				"<div class='form-group'>" +
				"<label class='col-sm-3 control-label righttext'>Current Colour Pattern : </label>" +
				"<div class='col-sm-7' >" +
				"{{color1 patternnumber}}</div>" +
				"</div>" +
				"<div class='form-group'>" +
				"<label class='col-sm-3 control-label righttext'></label>" +
				"<div class='col-sm-7' >" +
				"<a class='btn btn-blue updatepattern'>Update Colour Pattern</a>" +
				"</div>" +
				"</div>" +
				"<div id='patterns'></div>" +
				"</div>" +
				"{{/each}}";
			},
			patterns:function(){
				return "{{#each .}}" +
				"<div class='form-group'>" +
				"<label class='col-sm-3 control-label righttext'>Pattern {{math @index '+'1}}  : </label>" +
				"<div class='col-sm-7' >" +
				"<span class='marright7'>{{color1 patternnumber}}</span>" +
				"<a class='btn btn-blue applypatternbtn' patid='{{id}}'>Apply</a>" +
				"</div>" +
				"</div>" +
				"{{/each}}" +
				"<div class='form-group'>" +
				"<label class='col-sm-3 control-label righttext'></label>" +
				"<div class='col-sm-7 error' >Note : You need to refresh the page to see the changes</div>" +
				"</div>";
			}
	};
	var view={
			colorpattern:function(){
				var list = model.colorpattern();
				helper.render(templates.colorpattern(),list,"#userdetails");
				$(".updatepattern").click(function(){
					view.patterns();
				});
			},
			patterns:function(){
				var list =  model.patterns();
				helper.render(templates.patterns(),list,"#patterns");
				$('.applypatternbtn').click(function(){
					var idval = $(this).attr('patid');
					swal({   
						title: "Are you sure to update the Pattern ?",  
						animation: "slide-from-top",
						showCancelButton: true,   
						confirmButtonColor: "#DD6B55",  
						confirmButtonText: "Yes, Update it!",   
						closeOnConfirm: false 
					}, 
					function(){   
						view.updatePattern(idval);
					});
				});
			},
			updatePattern:function(id){
				var str = model.updatecolorpattern(id);
				if(str=="OK"){
					swal({   
						title: "Updated!",
						text: "You need to refresh the page to see the changes",
						type:"success",
						animation: "slide-from-top",
						timer: 4000,
						showConfirmButton: false
					});								
				}else{
					swal({   
						title: "Failure !",
						text: "Something went wrong. Please Try Again later",
						type:"error",
						animation: "slide-from-top",
						timer: 4000,
						showConfirmButton: false
					});
				} 
				$('.colorpatternlink').trigger('click');
			}
	};

	var helper={
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			}
	};
	return {
		init:function(){
			initialize();	
		}
	};	
	function initialize(){
		view.colorpattern();
	}		
});