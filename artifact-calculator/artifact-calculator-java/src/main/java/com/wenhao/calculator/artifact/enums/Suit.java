package com.wenhao.calculator.artifact.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import lombok.Getter;

import java.util.List;

import static com.wenhao.calculator.artifact.enums.Keyword.*;

/**
 * @author wenhao
 */
@Getter
public enum Suit {
    /**
     * 角斗士的终幕礼
     */
    GLADIATORS_FINALE(List.of(
            new ArtifactSub().setKeyword(Keyword.ATK).setValue(0.18),
            new ArtifactSub().setKeyword(Keyword.BONUS).setValue(0.35)
    ), 0),
    /**
     * 流浪大地的乐团
     */
    WANDERERS_TROUPE(List.of(
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(80.0),
            new ArtifactSub().setKeyword(Keyword.BONUS).setValue(0.35)
    ), 1),
    /**
     * 昔日宗室之仪
     */
    NOBLESSE_OBLIGE(List.of(
            new ArtifactSub().setKeyword(Keyword.BONUS).setValue(0.2),
            new ArtifactSub().setKeyword(Keyword.ATK).setValue(0.2)
    ), 2),
    /**
     * 魔女
     */
    CRIMSON_WITCH_OF_FLAMES(List.of(
            new ArtifactSub().setKeyword(Keyword.PYRO_BONUS).setValue(0.15),
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(80.0),
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(40.0),
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(40.0),
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(40.0)
    ), 3),
    /**
     * 追忆
     */
    SHIMENAWAS_REMINISCENCE(List.of(
            new ArtifactSub().setKeyword(Keyword.ATK).setValue(0.18),
            new ArtifactSub().setKeyword(Keyword.BONUS).setValue(0.5)
    ), 4),
    /**
     * 翠绿之影
     */
    VIRIDESCENT_VENERER(List.of(
            new ArtifactSub()
    ), 5),
    /**
     * 如雷的盛怒
     */
    THUNDERING_FURY(List.of(
            new ArtifactSub()
    ), 6),
    /**
     * 渡过烈火的贤人
     */
    LAVAWALKER(List.of(
            new ArtifactSub()
    ), 7),
    /**
     * 冰封迷途的勇士
     */
    BLIZZARD_STRAYER(List.of(
            new ArtifactSub()
    ), 8),
    /**
     * 沉沦之心
     */
    HEART_OF_DEPTH(List.of(
            new ArtifactSub()
    ), 9),
    /**
     * 千岩牢固
     */
    TENACITY_OF_THE_MILLELITH(List.of(
            new ArtifactSub()
    ), 10),
    /**
     * 绝缘之旗印
     */
    EMBLEM_OF_SEVERED_FATE(List.of(
            new ArtifactSub()
    ), 11),
    /**
     * 华馆梦醒形骸记
     */
    HUSK_OF_OPULENT(List.of(
            new ArtifactSub().setKeyword(DEFENCE).setValue(0.3)
    ), 12),
    /**
     * 海染砗磲
     */
    OCEAN_HUED_CLAM(List.of(
            new ArtifactSub()
    ), 13),
    /**
     * 辰砂往生录
     */
    VERMILLION_HEREAFTER(List.of(
            new ArtifactSub()
    ), 14),
    /**
     * 深林的记忆
     */
    DEEPWOOD_MEMORIES(List.of(
            new ArtifactSub().setKeyword(DENDRO_BONUS).setValue(0.15)
                    .setKeyword(REDUCTION).setValue(-0.3)
    ), 15),
    /**
     * 饰金之梦
     */
    GILDED_DREAMS(List.of(
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(80.0),
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(80.0),
            new ArtifactSub().setKeyword(Keyword.MASTERY).setValue(80.0)
    ), 16),
    /**
     * 沙上楼阁史话
     */
    DESERT_PAVILION_CHRONICLE(List.of(
            new ArtifactSub()
    ), 17),
    /**
     * 乐园遗落之花
     */
    FLOWER_OF_PARADISE_LOST(List.of(
            new ArtifactSub()
    ), 18),
    /**
     * 水仙之梦
     */
    NYMPHS_DREAM(List.of(
            new ArtifactSub()
    ), 19),
    /**
     * 花海甘露之光
     */
    VOURUKASHAS_GLOW(List.of(
            new ArtifactSub()
    ), 20),
    /**
     * 少女
     */
    MAIDEN_BELOVED(List.of(new ArtifactSub()), 21),
    ;

    private final List<ArtifactSub> subs;

    @EnumValue
    @JsonValue
    private final Integer key;

    Suit(List<ArtifactSub> subs, int key) {
        this.subs = subs;
        this.key = key;
    }
}
