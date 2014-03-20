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
 * @author nightwolf 陈纪栋 这个类使用ResultSet来操作数据库，使用ResultSet时保持数据库连接
 * 需要手动管理Connection Statement 和 ResultSet的关闭。
 */
public class OnlineDatabaseAccessor {

    public static Statement createStatement(Connection con) {
        Statement stmt = null;
        try {
            stmt = (Statement) con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }

    public static ResultSet select(Statement stmt, String sql) {

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public static boolean insert(Statement stmt, String sql) {
        try {
            return stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static int update(Statement stmt, String sql) {

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public static int delete(Statement stmt, String sql) {
        return update(stmt, sql);
    }

    public boolean insert(String sql) {
        ConnectionManager conMgr = new ConnectionManager();
        Connection con = conMgr.getConnection();
        Statement stmt = OnlineDatabaseAccessor.createStatement(con);
        boolean toReturn = OnlineDatabaseAccessor.insert(stmt, sql);

        try {
            con.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    public int update(String sql) {
        ConnectionManager conMgr = new ConnectionManager();
        Connection con = conMgr.getConnection();
        Statement stmt = OnlineDatabaseAccessor.createStatement(con);
        int toReturn = OnlineDatabaseAccessor.update(stmt, sql);

        try {
            con.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return toReturn;
    }

    public int delete(String sql) {
        ConnectionManager conMgr = new ConnectionManager();
        Connection con = conMgr.getConnection();
        Statement stmt = OnlineDatabaseAccessor.createStatement(con);
        int toReturn = OnlineDatabaseAccessor.delete(stmt, sql);
  
        try {
            con.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return toReturn;
    }
}
