<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ include file="../../Connections/spiderting.jsp" %>
<%
Driver DriverRecordset1 = (Driver)Class.forName(MM_spiderting_DRIVER).newInstance();
Connection ConnRecordset1 = DriverManager.getConnection(MM_spiderting_STRING,MM_spiderting_USERNAME,MM_spiderting_PASSWORD);
PreparedStatement StatementRecordset1 = ConnRecordset1.prepareStatement("SELECT SongName, SongId, SingerName, SingerId, SingerPageURL, DownURL FROM spiderting.spiderting6");
ResultSet Recordset1 = StatementRecordset1.executeQuery();
boolean Recordset1_isEmpty = !Recordset1.next();
boolean Recordset1_hasData = !Recordset1_isEmpty;
Object Recordset1_data;
int Recordset1_numRows = 0;
%>
<%
int Repeat1__numRows = 20;
int Repeat1__index = 0;
Recordset1_numRows += Repeat1__numRows;
%>
<%
// *** Recordset Stats, Move To Record, and Go To Record: declare stats variables

int Recordset1_first = 1;
int Recordset1_last  = 1;
int Recordset1_total = -1;

if (Recordset1_isEmpty) {
  Recordset1_total = Recordset1_first = Recordset1_last = 0;
}

//set the number of rows displayed on this page
if (Recordset1_numRows == 0) {
  Recordset1_numRows = 1;
}
%>
<% String MM_paramName = ""; %>
<%
// *** Move To Record and Go To Record: declare variables

ResultSet MM_rs = Recordset1;
int       MM_rsCount = Recordset1_total;
int       MM_size = Recordset1_numRows;
String    MM_uniqueCol = "";
          MM_paramName = "";
int       MM_offset = 0;
boolean   MM_atTotal = false;
boolean   MM_paramIsDefined = (MM_paramName.length() != 0 && request.getParameter(MM_paramName) != null);
%>
<%
// *** Move To Record: handle 'index' or 'offset' parameter

if (!MM_paramIsDefined && MM_rsCount != 0) {

  //use index parameter if defined, otherwise use offset parameter
  String r = request.getParameter("index");
  if (r==null) r = request.getParameter("offset");
  if (r!=null) MM_offset = Integer.parseInt(r);

  // if we have a record count, check if we are past the end of the recordset
  if (MM_rsCount != -1) {
    if (MM_offset >= MM_rsCount || MM_offset == -1) {  // past end or move last
      if (MM_rsCount % MM_size != 0)    // last page not a full repeat region
        MM_offset = MM_rsCount - MM_rsCount % MM_size;
      else
        MM_offset = MM_rsCount - MM_size;
    }
  }

  //move the cursor to the selected record
  int i;
  for (i=0; Recordset1_hasData && (i < MM_offset || MM_offset == -1); i++) {
    Recordset1_hasData = MM_rs.next();
  }
  if (!Recordset1_hasData) MM_offset = i;  // set MM_offset to the last possible record
}
%>
<%
// *** Move To Record: if we dont know the record count, check the display range

if (MM_rsCount == -1) {

  // walk to the end of the display range for this page
  int i;
  for (i=MM_offset; Recordset1_hasData && (MM_size < 0 || i < MM_offset + MM_size); i++) {
    Recordset1_hasData = MM_rs.next();
  }

  // if we walked off the end of the recordset, set MM_rsCount and MM_size
  if (!Recordset1_hasData) {
    MM_rsCount = i;
    if (MM_size < 0 || MM_size > MM_rsCount) MM_size = MM_rsCount;
  }

  // if we walked off the end, set the offset based on page size
  if (!Recordset1_hasData && !MM_paramIsDefined) {
    if (MM_offset > MM_rsCount - MM_size || MM_offset == -1) { //check if past end or last
      if (MM_rsCount % MM_size != 0)  //last page has less records than MM_size
        MM_offset = MM_rsCount - MM_rsCount % MM_size;
      else
        MM_offset = MM_rsCount - MM_size;
    }
  }

  // reset the cursor to the beginning
  Recordset1.close();
  Recordset1 = StatementRecordset1.executeQuery();
  Recordset1_hasData = Recordset1.next();
  MM_rs = Recordset1;

  // move the cursor to the selected record
  for (i=0; Recordset1_hasData && i < MM_offset; i++) {
    Recordset1_hasData = MM_rs.next();
  }
}
%>
<%
// *** Move To Record: update recordset stats

// set the first and last displayed record
Recordset1_first = MM_offset + 1;
Recordset1_last  = MM_offset + MM_size;
if (MM_rsCount != -1) {
  Recordset1_first = Math.min(Recordset1_first, MM_rsCount);
  Recordset1_last  = Math.min(Recordset1_last, MM_rsCount);
}

// set the boolean used by hide region to check if we are on the last record
MM_atTotal  = (MM_rsCount != -1 && MM_offset + MM_size >= MM_rsCount);
%>
<%
// *** Go To Record and Move To Record: create strings for maintaining URL and Form parameters

String MM_keepBoth,MM_keepURL="",MM_keepForm="",MM_keepNone="";
String[] MM_removeList = { "index", MM_paramName };

// create the MM_keepURL string
if (request.getQueryString() != null) {
  MM_keepURL = '&' + request.getQueryString();
  for (int i=0; i < MM_removeList.length && MM_removeList[i].length() != 0; i++) {
  int start = MM_keepURL.indexOf(MM_removeList[i]) - 1;
    if (start >= 0 && MM_keepURL.charAt(start) == '&' &&
        MM_keepURL.charAt(start + MM_removeList[i].length() + 1) == '=') {
      int stop = MM_keepURL.indexOf('&', start + 1);
      if (stop == -1) stop = MM_keepURL.length();
      MM_keepURL = MM_keepURL.substring(0,start) + MM_keepURL.substring(stop);
    }
  }
}

// add the Form variables to the MM_keepForm string
if (request.getParameterNames().hasMoreElements()) {
  java.util.Enumeration items = request.getParameterNames();
  while (items.hasMoreElements()) {
    String nextItem = (String)items.nextElement();
    boolean found = false;
    for (int i=0; !found && i < MM_removeList.length; i++) {
      if (MM_removeList[i].equals(nextItem)) found = true;
    }
    if (!found && MM_keepURL.indexOf('&' + nextItem + '=') == -1) {
      MM_keepForm = MM_keepForm + '&' + nextItem + '=' + java.net.URLEncoder.encode(request.getParameter(nextItem));
    }
  }
}

String tempStr = "";
for (int i=0; i < MM_keepURL.length(); i++) {
  if (MM_keepURL.charAt(i) == '<') tempStr = tempStr + "&lt;";
  else if (MM_keepURL.charAt(i) == '>') tempStr = tempStr + "&gt;";
  else if (MM_keepURL.charAt(i) == '"') tempStr = tempStr +  "&quot;";
  else tempStr = tempStr + MM_keepURL.charAt(i);
}
MM_keepURL = tempStr;

tempStr = "";
for (int i=0; i < MM_keepForm.length(); i++) {
  if (MM_keepForm.charAt(i) == '<') tempStr = tempStr + "&lt;";
  else if (MM_keepForm.charAt(i) == '>') tempStr = tempStr + "&gt;";
  else if (MM_keepForm.charAt(i) == '"') tempStr = tempStr +  "&quot;";
  else tempStr = tempStr + MM_keepForm.charAt(i);
}
MM_keepForm = tempStr;

// create the Form + URL string and remove the intial '&' from each of the strings
MM_keepBoth = MM_keepURL + MM_keepForm;
if (MM_keepBoth.length() > 0) MM_keepBoth = MM_keepBoth.substring(1);
if (MM_keepURL.length() > 0)  MM_keepURL = MM_keepURL.substring(1);
if (MM_keepForm.length() > 0) MM_keepForm = MM_keepForm.substring(1);
%>
<%
// *** Move To Record: set the strings for the first, last, next, and previous links

String MM_moveFirst,MM_moveLast,MM_moveNext,MM_movePrev;
{
  String MM_keepMove = MM_keepBoth;  // keep both Form and URL parameters for moves
  String MM_moveParam = "index=";

  // if the page has a repeated region, remove 'offset' from the maintained parameters
  if (MM_size > 1) {
    MM_moveParam = "offset=";
    int start = MM_keepMove.indexOf(MM_moveParam);
    if (start != -1 && (start == 0 || MM_keepMove.charAt(start-1) == '&')) {
      int stop = MM_keepMove.indexOf('&', start);
      if (start == 0 && stop != -1) stop++;
      if (stop == -1) stop = MM_keepMove.length();
      if (start > 0) start--;
      MM_keepMove = MM_keepMove.substring(0,start) + MM_keepMove.substring(stop);
    }
  }

  // set the strings for the move to links
  StringBuffer urlStr = new StringBuffer(request.getRequestURI()).append('?').append(MM_keepMove);
  if (MM_keepMove.length() > 0) urlStr.append('&');
  urlStr.append(MM_moveParam);
  MM_moveFirst = urlStr + "0";
  MM_moveLast  = urlStr + "-1";
  MM_moveNext  = urlStr + Integer.toString(MM_offset+MM_size);
  MM_movePrev  = urlStr + Integer.toString(Math.max(MM_offset-MM_size,0));
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
.logo {
	background-image: url(title.jpg);
	background-repeat: no-repeat;
	position: relative;
	height: 200px;
	width: 380px;
	float: left;
	top: auto;
	right: auto;
	left: auto;
	bottom: auto;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
	margin-left: 0px;
}
.login {
	float: left;
	height: 200px;
	width: 195px;
	background-repeat: no-repeat;
	background-image: url(loginback.png);
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 5px;
}
.myhead .login #form3 {
	font-family: Georgia, "Times New Roman", Times, serif, "微软雅黑", "幼圆", sans-serif, Euphemia, "宋体-方正超大字符集", "楷体";
	font-size: 16px;
	color: #FFFFFF;
	background-color: #333333;
	vertical-align: middle;
	height: 200px;
	width: 200px;
	margin: auto;
}
main {
	margin-top: 20px;
	margin-right: auto;
	margin-bottom: 10px;
	margin-left: auto;
	width: 800px;
}
button {
	font-family: "新宋体";
	background-image: url(wooden_back11.jpg);
}
.intro {
	float: left;
	height: 200px;
	width: 190px;
	font-size: 18px;
	font-style: normal;
	color: #FFFFFF;
	font-family: Georgia, "Times New Roman", Times, serif, "微软雅黑", "幼圆", sans-serif, Euphemia, "宋体-方正超大字符集", "楷体";
	font-variant: normal;
	line-height: normal;
	margin: 5px;
}
body {
	background-image: url();
	width: 800px;
	margin: auto;
}
.myhead {
	padding: 5px;
	height: 200px;
	width: 800px;
	margin-top: 30px;
	margin-right: auto;
	margin-bottom: 15px;
	margin-left: auto;
}
struct {
	width: 800px;
	text-align: center;
	height: auto;
	background-image: url(Background33.jpg);
	background-repeat: repeat;
	margin: 25px;
}
.myitem {
	background-image: url(back3.gif);
	background-position: center center;
	height: 30px;
	width: auto;
	background-repeat: repeat;
	margin-top: 10px;
	margin-right: auto;
	margin-bottom: 10px;
	margin-left: auto;
	font-size: 12px;
	color: #000000;
}
main {
	width: 800px;
	margin: auto;
}
.myhead1 {
	height: 200px;
	margin-top: 30px;
	margin-right: auto;
	margin-bottom: 10px;
	margin-left: auto;
	width: 800px;
	background-image: url(background2.jpg);
}
.pageControl {
	color: #FFFFFF;
	width: 800px;
	margin: auto;
}
.foot {
	font-family: "宋体";
	font-size: 20px;
	font-style: normal;
	line-height: normal;
	color: #FFFFFF;
	background-color: #0000FF;
	font-weight: bold;
	text-align: center;
	width: 800px;
	margin-right: auto;
	margin-left: auto;
}
-->
</style>
</head>

<body>
<div class="struct">
<div class="foot">Welcome to Nightwolf Music</div>
  <div class="myhead1">
    <div class="logo" id="logo"></div>
    <div class="intro">
      <p>跟随着自由的心，聆听来自灵魂的声音，爱音乐--Nightwolf</p>
      <form id="form2" name="form2" method="post" action="">
        <label>
        <div align="center"><span class="STYLE3">为我推荐音乐</span>:
          <input type="submit" name="Submit2" value="音乐推荐" />
        </div>
        </label>
      </form>
      <p>&nbsp;</p>
    </div>
    <div class="login">
      <form id="form3" name="form3" method="post" action="">
        <label>
        <div align="center"><br />
          <br />
          <br />
        username
          <input type="text" name="textfield2" />
          <br />
          <br />
        </div>
        </label>
        <label>
        <div align="center">password
          <input type="text" name="textfield3" />
        </div>
        </label>
        <p align="center">
          <label></label>
          <label>
          <input name="submit" type="submit" id="submit" value="登录" />
          </label>
        </p>
      </form>
    </div>
    <p>&nbsp;</p>
    <div align="center"></div>
  </div>
  
  
  
  
  
  
  <div class="main">
    <% while ((Recordset1_hasData)&&(Repeat1__numRows-- != 0)) { %>
      <div class="myitem">
        <table width="769" border="0" bordercolor="">
          <tr>
            <td width="323" height="25">歌曲：<%=(((Recordset1_data = Recordset1.getObject("SongName"))==null || Recordset1.wasNull())?"":Recordset1_data)%></td>
            <td width="338">歌手：<%=(((Recordset1_data = Recordset1.getObject("SingerName"))==null || Recordset1.wasNull())?"":Recordset1_data)%></td>
            <td width="57"><a href="../../Player.jsp?songid=<%=(((Recordset1_data = Recordset1.getObject("SongId"))==null || Recordset1.wasNull())?"":Recordset1_data)%>">试听</a></td>
            <td width="33"><a href="Download.jsp?songid=<%=(((Recordset1_data = Recordset1.getObject("SongId"))==null || Recordset1.wasNull())?"":Recordset1_data)%>">下载</a></td>
          </tr>
        </table>
      </div>
      <%
  Repeat1__index++;
  Recordset1_hasData = Recordset1.next();
}
%>
      <div class="pageControl">
        <table border="0" width="50%" align="center">
          <tr>
            <td width="23%" align="center"><% if (MM_offset !=0) { %>
                <a href="<%=MM_moveFirst%>">第一页</a>
                <% } /* end MM_offset != 0 */ %>
            </td>
            <td width="31%" align="center"><% if (MM_offset !=0) { %>
                <a href="<%=MM_movePrev%>">前一页</a>
                <% } /* end MM_offset != 0 */ %>
            </td>
            <td width="23%" align="center"><% if (!MM_atTotal) { %>
                <a href="<%=MM_moveNext%>">下一页</a>
                <% } /* end !MM_atTotal */ %>
            </td>
            <td width="23%" align="center"><% if (!MM_atTotal) { %>
                <a href="<%=MM_moveLast%>">最后一页</a>
                <% } /* end !MM_atTotal */ %>
            </td>
          </tr>
        </table>
    </div>
</div>
 
  <div class="foot">Copyright 2011 nightwolf all rignt resered.Powered by Nightwolf-chen. </div>
</div>
</body>
</html>
<%
Recordset1.close();
StatementRecordset1.close();
ConnRecordset1.close();
%>
