package com.wenhao.calculator.artifact;

import com.wenhao.calculator.artifact.enums.Position;
import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.model.Artifact;

import java.util.List;

public interface ArtifactService {
    List<Artifact> list();

    Artifact getById(Long id);

    Artifact getByPosition(Position position);

    Artifact getBySuit(Suit suit);

    Artifact createArtifact(Artifact artifact);

    Artifact updateArtifact(Artifact artifact);

    void deleteArtifact(Long id);

    List<Suit> getSuits();
}
