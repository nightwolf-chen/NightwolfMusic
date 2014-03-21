<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="spiders.SpiderTingLRC"%>
<%@page import="spiders.SongURLAchiever"%>
<%@page import="spiders.SpiderTingIntroduction"%>
<%@ include file="../Connections/spiderting.jsp" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    }
%>
<%
    String userhistory__MMColParam = "1";
    if (session.getAttribute("username") != null) {
        userhistory__MMColParam = (String) session.getAttribute("username");
    }
%>
<%
    Driver Driveruserhistory = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
    Connection Connuserhistory = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
    PreparedStatement Statementuserhistory = Connuserhistory.prepareStatement("SELECT * FROM userhistory WHERE username = '" + userhistory__MMColParam + "' ORDER BY visitedtimes DESC");
    ResultSet userhistory = Statementuserhistory.executeQuery();
    boolean userhistory_isEmpty = !userhistory.next();
    boolean userhistory_hasData = !userhistory_isEmpty;
    Object userhistory_data;
    int userhistory_numRows = 0;
%>
<%
    String favorlist__MMColParam = "1";
    if (session.getAttribute("username") != null) {
        favorlist__MMColParam = (String) session.getAttribute("username");
    }
%>
<%
    Driver Driverfavorlist = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
    Connection Connfavorlist = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
    PreparedStatement Statementfavorlist = Connfavorlist.prepareStatement("SELECT * FROM userfavorite WHERE username = '" + favorlist__MMColParam + "' ORDER BY visitedtime DESC");
    ResultSet favorlist = Statementfavorlist.executeQuery();
    boolean favorlist_isEmpty = !favorlist.next();
    boolean favorlist_hasData = !favorlist_isEmpty;
    Object favorlist_data;
    int favorlist_numRows = 0;
%>
<%
    int Repeat1__numRows = 10;
    int Repeat1__index = 0;
    userhistory_numRows += Repeat1__numRows;
%>
<%
    int Repeat2__numRows = 10;
    int Repeat2__index = 0;
    favorlist_numRows += Repeat2__numRows;
%>
<%
// *** Recordset Stats, Move To Record, and Go To Record: declare stats variables

    int favorlist_first = 1;
    int favorlist_last = 1;
    int favorlist_total = -1;

    if (favorlist_isEmpty) {
        favorlist_total = favorlist_first = favorlist_last = 0;
    }

//set the number of rows displayed on this page
    if (favorlist_numRows == 0) {
        favorlist_numRows = 1;
    }
%>
<% String MM_paramName = "";%>
<%
// *** Move To Record and Go To Record: declare variables

    ResultSet MM_rs = favorlist;
    int MM_rsCount = favorlist_total;
    int MM_size = favorlist_numRows;
    String MM_uniqueCol = "";
    MM_paramName = "";
    int MM_offset = 0;
    boolean MM_atTotal = false;
    boolean MM_paramIsDefined = (MM_paramName.length() != 0 && request.getParameter(MM_paramName) != null);
%>
<%
// *** Move To Record: handle 'index' or 'offset' parameter

    if (!MM_paramIsDefined && MM_rsCount != 0) {

        //use index parameter if defined, otherwise use offset parameter
        String r = request.getParameter("index");
        if (r == null) {
            r = request.getParameter("offset");
        }
        if (r != null) {
            MM_offset = Integer.parseInt(r);
        }

        // if we have a record count, check if we are past the end of the recordset
        if (MM_rsCount != -1) {
            if (MM_offset >= MM_rsCount || MM_offset == -1) {  // past end or move last
                if (MM_rsCount % MM_size != 0) // last page not a full repeat region
                {
                    MM_offset = MM_rsCount - MM_rsCount % MM_size;
                } else {
                    MM_offset = MM_rsCount - MM_size;
                }
            }
        }

        //move the cursor to the selected record
        int i;
        for (i = 0; favorlist_hasData && (i < MM_offset || MM_offset == -1); i++) {
            favorlist_hasData = MM_rs.next();
        }
        if (!favorlist_hasData) {
            MM_offset = i;  // set MM_offset to the last possible record
        }
    }
%>
<%
// *** Move To Record: if we dont know the record count, check the display range

    if (MM_rsCount == -1) {

        // walk to the end of the display range for this page
        int i;
        for (i = MM_offset; favorlist_hasData && (MM_size < 0 || i < MM_offset + MM_size); i++) {
            favorlist_hasData = favorlist.next();
        }

        // if we walked off the end of the recordset, set MM_rsCount and MM_size
        if (!favorlist_hasData) {
            MM_rsCount = i;
            if (MM_size < 0 || MM_size > MM_rsCount) {
                MM_size = MM_rsCount;
            }
        }

        // if we walked off the end, set the offset based on page size
        if (!favorlist_hasData && !MM_paramIsDefined) {
            if (MM_offset > MM_rsCount - MM_size || MM_offset == -1) { //check if past end or last
                if (MM_rsCount % MM_size != 0) //last page has less records than MM_size
                {
                    MM_offset = MM_rsCount - MM_rsCount % MM_size;
                } else {
                    MM_offset = MM_rsCount - MM_size;
                }
            }
        }

        // reset the cursor to the beginning
        favorlist.close();
        favorlist = Statementfavorlist.executeQuery();
        favorlist_hasData = favorlist.next();
        MM_rs = favorlist;

        // move the cursor to the selected record
        for (i = 0; favorlist_hasData && i < MM_offset; i++) {
            favorlist_hasData = favorlist.next();
        }
    }
%>
<%
// *** Move To Record: update recordset stats

// set the first and last displayed record
    favorlist_first = MM_offset + 1;
    favorlist_last = MM_offset + MM_size;
    if (MM_rsCount != -1) {
        favorlist_first = Math.min(favorlist_first, MM_rsCount);
        favorlist_last = Math.min(favorlist_last, MM_rsCount);
    }

// set the boolean used by hide region to check if we are on the last record
    MM_atTotal = (MM_rsCount != -1 && MM_offset + MM_size >= MM_rsCount);
%>
<%
// *** Go To Record and Move To Record: create strings for maintaining URL and Form parameters

    String MM_keepBoth, MM_keepURL = "", MM_keepForm = "", MM_keepNone = "";
    String[] MM_removeList = {"index", MM_paramName};

// create the MM_keepURL string
    if (request.getQueryString() != null) {
        MM_keepURL = '&' + request.getQueryString();
        for (int i = 0; i < MM_removeList.length && MM_removeList[i].length() != 0; i++) {
            int start = MM_keepURL.indexOf(MM_removeList[i]) - 1;
            if (start >= 0 && MM_keepURL.charAt(start) == '&'
                    && MM_keepURL.charAt(start + MM_removeList[i].length() + 1) == '=') {
                int stop = MM_keepURL.indexOf('&', start + 1);
                if (stop == -1) {
                    stop = MM_keepURL.length();
                }
                MM_keepURL = MM_keepURL.substring(0, start) + MM_keepURL.substring(stop);
            }
        }
    }

// add the Form variables to the MM_keepForm string
    if (request.getParameterNames().hasMoreElements()) {
        java.util.Enumeration items = request.getParameterNames();
        while (items.hasMoreElements()) {
            String nextItem = (String) items.nextElement();
            boolean found = false;
            for (int i = 0; !found && i < MM_removeList.length; i++) {
                if (MM_removeList[i].equals(nextItem)) {
                    found = true;
                }
            }
            if (!found && MM_keepURL.indexOf('&' + nextItem + '=') == -1) {
                MM_keepForm = MM_keepForm + '&' + nextItem + '=' + java.net.URLEncoder.encode(request.getParameter(nextItem));
            }
        }
    }

    String tempStr = "";
    for (int i = 0; i < MM_keepURL.length(); i++) {
        if (MM_keepURL.charAt(i) == '<') {
            tempStr = tempStr + "&lt;";
        } else if (MM_keepURL.charAt(i) == '>') {
            tempStr = tempStr + "&gt;";
        } else if (MM_keepURL.charAt(i) == '"') {
            tempStr = tempStr + "&quot;";
        } else {
            tempStr = tempStr + MM_keepURL.charAt(i);
        }
    }
    MM_keepURL = tempStr;

    tempStr = "";
    for (int i = 0; i < MM_keepForm.length(); i++) {
        if (MM_keepForm.charAt(i) == '<') {
            tempStr = tempStr + "&lt;";
        } else if (MM_keepForm.charAt(i) == '>') {
            tempStr = tempStr + "&gt;";
        } else if (MM_keepForm.charAt(i) == '"') {
            tempStr = tempStr + "&quot;";
        } else {
            tempStr = tempStr + MM_keepForm.charAt(i);
        }
    }
    MM_keepForm = tempStr;

// create the Form + URL string and remove the intial '&' from each of the strings
    MM_keepBoth = MM_keepURL + MM_keepForm;
    if (MM_keepBoth.length() > 0) {
        MM_keepBoth = MM_keepBoth.substring(1);
    }
    if (MM_keepURL.length() > 0) {
        MM_keepURL = MM_keepURL.substring(1);
    }
    if (MM_keepForm.length() > 0) {
        MM_keepForm = MM_keepForm.substring(1);
    }
%>
<%
// *** Move To Record: set the strings for the first, last, next, and previous links

    String MM_moveFirst, MM_moveLast, MM_moveNext, MM_movePrev;
    {
        String MM_keepMove = MM_keepBoth;  // keep both Form and URL parameters for moves
        String MM_moveParam = "index=";

        // if the page has a repeated region, remove 'offset' from the maintained parameters
        if (MM_size > 1) {
            MM_moveParam = "offset=";
            int start = MM_keepMove.indexOf(MM_moveParam);
            if (start != -1 && (start == 0 || MM_keepMove.charAt(start - 1) == '&')) {
                int stop = MM_keepMove.indexOf('&', start);
                if (start == 0 && stop != -1) {
                    stop++;
                }
                if (stop == -1) {
                    stop = MM_keepMove.length();
                }
                if (start > 0) {
                    start--;
                }
                MM_keepMove = MM_keepMove.substring(0, start) + MM_keepMove.substring(stop);
            }
        }

        // set the strings for the move to links
        StringBuffer urlStr = new StringBuffer(request.getRequestURI()).append('?').append(MM_keepMove);
        if (MM_keepMove.length() > 0) {
            urlStr.append('&');
        }
        urlStr.append(MM_moveParam);
        MM_moveFirst = urlStr + "0";
        MM_moveLast = urlStr + "-1";
        MM_moveNext = urlStr + Integer.toString(MM_offset + MM_size);
        MM_movePrev = urlStr + Integer.toString(Math.max(MM_offset - MM_size, 0));
    }
%>
<%
    String nickyname = (String) session.getAttribute("nickyname");
    String sex = (String) session.getAttribute("sex");
    String intro = (String) session.getAttribute("intro");
    if (intro == null) {
        intro = "还没有个性签名哦！快写一下吧";
    }
    if (nickyname == null) {
        if (sex != null) {
            if (sex.equals("man")) {
                nickyname = "帅哥";
            } else {
                nickyname = "美女";
            }
        } else {
            nickyname = "外星人";
        }
    }
%>
<%//Get user's head picture
    Statement picStatement = Connuserhistory.createStatement();
    ResultSet picSet = picStatement.executeQuery("select * from users where username='" + session.getAttribute("username") + "'");
    String picUrl = "";
    if (picSet.next()) {
        picUrl = picSet.getString("photourl");
    }
    if (picUrl == null) {
        picUrl = "image/man.GIF";
    } else if (picUrl.equals("")) {
        picUrl = "image/man.GIF";
    }
%>
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



            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="span2">
                        <!--Sidebar content-->
                        <div class="img">
                            <div id="CurruntPicContainer">
                                <div class="photocontainer">
                                    <img id="imgphoto" src="../<%=picUrl%>" style="border-width:0px;"/>                                </div>
                            </div>
                        </div>
                        <div><h5>您好！亲爱的<%=nickyname%></h5></div>
                        <div><h5>个性签名：<%=intro%></h5></div>
                        <div><h5><a href="uploadimage.jsp">上传头像</a></h5></div>
                    </div>
                    <div class="span5">
                        <h3>播放历史</h3>
                        <!--Body content-->
                        <% while ((userhistory_hasData) && (Repeat1__numRows-- != 0)) {%>
                        <%
                            request.setCharacterEncoding("gb2312");
                            String songName = userhistory.getString("songname");
                            String songId = userhistory.getString("songid");
                            String singerName = userhistory.getString("singername");
                            singerName = URLEncoder.encode(singerName, "utf-8");
                            songName = URLEncoder.encode(songName, "utf-8");
                            String singerId = userhistory.getString("singerid");
                        %>
                        <div>
                            <table width="360" border="0" bordercolor="">
                                <tr>
                                    <td width="40%" height="35"><%=(((userhistory_data = userhistory.getObject("SongName")) == null || userhistory.wasNull()) ? "" : userhistory_data)%></td>
                                    <td width="40%" ><a href="SingerInfo.jsp?singerid=<%=singerId%>"><%=(((userhistory_data = userhistory.getObject("SingerName")) == null || userhistory.wasNull()) ? "" : userhistory_data)%></a></td>
                                    <td width="3.3%"><a href="Player.jsp?songid=<%=songId%>&songname=<%=songName%>&singerid=<%=singerId%>&singername=<%=singerName%>" target="_blank"><i class="icon-play"></i></a></td>
                                    <td width="3.3%"><a href="AddResult.jsp?songid=<%=songId%>&songname=<%=songName%>&singerid=<%=singerId%>&singername=<%=singerName%>" target="_blank"><i class="icon-heart"></i></a></td>
                                    <td width="6.3%"><a href="Download.jsp?songid=<%=songId%>" target="blank" ><i class="icon-download-alt"></i></a></td>
                                    <td width="6.3%"><a href="DelHistory.jsp?songid=<%=songId%>"><i class="icon-trash"></i></a></td>
                                </tr>
                            </table>
                        </div>
                        <%
                                Repeat1__index++;
                                userhistory_hasData = userhistory.next();
                            }
                        %>

                        <table border="0" width="50%" align="center">
                            <tr>
                                <td width="23%" align="center"><% if (MM_offset != 0) {%>
                                    <a href="<%=MM_moveFirst%>">First</a>
                                    <% } /* end MM_offset != 0 */%>
                                </td>
                                <td width="31%" align="center"><% if (MM_offset != 0) {%>
                                    <a href="<%=MM_movePrev%>">Previous</a>
                                    <% } /* end MM_offset != 0 */%>
                                </td>
                                <td width="23%" align="center"><% if (!MM_atTotal) {%>
                                    <a href="<%=MM_moveNext%>">Next</a>
                                    <% } /* end !MM_atTotal */%>
                                </td>
                                <td width="23%" align="center"><% if (!MM_atTotal) {%>
                                    <a href="<%=MM_moveLast%>">Last</a>
                                    <% } /* end !MM_atTotal */%>
                                </td>
                            </tr>
                        </table>


                    </div>
                    <div class="span5">
                        <!--Body content-->
                        <h3>个人收藏</h3>
                        <% while ((favorlist_hasData) && (Repeat2__numRows-- != 0)) {%>
                        <%
                            request.setCharacterEncoding("gb2312");
                            String songName = favorlist.getString("songname");
                            String songId = favorlist.getString("songid");
                            String singerName = favorlist.getString("singername");
                            singerName = URLEncoder.encode(singerName, "utf-8");
                            songName = URLEncoder.encode(songName, "utf-8");
                            String singerId = favorlist.getString("singerid");
                        %>
                        <div class="myitem">
                            <table width="360" border="0" bordercolor="">
                                <tr>
                                    <td width="40%" height="35"><%=(((favorlist_data = favorlist.getObject("SongName")) == null || favorlist.wasNull()) ? "" : favorlist_data)%></td>
                                    <td width="40%" ><a href="SingerInfo.jsp?singerid=<%=singerId%>"><%=(((favorlist_data = favorlist.getObject("SingerName")) == null || favorlist.wasNull()) ? "" : favorlist_data)%></a></td>
                                    <td width="6.3%"><a href="Player.jsp?songid=<%=songId%>&songname=<%=songName%>&singerid=<%=singerId%>&singername=<%=singerName%>" target="_blank"><i class="icon-play"></i></a></td>
                                    <td width="6.3%"><a href="Download.jsp?songid=<%=songId%>" target="blank" ><i class="icon-download-alt"></i></a></td>
                                    <td width="6.3%"><a href="DelFavorite.jsp?songid=<%=songId%>"><i class="icon-trash"></i></a></td>
                                </tr>
                            </table>
                        </div>
                        <%
                                Repeat2__index++;
                                favorlist_hasData = favorlist.next();
                            }
                        %>
                        <div>
                            <table border="0" width="50%" align="center">
                                <tr>
                                    <td width="23%" align="center"><% if (MM_offset != 0) {%>
                                        <a href="<%=MM_moveFirst%>">First</a>
                                        <% } /* end MM_offset != 0 */%>
                                    </td>
                                    <td width="31%" align="center"><% if (MM_offset != 0) {%>
                                        <a href="<%=MM_movePrev%>">Previous</a>
                                        <% } /* end MM_offset != 0 */%>
                                    </td>
                                    <td width="23%" align="center"><% if (!MM_atTotal) {%>
                                        <a href="<%=MM_moveNext%>">Next</a>
                                        <% } /* end !MM_atTotal */%>
                                    </td>
                                    <td width="23%" align="center"><% if (!MM_atTotal) {%>
                                        <a href="<%=MM_moveLast%>">Last</a>
                                        <% } /* end !MM_atTotal */%>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>







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
<%
    userhistory.close();
    Statementuserhistory.close();
    Connuserhistory.close();
%>
<%
    favorlist.close();
    Statementfavorlist.close();
    Connfavorlist.close();
%>