package org.dreilaenderhack.app;

//Copyright (c) Microsoft Corporation.
//All rights reserved.
//
//This code is licensed under the MIT License.
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files(the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions :
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;








import javax.net.ssl.HttpsURLConnection;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.aad.adal4j.UserAssertion;

public class PublicClient {

 private final String AUTHORITY;
 private final String CLIENT_ID;
 private final String CLIENT_SECRET;
 private final static String RESOURCE = "https://graph.microsoft.com";

 //https://blogs.oracle.com/saaspaas/oauth-user-assertion-flow-from-non-oracle-cloud  (HttpClient with Access token example)
 //https://github.com/microsoft/azure-spring-boot/blob/master/azure-spring-boot/src/main/java/com/microsoft/azure/spring/autoconfigure/aad/AzureADGraphClient.java 
 public PublicClient() throws  IOException 
 {
	 try (InputStream input = new FileInputStream("config.properties")) {

         Properties prop = new Properties();

         // load a properties file
         prop.load(input);

         // get the property value and print it out
         AUTHORITY = prop.getProperty("AUTHORITY");
         CLIENT_ID = prop.getProperty("CLIENT_ID");
         CLIENT_SECRET = prop.getProperty("CLIENT_SECRET");

     }
 }

 public AuthenticationResult getAccessTokenFromUserCredentials(
         String jwtToken) throws Exception {
     AuthenticationContext context = null;
     AuthenticationResult result = null;
     ExecutorService service = null;
     UserAssertion assertion = null;
     try {
         service = Executors.newFixedThreadPool(1);
         context = new AuthenticationContext(AUTHORITY, false, service);
         assertion = new UserAssertion(jwtToken);
         Future<AuthenticationResult> resp = context.acquireToken(RESOURCE, assertion , new ClientCredential(CLIENT_ID, CLIENT_SECRET), null);
         return resp.get();
     } finally {
         service.shutdown();
     }

 
 }
 
 public String getUserInfoFromGraph(String accessToken) throws IOException {

	 //https://devsbb.sharepoint.com
     URL url = new URL("https://graph.microsoft.com/v1.0/me");
     HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

     conn.setRequestMethod("GET");
     conn.setRequestProperty("Authorization", "Bearer " + accessToken);
     conn.setRequestProperty("Accept","application/json");

     int httpResponseCode = conn.getResponseCode();
     if(httpResponseCode == 200) {
         BufferedReader in = null;
         StringBuilder response;
         try{
             in = new BufferedReader(
                     new InputStreamReader(conn.getInputStream()));
             String inputLine;
             response = new StringBuilder();
             while ((inputLine = in.readLine()) != null) {
                 response.append(inputLine);
             }
         } finally {
             in.close();
         }
         return response.toString();
     } else {
         return String.format("Connection returned HTTP code: %s with message: %s",
                 httpResponseCode, conn.getResponseMessage());
     }
 }
 
}