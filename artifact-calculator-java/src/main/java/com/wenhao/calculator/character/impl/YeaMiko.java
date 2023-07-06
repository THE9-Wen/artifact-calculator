package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.calculator.Reaction;
import com.wenhao.calculator.character.BaseCharacter;
import com.wenhao.calculator.character.BasicValue;
import org.springframework.beans.BeanUtils;

/**
 * YeaMiko
 *
 * @author: Wenhao Tong
 * @date: 2023/6/19
 */
public class YeaMiko extends BaseCharacter {

    public YeaMiko() {
        super(new BasicValue());
        basicValue.setHp(10300.0);
        basicValue.setAtk(337.0);
        basicValue.setCritDmg(0.5);
        basicValue.setCritRate(0.242);
        basicValue.setDefence(565.0);
        basicValue.setCharging(1.0);
        basicValue.setBonus(0.0);
        basicValue.setMastery(0.0);
        basicValue.setSkillDmg(1.706);
        basicValue.setName("八重神子");
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
