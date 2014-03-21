/*
 * Copyright 2014 bruce.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package network;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import db.ConnectionManager;
import db.OnlineDatabaseAccessor;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.json.JSONArray;
import org.json.JSONException;
import util.TimeTool;


/**
 *
 * @author bruce
 */
public class HttpProxyGetter {
    
    private final int UpdateTimeGap = 1000 * 60 * 5;
    
    public HttpHost getARandomProxy(){
        
        List<HttpHost> proxies = this.getAvailableProxies();

        int randomIndex = (int) ((Math.random() * 1000) % proxies.size());
        HttpHost proxy  = proxies.get(randomIndex);
        
        return proxy;
    
    }
    public List<HttpHost> getAvailableProxies() {
        try {

            this.checkProxyUpdate();
            List<HttpHost> proxies = new ArrayList<HttpHost>();

            Connection dbCon = new ConnectionManager().getConnection();
            Statement stmt = OnlineDatabaseAccessor.createStatement(dbCon);
            ResultSet rs = OnlineDatabaseAccessor.select(stmt, "select * from proxies order by updatetime desc");

            while (rs.next()) {
                String host = rs.getString("host");
                String port = rs.getString("port");
                proxies.add(new HttpHost(host, Integer.valueOf(port)));
            }

            rs.close();
            stmt.close();
            dbCon.close();
            rs = null;
            stmt = null;
            dbCon = null;

            return proxies;
        } catch (SQLException ex) {
            Logger.getLogger(HttpProxyGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void checkProxyUpdate() {

        if (!this.shouldCheckUpdate()) {
            return;
        }

        System.out.println("Update proxy list...");
        
        try {

            String currentTimeStr = new TimeTool().getCurrentTime();
            Connection con = new ConnectionManager().getConnection();
            Statement stmt = OnlineDatabaseAccessor.createStatement(con);
            HttpClientAdaptor httpClientAdaptor = new HttpClientAdaptor();

            String jsonContent = httpClientAdaptor.doGet("http://letushide.com/export/json/http,all,cn/");
            JSONArray jsonArray = new JSONArray(jsonContent);

            for (int i = 0; i < jsonArray.length(); i++) {

                String host = jsonArray.getJSONObject(i).getString("host");
                int port = jsonArray.getJSONObject(i).getInt("port");
                String portStr = String.valueOf(port);

                ResultSet rs = OnlineDatabaseAccessor.select(stmt, "select * from proxies where host='" + host + "' and port='" + portStr + "'");
                if (rs.next()) {
                    OnlineDatabaseAccessor.update(stmt, "update proxies set updatetime='" + currentTimeStr + "' where host='" + host + "' and port ='" + portStr + "'");
                } else {
                    OnlineDatabaseAccessor.insert(stmt, "insert into proxies(host,port,updatetime) values('" + host + "','" + portStr + "','" + currentTimeStr + "')");
                }
                rs.close();
            }

            OnlineDatabaseAccessor.delete(stmt, "delete from proxies where updatetime<'" + currentTimeStr + "'");

            stmt.close();
            con.close();
            stmt = null;
            con = null;

            try {
                httpClientAdaptor.getHttpclient().close();
            } catch (IOException ex) {
                Logger.getLogger(HttpProxyGetter.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(HttpProxyGetter.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(HttpProxyGetter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean shouldCheckUpdate() {

        try {
            
            Connection dbCon = new ConnectionManager().getConnection();
            Statement stmt = OnlineDatabaseAccessor.createStatement(dbCon);
            ResultSet rs = OnlineDatabaseAccessor.select(stmt, "select * from proxies order by updatetime");

            if (rs.next()) {
                TimeTool tTool = new TimeTool();
                String lastUpdateTime = rs.getString("updatetime");
                String currentTime = tTool.getCurrentTime();
                long timeGap = tTool.calculateDiscance(lastUpdateTime, currentTime);
                if(timeGap >= 0 && timeGap < this.UpdateTimeGap){
                    return false;
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(HttpProxyGetter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
}
