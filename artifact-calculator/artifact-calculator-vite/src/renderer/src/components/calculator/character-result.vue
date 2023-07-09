<script setup>
defineProps({
  calculateResult: Object,
  character: Object
})
</script>

<template>
  <div class="character-result-container">
    <el-card v-if="calculateResult">
      <template #header>
        <div>
          {{ character.label }}
        </div>
      </template>
      <div>生命值: {{ calculateResult.hp }}</div>
      <div>攻击力: {{ calculateResult.atk }}</div>
      <div>防御力: {{ calculateResult.defence }}</div>
      <div>元素精通: {{ calculateResult.mastery }}</div>
      <div>暴击率: {{ calculateResult.critRate }}</div>
      <div>暴击伤害: {{ calculateResult.critDmg }}</div>
      <div>元素充能效率: {{ calculateResult.charging }}</div>
      <div>伤害加成: {{ calculateResult.bonus }}</div>
    </el-card>
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
      <div v-for="sub in artifact.subs" :key="sub.keyword">
        {{ sub.label }}: {{ sub.value }}
      </div>
    </el-card>
  </el-row>
</template>

<style scoped>
.character-result-container {
  width: 50%;
  min-width: 600px;
  margin: 0 auto;
}
.calculate-result {
  width: 100%;
  text-align: center;
}
</style>
