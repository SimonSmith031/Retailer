import { createRouter, createWebHistory } from "vue-router";

// store
import store from "../store";

const routes = [
  {
    path: "/",
    name: "Index",
    redirect: '/books',
  },
  {
    path: "/books",
    name: "Book",
    component: () => import("../views/Books.vue"),
  },
  {
    path: "/shopcart",
    name: "Shopcart",
    component: () => import("../views/Shopcart.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/Login.vue"),
  },
  {
    path: "/repository",
    name: "PurchasedItems",
    component: () => import("../views/PurchasedItems.vue"),
  },
  {
    path: "/manager/add",
    name: "AddNewBooks",
    component: () => import("../views/AddNewBooks.vue"),
  },
  {
    path: "/:any(.*)*",
    name: "Error",
    component: () => import("../views/ErrorPage.vue"),
  },
];

const router = createRouter({
  mode: 'history',
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

function fetchResources(path) {
  if (path == "/books" || path == "/") {
    store.dispatch("fetchBooksOnSale");
  } else if (path == "/shopcart") {
    // 去取之前先设置页面为空，这样就不会有刚买之后一闪而过的效果
    store.commit("setShopcartItems", []);
    store.dispatch("fetchShopcartItems");
  } else if (path == "/repository") {
    store.dispatch("fetchPurchasedItems");
  } else if (path == '/login') {
    if (store.state.user != null && store.state.user.name != null) {
      store.dispatch('clearUser');
    }
  }
}

// 提交要用来展示的info
var info = null;
var committed = false;
function commit() {
  store.commit("setInfo", info);
  committed = true;
}

router.beforeEach((to, from, next) => {
  console.log(to.path, from.path, "info: ", info);

  // 清除过期的提示信息
  if (committed) {
    info = null;
    committed = false;
  }

  // 未登录且访问不合法内容的时候
  if (
    (store.state.user == null || store.state.user.name == null) &&
    to.path != "/books" &&
    to.path != "/" &&
    to.path != "/login"
  ) {
    console.log("😅 trying to get to invalid page");
    info = {
      text: "若要进入该标签页，请先登录",
      color: "red",
    };
    next({ path: "/login" });
    // 因为如果当前的位置已经是'/login'则不会触发next，所以还需要再次标记committed
    // 1. 如果当前已经在这个path，则next不会再次进行视图解析
    // 2. next只负责视图解析，并不会让函数终止
    if (from.path == "/login") {
      commit();
    }
    return;
  }

  // 已经登录或者访问合法内容的时候
  console.log("visiting a valid page! 🙋‍♂️");
  fetchResources(to.path);
  next();
  commit();
});

export default router;
