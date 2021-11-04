package com.example.demo.controller;

import com.example.demo.common.ResponseResult;
import com.example.demo.entity.Post;
import com.example.demo.entity.Reply;
import com.example.demo.entity.User;
import com.example.demo.service.ForumService;
import com.example.demo.utils.JwtUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author Wu Yong
 */
@RestController
@RequestMapping("/forum")
public class ForumController {
    @Resource
    private ForumService forumService;

    //首页帖子默认页数
    private static final String DEFAULT_POSTS_PAGE_NUMBER = 1 + "";
    //首页帖子每页默认显示数量
    private static final String DEFAULT_POSTS_PAGE_SIZE = 8 + "";
    //查询帖子回复默认页数
    private static final String DEFAULT_REPLYS_PAGE_NUMBER = 1 + "";
    //查询帖子回复每页默认显示数量
    private static final String DEFAULT_REPLYS_PAGE_SIZE = 5 + "";
    //点赞方式，默认为 1，表示给主楼点赞
    private static final String THUMBSUP_DEFAULT_TYPE = 1 + "";
    //点赞方式，0 表示给楼层点赞
    private static final String THUMBSUP_FLOOR_TYPE = 0 + "";

    /**
     * 获取论坛首页的多条帖子信息接口，结果按照发帖时间排序，时间越新越靠前。用于论坛首页的浏览。
     * @param pageNum   查询的页数(第几页)，默认为 1
     * @param pageSize  每页的帖子数，默认为 8
     * @return  返回数据： Post 实体类的集合
     */
    @GetMapping("/getPosts")
    public ResponseResult<?> getPosts(@RequestParam(defaultValue = DEFAULT_POSTS_PAGE_NUMBER) Integer pageNum,
                                      @RequestParam(defaultValue = DEFAULT_POSTS_PAGE_SIZE) Integer pageSize) {
        //查询参数限制
        if ( pageNum < 1 || pageSize < 1 ) {
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "查询参数有误！");
        }
        try {
            ArrayList<Post> postList;
            postList = forumService.selectPosts((pageNum - 1) * pageSize, pageSize);
            return new ResponseResult<>(ResponseResult.CODE_SUCCESS, "查询帖子成功！", postList);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "数据库查询出错！");
        }
    }

    /**
     * 发起一个新帖子接口
     * @param post
     * @return
     */
    @PostMapping("/createPost")
    @Transactional
    public ResponseResult<?> createPost(@RequestBody Post post) {
        if (post.getOwnerId() == null) {
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "提交参数有误，缺少 id 信息！");
        }
        //插入数据库
        try {
            forumService.createPost(post);
            //获取帖子 id 和 时间戳，之后将所有信息写入返回体
            ArrayList<Post> posts;
            posts = forumService.selectLatestPostByOwnerAndTitle(post.getOwnerId(), post.getTitle());
            Post selectedPost =  posts.get(0);
            if (selectedPost != null) {
                return new ResponseResult<Object>(ResponseResult.CODE_SUCCESS, "成功插入记录！", selectedPost);
            } else {
                System.out.println("warning : createPost 接口数据库查询有误！");
                return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "接口数据库查询有误！");
            }
        }catch (Exception e) {
            System.out.println(e);
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "数据库插入出错！");
        }
    }

    /**
     * 发起一个新的回复
     * @param
     * @return
     */
    @PostMapping("/createReply")
    public ResponseResult<?> createReply(@RequestBody Reply reply) {
        if (reply.getReplyUser() == null || reply.getPostId() == null || reply.getType() == null) {
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "提交参数有误，缺少 id 信息！");
        }
        try {
            //先查询当前主贴有多少楼层
            Integer floorCount = 0;
            floorCount = forumService.selectFloorCountByPostId(reply.getPostId());
            if (floorCount == 0) {
                return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "未查询到此主帖！");
            }
            if (reply.getType() == 1) {
                if (reply.getToFloor() < 1 || reply.getToFloor() > floorCount) {
                    return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "提交参数有误，toFloor不符合规则");
                }
            }
            reply.setFloor(floorCount + 1);
            //插入数据库
            forumService.createReply(reply);
            //获取回复 id 和 时间戳，之后将所有信息写入返回体
            Reply selectedReply = forumService.selectReplyByPostIdAndFloor(reply.getPostId(), reply.getFloor());
            if (selectedReply != null) {
                //更新主贴的回复数
                switch (selectedReply.getType()) {
                    case (0):
                        forumService.updatePostReplyCount(selectedReply.getPostId());
                        return new ResponseResult<Object>(ResponseResult.CODE_SUCCESS, "成功插入回复记录！", selectedReply);
                    case (1):
                        forumService.updateReplyCountByFloor(selectedReply.getPostId(), selectedReply.getToFloor());
                        return new ResponseResult<Object>(ResponseResult.CODE_SUCCESS, "成功插入回复记录！", selectedReply);
                    default:
                        return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "createReply 接口数据库查询有误！");
                }
            } else {
                System.out.println("warning : createPost 接口数据库查询有误！");
                return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "createReply 接口数据库查询有误！");
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "数据库插入出错！");
        }
    }

    /**
     * 根据帖子 id 查询帖子回复
     * @param postId, pageNum, pageSize
     * @return
     */
    @GetMapping("/getReplysById")
    public ResponseResult<?> getPost(@RequestParam Integer postId,
                                     @RequestParam(defaultValue = DEFAULT_REPLYS_PAGE_NUMBER) Integer pageNum,
                                     @RequestParam(defaultValue = DEFAULT_REPLYS_PAGE_SIZE) Integer pageSize) {
        //查询参数限制
        if ( pageNum < 1 || pageSize < 1 ) {
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "查询参数有误！");
        }
        try {
            ArrayList<Reply> replyList;
            replyList = forumService.selectReplysByPostId(postId, (pageNum - 1) * pageSize, pageSize);
            return new ResponseResult<>(ResponseResult.CODE_SUCCESS, "查询帖子成功！", replyList);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "数据库查询出错！");
        }
    }

    /**
     * 给主楼或其它楼层点赞
     * @param targetId  点赞目标的 id
     * @param type     如果是给主楼点赞，此项可不带，否则要带上点赞的楼层
     * @return
     */
    @GetMapping("/thumbsUp")
    public ResponseResult<?> thumbsUp(@RequestParam Integer targetId,
                                      @RequestParam(defaultValue = THUMBSUP_DEFAULT_TYPE) Integer type) {
        if (type != 0 && type != 1) {
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "提交参数有误！type不合法");
        }
        if ( targetId < 1 ) {
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "提交参数有误！targetId不合法");
        }
        try {
            //给主楼点赞
            if (type == 1) {
                forumService.updatePostLikesCount(targetId);
            } else {
                //给其它楼层点赞
                forumService.updateLikesCountByReplyId(targetId);
            }
        } catch (Exception e) {
            return new ResponseResult<>(ResponseResult.CODE_FAILED, "数据库插入出错！");
        }
        return new ResponseResult<>(ResponseResult.CODE_SUCCESS, "点赞成功！");
    }
}
