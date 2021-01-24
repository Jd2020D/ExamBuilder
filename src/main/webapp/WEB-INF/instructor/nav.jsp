<ul class="navbar-nav ml-auto">
    <li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="/instructor/editUser">Info</a></li>

    <li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="/instructor/students">Students</a></li>

    <li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="/instructor/exams">Exam</a></li>

    <li class="nav-item mx-0 mx-lg-1"> <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input class="nav-link py-2 px-0 px-lg-3 rounded js-scroll-trigger" type="submit" value="Logout!" />
    </form>
    </li>
</ul>
