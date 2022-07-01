package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.design.model.Project;
import org.crsoft.cartonplast.vo.res.ProjectRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 23/06/2022
 */
@Mapper(componentModel = "spring", uses = {
        ClientMapper.class,
        HomoPolymerMapper.class,
        ColorBMapper.class,
        CyrelMapper.class,
        TalcMapper.class,
        CalciumCarbonateMapper.class,
        DieProductMapper.class,
        ProjectTypeMapper.class
})
public interface ProjectMapper {

    ProjectRes toProjectRes(Project project);

    List<ProjectRes> toProjectResList(List<Project> projectList);
}
