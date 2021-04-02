<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>

  <%@ include file = "header.jsp" %>
  <link href="/css/loginPage.css" rel="stylesheet" />

  </head>
  <body>
    <div class="container login-container">
          <form class="col-md-6 login-form-2" method="POST" action="/login" >
              <h3>Sign In</h3>
              <p>
                <c:if test="${logoutMessage != null}">
                  <c:out value="${logoutMessage}"></c:out>
                </c:if>
                <c:if test="${errorMessage != null}">
                <c:out value="${errorMessage}"></c:out>
                </c:if>  
              </p>
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="form-group">
                  <input type="text" id="username" name="username"  class="form-control" placeholder="Email / Username" value="" required autofocus/>
              </div>
              <div class="form-group">
                  <input type="password" id="password" name="password" class="form-control" placeholder="Password" value="" required />
              </div>
              <div class="form-group button-container">
                  <input type="submit" class="btnSubmit" value="Login" />
              </div>
              <div class="form-group">
                  <a href="#" class="btnForgetPwd" value="Login">Forget Password?</a>
              </div>
            </form>
      </div>

  </body>
</html>

  
