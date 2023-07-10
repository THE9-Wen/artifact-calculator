<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import artifactApi from '../../api/artifact-api.js'
import { Artifact, artifactMains, artifactSuits } from './artifact.js'

const artifacts = ref([])
const suit = ref()
const main = ref()
const currentPage = ref(1)
const pageSize = 20
const pageContent = ref()

onMounted(() => {
  listArtifacts()
})

const listArtifacts = () => {
  artifactApi.listArtifacts().then((response) => {
    artifacts.value = []
    for (const json of response.data) {
      artifacts.value.push(new Artifact(json))
    }
  })
}

const filteredArtifacts = computed(() => {
  return artifacts.value
    .filter((item) => {
      if (suit.value || suit.value === 0) {
        return suit.value === item.suit.key
      }
      return true
    })
    .filter((item) => {
      if (main.value || main.value === 0) {
        return main.value === item.main.keyword
      }
      return true
    })
})

watch(
  filteredArtifacts,
  () => {
    updatePageContent()
  }
)

const updatePageContent = () => {
  const start = pageSize * (currentPage.value - 1)
  const end = start + pageSize
  pageContent.value = filteredArtifacts.value.slice(
    start,
    end < filteredArtifacts.value.length ? end : filteredArtifacts.value.length
  )
}

const deleteArtifact = (artifact) => {
  artifacts.value = artifacts.value.filter((item) => item !== artifact)
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
          <template #label> 圣遗物套装： </template>
          <el-select v-model="suit" clearable>
            <el-option
              v-for="item in artifactSuits"
              :key="item.key"
              :label="item.label"
              :value="item.key"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <template #label> 圣遗物主词条： </template>
          <el-select v-model="main" clearable>
            <el-option
              v-for="item in artifactMains"
              :key="item.keyword"
              :label="item.label"
              :value="item.keyword"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-row>
    <el-row>
      <el-card
        v-for="artifact in pageContent"
        :key="artifact.id"
        class="artifact-card"
        shadow="hover"
      >
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
    </el-row>
    <el-pagination
      v-model:current-page="currentPage"
      small
      hide-on-single-page
      layout="total, prev, pager, next"
      :page-size="pageSize"
      :total="filteredArtifacts.length"
      @current-change="updatePageContent"
    />
  </div>
</template>

<style scoped>
.el-button {
  float: right;
}
.el-form {
  display: flex;
}
.el-form-item {
  padding-left: 20px;
  width: 50%;
}
</style>
