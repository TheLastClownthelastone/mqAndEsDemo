package com.pt;

import com.pt.bean.MessageBean;
import org.junit.Test;

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


    public MessageBean getBean(){
        MessageBean messageBean = new MessageBean();
        messageBean.setId("1");
        messageBean.setAge(10);
        messageBean.setContent("mqToEs");

        return messageBean;
    }
}
