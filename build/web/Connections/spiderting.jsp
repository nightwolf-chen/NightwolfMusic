
<%@page import="org.json.JSONObject"%>
<%
// FileName="mysql_jdbc_conn.htm"
// Type="JDBC" ""
// DesigntimeType="JDBC"
// HTTP="false"
// Catalog=""
// Schema=""
    /*
     String MM_spiderting_DRIVER = "com.mysql.jdbc.Driver";
     String MM_spiderting_USERNAME = "admin";
     String MM_spiderting_PASSWORD = "a13827970772b";
     String MM_spiderting_STRING = "jdbc:mysql://127.11.116.129:3306/music?characterEncoding=GBK";
     */

    String databaseInfo = java.lang.System.getenv("VCAP_SERVICES");
    String host = "127.0.0.1";
    String port = "3306";
    String password = "123";
    String dbName  = "nightwolf_music";
    String username  = "root";
   
    if (databaseInfo != null) {

        JSONObject jsonObject = new JSONObject(databaseInfo);
        JSONObject mysqlObj = jsonObject.getJSONArray("mysql-5.1").getJSONObject(0);
        JSONObject credentials = mysqlObj.getJSONObject("credentials");

        host = credentials.getString("hostname");
        port = String.valueOf(credentials.getInt("port"));
        dbName = credentials.getString("name");
        username = credentials.getString("username");
        password = credentials.getString("password");
    }

    String MM_spiderting_DRIVER = "com.mysql.jdbc.Driver";
    String MM_spiderting_USERNAME = username;
    String MM_spiderting_PASSWORD = password;
    String MM_spiderting_STRING = "jdbc:mysql://"+host+":"+port+"/"+dbName+"";

%>
