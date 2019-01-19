package com.yiningv.dxblog.repository;

import com.yiningv.dxblog.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String>, ArticleOperations {

}
