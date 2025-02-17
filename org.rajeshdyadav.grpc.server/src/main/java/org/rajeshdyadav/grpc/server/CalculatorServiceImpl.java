package org.rajeshdyadav.grpc.server;

import io.grpc.stub.StreamObserver;
import org.rajeshdyadav.grpc.stubs.calculator.CalculatorProto;
import org.rajeshdyadav.grpc.stubs.calculator.CalculatorServiceGrpc;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void add(CalculatorProto.AddRequest request, StreamObserver<CalculatorProto.AddResponse> responseObserver) {
        int result = request.getNum1() + request.getNum2();
        CalculatorProto.AddResponse response = CalculatorProto.AddResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void subtract(CalculatorProto.SubtractRequest request, StreamObserver<CalculatorProto.SubtractResponse> responseObserver) {
        int result = request.getNum1() - request.getNum2();
        CalculatorProto.SubtractResponse response = CalculatorProto.SubtractResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}