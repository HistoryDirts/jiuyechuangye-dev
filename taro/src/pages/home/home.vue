
<template>
    <el-input class="input_box" prefix-icon="el-icon-search" v-model="context" placeholder="首页"></el-input>
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
    <el-carousel indicator-position="outside" height="200px" style="width: 90%; left: 5%">
        <el-carousel-item v-for="item in itemSet" :key="item">
            <image :src="item.src" alt="Reg logo" style="width: 100%; height: 100%" />
            <h3>{{ item.picName }}</h3>
        </el-carousel-item>
    </el-carousel>
    <div class="infinite-list-wrapper" style="width: 88%; height: 400px; position: absolute; left: 6%; overflow: auto; margin: 10px 0 0 0">
        <ul
        v-infinite-scroll="load"
        class="list"
        infinite-scroll-disabled="disabled"
        >
            <li v-for="i in count" :key="i" class="content_text">
                <h3>标题{{ i }}</h3>
                <span>1323213548646515</span>
            </li>
        </ul>
        <p v-if="loading">Loading...</p>
        <p v-if="noMore">No more</p>
    </div>
</template>


<script>
import Taro from '@tarojs/taro';
import { defineComponent, ref, computed } from 'vue'
    export default defineComponent({
        name:'NavMenu',
        data() {
            return {
                activeIndex: "pages/home/home",
                context:'',
                itemSet:[
                    {picName: '兰州交通大学', src:require('./../../assets/img1.jpg')},
                    {picName: '兰州交通大学', src:require('./../../assets/img1.jpg')},
                    {picName: '兰州交通大学', src:require('./../../assets/img1.jpg')},
                    {picName: '兰州交通大学', src:require('./../../assets/img1.jpg')},
                ]
            }
        },
        setup() {
            const count = ref(10)
            const loading = ref(false)
            const noMore = computed(() => count.value >= 20)
            const disabled = computed(() => loading.value || noMore.value)
            const load = () => {
            loading.value = true
            setTimeout(() => {
                count.value += 2
                loading.value = false
            }, 2000)
            }
            return {
                count,
                loading,
                noMore,
                disabled,
                load,
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
        
  })
</script>

<style>
    .input_box{
        width: 80%; 
        margin: 100px auto;
        position: relative;
        left: 10%;
    }
    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }
    .el-carousel__item h3 {
        width: 100%;
        height: 10%;
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        margin: 0;
        text-align: center;
        position: absolute;
        top: 90%;
    }
    .el-carousel__item:nth-child(2n + 1) {
        background-color: #d3dce6;
    }
    .infinite-list-wrapper {
        height: 300px;
        text-align: center;
    }
    .list {
        padding: 0;
        margin: 0;
        list-style: none;
    }
    .content_text {
        height: 155px;
        background: #99a9bf;
        margin: 5px 0 5px 0;
        border: 1px solid;
    }
    .content_text h3{
        position: relative;
        width: 100%;
        height: 50px;
        text-align: left;
        font-size: 5px;
        border: 1px solid;
    }
    .content_text span{
        position: absolute;
        width: 100%;
        height: 100px;
        left: 0%;
        font-size: 5px;
        border: 1px solid;
    }
</style>
