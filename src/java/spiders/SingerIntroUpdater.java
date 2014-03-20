/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nightwolf
 */
public class SingerIntroUpdater {
    private void IntroUpdater() throws SQLException, UnsupportedEncodingException{
        Connection con = (Connection) MyJDBC.con;
        Statement singerStmt = (Statement) con.createStatement();
        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("update singer set intro=? where singerid=?");
        
        ResultSet singerSet = singerStmt.executeQuery("select singerid from singer order by visitedcount");
        
       while(singerSet.next()){
            String singerId = singerSet.getString("singerid") ;
            System.out.println("id:"+singerId) ;
            SpiderTingIntroduction intro = new SpiderTingIntroduction(singerId);

            String introString = intro.getIntroduction();
                        System.out.println(introString) ;
            pstmt.setString(1, introString);
            pstmt.setString(2, singerId);
            if(!introString.equals(""))
            pstmt.executeUpdate();
        }
    }
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args){
        try {
            new SingerIntroUpdater().IntroUpdater();
        } catch (SQLException ex) {
            Logger.getLogger(SingerIntroUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SingerIntroUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
