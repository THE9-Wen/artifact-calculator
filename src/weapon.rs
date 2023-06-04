#![allow(dead_code)]
#![allow(unused_variables)]
use crate::artifact::{ArtifactKeyword, ArtifactSub};

pub struct Weapon {
    pub atk: f64,
    pub effect: Vec<ArtifactSub>,
}

impl Weapon {
    pub fn new_kagura() -> Self {
        Self {
            atk: 608.0,
            effect: vec![
                ArtifactSub::new(ArtifactKeyword::CritDmg, 0.662),
                ArtifactSub::new(ArtifactKeyword::Bonus, 0.48),
            ]
        }
    }
}
