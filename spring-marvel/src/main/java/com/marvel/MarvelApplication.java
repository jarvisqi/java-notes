package com.marvel;

import com.marvel.mapper.BaseMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
//MapperScan 导入  org.mybatis.spring.annotation 会报错
import tk.mybatis.spring.annotation.MapperScan;

/***
 *
 * @author : jarvis
 * @date : 2018-02-01
 *
 */
@SpringBootApplication
@MapperScan(value = "com.marvel.mapper", markerInterface = BaseMapper.class)
public class MarvelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarvelApplication.class, args);
    }

}

