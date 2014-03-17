package Searcher;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class SearchByName {

    String singerName;
    String path;
    List<String> name = new ArrayList<String>();
    List<String> url = new ArrayList<String>();

    SearchByName(String path, String name) {
        this.singerName = name;
        this.path = path;
    }

    boolean isExist() {
        File file = new File(this.path, this.singerName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    int search() throws IOException {
        if(!this.isExist())
            return 0 ;
        String data = GetData.getdata(this.path, this.singerName);
        System.out.println(data);
        Pattern pattern = Pattern.compile("m1(.*?)m2(.*?)m3");       // 这个正则表达式匹配歌手的主页，得到歌手主页的url和歌手的名字
        Matcher page = pattern.matcher(data); //在getcode（也就是得到的网页）里面找相匹配的字符串
        while (page.find()) {
            name.add(page.group(1));
            url.add(page.group(2));
        }
        return name.size();
    }

    /**
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SearchByName sb = new SearchByName("D:/testSpiderTing2/", "陈楚生");
        if (sb.search() > 0) {
            for (int i = 0; i < sb.name.size(); i++) {
                System.out.println(sb.name.get(i) + "=="+sb.url.get(i));
            }
        } else {
            System.out.println("Nothing to be found!");
        }
    }
}
