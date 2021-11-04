package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Wu Yong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    public static final Integer REPLY_TYPE_MAIN = 0;    //回复类型，默认 0 表示回复主楼，1 表示回复其他人
    public static final Integer REPLY_TYPE_OTHER = 1;
    private Integer postId;     //回复的帖子的 id
    private Integer replyId;    //回复 id
    private Integer replyUser;  //回复人的 id
    private Integer floor;      //回复所在楼层
    private String content = "内容";     //回复内容
    private Integer type = REPLY_TYPE_MAIN;       //回复类型
    private Integer toFloor = 1;    //回复对象所在楼层
    private Integer likesNumber = 0;  //获得得点赞数
    private Integer commentNumber = 0; //评论数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp replyTime;    //回复时间
}
