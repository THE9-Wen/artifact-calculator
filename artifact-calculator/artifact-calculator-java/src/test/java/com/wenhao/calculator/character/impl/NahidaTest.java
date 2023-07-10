package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.monster.Monster;
import com.wenhao.calculator.weapon.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NahidaTest {
    @Test
    public void test_hit_without_artifact() {
        Nahida nahida = new Nahida();
        nahida.equipWeapon(Weapon.PROTOTYPE_AMBER);
        Damage damage = nahida.hit(new Monster(85, 0.1));
        assertEquals(1061.0, damage.basicDamage(false), 5.0);
        assertEquals(2488.0, damage.basicDamage(true), 5.0);
//        assertEquals(4845.0, damage.critDamage(false), 5.0);
//        assertEquals(7268.0, damage.critDamage(true), 5.0);
//        assertEquals(3522.0, damage.expectationDamage(false), 5.0);
//        assertEquals(5283.0, damage.expectationDamage(true), 5.0);
    }
}
