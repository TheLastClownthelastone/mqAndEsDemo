package com.pt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pt.bean.MessageBean;
import com.pt.util.ConnUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.jms.*;
import java.util.Map;

/**
 * @author nate-pt
 * @date 2021/7/2 9:37
 * @Since 1.8
 * @Description 提供接口 将数据作为消息传到mq中
 */
public class Apis {
    /**
     * 队列名称
     */
    private static  final String QUEUE = "es-mq-queue";

    private static final String INDEXANDTYPENAME = "mqtoes";


    /**
     * 发送消息
     * @param messageBean
     * @throws Exception
     */
    public static void sendMessage(MessageBean messageBean) throws Exception {
        Session session = ConnUtil.getSession();

        Queue queue = session.createQueue(QUEUE);


        MessageProducer producer = session.createProducer(queue);

        String s = JSON.toJSONString(messageBean);

        TextMessage textMessage = session.createTextMessage(s);
        producer.send(textMessage);
    }

    /**
     * 接收到消息进行处理到es中
     */
    public static void consumer() throws Exception {
        Session session = ConnUtil.getSession();

        Queue queue = session.createQueue(QUEUE);

        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(message -> {
            try {
                // 消息进行ack确认，告诉mq服务器接收到消息
                message.acknowledge();

                String text = ((TextMessage) message).getText();

                MessageBean messageBean = JSONObject.parseObject(text, MessageBean.class);
                messageToEs(messageBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.in.read();
    }

    /**
     * 消息传输到es中
     * @param messageBean
     */
    public static void messageToEs(MessageBean messageBean) throws Exception {
        System.out.println("数据入es中");
        TransportClient client = ConnUtil.getClient();

//        XContentBuilder source = XContentFactory.jsonBuilder().startObject().
//                field("id",messageBean.getId())
//                .field("age",messageBean.getAge())
//                .field("content",messageBean.getContent()).endObject();

        Map<String,Object> map = JSON.parseObject(JSON.toJSONString(messageBean), Map.class);

        XContentBuilder source = XContentFactory.smileBuilder().map(map);

        IndexResponse indexResponse = client.prepareIndex(INDEXANDTYPENAME, INDEXANDTYPENAME, "3").setSource(source).get();

        System.out.println(indexResponse);
    }




}
