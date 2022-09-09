package com.softmax.mq.courier.rocket.springboot;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.softmax.mq.courier.rocket.springboot.Producer.TX_PGROUP_NAME;

/**
 * RocketMQTemplate 发送事务消息
 *
 * @author Jarvis
 * @date 2019/04/30
 */
@RocketMQTransactionListener(txProducerGroup = TX_PGROUP_NAME)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    private final AtomicInteger transactionIndex = new AtomicInteger(0);

    private final ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>(4);

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //事务ID
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.printf("#### executeLocalTransaction is executed, msgTransactionId=%s %n", transId);
        //获取增量
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        assert transId != null;
        localTrans.put(transId, status);
        if (status == 0) {
            System.out.printf("    # COMMIT # 模拟消息 %s 本地事务执行成功! ### %n", message.getPayload());
            return RocketMQLocalTransactionState.COMMIT;
        }
        if (status == 1) {
            System.out.printf("    # COMMIT # 模拟消息 %s 本地事务执行失败! ### %n", message.getPayload());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        System.out.printf("    # UNKNOW # 模拟 %s 本地事务执行异常! \n");
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //事务ID
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        RocketMQLocalTransactionState transactionState = RocketMQLocalTransactionState.COMMIT;
        Integer status = localTrans.get(transId);
        if (null != status) {
            switch (status) {
                case 0:
                    transactionState = RocketMQLocalTransactionState.UNKNOWN;
                    break;
                case 1:
                    transactionState = RocketMQLocalTransactionState.COMMIT;
                    break;
                case 2:
                    transactionState = RocketMQLocalTransactionState.ROLLBACK;
                    break;
            }
        }
        System.out.printf("------ !!! checkLocalTransaction is executed once," +
                        " msgTransactionId=%s, TransactionState=%s status=%s %n",
                transId, transactionState, status);

        return transactionState;
    }
}
