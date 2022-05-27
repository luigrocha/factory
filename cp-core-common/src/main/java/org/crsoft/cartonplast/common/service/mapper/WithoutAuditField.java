package org.crsoft.cartonplast.common.service.mapper;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lpillaga on 09/05/2022
 */
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "validFrom", ignore = true)
@Mapping(target = "validTo", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "updatedBy", ignore = true)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "updatedAt", ignore = true)
public @interface WithoutAuditField {
}
