<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div class="container">
      
    <table class="table table-striped table-light">
        <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Actions</th>            
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="u">
                <tr id="${u.id}" >
                    <th scope="row">${u.id}</th>
                    <td>${u.username}</td>
                    <td>
                    <a class="btn btn btn-secondary" href="#">View Exams</a>
                    <security:authorize access="hasAnyRole('ADMIN','INSTRUCTOR')">
                        <a class="btn btn-warning" href="/admin/editUser/${u.id}">Edit</a>
                        <a class="btn btn-danger" href="/admin/deleteUser/${u.id}">Delete</a>                        
                    </security:authorize>                
                    </td>    
        
                </tr>
        
            </c:forEach>        
        </tbody>
      </table>

        

<a href="/admin/insertUser" class="btn btn-success"> Add a User</a>
    </div>
