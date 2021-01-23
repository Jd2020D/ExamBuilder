
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Exams</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>


<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams" modelAttribute="exam">
    <h1 style="display:inline;">Create Exam
    <p style="font-size:14;margin-left:40px;">             
           <form:label path="isExtra">Is it Extra Exam?:</form:label>
                <form:errors path="isExtra"/>
             <form:checkbox path="isExtra"/>
    </p>
    </h1>
    	
    <p >
        <form:label path="title">Title:</form:label>
                        <form:errors path="title"/>
        
        <span style="display:block;">
        <form:input path="title"/>
        
        </span>
    </p>		
    
    <p>
        <form:label path="markFrom">Mark:</form:label>
                <span style="display:block;">
        
        <form:errors path="markFrom"/>
        <form:input path="markFrom"/>
                </span>
        
    </p>
    <p>
        <form:label path="examDay">Exam Day:</form:label>
                <span style="display:block;">
        
        <form:errors path="examDay"/>
        <form:input type="date" path="examDay"/>
    </p>
    <p>
        <form:label path="examHour">Exam Hour:</form:label>
                <span style="display:block;">
        
        <form:errors path="examHour"/>
        <form:input type="time" path="examHour"/>
                </span>
        
    </p>
    <p>
        <form:label path="duration">Duration:</form:label>
                <span style="display:block;">
        
        <form:errors path="duration"/>
        <form:input path="duration"/>
                </span>
        
    </p>
    <p>
        	
    </p>

    <input type="submit" value="Make new Exam"/>
    
</form:form>
<table>
    <thead>
        <tr>
            <th>id</th>
            <th>Title</th>
            <th>Duration</th>
           	<th>Mark</th>
         	<th>Time</th>
            <th>Date</th>
            <th>Extra</th>
            <th>Published</th>
            <th>Action</th>	
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
            <td>
<a href="/instructor/${instructor.id}/exams/${exam.id}/delete">
Delete
</a>

			</td>
        </tr>
        </c:forEach>
    </tbody>
</table>





</body>
</html>
