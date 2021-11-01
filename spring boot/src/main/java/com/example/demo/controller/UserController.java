package com.example.demo.controller;

import com.example.demo.common.ResponseResult;
import com.example.demo.entity.*;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据学号/工号和密码进行登录校验
     * @param user(Integer number, String password, String identity)
     * @return 
     */
    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody(required = true) User user) {
        //学号/工号、密码、身份均不能为空
        if(user.getNumber() == null) {
            return new ResponseResult<Object>(-1, "number为空", null);
        }
        if(user.getPassword() == null) {
            return new ResponseResult<Object>(-1, "password为空", null);
        }
        if(user.getIdentity() == null) {
            return new ResponseResult<Object>(-1, "identity为空", null);
        }
            
        User loginUser;
  
        // 根据不同登录身份进行查询
        switch(user.getIdentity()) {
            case("student") :
                loginUser = userService.selectStuInfoByNumber(user.getNumber()); 
                break;  
            case("teacher") :
                loginUser = userService.selectTchInfoByNumber(user.getNumber()); 
                break; 
            case("company") :
                loginUser = userService.selectCmpInfoByNumber(user.getNumber()); 
                break; 
            case("other") :
                loginUser = userService.selectOtherInfoByNumber(user.getNumber()); 
                break; 
            default :
                return new ResponseResult<Object>(-1, "identity不是指定类型值", null);
        }

        if(loginUser == null) {
            return new ResponseResult<Object>(-1, "未查询到此用户", null);
        }

        // 判断登录密码是否正确
        if(!(user.getPassword().equals(loginUser.getPassword()))) {
            return new ResponseResult<Object>(-1, "用户名与密码不匹配", null);
        }

        //密码正确
        loginUser.setIdentity(user.getIdentity());
        loginUser.setNumber(user.getNumber());
        Map<String, Object> map = null;
        
        //生成token
        // String token = TokenUtils.genToken(loginUser);
        String token = JwtUtil.createToken(loginUser);
        System.out.println("token : " + token);
        map = new HashMap<>();
        map.put("token", token);
        map.put("user", loginUser);

        return new ResponseResult<Map<String, Object>>(200, "登录成功", map);
    }

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    @PostMapping("/register")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseResult<?> register(@RequestBody User user) {
        
        if(user.getIdentity() == null) {
            return new ResponseResult<Object>(-1, "identity为空", null);
        }

        User loginUser = userService.selectByName(user.getName());
        if (loginUser != null) {
            return new ResponseResult<Object>(-1, "用户名重复", null);
        }
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        //将用户信息插入用户主表
        userService.insert(user);
        //插入各自身份对应的表
        user.setId(userService.selectByName(user.getName()).getId());

        switch (user.getIdentity()) {
            case("student"):
                userService.insertToStudent(new UserStu(user.getId(),user.getNumber(),
                        user.getName(),"nothing","nothing","nothing"));
                userService.insertToIdentity(new User_Identity(user.getId(), Identity.STUDENT_TYPE_ID));
                loginUser = userService.selectStuInfoByNumber(user.getNumber()); 
                break;
            case("teacher"):
                userService.insertToTeacher(new UserTch(user.getId(),user.getNumber(),
                        user.getName(),"nothing","nothing","nothing"));
                userService.insertToIdentity(new User_Identity(user.getId(), Identity.TEACHER_TYPE_ID));
                loginUser = userService.selectTchInfoByNumber(user.getNumber());
                break;
            case("company"):
                userService.insertToCompany(new UserCmp(user.getId(),user.getNumber(),
                        user.getName(),"nothing","nothing","nothing"));
                userService.insertToIdentity(new User_Identity(user.getId(), Identity.COMPANY_TYPE_ID));
                loginUser = userService.selectCmpInfoByNumber(user.getNumber()); 
                break;
            case("other"):
                userService.insertToOther(new UserOther(user.getId(),user.getNumber(),
                        user.getName(),"nothing","nothing","nothing"));
                userService.insertToIdentity(new User_Identity(user.getId(), Identity.OTHER_TYPE_ID));
                loginUser = userService.selectOtherInfoByNumber(user.getNumber());
                break;
            default:
                return new ResponseResult<Object>(-1, "identity不是指定类型值", null);
        }

        //生成token
        Map<String, Object> map = null;
        map = new HashMap<>();
        // String token = TokenUtils.genToken(loginUser);
        String token = JwtUtil.createToken(loginUser);

        map.put("token", token);
        map.put("user", loginUser);

        return new ResponseResult<Map<String, Object>>(200, "注册成功", map);
    }

    /**
     * 
     * @param user /(Integer id)
     * @return
     */
    @PostMapping("/userInfo")
    public ResponseResult<?> getUserInfo(@RequestBody User user) {
        //User resultUser = userService.getUserInfo();
        //number 和 identity不能为空
        if(user.getNumber() == null)
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "id为空", null);
        if(user.getIdentity() == null)
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "identity为空", null);
            
        User loginUser;
  
        // 根据不同登录身份进行查询
        switch(user.getIdentity()) {
            case("student") :
                loginUser = userService.selectStuInfoByNumber(user.getNumber()); 
                break;  
            case("teacher") :
                loginUser = userService.selectTchInfoByNumber(user.getNumber()); 
                break; 
            case("company") :
                loginUser = userService.selectCmpInfoByNumber(user.getNumber()); 
                break; 
            case("other") :
                loginUser = userService.selectOtherInfoByNumber(user.getNumber()); 
                break; 
            default :
                return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "identity不是指定类型值", null);
        }

        if(loginUser == null) {
            return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "未查询到此用户", null);
        }

        loginUser.setNumber(user.getNumber());
        loginUser.setIdentity(user.getIdentity());

        Map<String, Object> map = null;
        map = new HashMap<>();
        map.put("user", loginUser);

        return new ResponseResult<Map<String, Object>>(ResponseResult.CODE_SUCCESS, "获取用户信息成功", map);
    }

    @PostMapping("/saveUserInfo")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseResult<?> saveUserInfo(@RequestBody User user, @RequestHeader("token") String token) {
         if(user.getIdentity() == null){
             return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "identity为空", null);
         }
         if(user.getId() == null){
             return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "id为空", null);
         }
//        查询用户信息
//         User loginUser = userService.selectById(user.getId());
//         if (loginUser == null) {
//             return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "用户不存在", null);
//         }
//        System.out.println("login user :" + loginUser);

        User loginUser;
        //更新用户主表的信息
        userService.updateUserInfo(user);

        //插入各自身份对应的表
         switch (user.getIdentity()) {
             case("student"):
                 if (user.getStu() != null && user.getStu().getId().equals(user.getId())) {
                     userService.updateStuInfo(user.getStu());
                     loginUser = userService.selectStuInfoByNumber(user.getNumber());
                     break;
                 } else {
                     return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "stu信息有误", null);
                 }
             case("teacher"):
                 if (user.getTch() != null && user.getTch().getId().equals(user.getId())) {
                     userService.updateTchInfo(user.getTch());
                     loginUser = userService.selectTchInfoByNumber(user.getNumber());
                     break;
                 } else {
                     return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "tch信息有误", null);
                 }
             case("company"):
                 if (user.getCmp() != null && user.getCmp().getId().equals(user.getId())) {
                     userService.updateCmpInfo(user.getCmp());
                     loginUser = userService.selectCmpInfoByNumber(user.getNumber());
                     break;
                 } else {
                     return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "cmp信息有误", null);
                 }
             case("other"):
                 if (user.getOther() != null && user.getOther().getId().equals(user.getId())) {
                     userService.updateOtherInfo(user.getOther());
                     loginUser = userService.selectOtherInfoByNumber(user.getNumber());
                     break;
                 } else {
                     return new ResponseResult<Object>(ResponseResult.CODE_FAILED, "other信息有误", null);
                 }
             default:
                 return new ResponseResult<Object>(-1, "identity不是指定类型值", null);
         }

        //返回更新后的 user 信息
        Map<String, Object> map = null;
        map = new HashMap<>();
        // String token = TokenUtils.genToken(loginUser);
        map.put("user", loginUser);

        return new ResponseResult<Map<String, Object>>(200, "保存用户信息成功", map);
    }
}
