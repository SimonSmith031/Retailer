<template>
  <div class="panel panel-primary">
    <div class="panel-heading">物品详情</div>
    <div class="panel-body">
      <div class="order">
        <img
          class="order-cover "
          :src="imgSrcPrefix + item.name"
        />
        <div class="order-tip">
          <div class="order-title">
            <h3 class="title">{{ item.fullName }}</h3>
            <div class="order-price">
              <h3>¥ {{ item.price }}</h3>
            </div>
          </div>

          <div class="order-detail">
            <div v-for="(paragraph, index) in item.paragraphs" :key="index">
              <p style="font-size: 17px;">{{ paragraph }}</p>
            </div>
          </div>

          <br />

          <div class="form-inline" style="margin-top: -15px;">
            <!--TODO: 这里不使用v-model，而是通过一个响应事件发出数据修改通知 -->
            <!-- <div> -->
            <input
              class="form-control"
              type="number"
              placeholder="数量"
              :value="item.number"
              :id="item.name + '-input-number'"
              @change="updateOne"
              style="width: 35%; font-size: 16px"
            />
            <span style="opacity: 0"> .</span>
            <!-- </div> -->
            <!-- <input type="hidden" name="book" :value="item.name" /> -->
            <button class="btn btn-success" type="submit" @click="buyOne">
              购买
            </button>

            <span style="opacity: 0"> .</span>
            <button class="btn btn-danger" type="submit" @click="deleteOne">
              删除
            </button>
            <!-- </form> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
export default {
  name: "ShopItemCard",
  props: ["item"],
  computed: mapState({
    imgSrcPrefix: state => state.imgSrcPrefix,
  }),
  methods: {
    // 购买一项购物车项目
    buyOne() {
      this.$emit("buy:item", this.item);
    },
    // 删除一本书的购物车记录
    deleteOne() {
      this.$emit("delete:item", this.item);
    },
    // 更新一本书的购物车记录
    updateOne() {
      const newNumber = document.getElementById(
        this.item.name + "-input-number"
      ).value;
      console.log("[ShopcartItemCard::updateItem]", this.item.name, newNumber);
      this.$emit("update:item", this.item, newNumber);
    },
  },
};
</script>

<style scoped></style>
