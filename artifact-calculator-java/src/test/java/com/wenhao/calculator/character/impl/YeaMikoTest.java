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

class YeaMikoTest {
    @Test
    void test_hit_without_artifact() {
        YeaMiko yeaMiko = new YeaMiko();
        yeaMiko.equipWeapon(Weapon.KAGURAS_VERITY);
        Damage damage = yeaMiko.hit(new Monster(85, 0.1));
        assertEquals(1085.0, damage.basicDamage(false), 5.0);
        assertEquals(2173.0, damage.basicDamage(true), 5.0);
        assertEquals(2347.0, damage.critDamage(false), 5.0);
        assertEquals(7268.0, damage.critDamage(true), 5.0);
        assertEquals(3522.0, damage.expectationDamage(false), 5.0);
        assertEquals(5283.0, damage.expectationDamage(true), 5.0);
    }

    @Test
    void test_hit_with_mastery() {
        YeaMiko yeaMiko = new YeaMiko();
        yeaMiko.equipWeapon(Weapon.KAGURAS_VERITY);
        Artifact artifact = new Artifact();
        artifact.setMain(new ArtifactSub().setKeyword(MASTERY).setValue(187.0));
        List<ArtifactSub> artifactSubs = List.of(new ArtifactSub().setKeyword(HP).setValue(0.245),
                new ArtifactSub().setKeyword(CRIT_DMG).setValue(0.07),
                new ArtifactSub().setKeyword(CRIT_RATE).setValue(0.07),
                new ArtifactSub().setKeyword(ATK_ABS).setValue(19.0));
        artifact.setSubs(artifactSubs);
        yeaMiko.equipArtifact(artifact);
        Damage damage = yeaMiko.hit(new Monster(85, 0.1));
        assertEquals(1085.0, damage.basicDamage(false), 5.0);
        assertEquals(2173.0, damage.basicDamage(true), 5.0);
        assertEquals(2347.0, damage.critDamage(false), 5.0);
        assertEquals(7268.0, damage.critDamage(true), 5.0);
        assertEquals(3522.0, damage.expectationDamage(false), 5.0);
        assertEquals(5283.0, damage.expectationDamage(true), 5.0);
    }
}
