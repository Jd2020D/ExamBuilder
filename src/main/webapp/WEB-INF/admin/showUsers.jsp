
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>


<h1>All Instructors</h1>

<table  class="table">
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


<a href="/shows/new" class="btn btn-primary"> Add a Show</a>

</body>
</html>
