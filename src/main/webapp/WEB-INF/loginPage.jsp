<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>




<div class="row">
    <div class="col-4 mx-auto mt-5">
        <div class="login-panel panel panel-success">

    <c:if test="${logoutMessage != null}">
        <c:out value="${logoutMessage}"></c:out>
    </c:if>
    <c:if test="${errorMessage != null}">
        <c:out value="${errorMessage}"></c:out>
    </c:if>

            <div class="panel-body">
                <div class="panel-heading">
                    <h1>Login</h1>
                </div>

                <form method="POST" action="/login">
            <p>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
            </p>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input class=" btn btn-primary" type="submit" value="Login"/>
</form>

            </div>
        </div>
    </div>

</div>