<template>
    <div class="box">
        <div class="logo">
          <image src='./../../assets/lanLOGO.png' alt="Reg logo" style="height:100px; weight: 240px; padding:20px 30px 0px 30px;" />
        </div>
        <div style="width: 400px; margin: 100px auto">
            <div style="font-size: 30px; text-align: center; padding: 30px 0">欢迎登录</div>
            <el-form ref="form" :model="form" size="normal" :rules="rules">
                <el-form-item class="input_box" prop="number">
                    <el-input prefix-icon="el-icon-user" v-model="form.number" maxlength="12" placeholder="请输入学号/工号(12位以内)"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input class="input_box" prefix-icon="el-icon-lock" maxlength="12" minlength="6" v-model="form.password" show-password placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                <div style="display: flex">
                    <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%; position: relative; left:10%" placeholder="请输入验证码"></el-input>
                    <ValidCode style="position: relative; right:-15%" @input="createValidCode" />
                </div>
                </el-form-item>
                <el-form-item label="用户身份" class="input_box">
                    <el-select style="float: right; right: 20%; width: 60%" v-model="form.identity" placeholder="请选择身份">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                        </el-option>
                    </el-select>
            </el-form-item>
                <el-form-item>
                    <el-button class="input_box" type="primary" @click="login">登 录</el-button>
                </el-form-item>
                <el-form-item class="input_box"><el-button type="text" @click="turnToRegister()">前往注册 >> </el-button></el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import request from "../../utils/request.js";
import ValidCode from '../../components/ValidCode.vue';
import Taro from '@tarojs/taro';

export default {
    name: "Login",
    components: {
      ValidCode
    },
    beforeMount() {
        this.form.number = null;
    },
    data() {
        return {
            form: {
                number:-1,
                password:'',
                identity:'student',
                validCode:''
            },
            rules: {
                number: [
                  {required: true, message: '请输入学号/工号', trigger: 'blur'},
                ],
                password: [
                  {required: true, message: '请输入密码', trigger: 'blur'},
                ],
            },
            trueValidCode: ''
            // 加背景图片
            // bg: {
            //   backgroundImage: "url(" + require("@/assets/bg.jpg") + ")",
            //   backgroundRepeat: "no-repeat",
            //   backgroundSize: "100% 100%"
            // }
        }
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
    created() {
        sessionStorage.removeItem("user")
    },
    methods: {
      // 接收验证码组件提交的 4位验证码
      createValidCode(data) {
          this.trueValidCode = data
      },
      turnToRegister(){
          Taro.navigateTo({
              url:'/pages/register/register'
          })
      },
      check(){
            if(this.form.number == null || this.form.number.length < 8 || this.form.number.length > 10){
                  this.$message({
                  type: "error",
                  message: '学/工号8-10位！'
                  })
                return false
            }else if(this.form.password.length < 6 || this.form.password.length > 12){
                this.$message({
                    type: 'error',
                    message: '密码6-12个字符！'
                })
                return false
            }else{
                return true
            }
        },
      login() {
          let postdata = {number:this.form.number, password:this.form.password, identity:this.form.identity} 
          if(this.check()){ 
              if (!this.form.validCode) {
                  this.$message.error("请填写验证码")
                  return
              }else if((this.form.validCode).toLowerCase() !== (this.trueValidCode).toLowerCase()) {
                  console.log(this.form.validCode+':'+this.trueValidCode)
                  this.$message.error("验证码错误")
                  return
              }else{
                  request.post("/user/login", postdata).then(res => {
                      if (res.code === '200') {
                          this.$message({
                          type: "success",
                          message: "登录成功"
                      })
                        sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
                        sessionStorage.setItem("token",JSON.stringify(res.data.token))
                        Taro.navigateTo({
                            url:'/pages/home/home'
                        }) //登录成功之后进行页面的跳转，跳转到主页
                      } else {
                          this.$message({
                          type: "error",
                          message: '登录失败！'
                      })
                      }
                  })
                return
              }          
          }
      }
    }
}
</script>

<style scoped>
    .logo{
        position: absolute;
        left: 5%;
    }
    .box{
        width: 100%; 
        height: 100vh; 
        overflow: hidden;
        background: rgb(237, 238, 235) no-repeat;
        background-size: 100% 100%;
    }
    .input_box{
        width: 80%;
        position: relative;
        left: 10%;
    }
</style>
