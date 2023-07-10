package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.calculator.Reaction;
import com.wenhao.calculator.character.BaseCharacter;
import com.wenhao.calculator.character.BasicValue;
import com.wenhao.calculator.weapon.Type;
import org.springframework.beans.BeanUtils;

/**
 * Nahida
 *
 * @author: Wenhao Tong
 * @date: 2023/7/7
 */
public class Nahida extends BaseCharacter {

    public Nahida() {
        super(new BasicValue());
        basicValue.setHp(10360.0);
        basicValue.setAtk(296.0);
        basicValue.setCritDmg(0.5);
        basicValue.setCritRate(0.05);
        basicValue.setDefence(630.0);
        basicValue.setCharging(1.0);
        basicValue.setBonus(0.0);
        basicValue.setMastery(115.2);
        basicValue.setSkillDmg(1.858, 3.715, 0.0, 0.0);
        basicValue.setName("纳西妲");
        basicValue.setWeapon(Type.CATALYST);
        BeanUtils.copyProperties(basicValue, this);
    }

    @Override
    public void talent() {
        if (mastery - 200.0 < 0) {
            return;
        }
        Double talentCritRate = Math.min((mastery - 200.0) * 0.0003, 0.24);
        Double talentBonus = Math.min((mastery - 200.0) * 0.001, 0.8);
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.CRIT_RATE).setValue(talentCritRate));
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.BONUS).setValue(talentBonus));
    }

    @Override
    public void setReaction(Damage damage) {
        damage.setReaction(Reaction.SPREAD);
    }

    @Override
    public void party() {
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(100.0));
    }
}
