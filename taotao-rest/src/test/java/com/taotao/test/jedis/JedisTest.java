package com.taotao.test.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisTest {

    // @Test
    // public void fun1() {
    // ApplicationContext ac = new ClassPathXmlApplicationContext(
    // "classpath:spring/applicationContext-*.xml");
    // JedisCluster JedisCluster = (JedisCluster) ac.getBean("redisClient");
    // String string = JedisCluster.get("key1");
    // System.out.println(string);
    // JedisCluster.close();
    // }

    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("key1");
        System.out.println(string);
        jedisCluster.close();
    }

    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.2.11", 7001));
        nodes.add(new HostAndPort("192.168.2.11", 7002));
        nodes.add(new HostAndPort("192.168.2.11", 7003));
        nodes.add(new HostAndPort("192.168.2.11", 7004));
        nodes.add(new HostAndPort("192.168.2.11", 7005));
        nodes.add(new HostAndPort("192.168.2.11", 7006));

        JedisCluster cluster = new JedisCluster(nodes);

        cluster.set("key22", "1000");
        String string = cluster.get("key22");
        System.out.println(string);

        cluster.close();
    }

}
