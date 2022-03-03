package com.qzl.xiaorpc;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int count = 1;
    private List<Peer> servers = new ArrayList<>() {{
        add(new Peer("127.0.0.1", 4399));
    }};
}
