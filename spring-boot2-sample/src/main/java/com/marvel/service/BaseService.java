package com.marvel.service;


import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author : Jarvis
 * @date : 2018/6/2
 */
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return
     */
    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 实体
     * @return int
     */
    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return int
     */
    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 实体
     * @return int
     */
    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 实体
     * @return int
     */
    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据Example条件进行查询
     * 这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
     *
     * @param example
     * @return
     */
    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
}
