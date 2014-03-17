/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MyJDBC {

    //String diver = "sun.jdbc.odbc.JdbcOdbcDriver" ;
    static String driver = "com.mysql.jdbc.Driver" ;
    //String strurl="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=books.mdb"; 
    //static final String URL = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=G:/SpiderTing.mdb" ;
    static final String URL = "jdbc:mysql://127.0.0.1:3306/music?characterEncoding=GBK" ;
    //static final String URL = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=192.168.204.113" ;
    //public static final String URL = "jdbc:odbc:SpiderTing" ;
    //public static final String userName = "nightwolf" ;
    /**
     * 
     */
    public static final String userName = "admin" ;
    /**
     * 
     */
    public static final String passWord = "password" ;
    /**
     * 
     */
    public static Connection con ;
    static {
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(URL,userName,passWord);
            } catch (SQLException ex) {
                Logger.getLogger(MyJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    /**
     * 
     */
    public static void connect(){
        try {
            con = DriverManager.getConnection(URL,userName,passWord);
        } catch (SQLException ex) {
            Logger.getLogger(MyJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
