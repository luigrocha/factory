package org.crsoft.cartonplast.mixture.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.crsoft.cartonplast.mixture.repository.MixtureRepository;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureDetailMapper;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureMapper;
import org.crsoft.cartonplast.vo.req.MixtureDetailReq;
import org.crsoft.cartonplast.vo.res.MixtureRes;
import org.crsoft.cartonplast.vo.res.MixtureShortRes;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_INSERT;
import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 3/7/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MixtureService implements IMixtureService {

    private static final String TABLE_NAME = "CETMIX";
    private final MixtureRepository mixtureRepository;
    private final MixtureMapper mixtureMapper;
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

    @Override
    public Collection<MixtureShortRes> findByQuery(String query) {
        return this.mixtureMapper.mixtureCollectionToMixtureShort(
                this.mixtureRepository.findAllValidMixtureFromQuery(query));
    }

    @Override
    public MixtureRes findByNumber(Integer number) {
        MixtureRes mixture = this.mixtureMapper.mixtureToMixtureRes(
                this.mixtureRepository.findValidMixtureByNumber(number)
        );
        mixture.setRows(this.mixtureDetailService.findAllByMixtureCode(mixture.getId()));
        return mixture;
    }

    @Override
    public Integer findNumberByLot(String lot) {
        try {
            Mixture mixture = this.mixtureRepository.findValidMixtureByLot(lot);
            return mixture.getNumber();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void edit(Integer id, Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq) {
        Optional<Mixture> findMixture = mixtureRepository.findById(id);
        if(findMixture.isPresent()){
            mixture.setId(findMixture.get().getId());
            this.mixtureRepository.save(mixture);

            Collection<MixtureDetail> mixtureDetails =
                    this.mixtureDetailMapper.mixtureDetailResCollectionToMixtureDetailCollection(mixtureDetailsReq);
            mixtureDetails.forEach(mixtureDetail -> {mixtureDetail.setMixture(findMixture.get());});
            this.mixtureDetailService.edit(mixtureDetails);
        }else {
            throw new NotExistException(MESSAGE_NOT_FOUND);
        }
    }
}
