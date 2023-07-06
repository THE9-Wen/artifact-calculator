package com.wenhao.calculator.artifact.model.typehandler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.wenhao.calculator.artifact.model.ArtifactSub;

import java.util.List;

/**
 * ArtifactSubListTypeHandler
 *
 * @author: Wenhao Tong
 * @date: 2023/6/24
 */
public class ArtifactSubListTypeHandler extends AbstractJsonTypeHandler<List<ArtifactSub>> {
    @Override
    protected List<ArtifactSub> parse(String json) {
        return JSON.parseArray(json, ArtifactSub.class);
    }

    @Override
    protected String toJson(List<ArtifactSub> obj) {
        return JSON.toJSONString(obj);
    }
}
