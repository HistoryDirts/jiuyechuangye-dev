package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.entity.Reply;

import java.util.ArrayList;

/**
 * @author Wu Yong
 */
public interface ForumService {
    public ArrayList<Post> selectPosts(Integer param1, Integer pageSize);

    //发起一个新帖子
    void createPost(Post post);

    //发起一个新回复
    void createReply(Reply reply);

    //根据发帖人 id 和 标题 查询帖子(结果按发帖时间降序排列)
    ArrayList<Post> selectLatestPostByOwnerAndTitle(Integer ownerId, String title);

    //根据帖子 id 和 所在楼层查询回复
    Reply selectReplyByPostIdAndFloor(Integer postId, Integer floor);

    //根据帖子 id 查询楼层数
    Integer selectFloorCountByPostId(Integer postId);

    //根据帖子 id 查询帖子回复
    ArrayList<Reply> selectReplysByPostId(Integer postId, Integer param1, Integer pageSize);

    //根据 postId 对帖子评论数 加 1
    void updatePostReplyCount(Integer postId);

    //根据 toFloor 对楼层评论数 加 1
    void updateReplyCountByFloor(Integer postId, Integer toFloor);

    //根据 postId 给主楼点赞数 加 1
    void updatePostLikesCount(Integer targetId);

    //根据 replyId 给其它楼层点赞数 加 1
    void updateLikesCountByReplyId(Integer targetId);
}
