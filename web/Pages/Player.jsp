<%@page import="api.BaiduFolkMusicApi"%>
<%@page import="object.Song"%>
<%@page import="spiders.SpiderTingLRC"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="spiders.SongURLAchiever"%>
<%@page import="java.net.Proxy"%>
<%@page import="java.net.SocketAddress"%>
<%@page import="java.net.InetSocketAddress"%>
<%@ include file="../Connections/spiderting.jsp" %>
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%
   
    String songId = request.getParameter("songid");
    
    //out.println("songURL"+songURL+"songid"+songId+"url:"+SongURLAchiever.getCurrentDownURL(songId)) ;
    Driver DriverRecordset1 = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
    Connection Conn = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
    PreparedStatement pstmt = Conn.prepareStatement("update song set visitedtime=visitedtime+1 where songid=?");
    pstmt.setString(1, songId);
    pstmt.executeUpdate();
%>
<%//这段代码对用户访问历史数据进行更新。

    request.setCharacterEncoding("gb2312");
    String songName = ""; //= request.getParameter("songname");
    String singerName = "";//= request.getParameter("singername");
    /*
    if(songName!=null)
    songName = URLDecoder.decode(songName, "utf-8");
    if(singerName!=null)
    singerName = URLDecoder.decode(singerName, "utf-8");
     * */
    String singerId = request.getParameter("singerid");
    String username = (String) session.getAttribute("username");

    Statement songStament = Conn.createStatement();
    ResultSet songResultSet = songStament.executeQuery("select * from song where songid=" + songId + "");
    
    if (songResultSet.next()) {
        songName = songResultSet.getString("songname");
        singerName = songResultSet.getString("singername");
    }
    if (username != null) {
        Statement stmt1 = Conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select * from userhistory where username='" + username + "' and songid='" + songId + "'");
        //stmt1.close();
        if (rs1.next()) {
            Statement stmt2 = Conn.createStatement();
            stmt2.executeUpdate("update userhistory set visitedtimes=visitedtimes+1 where username='" + username + "' and songid ='" + songId + "'");
            stmt2.close();
        } else {
            //Conn.
            PreparedStatement pstmtUserhistory = Conn.prepareStatement("insert into userhistory(username,songid,songname,singerid,singername,visitedtimes,createtime) values(?,?,?,?,?,?,?)");
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
        }
        stmt1.close();
        pstmt.close();

        rs1.close();
    }
    System.out.println(songName + " " + singerName + " " + singerId);
    
//    Song currentSong = new BaiduMusicApi().getSongDetail(songName, singerName);
    Song currentSong = new BaiduFolkMusicApi().getSongById(songId);
    String songURL = currentSong.getSongUrl();
%>
<%//这段代码对用户喜爱的歌曲的数据进行更新

    if (username != null) {
        Statement stmtFavor = Conn.createStatement();
        ResultSet rsFavor = stmtFavor.executeQuery("select * from userfavorite where username='" + username + "' and songid='" + songId + "'");
        if (rsFavor.next()) {
            Statement stmtFavorAdd = Conn.createStatement();
            stmtFavorAdd.executeUpdate("update userfavorite set visitedtime=visitedtime+1 where username='" + username + "' and songid='" + songId + "'");
            stmtFavorAdd.close();
        }
        stmtFavor.close();
        rsFavor.close();
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
                //out.println("name:"+fileName) ;
                String blank = "" ;
                if(fileName.equals("/Pages/Player.jsp")){
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
            <div class="row">
                <div class="span2"></div>
                <div class="span2">
                    <a><h4></strong><%=songName%></h4></a>

                </div>
                <div class="span2">
                    <a href="SingerInfo.jsp?singerid=<%=singerId%>" target="_blank"><h4><%=singerName%></h4></a>
                        <div>
                            <div><a href="AddResult.jsp?songid=<%=songId%>" target="_blank">收藏</a><i class="icon-heart"></i></div>
                        </div>
                </div>


                <div class="span1">
                    <div class="player">
                        <div>
                            <div align="center">
                                <object class="playerbox"  id="MediaPlayer1" width="300" height="68" classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95"
                                        codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715" border="0" standby="Loading Microsoft Windows Media Player components..."
                                        type="application/x-oleobject">
                                    <param name="FileName"value="<%=songURL%>"/>
                                    <param name="ShowControls" value="1"/>
                                    <param name="ShowPositionControls" value="0"/>
                                    <param name="Show??Controls" value="1"/>
                                    <param name="ShowTracker" value="1"/>
                                    <param name="ShowDisplay" value="0"/>
                                    <param name="ShowStatusBar" value="1"/>
                                    <param name="AutoSize" value="0"/>
                                    <param name="ShowGotoBar" value="0"/>
                                    <param name="ShowCaptioning" value="0"/>
                                    <param name="AutoStart" value="1"/>
                                    <param name="PlayCount" value="0"/>
                                    <param name="AnimationAtStart" value="0"/>
                                    <param name="TransparentAtStart" value="0"/>
                                    <param name="AllowScan" value="0"/>
                                    <param name="EnableContextMenu" value="1"/>
                                    <param name="ClickToPlay" value="0"/>
                                    <param name="InvokeURLs" value="1"/>
                                    <param name="DefaultFrame" value="datawindow"/>
                                    <embed src="<%=songURL%>" border="0" width="300" height="68"
                                           type="application/x-mplayer2"
                                           pluginspage="http://www.microsoft.com/isapi/redir.dll?prd=windows&sbp=mediaplayer&ar=media&sba=plugin&"
                                           name="MediaPlayer" showcontrols="1" showpositioncontrols="0" showtracker="1" showdisplay="0"
                                           showstatusbar="1"
                                           autosize="0"
                                           showgotobar="0" showcaptioning="0" autostart="1" autorewind="0"
                                           animationatstart="0" transparentatstart="0" allowscan="1"
                                           enablecontextmenu="1" clicktoplay="1" invokeurls="<%=songURL%>"
                                           defaultframe="datawindow"/>                      
                                </object>
                            </div>
                        </div>		
                    </div>
                </div><!--end of span5-->
            </div><!--row end-->

            <div class="row">
                <div class="span10">
                    <div align="center">歌词:
                        <%
                          
                            int numMin = 15;
                            String lrcString = currentSong.getLrc();
                            if(lrcString == null){
                                return ;
                            }
                            String[] lrcStrings = lrcString.split("\n");
                            for (String tmp : lrcStrings) {
                                out.println(tmp + "<br>");
                                numMin--;
                            }
                            
                            while(numMin-- >= 0){
                                out.println("<br>");
                            }
                        %>
                    </div>
                </div>
            </div><!--end of row-->

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
