$(".answer-write input[type='submit']").on("click", addAnswer);

function addAnswer(e) {
	e.preventDefault();
	var queryString = $("form[name=answer]").serialize();

	$.ajax({
		type : 'post',
		url : '/api/qna/addAnswer',
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
}

$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();
	deleteBtn = $(this);
	var queryString = deleteBtn.closest("form").serialize();

	$.ajax({
		type : 'post',
		url : '/api/qna/deleteAnswer',
		data : queryString,
		dataType : 'json',
		error : function(request,status,error){
		    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		   },
		success : function(json, status) {
			if (json.status) {
				deleteBtn.closest('article').remove();
			}
		}
	});
}

function onDeleteSuccess(json, status) {
	if (json.status) {
		deleteBtn.closest('article').remove();
	}
}

function onSuccess(json, status) {
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(json.writer,
			new Date(json.createdDate), json.contents, json.answerId,
			json.answerId);
	$(".qna-comment-slipp-articles").append(template);
}

function onError() {
	alert("error");
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};