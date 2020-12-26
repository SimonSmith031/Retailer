<template>
  <div class="page-header">
    <h2>添加书籍</h2>
  </div>
  <form
    id="newBookForm"
    method="post"
    :action="this.apiRequestPrefix + 'api/book'"
    enctype="multipart/form-data"
    @submit.prevent="addNewBook"
  >
    <div class="row">
      <div class="form-group col-xs-10">
        <div class="input-group">
          <span class="input-group-addon">简称</span>
          <input
            type="text"
            placeholder="书籍的简称，用英文、连字符、下划线和数字组成"
            name="name"
            class="form-control input-lg"
            v-model.trim="formData.name"
          />
        </div>

        <br />

        <div class="input-group">
          <span class="input-group-addon">全称</span>
          <input
            type="text"
            placeholder="书籍的全名"
            name="fullName"
            class="form-control input-lg"
            v-model.trim="formData.fullName"
          />
        </div>

        <br />

        <div class="input-group">
          <span class="input-group-addon">
            价格
          </span>
          <input
            type="number"
            placeholder="书籍的价格"
            name="price"
            class="form-control input-lg"
            step="0.01"
            v-model="formData.price"
          />
        </div>

        <br />

        <div class="input-group">
          <span class="input-group-addon">介绍</span>

          <input
            type="text"
            placeholder="书籍内容介绍"
            name="description"
            class="form-control input-lg"
            v-model.trim="formData.description"
          />
        </div>

        <br />

        <div class="input-group">
          <span class="input-group-addon">库存</span>
          <input
            type="number"
            placeholder="书籍当前的库存数量"
            name="stockNumber"
            class="form-control input-lg"
            step="1"
            v-model="formData.stockNumber"
          />
        </div>

        <br />

        
        <div class="input-group">
          <span class="input-group-addon">封面</span>
          <input
            type="file"
            placeholder="封面图片"
            name="cover"
            class="input-lg form-control btn btn-default"
            id="cover"
            accept="image/png,image/gif,image/jpeg"
          />
        </div>

        <br />

        <!-- <div class="input-group">
          <span class="input-group-addon">资源</span>
          <input
            type="file"
            placeholder="书籍资源"
            title="书籍资源"
            name="pdf"
            class="input-lg btn btn-default form-control"
            id="pdf"
          />
        </div> -->
      </div>
    </div>

    <button type="submit" class="btn btn-primary btn-lg col-xs-2">
      提交
    </button>
  </form>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
// const qs = require('qs');

// function invalidString(s) {
//   var pattern = new RegExp(
//     "[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！ \\n\\t@#￥……&*（）——|{}【】‘；：”“'。，、？]"
//   );
//   console.log("regex:", pattern);
//   return pattern.test(s);
// }

export default {
  name: "AddNewBooks",
  computed: {
    ...mapState({
      apiRequestPrefix: (state) => state.apiRequestPrefix,
      token: (state) => state.user.token,
    }),
  },
  data() {
    return {
      formData: {
        name: "",
        fullName: "",
        description: "",
        stockNumber: 1,
        price: 9.9,
      },
    };
  },
  methods: {
    addNewBook() {
      // 检查表单信息
      // 检查用户名是否合法
      //   if (invalidString(name)) {
      //     alert("简称中包含特殊字符或空格，不能提交。");
      //     return;
      //   }

      // 检查字符串信息是否填写完整
      if (
        this.formData.fullName.length == 0 ||
        this.formData.name.length == 0 ||
        this.formData.description.length == 0
      ) {
        alert("部分信息没有填写完整，不能够提交。");
        return;
      }

      // 对数字做检查
      const stock = Number(this.formData.stockNumber);
      const price = Number(this.formData.price);
      console.log("stockNumber =>", this.formData.stockNumber, stock);
      console.log("price =>", this.formData.price, price);
      if (isNaN(stock) || isNaN(price)) {
        alert("提供的价格和库存不是能够识别的数字，请检查输入。");
        return;
      }
      // 把转换好的数字写回表单
      this.formData.stockNumber = stock;
      this.formData.price = price;

      console.log("url:", this.apiRequestPrefix + "api/book");

      // 获取上传的文件信息
      const coverFiles = document.getElementById("cover").files;
      // const pdfFiles = document.getElementById("pdf").files;
      console.log("coverFiles:", coverFiles);

      // 创建表单数据
      var formData = new FormData();
      formData.append("name", this.formData.name);
      formData.append("fullName", this.formData.fullName);
      formData.append("description", this.formData.description);
      formData.append("stockNumber", this.formData.stockNumber);
      formData.append("price", this.formData.price);
      formData.append("cover", coverFiles[0]);
      // 不上传pdf，这个功能不做了
      // formData.append("pdf", pdfFiles[0]);
      formData.append("token", this.token);

      axios
        .post(this.apiRequestPrefix + "api/book", formData)
        .then((res) => {
          if (res.data.status == "success") {
            console.log("[addNewBook] 书籍添加成功");
            this.$router.push("/books");
          } else {
            alert("书籍添加失败，请检查输入是否合法。");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
