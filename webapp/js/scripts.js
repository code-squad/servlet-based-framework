
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

$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    var queryString = $("form[name=answer]").serialize(); // form data 들을 자동으로 묶어준다.

    $.ajax({
        type : 'post',
        url : '/api/qna/addanswer',
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onsuccess
    });
}