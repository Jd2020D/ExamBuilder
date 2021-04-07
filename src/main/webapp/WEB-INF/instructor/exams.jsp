
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form class="needs-validation"   method="POST" action="/instructor/${instructor.id}/exams" modelAttribute="exam" >
<h1>Create Exam</h1>
    <%@ include file = "examFormInputs.jsp" %>
        <input class="btn btn-success" type="submit" value="Make new Exam"/>  
      </div>
    </div>
    
</form:form>
<div class="overflow-auto table-light">
  <table class="table">
    <thead class="thead-dark">
        <tr>
            <th>id</th>
            <th>Title</th>
            <th>Duration</th>
           	<th>Mark</th>
         	<th>Time</th>
            <th>Date</th>
            <th>Extra</th>
            <th>Published</th>
            <th>Actions</th>	
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${instructor.exams}" var="exam">
        <tr>
            <td><c:out value="${exam.id}"/></td>
            <td><a href="/instructor/${instructor.id }/exams/${exam.id }"><c:out value="${exam.title}"/></a></td>
            <td><c:out value="${exam.duration}"/> Minutes</td>
            <td><c:out value="${exam.markFrom}"/></td>
            <td><c:out value="${exam.getExamTime()}"/></td>
            <td><c:out value="${exam.getExamDate()}"/></td>
            <td >
              <c:choose>
                <c:when test="${exam.isExtra}">
                  <svg class="text-success" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                  </svg>                
                </c:when>    
                <c:otherwise>
                  <svg class="text-danger" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                  </svg>
                </c:otherwise>
            </c:choose>
            </td>
            <td >
              <c:choose>
                <c:when test="${exam.isPublished}">
                  <svg class="text-success" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                  </svg>                
                </c:when>    
                <c:otherwise>
                  <svg class="text-danger" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                  </svg>
                </c:otherwise>
            </c:choose>
            </td>
            <td >
              <form id="delete-form"  action="/instructor/${instructor.id}/exams/${exam.id}" method="post">
                <a class="btn btn-secondary  btn-sm" href="/instructor/${instructor.id }/exams/${exam.id }">View</a>
                <a class="btn btn-warning  btn-sm" href="/instructor/${instructor.id }/exams/${exam.id }">Edit</a>  
                <input type="hidden" name="_method" value="delete">
                <input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>            
                <button class="btn btn-danger btn-sm" type="submit" >Delete</button>
              </form>
        
			</td>
        </tr>
        </c:forEach>
    </tbody>
</table>


</div>
<script>
  $("form input").map(function(item,index){
  if($("#"+$(this).attr("id")+"\\.errors").val()!==undefined){
    $(this).addClass("is-invalid")
  }
  else {
    $(this).removeClass("is-invalid")
  }
});

</script>

