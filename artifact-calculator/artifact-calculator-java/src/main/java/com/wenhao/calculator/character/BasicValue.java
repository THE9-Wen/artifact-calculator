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

    private Double skillDmg;

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
}
