package com.example.smartphone;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

/**
 * http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-
 * url/ http://avilyne.com/?p=105
 * 
 * @author Jana
 *         http://developer.android.com/guide/topics/ui/controls/radiobutton
 *         .html wichtig
 */
public class WebService extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... urls)
    {
        // TODO Auto-generated method stub
        // http://www.androidsnippets.com/executing-a-http-get-request-with-httpclient
        URL url = null;

        URI uri;
        try {
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
            HttpClient httpClient = new DefaultHttpClient(httpParams);

            uri = new URI(urls[0]);
            HttpGet get = new HttpGet();
            get.setURI(uri);
            System.out.println(uri);
            try {
                httpClient.execute(get);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /**
         * HttpURLConnection conn; try { conn = (HttpURLConnection)
         * url.openConnection(); conn.setRequestMethod("GET");
         * System.out.println("aufgerufen");
         * 
         * conn.connect();
         * 
         * System.out.println( conn.getResponseMessage()); } catch (IOException
         * e) {System.out.println("hallo"); // TODO Auto-generated catch block
         * e.printStackTrace(); }
         * 
         * 
         * 
         * URI url=null;
         * 
         * try { url = new URI( urls[0]); } catch (URISyntaxException e1) { //
         * TODO Auto-generated catch block e1.printStackTrace(); } HttpParams
         * htpp = new BasicHttpParams();
         * 
         * HttpClient h=new DefaultHttpClient(htpp); HttpGet get=new HttpGet();
         * 
         * 
         * get.setURI(url); try { h.execute(get); } catch
         * (ClientProtocolException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
         * catch block e.printStackTrace(); }
         **/
        return null;
    }

}
