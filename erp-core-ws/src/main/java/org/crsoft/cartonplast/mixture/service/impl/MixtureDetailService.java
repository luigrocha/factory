package org.crsoft.cartonplast.mixture.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.crsoft.cartonplast.mixture.repository.MixtureDetailRepository;
import org.crsoft.cartonplast.mixture.service.IMixtureDetailService;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureDetailMapper;
import org.crsoft.cartonplast.vo.res.MixtureDetailRes;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_INSERT;

/**
 * @author jyepez on 5/7/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MixtureDetailService implements IMixtureDetailService {
    private static final String TABLE_NAME = "CETMIX_DET";
    private final MixtureDetailRepository mixtureDetailRepository;
    private final MixtureDetailMapper mixtureDetailMapper;

    @Override
    public void create(MixtureDetail mixtureDetail) throws InsertException {
        try {
            this.mixtureDetailRepository.save(mixtureDetail);
        } catch (Exception e) {
            log.error("Error to create mixture detail: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public void createAll(Collection<MixtureDetail> mixtureDetails) throws InsertException {
        try {
            this.mixtureDetailRepository.saveAll(mixtureDetails);
        } catch (Exception e) {
            log.error("Error to create all mixture detail: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public Collection<MixtureDetailRes> findAllByMixtureCode(Integer mixtureCode) {
        return this.mixtureDetailMapper.mixtureDetailCollectionToMixtureDetailRes(
                this.mixtureDetailRepository.findAllValidMixtureByMixtureCode(mixtureCode));
    }

    @Override
    public void edit(Collection<MixtureDetail> mixtureDetails) {
        Integer idMixture = mixtureDetails.stream().findAny().get().getMixture().getId();
        Collection<MixtureDetail> mixtureDetailsFind = this.mixtureDetailRepository
                .findAllValidMixtureByMixtureCode(idMixture);

        this.mixtureDetailRepository.deleteAll(mixtureDetailsFind);
        this.mixtureDetailRepository.saveAll(mixtureDetails);

    }


}
