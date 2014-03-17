package MusicPlayer;

import java.io.File;
import java.net.MalformedURLException;

import javax.media.Manager;
import javax.media.Player;

//@SuppressWarnings("restriction") 
/**
 * 
 * @author nightwolf
 */
public class MusicPlayer {

    /**
     * 
     */
    public Player audioPlayer = null;//建立一个播放接口
    String path = "";
    Thread td = null;
    Thread tp = null;

    /**
     * 
     * @param url
     * @throws Exception
     */
    public MusicPlayer(String url) throws Exception {//创建一个准备Player,准备好播放

        File urlreal = new File(url);
        audioPlayer = Manager.createRealizedPlayer(urlreal.toURL());

    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    /*
    public SimpleAudioPlayer(File file) throws MalformedURLException, Exception{//将本地文件改为URL 
    this(file.toURL()); 
    } 
     */
    public void play() {//直接调用播放方法就可以

        this.audioPlayer.start();
    }

    /**
     * 
     */
    public void stop() {//停止的时候一定要释放资源 
        audioPlayer.stop();
        audioPlayer.close();
    }

    /**
     * 
     * @param args
     * @throws MalformedURLException
     * @throws Exception
     */
    public static void main(String[] args) throws MalformedURLException, Exception {
        MusicPlayer Player = new MusicPlayer("D:/Niglwof_music/眼色（Live版）.mp3");
        Player.play();
    }
}
