
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
                    <h1>Insert</h1>
                </div>


                <p><form:errors path="user.*"/></p>

<form:form method="POST" action="/admin/editUser" modelAttribute="user">

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
        <form:select path="selected">
            <c:forEach items="${ allRoles }" var="r">
                <option value="${ r }">${ r }</option>
            </c:forEach>
        </form:select>
    </div>


    <input type="submit" value="Edit!"/>

    <a class="btn btn-primary" href="/admin/deleteUser/${user.id}">Delete</a>

</form:form>

            </div>
        </div>

    </div>
</div>

</body>
</html>
</body>
</html>