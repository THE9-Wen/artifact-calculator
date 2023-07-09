<script setup>
import { artifactMains } from '../artifact/artifact'
import { ref, watch } from 'vue'

const props = defineProps({
  keywords: Array
})

const innerKeywords = ref(props.keywords)

watch(
  innerKeywords,
  (value) => {
    emit('update:keywords', value)
  },
  { deep: true }
)

const emit = defineEmits(['update:keywords'])
</script>

<template>
  <div>主词条</div>
  <div class="artifact-main-container">
    <el-checkbox-group v-model="innerKeywords">
      <el-checkbox
        v-for="keyword in artifactMains.filter((item) => item.keyword !== 5 && item.keyword !== 11)"
        :key="keyword.keyword"
        :disabled="keyword.keyword === 3 || keyword.keyword === 1"
        :value="keyword.keyword"
        :label="keyword.keyword"
      >
        {{ keyword.label }}
      </el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<style scoped>
.artifact-main-container {
  border: solid #b3e19d;
  max-width: 800px;
  display: inline-block;
  text-align: left;
  margin: 10px 10px 10px 10px;
}
.el-checkbox {
  min-width: 125px;
  margin-left: 10px;
}
</style>
