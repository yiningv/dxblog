package com.yiningv.dxblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配置，参考hexo的配置_config.yml
 * 这些数据不会通过webhook进行修改，防止自己手贱经常修改网站信息
 * 通过配置写死
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSetting {

    // 网站标题
    private String title;

    // 网站副标题
    private String subtitle;

    // 网站描述
    private String description;

    // 作者
    private String author;
}
