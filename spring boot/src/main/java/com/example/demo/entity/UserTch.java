package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTch {
    private Integer id;
    private Integer number = 0;
    private String name = "教师用户";
    private String guidance = "无";
    private String specialty = "无";
    private String hobby = "无";
}
