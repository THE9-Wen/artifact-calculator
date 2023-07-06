package com.wenhao.calculator.monster;

import lombok.Data;

/**
 * Monster
 *
 * @author: Wenhao Tong
 * @date: 2023/6/19
 */
@Data
public class Monster {
    private Integer level;

    private  Double resist;

    public Monster() {
        level = 87;
        resist = 0.1;
    }

    public Monster(Integer level, Double resist) {
        this.level = level;
        this.resist = resist;
    }
}
