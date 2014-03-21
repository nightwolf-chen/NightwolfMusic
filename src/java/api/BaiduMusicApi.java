/*
 * Copyright 2014 bruce.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.HttpClientAdaptor;
import network.HttpClientAdaptorFactory;
import object.Song;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author bruce
 */
public class BaiduMusicApi {

    private final String kSongURL = "kSongURL";
    private final String kLrcid = "kLrcid";
    
    public Song getSongDetail(String songName, String artist) throws UnsupportedEncodingException {

        Song aSong = new Song();
        songName = URLEncoder.encode(songName.trim(), "utf8");
        artist = URLEncoder.encode(artist.trim(), "utf8");
        String apiUrl = "http://box.zhangmen.baidu.com/x?op=12&count=1&title=" + songName + "$$" + artist + "$$$$";
        HttpClientAdaptor clientAdaptor = HttpClientAdaptorFactory.createDefaultHttpClientAdaptor("gb2312");
        String xmlStr = clientAdaptor.doGet(apiUrl);
        Map<String, String> data = paserForSongData(xmlStr);
        
        String lrcUrl = this.generateLrcUrl(data.get(kLrcid));
        String lrcStr = clientAdaptor.doGet(lrcUrl);
        
        if(lrcStr.contains("很抱歉，您要访问的页面不存在")){
            lrcStr = "暂无歌词";
        }
        
        aSong.setLrc(lrcStr);
        aSong.setSongUrl(data.get(kSongURL));

        System.out.println(data.get(kSongURL));
        
        try {
            clientAdaptor.getHttpclient().close();
        } catch (IOException ex) {
            Logger.getLogger(BaiduMusicApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aSong;
    }

    private Map<String, String> paserForSongData(String xmlStr) {

        Map<String, String> data = new HashMap<String, String>();
        DOMParser domParser = new DOMParser();
        Document document = domParser.parse(xmlStr);
        Element elem = document.getDocumentElement();

        NodeList resultNodes = elem.getChildNodes();
        Node urlNode = resultNodes.item(1);
        NodeList urlNodes = urlNode.getChildNodes();

        String encodedUrl = urlNodes.item(0).getTextContent();
        String decodedUrl = urlNodes.item(1).getTextContent();
        String lrcId = urlNodes.item(3).getTextContent();

        String[] splitedUrl = encodedUrl.split("/");
        String downloadURL = encodedUrl.replace(splitedUrl[splitedUrl.length - 1], decodedUrl);
        data.put(kSongURL, downloadURL);
        data.put(kLrcid, lrcId);
        
        
        
        return data;
        
    }
    
    private String generateLrcUrl(String lrcid){
        int tmp = Integer.valueOf(lrcid);
        String lrcCode = String.valueOf((int)(tmp / 100));
        return "http://box.zhangmen.baidu.com/bdlrc/"+lrcCode+"/"+lrcid+".lrc";
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        new BaiduMusicApi().getSongDetail("风继续吹", "张国荣");
    }
}
