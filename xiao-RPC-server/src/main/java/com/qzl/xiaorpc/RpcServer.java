package com.qzl.xiaorpc;

import com.qzl.xiaorpc.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream response) {
            Response resp = new Response();

            try {
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(inBytes, Request.class);
                log.info("get req: {}", request);

                ServiceInstance serviceInstance = serviceManager.lookup(request);

                Object res = serviceInvoker.invoke(serviceInstance, request);
                resp.setData(res);
            } catch (IOException e) {
                e.printStackTrace();
                resp.setCode(1);
                resp.setMessage("RPC error " + e.getClass().getName() + "\t" + e.getMessage());
            } finally {
                try {
                    IOUtils.write(encoder.encode(resp), response);
                    log.info("resp succeed");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("resp error");
                }
            }
        }
    };

    public RpcServer(){
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        net.init(config.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    public void start() {
        log.info("server will start on port: {}", config.getPort());
        this.net.start();
    }

    public void stop() {
        this.net.close();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }
}
