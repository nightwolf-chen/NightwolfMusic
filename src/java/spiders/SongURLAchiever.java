/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import network.HttpClientAdaptor;
import network.HttpClientAdaptorFactory;

/**
 *
 * @author nightwolf
 */
public class SongURLAchiever {

    static private String fathUrl = "http://music.baidu.com";
    private static String decode = "utf-8";

    /**
     *
     * @param songId
     * @param pro
     * @return
     */
    public String getCurrentDownURL(String songId, Proxy pro) {

        if (songId == null) {
            System.out.println("songId Null!");
            return null;
        }

        HttpClientAdaptor clientAdapor = new HttpClientAdaptor();
        String str = "href=\"(.*?)\"";
        //Pattern downloadUrlPattern = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        Pattern downloadUrlPattern = Pattern.compile(str);
        Matcher downloadPageMatcher;
        String downloadPageUrl = fathUrl + "/song/" + songId + "/download";
        String downloadPageSource = clientAdapor.doGet(downloadPageUrl);
        
        System.out.println(downloadPageUrl);
        System.out.println(downloadPageSource);
        
        downloadPageMatcher = downloadUrlPattern.matcher(downloadPageSource);
        String downUrl="";
        while (downloadPageMatcher.find()) {
            downUrl = fathUrl + downloadPageMatcher.group(1);
            System.out.println("Find download URL:" + downUrl);
            
        }
        return downUrl;
    }

    /**
     *
     * @param songId
     * @return
     */
    public String getCurrentDownURL(String songId) {
        Code code = new Code();
        String str = "href=\"/data/music/file?link=(.*?)\"  id=\"(.*?)\"";
        //Pattern downloadUrlPattern = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        Pattern pattern5 = Pattern.compile(str);
        Matcher downPage;
        String downPageUrl = fathUrl + "/song/" + songId + "/download/";
        if (songId == null) {
            System.out.println("songId Null!");
        }
        //System.out.println("downloadPageUrl:"+downloadPageUrl) ;
        // songName = songPage.group(2);
        String getcode4 = code.getCode(downPageUrl, decode);
        System.out.println(getcode4);
        downPage = pattern5.matcher(getcode4);
        if (!downPage.find()) {
            return "";
        }
        String downUrl = fathUrl + downPage.group(2);
        System.out.println("Find download URL:" + downUrl);
        return downUrl;
    }

    /**
     *
     * @param songId
     * @param decode
     * @return
     */
    public String getCurrentDownURL(String songId, String decode) {
        Code code = new Code();
        String str = "<a href=\"(.*?)\" id=\"download\" hidefocus=\"true\" class=\"btn-download\">(.*?)</a><span class=\"btn-download-span\"></span>";
        //Pattern downloadUrlPattern = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">下载</a>");
        Pattern pattern5 = Pattern.compile(str);
        Matcher downPage;
        String downPageUrl = fathUrl + "/song/" + songId + "/download/";
        if (songId == null) {
            System.out.println("songId Null!");
        }
        //System.out.println("downloadPageUrl:"+downloadPageUrl) ;
        // songName = songPage.group(2);
        String getcode4 = code.getCode(downPageUrl, decode);
        downPage = pattern5.matcher(getcode4);
        if (!downPage.find()) {
            return "";
        }
        String downUrl = fathUrl + downPage.group(1);
        System.out.println("Find download URL:" + downUrl);
        return downUrl;
    }

    public String getSongDownloadURL(String songName, String artist) throws UnsupportedEncodingException{
        songName = URLEncoder.encode(songName.trim(), "utf8");
        artist = URLEncoder.encode(artist.trim(), "utf8");
        String apiUrl = "http://box.zhangmen.baidu.com/x?op=12&count=1&title="+songName+"$$"+artist+"$$$$";
      
        HttpClientAdaptor clientAdaptor = HttpClientAdaptorFactory.createDefaultHttpClientAdaptor("utf8");
        
        String xmlStr = clientAdaptor.doGet(apiUrl);
        
        System.out.println(xmlStr);
        String downloadURL = null;
        
        return downloadURL;
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String songId = "7313183";
            SongURLAchiever urlAchiever = new SongURLAchiever();
            System.out.println(urlAchiever.getSongDownloadURL("风继续吹", "张国荣"));
            System.out.println("Finished");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SongURLAchiever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
