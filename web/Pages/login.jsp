<%-- 
    Document   : login
    Created on : 2012-12-10, 14:53:06
    Author     : nightwolf
--%>

<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ include file="../Connections/spiderting.jsp" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    //String xcode = request.getParameter("xcode");
    //System.out.println("xcode:" + xcode + "xcodec:" + session.getAttribute("xcode"));
    if (username != null && password != null) {
        /*
        if (!xcode.equals(session.getAttribute("xcode"))) {
        response.sendRedirect("login.jsp?message=certcode incorrect!");
        return;
        }
         * */
        Driver DriverRecordset1 = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
        Connection Conn = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
        PreparedStatement pstmt = Conn.prepareStatement("select * from users where username=?");
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            if (rs.getString("password").equals(password)) {

                session.setAttribute("username", username);
                session.setAttribute("nickyname", rs.getString("nickname"));
                session.setAttribute("sex", rs.getString("sex"));
                session.setAttribute("intro", rs.getString("intro"));
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("login.jsp?message=Password increct.Please retry.");
            }
        } else {
            response.sendRedirect("login.jsp?message=No such user!Please check out you username.");
        }

    }//If

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="GBK">
        <title>登录</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="../assets/css/bootstrap.css" rel="stylesheet">
        <style>
            body {
                padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
            }

            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #f5f5f5;
            }

            .form-signin {
                max-width: 300px;
                padding: 19px 29px 29px;
                margin: 0 auto 20px;
                background-color: #fff;
                border: 1px solid #e5e5e5;
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
                -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
            }
            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin input[type="text"],
            .form-signin input[type="password"] {
                font-size: 16px;
                height: auto;
                margin-bottom: 15px;
                padding: 7px 9px;
            }

        </style>
        <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">

    </style>
    <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="../assets/ico/favicon.png">
</head>

<body>

    <div class="navbar navbar-inverse navbar-fixed-top">
        <%
            String fileName = request.getServletPath();
            // out.println("name:"+servletName) ;
            String blank = "";
            if (fileName.equals("/Pages/Player.jsp ")) {
                blank = "target=\"_blank\"";
            }
        %>
        <div class="navbar-inner">
            <div class="container">
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <a class="brand" href="index.jsp" <%=blank%>>音乐首页</a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li><a href="recommendation.jsp" <%=blank%>>音乐推荐</a></li>
                        <%
                            if (session.getAttribute("username") == null) {
                                out.println("<li><a href=\"login.jsp\" " + blank + ">登录</a></li>");
                                out.println("<li><a href=\"register.jsp\" " + blank + ">注册</a></li>");
                            } else {
                                out.println("<li><a href=\"user.jsp\" " + blank + ">个人主页</a></li>");
                                out.println("<li><a href=\"quit.jsp\" " + blank + ">退出登录</a></li>");
                            }
                        %>
                        <li><a href="about.jsp" <%=blank%>>关于</a></li>

                    </ul>
                    <form class="navbar-form pull-right" method="post" action="index.jsp" charset="gb2312">
                        <input name="key"class="span2" type="text"/>
                        <button type="submit" class="btn">音乐搜索</button>
                    </form>

                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>

    <div class="container">
        <form class="form-signin" action="login.jsp" method="post">
            <h2 class="form-signin-heading">请登录</h2>
            <%
                String message = request.getParameter("message");
                if (message == null) {
                    message = "Welcome to Nightwolf Music";
                }
            %>
            <div align="center">
                <% if (message != null) {
                        out.println("<a><font color=\"red\">" + message + "</font></a>");
                    }%>
            </div>
            
            <div class="control-group">
                <label class="control-label" >用户名</label>
                <div class="controls">
                   <input name="username" type="text" class="input-block-level"></input>
                </div>
            </div>
            
            <div class="control-group">
                <label class="control-label" >密码</label>
                <div class="controls">
                    <input name="password" type="password" class="input-block-level" placeholder="Password"></input>
                </div>
            </div>
            
                
           

           
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <button class="btn btn-large btn-primary" type="submit">Sign in</button>


            <div class="footer">
                <p>&copy; nightwolf-chen 2012</p>
            </div>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../assets/js/jquery.js"></script>
    <script src="../assets/js/bootstrap-transition.js"></script>
    <script src="../assets/js/bootstrap-alert.js"></script>
    <script src="../assets/js/bootstrap-modal.js"></script>
    <script src="../assets/js/bootstrap-dropdown.js"></script>
    <script src="../assets/js/bootstrap-scrollspy.js"></script>
    <script src="../assets/js/bootstrap-tab.js"></script>
    <script src="../assets/js/bootstrap-tooltip.js"></script>
    <script src="../assets/js/bootstrap-popover.js"></script>
    <script src="../assets/js/bootstrap-button.js"></script>
    <script src="../assets/js/bootstrap-collapse.js"></script>
    <script src="../assets/js/bootstrap-carousel.js"></script>
    <script src="../assets/js/bootstrap-typeahead.js"></script>

</body>
</html>
