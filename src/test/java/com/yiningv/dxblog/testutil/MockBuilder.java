package com.yiningv.dxblog.testutil;

import com.google.common.collect.Sets;
import com.yiningv.dxblog.model.Article;

import java.util.Date;

public final class MockBuilder {

    private MockBuilder() {}

    public static Article mockArticle(String index, String reposId) {
        return Article.builder()
                .id(index)
                .reposId(reposId)
                .sha(index)
                .path(index)
                .htmlUrl(index)
                .title(index)
                .description(index)
                .content(index)
                .category(index)
                .tags(Sets.newHashSet(index, "tag"))
                .created(new Date())
                .build();
    }
}
