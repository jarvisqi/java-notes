使用命令：
```jshelllanguage
java -jar target/rabbit-tutorial-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut3,sender03   --tutorial.client.duration=60000
java -jar target/rabbit-tutorial-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut3,receiver03  --tutorial.client.duration=60000
```

1. RabbitMQ 消息模型的核心理念是：发布者（producer）不会直接发送任何消息给队列。事实上，发布者（producer）甚至不知道消息是否已经被投递到队列。
2. 发布者（producer）只需要把消息发送给一个交换器（exchange）。交换器非常简单，它一边从发布者方接收消息，一边把消息推送到队列。交换器必须知道如何处理它接收到的消息，是应该推送到指定的队列还是多个队列，或者是直接忽略消息。这些规则是通过交换器类型（exchange type）来定义的。
3. 有几个可供选择的交换器类型：direct, topic, headers，fanout。
4. 禁止发布到不存在的交换器
5. 如果没有任何队列绑定到交换器，消息将丢失 
6. AnonymousQueue来作为临时队列。它是一个非持久化的、独占的、可自动删除的队列。
```jshelllanguage
当连接到 RabbitMQ，我们需要一个新的空的队列。服务器为我们选择一个随机的队列名。  
当与消费者（consumer）断开连接的时候，这个队列应当被立即删除。  
``` 


