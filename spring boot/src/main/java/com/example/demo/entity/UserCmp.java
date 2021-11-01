package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCmp {
    private Integer id;
    private Integer number = 0;
    private String name = "企业用户";
    private String business = "无";
    private String linkman = "无";
    private String profile = "无";
}
