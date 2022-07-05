package org.crsoft.cartonplast.mixture.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.mixture.service.IMixtureDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jyepez on 5/7/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(GlobalConstant.V1_API_VERSION + "/mixture-detail")
public class MixtureDetailController {
    private final IMixtureDetailService mixtureDetailService;

}
