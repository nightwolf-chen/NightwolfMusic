/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpiderFather;

import ChineseToPinyin.Pinyin;
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
public class SongSpider {

    String fathUrl = "http://music.baidu.com";
    String decode = "utf-8";
    Connection con = MyJDBC.con;

    private ResultSet getSingerSet(String area, String form) {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select singerid,singername from singer where area='" + area + "' and form='" + form + "'");
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private ResultSet getSingerSet(String area) {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select singerid,singername from singer where area='" + area + "'");
            Statement stmt2 = (Statement) con.createStatement();
            ResultSet rs1 = stmt2.executeQuery("select * from updateinfo where updateid=1");
            String singerindex = null;
            if (rs1.next()) {
                singerindex = rs1.getString("singerindex");

            }
            if (singerindex != null) {
                System.out.println("singerindex:" + singerindex);
                while (rs.next() && !rs.getString("singerid").equals(singerindex)) {
                }
            }
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean updateHelper(String songId, String songName, String singerId, String singerName) {
        try {
            String songNamePinyin = songName;
            String singerNamePinyin = singerName;
            Statement stmt = (Statement) con.createStatement();
            Statement stmt2 = (Statement) con.createStatement();
            stmt2.executeUpdate("update updateinfo set singerindex='" + singerId + "' where updateid=1");
            ResultSet rs = stmt.executeQuery("select * from song where songid='" + songId + "'");
            if (!rs.next()) {
                if (songName.getBytes().length > songName.length()) {
                    songNamePinyin = Pinyin.getPinyin(songName);
                }
                if (singerName.getBytes().length > singerName.length()) {
                    singerNamePinyin = Pinyin.getPinyin(singerName);
                }

                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("insert into song(songid,songname,sonpinyin,singerid,singername,sinpinyin) values(?,?,?,?,?,?)");
                pstmt.setString(1, songId);
                pstmt.setString(2, songName);
                pstmt.setString(3, songNamePinyin);
                pstmt.setString(4, singerId);
                pstmt.setString(5, singerName);
                pstmt.setString(6, singerNamePinyin);
                pstmt.execute();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void getSingerSongBySingerId(String singerId, String singerName) {
        String downPageUrl = "";
        String songListUrl = "";

        String songName = "";
        String getcode3 = "";
        String getcode4 = "";
        String downUrl = "";
        String songId;

        Code code = new Code();
        /*Patterns*/
        Pattern pattern2 = Pattern.compile("<li><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></li>");
        Pattern pattern3 = Pattern.compile("<a href=\"/song/(.*?)\" title=\"(.*?)\">(.*?)</a>");
        Pattern pattern4 = Pattern.compile("/artist/(.*?)/song(.*?)order(.*?)time(.*?)start=(.*?)(.*?)size=20");
        Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        /*Matchers*/
        Matcher singerListPage;
        Matcher songPage;
        Matcher expand;
        Matcher downPage;
        Matcher songExpandPage;


        songListUrl = this.fathUrl + "/artist" + "/" + singerId;
        getcode3 = code.getCode(songListUrl, this.decode);
        songPage = pattern3.matcher(getcode3);
        expand = pattern4.matcher(getcode3);

        while (songPage.find()) {
            songId = songPage.group(1);
            songName = songPage.group(2);
            boolean isInsert = this.updateHelper(songId, songName, singerId, singerName);
            if (isInsert) {
                System.out.println("inserted-->songId:" + songId + " songName:" + songName + " singer:" + singerName);
            }

        }


        while (expand.find()) {//在当前找到的歌曲页面扩展
            String songExpandUrl = this.fathUrl + expand.group(0);//得到需要扩展的页面的url
            String getcode5 = code.getCode(songExpandUrl, this.decode);//获取页面的代码
            songExpandPage = pattern3.matcher(getcode5);
            while (songExpandPage.find()) {
                songId = songExpandPage.group(1);
                songName = songExpandPage.group(2);
                boolean isInsert = this.updateHelper(songId, songName, singerId, singerName);
                if (isInsert) {
                    System.out.println("inserted-->songId:" + songId + " songName:" + songName + " singer:" + singerName);
                }
            }//songExpandPage.find()

        }//expand while

    }

    private void spider(String area, String form) throws SQLException {

        /*Strings*/
        String downPageUrl = "";
        String songListUrl = "";
        String singerName = "";
        String songName = "";
        String getcode3 = "";
        String getcode4 = "";
        String downUrl = "";
        String songId;
        String singerId;
        Code code = new Code();
        /*Patterns*/
        Pattern pattern2 = Pattern.compile("<li><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></li>");
        Pattern pattern3 = Pattern.compile("<a href=\"/song/(.*?)\" title=\"(.*?)\">(.*?)</a>");
        Pattern pattern4 = Pattern.compile("/artist/(.*?)/song(.*?)order(.*?)time(.*?)start=(.*?)(.*?)size=20");
        Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        /*Matchers*/
        Matcher singerListPage;
        Matcher songPage;
        Matcher expand;
        Matcher downPage;
        Matcher songExpandPage;


        ResultSet singerSet = this.getSingerSet(area, form);
        if (singerSet == null) {
            return;
        }
        while (singerSet.next()) {
            singerId = singerSet.getString("singerid");
            singerName = singerSet.getString("singername");


            songListUrl = this.fathUrl + "/artist" + "/" + singerId;
            getcode3 = code.getCode(songListUrl, this.decode);
            songPage = pattern3.matcher(getcode3);
            expand = pattern4.matcher(getcode3);

            while (songPage.find()) {
                songId = songPage.group(1);
                songName = songPage.group(2);
                boolean isInsert = this.updateHelper(songId, songName, singerId, singerName);
                if (isInsert) {
                    System.out.println("inserted-->songId:" + songId + " songName:" + songName + " singer:" + singerName);
                }

            }


            while (expand.find()) {//在当前找到的歌曲页面扩展
                String songExpandUrl = this.fathUrl + expand.group(0);//得到需要扩展的页面的url
                String getcode5 = code.getCode(songExpandUrl, this.decode);//获取页面的代码
                songExpandPage = pattern3.matcher(getcode5);
                while (songExpandPage.find()) {
                    songId = songExpandPage.group(1);
                    songName = songExpandPage.group(2);
                    boolean isInsert = this.updateHelper(songId, songName, singerId, singerName);
                    if (isInsert) {
                        System.out.println("inserted-->songId:" + songId + " songName:" + songName + " singer:" + singerName);
                    }
                }//songExpandPage.find()

            }//expand while

        }//singerListwhile

    }

    private void spider(String area) throws SQLException {

        /*Strings*/
        String downPageUrl = "";
        String songListUrl = "";
        String singerName = "";
        String songName = "";
        String getcode3 = "";
        String getcode4 = "";
        String downUrl = "";
        String songId;
        String singerId;
        Code code = new Code();
        /*Patterns*/
        Pattern pattern2 = Pattern.compile("<li><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></li>");
        Pattern pattern3 = Pattern.compile("<a href=\"/song/(.*?)\" title=\"(.*?)\">(.*?)</a>");
        Pattern pattern4 = Pattern.compile("/artist/(.*?)/song(.*?)order(.*?)time(.*?)start=(.*?)(.*?)size=20");
        Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        /*Matchers*/
        Matcher singerListPage;
        Matcher songPage;
        Matcher expand;
        Matcher downPage;
        Matcher songExpandPage;


        ResultSet singerSet = this.getSingerSet(area);
        if (singerSet == null) {
            return;
        }
        while (singerSet.next()) {
            singerId = singerSet.getString("singerid");
            singerName = singerSet.getString("singername");


            songListUrl = this.fathUrl + "/artist" + "/" + singerId;
            getcode3 = code.getCode(songListUrl, this.decode);
            songPage = pattern3.matcher(getcode3);
            expand = pattern4.matcher(getcode3);

            while (songPage.find()) {
                songId = songPage.group(1);
                songName = songPage.group(2);
                boolean isInsert = this.updateHelper(songId, songName, singerId, singerName);
                if (isInsert) {
                    System.out.println("inserted-->songId:" + songId + " songName:" + songName + " singer:" + singerName);
                }

            }


            while (expand.find()) {//在当前找到的歌曲页面扩展
                String songExpandUrl = this.fathUrl + expand.group(0);//得到需要扩展的页面的url
                String getcode5 = code.getCode(songExpandUrl, this.decode);//获取页面的代码
                songExpandPage = pattern3.matcher(getcode5);
                while (songExpandPage.find()) {
                    songId = songExpandPage.group(1);
                    songName = songExpandPage.group(2);
                    boolean isInsert = this.updateHelper(songId, songName, singerId, singerName);
                    if (isInsert) {
                        System.out.println("inserted-->songId:" + songId + " songName:" + songName + " singer:" + singerName);
                    }
                }//songExpandPage.find()

            }//expand while

        }//singerListwhile
    }

    private ResultSet getTopSingerSet() {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select singerid,singername from topsinger order by rank");
            Statement stmt2 = (Statement) con.createStatement();
            ResultSet rs1 = stmt2.executeQuery("select * from updateinfo where updateid=1");
            String singerindex = null;
            if (rs1.next()) {
                singerindex = rs1.getString("singerindex");

            }
            if (singerindex != null) {
                System.out.println("singerindex:" + singerindex);
                while (rs.next() && !rs.getString("singerid").equals(singerindex)) {
                }
            }
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void topSingerSpider() {
        try {
            ResultSet topSingerSet = this.getTopSingerSet();
            while(topSingerSet.next()){
                String singerId  = topSingerSet.getString("singerid");
                String singerName = topSingerSet.getString("singername");
                this.getSingerSongBySingerId(singerId, singerName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        /*
         *  western/female
         */

            new SongSpider().topSingerSpider();
       
    }
}
