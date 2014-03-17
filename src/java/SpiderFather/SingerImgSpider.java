/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SpiderFather;

import MusicDownload.Img;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spiderting.MyJDBC;
import spiderting.SpiderTingIntroduction;

/**
 *
 * @author nightwolf
 */
public class SingerImgSpider {

    String folderPath = "C:\\Users\\nightwolf\\Documents\\NetBeansProjects\\NightwolfMusic\\build\\web\\img\\";
    String fathUrl = "http://music.baidu.com";
    String decode = "utf-8";
    Connection con = (Connection) MyJDBC.con;

    private ResultSet getSingerSet() {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select singerid,singername from singer where hasphoto=0");
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private ResultSet getTopSingerSet() {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("select singerid from singer where singerid in(select singerid from topsinger) and hasphoto=0");
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SongSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private int getPhoto() {
        try {
            int count = 0;
            Img img = new Img();

            ResultSet singerSet = this.getTopSingerSet();
            while (singerSet.next()) {
                String singerId = singerSet.getString("singerid");
                //String singerName = singerSet.getString("singername");
                SpiderTingIntroduction intro = new SpiderTingIntroduction(singerId);
                String photoURL = intro.getPhotoURL();
                boolean isDownload = img.downLoad(folderPath, singerId, photoURL);
                if(isDownload){
                    PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("update singer set hasphoto = 1 where singerid=?");
                    pstmt.setString(1, singerId);
                    pstmt.executeUpdate();
                    count++ ;
                }
                //System.out.println("name:"+singerName) ;
            }
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(SingerImgSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args){
        int count = new SingerImgSpider().getPhoto();
        System.out.println("all:"+count) ;
    }
}
