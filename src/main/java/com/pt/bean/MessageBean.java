package com.pt.bean;

import java.io.Serializable;

/**
 * @author nate-pt
 * @date 2021/7/2 9:42
 * @Since 1.8
 * @Description 消息bean
 */
public class MessageBean implements Serializable {

    private String id;

    private int age;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
