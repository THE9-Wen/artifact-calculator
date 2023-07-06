package com.wenhao.calculator.calculator.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.mapper.ArtifactMapper;
import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.CalculatorService;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.calculator.vo.CalculateResultVo;
import com.wenhao.calculator.calculator.vo.CalculateVo;
import com.wenhao.calculator.character.BaseCharacter;
import com.wenhao.calculator.common.exceptions.ArtifactException;
import com.wenhao.calculator.monster.Monster;
import com.wenhao.calculator.weapon.Weapon;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;

/**
 * CalculatorServiceImpl
 *
 * @author: Wenhao Tong
 * @date: 2023/6/20
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {
    private static final String CHARACTER_PACKAGE_PATH = "com.wenhao.calculator.character.impl.";

    private final ArtifactMapper artifactMapper;

    public CalculatorServiceImpl(ArtifactMapper artifactMapper) {
        this.artifactMapper = artifactMapper;
    }

    @Override
    public CalculateVo calculate(String name, Weapon weapon, List<Artifact> artifacts) {
        BaseCharacter character = constructCharacter(name);
        Damage damage = calculate(character, Weapon.DEATHMATCH, artifacts.toArray(new Artifact[5]), artifacts.get(0).getSuit().getSubs());
        CalculateVo calculateVo = new CalculateVo();
        calculateVo.setCharacter(character);
        calculateVo.setBasicDamage(damage.basicDamage(false));
        calculateVo.setBasicReactDamage(damage.basicDamage(true));
        calculateVo.setCritDamage(damage.critDamage(false));
        calculateVo.setCritReactDamage(damage.critDamage(true));
        return calculateVo;
    }

    @Override
    public CalculateResultVo selectSuitArtifacts(Suit suit, Weapon weapon, List<Keyword> keywords, String name) {
        BaseCharacter character = constructCharacter(name);
        QueryWrapper<Artifact> singleQueryWrapper = new QueryWrapper<>();
        singleQueryWrapper.in("main", keywords.stream().map(Keyword::getKey).toArray());
        List<Artifact> artifacts = artifactMapper.selectList(new QueryWrapper<>())
                .stream()
                .filter(item -> keywords.contains(item.getMain().getKeyword()))
                .toList();

        // 区分套装和散件
        List<Artifact> suits = new ArrayList<>();
        List<Artifact> singles = new ArrayList<>();
        artifacts.forEach(artifact -> {
            if (artifact.getSuit() == suit) {
                suits.add(artifact);
            } else {
                singles.add(artifact);
            }
        });

        var ref = new Object() {
            Damage damage = null;
        };
        CalculateResultVo calculateResultVo = new CalculateResultVo();
        equipArtifacts(suits, suits, singles, equippedArtifacts ->
                ref.damage = updateIfBetter(ref.damage, character.clone(), weapon, equippedArtifacts, suit.getSubs(), calculateResultVo));
        if (ref.damage == null) {
            throw new ArtifactException("无法凑齐四件套");
        }
        calculateResultVo.setBasicDamage(ref.damage.basicDamage(false));
        calculateResultVo.setCritDamage(ref.damage.critDamage(false));
        calculateResultVo.setCritReactDamage(ref.damage.critDamage(true));
        calculateResultVo.setBasicReactDamage(ref.damage.basicDamage(true));
        return calculateResultVo;
    }

    private Damage updateIfBetter(Damage damage, BaseCharacter clone, Weapon weapon, Artifact[] equippedArtifacts, List<ArtifactSub> suit, CalculateResultVo calculateResultVo) {
        Damage temp = calculate(clone, weapon, equippedArtifacts, suit);
        if (damage == null || damage.expectationDamage(true) < temp.expectationDamage(true)) {
            calculateResultVo.setCharacter(clone);
            calculateResultVo.setArtifacts(List.of(equippedArtifacts));
            return temp;
        }
        return damage;
    }

    @Override
    public CalculateResultVo selectDoubleSuitArtifacts(Keyword suitKeyword1, Keyword suitKeyword2, Weapon weapon, List<Keyword> keywords,  String name) {
        BaseCharacter character = constructCharacter(name);
        List<Artifact> artifacts = artifactMapper.selectList(new QueryWrapper<>())
                .stream()
                .filter(item -> keywords.contains(item.getMain().getKeyword()))
                .toList();

        // 筛选出二件套效果符合要求的
        HashMap<Suit, List<Artifact>> keywordListHashMap1 = new HashMap<>();
        HashMap<Suit, List<Artifact>> keywordListHashMap2 = new HashMap<>();
        for (Suit suit : Suit.values()) {
            Keyword keyword = suit.getSubs().get(0).getKeyword();
            if (keyword == suitKeyword1) {
                keywordListHashMap1.put(suit, new ArrayList<>());
            }
            if (keyword == suitKeyword2) {
                keywordListHashMap2.put(suit, new ArrayList<>());
            }
        }
        List<Artifact> singles = new ArrayList<>();
        artifacts.forEach(artifact -> {
            Keyword keyword = artifact.getSuit().getSubs().get(0).getKeyword();
            if (keyword != suitKeyword1 && keyword != suitKeyword2) {
                singles.add(artifact);
                return;
            }
            if (keyword == suitKeyword1 && keywordListHashMap1.containsKey(artifact.getSuit())) {
                keywordListHashMap1.get(artifact.getSuit()).add(artifact);
            }
            if (keyword == suitKeyword2 && keywordListHashMap2.containsKey(artifact.getSuit())) {
                keywordListHashMap2.get(artifact.getSuit()).add(artifact);
            }
        });

        var ref = new Object() {
            Damage damage = null;
        };
        CalculateResultVo calculateResultVo = new CalculateResultVo();

        keywordListHashMap1.forEach((key1, list1) -> {
            if (list1.size() < 2) {
                return;
            }
            keywordListHashMap2.forEach((key2, list2) -> {
                if (key1 == key2 || list2.size() < 2) {
                    return;
                }
                equipArtifacts(list1, list2, singles, equippedArtifacts -> {
                    ArrayList<ArtifactSub> subs = new ArrayList<>();
                    subs.add(list1.get(0).getSuit().getSubs().get(0));
                    subs.add(list2.get(0).getSuit().getSubs().get(0));
                    ref.damage = updateIfBetter(ref.damage, character.clone(), weapon, equippedArtifacts, subs, calculateResultVo);
                });
            });
        });
        if (ref.damage == null) {
            throw new ArtifactException("无法生成五件圣遗物");
        }
        calculateResultVo.setBasicDamage(ref.damage.basicDamage(false));
        calculateResultVo.setCritDamage(ref.damage.critDamage(false));
        calculateResultVo.setCritReactDamage(ref.damage.critDamage(true));
        calculateResultVo.setBasicReactDamage(ref.damage.basicDamage(true));
        return calculateResultVo;
    }

    private void equipArtifacts(List<Artifact> list1, List<Artifact> list2, List<Artifact> singles, Consumer<Artifact[]> consumer) {
        Artifact[] equippedArtifacts = new Artifact[5];
        list1.forEach(artifact1 -> {
            if (conflictPosition(equippedArtifacts, artifact1)) return;
            list1.forEach(artifact2 -> {
                if (conflictPosition(equippedArtifacts, artifact2)) return;
                list2.forEach(artifact3 -> {
                    if (conflictPosition(equippedArtifacts, artifact3)) return;
                    list2.forEach(artifact4 -> {
                        if (conflictPosition(equippedArtifacts, artifact4)) return;
                        singles.forEach(artifact5 -> {
                            if (conflictPosition(equippedArtifacts, artifact5)) return;
                            consumer.accept(equippedArtifacts);
                            removeArtifact(equippedArtifacts, artifact5);
                        });
                        removeArtifact(equippedArtifacts, artifact4);
                    });
                    removeArtifact(equippedArtifacts, artifact3);
                });
                removeArtifact(equippedArtifacts, artifact2);
            });
            removeArtifact(equippedArtifacts, artifact1);
        });
    }

    private void removeArtifact(Artifact[] equippedArtifacts, Artifact artifact) {
        equippedArtifacts[artifact.getPosition().getKey()] = null;
    }

    private static boolean conflictPosition(Artifact[] equippedArtifacts, Artifact artifact) {
        Integer position2 = artifact.getPosition().getKey();
        if (equippedArtifacts[position2] != null) {
            return true;
        }
        equippedArtifacts[position2] = artifact;
        return false;
    }

    private BaseCharacter constructCharacter(String name) {
        BaseCharacter character;
        try {
            Class<?> characterClass = Class.forName(CHARACTER_PACKAGE_PATH + name);
            Constructor<?> constructor = characterClass.getDeclaredConstructor();
            Object temp = constructor.newInstance();
            if (temp instanceof BaseCharacter) {
                character = (BaseCharacter) temp;
            } else {
                throw new ArtifactException("Fail to construct " + name + ".");
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new ArtifactException("There's no character named " + name + ".");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ArtifactException("Fail to construct " + name + ".");
        }
        return character;
    }

    private Damage calculate(BaseCharacter character, Weapon weapon, Artifact[] artifacts, List<ArtifactSub> suit) {
        character.equipWeapon(weapon);
        for (Artifact artifact : artifacts) {
            if (artifact == null) {
                continue;
            }
            character.equipArtifact(artifact);
        }
        suit.forEach(character::updateCharacterValue);
        return character.hit(new Monster());
    }

}
