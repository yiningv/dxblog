package com.yiningv.dxblog.repository;


import com.yiningv.dxblog.model.TagCount;

import java.util.List;

public interface ArticleOperations {

    List<TagCount> findAllTags();

    void deleteByReposId(String reposId);
}
