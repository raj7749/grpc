package org.rajeshdyadav.grpc.client;

import org.rajeshdyadav.grpc.stubs.helloworld.HelloWorldProto;
import org.rajeshdyadav.grpc.stubs.helloworld.HelloWorldServiceGrpc;

import java.util.Arrays;

public class HelloWorldClient {
    private final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub blockingStub;

    public HelloWorldClient() {
        this.blockingStub = HelloWorldServiceGrpc.newBlockingStub(MyGrpcClient.getChannel());
    }

    public void greet(String name) {
        HelloWorldProto.HelloRequest request = HelloWorldProto.HelloRequest.newBuilder().setName(name).build();
        HelloWorldProto.HelloReply response = blockingStub.sayHello(request);
        System.out.println("Greeting: " + response.getMessage());
    }

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient();
        Arrays.asList("World","Rajesh","Test1","Test2").forEach(client::greet);
    }

}
