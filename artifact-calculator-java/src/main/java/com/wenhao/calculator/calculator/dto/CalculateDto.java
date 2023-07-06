package com.wenhao.calculator.calculator.dto;

import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.weapon.Weapon;
import lombok.Data;

import java.util.List;

/**
 * CalculateDto
 *
 * @author: Wenhao Tong
 * @date: 2023/6/21
 */
@Data
public class CalculateDto {
    private String name;

    private Weapon weapon;

    private List<Artifact> artifacts;
}
