package Test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import spiderting.Code;

/**
 *
 * @author Administrator
 */
public class TestExpand {

    String fathUrl = "http://ting.baidu.com";
    String decode = "utf-8";
    //String decode = "gb2312" ;
    // String[] index = {"mainland/male/","mainland/female/","mainland/group/","hktw/male/","hktw/male/","hktw/male/","jpkr/male/","jpkr/male/","jpkr/male/","other/male/","other/male/","other/male/"."western/male/","western/male/","western/male/"} ; 
    void Spider(String path) throws FileNotFoundException, IOException {
        //System.out.println("in spider!") ;
        File dirFile = new File(path);         //ʵ�����ļ�
        if (!dirFile.exists()) {                       //���ļ��в����ڴ����µ��ļ���
            dirFile.mkdirs();
        }

        File singerFile = new File(path, "singer.tmp");
        if (!singerFile.exists()) {
            singerFile.createNewFile();
        }
        FileOutputStream singeros = new FileOutputStream(singerFile);
        BufferedOutputStream singerbos = new BufferedOutputStream(singeros);
        OutputStreamWriter singerosw = new OutputStreamWriter(singerbos);
        
        System.out.println("fathUrl:" + fathUrl);

                String singerName = "�³���";

                System.out.println("singerName:" + singerName);

                System.out.println("write:"+singerName+" intoFile") ;
                singerosw.write("m1" + singerName + "m2");
                singerosw.flush();

                File singer = new File(path, singerName);
                if (!singer.exists()) {
                    singer.createNewFile();
                }
                FileOutputStream os = new FileOutputStream(singer);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                OutputStreamWriter osw = new OutputStreamWriter(bos);

                Code code3 = new Code();
                String getcode3 = code3.getCode("http://ting.baidu.com/artist/1038/song", this.decode);
                //System.out.println(getcode3) ;
                Pattern pattern3 = Pattern.compile("<a href=\"/song/(.*?)\" title=\"(.*?)\">(.*?)</a>");

                Matcher songPage = pattern3.matcher(getcode3);

                Pattern pattern4 = Pattern.compile("/artist/(.*?)/song(.*?)order(.*?)time(.*?)start=(.*?)(.*?)size=20");
                //�³���:http://ting.baidu.com/artist/1038/song
                Matcher expand = pattern4.matcher(getcode3);

                Pattern pattern5 = Pattern.compile("<a href=\"(.*?)\" class=\"btn-download\">����</a>");
                
                 while (expand.find()) {//�ڵ�ǰ�ҵ��ĸ���ҳ����չ
                    String songExpandUrl = this.fathUrl + expand.group(0);//�õ���Ҫ��չ��ҳ���url
                    System.out.println("ExpandPageUrl: " + songExpandUrl);
                    Code code5 = new Code();
                    String getcode5 = code5.getCode(songExpandUrl, this.decode);//��ȡҳ��Ĵ���
                    Matcher songExpandPage = pattern3.matcher(getcode5);
                    System.out.println(getcode5) ;
                    while (songExpandPage.find()) {
                        System.out.println("In Expanding!");
     
                        String downPageUrl = this.fathUrl + "/song/" + songExpandPage.group(1) + "/download/";

                        String songName = songExpandPage.group(2);

                        Code code4 = new Code();
                        String getcode4 = code4.getCode(downPageUrl, this.decode);
                        Matcher downPage = pattern5.matcher(getcode4);

                        if (!downPage.find()) {
                            continue;
                        }

                        String downUrl = downPage.group(1);
                        osw.write("m1" + songName + "m2" + downUrl + "m3");
                        osw.flush();
                        System.out.println("songName" + songName);
                        System.out.println("Find download URL:" + downUrl);
                    }//songExpandPage.find()
                         
                }//expand while
                 
        
    }//clase

    /**
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        TestExpand spider = new TestExpand() ;
        spider.Spider("D:/testSpiderTing2/");
    }
}


    /*
                while (songPage.find()) {
                String downPageUrl = this.fathUrl + "/song/" + songPage.group(1) + "/download/";
                //System.out.println("downPageUrl:"+downPageUrl) ;
                String songName = songPage.group(2);
                
                Code code4 = new Code();
                String getcode4 = code4.getCode(downPageUrl, this.decode);
                Matcher downPage = pattern5.matcher(getcode4);
                
                if (!downPage.find()) {
                continue;
                }
                String downUrl =this.fathUrl + downPage.group(1);
                osw.write("m1"+songName+"m2"+downUrl+"m3") ;
                osw.flush() ;
                System.out.println("songName"+songName) ;
                System.out.println("Find download URL:"+downUrl) ;
                }
                 * */
                /*
                while (expand.find()) {//�ڵ�ǰ�ҵ��ĸ���ҳ����չ
                    String songExpandUrl = this.fathUrl + expand.group(0);//�õ���Ҫ��չ��ҳ���url
                    System.out.println("ExpandPageUrl: " + songExpandUrl);
                    Code code5 = new Code();
                    String getcode5 = code5.getCode(songExpandUrl, this.decode);//��ȡҳ��Ĵ���
                    Matcher songExpandPage = pattern3.matcher(getcode5);

                    while (songExpandPage.find()) {
                        System.out.println("In Expanding!");
                        String downPageUrl = this.fathUrl + "/song/" + songPage.group(1) + "/download/";

                        String songName = songPage.group(2);

                        Code code4 = new Code();
                        String getcode4 = code4.getCode(downPageUrl, this.decode);
                        Matcher downPage = pattern5.matcher(getcode4);

                        if (!downPage.find()) {
                            continue;
                        }

                        String downUrl =this.fathUrl + downPage.group(1);
                        osw.write("m1" + songName + "m2" + downUrl + "m3");
                        osw.flush();
                        System.out.println("songName" + songName);
                        System.out.println("Find download URL:" + downUrl);
                    }//songExpandPage.find()
                    
                    
                }//expand while
                */