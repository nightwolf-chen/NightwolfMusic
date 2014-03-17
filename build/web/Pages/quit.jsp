<%-- 
    Document   : quit
    Created on : 2012-12-2, 16:44:28
    Author     : nightwolf
--%>

<%@page contentType="text/html" pageEncoding="GB2312"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GB2312">
        <title>quit</title>
    </head>
    <body>
        <%
            if(session.getAttribute("username") != null){
                session.removeAttribute("username");
            }
                response.sendRedirect("index.jsp");
        %>
    </body>
</html>
