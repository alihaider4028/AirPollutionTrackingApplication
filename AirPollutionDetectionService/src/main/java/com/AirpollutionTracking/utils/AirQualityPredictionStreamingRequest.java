package com.AirpollutionTracking.utils;

import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.CheckAirQualityPredictionResponse;
import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.ResponseDTO;
import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.checkRealTimeAirQualityResponse;
import com.AirPollutionTrackingApp.commons.Request;
import com.AirPollutionTrackingApp.commons.components;
import com.AirPollutionTrackingApp.commons.status;
import com.AirpollutionTracking.controller.AirQuality;
import io.grpc.stub.StreamObserver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class AirQualityPredictionStreamingRequest implements StreamObserver<Request> {
   private StreamObserver<CheckAirQualityPredictionResponse> streamObserver;
   AirQuality  airQuality=new AirQuality();
   private CheckAirQualityPredictionResponse.Builder checkAirQualityPredictionResponse =CheckAirQualityPredictionResponse.newBuilder();
   private ResponseDTO.Builder builder=ResponseDTO.newBuilder();

 public AirQualityPredictionStreamingRequest(StreamObserver<CheckAirQualityPredictionResponse> streamObserver) {
  this.streamObserver = streamObserver;
 }

 @Override
    public void onNext(Request request) {
  airQuality.setLat((request.getLat()));
  airQuality.setLon((request.getLon()));
  try {
   JSONArray jsonArray = airQuality.getAirQualityPrediction().getJSONArray("list");
   for (int i=0;i< jsonArray.length();i++){
       JSONObject jsonObject =jsonArray.getJSONObject(i);

   components.Builder componentsBuilder = components.newBuilder();

// Set values for each field
   componentsBuilder.setCo(jsonObject.getJSONObject("components").getDouble("co"));
   componentsBuilder.setNo(jsonObject.getJSONObject("components").getDouble("no"));
   componentsBuilder.setNo2(jsonObject.getJSONObject("components").getDouble("no2"));
   componentsBuilder.setO3(jsonObject.getJSONObject("components").getDouble("o3"));
   componentsBuilder.setSo2(jsonObject.getJSONObject("components").getDouble("so2"));
   componentsBuilder.setPm25(jsonObject.getJSONObject("components").getDouble("pm2_5"));
   componentsBuilder.setPm10(jsonObject.getJSONObject("components").getDouble("pm10"));
   componentsBuilder.setNh3(jsonObject.getJSONObject("components").getDouble("nh3"));
   double aqi = jsonObject.getJSONObject("main").getDouble("aqi");
   status stat;
   if (aqi == 0) {
    stat = status.Good;
   } else if (aqi == 1) {
    stat = status.Fair;
   } else if (aqi == 2) {
    stat = status.Moderate;
   } else if (aqi == 3) {
    stat = status.Poor;
   } else if (aqi == 4) {
    stat = status.Very_Poor;
   } else {
    // Default case when AQI value does not match any enum value
    // You can handle this case as per your requirement
    stat = null; // or set to a default enum value
   }


   builder.setAqi(aqi).setComponents(componentsBuilder.build()).setStat(stat).setDescription("api called successfully").build();

   checkAirQualityPredictionResponse.addResp(builder.build()).build();}
  } catch (
 JSONException e) {
  throw new RuntimeException(e);}


 }




    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
streamObserver.onNext(checkAirQualityPredictionResponse.build());
streamObserver.onCompleted();
    }
}
