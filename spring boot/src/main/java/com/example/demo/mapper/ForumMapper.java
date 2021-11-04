package com.example.demo.mapper;

import com.example.demo.entity.Post;
import com.example.demo.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author Wu Yong
 */
@Mapper
@Repository
public interface ForumMapper {
    ArrayList<Post> selectPosts(Integer param1, Integer pageSize);

    void createPost(Post post);

    void createReply(Reply reply);

    ArrayList<Post> selectLatestPostByOwnerAndTitle(Integer ownerId, String title);

    Reply selectReplyByPostIdAndFloor(Integer postId, Integer floor);

    Integer selectFloorCountByPostId(Integer postId);

    ArrayList<Reply> selectReplysByPostId(Integer postId, Integer param1, Integer pageSize);

    void updatePostReplyCount(Integer postId);

    void updateReplyCountByFloor(Integer postId, Integer toFloor);

    void updatePostLikesCount(Integer targetId);

    void updateLikesCountByReplyId(Integer targetId);
}
