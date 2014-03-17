<%-- 
    Document   : Download
    Created on : 2012-10-20, 0:32:23
    Author     : nightwolf
--%>

<%@page import="java.net.SocketAddress"%>
<%@page import="java.net.Proxy"%>
<%@page import="java.net.InetSocketAddress"%>
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
        SocketAddress addr = new InetSocketAddress("211.144.120.141", 8080);//是代理地址:192.9.208.16:3128
        Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);

        String songId = request.getParameter("songid");
        String songURL = new SongURLAchiever().getCurrentDownURL(songId, typeProxy);
    %>
    <script type="text/javascript">
        function download(){
            window.location = "http://anonym.to/?<%=songURL%>" ;
        }
    </script>
    <body onload="download()">

    <head >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
</body>
</html>
