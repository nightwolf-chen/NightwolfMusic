<%-- 
    Document   : AddResult
    Created on : 2012-12-4, 12:07:29
    Author     : nightwolf
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.ResultSet"%>
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
        <title>Add Favorite</title>
        <script language="JavaScript" type="text/javascript">

            function closewindow()
            {
                window.close()
            }
   
        </Script>
    </head>
    <body onload="setTimeout('closewindow()',500)">
        <%
            Driver DriverRecordset1 = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
            Connection Conn = (Connection) DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
            String songId = request.getParameter("songid");
            String username = (String) session.getAttribute("username");
            String message = "" ;
            if(username != null){
            Statement stmtCheck = (Statement) Conn.createStatement();
            ResultSet rsCheck = stmtCheck.executeQuery("select * from song where songid='" + songId + "'");
            if (rsCheck.next()) {
                String songName = rsCheck.getString("songname");
                String singerId = rsCheck.getString("singerid");
                String singerName = rsCheck.getString("singername");


                Statement stmt1 = (Statement) Conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select * from userfavorite where username='" + username + "' and songid='" + songId + "'");
                //stmt1.close();
                if (rs1.next()) {
                    message = "歌曲已经收藏过了，无需再收藏" ;
                } else {
                    PreparedStatement pstmtUserhistory = (PreparedStatement) Conn.prepareStatement("insert into userfavorite(username,songid,songname,singerid,singername,visitedtime,createtime) values(?,?,?,?,?,?,?)");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//MM，HH大写out.println(sdf.format());
                    String currentTime = sdf.format(new java.util.Date());
                    pstmtUserhistory.setString(1, username);
                    pstmtUserhistory.setString(2, songId);
                    pstmtUserhistory.setString(3, songName);
                    pstmtUserhistory.setString(4, singerId);
                    pstmtUserhistory.setString(5, singerName);
                    pstmtUserhistory.setString(6, "1");
                    pstmtUserhistory.setString(7, currentTime);
                    pstmtUserhistory.execute();
                    pstmtUserhistory.close();
                    message="成功收藏歌曲" ;
                    response.sendRedirect("user.jsp");
                }


            } else {
                message = "歌曲不存在！";
            }
                       }else{
                       message = "要收藏歌曲请先登录！" ;
                       response.sendRedirect("login.jsp");
                       }

        %>
        <div align="center"><font color="red"><%=message%></font></div>
    </body>
</html>
