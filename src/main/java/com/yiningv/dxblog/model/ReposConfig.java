package com.yiningv.dxblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 通过webhook接入site中的github repository的配置
 * 用来说明仓库的元数据, 默认读取仓库根目录下的dxBlogConfig.yml,
 * 不想写配置了，这个参数就这样定死吧
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("repos_config")
public class ReposConfig {

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String reposId;

    // 指明可以拉取哪些文件夹下的md文章
    private List<String> articleFolders;

}
