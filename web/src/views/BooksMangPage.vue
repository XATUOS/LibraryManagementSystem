<script setup lang="ts">

import {ref} from "vue";
import {Request} from "@/request/Request";
import {Data, Operate} from "@/model/Util";

const books = ref([]);
refreshBooks();

function refreshBooks() {
  Request.get("book/all", context => {
    books.value = context.data;
  })
}

const addBookFrom = ref({
  bno: undefined,
  bname: undefined,
  bauthor: undefined,
  bversion: undefined,
  bpress: undefined,
  binventory: undefined,
})
const showAddBookDialogFlag = ref(false);
const modifyBookFrom = ref({
  bno: undefined,
  bname: undefined,
  bauthor: undefined,
  bversion: undefined,
  bpress: undefined,
  binventory: undefined,
})
const showModifyBookDialogFlag = ref(false);
const showDelBookDialogFlag = ref(false);

function showModifyBookDialog(book) {
  modifyBookFrom.value = Data.copy(book);
  showModifyBookDialogFlag.value = true;
}

function cancelModifyBookDialog() {
  modifyBookFrom.value = {
    bno: undefined,
    bname: undefined,
    bauthor: undefined,
    bversion: undefined,
    bpress: undefined,
    binventory: undefined,
  }
  showModifyBookDialogFlag.value = false;
}

function showDelBookDialog(book) {
  modifyBookFrom.value = Data.copy(book);
  showDelBookDialogFlag.value = true;
}

function cancelDelBookDialog() {
  modifyBookFrom.value = {
    bno: undefined,
    bname: undefined,
    bauthor: undefined,
    bversion: undefined,
    bpress: undefined,
    binventory: undefined,
  }
  showDelBookDialogFlag.value = false;
}

function showAddBookDialog() {
  showAddBookDialogFlag.value = true;
}

function addBook() {
  Request.put("tea/add_book", addBookFrom.value, _ => {
    refreshBooks();
    addBookFrom.value = {
      bno: undefined,
      bname: undefined,
      bauthor: undefined,
      bversion: undefined,
      bpress: undefined,
      binventory: undefined,
    }
    showAddBookDialogFlag.value = false;
    Operate.success();
  });
}

function delBookdent() {
  Request.del(`tea/remove_book/${modifyBookFrom.value.bno}`, _ => {
    refreshBooks();
    cancelDelBookDialog();
    Operate.success();
  });
}

function modifyBookdent() {
  Request.post("tea/modify_book", modifyBookFrom.value, _ => {
    refreshBooks();
    cancelModifyBookDialog();
    Operate.success();
  });
}
</script>

<template>
  <div>
    <el-button type="success" @click="showAddBookDialog">新增</el-button>
    <el-table :data="books">
      <el-table-column prop="bno" label="编号" width="60"/>
      <el-table-column prop="bname" label="名称" width="200"/>
      <el-table-column prop="bauthor" label="书名" width="200"/>
      <el-table-column prop="bversion" label="版本" width="80"/>
      <el-table-column prop="bpress" label="出版社"/>
      <el-table-column prop="binventory" label="库存" width="60"/>
      <el-table-column label="操作" width="200">
        <template #default="{row}">
          <el-button-group>
            <el-button type="warning" size="small" @click="showModifyBookDialog(row)">修改</el-button>
            <el-button type="danger" size="small" @click="showDelBookDialog(row)">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>



    <el-dialog title="创建书籍" v-model="showAddBookDialogFlag" width="600px">
      <el-form model="addBookFrom" label-width="60px">
        <el-form-item label="编号">
          <el-input type="number" v-model="addBookFrom.bno"/>
        </el-form-item>
        <el-form-item label="书名">
          <el-input v-model="addBookFrom.bname"/>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="addBookFrom.bauthor"/>
        </el-form-item>
        <el-form-item label="版本">
          <el-input v-model="addBookFrom.bversion"/>
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="addBookFrom.bpress"/>
        </el-form-item>
        <el-form-item label="库存">
          <el-input type="number" v-model="addBookFrom.binventory"/>
        </el-form-item>
      </el-form>
      <el-footer>
        <el-button type="success" @click="addBook">提交</el-button>
        <el-button type="info" @click="showAddBookDialogFlag=false">取消</el-button>
      </el-footer>
    </el-dialog>

    <el-dialog title="修改书籍" v-model="showModifyBookDialogFlag" width="600px" @close="cancelModifyBookDialog">
      <el-form model="modifyBookFrom" label-width="60px">
        <el-form-item label="编号">
          <el-input type="number" disabled v-model="modifyBookFrom.bno"/>
        </el-form-item>
        <el-form-item label="书名">
          <el-input v-model="modifyBookFrom.bname"/>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="modifyBookFrom.bauthor"/>
        </el-form-item>
        <el-form-item label="版本">
          <el-input v-model="modifyBookFrom.bversion"/>
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="modifyBookFrom.bpress"/>
        </el-form-item>
        <el-form-item label="库存">
          <el-input type="number" v-model="modifyBookFrom.binventory"/>
        </el-form-item>
      </el-form>
      <el-footer>
        <el-button type="success" @click="modifyBookdent">提交</el-button>
        <el-button type="info" @click="cancelModifyBookDialog">取消</el-button>
      </el-footer>
    </el-dialog>

    <el-dialog title="删除书籍" :model-value="showDelBookDialogFlag" @close="cancelDelBookDialog">
      <el-form model="modifyBookFrom" label-width="60px">
        <el-form-item label="编号">
          <el-input type="number" disabled v-model="modifyBookFrom.bno"/>
        </el-form-item>
        <el-form-item label="书名">
          <el-input disabled v-model="modifyBookFrom.bname"/>
        </el-form-item>
        <el-form-item label="作者">
          <el-input disabled v-model="modifyBookFrom.bauthor"/>
        </el-form-item>
        <el-form-item label="版本">
          <el-input disabled v-model="modifyBookFrom.bversion"/>
        </el-form-item>
        <el-form-item label="出版社">
          <el-input disabled v-model="modifyBookFrom.bpress"/>
        </el-form-item>
        <el-form-item label="库存">
          <el-input type="number" disabled v-model="modifyBookFrom.binventory"/>
        </el-form-item>
      </el-form>
      <el-footer>
        <el-button type="danger" @click="delBookdent">确认</el-button>
        <el-button type="info" @click="cancelDelBookDialog">取消</el-button>
      </el-footer>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">

</style>