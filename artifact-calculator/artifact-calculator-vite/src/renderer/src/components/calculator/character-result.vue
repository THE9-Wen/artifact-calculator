<script setup>
import { geIntegerValue, getPercentValue } from '../../js/formatter'

defineProps({
  calculateResult: Object,
  character: Object
})
</script>

<template>
  <div class="character-result-container">
    <el-row v-if="calculateResult">
      <el-col :span="12">
        <el-card :body-style="{ padding: 0 }">
          <el-image
            :src="`src/assets/characters/${character.value}.png`"
            :fit="'scale-down'"
          ></el-image>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <h2>{{ character.label }}</h2>
          <div>生命值: {{ geIntegerValue(calculateResult.hp) }}</div>
          <div>攻击力: {{ geIntegerValue(calculateResult.atk) }}</div>
          <div>防御力: {{ geIntegerValue(calculateResult.defence) }}</div>
          <div>元素精通: {{ geIntegerValue(calculateResult.mastery) }}</div>
          <div>暴击率: {{ getPercentValue(calculateResult.critRate) }}</div>
          <div>暴击伤害: {{ getPercentValue(calculateResult.critDmg) }}</div>
          <div>元素充能效率: {{ getPercentValue(calculateResult.charging) }}</div>
          <div>伤害加成: {{ getPercentValue(calculateResult.bonus) }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <el-row v-if="calculateResult.artifacts">
    <el-card
      v-for="artifact in calculateResult.artifacts"
      :key="artifact.id"
      class="artifact-card"
      shadow="hover"
    >
      <template #header>
        <div>
          {{ artifact.suit.label }}
        </div>
        <div>{{ artifact.position.label }}</div>
      </template>
      <div>{{ artifact.main.label }}: {{ artifact.main.value }}</div>
      <div v-for="sub in artifact.subs" :key="sub.keyword">{{ sub.label }}: {{ sub.value }}</div>
    </el-card>
  </el-row>
</template>

<style scoped>
.character-result-container {
  width: 50%;
  min-width: 600px;
  margin: 0 auto;
}
.el-col {
  min-height: 300px;
}
.calculate-result {
  width: 100%;
  text-align: center;
}
.character-result-container .el-card {
  display: flex;
  align-items: center;
  height: 100%;
}
.character-result-container .el-card :deep(.el-card__body) {
  flex: 1;
}
</style>
