<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="container">



    <table  class="table">
        <thead class="thead-dark">
        <tr>
            <th>name</th>
            <th>action</th>

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


    </div>