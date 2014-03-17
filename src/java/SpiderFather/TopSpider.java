/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpiderFather;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class TopSpider {

    String decode = "utf-8";
    Connection con = (Connection) MyJDBC.con;

    /**
     * 
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public int topSong500() throws SQLException, IOException {
        Code code = new Code();
        int rank = 1;
        String pageURL = "http://music.baidu.com/top/dayhot" ;
        String pageContent = code.getCode(pageURL, this.decode);
        System.out.println(pageContent);
        //System.out.println(pageContent) ;
        Pattern songPattern = Pattern.compile("\"/song/(.+?)\"");
        Matcher songMatcher = songPattern.matcher(pageContent);
        while (songMatcher.find()) {
            String songId = songMatcher.group(1);
            System.out.println("songId" + songId);
            String rankString = String.valueOf(rank);
            String isNew = "0";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from topsong where songid='" + songId + "'");
            if (rs.next()) {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("update topsong set rank=? where songid=?");
                pstmt.setString(1, rankString);
                pstmt.setString(2, songId);
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("insert into topsong(songid,rank,isnew) value(?,?,?)");
                pstmt.setString(1, songId);
                pstmt.setString(2, rankString);
                pstmt.setString(3, isNew);
                pstmt.execute();
            }
            rank++ ;
        }
        
        return rank;
    }

    /**
     * 
     * @return
     * @throws SQLException
     */
    public int newSong100() throws SQLException {
        Code code = new Code();
        int rank = 1;
        String pageURL = "http://music.baidu.com/top/new";
        String pageContent = code.getCode(pageURL, this.decode);
        Pattern songPattern = Pattern.compile("\"/song/(.+?)\"");
        Matcher songMatcher = songPattern.matcher(pageContent);
        while (songMatcher.find()) {
            String songId = songMatcher.group(1);
            System.out.println("songId" + songId);
            String rankString = String.valueOf(rank);
            String isNew = "1";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from topsong where songid='" + songId + "'");
            if (rs.next()) {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("update topsong set rank=? where songid=?");
                pstmt.setString(1, rankString);
                pstmt.setString(2, songId);
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("insert into topsong(songid,rank,isnew) value(?,?,?)");
                pstmt.setString(1, songId);
                pstmt.setString(2, rankString);
                pstmt.setString(3, isNew);
                pstmt.execute();
            }
            rank++ ;
        }
         
        return rank;

    }

    /**
     * 
     * @throws SQLException
     */
    public void topSinger200() throws SQLException {
        //<div class="artist-name"><a href="/artist/1157" title="Íô·å">Íô·å</a></div>
        int rank = 1;
        String pageURL = "http://music.baidu.com/top/artist" ;
        Code code = new Code() ;
        String pageContent = code.getCode(pageURL, decode);
        Pattern singerPattern = Pattern.compile("<a href=\"/artist/(.+?)\">(.+?)</a>");
        Matcher singerMatcher = singerPattern.matcher(pageContent);
        while(singerMatcher.find()){
            String singerId = singerMatcher.group(1);
            if(singerId.contains("title")){
                singerId = singerId.substring(0,  singerId.indexOf("title")-2);
                System.out.println(singerId) ;
            }
            String singerName = singerMatcher.group(2);
            System.out.println("singerName"+singerName) ;
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from topsinger where singerid='" + singerId + "'");
            if (rs.next()) {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("update topsinger set rank=? where singerid=?");
                pstmt.setString(1, String.valueOf(rank));
                pstmt.setString(2, singerId);
                pstmt.executeUpdate();
            } else {
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("insert into topsinger(singerid,singername,rank) values(?,?,?)");
                pstmt.setString(1, singerId);
                pstmt.setString(2, singerName);
                pstmt.setString(3, String.valueOf(rank));
                pstmt.execute();
            }
            rank++ ;
        }
    }   
    private void doBatch() throws SQLException, IOException{
        this.newSong100();
        this.topSinger200();
        this.topSong500();
    }

    /**
     * 
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        try {
            TopSpider topSpider = new TopSpider();
            topSpider.topSong500();
            System.out.println("finished");
        } catch (IOException ex) {
            Logger.getLogger(TopSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
