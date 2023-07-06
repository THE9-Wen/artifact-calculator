<script setup>
import { ref } from 'vue'
import calculatorApi from '../../api/calculator-api.js'
import characters from '../character/character.js'
import { Artifact, artifactMains, artifactSuits } from '../artifact/artifact.js'
import weapons from '../weapon/weapon.js'
import CharacterResult from './character-result.vue'
import { ElMessage } from 'element-plus'

const character = ref({})
const artifactSuit = ref('')
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

const handleSelect = value => {
  character.value = value
}

const calculate = () => {
  calculatorApi.suit({
    name: character.value.value,
    suit: artifactSuit.value,
    weapon: weapon.value.key,
    keywords: keywords.value
  }).then(response => {
    const data = response.data
    bestSuit.value = data.data.artifacts.map(item => new Artifact(item))
    calculateResult.value.character = data.data.character
    calculateResult.value.basicDamage = data.data.basicDamage
    calculateResult.value.basicReactDamage = data.data.basicReactDamage
    calculateResult.value.critDamage = data.data.critDamage
    calculateResult.value.critReactDamage = data.data.critReactDamage
  }).catch(e => {
    ElMessage({
      message: e.response.data.message,
      type: 'error'
    })
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
          <el-form-item label="四件套">
            <el-select v-model="artifactSuit" value-key="key">
              <el-option
                  v-for="suit in artifactSuits"
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
.button-container {
  width: 100%;
}
.el-button {
  float: right;
}
</style>
