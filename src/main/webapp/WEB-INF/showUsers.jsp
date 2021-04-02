<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



      
    <table class="table table-striped table-light">
        <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Actions</th>            
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr id="${user.id}" >
                    <th scope="row">${user.id}</th>
                    <td>${user.username}</td>
                    <td>
                    <a class="btn btn btn-secondary" href="#">View Exams</a>
                    <security:authorize access="hasAnyRole('ADMIN')">
                        <a class="btn btn-warning" href="/admin/editUser/${user.id}">Edit</a>
                    </security:authorize>
                    <%@ include file = "admin/DeleteUserBtn.jsp" %>
                
                    </td>    
        
                </tr>
        
            </c:forEach>        
        </tbody>
      </table>

        
      <security:authorize access="hasAnyRole('ADMIN')">

<a href="/admin/insertUser" class="btn btn-success"> Add a User</a>
</security:authorize>

