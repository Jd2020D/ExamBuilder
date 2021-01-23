
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-8 mx-auto mt-5">

        <table  class="table table-bordered table-striped">
    <thead class="thead-dark">
    <tr>
        <th>name</th>

    </tr>
    </thead>

    <tbody>
    <c:forEach items="${users}" var="u">

        <tr>
            <td>${u.username}</td>
        </tr>

    </c:forEach>


    </tbody>
</table>


<a href="/student/editUser" class="btn btn-primary"> Edit</a>


    </div>
</div>
</div>