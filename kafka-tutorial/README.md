#### Kafka如何保证数据的不丢失
#### 1.生产者数据的不丢失
- 新版本的producer采用异步发送机制。KafkaProducer.send(ProducerRecord)方法仅仅是把这条消息放入一个缓存中(即RecordAccumulator，本质上使用了队列来缓存记录)，同时后台的IO线程会不断扫描该缓存区，将满足条件的消息封装到某个batch中然后发送出去。显然，这个过程中就有一个数据丢失的窗口：若IO线程发送之前client端挂掉了，累积在accumulator中的数据的确有可能会丢失。
kafka的ack机制：在kafka发送数据的时候，每次发送消息都会有一个确认反馈机制，确保消息正常的能够被收到。
- 如果是同步模式：ack机制能够保证数据的不丢失，如果ack设置为0，风险很大，一般不建议设置为0


        producer.type=sync 
        request.required.acks=1

- 如果是异步模式：通过buffer来进行控制数据的发送，有两个值来进行控制，时间阈值与消息的数量阈值，如果buffer满了数据还没有发送出去，如果设置的是立即清理模式，风险很大，一定要设置为阻塞模式 

    
        producer.type=async 
        request.required.acks=1 
        queue.buffering.max.ms=5000 
        queue.buffering.max.messages=10000 
        queue.enqueue.timeout.ms = -1 
        batch.num.messages=200
        
- 结论：producer有丢数据的可能，但是可以通过配置保证消息的不丢失

#### 2.消费者数据的不丢失
- 如果在消息处理完成前就提交了offset，那么就有可能造成数据的丢失。由于Kafka consumer默认是自动提交位移的，所以在后台提交位移前一定要保证消息被正常处理了，因此不建议采用很重的处理逻辑，如果处理耗时很长，则建议把逻辑放到另一个线程中去做。为了避免数据丢失，现给出两点建议：
    
    
        enable.auto.commit=false  关闭自动提交位移
        在消息被完整处理之后再手动提交位移
    
    
- 如果使用了storm，要开启storm的ackfail机制；
- 如果没有使用storm，确认数据被完成处理之后，再更新offset值。低级API中需要手动控制offset值。通过offset commit 来保证数据的不丢失，kafka自己记录了每次消费的offset数值，下次继续消费的时候，接着上次的offset进行消费即可。