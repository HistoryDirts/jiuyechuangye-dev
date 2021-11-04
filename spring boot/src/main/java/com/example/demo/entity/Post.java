package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 论坛页帖子信息
 * @author Wu Yong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    //帖子 id，由数据库自动生成
    private Integer id;
    //帖子标题
    private String title = "标题";
    //发帖人 id
    private Integer ownerId;
    //帖子内容（一楼显示的内容）
    private String content = "内容";
    //回复数，默认为 0
    private Integer replysNumber = 0;
    //点赞数， 默认为 0
    private Integer likesNumber = 0;
    //发帖时间，由数据库自动生成
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createdTime;
}