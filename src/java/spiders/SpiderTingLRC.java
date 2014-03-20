/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * nightwolf
 */
/**
 * 
 * @author nightwolf
 */
public class SpiderTingLRC {

    String songURL;
    String decode = "utf-8";

    /**
     * 
     * @param code
     */
    public SpiderTingLRC(String code) {
        this.songURL = "http://music.baidu.com/song/" + code;
    }

   /**
    * 
    * @return
    */
   public String getLrc() {
        String lrc = "";
        Code code = new Code();
        String getCode = code.getCode(this.songURL, this.decode);
        //System.out.println(getCode) ;
        Pattern pattern = Pattern.compile("lyric_clip = \'(.*?)\'");
        Matcher lrcMatcher = pattern.matcher(getCode);

        lrcMatcher.find();
        lrc = lrcMatcher.group(1);

        Pattern pattern0 = Pattern.compile("\\\\n");//ÌÞ³ýÎÞ¹ØµÄ×Ö·û
        Matcher m0 = pattern0.matcher(lrc);
        lrc = m0.replaceAll("\n");
        //String[] lrcline =lrc.split(lrc) ;
        //lrc = lrc.replaceAll("\\n", "\\r\\n") ;
        System.out.print(lrc);
        //for(String tmp:lrcline)
        //  System.out.println(tmp) ;
        return lrc;
    }

   /**
    * 
    * @param args
    */
   public static void main(String[] args) {
        SpiderTingLRC spiderlrc = new SpiderTingLRC("2103686");
        spiderlrc.getLrc();
    }
}
