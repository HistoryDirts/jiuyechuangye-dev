package com.example.demo.mapper;


import com.example.demo.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper{

    User login(User user);
    public List<User> findAll();

    User selectByName(String name);

    User selectById(Integer id);

    void insert(User user);

    void insertToStudent(UserStu userStu);

    void insertToIdentity(User_Identity user_identity);

    void insertToTeacher(UserTch userTch);

    void insertToCompany(UserCmp userCmp);

    void insertToOther(UserOther userOther);

    User getUserInfo(User user);

    //根据学号查询学生用户信息
    User selectStuInfoByNumber(Integer number);

    //根据工号查询教师用户信息
    User selectTchInfoByNumber(Integer number);

    //根据编号查询企业用户信息
    User selectCmpInfoByNumber(Integer number);

    //根据编号查询其它用户信息
    User selectOtherInfoByNumber(Integer number);

    //更新用户主表
    void updateUserInfo(User user);

    //更新学生信息表
    void updateStuInfo(UserStu stu);

    //更新教师信息表
    void updateTchInfo(UserTch tch);

    //更新企业信息表
    void updateCmpInfo(UserCmp cmp);

    //更新其他人员信息表
    void updateOtherInfo(UserOther other);
}
