import axios from 'axios'

const BASE_PATH = 'https://10c4aaad.r7.vip.cpolar.cn/artifact/'

export default {
  getArtifactById (id) {
    return axios.get(`${BASE_PATH}${id}`)
  },
  createArtifact (artifact) {
    return axios.post(`${BASE_PATH}`, artifact)
  },
  updateArtifact (id, artifact) {
    return axios.put(`${BASE_PATH}`, { id, artifact })
  },
  deleteArtifact (id) {
    return axios.delete(`${BASE_PATH}${id}`)
  },
  listArtifacts () {
    return axios.get(`${BASE_PATH}`)
  }
}
