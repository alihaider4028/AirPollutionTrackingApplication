package com.bwap.weatherapp.AirPollutionDetectionApp.controller;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AirQuality {
    private OkHttpClient client;
    private Response response;
    private String lat;
    private String lon;
    private String APIkey = "put your api";

    //Getting Data from OpenWeather API
    public JSONObject getHistoricalAnalysis(){
        client = new OkHttpClient();  //using OKHTTP dependency . You have to add this mannually form OKHTTP website
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/air_pollution?lat="+lat+"&lon="+lon+"&appid="+APIkey)
                .build();

        try {
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //Getting required data from Weather JSON API
    //JSON Objects and JSON Arrays


    public JSONArray returnHistoricalAnalysisArray() throws JSONException {
        JSONArray list = getHistoricalAnalysis().getJSONArray("list");
        return list;
    }

    public JSONObject returnMainObject(int i) throws JSONException {
        JSONObject mainObject = returnHistoricalAnalysisArray().getJSONObject(i);
        return mainObject;
    }

    public JSONObject returnMain(int i) throws JSONException {
        JSONObject main = returnHistoricalAnalysisArray().getJSONObject(i).getJSONObject("main");
        return main;
    }

    public JSONObject returnAqi(int i) throws JSONException {
        JSONObject aqi = returnMain(i).getJSONObject("aqi");
        return aqi;
        }


    public JSONObject returnComponent(int i) throws JSONException{
        JSONObject components = returnMainObject(i).getJSONObject("components");
        return components;

        }
    public JSONObject returnDate(int i) throws JSONException{
        JSONObject dt = returnMainObject(i).getJSONObject("dt");
        return dt;

    }


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
