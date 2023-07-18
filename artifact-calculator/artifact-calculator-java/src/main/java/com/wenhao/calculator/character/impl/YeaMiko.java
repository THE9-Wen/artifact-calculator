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
 * YeaMiko
 *
 * @author: Wenhao Tong
 * @date: 2023/6/19
 */
public class YeaMiko extends BaseCharacter {
    private static final Float HP_1 = 807.462f;

    private static final Float ATK_1 = 26.4404f;

    private static final Float DEFENCE_1 = 44.2742f;

    public YeaMiko(Integer level) {
        super(new BasicValue());
        this.level = level;
        basicValue.setHp((double) (HP_1 * getGrowth()));
        basicValue.setAtk((double) (ATK_1 * getGrowth()));
        basicValue.setDefence((double) (DEFENCE_1 * getGrowth()));
        basicValue.setCritRate(0.048 * 4 + 0.05);
        basicValue.setCritDmg(0.5);
        basicValue.setCharging(1.0);
        basicValue.setBonus(0.0);
        basicValue.setMastery(0.0);
        basicValue.setSkillDmg(1.706);
        basicValue.setName("八重神子");
        basicValue.setWeapon(Type.CATALYST);
        BeanUtils.copyProperties(basicValue, this);
    }

    @Override
    public void talent() {
        ArtifactSub sub = new ArtifactSub();
        sub.setKeyword(Keyword.BONUS);
        sub.setValue(0.0015 * mastery);
        this.updateCharacterValue(sub);
    }

    @Override
    public void setReaction(Damage damage) {
        damage.setReaction(Reaction.AGGRAVATE);
    }

    @Override
    public void party() {
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(100.0));
    }
}
