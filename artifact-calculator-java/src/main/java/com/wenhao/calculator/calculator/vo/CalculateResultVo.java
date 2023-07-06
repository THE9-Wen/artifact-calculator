package com.wenhao.calculator.calculator.vo;

import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.character.BaseCharacter;
import lombok.Data;

import java.util.List;

/**
 * SuitVo
 *
 * @author: Wenhao Tong
 * @date: 2023/6/20
 */
@Data
public class CalculateResultVo {
    private List<Artifact> artifacts;

    private BaseCharacter character;

    private Double basicDamage;

    private Double basicReactDamage;

    private Double critDamage;

    private Double critReactDamage;
}
