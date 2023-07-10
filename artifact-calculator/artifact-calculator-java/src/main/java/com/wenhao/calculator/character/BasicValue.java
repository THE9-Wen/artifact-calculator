package com.wenhao.calculator.character;

import com.wenhao.calculator.weapon.Type;
import lombok.Data;

/**
 * BasicValue
 *
 * @author: Wenhao Tong
 * @date: 2023/6/19
 */
@Data
public class BasicValue implements Cloneable {
    private Double atk;

    private Double hp;

    private Double critDmg;

    private Double critRate;

    private Double defence;

    private Double charging;

    private Double bonus;

    private Double mastery;

    /**
     * 攻击倍率 精通倍率 生命倍率 防御倍率
     */
    private final Double[] skillDmg = new Double[4];

    private String name;

    private Type weapon;

    @Override
    public BasicValue clone() {
        try {
            return (BasicValue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Double[] getSkillDmg() {
        return skillDmg;
    }

    public void setSkillDmg(Double atkMagnification) {
        this.skillDmg[0] = atkMagnification;
        this.skillDmg[1] = 0.0;
        this.skillDmg[2] = 0.0;
        this.skillDmg[3] = 0.0;
    }

    public void setSkillDmg(Double atkMagnification, Double masteryMagnification, Double hpMagnification, Double defenceMagnification) {
        this.skillDmg[0] = atkMagnification;
        this.skillDmg[1] = masteryMagnification;
        this.skillDmg[2] = hpMagnification;
        this.skillDmg[3] = defenceMagnification;
    }
}
