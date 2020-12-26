<template>
  <h2 class="page-header">登录</h2>

  <p>输入您的账户名和密码，首次使用将自动创建账户。</p>

  <form id="login-form">
    <div class="row">
      <div class="form-group col-xs-10">
        <div class="input-group">
          <span class="input-group-addon">🧍‍</span>
          <input
            type="text"
            placeholder="您的用户名"
            name="user"
            class="form-control input-lg"
            v-model="username"
          />
        </div>

        <br />

        <div class="input-group">
          <span class="input-group-addon">🔑</span>
          <input
            type="password"
            placeholder="您的密码"
            name="password"
            class="form-control input-lg"
            v-model="password"
          />
        </div>
      </div>
    </div>
    <button
      value="Login"
      class="btn btn-primary btn-lg col-xs-2"
      @click.prevent="submit"
    >
      登录
    </button>
  </form>
  <!-- 不能够马上提交表单，应该先用函数处理，然后由响应事件提交表单 -->
</template>

<script>
import axios from "axios";
import { mapState } from 'vuex';

export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
    };
  },
  computed: {
    ...mapState({
      apiRequestPrefix: state => state.apiRequestPrefix,
    })
  },
  methods: {
    submit() {
      console.log("username:", this.username, "password:", this.password);
      // 捕获现在的用户名和密码
      const username = this.username;
      const password = this.password;
      axios
        .post(
          this.apiRequestPrefix + "api/login",
          {
            username: username,
            password: password,
          },
          {
            headers: {},
          }
        )
        .then((response) => {
          if (response.data.status == 'success') {
            // 通过
            console.log("登录成功。即将跳转页面。");
            this.$store.commit('setUserName', username);
            this.$store.commit('setUserToken', response.data.token);
            this.$router.push('/books');
          } else {
            // 没有通过
            this.$store.commit('setInfo', {
              // text: '密码、账号出错或出现网络问题，验证失败。',
              text: response.data.info,
              color: 'red',
            })
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
