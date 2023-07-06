<template>
  <el-form
      label-position="right"
      label-width="100px"
      :model="artifact"
  >
    <el-form-item>
      <template v-slot:label>
        圣遗物套装：
      </template>
      <el-select v-model="artifact.suit">
        <el-option
            v-for="suit in artifactSuits"
            :key="suit.key"
            :value="suit.key"
            :label="suit.label"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <template v-slot:label>
        圣遗物位置：
      </template>
      <el-select v-model="artifact.position" @change="artifact.check()">
        <el-option
            v-for="position in positions"
            :key="position.key"
            :value="position.key"
            :label="position.label"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <template v-slot:label>
        主词条：
      </template>
      <el-select v-model="artifact.main" value-key="keyword" @change="artifact.check()">
        <el-option
            v-for="artifactMain in filteredMains"
            :key="artifactMain.keyword"
            :value="artifactMain"
            :label="artifactMain.label"
        ></el-option>
      </el-select>
      <el-input disabled :model-value="artifact.main?.value"></el-input>
    </el-form-item>
    <el-form-item v-for="(sub, index) in artifact.subs" :key="index">
      <template v-slot:label>
        副词条{{ index }}：
      </template>
      <el-select v-model="artifact.subs[index]" value-key="keyword" @change="artifact.check()">
        <el-option
            v-for="artifactSub in filteredSubs"
            :key="artifactSub.keyword"
            :value="artifactSub"
            :label="artifactSub.label"
        ></el-option>
      </el-select>
      <el-input
          v-if="Artifact.isKeywordAbs(sub.keyword)"
          label="值"
          v-model="sub.value"
      ></el-input>
      <el-input
          v-else
          v-model="sub.value"
      >
        <template #append>%</template>
      </el-input>
    </el-form-item>
  </el-form>
  <el-button class="submit-button" @click="submit">提交</el-button>
  <el-button class="submit-button" @click="clear">清空副词条</el-button>
  <el-button class="submit-button" v-if="artifact.id" @click="update">更新</el-button>
</template>

<script setup>
import { computed, ref } from 'vue'
import { Artifact, artifactMains, artifactSubs, artifactSuits, positions } from './artifact.js'
import artifactApi from '../../api/artifact-api.js'

const artifact = ref(new Artifact())

const filteredMains = computed(() => {
  return artifactMains.filter(item => item.positions.includes(artifact.value.position))
})

const filteredSubs = computed(() => {
  return artifactSubs.filter(item => {
    const subDuplicated = artifact.value.subs.filter(sub => sub.keyword === item.keyword).length
    const mainDuplicated = artifact.value.main?.keyword === item.keyword
    return !subDuplicated && !mainDuplicated
  })
})

const submit = () => {
  artifactApi.createArtifact(artifact.value.convertToDTO())
    .then(response => {
      const data = response.data
      if (data.code === 200) {
        artifact.value.id = data.data.id
        alert('提交成功')
      } else {
        console.log(data.desc)
      }
    })
}

const clear = () => {
  artifact.value.subs.forEach(item => {
    item.value = undefined
  })
  artifact.value.subs = [{}, {}, {}, {}]
}

const update = () => {
  artifactApi.updateArtifact(artifact.value.id, artifact.value.convertToDTO())
}
</script>

<style scoped>
.submit-button {
  margin-left: 100px;
}

.el-input {
  margin-left: 20px;
  width: 200px
}
</style>
