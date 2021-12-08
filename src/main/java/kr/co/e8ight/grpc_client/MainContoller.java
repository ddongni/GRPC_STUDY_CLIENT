package kr.co.e8ight.grpc_client;

import com.example.grpc_test.proto.HelloRequest;
import com.example.grpc_test.proto.HelloResponse;
import com.example.grpc_test.proto.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainContoller {

    @GetMapping(value = "/api")
    public Status.Code getCode(){
        try {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

            HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);

            HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                    .setFirstName("TEST")
                    .setLastName("gRPC")
                    .build());

            channel.shutdown();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(Status.Code.INVALID_ARGUMENT); //400 에러
            return Status.Code.INVALID_ARGUMENT;
        }
        System.out.println(" 요청 완료 "+ Status.Code.OK);
        return Status.Code.OK;
    }
}
