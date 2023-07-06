<script setup>

import { Artifact, artifactMains, artifactSuits } from '../artifact/artifact.js'
import weapons from '../weapon/weapon.js'
import { computed, ref } from 'vue'
import characters from '../character/character.js'
import calculatorApi from '../../api/calculator-api.js'
import CharacterResult from './character-result.vue'

const character = ref({})
const suitKeyword1 = ref('')
const suitKeyword2 = ref('')
const weapon = ref('')
const keywords = ref([artifactMains[1].keyword, artifactMains[3].keyword])

const bestSuit = ref([])
const calculateResult = ref({})

const querySearch = (value, cb) => {
  const results = value
    ? characters.filter(c => c.label.includes(value) || c.value.includes(value))
    : characters
  cb(results)
}

const doubleSuit = computed(() => {
  const set = new Set()
  artifactSuits.forEach(suit => {
    const label = artifactMains.find(main => main.keyword === suit.keyword)?.label
    set.add({ key: suit.keyword, label })
  })
  return [...set]
})

const handleSelect = value => {
  character.value = value
}

const calculate = () => {
  calculatorApi.doubleSuit({
    name: character.value.value,
    suitKeyword1: suitKeyword1.value,
    suitKeyword2: suitKeyword2.value,
    weapon: weapon.value.key,
    keywords: keywords.value
  }).then(response => {
    console.log(response)
    bestSuit.value = response.data.data.artifacts.map(item => new Artifact(item))
    calculateResult.value.character = response.data.data.character
    calculateResult.value.basicDamage = response.data.data.basicDamage
    calculateResult.value.basicReactDamage = response.data.data.basicReactDamage
    calculateResult.value.critDamage = response.data.data.critDamage
    calculateResult.value.critReactDamage = response.data.data.critReactDamage
  })
}
</script>

<template>
  <div id="calculator">
    <el-row>
      <el-form label-position="top">
        <el-col :span="24">
          <el-form-item label="角色名称">
            <el-autocomplete
                v-model="character.label"
                :fetch-suggestions="querySearch"
                clearable
                placeholder="请输入角色名"
                value-key="label"
                @select="handleSelect"
            ></el-autocomplete>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="两件套1">
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
          <el-form-item label="两件套2">
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
        </el-col>
        <el-col :span="24">
          <el-form-item label="武器">
            <el-select v-model="weapon" value-key="key">
              <el-option
                  v-for="weapon in weapons"
                  :key="weapon.key"
                  :value="weapon"
                  :label="weapon.label"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="主词条">
            <el-checkbox-group v-model="keywords">
              <el-checkbox
                  v-for="keyword in artifactMains.filter(item => item.keyword !== 5 && item.keyword !== 11)"
                  :disabled="keyword.keyword === 3 || keyword.keyword === 1"
                  :value="keyword.keyword"
                  :key="keyword.keyword"
                  :label="keyword.keyword">
                {{ keyword.label }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-row>
      <character-result
          :calculate-result="calculateResult"
      ></character-result>
    </el-row>
    <el-row>
      <el-col v-for="artifact in bestSuit" :span="6" :key="artifact.id">
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
      <el-button @click="calculate">最佳搭配</el-button>
    </div>
  </div>
</template>

<style scoped>

</style>
