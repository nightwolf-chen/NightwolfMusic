package org.apache.jsp.Pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class JPlayerOnJSP_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

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
      response.setContentType("text/html;charset=GB2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"utf-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width; initial-scale=1.5; user-scalable=no;\">\n");
      out.write("        <title>jPlayer - Circle Player</title>\n");
      out.write("\n");
      out.write("        <link rel=\"stylesheet\" href=\"../css/not.the.skin.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"../circle.skin/circle.player.css\">\n");
      out.write("\n");
      out.write("        <script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"../js/jquery.transform2d.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"../js/jquery.grab.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"../js/jquery.jplayer.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"../js/mod.csstransforms.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"../js/circle.player.js\"></script>\n");
      out.write("        <!--<script type=\"text/javascript\" src=\"https://getfirebug.com/firebug-lite.js\"></script>-->\n");
      out.write("\n");
      out.write("        <script type=\"text/javascript\">\n");
      out.write("            $(document).ready(function() {\n");
      out.write("\n");
      out.write("                /*\n");
      out.write("                 * Instance CirclePlayer inside jQuery doc ready\n");
      out.write("                 *\n");
      out.write("                 * CirclePlayer(jPlayerSelector, media, options)\n");
      out.write("                 *   jPlayerSelector: String - The css selector of the jPlayer div.\n");
      out.write("                 *   media: Object - The media object used in jPlayer(\"setMedia\",media).\n");
      out.write("                 *   options: Object - The jPlayer options.\n");
      out.write("                 *\n");
      out.write("                 * Multiple instances must set the cssSelectorAncestor in the jPlayer options. Defaults to \"#cp_container_1\" in CirclePlayer.\n");
      out.write("                 */\n");
      out.write("\n");
      out.write("               var myCirclePlayer = new CirclePlayer(\"#jquery_jplayer_1\",\n");
      out.write("                        {\n");
      out.write("                            m4a: \"http://zhangmenshiting.baidu.com/data2/music/29890445/29890445.mp3?xcode=63b97eae54387d3a5063546adfa037dd2d55ed41905be34f&mid=0.32007480761468\",\n");
      out.write("                            oga: \"http://zhangmenshiting.baidu.com/data2/music/29890445/29890445.mp3?xcode=63b97eae54387d3a5063546adfa037dd2d55ed41905be34f&mid=0.32007480761468\"\n");
      out.write("                        },\n");
      out.write("                {\n");
      out.write("                    cssSelectorAncestor: \"#cp_container_1\"\n");
      out.write("                });\n");
      out.write("\n");
      out.write("                // This code creates a 2nd instance. Delete if not required.\n");
      out.write("\n");
      out.write("\n");
      out.write("            });\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        <!-- The jPlayer div must not be hidden. Keep it at the root of the body element to avoid any such problems. -->\n");
      out.write("        <div id=\"jquery_jplayer_1\" class=\"cp-jplayer\"></div>\n");
      out.write("\n");
      out.write("        <!-- This is the 2nd instance's jPlayer div -->\n");
      out.write("        <div id=\"jquery_jplayer_2\" class=\"cp-jplayer\"></div>\n");
      out.write("\n");
      out.write("        <div class=\"prototype-wrapper\"> <!-- A wrapper to emulate use in a webpage and center align -->\n");
      out.write("\n");
      out.write("\n");
      out.write("            <!-- The container for the interface can go where you want to display it. Show and hide it as you need. -->\n");
      out.write("\n");
      out.write("            <div id=\"cp_container_1\" class=\"cp-container\">\n");
      out.write("                <div class=\"cp-buffer-holder\"> <!-- .cp-gt50 only needed when buffer is > than 50% -->\n");
      out.write("                    <div class=\"cp-buffer-1\"></div>\n");
      out.write("                    <div class=\"cp-buffer-2\"></div>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"cp-progress-holder\"> <!-- .cp-gt50 only needed when progress is > than 50% -->\n");
      out.write("                    <div class=\"cp-progress-1\"></div>\n");
      out.write("                    <div class=\"cp-progress-2\"></div>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"cp-circle-control\"></div>\n");
      out.write("                <ul class=\"cp-controls\">\n");
      out.write("                    <li><a class=\"cp-play\" tabindex=\"1\">play</a></li>\n");
      out.write("                    <li><a class=\"cp-pause\" style=\"display:none;\" tabindex=\"1\">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->\n");
      out.write("                </ul>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("       \n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
      out.write("\n");
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
