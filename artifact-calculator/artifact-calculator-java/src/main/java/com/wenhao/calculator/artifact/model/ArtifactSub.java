package com.wenhao.calculator.artifact.model;

import com.wenhao.calculator.artifact.enums.Keyword;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ArtifactSub
 *
 * @author: Wenhao Tong
 * @date: 2023/6/14
 */
@Data
@Accessors(chain = true)
public class ArtifactSub {
    private Keyword keyword;

    private Double value;
}
