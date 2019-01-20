package com.yiningv.dxblog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("article")
public class Article {

    // 文章所在github仓库id + 文件的相对路径md5
    @Id
    private String id;

    @NonNull
    private String reposId;

    // github api读取到的sha
    private String sha;

    private String path;

    private String htmlUrl;

    @NonNull
    private String title;

    private String description;

    @NonNull
    private String content;

    private String category;

    private Set<String> tags;

    private Date created;

}
