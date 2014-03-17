<%-- 
    Document   : Download
    Created on : 2012-10-20, 0:32:23
    Author     : nightwolf
--%>

<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="spiderting.Code"%>
<%@page import="spiderting.SongURLAchiever"%>
<%@page contentType="text/html" pageEncoding="gb2312"%>
<!DOCTYPE html>
<html>
    <script src="http://js.anonym.to/anonym/anonymize.js" type="text/javascript"></script>

<script type="text/javascript"><!--
protected_links = "";
auto_anonymize();
//--></script>

    <%
        String songId = request.getParameter("songid");
        String downUrl = SongURLAchiever.getCurrentDownURL(songId);
    %>
    <script type="text/javascript">
        function download(){
            window.location = "http://anonym.to/?<%=downUrl%>" ;
        }
    </script>
    <body onload="download()">

    <head >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
</body>
</html>
