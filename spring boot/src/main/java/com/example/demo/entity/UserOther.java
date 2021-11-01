package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOther {
    private Integer id;
    private Integer number = 0;
    private String name = "其它用户";
    private String profile = "无";
    private String specialty = "无";
    private String hobby = "无";
}
