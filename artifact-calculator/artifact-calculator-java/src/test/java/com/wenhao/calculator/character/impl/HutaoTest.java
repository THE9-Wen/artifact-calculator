package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.monster.Monster;
import com.wenhao.calculator.weapon.Weapon;
import org.junit.jupiter.api.Test;

import static com.wenhao.calculator.artifact.enums.Keyword.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HutaoTest {
    @Test
    void test_basicValue() {
        Hutao hutao = new Hutao(89);
        assertEquals(106, hutao.getAtk(), 1.0);
        assertEquals(870, hutao.getDefence(), 1.0);
        assertEquals(15443, hutao.getHp(), 1.0);
    }

    @Test
    void test_hit_without_artifact() {
        Hutao hutao = new Hutao(89);
        hutao.equipWeapon(Weapon.DEATHMATCH);
        hutao.updateCharacterValue(new ArtifactSub().setKeyword(MASTERY).setValue(187.0f));
        hutao.updateCharacterValue(new ArtifactSub().setKeyword(ATK_ABS).setValue(16f));
        hutao.updateCharacterValue(new ArtifactSub().setKeyword(HP).setValue(0.041f));
        hutao.updateCharacterValue(new ArtifactSub().setKeyword(CRIT_RATE).setValue(0.167f));
        Damage damage = hutao.hit(new Monster(90, 0.1));
        assertEquals(0,  damage.critDamage(false) / 5613.0 - 1.0, 0.001);
        assertEquals(0, damage.critDamage(true) / 11172.0 - 1.0, 0.001);
    }
}
