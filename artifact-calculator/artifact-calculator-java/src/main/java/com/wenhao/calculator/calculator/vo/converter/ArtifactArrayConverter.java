package com.wenhao.calculator.calculator.vo.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.fastjson2.JSON;
import com.wenhao.calculator.artifact.model.Artifact;

import java.util.Arrays;
import java.util.List;

/**
 * ArtifactArrayConverter
 *
 * @author: Wenhao Tong
 * @date: 2023/7/8
 */
public class ArtifactArrayConverter implements Converter<Artifact[]> {

    @Override
    public WriteCellData<String> convertToExcelData(Artifact[] value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        List<Long> artifactIds = Arrays.stream(value).map(Artifact::getId).toList();
        return new WriteCellData<>(JSON.toJSONString(artifactIds));
    }
}
