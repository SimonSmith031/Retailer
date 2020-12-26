<template>
  <div class="page-header">
    <h2>购买记录</h2>
  </div>

  <div class="row">
    <div v-for="(item, index) in items" :key="index">
        <purchased-item-card :item="item"/>
    </div>
  </div>
</template>


<script>
import { mapState } from 'vuex'
import PurchasedItemCard from '../components/PurchasedItemCard.vue'

export default {
    name: "PurchasedItems",
    components: {PurchasedItemCard},
    computed: {
      ...mapState({
        items: state => state.purchasedItems,
      }),
    },
    methods: {
      // test() {
      //   console.log(this.items);
      // }
    }
}
</script>