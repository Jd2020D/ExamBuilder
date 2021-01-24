
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="pl-3">
<h1> ${thisExam.title }</h1>
<form:form  method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/publish" modelAttribute="publishExam">
   <div style="display: none">
    <p>             
			    
    <label style="visibility: hidden">s</label>
   <span style="display:block;">
      
    <form:label  path="isExtra">Extra <form:checkbox path="isExtra"/></form:label>
    </span>
    </p>
    	
    <p >
        <form:label path="title">Title: <form:errors path="title"/></form:label>
        <span style="display:block;">
        <form:input path="title"/>
        
        </span>
    </p>		
    
    <p>
        <form:label path="markFrom">Mark: <form:errors path="markFrom"/></form:label>
                <span style="display:block;">
        
        
        <form:input path="markFrom"/>
                </span>
        
    </p>
    <p>
        <form:label path="examDay">Exam Day: <form:errors path="examDay"/></form:label>
                <span style="display:block;">
        
        
        <form:input type="date" path="examDay"/>
    </p>
    <p>
        <form:label path="examHour">Exam Hour: <form:errors path="examHour"/></form:label>
                <span style="display:block;">
        
        
        <form:input type="time" path="examHour"/>
                </span>
        
    </p>
    <p>
        <form:label path="duration">Duration: <form:errors path="duration"/></form:label>
                <span style="display:block;">
        
        
        <form:input path="duration"/>
                </span>
        
    </p>
        </div>
    <c:choose>
		    <c:when test="${thisExam.isPublished == true}">
				<input class="btn btn-warning " type="submit" value="unPublish"/>
		    </c:when>
		    <c:otherwise>
				<input class="btn btn-warning" type="submit" value="Publish"/>
		    </c:otherwise>
	</c:choose>
		
     
     
    <p>
             <form:errors path="isPublished"/>
    
    </p>
    
</form:form>

<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/update" modelAttribute="exam" >
    <p>             
			    
    <label style="visibility: hidden">s</label>
   <span style="display:block;">
      
    <form:label path="isExtra">Extra <form:checkbox path="isExtra"/></form:label>       
    </span>
    </p>
    	
    <p >
        <form:label path="title">Title: <form:errors path="title"/></form:label>
        <span style="display:block;">
        <form:input path="title"/>
        
        </span>
    </p>		
    
    <p>
        <form:label path="markFrom">Mark: <form:errors path="markFrom"/></form:label>
                <span style="display:block;">
        
        
        <form:input path="markFrom"/>
                </span>
        
    </p>
    <p>
        <form:label path="examDay">Exam Day: <form:errors path="examDay"/></form:label>
                <span style="display:block;">
        
        
        <form:input type="date" path="examDay"/>
    </p>
    <p>
        <form:label path="examHour">Exam Hour: <form:errors path="examHour"/></form:label>
                <span style="display:block;">
        
        
        <form:input type="time" path="examHour"/>
                </span>
        
    </p>
    <p>
        <form:label path="duration">Duration: <form:errors path="duration"/></form:label>
                <span style="display:block;">
        
        
        <form:input path="duration"/>
                </span>
        
    </p>
    <p>
    <label style="visibility: hidden">s</label>
                    <span style="display:block;">
  
     <input class="btn btn-primary" type="submit" value="Edit exam"/>
                     </span>
     
    </p>

    
</form:form>
<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id}/addq" modelAttribute="question">
        <form:label path="questionText">Question:</form:label>
        <form:input path="questionText"/>
    <input class="btn btn-primary" type="submit" value="Add Question"/>
	<c:if test="${erroredQuestion.id == q.id}">
		<td><form:errors path="questionText"/></td>
	</c:if>
    
</form:form>
    <c:forEach items="${thisExam.questions}" var="q">
            <section>
            <form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/update" modelAttribute="question">

            	<table>

            		<tr>
            			<td><form:input path="questionText" value= "${q.questionText }"/></td>

                        <td><input class="btn btn-primary btn-sm" type="submit" value="Edit"/></td>
            			<td><a name="question_${q.id}"></a><a class="btn btn-danger btn-sm"  href="/instructor/${instructor.id }/exams/${thisExam.id }/questions/${q.id }/delete"> Delete</a></td>
                		<c:if test="${erroredQuestion.id == q.id}">
                		<td><form:errors path="questionText"/></td>
                		</c:if>
            		</tr>
            	</table>
            
             </form:form>

                 <table >
                	<tr>
                	<td>is Correct</td>
                	</tr>
                	<tr>	
                	<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/adda" modelAttribute="answer">
                		<td><form:checkbox path="isCorrect"/></td>										       
                		<td><form:input path="text"/></td>
                		<td><input class="btn btn-primary btn-sm" type="submit" value="Add"/></td>
                		<c:if test="${answerQuestion.id == q.id}">
                		<td><form:errors path="text"/></td>
                		</c:if>
                	</form:form>
                	</tr>	
                	
                    <c:forEach items="${q.answers}" var="a">	
                		<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/answers/${a.id}/update" modelAttribute="answer">
                						<tr>
                						<c:choose>
										    <c:when test="${a.isCorrect == true}">
												<td><form:checkbox path="isCorrect"   checked="checked" /></td>										       
										    </c:when>
										    <c:otherwise>
												<td><form:checkbox path="isCorrect"  /></td>										     
										    </c:otherwise>
										</c:choose>
                						<td><form:input path="text" value="${a.text }" /></td>
                						<td><input class="btn btn-primary btn-sm" type="submit" value="Edit"/></td>
                						<td><a name="answer_${a.id}"></a><a class="btn btn-danger btn-sm"  href="/instructor/${instructor.id }/exams/${thisExam.id }/questions/${q.id }/answers/${a.id }/delete">Delete</a></td>
                						<c:if test="${erroredAnswer.id == a.id}">
										<td><span id="text.errors">size must be between 1 and 100</span></td>				
										</c:if>
                						</tr>	        
                		</form:form>
                		
                	</c:forEach>	
                </table>
                	
                
                
            
            </section>
    </c:forEach>


</div>