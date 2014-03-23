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
package object;

/**
 *
 * @author bruce
 */
public class Song {

    String songId;
    String songname;
    String songUrl;

    String songSize;
    String songDuration;
    String songSource;
    String songFormat;
    String songHash;

    Artist artist;
    Album album;

    String lrc;

    public Song() {
        this.artist = new Artist();
        this.album = new Album();
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getSongname() {
        return songname;
    }

    public Artist getArtist() {
        return artist;
    }

    public Album getAlbum() {
        return album;
    }

    public String getSongId() {
        return songId;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getLrc() {
        return lrc;
    }

    public String getSongSize() {
        return songSize;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public String getSongSource() {
        return songSource;
    }

    public String getSongFormat() {
        return songFormat;
    }

    public String getSongHash() {
        return songHash;
    }

    public void setSongSize(String songSize) {
        this.songSize = songSize;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public void setSongSource(String songSource) {
        this.songSource = songSource;
    }

    public void setSongFormat(String songFormat) {
        this.songFormat = songFormat;
    }

    public void setSongHash(String songHash) {
        this.songHash = songHash;
    }

    
}
