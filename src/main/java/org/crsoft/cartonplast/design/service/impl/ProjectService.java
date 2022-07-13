package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.Project;
import org.crsoft.cartonplast.design.repository.ProjectRepository;
import org.crsoft.cartonplast.design.service.IProjectService;
import org.crsoft.cartonplast.design.service.mapper.ProjectMapper;
import org.crsoft.cartonplast.vo.res.ProjectRes;
import org.crsoft.cartonplast.vo.res.ProjectShortRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 23/06/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectRes> findAllValidProjects() {
        return projectMapper.toProjectResList(projectRepository.findAllValidProjects());
    }

    @Override
    public List<ProjectShortRes> findProjectsByClientId(Integer clientId) {
        return projectMapper.toProjectShortResList(projectRepository.findProjectsByClientId(clientId));
    }

    @Override
    public Project findProjectById(Integer projectId) {
        return this.projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.error("Project not found with id: {}", projectId);
                    return new BusinessException(BusinessExceptionReason.PROJECT_NOT_FOUND, projectId);
                });
    }
}
