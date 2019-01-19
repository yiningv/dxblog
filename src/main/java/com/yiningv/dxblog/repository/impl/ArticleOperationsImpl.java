package com.yiningv.dxblog.repository.impl;

import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.repository.ArticleOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class ArticleOperationsImpl implements ArticleOperations {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void deleteByArticleIdSet(Iterable<String> articleIds) {
        Assert.notNull(articleIds, "The given Iterable of articleIds not be null!");

        articleIds.forEach(this::deleteByArticleId);
    }

    @Override
    public void deleteByArticleId(String articleId) {
        Assert.notNull(articleId, "The given articleId must not be null!");

        mongoOperations.remove(new Query(Criteria.where("articleId").is(articleId)), Article.class);
    }
}
