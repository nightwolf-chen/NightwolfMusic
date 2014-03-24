<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="object.Song"%>
<%@page import="api.BaiduOfficialMusicApi"%>
<%@page import="spiders.SpiderTingLRC"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="spiders.SongURLAchiever"%>
<%@page import="java.net.Proxy"%>
<%@page import="java.net.SocketAddress"%>
<%@page import="java.net.InetSocketAddress"%>
<%@ include file="../Connections/spiderting.jsp" %>
<%@page contentType="text/html" pageEncoding="GB2312"%>
<%    String songId = request.getParameter("songid");

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

    Song currentSong = new BaiduOfficialMusicApi().getSongDetail(songName, singerName);
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
        <meta charset="utf-8">
        <title>jPlayer - Circle Player</title>

        <link rel="stylesheet" href="../css/not.the.skin.css">
        <link rel="stylesheet" href="../circle.skin/circle.player.css">

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js"></script>
        <script type="text/javascript" src="../js/jquery.transform2d.js"></script>
        <script type="text/javascript" src="../js/jquery.grab.js"></script>
        <script type="text/javascript" src="../js/jquery.jplayer.js"></script>
        <script type="text/javascript" src="../js/mod.csstransforms.min.js"></script>
        <script type="text/javascript" src="../js/circle.player.js"></script>
        <!--<script type="text/javascript" src="https://getfirebug.com/firebug-lite.js"></script>-->

        <script type="text/javascript">

            $(document).ready(function() {

                var myCirclePlayer = new CirclePlayer("#jquery_jplayer_1",
                        {
                            m4a: "https://href.li/?<%=songURL%>",
                            oga: "https://href.li/?<%=songURL%>"
                        },
                {
                    cssSelectorAncestor: "#cp_container_1"
                });

            });
        </script>

        <script language=Javascript>

            function open_new_window(full_link) {
                window.open('javascript:window.name;', '<script>location.replace("' + full_link + '")<\/script>');
            }

        </script>
    </head>
    <body>

        <!-- The jPlayer div must not be hidden. Keep it at the root of the body element to avoid any such problems. -->
        <div id="jquery_jplayer_1" class="cp-jplayer"></div>

        <!-- This is the 2nd instance's jPlayer div -->
        <div id="jquery_jplayer_2" class="cp-jplayer"></div>

        <div class="prototype-wrapper"> <!-- A wrapper to emulate use in a webpage and center align -->


            <!-- The container for the interface can go where you want to display it. Show and hide it as you need. -->

            <div id="cp_container_1" class="cp-container">
                <div class="cp-buffer-holder"> <!-- .cp-gt50 only needed when buffer is > than 50% -->
                    <div class="cp-buffer-1"></div>
                    <div class="cp-buffer-2"></div>
                </div>
                <div class="cp-progress-holder"> <!-- .cp-gt50 only needed when progress is > than 50% -->
                    <div class="cp-progress-1"></div>
                    <div class="cp-progress-2"></div>
                </div>
                <div class="cp-circle-control"></div>
                <ul class="cp-controls">
                    <li><a class="cp-play" tabindex="1">play</a></li>
                    <li><a class="cp-pause" style="display:none;" tabindex="1">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->
                </ul>
            </div>


            <%
//                songName = new String(songName.getBytes("gb2312"),"utf8");
//                singerName = new String(singerName.getBytes("gb2312"),"utf8");
                songName = URLEncoder.encode(songName, "utf8");
                singerName = URLEncoder.encode(singerName, "utf8");
            %>
            <!--<embed width="400px" height="95px" src="http://box.baidu.com/widget/flash/bdspacesong.swf?&amp;url=&amp;name=<%=songName%>&amp;artist=<%=singerName%>&amp;extra=&amp;autoPlay=false&amp;loop=true" type="application/x-shockwave-flash" allowscriptaccess="never" />-->

            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="400" height="95" id="bdmp3widget8907"><param name="movie" value="https://href.li/?http://box.baidu.com/widget/flash/mbsong.swf?name=%E6%83%85%E4%BA%BA&artist=Beyond&extra="></param><param name="wmode" value="opaque"></param><param name="allowscriptaccess" value="always"></param><embed src="http://box.baidu.com/widget/flash/mbsong.swf?name=%E6%83%85%E4%BA%BA&artist=Beyond&extra=" type="application/x-shockwave-flash" wmode="opaque" allowscriptaccess="always" width="400" height="95" name="bdmp3widget8907"></embed></object>

        </div>

        <div class="row"> 
            <button onclick="createPlayer()
                            ;" value="create player"></button> 
        </div>
    </body>
</html>

