package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.ProjectType;
import org.crsoft.cartonplast.vo.res.ProjectTypeRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 23/06/2022
 */
@Mapper(componentModel = "spring")
public interface ProjectTypeMapper {

    ProjectTypeRes toProjectTypeRes(ProjectType projectType);
}
