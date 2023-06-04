#![allow(dead_code)]
#![allow(unused_variables)]

use std::collections::VecDeque;
use std::fs::{File, OpenOptions};
use std::io::{Read, Write};

use crate::artifact::{Artifact, ArtifactSub, ParseError};
use crate::ArtifactKeyword;
use crate::character::Character;
use crate::monster::Monster;
use crate::reaction::Reaction;

pub struct Calculator {}

impl Calculator {
    pub fn calculate(character: &Character, reaction: &Option<Reaction>) {
        let mut suits_json = File::open("artifacts/suit/artifacts.json").unwrap();
        let mut suits_buffer = String::new();
        suits_json.read_to_string(&mut suits_buffer);
        let mut singles_json = File::open("artifacts/single/artifacts.json").unwrap();
        let mut singles_buffer = String::new();
        singles_json.read_to_string(&mut singles_buffer);
        let suits: Vec<Artifact> = serde_json::from_str(&suits_buffer).unwrap();
        let singles: Vec<Artifact> = serde_json::from_str(&singles_buffer).unwrap();
        let mut max_expectation = 0.0;
        let mut best_artifacts = [None, None, None, None, None];
        for single in singles.iter() {
            let result = Self::traverse_suits(&suits, single, character, reaction);
            if result.0 > max_expectation {
                max_expectation = result.0;
                best_artifacts = result.1;
            }
        }
        println!("max_expectation: {}, best_artifacts: {}", max_expectation, serde_json::to_string(&best_artifacts).unwrap());
    }

    fn traverse_suits<'a>(suits: &'a Vec<Artifact>, single: &'a Artifact, character: &'a Character, reaction: &'a Option<Reaction>) -> (f64, [Option<&'a Artifact>; 5]) {
        let mut equipped_artifacts: [Option<&Artifact>; 5] = [None, None, None, None, None];
        equipped_artifacts[single.position] = Some(single);
        let mut queue = VecDeque::new();
        for artifact in suits {
            if let None = equipped_artifacts[artifact.position] {
                equipped_artifacts[artifact.position] = Some(artifact);
            } else if artifact.position != single.position {
                queue.push_back(artifact);
            }
        }
        for artifact in equipped_artifacts {
            if let None = artifact {
                panic!("The supplied artifacts are unable to fit in five different positions.");
            }
        }
        let mut max_expectation = 0.0;
        let mut best_artifacts = [None, None, None, None, None];
        let mut result = OpenOptions::new().create(true).append(true).open("result/yea_miko_with_party.txt").unwrap();
        result.write_all("攻击力\t元素精通\t暴击伤害\t暴击率\t伤害加成\t基础伤害\t激化伤害\t三次期望伤害\t圣遗物搭配\t\n".as_bytes());
        loop {
            let mut character_clone = character.clone();
            let artifact = queue.pop_front().unwrap();
            equipped_artifacts[artifact.position] = Some(artifact);
            let suit_subs = Self::get_valid_suit_subs(&equipped_artifacts, single).expect("All suit are invalid.");
            character_clone.equip_suit(suit_subs);
            character_clone.equip_artifact_subs(&equipped_artifacts);
            character_clone.update_character_value(&ArtifactKeyword::Bonus, &(character_clone.mastery * 0.0015));
            let mut damage = character_clone.hit(&Monster::default(), reaction);
            let with_reaction = damage.crit_expectation(&true);
            let no_reaction = damage.crit_expectation(&false);
            let real_value = with_reaction + no_reaction * 2.0;
            let mut artifacts = String::new();
            for artifact in equipped_artifacts {
                artifacts.push_str(&artifact.unwrap().file_name)
            }
            result.write_all(format!("{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t\n",
                                     character_clone.atk, character_clone.mastery, character_clone.crit_dmg, character_clone.crit_rate, character_clone.bonus,
                no_reaction, with_reaction, real_value, artifacts
            ).as_bytes());
            if real_value > max_expectation {
                max_expectation = real_value;
                best_artifacts.clone_from(&equipped_artifacts);
            }
            if queue.len() == 0 {
                break;
            }
        }
        (max_expectation, best_artifacts)
    }

    pub fn get_valid_suit_subs<'a>(artifacts: &'a [Option<&Artifact>; 5], single: &'a Artifact) -> Option<&'a Vec<ArtifactSub>> {
        for artifact in artifacts {
            if let Some(artifact) = *artifact {
                if artifact.position != single.position && !artifact.error.contains(&ParseError::SuitError) {
                    return Some(&artifact.suit);
                }
            }
        }
        None
    }
}
