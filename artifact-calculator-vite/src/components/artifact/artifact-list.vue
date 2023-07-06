<script setup>
import { computed, onMounted, ref } from 'vue'
import artifactApi from '../../api/artifact-api.js'
import { Artifact, artifactMains, artifactSuits } from './artifact.js'

const artifacts = ref([])
const suit = ref()
const main = ref()

onMounted(() => listArtifacts())

const listArtifacts = () => {
  artifactApi.listArtifacts().then(response => {
    if (response.data.code !== 200) {
      return
    }
    artifacts.value = []
    for (const json of response.data.data) {
      artifacts.value.push(new Artifact(json))
    }
  })
}

const filteredArtifacts = computed(() => {
  return artifacts.value
    .filter(item => {
      if (suit.value || suit.value === 0) {
        return suit.value === item.suit.key
      }
      return true
    })
    .filter(item => {
      if (main.value || main.value === 0) {
        return main.value === item.main.keyword
      }
      return true
    })
})

const deleteArtifact = artifact => {
  artifacts.value = artifacts.value.filter(item => item !== artifact)
  artifactApi.deleteArtifact(artifact.id)
}

defineExpose({
  listArtifacts
})
</script>

<template>
  <div>
    <el-row>
      <el-form>
        <el-form-item>
          <template v-slot:label>
            圣遗物套装：
          </template>
          <el-select
              v-model="suit"
              clearable
          >
            <el-option
                v-for="suit in artifactSuits"
                :key="suit.key"
                :label="suit.label"
                :value="suit.key"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <template v-slot:label>
            圣遗物主词条：
          </template>
          <el-select
              v-model="main"
              clearable
          >
            <el-option
                v-for="main in artifactMains"
                :key="main.keyword"
                :label="main.label"
                :value="main.keyword"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-row>
    <el-row>
      <el-col v-for="artifact in filteredArtifacts" :span="6" :key="artifact.id">
        <el-card class="artifact-card" shadow="hover">
          <template #header>
            <div>
              {{ artifact.suit.label }}
              <el-button type="danger" link circle @click="deleteArtifact(artifact)">删除</el-button>
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
  </div>
</template>

<style scoped>
.el-col {
  margin-bottom: 20px;
}
.el-button {
  float: right;
}
.artifact-card {
  height: 225px;
  width: 225px;
}
.el-form {
  display: flex;
}
.el-form-item {
  padding-right: 20px;
}
</style>
