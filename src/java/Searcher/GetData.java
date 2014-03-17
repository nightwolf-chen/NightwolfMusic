package Searcher;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class GetData {

    static String getdata(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        String str = "";
        FileInputStream in = new FileInputStream(file);//打开输入流
        Reader rd = new InputStreamReader(in);                //字符流
        int c = 0;
        StringBuilder temp = new StringBuilder();
        while ((c = rd.read()) != -1) {
            temp.append((char) c);
        }
        in.close();
        str = temp.toString();
        return str; 
    }
}
