<template>
  <div class="login">
    <el-header height="100vh"></el-header>
    <el-row justify="center">
      <el-card class="card" @keyup.enter="login">
        <el-header height="5vh">
          <el-row justify="center">
            <el-text size="large">登录</el-text>
          </el-row>
        </el-header>
        <el-form model="loginForm" label-position="right" label-width="60px">
          <el-form-item label="身份">
            <el-select v-model="optionValue" placeholder="Select" style="width: 170px">
              <el-option v-for="option in loginOption" :key="option.value" :value="option.value" :label="option.label"/>
            </el-select>
          </el-form-item>
          <el-form-item label="账号">
            <el-input v-model="loginForm.uid" style="width: 170px"/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="loginForm.password" show-password style="width: 170px"/>
          </el-form-item>
        </el-form>
        <el-footer height="5vh">
          <el-row justify="center">
            <el-button type="success" @click="login()" :disabled="optionValue==void 0">登录</el-button>
          </el-row>
        </el-footer>
      </el-card>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import {ref, Ref} from "vue";
import {Request} from "@/request/Request";
import {Operate} from "@/model/Util";
import router, {refreshRouter} from "@/router";

const optionValue = ref();
const loginOption: { value: number, label: string }[] = [
  {value: 0, label: "学生"},
  {value: 1, label: "教师"}
]

const loginForm: Ref<{ uid: number, password: string }> = ref({
  uid: ref(),
  password: ref()
});

function login() {
  if (optionValue.value == 0) studentLogin();
  else teacherLogin();
}

function teacherLogin() {
  loginFunc("tea");
}

function studentLogin() {
  loginFunc("stu");
}

function loginFunc(type: string) {
  Request.post(`${type}/login`, loginForm.value, context => {
    if (context.code == 200) {
      localStorage.setItem('userType', type);
      localStorage.setItem('uid', loginForm.value.uid.toString());
      localStorage.setItem('token', context.data.token);
      localStorage.setItem('username', context.data.username);
      router.push({path: "/"});
      refreshRouter()
    } else {
      Operate.error(context.msg);
    }
  }, false);
}
</script>

<style scoped lang="scss">
.login {
  height: 100vh;
  background: linear-gradient(to right, #c7d2fe, #fecaca, #fef9c3);
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  width: 300px;
  height: 260px;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(3vh);
}
</style>
