package com.yiningv.dxblog.model.entity;

import lombok.Data;

@Data
public class Comment {

    private Integer id;

    private Integer articleId;

    private Long created;

    private String nickName;

    private String mail;

    private String url;

    private String ip;

    private String agent;

    private String content;

    private Integer parentId;

    private String status;
}
