package com.yueyitong.emqxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.yueyitong.emqxdemo.config.EmqxConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author yangxin
 * @date 2020/5/3
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private HttpServletRequest request;

    @Resource
    private MqttClient mqttClient;

    @Value("${chat.room.client.topic}")
    private String clientTopic;

    @Resource
    private EmqxConfig emqxConfig;

    @GetMapping("/")
    public String index(Long roomId, String username) {
        request.setAttribute("roomId", roomId);
        request.setAttribute("username", username);
        request.setAttribute("clientId", "chatRoom_" + roomId + "_" + username);
        request.setAttribute("topic", String.format(clientTopic, roomId));
        request.setAttribute("websocketUrl", emqxConfig.getWebsocketUrl());
        return "index";
    }

    @PostMapping("/send")
    @ResponseBody
    public Object send(String username, Long roomId, String msg) {
        try {
            JSONObject content = new JSONObject();
            content.put("msg", msg);
            content.put("username", username);
            mqttClient.publish(String.format(clientTopic, roomId), content.toJSONString().getBytes(StandardCharsets.UTF_8), 2, false);
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("data", msg);
            result.put("msg", "success");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("msg", e.getMessage());
            return result;
        }
    }

}