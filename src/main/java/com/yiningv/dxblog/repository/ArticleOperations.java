package com.yiningv.dxblog.repository;


public interface ArticleOperations {
    void deleteByArticleIdSet(Iterable<String> articleIds);

    void deleteByArticleId(String articleId);
}
