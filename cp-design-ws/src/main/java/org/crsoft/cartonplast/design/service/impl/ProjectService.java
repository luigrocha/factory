package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.repository.ProjectRepository;
import org.crsoft.cartonplast.design.service.IProjectService;
import org.crsoft.cartonplast.design.service.mapper.ProjectMapper;
import org.crsoft.cartonplast.design.vo.res.ProjectRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 23/06/2022
 */
@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectRes> findAllValidProjects() {
        return projectMapper.toProjectResList(projectRepository.findAllValidProjects());
    }
}
