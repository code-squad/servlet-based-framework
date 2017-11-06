
$(document).ready(function(){/* jQuery toggle layout */
	String.prototype.format = function() {
		var args = arguments;
		return this.replace(/{(\d+)}/g, function(match, number) {
			return typeof args[number] != 'undefined' ? args[number] : match;
		});
	};
	$('#btnToggle').click(function(){
	  if ($(this).hasClass('on')) {
	    $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
	    $(this).removeClass('on');
	  }
	  else {
	    $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
	    $(this).addClass('on');
	  }
	});
	$(".form-delete").click(removeAnswer);
	$(".answerWrite input[type=submit]").click(addAnswer);
	function addAnswer(e) {
	    e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.
	    var queryString = $("form[name=answer]").serialize(); //form data들을 자동으로 묶어준다. 
	    $.ajax({
	        type : 'post',
	        url : '/api/qna/addanswer',
	        data : queryString,
	        dataType : 'json',
	        error: onError,
	        success : onSuccess,
	    });
	}
	function onError(json, status){
		console.log(json);
	}
	function onSuccess(json, status){
		var answerTemplate = $("#answerTemplate").html();
		console.log(json.writer);
		var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId, json.answerId);
		$(".qna-comment-slipp-articles").prepend(template);
		$("input[name=writer]").val("");
		$("textarea[name=contents]").val("");
	}
	function removeAnswer(e) {
		e.preventDefault();
		var deleteBtn = $(this);
		var url = $(this).closest('form').attr('action');
		var queryString = $(this).closest('form').serialize();
		if (confirm('삭제하시겠습니까?')) {
			$.ajax({
				type : 'POST',
				url : url,
				data : queryString,
				dataType : 'json',
				error : function(data){
					console.log(data);
				},
				success : function(data) {
					console.log(data);
					if (data.status) {
						deleteBtn.closest('article').remove();
					}
				}
			});
		}
	}
});