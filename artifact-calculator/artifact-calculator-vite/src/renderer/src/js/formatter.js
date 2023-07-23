export function getPercentValue(value) {
  return `${Math.round(value * 1000) / 10}%`
}

export function geIntegerValue(value) {
  return Math.round(value)
}
