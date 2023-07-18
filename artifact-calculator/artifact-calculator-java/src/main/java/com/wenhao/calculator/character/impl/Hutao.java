package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.calculator.Reaction;
import com.wenhao.calculator.character.BaseCharacter;
import com.wenhao.calculator.character.BasicValue;
import com.wenhao.calculator.weapon.Type;
import org.springframework.beans.BeanUtils;

import static com.wenhao.calculator.artifact.enums.Keyword.HP;

/**
 * Hutao
 *
 * @author: Wenhao Tong
 * @date: 2023/6/19
 */
public class Hutao extends BaseCharacter {

    private static final Float HP_1 = 1210.7164f;

    private static final Float ATK_1 = 8.2859f;

    private static final Float DEFENCE_1 = 68.2062f;


    public Hutao(Integer level) {
        super(new BasicValue());
        this.level = level;
        basicValue.setHp((double) (HP_1 * getGrowth()));
        basicValue.setAtk((double) (ATK_1 * getGrowth()));
        basicValue.setDefence((double) (DEFENCE_1 * getGrowth()));
        basicValue.setCritDmg(0.5 + 0.096 * 4);
        basicValue.setCritRate(0.05);
        basicValue.setBonus(0.0);
        basicValue.setCharging(1.0);
        basicValue.setMastery(0.0);
        basicValue.setSkillDmg(2.426);
        basicValue.setName("胡桃");
        basicValue.setWeapon(Type.POLEARM);
        BeanUtils.copyProperties(basicValue, this);
    }

    @Override
    public void talent() {
        ArtifactSub sub = new ArtifactSub();
        sub.setKeyword(Keyword.BONUS).setValue(0.33);
        updateCharacterValue(sub);
        sub.setKeyword(Keyword.ATK_ABS).setValue(Math.min(basicValue.getAtk() * 4.0, hp * 0.0626));
        updateCharacterValue(sub);
    }

    @Override
    public void setReaction(Damage damage) {
        damage.setReaction(Reaction.VAPORIZE);
    }

    @Override
    public void party() {
        updateCharacterValue(new ArtifactSub().setKeyword(HP).setValue(0.25));
    }
}
