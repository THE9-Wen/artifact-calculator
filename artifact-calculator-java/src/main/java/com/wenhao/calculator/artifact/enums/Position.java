package com.wenhao.calculator.artifact.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum Position {
    /**
     * 生之花
     */
    FLOWER(0),
    /**
     * 死之羽
     */
    PLUME(1),
    /**
     * 空之杯
     */
    SAND(2),
    /**
     * 空之杯
     */
    GOBLET(3),
    /**
     * 理之冠
     */
    CIRCLET(4);

    @EnumValue
    @JsonValue
    private final Integer key;

    Position(int index) {
        this.key = index;
    }

    public Integer getKey() {
        return key;
    }
}
