<template>
    <div style="background:gray">
        <div class="header">
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
                <el-menu style="border-radius: 35px; background: rgb(234, 238, 234)">
                    <el-menu-item style="width: 95px; height: 60px; float: left; line-height: 60px; text-align: center">{{form.name}}</el-menu-item>
                    <el-menu-item style="width: 95px; height: 60px; float: left; line-height: 60px; text-align: center">{{form.level}}</el-menu-item>
                    <el-menu-item style="width: 95px; height: 60px; float: left; line-height: 60px; text-align: center">{{form.score}}</el-menu-item>
                </el-menu>
            </div>
        </div>
        <div class="main_body">
            <div class="user_opts">
                <el-menu
                :default-active="activeIndex"
                @select="checkInfo"
                >
                    <el-menu-item index="pages/user/userInfo1" style="height: 60px; margin:10px 10px 5px 10px; border: 1px solid; line-height: 60px; background:#545c64"><i class="el-icon-setting"></i>基本信息</el-menu-item>
                    <el-menu-item index="pages/user/userInfo2" style="height: 60px; margin:10px 10px 5px 10px; border: 1px solid; line-height: 60px; background:#545c64"><i class="el-icon-setting"></i>其他信息</el-menu-item>
                    <el-menu-item index="pages/setting/setting" style="height: 60px; margin:5px 10px 5px 10px; border: 1px solid; line-height: 60px; background:#545c64"><i class="el-icon-s-operation"></i>设置</el-menu-item>
                    <el-menu-item index="pages/login/login" style="height: 60px; margin:5px 10px 10px 10px; border: 1px solid; line-height: 60px; background:#545c64"><i class="el-icon-switch-button"></i>注销</el-menu-item>
                </el-menu>
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
        name:'NavMenu',
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
        beforeMount() {
            // let userInfo = sessionStorage.getItem("user");
            // this.form = userInfo;
        },  // 将后端返回的user先赋值给userInfo后赋值给form
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
            checkInfo(key, keyPath){
                console.log(typeof(key), keyPath)
                if(key =="pages/user/userInfo1"){
                    Taro.navigateTo({
                        url:'pages/user/userInfo'
                    })
                }else if(key =="pages/user/userInfo2"){
                    if(this.form.identity == 'student'){
                        Taro.navigateTo({
                        url:'pages/user/stuInfo/stuInfo'
                    })
                    }else if(this.form.identity == 'teacher'){
                        Taro.navigateTo({
                        url:'pages/user/tchInfo/tchInfo'
                    })
                    }else if(this.form.identity == 'company'){
                        Taro.navigateTo({
                        url:'pages/user/cmpInfo/cmpInfo'
                    })
                    }else{
                        Taro.navigateTo({
                        url:'pages/user/othInfo/othInfo'
                    })
                    }   // 根据form中的identity值选择路由

                    // Taro.navigateTo({
                    //     url:'pages/user/othInfo/othInfo'
                    // })
                }else if(key =="pages/setting/setting"){
                    Taro.navigateTo({
                        url:'pages/setting/setting'
                    })
                    this.$message({
                        type: "success",
                        message: '注意及时保存设置'
                    })
                }else{
                    Taro.navigateTo({
                        url:'pages/login/login'
                    })
                    this.$message({
                        type: "warnning",
                        message: '已退出登录'
                    })
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
    .user_opts{
        width: 88%;
        position: absolute;
        left: 6%;
        top: 25%;

    }
    .header{
        /* position: relative; */
        height: 300px;
        background: #545c64;
    }
    .main_body{
        height: 1280px;
        background: rgb(234, 238, 234);
        border: 1px solid;
    }
</style>
