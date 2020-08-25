### 并发限流算法
---
#### 一、计数器算法原理
计数器法是限流算法里最简单也是最容易实现的一种算法，一般我们会限制一秒钟能够通过的请求数。比如我们规定，对于A接口来说，我们1分钟的访问次数不能超过100个。那么我们可以这么做：在一开始的时候，我们可以设置一个计数器counter，每当一个请求过来的时候， counter就加1，如果counter的值大于100并且该请求与第一个请求的间隔时间还在1分钟之内，那么说明请求数过多; 如果该请求与第一个请求的间隔时间大于1分钟，且counter的值还在限流范围内，那么就重置 counter。


![](https://mmbiz.qpic.cn/mmbiz_png/P13HW4Fm1HWODEMe6rcCOGAfDUofiapPIZ4gcyqTsykU4dnv5cDZAYNeY81VBLlzG7vZshhq4CcNfL7aPTUK8Xg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

计算器算法存在问题
对于秒级以上的时间周期来说，会存在一个非常严重的问题，那就是临界问题。
![](https://mmbiz.qpic.cn/mmbiz_png/P13HW4Fm1HWODEMe6rcCOGAfDUofiapPIZ4gcyqTsykU4dnv5cDZAYNeY81VBLlzG7vZshhq4CcNfL7aPTUK8Xg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
从上图中我们可以看到，假设有一个恶意用户，他在0:59时，瞬间发送了100个请求，并且1:00又瞬间发送了100个请求，那么其实这个用户在 1秒里面，瞬间发送了200个请求。我们刚才规定的是1分钟最多100个请求，也就是每秒钟最多1.7个请求，用户通过在时间窗口的重置节点处突发请求， 可以瞬间超过我们的速率限制。用户有可能通过算法的这个漏洞，瞬间压垮我们的应用。
 
####  二、滑动窗口算法
滑动窗口，又称rolling window。为了解决计数器法统计精度太低的问题，引入了滑动窗口算法。下面这张图，很好 地解释了滑动窗口算法:  
![](https://mmbiz.qpic.cn/mmbiz_png/P13HW4Fm1HWODEMe6rcCOGAfDUofiapPIicttfrg2QL1urruW9nayZCfvhfudBZ1vqDziabe3ej7IvHfhumib0ER2w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)  
是一分钟。然后我们将时间窗口进行划分，比如图中，我们就将滑动窗口划成了6格，所以每格代表的是10秒钟。每过10秒钟，我们的时间窗口就会往右滑动一格。每一个格子都有自己独立的计数器counter，比如当一个请求 在0:35秒的时候到达，那么0:30~0:39对应的counter就会加1。
那么滑动窗口怎么解决刚才的临界问题的呢？在上图中，0:59到达的100个请求会落在灰色的格子中，而1:00到达的请求会落在橘×××的格子中。当时间到达1:00时，我们的窗口会往右移动一格，那么此时时间窗口内的总请求数量一共是200个，超过了限定的100个，所以此时能够检测出来触发了限流。
回顾一下上面的计数器算法，我们可以发现，计数器算法其实就是滑动窗口算法。只是它没有对时间窗口做进一步地划分，所以只有1格。
由此可见，当滑动窗口的格子划分的越多，那么滑动窗口的滚动就越平滑，限流的统计就会越精确。

####  三、漏桶算法
首先，我们有一个固定容量的桶，有水流进来，也有水流出去。对于流进来的水来说，我们无法预计一共有多少水会流进来，也无法预计水流的速度。但是对于流出去的水来说，这个桶可以固定水流出的速率。而且，当桶满了之后，多余的水将会溢出。
我们将算法中的水换成实际应用中的请求，我们可以看到漏桶算法天生就限制了请求的速度。当使用了漏桶算法，我们可以保证接口会以一个常速速率来处理请求。所以漏桶算法天生不会出现临界问题。

漏桶算法可以粗略的认为就是注水漏水过程，往桶中以一定速率流出水，以任意速率流入水，当水超过桶流量则丢弃，因为桶容量是不变的，保证了整体的速率。

#### 总结：

（1）滑动窗口算法是以当前这个时间点为基准，往前推移1秒进行计算当前1秒内的请求量情况。
（2）滑动窗口限流统计的精准度是由划分的格子多少决定的，这个怎么理解呐，就是把1秒中进行划分成多个时间段，比如2个格子的话，那么就是2段，0-500ms和501-1000ms。那么就会两个值进行存储统计请求量，比如数组[0,1] 各存储一个段的请求值。
（3）计算器算法是滑动窗口算法将时间段划分为1的特殊情况。
    综上我们对滑动窗口算法下个定义：滑动窗口算法是将时间周期分为N个小周期，分别记录每个小周期内访问次数，并且根据时间滑动删除过期的小周期。
       