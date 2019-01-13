package com.yiningv.dxblog.mapper;

import com.yiningv.dxblog.model.entity.Article;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ArticleMapper extends Mapper<Article>, MySqlMapper<Article> {
}