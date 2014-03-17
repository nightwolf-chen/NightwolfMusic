<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" %>
<%@ include file="../../Connections/spiderting.jsp" %>
<%
Driver DriverRecordset1 = (Driver)Class.forName(MM_spiderting_DRIVER).newInstance();
Connection ConnRecordset1 = DriverManager.getConnection(MM_spiderting_STRING,MM_spiderting_USERNAME,MM_spiderting_PASSWORD);
PreparedStatement StatementRecordset1 = ConnRecordset1.prepareStatement("SELECT SongName, SongId, SingerName, SingerId, SingerPageURL, DownURL FROM spiderting2");
ResultSet Recordset1 = StatementRecordset1.executeQuery();
boolean Recordset1_isEmpty = !Recordset1.next();
boolean Recordset1_hasData = !Recordset1_isEmpty;
Object Recordset1_data;
int Recordset1_numRows = 0;
%>
<%
int Repeat1__numRows = 10;
int Repeat1__index = 0;
Recordset1_numRows += Repeat1__numRows;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html" charset="gb2312" />
<link href="css.css" type="text/css" rel="stylesheet" />
<title></title>
<script src="flash/flash.js" language="JavaScript" type="text/javascript"></script>
</head>
<body>
<div id="head">
  <script language="JavaScript" type="text/javascript">RunFlash();</script>
</div>
<div id="page">
  <div style="background-color:#ffffff; height:100%;">
    <div id="l">
      <div style="background-color:#E5E5E5;" align="right"><img src="i/well_rt.gif" width="10" height="10" border="0" alt=""/></div>
      <div style="background-color:#E5E5E5; padding:16px 25px 20px 20px;">
        <h1><img src="i/h_wellcome.gif" width="327" height="26" border="0" alt=""/></h1>
        <h1>Lorem ipsum dolor sit amet, consectetuer adipiscing elit!</h1>
        <p>Nunc nonummy est id ipsum. Etiam aliquam porttitor lacus. Integer tincidunt convallis enim. Aenean imperdiet nisl vitae massa. Phasellus et sem. Mauris else volutpat. Donec et ipsum. Pellentesque fringilla fermentum ipsum. Mauris with ultricies tristique leo. Suspendisse sodales neque at lorem. Aliquam consectetuer mi eget mauris.</p>
      </div>
      <div style="background-color:#E5E5E5;"><img src="i/well_lb.gif" width="10" height="10" border="0" alt=""/></div>
      <br/>
      <br/>
      <p><img src="i/h_best.gif" width="109" height="22" border="0" alt="" hspace="19"/></p>
      <div style="background-color:#E5E5E5; padding:7px 0px 0px 0px; height:23px;">
        <div style="float:left; padding-left:19px;">¡í</div>
        <div style="float:left; padding-left:29px;">Artist</div>
        <div style="float:left; padding-left:119px;">Track</div>
        <div style="float:left; padding-left:125px;">Lincs</div>
      </div>
      
      
      
      
      
      <% while ((Recordset1_hasData)&&(Repeat1__numRows-- != 0)) { %>
        <div class="num"></div>
        <div class="artist"><%=(((Recordset1_data = Recordset1.getObject("SingerName"))==null || Recordset1.wasNull())?"":Recordset1_data)%></div>
        <div class="track"><%=(((Recordset1_data = Recordset1.getObject("SongName"))==null || Recordset1.wasNull())?"":Recordset1_data)%></div>
        <div class="downloads"><b><a href="Download.jsp?songid=<%=(((Recordset1_data = Recordset1.getObject("SongId"))==null || Recordset1.wasNull())?"":Recordset1_data)%>">Download</a></b></div>
        <div style="background:url(i/slash.gif); height:30px; clear: both;">
          <div class="num"></div>
          <div class="artist"></div>
          <div class="track"></div>
          <div class="downloads"><b><a href="../../Player.jsp?songid=<%=(((Recordset1_data = Recordset1.getObject("SongId"))==null || Recordset1.wasNull())?"":Recordset1_data)%>">Play</a></b></div>
        </div>
        <%
  Repeat1__index++;
  Recordset1_hasData = Recordset1.next();
}
%>
</div>
    <div id="rcol">
      <div style="background:url(i/login_bg.gif) repeat-y; width:263px;">
        <div style="background:url(i/login_t.gif) no-repeat; padding:71px 0px 0px 30px; color:#4A4740; font-size:10px;"> LOGIN:<br/>
          <input type="text"/>
          <br/>
          PASSWORD:<br/>
          <input type="password"/>
          <br/>
          <div style="float:right"><a href=""><img src="i/b_login.gif" width="71" height="33" border="0" alt=""/></a></div>
          <a href="">Forgot password</a><br/>
          <a href="">Registration</a> </div>
        <div><img src="i/login_b.gif" width="263" height="8" border="0" alt=""/></div>
      </div>
      <br/>
      <br/>
      <div style="padding-right:20px;">
        <div><img src="i/h_fivenews.gif" width="65" height="15" border="0" alt="" hspace="27px;"/></div>
        <div style="height:23px; background:url(i/dots_hor.gif) repeat-x 0px 11px;"></div>
        <b><img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>Mumyi Troll - Ya budu</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Download</a></div>
        <b><img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>PPK - Rezulation</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Download</a></div>
        <b><img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>Ostin Lae - Free</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Download</a></div>
        <b><img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>Madonna - Music</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Download</a></div>
        <b><img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>No Doubt - 111</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Download</a></div>
        <br/>
        <br/>
        <br/>
        <br/>
        <div><img src="i/h_community.gif" width="103" height="20" border="0" alt="" hspace="27px;"/></div>
        <div style="height:23px; background:url(i/dots_hor.gif) repeat-x 0px 11px;"></div>
        <img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>Mr Dir wrote:<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Hello my friend and people</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Read more</a></div>
        <img src="i/arrows.gif" width="26" height="8" border="0" alt=""/>Angel wrote:<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Heeyyy! Every body man</b><br/>
        <div style="background:url(i/slash.gif); padding:3px 25px 5px 0px; margin-top:3px; text-align:right;"><img src="i/arrow.gif" width="10" height="6" border="0" alt=""/><a href="">Read more</a></div>
      </div>
    </div>
  </div>
  <div id="foot">2007 ? Copyright <a href="">CompanyName</a>. All rights reserved. Read <a href="">Legal policy</a> and <a href="">Privacy policy</a></div>
</div>
</body>
</html>
<%
Recordset1.close();
StatementRecordset1.close();
ConnRecordset1.close();
%>

