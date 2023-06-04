#![allow(dead_code)]
#![allow(unused_variables)]
extern crate core;

use std::env;

use image::ImageError;

use crate::artifact::ArtifactKeyword;
use crate::calculator::Calculator;
use crate::character::{BasicValue, Character};
use crate::preanalyzer::PreAnalyzer;
use crate::reaction::Reaction;
use crate::weapon::Weapon;

mod preanalyzer;
mod artifact;
mod character;
mod weapon;
mod monster;
mod damage;
mod reaction;
mod calculator;

fn main() -> Result<(), ImageError> {
    let args: Vec<String> = env::args().collect();
    match args.get(1) {
        Some(process) => {
            match process.as_str() {
                "--preanalyze" => {
                    preanalyze()?;
                },
                "--calculate" => {
                    calculate();
                },
                _ => {
                    println!("Unsupported command: {}", process);
                    return Ok(());
                },
            }
        },
        None => return Ok(()),
    }
    Ok(())
}

fn preanalyze() -> Result<(), ImageError> {
    let args: Vec<String> = env::args().collect();
    let suit_path = if let Some(path) = args.get(2) {
        path
    } else {
        "artifacts/suit/"
    };
    let single_path = if let Some(path) = args.get(3) {
        path
    } else {
        "artifacts/single/"
    };
    println!("suit_path: {}, single_path: {}", suit_path, single_path);
    let analyzer = PreAnalyzer::new(suit_path.to_string(), single_path.to_string());
    analyzer.pre_analyze()?;
    Ok(())
}

fn calculate() {
    let args: Vec<String> = env::args().collect();
    let character_name = args.get(2);
    let mut reaction = None;
    let mut character = if let Some(name) = character_name {
        match name.as_str() {
            "yea_miko" => {
                reaction = Some(Reaction::AGGRAVATE);
                let mut yea_miko = Character::new(BasicValue::yea_miko());
                yea_miko.update_character_value(&ArtifactKeyword::Mastery, &300.0);
                yea_miko.equip_weapon(&Weapon::new_kagura());
                yea_miko
            },
            "hutao" => {
                println!("Calculating hutao!");
                panic!("hello");
            },
            _ => {
                panic!("Unsupported character: {}", name)
            }
        }
    } else {
        panic!("Please input character name, use --help to see supported characters.")
    };
    Calculator::calculate(&character, &reaction);
}
