import { Artifact } from '../artifact/artifact'

export const handleCalculateResult = (data) => {
  const result = {}
  Object.keys(data).forEach((key) => {
    if (key === 'artifacts') {
      result[key] = data.artifacts.map((item) => new Artifact(item))
    } else {
      result[key] = data[key]
    }
  })
  return result
}

export const exportExcel = (data, fileName) => {
  const blob = new Blob([data], { type: 'application/vnd.ms-excel' })
  const objectUrl = URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = objectUrl
  link.download = `${fileName}-伤害计算表格.xlsx`
  document.body.appendChild(link)
  link.click()
  window.URL.revokeObjectURL(link.href)
}
