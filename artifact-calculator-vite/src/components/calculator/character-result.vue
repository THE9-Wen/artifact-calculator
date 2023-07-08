<script setup>
defineProps(['calculateResult'])
</script>

<template>
  <el-container>
    <el-main>
      <el-row>
        <el-card>
          <template #header>
            <div>
              {{ calculateResult.name }}
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
      </el-row>
      <el-row v-if="calculateResult.artifacts">
        <el-col v-for="artifact in calculateResult.artifacts" :span="6" :key="artifact.id">
          <el-card class="artifact-card" shadow="hover">
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
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>
