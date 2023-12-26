<script setup lang="ts">
import {Request} from "@/request/Request";
import {Ref, ref} from "vue";
import {Operate} from "@/model/Util";

const search = ref("");
const books: Ref<{ bname: string }[] | any[]> = ref([]);
const showBooks: Ref<{ bname: string }[] | any[]> = ref([]);
refreshBooks();

function refreshBooks() {
  Request.get("book/all", context => {
    books.value = context.data;
    refreshShowBooks();
  })
}

function refreshShowBooks() {
  showBooks.value = [];
  for (let book of books.value) {
    if (book.bname.includes(search.value))
      showBooks.value.push(book);
  }
}

function addBookList(book: any) {
  Request.post("stu/book_list/add", {
    uid: Number.parseInt(localStorage.getItem("uid")!),
    bid: book.bno
  }, _ => {
    Operate.success();
    refreshBooks();
  })
}

function addBorrowing(book: any) {
  Request.post("stu/borrowing/add", {
    uid: Number.parseInt(localStorage.getItem("uid")!),
    bid: book.bno
  }, _ => {
    Operate.success();
    refreshBooks();
  })
}
</script>

<template>
  <div>
    <el-input v-model="search" style="width: 120px" @input="refreshShowBooks"/>
    <el-table :data="showBooks">
      <el-table-column prop="bno" label="编号" width="60"/>
      <el-table-column prop="bname" label="名称" width="200"/>
      <el-table-column prop="bauthor" label="作者" width="200"/>
      <el-table-column prop="bversion" label="版本" width="80"/>
      <el-table-column prop="bpress" label="出版社"/>
      <el-table-column prop="binventory" label="库存" width="60"/>
      <el-table-column label="操作" width="200">
        <template #default="{row}">
          <el-button-group>
            <el-button type="primary" size="small" @click="addBookList(row)">加入书单</el-button>
            <el-button type="success" size="small" @click="addBorrowing(row)">借阅</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped lang="scss">

</style>