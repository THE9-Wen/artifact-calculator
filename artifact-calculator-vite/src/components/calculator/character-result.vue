<script setup>
import { onMounted } from 'vue'

const props = defineProps(['calculateResult'])

onMounted(() => {
  console.log(props.calculateResult)
})
</script>

<template>
  <el-container>
    <el-header>{{ calculateResult }}</el-header>
    <el-main>
      <div>{{  }}</div>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>
