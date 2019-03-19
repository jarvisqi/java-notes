package com.marvel.mapper;

import com.marvel.entity.Article;
import org.springframework.stereotype.Component;

/**
 * @author Jarvis
 * @date 2018/07/03
 */
@Component(value = "articleMapper")
public interface ArticleMapper extends BaseMapper<Article> {
}