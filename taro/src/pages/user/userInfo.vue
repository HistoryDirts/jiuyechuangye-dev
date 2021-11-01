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
                <el-descriptions
                    title="用户基本信息"
                    direction="vertical"
                    :column="2"
                    border
                >
                    <el-descriptions-item label="ID">{{form.id}}</el-descriptions-item>
                    <el-descriptions-item label="用户名">{{form.name}}</el-descriptions-item>
                    <el-descriptions-item label="学/工号">{{form.number}}</el-descriptions-item>
                    <el-descriptions-item label="用户等级">{{form.level}}</el-descriptions-item>
                    <el-descriptions-item label="用户积分">{{form.score}}</el-descriptions-item>
                    <el-descriptions-item label="用户经验">{{form.exp}}</el-descriptions-item>
                    <el-descriptions-item label="国籍">{{form.nationality}}</el-descriptions-item>
                    <el-descriptions-item label="用户身份">{{form.identity}}</el-descriptions-item>
                </el-descriptions>
                <el-descriptions
                    title="用户介绍"
                    direction="vertical"
                    :column="1"
                    border
                >
                    <el-descriptions-item label="用户简介">{{form.intro}}</el-descriptions-item>
                </el-descriptions>
                <el-form>
                    <el-form-item class="submit_box0">
                        <el-button type="info" @click="edit">编辑</el-button>
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
import Taro from '@tarojs/taro';
    export default {
        name:'StuInfo',
        beforeMount() {
            // let userInfo = sessionStorage.getItem("user");
            // this.form = userInfo;
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
                    headPath: 'https://img1.baidu.com/it/u=4188564071,3195263640&fm=26&fmt=auto',
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
            edit(){
                Taro.navigateTo({
                    url:'pages/user/userInfoEdit'
                })
                this.$message({
                    type: "success",
                    message: '注意及时保存信息'
                })
            },
            backToUser(){
                Taro.navigateTo({
                    url:'pages/user/user'
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
        top: 20%;
        /* border: 1px solid; */
    }
    .header{
        height: 300px;
        background: #545c64;
    }
    .main_body{
        height: 1280px;
        background: rgb(234, 238, 234);
    }
    .input_box{
        width: 80%;
        position: relative;
        left: 10%;
    }
    .submit_box0{
        position: relative;
        left: 80%;
    }
</style>
