<%-- 
    Document   : DelHistory
    Created on : 2012-12-9, 19:36:50
    Author     : nightwolf
--%>

<%@page import="com.mysql.jdbc.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@page contentType="text/html" pageEncoding="GB2312"%>
<%@ include file="../Connections/spiderting.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GB2312">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Driver DriverRecordset1 = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
            Connection Conn = (Connection) DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
            String songId = request.getParameter("songid");
            String username = (String)session.getAttribute("username");
            Statement stmt = (Statement)Conn.createStatement();
           stmt.executeUpdate("delete from userfavorite where username='"+username+"' and songid='"+songId+"'");
            response.sendRedirect("user.jsp");
        %>
    </body>
</html>
