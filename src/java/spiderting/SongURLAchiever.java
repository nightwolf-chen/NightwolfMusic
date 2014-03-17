/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderting;

import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String getCurrentDownURL(String songId,Proxy pro) {
        Code code = new Code();
        String str = "<a href=\"(.*?)\" id=\"download\" hidefocus=\"true\" class=\"btn-download\">(.*?)</a><span class=\"btn-download-span\"></span>" ;
        //Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">обть</a>");
        Pattern pattern5 = Pattern.compile(str);
        Matcher downPage;
        String downPageUrl = fathUrl + "/song/" + songId + "/download/";
        if(songId==null){
            System.out.println("songId Null!") ;
        }
        //System.out.println("downPageUrl:"+downPageUrl) ;
        // songName = songPage.group(2);
        String getcode4 = code.getCode(downPageUrl, pro);
        downPage = pattern5.matcher(getcode4);
        if (!downPage.find()) {
            return "" ;
        }
        String downUrl = fathUrl + downPage.group(1);
        System.out.println("Find download URL:" + downUrl);
        return downUrl ;
    }
     /**
      * 
      * @param songId
      * @return
      */
     public String getCurrentDownURL(String songId) {
        Code code = new Code();
        String str = "<a href=\"(.*?)\" id=\"download\" hidefocus=\"true\" class=\"btn-download\">(.*?)</a><span class=\"btn-download-span\"></span>" ;
        //Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">обть</a>");
        Pattern pattern5 = Pattern.compile(str);
        Matcher downPage;
        String downPageUrl = fathUrl + "/song/" + songId + "/download/";
        if(songId==null){
            System.out.println("songId Null!") ;
        }
        //System.out.println("downPageUrl:"+downPageUrl) ;
        // songName = songPage.group(2);
        String getcode4 = code.getCode(downPageUrl, decode);
        downPage = pattern5.matcher(getcode4);
        if (!downPage.find()) {
            return "" ;
        }
        String downUrl = fathUrl + downPage.group(1);
        System.out.println("Find download URL:" + downUrl);
        return downUrl ;
    }
    /**
     * 
     * @param songId
     * @param decode
     * @return
     */
    public String getCurrentDownURL(String songId,String decode) {
        Code code = new Code();
        String str = "<a href=\"(.*?)\" id=\"download\" hidefocus=\"true\" class=\"btn-download\">(.*?)</a><span class=\"btn-download-span\"></span>" ;
        //Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">обть</a>");
        Pattern pattern5 = Pattern.compile(str);
        Matcher downPage;
        String downPageUrl = fathUrl + "/song/" + songId + "/download/";
        if(songId==null){
            System.out.println("songId Null!") ;
        }
        //System.out.println("downPageUrl:"+downPageUrl) ;
        // songName = songPage.group(2);
        String getcode4 = code.getCode(downPageUrl, decode);
        downPage = pattern5.matcher(getcode4);
        if (!downPage.find()) {
            return "" ;
        }
        String downUrl = fathUrl + downPage.group(1);
        System.out.println("Find download URL:" + downUrl);
        return downUrl ;
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args){
        
        System.out.println("Finished") ;
    }
}
