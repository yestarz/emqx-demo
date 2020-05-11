<html>
<head>
    <title>title</title>
    <script src="/mqtt.js"></script>
    　
    <script src="/jquery.js"></script>
    <script src="/index.js"></script>
</head>

<body>
<div>
    <span>${username!}成功进入了${roomId!}聊天室</span><br/>
    <span id="connectionTip"></span><br/>
    <hr/>
    输入消息：<input name="content" placeholder="请输入消息"/>
    <button name="send" id="send">发送</button>
    <hr/>

    <div id="msgContent">

    </div>
</div>
</body>
</html>

<script>
    var username = '${username!}';
    var topic = '${topic!}';
    var roomId = '${roomId!}';
    var websocketUrl = '${websocketUrl!}';
    var clientId = '${clientId!}';
</script>