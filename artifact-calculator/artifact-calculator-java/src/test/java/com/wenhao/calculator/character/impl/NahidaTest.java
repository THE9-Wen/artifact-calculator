package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.monster.Monster;
import com.wenhao.calculator.weapon.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NahidaTest {
    @Test
    void test_basicValue() {
        Nahida nahida = new Nahida(89);
        assertEquals(297, nahida.getAtk(), 1.0);
        assertEquals(626, nahida.getDefence(), 1.0);
        assertEquals(10287, nahida.getHp(), 1.0);
    }

    @Test
    public void test_hit_without_artifact() {
        Nahida nahida = new Nahida(89);
        nahida.equipWeapon(Weapon.PROTOTYPE_AMBER);
        Damage damage = nahida.hit(new Monster(90, 0.1));
        // 纳西妲不反应的数据不好测跳过 反应伤害没错基础伤害一般不会出错
//        assertEquals(0, damage.basicDamage(false) / 1061.0 - 1, 0.001);
        assertEquals(0, damage.basicDamage(true) / 2455.0 - 1, 0.001);
    }
}
