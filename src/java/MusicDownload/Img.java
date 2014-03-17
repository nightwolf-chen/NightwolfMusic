/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicDownload;

/**
 *
 * @author Administrator
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author nightwolf
 */
public class Img {

    /**
     * 
     * @param audio
     * @param audioUrl
     * @param song
     * @return
     */
    public static boolean download(String audio, String audioUrl, String song) {

        try {
            long beginTime = System.currentTimeMillis();    //��¼���ؿ�ʼ��ʱ��

            System.out.println("Downloading:" + song + "......");
            String mp3 = song + ".jpg";                     //Ϊ�ļ������Ϻ�׺"mp3"
            String dir = audio.replaceAll(mp3, "");         //���ļ�·�����е��ļ���ȥ������ȡ�ļ��е�·��
            //String dir = audio ;
            File fdir = new File(dir);                      //ʵ�����ļ�
            File file = new File(audio);

            if (!fdir.exists()) {                           //����ļ��в������򴴽��µ��ļ���
                fdir.mkdirs();
            }
            if(file.exists()){
                return true ;
            }
            OutputStream os = null;                         //�������
            URL url = new URL(audioUrl);                    //�����µ�URL����
            URLConnection uc = url.openConnection();        //������
            uc.setConnectTimeout(10000);                    //���õȴ�ʱ��
            uc.setDoOutput(true);                           //�����������
            InputStream is = uc.getInputStream();
            file.createNewFile();                           //�����ļ�.mp3

            /*
             * �����һ���ִ����ǽ��ǽ��Ѿ��򿪵������е������д�뵽ָ�����ļ�
             * ���У��������Ԥ���趨��ʱ����Ȼû�н��յ��������ж�Ϊ����ʧ�ܡ�
             */
            os = new FileOutputStream(file);
            int b = is.read();
            while (b != -1) {
                os.write(b);
                b = is.read();
                if ((System.currentTimeMillis() - beginTime) > 1200000) {
                    System.out.println("time out!"+audio + "DownLoading Failed��");
                    return false;
                }
            }
            is.close();
            os.close();  //�ر����������                       

            /*����������õ�ʱ��*/
            System.out.println(audio + "Download In:" + (System.currentTimeMillis() - beginTime) / 1000 + "sec");
            return true;
        } catch (Exception e) {  
            System.out.println(e.toString()) ;
            System.out.println(audio + " " + "Failed!");
            return false;
        }
    }
    /**
     * 
     * @param path
     * @param songName
     * @param url
     * @return
     */
    public static boolean downLoad(String path ,String songName , String url){//��������е�path��ָ�ļ��е�·���������ļ�����songName��������׺.mp3
        if(Img.download(path+songName+".jpg",url, songName))
        return true ;
        else 
        return false ;
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
    }
}
