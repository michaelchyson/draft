package net.chyson.codis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * michael.chyson
 * 5/8/2018
 */
public class CodisPool {
    private static CodisConfig config;

    public CodisPool(CodisConfig config) {
        this.config = config;
    }

    public JedisPool getPool() {
        List<HostAndPort> hostAndPorts = getHostAndPorts();
        Random random = new Random();
        int i = random.nextInt(hostAndPorts.size());
        HostAndPort hostAndPort = hostAndPorts.get(i);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxWaitMillis(60000L);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(3);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        return new JedisPool(jedisPoolConfig, hostAndPort.getHost(), hostAndPort.getPort());

    }

    public List<HostAndPort> getHostAndPorts() {
        String[] addresses = config.getAddresses();
        ArrayList<HostAndPort> hostAndPorts = new ArrayList<>(addresses.length);
        for (String s : addresses) {
            String[] split = s.split(":");
            String host = split[0];
            int port = Integer.valueOf(split[1]);
            hostAndPorts.add(new HostAndPort(host, port));
        }
        return hostAndPorts;
    }
}
