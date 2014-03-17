/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Searcher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import spiderting.MyJDBC;

/**
 *
 * @author Administrator
 */
public class SearchTest {

    /**
     * 
     */
    public ResultSet rs;
    Statement st;

    /**
     * 
     * @param index
     * @param name
     * @return
     */
    public boolean testSQL(int index, String name) {

        String sql = "select * from SpiderTing" + String.valueOf(index) + " where SingerName like '%" + name + "%'" + " or SongName like '%" + name + "%'";
        try {

            st = MyJDBC.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet tmp = st.executeQuery(sql);
            if(tmp.next()){
                tmp.previous();
                this.rs = tmp;
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchTest.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }
    /**
     * 
     * @param request
     * @return
     */
    public boolean search(String request) {
        int index = 0;
        while (index <= 14) {
            if (testSQL(index++, request)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        SearchTest st = new SearchTest();
        int index = 0;
        boolean find = false;
        while (index <= 14 && find == false) {
            if (st.testSQL(index++, "ÄãÊÇÎÒµÄÑÛ")) {
                find = true;
                System.out.println("find!");
            } else {
                System.out.println("Nothing!");
            }
        }
    }
}
