package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name = "新用户";    //用户名
    private String password = "123456";
    private Integer number = 0; //学号/工号
    private Integer level = 1;  //用户等级
    private Integer score = 0;  //用户积分
    private Integer exp = 0;    //用户经验值
    private String intro = "无";   //用户简介
    private String nationality = "中国";//国籍
    private String headPath = "./headshot";//头像路径
    private String identity = "student";//用户身份

    private UserStu stu;    //学生用户信息
    private UserTch tch;    //教师用户信息
    private UserCmp cmp;    //企业用户信息
    private UserOther other;//其它用户信息
}
