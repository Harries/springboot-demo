/*
 * Copyright (c) 2016-2023 The gRPC-Spring Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.et.grpc.client;

import com.et.grpc.api.protobuf.lib.HelloReply;
import com.et.grpc.api.protobuf.lib.HelloRequest;
import com.et.grpc.api.protobuf.lib.MyServiceGrpc;
import org.springframework.stereotype.Service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;


/**
 * @author Michael (yidongnan@gmail.com)
 * @since 2016/11/8
 */
@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    // @GrpcClient("cloud-grpc-server")
    private MyServiceGrpc.MyServiceBlockingStub myServiceStub;

    public String sendMessage(final String name) {
        try {
            final HelloReply response = this.myServiceStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

}
