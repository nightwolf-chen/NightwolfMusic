/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *JDKĬ��û��org.apache.http������Ҫ��ȥhttp://hc.apache.org/downloads.cgi����
 *����HttpClient����ѹ����Eclipse�е�������JAR
 */
public class WebApache {

    /**
     * @param url 
     * @return  
     */
    public String getContent(String url) {
        try {
            HttpGet httpget = new HttpGet(url);
            //����HttpPost����
            HttpResponse response = new DefaultHttpClient().execute(httpget);
            //����GET,������һ��HttpResponse���������POST��ʡȥ�����NameValuePair����������
            if (response.getStatusLine().getStatusCode() == 200) {//���״̬��Ϊ200,������������
                String result = EntityUtils.toString(response.getEntity());
                //�õ����ص��ַ���
                return result;
            }
        } catch (IOException ex) {
            Logger.getLogger(WebApache.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String url = "http://localhost/newspaper/test/1.php?usr=156gs";
        //GET��URL,����ֱ�Ӽ�URL��
        HttpGet httpget = new HttpGet(url);
        //����HttpPost����
        HttpResponse response = new DefaultHttpClient().execute(httpget);
        //����GET,������һ��HttpResponse���������POST��ʡȥ�����NameValuePair����������
        if (response.getStatusLine().getStatusCode() == 200) {//���״̬��Ϊ200,������������
            String result = EntityUtils.toString(response.getEntity());
            //�õ����ص��ַ���
            System.out.println(result);
            //��ӡ���
        }
    }
}