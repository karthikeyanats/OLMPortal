$(document).ready(function()
{
	contextpath=$('#context').html();

	$('.faqModalbtn').click(function()
	{
		var courselectureid=$(this).attr('courselectureid');
		$('.askquestionbtn').attr('courselectureid',courselectureid);
		getFaqForLecture(courselectureid);
	});

	$('.takeexambtn').click(function(e)
			{
		var examstatus=$(this).attr('examstatus');
		if(examstatus==="F")
		{
			
			e.preventDefault();
		}
		else
		{
			
			$('[name=courselectureid]').val($(this).attr('courselectureid'));
			$('[name=examform]').attr('action','examWrite');
			$('[name=examform]').submit();	
		}

			});

	$('.faqModalbtn').click(function()
			{
		var courselectureid=$(this).attr('courselectureid');
		$('.askquestionbtn').attr('courselectureid',courselectureid);
		$('#faqModal').modal('show');
		getFaqForLecture(courselectureid);

			});

	$(".ratecontenttab").click(function()
			{
		var courseid=$(this).attr('courseid');
		showratings(courseid);
		$('.reviewsubmitbtn').click(function()
		{
			data={};
			data.courseratingdescription=$('.reviewdesc').val();
			data.coursereview=$('#count-existing').text();
			data.courseid=courseid;
			ajaxhelperwithdata("saveRating", data);
			$('.rateenquiery').hide();
			$('.thanksmsg').show();
		});
	});
});

function quizManageMtd(courselectureid)
{
	$('[name=courselectureidforQuiz]').val(courselectureid);
	$('[name =examform]').attr("action","examManageAction");
	$('[name =examform]').submit();
}

function loadlecturenotes(courselectureid)
{
	var data={};
	data.courselectureid=courselectureid;

	privateNotes=ajaxhelperwithdata("loadprivatelecturenotes", data);

	if(privateNotes!=false)
	{
		renderTemplate('#privatelecturenotestmpl',privateNotes,'#privatelecturenotestable');
	}
	else
	{

		renderTemplate('#privatelecturenotestmpl',privateNotes,'#privatelecturenotestable');
		$('.privatelecturenotesnotDiv').toggle();

	}

	publicNotes=ajaxhelperwithdata("loadpubliclecturenotes", data);

	if(publicNotes!=false)
	{
		renderTemplate('#publiclecturenotestmpl',publicNotes,'#publicNotesTable');
		$('.sharenotesbtn').click(function()
				{


			var data={};
			data.notesid=$(this).attr('notesid');
			output=ajaxhelperwithdata("updateNoteStatus", data);
			loadlecturenotes($(this).attr('courselectureid'));
				});
	}

	else
	{	
		renderTemplate('#publiclecturenotestmpl',publicNotes,'#publicNotesTable');
		$('.sharenotesbtn').click(function()
				{
			var data={};
			data.notesid=$(this).attr('notesid');
			output=ajaxhelperwithdata("updateNoteStatus", data);
			loadlecturenotes($(this).attr('courselectureid'));
				});
		$('.publiclecturenotesnotDiv').toggle();
	}


	$('.submitnotes').click(function()
			{
		var notess=$('.notebook').val();
		$('[name=notedescription]').val($('.notebook').val());
		if(notess!=''){
			saveNotes($(this).attr('courselectureid'),notess);	
		}

		$('.notebook').val('');
			});
}

function saveNotes(courselectureid,notesdescription)
{

	var data={};
	data.courselectureid=courselectureid;
	data.notesdescription=notesdescription;
	ajaxhelperwithdata("saveNotes", data);
	downloadlecture(courselectureid);

} 

function downloadlecture(courselectureid){

	var data={};
	data.courselectureid=courselectureid;
	output=ajaxhelperwithdata("writeNotes", data);
	$('.downloadmaterialsbtn').attr('href',contextpath+"/OpenFile?r1=serverpath&r2="+output+"&r3=download");
	loadlecturenotes(courselectureid);
}

function showratings(courseid)
{
	data={};
	data.courseid=courseid;
	output=ajaxhelperwithdata("getRatings", data);
	if(output!="false") 
	{
		renderTemplate("#ratingstmpl", output, "#ratingstable");
		$('#ratingstable').show	();
	}

	else
	{
		renderTemplate("#ratingsquerytmpl", output, "#ratingsquerytable");
		stars();
		$('.starrr').on('starrr:change', function(e, value){
			$('#count-existing').html(value);
		});
		$('#ratingstable').hide();
	}
}

function getFaqForLecture(courselectureid)
{
	data={};

	data.courselectureid=courselectureid;
	output=ajaxhelperwithdata("getFaqForLecture", data);

	renderTemplate('#allfaqtmpl',output,'#allfaqtable');


	$('.askquestionbtn').click(function()
			{

		var FAQquestion=$('.FAQquestionval').val();

		if(FAQquestion!='')
		{
			savefaqquestion(courselectureid,FAQquestion);
		}
		$('.FAQquestionval').val('');

			});

	$('.answerforfaqbtn').click(function()
			{

		$('#faqanswerstable').show();
		$('.faqquestions').hide();
		getFaqAnswerForLecture($(this).attr('FAQid'));


			}); 


}

function getFaqAnswerForLecture(FAQid)
{

	var data={};
	data.FAQid=FAQid;

	output=ajaxhelperwithdata("getFaqAnswerForLecture", data);
	renderTemplate('#faqanswerstmpl',output,'#faqanswerstable');
	$('.faqanswersubmitbtn').attr('FAQid',FAQid);
	$('.faqanswersubmitbtn').click(function()
			{

		var FAQid= $(this).attr('FAQid');
		var faqanswer=$('.FAQanswerval').val();
		savefaqanswer(FAQid,faqanswer);

			});

	$('.backtoquestionsbtn').click(function()
			{
		$('#faqanswerstable').hide();
		$('.faqquestions').show();
		getFaqForLecture($('.faqModalbtn').attr('courselectureid'));

			});

}
function savefaqquestion(courselectureid,FAQquestion)
{
	var data={};
	data.courselectureid=courselectureid;
	data.FAQquestion=FAQquestion;
	ajaxhelperwithdata("savefaqquestion", data);
	getFaqForLecture(courselectureid);

}


function savefaqanswer(FAQid,faqanswer)
{
	var data={};
	data.FAQid=FAQid;
	data.faqanswer=faqanswer;
	ajaxhelperwithdata("savefaqanswer", data);
	getFaqAnswerForLecture(FAQid);

}

function stars(){
	//Starrr plugin (https://github.com/dobtco/starrr)
	var __slice = [].slice;

	(function($, window) {
		var Starrr;

		Starrr = (function() {
			Starrr.prototype.defaults = {
					rating: void 0,
					numStars: 5,
					change: function(e, value) {}
			};

			function Starrr($el, options) {
				var i, _, _ref,
				_this = this;

				this.options = $.extend({}, this.defaults, options);
				this.$el = $el;
				_ref = this.defaults;
				for (i in _ref) {
					_ = _ref[i];
					if (this.$el.data(i) != null) {
						this.options[i] = this.$el.data(i);
					}
				}
				this.createStars();
				this.syncRating();
				this.$el.on('mouseover.starrr', 'span', function(e) {
					return _this.syncRating(_this.$el.find('span').index(e.currentTarget) + 1);
				});
				this.$el.on('mouseout.starrr', function() {
					return _this.syncRating();
				});
				this.$el.on('click.starrr', 'span', function(e) {
					return _this.setRating(_this.$el.find('span').index(e.currentTarget) + 1);
				});
				this.$el.on('starrr:change', this.options.change);
			}

			Starrr.prototype.createStars = function() {
				var _i, _ref, _results;

				_results = [];
				for (_i = 1, _ref = this.options.numStars; 1 <= _ref ? _i <= _ref : _i >= _ref; 1 <= _ref ? _i++ : _i--) {
					_results.push(this.$el.append("<span class=icon-star-empty'></span>"));
				}
				return _results;
			};

			Starrr.prototype.setRating = function(rating) {
				if (this.options.rating === rating) {
					rating = void 0;
				}
				this.options.rating = rating;
				this.syncRating();
				return this.$el.trigger('starrr:change', rating);
			};

			Starrr.prototype.syncRating = function(rating) {
				var i, _i, _j, _ref;

				rating || (rating = this.options.rating);
				if (rating) {
					for (i = _i = 0, _ref = rating - 1; 0 <= _ref ? _i <= _ref : _i >= _ref; i = 0 <= _ref ? ++_i : --_i) {
						this.$el.find('span').eq(i).removeClass('icon-star-empty').addClass('icon-star');
					}
				}
				if (rating && rating < 5) {
					for (i = _j = rating; rating <= 4 ? _j <= 4 : _j >= 4; i = rating <= 4 ? ++_j : --_j) {
						this.$el.find('span').eq(i).removeClass('icon-star').addClass('icon-star-empty');
					}
				}
				if (!rating) {
					return this.$el.find('span').removeClass('icon-star').addClass('icon-star-empty');
				}
			};

			return Starrr;

		})();
		return $.fn.extend({
			starrr: function() {
				var args, option;

				option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
				return this.each(function() {
					var data;

					data = $(this).data('star-rating');
					if (!data) {
						$(this).data('star-rating', (data = new Starrr($(this), option)));
					}
					if (typeof option === 'string') {
						return data[option].apply(data, args);
					}
				});
			}
		});
	})(window.jQuery, window);
	$(function() 
			{
		return $(".starrr").starrr();
			});
}