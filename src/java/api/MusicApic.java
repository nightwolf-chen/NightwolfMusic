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

import object.Album;
import object.Artist;
import object.Song;

/**
 *
 * @author bruce
 */
public interface MusicApic {
    
    abstract Song getSongById(String songId);
    abstract Album getAlbumById(String albumId);
    abstract Artist getArtistById(String artistId);
            
}
