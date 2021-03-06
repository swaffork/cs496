package com.example.myfirstapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

public class WeatherHttpClient {

    private static String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_KEY = "978c1079e8f399666c17af2e9c092eaf";
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    public String getWeatherData(String location) {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        location = "London";

        try {
            // Make the request:
            String urlString = API_URL + location + "&APPID=" + API_KEY;
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // Read the response:
            StringBuffer buffer = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null ) {
                buffer.append(line + "\r\n");
            }
            is.close();
            connection.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        // Clean up:
        finally {
            try {
                is.close();
            }
            catch(Throwable t) {}
            try {
                connection.disconnect();
            }
            catch(Throwable t) {}
        }
        return null;
    }

    public byte[] getImage(String imageCode) {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            // Make the request:
            url = new URL(IMG_URL + imageCode);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // Read the response:
            is = connection.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1 ) {
                baos.write(buffer);
            }
            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        // Clean up:
        finally {
            try {
                is.close();
            }
            catch(Throwable t) {}
            try {
                connection.disconnect();
            }
            catch(Throwable t) {}
        }
        return null;
    }
}
