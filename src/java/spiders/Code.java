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
 * Code�࣬��Ҫ���� getcode(String url , String encode)�÷���
 * url����ҳ��url
 * encode�����뷽ʽ
 * getcode�Ĺ��ܣ���ָ��url����ҳ����ȡ��Դ���룬����String
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
            URLConnection uc = new URL(url).openConnection();//������
            
            uc.setConnectTimeout(3000);                     //���õȴ�ʱ��
            uc.setDoOutput(true);                            //�����������
            InputStream inputStream = uc.getInputStream();
            
            InputStream in = new BufferedInputStream(inputStream);//��������
            
            Reader rd = new InputStreamReader(in, encode);                //�ַ���
            
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
            URLConnection uc = new URL(url).openConnection(pro);//������
            uc.setConnectTimeout(3000);                     //���õȴ�ʱ��
            uc.setDoOutput(true);                            //�����������
            InputStream inputStream = uc.getInputStream();
            
            InputStream in = new BufferedInputStream(inputStream);//��������
           
            Reader rd = new InputStreamReader(in, "utf8");                //�ַ���
            
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
            URLConnection uc = new URL(url).openConnection();//������
            uc.setConnectTimeout(3000);                     //���õȴ�ʱ��
            uc.setDoOutput(true);                            //�����������
            InputStream inputStream = uc.getInputStream();
            
            InputStream in = new BufferedInputStream(inputStream);//��������
           
            Reader rd = new InputStreamReader(in, encode);                //�ַ���
            
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

