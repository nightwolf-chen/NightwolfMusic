/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class SearchBySong {
    String path ;
    String songName ;
    List<String> url = new ArrayList<String>();
    SearchBySong(String path , String songName){
    this.path = path ;
    this.songName = songName ;
    }
    int search(String singerName) throws IOException {
        String data = GetData.getdata(this.path, singerName);
        Pattern pattern = Pattern.compile("m1"+this.songName+"m2(.*?)m3");       // 这个正则表达式匹配歌手的主页，得到歌手主页的url和歌手的名字
        Matcher page = pattern.matcher(data); //在getcode（也就是得到的网页）里面找相匹配的字符串
        while (page.find()) {
            url.add(page.group(1));
        }
        return url.size();
    }
    int searchSong() throws IOException{
        String singerList = GetData.getdata(path, "singer.tmp") ; 
        Pattern pattern = Pattern.compile("m1(.*?)m2");       
        Matcher singerName = pattern.matcher(singerList);
        while(singerName.find()){
            String singerNamestr = singerName.group(1) ;
            search(singerNamestr) ;
        }
        return url.size() ;
    }
    /*
    boolean downLoad(String path ,String songName , String url){
        if(Mp31.download(path+songName+".mp3",url, songName))
        return true ;
        else 
        return false ;
    }
     * 
     */
    /**
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
    SearchBySong search = new SearchBySong("G:/Spider/" ,"哥对不起") ;
    if(search.searchSong() > 0){
    for(int i = 0 ; i < search.url.size() ;i++){
        System.out.println(search.url.get(i)) ;
        //Mp31.download("K:/" + search.songName + ".mp3/", search.url.get(i), search.songName);
    }
    }
    else
        System.out.println("Nothing Found!") ;
    } 
}
