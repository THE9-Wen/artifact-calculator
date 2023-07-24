package com.wenhao.calculator.enka;

import com.wenhao.calculator.artifact.model.Artifact;

import java.util.List;

public interface EnkaService {
    List<Artifact> getArtifactsByUid(String uid);
}
