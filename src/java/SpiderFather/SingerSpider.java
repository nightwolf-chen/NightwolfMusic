/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpiderFather;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import spiderting.Code;
import spiderting.MyJDBC;

/**
 *
 * @author nightwolf
 */
public class SingerSpider {

    String fathUrl = "http://music.baidu.com/artist";
    String decode = "utf-8";
    Connection con = MyJDBC.con;
    Code code = new Code();

    private void updateHelper(String singerId, String singerName, String area, String form) {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from singer where singerid='" + singerId + "'");
            if (rs.next()) {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("update singer set area=?,form=? where singerid=?");
                pstmt.setString(1, area);
                pstmt.setString(2, form);
                pstmt.setString(3, singerId);
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("insert into singer(singerid,singername,area,form) values(?,?,?,?)");
                pstmt.setString(1, singerId);
                pstmt.setString(2, singerName);
                pstmt.setString(3, area);
                pstmt.setString(4, form);
                pstmt.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingerSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int singerSpider(String area, String form) {
        int count = 0 ;
        String artistCategoryPageURL = this.fathUrl + "/" + area + "/" + form;
        System.out.println(artistCategoryPageURL);
        String artistCategoryPage = code.getCode(artistCategoryPageURL, decode);
        //System.out.println(artistCategoryPage);
        Pattern artistPattern = Pattern.compile("<a href=\"/artist/(.+?)\" title=\"(.+?)\" (class=\"has-icon\")?>(.+?)</a>");
        Matcher artistMather = artistPattern.matcher(artistCategoryPage);
        //System.out.println(artistCategoryMatcher.group(0)) ;
              
        while(artistMather.find()){
        count++ ;
        String singerId = artistMather.group(1) ;
        String singerName = artistMather.group(4) ;
        this.updateHelper(singerId, singerName, area, form);
        System.out.println("id:"+singerId+" name:"+singerName);
        }  
        
        return count ;
    }

    /**
     * 
     */
    public void singerUpdater() {
        int count = 0;

        String artistPage = code.getCode(fathUrl, decode);
        Pattern artistCategory = Pattern.compile("<a href=\"/artist/(.+?)/(.+?)\">(.+?)</a>");
        Matcher artistCategoryMatcher = artistCategory.matcher(artistPage);
        while (artistCategoryMatcher.find()) {
            String area = artistCategoryMatcher.group(1);
            String form = artistCategoryMatcher.group(2);
           count+= this.singerSpider(area, form);
        }
            count+=this.singerSpider("other", "");
           System.out.println("all:" + count);
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        new SingerSpider().singerUpdater();
    }
}
