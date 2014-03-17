<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
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
                    <a class="brand" href="index.jsp" <%=blank%>>������ҳ</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav">
                            <li><a href="recommendation.jsp" <%=blank%>>�����Ƽ�</a></li>
                            <%
                                if (session.getAttribute("username") == null) {
                                    out.println("<li><a href=\"login.jsp\" " + blank + ">��¼</a></li>");
                                    out.println("<li><a href=\"register.jsp\" " + blank + ">ע��</a></li>");
                                } else {
                                    out.println("<li><a href=\"user.jsp\" " + blank + ">������ҳ</a></li>");
                                    out.println("<li><a href=\"quit.jsp\" " + blank + ">�˳���¼</a></li>");
                                }
                            %>
                            <li><a href="about.jsp" <%=blank%>>����</a></li>

                        </ul>
                        <form class="navbar-form pull-right" method="post" action="index.jsp" charset="gb2312">
                            <input name="key"class="span2" type="text"/>
                            <button type="submit" class="btn">��������</button>
                        </form>

                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row"><div class="span6 offset1"><strong>�㶫������ó��ѧ</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>Guangdong university of foreign studies (China)</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>˼����ϢѧԺ</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>Cisco Informatics College</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>�������10��</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>Software Engineering</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>�¼Ͷ�</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>nickyname��nightwolf-chen</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>Email��<a href="mailto:466202783@qq.com">466202783@qq.com</a></strong></div></div>
           <div class="row"><div class="span6 offset1"><strong>��л����������Դ���ṩ�ߣ���ӭ������</strong></div></div>
           <div class="row"><div class="span6 offset1"><strong><strong>Any question or advice please contact with me.</strong></strong></div></div>
            

            

            

            

            

            

            

            

            

            


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
