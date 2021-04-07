
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<nav  class="navbar navbar-expand-lg navbar-dark ">
    <a class="navbar-brand" href="#">
        
        <img src="/img/exam.png" width="30" height="30" class="d-inline-block align-top" alt=""> 
        <security:authorize access="hasAnyRole('ADMIN')"> Admin </security:authorize>                
        <security:authorize access="hasAnyRole('INSTRUCTOR')"> Instructor </security:authorize>                
        <security:authorize access="hasAnyRole('STUDENT')"> Student </security:authorize>                

        
    </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul id="navBarList" class="navbar-nav">
      <security:authorize access="hasAnyRole('ADMIN')">  
        <li id="instructorsNav" class="nav-item" >
          <a  class="nav-link" href="/admins">Admins</a>
        </li>
      </security:authorize>                

      <security:authorize access="hasAnyRole('ADMIN')">  
        <li id="instructorsNav" class="nav-item" >
          <a  class="nav-link" href="/instructors">Instructors</a>
        </li>
      </security:authorize>                
  
      <security:authorize access="hasAnyRole('ADMIN','INSTRUCTOR')">  
      <li id="studentsNav" class="nav-item active">
        <a  class="nav-link" href="/students">Students</a>
      </li>
    </security:authorize>      
    <security:authorize access="hasAnyRole('ADMIN')">  
      <li id="instructorsNav" class="nav-item" >
        <a  class="nav-link" href="/addUser">Add User</a>
      </li>
    </security:authorize>                
        
    <security:authorize access="hasAnyRole('INSTRUCTOR')">  
      <li id="examsNav" class="nav-item">
        <a  class="nav-link ${examsPage}" href="/instructor/exams" >My Exams</a>
      </li>
    </security:authorize>                
      <security:authorize access="hasAnyRole('STUDENT')">  
      <li id="myExamsNav" class="nav-item">
        <a  class="nav-link " href="/student/exams">My Exams</a>
      </li>
      </security:authorize>   
      <security:authorize access="hasAnyRole('STUDENT','INSTRUCTOR')">               
      <li id="myExamsNav" class="nav-item">
        <a  class="nav-link " href="/extras">Extra Exams</a>
      </li>
      </security:authorize>

    </ul>
  </div>
  <h6 id="user-full-name" class="text-light m-3 "></h6>

  <form class="form-inline my-2 my-lg-0" id="logoutForm" method="POST" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="logout-button" class="btn  my-2 my-sm-0" type="submit">Logout</button>
</form>
</nav>
