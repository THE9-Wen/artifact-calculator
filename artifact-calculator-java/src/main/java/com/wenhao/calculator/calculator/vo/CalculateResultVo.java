package com.wenhao.calculator.calculator.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.calculator.vo.converter.ArtifactArrayConverter;
import com.wenhao.calculator.character.BaseCharacter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;

/**
 * CalculateExcelData
 *
 * @author: Wenhao Tong
 * @date: 2023/7/7
 */
@Data
@NoArgsConstructor
public class CalculateResultVo {
    @ExcelProperty(value = "攻击力")
    @ContentStyle(dataFormat = 2)
    private Double atk;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "生命值")
    private Double hp;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "暴击伤害")
    private Double critDmg;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "暴击率")
    private Double critRate;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "防御力")
    private Double defence;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "元素充能效率")
    private Double charging;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "伤害加成")
    private Double bonus;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "元素精通")
    private Double mastery;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "基础伤害值")
    private Double basicDamage;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "反应伤害值")
    private Double basicReactDamage;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "暴击伤害值")
    private Double critDamage;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "基础伤害期望")
    private Double basicExpectationDamage;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "反应并暴击伤害值")
    private Double critReactDamage;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "反应伤害期望")
    private Double expectationDamage;

    @ExcelProperty(value = "圣遗物", converter = ArtifactArrayConverter.class)
    private Artifact[] artifacts;

    public CalculateResultVo(Damage damage, Artifact[] artifacts, BaseCharacter character) {
        this.artifacts = artifacts.clone();
        BeanUtils.copyProperties(character, this);
        this.basicDamage = damage.basicDamage(false);
        this.critDamage = damage.critDamage(false);
        this.basicReactDamage = damage.basicDamage(true);
        this.critReactDamage = damage.critDamage(true);
        this.expectationDamage = damage.expectationDamage(true);
        this.basicExpectationDamage = damage.expectationDamage(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalculateResultVo that)) return false;
        return Arrays.equals(artifacts, that.artifacts);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(artifacts);
    }
}
