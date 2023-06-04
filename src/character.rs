#![allow(dead_code)]
#![allow(unused_variables)]
use crate::artifact::{Artifact, ArtifactKeyword, ArtifactSub};
use crate::damage::Damage;
use crate::monster::Monster;
use crate::reaction::Reaction;
use crate::weapon::Weapon;

#[derive(Clone)]
pub struct BasicValue {
    pub hp: f64,
    pub atk: f64,
    pub crit_dmg: f64,
    pub crit_rate: f64,
    pub defence: f64,
    pub charging: f64,
    pub bonus: f64,
    pub mastery: f64,
    pub skill_dmg: f64,
}

impl BasicValue {
    pub fn yea_miko() -> Self {
        Self {
            hp: 10300.0,
            atk: 337.0,
            crit_dmg: 0.5,
            crit_rate: 0.242,
            defence: 565.0,
            charging: 1.0,
            bonus: 0.0,
            mastery: 0.0,
            skill_dmg: 1.706,
        }
    }
}

#[derive(Clone)]
pub struct Character {
    pub level: f64,
    pub atk: f64,
    pub hp: f64,
    pub crit_dmg: f64,
    pub crit_rate: f64,
    pub bonus: f64,
    pub mastery: f64,
    pub charging: f64,
    pub defence: f64,
    pub basic_value: BasicValue,
}

impl Character {
    pub fn new(basic_value: BasicValue) -> Self {
        Self {
            level: 89.0,
            atk: basic_value.atk,
            hp: basic_value.hp,
            crit_dmg: basic_value.crit_dmg,
            crit_rate: basic_value.crit_rate,
            bonus: basic_value.bonus,
            mastery: basic_value.mastery,
            charging: basic_value.charging,
            defence: basic_value.defence,
            basic_value,
        }
    }

    pub fn equip_weapon(&mut self, weapon: &Weapon) {
        self.atk += weapon.atk;
        self.basic_value.atk += weapon.atk;
        for sub in weapon.effect.iter() {
            self.update_character_value(&sub.keyword_type, &sub.value);
        }
    }

    pub fn update_character_value(&mut self, artifact_keyword: &ArtifactKeyword, value: &f64) {
        match artifact_keyword {
            ArtifactKeyword::Hp => self.hp += self.basic_value.hp * value,
            ArtifactKeyword::Atk => self.atk += self.basic_value.atk * value,
            ArtifactKeyword::Mastery => self.mastery += value,
            ArtifactKeyword::CritRate => self.crit_rate += value,
            ArtifactKeyword::CritDmg => self.crit_dmg += value,
            ArtifactKeyword::Bonus => self.bonus += value,
            ArtifactKeyword::AtkAbs => self.atk += value,
            ArtifactKeyword::HpAbs =>  self.hp += value ,
            ArtifactKeyword::Defence => self.defence += value,
            ArtifactKeyword::DefenceAbs => self.defence += self.basic_value.defence * value,
            ArtifactKeyword::Charging => self.charging += value,
        }
    }

    pub fn hit(&self, monster: &Monster, reaction: &Option<Reaction>) -> Damage {
        let mut damage = Damage::default();
        let level_resist = self.level_resist(monster);
        let element_resist = self.element_resist(monster);
        damage.resist = level_resist * element_resist;
        damage.basic_dmg = self.basic_damage();
        damage.crit_dmg = self.crit_dmg + 1.0;
        damage.crit_rate = self.crit_rate;
        damage.bonus = 1.0 + self.bonus;
        if let Some(reaction) = reaction {
            damage.react(reaction, &self.mastery);
        }
        damage
    }

    /**
    等级抗性区
     */
    pub(crate) fn level_resist(&self, monster: &Monster) -> f64 {
        (self.level + 100.0) / (self.level + monster.level + 200.0)
    }

    /**
    怪物抗性区
     */
    pub(crate) fn element_resist(&self, monster: &Monster) -> f64 {
        let resist = monster.resist;
        if resist < 0.0 {
            1.0 - resist / 2.0
        } else if resist < 0.75 {
            1.0 - resist
        } else {
            1.0 / (1.0 + 4.0 * resist)
        }
    }

    /**
    攻击力基础伤害
     */
    pub(crate) fn basic_damage(&self) -> f64 {
        self.atk * self.basic_value.skill_dmg
    }

    pub fn print(&self) {
        println!("atk: {}, mastery: {}, crit_dmg: {}, crit_rate: {}, bonus: {}", self.atk, self.mastery, self.crit_dmg, self.crit_rate, self.bonus);
    }

    pub fn equip_suit(&mut self, suit: &Vec<ArtifactSub>) {
        for sub in suit.iter() {
            self.update_character_value(&sub.keyword_type, &sub.value);
        }
    }

    pub fn equip_artifact_subs(&mut self, artifacts: &[Option<&Artifact>; 5]) {
        for item in artifacts {
            if let Some(artifact) = item {
                self.equip_sub(artifact);
            }
        }
    }

    pub fn equip_sub(&mut self, artifact: &Artifact) {
        for sub in artifact.keywords.iter() {
            self.update_character_value(&sub.keyword_type, &sub.value);
        }
    }
}

#[cfg(test)]
mod test {
    use assert_approx_eq::assert_approx_eq;
    use crate::artifact::ArtifactKeyword;
    use crate::character::{BasicValue, Character};
    use crate::monster::Monster;
    use crate::reaction::Reaction;
    use crate::weapon::Weapon;

    #[test]
    fn validate_no_mastery() {
        let yea_miko_basic_value = BasicValue::yea_miko();
        let mut yea_miko = Character::new(yea_miko_basic_value);
        let kagura = Weapon::new_kagura();
        yea_miko.equip_weapon(&kagura);
        yea_miko.update_character_value(&ArtifactKeyword::Bonus, &(yea_miko.mastery * 0.0015));
        let mut damage = yea_miko.hit(&Monster::new(85.0, 0.1), &Some(Reaction::AGGRAVATE));
        assert_approx_eq!(2346.0, damage.crit_value(&false), 1.0);
        assert_approx_eq!(1085.0, damage.value(&false), 1.0);
        assert_approx_eq!(4698.0, damage.crit_value(&true), 1.0);
        assert_approx_eq!(2173.0, damage.value(&true), 1.0);
    }

    #[test]
    fn validate_with_mastery() {
        let yea_miko_basic_value = BasicValue::yea_miko();
        let mut yea_miko = Character::new(yea_miko_basic_value);
        let kagura = Weapon::new_kagura();
        yea_miko.equip_weapon(&kagura);
        yea_miko.update_character_value(&ArtifactKeyword::Mastery, &187.0);
        yea_miko.update_character_value(&ArtifactKeyword::AtkAbs, &35.0);
        yea_miko.update_character_value(&ArtifactKeyword::Bonus, &(yea_miko.mastery * 0.0015));
        let mut damage = yea_miko.hit(&Monster::new(85.0, 0.1), &Some(Reaction::AGGRAVATE));
        assert_approx_eq!(2894.0, damage.crit_value(&false), 5.0);
        assert_approx_eq!(1338.0, damage.value(&false), 5.0);
        assert_approx_eq!(7573.0, damage.crit_value(&true), 5.0);
        assert_approx_eq!(3501.0, damage.value(&true), 5.0);
    }
}
