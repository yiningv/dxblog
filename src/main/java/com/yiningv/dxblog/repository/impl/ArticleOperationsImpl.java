package com.yiningv.dxblog.repository.impl;

import com.yiningv.dxblog.model.Article;
import com.yiningv.dxblog.model.TagCount;
import com.yiningv.dxblog.repository.ArticleOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
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
                Aggregation.group("tags").count().as("tagCount"),
                Aggregation.project("_id", "tagCount"));
        AggregationResults<TagCount> results = mongoOperations.aggregate(aggregation, TagCount.class);
        return results.getMappedResults();
    }

}
