package com.AirpollutionTracking.services;

import com.AirPollutionTrackingApp.CheckAirQualityPredictionServices.CheckAirQualityPrediction;
import com.AirPollutionTrackingApp.CheckAirQualityPredictionServices.CheckAirQualityPredictionServiceGrpc;
import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.CheckAirQualityPredictionResponse;
import com.AirPollutionTrackingApp.CheckRealTimeAirQuality.checkRealTimeAirQualityResponse;
import com.AirPollutionTrackingApp.commons.Request;
import com.AirpollutionTracking.utils.AirQualityPredictionStreamingRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CheckAirQualityPredictionService extends CheckAirQualityPredictionServiceGrpc.CheckAirQualityPredictionServiceImplBase {
    @Override
    public StreamObserver<Request> checkAirQualityPrediction(StreamObserver<CheckAirQualityPredictionResponse> responseObserver) {
        return new AirQualityPredictionStreamingRequest(responseObserver);
    }
}
