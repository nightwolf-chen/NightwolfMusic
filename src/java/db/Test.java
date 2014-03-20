/*
 *  Copyright 2013 Jidong Chen, Inc. All rights reserved.
 * 
 */

package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nightwolf
 * 陈纪栋
 */
public class Test {
        public static void main(String[] args){
            try { 
                
                ConnectionManager conMgr = new ConnectionManager();
                Connection con = conMgr.getConnection();
                Statement stmt = OnlineDatabaseAccessor.createStatement(con);
                ResultSet rs = OnlineDatabaseAccessor.select(stmt,"select count(songid) from song");
                
                rs.close();
                rs = null;
                stmt.close();
                stmt=null;
                con.close();
                con=null;
                
                
                System.out.println("sdfsdf");
            } catch (SQLException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
