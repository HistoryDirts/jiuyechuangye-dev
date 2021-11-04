package com.example.demo.service;
import com.example.demo.entity.*;

import java.util.List;


public interface UserService {
    User selectById(Integer id);

    User login(User user);

    List<User> findAll();

    User selectByName(String name);

    void insert(User user);

    void insertToStudent(UserStu userStu);

    void insertToIdentity(User_Identity user_identity);

    void insertToTeacher(UserTch userTch);

    void insertToCompany(UserCmp userCmp);

    void insertToOther(UserOther userOther);

    User getUserInfo(User user);


    User selectStuInfoByNumber(Integer number);

    User selectTchInfoByNumber(Integer number);

    User selectCmpInfoByNumber(Integer number);

    User selectOtherInfoByNumber(Integer number);

    void updateUserInfo(User user);

    void updateStuInfo(UserStu stu);

    void updateTchInfo(UserTch tch);

    void updateCmpInfo(UserCmp cmp);

    void updateOtherInfo(UserOther other);
}
