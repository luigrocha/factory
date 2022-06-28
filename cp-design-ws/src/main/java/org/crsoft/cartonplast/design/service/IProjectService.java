package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.res.ProjectRes;

import java.util.List;

/**
 * @author lpillaga on 23/06/2022
 */
public interface IProjectService {

    List<ProjectRes> findAllValidProjects();
}
