
<template>
    <el-input class="input_box" prefix-icon="el-icon-search" v-model="context" placeholder="论坛"></el-input>
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
</template>


<script>
import Taro from '@tarojs/taro';
    export default {
        name:'NavMenu',
        data() {
            return {
                activeIndex: "pages/discover/discover"
            }
        },
        methods: {
            onClick(key, keyPath) {
                console.log(typeof(key), keyPath)
                this.activeIndex = key
                console.log(this.activeIndex)
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
        },
        
  }
</script>

<style>

</style>
