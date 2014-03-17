<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@ page language="java" contentType="text/html; charset=GBK"
         pageEncoding="GBK"%>
<%@ include file="../Connections/spiderting.jsp" %>
<%
    String picUrl = request.getParameter("Picurl");
    String step = request.getParameter("step");
    String defaultPic = "image/man.GIF";
    if ("3".equals(step)) {
        defaultPic = "User/UserHeadImage/" + picUrl;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <title>UpLoad Photo</title>
        <link href="../css/main.css" type="text/css" rel="Stylesheet" />
        <script type="text/javascript" src="../js/jquery1.2.6.pack.js"></script>
        <script  type="text/javascript" src="../js/ui.core.packed.js" ></script>
        <script type="text/javascript" src="../js/ui.draggable.packed.js" ></script>
        <script type="text/javascript" src="../js/CutPic.js"></script>
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
                                    <script type="text/javascript">
            
                                        function Step1() {
                                            $("#Step2Container").hide();           
                                        }

                                        function Step2() {
                                            $("#CurruntPicContainer").hide();
                                        }
                                        function Step3() {
                                            $("#Step2Container").hide();          
                                        }
                                    </script>
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
                                            <div class="row">
                                                <div class="span4 offset1">
                                                    <h1>Nightwolf Music</h1>
                                                    <p>Only for music lovers</p>
                                                </div>
                                            </div>
                                            <div>
                                                <div class="left">
                                                    <!--��ǰ��Ƭ-->
                                                    <div id="CurruntPicContainer">
                                                        <div class="title"><b>��ǰ��Ƭ</b></div>
                                                        <div class="photocontainer">
                                                            <img id="imgphoto" src="../<%=defaultPic%>" style="border-width:0px;" />
                                                            <%


                                                                if (step != null && step.equals("3")) {
                                                                    out.println("<a href=\"user.jsp\">�ϴ���ɣ����ظ�����ҳ</a>");
                                                                    Driver Driveruserhistory = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
                                                                    Connection con = (Connection) DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
                                                                    PreparedStatement pstmt;
                                                                    String username = (String) session.getAttribute("username");
                                                                    pstmt = (PreparedStatement) con.prepareStatement("update users set photourl=? where username=?");
                                                                    pstmt.setString(1, defaultPic);
                                                                    pstmt.setString(2, username);
                                                                    pstmt.executeUpdate();
                                                                    pstmt.close();
                                                                    con.close();
                                                                }
                                                            %>
                                                        </div>
                                                    </div>
                                                    <!--Step 2-->
                                                    <div id="Step2Container">
                                                        <div class="title"><b> ����ͷ����Ƭ</b></div>
                                                        <div class="uploadtooltip">�������϶���Ƭ�Բü������ͷ��</div>                              
                                                        <div id="Canvas" class="uploaddiv">

                                                            <div id="ImageDragContainer">                               
                                                                <img id="ImageDrag" class="imagePhoto" src="../UploadPhoto/<%=picUrl%>" style="border-width:0px;" />                                                        
                                                            </div>
                                                            <div id="IconContainer">                               
                                                                <img id="ImageIcon" class="imagePhoto" src="../UploadPhoto/<%=picUrl%>" style="border-width:0px;" />                                                        
                                                            </div>                          
                                                        </div>
                                                        <div class="uploaddiv">
                                                            <table>
                                                                <tr>
                                                                    <td id="Min">
                                                                        <img alt="��С" src="../image/_c.gif" onmouseover="this.src='../image/_c.gif';" onmouseout="this.src='../image/_h.gif';" id="moresmall" class="smallbig" />
                                                                    </td>
                                                                    <td>
                                                                        <div id="bar">
                                                                            <div class="child">
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td id="Max">
                                                                        <img alt="�Ŵ�" src="../image/c.gif" onmouseover="this.src='../image/c.gif';" onmouseout="this.src='../image/h.gif';" id="morebig" class="smallbig" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                        <form action="../ZoomImage" method="post">
                                                            <input type="hidden" name="picture" value="<%=picUrl%>"/>
                                                            <div class="uploaddiv">
                                                                <input type="submit" name="btn_Image" value="����ͷ��" id="btn_Image" />
                                                            </div>           
                                                            <div>
                                                                ͼƬʵ�ʿ�ȣ� <input name="txt_width" type="text" value="1" id="txt_width" /><br />
                                                                ͼƬʵ�ʸ߶ȣ� <input name="txt_height" type="text" value="1" id="txt_height" /><br />
                                                                ���붥���� <input name="txt_top" type="text" value="82" id="txt_top" /><br />
                                                                ������ߣ� <input name="txt_left" type="text" value="73" id="txt_left" /><br />
                                                                ��ȡ��Ŀ� <input name="txt_DropWidth" type="text" value="120" id="txt_DropWidth" /><br />
                                                                ��ȡ��ĸߣ� <input name="txt_DropHeight" type="text" value="120" id="txt_DropHeight" /><br />
                                                                �Ŵ����� <input name="txt_Zoom" type="text" id="txt_Zoom" />
                                                            </div>  </form>
                                                    </div>

                                                </div>
                                                <form name="form1" method="post" action="../UpLoadUserHeadImage" id="form1" enctype="multipart/form-data">
                                                    <div class="right">
                                                        <!--Step 1-->
                                                        <div id="Step1Container">
                                                            <div class="title"><b>������Ƭ</b></div>
                                                            <div id="uploadcontainer">
                                                                <div class="uploadtooltip">��ѡ���µ���Ƭ�ļ����ļ���С��2.5MB</div>
                                                                <div class="uploaddiv"><input type="file" name="fuPhoto" id="fuPhoto" title="ѡ����Ƭ" /></div>
                                                                <div class="uploaddiv"><input type="submit" name="btnUpload" value="�� ��" id="btnUpload" /></div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <%
                                                if (null == picUrl || "".equals(picUrl)) {%>
                                            <script type='text/javascript'>Step1();</script>
                                            <%} else if (!"".equals(picUrl) && "2".equals(step)) {
                                            %>
                                            <script type='text/javascript'>Step2();</script>
                                            <%} else if (!"".equals(picUrl) && "3".equals(step)) {
                                            %>
                                            <script type='text/javascript'>Step3();</script>
                                            <%}%>
                                            <div class="row">
                                                <div class="span4 offset1">
                                                    <div class="footer">
                                                        <p>&copy; nightwolf-chen 2012</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div> <!-- /container -->

                                    </body>
                                    </html>