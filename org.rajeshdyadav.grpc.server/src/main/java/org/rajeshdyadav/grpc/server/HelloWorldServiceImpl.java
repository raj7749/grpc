package org.rajeshdyadav.grpc.server;

import io.grpc.stub.StreamObserver;
import org.rajeshdyadav.gprc.helloworld.HelloWorldProto;
import org.rajeshdyadav.gprc.helloworld.HelloWorldServiceGrpc;

public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void sayHello(HelloWorldProto.HelloRequest req, StreamObserver<HelloWorldProto.HelloReply> responseObserver) {
        HelloWorldProto.HelloReply reply = HelloWorldProto.HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
