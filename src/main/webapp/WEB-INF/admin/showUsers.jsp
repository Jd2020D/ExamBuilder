<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>


<h1>All Instructors</h1>
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

            <td><a class="btn btn primary" href="/admin/editUser/${u.id}">Edit</a></td>
        </tr>

    </c:forEach>


    </tbody>
</table>


<a href="/admin/insertUser" class="btn btn-success"> Add a User</a>
    </div>
</div>
</div>