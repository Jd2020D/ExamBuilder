
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


  <h1>${theExam.exam.title} Exam</h1>
  <h4 id="remaining-time"  class="alert alert-danger">&nbsp;</h4>
  <div id="exam-date" style="display: none;" >${theExam.exam.examDay}</div>
  <div id="duration" style="display: none;">${theExam.exam.duration}</div>
  <div class="pull-left text-light">
    <button  class="btn btn-light ">
    </button>
    Current Question&nbsp;&nbsp;&nbsp;
    <button class="btn btn-success">
    </button>
    Answerd Question&nbsp;&nbsp;&nbsp;
    <button class="btn btn-warning">
    </button>
    Answer Required
    </div>
    <br/>
  <p class="alert alert-warning" role="alert">Choose the correct answer from the below question </p>
  <div  id="student-questions-div">
    <div id="student-questions-numbers" class="form-row">
    </div>
    <div class="bg-light card  ">
      <c:forEach items="${theExam.studentQuestions}" var="q">
        <div id="collapse_${q.id}" class="collapse " aria-labelledby="heading_${q.id}" data-parent="#student-questions-div">

          <div class="card-body">${q.question.questionText }</div >
          <div class="card-body">
            <c:forEach items="${q.getStudentsAnswers()}" var="aan">
              <div class="form-check">
                <input class="form-check-input" value="${aan.id}" type="radio" name="answer_question_${q.id}" id="answer_${aan.id}" <c:if test="${q.chosedAnswerId ==aan.id }">checked</c:if>>
                <label class="form-check-label text-dark" for="answer_${aan.id}">
                  ${aan.answer.text }
                </label>
              </div>  
            </c:forEach>         
          </div>
        </div>
      </c:forEach>
    </div>
      <form action="/student/${theExam.student.id}/exams/${theExam.exam.id}" method="POST"  style="display: none;" id="submitForm">
        <input type="hidden" name="_method" value="put">
        <input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>            
        <button class="btn btn-danger btn-sm" type="submit" >Submit</button>
      </form>

  </div>

<script>
  var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

  var examFinishDate;
  $("#exam-date").text(function(index,text){
   examFinishDate =new Date(new Date(text).getTime() + ($('#duration').text() * 60000));
})
var timer = setInterval(function(){
  var distance =examFinishDate.getTime()- new Date().getTime();
  if(distance<0){
    $.ajax({
    beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
    url: window.location.href,
    method: 'PUT',
    data: {} ,
    success: function(serverResponse){
    }
   })
  clearInterval(timer);
   window.location.href=window.location.origin+"/student/"+window.location.pathname.split('/')[2]+"/exams"

  }
  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
  var seconds = Math.floor((distance % (1000 * 60)) / 1000);

  $("#remaining-time").text(hours+" H : "+minutes +" M " + seconds+ " S Time Remaining")
},1000);

$("div[id^='collapse']").each(function(index,el){
  if(index===0)
      $(this).addClass('show')
  $("#student-questions-numbers")
    .append('<h5 class="mb-0 m-2"><button id="'+
            $(this).attr("aria-labelledby")+
            '" class="btn '+(index===0?'btn-light':'btn-warning')+
            '" data-toggle="collapse" data-target="#'+$(this).attr('id')+
            '" aria-expanded="'+String(index===0)+
            '" aria-controls="'+$(this).attr('id')+
            '">'+(index+1)+'</button></h5>')
  
})
var checks=function(){
  if(  $('input:checked[name^="answer_question_"]' ).toArray().length===$("button[id^='heading']").toArray().length)
      $("#submitForm").attr("style","")
  $('input:checked[name^="answer_question_"]' ).each(function(index,el){
    var headerSelector="#heading_"+$(this).attr("name").split("answer_question_")[1];
      if(!$(headerSelector).hasClass('btn-light'))
        $(headerSelector).addClass('btn-success').removeClass('btn-warning')
  })
}
checks();

$("button[id^='heading']").click(function(e){
    var buttonId=$(this).attr('id')
  $("button[id^='heading']").each(function(index,el){
  if($(this).attr('id')===buttonId){
    $(this).addClass('btn-light').removeClass('btn-warning btn-success')
  }
  else
    $(this).addClass('btn-warning').removeClass('btn-light')
  checks();
})
})

$('.form-check-input').change(function (e) {
  $.ajax({
    beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
    url: '/student/${theExam.student.id}/answers/'+$(this).attr('value'),
    method: 'POST',
    data: {} ,
    success: function(serverResponse){
    }
   })
  })

</script>
