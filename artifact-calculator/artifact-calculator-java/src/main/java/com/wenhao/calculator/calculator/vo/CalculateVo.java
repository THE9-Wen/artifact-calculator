package com.wenhao.calculator.calculator.vo;

import com.wenhao.calculator.character.BaseCharacter;
import lombok.Data;

/**
 * CalculateVo
 *
 * @author: Wenhao Tong
 * @date: 2023/6/21
 */
@Data
public class CalculateVo {
    private BaseCharacter character;

    private Double basicDamage;

    private Double basicReactDamage;

    private Double critDamage;

    private Double critReactDamage;
}
