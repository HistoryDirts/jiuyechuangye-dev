<template>
    <div class="box">
        <div class="logo">
            <image src='./../../assets/lanLOGO.png' alt="Reg logo" style="height:100px; weight: 240px; padding:20px 30px 0px 30px;" />
        </div>
        <div style="width: 400px; margin: 100px auto">
            <div style="font-size: 30px; text-align: center; padding: 30px 0"></div>
            <el-form ref="form" :model="form" size="normal" :rules="rules">
                <el-form-item class="input_box" prop="name">
                    <el-input prefix-icon="el-icon-user-solid" v-model="form.name" maxlength="12" minlength="2" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item class="input_box" prop="number">
                    <el-input prefix-icon="el-icon-user" v-model="form.number" maxlength="10" minlength="8" placeholder="请输入学号/工号(12位以内)"></el-input>
                </el-form-item>
                <el-form-item class="input_box" prop="password">
                    <el-input prefix-icon="el-icon-lock" v-model="form.password" maxlength="12" minlength="6" show-password placeholder="请输入密码(恰好12位)"></el-input>
                </el-form-item>
                <el-form-item class="input_box" prop="confirm">
                    <el-input prefix-icon="el-icon-lock" v-model="form.confirm" maxlength="12" minlength="6" show-password placeholder="请确认密码(恰好12位)"></el-input>
                </el-form-item>
                <div style="display: flex">
                    <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%; position: relative; left:10%" placeholder="请输入验证码"></el-input>
                    <ValidCode style="position: relative; right:-15%" @input="createValidCode" />
                </div>
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
                    <el-button class="input_box" type="primary" @click="register">注册</el-button>
                </el-form-item>
                <el-form-item class="input_box"><el-button type="text" @click="turnToLogin">前往登录 >> </el-button></el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import request from "../../utils/request.js";
import ValidCode from '../../components/ValidCode.vue';
import Taro from '@tarojs/taro';

export default {
    name: "Register",
    components: {
        ValidCode
    },
    beforeMount() {
          this.form.number = null;
      },
    data() {
        return {
            form: {
                name:'',
                number:-1,
                password:'',
                confirm:'',
                identity:'student',
                ValidCode:''
            }, 
            rules: {
                name: [
                  {required: true, message: '请输入用户名', trigger: 'blur'},
                ],
                number: [
                  {required: true, message: '请输入学号/工号', trigger: 'blur'},
                ],
                password: [
                  {required: true, message: '请输入密码', trigger: 'blur'},
                ],
                confirm: [
                  {required: true, message: '请确认密码', trigger: 'blur'},
                ],
            },
            trueValidCode:''
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
    methods: {
        createValidCode(data) {
            this.trueValidCode = data
        },
        turnToLogin(){
            Taro.navigateTo({
                url:'/pages/login/login'
            })
        },
        check(){
            if(this.form.name == null || this.form.name.length < 2 || this.form.name.length > 12){
                this.$message({
                type: "error",
                message: '用户名2-12个字符！'
                })
                return false
            }else if(this.form.number == null || this.form.number.length < 8 || this.form.number.length > 10){
                this.$message({
                type: "error",
                message: '学/工号8-10位！'
                })
                return false
            }else if(this.form.number == null || this.form.password.length < 6 || this.form.password.length > 12){
                this.$message({
                type: "error",
                message: '密码6-12个字符！'
                })
                return false
            }else if (this.form.password != this.form.confirm) {
                this.$message({
                type: "error",
                message: '2次密码输入不一致！'
                })
                return false
            }else if (!this.form.validCode) {
                this.$message.error("请填写验证码")
                return
            }else if((this.form.validCode).toLowerCase() !== (this.trueValidCode).toLowerCase()) {
                console.log(this.form.validCode+':'+this.trueValidCode)
                this.$message.error("验证码错误")
                return
            }else{
                return true
            }
        },
      register() {
          if(this.check()){
              this.$refs['form'].validate((valid) => {
                  if (valid) {
                      // 打包上传数据
                      let postdata = {name: this.form.name, number: this.form.number, password: this.form.password, identity:this.form.identity}

                      request.post("/user/register", postdata).then(res => {
                        if (res.code === '200') {
                            this.$message({
                                type: "success",
                                message: "注册成功"
                            })
                            sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
                            sessionStorage.setItem("token",JSON.stringify(res.data.token))
                            Taro.navigateTo({
                                url:'/pages/home/home'
                            })  //登录成功之后进行页面的跳转，跳转到主页
                            } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                      })
                  }
              })
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
        background-size: cover;
    }
    .input_box{
        width: 80%;
        position: relative;
        left: 10%;
    }
</style>
