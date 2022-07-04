package org.crsoft.cartonplast.mixture.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.mixture.repository.MixtureRepository;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.springframework.stereotype.Service;

/**
 * @author jyepez on 3/7/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MixtureService implements IMixtureService {

    private final MixtureRepository mixtureRepository;

    @Override
    public long findNumber() {
        return this.mixtureRepository.count();
    }
}
