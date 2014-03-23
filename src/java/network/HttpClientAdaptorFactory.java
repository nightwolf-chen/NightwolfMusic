/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

import org.apache.http.HttpHost;

/**
 *
 * @author bruce
 */
public class HttpClientAdaptorFactory {
    public static HttpClientAdaptor createDefaultHttpClientAdaptor(String connectionEncode){
        HttpHost proxy = new HttpProxyGetter().getAProxy();
        return new ProxiedHttpClientAdaptor(proxy, connectionEncode);
    }
    
     public static HttpClientAdaptor createDefaultHttpClientAdaptor(){
//        HttpHost proxy = new HttpProxyGetter().getAProxy();
//        return new ProxiedHttpClientAdaptor(proxy);
         return new HttpClientAdaptor();
    }
}
