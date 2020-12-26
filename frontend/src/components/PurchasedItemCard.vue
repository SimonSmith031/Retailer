<template>
  <div class="col-xs-10 col-xs-offset-1 col-md-4 col-md-offset-0">
    <div class="card">
      <div class="card-image">
        <img :src="imgSrcPrefix + item.bookName" class="bookimage-long"/>
      </div>
      <div class="card-action">
        <div class="input-group">
          <span class=" form-control" disabled>{{ dateString(item.time) }}</span>
          <!-- <input
            type="text"
            class="form-control"
            :placeholder="item.price"
            disabled
            style="opacity:0;"
          /> -->

          <span class="input-group-btn">
            <button class="btn btn-default" type="button" disabled>
              {{ item.number }} 本
            </button>
            <!-- 详情功能尚未完成 -->
            <a class="btn btn-success" @click="bookInfo">详情</a>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
export default {
  name: "PurchasedItemCard",
  props: ["item"],
  computed: {
    ...mapState({
      imgSrcPrefix: state => state.imgSrcPrefix,
    }),
  },
  methods: {
    bookInfo() {
      alert('您购买这本书的时间是 ' + this.dateString(this.item.time));
    },
    dateString: (timestamp) => {
      const date = new Date(timestamp);
      var hours = date.getHours().toString();
      var mins = date.getMinutes().toString();
      if (hours.length == 1) {
        hours = '0' + hours;
      }
      if (mins.length == 1) {
        mins = '0' + mins;
      }
      return (
        date.getFullYear() +
        "-" +
        date.getMonth() +
        "-" +
        date.getDate() +
        " " +
        hours +
        ":" +
        mins
      );
    },
  },
};
</script>
