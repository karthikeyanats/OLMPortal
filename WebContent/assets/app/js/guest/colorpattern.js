$(function(){ 
	guest.orgcolorpattern.init();
});

var guest = guest || {};

guest.orgcolorpattern = (function(){
	var controller={
			colorpattern:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/colorpattern",undefined,"json","application/json");	
			}
	};
	var model={
			colorpattern:function(){
				return controller.colorpattern();	
			}
	};
	var view={
			colorpattern:function(){
				var list = model.colorpattern();
				var colorpatterval = list[0].patternid;
				var colorval=null;
				try{
					if(_.isNumber(parseInt(colorpatterval))){
						if(colorpatterval=="1"){
							colorval = "default";
						}else{
							colorval = parseInt(colorpatterval)-parseInt("1");
						}
					}else{
						colorval = "default";
					}
				}catch(e){
					console.info("catch");
					colorval = "default";
				}
				$("<link/>", {
					rel: "stylesheet",
					type: "text/css",
					href: "assets/app/css/colorstyle/"+colorval+".css"
				}).appendTo("head");
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
})();