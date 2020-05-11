package com.yueyitong.emqxdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangxin
 * @date 2020/5/3
 */
@Component
@Slf4j
public class PushCallBack implements MqttCallback {

    @Resource
    private MqttClient mqttClient;

    @Override
    public void connectionLost(Throwable throwable) {
        log.info("连接断开，可以做重连");
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        log.info("接收消息主题:" + topic);
        log.info("接收消息Qos:" + message.getQos());
        log.info("接收消息内容:" + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete---------" + token.isComplete());
    }
}