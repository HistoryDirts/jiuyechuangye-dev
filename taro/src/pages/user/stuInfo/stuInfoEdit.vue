<template>
    <div style="background:gray">
        <div class="header">
            <el-button-group  style="float: left">
                <el-button class="back_box" type="info" icon="el-icon-arrow-left" @click="backToUser">返回</el-button>
            </el-button-group>
            <div class="user_fig">
                <el-upload
                    action="http://localhost:9090/user/import"
                    :on-success="handleUploadSuccess"
                    :show-file-list=false
                    :limit="1"
                    accept='.jpg'
                    style="display: inline-block;"
                >
                    <el-button style="width: 70px; height: 70px; float: left; border-radius: 35px;"></el-button>
                    <image :src="form.headPath" alt="Reg logo" style="width: 70px; height: 70px; border-radius: 35px; left:-70px; background-size: contain"/>
                </el-upload>
            </div>
            <div class="user_info">
                <el-menu style="border-radius: 25px; background: rgb(234, 238, 234)">
                    <el-menu-item style="width: 95px; height: 60px; float: left; line-height: 60px; text-align: center">{{form.name}}</el-menu-item>
                    <el-menu-item style="width: 95px; height: 60px; float: left; line-height: 60px; text-align: center">{{form.level}}</el-menu-item>
                    <el-menu-item style="width: 95px; height: 60px; float: left; line-height: 60px; text-align: center">{{form.score}}</el-menu-item>
                </el-menu>
            </div>
        </div>
        <div class="main_body">
            <div class="user_opt">
                <span style="position: relative; left: 10%">学生其他信息</span>
                <el-form ref="form" :model="form" size="normal" :rules="rules">
                    <el-form-item style="position: relative; left: 10%" prop="name">
                        <span style="float: left">ID：</span>
                        <el-input style="float: right; right: 20%; width: 60%" disabled prefix-icon="el-icon-s-custom" v-model="form.id" placeholder="ID不可修改"></el-input>
                    </el-form-item>
                    <el-form-item style="position: relative; left: 10%" prop="name">
                        <span style="float: left">用户名：</span>
                        <el-input style="float: right; right: 20%; width: 60%" maxlength="12" minlength="2" prefix-icon="el-icon-user-solid" v-model="form.name" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item style="position: relative; left: 10%" prop="number">
                        <span style="float: left">学/工号：</span>
                        <el-input style="float: right; right: 20%; width: 60%" maxlength="10" minlength="8" prefix-icon="el-icon-user" v-model="form.number" placeholder="请输入学号/工号(12位以内)"></el-input>
                    </el-form-item>
                    <el-form-item style="position: relative; left: 10%" prop="name">
                        <span style="float: left">专业：</span>
                        <el-input style="float: right; right: 20%; width: 60%" prefix-icon="el-icon-school" v-model="form.stu.majorName" placeholder="请输入专业"></el-input>
                    </el-form-item>
                    <el-form-item style="position: relative; left: 10%" prop="number">
                        <span style="float: left">特长：</span>
                        <el-input style="float: right; right: 20%; width: 60%" prefix-icon="el-icon-office-building" v-model="form.stu.specialty" placeholder="请输入特长"></el-input>
                    </el-form-item>
                    <el-form-item style="position: relative; left: 10%" prop="name">
                        <span style="float: left">爱好：</span>
                        <el-input style="float: right; right: 20%; width: 60%" prefix-icon="el-icon-s-opportunity" v-model="form.stu.hobby" placeholder="请输入爱好"></el-input>
                    </el-form-item>
                </el-form>
                <el-form>
                    <el-form-item class="submit_box">
                        <el-button type="info" @click="save">保存</el-button>
                        <el-button type="info" @click="submit">提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <div style="width: 100%; position:absolute; bottom:0%">
            <el-menu
                :default-active="activeIndex"
                class="el-menu-demo"
                mode="horizontal"
                @select="onClick"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                >
                <el-menu-item index="pages/home/home" style="width: 25%; float:left"><i class="el-icon-menu"></i>首页</el-menu-item>
                <el-menu-item index="pages/discover/discover" style="width: 25%; float:left"><i class="el-icon-s-promotion"></i>发现</el-menu-item>
                <el-menu-item index="pages/column/column" style="width: 25%; float:left"><i class="el-icon-s-comment"></i>广场</el-menu-item>
                <el-menu-item index="pages/user/user" style="width: 25%; float:left"><i class="el-icon-user-solid"></i>我的</el-menu-item>
            </el-menu>
        </div>
    </div>
</template>


<script>
import Taro, { Current } from '@tarojs/taro';
import request from "./../../../utils/request.js"

    export default  {
        name:'CmpInfo',
        beforeMount() {
            // let userInfo = sessionStorage.getItem("user");
            // this.form = userInfo;
        },
        setup(){
            return {
                options: ([
                    {
                        value: 'student',
                        label: '学生'
                    },
                    {
                        value: 'teacher',
                        label: '教师'
                    },
                    {
                        value: 'company',
                        label: '企业'
                    },
                    {
                        value: 'other',
                        label: '其他'
                    }
                ]),
            }
        },
        data() {
            return {
                activeIndex: "pages/user/user",
                form: {
                    id: 123,
                    name: '123',
                    number: 123,
                    level:1,
                    score:99,
                    exp:100,
                    intro:'hello',
                    nationality: 'china',
                    identity: 'student',
                    headPath: 'https://img1.baidu.com/it/u=4188564071,3195263640&fm=26&fmt=auto', // 问题项
                    stu:{
                        id: 123,
                        name: '123',
                        number: 123,
                        majorName: 'computer',
                        specialty: 'c++',
                        hobby: 'game'
                    }
                }
            }
        },
        methods: {
            onClick(key, keyPath) {
                console.log(typeof(key), keyPath)
                if(key == "pages/home/home"){
                    Taro.navigateTo({
                    url:'pages/home/home'
                })
                this.$message({
                    type: "success",
                    message: '欢迎来到首页！'
                })
                }else if(key == "pages/discover/discover"){
                    Taro.navigateTo({
                    url:'pages/discover/discover'
                })
                this.$message({
                    type: "success",
                    message: '欢迎来到论坛！'
                })
                }else if(key == "pages/column/column"){
                    Taro.navigateTo({
                    url:'pages/column/column'
                })
                this.$message({
                    type: "success",
                    message: '欢迎来到广场！'
                })
                }else{
                    Taro.navigateTo({
                    url:'pages/user/user'
                })
                this.$message({
                    type: "success",
                    message: '欢迎来到个人主页！'
                })
                }
            },
            save(){
                let currentdata = this.form
                sessionStorage.removeItem("user")   // 删除之前的user数据
                sessionStorage.setItem("user", JSON.stringify(currentdata))  // 本地缓存user
                // console.log(sessionStorage.user)
            },
            submit(){
                let postdata= this.form
                request.post("/user/login", postdata).then(res => {
                      if (res.code === '200') {
                          this.$message({
                          type: "success",
                          message: "提交成功"
                      })
                        sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
                        // sessionStorage.setItem("token",JSON.stringify(res.data.token))
                        Taro.navigateTo({
                            url:'/pages/user/user'
                        }) //登录成功之后进行页面的跳转，跳转到主页
                      } else {
                          this.$message({
                          type: "error",
                          message: '提交失败！'
                      })
                      }
                  })
                return
            },
            backToUser(){
                Taro.navigateTo({
                    url:'pages/user/stuInfo/stuInfo'
                })
            },
            handleUploadSuccess(res) {
                if (res.code === "0") {
                    this.$message.success("导入成功")
                    this.load()
                }
            }
        },
        
  }
</script>

<style>
    .user_info{
        width: 70%;
        position: absolute;
        float: left;
        right: 6%;
        top:10%;
    }
    .user_fig{
        position: absolute;
        float: left;
        left: 4%;
        top:9.5%;
    }
    .user_opt{
        width: 88%;
        position: absolute;
        left: 6%;
        top: 25%;
        /* border: 1px solid; */
    }
    .header{
        height: 300px;
        background: #545c64;
    }
    .main_body{
        height: 1280px;
        background: rgb(234, 238, 234);
        border:1px solid;
    }
    .submit_box{
        position: relative;
        left: 60%;
        top: 120px;
    }
</style>
