/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import MusicDownload.Img;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class SpiderTingPho {

    String singerURL;
    String decode = "utf-8";
    String path;
    String singerName ;
    /**
     * 
     * @param singerId
     * @param singerName
     */
    public SpiderTingPho(String singerId,String singerName) {
        this.singerURL = "http://ting.baidu.com/artist/" + singerId  ;
        this.singerName = singerName ;
    }

    /**
     * 
     * @param path
     * @return
     */
    public String getPhoto(String path) {
        String imgURL = "";
        Code code = new Code();
        String getCode = code.getCode(this.singerURL, this.decode);
        //System.out.println(getCode) ;
        Pattern pattern = Pattern.compile("http://hiphotos.baidu.com/ting/pic/item/(.*?).jpg") ;
        Matcher lrcMatcher = pattern.matcher(getCode) ;

        while(lrcMatcher.find()){
        imgURL = lrcMatcher.group(0);
        Img.downLoad(path, singerName, imgURL);
        }
        //System.out.print(intro) ;
        return path + singerName + ".jpg";
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpiderTingPho spider = new SpiderTingPho("1202","Îé°Û");
        spider.getPhoto("D:/Niglwof_music/") ;
    }
}
