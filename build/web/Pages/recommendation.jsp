<%-- 
    Document   : recommendation
    Created on : 2012-12-10, 23:50:42
    Author     : nightwolf
--%>


<%@page import="java.net.URLDecoder"%>
<%@page import="spiderting.SpiderTingLRC"%>
<%@page import="spiderting.SongURLAchiever"%>
<%@page import="spiderting.SpiderTingIntroduction"%>
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ include file="../Connections/spiderting.jsp" %>
<%
    Driver Drivertopsinger = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
    Connection Conntopsinger = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
    PreparedStatement Statementtopsinger = Conntopsinger.prepareStatement("SELECT * FROM topsinger ORDER BY rank ASC");
    ResultSet topsinger = Statementtopsinger.executeQuery();
    boolean topsinger_isEmpty = !topsinger.next();
    boolean topsinger_hasData = !topsinger_isEmpty;
    Object topsinger_data;
    int topsinger_numRows = 0;
%>
<%
    int Repeat1__numRows = 36;
    int Repeat1__index = 0;
    topsinger_numRows += Repeat1__numRows;
%>
<%
// *** Recordset Stats, Move To Record, and Go To Record: declare stats variables

    int topsinger_first = 1;
    int topsinger_last = 1;
    int topsinger_total = -1;

    if (topsinger_isEmpty) {
        topsinger_total = topsinger_first = topsinger_last = 0;
    }

//set the number of rows displayed on this page
    if (topsinger_numRows == 0) {
        topsinger_numRows = 1;
    }
%>
<% String MM_paramName = "";%>
<%
// *** Move To Record and Go To Record: declare variables

    ResultSet MM_rs = topsinger;
    int MM_rsCount = topsinger_total;
    int MM_size = topsinger_numRows;
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
        for (i = 0; topsinger_hasData && (i < MM_offset || MM_offset == -1); i++) {
            topsinger_hasData = MM_rs.next();
        }
        if (!topsinger_hasData) {
            MM_offset = i;  // set MM_offset to the last possible record
        }
    }
%>
<%
// *** Move To Record: if we dont know the record count, check the display range

    if (MM_rsCount == -1) {

        // walk to the end of the display range for this page
        int i;
        for (i = MM_offset; topsinger_hasData && (MM_size < 0 || i < MM_offset + MM_size); i++) {
            topsinger_hasData = MM_rs.next();
        }

        // if we walked off the end of the recordset, set MM_rsCount and MM_size
        if (!topsinger_hasData) {
            MM_rsCount = i;
            if (MM_size < 0 || MM_size > MM_rsCount) {
                MM_size = MM_rsCount;
            }
        }

        // if we walked off the end, set the offset based on page size
        if (!topsinger_hasData && !MM_paramIsDefined) {
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
        topsinger.close();
        topsinger = Statementtopsinger.executeQuery();
        topsinger_hasData = topsinger.next();
        MM_rs = topsinger;

        // move the cursor to the selected record
        for (i = 0; topsinger_hasData && i < MM_offset; i++) {
            topsinger_hasData = MM_rs.next();
        }
    }
%>
<%
// *** Move To Record: update recordset stats

// set the first and last displayed record
    topsinger_first = MM_offset + 1;
    topsinger_last = MM_offset + MM_size;
    if (MM_rsCount != -1) {
        topsinger_first = Math.min(topsinger_first, MM_rsCount);
        topsinger_last = Math.min(topsinger_last, MM_rsCount);
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
        <link rel="stylesheet" media="all" type="text/css" href="css/photo-info.css" />
        <script src="js/jquery-1.2.6.min.js" type="text/javascript"></script>
        <script src="js/photo-info.js" type="text/javascript"></script>
        <script language="javascript" src="js/720.js"></script>
    </head>

    <body>

       <div class="navbar navbar-inverse navbar-fixed-top">
          <%
                String fileName = request.getServletPath();
               // out.println("name:"+servletName) ;
                String blank = "" ;
                if(fileName.equals("/Pages/Player.jsp ")){
                    blank="target=\"_blank\"";
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
                                    out.println("<li><a href=\"login.jsp\" "+blank+">登录</a></li>");
                                    out.println("<li><a href=\"register.jsp\" "+blank+">注册</a></li>");
                                } else {
                                    out.println("<li><a href=\"user.jsp\" "+blank+">个人主页</a></li>");
                                    out.println("<li><a href=\"quit.jsp\" "+blank+">退出登录</a></li>");
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

            <%
                final int NumPerRow = 6;
                final int RowNumberPerPage = 6;
                int count = 0;
                int rowNum = 0;
            %>

            <% while ((topsinger_hasData) && (Repeat1__numRows-- != 0)) {%>

            <% if (count == 0) {
                    out.println("<div class=\"row\">");
                }
                count++;
            %>

            <div class="span2">
                <div class="frame">
                    <div class="wrap">
                        <img href="SingerInfo.jsp?singerid=<%=(((topsinger_data = topsinger.getObject("singerid")) == null || topsinger.wasNull()) ? "" : topsinger_data)%>" src='../img/<%=(((topsinger_data = topsinger.getObject("singerid")) == null || topsinger.wasNull()) ? "" : topsinger_data)%>.jpg' alt='' />
                        <div>
                            <b></b>
                            <span>
                                <p>歌手:<%=(((topsinger_data = topsinger.getObject("singername")) == null || topsinger.wasNull()) ? "" : topsinger_data)%></p> 
                                <p>榜单排名:<%=(((topsinger_data = topsinger.getObject("rank")) == null || topsinger.wasNull()) ? "" : topsinger_data)%></p>
                                <p><a href="SingerInfo.jsp?singerid=<%=(((topsinger_data = topsinger.getObject("singerid")) == null || topsinger.wasNull()) ? "" : topsinger_data)%>">歌手主页...</a></p>
                            </span>             </div>
                    </div>

                </div> 
                <!-- end wrap -->
            </div><!--end span2-->

            <%
                if (count == NumPerRow) {
                    count = 0;
                    rowNum++;
                    out.println("</div>");
                }
            %>
            <%
                    Repeat1__index++;
                    topsinger_hasData = topsinger.next();
                }
                if (rowNum < RowNumberPerPage) {
                    out.println("</div>");
                }
            %>



            <div class="row">
                <div class="span12">
                    <div>
                        <table border="0" width="50%" align="center">
                            <tr>
                                <td width="23%" align="center"><% if (MM_offset != 0) {%>
                                    <a href="<%=MM_moveFirst%>">第一页</a>
                                    <% } /* end MM_offset != 0 */%>
                                </td>
                                <td width="31%" align="center"><% if (MM_offset != 0) {%>
                                    <a href="<%=MM_movePrev%>">上一页</i></a>
                                    <% } /* end MM_offset != 0 */%>
                                </td>
                                <td width="23%" align="center"><% if (!MM_atTotal) {%>
                                    <a href="<%=MM_moveNext%>">下一页</i></a>
                                    <% } /* end !MM_atTotal */%>
                                </td>
                                <td width="23%" align="center"><% if (!MM_atTotal) {%>
                                    <a href="<%=MM_moveLast%>">最后一页</a>
                                    <% } /* end !MM_atTotal */%>
                                </td>
                            </tr>
                        </table>
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
    topsinger.close();
    Statementtopsinger.close();
    Conntopsinger.close();
%>
