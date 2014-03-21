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
package network;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

/**
 *
 * @author bruce
 */
public class ProxiedHttpClientAdaptor extends HttpClientAdaptor {

    public ProxiedHttpClientAdaptor(HttpHost proxy) {

        super();

        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        this.httpclient = HttpClients.custom()
                                     .setRoutePlanner(routePlanner)
                                     .build();
    }
    
    public ProxiedHttpClientAdaptor(HttpHost proxy,String encode){
        super(encode);
        
         DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        this.httpclient = HttpClients.custom()
                                     .setRoutePlanner(routePlanner)
                                     .build();
    }

}
