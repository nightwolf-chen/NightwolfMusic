/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiders;

/**
 *
 * @author Administrator
 */
/*
 * Code类，主要方法 getcode(String url , String encode)用法：
 * url：网页的url
 * encode：编码方式
 * getcode的功能：打开指定url的网页并获取其源代码，返回String
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author nightwolf
 */
public class Code {

    /**
     * 
     * @param url
     * @return
     */
    public String getCode(String url) {
        String strHtml = "";
        String encode="gb2312" ;
        try {
            URLConnection uc = new URL(url).openConnection();//打开链接
            
            uc.setConnectTimeout(3000);                     //设置等待时间
            uc.setDoOutput(true);                            //设置输出属性
            InputStream inputStream = uc.getInputStream();
            
            InputStream in = new BufferedInputStream(inputStream);//打开输入流
            
            Reader rd = new InputStreamReader(in, encode);                //字符流
            
            int c = 0;
            StringBuilder temp = new StringBuilder();
           
            
            while ((c = rd.read()) != -1) {
                temp.append((char) c);
            }
            in.close();
            strHtml = temp.toString();
        } catch (IOException ex) {
            Logger.getLogger(Code.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strHtml;
    }
     /**
      * 
      * @param url
      * @param pro
      * @return
      */
     public String getCode(String url,Proxy pro){
           String strHtml = "";
        try {
            URLConnection uc = new URL(url).openConnection(pro);//打开链接
            uc.setConnectTimeout(3000);                     //设置等待时间
            uc.setDoOutput(true);                            //设置输出属性
            InputStream inputStream = uc.getInputStream();
            
            InputStream in = new BufferedInputStream(inputStream);//打开输入流
           
            Reader rd = new InputStreamReader(in, "utf8");                //字符流
            
            int c = 0;
            StringBuilder temp = new StringBuilder();
           
            
            while ((c = rd.read()) != -1) {
                temp.append((char) c);
            }
            in.close();
            strHtml = temp.toString();
        } catch (IOException ex) {
            Logger.getLogger(Code.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strHtml;
     }
     /**
      * 
      * @param url
      * @param encode
      * @return
      */
     public String getCode(String url, String encode) {
        String strHtml = "";
        try {
            URLConnection uc = new URL(url).openConnection();//打开链接
            uc.setConnectTimeout(3000);                     //设置等待时间
            uc.setDoOutput(true);                            //设置输出属性
            InputStream inputStream = uc.getInputStream();
            
            InputStream in = new BufferedInputStream(inputStream);//打开输入流
           
            Reader rd = new InputStreamReader(in, encode);                //字符流
            
            int c = 0;
            StringBuilder temp = new StringBuilder();
           
            
            while ((c = rd.read()) != -1) {
                temp.append((char) c);
            }
            in.close();
            strHtml = temp.toString();
        } catch (IOException ex) {
            Logger.getLogger(Code.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strHtml;
    }
}

