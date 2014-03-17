package MusicDownload;


/*
 *这个类是用于指定urlMP3文件的下载的类
 * MP31里面的download类的具体用法：
 * String audio指向的是文件存储的路径，包括本身的文件名
 * audioUrl 是文件资源的url
 * song参数指定是文件名（不包含后缀名MP3）
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
public class Mp31 {

    /**
     * 
     * @param audio
     * @param audioUrl
     * @param song
     * @return
     */
    public static boolean download(String audio, String audioUrl, String song) {

        try {
            long beginTime = System.currentTimeMillis();    //记录下载开始的时间

            System.out.println("Downloading:" + song + "......");
            String mp3 = song + ".mp3";                     //为文件名加上后缀"mp3"
            String dir = audio.replaceAll(mp3, "");         //将文件路径当中的文件名去掉，获取文件夹的路径
            //String dir = audio ;
            File fdir = new File(dir);                      //实例化文件
            File file = new File(audio);

            if (!fdir.exists()) {                           //如果文件夹不存在则创建新的文件夹
                fdir.mkdirs();
            }

            OutputStream os = null;                         //打开输出流
            URL url = new URL(audioUrl);                    //创建新的URL对象
            URLConnection uc = url.openConnection();        //打开链接
            uc.setConnectTimeout(10000);                    //设置等待时间
            uc.setDoOutput(true);                           //设置输出属性
            InputStream is = uc.getInputStream();
            file.createNewFile();                           //创建文件.mp3

            /*
             * 下面的一部分代码是将是将已经打开的链接中得输出流写入到指定的文件
             * 当中，如果超过预先设定的时间任然没有接收到数据则判定为下载失败。
             */
            os = new FileOutputStream(file);
            int b = is.read();
            while (b != -1) {
                os.write(b);
                b = is.read();
                if ((System.currentTimeMillis() - beginTime) > 1200000) {
                    System.out.println("time out!"+audio + "DownLoading Failed！");
                    return false;
                }
            }
            is.close();
            os.close();  //关闭输出输入流                       

            /*输出下载所用的时间*/
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
    public static boolean downLoad(String path ,String songName , String url){//这个方法中的path是指文件夹的路径不包含文件名，songName不包含后缀.mp3
        if(Mp31.download(path+songName+".mp3",url, songName))
        return true ;
        else 
        return false ;
    }
}
