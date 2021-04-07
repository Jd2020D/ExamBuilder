
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
    <div class="col-6 mx-auto mt-5">
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
        <form:label path="username">Email :</form:label>
        <form:input path="username"/>
    </p>
    <p>
        <form:label path="firstName">First name :</form:label>
        <form:input path="firstName"/>
    </p>
    <p>
        <form:label path="lastName">Last name :</form:label>
        <form:input path="lastName"/>
    </p>
    <p>
        <form:label path="password">New Password:</form:label>
        <form:password path="password"/>
    </p>
    <p>
        <form:label path="passwordConfirmation">New Password Confirmation:</form:label>
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


    <input class="btn btn-warning" type="submit" value="Edit!"/>

</form:form>
            </div>
        </div>

    </div>
</div>
</body>
</html>
</body>
</html>
