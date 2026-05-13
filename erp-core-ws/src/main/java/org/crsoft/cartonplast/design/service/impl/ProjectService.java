package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.Project;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.design.repository.ProjectRepository;
import org.crsoft.cartonplast.design.service.IProjectService;
import org.crsoft.cartonplast.design.service.mapper.ProjectMapper;
import org.crsoft.cartonplast.vo.res.ProjectRes;
import org.crsoft.cartonplast.vo.res.ProjectShortRes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

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
    public ProjectRes findProjectToCodeGen(String codeGen) throws NotFoundException {
        Optional<Project> project = this.projectRepository.findProjectToCodeGen(codeGen);
        if(project.isPresent()){
            return this.projectMapper.toProjectRes(project.get());
        } else  {
            log.error("Error to findProjectToCodeGen: {}", codeGen);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
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
