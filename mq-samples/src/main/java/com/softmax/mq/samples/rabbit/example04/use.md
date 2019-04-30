使用命令：
```jshelllanguage
java -jar target/rabbit-tutorial-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut4,receiver04  --tutorial.client.duration=60000
java -jar target/rabbit-tutorial-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut4,sender04  --tutorial.client.duration=60000
```

1. 路由的算法很简单——交换器将会对绑定键（binding key）和路由键（routing key）进行精确匹配，从而确定消息该分发到哪个队列。
2. 当路由键为 orange 的消息发布到交换器，就会被路由到队列 Q1。路由键为 black 或者 green 的消息就会路由到 Q2。其他的所有消息都将会被丢弃。
3. 多个队列使用相同的绑定键是可以的。

