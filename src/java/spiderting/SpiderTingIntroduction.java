/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderting;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * nightwolf
 */
/**
 * 
 * @author nightwolf
 */
public class SpiderTingIntroduction {

    String singerURL;
    String decode = "utf-8";
    String singerId ="" ;
    /**
     * 
     * @param singerId
     */
    public SpiderTingIntroduction(String singerId) {
        this.singerId = singerId ;
        this.singerURL = "http://music.baidu.com/artist/" + singerId;
    }

    private String getBaikeURL() throws UnsupportedEncodingException {
        String baikeURL = "";
        Code code = new Code();
        String getCode = code.getCode(this.singerURL, this.decode);

        Pattern pattern = Pattern.compile("_href = \"(.*?)\"");
        Matcher lrcMatcher = pattern.matcher(getCode);

        lrcMatcher.find();
        baikeURL = lrcMatcher.group(1);
        return baikeURL;
    }

    /**
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getIntroduction() throws UnsupportedEncodingException {
        String intro = "";
        Code code = new Code();
        String getCode = code.getCode(this.getBaikeURL(), "gb2312");
        //System.out.println(getCode);
        Pattern pattern = Pattern.compile("<div class=\"card-summary-content\">(.*?)</div>");
        Matcher introMatcher = pattern.matcher(getCode);
        if (introMatcher.find()) {
            intro = introMatcher.group(1);
        }
        Pattern pattern0 = Pattern.compile("[a-zA-Z0-9/<>=_.]");//ÌÞ³ýÎÞ¹ØµÄ×Ö·û
        Matcher m0 = pattern0.matcher(intro);
        intro = m0.replaceAll("");
        System.out.println("intro"+intro) ;
        return intro;
    }

    /**
     * 
     * @return
     */
    public String getIntroduction_ole() {
        String intro = "";
        Code code = new Code();
        String getCode = code.getCode(this.singerURL, this.decode);

        Pattern pattern = Pattern.compile("<strong>¼ò½é</strong><span>(.*?)</span></li>");
        Matcher lrcMatcher = pattern.matcher(getCode);

        lrcMatcher.find();
        intro = lrcMatcher.group(1);

        Pattern pattern0 = Pattern.compile("<br />");//ÌÞ³ýÎÞ¹ØµÄ×Ö·û
        Matcher m0 = pattern0.matcher(intro);
        intro = m0.replaceAll("\n");
        //System.out.print(intro) ;
        return intro;
    }
    /**
     * 
     * @return
     */
    public String getPhotoURL()
    {
        String baikeURL = "";
        Code code = new Code();
        String getCode = code.getCode(this.singerURL, this.decode);

        Pattern pattern = Pattern.compile("<span class=\"cover\"><img src=\"(.*?)\" alt=\"(.*?)\" />");
        Matcher photoMatcher = pattern.matcher(getCode);
        if(photoMatcher.find()){
            return photoMatcher.group(1);
        }else{
            return "";
        }
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            SpiderTingIntroduction intro = new SpiderTingIntroduction("1157");
            
            System.out.println(intro.getBaikeURL());
            System.out.println("intro:"+intro.getIntroduction());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SpiderTingIntroduction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
