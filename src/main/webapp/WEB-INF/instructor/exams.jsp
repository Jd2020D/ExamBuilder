
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
            <td><c:out value="${exam.isExtra}"/></td>
            <td><c:out value="${exam.isPublished}"/></td>
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

