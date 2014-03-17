/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseToPinyin;

/**
 *
 * @author nightwolf
 */
import net.sourceforge.pinyin4j.PinyinHelper;

// only when you need formating the output 
import net.sourceforge.pinyin4j.format.exception.*;
/**
 * 
 * @author nightwolf
 */
public class Pinyin {

    /**
     * @param chinese
     * @return  
     */
    public static String getPinyin(String chinese){
        String pinyin = "" ;
        int len = chinese.length();
        String[] pinyinEach ;
        for(int i = 0 ;i < len ; i++){
        pinyinEach = PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(i));
        if(pinyinEach != null)
        pinyin += pinyinEach[0];
        pinyin += " ";
        }
        return pinyin ;
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
    }
}
