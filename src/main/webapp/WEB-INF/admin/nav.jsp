
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<nav class="navbar navbar-expand-lg navbar-dark ">
    <a class="navbar-brand" href="#">
        
        <img src="/img/exam.png" width="30" height="30" class="d-inline-block align-top" alt=""> Admin
    </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a  class="nav-link" href="/students">Students <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item" >
        <a  class="nav-link" href="/instructors">Instructors</a>
      </li>
      <li class="nav-item">
        <a  class="nav-link ${examsPage}" href="/exams" >Exams </a>
      </li>
      <li class="nav-item">
        <a  class="nav-link " href="/instructor/exams">MyExams</a>
      </li>

    </ul>
  </div>
  <form class="form-inline my-2 my-lg-0" id="logoutForm" method="POST" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="logout-button" class="btn  my-2 my-sm-0" type="submit">Logout</button>
</form>
</nav>
    