<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



      
    <table class="table table-striped table-light">
        <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Email</th>
                <th scope="col">Name</th>
                <th scope="col">Actions</th>            
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr id="${user.id}" >
                    <th scope="row">${user.id}</th>
                    <td>${user.username}</td>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>
                    <form action="/admin/deleteUser/${user.id}" method="POST">

                    <input type="hidden" name="_method" value="delete">
                    <input type="hidden"name="${_csrf.parameterName}"  value="${_csrf.token}"/>
                    <security:authorize access="hasAnyRole('ADMIN')">
                        <a class="btn btn-warning" href="/admin/editUser/${user.id}">Edit</a>
                        <button class="btn btn-danger" type="submit" >Delete</button>

                    </security:authorize>
                    </form>

                    </td>    
        
                </tr>
        
            </c:forEach>        
        </tbody>
      </table>

        
      <security:authorize access="hasAnyRole('ADMIN')">

</security:authorize>

