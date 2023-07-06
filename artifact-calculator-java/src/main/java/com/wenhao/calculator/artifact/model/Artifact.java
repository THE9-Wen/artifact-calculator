package com.wenhao.calculator.artifact.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.wenhao.calculator.artifact.enums.Position;
import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.model.typehandler.ArtifactSubListTypeHandler;
import lombok.Data;

import java.util.List;

/**
 * ArtifactDO
 *
 * @author: Wenhao Tong
 * @date: 2023/6/15
 */
@Data
@TableName(autoResultMap = true)
public class Artifact {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Position position;

    private Suit suit;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private ArtifactSub main;

    @TableField(typeHandler = ArtifactSubListTypeHandler.class)
    private List<ArtifactSub> subs;
}
