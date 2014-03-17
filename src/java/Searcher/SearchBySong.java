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
        Pattern pattern = Pattern.compile("m1"+this.songName+"m2(.*?)m3");       // ���������ʽƥ����ֵ���ҳ���õ�������ҳ��url�͸��ֵ�����
        Matcher page = pattern.matcher(data); //��getcode��Ҳ���ǵõ�����ҳ����������ƥ����ַ���
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
    SearchBySong search = new SearchBySong("G:/Spider/" ,"��Բ���") ;
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
