package com.yiningv.dxblog.repository;

import com.yiningv.dxblog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String>, ArticleOperations {

    void deleteByReposId(String reposId);
    List<Article> findAllByReposId(String reposId);
}
