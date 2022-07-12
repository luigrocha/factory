package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.ProjectRes;
import org.crsoft.cartonplast.vo.res.ProjectShortRes;

import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 23/06/2022
 */
public interface IProjectService {

    List<ProjectRes> findAllValidProjects();

    List<ProjectShortRes> findProjectsByClientId(Integer clientId);

    ProjectRes findProjectToCodeGen(String codeGen) throws NotFoundException;
}
