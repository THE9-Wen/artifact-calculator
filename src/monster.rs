#![allow(dead_code)]
#![allow(unused_variables)]
pub struct Monster {
    pub level: f64,
    pub resist: f64,
}

impl Monster {
    pub fn default() -> Self {
        Self {
            level: 93.0,
            resist: -0.1,
        }
    }

    pub fn new(level: f64, resist: f64) -> Self {
        Self {
            level,
            resist,
        }
    }
}
