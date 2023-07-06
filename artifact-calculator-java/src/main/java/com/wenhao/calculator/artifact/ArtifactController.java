package com.wenhao.calculator.artifact;

import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.common.Response;
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
    public Response<List<Artifact>> list() {
        return new Response<>(artifactService.list());
    }

    @RequestMapping(value = "/getSuits", method = GET)
    public Response<List<Suit>> getSuits() {
        return new Response<>(artifactService.getSuits());
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Response<Artifact> getById(@PathVariable Long id) {
        return new Response<>(artifactService.getById(id));
    }

    @RequestMapping(value = "/", method = POST, produces = "application/json")
    public Response<Artifact> createArtifact(@RequestBody Artifact artifact) {
        return new Response<>(artifactService.createArtifact(artifact));
    }

    @RequestMapping(value = "/{id}", method = PUT, produces = "application/json")
    public Response<Artifact> updateArtifact(@PathVariable Long id ,@RequestBody Artifact artifact) {
        artifact.setId(id);
        return new Response<>(artifactService.updateArtifact(artifact));
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public Response<Boolean> deleteById(@PathVariable Long id) {
        artifactService.deleteArtifact(id);
        return new Response<>(true);
    }
}
