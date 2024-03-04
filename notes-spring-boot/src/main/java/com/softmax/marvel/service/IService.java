package com.softmax.marvel.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service 通用接口
 *
 * @author : Jarvis
 * @date : 2018/6/2
 */
@Service
public interface IService<T> {

    T selectByKey(Object key);

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     * 删除
     *
     * @param key
     * @return
     */
    int delete(Object key);

    /**
     * 更新更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    int updateAll(T entity);

    /**
     * 更新属性不为null的值
     *
     * @param entity
     * @return
     */
    int updateNotNull(T entity);

    /**
     * 根据条件查询
     *
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);
}
