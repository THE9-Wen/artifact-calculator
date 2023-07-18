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
    ATK(0, 5.83f),
    /**
     * 攻击力绝对值
     */
    ATK_ABS(1, 1945f),
    /**
     * 生命值百分比
     */
    HP(2, 5.83f),
    /**
     * 生命值绝对值
     */
    HP_ABS(3, 29875f),
    /**
     * 防御力百分比
     */
    DEFENCE(4, 7.29f),
    /**
     * 防御力绝对值
     */
    DEFENCE_ABS(5, 2315f),
    /**
     * 元素精通
     */
    MASTERY(6, 2331f),
    /**
     * 暴击伤害
     */
    CRIT_DMG(7, 7.77f),
    /**
     * 暴击率
     */
    CRIT_RATE(8, 3.89f),
    /**
     * 元素充能效率
     */
    ENERGY_RECHARGE(9, 6.48f),
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

    /**
     * 减抗
     */
    REDUCTION(-1),
    ;

    @JsonValue
    @EnumValue
    private final int key;

    private final Float maxValue;

    Keyword(int key, Float maxValue) {
        this.key = key;
        this.maxValue = maxValue;
    }

    Keyword(int key) {
        this.key = key;
        this.maxValue = 0f;
    }

    public int getKey() {
        return key;
    }

    public boolean isBonus() {
        return key >= 10;
    }

    public boolean isReduction() {
        return key < 0;
    }

    public Float getAccurate(Float value) {
        if (maxValue == 0) {
            return value;
        }
        float accurateValue = Math.round(value / maxValue * 1000f) * maxValue;
        if (maxValue < 10) {
            return Math.round(accurateValue * 100f) / 100000f;
        } else {
            return Math.round(accurateValue) / 1000f;
        }
    }
}
