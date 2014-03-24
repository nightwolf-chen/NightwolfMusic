package org.apache.jsp.Pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.nio.charset.Charset;
import java.net.URLDecoder;
import ChineseToPinyin.Pinyin;
import java.net.URLEncoder;
import java.sql.*;
import org.json.JSONObject;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/Pages/../Connections/spiderting.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\r\n");
      out.write("\r\n");

// FileName="mysql_jdbc_conn.htm"
// Type="JDBC" ""
// DesigntimeType="JDBC"
// HTTP="false"
// Catalog=""
// Schema=""
    /*
     String MM_spiderting_DRIVER = "com.mysql.jdbc.Driver";
     String MM_spiderting_USERNAME = "admin";
     String MM_spiderting_PASSWORD = "a13827970772b";
     String MM_spiderting_STRING = "jdbc:mysql://127.11.116.129:3306/music?characterEncoding=GBK";
     */

    String databaseInfo = java.lang.System.getenv("VCAP_SERVICES");
    String host = "127.0.0.1";
    String port = "3306";
    String dbPassword = "123";
    String dbName  = "nightwolf_music";
    String dbUsername  = "root";
   
    if (databaseInfo != null) {

        JSONObject jsonObject = new JSONObject(databaseInfo);
        JSONObject mysqlObj = jsonObject.getJSONArray("mysql-5.1").getJSONObject(0);
        JSONObject credentials = mysqlObj.getJSONObject("credentials");

        host = credentials.getString("hostname");
        port = String.valueOf(credentials.getInt("port"));
        dbName = credentials.getString("name");
        dbUsername = credentials.getString("username");
        dbPassword = credentials.getString("password");
    }

    String MM_spiderting_DRIVER = "com.mysql.jdbc.Driver";
    String MM_spiderting_USERNAME = dbUsername;
    String MM_spiderting_PASSWORD = dbPassword;
    String MM_spiderting_STRING = "jdbc:mysql://"+host+":"+port+"/"+dbName+"";


      out.write('\r');
      out.write('\n');
      out.write('\n');

    request.setCharacterEncoding("gb2312");
    String key = request.getParameter("key");
    String sql = "";
    String tmp = request.getParameter("offset");
    if (tmp != null && key != null) {
        key = URLDecoder.decode(key, "gb2312");
        System.out.println("decoded key:" + key);
    }
    if (key == null) {
        sql = "select * from song order by visitedtime desc limit 100";
    } else {
        if (key.getBytes().length > key.length()) {
            key = Pinyin.getPinyin(key);
        }
        // System.out.println("key:"+key) ;
        // sql = "select * from song where match(sinpinyin) against('"+key+"') or match(sonpinyin) against('"+key+"') ";
        // sql = "select * from song where match(sonpinyin) against('"+key+"' in boolean mode) union all select * from song where match(sinpinyin) against('"+key+"')" ;
        sql = "select * from song where match(sonpinyin,sinpinyin) against('" + key + "')";//This sql has the bese performerce
        //SELECT id FROM reviews WHERE MATCH (data) AGAINST ('hell rocks' IN BOOLEAN MODE);
    }
    Driver DriverRecordset1 = (Driver) Class.forName(MM_spiderting_DRIVER).newInstance();
    Connection ConnRecordset1 = DriverManager.getConnection(MM_spiderting_STRING, MM_spiderting_USERNAME, MM_spiderting_PASSWORD);
    PreparedStatement StatementRecordset1 = ConnRecordset1.prepareStatement(sql);

    ResultSet Recordset1 = StatementRecordset1.executeQuery();
    //System.out.println("rows:"+Recordset1.getFetchSize());
    boolean Recordset1_isEmpty = !Recordset1.next();
    boolean Recordset1_hasData = !Recordset1_isEmpty;
    Object Recordset1_data;
    int Recordset1_numRows = 0;

      out.write('\n');

    int Repeat1__numRows = 23;
    int Repeat1__index = 0;
    Recordset1_numRows += Repeat1__numRows;

      out.write('\n');

// *** Recordset Stats, Move To Record, and Go To Record: declare stats variables

    int Recordset1_first = 1;
    int Recordset1_last = 1;
    int Recordset1_total = -1;

    if (Recordset1_isEmpty) {
        Recordset1_total = Recordset1_first = Recordset1_last = 0;
    }

//set the number of rows displayed on this page
    if (Recordset1_numRows == 0) {
        Recordset1_numRows = 1;
    }

      out.write('\n');
 String MM_paramName = "";
      out.write('\n');

// *** Move To Record and Go To Record: declare variables

    ResultSet MM_rs = Recordset1;
    int MM_rsCount = Recordset1_total;
    int MM_size = Recordset1_numRows;
    String MM_uniqueCol = "";
    MM_paramName = "";
    int MM_offset = 0;
    boolean MM_atTotal = false;
    boolean MM_paramIsDefined = (MM_paramName.length() != 0 && request.getParameter(MM_paramName) != null);

      out.write('\n');

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
        for (i = 0; Recordset1_hasData && (i < MM_offset || MM_offset == -1); i++) {
            Recordset1_hasData = MM_rs.next();
        }
        if (!Recordset1_hasData) {
            MM_offset = i;  // set MM_offset to the last possible record
        }
    }

      out.write('\n');

// *** Move To Record: if we dont know the record count, check the display range

    if (MM_rsCount == -1) {

        // walk to the end of the display range for this page
        int i;
        for (i = MM_offset; Recordset1_hasData && (MM_size < 0 || i < MM_offset + MM_size); i++) {
            Recordset1_hasData = MM_rs.next();
        }

        // if we walked off the end of the recordset, set MM_rsCount and MM_size
        if (!Recordset1_hasData) {
            MM_rsCount = i;
            if (MM_size < 0 || MM_size > MM_rsCount) {
                MM_size = MM_rsCount;
            }
        }

        // if we walked off the end, set the offset based on page size
        if (!Recordset1_hasData && !MM_paramIsDefined) {
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
        Recordset1.close();
        Recordset1 = StatementRecordset1.executeQuery();
        Recordset1_hasData = Recordset1.next();
        MM_rs = Recordset1;

        // move the cursor to the selected record
        for (i = 0; Recordset1_hasData && i < MM_offset; i++) {
            Recordset1_hasData = MM_rs.next();
        }
    }

      out.write('\n');

// *** Move To Record: update recordset stats

// set the first and last displayed record
    Recordset1_first = MM_offset + 1;
    Recordset1_last = MM_offset + MM_size;
    if (MM_rsCount != -1) {
        Recordset1_first = Math.min(Recordset1_first, MM_rsCount);
        Recordset1_last = Math.min(Recordset1_last, MM_rsCount);
    }

// set the boolean used by hide region to check if we are on the last record
    MM_atTotal = (MM_rsCount != -1 && MM_offset + MM_size >= MM_rsCount);

      out.write('\n');

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
                MM_keepForm = MM_keepForm + '&' + nextItem + '=' + java.net.URLEncoder.encode(request.getParameter(nextItem), "gb2312");
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
    MM_keepForm = "";//!!!!

      out.write('\n');

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
        String myParamentKeeper = "";
        if (key != null) {
            myParamentKeeper = "key=" + key + "&";
        }
        urlStr = new StringBuffer(request.getRequestURI() + "?" + myParamentKeeper + "offset=");
        MM_moveFirst = urlStr + "0";
        MM_moveLast = urlStr + "-1";
        MM_moveNext = urlStr + Integer.toString(MM_offset + MM_size);
        MM_movePrev = urlStr + Integer.toString(Math.max(MM_offset - MM_size, 0));
    }

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"GBK\">\n");
      out.write("        <title>Nightwolf Music</title>\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("        <meta name=\"description\" content=\"\">\n");
      out.write("        <meta name=\"author\" content=\"\">\n");
      out.write("\n");
      out.write("        <!-- Le styles -->\n");
      out.write("        <link href=\"../assets/css/bootstrap.css\" rel=\"stylesheet\">\n");
      out.write("        <style>\n");
      out.write("            body {\n");
      out.write("                padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        <link href=\"../assets/css/bootstrap-responsive.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"../css/metro.css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->\n");
      out.write("        <!--[if lt IE 9]>\n");
      out.write("          <script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n");
      out.write("        <![endif]-->\n");
      out.write("\n");
      out.write("        <!-- Fav and touch icons -->\n");
      out.write("        <link rel=\"apple-touch-icon-precomposed\" sizes=\"144x144\" href=\"../assets/ico/apple-touch-icon-144-precomposed.png\">\n");
      out.write("        <link rel=\"apple-touch-icon-precomposed\" sizes=\"114x114\" href=\"../assets/ico/apple-touch-icon-114-precomposed.png\">\n");
      out.write("        <link rel=\"apple-touch-icon-precomposed\" sizes=\"72x72\" href=\"../assets/ico/apple-touch-icon-72-precomposed.png\">\n");
      out.write("        <link rel=\"apple-touch-icon-precomposed\" href=\"../assets/ico/apple-touch-icon-57-precomposed.png\">\n");
      out.write("        <link rel=\"shortcut icon\" href=\"../assets/ico/favicon.png\">\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("       <div class=\"navbar navbar-inverse navbar-fixed-top\">\n");
      out.write("          ");

                String fileName = request.getServletPath();
               // out.println("name:"+servletName) ;
                String blank = "" ;
                if(fileName.equals("/Pages/Player.jsp ")){
                    blank="target=\"_blank\"";
                }
          
      out.write("\n");
      out.write("            <div class=\"navbar-inner\">\n");
      out.write("                <div class=\"container\">\n");
      out.write("                    <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n");
      out.write("                        <span class=\"icon-bar\"></span>\n");
      out.write("                        <span class=\"icon-bar\"></span>\n");
      out.write("                        <span class=\"icon-bar\"></span>\n");
      out.write("                    </a>\n");
      out.write("                    <a class=\"brand\" href=\"index.jsp\" ");
      out.print(blank);
      out.write(">音乐首页</a>\n");
      out.write("                    <div class=\"nav-collapse collapse\">\n");
      out.write("                        <ul class=\"nav\">\n");
      out.write("                            <li><a href=\"recommendation.jsp\" ");
      out.print(blank);
      out.write(">音乐推荐</a></li>\n");
      out.write("                            ");

                                if (session.getAttribute("username") == null) {
                                    out.println("<li><a href=\"login.jsp\" "+blank+">登录</a></li>");
                                    out.println("<li><a href=\"register.jsp\" "+blank+">注册</a></li>");
                                } else {
                                    out.println("<li><a href=\"user.jsp\" "+blank+">个人主页</a></li>");
                                    out.println("<li><a href=\"quit.jsp\" "+blank+">退出登录</a></li>");
                                }
                            
      out.write("\n");
      out.write("                            <li><a href=\"about.jsp\" ");
      out.print(blank);
      out.write(">关于</a></li>\n");
      out.write("                            <li><a href=\"\" >旧版</a></li>\n");
      out.write("\n");
      out.write("                        </ul>\n");
      out.write("                        <form class=\"navbar-form pull-right\" method=\"post\" action=\"index.jsp\" charset=\"gb2312\">\n");
      out.write("                            <input name=\"key\"class=\"span2\" type=\"text\"/>\n");
      out.write("                            <button type=\"submit\" class=\"btn\">音乐搜索</button>\n");
      out.write("                        </form>\n");
      out.write("\n");
      out.write("                    </div><!--/.nav-collapse -->\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <h1>Nirvawolf Music</h1>\n");
      out.write("            <p>Only for music lovers</p>\n");
      out.write("\n");
      out.write("            <div class=\"row\">\n");
      out.write("                ");

                    int count = 0 ;
                    String countStr="" ;
                
      out.write("\n");
      out.write("                ");
 while ((Recordset1_hasData) && (Repeat1__numRows-- != 0)) {
      out.write("\n");
      out.write("                ");

                    countStr = String.valueOf(count);
                    request.setCharacterEncoding("gb2312");
                    String songName = Recordset1.getString("songname");
                    String orisongName = songName;
                    // orisongName = new String(orisongName.getBytes("iso-8859-1"),"utf-8");
                    String songId = Recordset1.getString("songid");
                    String singerName = Recordset1.getString("singername");
                    singerName = URLEncoder.encode(singerName, "utf-8");
                    songName = URLEncoder.encode(songName, "utf-8");
                    String visitedtime =  Recordset1.getString("visitedtime");
                    // String charset = Charset.defaultCharset().displayName();
                    // out.println("default:"+charset) ;
                    String singerId = Recordset1.getString("singerid");
                
      out.write("\n");
      out.write("\n");
      out.write("                <div class=\"metro");
      out.print(countStr);
      out.write("\">\n");
      out.write("                    <div><h5>");
      out.print(orisongName);
      out.write("</h5></div>\n");
      out.write("                    <div><h5><a href=\"SingerInfo.jsp?singerid=");
      out.print(singerId);
      out.write('"');
      out.write('>');
      out.print((((Recordset1_data = Recordset1.getObject("SingerName")) == null || Recordset1.wasNull()) ? "" : Recordset1_data));
      out.write("</a></h5></div>\n");
      out.write("                    <div><h5><a href=\"JPlayerTest.jsp?songid=");
      out.print(songId);
      out.write("&songname=");
      out.print(songName);
      out.write("&singerid=");
      out.print(singerId);
      out.write("&singername=");
      out.print(singerName);
      out.write("\" target=\"_blank\">试听<i class=\"icon-play icon-white\"></i></a>\n");
      out.write("                    <a href=\"Download.jsp?songid=");
      out.print(songId);
      out.write("\" target=\"blank\" >下载<i class=\"icon-download-alt icon-white\"></i></a></h5>\n");
      out.write("                    </div>\n");
      out.write("                   \n");
      out.write("                </div>\n");
      out.write("                \n");
      out.write("                ");

                        count++ ;
                        count %= 7 ;
                        Repeat1__index++;
                        Recordset1_hasData = Recordset1.next();
                    }
                
      out.write("\n");
      out.write("                \n");
      out.write("                \n");
      out.write("                <div class=\"row\">\n");
      out.write("                    <div class=\"span12\">\n");
      out.write("                         <div>\n");
      out.write("                    <table border=\"0\" width=\"50%\" align=\"center\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td width=\"23%\" align=\"center\">");
 if (MM_offset != 0) {
      out.write("\n");
      out.write("                                <a href=\"");
      out.print(MM_moveFirst);
      out.write("\">第一页</a>\n");
      out.write("                                ");
 } /* end MM_offset != 0 */
      out.write("\n");
      out.write("                            </td>\n");
      out.write("                            <td width=\"31%\" align=\"center\">");
 if (MM_offset != 0) {
      out.write("\n");
      out.write("                                <a href=\"");
      out.print(MM_movePrev);
      out.write("\">前一页</a>\n");
      out.write("                                ");
 } /* end MM_offset != 0 */
      out.write("\n");
      out.write("                            </td>\n");
      out.write("                            <td width=\"23%\" align=\"center\">");
 if (!MM_atTotal) {
      out.write("\n");
      out.write("                                <a href=\"");
      out.print(MM_moveNext);
      out.write("\">下一页</a>\n");
      out.write("                                ");
 } /* end !MM_atTotal */
      out.write("\n");
      out.write("                            </td>\n");
      out.write("                            <td width=\"23%\" align=\"center\">");
 if (!MM_atTotal) {
      out.write("\n");
      out.write("                                <a href=\"");
      out.print(MM_moveLast);
      out.write("\">最后一页</a>\n");
      out.write("                                ");
 } /* end !MM_atTotal */
      out.write("\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                    </table>\n");
      out.write("                </div>\n");
      out.write("                        \n");
      out.write("                    </div>\n");
      out.write("                    \n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </div><!--end of row-->\n");
      out.write("\n");
      out.write("            <div class=\"footer\">\n");
      out.write("                <p>&copy; nightwolf-chen 2012</p>\n");
      out.write("            </div>\n");
      out.write("        </div> <!-- /container -->\n");
      out.write("\n");
      out.write("        <!-- Le javascript\n");
      out.write("        ================================================== -->\n");
      out.write("        <!-- Placed at the end of the document so the pages load faster -->\n");
      out.write("        <script src=\"../assets/js/jquery.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-transition.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-alert.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-modal.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-dropdown.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-scrollspy.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-tab.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-tooltip.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-popover.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-button.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-collapse.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-carousel.js\"></script>\n");
      out.write("        <script src=\"../assets/js/bootstrap-typeahead.js\"></script>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");

    Recordset1.close();
    StatementRecordset1.close();
    ConnRecordset1.close();

      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
