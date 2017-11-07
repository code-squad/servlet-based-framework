String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $("form[name=answer]").serialize();

    $.ajax({
        type : 'post',
        url : '/api/qna/addanswer',
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess,
    });
}

function onSuccess(json, status){
	console.log(json);
	  var answerTemplate = $("#answerTemplate").html();
	  var template = answerTemplate.format(json.answer.writer, new Date(json.answer.createdDate), json.answer.contents, json.answer.answerId, json.answer.answerId);
	  $(".qna-comment-slipp-articles").prepend(template);
	}

	function onError(xhr, status) {
	  alert("error");
	}
	
	$(".form-delete").click(deleteAnswer);
	function deleteAnswer(e) {
		e.preventDefault();

		var deleteBtn = $(this);
		var queryString = deleteBtn.closest("form").serialize();

		$.ajax({
		        type : 'post',
		        url : '/api/qna/deleteAnswer',
		        data : queryString,
		        dataType : 'json',
		  error: function (xhr, status) {
		    alert("error");
		  },
		  success: function (json, status) {
		    if (json.status) {
		      deleteBtn.closest('article').remove();
		    }
		  }
		});
		}