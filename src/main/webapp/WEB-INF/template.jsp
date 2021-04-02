  
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <head>
        <%@ include file = "header.jsp" %>

         <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

        <!-- Core theme CSS (includes Bootstrap)-->


    </head>

<body>

        
    <%@ include file = "nav.jsp" %>


<section class="page-section  mt-5" >
    <div id="Main" class="container">

    <jsp:include     page="${page}" />
    </div>
    
</section>









<script>
    $("#navBarList").children().map(function(item,index){
        $(this).removeClass("active");
        if($(location).attr('pathname').length>1&&$(this).children().attr("href").slice(1,this.length).startsWith($(location).attr('pathname').split("/")[1])){
            $(this).addClass("active");
            
        }
        });
</script>


    </body>
</html>