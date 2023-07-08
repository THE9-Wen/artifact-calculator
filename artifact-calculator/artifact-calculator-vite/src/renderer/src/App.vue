<script setup>
import ArtifactForm from './components/artifact/artifact-form.vue'
import ArtifactList from './components/artifact/artifact-list.vue'
import CalculatorHeader from './components/common/calculator-header.vue'
import CalculatorFooter from './components/common/calculator-footer.vue'
import ArtifactCalculator from './components/calculator/artifact-calculator-suit.vue'
import ArtifactCalculatorDoubleSuit from './components/calculator/artifact-calculator-double-suit.vue'
import { ref } from 'vue'

const artifactListRef = ref(null)
const clickArtifactList = () => {
  artifactListRef.value.listArtifacts()
}
</script>

<template>
  <div>
    <calculator-header></calculator-header>
    <el-tabs>
      <el-tab-pane label="圣遗物" @click="clickArtifactList">
        <artifact-list ref="artifactListRef"></artifact-list>
      </el-tab-pane>
      <el-tab-pane label="新增圣遗物">
        <artifact-form></artifact-form>
      </el-tab-pane>
      <el-tab-pane label="四件套计算器">
        <artifact-calculator></artifact-calculator>
      </el-tab-pane>
      <el-tab-pane label="2+2计算器">
        <artifact-calculator-double-suit></artifact-calculator-double-suit>
      </el-tab-pane>
    </el-tabs>
    <calculator-footer></calculator-footer>
  </div>
</template>

<style scoped>
:deep(.el-tabs__item) {
  min-width: 200px;
}
</style>
