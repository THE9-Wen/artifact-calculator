package com.wenhao.calculator.weapon;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

import static com.wenhao.calculator.artifact.enums.Keyword.*;
import static com.wenhao.calculator.weapon.Type.CATALYST;
import static com.wenhao.calculator.weapon.Type.POLEARM;

/**
 * Polearm
 *
 * @author: Wenhao Tong
 * @date: 2023/6/21
 */
@Getter
public enum Weapon {
    /**
     * 决斗之枪
     */
    DEATHMATCH(0,
            POLEARM,
            454.0,
            new ArtifactSub().setKeyword(CRIT_RATE).setValue(0.368),
            List.of(new ArtifactSub().setKeyword(ATK).setValue(0.42))),
    /**
     * 神乐之真意
     */
    KAGURAS_VERITY(
            1,
            CATALYST,
            608.07,
            new ArtifactSub().setKeyword(CRIT_DMG).setValue(0.662),
            List.of(new ArtifactSub().setKeyword(BONUS).setValue(0.12),
                    new ArtifactSub().setKeyword(BONUS).setValue(0.12),
                    new ArtifactSub().setKeyword(BONUS).setValue(0.12),
                    new ArtifactSub().setKeyword(BONUS).setValue(0.12))),
    PROTOTYPE_AMBER(
            2,
            CATALYST,
            510.0,
            new ArtifactSub().setKeyword(HP).setValue(0.413),
            Collections.emptyList()
    )
    ;
    @JsonValue
    @EnumValue
    private final Integer key;

    private final Type type;

    private final Double atk;

    private final ArtifactSub main;

    private final List<ArtifactSub> extra;

    Weapon(Integer key, Type type, Double atk, ArtifactSub main, List<ArtifactSub> extra) {
        this.key = key;
        this.type = type;
        this.atk = atk;
        this.main = main;
        this.extra = extra;
    }
}
