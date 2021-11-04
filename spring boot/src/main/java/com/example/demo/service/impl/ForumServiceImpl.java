package com.example.demo.service.impl;

import com.example.demo.entity.Post;
import com.example.demo.entity.Reply;
import com.example.demo.mapper.ForumMapper;
import com.example.demo.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Wu Yong
 */
@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    ForumMapper forumMapper;

    @Override
    public ArrayList<Post> selectPosts(Integer param1, Integer pageSize) {
        return forumMapper.selectPosts(param1, pageSize);
    }

    @Override
    public void createPost(Post post) {
        forumMapper.createPost(post);
    }

    @Override
    public void createReply(Reply reply) {
        forumMapper.createReply(reply);
    }

    @Override
    public ArrayList<Post> selectLatestPostByOwnerAndTitle(Integer ownerId, String title) {
        return forumMapper.selectLatestPostByOwnerAndTitle(ownerId, title);
    }

    @Override
    public Reply selectReplyByPostIdAndFloor(Integer postId, Integer floor) {
        return forumMapper.selectReplyByPostIdAndFloor(postId, floor);
    }

    @Override
    public Integer selectFloorCountByPostId(Integer postId) {
        return forumMapper.selectFloorCountByPostId(postId);
    }

    @Override
    public ArrayList<Reply> selectReplysByPostId(Integer postId, Integer param1, Integer pageSize) {
        return forumMapper.selectReplysByPostId(postId, param1, pageSize);
    }

    @Override
    public void updatePostReplyCount(Integer postId) {
        forumMapper.updatePostReplyCount(postId);
    }

    @Override
    public void updateReplyCountByFloor(Integer postId,Integer toFloor) {
        forumMapper.updateReplyCountByFloor(postId, toFloor);
    }

    @Override
    public void updatePostLikesCount(Integer targetId) {
        forumMapper.updatePostLikesCount(targetId);
    }

    @Override
    public void updateLikesCountByReplyId(Integer targetId) {
        forumMapper.updateLikesCountByReplyId(targetId);
    }

}
