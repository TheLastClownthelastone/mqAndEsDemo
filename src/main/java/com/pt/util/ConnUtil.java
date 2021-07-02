package com.pt.util;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.TransportFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import java.net.InetAddress;

/**
 * @author nate-pt
 * @date 2021/7/2 9:45
 * @Since 1.8
 * @Description 创建es链接和mq链接的bean
 */
public class ConnUtil {

    /**
     * 获取es的client
     * @return
     */
    public static TransportClient getClient() throws Exception{

        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();

        TransportAddress address = new TransportAddress(InetAddress.getByName("127.0.0.1"),9300);

        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(address);

        return client;

    }

    /**
     * 获取activemq的session
     * @return
     */
    public static Session getSession() throws Exception{
        // 通过工程创建链接
        ConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin","tcp://127.0.0.1:61616");

        Connection connection = factory.createConnection();

        connection.start();

        //通过消息ack方式确认消息时候收到
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

        return session;
    }


}
