<script setup>
import { artifactMains, artifactSuits } from '../artifact/artifact.js'
import weapons from '../weapon/weapon.js'
import { computed, ref } from 'vue'
import characters from '../character/character.js'
import calculatorApi from '../../api/calculator-api.js'
import CharacterResult from './character-result.vue'
import { ElMessage } from 'element-plus'
import { exportExcel, handleCalculateResult } from './artifact-calculator'
import ArtifactMainSelector from './artifact-main-selector.vue'

const character = ref({})
const suitKeyword1 = ref('')
const suitKeyword2 = ref('')
const weapon = ref('')
const keywords = ref([artifactMains[1].keyword, artifactMains[3].keyword])

const bestSuit = ref([])
const calculateResult = ref()

const querySearch = (value, cb) => {
  const results = value
    ? characters.filter((c) => c.label.includes(value) || c.value.includes(value))
    : characters
  cb(results)
}

const doubleSuit = computed(() => {
  const set = new Set()
  artifactSuits.forEach((suit) => {
    const label = artifactMains.find((main) => main.keyword === suit.keyword)?.label
    set.add({ key: suit.keyword, label })
  })
  return [...set]
})

const handleSelect = (value) => {
  character.value = value
}

const calculate = () => {
  calculatorApi
    .doubleSuit({
      name: character.value.value,
      suitKeyword1: suitKeyword1.value,
      suitKeyword2: suitKeyword2.value,
      weapon: weapon.value.key,
      keywords: keywords.value
    })
    .then((response) => {
      calculateResult.value = handleCalculateResult(response.data)
    })
    .catch((e) => {
      ElMessage({
        message: e.response.data.message,
        type: 'error'
      })
    })
}

const filteredWeapons = computed(() => {
  if (character.value.label) {
    return weapons.filter((item) => item.type === character.value.weaponType)
  }
  return weapons
})

const getExcel = () => {
  calculatorApi
    .getExcel()
    .then((response) => {
      exportExcel(
        response.data,
        `${character.value.label}-${suitKeyword1.value}-${suitKeyword2.value}-${weapon.value.label}`
      )
    })
    .catch((e) => {
      ElMessage({
        message: e.response.data.message,
        type: 'error'
      })
    })
}
</script>

<template>
  <div id="calculator">
    <div class="artifact-form">
      <el-form label-width="100px">
        <el-form-item label="角色名称:">
          <el-autocomplete
            v-model="character.label"
            :fetch-suggestions="querySearch"
            clearable
            placeholder="请输入角色名"
            value-key="label"
            @select="handleSelect"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="武器:">
          <el-select v-model="weapon" value-key="key">
            <el-option
              v-for="item in filteredWeapons"
              :key="item.key"
              :value="item"
              :label="item.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="两件套1:">
          <el-select v-model="suitKeyword1" value-key="key">
            <el-option
              v-for="suit in doubleSuit"
              :key="suit.key"
              :value="suit.key"
              :label="suit.label"
            >
              {{ suit.label }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="两件套2:">
          <el-select v-model="suitKeyword2" value-key="key">
            <el-option
              v-for="suit in doubleSuit"
              :key="suit.key"
              :value="suit.key"
              :label="suit.label"
            >
              {{ suit.label }}
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <artifact-main-selector v-model:keywords="keywords"></artifact-main-selector>
    </div>
    <el-row v-if="calculateResult"
      ><character-result :calculate-result="calculateResult" :character="character"></character-result>
    </el-row>
    <el-row>
      <el-col v-for="artifact in bestSuit" :key="artifact.id" :span="6">
        <el-card class="artifact-card" shadow="hover">
          <template #header>
            <div>
              {{ artifact.suit.label }}
            </div>
            <div>{{ artifact.position.label }}</div>
          </template>
          <template #default>
            <div>{{ artifact.main.label }}: {{ artifact.main.value }}</div>
            <div v-for="sub in artifact.subs" :key="sub.keyword">
              {{ sub.label }}: {{ sub.value }}
            </div>
          </template>
        </el-card>
      </el-col>
    </el-row>
    <div class="button-container">
      <el-button :disabled="!calculateResult" @click="getExcel">获取计算表格</el-button>
      <el-button @click="calculate">最佳搭配</el-button>
    </div>
  </div>
</template>

<style scoped>
.el-button {
  padding-right: 20px;
}
</style>
