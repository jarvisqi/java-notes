#### 使用redis的setNX命令实现分布式锁

1、实现的原理  

Redis为单进程单线程模式，采用队列模式将并发访问变成串行访问，且多客户端对Redis的连接并不存在竞争关系。  
redis的`SETNX`命令可以方便的实现分布式锁。  
>主要是使用了redis 的setnx命令,缓存了锁.   
>reids缓存的key是锁的key,所有的共享,value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)   
>执行过程:  
>>1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁  
>>2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值  

2、基本命令解析  

1）setNX（SET if Not eXists）

语法： `SETNX key value`

将 key 的值设为 value ，当且仅当 key 不存在。

若给定的 key 已经存在，则 SETNX 不做任何动作。

SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写

返回值：
　　设置成功，返回 1 。
　　设置失败，返回 0 。

2）getSET

语法：

GETSET key value

　　将给定 key 的值设为 value ，并返回 key 的旧值(old value)。

　　当 key 存在但不是字符串类型时，返回一个错误。

返回值：
　　返回给定 key 的旧值。
　　当 key 没有旧值时，也即是， key 不存在时，返回 nil 。
3）get
语法：

GET key

 返回值：
　　当 key 不存在时，返回 nil ，否则，返回 key 的值。
　　如果 key 不是字符串类型，那么返回一个错误