package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.monster.Monster;
import org.junit.jupiter.api.Test;

import static com.wenhao.calculator.weapon.Weapon.KAGURAS_VERITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 目标相对误差千分之一
 */
class YeaMikoTest {
    @Test
    void test_basicValue() {
        YeaMiko yeaMiko = new YeaMiko(89);
        assertEquals(337, yeaMiko.getAtk(), 1.0);
        assertEquals(565, yeaMiko.getDefence(), 1.0);
        assertEquals(10300, yeaMiko.getHp(), 1.0);
    }

    /**
     * 测试环境
     * 89级零命神子 90级精一神乐 双草无圣遗物 90级公义
     */
    @Test
    void test_hit_without_artifact() {
        YeaMiko yeaMiko = new YeaMiko(89);
        yeaMiko.equipWeapon(KAGURAS_VERITY);
        Damage damage = yeaMiko.hit(new Monster(90, 0.1));
        assertEquals(0, damage.basicDamage(false) / 1180.0 - 1.0, 0.001);
        assertEquals(0, damage.critDamage(false) / 2550.0 - 1.0, 0.001);
        assertEquals(0, damage.basicDamage(true) / 2816.0 - 1.0, 0.001);
        assertEquals(0, damage.critDamage(true) / 6088.0 - 1.0, 0.001);
    }
}
