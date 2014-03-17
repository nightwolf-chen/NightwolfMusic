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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import spiderting.Code;

/**
 *
 * @author Administrator
 */
public class SpiderTingX {

    String fathUrl = "http://ting.baidu.com";
    String decode = "utf-8";
    int indexnum = 9;
    String foder = "9";
    //String decode = "gb2312" ;
    //String[] index ;
    String[] index = {"mainland/male/", "mainland/female/", "mainland/group/", "hktw/male/", "hktw/female/", "hktw/group/", "jpkr/male/", "jpkr/female/", "jpkr/group/", "western/male/", "western/female/", "western/group/", "other/male/", "other/female/", "other/group/"};

    void Spider(String path) throws FileNotFoundException, IOException {

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
        Code code = new Code();
        /*Patterns*/
        Pattern pattern2 = Pattern.compile("<li><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></li>");
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
        //FileOutputStream singeros = new FileOutputStream(singerFile);
        //BufferedOutputStream singerbos = new BufferedOutputStream(singeros);
       // OutputStreamWriter singerosw = new OutputStreamWriter(singerbos);
        /*IOsinger*/
        //FileOutputStream os ;
        //BufferedOutputStream bos ;
        //OutputStreamWriter osw  ;
        
        String singerListUrl = this.fathUrl + "/artist/" + index[this.indexnum];
        System.out.println("singerListUrl:" + singerListUrl);

        String getcode2 = code.getCode(singerListUrl, this.decode);

        singerListPage = pattern2.matcher(getcode2);

        while (singerListPage.find()) {

            songListUrl = this.fathUrl + singerListPage.group(1) + "/song";

            singerName = singerListPage.group(3);
            //Pattern pattern0 = Pattern.compile("[\\:/*?\"<>?|]*");//剔除无关的字符
            //Matcher m0 = pattern0.matcher(singerName);
            //singerName = m0.replaceAll("");
            System.out.println("singerName:" + singerName);

          

            File singer = new File(path, singerName);
            if (!singer.exists()) {
                singer.createNewFile();
            } else {
                continue;
            }
            //os = new FileOutputStream(singer);
           // bos = new BufferedOutputStream(os);
            //osw = new OutputStreamWriter(bos);

            getcode3 = code.getCode(songListUrl, this.decode);
            
            songPage = pattern3.matcher(getcode3);
            
            expand = pattern4.matcher(getcode3);

            while (songPage.find()) {
                downPageUrl = this.fathUrl + "/song/" + songPage.group(1) + "/download/";
                //System.out.println("downPageUrl:"+downPageUrl) ;
                songName = songPage.group(2);

                getcode4 = code.getCode(downPageUrl, this.decode);
                downPage = pattern5.matcher(getcode4);

                if (!downPage.find()) {
                    continue;
                }
                downUrl = this.fathUrl + downPage.group(1);
               // osw.write("m1" + songName + "m2" + downUrl + "m3");
                //osw.flush();
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
                    downPageUrl = this.fathUrl + "/song/" + songExpandPage.group(1) + "/download/";

                    songName = songExpandPage.group(2);

                    getcode4 = code.getCode(downPageUrl, this.decode);
                    downPage = pattern5.matcher(getcode4);

                    if (!downPage.find()) {
                        System.out.println("URL_NOT_FOUND!") ;
                        continue;
                    }

                    downUrl = this.fathUrl + downPage.group(1);
                   // osw.write("m1" + songName + "m2" + downUrl + "m3");
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
        SpiderTingX spider = new SpiderTingX();
        try {
            spider.Spider("K:/testSpiderTingSuper/" + spider.foder);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpiderTingX.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SpiderTingX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
