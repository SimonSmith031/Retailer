import { createStore } from "vuex";
import axios from "axios";

// 从环境中获取后端的端口号
const backendPort = process.env.JAVA_SERVER_PORT ? process.env.JAVA_SERVER_PORT : 8092;

export default createStore({
  state: {
    // 配置信息
    // 这里只包含了http协议和主机信息，没有包含请求的路径
    apiRequestPrefix: "http://localhost:" + backendPort + "/",
    // 图片的路径信息
    imgSrcPrefix: "http://localhost:" + backendPort + "/api/image/",
    // 全局通用信息
    user: {
      name: null, // 测试用，平时这个时候拿不到token
      token: null,
    },
    info: {
      text: null,
      color: "red",
    },

    // 数据信息
    // 书籍首页
    booksOnSale: [],
    // 购物车
    itemsInShopcart: [],
    // 用户已购项目
    purchasedItems: [],
  },
  mutations: {
    setInfo(state, newInfo) {
      if (newInfo == null) {
        state.info.text = null;
      } else {
        state.info = newInfo;
      }
    },
    // 好像payload只能够有一个，但是可以组织成对象的形式
    setUserName(state, userName) {
      state.user.name = userName;
    },
    setUserToken(state, token) {
      state.user.token = token;
    },
    setBooksOnSale(state, books) {
      state.booksOnSale = books;
    },
    setShopcartItems(state, items) {
      state.itemsInShopcart = items;
    },
    setPurchasedItems(state, items) {
      state.purchasedItems = items;
    },
    setItemNumber(state, payload) {
      console.log('[setItemNumber]: index:', payload.index, 'number:', payload.number);
      // 不知道为什么刷新没有触发
      // 更改策略，不检查数字的合法性，只有提交的时候才检查
      state.itemsInShopcart[payload.index].number = payload.number;
    },
  },
  actions: {
    fetchBooksOnSale({ commit }) {
      // 从后端获取书籍信息
      axios
        .get(this.state.apiRequestPrefix + "api/books")
        .then((response) => {
          commit("setBooksOnSale", response.data);
          console.log('[fetchBooksOnSale] success');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchShopcartItems({ commit }) {
      axios
        .get(
          this.state.apiRequestPrefix +
            "api/shopcart-items/" +
            this.state.user.token
        )
        .then((response) => {
          commit("setShopcartItems", response.data);
          console.log('[fetchShopcartItems] success');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchPurchasedItems({ commit }) {
      axios
        .get(
          this.state.apiRequestPrefix +
            "api/purchased-items/" +
            this.state.user.token
        )
        .then((response) => {
          commit("setPurchasedItems", response.data);
          console.log('[fetchPurchasedItems] success');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateItemLocally({ commit }, payload) {
      // 查找item
      const index = this.state.itemsInShopcart.findIndex(
        (item) => item.name == payload.name
      );
      // 检查是否找到了这样的一个item
      if (index < 0) {
        console.log("[updateItemLocally] index not found");
        return;
      }

      console.log("[updateItemLocally] index found")

      // // 检查数字的合法性
      // if (payload.number <= 0) {
      //   console.log("输入框的数字只能够为正整数。");
      // }
      // 先不检查数字的合法性，只有在最后提交的时候才会检查

      commit("setItemNumber", {
        index: index,
        number: payload.number,
      });
    },
    // 清除用户登录信息
    clearUser({commit}) {
      commit('setUserName', null);
      commit('setUserToken', null);
    }
  },
  modules: {},
  strict: false,
});
