/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicDownload;

/**
 *
 * @author Administrator
 */
public class DownLoad implements Runnable {
     String path ;
     String url ;
     String songName ;
     /**
      * 
      */
     public boolean isDownload ;
     /**
      * 
      * @param path
      * @param songName
      * @param url
      */
     public DownLoad(String path, String songName, String url) {
        this.path = path;
        this.url = url;
        this.songName = songName;
    }
    @Override
    public void run() {
       if(Mp31.downLoad(path, songName, url))
           this.isDownload = true ;
    }
    
}
