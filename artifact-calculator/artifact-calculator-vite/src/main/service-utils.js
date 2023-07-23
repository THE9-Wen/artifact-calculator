const { exec } = require('child_process')

let process
export const startService = () => {
  process = exec('java -jar ./web-service/artifact-calculator-0.0.1-SNAPSHOT.jar')
}

export const killService = () => {
  if (process) {
    exec(`kill -9 ${process.pid}`)
  }
}
