package com.AirpollutionTracking.services;

import com.AirpollutionTracking.utils.AirPollutionSources;
import healthadvice.HealthAdviceServiceGrpc;
import healthadvice.Healthadvice;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HealthAdviceServiceGrpcImpl extends HealthAdviceServiceGrpc.HealthAdviceServiceImplBase {
    AirPollutionSources source= new AirPollutionSources();

    @Override
    public void getHealthAdvice(Healthadvice.HealthAdviceRequest request, StreamObserver<Healthadvice.HealthAdviceResponse> responseObserver) {
        String pollutant = request.getPollutant();
        String source = this.source.getAirPollutionSource(request.getPollutant().toUpperCase());
        String advice = this.source.getHealthAdvice(request.getPollutant().toUpperCase());

        Healthadvice.HealthAdviceResponse response = Healthadvice.HealthAdviceResponse.newBuilder()
                .setSource(source)
                .setAdvice(advice)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
