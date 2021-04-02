
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Exams</h1>
<p class="text-danger">${JoinErrorMessage}</p>
<table class="table table-light">
  <thead class="thead-dark">

    <tr>
      <th scope="col">ID</th>
      <th scope="col">Title</th>
      <th scope="col">Duration</th>
      <th scope="col">Out Of</th>
      <th scope="col">Time</th>
      <th scope="col">Date</th>
      <th scope="col">Extra</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
            <c:forEach items="${nonSubmittedExams}" var="stuExam">
    <c:if test="${stuExam.exam.isPublished}">
      <tr>
        <th  scope="row"><c:out value="${stuExam.exam.id}"/></th>
        <td><c:out value="${stuExam.exam.title}"/></td>
        <td class="exam-duration"><c:out value="${stuExam.exam.duration}"/> Minutes</td>
        <td><c:out value="${stuExam.exam.markFrom}"/></td>
        <td class="exam-hour"><c:out value="${stuExam.exam.getExamTime()}"/></td>
        <td class="exam-date"><c:out value="${stuExam.exam.getExamDate()}"/></td>
        <td><c:out value="${stuExam.exam.isExtra}"/></td>
        <td>
            <form action="/student/${stuExam.student.id}/exams/${stuExam.exam.id}" method="POST">
              <a href="/student/${student.id }/exams/${stuExam.exam.id }" class="btn btn-success text-light font-weight-bold">JOIN</a>
              <c:if test="${stuExam.exam.isExtra}">
                <input type="hidden" name="_method" value="delete">
                <input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>
                <button class="btn btn-danger" type="submit" >WITHDRAW</button>
              </c:if>
            </form>

        </td>
      </tr>
      <tr  class='  m-0 ml-2 p-0 font-weight-bold '> 
        <td  role="alert"   id="${stuExam.exam.id}_time-display" colspan='8'></td>
        <td id="${stuExam.exam.id}_duration" colspan='8' style="display: none;">${stuExam.exam.duration}</td>
        <td exam-id="${stuExam.exam.id}" id="${stuExam.exam.id}_date" colspan='8' style="display: none;">${stuExam.exam.examDay}</td>
      </tr>

    </c:if>
    </c:forEach>
  </tbody>
</table>

<h1>Results</h1>
<table class="table table-light">
  <thead class="thead-dark">

    <tr>
      <th scope="col">ID</th>
      <th scope="col">Title</th>
      <th scope="col">Mark</th>
      <th scope="col">Out Of</th>
      <th scope="col">Extra</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
            <c:forEach items="${submittedExams}" var="stuExam">
      <tr>
        <th scope="row"><c:out value="${stuExam.exam.id}"/></th>
        <td><c:out value="${stuExam.exam.title}"/></td>
        <td><c:out value="${stuExam.totalMarks}"/></td>
        <td><c:out value="${stuExam.exam.markFrom}"/></td>
        <td><c:out value="${stuExam.exam.isExtra}"/></td>
        <td>
            <form action="/student/${stuExam.student.id}/exams/${stuExam.exam.id}" method="POST">
              <c:if test="${stuExam.exam.isExtra}">
                <input type="hidden" name="_method" value="delete">
                <input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>
                <button class="btn btn-danger" type="submit" >WITHDRAW</button>
              </c:if>
            </form>

        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<script>
  var isToday = function(date)  {
  var today = new Date();
  return date.getDate() == today.getDate() &&
    date.getMonth() == today.getMonth() &&
    date.getFullYear() == today.getFullYear();
}


  $("td[id$='_date']").each(function(index,el){
    if(isToday(new Date($(this).text()))){
      var examId=$(this).attr("exam-id");
      var examStartTime;
      var examFinishTime;
      var currentTime;
      var distance;
      var prefix;
      $("#"+examId+"_time-display").addClass('alert-danger');
      var timer= setInterval(()=> {
        examStartTime=new Date($(this).text()).getTime();
        examFinishTime=new Date(examStartTime + ($("#"+examId+"_duration").text() * 60000)).getTime();
        currentTime=new Date().getTime();
        if(examStartTime>currentTime){
          distance=examStartTime-currentTime;
          state=0;
        }
        else if(examFinishTime>currentTime){
          distance=examFinishTime-currentTime;
          state=1;
        }
        else {
          state=2;
          clearInterval(timer);
        }
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);
        $("#"+examId+"_time-display").text(state===0?hours+" H : "+minutes +" M " + seconds+ " S Time Remaining to start!":state===1?"Exam will end in "+hours+" H : "+minutes+" M "+seconds+" S ":"Exam Timout!")
      },1000);

    }
  })

</script>
