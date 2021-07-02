package com.pt;

import com.alibaba.fastjson.JSON;
import com.pt.bean.MessageBean;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author nate-pt
 * @date 2021/7/2 10:33
 * @Since 1.8
 * @Description
 */
public class Demo {

    /**
     * 消息发送
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        Apis.sendMessage(getBean());
    }

    /**
     * 消息消费
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Apis.consumer();
    }


    public static MessageBean getBean(){
        MessageBean messageBean = new MessageBean();
        messageBean.setId("1");
        messageBean.setAge(10);
        messageBean.setContent("mqToEs");

        return messageBean;
    }

    public static void main(String[] args) throws IOException {
        MessageBean bean = getBean();

        Map<String,Object> map = JSON.parseObject(JSON.toJSONString(bean), Map.class);
        System.out.println(map);

        //XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject().map(map).endObject();

        XContentBuilder xContentBuilder = XContentFactory.smileBuilder().map(map);

        System.out.println(xContentBuilder);

    }
}
