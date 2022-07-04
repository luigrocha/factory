package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.design.service.impl.ProjectService;
import org.crsoft.cartonplast.vo.res.ProjectRes;
import org.crsoft.cartonplast.vo.res.ProjectShortRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 23/06/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectRes>> getAllProjects() {
        return ResponseEntity.ok(this.projectService.findAllValidProjects());
    }

    @GetMapping("/search/client/{clientId}")
    public ResponseEntity<List<ProjectShortRes>> searchProjects(
            @PathVariable("clientId") Integer clientId) {
        return ResponseEntity.ok(this.projectService.findProjectsByClientId(clientId));
    }

    @GetMapping("/search/code/{codeGen}")
    public ResponseEntity<ProjectRes> findProjectToCodeGen(@PathVariable("codeGen") String codeGen) throws NotFoundException {
        return ResponseEntity.ok(this.projectService.findProjectToCodeGen(codeGen));
    }
}
