package com.wenhao.calculator.artifact.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Keyword
 *
 * @author: Wenhao Tong
 * @date: 2023/6/14
 */
public enum Keyword {
    /**
     * 攻击力百分比
     */
    ATK(0),
    /**
     * 攻击力绝对值
     */
    ATK_ABS(1),
    /**
     * 生命值百分比
     */
    HP(2),
    /**
     * 生命值绝对值
     */
    HP_ABS(3),
    /**
     * 防御力百分比
     */
    DEFENCE(4),
    /**
     * 防御力绝对值
     */
    DEFENCE_ABS(5),
    /**
     * 元素精通
     */
    MASTERY(6),
    /**
     * 暴击伤害
     */
    CRIT_DMG(7),
    /**
     * 暴击率
     */
    CRIT_RATE(8),
    /**
     * 元素充能效率
     */
    ENERGY_RECHARGE(9),
    /**
     * 治疗加成
     */
    HEALING_BONUS(10),
    /**
     * 伤害加成
     */
    BONUS(11),
    /**
     * 火元素伤害加成
     */
    PYRO_BONUS(12),
    /**
     * 物理伤害加成
     */
    PHYSICAL_BONUS(13),
    /**
     * 水元素伤害加成
     */
    HYDRO_BONUS(14),
    /**
     * 草元素伤害加成
     */
    DENDRO_BONUS(15),
    /**
     * 雷元素伤害加成
     */
    ELECTRO_BONUS(16),
    /**
     * 风元素伤害加成
     */
    ANEMO_BONUS(17),
    /**
     * 冰元素伤害加成
     */
    CRO_BONUS(18),
    /**
     * 岩元素伤害加成
     */
    GEO_BONUS(19),
    ;

    @JsonValue
    @EnumValue
    private final int key;

    Keyword(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public boolean isBonus() {
        return key >= 10;
    }
}
