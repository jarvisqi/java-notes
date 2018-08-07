package com.marvel;

import com.marvel.mapper.BaseMapper;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
//MapperScan 导入  org.mybatis.spring.annotation 会报错
import tk.mybatis.spring.annotation.MapperScan;


/***
 *
 * @author : Jarvis
 * @date : 2018/02/01
 *
 */
@SpringBootApplication
@MapperScan(value = "com.marvel.mapper", markerInterface = BaseMapper.class)
public class MarvelApplication {

    public static void main(String[] args) {

//        SpringApplication application = new SpringApplication(MarvelApplication.class);
//        application.setBanner((environment, sourceClass, out) -> System.out.println("Marvel"));
//        application.setBannerMode(Banner.Mode.OFF);
//        application.run(args);

        SpringApplication.run(MarvelApplication.class, args);
    }

}

