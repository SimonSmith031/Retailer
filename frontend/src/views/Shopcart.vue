<template>
  <h2 class="page-header">购物车</h2>

  <div v-for="(item, index) in items" :key="index">
    <shop-item-card
      :item="item"
      @buy:item="buyOne"
      @delete:item="deleteOne"
      @update:item="updateOne"
    ></shop-item-card>
  </div>

  <div v-if="items.length != 0" class="button-row">
    <!-- <a href="/list/save-all">
      <button type="button" class="btn btn-default">保存</button>
    </a> -->
    <!-- <div class="btn" disabled="true" style="opacity: 0"></div> -->
    <button type="button" class="btn btn-success" @click="buyAll">
      全部购买
    </button>
    <div style="opacity: 0">......</div>
    <button type="button" class="btn btn-danger" @click="deleteAll">
      全部删除
    </button>
  </div>

  <p v-if="items.length == 0" class="dim text-center">
    当前您的购物车暂无商品。
  </p>
</template>

<script>
import { mapState } from "vuex";
import ShopItemCard from "../components/ShopItemCard.vue";
import axios from "axios";

export default {
  name: "Shopcart",
  components: { ShopItemCard },
  computed: {
    ...mapState({
      info: (state) => state.info,
      items: (state) => state.itemsInShopcart,
      apiRequestPrefix: (state) => state.apiRequestPrefix,
      token: (state) => state.user.token,
    }),
  },
  methods: {
    // 购买所有购物车项目
    buyAll() {
        // 数量的安全检查
        for (const item of this.items) {
            if (item.number <= 0) {
                alert('购买的数量必须是正整数。');
                return;
            }
        }

        // 请求服务端变更数据
        axios.post(this.apiRequestPrefix + 'api/purchased-items/' + this.token, {
            items: JSON.stringify(this.items)
        }).then((response) => {
            console.log('[buyAll] status:', response.data.status);
            // 如果购买成功则跳转
            if (response.data.status == 'success') {
                this.$router.push('/repository');
            }
        }).catch((err) => {
            console.log(err);
        })
    },
    deleteAll() {
      axios.delete(this.apiRequestPrefix + 'api/shopcart-items/' + this.token).then((response) => {
          console.log('[deleteAll] status:', response.data.status);
          if (response.data.status == 'success') {
              // 如果删除成功，则刷新购物车信息
              this.$store.dispatch('fetchShopcartItems');
          }
      })
    },
    // 购买一本书
    buyOne(item) {
      console.log("buyOne: [" + item.name + "] called");
      // 安全检查
      if (item.number <= 0) {
          alert('购买的数量必须是正整数。');
          return;
      }
      axios
        .post(
          this.apiRequestPrefix +
            "api/purchased-item/" +
            this.token +
            "/" +
            item.name +
            "/" +
            item.number
        )
        .then((response) => {
          if (response.data.status == "success") {
            console.log("[Shopcart::buyOne] 请求成功", item.name, item.number);
            // 购买之后页面要跳转
            this.$router.push("/repository");
          } else {
            console.log("[Shopcart::buyOne] 请求失败", item.name, item.number);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 删除一本书的购物车记录
    deleteOne(item) {
      console.log("deleteOne: [" + item.name + "] called");
      axios
        .delete(
          this.apiRequestPrefix +
            "api/shopcart-item/" +
            this.token +
            "/" +
            item.name
        )
        .then((response) => {
          if (response.data.status == "success") {
            console.log("[Shopcart::deleteOne] 请求成功", item.name);
            // 更新购物车信息
            this.$store.dispatch('fetchShopcartItems');
          } else {
            console.log("[Shopcart::deleteOne] 请求失败", item.name);
          }
        });
    },
    // 更新一本书的购物车信息
    updateOne(item, newNumber) {
        console.log("updateOne: [" + item.name + "] called");
      // 请求更新本地的信息
      this.$store.dispatch('updateItemLocally', {
          name: item.name,
          number: newNumber,
      });

      // 如果已知当前数目不合法就不通知服务器了，但是本地模型会变化
      if (newNumber <= 0) {
          return;
      }

      // 请求更新服务器的信息
      axios
        .put(
          this.apiRequestPrefix +
            "api/shopcart-item/" +
            this.token +
            "/" +
            item.name +
            "/" +
            newNumber
        )
        .then((response) => {
          // 不需要刷新页面
          if (response.data.status == "success") {
            console.log("[Shopcart::updateOne] 请求成功", item.name, newNumber);
          } else {
            console.log("[Shopcart::updateOne] 请求失败", item.name, newNumber);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.button-row {
  position: absolute;
  left: 50%;
  right: 50%;
  /* margin-top: -6px; */
  display: flex;
  justify-content: space-around;
}
</style>
