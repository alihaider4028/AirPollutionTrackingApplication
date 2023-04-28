package com.AirpollutionTracking.utils;

import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.CheckAirQualityPredictionResponse;
import com.AirPollutionTrackingApp.commons.components;
import com.AirPollutionTrackingApp.commons.status;
import com.AirPollutionTrackingApp.pollutionIdentifier.PollResponse;
import com.AirPollutionTrackingApp.pollutionIdentifier.PollutionRequest;
import com.AirPollutionTrackingApp.pollutionIdentifier.PollutionResponse;
import com.AirpollutionTracking.controller.AirQuality;
import io.grpc.stub.StreamObserver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class pollutionSourceIdentifierStreamingRequest implements StreamObserver<PollutionRequest> {
    public pollutionSourceIdentifierStreamingRequest(StreamObserver<PollutionResponse> streamObserver) {
        this.streamObserver = streamObserver;
    }

    private StreamObserver<PollutionResponse> streamObserver;
    AirQuality airQuality=new AirQuality();
    AirPollutionSources source= new AirPollutionSources();
    PollutionResponse.Builder builder=PollutionResponse.newBuilder();
    PollResponse.Builder resp= PollResponse.newBuilder();
    UnixToTimeConverter converter= new UnixToTimeConverter();
//sample 2022-08-12 00:00:00


    @Override
    public void onNext(PollutionRequest pollutionRequest) {
        airQuality.setLat(pollutionRequest.getLat());
        airQuality.setLon(pollutionRequest.getLong());
     try {
         JSONArray jsonArray = airQuality.getHistoricalAnalysis().getJSONArray("list");
         for (int i = 0; i < jsonArray.length(); i++) {
             JSONObject jsonObject = jsonArray.getJSONObject(i);
             System.out.println(converter.convertUnixToTime(jsonObject.getLong("dt")));

             if (converter.convertUnixToTime(jsonObject.getLong("dt")).equals(pollutionRequest.getTime())){

// Set values for each field
                 resp.setSources(source.pollutant1).setValue(jsonObject.getJSONObject("components").getDouble("co") + "")
                         .setMessage(source.getAirPollutionSource(source.pollutant1));
                 builder.addResp(resp).build();
                 resp.setSources(source.pollutant2).setValue(jsonObject.getJSONObject("components").getDouble("no") + "")
                         .setMessage(source.getAirPollutionSource(source.pollutant2));
                 builder.addResp(resp).build();
                 resp.setSources(source.pollutant3).setValue(jsonObject.getJSONObject("components").getDouble("no2") + "")
                         .setMessage(source.getAirPollutionSource(source.pollutant3));
                 builder.addResp(resp).build();
                 resp.setSources(source.pollutant4).setValue(jsonObject.getJSONObject("components").getDouble("o3") + "")
                         .setMessage(source.getAirPollutionSource(source.pollutant4));
                 builder.addResp(resp).build();
                 resp.setSources(source.pollutant5).setValue(jsonObject.getJSONObject("components").getDouble("so2") + "")
                         .setMessage(source.getAirPollutionSource(source.pollutant5));
                 builder.addResp(resp).build();
                 resp.setSources(source.pollutant6).setValue(jsonObject.getJSONObject("components").getDouble("pm2_5") + "")
                         .setMessage(source.getAirPollutionSource(source.pollutant6));
                 builder.addResp(resp).build();

             }
             }
         } catch(
                 JSONException e){
             throw new RuntimeException(e);
         }


}

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

        streamObserver.onNext(builder.build());
        streamObserver.onCompleted();
    }
}
