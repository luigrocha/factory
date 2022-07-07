package org.crsoft.cartonplast.mixture.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.crsoft.cartonplast.mixture.repository.MixtureRepository;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureDetailMapper;
import org.crsoft.cartonplast.vo.req.MixtureDetailReq;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_INSERT;

/**
 * @author jyepez on 3/7/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MixtureService implements IMixtureService {

    private static final String TABLE_NAME = "CETMIX";
    private final MixtureRepository mixtureRepository;
    private final MixtureDetailMapper mixtureDetailMapper;
    private final MixtureDetailService mixtureDetailService;

    @Override
    public long findNumber() {
        return this.mixtureRepository.count();
    }

    @Override
    public void create(Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq) throws InsertException {
        try {
            Mixture mixtureSave = this.mixtureRepository.save(mixture);
            Collection<MixtureDetail> mixtureDetails =
                    this.mixtureDetailMapper.mixtureDetailResCollectionToMixtureDetailCollection(mixtureDetailsReq);
            mixtureDetails.forEach(mixtureDetail -> {mixtureDetail.setMixture(mixtureSave);});
            this.mixtureDetailService.createAll(mixtureDetails);
        } catch (Exception e) {
            log.error("Error to create mixture: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }
}
