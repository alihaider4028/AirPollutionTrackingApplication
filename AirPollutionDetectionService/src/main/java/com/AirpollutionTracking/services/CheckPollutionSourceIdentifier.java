package com.AirpollutionTracking.services;


import com.AirPollutionTrackingApp.pollutionIdentifier.PollutionRequest;
import com.AirPollutionTrackingApp.pollutionIdentifier.PollutionResponse;
import com.AirPollutionTrackingApp.pollutionIdentifier.PollutionSourceIdentificationGrpc;
import com.AirpollutionTracking.utils.pollutionSourceIdentifierStreamingRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CheckPollutionSourceIdentifier extends PollutionSourceIdentificationGrpc.PollutionSourceIdentificationImplBase {
    @Override
    public StreamObserver<PollutionRequest> identifySourceOfPollution(StreamObserver<PollutionResponse> responseObserver) {
        return new pollutionSourceIdentifierStreamingRequest(responseObserver);
    }
}
