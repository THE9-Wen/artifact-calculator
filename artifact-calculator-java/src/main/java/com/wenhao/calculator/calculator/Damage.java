package com.wenhao.calculator.calculator;

import lombok.Data;

import static com.wenhao.calculator.calculator.Reaction.NONE;

/**
 * Damage
 *
 * @author: Wenhao Tong
 * @date: 2023/6/20
 */
@Data
public class Damage {
    private Double basicDmg = 0.0;

    private Double critDmg = 0.0;

    private Double critRate = 0.0;

    private Double mastery = 0.0;

    private Reaction reaction = NONE;

    private Double reactionBonus;

    /**
     * @return reaction bonus
     */
    private Double reactionBonus() {
        if (reactionBonus != null) {
            return reactionBonus;
        }
        return switch (reaction) {
            case VAPORIZE, MELT -> 1.5 * (1.0 + (2.78 * mastery) / (1400.0 + mastery));
            case AGGRAVATE, SPREAD -> 1 + 1.15 * 1405.097377 * (1.0 + (5.0 * mastery) / (1200.0 + mastery)) / basicDmg;
            case NONE -> 1.0;
        };
    }

    public Double basicDamage(Boolean react) {
        if (react) {
            return basicDmg * reactionBonus();
        }
        return basicDmg;
    }

    public Double critDamage(Boolean react) {
        return (critDmg + 1) * basicDamage(react);
    }

    public Double expectationDamage(Boolean react) {
        if (critRate > 1) {
            return critDamage(react);
        }
        return critDamage(react) * critRate + basicDamage(react) * (1 - critRate);
    }
}
