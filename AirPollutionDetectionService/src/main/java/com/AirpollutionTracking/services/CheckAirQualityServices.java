package com.AirpollutionTracking.services;

import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.checkRealTimeAirQualityResponse;
import com.AirPollutionTrackingApp.CheckRealTimeAirQualityServices.CheckRealTimeAirQualityServiceGrpc;
import com.AirPollutionTrackingApp.commons.Request;
import com.AirPollutionTrackingApp.commons.components;
import com.AirPollutionTrackingApp.commons.status;

import com.AirpollutionTracking.controller.AirQuality;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GrpcService
public class CheckAirQualityServices extends CheckRealTimeAirQualityServiceGrpc.CheckRealTimeAirQualityServiceImplBase {
    @Autowired
    AirQuality airQuality;

    @Override
    public void checkRealTimeAirQuality(Request request, StreamObserver<checkRealTimeAirQualityResponse> responseObserver) {
        airQuality.setLat(String.valueOf(request.getLat()));
        airQuality.setLon(String.valueOf(request.getLon()));
        try {
            JSONObject jsonObject = airQuality.getRealtimeData().getJSONObject("list");
            components.Builder componentsBuilder = components.newBuilder();
            checkRealTimeAirQualityResponse.Builder builder = checkRealTimeAirQualityResponse.newBuilder();

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


            builder
                    .setAqi(aqi).setComponents(componentsBuilder.build()).setStat(stat).setDescription("api called successfully").build();
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
}
