
$(document).ready(function(){/* jQuery toggle layout */
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
});
// 답변추가
$(".btn btn-success pull-right input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $("form[name=answer]").serialize(); // form data 들을 자동으로 묶어준다.

    $.ajax({
        type : 'post',
        url : '/api/qna/addAnswer',
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onSuccess(json, status){
    // 서버로부터 전달받은 데이터를 json 형태로 잘 왔는지 확인
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
    // 동적 html 생성 필요한 부분에 template 을 붙인다.
    $(".qna-comment-slipp-articles").prepend(template);
    console.log(json);
}

function onError(){
    console.log("error");
}

function showQuestions(e) {
    // e.preventDefault();
    // 서버로 보낼 데이터 없음.

    $.ajax({
        type: 'get',
        url : '/api/questions',
        dataType: 'json',
        error : onError,
        success : onQuestionSuccess
    })
}

function onQuestionSuccess(json, status) {
    var questionTemplate = $("#questionTemplate").html();
    var template = questionTemplate.format(json.title, new Date(json.createdDate), json.writer, json.countOfComment);
    $(".list").prepend(template);
    console.log(json);
}
