import axios from 'axios'

const BASE_PATH = 'http://localhost:8080/calculator'
export default {
  calculate({ name, weapon, artifacts }) {
    return axios.post(`${BASE_PATH}/calculate`, { name, weapon, artifacts })
  },
  suit({ name, suit, weapon, keywords }) {
    return axios.post(`${BASE_PATH}/suit`, { name, suit, weapon, keywords })
  },
  doubleSuit({ name, suitKeyword1, suitKeyword2, weapon, keywords }) {
    return axios.post(`${BASE_PATH}/doubleSuit`, {
      name,
      suitKeyword1,
      suitKeyword2,
      weapon,
      keywords
    })
  },
  getExcel() {
    return axios.get(`${BASE_PATH}/getExcel`, { responseType: 'blob' })
  }
}
