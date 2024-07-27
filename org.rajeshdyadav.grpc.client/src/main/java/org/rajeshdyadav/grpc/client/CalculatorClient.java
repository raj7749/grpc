package org.rajeshdyadav.grpc.client;

import org.rajeshdyadav.gprc.calculator.CalculatorProto;
import org.rajeshdyadav.gprc.calculator.CalculatorServiceGrpc;

public class CalculatorClient {
    private final CalculatorServiceGrpc.CalculatorServiceBlockingStub blockingStub;

    public CalculatorClient() {
        blockingStub = CalculatorServiceGrpc.newBlockingStub(MyGrpcClient.getChannel());
    }

    public static void main(String[] args) throws Exception {
        CalculatorClient client = new CalculatorClient();
        client.addRequest(100, 200);
        client.addRequest(300, 400);

        client.subRequest(200, 100);
        client.subRequest(300, 200);
    }

    void addRequest(int a, int b) {
        CalculatorProto.AddRequest request = CalculatorProto.AddRequest.newBuilder()
                .setNum1(a)
                .setNum2(b)
                .build();

        CalculatorProto.AddResponse response = blockingStub.add(request);
        System.out.printf("%d + %d : %d%n", a, b, response.getResult());
    }

    void subRequest(int a, int b) {
        CalculatorProto.SubtractRequest request = CalculatorProto.SubtractRequest.newBuilder()
                .setNum1(a)
                .setNum2(b)
                .build();

        CalculatorProto.SubtractResponse response = blockingStub.subtract(request);
        System.out.printf("%d - %d : %d%n", a, b, response.getResult());
    }
}