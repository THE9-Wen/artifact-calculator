export class Artifact {
  main
  position
  subs
  suit

  constructor (json) {
    if (!json) {
      this.subs = [{}, {}, {}, {}]
      return
    }
    this.id = json.id
    this.main = artifactMains[json.main.keyword]
    this.position = positions[json.position]
    this.subs = json.subs.map(sub => {
      const result = JSON.parse(JSON.stringify(artifactSubs[sub.keyword]))
      if (Artifact.isKeywordAbs(sub.keyword)) {
        result.value = sub.value
      } else {
        result.value = `${(sub.value * 100).toFixed(1)}%`
      }
      return result
    })
    this.suit = artifactSuits[json.suit]
  }

  static absKeyword = [1, 3, 5, 6]

  static isKeywordAbs (keyword) {
    if (keyword === undefined) {
      return true
    }
    return Artifact.absKeyword.includes(keyword)
  }

  check () {
    const occupiedSubs = new Set()
    if (this.main && !this.main.positions.includes(this.position)) {
      artifactMains.forEach(item => {
        if (item.positions.includes(this.position)) {
          this.main = item
        }
      })
    }
    occupiedSubs.add(this.main?.keyword)
    this.subs.forEach((item, index) => {
      if (occupiedSubs.has(item.keyword)) {
        this.subs[index] = {}
      } else {
        occupiedSubs.add(item.keyword)
      }
    }
    )
  }

  convertToDTO () {
    const dto = {}
    dto.main = {
      keyword: this.main.keyword,
      value: this.main.value
    }
    dto.subs = this.subs.map(item => {
      const subDto = {}
      subDto.keyword = item.keyword
      subDto.value = Artifact.isKeywordAbs(item.keyword) ? item.value / 1 : item.value / 100
      return subDto
    })
    dto.position = this.position
    dto.suit = this.suit
    console.log(dto)
    return dto
  }
}

export const positions = [
  {
    label: '生之花',
    key: 0
  },
  {
    label: '死之羽',
    key: 1
  },
  {
    label: '时之沙',
    key: 2
  },
  {
    label: '空之杯',
    key: 3
  },
  {
    label: '理之冠',
    key: 4
  }
]
export const artifactSubs = [
  {
    label: '攻击力百分比',
    keyword: 0
  },
  {
    label: '攻击力',
    keyword: 1
  },
  {
    label: '生命值百分比',
    keyword: 2
  },
  {
    label: '生命值',
    keyword: 3
  },
  {
    label: '防御力百分比',
    keyword: 4
  },
  {
    label: '防御力',
    keyword: 5
  },
  {
    label: '元素精通',
    keyword: 6
  },
  {
    label: '暴击伤害',
    keyword: 7
  },
  {
    label: '暴击率',
    keyword: 8
  },
  {
    label: '元素充能效率',
    keyword: 9
  }
]
export const artifactMains = [
  {
    label: '攻击力百分比',
    keyword: 0,
    value: 0.466,
    positions: [2, 3, 4]
  },
  {
    label: '攻击力',
    keyword: 1,
    value: 311,
    positions: [1]
  },
  {
    label: '生命值百分比',
    keyword: 2,
    value: 0.466,
    positions: [2, 3, 4]
  },
  {
    label: '生命值',
    keyword: 3,
    value: 4870,
    positions: [0]
  },
  {
    label: '防御力百分比',
    keyword: 4,
    value: 0.466,
    positions: [2, 3, 4]
  },
  {
    label: '防御力',
    keyword: 5,
    value: 0,
    positions: []
  },
  {
    label: '元素精通',
    keyword: 6,
    value: 187,
    positions: [2, 3, 4]
  },
  {
    label: '暴击伤害',
    keyword: 7,
    value: 0.662,
    positions: [4]
  },
  {
    label: '暴击率',
    keyword: 8,
    value: 0.331,
    positions: [4]
  },
  {
    label: '元素充能效率',
    keyword: 9,
    value: 0.466,
    positions: [2]
  },
  {
    label: '治疗加成',
    keyword: 10,
    value: 0.55,
    positions: [4]
  },
  {
    label: '伤害加成',
    keyword: 11,
    value: 0,
    positions: []
  },
  {
    label: '火元素伤害加成',
    keyword: 12,
    value: 0.466,
    positions: [3]
  },
  {
    label: '物理伤害加成',
    keyword: 13,
    value: 0.466,
    positions: [3]
  },
  {
    label: '水元素伤害加成',
    keyword: 14,
    value: 0.466,
    positions: [3]
  },
  {
    label: '草元素伤害加成',
    keyword: 15,
    value: 0.466,
    positions: [3]
  },
  {
    label: '雷元素伤害加成',
    keyword: 16,
    value: 0.466,
    positions: [3]
  },
  {
    label: '风元素伤害加成',
    keyword: 17,
    value: 0.466,
    positions: [3]
  },
  {
    label: '冰元素伤害加成',
    keyword: 18,
    value: 0.466,
    positions: [3]
  },
  {
    label: '岩元素伤害加成',
    keyword: 19,
    value: 0.466,
    positions: [3]
  }
]
export const artifactSuits = [
  { label: '角斗士的终幕礼', key: 0, keyword: 0 },
  { label: '流浪大地的乐团', key: 1, keyword: 6 },
  { label: '昔日宗室之仪', key: 2, keyword: 11 },
  { label: '炽烈的炎之魔女', key: 3, keyword: 12 },
  { label: '追忆之注连', key: 4, keyword: 0 },
  { label: '翠绿之影', key: 5, keyword: 17 },
  { label: '如雷的盛怒', key: 6, keyword: 16 },
  { label: '渡过烈火的贤人', key: 7, keyword: 10 },
  { label: '冰封迷途的勇士', key: 8, keyword: 18 },
  { label: '沉沦之心', key: 9, keyword: 14 },
  { label: '千岩牢固', key: 10, keyword: 2 },
  { label: '绝缘之旗印', key: 11, keyword: 9 },
  { label: '华馆梦醒形骸记', key: 12, keyword: 4 },
  { label: '海染砗磲', key: 13, keyword: 10 },
  { label: '辰砂往生录', key: 14, keyword: 0 },
  { label: '深林的记忆', key: 15, keyword: 15 },
  { label: '饰金之梦', key: 16, keyword: 6 },
  { label: '沙上楼阁史话', key: 17, keyword: 6 },
  { label: '乐园遗落之花', key: 18, keyword: 2 },
  { label: '水仙之梦', key: 19, keyword: 14 },
  { label: '花海甘露之光', key: 20, keyword: 6 },
  { label: '被怜爱的少女', key: 100, keyword: 10 }
]
