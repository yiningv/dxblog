package com.yiningv.dxblog.model.entity;

import lombok.Data;

@Data
public class Article {

    private Integer id;

    private Long created;

    private Long modified;

    private String title;

    private String content;

    private Integer hits;

    private String status;

    private Integer commentNum;

    private Boolean allowComment;
}
