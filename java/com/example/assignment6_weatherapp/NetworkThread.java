package com.example.assignment6_weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkThread extends Thread{

    @Override
    public void run(){
        try {
            Log.i("Thread", "Underway, attempting to retrieve API Data");
            String urlL = "https://api.openweathermap.org/data/2.5/onecall?lat=30.267153&lon=-97.743057&exclude=minutely&units=imperial&appid=2792957f89377b17cd33fc9087f940e5";

            URL url = new URL(urlL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            Log.i("Thread","Trying to connect");


            InputStream input = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            StringBuilder jsonText = new StringBuilder();
            String temp = "";
            while((temp = br.readLine()) != null){
                jsonText.append(temp);
            }


            Log.i("API Connection", "We have read from the api");
            //Log.i("API",jsonText.toString());

            JSONObject jsonObject = new JSONObject(jsonText.toString());
            MainActivity.jsonObject = jsonObject;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        //JSONObject infoObj = new JSONObject(connect);
    }
}
