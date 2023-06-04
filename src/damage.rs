#![allow(dead_code)]
#![allow(unused_variables)]
use crate::reaction::Reaction;

pub struct Damage {
    pub basic_dmg: f64,
    pub react_dmg: f64,
    pub bonus: f64,
    pub crit_dmg: f64,
    pub crit_rate: f64,
    pub resist: f64,
}

impl Damage {
    /**
    暴击之后的伤害
    */
    pub fn crit_value(&mut self, react: &bool) -> f64 {
        self.value(react) * self.crit_dmg
    }

    /**
    不暴击的伤害
    */
    pub fn value(&mut self, react: &bool) -> f64 {
        let value = if *react {
            self.react_dmg
        } else {
            self.basic_dmg
        };
        value * self.bonus * self.resist
    }

    /**
    期望伤害
    */
    pub fn crit_expectation(&mut self, react: &bool) -> f64 {
        if self.crit_rate > 1.0 {
            self.crit_value(react)
        } else {
            self.crit_value(react) * self.crit_rate + self.value(react) * (1.0 - self.crit_rate)
        }
    }

    pub fn default() -> Self {
        Self {
            basic_dmg: 0.0,
            react_dmg: 0.0,
            bonus: 1.0,
            crit_dmg: 1.5,
            crit_rate: 0.05,
            resist: 1.0,
        }
    }

    /**
    因为需要根据具体角色确定反应类型因此反应的方法暴露出去
    */
    pub fn react(&mut self, reaction: &Reaction, mastery: &f64) {
        match reaction {
            Reaction::MELT => {
                self.react_dmg = self.basic_dmg * 1.5 * (1.0 + (2.78 * mastery) / (1400.0 + mastery));
            }
            Reaction::AGGRAVATE => {
                self.react_dmg = self.basic_dmg + 1.15 * 1405.097377 * (1.0 + (5.0 * mastery) / (1200.0 + mastery));
            }
            Reaction::SPREAD => {
                self.react_dmg = self.basic_dmg + 1.15 * 1405.097377 * (1.0 + (5.0 * mastery) / (1200.0 + mastery))
            }
            Reaction::VAPORIZE => {
                self.react_dmg = self.basic_dmg * 1.5 * (1.0 + (2.78 * mastery) / (1400.0 + mastery));
            }
        }
    }
}
