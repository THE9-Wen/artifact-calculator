package com.wenhao.calculator.artifact;

import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.model.Artifact;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author wenhao
 */
@RestController
@RequestMapping("/artifact")
@ResponseBody
public class ArtifactController {
    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @RequestMapping(value = "/", method = GET)
    public List<Artifact> list() {
        return artifactService.list();
    }

    @RequestMapping(value = "/getSuits", method = GET)
    public List<Suit> getSuits() {
        return artifactService.getSuits();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Artifact getById(@PathVariable Long id) {
        return artifactService.getById(id);
    }

    @RequestMapping(value = "/", method = POST, produces = "application/json")
    public Artifact createArtifact(@RequestBody Artifact artifact) {
        return artifactService.createArtifact(artifact);
    }

    @RequestMapping(value = "/{id}", method = PUT, produces = "application/json")
    public Artifact updateArtifact(@PathVariable Long id ,@RequestBody Artifact artifact) {
        artifact.setId(id);
        return artifactService.updateArtifact(artifact);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public Boolean deleteById(@PathVariable Long id) {
        artifactService.deleteArtifact(id);
        return true;
    }

    @RequestMapping(value = "/selectByIds", method = POST)
    public List<Artifact> selectByIds(@RequestBody List<Long> ids) {
        return artifactService.selectByIds(ids);
    }
}
