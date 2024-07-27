package org.rajeshdyadav.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MyGrpcClient {

    private static volatile ManagedChannel channel;

    private MyGrpcClient() {}

    public static ManagedChannel getChannel() {
        if (channel == null) {
            synchronized (MyGrpcClient.class) {
                if (channel == null) {
                    channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                            .usePlaintext()
                            .build();
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        if (channel != null) {
                            channel.shutdown();
                        }
                    }));
                }
            }
        }
        return channel;
    }

}
