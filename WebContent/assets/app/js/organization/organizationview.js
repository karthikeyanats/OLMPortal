$(document).ready(function(){
	organization.organizationview();
});
organization={
	 organizationview : function(){
		var orglogopath = $("[name=orglog]").val();
		var contextpath	=	$(".contextpath").val();
		$(".orglogopath").attr("src",""+contextpath+"/OpenFile?r1=serverpath&r2="+orglogopath+"&filetype=logo");
	},
};