/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetandPost {
  private GetandPost()
  {

  }

  public static void myGetRequest() throws IOException {
    final URL urlForGetRequest = new URL("http://localhost:8080/api/queries/stageone/get");
    String readLine = null;
    final HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
    conection.setRequestMethod("GET");
    final int responseCode = conection.getResponseCode();
    final StringBuffer response = new StringBuffer();

    if (responseCode == HttpURLConnection.HTTP_OK) {
      final BufferedReader in = new BufferedReader(
          new InputStreamReader(conection.getInputStream()));

      while ((readLine = in .readLine()) != null) {
        response.append(readLine);
      } in .close();
      // print result
      System.out.println("Result " + response.toString());

    } else {
      System.out.println("GET NOT WORKED");
    }

  }




  public static void postRequest(String POST_PARAMS) throws IOException {

    final URL obj = new URL("http://localhost:8080/api/queries/stageone/post");
    final HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
    postConnection.setRequestMethod("POST");
    postConnection.setDoOutput(true);
    final OutputStream os = postConnection.getOutputStream();
    os.write(POST_PARAMS.getBytes());
    os.flush();
    os.close();
    final int responseCode = postConnection.getResponseCode();
    System.out.println("POST Response Code :  " + responseCode);
    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
    if (responseCode == HttpURLConnection.HTTP_OK) { //success
      final BufferedReader in = new BufferedReader(new InputStreamReader(
          postConnection.getInputStream()));
      String inputLine;
      final StringBuffer response = new StringBuffer();
      while ((inputLine = in .readLine()) != null) {
        response.append(inputLine);
      } in .close();
      // print result
      System.out.println(response.toString());
    } else {
      System.out.println("POST NOT WORKED");
    }
  }

}
