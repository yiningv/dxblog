package com.yiningv.dxblog.repository.impl;

import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.model.TagCount;
import com.yiningv.dxblog.repository.ArticleOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleOperationsImpl implements ArticleOperations {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<TagCount> findAllTags() {
        TypedAggregation<Article> aggregation = Aggregation.newAggregation(Article.class,
                Aggregation.unwind("tags"),
                Aggregation.group("tags").count().as("count"),
                Aggregation.project("_id", "count"));
        AggregationResults<TagCount> results = mongoOperations.aggregate(aggregation, TagCount.class);
        return results.getMappedResults();
    }

//    @Override
////    public void deleteByReposId(String reposId) {
////        mongoOperations.remove(new Query(Criteria.where("reposId").is(reposId)), Article.class);
////    }

//    @Override
//    public List<Article> findAllByReposId(String reposId) {
//        List<Article> articles = mongoOperations.find(new Query(Criteria.where("reposId").is(reposId)), Article.class);
//        return articles;
//    }

}
