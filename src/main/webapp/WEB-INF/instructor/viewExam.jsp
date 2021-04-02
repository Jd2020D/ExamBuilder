
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>




<h1> ${thisExam.title }</h1>
<form:form  method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/publish" modelAttribute="publishExam">
    <div style="display: none;">
        <%@ include file = "examFormInputs.jsp" %>
            </div>
        </div>
    </div>
<c:choose>
		    <c:when test="${thisExam.isPublished == true}">
				<input class="btn btn-danger" type="submit" value="unPublish"/>			
                <a  class="btn btn-info text-light" href="/instructor/${instructor.id }/exams/${thisExam.id }/students">Students</a> 
		    </c:when>
		    <c:otherwise>
                
				<input type="submit" class="btn btn-success" value="Publish"/>
                <a  class="btn btn-info text-light" href="/instructor/${instructor.id }/exams/${thisExam.id }/students">Students</a>

				 <p class="text-warning"><c:out value="${examUnPublish}"/></p>
		    </c:otherwise>
	</c:choose>


     <p class="text-warning mt-2">${thisExam.studentExam.size()} Students</p>
    <p>
             <form:errors class="text-warning" path="isPublished"/>
    
    </p>
    
</form:form>
<div class="form-row" id="editExamSec">
      <button style="color: black;" class="col-md-12 btn btn-warning collapsed" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo" id="headingTwo">
        <h5 class="mb-0">
            Edit Exam
        </h5>
    </button>
      <div id="collapseTwo" class="collapse border-right border-bottom border-left border-warning mt-2 col-md-12" aria-labelledby="headingTwo" data-parent="#editExamSec">
            <form:form  method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/update" modelAttribute="exam" >
            <%@ include file = "examFormInputs.jsp" %>
                    <input class="btn btn-warning text-dark"   type="submit" value="Update"/>  
                </div>
            </div>
        
        </form:form>
      </div>
    </div>
<br/>
<form:form class="w-100" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id}/addq" modelAttribute="question">
            <div class="form-row">
                <form:label class="col-md-1.5"   path="questionText">New Question: &nbsp;
                    <div style="font-size:0.8rem;" class="text-success font-weight-bold">${thisExam.questions.size()} Questions</div>
                </form:label>
                <div class="col-md-3">
                    <form:input class="form-control " path="questionText"/>
                    <div class="text-danger">
                        <c:if test="${erroredQuestion.id == q.id}">
                            <form:errors path="questionText"/>
                        </c:if>
                    </div>              
                </div>
                <input class="btn btn-success col-md-1 h-25 " type="submit" value="Add"/>        
            </div>
    
</form:form>
    <c:forEach items="${thisExam.questions}" var="q">
            <section class="border-bottom border-left  border-warning p-2 m-2">
            <form:form  method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/update" modelAttribute="question">
                <div class="form-row"> 
                            <div class="col-md-3">
                                <c:if test="${erroredQuestion.id == q.id}">
                                    <form:input class="form-control is-invalid" path="questionText" value= "${q.questionText }"/>
                                    <div class="invalid-feedback">
                                            <form:errors path="questionText"/>
                                    </div>  
                                </c:if>   
                                <c:if test="${erroredQuestion.id != q.id}">
                                    <form:input class="form-control" path="questionText" value= "${q.questionText }"/>
                                    <div id="question_${q.id}_answers_count" class="text-warning" style="font-size: 0.8rem;">
                                    </div> 
                                </c:if>            
                            </div>
                            <input class="btn btn-warning text-dark col-md-1 h-25 mr-2" type="submit" value="Edit"/>
                            <a id="deleteQuestionBtn" href="/instructor/${instructor.id }/exams/${thisExam.id }"  url="/instructor/${instructor.id }/exams/${thisExam.id }/questions/${q.id }" class="btn btn-danger col-md-1 h-25 mr-2" type="submit" >Delete</a>
                            <a name="question_${q.id}"></a>
                            <a id="viewAnswers" class="btn btn-secondary h-25 col-md-1" data-toggle="collapse" href="#question_${q.id}_answers" role="button" aria-expanded="true" aria-controls="question_${q.id}_answers">Hide</a>
                            
                </div>
            </form:form>	
                 <div class="collapse show" id="question_${q.id}_answers">
                	<label>is Correct</label>
                	<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/adda" modelAttribute="answer">
                		<form:checkbox path="isCorrect"/>
                        <div class="col-md-4">
                            <c:if test="${answerQuestion.id == q.id}">
                                    <form:input class="form-control is-invalid" path="text"/>
                                    <div class="invalid-feedback">
                                        <form:errors path="text"/>
                                    </div> 
                            </c:if>   
                            <c:if test="${answerQuestion.id != q.id}">
                                <form:input class="form-control" path="text"/>
                            </c:if>
                        </div>										       
                        <input class ="btn btn-success ml-2 h-25" type="submit" value="Add Answer"/>           
                	</form:form>
                	</tr>	
                	
                    <c:forEach items="${q.answers}" var="a">	
                		<form:form style="display:flex;" method="POST" action="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/answers/${a.id}/update" modelAttribute="answer">
                                    <c:choose>
                                        <c:when test="${a.isCorrect == true}">
                                            <form:checkbox path="isCorrect"   checked="checked" />										       
                                        </c:when>
                                        <c:otherwise>
                                            <form:checkbox path="isCorrect"  />										     
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="col-md-3">
                                        <c:if test="${erroredAnswer.id == a.id}">
                                            <form:input class="form-control is-invalid" path="text"/>
                                                <div class="invalid-feedback">
                                                    <span class="text-danger" id="text.errors">size must be between 1 and 100</span>
                                                </div> 
                                        </c:if>   
                                        <c:if test="${erroredAnswer.id != a.id}">
                                            <form:input class="form-control" path="text" value="${a.text }"/>
                                        </c:if>                
                                    </div>
                                        <input class="btn btn-warning ml-2 text-dark h-25" type="submit" value="Edit"/>
                                        <a id="deleteAnswerBtn" href="/instructor/${instructor.id}/exams/${thisExam.id }/#question_${q.id}" url="/instructor/${instructor.id}/exams/${thisExam.id }/questions/${q.id }/answers/${a.id}" class="btn btn-danger ml-1 h-25" type="submit" >Delete</a>
                                        <a name="answer_${a.id}"></a>
                        </form:form>
                	</c:forEach>	
                </div>
                	
                
                
            
            </section>
            
    </c:forEach>

<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $("a#deleteQuestionBtn").click(function(e){
        $.ajax({
                beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
                url:$(this).attr("url"),
                method: 'DELETE',
                success: function(serverResponse){
                    console.log(serverResponse)
                },
                error:function(error){
                }
            })  
    })
    $("a#deleteAnswerBtn").click(function(e){
        $.ajax({
                beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
                url:$(this).attr("url"),
                method: 'DELETE',
                success: function(serverResponse){
                    $("a[name='answer_"+serverResponse+"']").closest("form").remove();
                },
                error:function(error){
                }
            })  
    })
    $("a#viewAnswers").click(function(e){
        if($(this).text()=="Hide")
            $(this).text("View");
        else
            $(this).text("Hide")
    })
    var matches = $("div[id$='_answers']").each(function(index,el){
        var wrongs=0;
        var correct =0;
        $("#"+el.id+" input[type=checkbox]").each(function(index,box){
            if($(this).prop("checked"))
                correct++;
            else
                wrongs++;
        })
        if(wrongs>0)
            wrongs--;
        $("#"+el.id+"_count").html("<span class ='text-success font-weight-bold'>"+correct+" Correct</span> , <span class ='text-danger font-weight-bold'>"+wrongs+" Wrong "+"Answers</span>");

    });
    var errorExist=false;
    $("form#exam input").map(function(item,index){
    if($("#"+$(this).attr("id")+"\\.errors").val()!==undefined){
        $(this).addClass("is-invalid");
        errorExist=true;
    }
    else {
        $(this).removeClass("is-invalid")

    }
    });
    if(errorExist){
        $("#headingTwo").removeClass("btn-warning text-dark").addClass("btn-danger text-light").text("Edit Exam (Error Found)")
    }
    else{
        $("#headingTwo").removeClass("btn-danger text-light").addClass("btn-warning text-dark").text("Edit Exam")

    }

</script>
