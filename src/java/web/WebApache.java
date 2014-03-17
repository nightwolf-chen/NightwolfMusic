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
 *JDK默认没有org.apache.http包，需要先去http://hc.apache.org/downloads.cgi下载
 *下载HttpClient，解压，在Eclipse中导入所有JAR
 */
public class WebApache {

    /**
     * @param url 
     * @return  
     */
    public String getContent(String url) {
        try {
            HttpGet httpget = new HttpGet(url);
            //建立HttpPost对象
            HttpResponse response = new DefaultHttpClient().execute(httpget);
            //发送GET,并返回一个HttpResponse对象，相对于POST，省去了添加NameValuePair数组作参数
            if (response.getStatusLine().getStatusCode() == 200) {//如果状态码为200,就是正常返回
                String result = EntityUtils.toString(response.getEntity());
                //得到返回的字符串
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
        //GET的URL,参数直接加URL后
        HttpGet httpget = new HttpGet(url);
        //建立HttpPost对象
        HttpResponse response = new DefaultHttpClient().execute(httpget);
        //发送GET,并返回一个HttpResponse对象，相对于POST，省去了添加NameValuePair数组作参数
        if (response.getStatusLine().getStatusCode() == 200) {//如果状态码为200,就是正常返回
            String result = EntityUtils.toString(response.getEntity());
            //得到返回的字符串
            System.out.println(result);
            //打印输出
        }
    }
}