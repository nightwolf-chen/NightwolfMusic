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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.HttpClientAdaptor;
import network.HttpClientAdaptorFactory;
import object.Album;
import object.Artist;
import object.Song;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author bruce
 */
public class BaiduFolkMusicApi implements MusicApic {

    private final String apiInvokeAddress = "http://musicapi.sinaapp.com/bdmusic.php";
    private final String dataSeparator = ",";
    HttpClientAdaptor httpClient = HttpClientAdaptorFactory.createDefaultHttpClientAdaptor();

    @Override
    public Song getSongById(String songId) {

        String rate = this.getDefaultRateBySongId(songId);
        String songName = this.doActionWith(songId, "getSongTitle");
        String artistName = this.doActionWith(songId, "getSongArtist");
        String songUrl = this.doActionWith(songId, "getSongURL");
        String songSize = this.doActionWith(songId, "getSongSize", rate);
        String songFormat = this.doActionWith(songId, "getSongFormat", rate);
        String songHash = this.doActionWith(songId, "getSongHash", rate);
        String songSource = this.doActionWith(songId, "getSongSource");
        String songDuration = this.doActionWith(songId, "getSongDuration", rate);
        String albumId = this.doActionWith(songId, "getAlbumID");
        String albumTitle = this.doActionWith(songId, "getAlbumTitle");
        String alnumImgUrl = this.doActionWith(songId, "getAlbumImageURL");
        String lrcUrl = this.doActionWith(songId, "getLyricURL");
        String lrc = null;
        if (lrcUrl != null && !lrcUrl.equals("")) {
            System.out.println(lrcUrl);
            lrc = this.httpClient.doGet(lrcUrl);
        }

        Album album = new Album();
        album.albumId = albumId;
        album.albumImgUrl = alnumImgUrl;
        album.albumTitle = albumTitle;

        Artist artist = new Artist();
        artist.artistName = artistName;

        Song song = new Song();
        song.setAlbum(album);
        song.setArtist(artist);
        song.setSongId(songId);
        song.setSongUrl(songUrl);
        song.setSongDuration(songDuration);
        song.setSongFormat(songFormat);
        song.setSongHash(songHash);
        song.setSongSize(songSize);
        song.setSongSource(songSource);
        song.setSongname(songName);
        
        return song;
    }

    @Override
    public Album getAlbumById(String albumId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist getArtistById(String artistId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private URI getRequestUri(List<NameValuePair> parameters) {

        try {

            parameters.add(new BasicNameValuePair("errordetails", "1"));
            URIBuilder uriBuilder = new URIBuilder(this.apiInvokeAddress);
            uriBuilder.addParameters(parameters);

            URI uri = uriBuilder.build();
            String uriStr = uri.toString();

            System.out.println(uriStr);

            return uri;

        } catch (URISyntaxException ex) {
            Logger.getLogger(BaiduFolkMusicApi.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String getDefaultRateBySongId(String songId) {

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("id", songId));
        parameters.add(new BasicNameValuePair("action", "listSongRate"));
        URI rateRequestUri = this.getRequestUri(parameters);

        String data = this.httpClient.doGet(rateRequestUri.toString());
        if (data != null) {
            String rates[] = data.split(this.dataSeparator);
            return rates[0];
        }

        return null;
    }

    public String doActionWith(String songId, String action, String rate) {

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("id", songId));
        parameters.add(new BasicNameValuePair("action", action));
        parameters.add(new BasicNameValuePair("rate", rate));

        String uriStr = this.getRequestUri(parameters).toString();

        return this.httpClient.doGet(uriStr);
    }

    public String doActionWith(String songId, String action) {

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("id", songId));
        parameters.add(new BasicNameValuePair("action", action));

        String uriStr = this.getRequestUri(parameters).toString();

        return this.httpClient.doGet(uriStr);
    }

    public static void main(String[] args) {

        BaiduFolkMusicApi folkApi = new BaiduFolkMusicApi();
        Song aSong = folkApi.getSongById("7313183");
        
        System.out.println(aSong.getSongname());
        System.out.println(aSong.getSongUrl());
         

    }
}
