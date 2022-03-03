package com.qzl.xiaorpc;

import com.qzl.xiaorpc.utils.ReflectionUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.spi.AbstractResourceBundleProvider;

@Slf4j
@Data
public class RandomTransportSelector implements TransportSelector {
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        clients = new ArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(1, count);
        for (Peer peer : peers) {
            if (count-- <= 0) {
                break;
            }
            TransportClient client = ReflectionUtils.newInstance(clazz);
            client.connect(peer);
            clients.add(client);
            log.info("connected {}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        if (client == null) {
            log.info("put a null into client pool");
            return;
        }
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
