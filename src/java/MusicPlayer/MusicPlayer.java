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
    public Player audioPlayer = null;//����һ�����Žӿ�
    String path = "";
    Thread td = null;
    Thread tp = null;

    /**
     * 
     * @param url
     * @throws Exception
     */
    public MusicPlayer(String url) throws Exception {//����һ��׼��Player,׼���ò���

        File urlreal = new File(url);
        audioPlayer = Manager.createRealizedPlayer(urlreal.toURL());

    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    /*
    public SimpleAudioPlayer(File file) throws MalformedURLException, Exception{//�������ļ���ΪURL 
    this(file.toURL()); 
    } 
     */
    public void play() {//ֱ�ӵ��ò��ŷ����Ϳ���

        this.audioPlayer.start();
    }

    /**
     * 
     */
    public void stop() {//ֹͣ��ʱ��һ��Ҫ�ͷ���Դ 
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
        MusicPlayer Player = new MusicPlayer("D:/Niglwof_music/��ɫ��Live�棩.mp3");
        Player.play();
    }
}
