package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.monster.Monster;
import com.wenhao.calculator.weapon.Weapon;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.wenhao.calculator.artifact.enums.Keyword.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 因为只有元素反应的逻辑是复杂的
 * 所以只测试有元素精通和无元素精通的场景
 */
class HutaoTest {
    @Test
    void test_hit_without_artifact() {
        Hutao hutao = new Hutao();
        hutao.equipWeapon(Weapon.DEATHMATCH);
        Damage damage = hutao.hit(new Monster());
        assertEquals(2570.0, damage.basicDamage(false), 5.0);
        assertEquals(3856.0, damage.basicDamage(true), 5.0);
        assertEquals(4845.0, damage.critDamage(false), 5.0);
        assertEquals(7268.0, damage.critDamage(true), 5.0);
        assertEquals(3522.0, damage.expectationDamage(false), 5.0);
        assertEquals(5283.0, damage.expectationDamage(true), 5.0);
    }

    @Test
    void test_hit_with_mastery() {
        Hutao hutao = new Hutao();
        hutao.equipWeapon(Weapon.DEATHMATCH);
        Artifact artifact = new Artifact();
        artifact.setMain(new ArtifactSub().setKeyword(MASTERY).setValue(187.0));
        List<ArtifactSub> artifactSubs = List.of(new ArtifactSub().setKeyword(HP_ABS).setValue(1374.0),
                new ArtifactSub().setKeyword(CRIT_DMG).setValue(0.07),
                new ArtifactSub().setKeyword(CRIT_RATE).setValue(0.066),
                new ArtifactSub().setKeyword(ENERGY_RECHARGE).setValue(0.045));
        artifact.setSubs(artifactSubs);
        hutao.equipArtifact(artifact);
        Damage damage = hutao.hit(new Monster());
        System.out.println(hutao);
        assertEquals(2696.0, damage.basicDamage(false), 5.0);
//        assertEquals(5365.0, damage.basicDamage(true), 5.0);
//        assertEquals(0.0, damage.critDamage(false), 5.0);
        assertEquals(10484.0, damage.critDamage(true), 5.0);
//        assertEquals(0.0, damage.expectationDamage(false), 5.0);
//        assertEquals(0.0, damage.expectationDamage(true), 5.0);
    }
}
