
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>Exam Students</title>
		
</head>
<body>


<h1> ${thisExam.title }</h1>
<div class="form-row">
	<a class="btn btn-warning text-dark h-25" href="/instructor/${instructor.id}/exams/${thisExam.id}">Exam Settings </a>
	<p class="text-warning m-3 font-weight-bold">Note : You can'nt add students to unpublished exam</p>
</div>

<h2>Joined Students</h2>
<table class="table table-light">
	<thead class="thead-dark">
		<tr>
			<th scope="col">ID</th>
			<th scope="col">Name</th>
			<th scope="col">Mark</th>
			<th scope="col">Actions</th>            
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${thisExam.getStudentExam()}" var="stuExam">
	<tr>
		<td>${stuExam.getStudent().getId()}</td>
		<td>${stuExam.getStudent().getUsername()}</td>
		<td>${stuExam.getTotalMarks()}</td>
		<td>
			<form action="/instructor/${instructor.id}/exams/${thisExam.id}/students/${stuExam.getStudent().getId()}" method="POST">
				<input type="hidden" name="_method" value="delete">
				<input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>            
				<button class="btn btn-danger " type="submit" >Delete</button>
			</form>	
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
<h2>All Students</h2>
<form:form  method="POST" action="/" modelAttribute="publishExam">
	    <p>
             <form:errors class="text-danger" path="isPublished"/>
    
    	</p>
</form:form>
<table class="table table-light">
	<thead class="thead-dark">
		<tr>
			<th scope="col">ID</th>
			<th scope="col">Name</th>
			<th scope="col">Actions</th>            
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${allStudents}" var="stu">
	<tr>
		<td>${stu.getId()}</td>
		<td>${stu.getUsername()}</td>
		<td>
			<form action="/instructor/${instructor.id}/exams/${thisExam.id}/students/${stu.getId()}" method="POST">
				<input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>            
				<button type="submit" class="btn btn-success">Add</button>
			</form>	
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>

</body>
</html>
