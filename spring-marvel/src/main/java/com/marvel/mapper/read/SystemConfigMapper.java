package com.marvel.mapper.read;

import com.marvel.entity.SystemConfig;

import java.util.List;

/**
 * 系统配置信息
 *
 * @author Jarvis
 * @date 2018/8/24
 */
public interface SystemConfigMapper {
    /**
     * 获取所有信息
     *
     * @return
     */
    List<SystemConfig> getAll();
}
