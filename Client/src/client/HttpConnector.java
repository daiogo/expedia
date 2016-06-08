/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author diego
 */
public class HttpConnector {

    private final String USER_AGENT = "Chrome/51.0.2704.79";

    // HTTP GET request
    public String sendGet(String url) {

        try {
            
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            
            // Optional (default is GET)
            con.setRequestMethod("GET");
            
            // Add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            
            // Prints response code
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            // Retrives response
            if(responseCode != 404){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // Return response
                return response.toString();
            }
            return "Page not Found";
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // HTTP POST request
    public String sendPost(String url, String postData) throws Exception {
        // Create HTTP client
        HttpClient httpclient = new DefaultHttpClient();

        // Creates POST request by setting headers and body
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(postData));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json; charset=UTF-8");

        // Sends request and waits for response
        HttpResponse response = httpclient.execute(httpPost);
        
        // Returns response
        return EntityUtils.toString(response.getEntity());
    }

}