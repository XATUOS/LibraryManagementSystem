<script setup lang="ts">
import {ref} from "vue";
import {Request} from "@/request/Request";
import {Operate} from "@/model/Util";

const bookList = ref([]);
const borrowings = ref([]);
refreshBookList();
refreshBorrowings();

function refreshBookList() {
  Request.get("stu/book_list/get", context => {
    bookList.value = context.data;
  });
}

function removeBookList(book: any) {
  Request.post("stu/book_list/remove", {
    uid: Number.parseInt(localStorage.getItem("uid")!),
    bid: book.bno
  }, _ => {
    Operate.success();
    refreshBookList();
  })
}

function refreshBorrowings() {
  Request.get("stu/borrowing/get", context => {
    borrowings.value = context.data;
  });
}

function addBorrowing(book: any) {
  Request.post("stu/borrowing/add", {
    uid: Number.parseInt(localStorage.getItem("uid")!),
    bid: book.bno
  }, _ => {
    Operate.success();
    refreshBookList();
    refreshBorrowings();
  })
}

function removeBorrowing(book: any) {
  Request.post("stu/borrowing/remove", {
    uid: Number.parseInt(localStorage.getItem("uid")!),
    bid: book.id
  }, _ => {
    Operate.success();
    refreshBookList();
    refreshBorrowings();
  })
}
</script>

<template>
  <div>
    <div style="height: 40vh">
      <el-row>
        <el-col :span="12"/>
        <el-col :span="12">
          <el-text>我的书单</el-text>
        </el-col>
        <el-col :span="12"/>
      </el-row>
      <el-table :data="bookList" height="97%">
        <el-table-column prop="bname" label="名称" width="200"/>
        <el-table-column prop="bauthor" label="作者" width="200"/>
        <el-table-column prop="bversion" label="版本" width="80"/>
        <el-table-column prop="bpress" label="出版社"/>
        <el-table-column prop="binventory" label="库存" width="60"/>
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button-group>
              <el-button type="primary" size="small" @click="removeBookList(row)">移除</el-button>
              <el-button type="success" size="small" @click="addBorrowing(row)">借阅</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="height: 40vh">
      <el-row>
        <el-col :span="12"/>
        <el-col :span="12">
          <el-text>我借的书</el-text>
        </el-col>
        <el-col :span="12"/>
      </el-row>
      <el-table :data="borrowings" height="97%">
        <el-table-column prop="bname" label="名称" width="200"/>
        <el-table-column prop="bauthor" label="作者" width="120"/>
        <el-table-column prop="bversion" label="版本" width="80"/>
        <el-table-column label="借书日期" width="180">
          <template #default="{row}">
            <el-text>{{ row.borrowing_date != undefined ? row.borrowing_date : "" }}</el-text>
          </template>
        </el-table-column>
        <el-table-column label="还书日期" width="180">
          <template #default="{row}">
            <el-text>{{ row.return_date != undefined ? row.return_date : "未归还" }}</el-text>
          </template>
        </el-table-column>
        <el-table-column prop="bpress" label="出版社"/>
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button-group>
              <el-button type="success" size="small" @click="removeBorrowing(row)" :disabled="row.return_date != undefined">归还</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>