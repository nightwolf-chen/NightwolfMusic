package SpiderFather;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import SpiderFather.SpiderTingDatabase;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class SpiderTingCircle {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpiderTingDatabase spider = new SpiderTingDatabase();
        for (int i = 0; i < 15; i++) {
            try {
                spider.indexnum = i;
                spider.foder = String.valueOf(spider.indexnum);

                spider.Spider("G:/testSpiderTingSuper/" + spider.foder);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SpiderTingDatabase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SpiderTingDatabase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SpiderTingDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
