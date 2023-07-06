package com.wenhao.calculator.calculator;

import com.wenhao.calculator.artifact.enums.Keyword;
import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.calculator.vo.CalculateResultVo;
import com.wenhao.calculator.calculator.vo.CalculateVo;
import com.wenhao.calculator.weapon.Weapon;

import java.util.List;

public interface CalculatorService {
    CalculateVo calculate(String character, Weapon weapon, List<Artifact> artifacts);

    CalculateResultVo selectSuitArtifacts(Suit suit, Weapon weapon, List<Keyword> keywords, String name);

    CalculateResultVo selectDoubleSuitArtifacts(Keyword suitKeyword1, Keyword suitKeyword2, Weapon weapon, List<Keyword> keywords, String name);
}
