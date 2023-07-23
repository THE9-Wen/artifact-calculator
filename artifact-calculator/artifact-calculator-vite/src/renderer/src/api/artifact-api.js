import axios from 'axios'

const BASE_PATH = 'http://127.0.0.1:10107/artifact/'

export default {
  getArtifactById(id) {
    return axios.get(`${BASE_PATH}${id}`)
  },
  createArtifact(artifact) {
    return axios.post(`${BASE_PATH}`, artifact)
  },
  updateArtifact(id, artifact) {
    return axios.put(`${BASE_PATH}`, { id, artifact })
  },
  deleteArtifact(id) {
    return axios.delete(`${BASE_PATH}${id}`)
  },
  listArtifacts() {
    return axios.get(`${BASE_PATH}`)
  }
}
