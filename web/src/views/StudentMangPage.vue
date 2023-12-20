<script setup lang="ts">
import {ref} from "vue";
import {Request} from "@/request/Request";
import {Data, Operate} from "@/model/Util";

const students = ref([]);
refreshStudents();

function refreshStudents() {
  Request.get("tea/all_stu", context => {
    students.value = context.data;
  })
}

const addStuFrom = ref({
  sno: undefined,
  sname: undefined,
  ssex: undefined,
  smajor: undefined,
  scollege: undefined,
  spassword: undefined
});
const showAddStuDialogFlag = ref(false);

function showAddStuDialog() {
  showAddStuDialogFlag.value = true;
}


const modifyStuFrom = ref({
  sno: undefined,
  sname: undefined,
  ssex: undefined,
  smajor: undefined,
  scollege: undefined,
  spassword: undefined
});
const showModifyStuDialogFlag = ref(false);

function showModifyStuDialog(student) {
  modifyStuFrom.value = Data.copy(student);
  showModifyStuDialogFlag.value = true;
}

function cancelModifyStuDialog() {
  modifyStuFrom.value = {
    sno: undefined,
    sname: undefined,
    ssex: undefined,
    smajor: undefined,
    scollege: undefined,
    spassword: undefined
  }
  showModifyStuDialogFlag.value = false;
}

const showDelStuDialogFlag = ref(false);

function showDelStuDialog(student) {
  modifyStuFrom.value = Data.copy(student);
  showDelStuDialogFlag.value = true;
}

function cancelDelStuDialog() {
  modifyStuFrom.value = {
    sno: undefined,
    sname: undefined,
    ssex: undefined,
    smajor: undefined,
    scollege: undefined,
    spassword: undefined
  }
  showDelStuDialogFlag.value = false;
}

function addStudent() {
  Request.put("tea/add_stu", addStuFrom.value, _ => {
    refreshStudents();
    addStuFrom.value = {
      sno: undefined,
      sname: undefined,
      ssex: undefined,
      smajor: undefined,
      scollege: undefined,
      spassword: undefined
    }
    showAddStuDialogFlag.value = false;
    Operate.success();
  });
}

function delStudent() {
  Request.del(`tea/remove_stu/${modifyStuFrom.value.sno}`, _ => {
    refreshStudents();
    cancelDelStuDialog();
    Operate.success();
  });
}

function modifyStudent() {
  Request.post("tea/modify_stu", modifyStuFrom.value, _ => {
    refreshStudents();
    cancelModifyStuDialog();
    Operate.success();
  });
}

</script>

<template>
  <div>
    <el-button type="success" @click="showAddStuDialog">新增</el-button>
    <el-table :data="students">
      <el-table-column prop="sno" label="学号" width="120"/>
      <el-table-column prop="sname" label="姓名" width="80"/>
      <el-table-column prop="ssex" label="性别" width="60"/>
      <el-table-column prop="smajor" label="专业"/>
      <el-table-column prop="scollege" label="学院"/>
      <el-table-column label="操作" width="200">
        <template #default="{row}">
          <el-button-group>
            <el-button type="warning" size="small" @click="showModifyStuDialog(row)">修改</el-button>
            <el-button type="danger" size="small" @click="showDelStuDialog(row)">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="创建学生" v-model="showAddStuDialogFlag" width="600px">
      <el-form model="addStuFrom">
        <el-form-item label="学号">
          <el-input type="number" v-model="addStuFrom.sno"/>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="addStuFrom.sname"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="addStuFrom.ssex"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="addStuFrom.smajor"/>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="addStuFrom.scollege"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input show-password v-model="addStuFrom.spassword"/>
        </el-form-item>
      </el-form>
      <el-footer>
        <el-button type="success" @click="addStudent">提交</el-button>
        <el-button type="info" @click="showAddStuDialogFlag=false">取消</el-button>
      </el-footer>
    </el-dialog>

    <el-dialog title="修改学生" v-model="showModifyStuDialogFlag" width="600px" @close="cancelModifyStuDialog">
      <el-form model="modifyStuFrom">
        <el-form-item label="学号">
          <el-input type="number" disabled v-model="modifyStuFrom.sno"/>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="modifyStuFrom.sname"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="modifyStuFrom.ssex"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="modifyStuFrom.smajor"/>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="modifyStuFrom.scollege"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input show-password v-model="modifyStuFrom.spassword"/>
        </el-form-item>
      </el-form>
      <el-footer>
        <el-button type="success" @click="modifyStudent">提交</el-button>
        <el-button type="info" @click="cancelModifyStuDialog">取消</el-button>
      </el-footer>
    </el-dialog>

    <el-dialog title="删除学生" :model-value="showDelStuDialogFlag" @close="cancelDelStuDialog">
      <el-form model="modifyStuFrom">
        <el-form-item label="学号">
          <el-input type="number" disabled v-model="modifyStuFrom.sno"/>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input disabled v-model="modifyStuFrom.sname"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-input disabled v-model="modifyStuFrom.ssex"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input disabled v-model="modifyStuFrom.smajor"/>
        </el-form-item>
        <el-form-item label="学院">
          <el-input disabled v-model="modifyStuFrom.scollege"/>
        </el-form-item>
      </el-form>
      <el-footer>
        <el-button type="danger" @click="delStudent">确认</el-button>
        <el-button type="info" @click="cancelDelStuDialog">取消</el-button>
      </el-footer>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">

</style>