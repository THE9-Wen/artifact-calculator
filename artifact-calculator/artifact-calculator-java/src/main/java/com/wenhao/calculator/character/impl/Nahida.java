package com.wenhao.calculator.character.impl;

import com.wenhao.calculator.calculator.Damage;
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

    protected Nahida(BasicValue basicValue) {
        super(new BasicValue());
        basicValue.setHp(10287.0);
        basicValue.setAtk(337.0);
        basicValue.setCritDmg(0.5);
        basicValue.setCritRate(0.242);
        basicValue.setDefence(565.0);
        basicValue.setCharging(1.0);
        basicValue.setBonus(0.0);
        basicValue.setMastery(0.0);
        basicValue.setSkillDmg(1.706);
        basicValue.setName("纳西妲");
        basicValue.setWeapon(Type.CATALYST);
        BeanUtils.copyProperties(basicValue, this);
    }

    @Override
    public void talent() {

    }

    @Override
    public void setReaction(Damage damage) {

    }

    @Override
    public void party() {

    }
}
