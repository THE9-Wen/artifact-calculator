#![allow(dead_code)]
#![allow(unused_variables)]

use serde::{Deserialize, Serialize};

const KEYWORDS: [&str; 8] = ["暴击率", "暴击伤害", "元素精通", "元素充能效率", "攻击力", "生命值", "防御力", "伤害加成"];
const POSITION: [&str; 5] = ["生之花", "死之羽", "时之沙", "空之杯", "理之冠"];
const NUMBERS: [char; 11] = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'];
const SUITS: [&str; 4] = ["角斗士", "沉沦", "饰金", "辰砂"];

#[derive(Serialize, Deserialize)]
pub struct Artifact {
    pub keywords: Vec<ArtifactSub>,
    pub position: usize,
    pub suit: Vec<ArtifactSub>,
    pub error: Vec<ParseError>,
    pub file_name: String,
}

fn is_number(c: &char) -> bool {
    NUMBERS.contains(c)
}

#[derive(Serialize, Deserialize, PartialEq)]
pub enum ParseError {
    PositionErr,
    SuitError,
    SubError,
}

impl Artifact {
    pub fn from(str: &String, file_name: String) -> Self {
        let keywords = Artifact::analyze_sub(str);
        let mut error = vec![];
        if keywords.len() != 5 {
            error.push(ParseError::SubError);
        }
        let suit = if let Some(suit) = Artifact::analyze_suit(str) {
            suit
        } else {
            error.push(ParseError::SuitError);
            vec![]
        };
        let position = if let Some(position) = Artifact::analyze_position(str) {
            position
        } else {
            error.push(ParseError::PositionErr);
            5
        };
        Self {
            keywords,
            position,
            suit,
            error,
            file_name,
        }
    }

    /**
    副词条
     */
    fn analyze_sub(str: &String) -> Vec<ArtifactSub> {
        let mut keywords = vec![];
        for keyword in KEYWORDS {
            let indices: Vec<usize> = str.match_indices(keyword).map(|(i, _)| i).collect();
            for index in indices.iter() {
                let mut value = String::new();
                for b in str[index + keyword.len()..].as_bytes() {
                    let c = *b as char;
                    if c == '+' && value.len() == 0 {
                        continue;
                    }
                    if is_number(&c) {
                        value.push(c);
                        continue;
                    }
                    if let Ok(v) = value.parse::<f64>() {
                        if c == '%' {
                            keywords.push(
                                ArtifactKeyword::from(format!("{}{}", keyword, "百分比").as_str(), v / 100.0).unwrap());
                        } else {
                            keywords.push(
                                ArtifactKeyword::from(keyword, v).unwrap());
                        };
                    }
                    break;
                }
            }
        }
        keywords
    }

    /**
    套装效果的词条 其中第一个为两件套效果 后面的为四件套效果 可以传入层数
     */
    fn analyze_suit(str: &String) -> Option<Vec<ArtifactSub>> {
        for suit in SUITS {
            if str.contains(suit) {
                return SuitFactory::create_suit(suit);
            }
        }
        None
    }

    fn analyze_position(str: &String) -> Option<usize> {
        let mut index = 0;
        for position in POSITION {
            if str.contains(position) {
                return Some(index);
            }
            index += 1;
        }
        None
    }
}

pub struct SuitFactory {}

impl SuitFactory {
    pub fn create_suit(str: &str) -> Option<Vec<ArtifactSub>> {
        let mut suit = vec![];
        match str {
            "角斗士" => {
                suit.push(ArtifactKeyword::from("攻击力百分比", 0.18).unwrap());
                suit.push(ArtifactKeyword::from("伤害加成百分比", 0.35).unwrap());
            }
            "沉沦" => {
                suit.push(ArtifactKeyword::from("伤害加成百分比", 0.15).unwrap());
                suit.push(ArtifactKeyword::from("伤害加成百分比", 0.30).unwrap());
            }
            "饰金" => {
                suit.push(ArtifactKeyword::from("元素精通", 80.0).unwrap());
                suit.push(ArtifactKeyword::from("元素精通", 50.0).unwrap());
                suit.push(ArtifactKeyword::from("元素精通", 50.0).unwrap());
                suit.push(ArtifactKeyword::from("元素精通", 50.0).unwrap());
            }
            "辰砂" => {
                suit.push(ArtifactKeyword::from("攻击力百分比", 0.18).unwrap());
                suit.push(ArtifactKeyword::from("攻击力百分比", 0.08).unwrap());
                suit.push(ArtifactKeyword::from("攻击力百分比", 0.1).unwrap());
                suit.push(ArtifactKeyword::from("攻击力百分比", 0.1).unwrap());
                suit.push(ArtifactKeyword::from("攻击力百分比", 0.1).unwrap());
            }
            _ => {
                return None;
            }
        }
        Some(suit)
    }
}

#[derive(Clone, Serialize, Deserialize)]
pub enum ArtifactKeyword {
    Hp,
    HpAbs,
    Atk,
    AtkAbs,
    Mastery,
    CritRate,
    CritDmg,
    Defence,
    DefenceAbs,
    Charging,
    Bonus,
}

impl ArtifactKeyword {
    pub fn from(str: &str, value: f64) -> Option<ArtifactSub> {
        match str {
            "暴击率百分比" => Some(ArtifactSub::new(ArtifactKeyword::CritRate, value)),
            "暴击伤害百分比" => Some(ArtifactSub::new(ArtifactKeyword::CritDmg, value)),
            "元素精通" => Some(ArtifactSub::new(ArtifactKeyword::Mastery, value)),
            "元素充能效率百分比" => Some(ArtifactSub::new(ArtifactKeyword::Charging, value)),
            "攻击力" => Some(ArtifactSub::new(ArtifactKeyword::AtkAbs, value)),
            "攻击力百分比" => Some(ArtifactSub::new(ArtifactKeyword::Atk, value)),
            "防御力" => Some(ArtifactSub::new(ArtifactKeyword::DefenceAbs, value)),
            "防御力百分比" => Some(ArtifactSub::new(ArtifactKeyword::Defence, value)),
            "生命值" => Some(ArtifactSub::new(ArtifactKeyword::HpAbs, value)),
            "生命值百分比" => Some(ArtifactSub::new(ArtifactKeyword::Hp, value)),
            "伤害加成百分比" => Some(ArtifactSub::new(ArtifactKeyword::Bonus, value)),
            _ => None,
        }
    }
}

#[derive(Serialize, Deserialize)]
pub struct ArtifactSub {
    pub keyword_type: ArtifactKeyword,
    pub value: f64,
}

impl ArtifactSub {
    pub fn new(keyword_type: ArtifactKeyword, value: f64) -> Self {
        Self {
            keyword_type,
            value,
        }
    }
}


#[cfg(test)]
mod test {
    use crate::artifact::Artifact;

    #[test]
    fn test_artifact_from() {
        let artifact = Artifact::from(&String::from("5生

岩之.

时之沙
攻击力46.6%
+20

暴击率+7.0%

元素精通+37

暴击伤害+12.4%

.防御力+10.9%

.生2

2御攻击力18%

4元素生
16生效攻击力8
%角生命值时攻击
力10%通
40.8生

效时角角时
攻元素效

时冰
生羽

212378990"), String::from(""));
    }
}
