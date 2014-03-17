package SpiderFather;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import spiderting.Code;
import spiderting.MyJDBC;

/**
 * @author Administrator
 */
public class SpiderTingDatabase {

    String fathUrl = "http://ting.baidu.com";
    String decode = "utf-8";
    int indexnum = 0;
   String foder = "0";
    String[] index = {"mainland/male/", "mainland/female/", "mainland/group/", "hktw/male/", "hktw/female/", "hktw/group/", "jpkr/male/", "jpkr/female/", "jpkr/group/", "western/male/", "western/female/", "western/group/", "other/male/", "other/female/", "other/group/"};
    //DONE: 0，1,2,3,14,13,12

    void addData(PreparedStatement ps, String SongName, String SongId, String SingerName, String SingerId, String SingerPageURL, String DownURL) throws SQLException {
        ps.setString(1, SongName);
        ps.setString(2, SongId);
        ps.setString(3, SingerName);
        ps.setString(4, SingerId);
        ps.setString(5, SingerPageURL);
        ps.setString(6, DownURL);
        ps.executeUpdate();
    }

    void refresh(Connection con, PreparedStatement ps, String sql) throws SQLException {
        con.close();
        MyJDBC.connect();
        ps = MyJDBC.con.prepareStatement(sql);
    }

    void Spider(String path) throws FileNotFoundException, IOException, SQLException {
        String sql = "insert into SpiderTing" + this.foder + "(SongName,SongId ,SingerName,SingerId,SingerPageURL,DownURL) values(?,?,?,?,?,?)";
        PreparedStatement ps = MyJDBC.con.prepareStatement(sql);
        //MyJDBC.con.close() ;
        File dirFile = new File(path);         //实例化文件
        if (!dirFile.exists()) {                       //若文件夹不存在创建新的文件夹
            dirFile.mkdirs();
        }
        File singerFile = new File(path, "singer.tmp");
        if (!singerFile.exists()) {
            singerFile.createNewFile();
        }
        /*Strings*/
        String downPageUrl = "";
        String songListUrl = "";
        String singerName = "";
        String songName = "";
        String getcode3 = "";
        String getcode4 = "";
        String downUrl = "";
        String songId = "";
        String singerId = "";
        String singerPageURL = "";
        Code code = new Code();
        /*Patterns*/
        Pattern pattern2 = Pattern.compile("<a href=\"/(.*?)/(.*?)\" title=\"(.*?)\">(.*?)</a>");
        Pattern pattern3 = Pattern.compile("<a href=\"/song/(.*?)\" title=\"(.*?)\">(.*?)</a>");
        Pattern pattern4 = Pattern.compile("/artist/(.*?)/song(.*?)order(.*?)time(.*?)start=(.*?)(.*?)size=20");
        Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        /*Matchers*/
        Matcher singerListPage;
        Matcher songPage;
        Matcher expand;
        Matcher downPage;
        Matcher songExpandPage;

        /*IO"singer.tmp"*/
        FileOutputStream singeros = new FileOutputStream(singerFile);
        BufferedOutputStream singerbos = new BufferedOutputStream(singeros);
        OutputStreamWriter singerosw = new OutputStreamWriter(singerbos);
        /*IOsinger*/
        FileOutputStream os;
        BufferedOutputStream bos;
        OutputStreamWriter osw;

        String singerListUrl = this.fathUrl + "/artist/" + index[this.indexnum];
        System.out.println("singerListUrl:" + singerListUrl);

        String getcode2 = code.getCode(singerListUrl, this.decode);

        singerListPage = pattern2.matcher(getcode2);

        while (singerListPage.find()) {

            singerName = singerListPage.group(3);
            singerId = singerListPage.group(2);
            songListUrl = this.fathUrl + "/artist/" + singerId + "/song";
            Pattern pattern0 = Pattern.compile("[\\:/*?\"<>?|]*");//剔除无关的字符
            Matcher m0 = pattern0.matcher(singerName);
            singerName = m0.replaceAll("");
            System.out.println("singerName:" + singerName);

            singerosw.write("m1" + singerName + "m2");
            singerosw.flush();

            File singer = new File(path, singerName);
            if (!singer.exists()) {
                singer.createNewFile();
            } else {
                continue;
            }
            os = new FileOutputStream(singer);
            bos = new BufferedOutputStream(os);
            osw = new OutputStreamWriter(bos);

            getcode3 = code.getCode(songListUrl, this.decode);

            songPage = pattern3.matcher(getcode3);

            expand = pattern4.matcher(getcode3);

            while (songPage.find()) {
                songId = songPage.group(1);
                downPageUrl = this.fathUrl + "/song/" + songId + "/download/";
                //System.out.println("downPageUrl:"+downPageUrl) ;
                songName = songPage.group(2);

                getcode4 = code.getCode(downPageUrl, this.decode);
                downPage = pattern5.matcher(getcode4);

                if (!downPage.find()) {
                    continue;
                }
                downUrl = this.fathUrl + downPage.group(1);
                this.addData(ps, songName, songId, singerName, singerId, this.fathUrl + "/artist/" + singerId, downUrl);
                //MyJDBC.con.close() ;
                //refresh(MyJDBC.con,ps,sql) ;
                osw.write("m1" + songName + "m2" + downUrl + "m3");
                osw.flush();
                System.out.println("songName" + songName);
                System.out.println("Find download URL:" + downUrl);
            }

            while (expand.find()) {//在当前找到的歌曲页面扩展
                String songExpandUrl = this.fathUrl + expand.group(0);//得到需要扩展的页面的url
                System.out.println("ExpandPageUrl: " + songExpandUrl);
                String getcode5 = code.getCode(songExpandUrl, this.decode);//获取页面的代码
                songExpandPage = pattern3.matcher(getcode5);

                while (songExpandPage.find()) {
                    System.out.println("In Expanding!");
                    songId = songExpandPage.group(1);
                    downPageUrl = this.fathUrl + "/song/" + songId + "/download/";
                    songName = songExpandPage.group(2);

                    getcode4 = code.getCode(downPageUrl, this.decode);
                    downPage = pattern5.matcher(getcode4);

                    if (!downPage.find()) {
                        System.out.println("URL_NOT_FOUND!");
                        continue;
                    }

                    downUrl = this.fathUrl + downPage.group(1);
                    this.addData(ps, songName, songId, singerName, singerId, this.fathUrl + "/artist/" + singerId, downUrl);
                    //MyJDBC.con.close() ;
                    //refresh(MyJDBC.con,ps,sql) ;
                    osw.write("m1" + songName + "m2" + downUrl + "m3");
                    //osw.flush();
                    System.out.println("songName" + songName);
                    System.out.println("Find download URL:" + downUrl);
                }//songExpandPage.find()
            }//expand while
        }//singerListwhile
    }//clase
  
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
    // TODO code application logic here
    SpiderTingDatabase spider = new SpiderTingDatabase();
    try {
    spider.Spider("G:/testSpiderTingSuper/"+spider.foder);
    } catch (FileNotFoundException ex) {
    Logger.getLogger(SpiderTingDatabase.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
    Logger.getLogger(SpiderTingDatabase.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
    Logger.getLogger(SpiderTingDatabase.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
}
