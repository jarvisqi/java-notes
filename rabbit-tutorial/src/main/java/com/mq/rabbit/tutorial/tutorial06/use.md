使用命令：
```jshelllanguage
java -jar target/rabbit-tutorial-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut5,sender05  --tutorial.client.duration=60000
java -jar target/rabbit-tutorial-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut5,receiver05  --tutorial.client.duration=60000
```
#### topic exchange 与 direct exchange 类似，也是将消息路由到 binding key 与 routing key 相匹配的 Queue 中，但这里的匹配规则有些不同，它约定：
- routing key 为一个句点号.分隔的字符串（我们将被句点号.分隔开的每一段独立的字符串称为一个单词），如 “stock.usd.nyse”、”nyse.vmw”、”quick.orange.rabbit”
+ binding key 与 routing key 一样也是句点号.分隔的字符串
- binding key 中可以存在两种特殊字符*与#，用于做模糊匹配，其中*用于匹配一个单词，#用于匹配多个单词（可以是零个）
- 如果我们违反约定，发送了一个 routing key 为一个单词或者四个单词

#### 代码路由说明
- routingKey=”quick.orange.rabbit” 的消息会同时路由到 Q1 与 Q2
- routingKey=”lazy.orange.fox” 的消息会路由到 Q1 与 Q2
- routingKey=”lazy.brown.fox” 的消息会路由到 Q2
- routingKey=”lazy.pink.rabbit” 的消息会路由到 Q2（只会投递给 Q2 一次，虽然这个 routingKey 与 Q2 的两个 bindingKey 都匹配）
- routingKey=”quick.brown.fox”、routingKey=”orange”、routingKey=”quick.orange.male.rabbit” 的消息将会被丢弃，因为它们没有匹配任何 bindingKey
- lazy.orange.male.rabbit” 有四个单词，他还是会匹配最后一个绑定，并且被投递到第二个队列中。
>topic exchange
  topic exchange 是强大的，它可以表现出跟其他 exchange 类似的行为。
  当一个队列的 binding key 为 “#”（井号） 的时候，它会接收所有消息，而不考虑 routing key，就像 fanout exchange。
  当 * (星号) 和 # (井号) 这两个特殊字符都未在绑定键中出现的时候，此时 topic exchange 会表现得像 direct exchange 一样。