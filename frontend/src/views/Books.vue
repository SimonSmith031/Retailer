<template>
  <p v-if="user == null" class="dim text-center page-header">
    当前以游客身份浏览网站，不能够进行购买等操作，如需操作请先<router-link
      to="/login"
      >登录</router-link
    >
  </p>

  <div class="page-header">
    <h2>书籍</h2>
  </div>

  <!-- 此页面需要获取的参数：bookList（书籍清单）-->
  <div class="row">
    <div v-for="(book, index) in books" :key="index">
      <book-card :book="book" @add:cart="addToCart" @update:stockAndPrice="updateStockAndPrice" @delete:book="deleteBook" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import BookCard from "../components/BookCard.vue";

export default {
  components: { BookCard },
  computed: {
    ...mapState({
      user: (state) => state.user,
      books: (state) => state.booksOnSale,
      apiRequestPrefix: (state) => state.apiRequestPrefix,
      token: (state) => state.user.token,
    }),
  },
  methods: {
    addToCart(book) {
      // TODO
      // 用户登录检查现在只由路由器处理，这里只负责尝试添加新的书籍
      this.$router.push("/shopcart");
      // 用户没有登录就可以直接返回了，上面的跳转肯定会失败，而且还会提供一个消息提示
      if (this.user == null || this.user.name == null || 
            this.user.name.length == 0 || this.user.token == null) {
        return;
      }
      // post 添加新的数据，异步请求
      console.log("准备将" + book.name + "加入购物车");
      axios
        .post(
          this.apiRequestPrefix +
            "api/shopcart-item/" +
            this.token +
            "/" +
            book.name
        )
        .then(() => {
          // 通知store刷新
          this.$store.dispatch("fetchShopcartItems");
        })
        .catch((err) => {
          console.log(err);
        });
      // 控制路由器路由到购物车页面
      // 已经提前调用
    },
    updateStockAndPrice(bookName, number, price) {
      axios.put(this.apiRequestPrefix + 'api/book/' + this.token + '/' + bookName + '/' + number + '/' + price).then((response) => {
        if (response.data.status == 'success') {
          console.log('[updateStockAndPrice] success');
        } else {
          console.log('[updateStockAndPrice] failure');
          alert('更新库存和图书数量失败。没有得到预期的网络回应，输入必须是合法的正整数，请检查输入和网络条件后重试。');
        }
      }).catch((err) => {
        console.log(err);
      })
    },
    // 删除一本书
    deleteBook(bookName) {
      axios.delete(this.apiRequestPrefix + 'api/book/' + this.token + '/'  + bookName).then((response) => {
        if (response.data.status == 'success') {
          console.log('[deleteBook] success');
          // alert('下架书籍：[' + bookName + '] 成功。');
          // 删除成功之后重载页面
          this.$store.dispatch('fetchBooksOnSale');
        } else if (response.data.status == 'failure') {
          // 显示错误提示信息
          alert(response.data.info);
        }
      }).catch(err => {
        console.log(err);
      })
    }
  },
};
</script>
