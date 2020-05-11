$(document).ready(function () {
    // https://github.com/mqttjs/MQTT.js
    // 将在全局初始化一个 mqtt 变量
    console.log(mqtt);

    // 连接选项
    const options = {
        connectTimeout: 4000, // 超时时间
        // 认证信息
        clientId: clientId, // 客户端id 这个自己填 尽量唯一
        username: username, // 取当前用户的名字
        password: '',
        will:{
            topic:`will/${clientId}`,
            payload:"I'm died",
            qos:2,
            retain:false
        },
    };
    const client = mqtt.connect(websocketUrl, options);

    client.on('connect', (e) => {
        console.log('成功连接服务器');
        $("#connectionTip").html("成功连接到消息服务器");
        // 订阅一个主题
        client.subscribe(topic, {qos: 2}, (error) => {
            if (!error) {
                console.log('订阅成功')
            }
        })
    });

    client.on('reconnect', (error) => {
        console.log('正在重连:' + error)
    });

    client.on('error', (error) => {
        console.log('连接失败:' + error)
    });

    // 监听接收消息事件
    client.on('message', (topic, message, callback) => {
        console.log('收到来自', topic, '的消息', message.toString());
        let html = `<span> 收到来自 ${topic} 的消息:${message.toString()}</span><br/>`;
        $("#msgContent").append(html);
    });

    $("#send").click(function () {
        let content = $("input[name='content']").val();
        $.post("/send", {roomId: roomId, msg: content,username:username}, function (data) {
            if (data.success) {
                console.log("发送成功");
            } else {
                alert(data.msg);
            }
        }, 'json')
    })

});