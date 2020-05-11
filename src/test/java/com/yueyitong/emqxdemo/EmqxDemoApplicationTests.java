package com.yueyitong.emqxdemo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class EmqxDemoApplicationTests {

    @Resource
    private MqttClient mqttClient;

    @Test
    public void publish() throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(2);
        mqttMessage.setPayload("hello world".getBytes());
        mqttMessage.setRetained(true);
        mqttClient.publish("testtopic/1", mqttMessage);
    }
}
