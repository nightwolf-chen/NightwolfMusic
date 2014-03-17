<%@ include file="../Connections/spiderting.jsp" %>
<%
    //The is the start of database opertation--
    request.setCharacterEncoding("gbk");
    String username = request.getParameter("username");
    String password1 = request.getParameter("password1");
    String password2 = request.getParameter("password2");
    String sex = request.getParameter("sex");
    String intro = request.getParameter("intro");
    String nickyname = request.getParameter("nickyname");
    String xcode = request.getParameter("xcode");
    System.out.println("username:" + username);
    if (username == null) {
        //response.sendRedirect("register.jsp?message=Username could not be null!");
    } else if (password1 == null || password2 == null) {
        //response.sendRedirect("register.jsp?message=Password could not be null!");
    } else if (!password1.equals(password2)) {
        //response.sendRedirect("register.jsp?message=Password not the same!");
    } else if (nickyname == null) {
        //response.sendRedirect("register.jsp?message=nicky could not be null!");
    } /*
    else if(!xcode.equals(session.getAttribute("xcode")) && xcode!=null){
    response.sendRedirect("register.jsp?message=certcode not correct!");
    }*/ else {

        Driver DriverRecordset1 = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
        Connection Conn = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
        PreparedStatement pstmt1 = Conn.prepareStatement("select * from users where username=?");
        pstmt1.setString(1, username);
        ResultSet rs1 = pstmt1.executeQuery();
        if (rs1.next()) {
            response.sendRedirect("register.jsp?message=User already exists!");
        } else {
            PreparedStatement pstmt2 = Conn.prepareStatement("insert into users(username,password,nickname,sex,intro) values(?,?,?,?,?)");
            pstmt2.setString(1, username);
            pstmt2.setString(2, password1);
            pstmt2.setString(3, nickyname);
            pstmt2.setString(4, sex);
            pstmt2.setString(5, intro);
            pstmt2.execute();
            session.setAttribute("username", username);
            session.setAttribute("nickyname", password1);
            session.setAttribute("sex", sex);
            session.setAttribute("intro", intro);
            response.sendRedirect("index.jsp");
        }

    }
%>
<%
    String message = request.getParameter("message");
    if (message == null) {
        message = "Welcom to nightwof music!";
    }
%>
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="GBK">
        <title>Nightwolf Music</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="../assets/css/bootstrap.css" rel="stylesheet">
        <style>
            body {
                padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
            }
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
            <div class="row">
                <div class="span4 offset1">
                    <h1>Nightwolf Music</h1>
                    <p>Only for music lovers</p>
                </div>
            </div>

            <!--Here to edit your web content-->
            <div align="center" ><font color="red"><%=message%></font></div>
            <div class="row" >
                <div class="span3 offset3">
                    <form class="form-horizontal" action="register.jsp">

                        <div class="control-group">
                            <label class="control-label" >用户名</label>
                            <div class="controls">
                                <input type="text"  name="username" placeholder="Text input"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="inputPassword">密码</label>
                            <div class="controls">
                                <input type="password" name="password1" id="inputPassword" placeholder="Password">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="inputPassword">再次输入密码</label>
                            <div class="controls">
                                <input type="password" name="password2" id="inputPassword" placeholder="Password">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="inputEmail">昵称</label>
                            <div class="controls">
                                <input type="text" name="nickyname" placeholder="Text input"/>
                            </div>
                        </div>


                        <div class="control-group">
                            <label class="control-label" for="inputEmail">性别</label>
                            <div class="controls">
                                <label class="radio">
                                    <input type="radio" name="sex" id="sex" value="man" checked>
                                    男
                                </label>
                                <label class="radio">
                                    <input type="radio" name="sex" id="sex" value="woman">
                                    女
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="inputTextara">音乐宣言</label>
                            <div class="controls">
                                <textarea rows="3" name="intro"></textarea>
                            </div>
                        </div>





                        <div class="control-group">
                            <div class="controls">
                                <label class="checkbox">
                                    <input type="checkbox"> 记住我
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" ></label>
                            <div class="controls">
                                <button type="submit" class="btn">确认注册</button>
                            </div>
                        </div>


                    </form>
                </div><!--end of span12-->
            </div> <!--end of row-->


            <div class="row">
                <div class="span4 offset1">
                    <div class="footer">
                        <p>&copy; nightwolf-chen 2012</p>
                    </div>
                </div>
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
