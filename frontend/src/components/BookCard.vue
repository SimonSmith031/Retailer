<template>
  <div class="col-xs-10 col-xs-offset-1 col-md-4 col-md-offset-0">
    <div class="card">
      <div class="card-image">
        <a><img :src="imgSrcPrefix + book.name" :class="userIsAdmin ? 'bookimage-short' : 'bookimage-long'"/></a>
      </div>
      <!-- 卡片操作元素 -->
      <div v-if="userIsAdmin">
        <!-- 管理员 -->
        <div class="card-action">
          <div class="input-group">
            <input
              type="number"
              class="form-control"
              :value="book.price"
              :id="book.name + '-price-input'"
            />
            <span class="input-group-addon">元</span>

            <!-- <span class="input-group-btn">
              <button class="btn btn-danger">
                下架
              </button>
            </span> -->

            <!-- 间隙 -->
            <span class="input-group-btn" style="opacity: 0;">
              <button></button>
            </span>

            <input
              type="number"
              class="form-control"
              :value="book.stockNumber"
              :id="book.name + '-number-input'"
            />
            <span class="input-group-addon">本</span>
          </div>

          <!-- <div class="input-group"> -->

          <!-- <span class="input-group-btn">
              <button class="btn btn-default">
                修改
              </button>
            </span> -->
          <!-- </div> -->
        </div>

        <div class="card-action">
          <div class="input-group">
            <input class="form-control" style="opacity: 0%;" />
            <span class="input-group-btn">
              <button class="btn btn-default" @click="updateStockAndPrice">
                修改
              </button>
            </span>

            <!-- 间隙 -->
            <span class="input-group-btn" style="opacity: 0;">
              <button></button>
            </span>

            <span class="input-group-btn">
              <button class="btn btn-danger" @click="deleteBook">
                下架
              </button>
            </span>
          </div>
        </div>
      </div>

      <div v-else>
        <!-- 其它用户 -->
        <div class="card-action">
          <div class="input-group">
            <span class="input-group-addon">¥</span>
            <input
              type="number"
              class="form-control"
              :value="book.price"
              step="0.01"
              disabled
            />

            <span class="input-group-addon" style="background: white;">
              {{ book.stockNumber }} 本
            </span>

            <span class="input-group-btn">
              <button class="btn btn-success" @click="addToCart">
                加入购物车
              </button>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
var card = {
  name: "BookCard",
  props: ["book"],
  computed: {
    ...mapState({
      user: (state) => state.user,
      userIsAdmin: (state) => state.user != null && state.user.name == "admin",
      imgSrcPrefix: state => state.imgSrcPrefix,
    }),
  },
  methods: {
    addToCart() {
      this.$emit("add:cart", this.book);
    },
    updateStockAndPrice() {
      const price = document.getElementById(this.book.name + '-price-input').value;
      const number = document.getElementById(this.book.name + '-number-input').value;
      console.log('price: ', price, 'number:', number, 'bookName:', this.book.name);
      this.$emit("update:stockAndPrice", this.book.name, number, price);
    },
    deleteBook() {
      this.$emit("delete:book", this.book.name);
    }
  },
};
export default card;
</script>

<style scoped>
</style>
