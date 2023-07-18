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

    private static final Float HP_1 = 806.5f;

    private static final Float ATK_1 = 23.27f;

    private static final Float DEFENCE_1 = 49.06f;

    public Nahida(Integer level) {
        super(new BasicValue());
        this.level = level;
        basicValue.setHp((double) (HP_1 * getGrowth()));
        basicValue.setAtk((double) (ATK_1 * getGrowth()));
        basicValue.setDefence((double) (DEFENCE_1 * getGrowth()));
        basicValue.setMastery(28.8 * 4);
        basicValue.setCritDmg(0.5);
        basicValue.setCritRate(0.05);
        basicValue.setCharging(1.0);
        basicValue.setBonus(0.0);
        basicValue.setSkillDmg(1.8576, 3.7152, 0.0, 0.0);
        basicValue.setName("纳西妲");
        basicValue.setWeapon(Type.CATALYST);
        BeanUtils.copyProperties(basicValue, this);
    }

    /**
     * 纳西妲实战中无法迟到自己大招提供的精通所以天赋中不考虑
     */
    @Override
    public void talent() {
        if (mastery - 200.0 < 0) {
            return;
        }
        Double talentCritRate = Math.min((mastery - 200.0) * 0.0003, 0.24);
        Double talentBonus = Math.min((mastery - 200.0) * 0.001, 0.8);
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.CRIT_RATE).setAccurateValue(talentCritRate));
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.BONUS).setAccurateValue(talentBonus));
    }

    @Override
    public void setReaction(Damage damage) {
        damage.setReaction(Reaction.SPREAD);
    }

    @Override
    public void party() {
        updateCharacterValue(new ArtifactSub().setKeyword(Keyword.MASTERY).setAccurateValue(100.0));
    }
}
