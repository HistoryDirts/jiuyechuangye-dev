package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    对应数据库中的user_stu表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserStu {
    private Integer id;
    private Integer number = 0;
    private String name = "学生用户";
    private String majorName = "无";
    private String specialty = "无";
    private String hobby = "无";
}

