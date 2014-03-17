package OldStyle;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import spiderting.Code;

/**
 *
 * @author Administrator
 */
public class SpiderTingPart {

    String fathUrl = "http://ting.baidu.com";
    String decode = "utf-8";
    int indexnum = 5 ;
    String foder = "5" ;
    //String decode = "gb2312" ;
    //String[] index ;
    String[] index = {"mainland/male/","mainland/female/","mainland/group/","hktw/male/","hktw/female/","hktw/group/","jpkr/male/","jpkr/female/","jpkr/group/","western/male/","western/female/","western/group/","other/male/","other/female/","other/group/" } ;
    void Spider(String path) throws FileNotFoundException, IOException {
        //System.out.println("in spider!") ;
       
        File dirFile = new File(path);         //实例化文件
        if (!dirFile.exists()) {                       //若文件夹不存在创建新的文件夹
            dirFile.mkdirs();
        }

        System.out.println("fathUrl:" + fathUrl);
        File singerFile = new File(path, "singer.tmp");
        if (!singerFile.exists()) {
            singerFile.createNewFile();
        }
  
        FileOutputStream singeros = new FileOutputStream(singerFile);
        BufferedOutputStream singerbos = new BufferedOutputStream(singeros);
        OutputStreamWriter singerosw = new OutputStreamWriter(singerbos);
        //String index = "" ;
            String singerListUrl = this.fathUrl + "/artist/" + index[this.indexnum]  ;
            System.out.println("singerListUrl:" + singerListUrl);

            Code code2 = new Code();
            String getcode2 = code2.getCode(singerListUrl, this.decode);
            Pattern pattern2 = Pattern.compile("<li><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></li>");
            Matcher singerListPage = pattern2.matcher(getcode2);

            while (singerListPage.find()) {
                String songListUrl = this.fathUrl + singerListPage.group(1) + "/song";
                //System.out.println("songListUrl:"+songListUrl) ;

                String singerName = singerListPage.group(3);

                System.out.println("singerName:" + singerName);

                System.out.println("write:"+singerName+" intoFile") ;
                singerosw.write("m1" + singerName + "m2");
                singerosw.flush();

                File singer = new File(path, singerName);
                if (!singer.exists()) {
                    singer.createNewFile();
                }
                else
                    continue ;
                FileOutputStream os = new FileOutputStream(singer);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                OutputStreamWriter osw = new OutputStreamWriter(bos);

                Code code3 = new Code();
                String getcode3 = code3.getCode(songListUrl, this.decode);
                Pattern pattern3 = Pattern.compile("<a href=\"/song/(.*?)\" title=\"(.*?)\">(.*?)</a>");

                Matcher songPage = pattern3.matcher(getcode3);

                Pattern pattern4 = Pattern.compile("/artist/(.*?)/song(.*?)order(.*?)time(.*?)start=(.*?)(.*?)size=20");

                Matcher expand = pattern4.matcher(getcode3);

                Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
                
                while (songPage.find()) {
                String downPageUrl = this.fathUrl + "/song/" + songPage.group(1) + "/download/";
                //System.out.println("downPageUrl:"+downPageUrl) ;
                String songName = songPage.group(2);
                
                Code code4 = new Code();
                String getcode4 = code4.getCode(downPageUrl, this.decode);
                Matcher downPage = pattern5.matcher(getcode4);
                
                if (!downPage.find()) {
                continue;
                }
                String downUrl =this.fathUrl + downPage.group(1);
                osw.write("m1"+songName+"m2"+downUrl+"m3") ;
                osw.flush() ;
                System.out.println("songName"+songName) ;
                System.out.println("Find download URL:"+downUrl) ;
                }
                
                while (expand.find()) {//在当前找到的歌曲页面扩展
                    String songExpandUrl = this.fathUrl + expand.group(0);//得到需要扩展的页面的url
                    System.out.println("ExpandPageUrl: " + songExpandUrl);
                    Code code5 = new Code();
                    String getcode5 = code5.getCode(songExpandUrl, this.decode);//获取页面的代码
                    Matcher songExpandPage = pattern3.matcher(getcode5);

                    while (songExpandPage.find()) {
                        System.out.println("In Expanding!");
                        String downPageUrl = this.fathUrl + "/song/" + songExpandPage.group(1) + "/download/";

                        String songName = songExpandPage.group(2);

                        Code code4 = new Code();
                        String getcode4 = code4.getCode(downPageUrl, this.decode);
                        Matcher downPage = pattern5.matcher(getcode4);

                        if (!downPage.find()) {
                            continue;
                        }

                        String downUrl =this.fathUrl + downPage.group(1);
                        osw.write("m1" + songName + "m2" + downUrl + "m3");
                        osw.flush();
                        System.out.println("songName" + songName);
                        System.out.println("Find download URL:" + downUrl);
                    }//songExpandPage.find()
                }//expand while
            }//singerListwhile
    }//clase

    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        SpiderTingPart spider = new SpiderTingPart();
        spider.Spider("K:/testSpiderTingSuper/"+spider.foder);
    }
}
