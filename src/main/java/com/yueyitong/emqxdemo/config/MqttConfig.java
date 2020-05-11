package com.yueyitong.emqxdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author yangxin
 * @date 2020/5/3
 */
@Configuration
@Slf4j
public class MqttConfig {

    @Resource
    private EmqxConfig emqxConfig;

    @Value("${chat.room.server.topic}")
    private String chatRoomServerTopic;

    @Value("${will.topic}")
    private String willTopic;

    @Bean(destroyMethod = "close")
    public MqttClient mqttClient(PushCallBack pushCallBack) {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient client = new MqttClient(emqxConfig.getBroker(), emqxConfig.getClientId(), persistence);

            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(emqxConfig.getUsername());
            connOpts.setPassword(emqxConfig.getPassword().toCharArray());
            // 保留会话
            connOpts.setCleanSession(emqxConfig.getCleanSession());

            // 设置回调
            client.setCallback(pushCallBack);

            // 建立连接
            log.info("Connecting to broker: {}", emqxConfig.getBroker());
            client.connect(connOpts);

            log.info("Connected");

            client.subscribe(chatRoomServerTopic, 2);
            client.subscribe(willTopic, 2);
            return client;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("初始化MQTT客户端失败！", e);
        }
    }

}