package com.yiningv.dxblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("article")
public class Article {

    // 根据github上文件的相对路径计算md5
    @Id
    @Indexed(unique = true)
    private String id;

    private String title;

    private String summary;

    private String content;

    private String category;

    private Set<String> tags = new HashSet<>();

    // 时间格式:yyyy-MM-dd HH:mm:ss
    private Date created;

    private Date updated;

    // 根据github上文件的相对路径计算md5
    @Indexed(unique = true)
    private String md5;
}
