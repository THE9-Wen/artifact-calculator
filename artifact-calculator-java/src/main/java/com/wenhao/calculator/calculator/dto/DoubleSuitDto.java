package com.wenhao.calculator.calculator.dto;

import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.weapon.Weapon;
import lombok.Data;

import java.util.List;

/**
 * DoubleSuitDto
 *
 * @author: Wenhao Tong
 * @date: 2023/6/20
 */
@Data
public class DoubleSuitDto {
    private Keyword suitKeyword1;

    private Keyword suitKeyword2;

    private Weapon weapon;

    private List<Keyword> keywords;

    private String name;
}
