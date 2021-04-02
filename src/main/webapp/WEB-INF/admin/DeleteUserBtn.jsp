<security:authorize access="hasAnyRole('ADMIN')">
        <a id="deleteUser" class="btn btn-danger" >Delete</a> 

    <script>
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $('#deleteUser').click(function (e) {
            $.ajax({
                beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
                url:"/admin/deleteUser/"+"${user.id}",
                method: 'DELETE',
                success: function(serverResponse){
                    try{
                        $("#"+serverResponse).remove(); 

                    }catch(e){
                    }
                },
                error:function(error){
                    console.log(error)
                }
            })
            })

    </script>
                 

</security:authorize>                
