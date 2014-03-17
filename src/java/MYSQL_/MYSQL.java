/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MYSQL_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MYSQL {
   static{
String driver = "com.mysql.jdbc.Driver";

// URL指向要访问的数据库名scutcs

String url = "jdbc:mysql://127.0.0.1:3306/spiderting";

// MySQL配置时的用户名

String user = "root";

// Java连接MySQL配置时的密码

String password = "466202783";
        try {
            // 加载驱动程序

            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MYSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // 连续数据库

            Connection conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(MYSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   /**
    * 
    * @param args
    */
   public static void main(String[] args) {
       
    }
}
