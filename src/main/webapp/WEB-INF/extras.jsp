
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<h1>Extra Exams</h1>
<table class="table table-light">
  <thead class="thead-dark">

    <tr>
      <th scope="col">ID</th>
      <th scope="col">Title</th>
      <th scope="col">Time</th>
      <th scope="col">Duration</th>
      <th scope="col">Date</th>
      <th scope="col">Out Of</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
            <c:forEach items="${extraExams}" var="extraExam">
    <c:if test="${extraExam.isPublished}">
      <tr>
        <th  scope="row"><c:out value="${extraExam.id}"/></th>
        <td><c:out value="${extraExam.title}"/></td>
        <td class="exam-hour"><c:out value="${extraExam.getExamTime()}"/></td>
        <td class="exam-duration"><c:out value="${extraExam.duration}"/> Minutes</td>
        <td class="exam-date"><c:out value="${extraExam.getExamDate()}"/></td>
        <td><c:out value="${extraExam.markFrom}"/></td>

        <td>
          <security:authorize access="hasAnyRole('STUDENT')"> 
            <form action="/student/extras/${extraExam.id}" method="POST">
              <input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>
              <button  class="btn btn-success text-light font-weight-bold">ADD</button>
            </form>
          </security:authorize>                
        </td>
      </tr>
      <tr  class='  m-0 ml-2 p-0 font-weight-bold '> 
        <td  role="alert"   id="${extraExam.id}_time-display" colspan='9'></td>
        <td id="${extraExam.id}_duration" colspan='9' style="display: none;">${extraExam.duration}</td>
        <td exam-id="${extraExam.id}" id="${extraExam.id}_date" colspan='9' style="display: none;">${extraExam.examDay}</td>
      </tr>

    </c:if>
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
      $("#"+examId+"_time-display").addClass('alert-warning');
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
