<template>
  <p
    v-if="info.text != null"
    class="dim text-center page-header"
    :style="'color:' + (info.color == null ? 'red' : info.color) + ';'"
  >
    {{ info.text }}
  </p>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Alert",
  computed: mapState({
    info: (state) => state.info,
  }),
};
</script>
