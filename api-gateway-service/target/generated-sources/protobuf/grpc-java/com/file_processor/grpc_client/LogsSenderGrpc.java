package com.file_processor.grpc_client;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: sender_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogsSenderGrpc {

  private LogsSenderGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.file_processor.grpc_client.LogsSender";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.file_processor.grpc_client.SenderService.Log,
      com.google.protobuf.Empty> getSendLogsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendLogs",
      requestType = com.file_processor.grpc_client.SenderService.Log.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.file_processor.grpc_client.SenderService.Log,
      com.google.protobuf.Empty> getSendLogsMethod() {
    io.grpc.MethodDescriptor<com.file_processor.grpc_client.SenderService.Log, com.google.protobuf.Empty> getSendLogsMethod;
    if ((getSendLogsMethod = LogsSenderGrpc.getSendLogsMethod) == null) {
      synchronized (LogsSenderGrpc.class) {
        if ((getSendLogsMethod = LogsSenderGrpc.getSendLogsMethod) == null) {
          LogsSenderGrpc.getSendLogsMethod = getSendLogsMethod =
              io.grpc.MethodDescriptor.<com.file_processor.grpc_client.SenderService.Log, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendLogs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.file_processor.grpc_client.SenderService.Log.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new LogsSenderMethodDescriptorSupplier("SendLogs"))
              .build();
        }
      }
    }
    return getSendLogsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogsSenderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogsSenderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogsSenderStub>() {
        @java.lang.Override
        public LogsSenderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogsSenderStub(channel, callOptions);
        }
      };
    return LogsSenderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogsSenderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogsSenderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogsSenderBlockingStub>() {
        @java.lang.Override
        public LogsSenderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogsSenderBlockingStub(channel, callOptions);
        }
      };
    return LogsSenderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogsSenderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogsSenderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogsSenderFutureStub>() {
        @java.lang.Override
        public LogsSenderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogsSenderFutureStub(channel, callOptions);
        }
      };
    return LogsSenderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Client streams Log messages; server replies with Empty once completed
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.file_processor.grpc_client.SenderService.Log> sendLogs(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSendLogsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LogsSender.
   */
  public static abstract class LogsSenderImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogsSenderGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogsSender.
   */
  public static final class LogsSenderStub
      extends io.grpc.stub.AbstractAsyncStub<LogsSenderStub> {
    private LogsSenderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogsSenderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogsSenderStub(channel, callOptions);
    }

    /**
     * <pre>
     * Client streams Log messages; server replies with Empty once completed
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.file_processor.grpc_client.SenderService.Log> sendLogs(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSendLogsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogsSender.
   */
  public static final class LogsSenderBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogsSenderBlockingStub> {
    private LogsSenderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogsSenderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogsSenderBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogsSender.
   */
  public static final class LogsSenderFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogsSenderFutureStub> {
    private LogsSenderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogsSenderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogsSenderFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SEND_LOGS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_LOGS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendLogs(
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendLogsMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.file_processor.grpc_client.SenderService.Log,
              com.google.protobuf.Empty>(
                service, METHODID_SEND_LOGS)))
        .build();
  }

  private static abstract class LogsSenderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogsSenderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.file_processor.grpc_client.SenderService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogsSender");
    }
  }

  private static final class LogsSenderFileDescriptorSupplier
      extends LogsSenderBaseDescriptorSupplier {
    LogsSenderFileDescriptorSupplier() {}
  }

  private static final class LogsSenderMethodDescriptorSupplier
      extends LogsSenderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogsSenderMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LogsSenderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogsSenderFileDescriptorSupplier())
              .addMethod(getSendLogsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
