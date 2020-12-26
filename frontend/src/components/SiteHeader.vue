<template>
  <header>
    <nav class="navbar navbar-default" id="nav">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button
            type="button"
            class="navbar-toggle collapsed"
            data-toggle="collapse"
            data-target="#navbar-collapse"
            aria-expanded="false"
          >
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand">在线书城</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-collapse">
          <ul class="nav navbar-nav">
            <li>
              <router-link to="/books">
                <span>书籍</span>
              </router-link>
            </li>

            <li v-if="!userIsAdmin">
              <router-link to="/shopcart">
                <span>购物车</span>
              </router-link>
            </li>

            <li v-if="userIsAdmin">
              <router-link to="/manager/add"
                ><span>上架新书</span>
              </router-link>
            </li>

            <li v-if="!userIsAdmin">
              <router-link to="/repository"><span>已购项目</span></router-link>
            </li>
            <!-- <li v-if="userIsAdmin">
              <router-link to="/manager/del"><span>下架图书</span></router-link>
            </li> -->
          </ul>

          <!-- <ul v-if="error == null" class="nav navbar-nav navbar-right"> -->
          <ul class="nav navbar-nav navbar-right">
            <li v-if="!userNotNull">
              <router-link to="/login">
                <span class="glyphicon glyphicon-log-in"></span>
                登录</router-link
              >
            </li>
            <li v-if="userNotNull">
              <router-link to="/login">
                <span>
                  <span class="glyphicon glyphicon-log-out"> </span>
                  注销：{{ userName }}
                </span>
              </router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  name: "SiteHeader",
  computed: mapState({
    userNotNull: (state) => state.user != null && state.user.name != null,
    userIsAdmin: (state) => state.user != null && state.user.name == "admin",
    error: (state) => state.error,
    userName: (state) => state.user.name,
  }),
  methods: {
    ...mapMutations(["clearInfo"]),
    // 如果登录状态下路由到了这个界面说明是登出请求了，用这个来判断
    // logout() {
    //   this.$store.commit('setUserName', null);
    //   this.$store.commit('setUserToken', null);
    // }
  },
};
</script>

<style scoped>
#nav a.active {
  color: blue;
}
</style>
