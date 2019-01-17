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
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Document("article")
public class Article {

    @Id
    private String id;

    // 根据github上文件的相对路径计算md5
    @NonNull
    @Indexed(unique = true)
    private String md5;

    @NonNull
    private String title;

    private String summary;

    @NonNull
    private String content;

    private String category;

    private Set<String> tags;

    private Date created;

    private Date updated;

}
