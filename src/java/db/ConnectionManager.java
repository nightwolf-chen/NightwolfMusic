/*
 *  Copyright 2013 Jidong Chen, Inc. All rights reserved.
 * 
 */
package db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nightwolf 陈纪栋
 */
public class ConnectionManager {

    private final String driver = "com.mysql.jdbc.Driver";
    private String DatabaseName = "lib_data";
    private final String Encode = "utf8";
    private String ServerHost = "localhost";
    private String port = "3306";
    private String userName = "root";
    private String passWord = "123";

    public ConnectionManager() {
        //VCAP_SERVICES
        String databaseInfo = java.lang.System.getenv("VCAP_SERVICES");

        if (databaseInfo != null) {
            try {
                JSONObject jsonObject = new JSONObject(databaseInfo);
                JSONObject mysqlObj = jsonObject.getJSONArray("mysql-5.1").getJSONObject(0);
                JSONObject credentials = mysqlObj.getJSONObject("credentials");

                this.ServerHost = credentials.getString("hostname");
                this.port = String.valueOf(credentials.getInt("port"));
                this.DatabaseName = credentials.getString("name");
                this.userName = credentials.getString("username");
                this.passWord = credentials.getString("password");

            } catch (JSONException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Connection getConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(this.getConnectionStr(), this.userName, this.passWord);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public String getConnectionStr() {
        return "jdbc:mysql://" + ServerHost + ":" + this.port + "/" + DatabaseName + "?CharacterEncoding=" + Encode;
    }

    public String getDatabaseName() {
        return DatabaseName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
    
    
}
