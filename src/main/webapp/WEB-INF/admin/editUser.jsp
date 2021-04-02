
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
    <div class="col-4 mx-auto mt-5">
        <div class="login-panel panel panel-success">

            <div class="panel-body">
                <div class="panel-heading">
                    <h1>Edit</h1>
                </div>


                <p>
                <c:forEach items="${errors}" var="error">
                   ${error.getField()} : ${error.getDefaultMessage()} 
                    <c:out value="${error.defaultMessage}" />
                </c:forEach>
                </p>

<form:form method="POST" action="/admin/editUser/" modelAttribute="user">
    <input type="hidden" name="_method" value="put">

    <form:hidden path="id" />

    <p>
        <form:label path="username">Username:</form:label>
        <form:input path="username"/>
    </p>
    <p>
        <form:label path="password">Password:</form:label>
        <form:password path="password"/>
    </p>
    <p>
        <form:label path="passwordConfirmation">Password Confirmation:</form:label>
        <form:password path="passwordConfirmation"/>
    </p>

    <div class="form-group">
        <form:label path="selected">Role</form:label>
        <form:errors path="selected"/>
        <form:select  path="selected" id="roles">
            <c:forEach items="${ allRoles }" var="r">
                <c:choose>
                    <c:when test="${user.roles[0].name == r}">
                        <option value="${ r }" selected>${ r }</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${ r }" >${ r }</option>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </form:select>
    </div>


    <input class="btn btn-primary" type="submit" value="Edit!"/>
    <%@ include file = "DeleteUserBtn.jsp" %>

</form:form>

            </div>
        </div>

    </div>
</div>
<script>
    changeRedirectUrl=function(role){
        switch(role){
            case "ROLE_STUDENT":
            $("#deleteUser").attr("href","/students");
            break;
            case "ROLE_ADMIN":
            $("#deleteUser").attr("href","/admins");
            break;
            case "ROLE_INSTRUCTOR":
            $("#deleteUser").attr("href","/instructors");
            break;
            
        }

    }
    changeRedirectUrl($("#roles").val())
    $("#roles").change(function(e){
        changeRedirectUrl($(this).val());
    })
</script>
</body>
</html>
</body>
</html>
