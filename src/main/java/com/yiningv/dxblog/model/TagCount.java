package com.yiningv.dxblog.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class TagCount {
    @Id
    private String name;
    private Integer count;
}
