package com.softmax.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 编程式事务
 *
 * @author Jarvis
 */
@Component
public class TransactionSample {

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void save() {

        query();
        findUser();
        //能够更小粒度的控制事务的范围，更直观。
        transactionTemplate.execute((status) -> {
            saveUpdate();
            delete();
            return Boolean.TRUE;
        });
    }


    public void query() {

    }

    public void findUser() {

    }

    public void saveUpdate() {

    }


    public void delete() {

    }

}
