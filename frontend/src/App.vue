<template>
  <site-header />
  <alert/>
  <main class="container">
    <router-view />
  </main>
  <site-footer />
</template>

<script>
import SiteHeader from "./components/SiteHeader.vue";
import SiteFooter from "./components/SiteFooter.vue";
import Alert from './components/Alert.vue';

export default {
  components: {
    SiteHeader,
    SiteFooter,
    Alert,
  },
  name: "App",
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  min-height: 100%;
  position: relative;
}

/* #nav {
  padding: 15px;
} */

#nav a {
  font-weight: normal;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

</style>
