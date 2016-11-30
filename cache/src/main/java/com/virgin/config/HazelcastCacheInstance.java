package com.virgin.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastCacheInstance {

    public static HazelcastInstance _instance = null;

    public static HazelcastInstance getConfig(String serverUrl) {
        if (_instance == null) {
            synchronized (HazelcastCacheInstance.class) {
                ClientConfig clientConfig = new ClientConfig();
                clientConfig.addAddress("127.0.0.1:5701");
                HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
                _instance = client;
                return client;
                /*ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
                clientNetworkConfig.addAddress(serverUrl);
                ClientConfig clientConfig = new ClientConfig();
                clientConfig.setNetworkConfig(clientNetworkConfig);
                return HazelcastClient.newHazelcastClient(clientConfig);*/
            }
        } else {
            return _instance;
        }
    }
}
