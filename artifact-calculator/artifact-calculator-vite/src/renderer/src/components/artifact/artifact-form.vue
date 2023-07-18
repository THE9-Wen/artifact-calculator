<template>
  <div class="artifact-form">
    <el-form ref="artifactForm" label-position="right" label-width="100px" :model="artifact">
      <el-form-item prop="suit" required>
        <template #label> 圣遗物套装： </template>
        <el-select v-model="artifact.suit">
          <el-option
            v-for="suit in artifactSuits"
            :key="suit.key"
            :value="suit.key"
            :label="suit.label"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="position" required>
        <template #label> 圣遗物位置： </template>
        <el-select v-model="artifact.position" @change="artifact.check()">
          <el-option
            v-for="position in positions"
            :key="position.key"
            :value="position.key"
            :label="position.label"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="main" required>
        <template #label> 主词条： </template>
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
      <el-form-item
        v-for="(sub, index) in artifact.subs"
        :key="index"
        :prop="`subs.${index}`"
        :rules="subRules"
        required
      >
        <template #label> 副词条{{ index }}： </template>
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
          v-model="sub.value"
          label="值"
        ></el-input>
        <el-input v-else v-model="sub.value">
          <template #append>%</template>
        </el-input>
      </el-form-item>
    </el-form>
    <div>
      <el-button class="submit-button" @click="submit">提交</el-button>
      <el-button class="submit-button" @click="clear">清空副词条</el-button>
      <el-button v-if="artifact.id" class="submit-button" @click="update">更新</el-button>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { Artifact, artifactMains, artifactSubs, artifactSuits, positions } from './artifact.js'
import artifactApi from '../../api/artifact-api.js'
import { ElMessage } from 'element-plus'

const artifact = ref(new Artifact())

const filteredMains = computed(() => {
  return artifactMains.filter((item) => item.positions.includes(artifact.value.position))
})

const filteredSubs = computed(() => {
  return artifactSubs.filter((item) => {
    const subDuplicated = artifact.value.subs.filter((sub) => sub.keyword === item.keyword).length
    const mainDuplicated = artifact.value.main?.keyword === item.keyword
    return !subDuplicated && !mainDuplicated
  })
})

const artifactForm = ref(null)

const submit = () => {
  artifactForm.value.validate().then(() => {
    artifactApi
      .createArtifact(artifact.value.convertToDTO())
      .then((response) => {
        artifact.value.id = response.id
        ElMessage({
          message: '提交成功',
          type: 'success'
        })
      })
      .catch((e) => {
        ElMessage({
          message: e.response.message,
          type: 'error'
        })
      })
  })
}

const subRules = [
  {
    validator: (rule, value, callBack) => {
      if (!value.keyword && !value.value) {
        callBack(new Error('artifact sub is required'))
      } else {
        callBack()
      }
    },
    trigger: 'blur'
  }
]

const clear = () => {
  artifact.value.subs.forEach((item) => {
    item.value = undefined
  })
  artifactForm.value.clearValidate()
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
  width: 200px;
}
</style>
