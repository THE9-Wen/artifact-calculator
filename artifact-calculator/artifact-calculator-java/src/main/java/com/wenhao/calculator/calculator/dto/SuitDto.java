package com.wenhao.calculator.calculator.dto;

import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.weapon.Weapon;
import lombok.Data;

import java.util.List;

/**
 * SuitDto
 *
 * @author: Wenhao Tong
 * @date: 2023/6/20
 */
@Data
public class SuitDto {
    private String name;

    private Suit suit;

    private Weapon weapon;

    private List<Keyword> keywords;
}
