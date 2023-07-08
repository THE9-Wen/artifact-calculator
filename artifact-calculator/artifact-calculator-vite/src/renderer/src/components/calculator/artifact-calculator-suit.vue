<script setup>
import { computed, ref } from 'vue'
import calculatorApi from '../../api/calculator-api.js'
import characters from '../character/character.js'
import { artifactMains, artifactSuits } from '../artifact/artifact.js'
import weapons from '../weapon/weapon.js'
import CharacterResult from './character-result.vue'
import { ElMessage } from 'element-plus'
import { exportExcel, handleCalculateResult } from './artifact-calculator'

const character = ref({})
const artifactSuit = ref('')
const weapon = ref('')
const keywords = ref([artifactMains[1].keyword, artifactMains[3].keyword])

const calculateResult = ref()

const querySearch = (value, cb) => {
  const results = value
    ? characters.filter((c) => c.label.includes(value) || c.value.includes(value))
    : characters
  cb(results)
}

const handleSelect = (value) => {
  character.value = value
}

const filteredWeapons = computed(() => {
  if (character.value.name) {
    return weapons.filter((item) => item.type === character.value.weaponType)
  }
  return weapons
})

const calculate = () => {
  calculatorApi
    .suit({
      name: character.value.value,
      suit: artifactSuit.value.key,
      weapon: weapon.value.key,
      keywords: keywords.value
    })
    .then((response) => {
      calculateResult.value = handleCalculateResult(response.data)
      calculateResult.value.name = character.value.label
    })
    .catch((e) => {
      ElMessage({
        message: e.response.data.message,
        type: 'error'
      })
    })
}

const getExcel = () => {
  calculatorApi
    .getExcel()
    .then((response) => {
      exportExcel(
        response.data,
        `${character.value.label}-${artifactSuit.value.label}-${weapon.value.label}`)
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
                :value="suit"
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
                v-for="item in filteredWeapons"
                :key="item.key"
                :value="item"
                :label="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="主词条">
            <el-checkbox-group v-model="keywords">
              <el-checkbox
                v-for="keyword in artifactMains.filter(
                  (item) => item.keyword !== 5 && item.keyword !== 11
                )"
                :key="keyword.keyword"
                :disabled="keyword.keyword === 3 || keyword.keyword === 1"
                :value="keyword.keyword"
                :label="keyword.keyword"
              >
                {{ keyword.label }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-row v-if="calculateResult">
      <character-result :calculate-result="calculateResult"></character-result>
    </el-row>
    <div class="button-container">
      <el-button :disabled="!calculateResult" @click="getExcel">获取计算表格</el-button>
      <el-button @click="calculate">最佳搭配</el-button>
    </div>
  </div>
</template>

<style scoped>
.button-container {
  width: 100%;
  text-align: right;
}
.el-button {
  padding-right: 20px;
  padding-left: 20px;
}
</style>
